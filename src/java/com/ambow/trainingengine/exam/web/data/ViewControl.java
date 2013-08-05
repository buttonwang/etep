package com.ambow.trainingengine.exam.web.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.util.ExamType;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;

/* 
 * ViewControlParam.java <br/>
 * 
 * 此处持有界面显示的控制参数
 * 留待后期重构--一些对象之间的相互依赖,可以考虑用Spring来处理
 * 
 * Created on Jul 14, 2008,4:58:31 PM <br/>
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
public class ViewControl {
	
	/*
	 * 构造函数
	 */
	public  ViewControl(){}

	protected String flowName;		//流程名称
	protected String examName;		//试卷名称
	protected String examTask;		//任务名称
	protected String examType;		//任务类型
	protected String sectionName;	//节名称
	protected String chapterName;	//章名称
	protected ExamType examTypePara;//考试类型
	
	protected boolean entranceExam=false; 	//进入考试的标志
	protected boolean showNextButton;
	protected boolean showPreButton;
	
	protected Integer nextPageNum;
	protected Integer prePageNum;
	
	protected int showModel; //1为Test 2为逐题浏览模式  3为view模式 4为widget模式
	
	protected int currentInsId;//当前节点的Id。。无论什么节点，如果整个流程则为0
	protected int currentTestStatusId;  	//当前答卷的Id。
	protected int historyTestStatusId=0;	//历史答卷的Id。

	protected boolean isWeaknessEnhance = false; //是否弱项强化
	protected boolean isExtPractice = false;     //是否拓展训练

	protected int redoType=0;	//1,redo 2做测试后的redo 该属性仅对单卷考试有效
	
	protected int testStatus=1; //1-首答 2-重做 3-弱项强化 4-拓展练习

	protected boolean isGroup=false;//默认为空
	
	protected Float itemScope;
	
	protected List<Float> rightRateForPass;
	
	protected boolean isPass;//是否高于所要求的正确率	
	protected int spendTime; //花费的时间	
	protected Float score;//得分	
	protected int rightRate; //正确率
	
	protected int totalItemNum;//本卷总题数
	protected int examItemNum;//总题数
	protected int markItemNum;//总标记数
	protected int doneItemNum;//总完成数
	protected int undoItemNum;//总未作数
	protected int rightItemNum;//正确数
	protected int errorItemNum;//错题数
	protected int disableItemNum;//本卷中被禁止的题目数
	protected int requireRightRate;//需要的正确率
	protected Float difficultyValue; //难度
	
	protected ExamBeginProperty  examBeginProperty;  //试卷答题开始前预告的属性
	protected ExamResultProperty examResultProperty; //试卷答题后返回属性

	protected int totalPageNum;//总页数
	protected int currentPageNum=0;//当前显示页数 
		
	protected float examValue;//多少分
	protected int examTime;// 单位秒
	protected int actualTime;//实际剩余时间
	
	protected int standardAnsweringTime=0; //标准答题时间

	protected Date startTime=null; //开始时间
	protected Date endTime=null;   //结束时间

	protected List<Page> pageList;	
	protected List<Section> sectionList;	
	protected List<Page> filterPageList;
	
	protected int markPolicy;     //允许标记策略 	   0 不允许 1 允许 
	protected int analysisPolicy; //允许查看解析策略 1 随时 2 做题后 3 正确后 0 永不
	protected int answerPolicy;   //允许查看答案策略 1 随时 2 做题后 3 正确后 0 永不
	protected int randomAnswerOptionsPolicy; //是否颠倒答案顺序    0-否;1-是
	protected int scorePolicy=1;  //允许查看得分      0 不允许 1 允许 
	protected int compareAnswerPolicy; //允许比较答案	0 不允许 1 允许
	
	protected int filterType=1;  //默认是1表示 overView 2:错误的 3:undo的 4:为疑问标记的
	
	protected Float itemType=-1f;//试题的类型 0-5星级题 11未答 12 错题 13 未答&错题 14正确题 15疑问题 -1全部
	
	protected Map<NodeInstance,Float> passRateMap=new HashMap<NodeInstance,Float>();
	
	protected Map<String,CurrentAnswersStatus> preAnswersStatus=null;//上一次的考试结果,放在此Map中
	
	protected Map<String,CurrentAnswersStatus> currentAnswersStatus=null;//当前的考试结果,放在这个Map中

	protected Map<String,HistoryAnswerStatus> historyStatusMap=null;//历史上某次
		
	protected List<NodeInstance> nodeInstances=new ArrayList<NodeInstance>();//当前测试的节点.有可能是多个nodeInstance..对应于多个paper
	
	protected NodeInstance examNodeIns; //当前节点实例
	
	protected Map<NodeInstance,CurrentTestStatus> preStatusMap=new HashMap<NodeInstance,CurrentTestStatus>(); //所有产生的节点考试情况
	
	protected Map<NodeInstance,Boolean> testResultMap=new HashMap<NodeInstance,Boolean>();//当前的一堆考试结果
	
	protected ProcessInstance processInstance;	//流程实例
	
	protected ProcessTrainingStatus processTrainingStatus; //流程训练情况

	protected boolean isPause=false; //是否暂停

	protected boolean isStoped=false; //是否结束
	
	protected boolean isFilter=false;//是否处于过滤模式..此处决定页码的取值
	
	protected Map<String,String> answerMap=new HashMap<String,String>();	
	protected Map<String,Integer> markMap=new HashMap<String,Integer>();	
	protected Map<String,Float> starMap=new HashMap<String,Float>();	
	protected Map<String,Boolean> rightMap=new HashMap<String,Boolean>();
	protected Map<String,Boolean> doneMap=new HashMap<String,Boolean>(); //试题是否已做Map 组卷时使用。
	protected Map<String,Float> subjectScoreMap=new HashMap<String,Float>();//主观题打分的结果。。	
	protected Map<String,Float> scoreMap=new HashMap<String,Float>();	//试题分值
	protected Map<String,String> score2Map=new HashMap<String,String>(); //试题打分分值分布情况。  1;2;0;3
	protected Map<String,String> answerOptionOrderMap=new HashMap<String,String>(); //试题颠倒选项字符串

	//考试结果的一些状态字段
	protected TrainingPolicy trainPolicy;//BT节点训练策略。
		
	protected int quitTypeForView;
	
	//test statuses
	protected int testPass=0;
	
	protected Float testScore;	

	protected boolean showAnswer = true; //是否显示标准答案和流程直通车。
	
	protected String projectVersion;	//项目版本名称 ky: 考研 	mpc: 数理化
	
	protected long processInstanceId;

	protected ItemRevise itemRevise; //试题审校信息

	protected int revising = 0; //校验试题状态  1: 校验
	
	public boolean isEntranceExam() {
		return entranceExam;
	}
	public void setEntranceExam(boolean entranceExam) {
		this.entranceExam = entranceExam;
	}
	public boolean isShowNextButton() {
		return showNextButton;
	}
	public void setShowNextButton(boolean showNextButton) {
		this.showNextButton = showNextButton;
	}
	public boolean isShowPreButton() {
		return showPreButton;
	}
	public void setShowPreButton(boolean showPreButton) {
		this.showPreButton = showPreButton;
	}
	public Integer getNextPageNum() {
		return nextPageNum;
	}
	public void setNextPageNum(Integer nextPageNum) {
		this.nextPageNum = nextPageNum;
	}
	public Integer getPrePageNum() {
		return prePageNum;
	}
	public void setPrePageNum(Integer prePageNum) {
		this.prePageNum = prePageNum;
	}
	public int getShowModel() {
		return showModel;
	}
	public void setShowModel(int showModel) {
		this.showModel = showModel;
	}
	public int getExamItemNum() {
		return examItemNum;
	}
	public void setExamItemNum(int examItemNum) {
		this.examItemNum = examItemNum;
	}
	public int getMarkItemNum() {
		return markItemNum;
	}
	public void setMarkItemNum(int markItemNum) {
		this.markItemNum = markItemNum;
	}
	public int getDoneItemNum() {
		return doneItemNum;
	}
	public void setDoneItemNum(int doneItemNum) {
		this.doneItemNum = doneItemNum;
	}
	public int getRightItemNum() {
		return rightItemNum;
	}
	public void setRightItemNum(int rightItemNum) {
		this.rightItemNum = rightItemNum;
	}
	public int getTotalPageNum() {
		return totalPageNum;
	}
	public void setTotalPageNum(int totalPageNum) {
		this.totalPageNum = totalPageNum;
	}
	public String getFlowName() {
		return flowName;
	}
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	public float getExamValue() {
		return examValue;
	}
	public void setExamValue(float examValue) {
		this.examValue = examValue;
	}
	public int getUndoItemNum() {
		return undoItemNum;
	}
	public void setUndoItemNum(int undoItemNum) {
		undoItemNum=this.examItemNum-this.doneItemNum;
		this.undoItemNum = undoItemNum;
	}
	public int getExamTime() {
		return examTime;
	}
	public void setExamTime(int examTime) {
		this.examTime = examTime;
	}
	
	public int getStandardAnsweringTime() {
		return standardAnsweringTime;
	}
	public void setStandardAnsweringTime(int standardAnsweringTime) {
		this.standardAnsweringTime = standardAnsweringTime;
	}
	
	/*
	 * 时长的表现形式 中文
	 */
	public String getExamTimeStr(){
		String str;
		str=ExamUtil.getChineseTimeStr(examTime);
		return str;
	}
	/*
	 * 时长的表现形式 examTime
	 *    xx'yy'' xx分yy秒
	 */
	public String getExamTimeStr2(){
		String str;
		str=ExamUtil.getTimeStr(examTime);
		return str;
	}
	/*
	 * 时长的表现形式 exam
	 */
	public String getActualTimeStr2(){
		return ExamUtil.getTimeStr(actualTime);
	}
	/*
	 * 花费的时间的表现形式
	 */
	public String getSpendTimeStr(){
		return ExamUtil.getTimeStr(examTime-actualTime);
	}
	public String getSpendTimeStr2(){
		return ExamUtil.getTimeStr2(examTime-actualTime);
	}
	public int getCurrentPageNum() {
		return currentPageNum;
	}
	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}	
	public ProcessTrainingStatus getProcessTrainingStatus() {
		return processTrainingStatus;
	}
	public void setProcessTrainingStatus(ProcessTrainingStatus processTrainingStatus) {
		this.processTrainingStatus = processTrainingStatus;
	}

	public List<NodeInstance> getNodeInstances() {
		return nodeInstances;
	}
	public void setNodeInstances(List<NodeInstance> nodeInstances) {
		this.nodeInstances = nodeInstances;
	}
	public List<Page> getPageList() {
		return pageList;
	}
	public void setPageList(List<Page> pageList) {
		this.pageList = pageList;
	}
	public List<Page> getFilterPageList() {
		return filterPageList;
	}
	public void setFilterPageList(List<Page> filterPageList) {
		this.filterPageList = filterPageList;
	}
	public int getFilterType() {
		return filterType;
	}
	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}
	public boolean isPause() {
		return isPause;
	}
	public void setPause(boolean isPause) {
		this.isPause = isPause;
	}
	public boolean getStoped() {
		return isStoped;
	}
	public void setStoped(boolean isStoped) {
		this.isStoped = isStoped;
	}
	public Map<String, String> getAnswerMap() {
		return answerMap;
	}
	public void setAnswerMap(Map<String, String> answerMap) {
		this.answerMap = answerMap;
	}
	public Map<String, Integer> getMarkMap() {
		return markMap;
	}
	public void setMarkMap(Map<String, Integer> markMap) {
		this.markMap = markMap;
	}
	public boolean isFilter() {
		return isFilter;
	}
	public void setFilter(boolean isFilter) {
		this.isFilter = isFilter;
	}
	public int getActualTime() {
		return actualTime;
	}
	public void setActualTime(int actualTime) {
		this.actualTime = actualTime;
	}
	public int getErrorItemNum() {
		return errorItemNum;
	}
	public void setErrorItemNum(int errorItemNum) {
		this.errorItemNum = errorItemNum;
	}
	public Map<String, CurrentAnswersStatus> getPreAnswersStatus() {
		return preAnswersStatus;
	}
	public void setPreAnswersStatus(
			Map<String, CurrentAnswersStatus> preAnswersStatus) {
		this.preAnswersStatus = preAnswersStatus;
	}
	public Map<String, Float> getStarMap() {
		return starMap;
	}
	public void setStarMap(Map<String, Float> starMap) {
		this.starMap = starMap;
	}
	public Map<String, Boolean> getRightMap() {
		return rightMap;
	}
	public void setRightMap(Map<String, Boolean> rightMap) {
		this.rightMap = rightMap;
	}
	public String getExamName() {
		return examName;
	}
	public void setExamName(String examName) {
		this.examName = examName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	public Boolean getIsFilter(){
		return this.isFilter;
	}
	public List<Float> getRightRateForPass() {
		return rightRateForPass;
	}
	public void setRightRateForPass(List<Float> rightRateForPass) {
		this.rightRateForPass = rightRateForPass;
	}
	public Map<String, CurrentAnswersStatus> getCurrentAnswersStatus() {
		return currentAnswersStatus;
	}
	public void setCurrentAnswersStatus(
			Map<String, CurrentAnswersStatus> currentAnswersStatus) {
		this.currentAnswersStatus = currentAnswersStatus;
	}
	public Map<String, HistoryAnswerStatus> getHistoryStatusMap() {
		return historyStatusMap;
	}
	public void setHistoryStatusMap(
			Map<String, HistoryAnswerStatus> historyStatusMap) {
		this.historyStatusMap = historyStatusMap;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public boolean getIsGroup(){
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public Float getItemScope() {
		return itemScope;
	}
	public void setItemScope(Float itemScope) {
		this.itemScope = itemScope;
	}
	public NodeInstance getExamNodeIns() {
		return examNodeIns;
	}
	public void setExamNodeIns(NodeInstance examNodeIns) {
		this.examNodeIns = examNodeIns;
	}
	public List<Section> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Section> sectionList) {
		this.sectionList = sectionList;
	}
	public int getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(int spendTime) {
		this.spendTime = spendTime;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public int getRightRate() {
		return rightRate;
	}
	public void setRightRate(int rightRate) {
		this.rightRate = rightRate;
	}
	public boolean isPass() {
		return isPass;
	}
	public boolean getIsPass(){
		return isPass;
	}
	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date examStartTime) {
		this.startTime = examStartTime;
	}	 
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Map<NodeInstance, CurrentTestStatus> getPreStatusMap() {
		return preStatusMap;
	}
	public void setPreStatusMap(Map<NodeInstance, CurrentTestStatus> preStatusMap) {
		this.preStatusMap = preStatusMap;
	}
	public Map<NodeInstance, Float> getPassRateMap() {
		return passRateMap;
	}
	public void setPassRateMap(Map<NodeInstance, Float> passRateMap) {
		this.passRateMap = passRateMap;
	}
	public Map<NodeInstance, Boolean> getTestResultMap() {
		return testResultMap;
	}
	public void setTestResultMap(Map<NodeInstance, Boolean> testResultMap) {
		this.testResultMap = testResultMap;
	}
	public Float getItemType() {
		return itemType;
	}
	public void setItemType(Float itemType) {
		this.itemType = itemType;
	}
	public int getRedoType() {
		return redoType;
	}
	public void setRedoType(int redoType) {
		this.redoType = redoType;
	}
	public int getTestStatus() {
		if (redoType!=0)		testStatus = 2;
		if (isWeaknessEnhance)	testStatus = 3;
		if (isExtPractice)		testStatus = 4;
		return testStatus;
	}
	public void setTestStatus(int testStatus) {
		this.testStatus = testStatus;
	}
	public boolean isExtPractice() {
		return isExtPractice;
	}
	public boolean getIsExtPractice() {
		return isExtPractice;
	}
	public void setExtPractice(boolean isExtPractice) {
		this.isExtPractice = isExtPractice;
	}
	public int getMarkPolicy() {
		return markPolicy;
	}
	public void setMarkPolicy(int markPolicy) {
		this.markPolicy = markPolicy;
	}
	public int getQuitTypeForView() {
		return quitTypeForView;
	}
	public void setQuitTypeForView(int quitTypeForView) {
		this.quitTypeForView = quitTypeForView;
	}
	public int getCurrentInsId() {
		return currentInsId;
	}
	public void setCurrentInsId(int currentInsId) {
		this.currentInsId = currentInsId;
	}
	public int getCurrentTestStatusId() {
		return currentTestStatusId;
	}
	public void setCurrentTestStatusId(int currentTestStatusId) {
		this.currentTestStatusId = currentTestStatusId;
	}	
	public int getHistoryTestStatusId() {
		return historyTestStatusId;
	}
	public void setHistoryTestStatusId(int historyTestStatusId) {
		this.historyTestStatusId = historyTestStatusId;
	}
	public int getRequireRightRate() {
		return requireRightRate;
	}
	public void setRequireRightRate(int requireRightRate) {
		this.requireRightRate = requireRightRate;
	}
	public int getAnalysisPolicy() {
		return analysisPolicy;
	}
	public void setAnalysisPolicy(int analysisPolicy) {
		this.analysisPolicy = analysisPolicy;
	}
	public boolean isWeaknessEnhance() {
		return isWeaknessEnhance;
	}
	public boolean getIsWeaknessEnhance() {
		return isWeaknessEnhance;
	}
	public void setWeaknessEnhance(boolean isWeaknessEnhance) {
		this.isWeaknessEnhance = isWeaknessEnhance;
	}
	public int getTestPass() {
		return testPass;
	}
	public void setTestPass(int testPass) {
		this.testPass = testPass;
	}
	public Float getTestScore() {
		return testScore;
	}
	public void setTestScore(Float testScore) {
		this.testScore = testScore;
	}
	public Map<String, Float> getSubjectScoreMap() {
		return subjectScoreMap;
	}
	public void setSubjectScoreMap(Map<String, Float> subjectScoreMap) {
		this.subjectScoreMap = subjectScoreMap;
	}
	public Map<String, Float> getScoreMap() {
		return scoreMap;
	}
	public void setScoreMap(Map<String, Float> scoreMap) {
		this.scoreMap = scoreMap;
	}	
	public Map<String, String> getScore2Map() {
		return score2Map;
	}
	public void setScore2Map(Map<String, String> score2Map) {
		this.score2Map = score2Map;
	}
	public Map<String, Boolean> getDoneMap() {
		return doneMap;
	}
	public void setDoneMap(Map<String, Boolean> doneMap) {
		this.doneMap = doneMap;
	}
	public Map<String, String> getAnswerOptionOrderMap() {
		return answerOptionOrderMap;
	}
	public void setAnswerOptionOrderMap(Map<String, String> answerOptionOrderMap) {
		this.answerOptionOrderMap = answerOptionOrderMap;
	}
	public String getExamTask() {
		return examTask;
	}
	public void setExamTask(String examTask) {
		this.examTask = examTask;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public TrainingPolicy getTrainPolicy() {
		return trainPolicy;
	}
	public void setTrainPolicy(TrainingPolicy trainPolicy) {
		this.trainPolicy = trainPolicy;
	}
	public Float getDifficultyValue() {
		return difficultyValue;
	}
	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}
	public int getDisableItemNum() {
		return disableItemNum;
	}
	public void setDisableItemNum(int disableItemNum) {
		this.disableItemNum = disableItemNum;
	}
	public int getTotalItemNum() {
		return totalItemNum;
	}
	public void setTotalItemNum(int totalItemNum) {
		this.totalItemNum = totalItemNum;
	}
	public ExamBeginProperty getExamBeginProperty() {
		return examBeginProperty;
	}
	public void setExamBeginProperty(ExamBeginProperty examBeginProperty) {
		this.examBeginProperty = examBeginProperty;
	}
	public ExamResultProperty getExamResultProperty() {
		return examResultProperty;
	}
	public void setExamResultProperty(ExamResultProperty examResultProperty) {
		this.examResultProperty = examResultProperty;
	}
	public int getAnswerPolicy() {
		return answerPolicy;
	}
	public void setAnswerPolicy(int answerPolicy) {
		this.answerPolicy = answerPolicy;
	}	
	public int getRandomAnswerOptionsPolicy() {
		return randomAnswerOptionsPolicy;
	}
	public void setRandomAnswerOptionsPolicy(int randomAnswerOptionsPolicy) {
		this.randomAnswerOptionsPolicy = randomAnswerOptionsPolicy;
	}
	public int getScorePolicy() {
		return scorePolicy;
	}
	public void setScorePolicy(int scorePolicy) {
		this.scorePolicy = scorePolicy;
	}
	public int getCompareAnswerPolicy() {
		return compareAnswerPolicy;
	}
	public void setCompareAnswerPolicy(int compareAnswerPolicy) {
		this.compareAnswerPolicy = compareAnswerPolicy;
	}
	public boolean isShowAnswer() {
		return showAnswer;
	}
	public void setShowAnswer(boolean showAnswer) {
		this.showAnswer = showAnswer;
	}	
	public String getProjectVersion() {
		return projectVersion;
	}
	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	public ExamType getExamTypePara() {
		return examTypePara;
	}
	public void setExamTypePara(ExamType examTypePara) {
		this.examTypePara = examTypePara;
	}
	public long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public ItemRevise getItemRevise() {
		return itemRevise;
	}
	public void setItemRevise(ItemRevise itemRevise) {
		this.itemRevise = itemRevise;
	}
	public int getRevising() {
		return revising;
	}
	public void setRevising(int revising) {
		this.revising = revising;
	}
	public List<Item> getItems(){
		List<Item> itemList = new ArrayList<Item>();
		for(Page page : pageList){
			itemList.addAll(page.getItems());
		}
		return itemList;
	}
}
