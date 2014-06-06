package soa.premisebroker.quartz.job;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import soa.common.model.User;
import soa.common.model.AbstractReservation.ReservationStatus;
import soa.premisebroker.extern.BidderRequester;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.PremiseReservation;
import soa.premisebroker.repository.BidderRepository;
import soa.premisebroker.repository.PremiseReservationRepository;

public class OflineReservationSendJob extends QuartzJobBean {

	@Autowired
	private PremiseReservationRepository reservationRepository;

	@Autowired
	private BidderRepository bidderRepository;

	@Autowired
	private BidderRequester bidderRequester;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		Date previosJobDate = context.getPreviousFireTime();
		if (previosJobDate == null)
			previosJobDate = new Date();
		List<Bidder> bidders = bidderRepository
				.getBiddersForReservationApproval(previosJobDate,
						ReservationStatus.AWAITANING);
		if (bidders != null)
			for (Bidder bidder : bidders) {
				List<PremiseReservation> reservations = reservationRepository
						.getAwaitaningRepositoriesForSend(previosJobDate,
								ReservationStatus.AWAITANING, bidder.getId());
				bidderRequester.sendReservationsRequestsEmail(((User) bidder
						.getCreatedBy()).getCredentials().getMail(),
						reservations);
			}

	}

}
