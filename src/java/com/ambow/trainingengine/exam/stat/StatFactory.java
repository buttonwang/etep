package com.ambow.trainingengine.exam.stat;

import java.util.HashMap;
import java.util.Map;


/**
 * StatFactory.java
 * 保存答案、统计工厂类
 * Created on 2009-2-5 下午06:12:39
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

public class StatFactory {

	private Map<String, IStat> statClass = new HashMap<String, IStat>();
	
	public IStat getStatImpl(String projectVersion) {
		return statClass.get(projectVersion);
	}
	
	public Map<String, IStat> getStatClass() {
		return statClass;
	}

	public void setStatClass(Map<String, IStat> statClass) {
		this.statClass = statClass;
	}
}
