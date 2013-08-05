package com.ambow.trainingengine.web.data;

public class TotalMasteryRateVO {
	private Integer totalMasteryRate;
	private long processInstanceId;
	private String userName;
	public Integer getTotalMasteryRate() {
		return totalMasteryRate;
	}
	public void setTotalMasteryRate(Integer totalMasteryRate) {
		this.totalMasteryRate = totalMasteryRate;
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
