package com.ambow.trainingengine.itembank.util;
/*
 * ItemSource.java
 * 
 * Created on 2008-7-17 上午09:59:05
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
public enum ItemSource {
	真题(1),
	模拟(2),
	自编(3),
	专项(4),
	其它(9);
	
	private int value;
	
	ItemSource (int value) {
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
