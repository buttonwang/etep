package com.ambow.studyflow.decision.impl;

import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.dao.NodeDao;
import com.ambow.studyflow.dao.NodeInstanceDao;
import com.ambow.studyflow.decision.IDecision;
import com.ambow.studyflow.domain.Node;

public class ExampleJumpDecision implements IDecision {
	
	private NodeDao nodeDao;
	
	private NodeInstanceDao nodeInstanceDao;
	
	/*
	 *  专有决策器
	 * 该决策器的任务是执行到完形模块
	 * 	进行考试后 
	 *  根据考试成绩进行跳转 80分以上的通过
	 *  65-80分的，没有通过，且重做训练
	 *  65分以下的 没有通过，打回重做基础训练（之间的节点的状态重置为未通过）
	 * @see com.ambow.studyflow.decision.IDecision#excute(java.util.Map, com.ambow.studyflow.common.NodeStatus)
	 */
	public Node excute(Map map, NodeStatus nodeStatus) {
		String score=(String)map.get("score");
		int scoreInt=Integer.parseInt(score);
		Long nodeGroupId=(Long)map.get("nodeGroupId");
		Long processInstanceId=(Long)map.get("processInstanceId");
		Assert.notNull(nodeGroupId);
		//Assert.notNull(scoreInt);
		switch (nodeStatus){
			case NOPASS:
				if(scoreInt>65){
					//大于65的但没通过的打回重做练习
					nodeInstanceDao.resetPracticeNodeInstanceStatusByGroupId(nodeGroupId, processInstanceId);
					return nodeDao.findDefaultPracticeNodeByGroupById(nodeGroupId);
				}else{
					//小于65的打回基础练习模块
					nodeInstanceDao.resetPracticeNodeInstanceStatusByGroupId(nodeGroupId, processInstanceId);//重置本模块的联系节点
					List<Node> nodes=nodeDao.find("from Node where name like '?'", "基础练习一");
					Node redoGroup=nodes.get(0);
					nodeInstanceDao.resetPracticeNodeInstanceStatusByGroupId(nodeGroupId, processInstanceId);
				}
				break;
			case PASSED:
				//通过的状态下，取得默认的下一个节点，并返回
				
				break;
		}
		
		//nodeInstanceDao.find("from NodeInstance ", 1141);
		return null;
	}

	public NodeDao getNodeDao() {
		return nodeDao;
	}

	public void setNodeDao(NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public NodeInstanceDao getNodeInstanceDao() {
		return nodeInstanceDao;
	}

	public void setNodeInstanceDao(NodeInstanceDao nodeInstanceDao) {
		this.nodeInstanceDao = nodeInstanceDao;
	}

}
