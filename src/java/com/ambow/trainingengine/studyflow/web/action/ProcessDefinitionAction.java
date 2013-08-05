package com.ambow.trainingengine.studyflow.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.trainingengine.studyflow.service.ProcessCategoryService;
import com.ambow.trainingengine.studyflow.service.ProcessDefinitionService;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.UtilAndTool_L;
import com.ambow.trainingengine.web.action.WebBaseAction;

public class ProcessDefinitionAction  extends WebBaseAction {
	
	private static final long serialVersionUID = 1686477134758816461L;	
		
	public String defaultRtype="show";
	public static final String dojo="process";
	public ProcessDefinitionService processDefinitionService;
	public ProcessCategoryService processCategoryService;
	public ProcessCategory processCategory ;
	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Long id;
	public String ids;
	private int pageNo = 1;	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public ProcessDefinition processDefinition;//也可能用于显示 
	public ParamObject p;
	
	
	//以下用于显示数据
	public Map viewMap;
	public List all;	
	public Page page;
	
	//显示错误消息用
	public List errorList;
	
	/**action Return type*/
	public String rtype;
	
	/*********************AUTHOR: L.赵亚***********START****************/
	public String searchObjName;  //确认是哪种查询条件：流程名称、流程分类、创建人、流程状态
	public String searchObjValue; //查询条件下要查询的内容
	public String simpleSearch; // 检查，是简易搜索还是高级搜索
	public String processName; //流程名称
	public String processDefinitionVersion; //流程版本
	public String processDescription; //流程版本
	public String processCategorySearch; //流程分类
	public String processCreator; //创建人
	public String processStatus; //状态
	public String processUpdator; //更新人
	public Object[] processSearchInfo; //查询条件（放所有上面的查询条件信息<采用PrepareStatement时，要把所有Value放到数组里面>）
	/*********************AUTHOR: L.赵亚***********END****************/
	
	

