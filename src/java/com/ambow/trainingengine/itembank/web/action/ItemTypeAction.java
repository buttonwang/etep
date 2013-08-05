package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.service.ItemTypeService;
import com.ambow.trainingengine.itembank.service.UtilForGradeSubjectService;

/*
 * ItemTypeAction.java
 * 
 * Created on 2008-7-11 下午04:09:51
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
public class ItemTypeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private ItemTypeService itemTypeService;
	
	private ItemType itemType; 
	
	private String code = "";
	
	private String codes = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	private UtilForGradeSubjectService utilForGradeSubjectService;
	private String grade_code  ;	
	private String subject_code ;
	public void gradeSubjectList_toRequestAttribute(){
		setRequestAttribute("subjectList",  this.utilForGradeSubjectService.find("from Subject" ));
		setRequestAttribute("gradeList",  this.utilForGradeSubjectService.find("from Grade"));
		setRequestAttribute("subject_code",subject_code);
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
	public String list(){
		List<ItemType> iteTypeList = itemTypeService.getAll();
		for (ItemType itemType : iteTypeList) {
			//System.out.println(itemType.getSubject().getName()+"  subjectCode "+itemType.getSubject().getCode());
			 
		}
		Page page = this.itemTypeService.listOrSearch(queryType,grade_code,subject_code,queryValue,pageNo);		 
		this.setRequestAttribute("page", page);
		gradeSubjectList_toRequestAttribute();
		return "home";
	}
		
	/** 保存对象 */
	public String save(){
		ItemType actItemType = this.itemTypeService.get(this.itemType.getCode());
		if (actItemType == null) {
			this.itemTypeService.save(this.itemType);
		} else {
			actItemType.setName(this.itemType.getName());
			actItemType.setItemNumPerpage(this.itemType.getItemNumPerpage());
			actItemType.setSubject(itemTypeService.get(Subject.class, itemType.getSubject().getCode()));
			actItemType.setGrade(itemTypeService.get(Grade.class, itemType.getGrade().getCode()));
			actItemType.setProjectVersion(itemType.getProjectVersion());
			this.itemTypeService.save(actItemType);
		}
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.itemType = this.itemTypeService.get(code);
		this.setRequestAttribute("itemType", this.itemType);
		gradeSubjectList_toRequestAttribute();
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.itemType = this.itemTypeService.get(code);
		this.setRequestAttribute("itemType", this.itemType);
		gradeSubjectList_toRequestAttribute();
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.itemTypeService.removeById(code);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.itemTypeService.deleteBatch(codes);
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

	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
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

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
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
