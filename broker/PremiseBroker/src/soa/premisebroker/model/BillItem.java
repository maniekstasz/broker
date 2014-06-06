package soa.premisebroker.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "bill_items")
public class BillItem extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2624617784738913455L;

	@NotNull
	@NotEmpty
	private String itemName;

	@NotNull
	private Double itemPrice;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Bill bill;

	public BillItem(String itemName, Double itemPrice) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}

	public BillItem() {
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

}
