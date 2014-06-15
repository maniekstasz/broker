package soa.common.model;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

import soa.common.model.Credentials;


@MappedSuperclass
public abstract class AbstractUser extends AbstractPersistable<Long> {

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
