package soa.premisebroker.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name="price_dictionary")
public class PriceDictionary extends AbstractPersistable<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5390813794506300534L;
	public enum Product {
		OFFER, RESERVATION
	}
	
	@NotNull
	private Product product;
	@NotNull
	private Double price;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}
