package com.ambow.trainingengine.report.web.action;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.itembank.domain.DynamicAssembledPaper;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.report.web.data.EvaluateGroupShowVO;
import com.ambow.trainingengine.report.web.data.ReportShowVO;
import com.ambow.trainingengine.util.CalculateUtil;
import com.ambow.trainingengine.util.DateUtil;
import com.ambow.trainingengine.util.MathUtil;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;

@SuppressWarnings("serial")
public class StudyAction extends ReportBaseAction {
	
	private MainPageService mainPageService;
	
	private ReportService reportService;
	//节点实例Id
	private long nodeInstanceId=0;//0为整个流程
	
	private int showListType=0;//0 统计页面, 1 测试列表页，2回顾复习 3评测列表 4阶段测试列表
	
	protected HibernateGenericDao genService;
	
	public String execute() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		//进入整个流程统计方法中
		if(nodeInstanceId==0){
			if(showListType==0)
				return this.getProcessData(userData);
			else if(showListType==1)
				return this.getTestList(userData, null);
			else if(showListType==3)
				return this.getEvaluatingListPage(userData, null);
			else if(showListType==4)
				return this.getPhasetestPage(userData, null);
		}
		
		NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, this.nodeInstanceId);
		
		if(showListType==1)
			return this.getTestList(userData, nodeInstance);
		else if(showListType==2)
			return this.getReviewPage(nodeInstance);
		else if(showListType==3)
			return this.getEvaluatingListPage(userData, nodeInstance);
		else if(showListType==4)
			return this.getPhasetestPage(userData, nodeInstance);
		
		String nodeType=nodeInstance.getNode().getNodeType().toString();
		//进入某个节点组统计方法中
		if(nodeType.equals(NodeType.GROUP.toString()))
			return this.getGroupData(userData,nodeInstance);
		
		//进入训练节点组统计方法中
		if(nodeType.equals(NodeType.PRACTICE.toString()))
			return this.getPracticeData(userData,nodeInstance);
		
		//进入评测节点组统计方法中
		else if(nodeType.equals(NodeType.EVALUATE.toString()))
			return this.getEvaluatingPage(userData, nodeInstance);
		
		//进入阶段测试节点组统计方法中
		else if(nodeType.equals(NodeType.PHASETEST.toString()))
			return this.getPhasetestPage(userData, nodeInstance);
		
		//进入测试节点组统计方法中
		else if(nodeType.equals(NodeType.UNITTEST.toString()))
			return this.getTestData(userData,nodeInstance);
		
		else
		return SUCCESS;
	}
	/**
	 * 装载study01.jsp页面数据
	 * @param userData
	 * @return
	 */
	private String getProcessData(UserDataVO userData){
		//获得掌握度排名
		userData=this.getMainPageService().getTotalMasteryRateOrder(userData);
		this.setSessionObj(SessionDict.UserData, userData);
		List<ReportShowVO> list=this.reportService.getProcessReportShowVOList(userData);
		this.setRequestAttribute("reportShowVOList",list);
		ReportShowVO vo=new ReportShowVO();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				
				vo.setFirstTestScore(vo.getFirstTestScore()+reportShowVO.getFirstTestScore());
				vo.setScore(vo.getScore()+reportShowVO.getScore());
				vo.setTotalScore(vo.getTotalScore()+reportShowVO.getTotalScore());
				vo.setItemsNum(vo.getItemsNum()+reportShowVO.getItemsNum());
				vo.setSumIncorrectItems(vo.getSumIncorrectItems()+reportShowVO.getSumIncorrectItems());
				vo.setSumUnfinishedItems(vo.getSumUnfinishedItems()+reportShowVO.getSumUnfinishedItems());
				vo.setSumZeroStarItems(vo.getSumZeroStarItems()+reportShowVO.getSumZeroStarItems());
				vo.setSumHalfStarItems(vo.getSumHalfStarItems()+reportShowVO.getSumHalfStarItems());
				vo.setSumOneStarItems(vo.getSumOneStarItems()+reportShowVO.getSumOneStarItems());
				vo.setSumTwoStarItems(vo.getSumTwoStarItems()+reportShowVO.getSumTwoStarItems());
				vo.setSumThreeStarItems(vo.getSumThreeStarItems()+reportShowVO.getSumThreeStarItems());
				vo.setSumFourStarItems(vo.getSumFourStarItems()+reportShowVO.getSumFourStarItems());
				vo.setSumFiveStarItems(vo.getSumFiveStarItems()+reportShowVO.getSumFiveStarItems());
			}
			vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(), vo.getTotalScore())));
			vo.setFirstTestAccuracyRate(Float.valueOf(MathUtil.percent(vo.getFirstTestScore(), vo.getTotalScore())));
			vo.setMasteryRate(Float.valueOf(CalculateUtil.masteryRate(vo.getSumZeroStarItems(), vo.getSumHalfStarItems(),
					vo.getSumOneStarItems(), vo.getSumTwoStarItems(), vo.getSumThreeStarItems(),
					vo.getSumFourStarItems(),vo.getSumFiveStarItems(), vo.getItemsNum())));
		}
		//列表总的统计值
		this.setRequestAttribute("showVO",vo);
		
		//获取已经训练过的训练卷数
		this.setRequestAttribute("practiceDoNum", this.reportService.getPracticeDoNum(userData, null));
		//获取通过训练卷数
		this.setRequestAttribute("practicePassNum", this.reportService.getPracticePassNum(userData, null));
		//获取训练卷数
		this.setRequestAttribute("practiceNum", this.reportService.getPracticeNum(userData, null));
		
		//设置试题类型标签显示控制
		//this.setTagShow(userData, null);
		
		return "process";
	}
	/**
	 * 装载study03.jsp页面数据单元列表页
	 * @param userData
	 * @return
	 */
	private String getGroupData(UserDataVO userData,NodeInstance nodeInstance){
		Long nodeGroupId=nodeInstance.getNode().getId();		
		//获取已经训练过的训练卷数
		this.setRequestAttribute("practiceDoNum", this.reportService.getPracticeDoNum(userData, nodeGroupId));
		//获取通过训练卷数
		this.setRequestAttribute("practicePassNum", this.reportService.getPracticePassNum(userData, nodeGroupId));
		//获取训练卷数
		this.setRequestAttribute("practiceNum", this.reportService.getPracticeNum(userData, nodeGroupId));
		
		List<ReportShowVO> list=this.reportService.getUnitReportShowVOList(userData,nodeGroupId);
		this.setRequestAttribute("reportShowVOList",list);
		ReportShowVO vo=new ReportShowVO();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				
				vo.setFirstTestScore(vo.getFirstTestScore()+reportShowVO.getFirstTestScore());
				vo.setScore(vo.getScore()+reportShowVO.getScore());
				vo.setTotalScore(vo.getTotalScore()+reportShowVO.getTotalScore());
				vo.setItemsNum(vo.getItemsNum()+reportShowVO.getItemsNum());
				vo.setSumIncorrectItems(vo.getSumIncorrectItems()+reportShowVO.getSumIncorrectItems());
				vo.setSumCorrectItems(vo.getSumCorrectItems()+reportShowVO.getSumCorrectItems());
				vo.setUnsureMarkItems(vo.getUnsureMarkItems()+reportShowVO.getUnsureMarkItems());
				vo.setSumUnfinishedItems(vo.getSumUnfinishedItems()+reportShowVO.getSumUnfinishedItems());
				vo.setSumZeroStarItems(vo.getSumZeroStarItems()+reportShowVO.getSumZeroStarItems());
				vo.setSumHalfStarItems(vo.getSumHalfStarItems()+reportShowVO.getSumHalfStarItems());
				vo.setSumOneStarItems(vo.getSumOneStarItems()+reportShowVO.getSumOneStarItems());
				vo.setSumTwoStarItems(vo.getSumTwoStarItems()+reportShowVO.getSumTwoStarItems());
				vo.setSumThreeStarItems(vo.getSumThreeStarItems()+reportShowVO.getSumThreeStarItems());
				vo.setSumFourStarItems(vo.getSumFourStarItems()+reportShowVO.getSumFourStarItems());
				vo.setSumFiveStarItems(vo.getSumFiveStarItems()+reportShowVO.getSumFiveStarItems());
			}
			vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(), vo.getTotalScore())));
			vo.setFirstTestAccuracyRate(Float.valueOf(MathUtil.percent(vo.getFirstTestScore(), vo.getTotalScore())));
			vo.setMasteryRate(Float.valueOf(CalculateUtil.masteryRate(vo.getSumZeroStarItems(), vo.getSumHalfStarItems(),
					vo.getSumOneStarItems(), vo.getSumTwoStarItems(), vo.getSumThreeStarItems(),
					vo.getSumFourStarItems(),vo.getSumFiveStarItems(), vo.getItemsNum())));
			vo.setSumfinishedItems(vo.getSumCorrectItems()+vo.getSumIncorrectItems());
		}
		//列表总的统计值
		this.setRequestAttribute("showVO",vo);
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		//设置试题类型标签显示控制
		this.setTagShow(userData, nodeGroupId);
		
		return "group";
	}
	/**
	 * 装载study04.jsp训练页面数据
	 * @param userData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getPracticeData(UserDataVO userData,NodeInstance nodeInstance){
		this.setRequestAttribute("nodeInstance", nodeInstance);
		CurrentTestStatus currentTestStatus=this.genService.get(CurrentTestStatus.class, nodeInstance.getId());
		PaperAssemblingPolicy paperAssemblingPolicy=this.genService.get(PaperAssemblingPolicy.class, nodeInstance.getNode().getId());
		TrainingPolicy trainingPolicy=this.genService.get(TrainingPolicy.class, nodeInstance.getNode().getId());
		//手动组卷
		if(paperAssemblingPolicy.getPaperAssemblingMode()==0){
			this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
			this.setRequestAttribute("itemNum",paperAssemblingPolicy.getItems_num());
		}
		else{//自动组卷
			List<DynamicAssembledPaper> dynamicAssembledPaperList=this.genService.find("from DynamicAssembledPaper where asfNodeInstance.id=?", nodeInstance.getId());
			if(dynamicAssembledPaperList.isEmpty()){
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
				this.setRequestAttribute("itemNum",paperAssemblingPolicy.getItems_num());
			}else{
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(dynamicAssembledPaperList.get(0).getAnsweringTime()));
				this.setRequestAttribute("itemNum",dynamicAssembledPaperList.get(0).getItemsNum());
			}
		}
		this.setRequestAttribute("paperAssemblingPolicy",paperAssemblingPolicy);
		this.setRequestAttribute("nodeStatus", nodeInstance.getNodeStatus().toInt());
		if(currentTestStatus==null){
			//currentTestStatus=new CurrentTestStatus();
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRightRateForPass());
			return "practice";
		}
	
		if(currentTestStatus.getTestStatus()>=1&&currentTestStatus.getIsTest()==false)
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRightRateRetraining());
		if(currentTestStatus.getIsTest()==true)
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRetrainingRightRateTestFaile());
		this.setRequestAttribute("timeUsed", DateUtil.timeFormat(currentTestStatus.getTimeUsed()));
		this.setRequestAttribute("currentTestStatus",currentTestStatus);
	
		List<ReportShowVO> list=this.reportService.getPracticeHistoryReportShowVOList(nodeInstanceId);
		this.setRequestAttribute("reportShowVOList",list);
		
		return "practice";
	}
	/**
	 * 装载study05.jsp页面数据
	 * @param userData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getTestData(UserDataVO userData,NodeInstance nodeInstance){
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		CurrentTestStatus currentTestStatus=this.genService.get(CurrentTestStatus.class, nodeInstance.getId());
		PaperAssemblingPolicy paperAssemblingPolicy=this.genService.get(PaperAssemblingPolicy.class, nodeInstance.getNode().getId());
		TrainingPolicy trainingPolicy=this.genService.get(TrainingPolicy.class, nodeInstance.getNode().getId());
		
		//手动组卷
		if(paperAssemblingPolicy.getPaperAssemblingMode()==0){
			this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
			this.setRequestAttribute("itemNum",paperAssemblingPolicy.getItems_num());
		}else{//自动组卷
			List<DynamicAssembledPaper> dynamicAssembledPaperList=this.genService.find("from DynamicAssembledPaper where asfNodeInstance.id=?", nodeInstance.getId());
			if(dynamicAssembledPaperList.isEmpty()){
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
				this.setRequestAttribute("itemNum",paperAssemblingPolicy.getItems_num());
			}else{
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(dynamicAssembledPaperList.get(0).getAnsweringTime()));
				this.setRequestAttribute("itemNum",dynamicAssembledPaperList.get(0).getItemsNum());
			}
		}
		
		this.setRequestAttribute("paperAssemblingPolicy",paperAssemblingPolicy);
		//判断此节点是否通过，通过显示回顾复习标签页面上并可以点击重练
		this.setRequestAttribute("nodeStatus", nodeInstance.getNodeStatus().toInt());
		if(currentTestStatus==null){
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRightRateForPass());
			return "unit_test";
		}
		if(currentTestStatus.getTestStatus()>=1&&currentTestStatus.getIsTest()==false)
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRightRateRetraining());
		if(currentTestStatus.getIsTest()==true)
			this.setRequestAttribute("rightRateForPass", trainingPolicy.getRetrainingRightRateTestFaile());
		this.setRequestAttribute("timeUsed", DateUtil.timeFormat(currentTestStatus.getTimeUsed()));
		this.setRequestAttribute("currentTestStatus",currentTestStatus);
		return "unit_test";
	}	
	/**
	 * 装载study02.jsp页面数据测试列表也
	 * @param userData
	 * @return
	 */
	private String getTestList(UserDataVO userData,NodeInstance nodeInstance){
		Long nodeGroupId=0l;
		if(nodeInstance!=null)
			nodeGroupId=nodeInstance.getNode().getId();
		List<ReportShowVO> list=this.reportService.getTestShowVOList(userData, nodeGroupId);
		this.setRequestAttribute("reportShowVOList",list);
		ReportShowVO vo=new ReportShowVO();
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				
				vo.setFirstTestScore(vo.getFirstTestScore()+reportShowVO.getFirstTestScore());
				vo.setScore(vo.getScore()+reportShowVO.getScore());
				vo.setTotalScore(vo.getTotalScore()+reportShowVO.getTotalScore());
				vo.setItemsNum(vo.getItemsNum()+reportShowVO.getItemsNum());
				vo.setSumIncorrectItems(vo.getSumIncorrectItems()+reportShowVO.getSumIncorrectItems());
				vo.setSumUnfinishedItems(vo.getSumUnfinishedItems()+reportShowVO.getSumUnfinishedItems());
				vo.setSumZeroStarItems(vo.getSumZeroStarItems()+reportShowVO.getSumZeroStarItems());
				vo.setSumHalfStarItems(vo.getSumHalfStarItems()+reportShowVO.getSumHalfStarItems());
				vo.setSumOneStarItems(vo.getSumOneStarItems()+reportShowVO.getSumOneStarItems());
				vo.setSumTwoStarItems(vo.getSumTwoStarItems()+reportShowVO.getSumTwoStarItems());
				vo.setSumThreeStarItems(vo.getSumThreeStarItems()+reportShowVO.getSumThreeStarItems());
				vo.setSumFourStarItems(vo.getSumFourStarItems()+reportShowVO.getSumFourStarItems());
				vo.setSumFiveStarItems(vo.getSumFiveStarItems()+reportShowVO.getSumFiveStarItems());
				vo.setStarNum(vo.getStarNum()+reportShowVO.getStarNum());
				vo.setFirstTestScore(vo.getFirstTestScore()+reportShowVO.getFirstTestScore());
			}	
			vo.setMasteryRate(Float.valueOf(CalculateUtil.masteryRate(vo.getSumZeroStarItems(), vo.getSumHalfStarItems(),
					vo.getSumOneStarItems(), vo.getSumTwoStarItems(), vo.getSumThreeStarItems(),
					vo.getSumFourStarItems(),vo.getSumFiveStarItems(), vo.getItemsNum())));
			vo.setScore(vo.getScore()/list.size());
			vo.setFirstTestScore(vo.getFirstTestScore()/list.size());
		}
		this.setRequestAttribute("showVO",vo);
		this.setTagShow(userData, nodeGroupId);
		return "test_list";
	}
	
	/**
	 * 装载study06.jsp回顾复习页面数据
	 * @param userData
	 * @return
	 */
	private String getReviewPage(NodeInstance nodeInstance){
		this.setRequestAttribute("nodeInstance", nodeInstance);
		CurrentTestStatus currentTestStatus=this.genService.get(CurrentTestStatus.class, nodeInstance.getId());
		if(currentTestStatus==null)
			currentTestStatus=new CurrentTestStatus();
		this.setRequestAttribute("currentTestStatus",currentTestStatus);
		int historyId=this.reportService.gethistoryTestStatusMaxId(nodeInstanceId);
		HistoryTestStatus historyTestStatus=this.genService.get(HistoryTestStatus.class,historyId);
		this.setRequestAttribute("historyTestStatus",historyTestStatus);
		return "review";
	}
	/**
	 * 装载study07.jsp阶段测试列表页面 
	 */		
	private String getPhasetestPage(UserDataVO userData,NodeInstance nodeInstance){
		List<ReportShowVO> list=null; 
		if(nodeInstance==null){
			list=this.reportService.getPhasetestShowVOList(userData, null);
			this.setTagShow(userData, null);
		}
		else{
			list=this.reportService.getPhasetestShowVOList(userData, nodeInstance.getNode().getId());
			this.setTagShow(userData,nodeInstance.getNode().getId());
		}
		this.setRequestAttribute("reportShowVOList",list);
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		return "phasetest";
	}
	/**
	 * 装载study08.jsp一个节点评测页面
	 */
	private String getEvaluatingPage(UserDataVO userData,NodeInstance nodeInstance){
		List<ReportShowVO> list=null;
		list=this.reportService.getEvluatingShowVOList(userData, nodeInstance.getNode().getId());
		ReportShowVO vo=new ReportShowVO();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				vo.setScore(vo.getScore()+reportShowVO.getScore());
				vo.setTotalScore(vo.getTotalScore()+reportShowVO.getTotalScore());
			}
			vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(),vo.getTotalScore())));
		}else{
			vo.setScore(0f);
			vo.setTotalScore(0f);
			vo.setAccuracyRate(0f);
		}
		this.setRequestAttribute("showVO", vo);
		this.setRequestAttribute("reportShowVOList",list);
		this.setRequestAttribute("nodeInstance", nodeInstance);
		this.setRequestAttribute("evaluate",1);
		return "evaluate";
	}

