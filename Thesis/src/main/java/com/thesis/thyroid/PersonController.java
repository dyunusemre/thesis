package com.thesis.thyroid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thesis.rdfdatasource.BloodTest;
import com.thesis.rdfdatasource.Patient;
import com.thesis.rdfdatasource.PatientDaoImpl;
import com.thesis.rdfdatasource.Staff;
import com.thesis.rdfdatasource.StaffDaoImpl;
import com.thesis.utils.URIconstants;

@RestController
public class PersonController {
	
	PatientDaoImpl pdao = new PatientDaoImpl();
	StaffDaoImpl sdao = new StaffDaoImpl();
	
	@RequestMapping(
			value ="/patient",
			params = {"id"}, 
			method = RequestMethod.GET)
	public @ResponseBody Patient getPatient(@RequestParam("id") String tcNo){	
		Patient p = pdao.getPatient(tcNo);
		return p;	
	}
	
	@RequestMapping(
			value = "/postPatient",
			method = RequestMethod.POST,
			produces = "application/json", 
			consumes = "application/json"			
			)
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient){
		if(pdao.isPatientExist(patient.getId())) {
			return new ResponseEntity<Patient>(patient,HttpStatus.CONFLICT);
		}else {
			pdao.addPatient(patient);
			return new ResponseEntity<Patient>(patient,HttpStatus.CREATED);
		}
		
	}
	
	@RequestMapping(
			value ="/allpatient",
			method = RequestMethod.GET)
	public @ResponseBody List<Patient> getAllPatient(){
		return pdao.getAllPatient();
	}
	
	@RequestMapping(
			value ="/staff",
			params = {"staffID"}, 
			method = RequestMethod.GET)
	public @ResponseBody Staff getStaff(@RequestParam("staffID") String staffId){	
		Staff s = sdao.getStaff(staffId);
		return s;	
	}

	@RequestMapping(value = URIconstants.GET_TESTS, method = RequestMethod.GET )
	public @ResponseBody List<BloodTest> getTestofPatient() {
		return null;
	}

	
}
