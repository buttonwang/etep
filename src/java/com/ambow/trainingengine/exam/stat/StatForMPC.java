package com.ambow.trainingengine.exam.stat;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ExamResultProperty;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.itembank.domain.ItemReviseAnswers;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.util.CalculateUtil;

/*
 * StatFoyMPC.java
 * 
 * Created on 2009-2-5 下午06:47:41
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

public class StatForMPC extends StatBase implements IStat {

	private HibernateGenericDao genService;
	private ExamItemService examItemService;
	
	public void saveAnswers(ViewControl viewControl) {
		viewControl.setEndTime(new Date());
		
		Map<CurrentAnswersStatus,Integer> currentMap = new HashMap<CurrentAnswersStatus,Integer>();
		
		prepareAnswersMap(viewControl, currentMap); //准备试题数据
		
		examScore(viewControl); //打分、星级 
		
		saveAllCurrentTestStatus(viewControl, currentMap); //保存当前答卷答题信息
		
		saveAllHistoryTestStatus(viewControl, currentMap);	//保存历史答卷答题信息 并 统计当前考试所需要的一些数据				
		
		savePoint(viewControl);		//保存积分及更新用户积分表
	}

	@Override
	public void saveReviseAnswers(ViewControl viewControl) {
		Map<CurrentAnswersStatus,Integer> currentMap = new HashMap<CurrentAnswersStatus,Integer>();
		
		prepareAnswersMap(viewControl, currentMap); //准备试题数据
		
		examScore(viewControl); //打分、星级 
		
		saveItemRevise(viewControl, currentMap);
	}
	
	/**
	 *根据考试结果保存知识点 
	 */
	public void statKPoint(ViewControl viewControl)	{		
		Map<String,CurrentAnswersStatus> answerStatusMap = viewControl.getPreAnswersStatus();		
		List<Item> paperItems  = viewControl.getItems();
		List<Item> listItems = new ArrayList<Item>();
		for (Item item: paperItems) {
			Item tmpItem = (Item)genService.get(Item.class, item.getId());
			listItems.add(tmpItem);
		}
		
		Map<KnowledgePoint,Float> totalScoreMap = new HashMap<KnowledgePoint,Float>();
		Map<KnowledgePoint,Float> cuScoreMap	= new HashMap<KnowledgePoint,Float>();
		CurrentAnswersStatus currentStatus = null;
		
		for (Item item: listItems) {
			if (ExamUtil.hasSubItem(item)) {
				for(SubItem subItem:item.getSubItems()){
					String mapKey=ExamUtil.getMapKey(item, subItem);
					currentStatus=answerStatusMap.get(mapKey);
					Float subScore  = subItem.getScore();
					Float subKvalue = subScore/(float)subItem.getKnowledgePoints().size();
					Float subUKvalue= null;
					if(currentStatus.getScore()!=null&&currentStatus.getScore()>0)
						subUKvalue = currentStatus.getScore()/(float)subItem.getKnowledgePoints().size();
					for(KnowledgePoint kPoint: subItem.getKnowledgePoints()){
						Float f1=totalScoreMap.get(kPoint);
						if(f1==null) f1=subKvalue;
						else  		 f1=f1+subKvalue;
						totalScoreMap.put(kPoint, f1);
						
						if(subUKvalue!=null) {
							Float f2=cuScoreMap.get(kPoint);
							if(f2==null) f2=subUKvalue;
							else  		 f2=f2+subUKvalue;
							cuScoreMap.put(kPoint, f2);
						}
					}
				}
			} else {
				String mapKey=ExamUtil.getMapKey(item, null);
				currentStatus=answerStatusMap.get(mapKey);
				Float subKvalue=item.getScore()/(float)item.getKnowledgePoints().size();				
				Float subUKvalue=null;
				if(currentStatus.getScore()!=null&&currentStatus.getScore()>0)
					subUKvalue=currentStatus.getScore()/(float)item.getKnowledgePoints().size();
				for(KnowledgePoint kPoint:item.getKnowledgePoints()){
					Float f1=totalScoreMap.get(kPoint);
					if(f1==null) f1=subKvalue;
					else		 f1=f1+subKvalue;
					totalScoreMap.put(kPoint, f1);
					
					if(subUKvalue!=null){
						Float f2=cuScoreMap.get(kPoint);
						if(f2==null) f2=subUKvalue;
						else		 f2=f2+subUKvalue;
						cuScoreMap.put(kPoint, f2);
					}					
				}				
			}
		}
		//遍历完毕两个map中内容应该是一样的
		Set<KnowledgePoint> kPoints=totalScoreMap.keySet();
		Set<EvaluatingAnswerStatus> eAnswerStatusSet = new HashSet<EvaluatingAnswerStatus>();
		EvaluatingAnswerStatus eAnswerStatus=null;
		NodeInstance asfNodeInstance=viewControl.getExamNodeIns();
		
		for(KnowledgePoint kPoint:kPoints){
			Float f1=totalScoreMap.get(kPoint);
			Float f2=cuScoreMap.get(kPoint);
			if(f2==null) f2=0f;
			Float f3=f2/f1;
			f3 = ExamUtil.roundFloat(f3);
						
			eAnswerStatus=new EvaluatingAnswerStatus();
			eAnswerStatus.setAsfNodeInstance(asfNodeInstance);
			eAnswerStatus.setKnowledgePoint(kPoint);			
			eAnswerStatus.setTotalScore(f1);
			eAnswerStatus.setScore(f2);
			eAnswerStatus.setRightRate(f3);
			eAnswerStatusSet.add(eAnswerStatus);
		}
		
		//保存之前删除老的评测记录。。只有一次
		String hql="delete from EvaluatingAnswerStatus eStatus where eStatus.asfNodeInstance.id="+asfNodeInstance.getId();
		this.genService.excuteHql(hql);
		this.genService.saveOrUpdateAll(eAnswerStatusSet);
		viewControl.getExamResultProperty().setEvaluatingAnswerStatus(eAnswerStatusSet);
		
		Set<EvaluatingAnswerStatus> preEvaluatingAnswerStatus = getPreEvaluatingAnswerStatus(viewControl, asfNodeInstance);
		
		//有前测
		if (preEvaluatingAnswerStatus!=null) {
			Set<EvaluatingAnswerStatus> allEvaluatingAnswerStatus = new HashSet<EvaluatingAnswerStatus>();
			Set<KnowledgePoint> preKPoints = new HashSet<KnowledgePoint>();
			
			for (EvaluatingAnswerStatus preAnswer: preEvaluatingAnswerStatus) {
				Float f1=totalScoreMap.get(preAnswer.getKnowledgePoint());
				if(f1==null) f1=0f;
				if (f1!=0f) {
					Float f2=cuScoreMap.get(preAnswer.getKnowledgePoint());
					if(f2==null) f2=0f;
					Float f3=f2/f1;
					f3 = ExamUtil.roundFloat(f3);
					
					preAnswer.setTotalScore2(f1);
					preAnswer.setScore2(f2);
					preAnswer.setRightRate(f3);
				}
				preKPoints.add(preAnswer.getKnowledgePoint());
			}
			
			for (EvaluatingAnswerStatus lastAnswer: eAnswerStatusSet) {
				if (!preKPoints.contains(lastAnswer.getKnowledgePoint())) {
					eAnswerStatus=new EvaluatingAnswerStatus();
					eAnswerStatus.setAsfNodeInstance(asfNodeInstance);
					eAnswerStatus.setKnowledgePoint(lastAnswer.getKnowledgePoint());			
					eAnswerStatus.setTotalScore2(lastAnswer.getTotalScore());
					eAnswerStatus.setScore2(lastAnswer.getScore());
					eAnswerStatus.setRightRate2(lastAnswer.getRightRate());
					allEvaluatingAnswerStatus.add(eAnswerStatus);
				}
			}
			
			allEvaluatingAnswerStatus.addAll(preEvaluatingAnswerStatus);
			viewControl.getExamResultProperty().setAllEvaluatingAnswerStatus(allEvaluatingAnswerStatus);
		}
		
	}

	

	/**
	 * 保存当前答题答卷信息
	 */
	private void saveAllCurrentTestStatus(ViewControl viewControl, Map<CurrentAnswersStatus, Integer> currentMap) {	
		for(NodeInstance nodeInstance:  viewControl.getNodeInstances()) {
			if (hasItems(viewControl, nodeInstance))
				saveCurrentTestStatus(viewControl, nodeInstance, currentMap);			
		}
	}
		

	/**
	 * 保存历史答题答卷信息
	 */
	private void saveAllHistoryTestStatus(ViewControl viewControl, Map<CurrentAnswersStatus, Integer> currentMap) {	
		for(NodeInstance nodeInstance:  viewControl.getNodeInstances()) {
			if (hasItems(currentMap, nodeInstance))
				saveHistoryTestStatus(viewControl, nodeInstance, currentMap);			
		}
	}
	
	/**
	 * 保存积分
	 */
	private void savePoint(ViewControl viewControl) {
		float diligence = 0f;
		float wisdom = 0f;
		
		String remark = "节点信息：" + viewControl.getFlowName() + "-" + viewControl.getChapterName() + "-" +
									  viewControl.getSectionName() + "-" + viewControl.getExamName() +
						"	任务名称：" + viewControl.getExamTask() + 
						"	任务性质：" + viewControl.getExamType();
		
		for(Page page: viewControl.getPageList()){
			for(Item item: page.getItems()) {
				diligence += item.getExamProperty().getDiligence();
				wisdom += item.getExamProperty().getWisdom();				
			}
		}
		
		MembershipPointHistory membershipPointOfDiligence = new MembershipPointHistory();
		membershipPointOfDiligence.setAsfProcessInstance(viewControl.getProcessInstance());
		membershipPointOfDiligence.setPointType(1);
		membershipPointOfDiligence.setPoint(ExamUtil.ceilInteger(diligence));
		membershipPointOfDiligence.setPointCause(ExamUtil.pointCauseForExam);
		membershipPointOfDiligence.setRemark(remark);
		membershipPointOfDiligence.setOperateTime(new Date());
		
		MembershipPointHistory membershipPointOfWisdom = new MembershipPointHistory();
		membershipPointOfWisdom.setAsfProcessInstance(viewControl.getProcessInstance());
		membershipPointOfWisdom.setPointType(2);
		membershipPointOfWisdom.setPoint(ExamUtil.ceilInteger(wisdom));
		membershipPointOfWisdom.setPointCause(ExamUtil.pointCauseForExam);
		membershipPointOfWisdom.setRemark(remark);
		membershipPointOfWisdom.setOperateTime(new Date());
		
		MembershipPoint membershipPoint = genService.get(MembershipPoint.class, viewControl.getProcessInstanceId());
		if (membershipPoint==null) {
			membershipPoint = new MembershipPoint();
			membershipPoint.setAsfProcessInstance(viewControl.getProcessInstance());
		}
		
		diligence += (membershipPoint.getDiligence()==null)?0f:membershipPoint.getDiligence();
		wisdom += (membershipPoint.getWisdom()==null)?0f:membershipPoint.getWisdom();		
		membershipPoint.setDiligence(ExamUtil.ceilInteger(diligence));
		membershipPoint.setWisdom(ExamUtil.ceilInteger(wisdom));		
		
		Session session=genService.getTemplate().getSessionFactory().openSession();
		Transaction transaction= session.beginTransaction();
		try {
			session.save(membershipPointOfDiligence);
			session.save(membershipPointOfWisdom);
			session.saveOrUpdate(membershipPoint);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}				
	}
	
	/** 
	 * 保存历史答题答卷信息， 只保存本次答题答卷信息，不是保存全部
	 * 与HistoryAnswer一起存
	 */
	private void saveHistoryTestStatus(ViewControl viewControl, NodeInstance nodeInstance, Map<CurrentAnswersStatus, Integer> currentMap) {
		
		HistoryTestStatus historyTestStatus=new HistoryTestStatus();
		historyTestStatus.setId(null);
		historyTestStatus.setAsfNodeInstance(nodeInstance);
		historyTestStatus.setStartTime(viewControl.getStartTime());
		historyTestStatus.setEndTime(viewControl.getEndTime());
		historyTestStatus.setTimeUsed(viewControl.getExamTime()-viewControl.getActualTime());
		historyTestStatus.setPaperAssemItemType(viewControl.getItemType());
		historyTestStatus.setTestStatus(viewControl.getTestStatus());
		historyTestStatus.setIsTest(viewControl.getRedoType()>0);
		historyTestStatus.setRequireAccuracyRate(viewControl.getRequireRightRate()*1f);

		int itemCount=0;
		int rightCount=0;
		int undoCount=0;
		int errorCount=0;
		int markCount=0;//疑问标记数
		
		int zeroStarCount=0;
		int halfStarCount=0;
		int oneStarCount=0;
		int twoStarCount=0;
		int threeStarCount=0;
		int fourStarCount=0;
		int fiveStarCount=0;
		
		float totalItemScore=0f;//题目总分
		float examScore=0f;//用户总得分
		
		Set<CurrentAnswersStatus> currentAnswersStatusMap = new HashSet<CurrentAnswersStatus>();
		
		for (CurrentAnswersStatus currentAnswersStatus: currentMap.keySet()) {
			if (currentAnswersStatus.getAsfNodeInstance().equals(nodeInstance))
				currentAnswersStatusMap.add(currentAnswersStatus);
		}
		
		for(CurrentAnswersStatus currentAnswersStatus: currentAnswersStatusMap ) {
			HistoryAnswerStatus historyAnswerStatus = new HistoryAnswerStatus();
		
			try {
				BeanUtils.copyProperties(historyAnswerStatus, currentAnswersStatus);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			historyAnswerStatus.setId(null);
			historyAnswerStatus.setHistoryTestStatus(historyTestStatus);
			historyTestStatus.getHistoryAnswerStatuses().add(historyAnswerStatus);
			
			//答卷信息计数 ，历史答题信息从当前答题信息中来
			if (currentAnswersStatus.getSubItem()==null) {
				itemCount++;
				if(currentAnswersStatus.isDone()==false) 	   undoCount=undoCount + 1;
				if(currentAnswersStatus.getIsCorrect()==true)  rightCount=rightCount + 1;				
				if(currentAnswersStatus.getIsCorrect()==false) errorCount=errorCount+1;
				if(currentAnswersStatus.getIsUnsureMarking())  markCount=markCount+1;
										
				if(currentAnswersStatus.getStarGrade()==0f)		zeroStarCount=zeroStarCount+1;
				if(currentAnswersStatus.getStarGrade()==0.5f)	halfStarCount=halfStarCount+1;
				if(currentAnswersStatus.getStarGrade()==1f)		oneStarCount=oneStarCount+1;
				if(currentAnswersStatus.getStarGrade()==2f)		twoStarCount=twoStarCount+1;
				if(currentAnswersStatus.getStarGrade()==3f)		threeStarCount=threeStarCount+1;
				if(currentAnswersStatus.getStarGrade()==4f)		fourStarCount=fourStarCount+1;
				if(currentAnswersStatus.getStarGrade()==5f)		fiveStarCount=fiveStarCount+1;
				
				totalItemScore = totalItemScore + currentAnswersStatus.getItem().getScore();
				examScore	   = examScore+currentAnswersStatus.getScore(); //大题可以不正确但是有分值
			}
			
		}
		
		historyTestStatus.setSumUnfinishedItems(undoCount);
		historyTestStatus.setSumCorrectItems(rightCount);
		historyTestStatus.setSumIncorrectItems(errorCount);
		historyTestStatus.setUnsureMarkItems(markCount);
				
		historyTestStatus.setSumZeroStarItems(zeroStarCount);
		historyTestStatus.setSumOneStarItems(oneStarCount);
		historyTestStatus.setSumHalfStarItems(halfStarCount);
		historyTestStatus.setSumTwoStarItems(twoStarCount);
		historyTestStatus.setSumThreeStarItems(threeStarCount);
		historyTestStatus.setSumFourStarItems(fourStarCount);
		historyTestStatus.setSumFiveStarItems(fiveStarCount);
		
		historyTestStatus.setTotalScore(Float.valueOf(totalItemScore).intValue());
		historyTestStatus.setScore(examScore);
		
		//计算正确率,掌握度
		float accuracyRate=examScore/totalItemScore*100;
		Float accuracyRateRount=ExamUtil.roundFloat(accuracyRate);
		int totalNum=zeroStarCount+halfStarCount+oneStarCount+twoStarCount+threeStarCount+fourStarCount+fiveStarCount;
		float masteryRate=CalculateUtil.masteryRate(zeroStarCount,halfStarCount,oneStarCount,twoStarCount,threeStarCount,
				fourStarCount,fiveStarCount, totalNum);
		historyTestStatus.setAccuracyRate(accuracyRateRount);
		historyTestStatus.setMasteryRate(masteryRate);
		
		genService.save(historyTestStatus);
		
		// 多节点累加
		ExamResultProperty examResultProperty; 
		if (viewControl.getExamResultProperty()==null)
			examResultProperty = new ExamResultProperty();
		else 
			examResultProperty = viewControl.getExamResultProperty();
		
		itemCount += examResultProperty.getItemCount();
		rightCount += examResultProperty.getRightCount();
		errorCount += examResultProperty.getErrorCount();
		undoCount += examResultProperty.getUndoCount();
		markCount += examResultProperty.getMarkCount();
		zeroStarCount += examResultProperty.getZeroStarCount();
		halfStarCount += examResultProperty.getHalfStarCount();
		oneStarCount += examResultProperty.getOneStarCount();
		twoStarCount += examResultProperty.getTwoStarCount();
		threeStarCount += examResultProperty.getThreeStarCount();
		fourStarCount += examResultProperty.getFourStarCount();		
		fiveStarCount += examResultProperty.getFiveStarCount();
		totalItemScore += examResultProperty.getItemScore();
		examScore += examResultProperty.getExamScore();
		
		accuracyRate =examScore/totalItemScore*100;
		accuracyRateRount=ExamUtil.roundFloat(accuracyRate);
		totalNum=zeroStarCount+halfStarCount+oneStarCount+twoStarCount+threeStarCount+fourStarCount+fiveStarCount;
		masteryRate=CalculateUtil.masteryRate(zeroStarCount,halfStarCount,oneStarCount,twoStarCount,threeStarCount,
				fourStarCount,fiveStarCount, totalNum);
		
		
		examResultProperty.setItemCount(itemCount);
		examResultProperty.setRightCount(rightCount);
		examResultProperty.setErrorCount(errorCount);
		examResultProperty.setUndoCount(undoCount);
		examResultProperty.setMarkCount(markCount);
		examResultProperty.setZeroStarCount(zeroStarCount);
		examResultProperty.setHalfStarCount(halfStarCount);
		examResultProperty.setOneStarCount(oneStarCount);
		examResultProperty.setTwoStarCount(twoStarCount);
		examResultProperty.setThreeStarCount(threeStarCount);
		examResultProperty.setFourStarCount(fourStarCount);
		examResultProperty.setFiveStarCount(fiveStarCount);
		examResultProperty.setItemScore(totalItemScore);
		examResultProperty.setExamScore(examScore);
		examResultProperty.setAccuracyRate(accuracyRateRount);
		examResultProperty.setMasteryRate(masteryRate);						
		examResultProperty.setSpendTime(viewControl.getExamTime()-viewControl.getActualTime());
		
		viewControl.setExamResultProperty(examResultProperty);
	}
	
	private void prepareAnswersMap(ViewControl viewControl, Map<CurrentAnswersStatus,Integer> currentMap) {
		for(Page page: viewControl.getPageList()){
			for(Item item: page.getItems()){
				if(ExamUtil.hasSubItem(item)) {
					for(SubItem subItem:item.getSubItems()){
						if(subItem.getEnable()==false) continue; //不计算未出的题
						putAnswersMap(viewControl, currentMap, item, subItem);	
					}
				}
				putAnswersMap(viewControl, currentMap, item, null);			 
			}
		}
	}
	
	//设置当前答卷状态
	private void saveCurrentTestStatus(ViewControl viewControl, NodeInstance nodeInstance,
			Map<CurrentAnswersStatus, Integer> currentMap) {
		Map<NodeInstance,Float> passRateMap			  = viewControl.getPassRateMap();//通过率
		Map<NodeInstance,Boolean> testResultMap		  = viewControl.getTestResultMap();//是否通过的MAP		
		
		CurrentTestStatus cTestStatus = viewControl.getPreStatusMap().get(nodeInstance);
		
		boolean firstAnswer=false;//是否首答的标记
		if(cTestStatus==null){
			cTestStatus=new CurrentTestStatus();
			cTestStatus.setIsTest(false);
			cTestStatus.setTestStatus(1);//首次做
			firstAnswer=true;
		}else{
			int testStatus=2; //重做
			if(viewControl.isWeaknessEnhance()) testStatus=3; //弱项强化
			cTestStatus.setTestStatus(testStatus);	//重做状态
		}
		
		Map<String,CurrentAnswersStatus>  currentNodeMap = 
			statExamResult(viewControl, nodeInstance, cTestStatus, currentMap);//统计 补全
		
		
		cTestStatus.setAsfNodeInstance(nodeInstance);
		
		Float passRate=passRateMap.get(nodeInstance);
		if(passRate==null) passRate=0f;
		
		cTestStatus.setStartTime(viewControl.getStartTime());
		cTestStatus.setEndTime(viewControl.getEndTime());
		cTestStatus.setTimeUsed(viewControl.getExamTime()-viewControl.getActualTime()); //计算考试用时
		cTestStatus.setTimeUsedTotal(													//计算考试总用时
			(cTestStatus.getTimeUsedTotal()==null?0:cTestStatus.getTimeUsedTotal()) + cTestStatus.getTimeUsed());
		if(cTestStatus.getAccuracyRate()>=passRate){
			if (cTestStatus.getPassedAccuracyRate()==0f) {
				cTestStatus.setPassedAccuracyRate(cTestStatus.getAccuracyRate());
				cTestStatus.setPassedMasteryRate(cTestStatus.getMasteryRate());
				cTestStatus.setPassedScore(cTestStatus.getScore());
				cTestStatus.setPassedTime(cTestStatus.getEndTime());
			}	
			testResultMap.put(nodeInstance, true);
		}else{
			testResultMap.put(nodeInstance, false);
		}
		
		if(firstAnswer){
			cTestStatus.setFirstTestAccuracyRate(cTestStatus.getAccuracyRate());
			cTestStatus.setFirstTestMasteryRate(cTestStatus.getMasteryRate());
			cTestStatus.setFirstTestScore(cTestStatus.getScore());
			cTestStatus.setFirstTestTime(cTestStatus.getEndTime());
		}
		
		cTestStatus.setPaperAssemItemType(viewControl.getItemType());
		
		
		Collection<CurrentAnswersStatus> cuAnswerCollection = currentNodeMap.values();				
		
		Session session=genService.getTemplate().getSessionFactory().openSession();
		Transaction transaction= session.beginTransaction();
		try {
			int i=0;
			for(CurrentAnswersStatus cuStatus:cuAnswerCollection){
				session.saveOrUpdate(cuStatus);
				i++;
				if(i%20==0){
					session.flush();
					session.clear();		
				}
			}
			session.saveOrUpdate(cTestStatus);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		viewControl.getPreStatusMap().put(nodeInstance, cTestStatus);
	}
	

	/*
	 * 打分
	 * 星级
	 * 第三个参数是给主观题打分专用
	 * 王伟 重构：抽象到打分类中实现
	 */
	private void examScore(ViewControl viewControl){
		List<Page> pages = viewControl.getPageList(); 
		Map<String,CurrentAnswersStatus> preAnswerMap = viewControl.getPreAnswersStatus();
		Map<String,Float> subjectScoreMap = viewControl.getSubjectScoreMap();
		
		String key = null;
		CurrentAnswersStatus currentAnswersStatus=null;
		Float cuScore = 0f;
		float maxsubstar=0f;
		boolean markedsubitem = false;
		boolean hasSubItem = false;
		for(Page page: pages){
			for(Item item: page.getItems()) {
				maxsubstar = 0f;
				markedsubitem = false;
				hasSubItem = ExamUtil.hasSubItem(item);
				if (hasSubItem){
					for(SubItem subItem: item.getSubItems()) {
						if(subItem.getEnable()==false) continue; //未出的题。在此不再处理
						key = ExamUtil.getMapKey(item, subItem);
						currentAnswersStatus=(CurrentAnswersStatus)preAnswerMap.get(key);
						cuScore = subjectScoreMap.get(key);
						itemScore(item, subItem, currentAnswersStatus, cuScore);
						starGradeCalculate(currentAnswersStatus);
						if (currentAnswersStatus.getStarGrade()>maxsubstar) maxsubstar = currentAnswersStatus.getStarGrade();
						if (currentAnswersStatus.getIsUnsureMarking()) markedsubitem = true;
					}
				}
				
				key = ExamUtil.getMapKey(item, null);
				currentAnswersStatus = (CurrentAnswersStatus)preAnswerMap.get(key);
				cuScore = subjectScoreMap.get(key);
				itemScore(item, null, currentAnswersStatus, cuScore);
				pointCalculate(currentAnswersStatus);
				
				//一对多题型答题的星级为子题中星级最高的题，子题有标记则大题也标记为疑问
				if (hasSubItem) {
					currentAnswersStatus.setStarGrade(maxsubstar);
					currentAnswersStatus.setIsUnsureMarking(markedsubitem);
					
					currentAnswersStatus.getItem().getExamProperty().setStar(maxsubstar);
				} else {
					starGradeCalculate(currentAnswersStatus); //没有子题的按照正常统计星级
				}
			}
		}
	}

	/**
	 * 星级的计算
	 */
	private void starGradeCalculate(CurrentAnswersStatus cuAnswerStatus){
		String c = cuAnswerStatus.getItem().getItemType().getCode().substring(3, 4);
		if (Integer.valueOf(c)<3) starGradeChoose(cuAnswerStatus);
		else starGradeFill(cuAnswerStatus);
		
		if (cuAnswerStatus.getSubItem()==null) 
			cuAnswerStatus.getItem().getExamProperty().setStar(cuAnswerStatus.getStarGrade());
		else
			cuAnswerStatus.getSubItem().getExamProperty().setStar(cuAnswerStatus.getStarGrade());
	}
	
	/**
	 * 客观题（MPC中为选择题）的星级计算机制
	 */
	private void starGradeChoose(CurrentAnswersStatus cuAnswerStatus) {
		if (cuAnswerStatus.getStarGrade()==null)
			starGradeChooseFirstDo(cuAnswerStatus);
		else {
			if (cuAnswerStatus.getStarGrade()==0f) 
				starGradeChooseReDoZero(cuAnswerStatus);
			else
				starGradeChooseReDoNotZero(cuAnswerStatus);
		}
	}
	
	//第一次做
	private void starGradeChooseFirstDo(CurrentAnswersStatus cuAnswerStatus) {		
		float star=0f;
		
		if(cuAnswerStatus.getIsCorrect()) {
			star=cuAnswerStatus.getIsUnsureMarking()?2f:0f;				
		}else{
			star=4f;
		}
		cuAnswerStatus.setStarGrade(star);
	}
	
	//重做星级为0题
	private void starGradeChooseReDoZero(CurrentAnswersStatus cuAnswerStatus) {
		float star = cuAnswerStatus.getStarGrade();
		
		if(cuAnswerStatus.getIsCorrect()){
			star=cuAnswerStatus.getIsUnsureMarking()?1f:0f;	
		}else{
			star=cuAnswerStatus.getIsUnsureMarking()?1f:2f;
		}
		cuAnswerStatus.setStarGrade(star);
	}
	
	/*
	 * 重做星级非0题
	 * 0.5星+1星=2星
	 * 星级非0题，星级最低只能降至0.5星, 最高只能为5星
	 */
	private void starGradeChooseReDoNotZero(CurrentAnswersStatus cuAnswerStatus) {
		float star = cuAnswerStatus.getStarGrade();
		if (star==0.5f) star = 1f;
		
		if(cuAnswerStatus.getIsCorrect()){
			if ((cuAnswerStatus.getContinueCorrectTimes()!=null)&&
				(cuAnswerStatus.getContinueCorrectTimes()>=2))
					star -= 2f;
			else {
				if (!cuAnswerStatus.getIsUnsureMarking()) star -= 1f;
			}	
		} else {
			star += 1f;
		}
		
		if (star<0.5f) star=0.5f;
		if (star>5.0f) star=5.0f;
		
		cuAnswerStatus.setStarGrade(star);
	}
	
	/*
	 * 主观题（MPC中为填空题）的星级计算机制
	 */
	private void starGradeFill(CurrentAnswersStatus cuAnswerStatus) {
		float star = 0f;
		
		float userscore = cuAnswerStatus.getScore()==null?0f:cuAnswerStatus.getScore();
		float score =0f;
		if (cuAnswerStatus.getSubItem()==null) 
			score = cuAnswerStatus.getItem().getScore()==null?0f:cuAnswerStatus.getItem().getScore();
		else 
			score = cuAnswerStatus.getSubItem().getScore()==null?0f:cuAnswerStatus.getSubItem().getScore();
		
		float sr = userscore/score;
		
		if (sr == 0f) 					   star = 5f;
		else if ((sr>0.2f) && (sr<=0.4f) ) star = 4f;
		else if ((sr>0.4f) && (sr<=0.6f) ) star = 3f;
		else if ((sr>0.6f) && (sr<=0.8f) ) star = 2f;
		else if ((sr>0.8f) && (sr<=0.9f) ) star = 1f;
		else if ((sr>0.9f) && (sr< 1f) )   star = 0.5f;
		else if (sr == 1f) 				   star = 0f;
		
		cuAnswerStatus.setStarGrade(star);
	}
	
	/**
	 * 积分的计算 
	 * diligence:勤奋 wisdom:智慧
	 */
	private void pointCalculate(CurrentAnswersStatus cuAnswerStatus) {
		if (cuAnswerStatus.getItem().getExamProperty().getIsDone()==false) return;
			
		int doTimes = 0;
		float diligence = 0f;
		float wisdom = 0f;
		float itemScore;
		
		itemScore = cuAnswerStatus.getItem().getScore();
		
		if (cuAnswerStatus.getDoTimes()==null) doTimes = 0;
		else doTimes = cuAnswerStatus.getDoTimes();
		
		doTimes++;
		if (doTimes==1) diligence = itemScore;
		if (doTimes==2) diligence = itemScore / 2;
		if (doTimes==3) diligence = itemScore / 3;
		if (doTimes==4) diligence = itemScore / 4;
		if (doTimes>=5) diligence = 0f;
		cuAnswerStatus.getItem().getExamProperty().setDiligence(diligence);
		cuAnswerStatus.setDoTimes(doTimes);
		
		if (cuAnswerStatus.getIsCorrect() && (cuAnswerStatus.getContinueCorrectTimes()==1)) {
			if (doTimes==1) wisdom = 5 * itemScore;
			if (doTimes==2) wisdom = 3 * itemScore;
			if (doTimes==3) wisdom = 2 * itemScore;
			if (doTimes>=4) wisdom = itemScore;
			cuAnswerStatus.getItem().getExamProperty().setWisdom(wisdom);
		}
	}
	
	/**
	 * 统计--补全--分析
	 * 连对的逻辑
	 * 按答题统计 2009-02-05
	 * 返回：历次当前试卷的所有试题的答题情况
	 */
	private Map<String,CurrentAnswersStatus> statExamResult(ViewControl viewControl, 
			NodeInstance nodeInstance, CurrentTestStatus currentTestStatus,
			Map<CurrentAnswersStatus,Integer> currentMap) {
			
		Map<String,CurrentAnswersStatus> currentNodeMap=new HashMap<String,CurrentAnswersStatus>();
		//if(currentTestStatus==null) currentTestStatus=new CurrentTestStatus();
		//List<Item> paperItems = examItemService.getItemsByNodeInstance(nodeInstance);
		//List<Item> paperItems = viewControl.getItems();
		
		int rightCount=0;
		int undoCount=0;
		int errorCount=0;
		float examScore=0f;//用户总得分
		int zeroStarCount=0;
		int halfStarCount=0;
		int oneStarCount=0;
		int twoStarCount=0;
		int threeStarCount=0;
		int fourStarCount=0;
		int fiveStarCount=0;
		float totalItemScore=0f;//题目总分
		int markItemCount=0;//疑问标记数
		String mapKey=null;
		
		Set<CurrentAnswersStatus> currentAnswersStatusMap = new HashSet<CurrentAnswersStatus>();
		for (CurrentAnswersStatus currentAnswersStatus: viewControl.getPreAnswersStatus().values()) {
			if (currentAnswersStatus.getAsfNodeInstance().equals(nodeInstance))
				currentAnswersStatusMap.add(currentAnswersStatus);
		}
		 
		for(CurrentAnswersStatus currentAnswersStatus: currentAnswersStatusMap ) {
			
			mapKey=ExamUtil.getMapKey(currentAnswersStatus.getItem(), currentAnswersStatus.getSubItem());					
			if(currentMap.containsKey(currentAnswersStatus))
				currentAnswersStatus.setDataSource(1);//current
			else
				currentAnswersStatus.setDataSource(2);//从历史答题里补过来的
			
			currentAnswersStatus.setAsfNodeInstance(nodeInstance);
			currentNodeMap.put(mapKey, currentAnswersStatus);
			
			if (currentAnswersStatus.getSubItem() == null) {
				totalItemScore = totalItemScore + currentAnswersStatus.getItem().getScore();
				if(!currentAnswersStatus.isDone()) undoCount=undoCount+1;
				if(currentAnswersStatus.getIsCorrect()){
					rightCount=rightCount+1;				
				}else{
					errorCount=errorCount+1;
				}
				examScore=examScore+currentAnswersStatus.getScore(); //大题不正确但是有分值
				
				if(currentAnswersStatus.getStarGrade()==0f)zeroStarCount=zeroStarCount+1;
				if(currentAnswersStatus.getStarGrade()==0.5f)halfStarCount=halfStarCount+1;
				if(currentAnswersStatus.getStarGrade()==1f)oneStarCount=oneStarCount+1;
				if(currentAnswersStatus.getStarGrade()==2f)twoStarCount=twoStarCount+1;
				if(currentAnswersStatus.getStarGrade()==3f)threeStarCount=threeStarCount+1;
				if(currentAnswersStatus.getStarGrade()==4f)fourStarCount=fourStarCount+1;
				if(currentAnswersStatus.getStarGrade()==5f)fiveStarCount=fiveStarCount+1;
				if(currentAnswersStatus.getIsUnsureMarking())markItemCount=markItemCount+1;
			}			
		}
			
		currentTestStatus.setSumUnfinishedItems(undoCount);
		currentTestStatus.setScore(examScore);
		currentTestStatus.setTotalScore(Float.valueOf(totalItemScore).intValue());
		currentTestStatus.setSumCorrectItems(rightCount);
		currentTestStatus.setSumIncorrectItems(errorCount);
		currentTestStatus.setSumZeroStarItems(zeroStarCount);
		currentTestStatus.setSumOneStarItems(oneStarCount);
		currentTestStatus.setSumHalfStarItems(halfStarCount);
		currentTestStatus.setSumTwoStarItems(twoStarCount);
		currentTestStatus.setSumThreeStarItems(threeStarCount);
		currentTestStatus.setSumFourStarItems(fourStarCount);
		currentTestStatus.setSumFiveStarItems(fiveStarCount);
		currentTestStatus.setUnsureMarkItems(markItemCount);
		
		//计算正确率,掌握度
		float accuracyRate=examScore/totalItemScore*100;
		int totalNum=zeroStarCount+halfStarCount+oneStarCount+twoStarCount+threeStarCount+fourStarCount+fiveStarCount;
		float masteryRate=CalculateUtil.masteryRate(zeroStarCount,halfStarCount,oneStarCount,twoStarCount,threeStarCount,fourStarCount,fiveStarCount,totalNum);
		currentTestStatus.setAccuracyRate(ExamUtil.roundFloat(accuracyRate));
		currentTestStatus.setMasteryRate(masteryRate);

		return currentNodeMap;
	}
	
	private boolean hasItems(ViewControl viewControl, NodeInstance nodeInstance){
		boolean has = false;
		for (CurrentAnswersStatus currentAnswersStatus: viewControl.getPreAnswersStatus().values()) {
			if (currentAnswersStatus.getAsfNodeInstance().equals(nodeInstance)) has = true;
		}
		return has;
	}
	

	private boolean hasItems(Map<CurrentAnswersStatus, Integer> currentMap, NodeInstance nodeInstance){
		boolean has = false;
		for (CurrentAnswersStatus currentAnswersStatus: currentMap.keySet()) {
			if (currentAnswersStatus.getAsfNodeInstance().equals(nodeInstance)) has = true;
		}
		return has;
	}
	
	/**
	 * 取得当前训练节点的章前评测，没有返回null
	 * 在本训练节点的节点组中找评测类型节点，并不是本节点的其它节点作为章前评测节点。
	 */
	@SuppressWarnings("unchecked")
	private Set<EvaluatingAnswerStatus> getPreEvaluatingAnswerStatus(ViewControl viewControl, NodeInstance nodeInstance){
		String sql = "select I.id from NodeInstance I, EvaluateNode E where I.processInstance = ? and I.id <> ? " +
					 " and E.nodeGroup.id=? and I.nodeStatus = 2 ";
		Long pNodeInstanceId = (Long)genService.findObjByHql(sql, viewControl.getProcessInstance(), 
				nodeInstance.getId(), nodeInstance.getNode().getNodeGroup().getId());
		if (pNodeInstanceId == null) {
			return null;
		} else {
			String esql = "from EvaluatingAnswerStatus E where E.asfNodeInstance.id = ?";
			List<EvaluatingAnswerStatus> evList = (List<EvaluatingAnswerStatus>)genService.find(esql, pNodeInstanceId);
			Set<EvaluatingAnswerStatus>  evSet = new HashSet<EvaluatingAnswerStatus>();
			evSet.addAll(evList);
			return evSet;
		}
	}
	
	
	private void saveItemRevise(ViewControl viewControl, Map<CurrentAnswersStatus, Integer> currentMap) {
		for(CurrentAnswersStatus currentAnswersStatus: currentMap.keySet()) {
			ItemReviseAnswers itemReviseAnswers = new ItemReviseAnswers();
		
			itemReviseAnswers.setAnswer(currentAnswersStatus.getAnswer());
			itemReviseAnswers.setCorrect(currentAnswersStatus.getIsCorrect());
			itemReviseAnswers.setItem(currentAnswersStatus.getItem());
			itemReviseAnswers.setSubItem(currentAnswersStatus.getSubItem());
			itemReviseAnswers.setScore(currentAnswersStatus.getScore());
			itemReviseAnswers.setItemScore2(currentAnswersStatus.getItemScore2());
			itemReviseAnswers.setItemRevise(viewControl.getItemRevise());
			genService.save(itemReviseAnswers);
		}
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

}
