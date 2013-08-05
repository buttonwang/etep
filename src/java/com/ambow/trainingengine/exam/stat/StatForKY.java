package com.ambow.trainingengine.exam.stat;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.score.IScore;
import com.ambow.trainingengine.exam.score.ScoreFactory;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.util.CalculateUtil;

/*
 * StatForKY.java
 * 
 * Created on 2009-2-5 下午06:47:08
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

public class StatForKY implements IStat {

	private HibernateGenericDao genService;
	private ExamAnswerService examAnswerService;
	private ExamItemService examItemService;

	private ScoreFactory scoreFactory;
	
	public void saveAnswers(ViewControl viewControl) {
		List<Page> pages = viewControl.getPageList(); 
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
			Map<String,CurrentAnswersStatus> currentNodeMap = 
				statExamResult(nodeInstance,cTestStatus,preAnswerMap,currentMap);//统计 补全
			
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
			examAnswerService.saveHistoryAnswers(cTestStatus,currentNodeMap);
			preTestMap.put(nodeInstance, cTestStatus);
		}
		session.close();
		examAnswerService.statCurrentResult(currentMap,viewControl);//统计当前考试所需要的一些数据..非试卷相关
		
	}

	public void saveReviseAnswers(ViewControl viewControl) {

	}
	
	public void statKPoint(ViewControl viewControl) {
		examAnswerService.statKPoint(viewControl);
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
	
	/*
	 * 重构后的打分方法
	 */
	public void itemScore(Item item,SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore){
		IScore score = scoreFactory.getScoreImpl(ExamUtil.getResultCode(item.getItemType().getCode()));
		score.ItemScore(item, subItem, cuAnswerStatus, subjectScore);
	}
	
	/*
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
	
	/*
	 * 统计--补全--分析
	 * TODO:零时的
	 * 连对的逻辑
	 */
	public Map<String,CurrentAnswersStatus> statExamResult(NodeInstance nodeInstance,CurrentTestStatus currentTestStatus,
			Map<String,CurrentAnswersStatus> preAnswerMap, Map<CurrentAnswersStatus,Integer> currentMap)
			{
		Map<String,CurrentAnswersStatus> currentNodeMap=new HashMap<String,CurrentAnswersStatus>();
		if(currentTestStatus==null) 
			currentTestStatus=new CurrentTestStatus();
		List<Item> paperItems = examItemService.getItemsByNodeInstance(nodeInstance);
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
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
		
	public ExamAnswerService getExamAnswerService() {
		return examAnswerService;
	}

	public void setExamAnswerService(ExamAnswerService examAnswerService) {
		this.examAnswerService = examAnswerService;
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
