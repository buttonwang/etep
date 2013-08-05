package com.ambow.trainingengine.exam.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ExtPracticeAction.java
 * 
 * Created on 2009-6-23 下午04:59:28
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

public class ExtPracticeAction extends BaseAction {

	private static final long serialVersionUID = 3226396961220394561L;

	public String execute() {
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControlProxy viewControl =  new ViewControlProxy(
			userDataVO.getProcessPolicy().getIsShowAnswer(),
			userDataVO.getProcessPolicy().getProjectVersion(),
			userDataVO.getProcessInstanceId());

		viewControl.setShowModel(3);	//3是view
		
		return SUCCESS;
	}
	
	@Override
	public String getAuthStr() {
		return null;
	}

}