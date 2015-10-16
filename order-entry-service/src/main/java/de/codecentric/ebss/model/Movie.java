package de.codecentric.ebss.model;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class Movie {

	private UUID id;
	private String title;
	private String description;
	private Integer year;
	private String image;

	public Movie() {
		this.id = UUID.randomUUID();
	}
	
	public Movie(UUID id, String title, String description, Integer year, String image) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.year = year;
		this.image = image;
	}
	
	public Movie(String title, String description, Integer year, String image) {
		this(UUID.randomUUID(), title, description, year, image);
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}
	
	public String getShortDescription() {
		return StringUtils.abbreviate(description, 200);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
