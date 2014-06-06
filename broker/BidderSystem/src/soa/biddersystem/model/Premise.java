package soa.biddersystem.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
	
	@Column(name="foreign_location")
	private String foreignLocation;

	public String getForeignLocation() {
		return foreignLocation;
	}

	public void setForeignLocation(String foreignLocation) {
		this.foreignLocation = foreignLocation;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	

}
