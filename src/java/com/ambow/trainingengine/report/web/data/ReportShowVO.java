package com.ambow.trainingengine.report.web.data;

public class ReportShowVO {
	private Integer sumIncorrectItems=0;
	private Integer sumUnfinishedItems=0;
	private Integer sumCorrectItems=0;//正确题数
	private Integer sumfinishedItems=0;//已作题数
	private Integer unsureMarkItems=0;//疑问题总数
	private Integer sumZeroStarItems=0;
	private Integer sumHalfStarItems=0;
	private Integer sumOneStarItems=0;
	private Integer sumTwoStarItems=0;
	private Integer sumThreeStarItems=0;
	private Integer sumFourStarItems=0;
	private Integer sumFiveStarItems=0;
	private Float firstTestScore=0f;
	private Float firstTestAccuracyRate=0f;
	private Float masteryRate=0f;
	private Float accuracyRate=0f;
	private Float score=0f;
	private Integer  itemsNum=0;
	private Float  totalScore=0f;
	private Long nodeId;
	private Long nodeGroupId;
	private String nodeName;
	private Float rightRateForPass=0f;
	private Integer answeringTime=0;
	private Double starNum=0d;
	private String endTime="";
	private Integer nodeStatus=0;
	private Integer times;
	private Long nodeInstanceId=0l;
	private String knowledgePointName;
	private String knowledgePointCode;
	private Float scoreTwo=0f;
	private Float totalScoreTwo=0f;
	private Float accuracyRateTwo=0f;
	
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getAnsweringTime() {
		return answeringTime;
	}
	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
	public Double getStarNum() {
		return starNum;
	}
	public void setStarNum(Double starNum) {
		this.starNum = starNum;
	}
	public Integer getSumIncorrectItems() {
		return sumIncorrectItems;
	}
	public void setSumIncorrectItems(Integer sumIncorrectItems) {
		this.sumIncorrectItems = sumIncorrectItems;
	}
	public Integer getSumUnfinishedItems() {
		return sumUnfinishedItems;
	}
	public void setSumUnfinishedItems(Integer sumUnfinishedItems) {
		this.sumUnfinishedItems = sumUnfinishedItems;
	}
	public Integer getSumZeroStarItems() {
		return sumZeroStarItems;
	}
	public void setSumZeroStarItems(Integer sumZeroStarItems) {
		this.sumZeroStarItems = sumZeroStarItems;
	}
	public Integer getSumHalfStarItems() {
		return sumHalfStarItems;
	}
	public void setSumHalfStarItems(Integer sumHalfStarItems) {
		this.sumHalfStarItems = sumHalfStarItems;
	}
	public Integer getSumOneStarItems() {
		return sumOneStarItems;
	}
	public void setSumOneStarItems(Integer sumOneStarItems) {
		this.sumOneStarItems = sumOneStarItems;
	}
	public Integer getSumTwoStarItems() {
		return sumTwoStarItems;
	}
	public void setSumTwoStarItems(Integer sumTwoStarItems) {
		this.sumTwoStarItems = sumTwoStarItems;
	}
	public Integer getSumThreeStarItems() {
		return sumThreeStarItems;
	}
	public void setSumThreeStarItems(Integer sumThreeStarItems) {
		this.sumThreeStarItems = sumThreeStarItems;
	}
	public Integer getSumFourStarItems() {
		return sumFourStarItems;
	}
	public void setSumFourStarItems(Integer sumFourStarItems) {
		this.sumFourStarItems = sumFourStarItems;
	}
	public Integer getSumFiveStarItems() {
		return sumFiveStarItems;
	}
	public void setSumFiveStarItems(Integer sumFiveStarItems) {
		this.sumFiveStarItems = sumFiveStarItems;
	}
	public Float getFirstTestScore() {
		return firstTestScore;
	}
	public void setFirstTestScore(Float firstTestScore) {
		this.firstTestScore = firstTestScore;
	}
	public Float getFirstTestAccuracyRate() {
		return firstTestAccuracyRate;
	}
	public void setFirstTestAccuracyRate(Float firstTestAccuracyRate) {
		this.firstTestAccuracyRate = firstTestAccuracyRate;
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
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Integer getItemsNum() {
		return itemsNum;
	}
	public void setItemsNum(Integer itemsNum) {
		this.itemsNum = itemsNum;
	}
	public Float getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}
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
	public Float getRightRateForPass() {
		return rightRateForPass;
	}
	public void setRightRateForPass(Float rightRateForPass) {
		this.rightRateForPass = rightRateForPass;
	}
	public Integer getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(Integer nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	public Integer getSumCorrectItems() {
		return sumCorrectItems;
	}
	public void setSumCorrectItems(Integer sumCorrectItems) {
		this.sumCorrectItems = sumCorrectItems;
	}
	public Integer getSumfinishedItems() {
		return sumfinishedItems;
	}
	public void setSumfinishedItems(Integer sumfinishedItems) {
		this.sumfinishedItems = sumfinishedItems;
	}
	public Integer getUnsureMarkItems() {
		return unsureMarkItems;
	}
	public void setUnsureMarkItems(Integer unsureMarkItems) {
		this.unsureMarkItems = unsureMarkItems;
	}
	public String getKnowledgePointName() {
		return knowledgePointName;
	}
	public void setKnowledgePointName(String knowledgePointName) {
		this.knowledgePointName = knowledgePointName;
	}
	public Long getNodeGroupId() {
		return nodeGroupId;
	}
	public void setNodeGroupId(Long nodeGroupId) {
		this.nodeGroupId = nodeGroupId;
	}
	public Float getScoreTwo() {
		return scoreTwo;
	}
	public void setScoreTwo(Float scoreTwo) {
		this.scoreTwo = scoreTwo;
	}
	public Float getTotalScoreTwo() {
		return totalScoreTwo;
	}
	public void setTotalScoreTwo(Float totalScoreTwo) {
		this.totalScoreTwo = totalScoreTwo;
	}
	public Float getAccuracyRateTwo() {
		return accuracyRateTwo;
	}
	public void setAccuracyRateTwo(Float accuracyRateTwo) {
		this.accuracyRateTwo = accuracyRateTwo;
	}
	public String getKnowledgePointCode() {
		return knowledgePointCode;
	}
	public void setKnowledgePointCode(String knowledgePointCode) {
		this.knowledgePointCode = knowledgePointCode;
	}
}
