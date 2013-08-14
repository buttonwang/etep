package com.ambow.trainingengine.exam.util;

public class LayoutUtil {
	/*单选，多选, 判断题型对应的layout1*/
	private static final String layout1="11,12,13";
	/*一对一填空题型对应的layout2*/
	private static final String layout2="33,34,35,36,37,38";
	/*一对多填空题型对应的layout3*/
	private static final String layout3="43,44,45,46,47,48";
	
	public static int getCountSize(String typeCode, String lay1,String lay2,String lay3, int defaultlay){
		String code = typeCode.substring(typeCode.length()-2);
		
		if(layout1.indexOf(code)>-1)
			return Integer.valueOf(lay1);
		else if(layout2.indexOf(code)>-1)
			return Integer.valueOf(lay2);
		else if(layout3.indexOf(code)>-1)
			return Integer.valueOf(lay3);
		else 
			return Integer.valueOf(defaultlay);
	}
}
