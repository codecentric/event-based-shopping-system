package de.codecentric.ebss.model;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID uuid;
	private Integer amount;
	private String movieId;
	private Recipient recipient;
	
	public Order(Integer amount, String movieId, Recipient recipient) {
		this.uuid = UUID.randomUUID();
		this.amount = amount;
		this.movieId = movieId;
		this.recipient = recipient;
	}

	public UUID getUuid() {
		return uuid;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public Recipient getRecipient() {
		return recipient;
	}

	public void setRecipient(Recipient recipient) {
		this.recipient = recipient;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
