package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.PhaseTestPolicyReq;
import com.ambow.trainingengine.policy.service.PhaseTestPolicyReqService;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PhaseTestPolicyReqAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="phaseTestPolicyReq";
	public static final String actionName="phaseTestPolicyReq";
	public PhaseTestPolicyReqService phaseTestPolicyReqService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public Integer pid;
	public String ids;
	public int pageNo;	
	public PhaseTestPolicyReq phaseTestPolicyReq;//也可能用于显示 
	
	
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
	private PhaseTestPolicyReq removeTestCanotModifyAttr(PhaseTestPolicyReq phaseTestPolicyReq){
		//PhaseTestPolicyReq phaseTestPolicyReq =new PhaseTestPolicyReq();
		return phaseTestPolicyReq;
	}

	private void setReturnType(Map viewMap){
		if(viewMap ==null||viewMap.size()==0){
			if(id==null){
				rtype=NOID;
			}
			rtype=NOTFOUND;
		}
	}

	@Override
	public String execute(){
		if(atype==null||atype.trim().equals("")){
			atype=defaultRtype;
		}
		rtype=atype;
		if("show".equals(atype)){
			viewMap = phaseTestPolicyReqService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestPolicyReq", viewMap.get("phaseTestPolicyReq"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = phaseTestPolicyReqService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("phaseTestPolicyReq", viewMap.get("phaseTestPolicyReq"));
			setReturnType(viewMap);
		}else if("add".equals(atype)||"iadd".equals(atype)){
			//removePhaseTestPolicyReqCanotModifyAttr(PhaseTestPolicyReq phaseTestPolicyReq);
			errorList = phaseTestPolicyReqService.add(phaseTestPolicyReq);
			if ("iadd".equals(atype)) {
				pid = phaseTestPolicyReq.getPhaseTestPolicyTemplate().getId();
				rtype = "review";
			}
		}else if("update".equals(atype)||"iupdate".equals(atype)){
			phaseTestPolicyReqService.update(phaseTestPolicyReq);
			if ("iupdate".equals(atype)) {
				pid = phaseTestPolicyReq.getPhaseTestPolicyTemplate().getId();
				rtype = "review";
			}
		}else if("list".equals(atype)){
			page = phaseTestPolicyReqService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = phaseTestPolicyReqService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			errorList = phaseTestPolicyReqService.delete(id);
			rtype = "review";
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = phaseTestPolicyReqService.deleteBatch(ids);
			rtype = "review";
		}else if("deleteAll".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = phaseTestPolicyReqService.deleteAll(pid);
			rtype = "review";
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

	public PhaseTestPolicyReqService getPhaseTestPolicyReqService() {
		return phaseTestPolicyReqService;
	}

	public void setPhaseTestPolicyReqService(PhaseTestPolicyReqService phaseTestPolicyReqService) {
		this.phaseTestPolicyReqService = phaseTestPolicyReqService;
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
	
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
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

	public PhaseTestPolicyReq getPhaseTestPolicyReq() {
		return phaseTestPolicyReq;
	}

	public void setPhaseTestPolicyReq(PhaseTestPolicyReq phaseTestPolicyReq) {
		this.phaseTestPolicyReq = phaseTestPolicyReq;
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
