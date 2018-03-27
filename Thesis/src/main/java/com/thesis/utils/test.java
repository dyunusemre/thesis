package com.thesis.utils;

public class test {
	
	public static void main(String[] args) {
	
		String tcNo = "1111111";
		String queryString = "SELECT ?s " 
							+ "WHERE {?s "
							+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasTCno> \""
							+tcNo
							+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
				
				String object = ResultDispacther.queryGetResult(queryString);
		
				System.out.println("data =>"+object);
				
				queryString = "SELECT ?o "+ 
	        			"WHERE {<"+object+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?o .}";
				System.out.println(ResultDispacther.queryGetResult(queryString));
				
			
	}

}
