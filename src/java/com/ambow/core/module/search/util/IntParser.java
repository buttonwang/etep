package com.ambow.core.module.search.util;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class IntParser {

	public static String intToString(Integer intval) {
		if (intval == null) {
			intval = 0;
		}
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);

		// 为保证排序正常，所以如果是负数就加上一个n，而正数加上一个a
		if (intval < 0) {
			buf.insert(0, "1");
		} else {
			buf.insert(0, "a");
		}
		return buf.toString();
	}
	
	public static String floatToString(Double floatval){
		if (floatval == null) {
			floatval = new Double(0.0); 
		}
		String formatted = "";

		DecimalFormat format =new DecimalFormat("#.0");
		if (floatval >= new Float(10.0)) {
			formatted = "0" + format.format(floatval);
		} else {
			formatted = format.format(floatval);
		}
		return formatted;
		
	}
	
	public static String dateToString(Timestamp date){

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		if(date ==null){
			return null;
		}
		return sdf.format(date);
		
	}
	
	public static int stringToInt(String intvalStr) {
		return Integer.parseInt(intvalStr.substring(1, intvalStr.length()), 16);
	}
} 
