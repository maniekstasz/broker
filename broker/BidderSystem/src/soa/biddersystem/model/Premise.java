package soa.biddersystem.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


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
	
	@Column(name="foreign_id")
	private Long foreignId;

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	@JsonIgnore
	public Offer getOffer() {
		return offer;
	}

	@JsonProperty
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	

}
