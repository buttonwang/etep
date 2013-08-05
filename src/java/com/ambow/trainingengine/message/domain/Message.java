/* 
 * Message.java
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
package com.ambow.trainingengine.message.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ambow.trainingengine.systemsecurity.domain.SysUser;

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "message")
public class Message {
	private Integer id;

	/** 流程实例id */
	private Long processInstanceId;

	/** 所属用户 */
	private String userId ="";

	/** 消息来源 */
	private Integer source;

	/** 消息类型 */
	private Integer type;
	
	/** 消息内容 */
	private String content;

	/** 引用标识 */
	private Integer refId;

	/** 发布人 */
	private SysUser publisher;

	/** 发布时间 */
	private Date publishTime;

	/** 状态 -1 删除; 0 正常 */
	private Integer state;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	@Column(name = "publish_time")
	public Date getPublishTime() {
		return publishTime;
	}

	@Column(name = "source")
	public Integer getSource() {
		return source;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}
	
	@Column(name = "ref_id")
	public Integer getRefId() {
		return refId;
	}
	
	@Column(name = "process_instance_id")
	public Long getProcessInstanceId() {
		return processInstanceId;
	}
	
	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	@ManyToOne()
	@JoinColumn(name = "publisher")
	public SysUser getPublisher() {
		return publisher;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	public void setPublisher(SysUser publisher) {
		this.publisher = publisher;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
