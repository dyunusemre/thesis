package com.thesis.rdfdatasource;


import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.thesis.utils.DB;
import com.thesis.utils.ResultDispacther;

import oracle.spatial.rdf.client.jena.Oracle;

public class PatientDaoImpl implements PatientDao {
	
	@Override
	public List<Patient> getAllPatient() {
		// TODO Auto-generated method stub
		List<Patient> pList = new ArrayList<>();
		String queryString = "SELECT ?tcno "
				  + "WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#patient>."
				  + "?s <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> ?tcno .} ";

				List<String> items = new ArrayList<>();
				@SuppressWarnings("resource")
				Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,4));
				while(sc.hasNext()){
					items.add(sc.next());
				}
				for (String tcNo : items) {
					pList.add(this.getPatient(tcNo));
				}
		return pList;
	}
	
	public boolean isPatientExist(String tcNo) {
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
				+tcNo
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientUri = ResultDispacther.queryGetResult(queryString,1);
		if(patientUri.equals("")) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public Patient getPatient(String tcNo) {
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
				+tcNo
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+patientUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?o .}";
		String name = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+patientUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?o .}";
		String surname = ResultDispacther.queryGetResult(queryString,1);
		
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+patientUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasAge> ?o .}";
		String age = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+patientUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasGender> ?o .}";
		String gender = ResultDispacther.queryGetResult(queryString,1);	
		
		Patient p = new Patient(name,surname,tcNo,new ArrayList<BloodTest>(),gender,Integer.parseInt(age));
		BloodTestDaoImpl b = new BloodTestDaoImpl();
		p.setTest(b.getAllTest(p));
		
		return p;
		
	}

	@Override
	public void updatePatient(Patient patient) {
		// commit deneme12..
		
	}

	@Override
	public void deletePatient(Patient patient) {
		Oracle oracle = new Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
		try {			
			Connection conn = oracle.getConnection();
			Statement stmt = conn.createStatement();			
			String queryString = "SELECT ?s  " 
								+ "WHERE {?s "
								+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
								+patient.getId()
								+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";		
			String object = ResultDispacther.queryGetResult(queryString,1);
			queryString = "DELETE FROM TestModel_TPL t "
						+"WHERE t.triple.GET_SUBJECT() = '<"+object+">'";			
			stmt.execute(queryString);
			stmt.execute("COMMIT");		
			stmt.close();
			conn.close();
			oracle.dispose();				
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		
	}
	
	@Override
	public void addPatient(Patient patient) {
		// TODO Auto-generated method stub
		
		String subject = DB.SPARQL_LINK+"#patient"+patient.getId()+">";
		String property = DB.SPARQL_LINK+"#hasTCno>";
		String object = "\""+patient.getId()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		String queryString;
		Oracle oracle = new Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
		
		try {	
			Connection conn = oracle.getConnection();
			Statement stmt = conn.createStatement();
			
			queryString = "INSERT INTO  TESTMODEL_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
	
			//defines type
			
			property = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
		    object = DB.SPARQL_LINK+"#patient>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			// name adding
			
			property = DB.SPARQL_LINK+"#hasName>";
		    object = "\""+patient.getName()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//surname adding 
			property = DB.SPARQL_LINK+"#hasSurname>";
		    object = "\""+patient.getSurname()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//gender adding 
			property = DB.SPARQL_LINK+"#hasGender>";
		    object = "\""+patient.getGender()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//age adding 
			property = DB.SPARQL_LINK+"#hasAge>";
		    object = "\""+Integer.toString(patient.getAge())+"\"^^<http://www.w3.org/2001/XMLSchema#integer>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			stmt.close();
			conn.close();
			oracle.dispose();
		    
		}catch (Exception e) {
		e.printStackTrace();// TODO: handle exception
		}
	}
}
