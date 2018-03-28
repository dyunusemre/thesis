package com.thesis.rdfdatasource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thesis.utils.ResultDispacther;

public class BloodTestDaoImpl implements BloodTestDao {

	@Override
	public List<BloodTest> getAllTest(Patient p) {
		// TODO Auto-generated method stub
		List<BloodTest> testList = new ArrayList<>();
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
				+p.getId()
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String patientUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?x ?o"+ 
				"WHERE {<"+patientUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTest> ?o."
						+ " ?o <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestName> ?x.}";
		String test = ResultDispacther.queryGetResult(queryString,10);
		System.out.println("data" + test);
		List<String> items = Arrays.asList(test.split("\\s*,\\s*"));
		BloodTest bTemp;
		for (int i = 0; i < items.size(); i++) {
			bTemp = new BloodTest();
			bTemp.setTestType(items.get(i));
			testList.add(bTemp);
			System.out.println("test name" + items.get(i));
			
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTest(Patient p) {
		// TODO Auto-generated method stub
		
	}
	
}
