package com.ambow.trainingengine.itembank.web.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ambow.trainingengine.itembank.domain.Item;

public class ItemStatusVO {
	private Item item;
	private BigDecimal allPasses;
	private BigDecimal allPass;
	private BigDecimal reviseRec;
	
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public BigDecimal getAllPasses() {
		return allPasses;
	}
	public void setAllPasses(BigDecimal allPasses) {
		this.allPasses = allPasses;
	}
	public BigDecimal getAllPass() {
		return allPass;
	}
	public void setAllPass(BigDecimal allPass) {
		this.allPass = allPass;
	}
	public BigDecimal getReviseRec() {
		return reviseRec;
	}
	public void setReviseRec(BigDecimal reviseRec) {
		this.reviseRec = reviseRec;
	}
	
	@SuppressWarnings("unchecked")
	public static ItemPageIDsVO getISVOIds(Collection isvoList){
		ItemPageIDsVO ipidVO = new ItemPageIDsVO();
		if(isvoList==null||isvoList.size()==0)
			return null;
		List<String> idsList = new ArrayList<String>();
		for(Object o : isvoList){
			ItemStatusVO isvo = (ItemStatusVO)o;
			idsList.add(isvo.getItem().getId()+"");
		}
		ipidVO.setIdList(idsList);
		
		return ipidVO;
	}
}
