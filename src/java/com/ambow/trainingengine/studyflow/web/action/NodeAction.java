package com.ambow.trainingengine.studyflow.web.action;

import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOID;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOOPTTYPE;
import static com.ambow.trainingengine.studyflow.web.util.CommonActionReturnType.NOTFOUND;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.util.ItemSource;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;
import com.ambow.trainingengine.studyflow.service.NodeService;
import com.ambow.trainingengine.studyflow.service.ProcessDefinitionService;
import com.ambow.trainingengine.studyflow.web.data.NodeJson;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.RequestAttributeByMap;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.action.WebBaseAction;
public class NodeAction  extends WebBaseAction {
	private static final long serialVersionUID = 1686477134758816461L;
	public String defaultRtype="show";
	public static final String dojo="node";
	public NodeService nodeService;
	public ProcessDefinitionService processDefinitionService;
	String to;
	
	private ProcessPolicyService processPolicyService;
	
	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}

	/**action type 请求的操作类型*/
	public String atype;
	
	/**action Return type*/
	public String rtype;
	
	/**页面参数项*/
	public ParamObject p;
	
	/**service返回的数据*/
	public Map viewMap;
	
	//显示错误消息用
	public List errorList;
	
	/**其它从页面获得的对象(省的自己转换)*/
	//public List nodeLst;
	
	/**
	 * 替除不允许修改的字段，防止用户非法不允许修改的字段
	 * @param test
	 * @return
	 */
	private Node removeTestCanotModifyAttr(Node node){
		//Node node =new Node();
		return node;
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
			viewMap = nodeService.showViewMap(id);
			setRequestAttribute("node", viewMap.get("node"));
			setReturnType(viewMap);
		}else if("edit".equals(atype)){
			viewMap = nodeService.editViewMap(id);
			setRequestAttribute("node", viewMap.get("node"));
			setRequestAttribute("parentList", viewMap.get("parentList"));
			setRequestAttribute("orderNumForShow",viewMap.get("orderNumForShow"));
			setReturnType(viewMap);
		}else if("add".equals(atype)){
			//removeNodeCanotModifyAttr(Node node);
			viewMap = nodeService.add(p,(SysUser) getSessionObj(SessionDict.AdminUser));			
		}else if("update".equals(atype)||"iupdate".equals(atype)||"iupdateNodeGroup".equals(atype)||"showProcessupdate".equals(atype)){
			viewMap=nodeService.update(p,(SysUser) getSessionObj(SessionDict.AdminUser));
		}else if("list".equals(atype)){
			page = nodeService.listByPage(pageNo);
			setRequestAttribute("page",page);//
		}else if("listAll".equals(atype)){
			all = nodeService.getAll();
			setRequestAttribute("all", all);//
		}else if("delete".equals(atype)){
			viewMap = nodeService.deleteBatch(" "+id,null);
			errorList =(List) viewMap.get("errorList");
		}else if("deleteBatch".equals(atype)){
			setRequestAttribute("ids",ids);
			viewMap = nodeService.deleteBatch(ids,null);
			errorList =(List) viewMap.get("errorList");
		}else if("sadd".equals(atype)){
			//用于取得所有流程的节点
			viewMap = nodeService.saddWithP(p);
			setRequestAttribute("v", viewMap);
			setRequestAttribute("processId", viewMap.get("processId"));
		}else if("param".equals(atype)){
			//System.out.println("success");
		}else if("showAll".equals(atype)){
			viewMap = nodeService.showAll(id);
			//2009年12月30日WEISY
			TrainingPolicy tp = nodeService.get(TrainingPolicy.class, id);
			if (tp!=null) {
				this.setRequestAttribute("names", processPolicyService.getStudyGuideNames(tp.getStudyGuideCodes()));//查询该训练策略拥有的学习指导
				this.setRequestAttribute("categoryId", tp.getAsfNode().getProcessDefinition().getCategoryId());
			}
			errorList = (List)viewMap.get("errorList");
			RequestAttributeByMap.setAttributeByMap(getHttpServletRequest(),viewMap);
			setRequestAttribute("itemTypeList", nodeService.getAll(ItemType.class));
		}else if("showGroupAll".equals(atype)){
			viewMap = nodeService.showGroupAll(id);
			errorList = (List)viewMap.get("errorList");
			RequestAttributeByMap.setAttributeByMap(getHttpServletRequest(),viewMap);
			setRequestAttribute("itemTypeList", nodeService.getAll(ItemType.class));
		}else if("nodeGroupAddTemplate".equals(atype)){
			id=p.getLong("id");
			errorList =(List) (nodeService.nodeGroupAddTemplate(p).get("errorList"));
			 
		}else if("nodeGroupDeleteTemplate".equals(atype)){
			id=p.getLong("id");
			errorList =(List) (nodeService.nodeGroupDeleteTemplate(p).get("errorList"));
			 
		}else{
			rtype=NOOPTTYPE;
		}
		if(errorList!=null&&errorList.size()>0){
			rtype=rtype+ERROR;
		}
		

		initQueryCondition();
		setRequestAttribute("to", to);
		setRequestAttribute("v", viewMap);
		setRequestAttribute("errorList", errorList);
		setRequestAttribute("atype",atype);
		setRequestAttribute("rtype",rtype);
		if(prid!=null){
			setRequestAttribute("processNodesJson",new NodeJson().getNodeJson(nodeService,prid));
		}
		return rtype;
	}
	private void initQueryCondition(){
		this.setRequestAttribute("regionList",nodeService.getAll(Region.class)); //地区
		this.setRequestAttribute("subjectList",nodeService.getAll(Subject.class)); //学科
		this.setRequestAttribute("itemTypeList",nodeService.getAll(ItemType.class)); //试题类型
		this.setRequestAttribute("itemSourceList",  ItemSource.values()); //试题来源
		this.setRequestAttribute("gradeList",nodeService.getAll(Grade.class, "parentLevel", true)); //适用学级		
		this.setRequestAttribute("knowledgePointList", nodeService.getAll(KnowledgePoint.class, "parentLevel", true)); //知识点
		this.setRequestAttribute("itemThemeList", nodeService.getAll(ItemTheme.class, "parentLevel", true)); //题材
	}
	public String getDefaultRtype() {
		return defaultRtype;
	}

	public void setDefaultRtype(String defaultRtype) {
		this.defaultRtype = defaultRtype;
	}

	public NodeService getNodeService() {
		return nodeService;
	}

	public void setNodeService(NodeService nodeService) {
		this.nodeService = nodeService;
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

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPrid() {
		return prid;
	}

	public void setPrid(Long prid) {
		this.prid = prid;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}
	
	/**action Return file转向的文件*/
	private String actionFile;
	
	//输出用
	public Long id;
	public String ids;
	public int pageNo;	
	public Node node;//也可能用于显示 
	public String nodeType;
	public Long parentId;
	public Long processId;
	
	public Long pid;
	public Long prid;
	
	//以下用于显示数据
	public List all;	
	public Page page;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	 
}
