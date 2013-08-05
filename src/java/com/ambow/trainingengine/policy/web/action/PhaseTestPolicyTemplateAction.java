package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.*;
import com.ambow.trainingengine.policy.service.*;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PhaseTestPolicyTemplateAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="phase_test_policy_template";
	public static final String actionName="phaseTestPolicyTemplate";
	public PhaseTestPolicyTemplateService phaseTestPolicyTemplateService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Integer id;
	public String ids;
	public int pageNo;	
	public PhaseTestPolicyTemplate phaseTestPolicyTemplate;//也可能用于显示 
	
	
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
	private PhaseTestPolicyTemplate removeTestCanotModifyAttr(PhaseTestPolicyTemplate phaseTestPolicyTemplate){
		//PhaseTestPolicyTemplate phaseTestPolicyTemplate =new PhaseTestPolicyTemplate();
		return phaseTestPolicyTemplate;
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
		if(atype==null||atype.trim().equals("")){
			atype=defaultRtype;
		}
		rtype=atype;
		if("show".equals(atype)){
			viewMap = phaseTestPolicyTemplateService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestPolicyTemplate", viewMap.get("PhaseTestPolicyTemplate"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = phaseTestPolicyTemplateService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestPolicyTemplate", viewMap.get("PhaseTestPolicyTemplate"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removePhaseTestPolicyTemplateCanotModifyAttr(PhaseTestPolicyTemplate phaseTestPolicyTemplate);
			errorList = phaseTestPolicyTemplateService.add(phaseTestPolicyTemplate);
		}else if("update".equals(atype)){
			phaseTestPolicyTemplateService.update(phaseTestPolicyTemplate);
		}else if("list".equals(atype)){
			page = phaseTestPolicyTemplateService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = phaseTestPolicyTemplateService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			errorList = phaseTestPolicyTemplateService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = phaseTestPolicyTemplateService.deleteBatch(ids);
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

	public PhaseTestPolicyTemplateService getPhaseTestPolicyTemplateService() {
		return phaseTestPolicyTemplateService;
	}

	public void setPhaseTestPolicyTemplateService(PhaseTestPolicyTemplateService phaseTestPolicyTemplateService) {
		this.phaseTestPolicyTemplateService = phaseTestPolicyTemplateService;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
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

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public PhaseTestPolicyTemplate getPhaseTestPolicyTemplate() {
		return phaseTestPolicyTemplate;
	}

	public void setPhaseTestPolicyTemplate(PhaseTestPolicyTemplate phaseTestPolicyTemplate) {
		this.phaseTestPolicyTemplate = phaseTestPolicyTemplate;
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
}
