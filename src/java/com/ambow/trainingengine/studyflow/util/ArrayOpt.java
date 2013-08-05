package com.ambow.trainingengine.studyflow.util;

public class ArrayOpt {

	public static String arrayToString(Object []objs){
		String str="";
		for (int i = 0; i < objs.length; i++) {
			Object object = objs[i];
			if(i!=objs.length&&i>0){
				str +=","+object.toString();
			}else{
				str += object.toString();
			}
		} 
		return str;
	}
}
