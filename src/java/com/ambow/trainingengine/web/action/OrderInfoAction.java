package com.ambow.trainingengine.web.action;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.RepuestDictForWeb;
@SuppressWarnings("serial")
public class OrderInfoAction extends WebBaseAction {
	private MainPageService mainPageService;
	public String execute() {

		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		
		//装载星卷列表		
	//	this.setRequestAttribute(RepuestDictForWeb.STAR_PPAPER_LIST,
	//			this.getMainPageService().getStarPaperList(userData));
		//装载掌握度列表
		this.setRequestAttribute(RepuestDictForWeb.TOTAL_MASTERY_RATE_TOP_LIST,
				this.getMainPageService().getTotalMasteryRateTopList(userData));
		//总进度排名
		this.setRequestAttribute(RepuestDictForWeb.LEARNING_PROCESS_RATE_TOP_LIST,
				this.getMainPageService().getLearningProcessRateTopList(userData));
		//装载总正确率排名
		this.setRequestAttribute(RepuestDictForWeb.ACCURACY_RATE_TOP_LIST,
				this.getMainPageService().getAccuracyRateTopList(userData));
		
		
		return SUCCESS;
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}

}
