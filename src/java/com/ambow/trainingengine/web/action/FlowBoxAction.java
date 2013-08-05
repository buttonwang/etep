package com.ambow.trainingengine.web.action;

import java.util.List;

import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;

public class FlowBoxAction extends WebBaseAction {
	private MainPageService mainPageService;
	private Node secondLevevNode;
	private static final long serialVersionUID = 1L;
	private long upNodeId;
	private long downNodeId;
	private long currentNodeId=0l;
	public String execute() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		
		init(userData);
		
		return SUCCESS;
	}
	
	public void init(UserDataVO userData){
		List<Node> nodes=mainPageService.getSecondLevevNodes(userData);
		if(nodes==null&&nodes.size()==0)
			return;
		Node currentSecondLevevNode=null;
		if(currentNodeId==0)
			currentSecondLevevNode=this.getMainPageService().getSecondLevevNode(userData.getCurrentNodeId());
		else
			currentSecondLevevNode=this.getMainPageService().get(Node.class,currentNodeId);
		this.setSecondLevevNode(currentSecondLevevNode);
		
		for(int i=0;i<nodes.size();i++){
			Node tempNode=nodes.get(i);
			if(currentSecondLevevNode.getId()==tempNode.getId()){
				if(i>0)
					upNodeId=nodes.get(i-1).getId();
				if(i<nodes.size()-1)
					downNodeId=nodes.get(i+1).getId();
			}
		}
		userData=mainPageService.getNodeInstanceInfoVOList(userData,currentSecondLevevNode);
		
		
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}

	public Node getSecondLevevNode() {
		return secondLevevNode;
	}

	public void setSecondLevevNode(Node secondLevevNode) {
		this.secondLevevNode = secondLevevNode;
	}

	public long getUpNodeId() {
		return upNodeId;
	}

	public void setUpNodeId(long upNodeId) {
		this.upNodeId = upNodeId;
	}

	public long getDownNodeId() {
		return downNodeId;
	}

	public void setDownNodeId(long downNodeId) {
		this.downNodeId = downNodeId;
	}

	public long getCurrentNodeId() {
		return currentNodeId;
	}

	public void setCurrentNodeId(long currentNodeId) {
		this.currentNodeId = currentNodeId;
	}

}
