package com.ambow.studyflow.common;

/*
 * ProcessPublishStatus.java
 * 
 * Created on Jun 26, 2008 8:28:55 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public enum ProcessReleaseStatus {
	UNRELEASE(0),
	RELEASE(1),
	ABANDON(-1);
	
	private int value;
	
	ProcessReleaseStatus(int value){
		this.value=value;
	}
	
	public int toInt() {
		return value;
	}

	// the valueOfMethod
    public static ProcessReleaseStatus fromInt(int value) {    
         switch(value) {
             case 0:  return UNRELEASE;
             case 1:  return RELEASE;
             case -1: return ABANDON;
             default: return UNRELEASE;
         }
    }

}