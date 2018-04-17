package com.thesis.thyroid;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.thesis.rdfdatasource.BloodTest;
import com.thesis.rdfdatasource.Patient;
import com.thesis.rdfdatasource.PatientDaoImpl;
import com.thesis.utils.URIconstants;

@RestController
public class PersonController {
	
	PatientDaoImpl dao = new PatientDaoImpl();
	
	@RequestMapping(
			value ="/patient",
			params = {"id"}, 
			method = RequestMethod.GET)
	@ResponseBody
	public  Patient getPatient(@RequestParam("id") String tcNo){	
		Patient p = dao.getPatient(tcNo);
		return p;
	}
	@RequestMapping(value = URIconstants.GET_TESTS, method = RequestMethod.GET )
	public @ResponseBody List<BloodTest> getTestofPatient() {
		return null;
	}
}
