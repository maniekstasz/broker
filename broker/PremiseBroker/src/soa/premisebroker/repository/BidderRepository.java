package soa.premisebroker.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import soa.common.model.AbstractReservation.ReservationStatus;
import soa.premisebroker.model.Bidder;
import soa.premisebroker.model.BidderWebHook;
import soa.premisebroker.model.BidderWebHook.WebHooType;

@RepositoryRestResource
public interface BidderRepository extends
		PagingAndSortingRepository<Bidder, Long>, BidderRepositoryCustom {

	@RestResource(exported = false)
	@Query("select webHook from BidderWebHook webHook left join webHook.bidder bidder where bidder.id = :bidderId and webHook.type=:type")
	public BidderWebHook getBidderWebHook(@Param("bidderId") Long bidderId,
			@Param("type") WebHooType type);

	@RestResource(exported = false)
	@Query("select offer.bidder from PremiseReservation reservation left join reservation.premise premise left join premise.offer offer where reservation.createdDate > :date and reservation.status = :status")
	public List<Bidder> getBiddersForReservationApproval(
			@Param("date") @Temporal Date date,
			@Param("status") ReservationStatus status);

	@RestResource(exported = false)
	@Query("select bidder from Bidder bidder left join bidder.createdBy cb where cb.id = :createdById")
	public Bidder findByCreatedById(@Param("createdById") Long createdById);
}