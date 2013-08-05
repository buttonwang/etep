/**
 * 
 */
package com.ambow.studyflow.event.impl;

import com.ambow.studyflow.dao.ProcessInstanceDao;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.event.EventHandler;

/*
 * NewWordBookFullEventHandler.java:生词本满的事件处理器
 * 
 * Created on 2008-6-2
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

public class NewWordBookFullEventHandler extends EventHandler{

	private ProcessInstanceDao processInstanceDao;
	@Override
	public void doAction(long processInstanceId) {
		// 将当前流程设为挂起
		ProcessInstance instance=processInstanceDao.get(ProcessInstance.class, processInstanceId);
		processInstanceDao.save(instance);
		
	}
	public ProcessInstanceDao getProcessInstanceDao() {
		return processInstanceDao;
	}
	public void setProcessInstanceDao(ProcessInstanceDao processInstanceDao) {
		this.processInstanceDao = processInstanceDao;
	}
	
	
	

	
}
