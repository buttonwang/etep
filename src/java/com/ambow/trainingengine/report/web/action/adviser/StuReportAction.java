/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.itembank.domain.DynamicAssembledPaper;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.report.service.adviser.AdviserService;
import com.ambow.trainingengine.report.service.adviser.UserAnswerService;
import com.ambow.trainingengine.report.web.data.ReportShowVO;
import com.ambow.trainingengine.util.CalculateUtil;
import com.ambow.trainingengine.util.DateUtil;
import com.ambow.trainingengine.util.MathUtil;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class StuReportAction extends AdviserBaseAction {

	protected HibernateGenericDao genService;
	
	private String userId = null;
	
	private String nodeId = null;
	
	protected AdviserService adviserService;
	
	protected UserAnswerService userAnswerService;
	
	private ReportService reportService;
	
	private MainPageService mainPageService;

	@Override
	public String execute() {
		int count =0;
		String retFlag="process";
		setRequestAttribute("userId", this.getUserId());
		Map userMap = this.getWebUserService().getUserDataById(this.getUserId());
		UserDataVO userData = new UserDataVO(
				this.getUserId(),
				(String)userMap.get("login_name"),
				(String)userMap.get("real_name"),
				(String)userMap.get("class_num"),
				(String)userMap.get("module"),
				((Long)userMap.get("process_definition_id")).toString(),
				(String)userMap.get("grade")
				);

		userData.setProcessInstanceId((Long)userMap.get("process_instance_id"));
		this.getMainPageService().getCurrentNodeInstanceInfoVO(userData);

		this.setRequestAttribute("userDataVO", userData);
		
		if(this.getNodeId() == null){
			return this.getProcessData(userData);
		}
		Map nodeMap = this.getAdviserService().getNodeMapByInstanceId(this.getNodeId());
		
		if(nodeMap.get("node_type").equals("PRACTICE")){
		
			Map stuUserMap = this.adviserService.getStuUserByNodeAndUser(this.getNodeId(), this.getUserId());
			if(stuUserMap.get("dyn_answering_time")!=null){
				stuUserMap.put("answering_time", stuUserMap.get("dyn_answering_time"));
			}
			setRequestAttribute("stuUserMap", stuUserMap);
			List historyRecordList = this.userAnswerService.getRecordByUserAndNode(this.getUserId(), this.getNodeId());
			setRequestAttribute("historyRecordList", historyRecordList);
			if(historyRecordList != null){
				count = historyRecordList.size();
				setRequestAttribute("historyRecordTotal", count);
			}
			retFlag="practice";
			return retFlag;
		}
		

		
		NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, Long.parseLong(this.getNodeId()));
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		if(nodeMap.get("node_type").equals(NodeType.GROUP.toString()))
				retFlag= this.getGroupData(userData,nodeInstance);
		
		//进入评测节点组统计方法中
		else if(nodeMap.get("node_type").equals(NodeType.EVALUATE.toString()))
			return this.getEvaluatingPage(userData, nodeInstance);
		
		//进入阶段测试节点组统计方法中
		else if(nodeMap.get("node_type").equals(NodeType.PHASETEST.toString()))
			return this.getPhasetestPage(userData, nodeInstance);
		
		//进入测试节点组统计方法中
		else if(nodeMap.get("node_type").equals(NodeType.UNITTEST.toString()))
			return this.getTestData(userData,nodeInstance);
		
		return retFlag;
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
		if(paperAssemblingPolicy.getPaperAssemblingMode()==0)
			this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
		else{//自动组卷
			List<DynamicAssembledPaper> dynamicAssembledPaperList=this.genService.find("from DynamicAssembledPaper where asfNodeInstance.id=?", nodeInstance.getId());
			if(dynamicAssembledPaperList.isEmpty())
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(paperAssemblingPolicy.getAnsweringTime()));
			else
				this.setRequestAttribute("answeringTime",DateUtil.timeFormat(dynamicAssembledPaperList.get(0).getAnsweringTime()));
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
		return "unittest";
	}	
	
	/**
	 * 装载study07.jsp阶段测试列表页面 
	 */		
	private String getPhasetestPage(UserDataVO userData,NodeInstance nodeInstance){
		List<ReportShowVO> list=null; 
		if(nodeInstance==null){
			list=this.reportService.getPhasetestShowVOList(userData, null);
			//this.setTagShow(userData, null);
		}
		else{
			list=this.reportService.getPhasetestShowVOList(userData, nodeInstance.getNode().getId());
			//this.setTagShow(userData,nodeInstance.getNode().getId());
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
		//this.setTagShow(userData, nodeGroupId);
		
		return "group";
	}
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public AdviserService getAdviserService() {
		return adviserService;
	}

	public void setAdviserService(AdviserService adviserService) {
		this.adviserService = adviserService;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public UserAnswerService getUserAnswerService() {
		return userAnswerService;
	}

	public void setUserAnswerService(UserAnswerService userAnswerService) {
		this.userAnswerService = userAnswerService;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
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
