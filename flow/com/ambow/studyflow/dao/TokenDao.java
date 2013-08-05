package com.ambow.studyflow.dao;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;

public class TokenDao extends HibernateGenericDao{

	public Node getCurrentNodeByProcessId(long instanceId) {
		String hql="select t.node from Token t where t.processInstance.id=?";
		return (Node)this.findObjByHql(hql, instanceId);
	}

	public NodeInstance getCurrentNodeInstanceByProcessId(long instanceId)
	{
		Node node=this.getCurrentNodeByProcessId(instanceId);
		if(node==null)
			return null;
		String hql="from NodeInstance n where n.processInstanceId=? and n.node.id=?";
		return (NodeInstance)this.findObjByHql(hql, instanceId,node.getId());
	}
	
}
