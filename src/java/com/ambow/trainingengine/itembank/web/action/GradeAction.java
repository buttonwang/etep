package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.service.GradeService;

/*
 * GradeAction.java
 * 
 * Created on 2008-7-11 下午04:07:43
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
public class GradeAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private GradeService gradeService;
	
	private Grade grade; 
	
	private String code = "";
	
	private String codes = "";
	
	private String pcode = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	/** 查看列表 -- 表格显示*/
	public String list2(){
		Page page = null;
		if (queryValue.equals(""))
			page = this.gradeService.list(pageNo);
		else if (queryType.equals("1"))
			page  = this.gradeService.findByCode(pageNo, queryValue);
		else if (queryType.equals("2"))
			page  = this.gradeService.findByName(pageNo, "%"+queryValue+"%");
		
		this.setRequestAttribute("page", page);
		return "home";
	}
	
	/** 查看列表 -- 树状显示 */
	public String list(){
		List<Grade> gradeList = this.gradeService.findTop();		
		this.setRequestAttribute("gradeList", gradeList);
		return "home";
	}
	
	/** 保存对象 */
	public String save(){
		this.gradeService.saveVO(grade);
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.grade = this.gradeService.get(code);
		this.setRequestAttribute("grade", this.grade);
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.grade = this.gradeService.get(code);
		
		this.setRequestAttribute("grade", grade);
		this.setRequestAttribute("pcode", (pcode.equals(""))?this.gradeService.getPCode(grade):pcode);
		this.setRequestAttribute("gradeList", this.gradeService.findOther(grade));
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.gradeService.removeById(code);
		return "redirect";
	}
	
	/**删除全部对象*/
	public String deleteAll(){
		this.gradeService.deleteAll();
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.gradeService.deleteBatch(codes);
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

	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
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

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
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
