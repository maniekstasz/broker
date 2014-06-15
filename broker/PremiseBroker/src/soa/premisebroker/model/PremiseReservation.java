package soa.premisebroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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

	public Premise getPremise() {
		return premise;
	}
	public void setPremise(Premise premise) {
		this.premise = premise;
	}

}
