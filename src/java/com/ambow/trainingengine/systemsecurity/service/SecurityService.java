package com.ambow.trainingengine.systemsecurity.service;
/**
 * SecurityService.java
 * 
 * Created on 2008-8-1 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
/**
 * 系统安全唯一性验证管理类
 */
import com.ambow.core.dao.HibernateEntityDao;

public class SecurityService extends HibernateEntityDao<Object>{
	/**
	 * 查询指定对象的指定属性的值是否是唯一的
	 * @param table 查询对象名
	 * @param name 属性名
	 * @param value 属性值
	 * @return
	 */
	public int nameOnly(String table,String name,String value){
		return this.find("from "+table+" x where x."+name+"='"+value+"'").size();
	}
}