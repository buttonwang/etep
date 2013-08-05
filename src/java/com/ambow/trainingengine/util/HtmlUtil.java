package com.ambow.trainingengine.util;

/*
 * HtmlUtil.java
 * 
 * Created on 2010-2-11 下午05:42:04
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

public class HtmlUtil {

	/**
	 * remove html string's head <p> and space chars
	 * @param htmlstr
	 * @return
	 */
	public static String beautyHtml(String htmlstr) {
		if (htmlstr == null) return "";
		
		htmlstr = htmlstr.replace("　", " ");
		return htmlstr.trim()
			.replaceFirst("^<p[^>]*>", "").replaceFirst("</p[^>]*>$", "")
			.replaceAll("<span[^>]*>\\s</span>", "")
			.trim();
	}
	
}
