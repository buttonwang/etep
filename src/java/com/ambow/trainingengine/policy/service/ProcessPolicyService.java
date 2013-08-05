package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
public class ProcessPolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(ProcessPolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from ProcessPolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ProcessPolicy where id="+id);
		}catch(Exception e){
			logger.error("［ProcessPolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ProcessPolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［ProcessPolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新ProcessPolicy 对象
	 * @param processPolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(ProcessPolicy processPolicy){
		List errorList = new ArrayList(0);
		try{
			if(processPolicy!=null&&processPolicy.getAsfProcessDefinition().getId()!=0){
				ProcessDefinition pdDB= get(ProcessDefinition.class, new Long(processPolicy.getAsfProcessDefinition().getId()));				
				if(pdDB!=null){
					ProcessPolicy processPolicyDB = get(ProcessPolicy.class,pdDB.getId());
					if(processPolicyDB!=null){
						processPolicy.setProcessId(processPolicyDB.getProcessId());
						processPolicyDB.setFreshWordSum(processPolicy.getFreshWordSum());
						processPolicyDB.setReloginAnotherDayPolicy(processPolicy.getReloginAnotherDayPolicy());
						processPolicyDB.setReloginSameDayPolicy(processPolicy.getReloginSameDayPolicy());
						processPolicyDB.setStudyDirection(processPolicy.getStudyDirection());
						processPolicyDB.setSuggestTrainingTime(processPolicy.getSuggestTrainingTime());
						processPolicyDB.setDefaultTestTime( processPolicy.getDefaultTestTime());
						processPolicyDB.setIsShowAnswer( processPolicy.getIsShowAnswer());
						//processPolicyDB.setIsUsedDms( processPolicy.getIsUsedDms());
						processPolicyDB.setProjectVersion( processPolicy.getProjectVersion());
						processPolicyDB.setDefaultExamTime( processPolicy.getDefaultExamTime());
						processPolicyDB.setStudyInfo( processPolicy.getStudyInfo());
						processPolicyDB.setStudyGuideCodes(processPolicy.getStudyGuideCodes());
						save(processPolicyDB);
					}else{
						processPolicy.setAsfProcessDefinition(pdDB);
						save(processPolicy);
					}
				}else{
					errorList.add("要添加策略的流程不存在或已经被删除");
				}
			}
			
		}catch(Exception e){
			//System.out.println(e.toString());
			String errorMsg = "［ProcessPolicyService:add(ProcessPolicy processPolicy)］"+processPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			e.printStackTrace();
			
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 ProcessPolicy 对象，默认与add行为相似
	 * @param processPolicy
	 * @return 更新 ProcessPolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(ProcessPolicy processPolicy){
		List errorList = new ArrayList(0);
		try{
			//update ProcessPolicy前的(如果有子对象)对其子对象进行相关处理
			
			save(processPolicy);
		}catch(Exception e){
			String errorMsg = "［ProcessPolicyService:update(ProcessPolicy processPolicy)］"+processPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		map.put("ProcessPolicy", get(ProcessPolicy.class, id));
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(ProcessPolicy.class) ;
	}
	
	public ProcessPolicy get(long id){
		return super.get(ProcessPolicy.class,id) ;
	}
	
	/**
	 * 获取训练节点策略
	 */
	@SuppressWarnings("unchecked")
	public TrainingUnitNodePolicy getTrainingUnitNodePolicy(Node node){
		List<TrainingUnitNodePolicy> list=this.getTemplate().find("from TrainingUnitNodePolicy where asfNode.id=?",node.getId());
		TrainingUnitNodePolicy po=null;
		if(list!=null&&list.size()>0)
			po=list.get(0);
		return po;
	}
	
	/**
	 * 获取后测试节点策略
	 */
	@SuppressWarnings("unchecked")
	public UnitTestNodePolicy getUnitTestNodePolicy(Node node){
		List<UnitTestNodePolicy> list=this.getTemplate().find("from UnitTestNodePolicy where asfNode.id=?",node.getId());
		UnitTestNodePolicy po=null;
		if(list!=null&&list.size()>0)
			po=list.get(0);
		return po;
	}
	/**
	 * 获取阶段测试（前测）跳转策略
	 */
	@SuppressWarnings("unchecked")
	public List<PhaseTestNodePolicy> getPhaseTestNodePolicy(Node node){
		return this.getTemplate().find("from PhaseTestNodePolicy where asfNode.id=? order by startValue",node.getId());
	}
	
	/**
	 * 根据流程实例id获取节点实例列表更新节点实例状态
	 */
	@SuppressWarnings("unchecked")
	public void updateNodeInstancesStatus(Long processInstanceId,Node currentNode,Node nextNode){
		List<NodeInstance> list=this.getHibernateTemplate().find(
				"from NodeInstance where processInstance.id=? order by node.orderNum"
				,processInstanceId);
		if(list!=null&&list.size()>0){
			NodeStatus status=null;
			int tag=0;
			@SuppressWarnings("unused")
			NodeInstance tempNodeInstance=null;
			for(int i=0;i<list.size();i++){
				NodeInstance po=list.get(i);
				
				if(po.getNode().getNodeType()==NodeType.GROUP)
					continue;
				
				if(po.getNode().getId()==currentNode.getId()&&tag==0){
					status=NodeStatus.SKIP;
					tag=1;
					po.setNodeStatus(NodeStatus.PASSED);
					this.save(po);
					continue;
				}
				else if(po.getNode().getId()==nextNode.getId()&&tag==1){
					tag=0;
					break;
				}
				
				/*当向前跳的时候只把nextNode的节点实例到本节点组最后一个节点实例状态改为不通过状态，
				 * 前提阶段测试以及评测节点实例状态不改变*/
				
				else if(po.getNode().getId()==nextNode.getId()&&tag==0){
					status=NodeStatus.NOPASS;
					tag=1;
					po.setNodeStatus(NodeStatus.NOPASS);
					this.save(po);
					tempNodeInstance=po;
					continue;
				}
				/*
				else if(po.getNode().getId()==currentNode.getId()&&tag==1){
					tag=0;
					po.setNodeStatus(NodeStatus.NOPASS);
					this.save(po);
					break;
				}*/
				else if(tempNodeInstance!=null
						&&po.getNode().getNodeGroup().getId()!=tempNodeInstance.getNode().getNodeGroup().getId()
						&&tag==1){
					tag=0;
					//po.setNodeStatus(NodeStatus.NOPASS);
					//this.save(po);
					break;
				}
				
				if(tag==1){
					if(tempNodeInstance!=null&&(
						po.getNode().getNodeType()==NodeType.EVALUATE
						||po.getNode().getNodeType()==NodeType.PHASETEST))
						continue;
					
					po.setNodeStatus(status);
					this.save(po);
				}
			}
		}
	}
	/**
	 * 寻找下一个未通过节点,未解决 
	 */
	@SuppressWarnings("unchecked")
	public Node getNextNode(NodeInstance defaultNextNodeInstance){
		if(defaultNextNodeInstance==null)
			return null;
		if(defaultNextNodeInstance.getNodeStatus().toInt()<=1)
			return defaultNextNodeInstance.getNode();
		else{
			if(defaultNextNodeInstance.getNode().getNextNode()==null)
				return null;
			List<NodeInstance> list=this.find(
					"from NodeInstance where processInstance.id=? and node.id=?",
					defaultNextNodeInstance.getProcessInstance().getId(),
					defaultNextNodeInstance.getNode().getNextNode().getId());			
			NodeInstance po=list.get(0);
			if(po==null)
				return null;
			else
				return getNextNode(po);
		}
	}
	/**
	 * 寻找上n级节点组的下一个未通过节点  XX测试XX测试，当前面测试不通过时流程还可以往后面流的训练流转，转到第三个X训练
	 */
	@SuppressWarnings("unchecked")
	public Node getParentNextNode(Node currentNode,Long processInstanceId,int level){
		Assert.notNull(currentNode);
		Assert.notNull(processInstanceId);
		String nodeIds=null;
		nodeIds=this.getNodeIds(nodeIds, 
				this.getNodeGroup(currentNode, level).getId());
		Assert.notNull(nodeIds);
		List<NodeInstance> list=this.find("from NodeInstance where processInstance.id=? and node.id in("
				+nodeIds+") order by node.orderNum", processInstanceId);
		Assert.notNull(list);
		Assert.notEmpty(list);
		Node nextNode=null;
		boolean frontTag=true;
		boolean afterTag=false;
		for(int i=0;i<list.size();i++){
			NodeInstance nodeInstance=list.get(i);
			if(nodeInstance.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()&&frontTag){
				nextNode=nodeInstance.getNode();
				frontTag=false;
			}
			if(nodeInstance.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()&&afterTag){
				nextNode=nodeInstance.getNode();
				break;
			}
			if(currentNode.getId()==nodeInstance.getNode().getId())
				afterTag=true;
			
		}
		return nextNode;
	}
	/**
	 * 获取节点组id
	 */
	public Node getNodeGroup(Node node,int level){
		Assert.notNull(node);
		if(level==-1)
				return node;
		level--;
		return getNodeGroup(node.getNodeGroup(),level);
	}
	/**
	 * 遍历该节点组下所有节点
	 */
	@SuppressWarnings("unchecked")
	public String getNodeIds(String nodeIds,Long nodeId){
		List nodeList=this.getHibernateTemplate().find("from Node where nodeGroup.id=? order by orderNum", nodeId);
		if(nodeList!=null&&nodeList.size()>0)
			for(int i=0;i<nodeList.size();i++){
				Node node=(Node)nodeList.get(i);
				if(node.getNodeType().equals(NodeType.GROUP))
					nodeIds=this.getNodeIds(nodeIds,node.getId());
				else{
					if(nodeIds==null||nodeIds.equals(""))
						nodeIds=String.valueOf(node.getId());
					else
						nodeIds+=","+node.getId();
				}
			}
		return nodeIds;
	}
	
	@SuppressWarnings("unchecked")
	public Node getDefaultNextNode(Node node){
		Node defaultNextNode=null;
		List<Node> list=this.find(
				"from Node where processDefinition.id=? and orderNum>? order by orderNum",
				node.getProcessDefinition().getId(),node.getOrderNum());
		if(!list.isEmpty())
			defaultNextNode=list.get(0);
		return defaultNextNode;
	}

	public String getStudyGuideNames(String studyGuideCodes) {
		if(studyGuideCodes == null || "".equals(studyGuideCodes.trim())) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		List<StudyGuide> sgs = this.find("from StudyGuide sg where sg.id in (" + studyGuideCodes + ")");
		for (StudyGuide sg : sgs) {
			sb.append(sg.getName()+",");
		}
		return sb.substring(0, sb.length()-1).toString();
	}
	
}
