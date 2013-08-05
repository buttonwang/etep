package com.ambow.trainingengine.web.data;


public class LearningProcessRateVO {
	private Integer learningProcessRate;
	private long processInstanceId;
	private String userName;
	public Integer getLearningProcessRate() {
		return learningProcessRate;
	}
	public void setLearningProcessRate(Integer learningProcessRate) {
		this.learningProcessRate = learningProcessRate;
	}
	public long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
