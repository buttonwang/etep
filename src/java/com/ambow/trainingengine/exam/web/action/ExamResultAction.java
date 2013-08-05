package com.ambow.trainingengine.exam.web.action;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.studyflow.service.impl.ProcessService;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamFlowService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.stat.IStat;
import com.ambow.trainingengine.exam.stat.StatFactory;
import com.ambow.trainingengine.exam.web.data.ResultShowVO;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.report.web.data.ReportShowVO;
import com.ambow.trainingengine.studyflow.service.ProcessDefinitionService;
import com.ambow.trainingengine.util.MathUtil;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
/*
 * ExamResultAction.java
 * 
 * Created on Jul 2, 2008 10:31:09 AM
 * 
 * 前一
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
@SuppressWarnings("serial")
public class ExamResultAction extends BaseAction {
	
	private ExamAnswerService examAnswerService;
	
	private ExamItemService examItemService;
	
	private ProcessService processService;
	
	private ExamFlowService examFlowService;	

	private ReportService reportService;
	
	private MainPageService mainPageService;
	
	private ProcessDefinitionService processDefinitionService;
	
	private StatFactory statFactory;

	@SuppressWarnings("unchecked")
	public String execute() throws IllegalAccessException, InvocationTargetException{
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControlProxy viewControl=(ViewControlProxy)this.getHttpSessionObj(SessionDict.ViewControl);
				
		//List<Page> pages=viewControl.getPageList();
		//examAnswerService.saveExamAnswers(pages, viewControl);
		
		//重构 按项目实现不同的接口
		IStat stat = statFactory.getStatImpl(viewControl.getProjectVersion());
		stat.saveAnswers(viewControl);
		
		viewControl.setShowModel(2);//逐题浏览 mode
		
		if (viewControl.isPause()) {	//在暂停的情况下
			examFlowService.updateProcessStatus(viewControl.getProcessInstanceId(), ProcessStatus.RUNNING);
			examFlowService.deletePauseInfo(viewControl.getProcessInstance().getId());
			this.getSession().remove("pauseInfoVO");
		}
		
		if (viewControl.isExtPractice()) {	//在拓展训练的情况下删除节点的暂存信息并直接返回
			examFlowService.deletePauseInfoEx(viewControl.getExamNodeIns().getId());
			return "EXTPRATICE";
		}
		
		// 数理化直接在试题中取答题信息
		if (viewControl.getProjectVersion().equals("ky")) {
			viewControl.generateMap();
			viewControl.setPages();
		}
		
		//在此驱动学习流
		NodeInstance nodeInstance=null;
		NodeStatus nodeStatus=null;
		List<NodeInstance> examNodeInstances=viewControl.getNodeInstances();
		Map<NodeInstance,Boolean> resultMap=viewControl.getTestResultMap();
		Map<NodeInstance,CurrentTestStatus> testStatusMap=viewControl.getPreStatusMap();// 当前每个节点的考试结果存在这里
		Boolean pass=null;
		//判断此次考试是否为训练类型
		CurrentTestStatus testStatus=null;
		
		for(int i=0;i<examNodeInstances.size();i++){
			nodeInstance=examNodeInstances.get(i);
			pass=resultMap.get(nodeInstance);
			if (pass==null) pass = false;
			if(!viewControl.getIsWeaknessEnhance())	
				viewControl.setPass(pass);//不是分析强化，则把是否通过丢到页面上去。。。
			if(pass) nodeStatus=NodeStatus.PASSED;
			else nodeStatus=NodeStatus.NOPASS;
			if(nodeInstance.getNode().getNodeType()==NodeType.EVALUATE||nodeInstance.getNode().getNodeType()==NodeType.PHASETEST){
				//对于阶段测试与评测来说没有不通过
				nodeStatus=NodeStatus.PASSED;
			}
			//测试代码。。
			if (viewControl.getTestPass()==1) {
				nodeStatus=NodeStatus.NOPASS;
			}
			if (viewControl.getTestPass()==2) {
				nodeStatus=NodeStatus.PASSED;
			}
			
			if (viewControl.getProcessInstance().getNode()==null||
			   nodeInstance.getNode().getId()!=viewControl.getProcessInstance().getNode().getId()) {			
				if(nodeStatus==NodeStatus.PASSED&&nodeInstance.getNodeStatus()==NodeStatus.NOPASS)
					examFlowService.changeStatusById(nodeInstance, nodeStatus);
			} else {
				//在当前节点,驱动工作流引擎奔向下一个节点
				Map<String,String> examResultMap=new HashMap<String,String>();
				testStatus=testStatusMap.get(nodeInstance);
				examResultMap.put("score", testStatus.getScore().toString());// 参数MAP，将跳转所需要的数据传给决策器
				examResultMap.put("rightRate", testStatus.getAccuracyRate().toString());
				examResultMap.put("projectVersion", viewControl.getProjectVersion());
				//获取显示策略节点所在组					
				if (userData.getShowNodeGroupVOList()!=null)
					for(ShowNodeGroupVO vo:userData.getShowNodeGroupVOList()){
						if(vo.getChildNodeIds().indexOf(String.valueOf(nodeInstance.getNode().getId()))>-1){
							examResultMap.put("showNodeGroupVONodeIds", vo.getChildNodeIds());
							break;
						}
					}		
				if(!viewControl.getIsWeaknessEnhance()||viewControl.getIsWeaknessEnhance()&&nodeStatus==NodeStatus.PASSED)
					processService.runProcessInstance(viewControl.getProcessInstance().getId(),nodeStatus,examResultMap);
			}
			
		}
		NodeInstance currentNodeIns=viewControl.getExamNodeIns();//注意，流程强化时，该对象可能为空
		
		//当前节点是阶段测试,处理
		if(currentNodeIns!=null&&currentNodeIns.getNode().getNodeType()==NodeType.PHASETEST){
			//阶段测试跳转信息
			this.setSessionObj("jumpTo", 
					this.reportService.instanceNodeWhereToJump(currentNodeIns, viewControl.getRightRate()));
			this.setSessionObj("phaseTestNodesList", 
					this.reportService.getNodesOfPhaseTest(currentNodeIns));
		}
		
		//当前节点是单元测试，且没有通过，此处还需将这些practice的node的值的currentStatus状态值设为isTest设为不过。
		if(currentNodeIns!=null&&currentNodeIns.getNode().getNodeType()==NodeType.UNITTEST&&nodeStatus==NodeStatus.NOPASS){
			List<Node> nodes=processDefinitionService.getPracticeNodeListInSamelevel(currentNodeIns.getNode());
			String redoInfo=" ";//
			for(Node node:nodes){
				redoInfo=redoInfo+"<li> "+node.getName()+"<br/>";
			}
			this.setSessionObj("redoInfo", redoInfo);
			examFlowService.changeTestStatus(viewControl.getProcessInstance().getId(), nodes);//更改训练的isTest属性值
			
			//如果是mpc版本把后测控制的所有训练的动态试卷状态全都置成0
			if(viewControl.getProjectVersion().equals("mpc"))
				examItemService.updateDynamicAssembledPaper(nodeInstance);
		}
		
		
		
		//判断此次考试是否为评测,如果是的话保存知识点
		if(currentNodeIns!=null&&currentNodeIns.getNode().getNodeType()==NodeType.EVALUATE){
			stat.statKPoint(viewControl);
			
			if (viewControl.getProjectVersion().equals("ky")) { 
				resultEvaluateForKY(userData, nodeInstance);
			}	
		}
			
		ResultShowVO vo=new ResultShowVO();
		
		//如果是训练便重新统计数据 可能是训练以及弱项强化。。
		if (viewControl.getProjectVersion().equals("ky")) {
			if((currentNodeIns!=null&&currentNodeIns.getNode().getNodeType()==NodeType.PRACTICE)||
				viewControl.isWeaknessEnhance()){
			
				vo=this.reportService.updateProcessTrainingStatus(userData,vo);
				//获取整体排名
				vo.setProcessScoreOrderNum(this.mainPageService.getScoreOrdeNum(userData));
				vo.setProcessMasteryRateOrderNum(this.mainPageService.getTotalMasteryRateOrder(userData).getTotalMasteryRateOrder());
			
				//判断是否为整个流程的弱项强化？带完善
				
				//获取当前所属单元的统计信息
				
				vo=this.reportService.getGroupReport(userData, vo, examNodeInstances);
				
				//return NodeType.PRACTICE.name();
				this.getHttpServletRequest().setAttribute("statvo", vo);
				this.setSessionObj("statvo", vo);
			}
			this.reportService.updateProcessNoTrainingStatus(userData);
			callPlatform(viewControl, userData);
		}
		else if (viewControl.getProjectVersion().equals("mpc")) {
			this.reportService.updateProcessTrainingStatusForMpc(userData);
			callPlatform(viewControl, userData);
		}
		
		
		//验证所有节点是否全为通过状态，全通过则更新流程状态为“完成”状态（-1）
		boolean stoped = this.reportService.hasProcessInstanceStop(userData);
		
		if (stoped) {
			viewControl.setStoped(true);
		} else { //返回当前节点的统计信息
			if (viewControl.getProjectVersion().equals("mpc")) {
				NodeInstance nextNodeIns = examFlowService.getNodeInstance(viewControl.getProcessInstanceId(),
					processService.getCurrentNodeByProcessId(viewControl.getProcessInstanceId()).getId());
			
				//	下一节点实例的名称、编号
				if (nextNodeIns.getNode().getNodeGroup().getNodeGroup() != null)
					this.setSessionObj("nextNodeChapterName", nextNodeIns.getNode().getNodeGroup().getNodeGroup().getName());
				else 
					this.setSessionObj("nextNodeChapterName", viewControl.getFlowName());
				this.setSessionObj("nextNodeSectionName", nextNodeIns.getNode().getNodeGroup().getName());
				this.setSessionObj("nextNodeName", nextNodeIns.getNode().getName());
				this.setSessionObj("nextNodeId",  nextNodeIns.getId());
				
				this.setSessionObj("studyGuide",  examFlowService.getStudyGuide(nodeInstance.getNode().getId()));
			} else  if (viewControl.getProjectVersion().equals("ky")){
				this.setSessionObj("currentTestStatus", viewControl.getPreStatusMap().get(currentNodeIns));
			}
		}
		
		if(viewControl.isWeaknessEnhance()||currentNodeIns.getNode().getNodeType()==NodeType.GROUP) {		
			if (viewControl.getProjectVersion().equals("mpc")) 
				return "WEAKNESSENHANCE";
			else if (viewControl.getProjectVersion().equals("ky"))
				return NodeType.PRACTICE.name();//如果是弱项强化则借用训练的UI界面
		}
		return currentNodeIns.getNode().getNodeType().name();
	}

	private void resultEvaluateForKY(UserDataVO userData, NodeInstance nodeInstance) {
		//examAnswerService.statKPoint(viewControl);
		List<ReportShowVO> list=null;
		list=this.reportService.getEvluatingShowVOList(userData, nodeInstance.getNode().getId());
		ReportShowVO vo2=new ReportShowVO();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				vo2.setScore(vo2.getScore()+reportShowVO.getScore());
				vo2.setTotalScore(vo2.getTotalScore()+reportShowVO.getTotalScore());
			}
			vo2.setAccuracyRate(Float.valueOf(MathUtil.percent(vo2.getScore(),vo2.getTotalScore())));
		}else{
			vo2.setScore(0f);
			vo2.setTotalScore(0f);
			vo2.setAccuracyRate(0f);
		}
		this.setSessionObj("showVO", vo2);
		this.setSessionObj("reportShowVOList",list);
		//this.setRequestAttribute("nodeInstance", nodeInstance);
	}

	private void resultEvaluateForMPC(UserDataVO userData, NodeInstance nodeInstance) {
		List<ReportShowVO> list=null;
		list=this.reportService.getEvluatingShowVOList(userData, nodeInstance.getNode().getId());
		ReportShowVO vo2=new ReportShowVO();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				ReportShowVO reportShowVO=list.get(i);
				vo2.setScore(vo2.getScore()+reportShowVO.getScore());
				vo2.setTotalScore(vo2.getTotalScore()+reportShowVO.getTotalScore());
			}
			vo2.setAccuracyRate(Float.valueOf(MathUtil.percent(vo2.getScore(),vo2.getTotalScore())));
		}else{
			vo2.setScore(0f);
			vo2.setTotalScore(0f);
			vo2.setAccuracyRate(0f);
		}
		this.setSessionObj("showVO", vo2);
		this.setSessionObj("reportShowVOList",list);
		//this.setRequestAttribute("nodeInstance", nodeInstance);
	}
	
	private void callPlatform(ViewControl viewControl, UserDataVO userData) {
		//回调平台
		try {
			if (viewControl.getProjectVersion().equals("mpc")) {
				com.ambow.evaluating.web.callback.RedPlatformPlugin.End(
						this.getHttpServletRequest(),Math.round(userData.getLearningProcessRate()),
						viewControl.getSpendTime(),  Math.round(userData.getTotalScore()),
						viewControl.getExamResultProperty().getAllStarCount(),
						viewControl.getExamResultProperty().getErrorCount(),
						viewControl.getExamResultProperty().getMarkCount(),
						viewControl.getExamResultProperty().getAccuracyRateInt(),
						0
						);
			} else if (viewControl.getProjectVersion().equals("ky")) {
				com.ambow.evaluating.web.callback.RedPlatformPlugin.End(
						this.getHttpServletRequest(),Math.round(userData.getLearningProcessRate()),
						viewControl.getSpendTime(),  Math.round(userData.getTotalScore()),
						0,
						0,
						0,
						0,
						0
						);
			}
		} catch (IOException e) {
			//System.out.println("Error when End callback!!");
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExamAnswerService getExamAnswerService() {
		return examAnswerService;
	}

	public void setExamAnswerService(ExamAnswerService examAnswerService) {
		this.examAnswerService = examAnswerService;
	}
	
	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public ExamFlowService getExamFlowService() {
		return examFlowService;
	}

	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
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

	public ProcessDefinitionService getProcessDefinitionService() {
		return processDefinitionService;
	}

	public void setProcessDefinitionService(
			ProcessDefinitionService processDefinitionService) {
		this.processDefinitionService = processDefinitionService;
	}
		
	public StatFactory getStatFactory() {
		return statFactory;
	}

	public void setStatFactory(StatFactory statFactory) {
		this.statFactory = statFactory;
	}

	public ExamItemService getExamItemService() {
		return examItemService;
	}

	public void setExamItemService(ExamItemService examItemService) {
		this.examItemService = examItemService;
	}

}
