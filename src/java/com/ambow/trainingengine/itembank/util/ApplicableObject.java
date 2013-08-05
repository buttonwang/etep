package com.ambow.trainingengine.itembank.util;
/*
 * ApplicableObject.java
 * 
 * Created on 2008-7-17 上午10:07:38
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
public enum ApplicableObject {
	文理科(0),
	文科(1),
	理科(2);
	
	private int value;
	
	ApplicableObject (int value) {
		this.value = value;
	}
	
	 public int toInt() {
	      return value;
	 }
	 public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
}
