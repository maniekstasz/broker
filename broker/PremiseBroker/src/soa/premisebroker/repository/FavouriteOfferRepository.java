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
import soa.premisebroker.model.FavouriteOffer;

@RepositoryRestResource
public interface FavouriteOfferRepository extends
		PagingAndSortingRepository<FavouriteOffer, Long> {
}