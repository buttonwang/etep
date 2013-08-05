package com.ambow.trainingengine.wordbank.util;
/*
 * WordLevel.java
 * 
 * Created on 2008-7-21 上午11:20:50
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 	
	大纲最基础词汇(0),
	大纲基础词汇(1),
	大纲词汇(2),
	真题词汇(3),
	四级词汇(4),
	大学词汇(5),
	六级词汇(6),	
	非大纲词汇(7),
	超纲词汇(9);	
 */
public enum WordLevel {

	
	大纲词汇("2"),
	非大纲词汇("7");
	
	private String value;
	
	WordLevel (String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public static WordLevel getWordLevel(int value) {
		WordLevel wordlevel = null; 
		switch (value) {
		case 2: wordlevel = 大纲词汇; 	break;
		case 7: wordlevel = 非大纲词汇; 	break;
		default: break;
		}
		return wordlevel;
	}
	
	public static WordLevel getWordLevel(String code) {
		if (code.equals("2")) return 大纲词汇;
		else if (code.equals("7")) return 非大纲词汇;
		else return null;
	}
}