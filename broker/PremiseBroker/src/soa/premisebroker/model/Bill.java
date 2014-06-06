package soa.premisebroker.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "bills")
@EntityListeners({AuditingEntityListener.class})
public class Bill extends AbstractPersistable<Long>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1638132210636483288L;

	public enum BillStatus {
		PAID, AWAITANING, CANCELED
	};

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "bill")
	private List<BillItem> billItems;

	@NotNull
	private BillStatus status;

	@ManyToOne(optional = false)
	private Bidder bidder;

	@CreatedDate
	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@LastModifiedDate
	@Column(name="last_modified_date", nullable=false)
	private Date lastModifiedDate;

	public Bill(Bidder bidder, List<BillItem> billItems) {
		this.bidder = bidder;
		this.billItems = billItems;
		for (BillItem item : this.billItems)
			item.setBill(this);
		this.status = BillStatus.AWAITANING;
	}

	public Bill() {
	}

	public List<BillItem> getBillItems() {
		return billItems;
	}

	public void setBillItems(List<BillItem> billItems) {
		this.billItems = billItems;
		for (BillItem item : this.billItems)
			item.setBill(this);
	}

	public BillStatus getStatus() {
		return status;
	}

	public void setStatus(BillStatus status) {
		this.status = status;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	public Double getAmount() {
		if (billItems == null)
			return null;
		double amount = 0;
		for (BillItem billItem : billItems) {
			amount += billItem.getItemPrice();
		}
		return amount;
	}

}
