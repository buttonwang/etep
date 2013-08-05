package com.ambow.trainingengine.exam.display;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;

/*
 * IDisplay.java
 * 
 * Created on 2008-11-13 下午02:34:48
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 定义Page对象的显示方法。
 * Changes 
 * -------
 * $log$
 */
public interface IDisplay {

	void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request);
	
}