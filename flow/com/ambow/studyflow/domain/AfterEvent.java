/**
 * 
 */
package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/*
 * AfterEvent.java:后置事件
 * 
 * 流转后执行
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
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@DiscriminatorValue("AFTER")
public class AfterEvent extends ProcessEvent {

	public AfterEvent(String eventHandler, long processInstanceId,int priority) {
		super(eventHandler, processInstanceId, priority);
	}

	public AfterEvent() {
		super();
	}

	@Override
	@Transient
	public String getEventType() {
		// TODO Auto-generated method stub
		return "AFTER";
	}
	
	
	

}
