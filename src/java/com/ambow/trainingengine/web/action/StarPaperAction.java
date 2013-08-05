package com.ambow.trainingengine.web.action;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.RepuestDictForWeb;
@SuppressWarnings("serial")
public class StarPaperAction extends WebBaseAction {
	private MainPageService mainPageService;
	public String execute() {

		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
				
		if(userData.getProcessPolicy().getProjectVersion().equals("ky"))
			this.starPaperListForKy(userData);
		else if(userData.getProcessPolicy().getProjectVersion().equals("mpc"))
			this.starPaperListForMpc(userData);
		
		return SUCCESS;
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
	
	public void starPaperListForKy(UserDataVO userData){
		//装载星卷列表		
		this.setRequestAttribute(RepuestDictForWeb.STAR_PPAPER_LIST,
				this.getMainPageService().getStarPaperList(userData,10));
	}
	
	public void starPaperListForMpc(UserDataVO userData){
		//装载星卷列表		
		this.setRequestAttribute(RepuestDictForWeb.STAR_PPAPER_LIST,
				this.getMainPageService().getStarPaperListForMpc(userData,10));
	}
}
