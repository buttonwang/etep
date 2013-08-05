package com.ambow.trainingengine.studyflow.service;

import javax.servlet.http.HttpServletRequest;

public class StudyFlowCommon {
	public Object getObject(HttpServletRequest request,Class clazz){
		request.getParameterNames();
		try {
			Object obj = clazz.newInstance();
			
		} catch (InstantiationException e) {
				e.printStackTrace();
		} catch (IllegalAccessException e) {		
			e.printStackTrace();
		}
		return null;
	}
}