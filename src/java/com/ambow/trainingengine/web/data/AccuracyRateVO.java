package com.ambow.trainingengine.web.data;


public class AccuracyRateVO {
	private Integer accuracyRate;
	private long processInstanceId;
	private String userName;	
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
	public Integer getAccuracyRate() {
		return accuracyRate;
	}
	public void setAccuracyRate(Integer accuracyRate) {
		this.accuracyRate = accuracyRate;
	}
}
