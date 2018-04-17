package com.thesis.rdfdatasource;

import java.util.Arrays;
import java.util.List;

import com.thesis.utils.ResultDispacther;

public class TestValueDaoImpl implements TestValueDao{


	@Override
	public TestValue getTestValue(String testUrl) {
		// TODO Auto-generated method stub
		TestValue testValue = new TestValue();
		/* attr
		 * subtype
		 * upperlimit
		 * lowerlimit
		 * type
		 * */
		String queryString = "SELECT ?U ?L "+ 
				"WHERE {<"+testUrl+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#upperLimit> ?U."
						+ " <"+testUrl+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#lowerLimit> ?L.}";
		List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,3).split("\\s*,\\s*"));	
		testValue.setUpperLimit(Integer.parseInt(items.get(0)));
		testValue.setLowerLimit(Integer.parseInt(items.get(1)));
		testValue.setType(testUrl.substring(67));
		
		return testValue;
	}

	@Override
	public void deleteTestValue(String testUrl) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTestValue(String testUrl) {
		// TODO Auto-generated method stub
		
	}

	
}
