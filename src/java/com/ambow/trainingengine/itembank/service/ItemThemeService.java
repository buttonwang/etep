package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.web.data.HQLObject;

/*
 * ItemThemeService.java
 * 
 * Created on 2008-7-11 下午04:05:57
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
public class ItemThemeService extends HibernateEntityDao<ItemTheme> {
	
	private List<ItemTheme> gList = new ArrayList<ItemTheme>(0);
	
	public Page list(int pageNo) {
		return this.pagedQuery("from ItemTheme", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemTheme> findTop() {
		return this.find("from ItemTheme where parent_code is null " );
	}
	
	@SuppressWarnings("unchecked")
	public List<ItemTheme> findOther(ItemTheme itemTheme) {
		List<ItemTheme> itemThemeList = this.getAll("parentLevel", true);
		if (itemTheme!=null) {
			synchronized (gList) {
				gList.clear();
				getAllChildrenGrades(itemTheme);
				itemThemeList.removeAll(gList);
				itemThemeList.remove(itemTheme);
				gList.clear();
			}
		}
		return itemThemeList;
	}
	
	/**获取全部子节点*/
	private void getAllChildrenGrades(ItemTheme itemTheme) {		
		for(ItemTheme tmpItemTheme: itemTheme.getChildrenItemThemes()) {
			gList.add(tmpItemTheme);
			getAllChildrenGrades(tmpItemTheme);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getPCode(ItemTheme itemTheme) {
		String PCode = "";
		if (itemTheme!=null)
			if (itemTheme.getParentItemTheme()!=null) PCode = itemTheme.getParentItemTheme().getCode();
		return PCode;
	}
	
	public void saveVO(ItemTheme itemTheme) {
		ItemTheme actItemTheme = get(itemTheme.getCode());		
		
		if (actItemTheme == null) {
			actItemTheme = itemTheme;
		} else {
			actItemTheme.setCode(itemTheme.getCode());
			actItemTheme.setName(itemTheme.getName());
			actItemTheme.setSubject(get(Subject.class, itemTheme.getSubject().getCode()));
			actItemTheme.setGrade(get(Grade.class, itemTheme.getGrade().getCode()));
		}
		actItemTheme.setParentItemTheme(get(itemTheme.getParentItemTheme().getCode()));
		actItemTheme.genparentLevel();
		save(actItemTheme);
	}
	
	public void deleteAll() {
		this.excuteHql("delete from ItemTheme");
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from ItemTheme where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from ItemTheme R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from ItemTheme R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
	
	public Page listOrSearch(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, "ItemTheme");
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
	
	public List<ItemTheme> findBySubject(String subject)
	{
		
		List<ItemTheme> list = this.find("from ItemTheme where subject.code=?", subject);
		return list;
	}
}
