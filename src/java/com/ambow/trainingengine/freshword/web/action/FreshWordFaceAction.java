package com.ambow.trainingengine.freshword.web.action;

/**
 * FreshWordFaceAction.java
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
public class FreshWordFaceAction extends FreshWordBaseAction {
	
	private static final long serialVersionUID = 1107L;
	
	/**
	 * 开放接口跳转到生词训练功能
	 */
	@Override
	public String execute() {
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		this.setSessionObj("newwordok","");//记录学生已经学会的生词
		this.setSessionObj("newwordno",0);//记录学生没学会的生词
		return SUCCESS;//正确进入生词训练功能
	}
}
