package com.ambow.trainingengine.policy.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.util.ItemSource;
import com.ambow.trainingengine.policy.domain.PaperAssemblingReqTemplate;
import com.ambow.trainingengine.policy.service.PaperAssemblingReqTemplateService;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PaperAssemblingReqTemplateAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="paper_assembling_req_template";
	public static final String actionName="paperAssemblingReqTemplate";
	public PaperAssemblingReqTemplateService paperAssemblingReqTemplateService;
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Integer id;
	public String ids;
	public int pageNo;	
	public PaperAssemblingReqTemplate paperAssemblingReqTemplate;//也可能用于显示 
	
	
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
	private PaperAssemblingReqTemplate removeTestCanotModifyAttr(PaperAssemblingReqTemplate paperAssemblingReqTemplate){
		//PaperAssemblingReqTemplate paperAssemblingReqTemplate =new PaperAssemblingReqTemplate();
		return paperAssemblingReqTemplate;
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
			initQueryCondition();
			viewMap = paperAssemblingReqTemplateService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("paperAssemblingReqTemplate", viewMap.get("PaperAssemblingReqTemplate"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			initQueryCondition();
			viewMap = paperAssemblingReqTemplateService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("paperAssemblingReqTemplate", viewMap.get("PaperAssemblingReqTemplate"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removePaperAssemblingReqTemplateCanotModifyAttr(PaperAssemblingReqTemplate paperAssemblingReqTemplate);
			errorList = paperAssemblingReqTemplateService.add(paperAssemblingReqTemplate);
		}else if("update".equals(atype)||"iupdate".equals(atype)){
			paperAssemblingReqTemplateService.update(paperAssemblingReqTemplate);
		}else if("list".equals(atype)){
			page = paperAssemblingReqTemplateService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = paperAssemblingReqTemplateService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			errorList = paperAssemblingReqTemplateService.delete(id);
		}else if("deleteBatch".equals(atype)||"ideleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = paperAssemblingReqTemplateService.deleteBatch(ids);
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

	private void initQueryCondition(){
		this.setRequestAttribute("regionList", this.paperAssemblingReqTemplateService.getAll(Region.class)); //地区
		this.setRequestAttribute("subjectList", this.paperAssemblingReqTemplateService.getAll(Subject.class)); //学科
		this.setRequestAttribute("itemTypeList", this.paperAssemblingReqTemplateService.getAll(ItemType.class)); //试题类型
		this.setRequestAttribute("itemSourceList",  ItemSource.values()); //试题来源
		this.setRequestAttribute("gradeList", this.paperAssemblingReqTemplateService.getAll(Grade.class, "parentLevel", true)); //适用学级		
		this.setRequestAttribute("knowledgePointList", this.paperAssemblingReqTemplateService.getAll(KnowledgePoint.class, "parentLevel", true)); //知识点
		this.setRequestAttribute("itemThemeList", this.paperAssemblingReqTemplateService.getAll(ItemTheme.class, "parentLevel", true)); //题材
	}
	
	public String getDefaultRtype() {
		return defaultRtype;
	}

	public void setDefaultRtype(String defaultRtype) {
		this.defaultRtype = defaultRtype;
	}

	public PaperAssemblingReqTemplateService getPaperAssemblingReqTemplateService() {
		return paperAssemblingReqTemplateService;
	}

	public void setPaperAssemblingReqTemplateService(PaperAssemblingReqTemplateService paperAssemblingReqTemplateService) {
		this.paperAssemblingReqTemplateService = paperAssemblingReqTemplateService;
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

	public PaperAssemblingReqTemplate getPaperAssemblingReqTemplate() {
		return paperAssemblingReqTemplate;
	}

	public void setPaperAssemblingReqTemplate(PaperAssemblingReqTemplate paperAssemblingReqTemplate) {
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
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
