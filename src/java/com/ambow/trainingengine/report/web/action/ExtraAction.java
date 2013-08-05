package com.ambow.trainingengine.report.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatusEx;
import com.ambow.trainingengine.exam.domain.PauseInfoEx;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.util.ExamType;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;
import com.ambow.trainingengine.report.service.ExtraService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
public class ExtraAction extends ReportBaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private HibernateGenericDao genService;
	private ExamAnswerService examAnswerService;
	private ExamItemService examItemService;
	private ExtraService extraService;

	private Long nodeGroupId;
	private Integer num;

	public String execute() {
		return doExecute();
	}
	
	private String doExecute(){
		String retStr = null;
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ProcessInstance processInstance = (ProcessInstance)genService.get(ProcessInstance.class, userDataVO.getProcessInstanceId());
		ViewControlProxy viewControl =  new ViewControlProxy(
				userDataVO.getProcessPolicy().getIsShowAnswer(),
				userDataVO.getProcessPolicy().getProjectVersion(),
				userDataVO.getProcessInstanceId());
		viewControl.setFlowName(processInstance.getProcessDefinition().getName());
		viewControl.setExtPractice(true);
		NodeInstance currentNodeInstance = this.getExtraService().fectchNodeInstance(userDataVO.getProcessInstanceId(),nodeGroupId);
		String chapterName = currentNodeInstance.getNode().getNodeGroup()==null?viewControl.getFlowName():currentNodeInstance.getNode().getNodeGroup().getName();
		viewControl.setChapterName(chapterName);
		viewControl.setSectionName(currentNodeInstance.getNode().getName());
		viewControl.setExamName("拓展练习");
		viewControl.setExamTask("拓展练习");
		viewControl.setExamType("拓展练习");
		viewControl.setExamTypePara(ExamType.extPractice);
		viewControl.setExamNodeIns(currentNodeInstance);
		
		setTrainingPolicy(viewControl);
		getNodesInfo(viewControl);
		setSessionObj(SessionDict.ViewControl, viewControl);
		
		List<Item> examItemsList=new ArrayList<Item>();
		int hisNum = 0;
		boolean isPause = examAnswerService.hasPauseInfoEx(currentNodeInstance);
		if (isPause) {
			resumeItems(examItemsList, viewControl);
		} else {
			String ids = getItems(userDataVO,currentNodeInstance);
			if (!ids.trim().equals("")) examItemsList = extraService.fetchItemListForMpc(ids);
			CurrentTestStatus currentTestStatus = this.getExtraService().getCurrentTestStatus(currentNodeInstance);
			List<HistoryTestStatus> historyTestStatusList = this.getExtraService().getHisTestStatus(currentNodeInstance);
			hisNum = historyTestStatusList.size();
			this.setRequestAttribute("currentTestStatus", currentTestStatus);
			this.setRequestAttribute("historyTestStatusList", historyTestStatusList);
		}
		
		overView(userDataVO, viewControl, examItemsList);
		if(isPause){
			retStr= "pause";
		}else if(hisNum>0){
			retStr= "proceed";
		}else{
			retStr= "main";
		}
		return retStr;
	}
	
	//设置ViewControl的节点信息
	private void getNodesInfo(ViewControl viewControl){
		List<NodeInstance> nodeInstances=new ArrayList<NodeInstance>();
		nodeInstances.add(viewControl.getExamNodeIns());
		viewControl.setNodeInstances(nodeInstances);
		
		viewControl.setPreAnswersStatus(examAnswerService.getCurrentAnswerMap(viewControl.getNodeInstances()));
		viewControl.setPreStatusMap(examAnswerService.getCurrentTestMap(viewControl.getNodeInstances()));
	}
	
	public void setTrainingPolicy(ViewControl viewControl) {
		viewControl.setAnalysisPolicy(2);
		viewControl.setAnswerPolicy(2);
		viewControl.setMarkPolicy(1);
		viewControl.setRandomAnswerOptionsPolicy(0);
		viewControl.setScorePolicy(1);
	}

	private String getItems(UserDataVO userDataVO,NodeInstance currentNodeInstance) {
		Set<Map<String,Object>> items = new HashSet<Map<String,Object>>();
		List<NodeInstance> nodeInstanceList = this.getExtraService().fetchNodeInstanceList(userDataVO.getProcessInstanceId(), nodeGroupId);
		String nodeInstanceIds = "";
		for(int i=0;i<nodeInstanceList.size();i++){
			NodeInstance nodeInstance = nodeInstanceList.get(i);
			if(i==0){
				nodeInstanceIds = nodeInstance.getId()+"";
			}else{
				nodeInstanceIds = nodeInstanceIds + ","+nodeInstance.getId();
			}
		}
		for (NodeInstance nodeInstance : nodeInstanceList) {
			Node node = nodeInstance.getNode();
			//获取组卷策略
			PaperAssemblingPolicy assPolicy = this.getExamItemService().fetchAssemblePolicy(node);
			//获取组卷模式
			int mode = assPolicy.getPaperAssemblingMode(); // 0手工1动态组卷2动态出题
			
			if(mode == 1 || (mode > 10 && mode < 20) || mode == 2 || (mode > 20 && mode < 30)){
				List<PaperAssemblingRequirements> paperAssemblingRequirementsList = this.getExtraService().fetchPaperAssemblingRequirementsList(node.getId());
				
				List<Map<String,Object>> itemList = this.getExtraService().fetchItemListForMpc(paperAssemblingRequirementsList,nodeInstanceIds,currentNodeInstance.getId());
				items.addAll(itemList);
			}
		}
		List<Map<String,Object>> newItemList = new ArrayList<Map<String,Object>>(items);
		if(num !=null){
			newItemList = newItemList.subList(0, num);
		}
		Float allScore = 0f;
		Integer allAnsweringTime = 0;
		String ids = "";
		int i=0;
		for(Map<String,Object> itemMap:newItemList)
		{
			if(i==0){
				ids = ((Integer)itemMap.get("id")).toString();
			}else{
				ids = ids+","+((Integer)itemMap.get("id")).toString();
			}
			Float score = (Float)itemMap.get("score");
			Integer answeringTime = (Integer)itemMap.get("answering_time");
			allScore = allScore+score;
			allAnsweringTime = allAnsweringTime +answeringTime;
			i = i+1;
		}
		this.setRequestAttribute("allSize", items.size());
		this.setRequestAttribute("currentSize", newItemList.size());
		this.setRequestAttribute("allScore", allScore.intValue());
		this.setRequestAttribute("allAnsweringTime", getAnsweringTime(allAnsweringTime));
		this.setRequestAttribute("nodeGroupId", nodeGroupId);
		this.setRequestAttribute("num", num);
		this.setSessionObj("ids", ids);
		return ids;
	}

	private void overView(UserDataVO userDataVO, ViewControlProxy viewControl, List<Item> examItemsList) {
		this.setSessionObj("startExamTag", false);
		viewControl.setShowModel(1);
		ProcessInstance processInstance=this.getExtraService().getProcessInstance(userDataVO);
		ProcessTrainingStatus processTrainingStatus = this.getExtraService().getProcessTrainingStatus(userDataVO);
		viewControl.setProcessInstance(processInstance);
		viewControl.setProcessTrainingStatus(processTrainingStatus);
		viewControl.initViewData(examItemsList);
		setSessionObj(SessionDict.ViewControl, viewControl);
	}
	
	private String getAnsweringTime(int answeringTime){
		int hour = answeringTime/(60*60);
		int minute = answeringTime/60%60;
		if(hour==0){
			return minute+"分钟";
		}else{
			return hour+"小时"+minute+"分钟";
		}
	}
	

	
	/**
	 *从暂停状态中恢复过来 
	 */
	public void resumeItems(List<Item> examItemsList,ViewControl viewControl){
		PauseInfoEx pauseInfo = examAnswerService.getPauseInfoEx(viewControl.getExamNodeIns());
		if(pauseInfo.getNodeInstanceId()==0) viewControl.setWeaknessEnhance(true);
		List<PauseAnswerStatusEx> totalPauseAnswers = examAnswerService.getPauseAnswersEx(viewControl.getExamNodeIns());
		
		Set<Item> itemSet=new HashSet<Item>();
		Set<SubItem> subItemSet=new HashSet<SubItem>();
		Map<String,String> answerStrMap=new HashMap<String,String>();
		Map<String,Integer> markMap=new HashMap<String,Integer>();
		Map<String,String> answerOptionOrderMap=new HashMap<String,String>();
		String mapKey=null;
		for(PauseAnswerStatusEx pauseStatus: totalPauseAnswers){
			pauseStatus.getItem().setNodeInstanceId(pauseStatus.getNodeInstance().getId());
			itemSet.add(pauseStatus.getItem());
			subItemSet.add(pauseStatus.getSubItem());
			mapKey=ExamUtil.getMapKey(pauseStatus.getItem(), pauseStatus.getSubItem());
			answerStrMap.put(mapKey, pauseStatus.getAnswer());
			markMap.put(mapKey, pauseStatus.getIsUnsureMarking()?1:0);
			answerOptionOrderMap.put(mapKey, pauseStatus.getAnswerOptionOrder());
		}
		examItemsList.addAll(itemSet);
		viewControl.setAnswerMap(answerStrMap);
		viewControl.setMarkMap(markMap);
		viewControl.setPause(true);
		viewControl.setStartTime(pauseInfo.getStartTestTime());
		viewControl.setActualTime(pauseInfo.getTimeLeft());
		viewControl.setCurrentPageNum(pauseInfo.getCurrentTestPageNum());
		viewControl.setItemType(pauseInfo.getPaperAssemItemType());
		viewControl.setExamType(pauseInfo.getPaperAssemItemTypeName());
		viewControl.setRedoType(pauseInfo.getIsTested());
	}
	
	public void info() throws IOException{
		doExecute();
		String str = this.getHttpServletRequest().getAttribute("currentSize")+","
					+this.getHttpServletRequest().getAttribute("allScore")+","
					+this.getHttpServletRequest().getAttribute("allAnsweringTime");
		this.getHttpServletResponse().getWriter().write(str);
	}

	
	public ExamItemService getExamItemService() {
		return examItemService;
	}
	public void setExamItemService(ExamItemService examItemService) {
		this.examItemService = examItemService;
	}

	public ExtraService getExtraService() {
		return extraService;
	}

	public void setExtraService(ExtraService extraService) {
		this.extraService = extraService;
	}
	public Long getNodeGroupId() {
		return nodeGroupId;
	}

	public void setNodeGroupId(Long nodeGroupId) {
		this.nodeGroupId = nodeGroupId;
	}
	
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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

}
