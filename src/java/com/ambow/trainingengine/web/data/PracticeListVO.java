package com.ambow.trainingengine.web.data;

public class PracticeListVO {
	private long nodeId;//节点定义id
	private long nodeInstanceId;//节点实例id
	private Float masteryRate;
	private Float accuracyRate;
	private String date;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	public Float getMasteryRate() {
		return masteryRate;
	}
	public void setMasteryRate(Float masteryRate) {
		this.masteryRate = masteryRate;
	}
	public Float getAccuracyRate() {
		return accuracyRate;
	}
	public void setAccuracyRate(Float accuracyRate) {
		this.accuracyRate = accuracyRate;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	} 
}
