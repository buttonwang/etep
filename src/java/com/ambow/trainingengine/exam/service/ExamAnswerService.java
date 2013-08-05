package com.ambow.trainingengine.exam.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatusEx;
import com.ambow.trainingengine.exam.domain.PauseInfo;
import com.ambow.trainingengine.exam.domain.PauseInfoEx;
import com.ambow.trainingengine.exam.score.IScore;
import com.ambow.trainingengine.exam.score.ScoreFactory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.util.CalculateUtil;

/* 
 * ExamAnswerService.java <br/>
 * 
 * Created on Jul 16, 2008,10:00:18 AM <br/>
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
public class ExamAnswerService {
	
	private HibernateGenericDao genService;
	
	private ExamItemService examItemService;
	
	private ScoreFactory scoreFactory;


	/*
	 * TODO：参数的设值
	 */
	public void saveTestStatus(NodeInstance nodeInstance){
		CurrentTestStatus currentTestStatus=new CurrentTestStatus();
		
		currentTestStatus.setAsfNodeInstance(nodeInstance);
		genService.save(currentTestStatus);
	}
	
	/**
	 * 保存暂存信息
	 */
	public void savePauseInfo(ViewControl viewControl){
		PauseInfo pauseInfo=new PauseInfo();
		pauseInfo.setAsfProcessInstance(viewControl.getProcessInstance());
		if(viewControl.getExamNodeIns()==null)
				pauseInfo.setNodeInstanceId(0l);
			else
				pauseInfo.setNodeInstanceId(viewControl.getExamNodeIns().getId());
		pauseInfo.setTimeLeft(viewControl.getActualTime());
		pauseInfo.setAnsweringTime(viewControl.getExamTime());
		Date pauseTime=new Date();
		pauseInfo.setPauseTime(pauseTime);
		pauseInfo.setTotalItemsNum(viewControl.getExamItemNum());
		pauseInfo.setCurrentTestPageNum(viewControl.getCurrentPageNum());
		pauseInfo.setUnfinishedItemsNum(viewControl.getUndoItemNum());
		pauseInfo.setUnsureItemsNum(viewControl.getMarkItemNum());
		pauseInfo.setPaperAssemItemType(viewControl.getItemType());
		pauseInfo.setStartTestTime(viewControl.getStartTime());
		pauseInfo.setTestStatus(viewControl.getTestStatus());
		pauseInfo.setIsTested(viewControl.getRedoType());
		
		String hql="delete from PauseInfo pause where pause.asfProcessInstance.id="+viewControl.getProcessInstance().getId();
		genService.excuteHql(hql);
		genService.save(pauseInfo);
		
	}
	
	/**
	 * 当前出的题,此处无补全
	 *  暂停考试
	 *  保存前,先删掉上次的历史暂停信息?
	 */
	public void savePauseAnswers(List<Page> pages,Map<String,String> answerMap,Map<String,Integer> markMap,ProcessInstance processInstance){
		PauseAnswerStatus pauseAnswerStatus=null;
		List<PauseAnswerStatus> pauseAnswerList=new ArrayList<PauseAnswerStatus>();
		String mapKey=null;
		for(Page page:pages){
		 for(Item item:page.getItems()){
			 if(item.getSubItems().size()>0){
				//有子题
				for(SubItem subItem:item.getSubItems()){
					if(subItem.getEnable()==false) continue;//2008-09-04未出的题不存
					mapKey=ExamUtil.getMapKey(item, subItem);
					String answer=answerMap.get(mapKey);
					Integer isMark=markMap.get(mapKey);
					pauseAnswerStatus=new PauseAnswerStatus();
					pauseAnswerStatus.setAnswer(answer);
					pauseAnswerStatus.setItem(item);
					pauseAnswerStatus.setSubItem(subItem);
					if(isMark!=null&&isMark==1)
						pauseAnswerStatus.setIsUnsureMarking(true);
					else
						pauseAnswerStatus.setIsUnsureMarking(false);					
					pauseAnswerStatus.setNodeInstanceId(item.getNodeInstanceId());
					pauseAnswerStatus.setAsfProcessInstance(processInstance);
					pauseAnswerList.add(pauseAnswerStatus);					
				}
				
			}else{
				//无子题
				mapKey=item.getId().toString();
				String answer=answerMap.get(mapKey);
				Integer isMark=markMap.get(mapKey);
				pauseAnswerStatus=new PauseAnswerStatus();
				pauseAnswerStatus.setAnswer(answer);
				pauseAnswerStatus.setItem(item);
				pauseAnswerStatus.setSubItem(null);
				if(isMark!=null&&isMark==1)
					pauseAnswerStatus.setIsUnsureMarking(true);
				else
					pauseAnswerStatus.setIsUnsureMarking(false);
				if (item.getExamProperty()!=null) //待优化
					pauseAnswerStatus.setAnswerOptionOrder(item.getExamProperty().getAnswerOptionOrder());
				pauseAnswerStatus.setNodeInstanceId(item.getNodeInstanceId());
				pauseAnswerStatus.setAsfProcessInstance(processInstance);
				pauseAnswerList.add(pauseAnswerStatus);
				
			}
			
			}
		}
		//删除本流程实例上次的暂停信息
		Session session=genService.getTemplate().getSessionFactory().openSession();
		Transaction transaction0=session.beginTransaction();
		String hql2="delete from PauseAnswerStatus answer where answer.asfProcessInstance.id="+processInstance.getId();
		Query query=session.createQuery(hql2);
		query.executeUpdate();
		transaction0.commit();
		Transaction transaction= session.beginTransaction();
		int i=0;
		for(PauseAnswerStatus answerStatus:pauseAnswerList){
			session.save(answerStatus);
			i++;
			if(i%20==0){
				session.flush();
				session.clear();		
			}
		}
		transaction.commit();
		session.close();
		
	}
	
	/**
	 * 保存拓展训练的暂存信息
	 */
	public void saveExtPauseInfo(ViewControl viewControl){
		PauseInfoEx pauseInfo=new PauseInfoEx();
		pauseInfo.setNodeInstance(viewControl.getExamNodeIns());
		pauseInfo.setTimeLeft(viewControl.getActualTime());
		pauseInfo.setAnsweringTime(viewControl.getExamTime());
		Date pauseTime=new Date();
		pauseInfo.setPauseTime(pauseTime);
		pauseInfo.setTotalItemsNum(viewControl.getExamItemNum());
		pauseInfo.setCurrentTestPageNum(viewControl.getCurrentPageNum());
		pauseInfo.setUnfinishedItemsNum(viewControl.getUndoItemNum());
		pauseInfo.setUnsureItemsNum(viewControl.getMarkItemNum());
		pauseInfo.setStartTestTime(viewControl.getStartTime());
		pauseInfo.setPaperAssemItemType(viewControl.getItemType());
		pauseInfo.setIsTested(viewControl.getRedoType());
		
		String hql="delete from PauseInfoEx pause where pause.nodeInstance.id="+viewControl.getExamNodeIns().getId();
		genService.excuteHql(hql);
		genService.save(pauseInfo);
	}
	
	/**
	 *  保存拓展训练的暂存试题信息
	 * 当前出的题,此处无补全
	 *  暂停考试
	 *  保存前,先删掉上次的历史暂停信息?
	 */
	public void saveExtPauseAnswers(List<Page> pages, Map<String,String> answerMap,Map<String,Integer> markMap, NodeInstance nodeInstance){
		PauseAnswerStatusEx pauseAnswerStatus=null;
		List<PauseAnswerStatusEx> pauseAnswerList=new ArrayList<PauseAnswerStatusEx>();
		String mapKey=null;
		for(Page page:pages){
		 for(Item item:page.getItems()){
			 if(item.getSubItems().size()>0){
				//有子题
				for(SubItem subItem:item.getSubItems()){
					if(subItem.getEnable()==false) continue;//2008-09-04未出的题不存
					mapKey=ExamUtil.getMapKey(item, subItem);
					String answer=answerMap.get(mapKey);
					Integer isMark=markMap.get(mapKey);
					pauseAnswerStatus=new PauseAnswerStatusEx();
					pauseAnswerStatus.setAnswer(answer);
					pauseAnswerStatus.setItem(item);
					pauseAnswerStatus.setSubItem(subItem);
					if(isMark!=null&&isMark==1)
						pauseAnswerStatus.setIsUnsureMarking(true);
					else
						pauseAnswerStatus.setIsUnsureMarking(false);					
					pauseAnswerStatus.setNodeInstance(nodeInstance);
					pauseAnswerList.add(pauseAnswerStatus);		
				}
				
			}else{
				//无子题
				mapKey=item.getId().toString();
				String answer=answerMap.get(mapKey);
				Integer isMark=markMap.get(mapKey);
				pauseAnswerStatus=new PauseAnswerStatusEx();
				pauseAnswerStatus.setAnswer(answer);
				pauseAnswerStatus.setItem(item);
				pauseAnswerStatus.setSubItem(null);
				if(isMark!=null&&isMark==1)
					pauseAnswerStatus.setIsUnsureMarking(true);
				else
					pauseAnswerStatus.setIsUnsureMarking(false);
				if (item.getExamProperty()!=null) //待优化
					pauseAnswerStatus.setAnswerOptionOrder(item.getExamProperty().getAnswerOptionOrder());
				pauseAnswerStatus.setNodeInstance(nodeInstance);
				pauseAnswerList.add(pauseAnswerStatus);
			}
			
			}
		}
		//删除本流程实例上次的暂停信息
		Session session=genService.getTemplate().getSessionFactory().openSession();
		Transaction transaction0=session.beginTransaction();
		String hql2="delete from PauseAnswerStatusEx answer where answer.nodeInstance.id=" + nodeInstance.getId();
		Query query=session.createQuery(hql2);
		query.executeUpdate();
		transaction0.commit();
		Transaction transaction= session.beginTransaction();
		int i=0;
		for(PauseAnswerStatusEx answerStatus: pauseAnswerList){
			session.save(answerStatus);
			i++;
			if(i%20==0){
				session.flush();
				session.clear();		
			}
		}
		transaction.commit();
		session.close();
	}
	
	/**
	 *
	 * 根据processInstance取得PauseInfo
	 */
	@SuppressWarnings("unchecked")
	public PauseInfo getPauseInfo(ProcessInstance processInstance){
		String hql="from PauseInfo info where info.asfProcessInstance.id="+processInstance.getId();
		List<PauseInfo> infos = (List<PauseInfo>)genService.find(hql);
		return infos.get(0);
	}
	
	/**
	 * 根据流程与节点实例取得相应的暂存答案
	 */
	@SuppressWarnings("unchecked")
	public List<PauseAnswerStatus> getPauseAnswers(ProcessInstance processInstance,NodeInstance nodeInstance){		
		String hql="from PauseAnswerStatus pauseAnswer where pauseAnswer.asfProcessInstance.id="+processInstance.getId()
			+" and pauseAnswer.nodeInstanceId="+nodeInstance.getId()+" order by pauseAnswer.item.id,pauseAnswer.subItem.id asc";
		List<PauseAnswerStatus> pauseAnswers= genService.find(hql);
		return pauseAnswers;		
	}
	
	/**
	 *
	 * 根据nodeInstance取得PauseInfoEx
	 */
	public boolean hasPauseInfoEx(NodeInstance nodeInstance){
		String hql="from PauseInfoEx info where info.nodeInstanceId="+nodeInstance.getId();
		return genService.find(hql).size()>0;
	}

	/**
	 *
	 * 根据nodeInstance取得PauseInfoEx
	 */
	@SuppressWarnings("unchecked")
	public PauseInfoEx getPauseInfoEx(NodeInstance nodeInstance){
		String hql="from PauseInfoEx info where info.nodeInstanceId="+nodeInstance.getId();
		List<PauseInfoEx> infos = (List<PauseInfoEx>)genService.find(hql);
		return infos.get(0);
	}
	
	/**
	 * 根据流程与节点实例取得相应的暂存答案
	 */
	@SuppressWarnings("unchecked")
	public List<PauseAnswerStatusEx> getPauseAnswersEx(NodeInstance nodeInstance){		
		String hql="from PauseAnswerStatusEx pauseAnswer where pauseAnswer.nodeInstance.id=" + nodeInstance.getId();
		List<PauseAnswerStatusEx> pauseAnswersEx= genService.find(hql);
		return pauseAnswersEx;
	}
	
	/**
	 * 从暂停中回复状态
	 */
	public void resume(){
		
	}
	
	/**
	 * 注意：此处的List<Item>应为所出的全部题!--暂停的题没法恢复原卷子出的题..
	 *在此版本中打分与保存分开来，脉络更清楚，职责更明确
	 *组装当前答案
	 *第一次答卷时的操作，为提高插入效率，此处自己管理Session
	 *
	 *处理逻辑如下描述：
	 *取得preAnswerMap
	 *判分
	 *补全答案
	 *删除preCurrentAnswer
	 *保存当前currentAnswer
	 *检查动态试卷的“是否做过”这个属性，判断是否补全
	 */
	public void saveExamAnswers(List<Page> pages,ViewControl viewControl) throws IllegalAccessException, InvocationTargetException{
		List<NodeInstance> nodeInstances=viewControl.getNodeInstances();
		Map<String,String> cuAnswerStrMap=viewControl.getAnswerMap();
		Map<String,Integer> markMap=viewControl.getMarkMap();
		Map<CurrentAnswersStatus,Integer> currentMap=new HashMap<CurrentAnswersStatus,Integer>();
		Map<NodeInstance,Float> passRateMap=viewControl.getPassRateMap();//通过率
		Map<NodeInstance,Boolean> testResultMap=viewControl.getTestResultMap();//是否通过的MAP
		
		Map<String,CurrentAnswersStatus> preAnswerMap=viewControl.getPreAnswersStatus();
		Map<NodeInstance,CurrentTestStatus> preTestMap=viewControl.getPreStatusMap();
				String key=null;
		CurrentAnswersStatus currentAnswersStatus=null;
		
		String answer=null;
		Integer mark=null;
		int itemSize=0;
		for(Page page:pages){
		 for(Item item:page.getItems()){
			if(item.getSubItems()!=null&&item.getSubItems().size()>0){
				//有子题的情况下
				for(SubItem subItem:item.getSubItems()){
					//不计算未出的题
					if(subItem.getEnable()==false) continue;
					key=ExamUtil.getMapKey(item,subItem);
					currentAnswersStatus=preAnswerMap.get(key);
					if(currentAnswersStatus==null)
						currentAnswersStatus=new CurrentAnswersStatus();
					//System.out.println("canswer id is:"+currentAnswersStatus.getId());
					answer=(String)cuAnswerStrMap.get(key);
					mark=(Integer)markMap.get(key);
					currentAnswersStatus.setAnswer(answer);
					if(mark!=null&&mark==1)
						currentAnswersStatus.setIsUnsureMarking(true);
					else
						currentAnswersStatus.setIsUnsureMarking(false);
					currentAnswersStatus.setItem(item);
					currentAnswersStatus.setSubItem(subItem);
					currentMap.put(currentAnswersStatus, 1);
					if(!preAnswerMap.containsKey(key))preAnswerMap.put(key, currentAnswersStatus);
					itemSize=itemSize+1;

				}
				
			 }else{
				key=null;
				key=item.getId().toString();
				currentAnswersStatus=preAnswerMap.get(key);
				if(currentAnswersStatus==null)
					currentAnswersStatus=new CurrentAnswersStatus();
				//System.out.println("canswer id of Item is:"+currentAnswersStatus.getId());
				answer=(String)cuAnswerStrMap.get(key);
				mark=(Integer)markMap.get(key);
				currentAnswersStatus.setAnswer(answer);
				if(mark!=null&&mark==1)
					currentAnswersStatus.setIsUnsureMarking(true);
				else
					currentAnswersStatus.setIsUnsureMarking(false);
				currentAnswersStatus.setItem(item);
				currentAnswersStatus.setSubItem(null);
				currentMap.put(currentAnswersStatus, 1);
				if(!preAnswerMap.containsKey(key))preAnswerMap.put(key, currentAnswersStatus);
				itemSize=itemSize+1;
				
			 }

			}
		}
		examScore(pages,preAnswerMap,viewControl.getSubjectScoreMap());//打分、星级 练对
		 
		//List<CurrentTestStatus> testStatusList=new ArrayList<CurrentTestStatus>();
		CurrentTestStatus cTestStatus=null;
		Session session=genService.getTemplate().getSessionFactory().openSession();
		for(NodeInstance nodeInstance:nodeInstances){
			cTestStatus=preTestMap.get(nodeInstance);
			boolean firstAnswer=false;//是否首答的标记
			if(cTestStatus==null){
				cTestStatus=new CurrentTestStatus();
				cTestStatus.setIsTest(false);
				cTestStatus.setTestStatus(1);//首次做
				firstAnswer=true;
			}else{
				int testStatus=2;
				if(viewControl.isWeaknessEnhance())testStatus=3;
				cTestStatus.setTestStatus(testStatus);//重做状态
				
			}
			Map<String,CurrentAnswersStatus> currentNodeMap=statExamResult(nodeInstance,cTestStatus,preAnswerMap,currentMap);//统计 补全
			cTestStatus.setAsfNodeInstance(nodeInstance);
			//
			Float passRate=passRateMap.get(nodeInstance);
			if(passRate==null)passRate=0f;//TODO:暂时福林
			//通过时的一些BT属性...
			cTestStatus.setStartTime(viewControl.getStartTime());
			Date endTime=new Date();
			cTestStatus.setEndTime(endTime);
			if(cTestStatus.getAccuracyRate()>=passRate){
				cTestStatus.setPassedAccuracyRate(cTestStatus.getAccuracyRate());
				cTestStatus.setPassedMasteryRate(cTestStatus.getMasteryRate());
				cTestStatus.setPassedScore(cTestStatus.getScore());
				cTestStatus.setPassedTime(cTestStatus.getEndTime());
				testResultMap.put(nodeInstance, true);
			}else{
				testResultMap.put(nodeInstance, false);
			}
			//首答时一些BT属性的写入..
			if(firstAnswer){
				cTestStatus.setFirstTestAccuracyRate(cTestStatus.getAccuracyRate());
				cTestStatus.setFirstTestMasteryRate(cTestStatus.getMasteryRate());
				cTestStatus.setFirstTestScore(cTestStatus.getScore());
				cTestStatus.setFirstTestTime(cTestStatus.getEndTime());
			}
			cTestStatus.setPaperAssemItemType(viewControl.getItemType());
			
			
			
			Transaction transaction= session.beginTransaction();
			Collection<CurrentAnswersStatus> cuAnswerCollection=currentNodeMap.values();
			int i=0;
			for(CurrentAnswersStatus cuStatus:cuAnswerCollection){
				session.saveOrUpdate(cuStatus);
				//session.save(cuStatus);
				i++;
				if(i%20==0){
					session.flush();
					session.clear();		
				}
			}
			transaction.commit();
			
			
			//计算考试用时
			int spendTime=viewControl.getExamTime()-viewControl.getActualTime();
			cTestStatus.setTimeUsed(spendTime);
			genService.save(cTestStatus);
			saveHistoryAnswers(cTestStatus,currentNodeMap);
			preTestMap.put(nodeInstance, cTestStatus);
		}
		session.close();
		statCurrentResult(currentMap,viewControl);//统计当前考试所需要的一些数据..非试卷相关
		
	}
	
	/**
	 * 统计--补全--分析
	 * TODO:零时的
	 * 连对的逻辑
	 */
	public Map<String,CurrentAnswersStatus> statExamResult(NodeInstance nodeInstance,CurrentTestStatus currentTestStatus,
			Map<String,CurrentAnswersStatus> preAnswerMap, Map<CurrentAnswersStatus,Integer> currentMap)
			throws IllegalAccessException, InvocationTargetException	{
		Map<String,CurrentAnswersStatus> currentNodeMap=new HashMap<String,CurrentAnswersStatus>();
		if(currentTestStatus==null) 
			currentTestStatus=new CurrentTestStatus();
		List<Item> paperItems=examItemService.getItemsByNodeInstance(nodeInstance);
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
		CurrentAnswersStatus currentAnswersStatus=null;
		for(Item item:paperItems){
			totalItemScore=totalItemScore+item.getScore();
			if(item.getSubItems().size()>0){
				for(SubItem subItem:item.getSubItems()){
					mapKey=ExamUtil.getMapKey(item, subItem);
					currentAnswersStatus=preAnswerMap.get(mapKey);
					if(currentMap.containsKey(currentAnswersStatus))
						currentAnswersStatus.setDataSource(1);//current
					else
						currentAnswersStatus.setDataSource(2);//从历史答题里补过来的
					//currentMap.put(mapKey, currentAnswersStatus);
					
					currentAnswersStatus.setAsfNodeInstance(nodeInstance);
					//System.out.println(" mapkey:"+mapKey+" currentAnswersStatus:"+currentAnswersStatus.getAnswer());
					if(currentAnswersStatus.getAnswer()==null||currentAnswersStatus.getAnswer().trim().length()==0)undoCount=undoCount+1;
					if(currentAnswersStatus.getIsCorrect()){
						rightCount=rightCount+1;
						examScore=examScore+currentAnswersStatus.getScore();
					}else{
						errorCount=errorCount+1;
					}
					if(currentAnswersStatus.getStarGrade()==0f)zeroStarCount=zeroStarCount+1;
					if(currentAnswersStatus.getStarGrade()==0.5f)halfStarCount=halfStarCount+1;
					if(currentAnswersStatus.getStarGrade()==1f)oneStarCount=oneStarCount+1;
					if(currentAnswersStatus.getStarGrade()==2f)twoStarCount=twoStarCount+1;
					if(currentAnswersStatus.getStarGrade()==3f)threeStarCount=threeStarCount+1;
					if(currentAnswersStatus.getStarGrade()==4f)fourStarCount=fourStarCount+1;
					if(currentAnswersStatus.getStarGrade()==5f)fiveStarCount=fiveStarCount+1;
					if(currentAnswersStatus.getIsUnsureMarking()==true)markItemCount=markItemCount+1;
					//currentAnswersStatus.setNodeInstanceId(nodeInstance.getId());
					currentNodeMap.put(mapKey, currentAnswersStatus);
				}
			}else{
				mapKey=ExamUtil.getMapKey(item, null);
				currentAnswersStatus=preAnswerMap.get(mapKey);
				if(currentMap.containsKey(currentAnswersStatus))
					currentAnswersStatus.setDataSource(1);//current
				else
					currentAnswersStatus.setDataSource(2);//从历史答题里补过来的
				
				currentAnswersStatus.setAsfNodeInstance(nodeInstance);
				//统计总分数与总做对题数
				if(currentAnswersStatus.getAnswer()==null||currentAnswersStatus.getAnswer().trim().length()==0)undoCount=undoCount+1;
				if(currentAnswersStatus.getIsCorrect()){
					rightCount=rightCount+1;
					examScore=examScore+currentAnswersStatus.getScore();
				}else{
					errorCount=errorCount+1;
				}
				if(currentAnswersStatus.getStarGrade()==0f)zeroStarCount=zeroStarCount+1;
				if(currentAnswersStatus.getStarGrade()==0.5f)halfStarCount=halfStarCount+1;
				if(currentAnswersStatus.getStarGrade()==1f)oneStarCount=oneStarCount+1;
				if(currentAnswersStatus.getStarGrade()==2f)twoStarCount=twoStarCount+1;
				if(currentAnswersStatus.getStarGrade()==3f)threeStarCount=threeStarCount+1;
				if(currentAnswersStatus.getStarGrade()==4f)fourStarCount=fourStarCount+1;
				if(currentAnswersStatus.getStarGrade()==5f)fiveStarCount=fiveStarCount+1;
				if(currentAnswersStatus.getIsUnsureMarking()==true)markItemCount=markItemCount+1;
				//currentAnswersStatus.setNodeInstanceId(nodeInstance.getId());
				currentNodeMap.put(mapKey, currentAnswersStatus);
			}
		}
		currentTestStatus.setSumUnfinishedItems(undoCount);
		currentTestStatus.setScore(examScore);
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
//		if(nodeInstance.getNodeStatus().equals(NodeStatus.INITIAL)){
//			//第一次答卷
//			currentTestStatus.setFirstTestAccuracyRate(accuracyRate);
//			currentTestStatus.setFirstTestMasteryRate(masteryRate);
//			currentTestStatus.setFirstTestScore(examScore);
//		}
		return currentNodeMap;
	}
	
	/*
	 * 计算当前考试的正确率 不分试卷..
	 */
	public void statCurrentResult(Map<CurrentAnswersStatus,Integer> map,ViewControl viewControl){
		Set<CurrentAnswersStatus> testSet=map.keySet();
		Iterator<CurrentAnswersStatus> iterator=testSet.iterator();
		CurrentAnswersStatus cuAnswer=null;
		int rightNum=0;
		int doneNum=0;
		int errorNum=0;
		float score=0f;
		//int i=0;
		while(iterator.hasNext()){
			cuAnswer=iterator.next();
			score=score + ((cuAnswer.getScore()==null)?0f:cuAnswer.getScore());
			if(cuAnswer.getIsCorrect()==true){
				rightNum=rightNum+1;
			}
			if(cuAnswer.getAnswer()!=null&&cuAnswer.getAnswer().trim().length()>0){
				doneNum=doneNum+1;
				if(cuAnswer.getIsCorrect()==false)errorNum=errorNum+1;
				
			}
		}
		viewControl.setScore(score);
		viewControl.setErrorItemNum(errorNum);
		viewControl.setDoneItemNum(doneNum);
		viewControl.setRightItemNum(rightNum);
		Float rightRate=(score/viewControl.getExamValue())*100;
		viewControl.setRightRate(rightRate.intValue());
	}
	
	/*
	 * 打分
	 * 星级
	 * 第三个参数是给主观题打分专用
	 * 王伟 重构：抽象到打分类中实现
	 */
	public void examScore(List<Page> pages,Map<String,CurrentAnswersStatus> preAnswerMap,Map<String,Float> subjectScoreMap){
		String key=null;
		CurrentAnswersStatus currentAnswersStatus=null;
		Float cuScore = 0f;
		for(Page page:pages){
			//if(page.getItemType().getCode().equals("39A")||page.getItemType().getCode().equals("39B")||
			//	 page.getItemType().getCode().equals("44A")||page.getItemType().getCode().equals("34C")){
			//	subjectSetValue(page,preAnswerMap,subjectScoreMap);
			//	continue;
			//} 
			for(Item item:page.getItems()){
				if(item.getSubItems()!=null&&item.getSubItems().size()>0){
					for(SubItem subItem:item.getSubItems()){
						//未出的题。在此不再处理
						if(subItem.getEnable()==false) continue;
						key=ExamUtil.getMapKey(item, subItem);
						currentAnswersStatus=(CurrentAnswersStatus)preAnswerMap.get(key);
						cuScore = subjectScoreMap.get(key);
						itemScore(item, subItem, currentAnswersStatus,cuScore);
						starGradeCalculate(currentAnswersStatus);
					}
					itemScore(item, null, currentAnswersStatus, cuScore);		
				}else{
					key=item.getId().toString();
					currentAnswersStatus=(CurrentAnswersStatus)preAnswerMap.get(key);
					cuScore = subjectScoreMap.get(key);
					itemScore(item,null,currentAnswersStatus,cuScore);
					starGradeCalculate(currentAnswersStatus);		
				}
			}
		}
		
	}
	
	/**
	 * 重构后的打分方法
	 */
	public void itemScore(Item item,SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore){
		IScore score = scoreFactory.getScoreImpl(ExamUtil.getResultCode(item.getItemType().getCode()));
		score.ItemScore(item, subItem, cuAnswerStatus, subjectScore);
	}
	
	
	/**
	 * 星级的计算
	 * TODO:考虑连对连错
	 * 当作过时需要考虑以前做过的纪录
	 */
	public void starGradeCalculate(CurrentAnswersStatus cuAnswerStatus){
		//第一次做
		Float preStar=cuAnswerStatus.getStarGrade();
		float star=0f;
		if(preStar==null){
			if(cuAnswerStatus.getIsCorrect()){
				star=0f;
			}else{
				star=3f;
			}
		}else{
			//以前作过
			star=preStar;
			if(cuAnswerStatus.getIsCorrect()){
				if(star>0){
					if(cuAnswerStatus.getContinueCorrectTimes()!=null&&cuAnswerStatus.getContinueCorrectTimes()==2)
							star=star-2f;
						else
							star=star-1f;
					if(star<0.5)star=0.5f;
				}
				
			}else{
				if(star==0.5f)star=1f;
				star=star+1f;
				if(star>=5f)star=5.0f;
			}
		}
		cuAnswerStatus.setStarGrade(star);
	}
	
	
	
	/**
	 * 取得上次考试的答案
	 */
	public Map<String,CurrentAnswersStatus> getCurrentAnswerMap(List<NodeInstance> nodeInstances){
		Map<String,CurrentAnswersStatus> map=new HashMap<String,CurrentAnswersStatus>();
		for(NodeInstance nodeInstance:nodeInstances){
			String hql="select canswer from CurrentAnswersStatus canswer where canswer.asfNodeInstance.id="+nodeInstance.getId();
			List<CurrentAnswersStatus> currentAnswerList= genService.find(hql);
			String key=null;
			for(CurrentAnswersStatus cAnswer:currentAnswerList){
				key=ExamUtil.getMapKey(cAnswer.getItem(),cAnswer.getSubItem());
				map.put(key,cAnswer);
			}
		}
		return map;
	}
	
	/**
	 * 取得上次考试的状态
	 */
	public Map<NodeInstance,CurrentTestStatus> getCurrentTestMap(List<NodeInstance> nodeInstances){
		Map<NodeInstance,CurrentTestStatus> map=new HashMap<NodeInstance,CurrentTestStatus>();
		for(NodeInstance nodeInstance:nodeInstances){
			String hql="select ctest from CurrentTestStatus ctest where ctest.asfNodeInstance.id="+nodeInstance.getId();
			List<CurrentTestStatus> currentTestList= genService.find(hql);
			CurrentTestStatus cTest=null;
			if(currentTestList!=null&&currentTestList.size()>0){
				cTest=currentTestList.get(0);
				map.put(nodeInstance, cTest);	
			}
		}
		return map;
	}
	
	/**
	 * 此处是补全后的CurrentAnswersStatus
	 * 单独列方法 在viewControl中保存与一个CurrentAnswerStatus方便显示
	 * 与HistoryAnswer一起存
	 */
	public void saveHistoryAnswers(CurrentTestStatus currentTestStatus,Map<String,CurrentAnswersStatus> answerStatusMap) {
		HistoryTestStatus historyTestStatus=new HistoryTestStatus();
		try {
			BeanUtils.copyProperties(historyTestStatus,currentTestStatus);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		historyTestStatus.setId(null);
		String key=null;
		CurrentAnswersStatus currentAnswersStatus=null;
		HistoryAnswerStatus historyAnswerStatus=null;
		Iterator<String> keyIterator=answerStatusMap.keySet().iterator();
		while(keyIterator.hasNext()){
			historyAnswerStatus=new HistoryAnswerStatus();
			key=keyIterator.next();
			currentAnswersStatus=answerStatusMap.get(key);
			try {
				BeanUtils.copyProperties(historyAnswerStatus, currentAnswersStatus);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			historyAnswerStatus.setId(null);
			//historyAnswerStatus.setItem(currentAnswersStatus.getItem());
			//historyAnswerStatus.setSubItem(currentAnswersStatus.getSubItem());
			historyAnswerStatus.setHistoryTestStatus(historyTestStatus);
			historyTestStatus.getHistoryAnswerStatuses().add(historyAnswerStatus);
			
		}
		genService.save(historyTestStatus);		
	}
	
	/**
	 *根据考试结果保存知识点 
	 */
	public void statKPoint(ViewControl viewControl){
		NodeInstance nodeInstance=viewControl.getExamNodeIns();
		Map<String,CurrentAnswersStatus> answerStatusMap=viewControl.getPreAnswersStatus();
		List<Item> paperItems=examItemService.getItemsByNodeInstance(nodeInstance);
		Map<KnowledgePoint,Float> totalScoreMap=new HashMap<KnowledgePoint,Float>();
		Map<KnowledgePoint,Float> cuScoreMap=new HashMap<KnowledgePoint,Float>();
		CurrentAnswersStatus currentStatus=null;
		
		for(Item item:paperItems){
			
			if(item.getSubItems().size()>0){
				Float subScore=item.getScore()/(float)item.getSubItems().size();
				for(SubItem subItem:item.getSubItems()){
					String mapKey=ExamUtil.getMapKey(item, subItem);
					currentStatus=answerStatusMap.get(mapKey);
					Float subKvalue=subScore/(float)subItem.getKnowledgePoints().size();
					//System.out.println("item:"+item.getId()+" subItem:"+subItem.getId()+" sub Score:"+subScore+" k Size:"+subItem.getKnowledgePoints().size()+" kvalue:"+subKvalue);
					Float subUKvalue=null;
					if(currentStatus.getScore()!=null&&currentStatus.getScore()>0)
						subUKvalue=currentStatus.getScore()/(float)subItem.getKnowledgePoints().size();
					for(KnowledgePoint kPoint:subItem.getKnowledgePoints()){
						Float f1=totalScoreMap.get(kPoint);
						if(f1==null)f1=subKvalue;
						else
							f1=f1+subKvalue;
						totalScoreMap.put(kPoint, f1);
						//System.out.println("The KPoint:"+kPoint.getName()+" Score:"+f1);
						
						if(subUKvalue!=null){
							Float f2=cuScoreMap.get(kPoint);
							if(f2==null) f2=subUKvalue;
							else
								f2=f2+subUKvalue;
							cuScoreMap.put(kPoint, f2);
						}
						
					}
				}
			}else{
				String mapKey=ExamUtil.getMapKey(item, null);
				currentStatus=answerStatusMap.get(mapKey);
				Float subKvalue=item.getScore()/(float)item.getKnowledgePoints().size();
				//System.out.println("item:"+item.getId()+" Score:"+item.getScore()+" k Size:"+item.getKnowledgePoints().size()+" kvalue:"+subKvalue);
				
				Float subUKvalue=null;
				if(currentStatus.getScore()!=null&&currentStatus.getScore()>0)
					subUKvalue=currentStatus.getScore()/(float)item.getKnowledgePoints().size();
				for(KnowledgePoint kPoint:item.getKnowledgePoints()){
					Float f1=totalScoreMap.get(kPoint);
					if(f1==null)f1=subKvalue;
					else
						f1=f1+subKvalue;
					totalScoreMap.put(kPoint, f1);
					//System.out.println("The KPoint:"+kPoint.getName()+" Score:"+f1);
					
					if(subUKvalue!=null){
						Float f2=cuScoreMap.get(kPoint);
						if(f2==null) f2=subUKvalue;
						else
							f2=f2+subUKvalue;
						cuScoreMap.put(kPoint, f2);
					}
					
				}
				
			}
		}
	//遍历完毕两个map中内容应该是一样的
		
		Set<KnowledgePoint> kPoints=totalScoreMap.keySet();
		EvaluatingAnswerStatus eAnswerStatus=null;
		NodeInstance asfNodeInstance=viewControl.getExamNodeIns();
		//保存之前删除老的评测记录。。只有一次
		String hql="delete from EvaluatingAnswerStatus eStatus where eStatus.asfNodeInstance.id="+asfNodeInstance.getId();
		this.genService.excuteHql(hql);
		
		for(KnowledgePoint kPoint:kPoints){
			eAnswerStatus=new EvaluatingAnswerStatus();
			eAnswerStatus.setAsfNodeInstance(asfNodeInstance);
			Float f1=totalScoreMap.get(kPoint);
			Float f2=cuScoreMap.get(kPoint);
			eAnswerStatus.setKnowledgePoint(kPoint);
			if(f2==null) f2=0f;
			eAnswerStatus.setScore(f2);
			eAnswerStatus.setTotalScore(f1);
			this.genService.save(eAnswerStatus);
			//System.out.println("KnowledgePoint:"+kPoint.getName()+" total:"+f1+" user Score:"+f2);
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
	
	public ScoreFactory getScoreFactory() {
		return scoreFactory;
	}

	public void setScoreFactory(ScoreFactory scoreFactory) {
		this.scoreFactory = scoreFactory;
	}
	

}
