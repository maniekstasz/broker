package soa.premisebroker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import soa.premisebroker.model.Offer;

@RepositoryRestResource
public interface OfferRepository extends
		PagingAndSortingRepository<Offer, Long> {
}
