package com.ambow.studyflow.decision.impl;

import java.util.Map;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;

public class ExamplePracticeDecision implements IDecision {
	
	private NodeDao nodeDao;
	
	private NodeInstanceDao nodeInstanceDao;

	public Node excute(Map map, NodeStatus nodeStatus) {
		// TODO Auto-generated method stub
		return null;
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
