package com.ambow.trainingengine.wordbank.service;

import java.util.List;

import org.springframework.util.Assert;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.wordbank.domain.WordExtension;

/*
 * WordExtensionService.java
 * 
 * Created on 2008-7-22 下午04:26:37
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
public class WordExtensionService extends HibernateEntityDao<WordExtension> {

	public void saveall(List<WordExtension> words) {
		Assert.notNull(words);
		this.saveOrUpdateAll(words);
	}

	public Page list(int pageNo) {
		return this.pagedQuery("from WordExtension W", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String ids) {
		this.excuteHql("delete from WordExtension where id in (" + ids + ")");		
	}
	
	/** 按学级查询 */
	public Page findByGrade(int pageNo, String gradeCode) {
		return this.pagedQuery("from WordExtension W where W.grade.code=?", pageNo, Constants.DEFAULT_PAGE_SIZE, gradeCode);
	}
	
	/** 按条件的查询 */
	public Page findByConditions(int pageNo, String constr) {
		return this.pagedQuery("from WordExtension W where 1=1 " + constr, pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
}
