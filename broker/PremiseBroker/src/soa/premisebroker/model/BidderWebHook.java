package soa.premisebroker.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "bidder_web_hook")
public class BidderWebHook extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5914760115850181455L;

	public enum WebHooType {
		RESERVATION_REQUEST, INVOICE_SEND
	}

	@NotNull
	@NotEmpty
	@URL
	private String uri;
	
	@NotNull

	private WebHooType type;

	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	private Bidder bidder;

	public WebHooType getType() {
		return type;
	}

	public void setType(WebHooType type) {
		this.type = type;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}
}
