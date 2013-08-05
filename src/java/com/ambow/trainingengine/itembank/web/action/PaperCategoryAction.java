package com.ambow.trainingengine.itembank.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.PaperCategory;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.service.PaperCategoryService;

/*
 * PaperCategoryAction.java
 * 
 * Created on 2008-7-11 下午04:09:20
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
public class PaperCategoryAction extends BaseAction {

private static final long serialVersionUID = 1L;
	
	private PaperCategoryService paperCategoryService;
	
	private PaperCategory paperCategory; 
	
	private String code = "";
	
	private String codes = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	private String grade_code  ;	
	private String subject_code ;
	public void gradeSubjectList_toRequestAttribute(){
		this.setRequestAttribute("subjectList",  this.paperCategoryService.find("from Subject" ));
		this.setRequestAttribute("gradeList",  this.paperCategoryService.find("from Grade"));
		this.setRequestAttribute("subject_code",  subject_code);
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
	public String list(){
		Page page = this.paperCategoryService.listOrSearch(queryType,grade_code,subject_code,queryValue,pageNo);		 
		this.setRequestAttribute("page", page);
		gradeSubjectList_toRequestAttribute();
		return "home";
	}
		
	/** 保存对象 */
	public String save(){
		PaperCategory actPaperCategory = this.paperCategoryService.get(this.paperCategory.getCode());
		if (actPaperCategory == null) {
			this.paperCategoryService.save(this.paperCategory);
		} else {
			actPaperCategory.setSubject(paperCategoryService.get(Subject.class,paperCategory.getSubject().getCode()));
			actPaperCategory.setGrade(paperCategoryService.get(Grade.class,paperCategory.getGrade().getCode()));
			this.paperCategoryService.save(actPaperCategory);
		}
		
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.paperCategory = this.paperCategoryService.get(code);
		this.setRequestAttribute("paperCategory", this.paperCategory);
		gradeSubjectList_toRequestAttribute();
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.paperCategory = this.paperCategoryService.get(code);
		this.setRequestAttribute("paperCategory", this.paperCategory);
		gradeSubjectList_toRequestAttribute();
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.paperCategoryService.removeById(code);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.paperCategoryService.deleteBatch(codes);
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

	public PaperCategoryService getPaperCategoryService() {
		return paperCategoryService;
	}

	public void setPaperCategoryService(PaperCategoryService paperCategoryService) {
		this.paperCategoryService = paperCategoryService;
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

	public PaperCategory getPaperCategory() {
		return paperCategory;
	}

	public void setPaperCategory(PaperCategory paperCategory) {
		this.paperCategory = paperCategory;
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
}
