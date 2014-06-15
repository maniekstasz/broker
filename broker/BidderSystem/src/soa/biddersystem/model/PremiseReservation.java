package soa.biddersystem.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import soa.common.model.AbstractReservation;

@Entity
@Table(name = "premise_reservations")
@EntityListeners({AuditingEntityListener.class})
public class PremiseReservation extends AbstractReservation<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2714159114502688009L;

	@NotNull
	@ManyToOne(optional = false)
	private Premise premise;

	@Column(name="foreign_id")
	private Long foreignId;

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	public Premise getPremise() {
		return premise;
	}
	
	public void setPremise(Premise premise) {
		this.premise = premise;
	}

}
