package com.thesis.rdfdatasource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.thesis.utils.DB;
import com.thesis.utils.ResultDispacther;

import oracle.spatial.rdf.client.jena.Oracle;

public class BloodTestDaoImpl implements BloodTestDao {

	@Override
	public List<BloodTest> getAllTest(Patient p) {
		// TODO Auto-generated method stub
		List<BloodTest> testList = new ArrayList<>();
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ ""+DB.SPARQL_LINK+"#hasTCno> \""
				+p.getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?result ?name ?valueType ?doctor ?tec ?nurse "+ 
				"WHERE {<"+patientUri+"> "+DB.SPARQL_LINK+"#hasTest> ?test."
						+ " ?test "+DB.SPARQL_LINK+"#hasTestName> ?name."
						+ " ?test "+DB.SPARQL_LINK+"#hasValueType> ?valueType."
						+ " ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result."
						+ " ?test "+DB.SPARQL_LINK+"#isRequestBy> ?doctor."
						+ " ?test "+DB.SPARQL_LINK+"#isResponsibleBy> ?nurse."
						+ " ?test "+DB.SPARQL_LINK+"#isDoneBy> ?tec.}";

		List<String> items = new ArrayList<>();
		BloodTest b;
		String[] infos;
		Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,39));
		
		while(sc.hasNext()) {
			items.add(sc.next());
		}
		for (int i = 0; i < items.size(); i++) {	
			 b = new BloodTest();
			 infos = items.get(i).split(",");
			 b.setTestResult(Integer.parseInt(infos[0]));
			 b.setTestType(infos[1]);
			 b.setDefaultTestValues(new TestValueDaoImpl().getTestValue(infos[2]));
			 b.setIsRequested(new StaffDaoImpl().getStaffOfPatient(infos[3]));
			 b.setIsResponsible(new StaffDaoImpl().getStaffOfPatient(infos[4]));
			 b.setIsDoneBy(new StaffDaoImpl().getStaffOfPatient(infos[5]));
			 testList.add(b);
			 System.out.println(items.get(i));
		}
		return testList;
	}

	@Override
	public BloodTest getTest(Patient p) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public void updateTest(Patient p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTest(Patient p, String testName) {
		// TODO Auto-generated method stub
		oracle.spatial.rdf.client.jena.Oracle oracle = new oracle.spatial.rdf.client.jena.Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
		try {	
			Connection conn = oracle.getConnection();
			Statement stmt = conn.createStatement();
			String queryString = "SELECT ?s  " 
					+ "WHERE {?s "
					+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
					+p.getId()
					+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		    String subject = ResultDispacther.queryGetResult(queryString,1);
            queryString = "SELECT ?t " 
					 + "WHERE {<"+subject+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTest> ?t ."
				     + "?t <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestName> \""
					        + testName
					        +"\"^^<http://www.w3.org/2001/XMLSchema#string> . }";
			String testSubject = ResultDispacther.queryGetResult(queryString,1);
		    queryString = "DELETE FROM TestModel_TPL t "
				+"WHERE t.triple.GET_SUBJECT() = '<"+testSubject+">'";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			stmt.close();
			conn.close();
			oracle.dispose();
		}catch (Exception e) {
			e.printStackTrace();	// TODO: handle exception
		}
		
	}

	@Override
	public void addTest(BloodTest t) {
		// TODO Auto-generated method stub
		
		String queryString = "SELECT ?s  " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
				+t.getPatient().getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientURI = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?s  " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> \""
				+t.getIsRequested().getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String doctorURI = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?s  " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> \""
				+t.getIsDoneBy().getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String techURI = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?s  " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> \""
				+t.getIsResponsible().getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String nurseURI = ResultDispacther.queryGetResult(queryString,1);
		
		
		
		String subject = DB.SPARQL_LINK+"#"+t.getTestType()+t.getPatient().getId()+">";
		String property = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
		String object = DB.SPARQL_LINK+"#"+t.getTestType()+">";
		
		Oracle oracle = new Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
		
		try {	
			Connection conn = oracle.getConnection();
			Statement stmt = conn.createStatement();
			//defines type
			queryString = "INSERT INTO  TESTMODEL_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//testname adding 
			property = DB.SPARQL_LINK+"#hasTestName>";
		    object = "\""+t.getTestType()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
	
			// doctor adding
			
			property = DB.SPARQL_LINK+"#isRequestBy>";
		    object = "<"+doctorURI+">";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			// nurse adding
			
			property = DB.SPARQL_LINK+"#isResponsibleBy>";
		    object = "<"+nurseURI+">";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//tech. adding 
			property = DB.SPARQL_LINK+"#isDoneBy>";
		    object = "<"+techURI+">";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//value type adding 
			property = DB.SPARQL_LINK+"#hasValueType>";
		    object = DB.SPARQL_LINK+"#"+t.getDefaultTestValues()+">";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//test result adding 
			property = DB.SPARQL_LINK+"#hasTestResult>";
		    object = "\""+t.getTestResult()+"\"^^<http://www.w3.org/2001/XMLSchema#integer>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//test adding to patient
			subject = "<"+patientURI+">";
			property = DB.SPARQL_LINK+"#hasTest>";
		    object = DB.SPARQL_LINK+"#"+t.getTestType()+t.getPatient().getId()+">";
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

	@Override //Bu fonksiyon getAllTest fonksiyonu içinde güncellenecek (inner join vs.)
	public List<BloodTest> getDisease(Patient p) {
		List<BloodTest> testList = new ArrayList<>();
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ ""+DB.SPARQL_LINK+"#hasTCno> \""
				+p.getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?result ?name ?valueType ?doctor ?tec ?nurse ?h "+ 
				"WHERE {<"+patientUri+"> "+DB.SPARQL_LINK+"#hasTest> ?test."
						+ " ?test "+DB.SPARQL_LINK+"#hasTestName> ?name."
						+ " ?test "+DB.SPARQL_LINK+"#hasValueType> ?valueType."
						+ " ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result."
						+ " ?test "+DB.SPARQL_LINK+"#isRequestBy> ?doctor."
						+ " ?test "+DB.SPARQL_LINK+"#isResponsibleBy> ?nurse."
						+ " ?test "+DB.SPARQL_LINK+"#isDoneBy> ?tec."
						+ " ?test "+DB.SPARQL_LINK+"#defines> ?h.}";

		List<String> items = new ArrayList<>();
		BloodTest b;
		String[] infos;
		Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,40));
		
		while(sc.hasNext()) {
			items.add(sc.next());
		}
		for (int i = 0; i < items.size(); i++) {	
			 b = new BloodTest();
			 infos = items.get(i).split(",");
			 b.setTestResult(Integer.parseInt(infos[0]));
			 b.setTestType(infos[1]);
			 b.setDefaultTestValues(new TestValueDaoImpl().getTestValue(infos[2]));
			 b.setIsRequested(new StaffDaoImpl().getStaffOfPatient(infos[3]));
			 b.setIsResponsible(new StaffDaoImpl().getStaffOfPatient(infos[4]));
			 b.setIsDoneBy(new StaffDaoImpl().getStaffOfPatient(infos[5]));
			 b.setDisease(infos[6].substring(67));
			 testList.add(b);
			 System.out.println(items.get(i));
		}
		return testList;
	}

	
}
