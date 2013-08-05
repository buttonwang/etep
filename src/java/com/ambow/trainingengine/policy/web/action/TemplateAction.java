package com.ambow.trainingengine.policy.web.action;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.policy.service.TemplateService;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.web.action.WebBaseAction;

public class TemplateAction  extends WebBaseAction {
	private static final long serialVersionUID = 3799601979106543414L;
	
	public String tType ;
	public Long nid;
	public Integer tid;
	public ParamObject p;
	public int pageNo = 1;
	public Map v;
	public TemplateService templateService;
	public static final String actionName="template";
	public String searchType;
	public String nameForSearch;
	
	public String atype;
	
	@Override
	public String execute(){
		String rType = "error";
		if("get".equals(atype)){
			v = templateService.getTemplates(tType,nid,pageNo, p);
			setRequestAttribute("v", v);
			if(((List) v.get("errorlst")).size() ==0){
				rType= "getTemplateSuccess";
			}else{
				rType= "getTemplateError";
			}
		}else if("set".equals(atype)){
			if(tType!=null&&!"".equals(tType)){
				v= templateService.initTemplate(tType, nid, tid, p);
				if(v!=null&&((List)v.get("errorLst")).size()==0){
					rType= "initTemplateSuccess";
				}else{
					rType= "initTemplateError";
				}
			}
		}else if("inherit".equals(atype)||"inheritNew".equals(atype)){
			if(tType!=null&&!"".equals(tType)){
				v= templateService.inheritTemplate(tType, nid , p);
				if(v!=null&&((List)v.get("errorLst")).size()==0){
					rType= "initTemplateSuccess";
				}else{
					rType= "initTemplateError";
				}
				if("inheritNew".equals(atype)){
					rType =  atype ;
				}
			}
		}else if("search".equals(atype)){
			v= templateService.search(searchType,nameForSearch,p); 
			setRequestAttribute("searchType", searchType);
			setRequestAttribute("nameForSearch", nameForSearch);
			setRequestAttribute("searchList", v.get("searchList"));
			rType=atype;
		}
		setRequestAttribute("nid", nid);
		setRequestAttribute("v", v);
		v.put("rType",rType);
		if(p!=null&&p.get("to")!=null){
			this.setRequestAttribute("to", p.get("to"));
			return p.get("to");
		}
		return rType;
	}
	public String getTType() {
		return tType;
	}

	public void setTType(String type) {
		tType = type;
	}

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public Map getV() {
		return v;
	}

	public void setV(Map v) {
		this.v = v;
	}

	public TemplateService getTemplateService() {
		return templateService;
	}

	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	public String getAtype() {
		return atype;
	}

	public void setAtype(String atype) {
		this.atype = atype;
	}
	
	public static String getActionName() {
		return actionName;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getNameForSearch() {
		return nameForSearch;
	}
	public void setNameForSearch(String nameForSearch) {
		this.nameForSearch = nameForSearch;
	}
}
