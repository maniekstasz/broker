package soa.premisebroker.model;

public class DebtCollectorDto {

	private final String firstName;
	private final String lastName;
	private final Address address;
	private Double amount;

	public DebtCollectorDto(Bidder bidder, Double initialAmount) {
		super();
		this.firstName = bidder.getFirstname();
		this.lastName = bidder.getLastname();
		this.address = bidder.getAddress();
		this.amount = initialAmount;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Address getAddress() {
		return address;
	}

	public Double getAmount() {
		return amount;
	}

	public void addToAmount(Double amount) {
		this.amount += amount;
	}

}
