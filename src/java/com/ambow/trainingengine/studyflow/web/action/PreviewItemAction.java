package com.ambow.trainingengine.studyflow.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.web.data.QueryConditions;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;

/*
 * PreviewItemAction.java
 * 
 * Created on 2008-8-29 下午01:55:00
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
@SuppressWarnings("serial")
public class PreviewItemAction extends BaseAction {

	private HibernateGenericDao genService;
	
	private int pageNo = 1;
	private long nodeId = 0;//节点编号
	private int reqId = 0;  //组卷条件编号
	private int reqIndex = 0;  //组卷条件编号
	private String info=""; //组卷信息
	private final String splitflag = "-";

	@SuppressWarnings("unchecked")
	@Override
	public String execute() {
		Set<Item> items = new HashSet<Item>(0);
		List<PaperAssemblingRequirements> requirementsList = null;
		int requirementsIndex = 1;
		if (reqIndex>0) requirementsIndex = reqIndex;
		
		if (nodeId > 0)
			requirementsList = genService.find("from PaperAssemblingRequirements p where p.asfNode.id = ?", nodeId);
		else
			requirementsList = genService.find("from PaperAssemblingRequirements p where p.id = ?", reqId);
		
		/*所有符合条件的题目总数*/
		int countItem = 0;
		
		for(PaperAssemblingRequirements requirements: requirementsList){
		 
			QueryConditions queryConditions = setItemQueryConditions(requirements);
			queryConditions.genHQLandValues("1");
			queryConditions.setStatusRang("1");
			queryConditions.setOrderby("rand()");
			Query query = genService.createQuery(queryConditions.hql, queryConditions.queryConditionList.toArray());
			//if (requirements.getAmount()!=null) query.setMaxResults(requirements.getAmount());			
			List<Item> reqItems = query.list();
			this.setRequestAttribute("countBigItem", reqItems.size());
			countItem += reqItems.size();
			int itemamount = 0;
			if (requirements.getAmount()==null) {
				itemamount = reqItems.size();
				info += genReqInfo(requirementsIndex, itemamount);
			} else {
				itemamount = requirements.getAmount();
				info += genReqInfo(requirementsIndex, itemamount, reqItems.size()>=itemamount?itemamount: reqItems.size());
			}
			for(Item item: reqItems){
				if (!items.contains(item)) {items.add(item); itemamount--; }
				if (itemamount==0) break;
			}
			requirementsIndex++;
		}
		
		//int startIndex = Page.getStartOfPage(pageNo, Constants.DEFAULT_PAGE_SIZE);
		//Page page = new Page(startIndex, items.size(), Constants.DEFAULT_PAGE_SIZE, items);
		Page page = new Page(0, items.size(), items.size(), items);
		
		this.setRequestAttribute("page", page);
		this.setRequestAttribute("countItem", countItem);
		
		return "list";
	}
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	private QueryConditions setItemQueryConditions(PaperAssemblingRequirements requirements) {
		QueryConditions queryConditions = new QueryConditions();
		
		if(requirements.getApplicableObject()!=null){
			queryConditions.setApplicableObjects(requirements.getApplicableObject().split(","));
		}
		if(requirements.getCourseVersions()!=null){
			queryConditions.setCourseVersions(requirements.getCourseVersions().split(","));
		}
		if (requirements.getYear()!=null&&!requirements.getYear().equals("")) {
			String tmpyear = requirements.getYear().trim();
			if (!tmpyear.contains(splitflag)) queryConditions.setYear(tmpyear);
			else {
				String[] years = tmpyear.split(splitflag);
				if (years.length>0) {
					if (tmpyear.startsWith(splitflag)) queryConditions.setYearUp(years[1]);
					else if (tmpyear.endsWith(splitflag)) queryConditions.setYearDown(years[0]);
					else {
						queryConditions.setYearDown(years[0]);
						queryConditions.setYearUp(years[1]);					
					}
				}	
			}
		}
		
		if (requirements.getValidityValue()!=null&&!requirements.getValidityValue().equals("")) {
			String tmpvalidityvalue = requirements.getValidityValue();
			if (!tmpvalidityvalue.contains(splitflag)) queryConditions.setValidityValue(tmpvalidityvalue);
			else {
				String[] validityvalues = tmpvalidityvalue.split(splitflag);
				if (validityvalues.length>0) {
					if (tmpvalidityvalue.startsWith(splitflag)) queryConditions.setValidityValueUp(validityvalues[1]);
					else if (tmpvalidityvalue.endsWith(splitflag)) queryConditions.setValidityValueDown(validityvalues[0]);
					else {
						queryConditions.setValidityValueDown(validityvalues[0]);
						queryConditions.setValidityValueUp(validityvalues[1]);	
					}
				}	
			}
		}
		
		if (requirements.getDifficultyValue()!=null&&!requirements.getDifficultyValue().equals("")) {
			String tmpdifficultyvalue = requirements.getDifficultyValue();
			if (!tmpdifficultyvalue.contains(splitflag)) queryConditions.setDifficultyValue(tmpdifficultyvalue);
			else {
				String[] difficultyvalues = tmpdifficultyvalue.split(splitflag);
				if (difficultyvalues.length>0) {
					if (tmpdifficultyvalue.startsWith(splitflag)) queryConditions.setDifficultyValueUp(difficultyvalues[1]);
					else if (tmpdifficultyvalue.endsWith(splitflag)) queryConditions.setDifficultyValueDown(difficultyvalues[0]);
					else {
						queryConditions.setDifficultyValueDown(difficultyvalues[0]);
						queryConditions.setDifficultyValueUp(difficultyvalues[1]);	
					}
				}	
			}
		}
		
		if (requirements.getOpinion()!=null&&!requirements.getOpinion().equals("")) {
			String tmpOpinion = requirements.getOpinion();
			if (!tmpOpinion.contains(splitflag)) queryConditions.setOpinion(tmpOpinion);
			else {
				String[] opinions = tmpOpinion.split(splitflag);
				if (opinions.length>0) {
					if (tmpOpinion.startsWith(splitflag)) queryConditions.setOpinionUp(opinions[1]);
					else if (tmpOpinion.endsWith(splitflag)) queryConditions.setOpinionDown(opinions[0]);
					else {
						queryConditions.setOpinionDown(opinions[0]);
						queryConditions.setOpinionUp(opinions[1]);	
					}
				}	
			}
		}
		
		queryConditions.setItemType(requirements.getItemTypeCode()==null?"":requirements.getItemTypeCode());
		queryConditions.setSource(requirements.getSource()==null?"":requirements.getSource());
		queryConditions.setOriginalPaperCode(requirements.getOriginalPaperCode()==null?"":requirements.getOriginalPaperCode());
		queryConditions.setRegion(requirements.getRegionCode()==null?"":requirements.getRegionCode());
		queryConditions.setSubject(requirements.getSubjectCode()==null?"":requirements.getSubjectCode());
		queryConditions.setGrade(requirements.getGradeCode()==null?"":requirements.getGradeCode());		
		queryConditions.setKnowledgePoint(requirements.getKnowledgePointCode()==null?"":requirements.getKnowledgePointCode());
		queryConditions.setAmount(requirements.getAmount());
		queryConditions.setReviewRound(requirements.getReviewRound()==null?"":requirements.getReviewRound());
		return queryConditions;
	}
	
	private String genReqInfo(int rIndex, int reqAct) {
		StringBuilder sb = new StringBuilder();
		sb.append("组卷条件").append(rIndex);
		sb.append("无组卷题数的限制");
		sb.append("  实际组卷题数为:").append(reqAct).append(";");
		return sb.toString();
	}

	private String genReqInfo(int rIndex, int reqAmount, int reqAct) {
		StringBuilder sb = new StringBuilder();
		sb.append("组卷条件").append(rIndex);
		sb.append("  需要题数为:").append(reqAmount);
		sb.append("  实际组卷题数为:").append(reqAct).append(";");
		return sb.toString();
	}
	
	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}
	
	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public int getReqId() {
		return reqId;
	}
	
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}

	public int getReqIndex() {
		return reqIndex;
	}
	
	public void setReqIndex(int reqIndex) {
		this.reqIndex = reqIndex;
	}
	
}