	public String getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}

	public void setProcessDefinitionVersion(String processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}

	public String getProcessDescription() {
		return processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public Object[] getProcessSearchInfo() {
		return processSearchInfo;
	}

	public void setProcessSearchInfo(Object[] processSearchInfo) {
		this.processSearchInfo = processSearchInfo;
	}

	public String getSimpleSearch() {
		return simpleSearch;
	}

	public void setSimpleSearch(String simpleSearch) {
		this.simpleSearch = simpleSearch;
	}

	public String getProcessName() {
		return processName;
	}

	public String getSearchObjName() {
		return searchObjName;
	}

	public void setSearchObjName(String searchObjName) {
		this.searchObjName = searchObjName;
	}

	public String getSearchObjValue() {
		return searchObjValue;
	}

	public void setSearchObjValue(String searchObjValue) {
		this.searchObjValue = searchObjValue;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessCategorySearch() {
		return processCategorySearch;
	}

	public void setProcessCategorySearch(String processCategorySearch) {
		this.processCategorySearch = processCategorySearch;
	}

	public String getProcessCreator() {
		return processCreator;
	}

	public void setProcessCreator(String processCreator) {
		this.processCreator = processCreator;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcessUpdator() {
		return processUpdator;
	}

	public void setProcessUpdator(String processUpdator) {
		this.processUpdator = processUpdator;
	}

	/**
	 * 替除不允许修改的字段，防止用户非法不允许修改的字段
	 * @param test
	 * @return
	 */
	private ProcessDefinition removeTestCanotModifyAttr(ProcessDefinition processDefinition){
		//ProcessDefinition processDefinition =new ProcessDefinition();
		return processDefinition;
	}

	private void setReturnType(Map viewMap){
		if(viewMap ==null||viewMap.size()==0){
			if(id==null){
				rtype=NOID;
			}
			rtype=NOTFOUND;
		}
	}
	
		
	/**
	 * 2009年11月20日增加按流程名称、流程版本查询
	 * WeiShaoying
	 * @return
	 */
	public String findByCondition() {
		List<Integer> versions = null;
		ProcessCategory pc = null;
		if(processDefinition != null) {
			versions = processCategoryService.getVersions(processDefinition.getCategoryId());
			pc = processCategoryService.get(processDefinition.getCategoryId());
			page = processDefinitionService.findLikeObj(processDefinition,pageNo,flag);
		}else {
			page = processDefinitionService.listByPage(pageNo);
		}
		this.setRequestAttribute("page", page);
		this.setRequestAttribute("flag", flag); //UPDATOR: L.赵亚
		this.setRequestAttribute("versions", versions);
		this.setRequestAttribute("processCategory", pc);
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String execute(){
		if(atype==null||atype.trim().equals("")){
			atype=defaultRtype;
		}
		if("show".equals(atype)){
			viewMap = processDefinitionService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("processDefinition", viewMap.get("processDefinition"));
			setRequestAttribute("processPolicy", viewMap.get("processPolicy"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = processDefinitionService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("pcListJson", viewMap.get("pcListJson"));
			setRequestAttribute("processDefinition", viewMap.get("ProcessDefinition"));
			
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
			if(sysUser!=null){
				processDefinition.setCreator(sysUser.getUsername());
			}
			errorList = processDefinitionService.add(processDefinition);
		}else if("update".equals(atype)||"iupdate".equals(atype)){
			SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
			if(sysUser!=null){
				processDefinition.setUpdator(sysUser.getUsername());
			}
			processDefinitionService.update(processDefinition,p);
//		}else if("list".equals(atype)||"search".equals(atype)){
		}else if("list".equals(atype)){
			page = processDefinitionService.listByPage(pageNo);
			Collection c = page.getResult();
			Collection cNew = new ArrayList();
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				cNew.add(processDefinitionService.getPVO((ProcessDefinition) iterator.next()));
			}
			page.setResult(cNew);
			setRequestAttribute("processCategoryLst", processCategoryService.getAll());
			setRequestAttribute("page",page);
		//}else if("search".equals(atype)){
		//	viewMap = processDefinitionService.search(p);
		//	setRequestAttribute("processCategoryLst", processCategoryService.getAll());
		}else if("search".equals(atype)){//用于查询操作      //AUTHOR: L.赵亚 
			String hql = generateSearchHQL();
			page = processDefinitionService.listByPage(hql, pageNo, this.processSearchInfo);
			Collection c = page.getResult();
			Collection cNew = new ArrayList();
			for (Iterator iterator = c.iterator(); iterator.hasNext();) {
				cNew.add(processDefinitionService.getPVO((ProcessDefinition) iterator.next()));
			}
			page.setResult(cNew);
			setRequestAttribute("processCategoryLst", processCategoryService.getAll());
			setRequestAttribute("page",page);
			setRequestAttribute("simpleSearch", simpleSearch);
		}else if("listAll".equals(atype)){
			all = processDefinitionService.getAll();
			setRequestAttribute("all", all);
		}else if("delete".equals(atype)){
			errorList = processDefinitionService.delete(id);
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = processDefinitionService.deleteBatch(ids);
		}else if("abandon".equals(atype)){
			setRequestAttribute("id",id);
			errorList = processDefinitionService.abandon(id);
		}else if("release".equals(atype)){
			setRequestAttribute("id",id);
			errorList = processDefinitionService.release(id);
		}else if("sadd".equals(atype)){
			viewMap = processDefinitionService.sadd( );
			setRequestAttribute("pcListJson", viewMap.get("pcListJson"));
		}else if("abandons".equals(atype)){
			errorList = processDefinitionService.abandons(ids);
		}else if("releases".equals(atype)){
			errorList = processDefinitionService.releases(ids);
		} else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=ERROR;
		}else if(rtype!=NOID&&rtype!=NOTFOUND&&rtype!=NOOPTTYPE){
			rtype=atype;
		};
		
		setRequestAttribute("processCategoryService", processCategoryService);
		setRequestAttribute("v", viewMap);
		setRequestAttribute("errorList", errorList);
		setRequestAttribute("atype",atype);
		setRequestAttribute("rtype",rtype);
		return rtype;
	}
	/*public String copyProcess(){
		Long processId = Long.valueOf(p.get("id"));
		if(processId!=null){
			processDefinition = processDefinitionService.copiedProceddDefinition(processDefinitionService.get(processId),(SysUser)getSession().get(AdminUser ));
		}
		
		return "copyProcess";
	}*/
	
	public String copyProcess() throws Exception{
		
		if(p.get("id")!=null){
			Long processId = Long.valueOf(p.get("id"));
			if(processId!=null){
				processDefinitionService.clonProcess(processId);
			}
		}
		
		if(p.get("ngid")!=null){
			Long nodeGroupId =  Long.valueOf(p.get("ngid"));
			if(nodeGroupId!=null){
				processDefinitionService.clonProcessByNodeGroupId(nodeGroupId) ;
			}
		}
		
		page = processDefinitionService.listByPage(pageNo);			
		Collection c = page.getResult();
		Collection cNew = new ArrayList();
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			cNew.add(processDefinitionService.getPVO((ProcessDefinition) iterator.next()));
			
		}			
		page.setResult(cNew);
		setRequestAttribute("processCategoryLst", processCategoryService.getAll());
		setRequestAttribute("page",page);//
		return "list";
	}
	
	public String getDefaultRtype() {
		return defaultRtype;
	}

	 

	public void setDefaultRtype(String defaultRtype) {
		this.defaultRtype = defaultRtype;
	}

	public ProcessDefinitionService getProcessDefinitionService() {
		return processDefinitionService;
	}

	public void setProcessDefinitionService(ProcessDefinitionService processDefinitionService) {
		this.processDefinitionService = processDefinitionService;
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


	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
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

	public ProcessCategoryService getProcessCategoryService() {
		return processCategoryService;
	}

	public void setProcessCategoryService(
			ProcessCategoryService processCategoryService) {
		this.processCategoryService = processCategoryService;
	}

	public ProcessCategory getProcessCategory() {
		return processCategory;
	}

	public void setProcessCategory(ProcessCategory processCategory) {
		this.processCategory = processCategory;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}
	
	/************************************************
	 * USE: 查询时，根据查询条件得到查询的SQL语句，并把查询条件封装到字符串数组中去
	 * PARAM: ...
	 * RETURN: 封装好的HQL语句
	 * FOR: 页面上面的简便查询和高级查询中，查询时根据查询条件组装HQL语句，并准备与查询相关的数据
	 * 
	 * AUTHOR:　L.赵亚
	 * DATE: 2010.02.25.14.40
	 * 
	 */
	public String generateSearchHQL(){
		String sqlReturn = "from ProcessDefinition p where 1=1 ";
		if("yes".equalsIgnoreCase(simpleSearch)&&UtilAndTool_L.checkNotNullOrZero(searchObjName)){
			this.setRequestAttribute("searchObjName", searchObjName);
			if(UtilAndTool_L.checkNotNullOrZero(searchObjValue)){
				processSearchInfo = new String[1];
				processSearchInfo[0] = searchObjValue;
				this.setRequestAttribute("searchObjValue", searchObjValue);
				if(searchObjName.equals("processName")){
					sqlReturn += " and p.name like '%" + searchObjValue + "%' ";
//				}else if(searchObjName.equals("processCategorySearch")){
//					sqlReturn = "from ProcessDefinition p ";
//					sqlReturn += " left join ProcessCategory ac on p.categoryId=ac.id ";
//					sqlReturn += " where ac.name like '%" + searchObjValue + "%' ";
				}else if(searchObjName.equals("processCreator")){
					sqlReturn += " and p.creator like '%" + searchObjValue + "%' ";
				}else if(searchObjName.equals("processStatus")){
					if("未发布".equals(searchObjValue)){
						sqlReturn += " and p.releaseStatus=0 ";
					}else if("已发布".equals(searchObjValue)||"发布".equals(searchObjValue)){
						sqlReturn += " and p.releaseStatus=1 ";
					}else if("已作废".equals(searchObjValue)||"作废".equals(searchObjValue)){
						sqlReturn += " and p.releaseStatus=-1 ";
					}
				}
			}
			
			return sqlReturn;
		}
		if(UtilAndTool_L.checkNotNullOrZero(processName)){
			sqlReturn += " and p.name like '%" + processName + "%' ";
			this.setRequestAttribute("processName", processName);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processDefinitionVersion)){
			sqlReturn += " and p.defVersion='" + processDefinitionVersion + "' ";
			this.setRequestAttribute("processDefinitionVersion", processDefinitionVersion);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processDescription)){
			sqlReturn += " and p.description like '%" + processDescription + "%' ";
			this.setRequestAttribute("processDescription", processDescription);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processCategorySearch)){
			sqlReturn += " and p.categoryId=" + processCategorySearch + " ";
			this.setRequestAttribute("processCategorySearch", processCategorySearch);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processCreator)){
			sqlReturn += " and p.creator like '%" + processCreator + "%' ";
			this.setRequestAttribute("processCreator", processCreator);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processStatus)){
			if("未发布".equals(processStatus)){
				sqlReturn += " and p.releaseStatus=0 ";
			}else if("已发布".equals(processStatus)||"发布".equals(processStatus)){
				sqlReturn += " and p.releaseStatus=1 ";
			}else if("已作废".equals(processStatus)||"作废".equals(processStatus)){
				sqlReturn += " and p.releaseStatus=-1 ";
			}
			this.setRequestAttribute("processStatus", processStatus);
		}
		if(UtilAndTool_L.checkNotNullOrZero(processUpdator)){
			sqlReturn += " and p.updator like '%" + processUpdator + "%' ";
			this.setRequestAttribute("processUpdator", processUpdator);
		}
		//processSearchInfo = values.toArray();
		
		return sqlReturn;
	
