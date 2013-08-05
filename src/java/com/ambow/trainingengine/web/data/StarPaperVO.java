package com.ambow.trainingengine.web.data;

public class StarPaperVO {
	private String nodeName;
	private String nodeGroupName;
	private Float firstAccuracyRate;
	private Integer accuracyRate;
	private Integer masteryRate;
	private String time;
	private Long instanceId;
	private Float totalScore;
	private Float score;
	private Integer correctItems;
	private Integer totalItems;
	private String orderNum;
	private String nodeType;
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public Float getFirstAccuracyRate() {
		return firstAccuracyRate;
	}
	public void setFirstAccuracyRate(Float firstAccuracyRate) {
		this.firstAccuracyRate = firstAccuracyRate;
	}
	public Integer getAccuracyRate() {
		return accuracyRate;
	}
	public void setAccuracyRate(Integer accuracyRate) {
		this.accuracyRate = accuracyRate;
	}
	public Integer getMasteryRate() {
		return masteryRate;
	}
	public void setMasteryRate(Integer masteryRate) {
		this.masteryRate = masteryRate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Long getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}
	public String getNodeGroupName() {
		return nodeGroupName;
	}
	public void setNodeGroupName(String nodeGroupName) {
		this.nodeGroupName = nodeGroupName;
	}
	public Float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getCorrectItems() {
		return correctItems;
	}
	public void setCorrectItems(Integer correctItems) {
		this.correctItems = correctItems;
	}
	public Integer getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
}
