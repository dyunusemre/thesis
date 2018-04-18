package com.thesis.rdfdatasource;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecException;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.thesis.utils.DatabaseConn;
import com.thesis.utils.ResultDispacther;

public class PatientDaoImpl implements PatientDao {
	
	private String resultString;
	private String SQL;
	
	@Override
	public List<Patient> getAllPatient() {
		// TODO Auto-generated method stub
		List<Patient> pList = new ArrayList<>();
		String queryString = "SELECT ?tcno "
				  + "WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#patient>."
				  + "?s <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> ?tcno .} ";

				List<String> items = new ArrayList<>();
				Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,4));
				while(sc.hasNext()){
					items.add(sc.next());
				}
				for (String tcNo : items) {
					pList.add(this.getPatient(tcNo));
				}
		return pList;
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
		Patient p = new Patient(name,gender,Integer.parseInt(age));
		p.setId(tcNo);
		p.setSurname(surname);
		BloodTestDaoImpl b = new BloodTestDaoImpl();
		p.setTest(b.getAllTest(p));
		
		return p;
		
	}

	@Override
	public void updatePatient(Patient patient) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deletePatient(Patient patient) {
		// TODO Auto-generated method stub
		
	}

}
