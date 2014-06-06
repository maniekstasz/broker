package soa.common.model;

import java.io.Serializable;
import java.security.SecureRandom;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Embeddable
public class Credentials implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6193222478729895383L;

	@Column(nullable = false)
	@NotNull
	private String password;

	@Column(nullable = false, unique = true)
	@NotNull
	private String mail;

	@Column(nullable = false, unique = true)
	@NotNull
	private String username;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
