package soa.premisebroker.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import soa.common.model.AbstractReservation.ReservationStatus;
import soa.premisebroker.model.PremiseReservation;

public interface PremiseReservationRepository extends
		PagingAndSortingRepository<PremiseReservation, Long> {

	@RestResource(exported = false)
	@Query("select reservation from PremiseReservation reservation left join reservation.premise premise left join premise.offer offer where reservation.createdDate > :date and reservation.status = :status and offer.bidder.id =:bidderId")
	public List<PremiseReservation> getAwaitaningReservationsForSend(
			@Param("date") @Temporal Date date,
			@Param("status") ReservationStatus status,
			@Param("bidderId") Long bidderId);
	
	@RestResource(exported = false)
	@Query("select reservation from PremiseReservation reservation left join reservation.premise premise left join premise.offer offer where reservation.status = '3' and offer.bidder.id =:bidderId")
	public List<PremiseReservation> getBidderAwaitaningReserwations(@Param("bidderId") Long bidderId);

	
	@RestResource(path="/bidder", rel="bidderReservations")
	@Query("select reservation from PremiseReservation reservation left join reservation.premise premise left join premise.offer offer where offer.bidder.id =:bidderId")
	public List<PremiseReservation> getBidderReserwations(@Param("bidderId") Long bidderId);
}
