package com.thesis.utils;

import java.io.FileReader;
import java.util.Properties;

public class DB {
	public static String USER_NAME;
	public static String PASSWORD;
	public final static String DB_URL = "jdbc:oracle:thin:@localhost:1521:thss";
	public final static String MODEL = "TestModel";
	public final static String TABLE_NAME = "TestModel_TPL";
	private static Properties login = new Properties();
	//set database username and password
	static {
		try {
			FileReader in = new FileReader("./res/login.properties"); 
			login.load(in);
			USER_NAME = login.getProperty("username");
			PASSWORD = login.getProperty("password");
		}catch(Exception e) {
			
		}
	}
}
