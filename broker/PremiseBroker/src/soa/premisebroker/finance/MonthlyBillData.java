package soa.premisebroker.finance;

import java.math.BigInteger;

import javax.persistence.Transient;

public class MonthlyBillData {
	private Long bidderId;
	private String email;
	private String uri;
	private Long offersCnt;
	private Long resCnt;

	public MonthlyBillData(Object[] object) {
		bidderId = ((BigInteger) object[0]).longValue();
		email = (String) object[1];
		uri = (String) object[2];
		offersCnt = ((BigInteger) object[3]).longValue();
		resCnt = ((BigInteger) object[4]).longValue();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Long getOffersCnt() {
		return offersCnt;
	}

	public void setOffersCnt(Long offersCnt) {
		this.offersCnt = offersCnt;
	}

	public Long getResCnt() {
		return resCnt;
	}

	public void setResCnt(Long resCnt) {
		this.resCnt = resCnt;
	}

	public Long getBidderId() {
		return bidderId;
	}

	public void setBidderId(Long bidderId) {
		this.bidderId = bidderId;
	}
}
