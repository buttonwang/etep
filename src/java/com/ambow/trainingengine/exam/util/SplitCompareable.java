package com.ambow.trainingengine.exam.util;
/*
 * SplitCompareable.java
 * 
 * Created on Jul 3, 2008 5:26:53 PM
 * 该接口为了抽象化分页算法
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

public interface SplitCompareable {
	
	
	public boolean splitCompareTo(Object object);
	
	public int getCountSize();

}
