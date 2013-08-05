package com.ambow.trainingengine.wordbank.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.service.WordBasicService;

/*
 * WordBasicAction.java
 * 
 * Created on 2008-7-22 上午09:25:50
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
public class WordBasicAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private WordBasic wordBasic;
	private WordBasicService wordBasicService;
	private Integer id = 0;
	private String ids = "";	
	private String queryValue = "";
	private int pageNo = 1;	
	private String errorInfo = "";
	
	/** 查看列表 */
	public String list(){
		Page page = null;
		if (queryValue.equals(""))
			page = this.wordBasicService.list(pageNo);
		else {
			String queryStr = " and word like '%" + queryValue.trim() + "%' ";
			page  = this.wordBasicService.findByConditions(pageNo, queryStr);
		}
		
		this.setRequestAttribute("page", page);
		return "home";
	}
		
	/** 保存对象 */
	public String save(){
		if (this.wordBasicService.hasSameName(this.wordBasic)) {
			this.errorInfo = "单词拼写已存在，请重新输入！";
			return saveError();
		}
		this.wordBasicService.save(this.wordBasic);
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.wordBasic = this.wordBasicService.get(id);
		this.setRequestAttribute("wordBasic", this.wordBasic);
				
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.wordBasic = this.wordBasicService.get(id);
		this.setRequestAttribute("wordBasic", this.wordBasic);
				
		return INPUT;
	}
	
	/**保存出错后的错误页面，让用户可以看到错误数据并对其进行修改*/
	public String saveError(){
		this.setRequestAttribute("wordBasic", this.wordBasic);
		return INPUT;
	}
	
	
	/**删除对象*/
	public String delete(){
		this.wordBasicService.removeById(id);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		String[] idArray = ids.split(",");
		for(String id: idArray){
			this.wordBasicService.removeById(Integer.parseInt(id));
		}
		
		return "redirect";
	}
		 	
	/**朗读单词*/
	public String readAloud(){
		return SUCCESS;
	}
	
	/**批量修改单词信息*/
	public String refreshBatch(){
		wordBasicService.refreshWord();
		return "redirect";
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public WordBasic getWordBasic() {
		return wordBasic;
	}

	public void setWordBasic(WordBasic wordBasic) {
		this.wordBasic = wordBasic;
	}

	public WordBasicService getWordBasicService() {
		return wordBasicService;
	}

	public void setWordBasicService(WordBasicService wordBasicService) {
		this.wordBasicService = wordBasicService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
