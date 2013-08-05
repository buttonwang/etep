/**
 * 
 */
package com.ambow.studyflow.decision.impl;

import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;

/*
 * DefaultGroupDecision.java:默认节点组决策器
 * 
 * Created on 2008-5-22
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

public class DefaultGroupDecision implements IDecision {
	private NodeDao nodeDao;
	/* (non-Javadoc)
	 * @see com.ambow.studyflow.decision.IDecision#excute(java.util.Map, com.ambow.studyflow.common.NodeStatus)
	 */
	public Node excute(Map map, NodeStatus nodeStatus) {
		Node currentNode=(Node)map.get("currentNode");
		Assert.notNull(currentNode);
		Assert.notNull(nodeStatus);
		return nodeDao.getGroupDefaultNode(currentNode.getId());
	}
	public NodeDao getNodeDao() {
		return nodeDao;
	}
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}
	
	

}
