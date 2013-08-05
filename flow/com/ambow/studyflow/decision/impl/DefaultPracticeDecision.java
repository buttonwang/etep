/**
 * 
 */
package com.ambow.studyflow.decision.impl;

import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.PracticeNode;

/*
 * DefaultPracticeDecision.java:默认练习节点决策器
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

public class DefaultPracticeDecision implements IDecision {

	private NodeDao nodeDao;
	private NodeInstanceDao nodeInstanceDao;
	
	/* 
	 * 判断当前节点是否通过，未通过则保持原状
	 * 如果当前节点通过，判断所在节点组练习节点是否全部通过，如果通过，去考试节点
	 * 如果未通过，去下一个未通过的练习节点
	 * @see com.ambow.studyflow.decision.IDecision#excute(java.util.Map)
	 */
	public Node excute(Map map, NodeStatus nodeStatus) {
		//默认目标节点
		//
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		Node currentNode=(Node)map.get("currentNode");
		Node defaultNextNode=(Node)map.get("defaultNextNode");		
		NodeInstance defaultNextNodeInstance=(NodeInstance)map.get("defaultNextNodeInstance");
		Long processInstanceId=(Long)map.get("processInstanceId");
		Assert.notNull(nodeStatus,"NodeStatus can't be null");
		Assert.notNull(nodeGroupId);
		Assert.notNull(defaultNextNode);
		Assert.notNull(processInstanceId);
		Assert.notNull(defaultNextNodeInstance);
		Assert.notNull(currentNode);
		System.out.println("nodeId ------------>"+currentNode.getId());
		System.out.println("nodeGroupId ------------>"+nodeGroupId);
		System.out.println("orderNum ------------>"+currentNode.getOrderNum());
		Node nextNode=null;
        //如果默认的下一个节点实例是未通过状态且是练习节点，则直接返回此节点
		if((defaultNextNodeInstance.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()) && (defaultNextNode instanceof PracticeNode))
			return defaultNextNode;
		//否则，向下找，向上找，有则直接返回
		
			//向后寻找本组下一个未通过的练习节点
			nextNode=nodeInstanceDao.findAfterCurrentNotPassPracticeNodeByGroupId(currentNode.getOrderNum(),nodeGroupId, processInstanceId);
			if(nextNode==null)
			{	//向前寻找
				nextNode=nodeInstanceDao.findBeforeCurrentNotPassPracticeNodeByGroupId(currentNode.getOrderNum(),nodeGroupId, processInstanceId);
			}
			
			//都没有，判断当前节点是否通过，没有通过，则返回本节点自己，已通过，则进入考试节点.
			if (nextNode == null) {
			if (nodeStatus.equals(NodeStatus.NOPASS))
				nextNode = currentNode;
			else {
				nextNode = nodeInstanceDao.findExamNodeByGroupId(nodeGroupId,
						currentNode.getOrderNum(), processInstanceId);
			}
		}
				
		return nextNode;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setNodeInstanceDao(NodeInstanceDao nodeInstanceDao) {
		this.nodeInstanceDao = nodeInstanceDao;
	}

	
}
