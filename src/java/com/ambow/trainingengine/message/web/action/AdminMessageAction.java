/* 
 * AdminMessageAction.java
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
package com.ambow.trainingengine.message.web.action;

import java.util.Date;
import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.message.domain.Message;
import com.ambow.trainingengine.message.service.MessageService;
import com.ambow.trainingengine.report.service.adviser.WebUserService;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;
 
@SuppressWarnings("serial")
public class AdminMessageAction extends BaseAction{
	private MessageService messageService;
	private WebUserService webUserService;
	private int pageNo;
	private int pageSize=10;
	/** 搜索条件 */
	private int source;
	/** 搜索条件 */
	private String content;
	private String batchMessgeId;
	
	/** 搜索条件 */
	private int sourceModify;
	/** 搜索条件 */
	private String contentModify;
	
	private Message message;
	
	/**
	 * 信息列表
	 * @return
	 */
	public String list(){
		if(pageNo<=0){
			pageNo=1;
		}
		String hql="from Message where state=0 and 1=1";
		if(source != 0 && source != -1){
			hql = hql + " and source=" + source;
		}
		if(content != null && !content.equals("")){
			hql = hql + " and content like '%"+content+"%'";
		}
		hql = hql + "order by publishTime desc";
		Page page = messageService.pagedQuery(hql, pageNo, pageSize);
		this.setRequestAttribute("page", page);
		return "list";
	}
	
	/**
	 * @return
	 */
	public String addBefore(){
		return "addBefore";
	}
	
	/**
	 * @return
	 */
	public String deploy(){
		Message message1 = messageService.get(message.getId());
		List<Webuser> webuserList = webUserService.getAllWebUser();
		Message m = null;
		for (Webuser webuser : webuserList) {
			m = new Message();
			m.setUserId(webuser.getId());
			m.setContent(message1.getContent());
			m.setPublisher(message1.getPublisher());
			m.setPublishTime(message1.getPublishTime());
			m.setProcessInstanceId(message1.getProcessInstanceId());
			m.setSource(message1.getSource());
			m.setRefId(message1.getRefId());
			m.setState(message1.getState());
			m.setType(message1.getType());
			messageService.save(m);
		}
		return "deploy";
	}
	
	/**
	 * 添加信息
	 * @return
	 */
	public String add(){
		SysUser sysUser = (SysUser)this.getSessionObj(SessionDict.AdminUser);
		message.setState(0);
		message.setPublisher(sysUser);
		message.setPublishTime(new Date());
		messageService.save(message);
		return "add";
	}
	
	/**
	 * 编辑前
	 * @return
	 */
	public String modifyBefore(){
		Message message1 = messageService.get(message.getId());
		this.setRequestAttribute("message", message1);
		return "modifyBefore";
	}
	
	/**
	 * 逻辑删除信息
	 * @return
	 */
	public String delete(){
		Message message1 = messageService.get(message.getId());
		message1.setState(-1);
		messageService.save(message1);
		return "delete";
	}
	
	public String modify(){
		Message message1 = messageService.get(message.getId());
		message1.setSource(message.getSource());
		message1.setType(message.getType());
		message1.setContent(message.getContent());
		messageService.save(message1);
		return "modify";
	}
	
	public String batchDelete(){
		String[] ids = batchMessgeId.split(",");
		for (int i = 0; i < ids.length; i++) {
			Integer id = Integer.valueOf(ids[i]);
			Message m = messageService.get(id);
			m.setState(-1);
			messageService.save(m);
		}
		return "delete";
	}
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getContent() {
		return content;
	}

	public int getSource() {
		return source;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getBatchMessgeId() {
		return batchMessgeId;
	}

	public void setBatchMessgeId(String batchMessgeId) {
		this.batchMessgeId = batchMessgeId;
	}

	public String getContentModify() {
		return contentModify;
	}

	public int getSourceModify() {
		return sourceModify;
	}

	public void setContentModify(String contentModify) {
		this.contentModify = contentModify;
	}

	public void setSourceModify(int sourceModify) {
		this.sourceModify = sourceModify;
	}

	public WebUserService getWebUserService() {
		return webUserService;
	}

	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
	}

}

 