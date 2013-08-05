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
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;

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

public class ExamDecision implements IDecision  {

	private NodeDao nodeDao;
	private NodeInstanceDao nodeInstanceDao;
	private ProcessPolicyService processPolicyService;
	@SuppressWarnings("unchecked")
	public Node excute(Map map, NodeStatus nodeStatus) {		
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		//Node defaultNextNode=(Node)map.get("defaultNextNode");
		Long processInstanceId=(Long)map.get("processInstanceId");
		Node currentNode=(Node)map.get("currentNode");
		
		Assert.notNull(nodeGroupId);
		Assert.notNull(nodeStatus);
		Assert.notNull(processInstanceId);
		Assert.notNull(currentNode);
		
		Node nextNode=null;
		if(nodeStatus.toInt()<NodeStatus.PASSED.toInt())
		{	
			//获取不通过的策略
			UnitTestNodePolicy unitTestNodePolicy=processPolicyService.getUnitTestNodePolicy(currentNode);
			Assert.notNull(unitTestNodePolicy);
			// 如果未通过则将本组练习节点全部置为未通过
			nodeInstanceDao.resetPracticeNodeInstanceStatusByGroupId(nodeGroupId, processInstanceId);
			if(unitTestNodePolicy.getRetrainingScope()==0)
				//进入本组第一个练习节点。								
				nextNode=nodeDao.findDefaultPracticeNodeByGroupById(nodeGroupId);
			else
				// 寻找上n级节点组的下一个未通过节点
				nextNode=processPolicyService.getParentNextNode(currentNode, processInstanceId, unitTestNodePolicy.getRetrainingScope());
			
		}
		//如果通过则进入下一节点
		return nextNode;
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
	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}
	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}
	
	

}
