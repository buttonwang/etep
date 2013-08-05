package com.ambow.studyflow.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.common.ServiceLocator;
import com.ambow.studyflow.common.URIGenerator;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.dao.ProcessCategoryDao;
import com.ambow.studyflow.dao.ProcessDefinitionDao;
import com.ambow.studyflow.dao.ProcessEventDao;
import com.ambow.studyflow.dao.ProcessInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.AfterEvent;
import com.ambow.studyflow.domain.BeforeEvent;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessEvent;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO;
import com.ambow.studyflow.dto.NodeDTOCollection;
import com.ambow.studyflow.dto.ProcessDTO;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.studyflow.event.EventHandler;
import com.ambow.studyflow.exception.AsfException;
import com.ambow.studyflow.service.IProcessService;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;

/**
 * 
 * ProcessService.java: 学习流可供客户端调用的方法
 * 
 * Created on 2008-5-15
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes ------- $Log$
 */
public class ProcessService implements IProcessService {

	private ProcessDefinitionDao processDefinitionDao;
	private ProcessInstanceDao processInstanceDao;
	private NodeDao nodeDao;
	private NodeInstanceDao nodeInstanceDao;
	private ServiceLocator serviceLocator;
	private ProcessEventDao processEventDao;
	private ProcessCategoryDao processCategoryDao;
	private ProcessPolicyService processPolicyService;
	/**
	 * orderNum每级相差位数
	 */
	public static final int STEP =2;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ambow.studyflow.service.IProcessService#deployProcess(com.ambow.studyflow.domain.ProcessDefinition)
	 */
	public ProcessDefinition deployProcess(ProcessDefinition process) {
		// 保存流程定义和节点组、节点、后面可能保存路径
		processDefinitionDao.save(process);
		for (Node node : process.getNodes()) {
			initNextNode(process, node);
		}
		return process;
	}

	/*
	 * 初始化节点的默认下个节点信息
	 * 
	 * @see com.ambow.studyflow.service.IProcessService#initNextNodeInfo(com.ambow.studyflow.domain.ProcessDefinition)
	 */
	public void initNextNodeInfo(long definitionId) {
		ProcessDefinition process = processDefinitionDao.findById(definitionId);
		for (Node node : process.getNodes()) {
			initNextNode(process, node);
		}
	}

	private void initNextNode(ProcessDefinition process, Node node) {
		node.setNextNode(nodeDao.getNodeByNameAndDefinitionAndGroup(node
				.getDest(), process.getId()));
		nodeDao.save(node);
		if (node.getNodeType().equals(NodeType.GROUP)) {
			for (Node subNode : ((NodeGroup) node).getNodes()) {
				initNextNode(process, subNode);
			}
		}
	}

	public Node getCurrentNodeByProcessId(long instanceId) {
		return processInstanceDao.getCurrentNodeByProcessId(instanceId);
	}

	public List<Node> getNodesByProcessInstance(long instanceId) {
		return null;
	}

	public void jumpToNode(ProcessInstance instance, String nodeName) {
		// 分几步，1. 找到当前节点之前的节点 2.将没有完成的节点实例置为跳过 3.在token中设置当前节点

		NodeInstance nodeInstance = nodeInstanceDao
				.findNodeInstanceByProcessInstanceIdAndNodeName(instance
						.getId(), nodeName);
		nodeInstanceDao.updateNodeInstanceStatusBeforeSpecialNode(nodeInstance
				.getId());

	}

	public ProcessInstance getProcessInstance(long instanceId) {
		return processInstanceDao.getById(instanceId);
	}

