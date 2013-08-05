/**
 * 
 */
package com.ambow.studyflow.decision.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.common.ServiceLocator;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;

/*
 * DefaultExamDecision.java:考试节点的默认决策器
 * 
 * Created on 2008-5-12
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */

public class DefaultExamDecision implements IDecision  {

	private NodeDao nodeDao;
	private NodeInstanceDao nodeInstanceDao;
	public Node excute(Map map, NodeStatus nodeStatus) {		
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		Node defaultNextNode=(Node)map.get("defaultNextNode");
		Long processInstanceId=(Long)map.get("processInstanceId");
		Assert.notNull(nodeGroupId);
		Assert.notNull(nodeStatus);
		Assert.notNull(processInstanceId);
		//考试节点的逻辑比较简单，如果通过则进入下一节点，
		// 如果未通过则将本组练习节点全部置为未通过,进入本组第一个练习节点。
		Node nextNode=defaultNextNode;
		if(nodeStatus.toInt()<NodeStatus.PASSED.toInt())
		{
			nodeInstanceDao.resetPracticeNodeInstanceStatusByGroupId(nodeGroupId, processInstanceId);			
			nextNode=nodeDao.findDefaultPracticeNodeByGroupById(nodeGroupId);
			return nextNode;
		}
		else
		{
		return nextNode;
		}
	}
	public NodeDao getNodeDao() {
		return nodeDao;
	}
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}
	public NodeInstanceDao getNodeInstanceDao() {
		return nodeInstanceDao;
	}
	public void setNodeInstanceDao(NodeInstanceDao nodeInstanceDao) {
		this.nodeInstanceDao = nodeInstanceDao;
	}
	
	

}
