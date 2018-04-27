package com.thesis.rdfdatasource;

import java.util.List;

public interface PatientDao {
	public List<Patient> getAllPatient();
	public Patient getPatient(String tcNo);
	public void updatePatient(Patient patient);
	public void deletePatient(Patient patient);
	public void addPatient(Patient patient);
}
