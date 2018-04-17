package com.thesis.utils;

import java.nio.charset.Charset;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class ResultDispacther {
	private static Query query;
	private static ResultSet resultSet;
	private static ByteArrayOutputStream baos;
	
	public static String queryGetResult(String queryStr,int trimCount) {
		
		query = QueryFactory.create(queryStr);
		QueryExecution q1Exe = QueryExecutionFactory.create(query,DatabaseConn.getModel());
		resultSet = q1Exe.execSelect();
		baos = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsCSV(baos,resultSet);
		String result = baos.toString(Charset.defaultCharset());
		result = result.substring(trimCount).trim();
		return result;
	}
}
