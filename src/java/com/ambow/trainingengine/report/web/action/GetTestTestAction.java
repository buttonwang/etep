package com.ambow.trainingengine.report.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ambow.trainingengine.report.service.TeacherManagerService;

/*
 * TeacherManager.java
 * 
 * Created on 2010-1-6 下午03:53:26
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

public class GetTestTestAction extends ReportBaseAction {
	
	private static final long serialVersionUID = 1553697764253917148L;
	
	int refID;
	int testID;
	String userID;
	String classID;

	TeacherManagerService teacherManagerService;

	public String execute() {
		try {
			String xmlInfo = "";
			xmlInfo = teacherManagerService.TestTestToXML(
					teacherManagerService.getFixTime(testID),
					teacherManagerService.getTestTestList(testID, classID));
			HttpServletResponse response = this.getHttpServletResponse();
			response.setContentType("text/xml");
			response.getWriter().write(xmlInfo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getCoursesTestAnalyse() {
		return "";
	}
	
	public String getTestTest() {
		return "";
	}
	
	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public int getRefID() {
		return refID;
	}

	public void setRefID(int refID) {
		this.refID = refID;
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public TeacherManagerService getTeacherManagerService() {
		return teacherManagerService;
	}

	public void setTeacherManagerService(TeacherManagerService teacherManagerService) {
		this.teacherManagerService = teacherManagerService;
	}
	
}
