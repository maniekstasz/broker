package soa.premisebroker.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import soa.common.model.AbstractPremise;
import soa.common.model.PicPath;

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

}
