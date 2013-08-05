package com.ambow.trainingengine.exam.service;

import com.ambow.core.dao.HibernateGenericDao;


/*
 * ExamViewService.java
 * 
 * Created on Aug 5, 2008 3:58:17 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class ExamViewService {
	
	private HibernateGenericDao genService;
	
	public Integer getHistoryIdByIns(Integer nodeInstanceId){
		String hql = "select testStatus.id from HistoryTestStatus testStatus where testStatus.asfNodeInstance.id="
					 +nodeInstanceId+"order by testStatus.endTime desc";
		return (Integer)genService.findObjByHql(hql);				
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

}
