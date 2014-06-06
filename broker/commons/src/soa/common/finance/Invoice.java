package soa.common.finance;

import java.io.Serializable;


public class Invoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8237452871934167911L;
	private String accountNr;
	private Double amount;
	private String title;

	private InvoicePart items[];

	public Invoice(String accountNr, Double amount, String title,
			InvoicePart[] items) {
		super();
		this.accountNr = accountNr;
		this.amount = amount;
		this.title = title;
		this.items = items;
	}
	
	public Invoice(){
		
	}

	public String getAccountNr() {
		return accountNr;
	}

	public Double getAmount() {
		return amount;
	}

	public String getTitle() {
		return title;
	}

	public InvoicePart[] getItems() {
		return items;
	}

	public static class InvoicePart {
		private final String name;
		private final double amount;

		public InvoicePart(String name, double amount) {
			this.name = name;
			this.amount = amount;
		}

		public String getName() {
			return name;
		}

		public double getAmount() {
			return amount;
		}
	}

	public void setAccountNr(String accountNr) {
		this.accountNr = accountNr;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setItems(InvoicePart[] items) {
		this.items = items;
	}

}
