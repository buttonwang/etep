/* 
 * MessageService.java
 * 
 * Created on 2009-8-26
 * 
 * Copyright(C) 2009, by ambow Develope & Research Branch.
 * 
 * Original Author: gaochao
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log$
 */
package com.ambow.trainingengine.message.service;

import java.util.List;

import org.hibernate.Query;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.message.domain.Message;
 
public class MessageService extends HibernateEntityDao<Message>{

	/**
	 * 获得所有的消息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getAllMessage(long processInstanceId, String userId){
		String hql = "from Message where state = 0 and " +
		" ((type=2 and processInstanceId=?) or (type=1 and userId=?)) order by publishTime desc ";
		Query query = this.createQuery(hql, processInstanceId, userId);
		return query.list();
	}
	
	/**
	 * 获得分页的消息
	 * @return
	 */
	public Page getPageMessage(long processInstanceId, String userId, int source, int pageNo, int pageSize){
		String hql = "from Message where state = 0 and source =? and " +
		" ((type=2 and processInstanceId=?) or (type=1 and userId=?)) order by publishTime desc ";
		Page page = null;
		if (source==-1) { 
			hql = hql.replace("and source =?", " ");
			page = pagedQuery(hql, pageNo, pageSize, processInstanceId, userId);
		} else {
			page = pagedQuery(hql, pageNo, pageSize, source, processInstanceId, userId);
		}
		return page;
	}
	
	/**
	 * 获得首页的消息
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Message> getMainPageMessage(long processInstanceId, String userId){
		String hql = "from Message where state = 0 and " +
		" ((type=2 and processInstanceId=?) or (type=1 and userId=?)) order by publishTime desc ";
		Query query = this.createQuery(hql, processInstanceId, userId);
		return query.setMaxResults(4).list();
	}
	
	/**
	 * 获得消息总数
	 * @return
	 */
	public int getMessageCount(long processInstanceId, String userId){
		String hql = "select count(*) from Message where state = 0 and " +
		" ((type=2 and processInstanceId=?) or (type=1 and userId=?)) ";
		Long count = (Long)this.findObjByHql(hql, processInstanceId, userId);
		return count==null?0:count.intValue();
	}
	
}

 