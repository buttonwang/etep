/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.report.service.adviser.WebUserService;

/**
 * @author yuanjunqi
 *
 */
public abstract class AdviserBaseAction extends BaseAction {
	protected WebUserService webUserService;
	
	private String ClassCode;// 班级编码
	
	private String ClassName;
	
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}
	public String getClassCode() {
		return ClassCode;
	}
	public void setClassCode(String classCode) {
		ClassCode = classCode;
	}
	public abstract String execute() ;
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	public WebUserService getWebUserService() {
		return webUserService;
	}
	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
	}
	


}

