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
		private  String name;
		private  Double amount;

		public InvoicePart(String name, Double amount) {
			this.name = name;
			this.amount = amount;
		}

		
		public InvoicePart(){
			
		}
		public String getName() {
			return name;
		}

		public Double getAmount() {
			return amount;
		}


		public void setName(String name) {
			this.name = name;
		}


		public void setAmount(Double amount) {
			this.amount = amount;
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
