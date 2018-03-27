package com.thesis.rdfdatasource;

public abstract class Person {
	private String name;
	private String surname;
	private long id;
	
	public Person(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
