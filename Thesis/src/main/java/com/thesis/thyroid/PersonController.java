package com.thesis.thyroid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thesis.rdfdatasource.BloodTest;
import com.thesis.rdfdatasource.BloodTestDaoImpl;
import com.thesis.rdfdatasource.Patient;
import com.thesis.rdfdatasource.PatientDaoImpl;
import com.thesis.rdfdatasource.Staff;
import com.thesis.rdfdatasource.StaffDaoImpl;
import com.thesis.utils.URIconstants;

@RestController
public class PersonController {
	
	
	@RequestMapping(
			value ="/patient",
			params = {"id"}, 
			method = RequestMethod.GET)
	public @ResponseBody Patient getPatient(@RequestParam("id") String tcNo){	
		PatientDaoImpl pdao = new PatientDaoImpl();
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
		PatientDaoImpl pdao = new PatientDaoImpl();
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
		PatientDaoImpl pdao = new PatientDaoImpl();
		return pdao.getAllPatient();
	}
	
	@RequestMapping(
			value ="/getDisease",
			params = {"id"}, 
			method = RequestMethod.GET)
	public @ResponseBody Patient getDisease(@RequestParam("id") String tcNo){	
		PatientDaoImpl pdao = new PatientDaoImpl();
		Patient p = pdao.getPatientOfDisease(tcNo);
		return p;	
	}
	
	@RequestMapping(
			value ="/staff",
			params = {"staffID"}, 
			method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Staff> getStaff(@RequestParam("staffID") String staffId){	
		StaffDaoImpl sdao = new StaffDaoImpl();
		Staff s = sdao.getStaff(staffId);
		if(sdao.isStaffExist(staffId)) {
			return new ResponseEntity<Staff>(s,HttpStatus.OK);
			
		}else {
			return new ResponseEntity<Staff>(HttpStatus.NO_CONTENT);
		}
	}

	@RequestMapping(value = URIconstants.GET_TESTS, method = RequestMethod.GET )
	public @ResponseBody List<BloodTest> getTestofPatient() {
		return null;
	}

	@RequestMapping(
			value ="/patient",
			params = {"id"}, 
			method = RequestMethod.DELETE)
	public ResponseEntity<Patient>  deletePatient(@RequestParam("id") String tcNo){	
		PatientDaoImpl pdao = new PatientDaoImpl();
		if(pdao.isPatientExist(tcNo)){
		Patient p = pdao.getPatient(tcNo);	
		pdao.deletePatient(p);
		return new ResponseEntity<Patient>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Patient>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/patient/deleteTest",
		    params = {"id", "name"},
		    method = RequestMethod.DELETE)
	public ResponseEntity<BloodTest> deleteTest(@RequestParam("id") String tcNO, @RequestParam("name") String name) {
		BloodTestDaoImpl bdao = new BloodTestDaoImpl();
		PatientDaoImpl pdao = new PatientDaoImpl();	
		bdao.deleteTest(pdao.getPatient(tcNO), name);
	return new ResponseEntity<BloodTest>(HttpStatus.NO_CONTENT); 
	}
	
	@RequestMapping(
			value = "/postStaff",
			method = RequestMethod.POST			
			)
	public ResponseEntity<Void> createStaff(@RequestBody Staff staff){
		StaffDaoImpl sdao = new StaffDaoImpl();
		if(sdao.isStaffExist(staff.getId())) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}else {
			sdao.addStaff(staff);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		
	}
	
	@RequestMapping(
			value ="/postTest", 
			method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> createTest(BloodTest test){	
		BloodTestDaoImpl bdao = new BloodTestDaoImpl();
		bdao.addTest(test);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
