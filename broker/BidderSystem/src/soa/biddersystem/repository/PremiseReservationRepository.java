package soa.biddersystem.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import soa.biddersystem.model.PremiseReservation;
import soa.common.model.AbstractReservation.ReservationStatus;

public interface PremiseReservationRepository extends
		PagingAndSortingRepository<PremiseReservation, Long> {

	@Query("select count(r.id) from PremiseReservation r left join r.premise p where (r.reservedFrom between :dateFrom  and :dateTo or r.reservedTo between :dateFrom  and :dateTo) and status = ACCEPTED and p=:premiseId")
	public Long isAvailable(@Param("dateFrom") Date dateFrom,@Param("dateTo") Date dateTo, @Param("premiseId") Long premiseId);
}
