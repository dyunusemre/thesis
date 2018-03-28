package com.thesis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thesis.rdfdatasource.BloodTest;

public class test {
	
	public static void main(String[] args) {
	
		String tcNo = "1111111";
		String queryString = "SELECT ?s  " 
							+ "WHERE {?s "
							+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
							+tcNo
							+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
				
				String object = ResultDispacther.queryGetResult(queryString,1);
		
				System.out.println("data =>"+object);
				
				
				queryString = "SELECT ?x ?o"+ 
	        					"WHERE {<"+object+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTest> ?o."
	        							+ " ?o <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestName> ?x.}";
				
				
				List<BloodTest> tests = new ArrayList<>();
				List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,10).split("\\s*,\\s*"));
				BloodTest b;
				for (int i = 0; i < items.size(); i++) {
					 b = new BloodTest();
					 b.setTestType(items.get(i));
					 tests.add(b);
				}
				for (BloodTest be : tests) {
					System.out.println(be.getTestType());
				}
				
				
				
			
	}

}
