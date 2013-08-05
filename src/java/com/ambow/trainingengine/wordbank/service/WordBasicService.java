package com.ambow.trainingengine.wordbank.service;

import java.util.List;

import org.springframework.util.Assert;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.wordbank.domain.ChineseMeaning;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.domain.WordType;

/*
 * WordBasicService.java
 * 
 * Created on 2008-7-21 上午11:15:11
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
public class WordBasicService extends HibernateEntityDao<WordBasic> {

	public void saveall(List<WordBasic> words) {
		Assert.notNull(words);
		this.saveOrUpdateAll(words);
	}

	public Page list(int pageNo) {
		return this.pagedQuery("from WordBasic W", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String ids) {
		this.excuteHql("delete from WordBasic where id in (" + ids + ")");		
	}
	
	/** 按条件的查询 */
	public Page findByConditions(int pageNo, String constr) {
		return this.pagedQuery("from WordBasic P where 1=1 " + constr, pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public boolean hasSameName(WordBasic wordbasic) {
		if (wordbasic==null) return false;
		return this.find("from WordBasic where id<>? and word=?", wordbasic.getId()==null?0:wordbasic.getId(), wordbasic.getWord().trim()).size()>0;
	}
	
	/** 转换词性，中文释义的全半角 */
	@SuppressWarnings("unchecked")
	public void refreshWord() {
		List<WordType> wordTypes = this.find("from WordType");
		for (WordType wt: wordTypes) {
			if (wt.getCode()!=null && !wt.getCode().equals(""))
				wt.setCode(ExamUtil.ToDBC(wt.getCode()));
			for (ChineseMeaning cm: wt.getChineseMeanings()) {
				if (cm.getMeaning()!=null && !cm.getMeaning().equals(""))
					cm.setMeaning(ExamUtil.ToDBC(cm.getMeaning()));
			}
		}
		saveOrUpdateAll(wordTypes);
	}
	
}
