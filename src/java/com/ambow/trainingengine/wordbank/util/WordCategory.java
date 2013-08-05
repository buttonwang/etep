package com.ambow.trainingengine.wordbank.util;
/*
 * WordCategory.java
 * 
 * Created on 2008-7-21 上午11:24:35
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
public enum WordCategory {	
	r("真题非大纲阅读词汇"),
	u("真题非大纲完形词汇"),
	t("真题非大纲翻译词汇");
	
	private String value;
	
	WordCategory (String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static WordCategory getWordCategory(String value) {		
		if (value.equals("r")) return r;
		else if (value.equals("u")) return u;
		else if (value.equals("t")) return t;
		else return null;
	}
}