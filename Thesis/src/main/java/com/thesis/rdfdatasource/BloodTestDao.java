package com.thesis.rdfdatasource;

import java.util.List;

public interface BloodTestDao {
	public List<BloodTest> getAllTest(Patient p);
	public List<BloodTest> getDisease(Patient p);
	public BloodTest getTest(Patient p);
	public void addTest(BloodTest t);
	public void deleteTest(Patient p, String testName);
	public void updateTest(Patient p);
}
