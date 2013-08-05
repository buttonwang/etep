package com.ambow.trainingengine.exam.web.data;

import com.ambow.studyflow.common.NodeStatus;

public class NodeInstanceVO {
	
	private Long id;
	
	private NodeStatus nodeStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NodeStatus getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(NodeStatus nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	
}
