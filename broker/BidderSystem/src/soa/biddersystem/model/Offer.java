package soa.biddersystem.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import soa.common.model.AbstractOffer;

@Entity
@Table(name = "offers")
public class Offer extends AbstractOffer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8946121525059476870L;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "offer", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE })
	private List<Premise> premises;
	
	@Column(name="foreign_id")
	private Long foreignId;

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}
	
	public List<Premise> getPremises() {
		return premises;
	}

	public void setPremises(List<Premise> premises) {
		this.premises = premises;
		for (Premise premise : this.premises)
			premise.setOffer(this);
	}


}
