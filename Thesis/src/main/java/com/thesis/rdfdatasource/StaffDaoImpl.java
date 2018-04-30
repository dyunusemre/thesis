package com.thesis.rdfdatasource;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import com.thesis.utils.DB;
import com.thesis.utils.ResultDispacther;

import oracle.spatial.rdf.client.jena.Oracle;

public class StaffDaoImpl implements StaffDao {

	@Override
	public List<Staff> getAllStaff() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Staff getStaff(String staffID) {
		// TODO Auto-generated method stub
		
		String queryString = "SELECT ?s "
				+ "WHERE {?s "
				+""+DB.SPARQL_LINK+"#hasStaffID> \"" 
				+staffID
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String staffUri = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?o .}";
		String name = ResultDispacther.queryGetResult(queryString,1);
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?o .}";
		String surname = ResultDispacther.queryGetResult(queryString,1);	
		queryString = "SELECT ?o "+ 
    			"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasType> ?o .}";
		String type = ResultDispacther.queryGetResult(queryString,1);
		Staff s = new Staff(name,surname,staffID,type);
		return s;
	}

	@Override
	public Staff getStaffOfPatient(String staffUri) {
		// TODO Auto-generated method stub
		Staff staff;
		//http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#nurse1

		String queryString = "SELECT ?staffId ?name ?surname ?type"+ 
				"WHERE {<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> ?staffId."
						+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasSurname> ?surname."
						+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasName> ?name."
						+"<"+staffUri+"> <http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasType> ?type.}";
		List<String> items = Arrays.asList(ResultDispacther.queryGetResult(queryString,25).split(","));	
		staff = new Staff(items.get(1),items.get(2),items.get(0),items.get(3));	
		return staff;
	}

	@Override
	public void addStaff(Staff staff) {
		// TODO Auto-generated method stub
		String subject = DB.SPARQL_LINK+"#"+staff.getType()+staff.getId()+">";
		String property = DB.SPARQL_LINK+"#hasStaffID>";
		String object = "\""+staff.getId()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		String queryString;
		Oracle oracle = new Oracle(DB.DB_URL, DB.USER_NAME, DB.PASSWORD);
		
		try {	
			Connection conn = oracle.getConnection();
			Statement stmt = conn.createStatement();
			// add staffID
			queryString = "INSERT INTO  TESTMODEL_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			//defines type
			property = "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>";
		    object = DB.SPARQL_LINK+"#"+staff.getType()+">";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			// name adding
			
			property = DB.SPARQL_LINK+"#hasName>";
		    object = "\""+staff.getName()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			
			//surname adding 
			property = DB.SPARQL_LINK+"#hasSurname>";
		    object = "\""+staff.getSurname()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
		    queryString = "INSERT INTO  TestModel_TPL VALUES ("+
					"SDO_RDF_TRIPLE_S('"+DB.MODEL+"', '"+subject+"', '"+property+"', '"+object+
					"'))";
			stmt.execute(queryString);
			stmt.execute("COMMIT");
			//Type adding
			property = DB.SPARQL_LINK+"#hasType>";
		    object = "\""+staff.getType()+"\"^^<http://www.w3.org/2001/XMLSchema#string>";
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

	@Override
	public void updateStaff(Staff staff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteStaff(Staff staff) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isStaffExist(String staffID) {
		String queryString = "SELECT ?s " 
				+ "WHERE {?s "
				+ "<http://www.semanticweb.org/mine/ontologies/2017/4/thyroid-ontology#hasStaffID> \""
				+staffID
				+"\"^^<http://www.w3.org/2001/XMLSchema#string> .}";
		String staffUri = ResultDispacther.queryGetResult(queryString,1);
		if(staffUri.equals("")) {
			return false;
		}else {
			return true;
		}
	}

	

}
