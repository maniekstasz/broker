package soa.premisebroker.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="favouriteOffer", types={FavouriteOffer.class})
public interface FavouriteOfferProjection {
	public Offer getOffer();
}
