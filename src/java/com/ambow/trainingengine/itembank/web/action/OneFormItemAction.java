package com.ambow.trainingengine.itembank.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.web.data.ItemFormXwork;
import com.ambow.trainingengine.util.ParamObject;

/*
 * ItemAction.java
 * 
 * Created on 2008-7-15 上午09:39:54
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
public class OneFormItemAction extends BaseAction {
	private static final long serialVersionUID = 2137005976124315345L;
	private Item itemVO;	
	private ParamObject p;
	private String[] reviewRound;
	private ItemService itemService;
	 
	public String saveAll(){
		if(itemVO!=null&&itemVO.getId()!=null){
			Item itemToSave =ItemFormXwork.makeToSaveItem(itemVO,p,itemService);
			itemService.save(itemToSave);
			setRequestAttribute("isEdit", p.get("isEdit"));
			setRequestAttribute("item", itemToSave);
		}
		return "saveAll";
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	public Item getItemVO() {
		return itemVO;
	}
	public void setItemVO(Item item) {
		this.itemVO = item;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public ParamObject getP() {
		return p;
	}
	public void setP(ParamObject param) {
		this.p = param;
	}

	 

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public void setReviewRound(String[] reviewRound) {
		String str = "";
		for (int i = 0; i < reviewRound.length; i++) {
			str = str + reviewRound[i]+",";
		}
		str = str.substring(0,str.lastIndexOf(","));
		this.itemVO.setReviewRound(str);
	}

	public String[] getReviewRound() {
		return reviewRound;
	}
}
