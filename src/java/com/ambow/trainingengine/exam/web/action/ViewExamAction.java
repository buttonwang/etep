package com.ambow.trainingengine.exam.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.service.ExamViewService;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ExamResultProperty;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/* 
 * ViewExamAction.java <br/>
 * 
 * Created on Jul 16, 2008,2:12:02 PM <br/>
 * 回顾试卷时的Action。。。
 * 根据
 *
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 *
 * Original Author: Li Xin <br/>
 * Contributor(s): 参与者的名称，参与者名称2， <br/>
 *
 * Changes  <br/> 
 * -------
 * $log$ <br/>
 */
public class ViewExamAction extends BaseAction {

	private static final long serialVersionUID = 8631221418880842188L;

	private Integer nodeInstanceId;
	
	private Integer historyTestStatusId;
	
	private ExamViewService examViewService;
	
	private Integer jumpType;
	
	private int examType;

	private HibernateGenericDao genService;
	
	private ExamItemService examItemService;
	
	private ExamAnswerService examAnswerService;

	public String execute() {
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControlProxy viewControl =  new ViewControlProxy(
			userDataVO.getProcessPolicy().getIsShowAnswer(),
			userDataVO.getProcessPolicy().getProjectVersion(),
			userDataVO.getProcessInstanceId());

		viewControl.setShowModel(3);	//3是view
		if(examType==6)	viewControl.setExtPractice(true); 	//拓展训练
		
		List<Item> itemList=new ArrayList<Item>();
		
		getProcessInfo(viewControl, userDataVO);
		getBaseInfo(userDataVO, viewControl, itemList);
				
		viewControl.generateMapForView();
		viewControl.initExamData();
		viewControl.initViewData(itemList);
				
		examItemService.setTrainPolicy(viewControl);//处理一些显示所需设置的策略
		
		this.setHttpSessionObj(SessionDict.ViewControl, viewControl);
		
		return SUCCESS;
	}

	//设置ViewControl的流程信息
	private void getProcessInfo(ViewControl viewControl, UserDataVO userDataVO) {
		ProcessInstance processInstance = 
			(ProcessInstance)genService.get(ProcessInstance.class, userDataVO.getProcessInstanceId());
		ProcessTrainingStatus processTrainingStatus = 
			(ProcessTrainingStatus)genService.get(ProcessTrainingStatus.class, userDataVO.getProcessInstanceId());
		
		viewControl.setProcessInstance(processInstance);
		viewControl.setProcessTrainingStatus(processTrainingStatus);			
	}
	
	//设置ViewControl的节点信息、答题信息、试题信息
	private void getBaseInfo(UserDataVO userDataVO, ViewControlProxy viewControl, List<Item> itemList) {
		if (viewControl.getProjectVersion().equals("ky")) {	//考研项目回顾当前节点的最后一次训练 TODO
			historyTestStatusId = examViewService.getHistoryIdByIns(nodeInstanceId);
		}
		if (historyTestStatusId == null) historyTestStatusId = 0;
		if (historyTestStatusId == 0) {
			viewControl.setCurrentTestStatusId(nodeInstanceId);
			getCurrentBaseInfo(viewControl, itemList);
		} else {
			viewControl.setCurrentTestStatusId(nodeInstanceId);
			viewControl.setHistoryTestStatusId(historyTestStatusId);
			getHistoryBaseInfo(viewControl, itemList);
		}
	}

