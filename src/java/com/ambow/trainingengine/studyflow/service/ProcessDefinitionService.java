package com.ambow.trainingengine.studyflow.service;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.common.ProcessReleaseStatus;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.DecisionRecord;
import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.studyflow.vo.ProcessDefinitionVO;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.CommonTool;
import com.ambow.trainingengine.util.ParamObject;


public class ProcessDefinitionService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(ProcessDefinitionService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from ProcessDefinition",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/************************************
	 * USE: 多个查询条件时，根据组装好的HQL语句和查询条件进行查询
	 * PARAM: hql 组装好的HQL语句，pageNo 页码，values 查询条件数组
	 * RETURN: 查询得到的结果
	 * FOR: 流程进行查询时，简易查询与高级查询使用此方法得到查询的结果
	 * 
	 * AURHOT: L.赵亚
	 * DATE: 2009.02.05.14.49
	 * 
	 */
	public Page listByPage(String hql, int pageNo, Object... values){
		return this.pagedQuery(hql, pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 取得与传入节点同级的所有训练节点 不包含单元测试 
	 * @param unitTestNode
	 * @param type
	 * @return
	 */
	public List getPracticeNodeListInSamelevel(Node unitTestNode ){
		return getPracticeNodeListInSamelevel(unitTestNode,null);
	}

	public ProcessDefinition copiedProceddDefinition(ProcessDefinition pd,SysUser sysUser){
		ProcessDefinition copiedProcess = null;
		if(pd!=null&&sysUser!=null){
			String creator = sysUser.getUsername();
			Date createDate = new Date();
			copiedProcess = new ProcessDefinition();
			copiedProcess.setCategoryId(pd.getCategoryId());
			copiedProcess.setCreateTime(createDate);
			copiedProcess.setCreator(creator);
			copiedProcess.setDefVersion(pd.getDefVersion());
			copiedProcess.setDescription(pd.getDescription());
			copiedProcess.setName("(复制) "+pd.getName());
			copiedProcess.setReleaseStatus(ProcessReleaseStatus.UNRELEASE);
			save(copiedProcess);
			List<Node> copiedProcessNodes = getNodes(pd.getNodes(),sysUser.getUsername(),new Date(),copiedProcess);
			for (Node node : copiedProcessNodes) {
				save(node);
			}
			 
		}
		return copiedProcess;
	}
	
	/**
	 * 
	 * @param processNodes
	 * @param creator
	 * @param createDate
	 * @param pd
	 * @return
	 */	
	@SuppressWarnings("unused")
	public void clonProcess(Long processId) throws HibernateException,SQLException{
		CallableStatement cstmt;
		cstmt = this.getHibernateTemplate().getSessionFactory().
		getCurrentSession().connection().prepareCall("{call clonProcess("+processId+",0)}");
		cstmt.executeUpdate(); 
	}
	
	public void clonProcessByNodeGroupId(Long nodeGroupId) throws HibernateException,SQLException{
		CallableStatement cstmt;
		cstmt = this.getHibernateTemplate().getSessionFactory().
		getCurrentSession().connection().prepareCall("{call clonProcess("+nodeGroupId+",1)}");
		cstmt.executeUpdate();
	}
	
	
	private List<Node> getNodes(List<Node> processNodes,String creator,Date createDate,ProcessDefinition pd){
		List<Node> newProcessNodes = new ArrayList<Node>();
		for (Node node : processNodes) {
			newProcessNodes.add(initNodeGroup(node,node.getNodeGroup(),creator,createDate,pd));
		}
		return newProcessNodes;
	}
	/**
	 * 
	 * @param ng 新对象
	 * @param parentNode
	 * @param sourceN
	 * @param creator
	 */
	private Node initNodeGroup(Node sourceN, NodeGroup parentNode,String creator,Date createDate,ProcessDefinition pd){
		Node n = null;
		if(sourceN!=null){
			if(sourceN instanceof NodeGroup){
				n=new NodeGroup();
				List<Node> itemNodes = new ArrayList<Node>();				
				for (Node _node : ((NodeGroup)sourceN).getNodes()) {
					itemNodes.add(initNodeGroup(_node ,(NodeGroup)n, creator,createDate,pd));
				}
				((NodeGroup)n).setNodes(itemNodes);
			}else if(sourceN instanceof PracticeNode){
				n=new PracticeNode();
			}else if(sourceN instanceof PhaseTestNode){
				n=new PhaseTestNode();
			}else if(sourceN instanceof UnitTestNode){
				n=new UnitTestNode();
			}else if(sourceN instanceof EvaluateNode){
				n=new EvaluateNode();
			}else if(sourceN instanceof ExamNode){
				n=new ExamNode();
			}
			if(n!=null){
				n.setCreateTime(createDate);
				n.setProcessDefinition(pd);
				n.setCreator(creator);
				n.setDescription(sourceN.getDescription());
				n.setDest(sourceN.getDest());
				n.setName(sourceN.getName());
				n.setOrderNum(sourceN.getOrderNum());
				n.setNodeGroup(parentNode);
			}
		}
		return n;
	}
	/**
	 * 根据策略取本级或上上级 训练节点及单元测试  
	 * @param unitTestNode 单元测试节点
	 * @param type 不为空：则取 所有单元测试 ＋ 所有训练节点 
	 * @return
	 */
	 public List getPracticeNodeListInSamelevel(Node unitTestNode,String type){
		List findList = new ArrayList(0);
		if(unitTestNode!=null){
			//默认为查找同级训练节点及单元测试  
			NodeGroup ng = unitTestNode.getNodeGroup();
			UnitTestNodePolicy utnp = (UnitTestNodePolicy)findObjByHql("from UnitTestNodePolicy where asfNode.id=? ",unitTestNode.getId());
			if(utnp!=null){
				Integer retrainingScope = utnp.getRetrainingScope();
				if(retrainingScope!=null){
					if(retrainingScope==1){
						if(ng!=null){
							//上级
							if(ng.getNodeGroup()!=null){
								ng = ng.getNodeGroup();
							}
						}
					}
					if(retrainingScope==2){
						if(ng!=null){
							//上级 
							NodeGroup png = ng.getNodeGroup();
							if(png!=null){
								ng=png;
								//上上级 爷爷级
								NodeGroup gng = png.getNodeGroup();
								if(gng!=null){
									ng = gng;
								}	
							}
						}
					}
				}
			}
			if(ng!=null){
				List ngSons =  nodeSons(ng);
				for (Iterator iterator = ngSons.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					if(node.getNodeType()==NodeType.PRACTICE){
						findList.add(node);
					}
					if(type!=null){
						if(node.getNodeType()==NodeType.UNITTEST){
							findList.add(node);
						}
					} 
				}
			}
		}	
		return findList;
	}
	 
	 /**
		 * 取得一个节点的所有儿子
		 * @param node
		 * @return
		 */

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
	public List getAllNode(Long processId){
		List nodeList =new ArrayList(0);
		ProcessDefinition pd=get(processId);
		if(pd!=null){
			String hql = "from Node n where n.processDefinition.id="+ pd.getId();
			nodeList =  find(hql, null);
		}
		return nodeList;
	}
	
	
	public Map search(ParamObject p){
		Map map = new HashMap(0);
		if(p!=null){
			 String hsql="from ProcessDefinition ";
			 String condition ="";
			 int i = 0;
			//1找到与查询条件相符的所有流程
			String pname = p.get("name");
			if(pname!=null&&!"".equals(pname.trim())){
				condition  = " name like '%" +pname.trim().replace("'","%") +"%' ";
			}
			
			String creator = p.get("creator");
			if(creator!=null&&!"".equals(creator.trim())){
				if(!"".equals(condition.trim())){
					condition += " and ";
				}
				condition  += " creator like '%" +creator.trim()+"%' ";
			}
			
			String updator = p.get("updator");
			if(updator!=null&&!"".equals(updator.trim())){
				if(!"".equals(condition.trim())){
					condition += " and ";
				}
				condition  += " updator like '%" +updator.trim()+"%' ";
			}
			
			
			 
			//如果 选择了分类名称
			String conditionCategory ="" ;
			String categoryName = p.get("categoryName");			
			if(categoryName!=null&&!"".equals(categoryName)) {
				List piLst = find("select id from ProcessCategory where name = ?",categoryName.trim());
				String processCategoryIds = piLst.toString().replaceAll("\\[|\\]", "");
				if(!"全部".equals(categoryName)){
					if(!"".equals(processCategoryIds.trim())){
						conditionCategory=" categoryId in ("+processCategoryIds+")" ;
					}
				}
			}
			
			String conditionStatus ="" ;
			String releaseStatus = p.get("releaseStatus");
			ProcessReleaseStatus processReleaseStatus = null;
			if(releaseStatus!=null&&!"".equals(releaseStatus)){
				releaseStatus = releaseStatus.trim();
				if(releaseStatus.equals("已作废")){
					processReleaseStatus = ProcessReleaseStatus.ABANDON;
				}else if(releaseStatus.equals("已发布")){
					processReleaseStatus = ProcessReleaseStatus.RELEASE;
				}else if(releaseStatus.equals("未发布")){
					processReleaseStatus = ProcessReleaseStatus.UNRELEASE;
				}
				if(!releaseStatus.equals("全部")){
					if(processReleaseStatus!=null){
						conditionStatus=" releaseStatus = ? " ;
					}
				}
			}
			
			List searchItems = new ArrayList();
			if(!"".equals(conditionCategory)){
				if(!"".equals(condition.trim())){
					condition += " and " +conditionCategory;
				}else{
					condition += conditionCategory;
				}
			}
			if(!"".equals(conditionStatus)){
				if(!"".equals(condition.trim())){
					condition +=" and "+conditionStatus;
				}else{
					condition += conditionStatus;
				}
			}
			if("".equals(condition.trim())){
				searchItems= find(hsql ) ;
			}else{
				hsql = hsql + (!"".equals(condition.trim())?" where "+condition:"");
				if(!"".equals(condition.trim())){
					if(processReleaseStatus!=null){
						searchItems= find(hsql,processReleaseStatus ) ; 
					}else{
						searchItems= find(hsql) ; 
					}
				}
			}
			
			List pvoLst = new ArrayList();
			for (Iterator iterator = searchItems.iterator(); iterator.hasNext();) {
				ProcessDefinition  name = (ProcessDefinition) iterator.next();
				pvoLst.add(getPVO(name));
			}
			map.put("searchItems",pvoLst);
		 }
		return map; 
	}
	
	
	public Query exeHQL(String hql){
		return getTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
	}
	
	/**查询所有流程实例状态为p*/
	public List test(ParamObject p){
		//2找到状态与查询条件相符的流程
		List prLstFromStatus = new ArrayList();
		String prStatus = p.get("processStatusStr");
		if(prStatus!=null&&!"".equals(prStatus)){
			String c  = "";
			ProcessStatus pStatus = null;
			if(prStatus.equals("终止")){
				pStatus = ProcessStatus.STOPED;
			}else if(prStatus.equals("运行中")){
				pStatus = ProcessStatus.RUNNING;
			}else if(prStatus.equals("用户暂停")){
				pStatus = ProcessStatus.SUSPEND_PAUSE;
			}else if(prStatus.equals("生词训练")){
				pStatus = ProcessStatus.SUSPEND_FRESHWORD;
			}
			if(c!=""&&pStatus!=null){
				String prLstFromStatusHQL = "from ProcessInstance where processStatus = ?"; 
				List piLst = find(prLstFromStatusHQL ,pStatus);
				String processIds = "";
				if(piLst!=null){
					int startNum = 0;
					for (Iterator iterator = piLst.iterator(); iterator.hasNext();) {
						ProcessInstance pi = (ProcessInstance) iterator.next();
						++startNum;		
						processIds +=""+ pi.getProcessDefinition().getId();
						if(startNum>1&&startNum!=piLst.size()){
							processIds += ",";
						}
					}
				}
				if(processIds!=""){
					prLstFromStatus.addAll(find("from ProcessDefinition where id in(?)" ,processIds));
				}
			}
			
			/*for (Iterator iterator = prLstFromStatus.iterator(); iterator.hasNext();) {
				ProcessDefinition p_temp = (ProcessDefinition) iterator.next();
				//allFindProcessSet.add(getPVO(p_temp));
			}
			
			*/
			
		}
		return prLstFromStatus;
	}
	
	public ProcessDefinitionVO getPVO(ProcessDefinition processDefinition){
		if(processDefinition!=null){
			ProcessDefinitionVO pVO = new ProcessDefinitionVO(processDefinition);
			if(processDefinition.getCategoryId()!=0){
				ProcessCategory pc = get(ProcessCategory.class,processDefinition.getCategoryId());
				if(pc!=null){
					pVO.setCategoryName(pc.getName());
				}
			}
			
			/*ProcessInstance pi = (ProcessInstance)findObjByHql("from ProcessInstance where processDefinition.id=?",processDefinition.getId());
			if(pi!=null){
				pVO.setProcessStatusStr(getProcessStatusStr(pi.getProcessStatus()));
			}
			*/
			return pVO;
		}
		return null;
	}
	
	public String getProcessStatusStr(ProcessStatus ps){
		String str ="";
		if(ps== ProcessStatus.RUNNING){
			str = "运行中";
		}else if(ps== ProcessStatus.STOPED){
			str = "终止";
		}else if(ps== ProcessStatus.SUSPEND_FRESHWORD){
			str = "生词训练";
		}else if(ps== ProcessStatus.SUSPEND_PAUSE){
			str = "用户暂停";
		}
		return str;
	}
	/**
	 * 作废流程
	 * @return
	 */
	public List abandon(long id){
		List errorList = new ArrayList(0);
		ProcessDefinition process = get(id);
		if(process!=null){
			process.setReleaseStatus(ProcessReleaseStatus.fromInt(-1));
			save(process);
		}else{
			errorList.add("要作废的流程不存在");
		}
		return errorList;
	}
	
	/**
	 * 批量作废流程
	 * @return
	 */
	public List abandons(String ids){
		List errorList = new ArrayList(0);
		try{
			if(false){
				//TODO 当流程处于什么意态时不能作废流程 
				errorList.add("不能作废流程的情况");
			}
			excuteHql("update ProcessDefinition set releaseStatus = -1 where id in ("+ids+")" );
		}catch(Exception e){
			errorList.add("批量作废流程时出现异常");
		}
		return  errorList;
	}
	
	
	
	/**
	 * 发布流程
	 * @return
	 */
	public List release(long id){
		return releases(""+id);		
	}
	
	/**
	 * 批量发布流程
	 * @return
	 */
	public List releases(String ids){
		List errorList = new ArrayList(0);
		try{
			if(false){
				//TODO 已经作废的流程是否能重新标记为发布
				errorList.add("已经作废的流程不能在发布");
			}
			
			
			
			excuteHql("update ProcessDefinition set releaseStatus = 1 where id in ("+ids+")");
			initProcessDefinition(ids,"","","");
		}catch(Exception e){
			errorList.add("批量作废流程时出现异常");
		}
		return  errorList;
	}
	
	/**
	 * 用于流程发布时初始化流程的 开始节点 ，流程中非节点组类型的下一节点 ，初始化决策器(?作废的又重新发布处理问题)
	 * @param processIdOrProcessDefition 流程id如(1,95,52,25)或 某个流程对象
	 * @param initStartNode  不为空则从新初始化流程开始节点; 为空不初始化流程开始节点
	 * @param initNextNode  不为空：设置流程中节点的 下一节点 ; 为空：不初始化下一结点
	 * @param initAsfDecisionRecord 不为空：设置流程中所有节点的决策器
	 * @return
	 */
	public Map initProcessDefinition(Object processIdOrProcessDefition,String initStartNode ,String initNextNode,String initAsfDecisionRecord){
		Map map = null;
		Long processIds [] =null;
		if(processIdOrProcessDefition!=null){
			if(processIdOrProcessDefition instanceof String){
				String idStrArray[]=((String)processIdOrProcessDefition).split(",");
				processIds= new Long [idStrArray.length];
				for (int i = 0; i < idStrArray.length; i++) {
					try{
						processIds[i] = new Long(idStrArray[i]);
					}catch(Exception e){
					}
				}
			}else if(processIdOrProcessDefition instanceof ProcessDefinition){
				processIds = new Long[1];
				processIds[0]=((ProcessDefinition)processIdOrProcessDefition).getId();
			}
		}
		if(processIds!=null){
			for (int i = 0; i < processIds.length; i++) {
				try{
					if(processIds[i]!=null){
						ProcessDefinition pd = get( ProcessDefinition.class,processIds[i]);
						List nodes = find("from Node where processDefinition.id=? order by orderNum",processIds[i]);
						List nodeNoNodeGroupList= new ArrayList();
						//取到所有非NodeGroup的节点
						for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
							Node node = (Node) iterator.next();
							if(node.getNodeType()!=NodeType.GROUP){
								nodeNoNodeGroupList.add(node);
							}
						}
						//如果流程存在
						if(pd!=null){
							//初始化流程 起始节点
							if(initStartNode!=null){
								for (Iterator iterator = nodes.iterator(); iterator
										.hasNext();) {
									Node node = (Node) iterator.next();
									if(node.getNodeType()!=NodeType.GROUP){
										pd.setStartNode(node);
										save(pd);
										break;
									};
								}
							}
							//初始化流程中节点的下一个结点
							if(initNextNode!=null){								
								if(nodeNoNodeGroupList.size()>0){
									int index=0;
									for (Iterator iterator = nodeNoNodeGroupList.iterator(); iterator.hasNext();) {
										Node node = (Node) iterator.next();
										if(++index<nodeNoNodeGroupList.size()){
											node.setNextNode((Node)nodeNoNodeGroupList.get(index));
											save(node);
										}
									}
									Node lastNode = ((Node)nodeNoNodeGroupList.get(nodeNoNodeGroupList.size()-1));
									lastNode.setNextNode(null);
									save(lastNode);
								}
							}
							//初始化流程决策器
							if(initAsfDecisionRecord!=null){
								//先删除流程中所有的决策器
								for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
									Node node = (Node) iterator.next();
									if(node.getNodeType()!=NodeType.EVALUATE){
										DecisionRecord dr =(DecisionRecord) findObjByHql("from DecisionRecord where node.id=?",node.getId());
										if(dr==null){
											dr=new DecisionRecord();
											dr.setNode(node);
										}
										if(node.getNodeType()==NodeType.PRACTICE){
											dr.setDecisionName("practiceDecision");
										}else if(node.getNodeType()==NodeType.PHASETEST){
											dr.setDecisionName("phaseExamDecision");
										}else if(node.getNodeType()==NodeType.GROUP){
											dr.setDecisionName("groupDecision");
										}else if(node.getNodeType()==NodeType.EXAM){
											dr.setDecisionName("examDecision");
										}else if(node.getNodeType()==NodeType.UNITTEST){
											dr.setDecisionName("examDecision");
										}
										save(dr);
									}
								}
							}
						}
					}
				}catch(Exception e){
					
				}
			}
		}
		return map;
	}
	
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			if(id==null||id==0){
				errorList.add("请选择一个要删除的流程，然后再试");
			}else{
				ProcessDefinition process = get(ProcessDefinition.class,id);
				if(process==null){
					errorList.add("要删除的流程不存在!");
				}else{
					//直接删除所有相关信息 （涉及的表 node 策略 ）
//					//boolean canDelete = false;
//					List piLst = find("from ProcessInstance where processDefinition.id=? ", process.getId());
//					if(piLst!=null&&piLst.size()>0 ){						
//						errorList.add("该流程已经被实例化过，您只能 作废 或 重新发布 ");
//					}else{
						
						//TODO 删除节点策略
						String hql = "select id from Node where processDefinition.id=? ";
						String nodeIds = find(hql,id).toString().replace("[", "").replace("]", "").trim();
						if(!"".equals(nodeIds)){
							excuteHql("delete from NodeInstance where node.id in("+nodeIds+")");							
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
							
							//先将流程的起始节点设置为空
							String processIds= find("select distinct(processDefinition.id) from Node where id in ("+nodeIds+")").toString().replace("[", "").replace("]", "").trim();
					
							excuteHql("delete from Node where id in("+nodeIds+")");
							excuteHql("delete from NodeGroup where id in("+nodeIds+")");
						}
						
						excuteHql("delete from ProcessPolicy where id="+id);
						excuteHql("delete from ProcessDefinition where id="+id);
					//}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			logger.error("［ProcessDefinitionService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("删除流程时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ProcessDefinition where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［ProcessDefinitionService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}

	public Map sadd(){
		Map map = new HashMap();
		map.put("processCategoryLst", super.getAll(ProcessCategory.class));
		map.put("pcListJson",ProcessCategoryService.getProcessCategoryJson(this));
		return map;
	}
	/**
	 * 向系统中添加 新ProcessDefinition 对象
	 * @param processDefinition
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(ProcessDefinition processDefinition){
		List errorList = new ArrayList(0);
		try{
			
			processDefinition.setCreateTime(new Date());
			
			save(processDefinition);
		}catch(Exception e){
			String errorMsg = "［ProcessDefinitionService:add(ProcessDefinition processDefinition)］"+processDefinition+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 ProcessDefinition 对象，默认与add行为相似
	 * @param processDefinition
	 * @return 更新 ProcessDefinition 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(ProcessDefinition processDefinition,ParamObject p){
		List errorList = new ArrayList(0);
		if(processDefinition!=null){
			ProcessDefinition processDefinitionDb =  get(processDefinition.getId());
			
			try{
				processDefinitionDb.setCategoryId(processDefinition.getCategoryId());
				processDefinitionDb.setDescription(processDefinition.getDescription());
				processDefinitionDb.setName(processDefinition.getName());		
				processDefinitionDb.setDefVersion(processDefinition.getDefVersion());
				//update ProcessDefinition前的(如果有子对象)对其子对象进行相关处理
				processDefinitionDb.setUpdateTime(new Date());
				processDefinitionDb.setUpdator(processDefinition.getUpdator());
				save(processDefinitionDb);
			}catch(Exception e){
				String errorMsg = "［ProcessDefinitionService:update(ProcessDefinition processDefinition)］"+processDefinition+" 保存对象时出现异常!"+e.toString();
				errorList.add("数据保存时出现异常");
				logger.error(errorMsg);
			}
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		ProcessDefinition process = get(ProcessDefinition.class, id);
		map.put("ProcessDefinition",process);
		map.put("processPolicy",get(ProcessPolicy.class, id));
		map.put("processCategory",get(ProcessCategory.class, process.getCategoryId()));
		map.put("processCategoryLst",getAll(ProcessCategory.class));
		map.put("pcListJson",ProcessCategoryService.getProcessCategoryJson(this));
		
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(ProcessDefinition.class) ;
	}
	
	public ProcessDefinition get(long id){
		return super.get(ProcessDefinition.class,id) ;
	}

	/*******************************************
	 * 
	 * CHANGES: 添加查询条件：流程说明
	 * UPDATOR: L.赵亚
	 * DATE: 2010.03.01
	 * 
	 */
	public Page findLikeObj(ProcessDefinition processDefinition,int pageNo,boolean flag) {
		StringBuilder sb = new StringBuilder("from ProcessDefinition p where p.categoryId = "+ processDefinition.getCategoryId());
		/*if(processDefinition.getCategoryId()!= null) {
			sb.append(" and p.categoryId = "+ processDefinition.getCategoryId());
		}*/
		if(CommonTool.notEmptyStr(processDefinition.getName())) {
			sb.append(" and p.name like '%"+ processDefinition.getName()+"%'");
		}
		
		//查询条件：流程说明
		if(CommonTool.notEmptyStr(processDefinition.getDescription())) {
			sb.append(" and p.description like '%"+ processDefinition.getDescription()+"%'");
		}
		
		if(flag) {
			sb.append(" and p.defVersion = " + processDefinition.getDefVersion());
		}
//		return this.pagedQuery(sb.toString(), pageNo, 2);
		return this.pagedQuery(sb.toString(), pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
}
