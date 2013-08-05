/* 
 * IEntityDao.java
 * 
 * Created on 2007-3-30
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: IEntityDao.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 *
 */
package com.ambow.core.dao;

import java.io.Serializable;
import java.util.List;

public interface IEntityDao<T> {

	/**
	 * 根据Domain ID 获得一个Domain 对象
	 * @param id ID
	 */
	T get(Serializable id);

	/**
	 * 获得数据库中所有的Domain 对象<br>
	 * 如果数据库内记录巨大的话，请慎用此方法
	 * @return List<T>
	 */
	List<T> getAll();

	/**
	 * 保存传入的Domain 对象进入数据库
	 * @param o Domain Object
	 */
	void save(Object o);

	/**
	 * 从数据库中删除此Domain 对象对应的数据
	 * @param o Domain Object
	 */
	void remove(Object o);

	/**
	 * 根据Domain 对象的ID 从数据库中删除相应的记录
	 * @param id Domain ID
	 */
	void removeById(Serializable id);

	/**
	 * 获取Entity对象的主键名
	 */
	String getIdName(Class clazz);
	
}
