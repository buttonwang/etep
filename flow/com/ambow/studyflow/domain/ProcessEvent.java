/**
 * 
 */
package com.ambow.studyflow.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * Event.java:事件记录
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
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name="asf_event")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
    name="event_type",
    discriminatorType=DiscriminatorType.STRING
)
public abstract class ProcessEvent {

	private long id;
	private String eventHandler;
	private long processInstanceId;
	private boolean hasDone=false;
	/**
	 * 优先级，数字越大优先级越高
	 */
	private int priority=0;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * 事件处理器
	 * 这里的名字是该处理器在spring中定义的bean id
	 * 
	 * @return
	 */
	@Column(name="event_handler",length=50)
	public String getEventHandler() {
		return eventHandler;
	}
	public void setEventHandler(String eventHandler) {
		this.eventHandler = eventHandler;
	}
	/**
	 * 相关流程实例Id
	 * @return
	 */
	@Column(name="process_instance_id",nullable=false)	
	public long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	/**
	 * 是否已经执行过
	 * @return
	 */
	@Column(name="has_done")
	public boolean isHasDone() {
		return hasDone;
	}
	public void setHasDone(boolean hasDone) {
		this.hasDone = hasDone;
	}
	/**
	 * 
	 * @param eventHandler 事件处理器名称
	 * @param processInstanceId 流程实例Id
	 * @param priority 优先级，越大优先级越高
	 */
	public ProcessEvent(String eventHandler, long processInstanceId, int priority) {
		super();
		this.eventHandler = eventHandler;
		this.processInstanceId = processInstanceId;
		this.priority=priority;
	}
	public ProcessEvent() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	// 取得流程事件类型
	@Transient
	public abstract String  getEventType();
}
