package de.codecentric.ebss.model;

public class Recipient {

	private String firstName;
	private String lastName;
	private Address invoiceAdress;
	private Address deliveryAdress;
	
	public Recipient(String firstName, String lastName, Address invoiceAdress, Address deliveryAdress) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.invoiceAdress = invoiceAdress;
		this.deliveryAdress = deliveryAdress;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Address getInvoiceAdress() {
		return invoiceAdress;
	}
	public void setInvoiceAdress(Address invoiceAdress) {
		this.invoiceAdress = invoiceAdress;
	}
	public Address getDeliveryAdress() {
		return deliveryAdress;
	}
	public void setDeliveryAdress(Address deliveryAdress) {
		this.deliveryAdress = deliveryAdress;
	}
	
}
