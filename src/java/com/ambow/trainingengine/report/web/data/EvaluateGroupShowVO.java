package com.ambow.trainingengine.report.web.data;

import java.util.ArrayList;
import java.util.List;

public class EvaluateGroupShowVO {
	private Long nodeGroupId;
	private String nodeGroupName;
	private ReportShowVO totalInfo;
	private List<ReportShowVO> knowledgePointList=new ArrayList<ReportShowVO>();
	public Long getNodeGroupId() {
		return nodeGroupId;
	}
	public void setNodeGroupId(Long nodeGroupId) {
		this.nodeGroupId = nodeGroupId;
	}
	public String getNodeGroupName() {
		return nodeGroupName;
	}
	public void setNodeGroupName(String nodeGroupName) {
		this.nodeGroupName = nodeGroupName;
	}
	public ReportShowVO getTotalInfo() {
		return totalInfo;
	}
	public void setTotalInfo(ReportShowVO totalInfo) {
		this.totalInfo = totalInfo;
	}
	public List<ReportShowVO> getKnowledgePointList() {
		return knowledgePointList;
	}
	public void setKnowledgePointList(List<ReportShowVO> knowledgePointList) {
		this.knowledgePointList = knowledgePointList;
	}
}
