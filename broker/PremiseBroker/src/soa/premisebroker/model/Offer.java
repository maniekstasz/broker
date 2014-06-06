package soa.premisebroker.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import soa.common.model.AbstractOffer;
import soa.common.model.PicPath;

@Entity
@Table(name = "offers")
public class Offer extends AbstractOffer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8991805503673680892L;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Bidder bidder;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "offer", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	@Valid
	private List<Premise> premises;
	
	public void setPremises(List<Premise> premises) {
		this.premises = premises;
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
