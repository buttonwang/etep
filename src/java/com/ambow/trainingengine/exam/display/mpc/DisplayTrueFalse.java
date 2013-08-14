package com.ambow.trainingengine.exam.display.mpc;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.display.Display;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;

/*
 * DisplaySingleChoose.java
 * 
 * Created on 2009-2-26 下午03:32:39
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 若选项 是 <p> content </p>  此种格式则去掉首尾 <p> </p>
 */

public class DisplayTrueFalse extends Display {

	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		genarateInput(page);
	}
	
	private void genarateInput(Page page) {
		for (Item item: page.getItems()) {
			item.getExamProperty().setContentView(item.getContent());
		}
	}
}
