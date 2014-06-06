package soa.biddersystem.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import soa.common.model.AbstractReservation;

@Entity
@Table(name = "premise_reservations")
public class PremiseReservation extends AbstractReservation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2714159114502688009L;

	@NotNull
	@ManyToOne(optional = false)
	private Premise premise;

	@Column(name="foreign_location")
	private String foreignLocation;

	public String getForeignLocation() {
		return foreignLocation;
	}

	public void setForeignLocation(String foreignLocation) {
		this.foreignLocation = foreignLocation;
	}

	public Premise getPremise() {
		return premise;
	}
	
	public void setPremise(Premise premise) {
		this.premise = premise;
	}

}
