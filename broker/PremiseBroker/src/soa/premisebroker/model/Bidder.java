package soa.premisebroker.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import soa.common.model.Auditable;
import soa.premisebroker.model.BidderWebHook.WebHooType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bidders", uniqueConstraints = @UniqueConstraint(columnNames = { "created_by" }))
@EntityListeners({ AuditingEntityListener.class })
public class Bidder extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2292820801038212746L;

	@Embedded
	@Valid
	@NotNull
	private Address address;

	@NotNull
	@NotEmpty
	@Column(name = "account_nr", unique = true)
	private String accountNr;

	@NotNull
	private Boolean verified = false;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder")
	private List<Offer> offers;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	private List<BidderWebHook> webHooks;

	@ManyToOne(optional = false)
	@JoinColumn(name = "created_by")
	@CreatedBy
	private User createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false)
	@CreatedDate
	private Date createdDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_modified_date", nullable = false)
	@LastModifiedDate
	private Date lastModifiedDate;
	
	@NotNull
	private String firstname;
	
	@NotNull
	private String lastname;
	
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAccountNr() {
		return accountNr;
	}

	public void setAccountNr(String accountNr) {
		this.accountNr = accountNr;
	}

	public Boolean getVerified() {
		return verified;
	}

	@JsonIgnore
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
		for (Offer offer : this.offers)
			offer.setBidder(this);
	}

	// @JsonIgnore
	public List<BidderWebHook> getWebHooks() {
		return webHooks;
	}

	public void setWebHooks(List<BidderWebHook> webHooks) {
		this.webHooks = webHooks;
		for (BidderWebHook hook : this.webHooks)
			hook.setBidder(this);
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

}
