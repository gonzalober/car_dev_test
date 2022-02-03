package com.example.restservice;

import org.springframework.data.annotation.Id;

public class Cars {

	private final Long id;
	private final String make;
	private final String model;
	private final String colour;
	private final Long year;

	public Cars(Long id, String make, String model, String colour, Long year) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.colour = colour;
		this.year = year;
	}

	@Id

	public Long getId() {
		return id;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public String getColour() {
		return colour;
	}

	public Long getYear() {
		return year;
	}

	public Cars updateWith(Cars cars) {
		return new Cars(this.id, cars.make, cars.model, cars.colour, cars.year);
	}
}