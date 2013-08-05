package com.ambow.trainingengine.exam.display.ky;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.util.ExamkyUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SwieBean;

/*
 * DisplayKy39.java
 * 
 * Created on 2008-11-13 下午06:34:05
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
public class Display39 extends Displayky {
	//注入写作系统信息
	private SwieBean swieBean;
	@Override
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		ExamkyUtil.handWritingView(page, viewControl);
		if(viewControl.getShowModel()==2||viewControl.getShowModel()==3)
			genSWIEInfo(request);
	}
	/* 
	 * 生成传送到客户端的SWIEbean信息。提交文章给外交的action 
	 */
	private void genSWIEInfo(HttpServletRequest request) {
		swieBean.setUserName(((Webuser)request.getSession().getAttribute("webuser")).getLoginName());
		request.setAttribute("swie", swieBean);
	}
	public SwieBean getSwieBean() {
		return swieBean;
	}
	public void setSwieBean(SwieBean swieBean) {
		this.swieBean = swieBean;
	}
	
}