/**
 * 装载study09.jsp多个个节点评测页面
 */
private String getEvaluatingListPage(UserDataVO userData,NodeInstance nodeInstance){
	Long nodeGroupId=0l;
	if(nodeInstance!=null)
		nodeGroupId=nodeInstance.getNode().getId();
	this.setTagShow(userData, nodeGroupId);
	List<ReportShowVO> list=this.reportService.getEvluatingShowVOList(userData, nodeGroupId);
	List<EvaluateGroupShowVO> outList=new ArrayList<EvaluateGroupShowVO>();
	
	
	//获取组id列表
	List<Long> nodeGroupIdList=new ArrayList<Long>();
	Long tempNodeGroupId=null;
	for(int i=0;i<list.size();i++){
		ReportShowVO vo=list.get(i);
		if(tempNodeGroupId==null||tempNodeGroupId.longValue()!=vo.getNodeGroupId().longValue()){
			nodeGroupIdList.add(vo.getNodeGroupId());
			tempNodeGroupId=vo.getNodeGroupId();
		}
	}
	for(int j=0;j<nodeGroupIdList.size();j++){
		Long groupId=nodeGroupIdList.get(j);
		EvaluateGroupShowVO vo=new EvaluateGroupShowVO();
		List<ReportShowVO> tempList=new ArrayList<ReportShowVO>();
		//Map<String,ReportShowVO> tempKnowledgePointMap=new Hashtable<String,ReportShowVO>();		
		/*for(int k=0;k<list.size();k++){
			ReportShowVO vo1=list.get(k);
			if(tempKnowledgePointMap.get(vo1.getKnowledgePointCode())==null&&groupId.longValue()==vo1.getNodeGroupId().longValue())
				tempKnowledgePointMap.put(vo1.getKnowledgePointCode(),vo1);
		}
		
		for(String knowledgePointCode :tempKnowledgePointMap.keySet()){
			boolean tag=true;//是否有第二次测评
			for(int k=0;k<list.size();k++){
				ReportShowVO vo2=list.get(k);
				if(knowledgePointCode.equals(vo2.getKnowledgePointCode())&&groupId.longValue()==vo2.getNodeGroupId().longValue()){
					ReportShowVO tempVO=tempKnowledgePointMap.get(knowledgePointCode);
					if(tempVO.getNodeId().longValue()!=vo2.getNodeId().longValue()){
						tempVO.setScoreTwo(vo2.getScore());
						tempVO.setTotalScoreTwo(vo2.getTotalScore());
						tempVO.setAccuracyRateTwo(vo2.getAccuracyRate());
						tempList.add(tempVO);
						tag=false;
						break;
					}
				}
			}
			if(tag)
				tempList.add(tempKnowledgePointMap.get(knowledgePointCode));
		}*/
		//获取此单元中最全的知识点列表
		Map<String,String> tempKnowledge=new Hashtable<String,String>();
		long firstNodeInstanceId=0l;
		boolean tag=true;
		for(int k=0;k<list.size();k++){
			ReportShowVO vo1=list.get(k);
			if(groupId.longValue()==vo1.getNodeGroupId().longValue()){
				tempKnowledge.put(vo1.getKnowledgePointCode(),vo1.getKnowledgePointName());
				if(tag){
					firstNodeInstanceId=vo1.getNodeInstanceId().longValue();
					tag=false;
				}
			}
		}
		for(String knowledgePointCode:tempKnowledge.keySet()){
			ReportShowVO tempVo=new ReportShowVO();
			tempVo.setScore(-1f);
			tempVo.setScoreTwo(-1f);
			tempVo.setTotalScore(-1f);
			tempVo.setTotalScoreTwo(-1f);
			tempVo.setAccuracyRate(-1f);
			tempVo.setAccuracyRateTwo(-1f);
			tempVo.setKnowledgePointCode(knowledgePointCode);
			tempVo.setKnowledgePointName(tempKnowledge.get(knowledgePointCode));
			for(int k=0;k<list.size();k++){
				ReportShowVO vo2=list.get(k);
				if(knowledgePointCode.equals(vo2.getKnowledgePointCode())
						&&groupId.longValue()==vo2.getNodeGroupId().longValue()
						&&firstNodeInstanceId==vo2.getNodeInstanceId().longValue()){
					tempVo.setScore(vo2.getScore());
					tempVo.setTotalScore(vo2.getTotalScore());
					tempVo.setAccuracyRate(vo2.getAccuracyRate());
				}
				else if(knowledgePointCode.equals(vo2.getKnowledgePointCode())
						&&groupId.longValue()==vo2.getNodeGroupId().longValue()
						&&firstNodeInstanceId!=vo2.getNodeInstanceId().longValue()){
					tempVo.setScoreTwo(vo2.getScore());
					tempVo.setTotalScoreTwo(vo2.getTotalScore());
					tempVo.setAccuracyRateTwo(vo2.getAccuracyRate());
				}
			}
			tempList.add(tempVo);
		}
				
		//获取求和信息
		ReportShowVO sumVO=new ReportShowVO();
		sumVO.setScore(-1f);
		sumVO.setScoreTwo(-1f);
		sumVO.setTotalScore(-1f);
		sumVO.setTotalScoreTwo(-1f);
		sumVO.setAccuracyRate(-1f);
		sumVO.setAccuracyRateTwo(-1f);
		boolean tag1=true,tag2=true;
		for(int i=0;i<tempList.size();i++){
			ReportShowVO reportShowVO=tempList.get(i);
			if(reportShowVO.getTotalScore()>-1){
				if(tag1){
					sumVO.setScore(0f);
					sumVO.setTotalScore(0f);
					tag1=false;
				}
					
				sumVO.setScore(sumVO.getScore()+reportShowVO.getScore());
				sumVO.setTotalScore(sumVO.getTotalScore()+reportShowVO.getTotalScore());
			}
			if(reportShowVO.getTotalScoreTwo()>-1){
				if(tag2){
					sumVO.setScoreTwo(0f);
					sumVO.setTotalScoreTwo(0f);
					tag2=false;
				}
				sumVO.setScoreTwo(sumVO.getScoreTwo()+reportShowVO.getScoreTwo());
				sumVO.setTotalScoreTwo(sumVO.getTotalScoreTwo()+reportShowVO.getTotalScoreTwo());
			}
		}
		if(sumVO.getTotalScore()>-1)
			sumVO.setAccuracyRate(Float.valueOf(MathUtil.percent(sumVO.getScore(), sumVO.getTotalScore())));
		
		if(sumVO.getTotalScoreTwo()>-1)
			sumVO.setAccuracyRateTwo(Float.valueOf(MathUtil.percent(sumVO.getScoreTwo(), sumVO.getTotalScoreTwo())));
		
		
		
		vo.setKnowledgePointList(tempList);
		vo.setTotalInfo(sumVO);
		vo.setNodeGroupId(groupId);
		vo.setNodeGroupName(((Node)this.genService.get(Node.class, groupId)).getName());
		outList.add(vo);
	}
	this.setRequestAttribute("evaluateGroupShowVOList", outList);
	return "evaluateList";
}
	/**
	 *设置试题类型标签显示控制
	 * @return
	 */
	public void setTagShow(UserDataVO userData,Long nodeGroupId){
		if(nodeGroupId==null||nodeGroupId==0){
			this.setRequestAttribute("evaluate",1);
			this.setRequestAttribute("phasetest",1);
			this.setRequestAttribute("practice",1);
			this.setRequestAttribute("unittest",1);
			
		}else{
		String str=this.reportService.getNodeTypesStr(userData,nodeGroupId);
		this.setRequestAttribute("evaluate", str.indexOf(NodeType.EVALUATE.toString()));
		this.setRequestAttribute("phasetest", str.indexOf(NodeType.PHASETEST.toString()));
		this.setRequestAttribute("practice", str.indexOf(NodeType.PRACTICE.toString()));
		this.setRequestAttribute("unittest", str.indexOf(NodeType.UNITTEST.toString()));
		}
	}
	public ReportService getReportService() {
		return reportService;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	public long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}

	public int getShowListType() {
		return showListType;
	}

	public void setShowListType(int showListType) {
		this.showListType = showListType;
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
	public HibernateGenericDao getGenService() {
		return genService;
	}
	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	
}
