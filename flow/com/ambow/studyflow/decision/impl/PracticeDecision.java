/**
 * 
 */
package com.ambow.studyflow.decision.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;

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

public class PracticeDecision implements IDecision {

	@SuppressWarnings("unused")
	private NodeDao nodeDao;
	private NodeInstanceDao nodeInstanceDao;
	
	private ProcessPolicyService processPolicyService;
	/* 
	 * 判断当前节点是否通过，未通过则保持原状
	 * 如果当前节点通过，判断所在节点组练习节点是否全部通过，如果通过，去考试节点
	 * 如果未通过，去下一个未通过的练习节点
	 * @see com.ambow.studyflow.decision.IDecision#excute(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Node excute(Map map, NodeStatus nodeStatus) {
		//默认目标节点
		//
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		Node currentNode=(Node)map.get("currentNode");
		Node defaultNextNode=(Node)map.get("defaultNextNode");		
		NodeInstance defaultNextNodeInstance=(NodeInstance)map.get("defaultNextNodeInstance");
		Long processInstanceId=(Long)map.get("processInstanceId");
		//String projectVersion=(String)map.get("projectVersion");
		Object idsObj=map.get("showNodeGroupVONodeIds");
		Assert.notNull(nodeStatus,"NodeStatus can't be null");
		Assert.notNull(nodeGroupId);
		//Assert.notNull(defaultNextNode);
		Assert.notNull(processInstanceId);
		//Assert.notNull(defaultNextNodeInstance);
		Assert.notNull(currentNode);		
		Node nextNode=null;
		TrainingUnitNodePolicy trainingUnitNodePolicy=processPolicyService.getTrainingUnitNodePolicy(currentNode);
		Assert.notNull(trainingUnitNodePolicy);
		int tag=0;
		//都没有，判断当前节点是否通过，获取对应策略字段数值
		if(nodeStatus.equals(NodeStatus.NOPASS))
			tag=trainingUnitNodePolicy.getFailed();
		else
			tag=trainingUnitNodePolicy.getPass();
		if (tag==1)//1，返回当前节点
			//nextNode = currentNode;
			return currentNode;
		
		//tag==0
		//如果默认的下一个节点实例是未通过状态且是练习节点，则直接返回此节点
		if(defaultNextNodeInstance!=null&&(defaultNextNodeInstance.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()) && (defaultNextNode instanceof PracticeNode))
			return defaultNextNode;		
		if(idsObj==null){//0 向前		
				//否则，向下找，向上找，有则直接返回
				//向后寻找本组下一个未通过的练习节点
			nextNode=nodeInstanceDao.findAfterCurrentNotPassPracticeNodeByGroupId(currentNode.getOrderNum(),nodeGroupId, processInstanceId);
			if(nextNode==null){
					//向前寻找			
				nextNode=nodeInstanceDao.findBeforeCurrentNotPassPracticeNodeByGroupId(currentNode.getOrderNum(),nodeGroupId, processInstanceId);
			}
			
		}	
		
		else if(idsObj!=null){
			
			List<NodeInstance> list=this.nodeInstanceDao.find("from NodeInstance a where a.processInstance.id=? and a.node.id in ("
					+idsObj.toString()+") order by a.node.orderNum", processInstanceId);
			//向后找是否有未通过的训练节点
			nextNode=nodeInstanceDao.findAfterCurrentNotPassPracticeNodeByGroupId(list,currentNode);
			if(nextNode==null){
				//向前寻找			
				nextNode=nodeInstanceDao.findBeforeCurrentNotPassPracticeNodeByGroupId(list,currentNode);
			}
		}
		if (nextNode == null) {
			if(nodeStatus.equals(NodeStatus.NOPASS))
				nextNode=currentNode;
			//else
			//	nextNode=processPolicyService.getNextNode(defaultNextNodeInstance);
		}	
		return nextNode;
	}
	
	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setNodeInstanceDao(NodeInstanceDao nodeInstanceDao) {
		this.nodeInstanceDao = nodeInstanceDao;
	}

	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}

	public NodeInstanceDao getNodeInstanceDao() {
		return nodeInstanceDao;
	}

	
}
