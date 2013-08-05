/**
 * 
 */
package com.ambow.trainingengine.report.service.adviser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.ReportBaseService;

/**
 * @author yuanjunqi
 *
 */
public class UserAnswerService extends ReportBaseService{

	/**
	 * 获取用户学习记录
	 * @param pageNo
	 * @param userId
	 * @param size
	 * @return
	 */
	public List<Map> getRecordByUser(int pageNo,String userId,int size) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select d.id,a.start_time,a.end_time,a.time_used,d.login_name,e.name from history_test_status a ");  
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node e on b.node_id = e.id ");
		sqlBuff.append(" inner join asf_process_instance c on b.process_instance_id = c.id ");
		sqlBuff.append(" inner join `webuser` d on c.actor=d.id ");
		sqlBuff.append(" where c.actor = ");
		sqlBuff.append(userId);
		sqlBuff.append(" order by start_time desc limit ");
		sqlBuff.append((pageNo-1)*size);
		sqlBuff.append(",");
		sqlBuff.append(size);
		
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	public List<Map> getRecordByUserAndNode(String userId,String nodeId) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.accuracy_rate,a.mastery_rate,a.sum_incorrect_items,a.sum_unfinished_items,");
		sqlBuff.append(" a.sum_five_star_items,a.sum_four_star_items,a.sum_three_star_items,a.sum_two_star_items, ");
		sqlBuff.append(" a.sum_one_star_items,a.sum_half_star_items,a.sum_zero_star_items ");
		sqlBuff.append(" from history_test_status a ");  
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node e on b.node_id = e.id ");
		sqlBuff.append(" inner join asf_process_instance c on b.process_instance_id = c.id ");
		sqlBuff.append(" inner join `webuser` d on c.actor=d.id ");
		sqlBuff.append(" where c.actor = ");
		sqlBuff.append(userId);
		sqlBuff.append(" and b.id=");
		sqlBuff.append(nodeId);
		sqlBuff.append(" order by a.start_time desc");
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	public Integer getRecordCountByUser(String userId) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(d.id) from history_test_status a ");  
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node e on b.node_id = e.id ");
		sqlBuff.append(" inner join asf_process_instance c on b.process_instance_id = c.id ");
		sqlBuff.append(" inner join `webuser` d on c.actor=d.id ");
		sqlBuff.append(" where c.actor = ");
		sqlBuff.append(userId);
		
		Integer amount=this.getJdbcTemplate().queryForInt(sqlBuff.toString(),new HashMap());
		return amount;
	}
}
