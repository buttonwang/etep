package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;

/**
 * 
 * ProcessCategoryDao.java:本类功能简介
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
public class ProcessCategoryDao  extends HibernateGenericDao {

	public List<ProcessCategory> getCategoryList() {
		String hql="from ProcessCategory pc order by pc.id";
		return this.find(hql);
	}
	
}
