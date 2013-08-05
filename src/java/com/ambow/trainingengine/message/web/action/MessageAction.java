/* 
 * MessageAction.java
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


import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.message.domain.Message;
import com.ambow.trainingengine.message.service.MessageService;
import com.ambow.trainingengine.util.PaginationSupport;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;
 
@SuppressWarnings("serial")
public class MessageAction extends BaseAction {
	
	private int pageNo;
	private int pageSize = 10;
	private Integer source;
	
	/** 批量删除消息id */
	private String messageIds;
	
	private MessageService messageService;
	
	public String list() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		String userId = userData.getUserID();
		long processInstanceId = userData.getProcessInstanceId();
		
		if(pageNo<=0) 	pageNo=1;
		Page page = messageService.getPageMessage(processInstanceId, userId, source, pageNo, pageSize);
		this.setRequestAttribute("messageList", page.getResult());
		PaginationSupport pag = new PaginationSupport((int)page.getTotalCount(), pageNo);
		this.setRequestAttribute("pag", pag);
		this.setRequestAttribute("totalMessage", messageService.getMessageCount(processInstanceId, userId));
		return "list";
	}
	
	public String delete(){
		String[] id = messageIds.split(",");
		for (int i = 0; i < id.length; i++) {
			Message m = messageService.get(Integer.valueOf(id[i]));
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
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public MessageService getMessageService() {
		return messageService;
	}
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	public String getMessageIds() {
		return messageIds;
	}
	public void setMessageIds(String messageIds) {
		this.messageIds = messageIds;
	}

}

 