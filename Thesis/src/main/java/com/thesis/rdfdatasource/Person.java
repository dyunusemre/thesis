package com.thesis.rdfdatasource;

import java.io.Serializable;

public abstract class Person implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String surname;
	private String id;
	public Person() {
		
	}
	public Person(String name,String surname,String id) {
		this.name = name;
		this.surname = surname;
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
}
