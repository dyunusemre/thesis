package com.thesis.rdfdatasource;

public class TestValue {
	private String type;
	private String subType;
	private int upperLimit;
	private int lowerLimit;
	
	public int getLowerLimit() {
		return lowerLimit;
	}
	public int getUpperLimit() {
		return upperLimit;
	}
	public void setLowerLimit(int lowerLimit) {
		this.lowerLimit = lowerLimit;
	}
	public void setUpperLimit(int upperLimit) {
		this.upperLimit = upperLimit;
	}
	public String getSubType() {
		return subType;
	}
	public String getType() {
		return type;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public void setType(String type) {
		this.type = type;
	}
}
