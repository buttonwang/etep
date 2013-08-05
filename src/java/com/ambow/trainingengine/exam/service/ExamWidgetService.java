package com.ambow.trainingengine.exam.service;

import java.util.List;

import org.hibernate.Query;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.attention.domain.ItemAttention;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;

/*
 * ExamWidgetService.java
 * 
 * Created on 2009-5-21 上午11:12:07
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

public class ExamWidgetService {
	
	private HibernateGenericDao genService;

	public ItemAttention getItemAttention(Integer attentionId) {
		return genService.get(ItemAttention.class, attentionId);
	}
	
	public Item getItem(Integer itemId) {
		return genService.get(Item.class, itemId);
	}
		
	@SuppressWarnings("unchecked")
	public List<Item> getItems(String itemIds) {
		return (List<Item>)genService.find("from Item where id in (" + itemIds + ")");
	}
	
	@SuppressWarnings("unchecked")
	public List<Item> getItems(String itemIds, int offset, int limit) {
		Query query = genService.createQuery("from Item where id in (" + itemIds + ")")
			.setFirstResult(offset)
			.setMaxResults(limit);
		return query.list();
	}
	
	public HistoryAnswerStatus getHistoryAnswerStatus(long hisId) {
		return genService.get(HistoryAnswerStatus.class, hisId);
	}
	
	@SuppressWarnings("unchecked")
	public List<HistoryAnswerStatus> getHistoryAnswersStatus(int testId, int itemId) {
		return (List<HistoryAnswerStatus>)genService.find("from HistoryAnswerStatus h " +
				"where h.historyTestStatus.id=? and h.item.id=?", testId, itemId);
	}
	
	public void setTrainingPolicy(ViewControl viewControl, long nodeId) {
		TrainingPolicy trainingPolicy = genService.get(TrainingPolicy.class, nodeId);
		
		if (trainingPolicy!=null) {
			if (trainingPolicy.getWhenToSeeAnalysis()==null) trainingPolicy.setWhenToSeeAnalysis(1);
			if (trainingPolicy.getWhenToCheckAnswer()==null) trainingPolicy.setWhenToCheckAnswer(1);
			if (trainingPolicy.getAllowUnsureMark()==null) trainingPolicy.setAllowUnsureMark(0);
			if (trainingPolicy.getIsRandomAnswerOptions()==null) trainingPolicy.setIsRandomAnswerOptions(0);
		
			viewControl.setAnalysisPolicy(trainingPolicy.getWhenToSeeAnalysis());
			viewControl.setAnswerPolicy(trainingPolicy.getWhenToCheckAnswer());
			viewControl.setMarkPolicy(trainingPolicy.getAllowUnsureMark());
			viewControl.setRandomAnswerOptionsPolicy(trainingPolicy.getIsRandomAnswerOptions());
			viewControl.setScorePolicy(1);
		} else {
			viewControl.setAnalysisPolicy(1);
			viewControl.setAnswerPolicy(1);
			viewControl.setMarkPolicy(1);
			viewControl.setRandomAnswerOptionsPolicy(0);
			viewControl.setScorePolicy(1);
		}
	}
	
	public void setTrainingPolicyNone(ViewControl viewControl) {
		viewControl.setAnalysisPolicy(0);
		viewControl.setAnswerPolicy(0);
		viewControl.setMarkPolicy(0);
		viewControl.setRandomAnswerOptionsPolicy(0);
		viewControl.setScorePolicy(0);
	}
	
	public HistoryAnswerStatus getFirstHistoryAnswerStatus(int itemId) {
		return (HistoryAnswerStatus)genService.findObjByHql("from HistoryAnswerStatus a where a.item.id=? order by a.id", itemId);
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
}
