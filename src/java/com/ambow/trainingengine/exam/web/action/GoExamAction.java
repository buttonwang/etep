package com.ambow.trainingengine.exam.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatus;
import com.ambow.trainingengine.exam.domain.PauseInfo;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamFlowService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.util.ExamType;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
/*
 * GoExamAction.java
 * 
 * Created on Jul 2, 2008 10:11:22 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 单节点
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class GoExamAction extends BaseAction {
	
	private static final long serialVersionUID = 339013484853115008L;

	Logger log = Logger.getLogger(this.getClass());
	
	private String nodeInstanceId;
	
	private int examType;
	
	private int isPause; //1为暂停
	
	private Float scope;//过滤0-5星级 11未答 12错题  -1全部
	
	private HibernateGenericDao genService;
	
	private ExamItemService examItemService;
	
	private ExamAnswerService examAnswerService;
	
	private ExamFlowService examFlowService;

	public String execute(){
		//正式进入考试的标记
		this.setSessionObj("startExamTag", false);
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControlProxy viewControl =  new ViewControlProxy(
			userDataVO.getProcessPolicy().getIsShowAnswer(),
			userDataVO.getProcessPolicy().getProjectVersion(),
			userDataVO.getProcessInstanceId());
		
		viewControl.setShowModel(1);
		
		if (examType==3)	viewControl.setWeaknessEnhance(true); //弱项强化
		if (examType==6)	viewControl.setExtPractice(true); 	  //拓展练习
		if (examType==6)	viewControl.setRedoType(1); 	  	  //拓展练习重练
		if (scope!=null) 	viewControl.setItemType(scope);		  //出题类型
		
		getProcessInfo(viewControl, userDataVO);
		
		getNodesInfo(viewControl, userDataVO);
		
		examItemService.setTrainPolicy(viewControl); //设置正确率条件显示策略 
		
		viewControl.initExamData();
		
		if (isPause!=1)	viewControl.generateMap();
		
		List<Item> examItemsList = getItemsInfo(viewControl);
		
		viewControl.initViewData(examItemsList);
		
		getKnowledgePoints(viewControl);		//知识点
				
		setSessionObj(SessionDict.ViewControl, viewControl);
		
		if (examType==4) {return "skip";}
		
		callPlatform(userDataVO);
		
		return (isPause==1)? "overview" : SUCCESS ;
		
	}

	private void callPlatform(UserDataVO userDataVO) {
		//回调平台
		//UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		try {
			com.ambow.evaluating.web.callback.RedPlatformPlugin.Start(this.getHttpServletRequest(),
					userDataVO.getRefID(), " ", Math.round(userDataVO.getLearningProcessRate()), " ",
					0,0,0,0,0);
		} catch (IOException e) {
			log.info("error when start callback start!");
			e.printStackTrace();
		}
	}

	@Override
	public String getAuthStr() {
		return null;
	}

	public int getExamType() {
		return examType;
	}

	public void setExamType(int examType) {
		this.examType = examType;
	}

	public int getIsPause() {
		return isPause;
	}

	public void setIsPause(int isPause) {
		this.isPause = isPause;
	}

	public String getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(String nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
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

	public ExamFlowService getExamFlowService() {
		return examFlowService;
	}

	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}

	public Float getScope() {
		return scope;
	}

	public void setScope(Float scope) {
		this.scope = scope;
	}
	
	/**
	 *08-08-04 移动至此 
	 *从暂停状态中恢复过来 
	 */
	public void resume(List<Item> examItemsList,ViewControl viewControl){
		
		PauseInfo pauseInfo=examAnswerService.getPauseInfo(viewControl.getProcessInstance());
		if(pauseInfo.getNodeInstanceId()==0)viewControl.setWeaknessEnhance(true);
		List<PauseAnswerStatus> totalPauseAnswers=new ArrayList<PauseAnswerStatus>();
		for(NodeInstance nodeInstance:viewControl.getNodeInstances()){
			List<PauseAnswerStatus> status=examAnswerService.getPauseAnswers(viewControl.getProcessInstance(), nodeInstance);
			totalPauseAnswers.addAll(status);
		}
		Set<Item> itemSet=new HashSet<Item>();
		Set<SubItem> subItemSet=new HashSet<SubItem>();
		Map<String,String> answerStrMap=new HashMap<String,String>();
		Map<String,Integer> markMap=new HashMap<String,Integer>();
		Map<String,String> answerOptionOrderMap=new HashMap<String,String>();
		String mapKey=null;
		for(PauseAnswerStatus pauseStatus:totalPauseAnswers){
			pauseStatus.getItem().setNodeInstanceId(pauseStatus.getNodeInstanceId());//把要用的NodeInstanceId再Set回去
			itemSet.add(pauseStatus.getItem());
			subItemSet.add(pauseStatus.getSubItem());
			mapKey=ExamUtil.getMapKey(pauseStatus.getItem(), pauseStatus.getSubItem());
			answerStrMap.put(mapKey, pauseStatus.getAnswer());
			markMap.put(mapKey, pauseStatus.getIsUnsureMarking()?1:0);
			answerOptionOrderMap.put(mapKey, pauseStatus.getAnswerOptionOrder());
		}
				
		if (viewControl.getProjectVersion().equals("ky")) {
			for(Item item:itemSet){
				if(item.getSubItems().size()>0){
					for(SubItem subItem:item.getSubItems()){
						if (subItemSet.contains(subItem)){
							subItem.setEnable(true);
						}else{
							subItem.setEnable(false);
						}
					}
				}
			}
		}
		examItemsList.addAll(itemSet);
		viewControl.setAnswerMap(answerStrMap);
		viewControl.setMarkMap(markMap);
		viewControl.setAnswerOptionOrderMap(answerOptionOrderMap);
		viewControl.setPause(true);
		viewControl.setStartTime(pauseInfo.getStartTestTime());
		viewControl.setItemType(pauseInfo.getPaperAssemItemType());
		viewControl.setActualTime(pauseInfo.getTimeLeft());
		viewControl.setCurrentPageNum(pauseInfo.getCurrentTestPageNum());
		viewControl.setRedoType(pauseInfo.getIsTested());
		viewControl.setTestStatus(pauseInfo.getTestStatus());
	}
	
	//设置ViewControl的流程信息
	private void getProcessInfo(ViewControl viewControl, UserDataVO userDataVO) {
		ProcessInstance processInstance = 
			(ProcessInstance)genService.get(ProcessInstance.class, userDataVO.getProcessInstanceId());
		ProcessTrainingStatus processTrainingStatus = 
			(ProcessTrainingStatus)genService.get(ProcessTrainingStatus.class, userDataVO.getProcessInstanceId());
		
		viewControl.setProcessInstance(processInstance);
		viewControl.setProcessTrainingStatus(processTrainingStatus);
		
		if (examFlowService.isPause(processInstance)) {		
			isPause=1;
			viewControl.setPause(true);
		}
	}
	
	//设置ViewControl的节点信息
	private void getNodesInfo(ViewControl viewControl,UserDataVO userData){
		long id = 0;
		if (nodeInstanceId==null) id = 0;
		else if (nodeInstanceId.trim().equals("")) id = 0;
		else id = Long.parseLong(nodeInstanceId); 
		
		List<NodeInstance> nodeInstances=new ArrayList<NodeInstance>();
		if (id==0) {
			//整个流程的弱项强化
			nodeInstances.addAll(examFlowService.getAllPracticeNode(viewControl.getProcessInstance()));
		} else {
			NodeInstance nodeInstance=genService.get(NodeInstance.class, id);
			viewControl.setExamNodeIns(nodeInstance);
			if((nodeInstance.getNode().getNodeType()==NodeType.GROUP) && (!viewControl.isExtPractice())){
				nodeInstances.addAll(examFlowService.getPracticeNodeFromGroup(
						examFlowService.getChildNodeIds(viewControl, userData),viewControl.getProcessInstance()));
			} else {
				nodeInstances.add(nodeInstance);
			}
		}
		
		viewControl.setNodeInstances(nodeInstances);
		
		viewControl.setPreAnswersStatus(examAnswerService.getCurrentAnswerMap(viewControl.getNodeInstances()));
		viewControl.setPreStatusMap(examAnswerService.getCurrentTestMap(viewControl.getNodeInstances()));
				
		if(examType!=ExamType.weekEnhance.getValue()&&examType!=ExamType.extPractice.getValue()
			&&viewControl.getNodeInstances().size()==1) {
			CurrentTestStatus testStatus=viewControl.getPreStatusMap().get(viewControl.getExamNodeIns());
			//判断是否是redo
			if(testStatus!=null) viewControl.setRedoType(testStatus.getIsTest()?2:1);
		}
	}
		
	//设置ViewControl的试题信息
	private List<Item> getItemsInfo(ViewControlProxy viewControl) {
		List<Item> examItemsList=new ArrayList<Item>();
		if(!viewControl.isExtPractice()&&(isPause==1)) {
			this.resume(examItemsList, viewControl);
		} else {
			examItemService.fetchItemList(examItemsList, viewControl);
		}
		return examItemsList;
	}
	
	/**
	 * 评测节点取得此节点下试题的知识点集合
	 */
	private void getKnowledgePoints(ViewControlProxy viewControl) {
		if (viewControl.getProjectVersion().equals("mpc")) {
			if (viewControl.getExamTypePara()==ExamType.evaluate) {
				examItemService.genKnowledgePoints(viewControl);
			}
		}
	}
}