	private void getCurrentBaseInfo(ViewControlProxy viewControl, List<Item> itemList) {
		CurrentTestStatus cTestStatus = genService.get(CurrentTestStatus.class, (long)nodeInstanceId);
	
		viewControl.setExamNodeIns(cTestStatus.getAsfNodeInstance());
		viewControl.getNodeInstances().add(cTestStatus.getAsfNodeInstance());
				
		Map<String,CurrentAnswersStatus> cAnswerStatusMap=new HashMap<String,CurrentAnswersStatus>();		
		
		cAnswerStatusMap = examAnswerService.getCurrentAnswerMap(viewControl.getNodeInstances());
		
		for(CurrentAnswersStatus answerStatus: cAnswerStatusMap.values()) {
			if (viewControl.getProjectVersion().equals("ky")) {
				itemList.add(answerStatus.getItem());
			} else if (viewControl.getProjectVersion().equals("mpc")) {
				if (answerStatus.getSubItem()==null) itemList.add(answerStatus.getItem());
			} else {
				itemList.add(answerStatus.getItem());
			}
		}
		viewControl.setCurrentAnswersStatus(cAnswerStatusMap);
				
		//答卷结果
		ExamResultProperty examResultProperty = new ExamResultProperty(); 		
		examResultProperty.setExamScore(cTestStatus.getScore());
		examResultProperty.setAccuracyRate(cTestStatus.getAccuracyRate());
		examResultProperty.setErrorCount(cTestStatus.getSumIncorrectItems());
		examResultProperty.setUndoCount(cTestStatus.getSumUnfinishedItems());
		examResultProperty.setMarkCount(cTestStatus.getUnsureMarkItems());
		examResultProperty.setSpendTime(cTestStatus.getTimeUsedTotal());
		viewControl.setExamResultProperty(examResultProperty);
	}
	
	private void getHistoryBaseInfo(ViewControlProxy viewControl, List<Item> itemList) {
		HistoryTestStatus hTestStatus=genService.get(HistoryTestStatus.class, historyTestStatusId);
			
		viewControl.setExamNodeIns(hTestStatus.getAsfNodeInstance());
		viewControl.getNodeInstances().add(hTestStatus.getAsfNodeInstance());
				
		Map<String,HistoryAnswerStatus> hAnswerStatusMap=new HashMap<String,HistoryAnswerStatus>();		
		String mapKey=null;		
		List<HistoryAnswerStatus> hAnswerStatusList=hTestStatus.getHistoryAnswerStatuses();
		
		for(HistoryAnswerStatus answerStatus:hAnswerStatusList) {
			mapKey=ExamUtil.getMapKey(answerStatus.getItem(), answerStatus.getSubItem());
			hAnswerStatusMap.put(mapKey, answerStatus);			
			
			if (viewControl.getProjectVersion().equals("ky")) {
				if (itemList.contains(answerStatus.getItem())==false) itemList.add(answerStatus.getItem());
			} else if (viewControl.getProjectVersion().equals("mpc")) {
				if (answerStatus.getSubItem()==null) itemList.add(answerStatus.getItem());
			} else {
				itemList.add(answerStatus.getItem());
			}
		}
		viewControl.setHistoryStatusMap(hAnswerStatusMap);
		
		
		//答卷结果
		ExamResultProperty examResultProperty = new ExamResultProperty(); 
		examResultProperty.setExamScore(hTestStatus.getScore());
		examResultProperty.setAccuracyRate(hTestStatus.getAccuracyRate());
		examResultProperty.setErrorCount(hTestStatus.getSumIncorrectItems());
		examResultProperty.setUndoCount(hTestStatus.getSumUnfinishedItems());
		examResultProperty.setMarkCount(hTestStatus.getUnsureMarkItems());
		examResultProperty.setSpendTime(hTestStatus.getTimeUsed());
		viewControl.setExamResultProperty(examResultProperty);
	}

	@Override
	public String getAuthStr() {
		return null;
	}

	public Integer getHistoryTestStatusId() {
		return historyTestStatusId;
	}

	public void setHistoryTestStatusId(Integer historyTestStatusId) {
		this.historyTestStatusId = historyTestStatusId;
	}

	public ExamViewService getExamViewService() {
		return examViewService;
	}

	public void setExamViewService(ExamViewService examViewService) {
		this.examViewService = examViewService;
	}

	public Integer getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(Integer nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}

	public Integer getJumpType() {
		return jumpType;
	}

	public void setJumpType(Integer jumpType) {
		this.jumpType = jumpType;
	}
	
	public int getExamType() {
		return examType;
	}

	public void setExamType(int examType) {
		this.examType = examType;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public ExamItemService getExamItemService() {
		return examItemService;
	}

	public void setExamItemService(ExamItemService examItemService) {
		this.examItemService = examItemService;
	}
	
	public ExamAnswerService getExamAnswerService() {
		return examAnswerService;
	}

	public void setExamAnswerService(ExamAnswerService examAnswerService) {
		this.examAnswerService = examAnswerService;
	}

}
