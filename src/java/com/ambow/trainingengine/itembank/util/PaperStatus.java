package com.ambow.trainingengine.itembank.util;


/*
 * PaperStatus.java
 * 
 * Created on 2008-8-6 下午06:17:45
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
public enum PaperStatus {
	
	未审核(0),
	已审核(1),
	已组卷(2),
	已使用(3),
	已废弃(-1);
	
	private int value;
	
	PaperStatus (int value) {
		this.value = value;
	}
	
	public int toInt() {
		return value;
	}
	 
	public static PaperStatus getPaperStatus(int value) {
		PaperStatus paperStatus = null;
		
		switch (value) {
			case 0: paperStatus = 未审核; 	break;
			case 1: paperStatus = 已审核; 	break;
			case 2: paperStatus = 已组卷; 	break;
			case 3: paperStatus = 已使用; 	break;
			case -1:paperStatus = 已废弃; 	break;
			default: break;
		}
		 
		return paperStatus;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
