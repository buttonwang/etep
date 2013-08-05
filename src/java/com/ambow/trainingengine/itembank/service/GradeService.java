package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Grade;

/*
 * GradeService.java
 * 
 * Created on 2008-7-11 下午03:55:31
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
public class GradeService extends HibernateEntityDao<Grade> {
	
	private List<Grade> gList = new ArrayList<Grade>(0);
	
	public Page list(int pageNo) {
		return this.pagedQuery("from Grade", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	@SuppressWarnings("unchecked")
	public List<Grade> findTop() {
		return this.find("from Grade where parent_code is null ");
	}

	@SuppressWarnings("unchecked")
	public List<Grade> findLeaf() {
		List<Grade> allGrades =  this.getAll();
		List<Grade> leafGrades = new ArrayList<Grade>(0);
		
		for (Grade grade: allGrades) {
			if (grade.getChildrenGrades().size()==0) leafGrades.add(grade);
		}
		return leafGrades;
	}

	@SuppressWarnings("unchecked")
	public List<Grade> findOther(Grade grade) {
		List<Grade> gradeList = this.getAll("parentLevel", true);
		if (grade!=null) {
			synchronized (gList) {
				gList.clear();
				getAllChildrenGrades(grade);
				gradeList.removeAll(gList);
				gradeList.remove(grade);
				gList.clear();
			}
		}
		return gradeList;
	}
	
	/**获取全部子节点*/
	private void getAllChildrenGrades(Grade grade) {		
		for(Grade tmpgrade: grade.getChildrenGrades()) {
			gList.add(tmpgrade);
			getAllChildrenGrades(tmpgrade);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getPCode(Grade grade) {
		String PCode = "";
		if (grade!=null)
			if (grade.getParentGrade()!=null) PCode = grade.getParentGrade().getCode();
		return PCode;
	}
		
	public void saveVO(Grade grade) {		
		Grade actGrade = get(grade.getCode());
		
		if (actGrade == null) {
			actGrade = grade;
		} else {
			actGrade.setCode(grade.getCode());
			actGrade.setName(grade.getName());		
		}		
		actGrade.setParentGrade(get(grade.getParentGrade().getCode()));		
		actGrade.genparentLevel();
		save(actGrade);
	}
	
	public void deleteAll() {
		this.excuteHql("delete from Grade");		
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from Grade where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from Grade R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from Grade R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
}
