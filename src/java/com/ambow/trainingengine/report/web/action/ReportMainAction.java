package com.ambow.trainingengine.report.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.report.service.ConsolidateService;
import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.PauseInfoVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;

@SuppressWarnings("serial")
public class ReportMainAction extends ReportBaseAction {
	
	private ReportService reportService;
	
	private MainPageService mainPageService;
	
	private ConsolidateService consolidateService;
	
	private long nodeInstanceId=0;
	
	private int showListType=0;//0 统计页面, 1 测试列表页，2回顾复习 3评测列表 4阶段测试列表
	
	private String orderNum;
	
	private String nodeType;
	
	private Integer hisId;

	public Integer getHisId() {
		return hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
	}

	public String execute() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		List<NodeVO> nodeList=this.reportService.getNodeListForTree(userData);
		
		this.setRequestAttribute("nodeList",nodeList);
		
		if(nodeInstanceId>0){
			NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, this.nodeInstanceId);
			Long nodeGroupId=0l;
			if(nodeInstance.getNode().getNodeGroup()!=null){
				nodeGroupId=nodeInstance.getNode().getNodeGroup().getId();
				nodeGroupId=this.mainPageService.getShowNodeGroupId(nodeGroupId);
			}
			this.setRequestAttribute("nodeGroupId", nodeGroupId);
		}
		return SUCCESS;
	}
	
	/**
	 * mpc弱项强化
	 * @return
	 */
	public String mpcMenu(){
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControl viewControl = (ViewControl)this.getSessionObj(SessionDict.ViewControl);
		long processInstanceId = userData.getProcessInstanceId();

		if(nodeInstanceId !=0){
			//if(viewControl.getExamTypePara().equals("extPractice")){
				this.setRequestAttribute("defaultNodeInstance",  "100001"+nodeInstanceId);
			//}
//			else{
//				NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, this.nodeInstanceId);
//				this.setRequestAttribute("defaultNodeInstance", nodeInstance.getNode().getId());
//			}
		}
		List<Map<String,Object>> oldNodeList = this.reportService.getNodeList(processInstanceId);
		
		Integer maxOrderNum = reportService.getMaxNodeGrounpLength(processInstanceId);
		
		if(oldNodeList.size()>0){

			Set<String> set = new HashSet<String>();
			for(int i=0;i<oldNodeList.size();i++){
				Map<String,Object> nodeMap = oldNodeList.get(i);
				String orderNum = (String)nodeMap.get("order_num");
				add(set,orderNum);
			}
			String orderNumArr = toString(set);
			List<Map<String,Object>> nodeList = this.reportService.getNodeList(processInstanceId, orderNumArr);
			
			PauseInfoVO vo = (PauseInfoVO)this.getSessionObj("pauseInfoVO");
			if(vo != null && vo.getNodeInstanceId() !=0){
				NodeInstance nodeInstance = this.getReportService().getNodeInstance(vo.getNodeInstanceId());
				this.setRequestAttribute("nodeInstance", nodeInstance);
			}
			
			List<Map<String,Object>> newNodeList = new ArrayList<Map<String,Object>>();
			Map<String,Object> oldNodeMap = null;
			for(int i=0;i<nodeList.size();i++){
				Map<String,Object> nodeMap = nodeList.get(i);
				if(nodeMap.get("order_num").toString().length()<=maxOrderNum && oldNodeMap != null){
					Map<String,Object> newNodeMap = new HashMap<String,Object>();
					newNodeMap.put("node_id", "100001"+ oldNodeMap.get("node_id"));
					newNodeMap.put("node_group_id", oldNodeMap.get("node_id"));
					newNodeMap.put("name", "拓展练习");
					newNodeMap.put("node_status", 0);
					newNodeMap.put("node_type", "EXTRA");
					newNodeMap.put("order_num", oldNodeMap.get("order_num"));
					newNodeList.add(newNodeMap);
					oldNodeMap = null;
				}
				newNodeList.add(nodeMap);
				if(nodeMap.get("node_type").equals("GROUP") && nodeMap.get("order_num").toString().length()==maxOrderNum){
					Integer amount = this.getReportService().getNodeCount(processInstanceId, (Long)nodeMap.get("node_id"));
					if(amount ==0){
						oldNodeMap = nodeMap;
					}
				}
			}
			
			if(oldNodeMap != null){
				Map<String,Object> newNodeMap = new HashMap<String,Object>();
				newNodeMap.put("node_id", "100001"+ oldNodeMap.get("node_id"));
				newNodeMap.put("node_group_id", oldNodeMap.get("node_id"));
				newNodeMap.put("name", "拓展练习");
				newNodeMap.put("node_status", 0);
				newNodeMap.put("node_type", "EXTRA");
				newNodeMap.put("order_num", oldNodeMap.get("order_num"));
				newNodeList.add(newNodeMap);
				oldNodeMap = null;
			}
			this.setRequestAttribute("nodeList", newNodeList);
		}
		return "menu";
	}
	
	private Set<String> add(Set<String> set ,String orderNum){
		String[] orderNumArr = orderNum.split(",");
		for(int i=0;i<orderNumArr.length;i++){
			set.add("'"+orderNum.substring(0,3*(i+1)-1)+"'");
		}
		return set;
	}
	
	public String toString(Set<String> set){
		String retStr = "";
		Object[] objArr = set.toArray();
		for(int i=0;i<objArr.length;i++){
			Object obj = objArr[i];
			if(i==0){
				retStr = retStr + obj;
			}else{
				retStr = retStr +","+ obj;
			}
		}
		return retStr;
	}
	
	public ReportService getReportService() {
		return reportService;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
	public void setShowListType(int showListType) {
		this.showListType = showListType;
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
	public MainPageService getMainPageService() {
		return mainPageService;
	}
	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public ConsolidateService getConsolidateService() {
		return consolidateService;
	}

	public void setConsolidateService(ConsolidateService consolidateService) {
		this.consolidateService = consolidateService;
	}
}
