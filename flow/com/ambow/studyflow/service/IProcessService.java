package com.ambow.studyflow.service;

import java.util.List;
import java.util.Map;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.ProcessDTO;
import com.ambow.studyflow.exception.AsfException;

/**
 * 此接口客户端可能用到的方法
 * 
 * @author suxiaoyong
 * 
 */
public interface IProcessService {

	/**
	 * 部署流程定义
	 * 
	 * @param process
	 */
	public ProcessDefinition deployProcess(ProcessDefinition process);
	/**
	 * 保存流程实例
	 * 
	 * @param instance
	 */
	public void saveProcessInstance(ProcessInstance instance);
	/**
	 * 运行流程实例,让流程走入下一步
	 * @param processInstanceId
	 * @param nodeStatus 节点状态
	 * @param param 参数map,主要用于决策器
	 * @throws AsfException 流程已挂起或停止时抛出异常
	 */
	public Node runProcessInstance(long processInstanceId, NodeStatus nodeStatus, Map param) throws AsfException;
	public void runProcessInstance(long processInstanceId, NodeStatus nodeStatus) throws AsfException;
	
	/**
	 * 获得流程实例
	 * 
	 * @param instanceId
	 * @return
	 */
	public ProcessInstance getProcessInstance(long instanceId);
	
	/**
	 * 根据流程实例得到节点列表
	 * 
	 * @param instanceId
	 * @return
	 */
	public List<Node> getNodesByProcessInstance(long instanceId);
	
	/**
	 * 直接跳到某一节点，并将token转移到此节点
	 * 
	 * @param instance
	 * @param nodeName
	 */
	public void jumpToNode(ProcessInstance instance,String nodeName);
	 
	/**
	 * 重置流程实例中一组节点状态
	 */
	public void updateNodeStatus(ProcessInstance instance,String[] nodeNames[]);
	
	/**
	 * 根据流程实例Id得到当前节点，从而得到当前流转信息
	 * 
	 * @param instanceId
	 * @return
	 */
	public Node getCurrentNodeByProcessId(long instanceId);
	/**
	 * 创建流程实例
	 * 创建第一个节点实例
	 * @param map 参数列表，必须包含actor
	 * @param definition TODO
	 * @return
	 */
	public ProcessInstance createProcessInstance(Map map, ProcessDefinition definition);
	/**
	 * 创建流程实例
	 * @param map 参数列表，必须包含actor
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessInstance createProcessInstance(Map map, long processDefinitionId);
	/**
	 * 根据节点得到URL
	 * @param node
	 * @return
	 */
	public String getNodeUrl(Node node);
	
	/**
	 * 取得一个可以用于显示流程状态的实例，显示为树状结构
	 * 用法可参考单元测试
	 * @param processInstanceId
	 * @return
	 */
	public ProcessDTO getFullProcessInstanceForView(long processInstanceId);
	
	/**
	 * 取得一个前台用于显示流程实例的流程，显示为平面结构
	 * 
	 * @param processInstanceId 流程实例Id
	 * @param showLevel 显示由外到内第几层
	 * @return
	 */
	public ProcessDTO getFullProcessInstanceForFront(long processInstanceId,int showLevel);
	
	/**
	 * 根据用户Id取得某用户所有的流程实例
	 * @param actorId
	 * @return
	 */
	public List<ProcessInstance> getProcessInstanceListByActor(String actorId);
	
	public void initNextNodeInfo(long definitionId);  
	
	public List<ProcessDefinition> getProcessDefinitionList();
	/**
	 * 事件结束
	 * @param processInstanceId
	 * @param beanName
	 */
	public void removeEvent(long processInstanceId, String beanName);
	/**
	 * 创建一个前置事件
	 * @param processInstanceId
	 * @param eventHandlerName
	 */
	public void createBeforeEvent(long processInstanceId,String eventHandlerName);
	/**
	 * 创建一个后置事件
	 * @param processInstanceId
	 * @param eventHandlerName
	 */
	public void createAfterEvent(long processInstanceId,String eventHandlerName);
	/**
	 * 创建流程分类
	 * @param name
	 */
	public void createCategory(String name);
	/**
	 * 删除流程分类
	 * @param id
	 */
	public void deleteCategory(long id);
	
	/**
	 * 取得分类列表，按id排序
	 * @return
	 */
	public List<ProcessCategory> getCategoryList();
	
	/**
	 * 按分类id取得流程定义列表
	 * @param categoryId
	 * @return
	 */
	public List<ProcessDefinition> getProcessDefinitionByCategoryId(long categoryId);
	
	
}
