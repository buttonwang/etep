package com.ambow.trainingengine.studyguide.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
import com.ambow.trainingengine.studyguide.service.ShowStudyGuideService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ShowStudyGuideAction.java
 * 
 * Created on 2009-8-3 下午05:44:27
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

public class ShowStudyGuideAction extends BaseAction {

	private static final long serialVersionUID = -6390555575764993425L;

	private ShowStudyGuideService showStudyGuideService;
	private Integer nodeId=null;
	
	/**
	 * 树型菜单
	 */
	public String execute() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		List<StudyGuide> sgList = this.showStudyGuideService.getStudyGuideListFromProcessDefinitionId(processDefinitionId);
		this.setRequestAttribute("list", sgList);
		return "main";
	}
	
	@Override
	public String getAuthStr() {
		return null;
	}
	
	public ShowStudyGuideService getShowStudyGuideService() {
		return showStudyGuideService;
	}
	public void setShowStudyGuideService(ShowStudyGuideService showStudyGuideService) {
		this.showStudyGuideService = showStudyGuideService;
	}

	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

}
