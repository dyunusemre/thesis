package com.thesis.rdfdatasource;

public interface TestValueDao {
	public TestValue getTestValue(String testUrl);
	public void deleteTestValue(String testUrl);
	public void updateTestValue(String testUrl); 

}
