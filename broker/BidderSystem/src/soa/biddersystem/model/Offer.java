package soa.biddersystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;

import soa.common.model.AbstractOffer;

@Entity
@Table(name = "offers")
public class Offer extends AbstractOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8946121525059476870L;
	
	@Column(name="foreign_location")
	private String foreignLocation;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "offer", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	@Valid
	private List<Premise> premises;
	
	public List<Premise> getPremises() {
		return premises;
	}

	public void setPremises(List<Premise> premises) {
		this.premises = premises;
		for (Premise premise : this.premises)
			premise.setOffer(this);
	}

	public String getForeignLocation() {
		return foreignLocation;
	}

	public void setForeignLocation(String foreignLocation) {
		this.foreignLocation = foreignLocation;
	}

}
