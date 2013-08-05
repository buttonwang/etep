package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.util.ItemRegx;

/*
 * FixItemAction.java
 * 导入试题之后对需要调整的试题做修改
 * 
 * Created on 2008-9-23 上午11:29:05
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
@SuppressWarnings("serial")
public class FixItemAction extends BaseAction {

	private HibernateGenericDao genService;
	
	
	@Override
	public String execute() {
		//fix47XItem();
		//fix25AItem();
		//fix25AItem2();
		fixMPCItem();
		return "redirect";
	}
	
	
	/** 对47系列的题修改 */
	@SuppressWarnings("unchecked")
	private void fix47XItem() {
		List<Item> items = genService.find("from Item I where I.itemType.code like 'Z1E47%' and I.content like  '%\\_%' ");
		for(Item item: items) {
			item.setContent(ItemRegx.underLine2Html(item.getContent()));			
		}
		genService.saveOrUpdateAll(items);
	}
	
	/** 对25A系列的题修改 */
	@SuppressWarnings("unchecked")
	private void fix25AItem() {
		List<SubItem> subitems = genService.find("from SubItem S where S.item.itemType.code = 'Z1E25A' and S.content not like  '%\\_%'");		
		for(SubItem subitem: subitems) {
			subitem.setContent(ItemRegx.space2UnderLine(subitem.getContent()));
		}
		genService.saveOrUpdateAll(subitems);
	}
	
	/** 对25A系列的题修改 */
	@SuppressWarnings("unchecked")
	private void fix25AItem2() {
		List<SubItem> subitems = genService.find("from SubItem S where S.item.itemType.code = 'Z1E25A' and S.relatedKeyPoints not like  '%\\_%'");		
		for(SubItem subitem: subitems) {
			subitem.setRelatedKeyPoints(ItemRegx.space2UnderLine(subitem.getRelatedKeyPoints()));
		}
		genService.saveOrUpdateAll(subitems);
	}
	
	/** 对MPC系列的题修改 
	 *  1. 去除HTML代码末尾无效的字符
	 *  2. 方法与技巧转化为纯文本的格式 
	 */
	@SuppressWarnings("unchecked")
	private void fixMPCItem() {
		List<Item> items = genService.find("from Item I where I.id > 1325 ");
		System.out.println(items.size());
		for(Item item: items) {
			System.out.println(item.getId());
			item.setContent(ItemRegx.rightTrim(item.getContent()));
			item.setHint(ItemRegx.rightTrim(item.getHint()));
			item.setAnalysisAtLarge1(ItemRegx.rightTrim(item.getAnalysisAtLarge1()));
			item.setAnalysisAtLarge2(ItemRegx.rightTrim(item.getAnalysisAtLarge2()));
			item.setAnalysisAtLarge3(ItemRegx.rightTrim(item.getAnalysisAtLarge3()));
			item.setAnswerPrototype(ItemRegx.rightTrim(item.getAnswerPrototype()));
			item.setSkills(ItemRegx.clearItemHtmlSign(item.getSkills()));
			
			if ((item.getCorrectAnswer()==null)||(item.getCorrectAnswer().equals("")))
				item.setCorrectAnswer(ItemRegx.clearItemHtmlSign(item.getAnswerPrototype()));
			
			for (SubItem subItem: item.getSubItems()) {
				subItem.setContent(ItemRegx.rightTrim(subItem.getContent()));
				subItem.setHint(ItemRegx.rightTrim(subItem.getHint()));
				subItem.setAnalysisAtLarge1(ItemRegx.rightTrim(subItem.getAnalysisAtLarge1()));
				subItem.setAnalysisAtLarge2(ItemRegx.rightTrim(subItem.getAnalysisAtLarge2()));
				subItem.setAnalysisAtLarge3(ItemRegx.rightTrim(subItem.getAnalysisAtLarge3()));
				subItem.setAnswerPrototype(ItemRegx.rightTrim(subItem.getAnswerPrototype()));
				subItem.setSkills(ItemRegx.clearItemHtmlSign(subItem.getSkills()));
				
				if ((subItem.getCorrectAnswer()==null)||(subItem.getCorrectAnswer().equals("")))
					subItem.setCorrectAnswer(ItemRegx.clearItemHtmlSign(subItem.getAnswerPrototype()));
			}
			
			for(AnswerOption answerOption: item.getAnswerOptions()) {
				answerOption.setContent(ItemRegx.rightTrim(answerOption.getContent()));
			}
			
			genService.save(item);
		}
		//genService.saveOrUpdateAll(items);
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

}