	@SuppressWarnings("unchecked")
	public Node runProcessInstance(long processInstanceId,
			NodeStatus nodeStatus, Map param) throws AsfException {

		// 1. 保存当前流程的执行结果到当前节点实例
		// 2. token移入下一步

		//执行前置事件
		List<ProcessEvent> beforeEventList=processEventDao.getBeforeEventListByProcessInstanceId(processInstanceId);
		doEventHandler(processInstanceId, beforeEventList);
		ProcessInstance processInstance = processInstanceDao
				.getById(processInstanceId);
		if (!processInstance.getProcessStatus().equals(ProcessStatus.RUNNING)) {
			throw new AsfException("此流程已挂起或已结束");
		}
		Node currentNode = processInstance.getNode();
		NodeInstance currentNodeInstance = nodeInstanceDao.getNodeInstanceByNodeAndProcessInstance(currentNode.getId(), processInstanceId);
		// 根据客户端反馈，设置当前节点实例状态
		currentNodeInstance.setNodeStatus(nodeStatus); 
		nodeInstanceDao.update(currentNodeInstance);
		// 执行后置事件
		List<ProcessEvent> afterEventList=processEventDao.getAfterEventListByProcessInstanceId(processInstanceId);
		doEventHandler(processInstanceId, afterEventList);
		//获取下一个节点，不从节点表中获取，在此程序中只获取当前节点下一个节点
	//	Node defaultNextNode = this.processPolicyService.getDefaultNextNode(currentNode);
		Node defaultNextNode = currentNode.getNextNode();
		NodeInstance defaultNextNodeInstance = null;
		if(defaultNextNode!=null)
			defaultNextNodeInstance =nodeInstanceDao.getNodeInstanceByNodeAndProcessInstance(
					defaultNextNode.getId(), processInstanceId);
		//defaultNextNode = processPolicyService.getNextNode(defaultNextNodeInstance);
		Node nextNode = null;
		if (currentNode.getDecisionName() != null) {
			// 根据决策器来决定下个节点
			param.put("nodeGroupId", currentNode.getNodeGroup().getId());
			param.put("nodeId", currentNode.getId());
			param.put("processInstanceId", processInstanceId);
			if (defaultNextNode != null) {
				
				param.put("defaultNextNode", defaultNextNode);
				param.put("defaultNextNodeInstance", defaultNextNodeInstance);
			}
			param.put("currentNode", currentNode);
			IDecision decision = (IDecision) serviceLocator
					.getService(currentNode.getDecisionName());
			nextNode = decision.excute(param, nodeStatus);
			//if(nextNode!=null)
			//	System.out.println(nextNode.getName());
		}
		if (nextNode == null) {
			
			if(currentNode.getNodeType()==NodeType.PRACTICE
					||currentNode.getNodeType()==NodeType.UNITTEST
					||currentNode.getNodeType()==NodeType.EVALUATE){
			
				//验证是否为最后一个节点，
				String ids="";
				if(param.get("showNodeGroupVONodeIds")!=null)//用于有节点组显示策略的流程
					ids=param.get("showNodeGroupVONodeIds").toString();
				else{//用于没有节点组策略的流程
					Node groupNode=currentNode.getNodeGroup();
					//获取该节点所有训练子节点
					String sql="select n.id from asf_node n where n.order_num like ? and n.process_definition_id=?"
						+" and n.node_type in('PRACTICE','EVALUATE','PHASETEST','UNITTEST') order by n.order_num";
					
					List list=this.jdbcTemplate.queryForList(sql,new Object[]{groupNode.getOrderNum()+",%",groupNode.getProcessDefinition().getId()});
					if(list!=null&&list.size()>0)
						for(int i=0;i<list.size();i++){
							Map map=(Map)list.get(i);
							if(ids.trim().length()>0) 
								ids=ids+",";
							ids=ids+(Long)map.get("id");
						}
				}
				String[] nodeIds=ids.split(",");
				List<NodeInstance> list=this.nodeInstanceDao.find("from NodeInstance a where a.processInstance.id=? and a.node.id in ("
						+ids+") order by a.node.orderNum", processInstanceId);
				if(Long.valueOf(nodeIds[nodeIds.length-1])==currentNode.getId()){
				//如果没找到下一个节点便验证，在本显示策略组中是否含有不通过的节点即状态为 1 的				
					//nextNode =this.getNoPassNodeInShowNodeGroup(ids, processInstanceId);
					for(NodeInstance po:list){
						if(po.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()){
							nextNode =po.getNode();
							break;
						}
					}
				}
				else{
					//验证不是(显示策略)组最后一个节点，
					//如果都通过了
					boolean tag=true;
					for(NodeInstance po:list){
						if(po.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()){
							tag=false;
							break;
						}
					}
					if(tag){
						//获取最后一个节点的的下一个节点
						Node node=this.nodeInstanceDao.get(Node.class, Long.valueOf(nodeIds[nodeIds.length-1]));
						nextNode =node.getNextNode();
					}else
						nextNode=processPolicyService.getNextNode(defaultNextNodeInstance);
				}
					
			}
			if (nextNode == null) 
				nextNode = defaultNextNode;
		}
		//if(nextNode!=null)
		//	System.out.println(nextNode.getName());
		// 检查下个节点是否为节点组，是节点组的话取节点组默认节点
		nextNode = checkNodeGroup(nodeStatus, nextNode);		
		processInstance.setNode(nextNode);
		processInstanceDao.save(processInstance);
		return nextNode;
	}
	
