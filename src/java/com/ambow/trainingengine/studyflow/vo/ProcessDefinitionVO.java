package com.ambow.trainingengine.studyflow.vo;

import com.ambow.studyflow.domain.ProcessDefinition;

public class ProcessDefinitionVO extends ProcessDefinition{
	private static final long serialVersionUID = 8370813136932657325L;
	public ProcessDefinitionVO (ProcessDefinition pr){
		if(pr!=null){
			this.setId(pr.getId());
			this.setName(pr.getName());
			this.setDescription(pr.getDescription());
			this.setStartNode(pr.getStartNode());
			this.setDefVersion(pr.getDefVersion());
			this.setNodes(pr.getNodes());
			this.setCategoryId(pr.getCategoryId());
			this.setCreator(pr.getCreator());
			this.setCreateTime(pr.getCreateTime()); 
			this.setUpdator(pr.getUpdator());
			this.setUpdateTime(pr.getUpdateTime());
			this.setReleaseStatus(pr.getReleaseStatus());
		}
	}
	public String categoryName;
	public String processStatusStr;
	public String getCategoryName() {
		return categoryName;
	}
	
	public String getProcessStatusStr() {
		return processStatusStr;
	}

	public void setProcessStatusStr(String processStatusStr) {
		this.processStatusStr = processStatusStr;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
