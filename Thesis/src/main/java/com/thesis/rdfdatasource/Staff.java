package com.thesis.rdfdatasource;

public class Staff extends Person{
	private String type;
	public Staff(String name,String type) {
		super(name);
		this.type = type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
