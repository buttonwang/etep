package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.service.TrainingPolicyService;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class TrainingPolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="training_policy";
	public static final String actionName="trainingPolicy";
	
	public TrainingPolicyService trainingPolicyService;
	
	public ParamObject p;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo;	
	public TrainingPolicy trainingPolicy;//也可能用于显示 
	
	
	//以下用于显示数据
	public Map viewMap=new HashMap();
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
	private TrainingPolicy removeTestCanotModifyAttr(TrainingPolicy trainingPolicy){
		//TrainingPolicy trainingPolicy =new TrainingPolicy();
		return trainingPolicy;
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
		if("show".equals(atype)||"iShow".equals(atype)){
			viewMap = trainingPolicyService.showViewMap(id);
			
			setRequestAttribute("trainingPolicy", viewMap.get("TrainingPolicy"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = trainingPolicyService.editViewMap(id);
			setRequestAttribute("trainingPolicy", viewMap.get("TrainingPolicy"));
			setRequestAttribute("nid", viewMap.get("nid"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removeTrainingPolicyCanotModifyAttr(TrainingPolicy trainingPolicy);
			errorList = trainingPolicyService.add(trainingPolicy);
		}else if("update".equals(atype)||"iupdate".equals(atype)){
			//p.set("tp", trainingPolicy);//p.get("trainingPolicy")
			//2009年12月30日WEISY
			viewMap = trainingPolicyService.update(trainingPolicy);
			errorList = (List)viewMap.get("errorList");
		}else if("list".equals(atype)){
			page = trainingPolicyService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = trainingPolicyService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)||"showNodedelete".equals(atype)){
			errorList = trainingPolicyService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = trainingPolicyService.deleteBatch(ids);
		}else if("sadd".equals(atype)){
			viewMap = trainingPolicyService.sadd(p);
			setRequestAttribute("nid", viewMap.get("nid"));
		}else{
			rtype=NOOPTTYPE;
		}
		try{
			errorList.addAll((List)viewMap.get("errorLst"));
		}catch(Exception e){
		}
		
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		
		setRequestAttribute("v", viewMap);
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

	public TrainingPolicyService getTrainingPolicyService() {
		return trainingPolicyService;
	}

	public void setTrainingPolicyService(TrainingPolicyService trainingPolicyService) {
		this.trainingPolicyService = trainingPolicyService;
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

	public TrainingPolicy getTrainingPolicy() {
		return trainingPolicy;
	}

	public void setTrainingPolicy(TrainingPolicy trainingPolicy) {
		this.trainingPolicy = trainingPolicy;
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

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}
	
	public static String getActionName() {
		return actionName;
	}
}
