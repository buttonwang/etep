package com.ambow.trainingengine.itembank.service;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.PaperCategory;
import com.ambow.trainingengine.itembank.web.data.HQLObject;

/*
 * PaperCategoryService.java
 * 
 * Created on 2008-7-11 下午04:01:49
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
public class PaperCategoryService extends HibernateEntityDao<PaperCategory> {

	public Page list(int pageNo) {
		return this.pagedQuery("from PaperCategory", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from PaperCategory where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from PaperCategory R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from PaperCategory R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
	
	
	public void invalid(int id){
		
	}
	
	public Page listOrSearch(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, "PaperCategory");
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
	
	
}
