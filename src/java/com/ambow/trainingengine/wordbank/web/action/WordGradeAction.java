package com.ambow.trainingengine.wordbank.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.service.GradeService;

/*
 * WordGradeAction.java
 * 
 * Created on 2008-8-13 上午10:35:04
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
@SuppressWarnings("serial")
public class WordGradeAction extends BaseAction {

	GradeService gradeService;
	
	@Override
	public String execute() {
		this.setRequestAttribute("gradeLeaf", gradeService.findLeaf());
		return SUCCESS;
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

}
