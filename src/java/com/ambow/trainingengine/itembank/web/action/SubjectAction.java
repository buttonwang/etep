package com.ambow.trainingengine.itembank.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.service.SubjectService;

/*
 * SubjectAction.java
 * 
 * Created on 2008-7-11 下午03:24:01
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
public class SubjectAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private SubjectService subjectService;
	
	private Subject subject; 
	
	private String code = "";
	
	private String codes = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	/** 查看列表 */
	public String list(){
		Page page = null;
		if (queryValue.equals(""))
			page = this.subjectService.list(pageNo);
		else if (queryType.equals("1"))
			page  = this.subjectService.findByCode(pageNo, queryValue);
		else if (queryType.equals("2"))
			page  = this.subjectService.findByName(pageNo, "%"+queryValue+"%");
		
		this.setRequestAttribute("page", page);
		return "home";
	}
		
	/** 保存对象 */
	public String save(){
		Subject actSubject = this.subjectService.get(this.subject.getCode());
		if (actSubject == null) {
			this.subjectService.save(this.subject);
		} else {
			actSubject.setName(this.subject.getName());
			this.subjectService.save(actSubject);
		}
		
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.subject = this.subjectService.get(code);
		this.setRequestAttribute("subject", this.subject);
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.subject = this.subjectService.get(code);
		this.setRequestAttribute("subject", this.subject);
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.subjectService.removeById(code);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.subjectService.deleteBatch(codes);
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

	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
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

	public Subject getRegion() {
		return subject;
	}

	public void setRegion(Subject subject) {
		this.subject = subject;
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
