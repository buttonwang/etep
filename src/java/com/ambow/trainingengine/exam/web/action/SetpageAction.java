package com.ambow.trainingengine.exam.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.service.SetpageService;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
public class SetpageAction  extends BaseAction {
	private String layout1;
	private String layout2;
	private String layout3;
	private String font;
	
	private static final long serialVersionUID = 1L;
	
	private SetpageService setpageService = null;
	
	public String execute() {
		UserDataVO userDataVO = (UserDataVO) this.getHttpSessionObj(SessionDict.UserData);
		long processInstanceId = userDataVO.getProcessInstanceId();
		ProcessTrainingStatus processTrainingStatus = this.setpageService.queryForObj(processInstanceId);
		this.setRequestAttribute("processTrainingStatus", processTrainingStatus);
		return SUCCESS;
	}
	
	/**
	 * 更新状态
	 */
	public String save(){
		UserDataVO userDataVO = (UserDataVO) this.getHttpSessionObj(SessionDict.UserData);
		long processInstanceId = userDataVO.getProcessInstanceId();
		String layout1 = this.getHttpServletRequest().getParameter("layout1");
		String layout2 = this.getHttpServletRequest().getParameter("layout2");
		String layout3 = this.getHttpServletRequest().getParameter("layout3");
		String font = this.getHttpServletRequest().getParameter("font");
		this.setpageService.update(processInstanceId, layout1, layout2, layout3, font);
		return "save";
	}

	@Override
	public String getAuthStr() {
		return null;
	}

	public SetpageService getSetpageService() {
		return setpageService;
	}

	public void setSetpageService(SetpageService setpageService) {
		this.setpageService = setpageService;
	}

	public String getLayout1() {
		return layout1;
	}

	public void setLayout1(String layout1) {
		this.layout1 = layout1;
	}

	public String getLayout2() {
		return layout2;
	}

	public void setLayout2(String layout2) {
		this.layout2 = layout2;
	}

	public String getLayout3() {
		return layout3;
	}

	public void setLayout3(String layout3) {
		this.layout3 = layout3;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
}
