package com.ambow.trainingengine.web.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import com.ambow.evaluating.web.callback.RedPlatformPlugin;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.util.ParseEncoding;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.LoginService;

@SuppressWarnings("serial")
public class LoginFlowAction extends WebBaseAction {

	private LoginService loginService;
	private String userID;	
	private String loginName;	
	private String userName;	
	private String classCode;	
	private String moduleID;	
	private String refID;	
	private String gradeID;	
	
	private String projectVersion;
	/**
	 * 1.检验用户流程信息，添加及更新
	 * 2.检验流程节点，添加流程节点
	 */
	public String execute() {
		String outStr=null;
		
		ParseEncoding parse = new ParseEncoding();
		try {
			String code = parse.getEncoding(userName.getBytes("ISO-8859-1"));
			System.out.println("userNameCode is " + code);
			//System.out.println(userName);
			if (code.startsWith("GB")) userName = new String(userName.getBytes("ISO-8859-1"), "GBK");
			if (code.startsWith("UTF")) userName = new String(userName.getBytes("ISO-8859-1"), "UTF-8");
			//userName = java.net.URLDecoder.decode(userName.replace("%", ""), "UTF-8");
			//if (code.startsWith("ASCII")) userName = new String((new String(userName.getBytes("ISO-8859-1"), "UTF-8")).getBytes("ISO-8859-1"), "UTF-8") ;
			//System.out.println(userName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		/*检验用户流程信息，添加及更新*/
		outStr=this.getLoginService().loginAndJumpTo(this.userID,
				this.loginName, 
				this.userName,
				this.classCode,
				this.moduleID,
				this.refID,
				this.gradeID);
		
		if(null!=outStr&&outStr.indexOf("success")<0)
			return outStr;
		
		
		UserDataVO userDataVO=new UserDataVO(this.userID,
				this.loginName, 
				this.userName, 
				this.classCode,
				this.moduleID,
				this.refID,
				this.gradeID);
		String[] str=outStr.split(",");
		userDataVO.setTrainingTimes(str[1]);
		userDataVO.setLastTrainingTime(str[2]);
		
		/*userData变量转载入session中*/
		this.setSessionObj(SessionDict.UserData,userDataVO);
		
		
		/*检验流程节点，添加流程节点*/
		outStr=this.getLoginService().checkAndGetFlowitem(userID, refID);
		
		if(null!=outStr&&!"success".equals(outStr))
			return outStr;
		else if(null==outStr)
			return "failure";
		
		/*webUser变量转载入session中*/
		this.setSessionObj(SessionDict.WebUser, 
				this.getLoginService().getWebUser(userID));
		
		/*调用流程策略判断项目版本*/
		ProcessPolicy processPolicy=this.getLoginService().get(ProcessPolicy.class,new Long(refID));
		userDataVO.setProcessPolicy(processPolicy);
		this.setProjectVersion(processPolicy.getProjectVersion());
		
		//与平台通讯
		HttpServletRequest request = this.getHttpServletRequest();
		RedPlatformPlugin.callBack(request);
		
		return outStr;
	}
	
	/**
	 * 装载
	 * @return
	 */
	
	public String LoginSecondStep() {
		
		return null;
	}
	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getModuleID() {
		return moduleID;
	}

	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getGradeID() {
		return gradeID;
	}

	public void setGradeID(String gradeID) {
		this.gradeID = gradeID;
	}
	
	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
}
