package com.ambow.trainingengine.web.action;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.StudyTaskService;

/*
 * StudyTaskAction.java
 * 
 * Created on 2008-7-31 下午04:08:50
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
public class StudyTaskAction extends WebBaseAction {

	/**
	 * 
	 */	
	private static final long serialVersionUID = 1L;

	private StudyTaskService studyTaskService;
	
	private long groupNodeId=0;
	
	public String execute() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);

		if(userData.getProcessPolicy().getProjectVersion().equals("ky"))
			this.studyTaskForKy(userData);
		else if(userData.getProcessPolicy().getProjectVersion().equals("mpc"))
			this.studyTaskForMpc(userData);
		
		return SUCCESS;
	}
	
	public void studyTaskForKy(UserDataVO userData){
		//节点组进度列表
		if(groupNodeId==0){
			ShowNodeGroupVO vo=userData.getShowNodeGroupVOList().get(0);
			if(vo!=null)
				this.setGroupNodeId(vo.getNodeId());
		}
		this.setRequestAttribute("groupNodeStatList", this.studyTaskService.getGroupNodeStatList(userData));
				
		//节点组内节点列表
		this.setRequestAttribute("nodeListOfGroupNodeList", this.studyTaskService.getNodeListOfGroupNode(this.groupNodeId, userData));
	}
	
	public void studyTaskForMpc(UserDataVO userData){
		//节点组进度列表
		if(groupNodeId==0)
				this.setGroupNodeId(this.studyTaskService.getFirstGroupNodeId(userData));
		
		this.setRequestAttribute("groupNodeStatList", this.studyTaskService.getGroupNodeStatListForMpc(userData));
		
		//章内节点列表
		this.setRequestAttribute("nodeListOfGroupNodeList", this.studyTaskService.getNodeVOListForMpc(this.groupNodeId, userData));
		
	}
	

	public StudyTaskService getStudyTaskService() {
		return studyTaskService;
	}

	public void setStudyTaskService(StudyTaskService studyTaskService) {
		this.studyTaskService = studyTaskService;
	}

	public long getGroupNodeId() {
		return groupNodeId;
	}

	public void setGroupNodeId(long groupNodeId) {
		this.groupNodeId = groupNodeId;
	}

}
