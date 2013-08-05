package com.ambow.trainingengine.freshword.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.service.ExamFlowService;
import com.ambow.trainingengine.exam.service.ExamFreshWordService;
import com.ambow.trainingengine.freshword.service.FreshWordService;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;

/**
 * FreshWordBaseAction.java
 * 
 * Created on 2008-7-24 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class FreshWordBaseAction extends BaseAction {
	
	private static final long serialVersionUID = 138L;
	
	/**
	 * 注入生词训练业务程序接口隔离
	 */
	private FreshWordService freshWordService;
	
	/**
	 * 生词训练结束回调服务
	 */
	
	private ExamFreshWordService addWordService;
	

	@Override
	public String execute() {
		return SUCCESS;
	}
	
	/**
	 * 总验证用户是否合法方法
	 */
	public String getAuthStr()
	{
		Webuser user = (Webuser)this.getSession().get(SessionDict.WebUser); 
		if (user!=null) {
			return "success";
		}
		return null;
	}

	public FreshWordService getFreshWordService() {
		return freshWordService;
	}

	public void setFreshWordService(FreshWordService freshWordService) {
		this.freshWordService = freshWordService;
	}

	public ExamFreshWordService getAddWordService() {
		return addWordService;
	}

	public void setAddWordService(ExamFreshWordService addWordService) {
		this.addWordService = addWordService;
	}

}
