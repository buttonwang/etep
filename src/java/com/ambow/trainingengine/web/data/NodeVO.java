package com.ambow.trainingengine.web.data;

import java.util.Date;
import java.util.List;

import com.ambow.trainingengine.util.MathUtil;

public class NodeVO {
	private Long nodeDefinitionId;
	private String nodeName;
	private String nodeType;
	private Long nodeInstanceId;
	private Integer itemsNum;
	private Integer answeringTime;
	private Float  rightRateForPass;
	private Float accuracyRate;
	private Integer nodeStatus;
	private Long nodeGroupId;
	private List<NodeVO> nodeVOList;
	private Integer rowNum;
	private String nodeGroupName;
	
	private Float firstTestScore;

	private Date startTime;
	private Date endTime;
	private Integer usedTime;
		
	public Long getNodeDefinitionId() {
		return nodeDefinitionId;
	}
	public void setNodeDefinitionId(Long nodeDefinitionId) {
		this.nodeDefinitionId = nodeDefinitionId;
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
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	public Integer getItemsNum() {
		return itemsNum;
	}
	public void setItemsNum(Integer itemsNum) {
		this.itemsNum = itemsNum;
	}
	public Integer getAnsweringTime() {
		if (answeringTime == null) answeringTime = 0;
		return answeringTime;
	}
	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
	public Float getRightRateForPass() {
		return rightRateForPass;
	}
	public void setRightRateForPass(Float rightRateForPass) {
		this.rightRateForPass = rightRateForPass;
	}
	public Float getAccuracyRate() {
		return accuracyRate;
	}
	public void setAccuracyRate(Float accuracyRate) {
		this.accuracyRate = accuracyRate;
	}
	public Integer getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(Integer nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public Long getNodeGroupId() {
		return nodeGroupId;
	}
	public void setNodeGroupId(Long nodeGroupId) {
		this.nodeGroupId = nodeGroupId;
	}
	public int getRightRateForPassPercent() {
		return MathUtil.div(rightRateForPass*100, 1);
	}
	public Float getFirstTestScore() {
		return firstTestScore;
	}
	public void setFirstTestScore(Float firstTestScore) {
		this.firstTestScore = firstTestScore;
	}
	public List<NodeVO> getNodeVOList() {
		return nodeVOList;
	}
	public void setNodeVOList(List<NodeVO> nodeVOList) {
		this.nodeVOList = nodeVOList;
	}
	public Integer getRowNum() {
		return rowNum;
	}
	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}
	public String getNodeGroupName() {
		return nodeGroupName;
	}
	public void setNodeGroupName(String nodeGroupName) {
		this.nodeGroupName = nodeGroupName;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getUsedTime() {
		return usedTime;
	}
	public void setUsedTime(Integer usedTime) {
		this.usedTime = usedTime;
	}
	
}
