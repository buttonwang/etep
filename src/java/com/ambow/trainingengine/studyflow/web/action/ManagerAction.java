package com.ambow.trainingengine.studyflow.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.policy.service.ProcessPolicyService;
import com.ambow.trainingengine.studyflow.service.AdminService;
import com.ambow.trainingengine.studyflow.service.ProcessAdminService;
import com.ambow.trainingengine.studyflow.service.ProcessCategoryService;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;
import com.ambow.trainingengine.studyflow.web.data.MzTreeView;
import com.ambow.trainingengine.studyflow.web.data.NodeForMzTreeView;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
import com.ambow.trainingengine.studyguide.service.StudyGuideService;
import com.ambow.trainingengine.util.JSTreeAmbowUtil;
import com.ambow.trainingengine.web.action.WebBaseAction;
import com.ambow.trainingengine.web.util.RequestSessionApplicationUtil;
/**
	管理流程、节点、策略模板的显示与更新
	ProcessDefinition
	Node
	AssemblePaperPolicyTemplate AssemblingPaperReqTemplate ModuleEvalPolicyTemplate ModuleEvaluatingNodePolicy NodeGroupPolicyAssembling PaperAssemblingPolicy PaperAssemblingReqTemplate PaperAssemblingRequirements PhaseTestNodePolicy PhaseTestPolicyReq PhaseTestPolicyTemplate PolicyTemplate ProcessPolicy TrainingPolicy TrainingPolicyTemplate TrainingUnitNodePolicy TrainingUnitPolicyTemplate UnitTestNodePolicy UnitTestPolicyTemplate 
	
	showItem 调用url:  admin/studyflow/showItem.jhtml?processDefinition.id=1&itemType=ProcessDefinition
 * @author zhujianmin
 */
public class ManagerAction extends WebBaseAction  {
	public static final String NOTFOUND="notfound" ;
	public static final String IDNO="idno" ;
	public static final String TYPENO= "typeno" ;
	public Map hashShowNodeId = new HashMap();
	private static final long serialVersionUID = 2955776319264174738L;
	public ProcessCategoryService processCategoryService;
	private StudyGuideService studyGuideService;
	private ProcessPolicyService processPolicyService;
	
	/*用于显示，更新在类*/
	ProcessDefinition processDefinition;
	Node node;
	private int type;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	//get一类对象 
	String itemType;
	Object item;
	
	String optType;
	public long id;
	
	/*相关service*/
	ProcessAdminService processAdminService;
	AdminService adminService;
	ProcessPolicy processPolicy;
	ProcessCategory processCategory;
	String pcListJson="[]";

	/**
	 * 2009年12月30日 增加选择树形结构的学习指导
	 * WeiShaoying
	 * @return
	 */
	public String getStudyGuideTree() {
		String subjectCode = this.changeIdToName(type);
		List<StudyGuide> studyGuides = studyGuideService.getStudyGuideBySubjectAndGrade(subjectCode, null);
		this.setRequestAttribute("studyGuides", studyGuides);
		String[]codes = itemType.split(",");
		this.setRequestAttribute("codes", codes);
		return SUCCESS;
	}
	
	private String changeIdToName(int id) {
		String ret = "";
		switch(id) {
			case 6 :
				ret = "C";
				break;
			case 7 :
				ret = "P";
				break;
			case 8 :
				ret = "M";
				break;
			default :
				ret = "E";
				break;
		}
		return ret;
	}

	public String showItem(){
		RequestSessionApplicationUtil.init(adminService, this, false);		
		if(null!=itemType||!"".equals(itemType)){
			String temp = itemType.toLowerCase();
			
			if("ProcessDefinition".toLowerCase().equals(temp)){
				id = processDefinition.getId();
				item =  adminService.get(processDefinition,id);
				
				String hql="from ProcessPolicy where processId = "+id;
				processPolicy = (ProcessPolicy)adminService.findObjByHql(hql, null);
				
				String hqlProcessCategory="from ProcessCategory where id ="+((ProcessDefinition)item).getCategoryId();
				pcListJson=ProcessCategoryService.getProcessCategoryJson(adminService);
				processCategory = (ProcessCategory) adminService.findObjByHql(hqlProcessCategory );
			
				/**
				 * 2009年11月20日13:52:55 WeiShaoying增加
				 */
				
				setRequestAttribute("nodeJSTreeListJson",JSTreeAmbowUtil.getNodeJSTreeJSon(id,adminService));
				setRequestAttribute("processPolicy", processPolicy);
				setRequestAttribute("processCategoryLst", processCategoryService.getAll());
				//查询名字WEISY
				if (processPolicy!=null)
					this.setRequestAttribute("names", processPolicyService.getStudyGuideNames(processPolicy.getStudyGuideCodes()));
				
				/*===============计算流程所有 大题与小题 数量====================*/
				List<Long> lst = processCategoryService.find("select id from Node where processDefinition.id=?",id) ;
				String ids = null;
				Long bigItemsNum = null;
				Long itemsNum = null;
				Long totalAnsweringTime = null;
				Long totalAnsweringTimeHours = null;
				if(lst!=null&&lst.size()>0){
					ids =lst.toString().replaceAll("\\]|\\[","") ; 
					bigItemsNum = (Long)processCategoryService.findObjByHql("select sum(big_items_num) as b from PaperAssemblingPolicy where  nodeId in ("+ids+")");
					itemsNum = (Long)processCategoryService.findObjByHql("select sum(items_num) as b from PaperAssemblingPolicy where  nodeId in ("+ids+")");
					totalAnsweringTime= (Long)processCategoryService.findObjByHql("select sum(answeringTime) as b from PaperAssemblingPolicy where  nodeId in ("+ids+")");
				}
				if(bigItemsNum==null){
					bigItemsNum = 0L;
				}
				if(itemsNum==null){
					itemsNum=0L;
				}
				if(totalAnsweringTime==null){
					totalAnsweringTime = 0L;
				}else{
					totalAnsweringTimeHours = totalAnsweringTime/3600;
					totalAnsweringTime = totalAnsweringTime%3600/60;
					
				}
				setRequestAttribute("bigItemsNum",bigItemsNum);
				setRequestAttribute("itemsNum",itemsNum);
				setRequestAttribute("totalAnsweringTime",totalAnsweringTime);
				setRequestAttribute("totalAnsweringTimeHours",totalAnsweringTimeHours);
				/*===============计算流程所有 大题与小题 数量====================*/
				//新树setRequestAttribute("processNodesJsonStr",getProcessNodesJsonStr(adminService,id).toString()); 
			}else if("Node".toLowerCase().equals(temp)){
				id = node.getId();
				//System.out.println("start");
				item = (Node)adminService.get(node,id);
				//System.out.println("end");
			}
			if(id>0){
				if(item!=null){
					setRequestAttribute("p", item);
					this.setRequestAttribute("pc",this.processCategoryService.getProcessCategoryName((ProcessDefinition)item));
					return itemType;
				}else{
					return NOTFOUND;
				}
			}else{
				return IDNO;
			}
		}
		return TYPENO;
	}

