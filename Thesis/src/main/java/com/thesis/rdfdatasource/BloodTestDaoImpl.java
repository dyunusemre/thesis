package com.thesis.rdfdatasource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thesis.utils.DB;
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
		queryString = "SELECT ?result ?name ?valueType ?doctorName ?nurseName ?tName "+ 
					"WHERE {<"+patientUri+"> "+DB.SPARQL_LINK+"#hasTest> ?test."
							+ " ?test "+DB.SPARQL_LINK+"#hasTestName> ?name."
							+ " ?test "+DB.SPARQL_LINK+"#hasValueType> ?valueType."
							+ " ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result."
						+ " ?test "+DB.SPARQL_LINK+"#isRequestBy> ?doctor."
						+ " ?doctor "+DB.SPARQL_LINK+"#hasName> ?doctorName."
						+ " ?test "+DB.SPARQL_LINK+"#isResponsibleBy> ?nurse."
						+ " ?nurse "+DB.SPARQL_LINK+"#hasName> ?nurseName."
						+ " ?test "+DB.SPARQL_LINK+"#isDoneBy> ?t."
						+ " ?t "+DB.SPARQL_LINK+"#hasName> ?tName.}";
		

		List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,50).split("\\n"));
		BloodTest b;
		String[] infos;
		for (int i = 0; i < items.size(); i++) {
			
			 b = new BloodTest();
			 infos = items.get(i).split("\\s*,\\s*");
			 b.setTestResult(Integer.parseInt(infos[0]));
			 b.setTestType(infos[1]);
			 b.setDefaultTestValues(new TestValueDaoImpl().getTestValue(infos[2]));
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTest(Patient p) {
		// TODO Auto-generated method stub
		
	}
	
}
