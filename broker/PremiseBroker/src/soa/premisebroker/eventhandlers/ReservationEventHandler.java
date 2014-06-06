package soa.premisebroker.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.access.AccessDeniedException;

import soa.common.model.User;
import soa.common.security.LoggedUser;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.webservices.response.ReservationRequestResponse;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.BidderWebHook;
import soa.premisebroker.model.PremiseReservation;
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
		ReservationRequestResponse response = getReservationResponse(reservation);
		if (response != null) {
			reservation.setBidderNotes(response.getBidderNotes());
			reservation.setStatus(response.getStatus());
			premiseReservationRepository.save(reservation);
		}
	}

	private ReservationRequestResponse getReservationResponse(
			PremiseReservation reservation) {
		Bidder bidder = reservation.getPremise().getOffer().getBidder();
		BidderWebHook webHook = bidderRepository.getBidderWebHook(
				bidder.getId(), WebHooType.RESERVATION_REQUEST);
		if (webHook != null) {
			ReservationRequestResponse response = bidderRequester
					.sendReservationRequest(webHook.getUri(), reservation);
			return response;
		}
		return null;
	}

	@HandleBeforeSave
	private void isAuthorized(PremiseReservation reservation) {
		LoggedUser loggedUser = securityEventHandlerSupportBean.getLoggedUser();
		PremiseReservation resInDb = premiseReservationRepository
				.findOne(reservation.getId());
		User user = premiseRepository.getPremiseOwner(resInDb.getPremise()
				.getId());
		if (!user.getId().equals(loggedUser.getId()))
			throw new AccessDeniedException("Not authorized");
	}

}