	/**
	 * @param processInstanceId
	 * @param eventList
	 */
	private void doEventHandler(long processInstanceId,
			List<ProcessEvent> eventList) {
		for(ProcessEvent event:eventList)
		{
			EventHandler handler=(EventHandler)serviceLocator.getService(event.getEventHandler());
			handler.doAction(processInstanceId);
		}
	}

	private Node checkNodeGroup(NodeStatus nodeStatus, Node nextNode) {
		if (nextNode != null && nextNode.getNodeType().equals(NodeType.GROUP)) {
			IDecision groupDecison = (IDecision) serviceLocator
					.getService(nextNode.getDecisionName());
			Map groupParams = new HashMap();
			groupParams.put("currentNode", nextNode);
			nextNode = groupDecison.excute(groupParams, nodeStatus);
			checkNodeGroup(nodeStatus, nextNode);
		}
		return nextNode;
	}

	public void saveProcessInstance(ProcessInstance instance) {

	}

	public void updateNodeStatus(ProcessInstance instance, String[][] nodeNames) {

	}

	public ProcessInstance createProcessInstance(Map map,
			ProcessDefinition definition) {
		ProcessInstance processInstance = definition.createProcessInstance(map);
		processInstanceDao.save(processInstance);
		// 实例化全部节点,如果节点非常多的话可能需要改为批量插入
		List<Node> nodeList = nodeDao
				.findNodeListByProcessDefinition(definition.getId());
		for (Node node : nodeList) {
			createNodeInstance(processInstance, node);
		}
		return processInstance;
	}

	public ProcessInstance createProcessInstance(Map map,
			long processDefinitionId) {
		ProcessDefinition definition = processDefinitionDao.get(
				ProcessDefinition.class, processDefinitionId);
		return createProcessInstance(map, definition);
	}

	/**
	 * 创建一个节点实例
	 * 
	 * @param processInstance
	 */
	private void createNodeInstance(ProcessInstance processInstance, Node node) {
		NodeInstance nodeInstance = new NodeInstance();
		nodeInstance.setProcessInstance(processInstance);
		nodeInstance.setNode(node);
		nodeInstanceDao.save(nodeInstance);
	}

	public String getNodeUrl(Node node) {
		URIGenerator generator = (URIGenerator) serviceLocator.getService(node
				.getUriKey());
		generator.addQueryData("practiceId", node.getKeyId());
		return generator.render();
	}

	public void runProcessInstance(long processInstanceId, NodeStatus nodeStatus)
			throws AsfException {
		Map map = new HashMap();
		runProcessInstance(processInstanceId, nodeStatus, map);

	}

