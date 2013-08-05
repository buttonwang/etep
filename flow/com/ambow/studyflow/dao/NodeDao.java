package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;

public class NodeDao extends HibernateGenericDao{

	/**
	 * 根据节点名和流程定义Id和节点所在节点组查询节点
	 * @param nodeName
	 * @param processDefinitionId
	 * @return
	 */
	public Node getNodeByNameAndDefinitionAndGroup(String nodeName, Long processDefinitionId) {
		
			String hql="from Node n where n.processDefinition.id=? and n.name=?";
			return (Node)this.findObjByHql(hql, processDefinitionId,nodeName);
	}

	/**
	 * 当前节点所在分组中，当前节点之后未通过的练习节点
	 * @param nodeId
	 * @param nodeGroupId
	 * @return
	 */
	public Node findAfterCurrentNotPassPracticeNodeByGroupId(Long nodeId,
			Long nodeGroupId) {
		String hql="from PracticeNode p where p.nodeGroup.id=? and p.id>? order by p.id";
		return (Node)this.findObjByHql(hql, nodeId,nodeGroupId);
	}

	/**
	 * 当前节点所在分组中，当前节点之前未通过的练习节点
	 * @param nodeId
	 * @param nodeGroupId
	 * @return
	 */
	public Node findBeforeCurrentNotPassPracticeNodeByGroupId(Long nodeId,
			Long nodeGroupId) {
		String hql="from PracticeNode p where p.nodeGroup.id=? and p.id<? order by p.orderNum";
		return (Node)this.findObjByHql(hql, nodeId,nodeGroupId);
	}

	/**
	 * 取得指定分组中的测试节点
	 * @param nodeGroupId
	 * @return
	 */
	public Node findExamNodeByGroupId(Long nodeGroupId) {
		String hql="from ExamNode e where e.nodeGroup.id=?";
		return (Node)this.findObjByHql(hql, nodeGroupId);
	}

	public Node findDefaultPracticeNodeByGroupById(Long nodeGroupId) {
		String hql="from PracticeNode p where p.nodeGroup.id=? order by p.orderNum";
		return (Node)this.findObjByHql(hql, nodeGroupId);
	}
	
	public List<Node> findNodeListByProcessDefinition(Long definitionId)
	{
		String hql="from Node n where n.processDefinition.id=? order by n.orderNum";
		return this.find(hql, definitionId);
	}

	public Node getGroupDefaultNode(Long nodeGroupId) {
		String hql="from Node n where n.nodeGroup.id=? order by n.orderNum";		
		return (Node)this.findObjByHql(hql, nodeGroupId);
	}
	

}
