package com.ambow.trainingengine.policy.web.action;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.policy.service.CommonPolicyService;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.web.action.WebBaseAction;

public class PolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public ParamObject p;
	public Integer id;
	public Long tId;
	public Map v;
	public CommonPolicyService  commonPolicyService;
	public static final String actionName="policy";
	String action;
	
	public String execute(){
		v = commonPolicyService.getAction(id);
		List errorLst= (List)v.get("errorLst");
		action =(String) v.get("action");
		if(errorLst.size()==0 && action!=null){
			return SUCCESS;
		}else{
			setRequestAttribute("errorLst", errorLst);
			return ERROR;
		}		
	}
	public ParamObject getP() {
		return p;
	}
	public void setP(ParamObject p) {
		this.p = p;
	}
	public Map getV() {
		return v;
	}
	public void setV(Map v) {
		this.v = v;
	}
	public CommonPolicyService getCommonPolicyService() {
		return commonPolicyService;
	}
	public void setCommonPolicyService(CommonPolicyService commonPolicyService) {
		this.commonPolicyService = commonPolicyService;
	}
	public String getActionUrl() {
		return action;
	}
	public void setActionUrl(String actionUrl) {
		this.action = actionUrl;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getTId() {
		return tId;
	}
	public void setTId(Long id) {
		tId = id;
	}
	
	public static String getActionName() {
		return actionName;
	}
}
