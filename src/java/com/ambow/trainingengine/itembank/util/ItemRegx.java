package com.ambow.trainingengine.itembank.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
 * ItemRegx.java
 * 处理试题用到的正则函数 
 * Created on 2008-9-23 上午11:38:12
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
public class ItemRegx {
	
	protected static final Log logger = LogFactory.getLog(ItemRegx.class);
	
	/** 替换 ___ 为 <u>&nbsp;&nbsp;&nbsp;</u> **/
	public static String underLine2Html(String ulstr) {
		String s = ulstr.replaceAll("([\\)\\.\\d\\s])(_+)([\\.\\s&<])", "$1<u>$2</u>$3");
		logger.info(s);
		
		Pattern p = Pattern.compile("(<u>)(_*)(_)([&nbsp;]*)(</u>)");		
		Matcher m = p.matcher(s);
		
		while (m.find()) {
			s = m.replaceAll("$1$2&nbsp;$4$5");
			m.reset(s);	
		}
		logger.info(s);
		return s;
	}
	
	/** 替换HTML中具有特殊含义的字符为真实字符 */
	public static String replacsHTMLSpecChar(String repStr) {
		if (repStr == null) return null;
		return repStr.replace("&nbsp;", " ")
			.replace("&quot;", "\"").replace("&amp;",  "&")
			.replace("&gt;", ">").replace("&lt;",   "<");
	}
	
	/** 替换25A子题题干中"   " 字符为 "_____" 多于2个的空格替换为_ */
	public static String space2UnderLine(String repStr) {
		
		return repStr.replaceAll("(\\s)(\\s)(\\s)", "___")
			.replaceAll("(_{3,})(\\s)", "$1_")
			.replaceAll("(_{3,})(\\s)", "$1_");
	}
	
	/** 
	 * 替换试题内容中HTML代码末尾的无效空格  
	 * 为开头和结尾加上<p></p>标签
	 * 删除内容中无用的 <p>&nbsp;</p>
	 * 删除<u>字符后的空字符
	 * 删除alt属性中的内容
	 * */
	public static String rightTrim(String repStr) {
		if (repStr == null ) return null;
		
		repStr = repStr.replaceFirst("</p>\\s+<p[^>]+>$", "</p>");
		repStr = repStr.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "</p>");
		repStr = repStr.replaceFirst("<p[^>]+><span[^>]+>$", "");
		
		if (!repStr.startsWith("<p")) repStr = "<p>" + repStr;
		if (!repStr.endsWith("</p>")) repStr = repStr + "</p>";
		
		repStr = repStr.replace("<p>&nbsp;</p>", "");
		repStr = repStr.replaceAll("<u>\\s*", "<u>");
		repStr = clearImgAlt(repStr);
		
		return repStr;
	}
	
	//清除标签内的HTML标记代码 如 <span> </span> ..  
	public static String clearItemHtmlSign(String repStr){
		if (repStr == null ) return null;
		return repStr.replaceAll("(<(/||[^>])*>)", "").replace("&quot;", "\"").replace("&nbsp;", " ").trim();
	}
	
	//清除标签内的Img标记内的Alt属性
	public static String clearImgAlt(String repStr){
		if (repStr == null ) return null;
		return repStr.replaceAll("alt\\s*=\\s*\"[^\"]+\"", "alt=\"\"");
	}
	
}
