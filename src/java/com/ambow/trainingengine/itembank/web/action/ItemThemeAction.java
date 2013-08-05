package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.service.ItemThemeService;
import com.ambow.trainingengine.itembank.service.UtilForGradeSubjectService;

/*
 * ItemThemeAction.java
 * 
 * Created on 2008-7-11 下午04:11:05
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
public class ItemThemeAction extends BaseAction {

private static final long serialVersionUID = 1L;
	
	private ItemThemeService itemThemeService;
	
	private ItemTheme itemTheme;
	
	private String code = "";
	
	private String codes = "";

	private String pcode = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	private UtilForGradeSubjectService utilForGradeSubjectService;
	private String grade_code  ;	
	private String subject_code ;
	public void gradeSubjectList_toRequestAttribute(){
		this.setRequestAttribute("subjectList",  this.utilForGradeSubjectService.find("from Subject" ));
		this.setRequestAttribute("gradeList",  this.utilForGradeSubjectService.find("from Grade"));
		this.setRequestAttribute("grade_code",grade_code);
		this.setRequestAttribute("subject_code",subject_code);
	}
	
	public UtilForGradeSubjectService getUtilForGradeSubjectService() {
		return utilForGradeSubjectService;
	}
	public void setUtilForGradeSubjectService(
			UtilForGradeSubjectService utilForGradeSubjectService) {
		this.utilForGradeSubjectService = utilForGradeSubjectService;
	}
	public String getGrade_code() {
		return grade_code;
	}

	public void setGrade_code(String grade_code) {
		this.grade_code = grade_code;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}
	/** 查看列表 */	
	public String list2(){
		Page page = this.itemThemeService.listOrSearch(queryType,grade_code,subject_code,queryValue,pageNo);		 
		this.setRequestAttribute("page", page);
		gradeSubjectList_toRequestAttribute();
		return "home";
	}	

	/** 查看列表 -- 树形显示 */
	public String list(){
		List<ItemTheme> itemThemeList = this.itemThemeService.findTop();
		this.setRequestAttribute("itemThemeList", itemThemeList);
		gradeSubjectList_toRequestAttribute();
		return "home";
	}
	
	/** 保存对象 */
	public String save(){
		gradeSubjectList_toRequestAttribute();
		this.itemThemeService.saveVO(itemTheme);
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.itemTheme = this.itemThemeService.get(code);
		this.setRequestAttribute("itemTheme", this.itemTheme);
		gradeSubjectList_toRequestAttribute();
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.itemTheme = this.itemThemeService.get(code);
		this.setRequestAttribute("itemTheme", this.itemTheme);
		this.setRequestAttribute("pcode", (pcode.equals(""))?this.itemThemeService.getPCode(itemTheme):pcode);
		this.setRequestAttribute("itemThemeList", this.itemThemeService.findOther(itemTheme));
		gradeSubjectList_toRequestAttribute();
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.itemThemeService.removeById(code);
		return "redirect";
	}
	
	/**删除全部对象*/
	public String deleteAll(){
		this.itemThemeService.deleteAll();
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.itemThemeService.deleteBatch(codes);
		return "redirect";
	}
		
	@Override
	public String execute(){
		return SUCCESS;
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemThemeService getItemThemeService() {
		return itemThemeService;
	}

	public void setItemThemeService(ItemThemeService itemThemeService) {
		this.itemThemeService = itemThemeService;
	}


	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public ItemTheme getItemTheme() {
		return itemTheme;
	}

	public void setItemTheme(ItemTheme itemTheme) {
		this.itemTheme = itemTheme;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}
}
