package soa.common.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;


@Entity
@Table(name = "users")
public class User extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2678415287912930990L;

	@Embedded
	@Valid
	@NotNull
	private Credentials credentials;

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

}
