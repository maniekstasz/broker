package soa.premisebroker.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import soa.premisebroker.model.Premise;
import soa.premisebroker.model.User;

@RepositoryRestResource
public interface PremiseRepository extends PagingAndSortingRepository<Premise, Long> {
	
	@RestResource(exported = false)
	@Query("select bidder.createdBy from PremiseReservation reservation left join reservation.premise premise left join premise.offer offer left join offer.bidder bidder where premise.id =:premiseId")
	public User getPremiseOwner(@Param("premiseId") Long premiseId);
}
