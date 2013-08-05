package com.ambow.trainingengine.web.data;

public class PauseInfoVO {
	String titleInfo;
	String pauseType;//训练 弱项强化
	Integer totalItemsNum;
	Integer finishedItemsNum;
	String totalAnsweringTime;
	String consumeTime;
	Long nodeInstanceId;
	public String getTitleInfo() {
		return titleInfo;
	}
	public void setTitleInfo(String titleInfo) {
		this.titleInfo = titleInfo;
	}
	public String getPauseType() {
		return pauseType;
	}
	public void setPauseType(String pauseType) {
		this.pauseType = pauseType;
	}
	
	public String getTotalAnsweringTime() {
		return totalAnsweringTime;
	}
	public void setTotalAnsweringTime(String totalAnsweringTime) {
		this.totalAnsweringTime = totalAnsweringTime;
	}
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
	public Integer getTotalItemsNum() {
		return totalItemsNum;
	}
	public void setTotalItemsNum(Integer totalItemsNum) {
		this.totalItemsNum = totalItemsNum;
	}
	public Integer getFinishedItemsNum() {
		return finishedItemsNum;
	}
	public void setFinishedItemsNum(Integer finishedItemsNum) {
		this.finishedItemsNum = finishedItemsNum;
	}
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
}
