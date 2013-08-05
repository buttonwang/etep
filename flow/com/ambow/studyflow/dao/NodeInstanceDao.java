package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;

public class NodeInstanceDao extends HibernateGenericDao{

	public NodeInstance findNodeInstanceByProcessInstanceIdAndNodeName(long id,
			String nodeName) {
		return null;
	}

	public void updateNodeInstanceStatusBeforeSpecialNode(long id) {
		
	}

	public NodeInstance getNodeInstanceByNodeAndProcessInstance(long nodeId,
			long processInstanceId) {
		String hql="from NodeInstance n where n.node.id=? and n.processInstance.id=?";
		
		NodeInstance node = (NodeInstance)findObjByHql(hql, nodeId, processInstanceId);
		
		return  node;
	}

	public void update(NodeInstance currentNodeInstance) {
		
	}

	public Node findAfterCurrentNotPassPracticeNodeByGroupId(
			String orderNum, Long nodeGroupId, Long processInstanceId) {
		String hql = "select p.id from PracticeNode p where p.orderNum>? and p.nodeGroup.id=?";
		return getNodeInstanceByNodeIds(orderNum, nodeGroupId, hql, processInstanceId);
	}

	public Node findAfterCurrentNotPassPracticeNodeByGroupId(List<NodeInstance> list,Node currentNode) {
		boolean tag=false;
		for(NodeInstance po:list){
			if(po.getNode().getNodeType()==NodeType.UNITTEST&&tag)
				return null;
			if(po.getNode().getNodeType()==NodeType.PRACTICE&&po.getNodeStatus().toInt()<2&&tag)
				return po.getNode();
			if(po.getNode().getId()==currentNode.getId())
				tag=true;
		}
		return null;
	}
	public Node findBeforeCurrentNotPassPracticeNodeByGroupId(
			List<NodeInstance> list,Node currentNode) {
		for(NodeInstance po:list){
			if(po.getNode().getId()==currentNode.getId())
				return null;
			if(po.getNode().getNodeType()==NodeType.PRACTICE&&po.getNodeStatus().toInt()<2)
				return po.getNode();
		}
		return null;
	}
	/**
	 * @param l
	 * @param nodeGroupId
	 * @param hql
	 * @param processInstnceId TODO
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Node getNodeInstanceByNodeIds(String orderNum, Long nodeGroupId,
			String hql, Long processInstnceId) {
		List<Long> list = this.find(hql, orderNum, nodeGroupId);
		
		if(list.isEmpty())
		{
			return null;
		}
		StringBuffer sb = new StringBuffer(
				"from NodeInstance n where n.processInstance.id=? and n.node.nodeGroup.id=? and (nodeStatus=? or nodeStatus=?)");
		if (!list.isEmpty()) {
			StringBuffer condition=new StringBuffer(" and n.node.id in(");
			for (int i=0;i<list.size();i++) {
				condition.append(list.get(i).toString());
				if(i<(list.size()-1))
					condition.append(",");
			}
			sb.append(condition.toString());
		}
		sb.append(") order by n.node.orderNum");
		NodeInstance ni = null;
		try{
			ni = (NodeInstance) this.findObjByHql(sb.toString(),processInstnceId,
				nodeGroupId,NodeStatus.NOPASS,NodeStatus.INITIAL);
		}catch(Exception e){
			e.printStackTrace();
		}
		if (ni != null) {
			return ni.getNode();
		} else
			return null;
	}

	public Node findBeforeCurrentNotPassPracticeNodeByGroupId(
			String orderNum, Long nodeGroupId, Long processInstanceId) {
		// TODO Auto-generated method stub
		String hql = "select p.id from PracticeNode p where p.orderNum<? and p.nodeGroup.id=?";
		return getNodeInstanceByNodeIds(orderNum, nodeGroupId, hql, processInstanceId);
	}

	public Node findExamNodeByGroupId(Long nodeGroupId, String orderNum, Long processInstanceId) {
		String hql = "select p.id from ExamNode p where p.orderNum>? and p.nodeGroup.id=?";
		return getNodeInstanceByNodeIds(orderNum, nodeGroupId, hql, processInstanceId);
	}

	public void resetPracticeNodeInstanceStatusByGroupId(Long nodeGroupId, Long processInstanceId) {
		String hql="update NodeInstance n set n.nodeStatus=? where n.processInstance.id=? and n.node.id in (select id from PracticeNode where nodeGroup.id=?)";
		this.excuteHql(hql, NodeStatus.NOPASS,processInstanceId,nodeGroupId);
	}
	
	@SuppressWarnings("unchecked")
	public List<NodeInstance> findNodeInstanceByProcessInstanceId(long processInstanceId) {
		String hql="from NodeInstance n where n.processInstance.id=?";
		return this.find(hql, processInstanceId);
	}

}
