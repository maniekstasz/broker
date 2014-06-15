package soa.premisebroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name="payment")
@EntityListeners({AuditingEntityListener.class})
public class Payment extends AbstractPersistable<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2976587867898357733L;

	public enum PaymentType {PAYMENT, CANCELATION};
	
	
	@NotNull
	@Column(name="payment_type")
	private PaymentType paymentType;
	
	@NotNull
	private String accountNr;
	
	@NotNull
	private String title;
	
	@CreatedDate
	@Column(name="created_date", nullable=false)
	private Date createdDate;
	
	
	private Double amount;

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAccountNr() {
		return accountNr;
	}

	public void setAccountNr(String accountNr) {
		this.accountNr = accountNr;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public Long getBillId(){
		int index = title.lastIndexOf("id: ");
		return new Long(title.substring(index + 4));
	}

}
