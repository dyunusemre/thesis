package com.thesis.rdfdatasource;

public class Staff extends Person{
	private String type;
	public Staff(String name,String surname,String id,String type) {
		super(name,surname,id);
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
