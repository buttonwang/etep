package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Paper;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.service.PaperAssemblingPolicyService;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.RequestAttributeByMap;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PaperAssemblingPolicyAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="paper_assembling_policy";
	public static final String actionName="paperAssemblingPolicy";
	public PaperAssemblingPolicyService paperAssemblingPolicyService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo=1;	
	public PaperAssemblingPolicy paperAssemblingPolicy;//也可能用于显示 
	public String paperIds;
	
	public ParamObject p;
	
	
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
	private PaperAssemblingPolicy removeTestCanotModifyAttr(PaperAssemblingPolicy paperAssemblingPolicy){
		//PaperAssemblingPolicy paperAssemblingPolicy =new PaperAssemblingPolicy();
		return paperAssemblingPolicy;
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
			viewMap = paperAssemblingPolicyService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("paperAssemblingPolicy", viewMap.get("PaperAssemblingPolicy"));
			RequestAttributeByMap.setAttributeByMap(getHttpServletRequest(),viewMap);			
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = paperAssemblingPolicyService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("paperAssemblingPolicy", viewMap.get("PaperAssemblingPolicy"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removePaperAssemblingPolicyCanotModifyAttr(PaperAssemblingPolicy paperAssemblingPolicy);
			errorList = paperAssemblingPolicyService.add(paperAssemblingPolicy);
		}else if("update".equals(atype)||"iupdate".equals(atype)||"selectPaper".equals(atype)){
			Integer paperId = null;
			if(paperIds!=null&&paperIds.trim().length()>0){
				if( paperIds.trim().indexOf(",")>-1){
					paperAssemblingPolicy.setPaperList(paperIds);//设置为多套卷子
					paperAssemblingPolicy.setPaperAssemblingMode(3);
				}else{
					paperId = new Integer(paperIds);
					paperAssemblingPolicy.setPaperAssemblingMode(0);
				}	
			}			
			errorList=paperAssemblingPolicyService.update(paperAssemblingPolicy,paperId);
			if("selectPaper".equals(atype)){
				setRequestAttribute("paperLst",paperAssemblingPolicyService.getAll(Paper.class));
			}
		}else if("list".equals(atype)){
			page = paperAssemblingPolicyService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = paperAssemblingPolicyService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)||"showNodedelete".equals(atype)){
			errorList = paperAssemblingPolicyService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = paperAssemblingPolicyService.deleteBatch(ids);
		}else if("setPaper".equals(atype)){
			paperAssemblingPolicyService.setPaperById(paperAssemblingPolicy); 
		}else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		setRequestAttribute("v",viewMap);
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

	public PaperAssemblingPolicyService getPaperAssemblingPolicyService() {
		return paperAssemblingPolicyService;
	}

	public void setPaperAssemblingPolicyService(PaperAssemblingPolicyService paperAssemblingPolicyService) {
		this.paperAssemblingPolicyService = paperAssemblingPolicyService;
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

	public PaperAssemblingPolicy getPaperAssemblingPolicy() {
		return paperAssemblingPolicy;
	}

	public void setPaperAssemblingPolicy(PaperAssemblingPolicy paperAssemblingPolicy) {
		this.paperAssemblingPolicy = paperAssemblingPolicy;
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

	public String getPaperIds() {
		return paperIds;
	}

	public void setPaperIds(String paperIds) {
		this.paperIds = paperIds;
	}
}
