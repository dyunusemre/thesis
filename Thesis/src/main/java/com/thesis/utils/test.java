package com.thesis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.thesis.rdfdatasource.BloodTest;
import com.thesis.rdfdatasource.StaffDaoImpl;
import com.thesis.rdfdatasource.TestValueDaoImpl;

public class test {
	
	public static void main(String[] args) {
	
		String tcNo = "1111111";
		String staffID = "4444444";
		
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
    					+" ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result.}";
    			*/
				queryString = "SELECT ?result ?name ?valueType ?doctor ?t ?nurse "+ 
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
				String staffUri = "http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#nurse1";
			 queryString = "SELECT ?result ?name ?valueType ?doctor ?tec ?nurse  "+ 
						"WHERE {<"+object+"> "+DB.SPARQL_LINK+"#hasTest> ?test."
								+ " ?test "+DB.SPARQL_LINK+"#hasTestName> ?name."
								+ " ?test "+DB.SPARQL_LINK+"#hasValueType> ?valueType."
								+ " ?test "+DB.SPARQL_LINK+"#hasTestResult> ?result."
								+ " ?test "+DB.SPARQL_LINK+"#isRequestBy> ?doctor."
								+ " ?test "+DB.SPARQL_LINK+"#isResponsibleBy> ?nurse."
								+ " ?test "+DB.SPARQL_LINK+"#isDoneBy> ?tec.}";
			

			/*List<String> items = new ArrayList<>();
			BloodTest b;
			String[] infos;
			Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,39));
			while(sc.hasNext()) {
				items.add(sc.next());
			}
			for (int i = 0; i < items.size(); i++) {
				infos = items.get(i).split(",");
				System.out.println(infos[0]);
				System.out.println(infos[1]);
				System.out.println(infos[2]);
				System.out.println(infos[3]);
				System.out.println(infos[4]);
				System.out.println(infos[5]);
				
			}*/
		 queryString = "SELECT ?staffId ?name ?surname "+ 
						"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> ?staffId."
								+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?surname."
								+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?name.}";
			
		  queryString = "SELECT ?s "
					+ "WHERE {?s "
					+""+DB.SPARQL_LINK+"#hasStaffID> \"" 
					+staffID
					+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		  
		  
		  queryString = "SELECT ?tcno "
				  + "WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#patient>."
				  + "?s <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> ?tcno .} ";

				List<String> items = new ArrayList<>();
				BloodTest b;
				Scanner sc = new Scanner(ResultDispacther.queryGetResult(queryString,4));
				while(sc.hasNext()){
					items.add(sc.next());
				}
				for (int i = 0; i < items.size(); i++) {
					
					 b = new BloodTest();
					 
					 System.out.println(items.get(1));
				}
				
				/*for (BloodTest be : tests) {
					System.out.println(be.getTestType());
				}*/
				 //b.setIsRequested(new Staff());
				 //b.setIsResponsible(new Staff());						
		
	}

}
