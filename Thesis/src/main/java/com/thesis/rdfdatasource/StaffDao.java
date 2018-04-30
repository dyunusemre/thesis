package com.thesis.rdfdatasource;

import java.util.List;

public interface StaffDao {
	public List<Staff> getAllStaff();
	public Staff getStaff(String staffID);
	public Staff getStaffOfPatient(String staffUri);
	public void addStaff(Staff staff);
	public void updateStaff(Staff staff);
	public void deleteStaff(Staff staff);
	public boolean isStaffExist(String staffID);
	
}
