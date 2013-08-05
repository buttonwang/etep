package com.ambow.trainingengine.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.StudyInfoUtil;




public class FormulatorTestAction extends WebBaseAction {

	private static final long serialVersionUID = 1L;

	private MainPageService mainPageService;
	
	
	public String execute() {
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		this.mainPageService.updateFormulatorTest(userData);
		
		return SUCCESS;
	}
	
	
	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}


	

}
