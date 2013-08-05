package com.ambow.trainingengine.wordbank.util;

import java.util.Date;

/*
 * ParseObject.java
 * 
 * Created on 2008-7-21 下午05:18:07
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class ParseObject {
	

	public static String fillZero(String str, int length) {
		String zero = "000000000000" + str.trim();
		return zero.substring(zero.length() - length);
	}
	
	public static Date ObjecttoDate(Object obj) {
		if (obj == null)
			return null;

		Date retvalue = null;
		try {
			retvalue = (Date) obj;
		} catch (Exception e) {
		}
		return retvalue;
	}

	public static String ObjecttoString(Object obj) {
		if (obj == null)
			return "";
		
		String retvalue = "";
		if (obj.getClass().getSimpleName().equals("String"))
			retvalue = (String) obj;
		else if (obj.getClass().getSimpleName().equals("Double")) {
			retvalue = String.valueOf(((Double) obj).intValue());
		}
		return retvalue.trim();
	}

	public static String ObjecttoString(Object obj, int length) {
		if (obj == null)
			return "";

		String retvalue = "";
		if (obj.getClass().getSimpleName().equals("String"))
			retvalue = (String) obj;
		else if (obj.getClass().getSimpleName().equals("Double")) {
			retvalue = String.valueOf(((Double) obj).intValue());
		}
		if (length > 0)
			retvalue = fillZero(retvalue, length);
		return retvalue.trim();
	}

	public static Double ObjecttoDouble(Object obj) {
		if (obj == null)
			return 0.00;

		Double retvalue = 0.00;
		if (obj.getClass().getSimpleName().equals("Double"))
			retvalue = (Double) obj;
		else {
			try {
				retvalue = (Double) obj;
			} catch (Exception e) {
			}
		}
		return retvalue;
	}
}
