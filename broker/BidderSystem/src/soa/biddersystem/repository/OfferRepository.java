package soa.biddersystem.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.biddersystem.model.Offer;


@RepositoryRestResource
public interface OfferRepository extends
		PagingAndSortingRepository<Offer, Long> {
	
}
