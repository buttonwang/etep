package com.ambow.trainingengine.web.data;

public class NodeInstanceInfoVO {
	//****（单元名），通过训练，我的成绩92，掌握度86% 有23人正在训练本单元
	private long nodeId;
	private long nodeInstanceId;
	private String nodeName;
	private String nodeType;
	private String orderNum;
	private int nodeStatus;
	private int pauseStatus;
	private Float score;
	private Float masteryRate;
	private int testingNum;
	private String cssClassName;
	private String cssClassName2;
	private String titleInfo="";
	private int tag=0;//1 代表有答题记录，0代表没有答题记录
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
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public int getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(int nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getMasteryRate() {
		return masteryRate;
	}
	public void setMasteryRate(Float masteryRate) {
		this.masteryRate = masteryRate;
	}
	public int getTestingNum() {
		return testingNum;
	}
	public void setTestingNum(int testingNum) {
		this.testingNum = testingNum;
	}
	public String getCssClassName() {
		return cssClassName;
	}
	public void setCssClassName(String cssClassName) {
		this.cssClassName = cssClassName;
	}
	public String getTitleInfo() {
		return titleInfo;
	}
	public void setTitleInfo(String titleInfo) {
		this.titleInfo = titleInfo;
	}
	public int getPauseStatus() {
		return pauseStatus;
	}
	public void setPauseStatus(int pauseStatus) {
		this.pauseStatus = pauseStatus;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getCssClassName2() {
		return cssClassName2;
	}
	public void setCssClassName2(String cssClassName2) {
		this.cssClassName2 = cssClassName2;
	}
}
