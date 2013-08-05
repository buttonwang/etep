package com.ambow.trainingengine.web.data;

public class ShowNodeGroupVO {
	private Long nodeId;
	private Long nodeInstanceId;
	private String nodeName;
	private String childNodeIds;
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getChildNodeIds() {
		return childNodeIds;
	}
	public void setChildNodeIds(String childNodeIds) {
		this.childNodeIds = childNodeIds;
	}
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	
}
