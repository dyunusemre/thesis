package com.thesis.rdfdatasource;

import java.util.List;

public interface BloodTestDao {
	public List<BloodTest> getAllTest(Patient p);
	public BloodTest getTest(Patient p);
	public void deleteTest(Patient p);
	public void updateTest(Patient p);
}
