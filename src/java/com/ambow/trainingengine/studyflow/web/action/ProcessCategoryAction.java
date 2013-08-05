package com.ambow.trainingengine.studyflow.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.trainingengine.studyflow.service.ProcessCategoryService;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class ProcessCategoryAction  extends WebBaseAction {
	
	private static final long serialVersionUID = 1686477134758816461L;
	
	public String defaultRtype="listAll";
	public static final String dojo="process_category";
	public ProcessCategoryService processCategoryService;

	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo;
	public ProcessCategory processCategory;//也可能用于显示 
	
	public Long parentId;
	public Long processId;
	
	//以下用于显示数据
	public Map viewMap=new HashMap(0);
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
	private ProcessCategory removeTestCanotModifyAttr(ProcessCategory processCategory){
		//ProcessCategory processCategory =new ProcessCategory();
		return processCategory;
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
			viewMap = processCategoryService.showViewMapForPage(id);
			List<Integer> versions = processCategoryService.getVersions(id);
			this.setRequestAttribute("versions", versions);
			setRequestAttribute("processCategory", viewMap.get("processCategory"));
			setRequestAttribute("parentCategory", viewMap.get("parentCategory"));
			setReturnType(viewMap);
			setRequestAttribute("page",viewMap.get("page")); //UPDATOR: L.赵亚
		}else if("edit".equals(atype)){
			viewMap = processCategoryService.editViewMap(id,parentId);
			setRequestAttribute("processCategory", viewMap.get("processCategory"));
			setRequestAttribute("parentCategory", viewMap.get("parentCategory"));
			
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removeProcessCategoryCanotModifyAttr(ProcessCategory processCategory);
			
			errorList = processCategoryService.add(processCategory);
		}else if("update".equals(atype)){
			errorList = processCategoryService.update(processCategory);
		}else if("list".equals(atype)){
			//page = processCategoryService.listByPage(pageNo);
			//setRequestAttribute("page",page);//
			viewMap=processCategoryService.allProcessCategory();
		}else if("listAll".equals(atype)){
			all = processCategoryService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			errorList = processCategoryService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = processCategoryService.deleteBatch(ids);
		}else if("sadd".equals(atype)){
			viewMap = processCategoryService.sadd(parentId);
			setRequestAttribute("parentCategory", viewMap.get("parentCategory"));
		}else if("deleteAll".equals(atype)){
			errorList = processCategoryService .deleteAll();
		}else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		setRequestAttribute("pcListJson",viewMap.get("pcListJson"));
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

	public ProcessCategoryService getProcessCategoryService() {
		return processCategoryService;
	}

	public void setProcessCategoryService(ProcessCategoryService processCategoryService) {
		this.processCategoryService = processCategoryService;
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

	public ProcessCategory getProcessCategory() {
		return processCategory;
	}

	public void setProcessCategory(ProcessCategory processCategory) {
		this.processCategory = processCategory;
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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}
}
