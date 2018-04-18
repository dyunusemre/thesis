package com.thesis.rdfdatasource;

import java.util.List;

public interface StaffDao {
	public List<Staff> getAllStaff();
	public Staff getStaff(String staffID);
	public Staff getStaffOfPatient(String staffUri);
	public void updatePatient(Staff staff);
	public void deletePatient(Staff staff);
}
