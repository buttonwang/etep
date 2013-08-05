/**
 * 
 */
package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.ProcessEvent;

/*
 * ProcessEventDao.java:本类功能简介
 * 
 * Created on 2008-5-30
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */

public class ProcessEventDao extends HibernateGenericDao{

	/**
	 * 取得待处理事件列表
	 * @param processInstanceId
	 * @return
	 */
	public List<ProcessEvent> getAfterEventListByProcessInstanceId(long processInstanceId) {
		String hql="from AfterEvent ae where ae.processInstanceId=? and ae.hasDone=? order by ae.priority desc";		
		return this.find(hql, processInstanceId,false);
	}

	public void removeEvent(long processInstanceId, String beanName) {		
		String hql="update ProcessEvent pe set pe.hasDone=? where pe.processInstanceId=? and pe.eventHandler=?";
		this.excuteHql(hql, true,processInstanceId,beanName);
	}

	public List<ProcessEvent> getBeforeEventListByProcessInstanceId(
			long processInstanceId) {
		String hql="from BeforeEvent be where be.processInstanceId=? and be.hasDone=? order by be.priority desc";		
		return this.find(hql, processInstanceId,false);
	}

}
