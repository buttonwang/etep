package com.ambow.trainingengine.itembank.util;

/*
 * ItemStatus.java
 * 
 * Created on 2008-7-17 上午10:09:41
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
public enum ItemStatus {
	未审核(0), 已审核(1), 已组卷(2), 已使用(3), 已废弃(-1);

	private int value;

	ItemStatus(int value) {
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
