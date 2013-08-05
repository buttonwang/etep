/**
 * 
 */
package com.ambow.trainingengine.report.service;

import java.util.List;
import java.util.Map;

/**
 * @author yuanjunqi
 *
 */
public class ConsolidateAdviserService extends ReportBaseService{
	
	/**
	 * 获取流程实例列表
	 * @param actor
	 * @return
	 */
	public List<Map<String,Object>> getProcessList(String actor,Integer categoryId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,b.learning_process_rate,c.name ");
		sqlBuff.append(" from asf_process_instance a ");
		sqlBuff.append(" left join asf_process_definition c on a.process_definition_id=c.id ");
		sqlBuff.append(" left join process_training_status b on a.id=b.process_instance_id ");
		sqlBuff.append(" where a.actor=? and c.category_id=?");
		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), actor, categoryId);
		
		return mapList;
	}
	
	/**
	 * 获取节点实例列表
	 * @param processInstanceId
	 * @return
	 */
	public List<Map<String,Object>> getNodeList(Long processInstanceId){

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select c.id,f.name as m_name,e.name c_name,d.name n_name,c.name,");
		sqlBuff.append(" sum_correct_items+sum_incorrect_items as sum_items,sum_incorrect_items,");
		sqlBuff.append(" sum_five_star_items,sum_four_star_items,sum_three_star_items,sum_two_star_items,");
		sqlBuff.append(" sum_one_star_items,sum_half_star_items,sum_zero_star_items,score,total_score,c.node_type ");
		sqlBuff.append(" from asf_node_instance a ");
		sqlBuff.append(" left join current_test_status b on a.id=b.node_instance_id ");
		sqlBuff.append(" left join asf_node c on a.node_id = c.id");
		sqlBuff.append(" left outer join asf_node d on c.node_group_id=d.id");
		sqlBuff.append(" left outer join asf_node e on d.node_group_id=e.id");
		sqlBuff.append(" left outer join asf_node f on e.node_group_id=f.id");
		sqlBuff.append(" where a.process_instance_id=? and a.node_status !=0 and a.node_status !=3 ");
		sqlBuff.append(" and (c.node_type='PRACTICE' or c.node_type='PHASETEST' or c.node_type='UNITTEST' or c.node_type='EVALUATE')");

		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		
		return mapList;
	}
}
