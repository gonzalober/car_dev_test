package com.example.restservice;

public class Cars {

	private final long id;
	private final String make;

	public Cars(long id, String make) {
		this.id = id;
		this.make = make;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return make;
	}
}