//		String sqlReturn = "from ProcessDefinition p where 1=1 ";
//		if(UtilAndTool_L.checkNotNullOrZero(searchObjName)){
//			this.setRequestAttribute("searchObjName", searchObjName);
//			if(UtilAndTool_L.checkNotNullOrZero(searchObjValue)){
//				processSearchInfo = new String[1];
//				processSearchInfo[0] = searchObjValue;
//				this.setRequestAttribute("searchObjValue", searchObjValue);
//				if(searchObjName.equals("processName"))
//					sqlReturn += " and p.name like '%?%' ";
//				else if(searchObjName.equals("processCategoryName")){
//					sqlReturn = "select {p.*} from ProcessDefinition p ";
//					sqlReturn += " left outer join asf_category ac on p.category_id=ac.id ";
//					sqlReturn += " where ac.name like '%?%' ";
//				}else if(searchObjName.equals("processCreator"))
//					sqlReturn += " and p.processCreator like '%?%' ";
//				else if(searchObjName.equals("processStatus"))
//					sqlReturn += " and p.processStatus=? ";
//			}
//			
//			return sqlReturn;
//		}
//		List<String> values = new ArrayList<String>(); 
//		if(UtilAndTool_L.checkNotNullOrZero(processName)){
//			sqlReturn += " and name like '%?%' ";
//			values.add(processName);
//		}
//		if(UtilAndTool_L.checkNotNullOrZero(processCategorySearch)){
//			sqlReturn += " and category_id=? ";
//			values.add(processCategorySearch);
//		}
//		if(UtilAndTool_L.checkNotNullOrZero(processCreator)){
//			sqlReturn += " and creator like '%?%' ";
//			values.add(processCreator);
//		}
//		if(UtilAndTool_L.checkNotNullOrZero(processStatus)){
//			sqlReturn += " and release_status=? ";
//			values.add(processStatus);
//		}
//		if(UtilAndTool_L.checkNotNullOrZero(processUpdator)){
//			sqlReturn += " and updator like '%?%' ";
//			values.add(processUpdator);
//		}
//		processSearchInfo = values.toArray();
//		
//		return sqlReturn;
	}
}
