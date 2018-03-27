package com.thesis.utils;

import java.sql.SQLException;

import oracle.spatial.rdf.client.jena.ModelOracleSem;
import oracle.spatial.rdf.client.jena.Oracle;

public class DatabaseConn {
	public static Oracle oracle;
	public static ModelOracleSem model;
	public static Oracle getOracle() {
		oracle = new Oracle(DB.DB_URL,DB.USER_NAME,DB.PASSWORD);
		return oracle;
	}
	public static ModelOracleSem getModel() {
		try {
			model = ModelOracleSem.createOracleSemModel(getOracle(), DB.MODEL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return model;
	}
}
