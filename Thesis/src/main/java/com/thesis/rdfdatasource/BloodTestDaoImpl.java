package com.thesis.rdfdatasource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.thesis.utils.DB;
import com.thesis.utils.ResultDispacther;

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
	public void deleteTest(Patient p) {
		// eklenecek
		
	}

	@Override
	public void updateTest(Patient p) {
		// TODO Auto-generated method stub
		
	}
	
}
