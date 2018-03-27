package com.thesis.rdfdatasource;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
	private List<BloodTest> test;
	private String gender;
	private int age;
	public Patient(String name,String gender, int age) {
		super(name);
		this.gender = gender;
		this.age = age;
		this.test = new ArrayList<>();
		
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