	public void setProcessDefinitionDao(
			ProcessDefinitionDao processDefinitionDao) {
		this.processDefinitionDao = processDefinitionDao;
	}

	public void setProcessInstanceDao(ProcessInstanceDao processInstanceDao) {
		this.processInstanceDao = processInstanceDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setNodeInstanceDao(NodeInstanceDao nodeInstanceDao) {
		this.nodeInstanceDao = nodeInstanceDao;
	}

	public void setServiceLocator(ServiceLocator serviceLocator) {
		this.serviceLocator = serviceLocator;
	}

	public List<ProcessInstance> getProcessInstanceListByActor(String actor) {
		return processInstanceDao.getByActor(actor);
	}

	public ProcessDTO getFullProcessInstanceForView(long processInstanceId) {
		// 取得流程定义
		// 取得流程结构
		// 取得该实例全部节点信息
		// 取得该流程状态
		// 取得当前节点
		ProcessInstance instance = processInstanceDao
				.getById(processInstanceId);
		ProcessDefinition definition = instance.getProcessDefinition();
		ProcessDTO process = new ProcessDTO();
		process.setName(definition.getName());
		process.setProcessDefinitionId(definition.getId());
		process.setProcessInstanceId(processInstanceId);
		Node currentNode = instance.getNode();
		Map<Long, NodeStatus> nodeInstanceMap = getNodeInstanceStatusMap(processInstanceId);

		for (Node node : definition.getNodes()) {
			long currentNodeId = 0;
			if (currentNode != null)
				currentNodeId = currentNode.getId();
			fillPrcessDTO(process, nodeInstanceMap, node, currentNodeId);
		}

		return process;
	}

	/**
	 * @param collection
	 * @param nodeInstanceMap
	 * @param node
	 * @param processInstanceId
	 *            TODO
	 */
	private void fillPrcessDTO(NodeDTOCollection collection,
			Map<Long, NodeStatus> nodeInstanceMap, Node node,
			long currentNodeId) {
		NodeDTO nodeDTO = new NodeDTO();
		nodeDTO.setName(node.getName());
		nodeDTO.setId(node.getId());
		nodeDTO.setNodeStatus(nodeInstanceMap.get(node.getId()));
		nodeDTO.setNodeType(node.getNodeType());
		if (node.getNodeType().equals(NodeType.GROUP)) {
			for (Node subNode : ((NodeGroup) node).getNodes()) {
				fillPrcessDTO(nodeDTO, nodeInstanceMap, subNode, currentNodeId);
			}
		}
		// 设置当前节点
		if (node.getId() == currentNodeId) {
			nodeDTO.setCurrent(true);
		}
		collection.addNode(nodeDTO);
	}

	/**
	 * @param processInstanceId
	 */
	private Map<Long, NodeStatus> getNodeInstanceStatusMap(
			long processInstanceId) {
		Map<Long, NodeStatus> nodeInstanceMap = new HashMap<Long, NodeStatus>();
		List<NodeInstance> nodeInstanceList = nodeInstanceDao
				.findNodeInstanceByProcessInstanceId(processInstanceId);
		for (NodeInstance ni : nodeInstanceList) {
			nodeInstanceMap.put(ni.getNode().getId(),
					ni.getNodeStatus());
		}
		return nodeInstanceMap;
	}

	
	public ProcessDTO getFullProcessInstanceForFront(long processInstanceId,
			int showLevel) {
		ProcessInstance instance = processInstanceDao
		.getById(processInstanceId);
		List<Node> nodeList=nodeDao.findNodeListByProcessDefinition(instance.getProcessDefinition().getId());
		ProcessDTO process=new ProcessDTO();
		process.setProcessDefinitionId(instance.getProcessDefinition().getId());
		process.setProcessInstanceId(processInstanceId);
		Map<Long, NodeStatus> nodeInstanceMap = getNodeInstanceStatusMap(processInstanceId);
		Node currentNode = instance.getNode();
		/*====================================
		 asf_node orderNum 字段已由bitint 转换成varchar
		long basicNum=nodeList.get(0).getOrderNum();
		int digits=this.getNumDigitis(basicNum);
//		long min=this.getMinNum(showLevel, digits, this.STEP);
//		long max=this.getMaxNum(showLevel, digits, min, this.STEP);
		//指定显示级别下，最小的orderNum后面0的个数，比如11000，zeroNum为3
		int zeroNum=digits-showLevel*this.STEP;
		for(Node node:nodeList)
		{
			
			if(node.getNodeType().equals(NodeType.GROUP))
			{
				if(this.isGroupNodeToShow(node.getOrderNum(), zeroNum))
				{
					addNodeDTO(process, node,nodeInstanceMap,currentNode);
				}
			}
			else
			{
				addNodeDTO(process, node,nodeInstanceMap,currentNode);
			}						
		}
		=========================================*/
		// 判断节点类型和排序值范围
		return process;
	}

	/**
	 * @param process
	 * @param node
	 */
	private void addNodeDTO(ProcessDTO process, Node node,Map<Long, NodeStatus> nodeInstanceMap,Node currentNode) {
		NodeDTO nodeDTO=new NodeDTO();
		nodeDTO.setId(node.getId());
		nodeDTO.setName(node.getName());
		nodeDTO.setNodeType(node.getNodeType());		
		nodeDTO.setNodeStatus(nodeInstanceMap.get(node.getId()));
		nodeDTO.setCurrent(node.getId()==currentNode.getId());
		process.addNode(nodeDTO);
	}

	
	/**
	 * 判断该节点组是否要在前台显示
	 * @param num1
	 * @param digits
	 * @param zeroNum
	 * @return
	 */
	private boolean isGroupNodeToShow(long num1, int zeroNum) {
		return num1%((long)Math.pow(10, zeroNum))==0 && num1%((long)Math.pow(10, zeroNum+this.STEP))>0;
	}


	/**
	 * @param num1
	 * @return
	 */
	private int getNumDigitis(long num1) {
		int digits=1;
		while(num1/10>0)
		{
			digits++;
			num1=num1/10;
		}
		return digits;
	}
	
	
	/**
	 * 为了mpc使用的新的更新节点组状态地方法
	 */
	@SuppressWarnings("unchecked")
	public void updateNodeInstancesStatus(NodeInstance nodeInstance,
			Node nextNode,NodeStatus nodeStatus){
		if(nodeStatus.toInt()<2)
			return;
		//通过的节点组实例临时变量
		NodeInstance tempNodeGroupInstance=null;
		if(checkPass(nodeInstance)){
			List<NodeInstance> list=this.nodeInstanceDao.find(
					"from NodeInstance a where a.node.id=? and a.processInstance=?" 
					,nodeInstance.getNode().getNodeGroup().getId(),
					nodeInstance.getProcessInstance().getId());
			if(list!=null&&list.size()>0){
				tempNodeGroupInstance=list.get(0);
				tempNodeGroupInstance.setNodeStatus(NodeStatus.PASSED);
				this.nodeInstanceDao.save(tempNodeGroupInstance);
			}
		}
			
		//如果是阶段测试，不是向回跳的情况,跳过的所有节点（在当前节点与流程写一个节点的实例）所在组实例都要验证
		if(nodeInstance.getNode().getNodeType()==NodeType.PHASETEST){
			//验证向回跳,就返回
			if(nodeInstance.getNode().getOrderNum()
					.compareTo(nextNode.getOrderNum())>0)
				return;	
			
			List<NodeInstance> list=this.nodeInstanceDao.find(
					"select a from NodeInstance a where a.processInstance=? and a.node.orderNum > ? and a.node.orderNum < ? order by a.node.orderNum"
					,nodeInstance.getProcessInstance().getId(),nodeInstance.getNode().getOrderNum(),nextNode.getOrderNum());
			//没有通过的节点组实例临时变量
			Node tempNoPassNodeGroup=null;
			if(list!=null&&list.size()>0)
				for(NodeInstance po:list){
					//等于tempNodeGroupInstance节点组状态已经更新，没必要在更新了
					if(tempNodeGroupInstance.getNode().getNodeGroup().getId()==po.getNode().getNodeGroup().getId())
						continue;
					//此时节点属于临时存放的不通过节点就没有必要在验证了，更没必要去更新。
					if(tempNoPassNodeGroup!=null&&tempNoPassNodeGroup.getId()==po.getNode().getNodeGroup().getId())
						continue;
					//验证该组中所有节点实例是否通过，通过便更新节点组的状态
					if(!checkPass(nodeInstance)){
						tempNoPassNodeGroup=nodeInstance.getNode().getNodeGroup();
						continue;
					}
					List<NodeInstance> list2=this.nodeInstanceDao.find(
							"from NodeInstance a where a.node.id=? and a.processInstance=?" 
							,po.getNode().getNodeGroup().getId(),
							po.getProcessInstance().getId());
					if(list2!=null&&list2.size()>0){
						tempNodeGroupInstance=list.get(0);
						tempNodeGroupInstance.setNodeStatus(NodeStatus.PASSED);
						this.nodeInstanceDao.save(tempNodeGroupInstance);
					}
				}
		}
	}
	/**
	 * 查看本组内节点是不是全都通过
	 */
	@SuppressWarnings("unchecked")
	public boolean checkPass(NodeInstance nodeInstance){
		boolean tag=true;
		List<NodeInstance> list=this.nodeInstanceDao.find(
				"select a from NodeInstance a where a.processInstance=? and a.node.nodeGroup.id=?"
				,nodeInstance.getProcessInstance().getId(),nodeInstance.getNode().getNodeGroup().getId());	
		for(NodeInstance po:list){
			if(po.getNodeStatus().toInt()<2){
				tag=false;
				break;
			}
		}
		return tag;
	}
	
	
	public List<ProcessDefinition> getProcessDefinitionList() {

		return processDefinitionDao.getAll(ProcessDefinition.class);
	}

	
	
	public void removeEvent(long processInstanceId, String beanName) {
		
		processEventDao.removeEvent(processInstanceId, beanName);
	}

	public void createAfterEvent(long processInstanceId, String eventHandlerName) {
		AfterEvent afterEvent=new AfterEvent();
		createEvent(processInstanceId, eventHandlerName, afterEvent);
	}

	/**
	 * @param processInstanceId
	 * @param eventHandlerName
	 * @param event
	 */
	private void createEvent(long processInstanceId, String eventHandlerName,
			ProcessEvent event) {
		event.setEventHandler(eventHandlerName);
		event.setProcessInstanceId(processInstanceId);
		event.setHasDone(false);
		processEventDao.save(event);
	}

	public void createBeforeEvent(long processInstanceId,
			String eventHandlerName) {
		BeforeEvent beforeEvent=new BeforeEvent();
		createEvent(processInstanceId, eventHandlerName, beforeEvent);
		
	}
	

	public void createCategory(String name) {
		
		processCategoryDao.save(new ProcessCategory(name));
	}

	public void deleteCategory(long id) {
		processCategoryDao.removeById(ProcessCategory.class, id);
	}

	public List<ProcessCategory> getCategoryList() {
		
		return processCategoryDao.getCategoryList();
	}

	public List<ProcessDefinition> getProcessDefinitionByCategoryId(
			long categoryId) {
		return processDefinitionDao.getProcessDefinitionByCategoryId(categoryId);
	}

	public void setProcessEventDao(ProcessEventDao processEventDao) {
		this.processEventDao = processEventDao;
	}

	public void setProcessCategoryDao(ProcessCategoryDao processCategoryDao) {
		this.processCategoryDao = processCategoryDao;
	}

	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}
	
 
	private SimpleJdbcTemplate jdbcTemplate ;

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
  
}