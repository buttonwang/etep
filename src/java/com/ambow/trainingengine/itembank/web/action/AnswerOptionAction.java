package com.ambow.trainingengine.itembank.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.service.ItemService;

/*
 * AnswerOptionAction.java
 * 
 * Created on 2008-7-20 下午03:01:56
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class AnswerOptionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ItemService itemService;

	private Integer itemId;
	
	private Integer id;
	
	private Integer[] answerOptionId;
	
	private String[] answerOptionCode;

	private String[] answerOptionContent;

	private String[] answerOptionTranslation;
	
	/** 保存对象 */
	public String save() {
		Item item = itemService.get(itemId);
		
		int index = 0;
		for(int i=0; i<answerOptionId.length; i++) {
			if (answerOptionId[i].equals(this.id)) {
				index = i;
				break;
			}
		}
		
		if (id==0) {
			AnswerOption answerOption = new AnswerOption();
			answerOption.setCode(answerOptionCode[index]);
			answerOption.setContent(answerOptionContent[index]);
			answerOption.setTranslation(answerOptionTranslation[index]);
			answerOption.setItem(item);
			item.getAnswerOptions().add(answerOption);
		} else 
			for (AnswerOption answerOption: item.getAnswerOptions()){
				if (answerOption.getId().equals(this.id)) {
					answerOption.setCode(answerOptionCode[index]);
					answerOption.setContent(answerOptionContent[index]);
					//answerOption.setTranslation(answerOptionTranslation[index]);
					break;
				}
			}
		
		itemService.save(item);		
		return "showItem";
	}
	
	/**删除对象 */
	public String delete() {
		Item item = itemService.get(itemId);
		
		for (AnswerOption answerOption: item.getAnswerOptions()){
			if (answerOption.getId().equals(this.id)) {
				item.getAnswerOptions().remove(answerOption);
				answerOption.setItem(null);
				break;
			}
		}
		
		itemService.save(item);		
		return "showItem";
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer[] getAnswerOptionId() {
		return answerOptionId;
	}

	public void setAnswerOptionId(Integer[] answerOptionId) {
		this.answerOptionId = answerOptionId;
	}

	public String[] getAnswerOptionCode() {
		return answerOptionCode;
	}

	public void setAnswerOptionCode(String[] answerOptionCode) {
		this.answerOptionCode = answerOptionCode;
	}

	public String[] getAnswerOptionContent() {
		return answerOptionContent;
	}

	public void setAnswerOptionContent(String[] answerOptionContent) {
		this.answerOptionContent = answerOptionContent;
	}

	public String[] getAnswerOptionTranslation() {
		return answerOptionTranslation;
	}

	public void setAnswerOptionTranslation(String[] answerOptionTranslation) {
		this.answerOptionTranslation = answerOptionTranslation;
	}

}
