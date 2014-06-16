package soa.biddersystem.eventhandlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import soa.biddersystem.model.PremiseReservation;
import soa.biddersystem.repository.PremiseRepository;
import soa.biddersystem.repository.PremiseReservationRepository;
import soa.common.model.AbstractReservation.ReservationStatus;
import soa.common.security.SecurityEventHandlerSupportBean;

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
	public void checkAvailability(PremiseReservation reservation){
		setStatus(reservation);
	}


	private void setStatus(PremiseReservation reservation) {
		if (premiseReservationRepository.isAvailable(
				reservation.getReservedFrom(), reservation.getReservedTo(),
				reservation.getPremise().getId()) > 0) {
			reservation.setStatus(ReservationStatus.DECLINED);
			reservation.setBidderNotes("Not available");
		} else {
			reservation.setBidderNotes("Payment on the spot");
			reservation.setStatus(ReservationStatus.ACCEPTED);
		}
	}
}
