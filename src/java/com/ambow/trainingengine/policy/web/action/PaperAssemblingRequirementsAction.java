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
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;
import com.ambow.trainingengine.policy.service.PaperAssemblingRequirementsService;
import com.ambow.trainingengine.policy.util.CodesNamesUtil;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class PaperAssemblingRequirementsAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="paper_assembling_requirements";
	public static final String actionName="paperAssemblingRequirementsAction";
	public PaperAssemblingRequirementsService paperAssemblingRequirementsService;
	
	/**action type 请求的操作*/
	public String atype;
	
	//输出用
	public Integer id;
	public String ids;
	public int pageNo;	
	public Long nodeId;
	public PaperAssemblingRequirements paperAssemblingRequirements;//也可能用于显示 
	
	
	//以下用于显示数据
	public Map viewMap;
	public List all;	
	public Page page;
	public String nodeType="";
	//显示错误消息用
	public List errorList;
	
	/**action Return type*/
	public String rtype;
	
	private String[] reviewRound;
	/**
	 * 替除不允许修改的字段，防止用户非法不允许修改的字段
	 * @param test
	 * @return
	 */
	private PaperAssemblingRequirements removeTestCanotModifyAttr(PaperAssemblingRequirements paperAssemblingRequirements){
		//PaperAssemblingRequirements paperAssemblingRequirements =new PaperAssemblingRequirements();
		return paperAssemblingRequirements;
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
			viewMap = paperAssemblingRequirementsService.showViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("paperAssemblingRequirements",CodesNamesUtil.initPaperAssemblingRequirements((PaperAssemblingRequirements)viewMap.get("PaperAssemblingRequirements"),paperAssemblingRequirementsService));
			setRequestAttribute("knowledgePointList", paperAssemblingRequirementsService.getAll(KnowledgePoint.class));
			setRequestAttribute("itemTypeList", paperAssemblingRequirementsService.getAll(ItemType.class));
			
			setReturnType(viewMap);
			initQueryCondition();
		}else if("edit".equals(atype)||"iedit".equals(atype)){
			initQueryCondition();
			viewMap = paperAssemblingRequirementsService.editViewMap(id);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("from",atype);
			PaperAssemblingRequirements paperAssemblingRequirements = CodesNamesUtil.initPaperAssemblingRequirements((PaperAssemblingRequirements)viewMap.get("PaperAssemblingRequirements"),paperAssemblingRequirementsService) ;
			setRequestAttribute("paperAssemblingRequirements",paperAssemblingRequirements);	
			String reviewRound = paperAssemblingRequirements.getReviewRound();
			if(reviewRound != null){
				String[] str = reviewRound.split(",");
				setRequestAttribute("selectedReviewRound", str);
			}
			setRequestAttribute("knowledgePointList", paperAssemblingRequirementsService.getAll(KnowledgePoint.class));
			setRequestAttribute("itemTypeList", paperAssemblingRequirementsService.getAll(ItemType.class));
			setReturnType(viewMap);
		}else if("add".equals(atype)||"iadd".equals(atype)||"igroupAdd".equals(atype)){
			//removePaperAssemblingRequirementsCanotModifyAttr(PaperAssemblingRequirements paperAssemblingRequirements);
			errorList = paperAssemblingRequirementsService.add(paperAssemblingRequirements);
		}else if("update".equals(atype)||"iupdate".equals(atype)||"nodeupdate".equals(atype)||"nodeGroupupdate".equals(atype)){
			errorList = paperAssemblingRequirementsService.update(paperAssemblingRequirements);
		}else if("list".equals(atype)){
			page = paperAssemblingRequirementsService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = paperAssemblingRequirementsService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			errorList = paperAssemblingRequirementsService.delete(id);			
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			errorList = paperAssemblingRequirementsService.deleteBatch(ids);
		}else if("listByNodeId".equals(atype)){
			all = paperAssemblingRequirementsService.listByNodeId(nodeId);
			setRequestAttribute("all",all);//
		}else if( "idelete".equals(atype)){
			errorList = paperAssemblingRequirementsService.delete(id);			
		}else if("deleteAllByNodeId".equals(atype)||"deleteAllByNodeGroupId".equals(atype)){
			errorList=paperAssemblingRequirementsService.deleteAllByNodeId(id);
		}
		else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		if("GROUP".equals(nodeType)){
			setRequestAttribute("nodeType", "nodeGroup");
		}else{
			setRequestAttribute("nodeType", "node");
		}
		setRequestAttribute("errorList", errorList);
		setRequestAttribute("atype",atype);
		setRequestAttribute("rtype",rtype);
		return rtype;
	}
	
	private void initQueryCondition(){
		this.setRequestAttribute("regionList",paperAssemblingRequirementsService.getAll(Region.class)); //地区
		this.setRequestAttribute("subjectList",paperAssemblingRequirementsService.getAll(Subject.class)); //学科
		this.setRequestAttribute("itemTypeList",paperAssemblingRequirementsService.getAll(ItemType.class)); //试题类型
		this.setRequestAttribute("itemSourceList",  ItemSource.values()); //试题来源
		this.setRequestAttribute("gradeList",paperAssemblingRequirementsService.getAll(Grade.class, "parentLevel", true)); //适用学级		
		this.setRequestAttribute("knowledgePointList", paperAssemblingRequirementsService.getAll(KnowledgePoint.class, "parentLevel", true)); //知识点
		this.setRequestAttribute("itemThemeList", paperAssemblingRequirementsService.getAll(ItemTheme.class, "parentLevel", true)); //题材
	}
	
	public String getDefaultRtype() {
		return defaultRtype;
	}

	public void setDefaultRtype(String defaultRtype) {
		this.defaultRtype = defaultRtype;
	}

	public PaperAssemblingRequirementsService getPaperAssemblingRequirementsService() {
		return paperAssemblingRequirementsService;
	}

	public void setPaperAssemblingRequirementsService(PaperAssemblingRequirementsService paperAssemblingRequirementsService) {
		this.paperAssemblingRequirementsService = paperAssemblingRequirementsService;
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

	public PaperAssemblingRequirements getPaperAssemblingRequirements() {
		return paperAssemblingRequirements;
	}

	public void setPaperAssemblingRequirements(PaperAssemblingRequirements paperAssemblingRequirements) {
		this.paperAssemblingRequirements = paperAssemblingRequirements;
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
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public void setReviewRound(String[] reviewRound) {
		String str = "";
		for (int i = 0; i < reviewRound.length; i++) {
			str = str + reviewRound[i]+",";
		}
		str = str.substring(0,str.lastIndexOf(","));
		paperAssemblingRequirements.setReviewRound(str);
	}
}
