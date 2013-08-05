package com.ambow.trainingengine.exam.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatusEx;
import com.ambow.trainingengine.exam.domain.PauseInfo;
import com.ambow.trainingengine.exam.domain.PauseInfoEx;
import com.ambow.trainingengine.exam.util.ExamType;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ExamFlowService.java
 * 
 * Created on Jul 2, 2008 8:37:43 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class ExamFlowService {
	
	private HibernateGenericDao genService;
	
	/*
	 *根据NodeInstanceId修改其状态
	 *注意:此处修改的是非当前节点! 
	 */
	public void changeStatusById(NodeInstance nodeInstance,NodeStatus nodeStatus){
		nodeInstance.setNodeStatus(nodeStatus);
		genService.save(nodeInstance);
	}
	
	/*
	 * 修改节点状态,暂停..
	 */
	public void updateProcessStatus(ProcessInstance processInstance,ProcessStatus processStatus){
		processInstance.setProcessStatus(processStatus);
		genService.save(processInstance);
	}
	
	/*
	 * 修改节点状态,暂停.. 重新get一次
	 */
	public void updateProcessStatus(long processInstanceId, ProcessStatus processStatus){
		ProcessInstance processInstance = genService.get(ProcessInstance.class, processInstanceId);
		processInstance.setProcessStatus(processStatus);
		genService.save(processInstance);
	}
	
	@SuppressWarnings("unchecked")
	public void deletePauseInfo(long processInstanceId) {		
		PauseInfo pause = genService.get(PauseInfo.class, processInstanceId);
		
		if (pause!=null) {
			genService.remove(pause);
			
			String hql2="from PauseAnswerStatus a where a.asfProcessInstance.id=" + processInstanceId;
			List<PauseAnswerStatus> pauseAnswerStatusList = genService.find(hql2);
			genService.removeAll(pauseAnswerStatusList);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deletePauseInfoEx(long nodeInstanceId) {		
		PauseInfoEx pause = genService.get(PauseInfoEx.class, nodeInstanceId);
		
		if (pause!=null) {
			genService.remove(pause);
			
			String hql2="from PauseAnswerStatusEx a where a.nodeInstance.id=" + nodeInstanceId;
			List<PauseAnswerStatusEx> pauseAnswerStatusList = genService.find(hql2);
			genService.removeAll(pauseAnswerStatusList);
		}
	}
	
	/*
	 * 如果是Node直接返回,如果是group则
	 * 新版本方法
	 * TODO: 考虑重构
	 */
	@Deprecated
	public void getNodesIns(Long id,ViewControl viewControl,UserDataVO userData){
		
		List<NodeInstance> nodeInstances=new ArrayList<NodeInstance>();
		if (id==0) {
			//整个流程的弱项强化
			nodeInstances.addAll(getAllPracticeNode(viewControl.getProcessInstance()));
			String name = viewControl.getProcessInstance().getProcessDefinition().getName()+" 弱项强化";
			viewControl.setExamName(name);			
		} else {
			NodeInstance nodeInstance=genService.get(NodeInstance.class, id);
						
			viewControl.setExamNodeIns(nodeInstance);
			if(nodeInstance.getNode().getNodeType()==NodeType.GROUP){
				
				viewControl.setExamName(nodeInstance.getNode().getName()+" 弱项强化");
				
				nodeInstances.addAll(this.getPracticeNodeFromGroup(
						this.getChildNodeIds(viewControl, userData),viewControl.getProcessInstance()));
			} else {
				nodeInstances.add(nodeInstance);
				viewControl.setExamName(nodeInstance.getNode().getName());
				viewControl.setExamType(ExamUtil.getTypeCHName(viewControl.getProjectVersion(), nodeInstance.getNode().getNodeType()));
				viewControl.setExamTypePara(ExamType.getExamTypeByNodeType(nodeInstance.getNode().getNodeType()));
				viewControl.setSectionName(nodeInstance.getNode().getNodeGroup().getName());
				viewControl.setChapterName(nodeInstance.getNode().getNodeGroup().getNodeGroup().getName());
			}
		}
		
		if(viewControl.isWeaknessEnhance())	viewControl.setExamType("弱项强化");
		
		viewControl.setNodeInstances(nodeInstances);
	}
	
	/**
	 * 根据
	 * @param ids
	 * @param processInstance
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getChildNodeIds(ViewControl viewControl,UserDataVO userData){
		String ids="";
		if(viewControl.getProjectVersion().equals("ky")){
			if (userData.getShowNodeGroupVOList()!=null)
			  for(ShowNodeGroupVO vo: userData.getShowNodeGroupVOList()){
			  //	if(vo.getNodeId().longValue()==userData.getUnitNodeId()){
				  if(vo.getNodeId().longValue()==viewControl.getExamNodeIns().getNode().getId()){
			    	ids=vo.getChildNodeIds();
			    	break;
			   }
			  }
		}
		else if(viewControl.getProjectVersion().equals("mpc")){
			Node groupNode=viewControl.getExamNodeIns().getNode();
			/*获取该节点所有训练子节点*/
			String hql="select praNode.id from PracticeNode praNode where praNode.orderNum like '"
				+groupNode.getOrderNum()
				+"%' and praNode.processDefinition.id="+
				+groupNode.getProcessDefinition().getId()
				+" order by praNode.orderNum";
			List<Long> insList=this.genService.find(hql);
			for(Long id:insList){
				if(ids.trim().length()>0) 
					ids=ids+",";
				ids=ids+id;
			}
		}
		return ids;
	}
	
	
	/*
	 *取得节点组的所有训练的实例 2008-08-25
	 */
	public List<NodeInstance> getPracticeNodeFromGroup(String ids,ProcessInstance processInstance){
		//List<NodeInstance> resultList=new ArrayList<NodeInstance>();
		String hql="select nodeIns.id from NodeInstance nodeIns,PracticeNode praNode where nodeIns.processInstance.id="+processInstance.getId()+" and praNode.id in( "+ids+" ) and nodeIns.node.id=praNode.id order by praNode.orderNum";
		List<Long> insList=this.genService.find(hql);
		String idStr="";
		for(Long id:insList){
			if(idStr.trim().length()>0) idStr=idStr+",";
			idStr=idStr+id;
		}
		String hql2="select ins from NodeInstance ins, CurrentTestStatus testStatus where ins.id=testStatus.asfNodeInstance.id and ins.id in("+idStr+")";
		List<NodeInstance> instances=this.genService.find(hql2);
		return instances;
	}
	
	/*
	 * 取得目前流程所有做过的节点实例
	 */
	public List<NodeInstance> getAllPracticeNode(ProcessInstance processInstance){
		String hql="select ins from NodeInstance ins,CurrentTestStatus testStatus,PracticeNode praNode where ins.processInstance.id=? and ins.node.id=praNode.id and testStatus.asfNodeInstance.id=ins.id";
		List<NodeInstance> nodeInstances=genService.find(hql, processInstance.getId());
		return nodeInstances;
	}
		
	
	/*
	 * 
	 */
	public boolean isPause(ProcessInstance processInstance){
		boolean flag = false;
		ProcessStatus processStatus=processInstance.getProcessStatus();
		if (processStatus==ProcessStatus.SUSPEND_PAUSE)	flag = true;
		
		PauseInfo pause = genService.get(PauseInfo.class, processInstance.getId());
		if (pause!=null) flag = true;
		return flag;
	}
	
	/*
	 * 考试未通过时，修改TestStatus的isTest属性
	 */
	public void changeTestStatus(long processId, List<Node> nodeList){
		String idStr="";
		for(Node node:nodeList){
			if(idStr.trim().length()>0) idStr=idStr+",";
			idStr=idStr+node.getId();
		}
		String idHql="select ins.id from NodeInstance ins where ins.processInstance.id="+processId+" and ins.node.id in("+idStr+")";
		List<Long> idList=genService.find(idHql);
		
		String idStr1="";
		for(Long id:idList){
			if(idStr1.trim().length()>0) idStr1=idStr1+",";
			idStr1=idStr1+id;
		}
		String hql="update CurrentTestStatus testStatus set testStatus.isTest=1 where testStatus.nodeInstanceId in("+idStr1+")";
		genService.excuteHql(hql);
		
	}
	
	public NodeInstance getNodeInstance(long processId, long nodeId) {
		String hql = "from NodeInstance ins where ins.processInstance.id=? and ins.node.id=?";
		return (NodeInstance)genService.findObjByHql(hql, processId, nodeId);
	}
	
	//取得节点配置的学习指导
	public String getStudyGuide(long nodeId) {
		String hql = "select studyGuideCodes from TrainingPolicy p where p.asfNode.id=?";
		String r = (String)genService.findObjByHql(hql, nodeId);
		return r==null?"":r.trim();
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}
	
	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

}
