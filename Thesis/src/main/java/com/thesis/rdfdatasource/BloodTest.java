package com.thesis.rdfdatasource;

public class BloodTest {
	private Patient patient;
	private String testType;
	private Staff isRequested;
	private Staff isResponsible;
	private Staff isDoneBy;
	private TestValue defaultTestValues;
	private int testResult;
	public BloodTest(Patient patient) {
		this.patient = patient;
	}
	public BloodTest() {};
	public void setIsRequested(Staff isRequested) {
		this.isRequested = isRequested;
	}
	public Staff getIsDoneBy() {
		return isDoneBy;
	}
	public void setIsDoneBy(Staff isDoneBy) {
		this.isDoneBy = isDoneBy;
	}
	public int getTestResult() {
		return testResult;
	}
	public void setTestResult(int testResult) {
		this.testResult = testResult;
	}
	public TestValue getDefaultTestValues() {
		return defaultTestValues;
	}
	public void setDefaultTestValues(TestValue defaultTestValues) {
		this.defaultTestValues = defaultTestValues;
	}
	public void setIsResponsible(Staff isResponsible) {
		this.isResponsible = isResponsible;
	}
	public Staff getIsResponsible() {
		return isResponsible;
	}
	public Staff getIsRequested() {
		return isRequested;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
}
