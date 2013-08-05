package com.ambow.trainingengine.itembank.service;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.Paper;

/*
 * PaperService.java
 * 
 * Created on 2008-7-14 上午09:55:52
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
public class PaperService extends HibernateEntityDao<Paper> {

	public Page list(int pageNo) {
		return this.pagedQuery("from Paper P", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String ids) {
		this.excuteHql("delete from Paper where id in (" + ids + ")");
	}
	
	/** 多条件的查询 */
	public Page findByConditions(int pageNo, String constr) {
		return this.pagedQuery("from Paper P where 1=1 " + constr + " order by P.id desc", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/** 返回试卷对应的试题的分页对象 */
	public Page findItems(int pageNo, Integer id) {
		return this.pagedQuery("select I from Paper P right join P.items I where P.id=? order by I.itemType", pageNo, Constants.DEFAULT_PAGE_SIZE*2, id);
	}
	
	/** 重新计算试卷的题数、分值、答题时间 */
	public void calculatePaperParams(Paper paper) {
		Integer answeringTime = 0;
		Integer itemsNum = 0;
		Float totalScore = new Float(0.00);
		
		for (Item item: paper.getItems()){
			answeringTime += (item.getAnsweringTime()==null)?0:item.getAnsweringTime();
			itemsNum += (item.getSubItems().size()==0)?1:item.getSubItems().size();
			totalScore += (item.getScore()==null)?0:item.getScore();
		}
		paper.setAnsweringTime(answeringTime);
		paper.setItemsNum(itemsNum);
		paper.setBigItemsNum(paper.getItems().size());
		paper.setTotalScore(totalScore.intValue());
	}
}
