package com.ambow.studyflow.decision.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;
public class PhaseExamDecision implements IDecision {
	
	private NodeDao nodeDao;
	
	private NodeInstanceDao nodeInstanceDao;
	private ProcessPolicyService processPolicyService;

	@SuppressWarnings("unchecked")
	public Node excute(Map map, NodeStatus nodeStatus) {
		
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		Long processInstanceId=(Long)map.get("processInstanceId");
		Node currentNode=(Node)map.get("currentNode");
		Node defaultNextNode=(Node)map.get("defaultNextNode");
		//String score=(String)map.get("score");
		String rightRate=(String)map.get("rightRate");
		if(rightRate==null)
			rightRate="0";
		Assert.notNull(rightRate);
		Assert.notNull(nodeGroupId);
		Assert.notNull(currentNode);
		Assert.notNull(defaultNextNode);
		Node nextNode=defaultNextNode;
		Float rightRateFloat=Float.valueOf(rightRate);
		List<PhaseTestNodePolicy> list=this.processPolicyService.getPhaseTestNodePolicy(currentNode);
		Assert.notNull(list);
		for(int i=0;i<list.size();i++){
			PhaseTestNodePolicy po=list.get(i);
			Assert.notNull(po.getJumpPosition());			
			boolean tag=true;
			if((rightRateFloat>=po.getStartValue()&&rightRateFloat<po.getEndValue())
					||(i==list.size()-1&&rightRateFloat==100&&po.getEndValue()==100))
				tag=false;
			if(tag)
				continue;
			
			nextNode=this.nodeDao.get(Node.class, po.getJumpPosition().longValue());
			processPolicyService.updateNodeInstancesStatus(processInstanceId, currentNode, nextNode);
			break;
		}
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
