package soa.biddersystem.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import soa.common.model.AbstractUser;

@Entity
@Table(name = "users")
public class User extends AbstractUser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2678415287912930990L;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy")
	private List<PremiseReservation> premiseReservations;

	public List<PremiseReservation> getPremiseReservations() {
		return premiseReservations;
	}

	public void setPremiseReservations(
			List<PremiseReservation> premiseReservations) {
		this.premiseReservations = premiseReservations;
	}



}
