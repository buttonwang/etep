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
public class TrainingUnitNodePolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="training_unit_node_policy";
	public static final String actionName="trainingUnitNodePolicy";
	public TrainingUnitNodePolicyService trainingUnitNodePolicyService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo;	
	public TrainingUnitNodePolicy trainingUnitNodePolicy;//也可能用于显示 
	
	
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
	private TrainingUnitNodePolicy removeTestCanotModifyAttr(TrainingUnitNodePolicy trainingUnitNodePolicy){
		//TrainingUnitNodePolicy trainingUnitNodePolicy =new TrainingUnitNodePolicy();
		return trainingUnitNodePolicy;
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
			viewMap = trainingUnitNodePolicyService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("trainingUnitNodePolicy", viewMap.get("TrainingUnitNodePolicy"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = trainingUnitNodePolicyService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("trainingUnitNodePolicy", viewMap.get("TrainingUnitNodePolicy"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removeTrainingUnitNodePolicyCanotModifyAttr(TrainingUnitNodePolicy trainingUnitNodePolicy);
			errorList = trainingUnitNodePolicyService.add(trainingUnitNodePolicy);
		}else if("update".equals(atype)||"iupdate".equals(atype)){
			trainingUnitNodePolicyService.update(trainingUnitNodePolicy);
		}else if("list".equals(atype)){
			page = trainingUnitNodePolicyService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = trainingUnitNodePolicyService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)||"showNodedelete".equals(atype)){
			errorList = trainingUnitNodePolicyService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = trainingUnitNodePolicyService.deleteBatch(ids);
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

	public TrainingUnitNodePolicyService getTrainingUnitNodePolicyService() {
		return trainingUnitNodePolicyService;
	}

	public void setTrainingUnitNodePolicyService(TrainingUnitNodePolicyService trainingUnitNodePolicyService) {
		this.trainingUnitNodePolicyService = trainingUnitNodePolicyService;
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

	public TrainingUnitNodePolicy getTrainingUnitNodePolicy() {
		return trainingUnitNodePolicy;
	}

	public void setTrainingUnitNodePolicy(TrainingUnitNodePolicy trainingUnitNodePolicy) {
		this.trainingUnitNodePolicy = trainingUnitNodePolicy;
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
