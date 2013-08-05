package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.ambow.trainingengine.systemsecurity.util.JSONReader;

public class TestJsonReader {
	private JSONReader reader = new JSONReader();
	
	
	
	public Object read(String str){
		
		try {
			return reader.read(str);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	public void example() {
		Object result = reader.read("[true]");
		System.out.println("JSONReader result is " + result + " of class "
				+ result.getClass());
	}
	public static void main(String[] args) {
		TestJsonReader t= new TestJsonReader();		
		String dataStr = "[{\"subjectCode\":\"01\",\"gradeCode\":\"cz\"},{\"subjectCode\":\"01\",\"gradeCode\":\"gk\"},{\"subjectCode\":\"01\",\"gradeCode\":\"gz\"},{\"subjectCode\":\"01\",\"gradeCode\":\"ky\"},{\"subjectCode\":\"01\",\"gradeCode\":\"lj\"},{\"subjectCode\":\"01\",\"gradeCode\":\"sj\"},{\"subjectCode\":\"01\",\"gradeCode\":\"zk\"},{\"subjectCode\":\"02\",\"gradeCode\":\"cz\"},{\"subjectCode\":\"02\",\"gradeCode\":\"gk\"},{\"subjectCode\":\"02\",\"gradeCode\":\"gz\"},{\"subjectCode\":\"02\",\"gradeCode\":\"ky\"},{\"subjectCode\":\"02\",\"gradeCode\":\"lj\"},{\"subjectCode\":\"02\",\"gradeCode\":\"sj\"},{\"subjectCode\":\"02\",\"gradeCode\":\"zk\"},{\"subjectCode\":\"03\",\"gradeCode\":\"cz\"},{\"subjectCode\":\"03\",\"gradeCode\":\"gk\"},{\"subjectCode\":\"03\",\"gradeCode\":\"gz\"},{\"subjectCode\":\"03\",\"gradeCode\":\"ky\"},{\"subjectCode\":\"03\",\"gradeCode\":\"lj\"},{\"subjectCode\":\"03\",\"gradeCode\":\"sj\"},{\"subjectCode\":\"03\",\"gradeCode\":\"zk\"},{\"subjectCode\":\"04\",\"gradeCode\":\"cz\"},{\"subjectCode\":\"04\",\"gradeCode\":\"gk\"},{\"subjectCode\":\"04\",\"gradeCode\":\"gz\"},{\"subjectCode\":\"04\",\"gradeCode\":\"ky\"},{\"subjectCode\":\"04\",\"gradeCode\":\"lj\"},{\"subjectCode\":\"04\",\"gradeCode\":\"sj\"},{\"subjectCode\":\"04\",\"gradeCode\":\"zk\"}]";
				
		Object data = new JSONReader().read(dataStr );
		if(data instanceof ArrayList){
			for (Object obj : (ArrayList) data) {
				if(obj instanceof HashMap){
					System.out.println(((HashMap)obj).get("gradeCode"));
				}else if(obj instanceof String){
					System.out.println(obj);
				}
			}
		}
	}
}
