/**
 * 
 */
package com.ambow.trainingengine.report.service;

import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;

/**
 * @author yuanjunqi
 *
 */
public class ConsolidateService extends ReportBaseService{
	
	/**
	 * 获取节点列表
	 * @param processInstanceId
	 * @return
	 */
	public List<Map<String,Object>> getOrderNumList(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select b.order_num");
		sqlBuff.append(" from asf_node_instance a");
		sqlBuff.append(" left outer join asf_node b on a.node_id=b.id");
		sqlBuff.append(" where a.process_instance_id=? and a.node_status !=0");
		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%'");
		}
		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		return mapList;
	}
	
	/**
	 * 获取节点列表
	 * @param processInstanceId
	 * @return
	 */
	public Integer getNodeCount(Long processInstanceId,String orderNumArr,String nodeType){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(*) as num");
		sqlBuff.append(" from asf_node_instance a");
		sqlBuff.append(" left outer join asf_node b on a.node_id = b.id");
		sqlBuff.append(" where a.process_instance_id=?");
		if(orderNumArr != null && !orderNumArr.equals("")){
			sqlBuff.append(" and b.order_num in("+orderNumArr+") ");
		}
		if(nodeType != null){
			sqlBuff.append(" and b.node_type='"+nodeType+"'");
		}
		Integer num = this.getJdbcTemplate().queryForInt(sqlBuff.toString(), processInstanceId);

		return num;
	}
	/**
	 * 获取节点组下面的流程实例状态
	 * @param processInstanceId
	 * @param nodeGroupId
	 * @return
	 */
	public List<Map<String,Object>> getNodeStatusList(Long processInstanceId,Long nodeGroupId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select node_status from `asf_node` a");
		sqlBuff.append(" left join `asf_node_instance` b on a.id=b.node_id");
		sqlBuff.append(" where a.node_group_id=? and b.process_instance_id=?");
		List<Map<String,Object>> nodeStatusList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), nodeGroupId,processInstanceId);
		return nodeStatusList;
	}
	
	/**
	 * 获取流程实例
	 * @param processInstanceId
	 * @return
	 */
	public ProcessInstance getProcessInstance(Long processInstanceId){
		String hsql = "from ProcessInstance a where a.id=?";
		
		ProcessInstance processInstance = (ProcessInstance)this.findObjByHql(hsql, processInstanceId);
		
		return processInstance;
	}
	
	/**
	 * 获取节点实例
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public NodeInstance getNodeInstance(Long processInstanceId,String orderNum){
		String hsql = "from NodeInstance a where a.processInstance.id=? and a.node.orderNum=?";
		
		NodeInstance nodeInstance = (NodeInstance)this.findObjByHql(hsql, processInstanceId,orderNum);
		return nodeInstance;
	}
	
	/**
	 * 获取节点列表
	 * @param processInstanceId
	 * @param groupId
	 * @return
	 */
	public List<Map<String,Object>> getNodeList(Long processInstanceId,String groupOrderNum,String nodeType){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,a.name,a.order_num,d.id as node_instance_id from asf_node a ");
		sqlBuff.append(" inner join asf_process_instance b on a.process_definition_id =b.process_definition_id");
		sqlBuff.append(" left join asf_node c on a.node_group_id = c.id ");
		sqlBuff.append(" left join asf_node_instance d on a.id = d.node_id ");
		sqlBuff.append(" where b.id=? and b.id=d.process_instance_id and (a.node_type='"+nodeType+"' or a.node_type='GROUP')");
		if(groupOrderNum == null || groupOrderNum.equals("")){
			sqlBuff.append(" and a.node_group_id is null");
		}else{
			sqlBuff.append(" and c.order_num ='"+groupOrderNum+"'");
		}

		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),processInstanceId);
		return list;
	}
	
	/**
	 * 弱项强化流程统计
	 * @param processInstanceId
	 * @param nodeIds
	 * @return
	 */
	public List<Map<String,Object>> getStatReportList(long processInstanceId,String nodeIds,String nodeType){
		String sql=" select sum(d.first_test_score) first_test_score,"
			+" sum(d.score) score,sum(c.total_score) total_score,sum(c.items_num) items_num,sum(d.sum_correct_items) sum_correct_items,"
			+" sum(d.sum_incorrect_items) sum_incorrect_items,sum(d.sum_unfinished_items) sum_unfinished_items,"
			+" sum(d.sum_zero_star_items) sum_zero_star_items,sum(d.sum_half_star_items) sum_half_star_items,"
			+" sum(d.sum_one_star_items) sum_one_star_items,sum(d.sum_two_star_items) sum_two_star_items,"
			+" sum(d.sum_three_star_items) sum_three_star_items,sum(d.sum_four_star_items) sum_four_star_items,"
			+" sum(d.sum_five_star_items) sum_five_star_items,sum(d.unsure_mark_items) unsure_mark_items "
			+" from asf_node a left outer join asf_node e on a.node_group_id=e.id,asf_node_instance b,"
			+" paper_assembling_policy c,current_test_status d "
			+" where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id and a.node_type='"+nodeType+"' and b.process_instance_id=?";
			//+" where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id and b.process_instance_id=?";
		if(nodeIds != null && !nodeIds.equals("")){
			sql = sql + " and a.id in("+nodeIds+") ";
		}
		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sql,processInstanceId);
		return list;
	}
	
	/**
	 * 获取测试报表统计列表
	 * @param processInstanceId
	 * @return
	 */
	public List<Map<String,Object>> getTestReportList(long processInstanceId,String nodeIds,String nodeType){
//		String sql=" select g.name as model_name,e.name as chapter_name,a.name as section_name,"
//			+" c.total_score,d.score,d.accuracy_rate"
//			+" from asf_node a left outer join asf_node e on a.node_group_id=e.id"
//			+" left outer join asf_node g on e.node_group_id = g.id,"
//			+" asf_node_instance b, paper_assembling_policy c,current_test_status d"
//			+" where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id "
//			+" and a.node_type='"+nodeType+"' and b.process_instance_id=?";
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select f.name as m_name,e.name as c_name ,d.name,c.name as k_name,b.score,");
		sqlBuff.append(" b.total_score,b.right_rate from asf_node_instance a ");
		sqlBuff.append(" right outer join evaluating_answer_status b on a.id = b.node_instance_id");
		sqlBuff.append(" left outer join knowledge_point c on b.knowledge_point_code = c.code");
		sqlBuff.append(" left outer join asf_node d on a.node_id = d.id");
		sqlBuff.append(" left outer join asf_node e on d.node_group_id=e.id");
		sqlBuff.append(" left outer join asf_node f on e.node_group_id=f.id");
		sqlBuff.append(" where a.process_instance_id=? ");
		if(nodeIds != null && !nodeIds.equals("")){
			sqlBuff.append(" and d.id in("+nodeIds+") ");
		}
		sqlBuff.append(" order by f.id ,e.id,c.code,name ");

		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),processInstanceId);
		return list;
	}
	
	/**
	 * 获取前测和后测的统计
	 * @param processInstanceId
	 * @param nodeIds
	 * @param nodeType
	 * @return
	 */
	public List<Map<String,Object>> getOtherTestReportList(long processInstanceId,String nodeIds,String nodeType){
		String sql=" select h.name as model_name,g.name as chapter_name,e.name as section_name,a.name as current_name,"
			//+" k.name as next_chapter_name,j.name as next_section_name,i.name as next_name," 
			+" sum(c.items_num) sum_items,c.standard_answering_time,b.node_status,"
			+" c.total_score,d.score,d.accuracy_rate,d.start_time,d.mastery_rate from asf_node a"
			+" left outer join asf_node e on a.node_group_id=e.id"
			+" left outer join asf_node g on e.node_group_id = g.id"
			+" left outer join asf_node h on g.node_group_id = h.id,"
			//+" left outer join asf_node i on a.next_node_id = i.id"
			//+" left outer join asf_node j on i.node_group_id =j.id"
			//+" left outer join asf_node k on j.node_group_id =k.id, "
			+" asf_node_instance b,paper_assembling_policy c,current_test_status d"
			+" where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id"
			+" and a.node_type='"+nodeType+"' and b.process_instance_id=?";
		if(nodeIds != null && !nodeIds.equals("")){
			sql = sql + " and a.id in("+nodeIds+") ";
		}
		sql = sql + " GROUP by a.name,e.name";
		sql = sql + " order by a.order_num ";
		
		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sql,processInstanceId);
		return list;
	}
	
	/**
	 * 获取章的数量
	 * @param processInstanceId
	 * @param nodeIds
	 * @param nodeType
	 * @return
	 */
	public List<Map<String,Object>> getAmount(long processInstanceId,String nodeIds,String nodeType){
		String sql = "select g.name,count(g.name) amount"
			+ " from asf_node a"
			+ " left outer join asf_node e on a.node_group_id=e.id"
			+ " left outer join asf_node g on e.node_group_id = g.id ,"
			+ " asf_node_instance b, paper_assembling_policy c,current_test_status d "
			+ " where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id and a.node_type='"+nodeType+"' "
			+ " and b.process_instance_id=? ";
		if(nodeIds != null && !nodeIds.equals("")){
			sql = sql + " and a.id in("+nodeIds+") ";
		}
		sql = sql + "GROUP by g.name order by a.order_num ";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql, processInstanceId);
		return list;
	}
	
	/**
	 * 获取评测中章的数量
	 * @param processInstanceId
	 * @param nodeIds
	 * @param nodeType
	 * @return
	 */
	public List<Map<String,Object>> getTestAmount(long processInstanceId,String nodeIds,String nodeType){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select e.name ,count(e.name) amount");
		sqlBuff.append(" from asf_node_instance a");
		sqlBuff.append(" right outer join evaluating_answer_status b on a.id = b.node_instance_id ");
		sqlBuff.append(" left outer join knowledge_point c on b.knowledge_point_code = c.code");
		sqlBuff.append(" left outer join asf_node d on a.node_id = d.id ");
		sqlBuff.append(" left outer join asf_node e on d.node_group_id=e.id ");
		sqlBuff.append(" where a.process_instance_id=?");
		sqlBuff.append(" group by e.name");
		sqlBuff.append(" order by e.order_num");
		if(nodeIds != null && !nodeIds.equals("")){
			sqlBuff.append(" and a.id in("+nodeIds+") ");
		}
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		return list;
	}
	
	/**
	 * 获取节点ID字符串
	 * @param orderNum
	 * @return
	 */
	public String getNodeIds(long processInstanceId,String orderNum,String nodeType){
		String nodeIds = "";
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,a.name from asf_node a ");
		sqlBuff.append(" inner join `asf_process_instance` b on a.`process_definition_id` =b.`process_definition_id` ");
		sqlBuff.append(" where b.id=? and a.node_type='"+nodeType+"' and a.order_num like '"+orderNum+"%'");
		//sqlBuff.append(" where b.id=? and a.order_num like '"+orderNum+"%'");
		List<Map<String,Object>> nodeList = this.getJdbcTemplate().queryForList(sqlBuff.toString(),processInstanceId);
		for(int i=0;i<nodeList.size();i++){
			Map<String,Object> map = nodeList.get(i);
			if(i==0){
				nodeIds = (Long)map.get("id")+"";
			}else{
				nodeIds = nodeIds + "," + (Long)map.get("id");
			}
		}
		return nodeIds;
	}
	
	/**
	 * 获取历史统计数据
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public Map<String,Object> getHistoryStat(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(*) as amount,sum(time_used) as time_used ");
		sqlBuff.append(" from `history_test_status` a  ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node c on b.node_id = c.id ");
		sqlBuff.append(" where c.order_num='"+orderNum+"' and b.process_instance_id=? ");
		
		Map<String,Object> statMap = this.getJdbcTemplate().queryForMap(sqlBuff.toString(), processInstanceId);
		
		return statMap;
	}
	
	/**
	 * 获取要求的正确率
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public Double getAccuracyRate(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select right_rate_for_pass from training_policy a");
		sqlBuff.append(" inner join asf_node b on a.node_id=b.id");
		sqlBuff.append(" inner join asf_node_instance c on c.node_id = b.id ");
		sqlBuff.append(" where b.order_num='"+orderNum+"' and c.process_instance_id=?");
		
		Double accuracyRate = this.getJdbcTemplate().queryForObject(sqlBuff.toString(), Double.class, processInstanceId);
		
		return accuracyRate;
	}
	
	/**
	 * 获取通过时正确率和掌握度
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public Map<String,Object> getPassStat(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.passed_time,a.accuracy_rate,a.mastery_rate");
		sqlBuff.append(" from `current_test_status` a  ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node c on b.node_id = c.id ");
		sqlBuff.append(" where c.order_num='"+orderNum+"' and b.process_instance_id=?");
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		Map<String,Object> statMap = null;
		if(list.size()>0){
			statMap = list.get(0);
		}
	
		return statMap;
	}
	
	/**
	 * 历史数据统计
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public Map<String,Object> getHistoryAllStat(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select sum(sum_correct_items+sum_incorrect_items) as train_items,");
		sqlBuff.append(" sum(sum_correct_items) as sum_correct_items,sum(sum_incorrect_items) as sum_incorrect_items,");
		sqlBuff.append(" sum(sum_unfinished_items) as sum_unfinished_items,sum(unsure_mark_items) as unsure_mark_items,");
		sqlBuff.append(" sum(sum_five_star_items) as sum_five_star_itmes,sum(sum_four_star_items) as sum_four_star_items,");
		sqlBuff.append(" sum(sum_three_star_items) as sum_three_star_items,sum(sum_two_star_items) as sum_two_star_items,");
		sqlBuff.append(" sum(sum_one_star_items) as sum_one_star_items,sum(sum_half_star_items) as sum_half_star_items,");
		sqlBuff.append(" sum(sum_zero_star_items) as sum_zero_star_items");
		sqlBuff.append(" from `history_test_status` a  ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node c on b.node_id = c.id ");
		sqlBuff.append(" where c.order_num='"+orderNum+"' and b.process_instance_id=?");
	
		Map<String,Object> statMap = this.getJdbcTemplate().queryForMap(sqlBuff.toString(), processInstanceId);
	
		return statMap;
	}
	
	/**
	 * 获取历史数据列表
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public List<Map<String,Object>> getHistoryList(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,a.node_instance_id,sum_correct_items,sum_incorrect_items,sum_unfinished_items,unsure_mark_items,");
		sqlBuff.append(" require_accuracy_rate,accuracy_rate,mastery_rate,test_status");
		sqlBuff.append(" from history_test_status a ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node c on b.node_id = c.id ");
		sqlBuff.append(" where c.order_num='"+orderNum+"' and b.process_instance_id=? ");
		//sqlBuff.append(" order by a.id desc");
		List<Map<String,Object>> historyList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		return historyList;
	}
	
	/**
	 * 获取节点实例ID
	 * @param processInstanceId
	 * @param orderNum
	 * @return
	 */
	public Map<String,Object> getNodeInstanceId(Long processInstanceId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id from asf_node_instance a");
		sqlBuff.append(" inner join asf_node b on a.node_id = b.id ");
		sqlBuff.append(" where a.process_instance_id=? and b.order_num='"+orderNum+"'");
			
		Map<String,Object> nodeInstanceMap = null;
		List<Map<String,Object>> nodeInstanceList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		if(nodeInstanceList.size()>0){
			nodeInstanceMap = nodeInstanceList.get(0);
		}
		
		return nodeInstanceMap;
	}
	
}
