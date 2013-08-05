package com.ambow.trainingengine.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.util.MathUtil;
import com.ambow.trainingengine.web.data.GroupNodeStatVO;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * StudyTaskService.java
 * 
 * Created on 2008-7-31 下午03:22:28
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class StudyTaskService extends BaseWebService {

	/**
	 * 获取节点组统计信息 for ky
	 * actor: 当前登陆用户
	 */
	@SuppressWarnings("unchecked")
	public List<GroupNodeStatVO> getGroupNodeStatList(UserDataVO userData){
		
		List<GroupNodeStatVO> outList=new ArrayList<GroupNodeStatVO>();
		for(ShowNodeGroupVO vo:userData.getShowNodeGroupVOList()){
			GroupNodeStatVO groupNodeStatVO=new GroupNodeStatVO();
			//单元中统计通过训练节点的试卷总分数
			int passTotalScore=this.getJdbcTemplate().queryForInt(
					"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b,asf_node_instance c"
					+" where a.id=b.node_id and a.id=c.node_id and c.node_status in(2,3) and a.id in ("+vo.getChildNodeIds()+") and c.process_instance_id=?"
					,new Object[]{userData.getProcessInstanceId()});
			//统计整个流程试卷训练节点的总分数
			int totalPaperScore=this.getJdbcTemplate().queryForInt(
					"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b"
					+" where a.id=b.node_id and a.id in ("+vo.getChildNodeIds()+")");
			
			groupNodeStatVO.setUserProgressRate(MathUtil.percent(passTotalScore, totalPaperScore));
			groupNodeStatVO.setId(vo.getNodeId());
			groupNodeStatVO.setName(vo.getNodeName());
			outList.add(groupNodeStatVO);
		}
		
		return outList;
	}
	
	/**
	 * 获取节点组统计信息 for mpc
	 * actor: 当前登陆用户
	 */
	@SuppressWarnings("unchecked")
	public List<GroupNodeStatVO> getGroupNodeStatListForMpc(UserDataVO userData){
		List<GroupNodeStatVO> outList=new ArrayList<GroupNodeStatVO>();
		List<Node> nodeList=this.find("from NodeGroup where nodeGroup is null and processDefinition.id=?", Long.valueOf(userData.getRefID()));
		for(Node po:nodeList){
			GroupNodeStatVO groupNodeStatVO=new GroupNodeStatVO();
			//单元中统计通过训练节点的试卷总分数
			int passTotalScore=this.getJdbcTemplate().queryForInt(
					"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b,asf_node_instance c"
					+" where a.id=b.node_id and a.id=c.node_id and c.node_status in(2,3) and a.id in ("+this.getChildNodeIdsForMpc(po)+") and c.process_instance_id=?"
					,new Object[]{userData.getProcessInstanceId()});
			//统计整个流程试卷训练节点的总分数
			int totalPaperScore=this.getJdbcTemplate().queryForInt(
					"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b"
					+" where a.id=b.node_id and a.id in ("+this.getChildNodeIdsForMpc(po)+")");
			
			groupNodeStatVO.setUserProgressRate(MathUtil.percent(passTotalScore, totalPaperScore));
			groupNodeStatVO.setId(po.getId());
			groupNodeStatVO.setName(po.getName());
			outList.add(groupNodeStatVO);
		}
		
		return outList;
	}
	
	/**
	 * 获取节点组下的节点信息
	 */
	@SuppressWarnings("unchecked")
	public List<NodeVO> getNodeListOfGroupNode(Long groupNode, UserDataVO userData){
		
		if(groupNode==0){
			if(userData.getUnitNodeId()>0)
				groupNode=userData.getUnitNodeId();
			else{//获取此流程第一个单元id
				/*List ids=this.getJdbcTemplate().queryForList(
						"select a.id from asf_node a where a.node_type='GROUP' and a.id=? order by a.order_num"
						, new Object[]{userData.getProcessDefinitionId()});
				if(ids!=null&&ids.size()>0)
					groupNode=(Long)((Map)ids.get(0)).get("id");*/
				ShowNodeGroupVO vo=userData.getShowNodeGroupVOList().get(0);
				groupNode=vo.getNodeId();
			}
		}
		String ids="";
		for(ShowNodeGroupVO vo:userData.getShowNodeGroupVOList()){
			if(vo.getNodeId().longValue()==groupNode){
				ids=vo.getChildNodeIds();
				break;
			}
		}
		String sql=	"SELECT n.id, n.name, n.node_type, un.node_status,cts.node_instance_id,ap.answering_time time,ap.paper_assembling_mode," +
		"dap.answering_time, ap.items_num, ap.total_score, tp.right_rate_for_pass,tp.right_rate_retraining,tp.retraining_right_rate_test_faile " +
		"FROM  asf_node n left join asf_node_instance un on n.id = un.node_id " +
		"left join dynamic_assembled_paper dap on un.id=dap.node_instance_id "+
		"left join paper_assembling_policy ap on n.id = ap.node_id " +
		"left join training_policy tp on n.id = tp.node_id " +
		"left join current_test_status cts on un.id = cts.node_instance_id " +
		"where n.id in("+ids+") and un.process_instance_id = ? order by n.order_num"; 
		List list=this.getJdbcTemplate().queryForList(sql,new Object[]{ userData.getProcessInstanceId()});
		List<NodeVO> outList=new ArrayList<NodeVO>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				NodeVO vo=new NodeVO();
				vo.setNodeDefinitionId((Long)map.get("id"));
				vo.setNodeName((String)map.get("name"));
				vo.setNodeType((String)map.get("node_type"));
				vo.setNodeStatus((Integer)map.get("node_status"));
								
				vo.setItemsNum((Integer)map.get("items_num"));
				if(map.get("answering_time")!=null)
					vo.setAnsweringTime((Integer)map.get("answering_time")/60);//把秒换算成分钟				
				else if(map.get("node_instance_id")!=null&&map.get("paper_assembling_mode")!=null&&(Integer)map.get("paper_assembling_mode")==0)//0为手动组卷
					vo.setAnsweringTime((Integer)map.get("time")/60);//把秒换算成分钟
				//if(testStatus==null)passRate=trainingPolicy.getRightRateForPass();
				//if(testStatus!=null&&testStatus.getTestStatus()>=1&&testStatus.getIsTest()==false)passRate=trainingPolicy.getRightRateRetraining();
				//if(testStatus!=null&&testStatus.getIsTest()==true)passRate=trainingPolicy.getRetrainingRightRateTestFaile();
				if(map.get("node_instance_id")==null)
					vo.setRightRateForPass((Float)map.get("right_rate_for_pass"));
				if(map.get("node_instance_id")!=null&&(Integer)map.get("node_status")>0&&!((String)map.get("node_type")).equals("UNITTEST"))
					vo.setRightRateForPass((Float)map.get("right_rate_retraining"));
				if(map.get("node_instance_id")!=null&&((String)map.get("node_type")).equals("UNITTEST"))
					vo.setRightRateForPass((Float)map.get("retraining_right_rate_test_faile"));	
				vo.setNodeInstanceId((Long)map.get("node_instance_id"));
				outList.add(vo);
			}
		}
		return outList;
	}	
	
	/**
	 * 获取节点组下所有非节点组类型的节点ids for mpc
	 */
	@SuppressWarnings("unchecked")
	public String getChildNodeIdsForMpc(Node node){
		String sql="select id from asf_node n where n.process_definition_id=? and n.order_num like ?"
			+" and n.node_type in ('EVALUATE','PHASETEST','PRACTICE','UNITTEST')";
		
		List idList=this.getJdbcTemplate().queryForList(sql, new Object[]{node.getProcessDefinition().getId(),node.getOrderNum()+"%"});
		String outIds="";
		if(idList!=null&&idList.size()>0){
			for(int i=0;i<idList.size();i++){
				Map map=(Map)idList.get(i);
				if(i>0)
					outIds+=",";
				outIds+=map.get("id").toString();
			}
		}
		return outIds;
	}
	
	/**
	 * 获取流程第一个节点组id for mpc
	 */
	@SuppressWarnings("unchecked")
	public Long getFirstGroupNodeId(UserDataVO userData){
		String sql="select id from asf_node where process_definition_id=? group by order_num";
		List idList=this.getJdbcTemplate().queryForList(sql, new Object[]{userData.getRefID()});
		Long outId=null;
		if(idList!=null&&idList.size()>0)
			outId=(Long)((Map)idList.get(0)).get("id");
		
		return outId;
	}
	
	/**
	 * 获取当前模块所有节点任务 for mpc
	 */
	@SuppressWarnings("unchecked")
	public List<NodeVO> getNodeVOListForMpc(Long groupNode, UserDataVO userData){
		Node node=this.get(Node.class,groupNode);
		String sql="select n.id,n.name,n.node_type,n.order_num,p.big_items_num,tp.right_rate_for_pass,p.answering_time"
			+",i.node_status from asf_node n left join paper_assembling_policy p on n.id=p.node_id "
			+" left join training_policy tp on n.id=tp.node_id,asf_node_instance i where n.id=i.node_id and process_instance_id=? "
			+"and n.order_num like ? order by n.order_num";
		List mapList=this.getJdbcTemplate().queryForList(sql, new Object[]{userData.getProcessInstanceId(),node.getOrderNum()+",%"});
		
		int processInstanceLength = getMaxOrderNum(userData.getProcessInstanceId());
		if (processInstanceLength==5) return getNodeVOListForMpc2level(node, mapList);
		
		List<NodeVO> nodeVOList=new ArrayList<NodeVO>();
		for(int i=0;i<mapList.size();i++){
			Map map=(Map)mapList.get(i);
			NodeVO secondLevelNode=new NodeVO();
			secondLevelNode.setNodeName(map.get("name").toString());
			String tempNodeName="";
			List<NodeVO> nodeVOList2=new ArrayList<NodeVO>();
			List<NodeVO> tempList=new ArrayList<NodeVO>();;
			if(map.get("node_type").toString().equals("GROUP")){
				int j=0;
				while(++i<mapList.size()){					
					Map map2=(Map)mapList.get(i);
					if(map2.get("order_num").toString().length()==5)
						break;
					NodeVO fouthLevelNode=new NodeVO();
					//if(map2.get("node_type").toString().equals("GROUP")){
					if(map2.get("node_type").toString().equals("GROUP")){
						tempNodeName=map2.get("name").toString();						
						j=0;
						continue;
					}else{
						//判断是否为三级节点						
						if(map2.get("order_num").toString().length()==8){
							fouthLevelNode.setNodeGroupName("");
							j=1;
						}else{
							fouthLevelNode.setNodeGroupName(tempNodeName);
							j++;
						}
						fouthLevelNode.setNodeName(map2.get("name").toString());
						fouthLevelNode.setItemsNum((Integer)map2.get("big_items_num"));
						fouthLevelNode.setRightRateForPass((Float)map2.get("right_rate_for_pass"));
						fouthLevelNode.setAnsweringTime((Integer)map2.get("answering_time")/60);
						fouthLevelNode.setNodeStatus((Integer)map2.get("node_status"));
						fouthLevelNode.setNodeType(map2.get("node_type").toString());
						fouthLevelNode.setRowNum(j);
						tempList.add(fouthLevelNode);
					}
				}
				
				NodeVO tempNodeVO=null;
				int tempNum=0;
				int k=0;
				for(NodeVO po:tempList){
					k++;
					if(po.getRowNum()==1){
						if(tempNodeVO!=null)
							tempNodeVO.setRowNum(tempNum);
						tempNum=1;
						tempNodeVO=po;
					}else{
						tempNum=po.getRowNum();
						po.setRowNum(0);
					}
					nodeVOList2.add(po);
				}
				tempNodeVO.setRowNum(tempNum);
				secondLevelNode.setNodeVOList(nodeVOList2);
				
				i--;
			}else{
				secondLevelNode.setItemsNum((Integer)map.get("big_items_num"));
				secondLevelNode.setAccuracyRate((Float)map.get("right_rate_for_pass"));
				secondLevelNode.setAnsweringTime((Integer)map.get("answering_time")/60);
				secondLevelNode.setNodeStatus((Integer)map.get("node_status"));
				secondLevelNode.setNodeType(map.get("node_type").toString());
			}
			nodeVOList.add(secondLevelNode);
			
		} 
		return nodeVOList;
	}
	
	private int getMaxOrderNum(long processInstanceId) {
		String sql="select max(length(n.order_num)) " +
				"from asf_node_instance i inner join asf_node n where i.node_id = n.id " +
				"and i.process_instance_id =? ";
		int length = this.getJdbcTemplate().queryForInt(sql, processInstanceId);
		return length;
	}
	
	/**
	 * 2层结构的节点返回
	 * @return
	 */
	public List<NodeVO> getNodeVOListForMpc2level(Node groupNode, List mapList) {
		List<NodeVO> nodeVOList = new ArrayList<NodeVO>();
		NodeVO secondLevelNode = new NodeVO();
		
		List<NodeVO> nodeVOList2=new ArrayList<NodeVO>();
		for(int i=0;i<mapList.size();i++){
			Map map=(Map)mapList.get(i);
			
			NodeVO lastLevelNode=new NodeVO();
			lastLevelNode.setNodeGroupName(groupNode.getName());
			lastLevelNode.setNodeName(map.get("name").toString());
			lastLevelNode.setItemsNum((Integer)map.get("big_items_num"));
			lastLevelNode.setRightRateForPass((Float)map.get("right_rate_for_pass"));
			lastLevelNode.setAnsweringTime((Integer)map.get("answering_time")/60);
			lastLevelNode.setNodeStatus((Integer)map.get("node_status"));
			lastLevelNode.setNodeType(map.get("node_type").toString());
			lastLevelNode.setRowNum(1);
			nodeVOList2.add(lastLevelNode);
		} 
		secondLevelNode.setNodeVOList(nodeVOList2);
		nodeVOList.add(secondLevelNode);
		return nodeVOList;
	}
}