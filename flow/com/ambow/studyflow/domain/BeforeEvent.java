/**
 * 
 */
package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/*
 * BeforeEvent.java:前置事件
 * 
 * 流转前执行
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
@DiscriminatorValue("BEFORE")
public class BeforeEvent extends ProcessEvent {
	public BeforeEvent(String eventHandler, long processInstanceId,int priority) {
		super(eventHandler, processInstanceId, priority);
	}

	public BeforeEvent() {
		super();
	}

	@Override
	@Transient
	public String getEventType() {
		// TODO Auto-generated method stub
		return "BEFORE";
	}
	
	

}
