package com.thesis.rdfdatasource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
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
		queryString = "SELECT ?result ?name ?valueType ?doctor ?tec ?nurse  "+ 
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
		@SuppressWarnings("resource")
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
			 System.out.println("TEKNISYEN" + infos[5]);
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
		Oracle oracle = new Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
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

	
}
