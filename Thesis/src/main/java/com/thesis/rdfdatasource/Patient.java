package com.thesis.rdfdatasource;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BloodTest> test;
	private String gender;
	private int age;
	public static int count = 5;
	public Patient() {
		
	}
	public Patient(String name,String surname,String id,ArrayList<BloodTest> list,String gender, int age) {
		super(name,surname,id);
		this.gender = gender;
		this.age = age;
		this.test = list;	
	}
	public int getAge() {
		return age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<BloodTest> getTest() {
		return test;
	}
	public void setTest(List<BloodTest> test) {
		this.test = test;
	}
}
