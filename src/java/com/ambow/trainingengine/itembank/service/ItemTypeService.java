package com.ambow.trainingengine.itembank.service;

import java.util.List;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.web.data.HQLObject;


public class ItemTypeService extends HibernateEntityDao<ItemType> {
	
	/**
	 * 查找所有题型
	 * @return
	 */
	public List<ItemType> list(String subject,String grade,String type){
		String hql = "from ItemType a ";
		if(subject != null){
			hql = hql + "where a.subject.code='"+subject+"'";
		}
		if(grade != null){
			hql = hql + "and a.grade.code='"+grade+"'";
		}
		if(type != null){
			hql = hql + "and a.code='"+type+"'";
		}
		List<ItemType> list = this.find(hql);
		return list;
	}

	
	public Page list(int pageNo) {
		return this.pagedQuery("from ItemType", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from ItemType where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from ItemType R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from ItemType R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
	public Page listOrSearch(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, "ItemType");
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
	
	public List<ItemType> findBySubject(String subject)
	{
		List<ItemType> list = this.find("from ItemType where subject.code=?", subject);
		return list;
	}
}
