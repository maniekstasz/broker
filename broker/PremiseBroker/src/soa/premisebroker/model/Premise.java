package soa.premisebroker.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import soa.common.model.AbstractPremise;

@Entity
@Table(name = "premises")
public class Premise extends AbstractPremise {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3069990790498836885L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Offer offer;


	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="premise", cascade={CascadeType.ALL})
	private List<PremiseReservation> premiseReservations;

	public List<PremiseReservation> getPremiseReservations() {
		return premiseReservations;
	}

	public void setPremiseReservations(List<PremiseReservation> premiseReservations) {
		this.premiseReservations = premiseReservations;
	}
}
