package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.ProcessInstance;

public class ProcessInstanceDao extends HibernateGenericDao {

	public ProcessInstance getById(long instanceId) {
		return this.get(ProcessInstance.class, instanceId);
	}

	public List<ProcessInstance> getByActor(String actor) {
		String hql="from ProcessInstance p where p.actor=? order by p.id";
		return this.find(hql, actor);
	}

	public Node getCurrentNodeByProcessId(long instanceId) {
		String hql="select p.node from ProcessInstance p where p.id=?";
		return (Node)this.findObjByHql(hql, instanceId);
	}

}
