package com.ambow.trainingengine.itembank.service;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.PaperType;
import com.ambow.trainingengine.itembank.web.data.HQLObject;

/*
 * PaperTypeService.java
 * 
 * Created on 2008-7-11 下午04:00:34
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
public class PaperTypeService extends HibernateEntityDao<PaperType> {
	
	public Page list(int pageNo) {
		return this.pagedQuery("from PaperType", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from PaperType where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from PaperType R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from PaperType R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
	
	public Page listOrSearch(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, "PaperType");
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
}
