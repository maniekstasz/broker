package soa.premisebroker.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import soa.common.model.AbstractUser;

@Entity
@Table(name = "users")
public class User extends AbstractUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2678415287912930990L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
	private List<PremiseReservation> premiseReservations;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
	private List<Bidder> bidders;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<FavouriteOffer> favouriteOffers;

	public List<PremiseReservation> getPremiseReservations() {
		return premiseReservations;
	}

	public void setPremiseReservations(
			List<PremiseReservation> premiseReservations) {
		this.premiseReservations = premiseReservations;
	}

	@JsonIgnore
	public List<Bidder> getBidders() {
		return bidders;
	}

	public void setBidder(List<Bidder> bidders) {
		this.bidders = bidders;
	}

	public List<FavouriteOffer> getFavouriteOffers() {
		return favouriteOffers;
	}

	public void setFavouriteOffers(List<FavouriteOffer> favouriteOffers) {
		this.favouriteOffers = favouriteOffers;
	}


}