	public StringBuffer getProcessNodesJsonStr ( AdminService adminService,Long processId){
		List<Node> nodes = adminService.find("from Node where processDefinition.id=?",processId);
		List nodesMzTreeViewLst = new ArrayList();
		
		Map map = new HashMap();
		
		//创建根节点
		Long rootId = -1L;
		Node _node = new Node();
			_node.setId(rootId);
			_node.setName("");
		NodeGroup _ng=new NodeGroup();
			_ng.setId(0);
		_node.setNodeGroup(_ng);
		NodeForMzTreeView nodeForMzTreeViewRoot = new NodeForMzTreeView(_node); 
		
		
		Map classProps = new HashMap();
		String outPrototype = "id,name,orderNum,updator,creator";
		classProps.put(Node.class.getName(), outPrototype);
		classProps.put(MzTreeView.class.getName(), "pid_id,obj");
		
		classProps.put(PhaseTestNode.class.getName(), outPrototype);
		classProps.put(PracticeNode.class.getName(), outPrototype);
		classProps.put(UnitTestNode.class.getName(), outPrototype);
		classProps.put(ExamNode.class.getName(), outPrototype);
		classProps.put(EvaluateNode.class.getName(), outPrototype);
		classProps.put(NodeGroup.class.getName(), outPrototype);
		classProps.put(ProcessDefinition.class.getName(), "id");
		JSONLeanW json = new JSONLeanW();
		json.setClassProps(classProps);
		
		
		nodesMzTreeViewLst.add(nodeForMzTreeViewRoot);
		for (Node node : nodes) {
			NodeForMzTreeView nodeForMzTreeView = new NodeForMzTreeView(node); 
			nodesMzTreeViewLst.add(nodeForMzTreeView);
		}
		return new StringBuffer().append(json.write(nodesMzTreeViewLst)); 
	}
	public String editProcessDefinition(){
		return INPUT;
	}
	
	public String deleteProcessDefinition(){
		
		return INPUT;
	}
	
	public String editNode(){
		return INPUT;
	}
	
	public String deleteNode(){
		return INPUT;
	} 
	
	public String addProcess(){
		if(processDefinition.getName()!=null){
			List errorList = processAdminService.addProcess(processDefinition,"processTestor");
			if(errorList.size()==0){
				return SUCCESS;
			}
		}else{
		}
		return INPUT;
	}
	
	public String editProcess(){
		if(optType==null||"view".equals(optType)){
			processDefinition = processAdminService.get(processDefinition.getId());
			return "editProcess";
		}
		
		if(processDefinition.getId()>0){			
			List errorList = processAdminService.addProcess(processDefinition,"processTestor");
			if(errorList.size()==0){
				return SUCCESS;
			}
		}else{
			
		}
		return INPUT;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	public ProcessAdminService getProcessAdminService() {
		return processAdminService;
	}
	public void setProcessDefinition(ProcessDefinition process) {
		this.processDefinition = process;
	}
	public AdminService getAdminService() {
		return adminService;
	}

	public String getItemType() {
		return itemType;
	}

	public Object getItem() {
		return item;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public void setItem(Object item) {
		this.item = item;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public void setProcessAdminService(ProcessAdminService processService) {
		this.processAdminService = processService;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ProcessPolicy getProcessPolicy() {
		return processPolicy;
	}

	public void setProcessPolicy(ProcessPolicy processPolicy) {
		this.processPolicy = processPolicy;
	}

	public Map getHashShowNodeId() {
		return hashShowNodeId;
	}

	public void setHashShowNodeId(Map hashShowNodeId) {
		this.hashShowNodeId = hashShowNodeId;
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

	public String getPcListJson() {
		return pcListJson;
	}

	public void setPcListJson(String pcListJson) {
		this.pcListJson = pcListJson;
	}

	public StudyGuideService getStudyGuideService() {
		return studyGuideService;
	}

	public void setStudyGuideService(StudyGuideService studyGuideService) {
		this.studyGuideService = studyGuideService;
	}

	public ProcessPolicyService getProcessPolicyService() {
		return processPolicyService;
	}

	public void setProcessPolicyService(ProcessPolicyService processPolicyService) {
		this.processPolicyService = processPolicyService;
	}
 

}