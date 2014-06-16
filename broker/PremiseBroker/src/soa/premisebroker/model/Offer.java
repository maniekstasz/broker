package soa.premisebroker.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import soa.common.model.AbstractOffer;

@Entity
@Table(name = "offers")
public class Offer extends AbstractOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8991805503673680892L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Bidder bidder;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "offer", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private List<Premise> premises;

	public void setPremises(List<Premise> premises) {
		this.premises = premises;
		if (this.premises != null)
			for (Premise premise : this.premises)
				premise.setOffer(this);
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	public List<Premise> getPremises() {
		return premises;
	}
	
}
