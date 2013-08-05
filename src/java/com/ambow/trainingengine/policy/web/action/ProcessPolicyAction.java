package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.*;
import com.ambow.trainingengine.policy.service.*;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class ProcessPolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="processPolicy";
	public static final String actionName="processPolicy";
	
	public ProcessPolicyService processPolicyService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo;	
	public ProcessPolicy processPolicy;//也可能用于显示 
	public String processPolicyDefaultExamTime ;//也可能用于显示 
	
	//以下用于显示数据
	public Map viewMap;
	public List all;	
	public Page page;
	
	//显示错误消息用
	public List errorList;
	
	/**action Return type*/
	public String rtype;
	
	/**
	 * 替除不允许修改的字段，防止用户非法不允许修改的字段
	 * @param test
	 * @return
	 */
	private ProcessPolicy removeTestCanotModifyAttr(ProcessPolicy processPolicy){
		//ProcessPolicy processPolicy =new ProcessPolicy();
		return processPolicy;
	}

	private void setReturnType(Map viewMap){
		if(viewMap ==null||viewMap.size()==0){
			if(id==null){
				rtype=NOID;
			}
			rtype=NOTFOUND;
		}
	}

	public String execute(){
		if(processPolicy!=null){
			try {
				SimpleDateFormat x = new SimpleDateFormat ("yyyy-MM-dd") ;
				Date date = x.parse(processPolicyDefaultExamTime);
				processPolicy.setDefaultExamTime(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(atype==null||atype.trim().equals("")){
			atype=defaultRtype;
		}
		rtype=atype;
		if("show".equals(atype)){
			viewMap = processPolicyService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("processPolicy", viewMap.get("processPolicy"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = processPolicyService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("processPolicy", viewMap.get("processPolicy"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removeProcessPolicyCanotModifyAttr(ProcessPolicy processPolicy);
			 
			//WEISY 2009年12月30日
			errorList = processPolicyService.add(processPolicy);
			//this.setRequestAttribute("names", ids);//最后重定向了没必要向request中赋值
			
		}else if("update".equals(atype)){
			errorList = processPolicyService.update(processPolicy);
		}else if("list".equals(atype)){
			page = processPolicyService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = processPolicyService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)||"idelete".equals(atype)){
			errorList = processPolicyService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = processPolicyService.deleteBatch(ids);
		}else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		setRequestAttribute("errorList", errorList);
		setRequestAttribute("atype",atype);
		setRequestAttribute("rtype",rtype);
		return rtype;
	}

	public String getDefaultRtype() {
		return defaultRtype;
	}

	public void setDefaultRtype(String defaultRtype) {
		this.defaultRtype = defaultRtype;
	}

	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public ProcessPolicy getProcessPolicy() {
		return processPolicy;
	}

	public void setProcessPolicy(ProcessPolicy processPolicy) {
		this.processPolicy = processPolicy;
	}

	public Map getViewMap() {
		return viewMap;
	}

	public void setViewMap(Map viewMap) {
		this.viewMap = viewMap;
	}

	public List getAll() {
		return all;
	}

	public void setAll(List all) {
		this.all = all;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List getErrorList() {
		return errorList;
	}

	public void setErrorList(List errorList) {
		this.errorList = errorList;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public static String getDojo() {
		return dojo;
	}
	
	public static String getActionName() {
		return actionName;
	}

	public String getProcessPolicyDefaultExamTime() {
		return processPolicyDefaultExamTime;
	}

	public void setProcessPolicyDefaultExamTime(String processPolicyDefaultExamTime) {
		this.processPolicyDefaultExamTime = processPolicyDefaultExamTime;
	}
}
