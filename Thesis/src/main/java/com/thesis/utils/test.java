package com.thesis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.thesis.rdfdatasource.BloodTest;
import com.thesis.rdfdatasource.TestValueDaoImpl;

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
				
				
				queryString = "SELECT  ?testResult"+ 
						"WHERE {<"+object+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTest> ?test."
								+ " ?test <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestResult> ?testResult.}";
				
				queryString = "SELECT  ?result ?name "+ 
						"WHERE {<"+object+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTest> ?o."
								+ " ?o <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestName> ?testName."
								+ " ?o <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTestResult> ?result.}";
				
				/*queryString = "SELECT ?result ?hasta "+ 
    					"WHERE {?hasta <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#patient>."
					    +" ?hasta"+DB.SPARQL_LINK+"#hasTCno> \""+tcNo+"\"^^<http://www.w3.org/2001/XMLSchema#string> ."
    					+" ?hasta"+DB.SPARQL_LINK+"#hasTest> ?test."
    					+" ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result.}";*/
				queryString = "SELECT ?result ?name ?valueType ?doctorName ?nurseName ?tName "+ 
      					"WHERE {<"+object+"> "+DB.SPARQL_LINK+"#hasTest> ?test."
      							+ " ?test "+DB.SPARQL_LINK+"#hasTestName> ?name."
      							+ " ?test "+DB.SPARQL_LINK+"#hasValueType> ?valueType."
      							+ " ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result."
								+ " ?test "+DB.SPARQL_LINK+"#isRequestBy> ?doctor."
								+ " ?doctor "+DB.SPARQL_LINK+"#hasName> ?doctorName."
								+ " ?test "+DB.SPARQL_LINK+"#isResponsibleBy> ?nurse."
								+ " ?nurse "+DB.SPARQL_LINK+"#hasName> ?nurseName."
								+ " ?test "+DB.SPARQL_LINK+"#isDoneBy> ?t."
								+ " ?t "+DB.SPARQL_LINK+"#hasName> ?tName.}";
				
					
				List<BloodTest> tests = new ArrayList<>();
				List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,0).split("\\n"));
				BloodTest b;
				String[] infos;
				for (int i = 0; i < items.size(); i++) {
					
					 b = new BloodTest();
					 infos = items.get(i).split("\\s*,\\s*");
				

					 System.out.println(items.get(i));
				}
				/*for (BloodTest be : tests) {
					System.out.println(be.getTestType());
				}*/
				 //b.setIsRequested(new Staff());
					 //b.setIsResponsible(new Staff());						
					// 
				
				
			
	}

}
