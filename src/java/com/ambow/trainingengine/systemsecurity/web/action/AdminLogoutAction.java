package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * AdminLogoutAction.java
 * 
 * Created on 2008-8-1 上午11:15:11
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
/**
 * 系统安全控制层用户退出控制类
 */
import static com.ambow.trainingengine.util.SessionDict.*;

@SuppressWarnings("serial")
public class AdminLogoutAction extends AdminBaseAction {

	/**
	 * 用户退出时销毁用户信息
	 */
	public String execute() {
		this.getSession().remove(AdminUser);
		return SUCCESS;
	}
}