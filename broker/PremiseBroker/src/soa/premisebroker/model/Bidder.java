package soa.premisebroker.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import soa.common.model.Auditable;
import soa.premisebroker.model.BidderWebHook.WebHooType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "bidders")
@EntityListeners({AuditingEntityListener.class})
public class Bidder extends Auditable {

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
	@Column(name = "account_nr", unique=true)
	private String accountNr;

	@NotNull
	private Boolean verified = false;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder")
	private List<Offer> offers;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bidder", cascade = {
			CascadeType.PERSIST, CascadeType.REMOVE })
	private List<BidderWebHook> webHooks;

//	public boolean hasWebHook(WebHooType webHookType) {
//		if (webHooks != null) {
//			for (BidderWebHook webHook : webHooks) {
//				if (webHookType.equals(webHook.getType()))
//					return true;
//			}
//		}
//		return false;
//	}

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

//	@JsonIgnore
	public List<BidderWebHook> getWebHooks() {
		return webHooks;
	}

	public void setWebHooks(List<BidderWebHook> webHooks) {
		this.webHooks = webHooks;
		for (BidderWebHook hook : this.webHooks)
			hook.setBidder(this);
	}

}
