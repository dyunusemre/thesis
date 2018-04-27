package com.thesis.rdfdatasource;

import java.util.Arrays;
import java.util.List;

import com.thesis.utils.DB;
import com.thesis.utils.ResultDispacther;

public class StaffDaoImpl implements StaffDao {

	@Override
	public List<Staff> getAllStaff() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaff(String staffID) {
		// TODO Auto-generated method stub
		
		String queryString = "SELECT ?s "
				+ "WHERE {?s "
				+""+DB.SPARQL_LINK+"#hasStaffID> \"" 
				+staffID
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String staffUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?o .}";
		String name = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?o .}";
		String surname = ResultDispacther.queryGetResult(queryString,1);	
		Staff s = new Staff(name,surname,staffID,staffUri.substring(67));
		return s;
	}

	@Override
	public Staff getStaffOfPatient(String staffUri) {
		// TODO Auto-generated method stub
		Staff staff;
		//http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#nurse1

		String queryString = "SELECT ?staffId ?name ?surname "+ 
				"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> ?staffId."
						+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?surname."
						+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?name.}";
		List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,21).split(","));	
		staff = new Staff(items.get(1),items.get(2),items.get(0),staffUri.substring(67));	
		return staff;
	}

	@Override
	public void updatePatient(Staff staff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePatient(Staff staff) {
		// TODO Auto-generated method stub
		
	}

}
