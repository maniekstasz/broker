package soa.premisebroker.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;

import soa.common.model.PremiseReservationDto;
import soa.common.security.LoggedUser;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.webservices.response.ReservationRequestResponse;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.BidderWebHook;
import soa.premisebroker.model.PremiseReservation;
import soa.premisebroker.model.User;
import soa.premisebroker.model.BidderWebHook.WebHooType;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.PremiseRepository;
import soa.premisebroker.repository.PremiseReservationRepository;

@RepositoryEventHandler(PremiseReservation.class)
public class ReservationEventHandler {

	@Autowired
	private BidderRequester bidderRequester;

	@Autowired
	private BidderRepository bidderRepository;
	@Autowired
	private PremiseReservationRepository premiseReservationRepository;

	@Autowired
	private SecurityEventHandlerSupportBean securityEventHandlerSupportBean;

	@Autowired
	private PremiseRepository premiseRepository;

	@HandleAfterCreate
	public void afterCreate(PremiseReservation reservation) {
		handleReservationChange(reservation, false);

	}

	private void handleReservationChange(PremiseReservation reservation,
			boolean update) {
		BidderWebHook webHook = getWebHook(reservation);
		if (webHook != null) {
			PremiseReservationDto resDto = getResDto(reservation);
			if (resDto == null)
				return;
			ReservationRequestResponse response = bidderRequester
					.sendReservation(webHook.getUri(), resDto, update);
			if (response != null) {
				reservation.setBidderNotes(response.getBidderNotes());
				reservation.setStatus(response.getStatus());
				premiseReservationRepository.save(reservation);
			}
		}
	}

	private void isAuthorized(PremiseReservation reservation) {
		LoggedUser loggedUser = securityEventHandlerSupportBean.getLoggedUser();
		PremiseReservation resInDb = premiseReservationRepository
				.findOne(reservation.getId());
		if (resInDb.getCreatedBy().getId().equals(loggedUser.getId()) && resInDb.getStatus().equals(reservation.getStatus()))
			return;
		User user = premiseRepository.getPremiseOwner(resInDb.getPremise()
				.getId());
		if (!user.getId().equals(loggedUser.getId()))
			throw new AccessDeniedException("Not authorized");
	}

	@HandleBeforeSave
	public void afterSave(PremiseReservation reservation) {
		isAuthorized(reservation);
		handleReservationChange(reservation, true);
	}

	private BidderWebHook getWebHook(PremiseReservation reservation) {
		Bidder bidder = reservation.getPremise().getOffer().getBidder();
		BidderWebHook webHook = bidderRepository.getBidderWebHook(
				bidder.getId(), WebHooType.RESERVATION_REQUEST);
		return webHook;
	}

	private PremiseReservationDto getResDto(PremiseReservation reservation) {
		return new PremiseReservationDto(reservation.getReservedFrom(),
				reservation.getReservedTo(), reservation.getStatus(),
				reservation.getUserNotes(), null, reservation.getPremise()
						.getId(), reservation.getId());
	}

}
