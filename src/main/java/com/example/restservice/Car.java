package com.example.restservice;

import javax.persistence.*;

@Entity
@Table
public class Car {

	@Id
	@SequenceGenerator(name = "car_sequence", sequenceName = "car_sequence", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_sequence")
	private Long id;
	private String make;
	private String model;
	private String colour;
	private Integer year;

	public Car() {
	}

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

	public void setCar(Car car) {
		this.make = car.getMake();
		this.model = car.getModel();
		this.colour = car.getColour();
		this.year = car.getYear();
	}

}
