package soa.biddersystem.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import soa.biddersystem.controller.ReservationDeclinedException;
import soa.biddersystem.controller.ReturnReservationResponseHack;
import soa.biddersystem.model.PremiseReservation;
import soa.biddersystem.repository.PremiseRepository;
import soa.biddersystem.repository.PremiseReservationRepository;
import soa.common.model.AbstractReservation.ReservationStatus;
import soa.common.security.LoggedUser;
import soa.common.security.SecurityEventHandlerSupportBean;
import soa.common.webservices.response.ReservationRequestResponse;

@RepositoryEventHandler(PremiseReservation.class)
public class ReservationEventHandler {

	@Autowired
	private PremiseReservationRepository premiseReservationRepository;

	@Autowired
	private SecurityEventHandlerSupportBean securityEventHandlerSupportBean;

	@Autowired
	private PremiseRepository premiseRepository;

	@Autowired
	private Environment env;

	@HandleBeforeCreate
	public void checkAvailability(PremiseReservation reservation)
			throws ReservationDeclinedException {
		if (premiseReservationRepository.isAvailable(
				reservation.getReservedFrom(), reservation.getReservedTo(),
				reservation.getPremise().getId()) > 0) {
			ReservationRequestResponse reservationRequestResponse = new ReservationRequestResponse(
					ReservationStatus.DECLINED,
					"Obiekt jest ju¿ zajêty w tym czasie");
			throw new ReservationDeclinedException(reservationRequestResponse);
		}

	}

	@HandleAfterCreate
	public void afterCreate(PremiseReservation reservation)
			throws ReturnReservationResponseHack {
		if (isPremiseBroker()) {
			ReservationRequestResponse reservationRequestResponse = new ReservationRequestResponse(
					ReservationStatus.ACCEPTED, "P³atne gotówk¹ na miejscu");
			throw new ReturnReservationResponseHack(reservationRequestResponse);
		}
	}

	private boolean isPremiseBroker() {
		LoggedUser loggedUser = securityEventHandlerSupportBean.getLoggedUser();
		return loggedUser.getUsername().equals(
				env.getProperty("premisebroker.credentials.username"));
	}
}
