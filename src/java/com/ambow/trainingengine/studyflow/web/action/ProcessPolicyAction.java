package com.ambow.trainingengine.studyflow.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.web.action.WebBaseAction;
import com.ambow.trainingengine.policy.domain.*;
import com.ambow.trainingengine.studyflow.service.*;

public class ProcessPolicyAction extends WebBaseAction {
	private ProcessPolicyService processPolicyService;
	private ProcessPolicy processPolicy;
	private int pageNo = 1;
	private long id;
	private String ids="";
	
	/**
	 * 替除不允许修改的字段，防止用户非法不允许修改的字段
	 * @param processPolicy
	 * @return
	 */
	private ProcessPolicy removeProcessPolicyCanotModifyAttr(ProcessPolicy processPolicy){
		//ProcessPolicy temp =new ProcessPolicy();
		return processPolicy;
	}

	/** 查看列表 */
	public String list(){
		Page page = null;
		page = this.processPolicyService.list(pageNo);
		this.setRequestAttribute("page", page);
		return "list";
	}

	public String show(){
		this.processPolicy = this.processPolicyService.get(id);
		this.setRequestAttribute("processPolicy", this.processPolicy);
		return SUCCESS;
	}

	/** 保存对象 */
	public String save(){
		//ProcessPolicy willSaved=removeProcessPolicyCanotModifyAttr(processPolicy);
		this.processPolicyService.save(this.processPolicy);
		return SUCCESS;
	}

	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this. processPolicy= this.processPolicyService.get(id);
		this.setRequestAttribute("processPolicy", this.processPolicy);
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
	 	this.processPolicyService.delete(this.id);
		return SUCCESS;
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
	 
		return SUCCESS;
	}

	@Override
	public String execute(){
		return SUCCESS;
	}

	public ProcessPolicy getProcessPolicy() {
		return processPolicy;
	}

	public void setProcessPolicy(ProcessPolicy processPolicy) {
		this.processPolicy = processPolicy;
	}
	
	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public String getIds() {
		return ids;
	}

	public long getId() {
		return id;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setId(long id) {
		this.id = id;
	}
}
