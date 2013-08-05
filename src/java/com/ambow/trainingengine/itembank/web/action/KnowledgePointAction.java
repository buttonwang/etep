package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;

/*
 * KnowledgePointAction.java
 * 
 * Created on 2008-7-11 下午04:10:19
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
public class KnowledgePointAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private KnowledgePointService knowledgePointService;
	
	private KnowledgePoint knowledgePoint;
	
	private String code = "";
	
	private String codes = "";
	
	private String pcode = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	private String grade_code  ;	
	private String subject_code ;
	public void gradeSubjectList_toRequestAttribute(){
		this.setRequestAttribute("subjectList",  this.knowledgePointService.find("from Subject" ));
		this.setRequestAttribute("gradeList",  this.knowledgePointService.find("from Grade"));
		this.setRequestAttribute("grade_code",grade_code);
		this.setRequestAttribute("subject_code",subject_code);
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
		Page page = this.knowledgePointService.listOrSearch(queryType,grade_code,subject_code,queryValue,pageNo);		 
		this.setRequestAttribute("page", page);
		gradeSubjectList_toRequestAttribute();
		return "home";
	}	
	
	/** 查看列表 -- 树形显示 */
	public String list(){
		List<KnowledgePoint> knowledgePointList = null;
		if(grade_code != null) {
			knowledgePointList = this.knowledgePointService.getAllKpsBySubjAndGrade(this.subject_code,this.grade_code);
		}else {
			knowledgePointList = this.knowledgePointService.getAllKpsBySubject(this.subject_code);
		}
		this.setRequestAttribute("knowledgePointList", knowledgePointList);
		gradeSubjectList_toRequestAttribute();
		if("sw".equals(queryType)) {
			return "smallWindow";
		}else if("sg".equals(queryType)) {
			return "studyGuide";
		}else {
			return "home";
		}
	}
	
	/** 保存对象 */
	public String save(){
		this.knowledgePointService.saveVO(knowledgePoint);
		gradeSubjectList_toRequestAttribute();
		return "redirect";
	}
	
	public String abandon(){
		if(code!=null&&!"".equals(code)){
			knowledgePointService.abandon(code);
		}
		gradeSubjectList_toRequestAttribute();
		return "redirect";
	} 
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.knowledgePoint = this.knowledgePointService.get(code);
		this.setRequestAttribute("knowledgePoint", this.knowledgePoint);
		gradeSubjectList_toRequestAttribute();
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.knowledgePoint = this.knowledgePointService.get(code);
		
		this.setRequestAttribute("knowledgePoint", this.knowledgePoint);
		this.setRequestAttribute("pcode", (pcode.equals(""))?this.knowledgePointService.getPCode(knowledgePoint):pcode);
		this.setRequestAttribute("knowledgePointList", this.knowledgePointService.findOther(knowledgePoint));
		gradeSubjectList_toRequestAttribute();
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.knowledgePointService.removeById(code);
		return "redirect";
	}
	
	/**删除全部对象*/
	public String deleteAll(){
		this.knowledgePointService.deleteAll();
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.knowledgePointService.deleteBatch(codes);
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

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
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

	public KnowledgePoint getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
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
