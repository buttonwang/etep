package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.policy.service.PhaseTestNodePolicyService;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PhaseTestNodePolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="phase_test_node_policy";
	public static final String actionName="phaseTestNodePolicy";
	public PhaseTestNodePolicyService phaseTestNodePolicyService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long nodeId;
	public Integer id;
	public String ids;
	public int pageNo;	
	public PhaseTestNodePolicy phaseTestNodePolicy;//也可能用于显示 
	public String showOpt;
	
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
	private PhaseTestNodePolicy removeTestCanotModifyAttr(PhaseTestNodePolicy phaseTestNodePolicy){
		//PhaseTestNodePolicy phaseTestNodePolicy =new PhaseTestNodePolicy();
		return phaseTestNodePolicy;
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
			viewMap = phaseTestNodePolicyService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestNodePolicy", viewMap.get("phaseTestNodePolicy"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = phaseTestNodePolicyService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestNodePolicy", viewMap.get("phaseTestNodePolicy"));
			setReturnType(viewMap);
		}else if("add".equals(atype)||"iadd".equals(atype)){
			//removePhaseTestNodePolicyCanotModifyAttr(PhaseTestNodePolicy phaseTestNodePolicy);
			errorList = phaseTestNodePolicyService.add(phaseTestNodePolicy);
		}else if("update".equals(atype)){
			phaseTestNodePolicyService.update(phaseTestNodePolicy);
		}else if("list".equals(atype)){
			page = phaseTestNodePolicyService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = phaseTestNodePolicyService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)||"idelete".equals(atype)||"showNodedelete".equals(atype)){
			errorList = phaseTestNodePolicyService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = phaseTestNodePolicyService.deleteBatch(ids);
		}else if("showByNodeId".equals(atype)){
			viewMap = phaseTestNodePolicyService.showByNodeIdViewMap(nodeId);
			errorList =(List) viewMap.get("errorList");
			setRequestAttribute("nodeId",nodeId);
			setRequestAttribute("showOpt",showOpt);
		}else if("iupdate".equals(atype)){
			phaseTestNodePolicyService.update(phaseTestNodePolicy);
		}else{
			rtype=NOOPTTYPE;
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

	public PhaseTestNodePolicyService getPhaseTestNodePolicyService() {
		return phaseTestNodePolicyService;
	}

	public void setPhaseTestNodePolicyService(PhaseTestNodePolicyService phaseTestNodePolicyService) {
		this.phaseTestNodePolicyService = phaseTestNodePolicyService;
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

	public PhaseTestNodePolicy getPhaseTestNodePolicy() {
		return phaseTestNodePolicy;
	}

	public void setPhaseTestNodePolicy(PhaseTestNodePolicy phaseTestNodePolicy) {
		this.phaseTestNodePolicy = phaseTestNodePolicy;
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

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getShowOpt() {
		return showOpt;
	}

	public void setShowOpt(String showAdd) {
		this.showOpt = showAdd;
	}
	
	public static String getActionName() {
		return actionName;
	}
}
