package soa.premisebroker.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import soa.premisebroker.model.FavouriteOffer;

@RepositoryRestResource
public interface FavouriteOfferRepository extends
		PagingAndSortingRepository<FavouriteOffer, Long> {
}