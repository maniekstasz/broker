package soa.biddersystem.repository;

import java.util.Date;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import soa.biddersystem.model.PremiseReservation;

public interface PremiseReservationRepository extends
		PagingAndSortingRepository<PremiseReservation, Long> {

	@Query("select count(r.id) from PremiseReservation r left join r.premise p where not (r.reservedFrom >= :dateTo or r.reservedTo <= :dateFrom)  and status = '0' and p.id=:premiseId")
	public Long isAvailable(@Param("dateFrom") Date dateFrom,@Param("dateTo") Date dateTo, @Param("premiseId") Long premiseId);
	
	@Query("select count(r.id) from PremiseReservation r left join r.premise p where not (r.reservedFrom >= :dateTo or r.reservedTo <= :dateFrom) and status = '0' and p.foreignId=:premiseId")
	public Long isAvailableByForeign(@Param("dateFrom") Date dateFrom,@Param("dateTo") Date dateTo, @Param("premiseId") Long premiseId);
	
	
	public PremiseReservation findByForeignId(@Param("foreignId") Long foreignId);
}
