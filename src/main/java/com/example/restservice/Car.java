package com.example.restservice;

import javax.persistence.*;

@Entity
@Table
public class Car {

	@Id
	@SequenceGenerator(name = "car_sequence", sequenceName = "car_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
	private final Long id;
	private final String make;
	private final String model;
	private final String colour;
	private final Integer year;

	public Car(Long id, String make, String model, String colour, Integer year) {
		this.id = id;
		this.make = make;
		this.model = model;
		this.colour = colour;
		this.year = year;
	}

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

	public Integer getYear() {
		return year;
	}

	@Override
	public String toString() {
		return "Car{" +
				"id=" + id +
				", make='" + make + '\'' +
				", model='" + model + '\'' +
				", colour=" + colour +
				", year=" + year +
				'}';
	}
}