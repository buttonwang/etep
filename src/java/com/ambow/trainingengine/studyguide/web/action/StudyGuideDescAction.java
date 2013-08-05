package com.ambow.trainingengine.studyguide.web.action;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.trainingengine.studyguide.service.ShowStudyGuideService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ShowStudyGuideAction.java
 * 
 * Created on 2009-8-3 下午05:44:27
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class StudyGuideDescAction extends BaseAction {

	 
	private static final long serialVersionUID = -4463970604758651732L;
	private ShowStudyGuideService showStudyGuideService;
	private HibernateGenericDao genService;
	private Integer nodeId=0;
	private Long nodeGroupId;
	private String nodeName;
	private String itemIds;
	private String kpCode=null;
	private Integer phaseTest=0;
	/**
	 * 节点详细描述
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		Map<String,Object> pageMap=this.showStudyGuideService.getTreePage(processDefinitionId, nodeId, nodeGroupId);
		pageMap.put("nodeId", nodeId);
		pageMap.put("nodeGroupId", nodeGroupId);
		this.setRequestAttribute("pageMap", pageMap);
		this.setSessionObj("pageMap", pageMap);
		 
		ProcessDefinition processDefinition = (ProcessDefinition)genService.get(ProcessDefinition.class, processDefinitionId);

	    //treefloor=2 为二级结点，treefloor=3 叶子节点
		int treefloor=(Integer)pageMap.get("treefloor");
	    if(treefloor==2){
	    	this.setRequestAttribute("sections", this.showStudyGuideService.getSections(processDefinitionId,nodeId));
	    	Map<String,Object>  map=this.showStudyGuideService.getChapterDesc(nodeId,processDefinitionId,nodeGroupId,"EVALUATE");
	    	List<Map<String,Object>> list=(List)map.get("thisknow");
	    	this.setRequestAttribute("mapList", list);				

	    	return "chapter";
	    }else{
	    	String str="%";
	    	if(phaseTest==1){
	    		str=kpCode;
	    	}
	    	Map<String,Object>  map=this.showStudyGuideService.getChapterDesc(nodeId,processDefinitionId,nodeGroupId,str);
	    	List<Map<String,Object>> list=(List)map.get("thisknow");
	    	List listTmp=(List)map.get("other");
	    	this.setRequestAttribute("other",listTmp );	
	    	this.setRequestAttribute("mapList",  list);
		 	this.setSessionObj("studyGuideKpcodes", getKpcodes( list));
	    	return "section";
	    }
	}
	
	public String getLinkPage() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		List<Map<String,Object>> list=this.showStudyGuideService.getTree(nodeId, processDefinitionId);
		this.setRequestAttribute("list", list);
		return "linkpage";
	}
	
	//为流程树为两层做的特例。
	public String getChapter2() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		String kpCodes = this.showStudyGuideService.getkpCodes(processDefinitionId);
		
		List<Map<String,Object>> studyGuideList = this.showStudyGuideService.getParentStudyGuide(kpCodes);
		this.setRequestAttribute("studyGuideList", studyGuideList);
		
		List<Map<String,Object>> kpList = this.showStudyGuideService.getKnowlegerPointByCode(kpCodes);
		this.setRequestAttribute("kpList", kpList);
		
		return "chapter2";
	}
	
	//通过知识点取学习指导
	public String getStudyGuide() {
		kpCode = kpCode.replaceAll("'", "");
		List<Map<String,Object>> studyGuideList = this.showStudyGuideService.getStudyGuide(kpCode);
		this.setRequestAttribute("studyGuideList", studyGuideList);
		
		this.setSessionObj("studyGuideKpcodes", "'"+kpCode+"'");
		return "section2";
	}
	
	public String typicalExample() {
		this.setRequestAttribute("pageMap", this.getSessionObj("pageMap"));
		String kpCodes = (String)this.getSessionObj("studyGuideKpcodes");
		if (kpCodes.equals("")) return "typicalExampleNone";
		this.itemIds  = showStudyGuideService.getItemIdsByKpCodes(kpCodes);
		return (itemIds.equals(""))? "typicalExampleNone":"typicalExample";
	}
	
	public String studyGuideForExam() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		Map<String,Object> map=this.showStudyGuideService.getChapterDesc(nodeId,processDefinitionId,nodeGroupId,"%");
		List<Map<String,Object>> list=(List)map.get("thisknow");
	 	this.setRequestAttribute("mapList", list);
	 	return "studyGuideExam";
	}
	
	public String typicalExample2() {
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		Map<String,Object> pageMap=this.showStudyGuideService.getTreePage(processDefinitionId, nodeId, nodeGroupId);
		pageMap.put("nodeId", nodeId);
		pageMap.put("nodeGroupId", nodeGroupId);
		this.setRequestAttribute("pageMap", pageMap);
		this.setSessionObj("pageMap", pageMap);
		 
		ProcessDefinition processDefinition = (ProcessDefinition)genService.get(ProcessDefinition.class, processDefinitionId);


	    //treefloor=2 为二级结点，treefloor=3 叶子节点
		int treefloor=(Integer)pageMap.get("treefloor");
	    if(treefloor==2){
	    	this.setRequestAttribute("sections", this.showStudyGuideService.getSections(processDefinitionId,nodeId));
	    	Map<String,Object> map=this.showStudyGuideService.getChapterDesc(nodeId,processDefinitionId,nodeGroupId,"EVALUATE");
	    	List<Map<String,Object>> list=(List)map.get("thisknow");
	    	this.setRequestAttribute("mapList", list);				

	    	return "chapter";
	    }else{
	    	Map<String,Object> map=this.showStudyGuideService.getChapterDesc(nodeId,processDefinitionId,nodeGroupId,"%");
	    	
	    	List<Map<String,Object>> list=(List)map.get("thisknow");
	    	this.setRequestAttribute("mapList", list);	
		 	this.setSessionObj("studyGuideKpcodes", getKpcodes(list));
	    	return typicalExample();
	    }
	}
	
	@SuppressWarnings("unchecked")
	private String getKpcodes(List list) {
		String r = "";
		if(list!=null){
			for (Map<String,Object> map: (List<Map<String,Object>>)list) {
				r += "'" + map.get("kp_code") + "'" + ",";
			}
			r = r.replaceFirst(",$", "");
			return r;		
		}else{
			return "";
		}
	}
	
	@Override
	public String getAuthStr() {
		return null;
	}
	public ShowStudyGuideService getShowStudyGuideService() {
		return showStudyGuideService;
	}
	public void setShowStudyGuideService(ShowStudyGuideService showStudyGuideService) {
		this.showStudyGuideService = showStudyGuideService;
	}
	public Integer getNodeId() {
		return nodeId;
	}
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}
	public Long getNodeGroupId() {
		return nodeGroupId;
	}
	public void setNodeGroupId(Long nodeGroupId) {
		this.nodeGroupId = nodeGroupId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getItemIds() {
		return itemIds;
	}
	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public Integer getPhaseTest() {
		return phaseTest;
	}

	public void setPhaseTest(Integer phaseTest) {
		this.phaseTest = phaseTest;
	}

	public String getKpCode() {
		return kpCode;
	}

	public void setKpCode(String kpCode) {
		this.kpCode = kpCode;
	}

 
	
}
