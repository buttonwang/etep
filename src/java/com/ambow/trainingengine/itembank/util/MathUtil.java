package com.ambow.trainingengine.itembank.util;

public class MathUtil {
	
	/**
	 * 添加  m：
	 * @param mathStr
	 * @return String
	 */
	public static String addMathXMLIfMathAnswer(String mathStr )
	{
		if(null != mathStr && !"".equals(mathStr.trim())&&(mathStr.indexOf("<img")>-1||mathStr.indexOf("math")>-1))
		{
			return mathStr.replaceAll("<", "<m:").replaceAll("<m:/", "</m:").replaceAll("\n|\r","");
		} 
		return mathStr;
	}
	/**
	 * mathStr 中包含 "math>" 则过滤掉mathStr 中<mtext>元素中只包含 中文空格和英文空格或中英文空格 的<mtext>标签
	 * @param mathStr
	 * @return String
	 */
	public static String removeSpaceAndChinaSpace(String mathStr) {
		if (null == mathStr || "".equals(mathStr)) {
			return mathStr;
		}
		if (mathStr.indexOf("math>") == -1) {
			return mathStr.replaceAll("<(m:)+","<");
		}
		String str = mathStr
					.replaceAll(" ?display ?= ?'block'", "")
					.replaceAll("<mtext>(　|&nbsp;)*</mtext>", "")
					.replaceAll("<(m:)+", "<")
					.replaceAll("</(m:)+", "</");
		return str;
	}
	
	

}
