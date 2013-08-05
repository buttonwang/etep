package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.policy.domain.AssemblePaperPolicyTemplate;
import com.ambow.trainingengine.policy.domain.ModuleEvalPolicyTemplate;
import com.ambow.trainingengine.policy.domain.NodeGroupPolicyAssembling;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingReqTemplate;
import com.ambow.trainingengine.policy.domain.PhaseTestPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;
import com.ambow.trainingengine.policy.domain.TrainingUnitPolicyTemplate;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.policy.domain.UnitTestPolicyTemplate;
import com.ambow.trainingengine.studyflow.web.data.NodeJson;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.ParamObject;

public class NodeService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(NodeService.class);
	ProcessDefinitionService processDefinitionService;
	
	/**
	 * 获取节点列表
	 * @return
	 */
	public List<Node> getNodeList(Long processId){
		String hql = "from Node n where n.processDefinition.id=? order by order_num ";
		List<Node> nodeList = this.find(hql, processId);
		return nodeList;
	}
	
	public Map nodeGroupDeleteTemplate(ParamObject p){
		Map map = new HashMap();
		Long nodeGroupId = p.getLong("nodeGroupId");
		String templateType = p.get("tType");
		NodeGroup nodeGroup = get(NodeGroup.class,nodeGroupId);
		if(nodeGroup!=null){
			NodeGroupPolicyAssembling ngpa = get(NodeGroupPolicyAssembling.class,nodeGroupId);
			if(ngpa!=null){
				if("all".equals(templateType)){
					remove(ngpa);
				}else{
					if("ModuleEvalPolicyTemplate".equals(templateType)){
						ngpa.setModuleEvalPolicyTemplate(null);
					}else if("PhaseTestPolicyTemplate".equals(templateType)){
						ngpa.setPhaseTestPolicyTemplate(null);
					}else if("TrainingUnitPolicyTemplate".equals(templateType)){
						ngpa.setTrainingUnitPolicyTemplate(null);
					}else if("UnitTestPolicyTemplate".equals(templateType)){
						ngpa.setUnitTestPolicyTemplate(null);
					}else if("TrainingPolicyTemplate".equals(templateType)){
						ngpa.setTrainingPolicyTemplate(null);
					}else if("PaperAssemblingReqTemplate".equals(templateType)){
						ngpa.setPaperAssemblingReqTemplate(null);
					}else if("AssemblePaperPolicyTemplate".equals(templateType)){
						ngpa.setAssemblePaperPolicyTemplate(null);
					}
					save(ngpa);
				}
			}
		}
		return map;
	}
	public Map nodeGroupAddTemplate(ParamObject p){
		Map map = new HashMap();
		if(p!=null){		
			Long nodeGroupId = p.getLong("nodeGroupId");
			Integer templateId = p.getInteger("tid");
			String templateType = p.get("tType");
			if(templateType!=null){
				Node node = get(NodeGroup.class,nodeGroupId);
				if(node!=null&&templateId!=null){
					NodeGroupPolicyAssembling ngpa = get(NodeGroupPolicyAssembling.class,nodeGroupId);
					if(ngpa==null){
						ngpa = new NodeGroupPolicyAssembling();
						ngpa.setAsfNode(node);
					}
					if("ModuleEvalPolicyTemplate".equals(templateType)){
						ngpa.setModuleEvalPolicyTemplate(get(ModuleEvalPolicyTemplate.class,templateId));
					}else if("PhaseTestPolicyTemplate".equals(templateType)){
						ngpa.setPhaseTestPolicyTemplate( get(PhaseTestPolicyTemplate.class,templateId));
					}else if("TrainingUnitPolicyTemplate".equals(templateType)){
						ngpa.setTrainingUnitPolicyTemplate(get(TrainingUnitPolicyTemplate.class,templateId));
					}else if("UnitTestPolicyTemplate".equals(templateType)){
						ngpa.setUnitTestPolicyTemplate(get(UnitTestPolicyTemplate.class,templateId));
					}else if("TrainingPolicyTemplate".equals(templateType)){
						ngpa.setTrainingPolicyTemplate(get(TrainingPolicyTemplate.class,templateId));
					}else if("PaperAssemblingReqTemplate".equals(templateType)){
						ngpa.setPaperAssemblingReqTemplate(get(PaperAssemblingReqTemplate.class,templateId));
					}else if("AssemblePaperPolicyTemplate".equals(templateType)){
						ngpa.setAssemblePaperPolicyTemplate(get(AssemblePaperPolicyTemplate.class,templateId));
					}
					save(ngpa);
				}
			}
		}
		return map;
	}
	public Map add(ParamObject p,SysUser sysUser){
		Map map = new HashMap();
		List errorLst = new ArrayList();
		errorLst.addAll(checkPara("prid,name,orderNum","long,string,long",p,"nodeAdd"));
		if(errorLst.size()!=0){
			map.put("checkErrorLst", errorLst);
		}else{
			Long processId = p.getLong("prid");
			if(processId==null){
				errorLst.add("没有选定流程！请先指定一个流程");
			}else{
				ProcessDefinition process = get(ProcessDefinition.class,processId);
				if(process==null){
					errorLst.add("指定的流程不存在或已经被删除！请从新指定一个流程");
				}else{					
					Node node = null;
					//根据节点类型创建相应节点 有私有属性则一并设置
					String nodeType = initNodeType(p.get("nodeType"));
					if("UNITTEST".equals(nodeType)){
						node = new UnitTestNode();
					}else if("PRACTICE".equals(nodeType)){
						node = new PracticeNode();
					}else if("EVALUATE".equals(nodeType)){
						node=new EvaluateNode();
					}else if("PHASETEST".equals(nodeType)){
						node=new PhaseTestNode();
					}else if("GROUP".equals(nodeType)){
						node=new NodeGroup();
					}
					if(node==null){
						errorLst.add("请节点类型节点类型是否填写正确");//没有指定节点
					}else{
						//公有属性设置
						node.setCreateTime(new Date());
						if(sysUser!=null){
							node.setCreator(sysUser.getUsername());
						}
						
						node.setName(p.get("name"));
						node.setDescription(p.get("description"));
					
						//如果有父亲？
						Long parentNodeId = p.getLong("pid");
						if(parentNodeId !=null){
							//父亲是节点组？
							NodeGroup nodeGroup = get(NodeGroup.class,parentNodeId);
							Node parentNode = get(Node.class,parentNodeId);
							if(nodeGroup==null){
								//父亲节点存在？存在更新父亲为节点组
								if(parentNode!=null){
									NodeGroup temp_ng =new  NodeGroup();
									temp_ng.setId(parentNode.getId());
									node.setNodeGroup(temp_ng);
									updateTable( "asf_node", "node_type","GROUP", "id", parentNode.getId());
									//TODO 是否更新相关策略？
								}else{
									errorLst.add("父亲节点不存在 ");
								}
							}else{
								node.setNodeGroup(nodeGroup);
							}
						}
						//计算并设置node的orderNum属性
						node.setProcessDefinition(process);
						initOrderNum(node,p.get("orderNum"),"add");
						try{
							save(node);
							if(process.getStartNode()==null&&node.getNodeType()!=NodeType.GROUP){
								process.setStartNode(node);
								save(process);
							}
							String processIds= find("select distinct(processDefinition.id) from Node where id in ("+node.getId()+")").toString().replace("[", "").replace("]", "").trim();
							reInitStartNode(processIds,"");
							map.put("addedNode", node);
						}catch(Exception e){
							e.printStackTrace();
							//System.out.println("");
						}
					}
				}
			}
		}
		map.put("errorLst", errorLst);
		return map;
	}
	 
	/**
	 * 设置节点的 orderNum
	 * 如果节点是节点组 则更新所有子孙的orderNum;
	 * @param nodeOrNodeGroup
	 * @param orderNum
	 */
	public void initOrderNum(Node node,String orderNum,String type){
		if(orderNum!=null&&node!=null){
			NodeGroup ng =null;
			try {
				ng=get(NodeGroup.class,node.getNodeGroup().getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(ng!=null){
				node.setOrderNum(ng.getOrderNum()+","+orderNum);
			}else{
				node.setOrderNum(orderNum);
			};
			save(node);
			if(NodeType.GROUP.equals(node.getNodeType())){
				List<Node> nodes = ((NodeGroup)node).getNodes();
				if(nodes!=null){
					for (Node n : nodes) {
						try {
							String s = n.getOrderNum()==null?"01":n.getOrderNum();
							initOrderNum(n,s.substring(s.lastIndexOf(",")+1, s.length()),type);
						} catch (Exception e) {
						}
					}
				}
			}
		}
	}
	
	public String orderNumForShow(Object obj){
		if(obj!=null){
			Node node = null;
			if(obj instanceof Long){
				node = get(Node.class,(Long)obj);
			}else if(obj instanceof Node){
				node = (Node)obj;
			}
			if(node!=null&&node.getOrderNum()!=null){
				String [] itemOrderNums = node.getOrderNum().split(",");
				if(itemOrderNums.length>0){
					return itemOrderNums[itemOrderNums.length-1];
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param id
	 * @return 与这个对象相关的所有相关信息
	 */
	public Map showAll(Long id ){
		Map map = new HashMap();
		List errorList = new ArrayList(0);
		if(id!=null&&id!=0){
			Node node = get(Node.class,id);
			if(node==null){
				errorList.add("预查看的节点不存在或已经被删除");
			}else{
				KnowledgePoint kp = new KnowledgePoint();
				if(node.getNodeGroup()!=null){
					 kp = getKnowledgePoint(node.getNodeGroup().getName(),this);
				}
				map.put("_kp", kp );
				map.put("node", node);
				map.put("orderNumForShow",orderNumForShow(node));
				map.put("nodeType", node.getNodeType().toString());
				List jumpToLst = find("from Node n where n.processDefinition.id=? order by orderNum",node.getProcessDefinition().getId());
				map.put("jumpToLst", jumpToLst);
				
				map.putAll(editViewMap(id));
				 
				
				Long nodeId = Long.valueOf(id);
				try{
					NodeType nodeType = node.getNodeType();
					//训练策略
					map.put("trainingPolicy",  get(TrainingPolicy.class,nodeId));
					//组卷策略					
					PaperAssemblingPolicy paperAssemblingPolicy = get(PaperAssemblingPolicy.class,nodeId);
					String paperIds = "";
					String paperNames = "";
					if(paperAssemblingPolicy!=null){
						if(paperAssemblingPolicy.getPaper()!=null){
							paperNames =paperAssemblingPolicy.getPaper().getName() ;
							paperIds = paperAssemblingPolicy.getPaper().getId().toString();
						}
						if(paperAssemblingPolicy.getPaperList()!=null&& !"".equals(paperAssemblingPolicy.getPaperList().trim() )) {
							String paperIdsStr  = paperAssemblingPolicy.getPaperList(); 
							paperNames = find("select name from Paper where id in("+paperIdsStr+")",null).toString().replace("[","").replace("]","").replace(", ", ",");
							paperIds = find("select id from Paper where id in("+paperIdsStr+")",null).toString().replace("[","").replace("]","").replace(", ", ",");
							
						}
					}
					map.put("paperIds",paperIds);
					map.put("paperNames",paperNames);
					map.put("paperAssemblingPolicy", paperAssemblingPolicy);
					
					//if(NodeType.UNITTEST.equals(nodeType)){
						//单元测试策略
						map.put("unitTestNodePolicy", get(UnitTestNodePolicy.class,id));
					//}else if(NodeType.PRACTICE.equals(nodeType)){
						//训练单元策略
						map.put("trainingUnitNodePolicy", get(TrainingUnitNodePolicy.class,id));
					//}else if(NodeType.EVALUATE.equals(nodeType)){
						//模块测试策略
						//map.put("moduleEvaluatingNodePolicy", get(ModuleEvaluatingNodePolicy.class,id));
					//}else if(NodeType.PHASETEST.equals(nodeType)){
						//阶段测试策略(多值)
						map.put("phaseTestNodePolicyLst",find("from PhaseTestNodePolicy where asfNode.id=?",node.getId()));					 
					//} if(NodeType.GROUP.equals(nodeType)){
						//节点组策略
						map.put("nodeGroupPolicy",findObjByHql("from NodeGroupPolicy where asfNode.id=?",node.getId()));
					//}
						
					//组卷条件
					map.put("paperAssemblingRequirementsLst", find("from PaperAssemblingRequirements where asfNode.id=?",nodeId));
				}catch(Exception e){
					e.printStackTrace(); 
					
				}
			}
		}else{
			errorList.add("请先选择一个节点");
		}
		map.put("errorList", errorList);
		return map;
	}
	public KnowledgePoint getKnowledgePoint(String kpName,HibernateGenericDao dao){
		KnowledgePoint kp = new KnowledgePoint();
		if(kpName!=null&&!"".equals(kpName)){
			List<KnowledgePoint> kpLst = (List<KnowledgePoint>) dao.find("from KnowledgePoint where name=?",kpName);
			if (kpLst != null && kpLst.size() == 1 && kpLst.get(0) != null) {
				kp = kpLst.get(0);
			}
		}
		return kp;
		
	}
	public Map showGroupAll(Long id){
		Map map = new HashMap(0);
		if(id!=null){
			Node node = get(NodeGroup.class,id);
			
			if(node!=null){
				map.put("node",node);
				map.put("nodeGroupPolicy",findObjByHql("from NodeGroupPolicy where asfNode.id=?",node.getId()));
				map.put("orderNumForShow",orderNumForShow(node));
				NodeGroupPolicyAssembling ngpa = get(NodeGroupPolicyAssembling.class,node.getId());
				map.put("_kp", getKnowledgePoint(node.getName(),this));
				if(ngpa!=null){
					map.put("unitTestPolicyTemplate", ngpa.getUnitTestPolicyTemplate() );
					map.put("moduleEvalPolicyTemplate", ngpa.getModuleEvalPolicyTemplate() );
					map.put("trainingPolicyTemplate", ngpa.getTrainingPolicyTemplate() );
					map.put("trainingUnitPolicyTemplate", ngpa.getTrainingUnitPolicyTemplate() );
					map.put("assemblePaperPolicyTemplate", ngpa.getAssemblePaperPolicyTemplate() );
					map.put("phaseTestPolicyTemplate", ngpa.getPhaseTestPolicyTemplate() );
					map.put("paperAssemblingReqTemplate", ngpa.getPaperAssemblingReqTemplate() );
					
					//map.put("phaseTestPolicyReqsListModuleEval", ngpa.getModuleEvalPolicyTemplate().getPhaseTestPolicyReqs());
					if(ngpa.getPhaseTestPolicyTemplate()!=null){					
						map.put("phaseTestPolicyReqsList", ngpa.getPhaseTestPolicyTemplate().getPhaseTestPolicyReqs());
					}
					if(ngpa.getPaperAssemblingReqTemplate()!=null){
						map.put("assemblingPaperReqTemplatesList", ngpa.getPaperAssemblingReqTemplate().getAssemblingPaperReqTemplates() );
					}
				}
				map.put("unitTestPolicyTemplateList",getAll(UnitTestPolicyTemplate.class) );
				map.put("moduleEvalPolicyTemplateList",getAll(ModuleEvalPolicyTemplate.class) );
				map.put("trainingPolicyTemplateList",getAll(TrainingPolicyTemplate.class) );
				map.put("trainingUnitPolicyTemplateList",getAll(TrainingUnitPolicyTemplate.class) );
				map.put("assemblePaperPolicyTemplateList",getAll(AssemblePaperPolicyTemplate.class) );
				map.put("phaseTestPolicyTemplateList",getAll(PhaseTestPolicyTemplate.class) );
				map.put("paperAssemblingReqTemplateList",getAll(PaperAssemblingReqTemplate.class) );
				//组卷条件
				map.put("paperAssemblingRequirementsLst", find("from PaperAssemblingRequirements where asfNode.id=?",id));
			
			}
		}
		return map;
	}
	public ProcessDefinition getProcessDefinition(ParamObject p,String idKey ){
		String keyName=(idKey==null||"".equals(idKey.trim()))?"prid":idKey;	 
		try{
			ProcessDefinition processDefinition = get(ProcessDefinition.class,p.getLong(keyName));
			return processDefinition;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public Node getNode(ParamObject p,String idKey ){
		String keyName=(idKey==null||"".equals(idKey.trim()))?"nid":idKey;	 
		try{
			Node node = get(Node.class,p.getLong(keyName));
			return node;
		}catch(Exception e){
		}
		return null;
	}
	
	public Map update(ParamObject p,SysUser sysUser){
		Map map = new HashMap();
		List errorLst = new ArrayList();
		errorLst.addAll(checkPara("prid,name,orderNum","long,string,long",p,"nodeAdd"));
		if(errorLst.size()!=0){
			map.put("checkErrorLst", errorLst);
		}else{
			ProcessDefinition process = getProcessDefinition(p,null);
			if(process==null){
				errorLst.add("没有指定流程");
			}else{
				Node node = getNode(p,null);
				if(node==null){
					errorLst.add("要更新的节点不存在");
				}else{
					//更新允许的修改项
					node.setUpdateTime(new Date());
					String updator = null;
					if(sysUser!=null){
						updator = sysUser.getUsername();
					}
					node.setUpdator(updator);
					node.setName(p.get("name"));
					node.setDescription(p.get("description"));
					
					Node parentNode = getNode(p,"pid");
					if(parentNode!=null){
						if(! "GROUP".equals(parentNode.getNodeType())){
							parentNode.setUpdator(updator);
							save(parentNode);
							updateTable( "asf_node", "node_type","GROUP", "id", parentNode.getId());
							NodeGroup parentNodeGroup = new NodeGroup();
							parentNodeGroup.setId(parentNode.getId());
							node.setNodeGroup(parentNodeGroup);
						}else{
							node.setNodeGroup((NodeGroup) parentNode);
						}
					}else{
						node.setNodeGroup(null);
					}
					node.setProcessDefinition(process);
					initOrderNum( node, p.get("orderNum"), "update");
					save(node);
					updateTable( "asf_node", "node_type",initNodeType(p.get("nodeType")), "id", node.getId());
					String processIds= find("select distinct(processDefinition.id) from Node where id in ("+node.getId()+")").toString().replace("[", "").replace("]", "").trim();
					reInitStartNode(processIds,"");
					map.put("updatedNode", node);
				}
			}
			
		}
		map.put("errorLst", errorLst);
		return map;
	} 
	
	public List checkPara(String  checksn,String checkst,ParamObject p,String type){
		String [] checkNames = checksn.split(",");
		String [] checkTypes = checksn.split(",");
		List checkErrorLst = new ArrayList(0);
		if("nodeAdd".equals(type)){
			for (int i = 0; i < checkTypes.length; i++) {
				String dataType=checkTypes[i];
				String keyName=checkNames[i];
				if("long".equals(dataType)){
					Long t = p.getLong(keyName);
					if(t==null||t==0){
						checkErrorLst.add(itemMeans( keyName)+" 没有指定!");
					}
				}else if("string".equals(dataType)){
					String str = p.get(keyName);
					if(str==null||"".equals(str.trim())){
						checkErrorLst.add(itemMeans(keyName)+"必须不能为空！");
					}
				}
			}
		}
		return checkErrorLst;
	}
	public String itemMeans(String name){
		if("prid".equals(name)){
			return "流程id";
		}else if("pid".equals(name)){
			return "父亲id";
		}else if("nid".equals(name)||"nodeId".equals(name)){
			return "节点id";
		}
		return "";
	}
	
	public static Map processSeted= new HashMap();
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from Node",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 
	 * 删除节点的同时删除所有相关策略	  
	 * @param ids 要删除的节点
	 * @return
	 */
	public Map deleteBatch(Object nodeOrNodeGroupOrIds,String type){
		Map map = new HashMap(0);
		List errorList = new ArrayList(0);
		String nodeIds ="";
		Set allNodeSet = new HashSet();
		//通过类型找到所有要删除的节点id 并组装成字符串
		if(nodeOrNodeGroupOrIds!=null){
			if(nodeOrNodeGroupOrIds instanceof Node){
				//如是节点
				allNodeSet.add(nodeOrNodeGroupOrIds);
			}else if(nodeOrNodeGroupOrIds instanceof NodeGroup){
				//如是节点组 ，找出所有该节点的子节点及子节点组
				findAllNodeByGroupId((NodeGroup)nodeOrNodeGroupOrIds,allNodeSet);
				nodeIds = makeIdsBySet(allNodeSet);
			}else if(nodeOrNodeGroupOrIds instanceof String){
			 
				List nodeGroupList = find("from NodeGroup where id in("+(String)nodeOrNodeGroupOrIds+")");
				for (Iterator iterator = nodeGroupList.iterator(); iterator
						.hasNext();) {
					NodeGroup ng = (NodeGroup) iterator.next();
					  findAllNodeByGroupId(ng,allNodeSet)  ;
				}
				List nodeList = find("from Node where id in("+(String)nodeOrNodeGroupOrIds+")");
				allNodeSet.addAll(nodeList);
			}
		}

		try{
			//将所有要删除的节点id的变成 字符串 
			nodeIds=makeIdsBySet(allNodeSet);
			if(!nodeIds.trim().equals("")){
				if(theProcessWithNodeidIsInstance(nodeIds)){
					errorList.add("流程已经被初始化过，不能删除节点");
				}else{
					excuteHql("delete from DecisionRecord where node.id in("+nodeIds+")");
					excuteHql("delete from PhaseTestNodePolicy where asfNode.id in("+nodeIds+")");
					excuteHql("delete from PaperAssemblingRequirements where asfNode.id in("+nodeIds+")");
					
					excuteHql("delete from ModuleEvaluatingNodePolicy where nodeId in("+nodeIds+")");
					excuteHql("delete from UnitTestNodePolicy where nodeId in("+nodeIds+")");
					excuteHql("delete from TrainingUnitNodePolicy where nodeId in("+nodeIds+")");
					
					excuteHql("delete from TrainingPolicy where nodeId in("+nodeIds+")");
					excuteHql("delete from PaperAssemblingPolicy where nodeId in("+nodeIds+")");
					excuteHql("delete from PaperAssemblingPolicy where nodeId in("+nodeIds+")");

					excuteHql("delete from NodeGroupPolicy where asfNode.id in("+nodeIds+")");
					
					//删除节点组的所有模板
					excuteHql("delete from NodeGroupPolicyAssembling where nodeId in("+nodeIds+")");
					//先将流程的起始节点设置为空
					String processIds= find("select distinct(processDefinition.id) from Node where id in ("+nodeIds+")").toString().replace("[", "").replace("]", "").trim();
					reInitStartNode(processIds,null);					
					excuteHql("delete from Node where id in("+nodeIds+")");
					excuteHql("delete from NodeGroup where id in("+nodeIds+")");
					//从新设置流程的起始节点
					reInitStartNode(processIds,"");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			errorList.add("删除节点策略时出现异常");
		} 
		map.put("errorList", errorList);
		return map;
	}
	
	/**
	 * 重设计算流程的 起始节点
	 * @param processId
	 */
	public void reInitStartNode(String processIds ,String isSet ){ 
		try{
			if(processIds!=null&&!"".equals(processIds)){
				List processList = find("from ProcessDefinition where id in("+processIds+")");
				List nodeList = find("from Node where processDefinition.id in("+processIds+")");
				for (Iterator iterator = processList.iterator(); iterator.hasNext();) {
					ProcessDefinition pd = (ProcessDefinition) iterator.next();
					if(null == isSet){
						pd.setStartNode(null);
					}else{
						if(nodeList!=null&&nodeList.size()>0){
							for (Iterator iterator1 = nodeList.iterator(); iterator1.hasNext();) {
								Node node = (Node) iterator1.next();
								//默认设置规则
								if(!(node instanceof NodeGroup)){
									pd.setStartNode(node);
									break;
								}
							}
						}
					}
					if(isSet!=null){
						//System.out.println();
					}
					save(pd);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println();
		}
		
	};
	
	/**
	 * 计算 节点或节点组 集合 id。  用,隔开的方式返回string  如（2,3,4,545,33） 
	 * @param set
	 * @return
	 */
	public String makeIdsBySet(Collection set){
		String nodeIds="";
		int i=0;
		if(set!=null){
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Node tnode = (Node) iterator.next();
				if(++i>1&&i<=set.size()){
					nodeIds +=","+tnode.getId(); 
				}else{
					nodeIds +=""+tnode.getId();
				}
			}
		}
		
		return nodeIds.trim();
	}
	public boolean theProcessWithNodeidIsInstance(String nodeIds){
		ProcessInstance pi = null;
		//找出所有要删除的节点对应的流程ids 
		String hql = "select distinct(processDefinition.id ) from Node where id in("+nodeIds+") ";
		String processIds = find(hql ).toString().replace("[", "").replace("]", "").trim();
		if(!"".equals(processIds)){
			pi = (ProcessInstance)findObjByHql("from ProcessInstance where processDefinition.id in ("+processIds+")");
		}
		if(pi!=null){
			return true;
		}
		return false;
	}
	
	public String getValueInSQL(Object value){
		if(value!=null){
			String vtype = value.getClass().getSimpleName();
			return "String".equals(vtype)? "'"+value+"'":""+value;
		}else{
			return "null";
		}
	};
	private int updateTable( String tableName,String coumlun,Object value,String coditionColumn,Object coditionValue){
		String sql="update " +tableName+" set "+coumlun+" = "+getValueInSQL(value)+" where "+coditionColumn+" = "+ getValueInSQL(coditionValue);
		logger.debug("[NodeService:updateTable()] 执行的sql为："+sql);
		return getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
		 
	}
 
	 /**
	  * 取出节点组所有的下级节点包含节点组本身 
	  * @param nodeGroup
	  * @param all
	  * @return
	  */
	private Set findAllNodeByGroupId(NodeGroup nodeGroup,Set all){
		if(nodeGroup!=null){
			if(all==null){
				all = new HashSet();
			}
			List nodesLst=nodeGroup.getNodes();
			all.addAll(nodesLst);
			all.add(nodeGroup);
			if(nodesLst!=null){
				for (Iterator iterator = nodesLst.iterator(); iterator.hasNext();){
					Node node =(Node) iterator.next();
					if(node instanceof NodeGroup){
						findAllNodeByGroupId((NodeGroup)node,all);
					}else{
						all.add(node);
					}
				}
			}
		}
		return all;
	}	 


	
	public ProcessDefinition findProcessDefinition(Node node){
		if(node==null){
			return null;
		}else{
			ProcessDefinition process=node.getProcessDefinition();
			if(process!=null){
				return process;
			}else{
				Node temp = node.getNodeGroup();
				if(temp!=null){
					return findProcessDefinition(temp);
				}else{
					return null;
				}
			}
		}
	} 
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		Node node = get(Node.class, id);
		
		//可能的父亲
		List parentList =null;
		if(node!=null){
			ProcessDefinition pd=findProcessDefinition(node );
			if(pd!=null){
				String hql = "from Node n where n.id!="+node.getId()+" and n.processDefinition.id="+ pd.getId();
				parentList =  find(hql, null);
				parentList.removeAll(nodeSons(node));
				map.put("processNodesJson", NodeJson .getNodeJson(this,pd.getId()));
			}
		}
		map.put("orderNumForShow",orderNumForShow(node));
		map.put("node",node);
		map.put("parentList",parentList);
		
		return map;
	}
	public List nodeSons(Node node){
		List sonLst=new ArrayList();
		if(node instanceof NodeGroup){
			for (Iterator iterator = ((NodeGroup)node).getNodes().iterator(); iterator.hasNext();) {
				Node fnode = (Node) iterator.next();
				if(fnode instanceof NodeGroup){
					sonLst.addAll(nodeSons(fnode));
					sonLst.add(fnode);
				}else{
					sonLst.add(fnode);
				}
			}
		}
		return sonLst;
	}
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(Node.class) ;
	}
	
	public Node get(long id){
		return super.get(Node.class,id) ;
	}
	
	
	/*================================用于增加节点前，更新节点前的相关数据显示（如父亲节点id,）|=================================*/
	/**
	 * 用于增加节点页面显示数据的提取
	 * @param p
	 * @return 包含key为parentNodeLst 的所有该流程节点
	 */
	public Map saddWithP(ParamObject p){
		Map viewMap = new HashMap();
		Long processId = p.getLong("prid");
		Long nodeId = p.getLong("nid");
		if(processId ==null || processId ==0){
			List errorLst = new ArrayList();
			errorLst.add("没有流程id");
			viewMap.put("errorLst",errorLst);
		}else{
			/*String hql = "";
			if(nodeId == null || nodeId==0){
				 hql = "from Node n where n.processDefinition.id=?";
				 viewMap.put("parentNodeLst",find(hql,processId));//viewMap.get("parentNodeLst")
			}else{
				 hql = "from Node n where n.id!=? and n.processDefinition.id=?";
				 viewMap.put("parentNodeLst",find(hql,nodeId,processId));
			}
			*/			 
			viewMap.put("parentNodeLst",find("from Node n where n.processDefinition.id=?",processId));
		}
		viewMap.put("processId",processId) ;
		viewMap.put("p",p);
		return viewMap;
	}
 
	public String initNodeType(String nodeType){
		if(nodeType==null||"".equals(nodeType.trim())){
			//默认为训练节点
			nodeType = "PRACTICE";
		}
		return  nodeType;
	}
	/*================================用于增加节点前，更新节点前的相关数据显示（如父亲节点id,）^|=================================*/

	public ProcessDefinitionService getProcessDefinitionService() {
		return processDefinitionService;
	}

	public void setProcessDefinitionService(
			ProcessDefinitionService processDefinitionService) {
		this.processDefinitionService = processDefinitionService;
	}
}
