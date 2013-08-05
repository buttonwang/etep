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
public class AdviserService extends ReportBaseService{

	/**
	 *  获取节点对象MAP
	 * @return
	 */
	public Map getNodeMapById(String nodeId) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,a.name,a.node_type,a.order_num ");
		sqlBuff.append(" from asf_node a");
		sqlBuff.append(" WHERE id= ");
		sqlBuff.append(nodeId);
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		Map nodeMap = null;
		if(list.size()>0){
			nodeMap = (Map)list.get(0);
		}
		return nodeMap;
	}
	
	/**
	 *  获取节点对象MAP
	 * @return
	 */
	public Map getNodeMapByInstanceId(String nodeId) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select b.id,b.name,b.node_type,b.order_num ");
		sqlBuff.append(" from asf_node_instance a");
		sqlBuff.append(" inner JOIN asf_node b on a.node_id=b.id");
		sqlBuff.append(" WHERE a.id= ");
		sqlBuff.append(nodeId);
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		Map nodeMap = null;
		if(list.size()>0){
			nodeMap = (Map)list.get(0);
		}
		return nodeMap;
	}
	
	/**
	 *     select distinct a.id,a.name,a.node_type,a.order_num from asf_node a  
	inner join asf_node_instance b on b.node_id = a.id   
	inner join process_training_status d on b.process_instance_id = d.process_instance_id 
 	where d.class_num='100000000001' and (a.node_type='GROUP' or a.node_type='PRACTICE')
 	and INSTR(a.order_num,',')=0
	 * @return
	 */
	public List<Map> getNodeListByOrderNum(String ClassCode,String orderNum) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select distinct a.id,a.name,a.node_type,a.order_num from asf_node a ");
		sqlBuff.append(" inner join asf_node_instance b on b.node_id = a.id ");
		sqlBuff.append(" inner join process_training_status d on b.process_instance_id = d.process_instance_id ");
		sqlBuff.append(" where d.class_num='");
		sqlBuff.append(ClassCode);
		sqlBuff.append("' and (a.node_type='GROUP' or a.node_type='PRACTICE') ");
		sqlBuff.append(orderNum);
		sqlBuff.append(" order by a.order_num"); 
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * select distinct a.id,a.name,a.node_type from asf_node a  
	inner join asf_node_instance b on b.node_id = a.id   
	inner join process_training_status d on b.process_instance_id = d.process_instance_id 
 	where d.class_num='100000000001' and (a.node_type='GROUP' or a.node_type='PRACTICE')
 	order by a.order_num 
	 * @return
	 */
	public List<Map> getNodeList(String ClassCode) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select distinct a.id,a.name,a.node_type from asf_node a ");
		sqlBuff.append(" inner join asf_node_instance b on b.node_id = a.id ");
		sqlBuff.append(" inner join process_training_status d on b.process_instance_id = d.process_instance_id ");
		sqlBuff.append(" where d.class_num='");
		sqlBuff.append(ClassCode);
		sqlBuff.append("' and (a.node_type='GROUP' or a.node_type='PRACTICE')");
		sqlBuff.append(" order by a.order_num"); 
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * select d.login_name,d.real_name,e.name,a.test_status,a.score,a.mastery_rate,a.first_test_time,
	 	a.end_time,a.time_used,f.time_count
		from `current_test_status` a   
		 inner join asf_node_instance b on a.node_instance_id = b.id 
		inner join asf_node e on b.node_id = e.id 
		 inner join asf_process_instance c on b.process_instance_id = c.id 
		 inner join `webuser` d on c.actor=d.id
		 inner join 
		 (     
		 select sum(aa.time_used ) as time_count,ee.id as node_id,dd.id as user_id
		    from `history_test_status` aa   
		     inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		    inner join asf_node ee on bb.node_id = ee.id 
		     inner join asf_process_instance cc on bb.process_instance_id = cc.id 
		     inner join `webuser` dd on cc.actor=dd.id
		     group by ee.id,dd.id
		 ) f
		 on f.node_id = e.id and f.user_id = d.id
		 where e.id=14
	 * @return
	 */
	public List<Map> getStuUserListByNodeId(String nodeSql,String status) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select b.id as node_id,d.id as login_id,d.login_name,d.real_name,e.name,b.node_status,");
		sqlBuff.append(" a.score,a.mastery_rate,a.first_test_time,a.end_time,a.time_used,f.time_count ");
		sqlBuff.append(" from current_test_status a ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node e on b.node_id = e.id ");
		sqlBuff.append(" inner join asf_process_instance c on b.process_instance_id = c.id "); 
		sqlBuff.append(" inner join webuser d on c.actor=d.id "); 
		sqlBuff.append(" inner join (  "); 
		sqlBuff.append(" 	select sum(aa.time_used ) as time_count,ee.id as node_id,dd.id as user_id"); 
		sqlBuff.append(" 	from history_test_status aa "); 
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id "); 
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id  "); 
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id "); 
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id "); 
		if(status !=null && !status.equals("-1")){
			sqlBuff.append(" and bb.node_status= ");
			sqlBuff.append(status);
		}
		sqlBuff.append(" 	group by ee.id,dd.id "); 
		sqlBuff.append(" ) f on f.node_id = e.id and f.user_id = d.id"); 
		sqlBuff.append(nodeSql);
		if(status !=null && !status.equals("-1")){
			sqlBuff.append(" and b.node_status= ");
			sqlBuff.append(status);
		}
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * select d.login_name,d.real_name,e.name,a.test_status,a.score,a.mastery_rate,a.first_test_time,a.start_time,a.time_used,f.time_count
		from `current_test_status` a   
		 inner join asf_node_instance b on a.node_instance_id = b.id 
		inner join asf_node e on b.node_id = e.id 
		 inner join asf_process_instance c on b.process_instance_id = c.id 
		 inner join `webuser` d on c.actor=d.id
		 where e.id=14 and d.id = '1000000000005'
	 * @return
	 */
	public Map getStuUserByNodeAndUser(String nodeId,String userId) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select d.id as login_id,d.login_name,d.real_name,e.name,a.test_status,a.score,");
		sqlBuff.append(" a.mastery_rate,a.start_time,a.time_used,f.total_score,f.items_num,f.answering_time,");
		sqlBuff.append(" a.first_test_time,a.first_test_score,a.first_test_accuracy_rate,a.accuracy_rate,");
		sqlBuff.append(" a.passed_time,a.passed_score,a.passed_accuracy_rate,a.sum_correct_items,a.sum_incorrect_items, ");
		sqlBuff.append(" a.sum_unfinished_items,b.node_status,a.is_tested,h.answering_time as dyn_answering_time,");
		sqlBuff.append(" g.right_rate_for_pass,g.right_rate_retraining,g.retraining_right_rate_test_faile, ");
		sqlBuff.append(" a.sum_five_star_items,a.sum_four_star_items,a.sum_three_star_items,a.sum_two_star_items, ");
		sqlBuff.append(" a.sum_one_star_items,a.sum_half_star_items,a.sum_zero_star_items ");
		sqlBuff.append(" from current_test_status a ");
		sqlBuff.append(" inner join asf_node_instance b on a.node_instance_id = b.id ");
		sqlBuff.append(" inner join asf_node e on b.node_id = e.id ");
		sqlBuff.append(" inner join asf_process_instance c on b.process_instance_id = c.id "); 
		sqlBuff.append(" inner join webuser d on c.actor=d.id "); 
		sqlBuff.append(" inner join paper_assembling_policy f on b.node_id = f.node_id ");
		sqlBuff.append(" inner join training_policy g on b.node_id = g.node_id");
		sqlBuff.append(" left join dynamic_assembled_paper h on h.id = b.id");
		sqlBuff.append(" where b.id= ");
		sqlBuff.append(nodeId);
		sqlBuff.append(" and d.id ='");
		sqlBuff.append(userId);
		sqlBuff.append(" ' ");
		Map map=this.getJdbcTemplate().queryForMap(sqlBuff.toString(),new HashMap());
		return map;
	}
	
	/**
	 * 单元训练报表显示
	 * 
	select aaa.name,ifnull(ccc.total_count,0) as total_count,ifnull(ddd.pass_count,0) as pass_count,
	ifnull(eee.nopass_count,0) as nopass_count,aaa.total_time/total_count as avg_total_time,
	bbb.pass_time/pass_count as avg_pass_time,aaa.mastery_rate/total_count as avg_mastery_rate,
	aaa.star_count/total_count as avg_star_count
	from (
	       select ee.name,sum(a.time_used) as total_time,sum(a.mastery_rate) as mastery_rate,
	       sum(a.sum_half_star_items)*0.5 + sum(a.sum_one_star_items) + sum(a.sum_two_star_items)*2
	       + sum(a.sum_three_star_items)*3 + sum(a.sum_four_star_items)*4 
	       + sum(a.sum_five_star_items)*5 as star_count
			from current_test_status aa 
	        left join history_test_status a on aa.node_instance_id=a.node_instance_id 
			inner join asf_node_instance bb on aa.node_instance_id = bb.id 
			inner join asf_node ee on bb.node_id = ee.id  
			inner join asf_process_instance cc on bb.process_instance_id = cc.id  
			inner join webuser dd on cc.actor=dd.id
			inner join process_training_status c on cc.id = c.process_instance_id
			where c.class_num='100000000001'
			group by ee.name
	) aaa
	inner join (
	       select ee.name,sum(a.time_used) as pass_time
			from current_test_status aa 
	        left join history_test_status a on aa.node_instance_id=a.node_instance_id 
	        and aa.passed_time<=a.end_time
			inner join asf_node_instance bb on aa.node_instance_id = bb.id 
			inner join asf_node ee on bb.node_id = ee.id  
			inner join asf_process_instance cc on bb.process_instance_id = cc.id  
			inner join webuser dd on cc.actor=dd.id
			inner join process_training_status c on cc.id = c.process_instance_id
			where c.class_num='100000000001'
			group by ee.name
	) bbb on aaa.name=bbb.name
	left join (
			select ee.name,count(bb.node_status) as total_count
			from `current_test_status` aa 
			inner join asf_node_instance bb on aa.node_instance_id = bb.id 
			inner join asf_node ee on bb.node_id = ee.id  
			inner join asf_process_instance cc on bb.process_instance_id = cc.id  
			inner join webuser dd on cc.actor=dd.id
			inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
			where c.class_num='100000000001'
			group by ee.name
	) ccc on aaa.name = ccc.name
	left join (
			select ee.name,count(bb.node_status) as pass_count
			from `current_test_status` aa 
			inner join asf_node_instance bb on aa.node_instance_id = bb.id 
			inner join asf_node ee on bb.node_id = ee.id  
			inner join asf_process_instance cc on bb.process_instance_id = cc.id  
			inner join webuser dd on cc.actor=dd.id
			inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
			where c.class_num='100000000001' and bb.node_status='2'
			group by ee.name
	) ddd on aaa.name = ddd.name
	left join (
			select ee.name,count(bb.node_status) as pass_count
			from `current_test_status` aa 
			inner join asf_node_instance bb on aa.node_instance_id = bb.id 
			inner join asf_node ee on bb.node_id = ee.id  
			inner join asf_process_instance cc on bb.process_instance_id = cc.id  
			inner join webuser dd on cc.actor=dd.id
			inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
			where c.class_num='100000000001' and cc.node_id=ee.id
			group by ee.name
	) eee on aaa.name = eee.name
	 * @return
	 */
	public List getUnitTrainReport(String classCode,String order_num) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select ifnull(ccc.total_count,0) as total_count,ifnull(ddd.pass_count,0) as pass_count,");
		sqlBuff.append(" ifnull(eee.nopass_count,0) as nopass_count,aaa.total_time/total_count as avg_total_time,");
		sqlBuff.append(" bbb.pass_time/pass_count as avg_pass_time,aaa.mastery_rate/total_count as avg_mastery_rate,");
		sqlBuff.append(" aaa.star_count/total_count as avg_star_count");
		sqlBuff.append(" from (");
		sqlBuff.append(" select '"+order_num+"' as id,sum(a.time_used) as total_time,sum(a.mastery_rate) as mastery_rate,");
		sqlBuff.append(" sum(a.sum_half_star_items)*0.5 + sum(a.sum_one_star_items) + sum(a.sum_two_star_items)*2");
		sqlBuff.append(" + sum(a.sum_three_star_items)*3 + sum(a.sum_four_star_items)*4 ");
		sqlBuff.append(" + sum(a.sum_five_star_items)*5 as star_count");
		sqlBuff.append(" from current_test_status aa ");
		sqlBuff.append(" left join history_test_status a on aa.node_instance_id=a.node_instance_id ");
		sqlBuff.append(" inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" inner join asf_node ee on bb.node_id = ee.id  ");
		sqlBuff.append(" inner join asf_process_instance cc on bb.process_instance_id = cc.id  ");
		sqlBuff.append(" inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" inner join process_training_status c on cc.id = c.process_instance_id");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and (ee.node_type='GROUP' or ee.node_type='PRACTICE') ");
		sqlBuff.append(" and instr(ee.order_num,'"+order_num+"')=1");
		sqlBuff.append(" ) aaa");
		sqlBuff.append(" inner join (");
		sqlBuff.append(" select '"+order_num+"' as id,sum(a.time_used) as pass_time");
		sqlBuff.append(" from current_test_status aa ");
		sqlBuff.append(" left join history_test_status a on aa.node_instance_id=a.node_instance_id ");
		sqlBuff.append(" and aa.passed_time<=a.end_time");
		sqlBuff.append(" inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" inner join asf_node ee on bb.node_id = ee.id  ");
		sqlBuff.append(" inner join asf_process_instance cc on bb.process_instance_id = cc.id  ");
		sqlBuff.append(" inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" inner join process_training_status c on cc.id = c.process_instance_id");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and (ee.node_type='GROUP' or ee.node_type='PRACTICE') ");
		sqlBuff.append(" and instr(ee.order_num,'"+order_num+"')=1");
		sqlBuff.append(" ) bbb on aaa.id=bbb.id");
		sqlBuff.append(" left join (");
		sqlBuff.append(" select '"+order_num+"' as id,count(bb.node_status) as total_count");
		sqlBuff.append(" from `current_test_status` aa ");
		sqlBuff.append(" inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" inner join asf_node ee on bb.node_id = ee.id  ");
		sqlBuff.append(" inner join asf_process_instance cc on bb.process_instance_id = cc.id  ");
		sqlBuff.append(" inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and (ee.node_type='GROUP' or ee.node_type='PRACTICE') ");
		sqlBuff.append(" and instr(ee.order_num,'"+order_num+"')=1");
		sqlBuff.append(" ) ccc on aaa.id = ccc.id");
		sqlBuff.append(" left join (");
		sqlBuff.append(" select'"+order_num+"' as id,count(bb.node_status) as pass_count");
		sqlBuff.append(" from `current_test_status` aa ");
		sqlBuff.append(" inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" inner join asf_node ee on bb.node_id = ee.id  ");
		sqlBuff.append(" inner join asf_process_instance cc on bb.process_instance_id = cc.id  ");
		sqlBuff.append(" inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and bb.node_status='2'");
		sqlBuff.append("  and (ee.node_type='GROUP' or ee.node_type='PRACTICE') ");
		sqlBuff.append(" and instr(ee.order_num,'"+order_num+"')=1");
		sqlBuff.append(" ) ddd on aaa.id = ddd.id");
		sqlBuff.append(" left join (");
		sqlBuff.append(" select '"+order_num+"' as id,count(bb.node_status) as nopass_count");
		sqlBuff.append(" from `current_test_status` aa ");
		sqlBuff.append(" inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" inner join asf_node ee on bb.node_id = ee.id  ");
		sqlBuff.append(" inner join asf_process_instance cc on bb.process_instance_id = cc.id  ");
		sqlBuff.append(" inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and cc.node_id=ee.id");
		sqlBuff.append("  and (ee.node_type='GROUP' or ee.node_type='PRACTICE') ");
		sqlBuff.append(" and instr(ee.order_num,'"+order_num+"')=1");
		sqlBuff.append(" ) eee on aaa.id = eee.id");
		
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * 单元测试报表显示
	 * 
		select aaa.name,aaa.total_count,bbb.first_pass_count,ccc.pass_count,ddd.avg_score,
		ddd.max_score,ddd.min_score,ccc.avg_passed_score,ddd.avg_mastery_rate from (
		       select ee.name,count(bb.node_status) as total_count
		       from `current_test_status` aa 
		       inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		       inner join asf_node ee on bb.node_id = ee.id  
		       inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		       inner join webuser dd on cc.actor=dd.id
		       inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
		       where c.class_num='100000000001'
		       group by ee.name
		) aaa
		inner join (
		      select ee.name,count(bb.node_status) as first_pass_count
		      from `current_test_status` aa 
		      inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		      inner join asf_node ee on bb.node_id = ee.id  
		      inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		      inner join webuser dd on cc.actor=dd.id
		      inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
		      where c.class_num='100000000001' and aa.first_test_time=passed_time
		      group by ee.name
		) bbb on aaa.name = bbb.name
		inner join (
		      select ee.name,count(bb.node_status) as pass_count,avg(aa.passed_score) as avg_passed_score
		      from `current_test_status` aa 
		      inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		      inner join asf_node ee on bb.node_id = ee.id  
		      inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		      inner join webuser dd on cc.actor=dd.id
		      inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
		      where c.class_num='100000000001' and bb.node_status='2'
		      group by ee.name
		) ccc on aaa.name = ccc.name
		inner join (
		             select ee.name,avg(a.score) avg_score,max(a.score) max_score,min(a.score) min_score,avg(a.mastery_rate) avg_mastery_rate
		             from current_test_status aa  
		             left join history_test_status a on aa.node_instance_id=a.node_instance_id  
		             inner join asf_node_instance bb on aa.node_instance_id = bb.id  
		             inner join asf_node ee on bb.node_id = ee.id   
		             inner join asf_process_instance cc on bb.process_instance_id = cc.id   
		             inner join webuser dd on cc.actor=dd.id 
		             inner join process_training_status c on cc.id = c.process_instance_id 
		             where c.class_num='100000000001' and ee.node_type='UNITTEST'
		             group by ee.name,dd.id
		) ddd on aaa.name = ddd.name
	 * @return
	 */
	public List getUnitTestReport(String classCode) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select aaa.id,aaa.name,aaa.total_count,bbb.first_pass_count/aaa.total_count*100 as first_pass_rate,");
		sqlBuff.append(" ccc.pass_count,ddd.avg_score,ddd.max_score,ddd.min_score,ccc.avg_passed_score,ddd.avg_mastery_rate from (");
		sqlBuff.append(" 	select ee.id,ee.name,count(bb.node_status) as total_count");
		sqlBuff.append("    from `current_test_status` aa ");
		sqlBuff.append("	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' 	group by ee.name ");
		sqlBuff.append(" ) aaa");
		sqlBuff.append(" inner join (  ");
		sqlBuff.append(" 	select ee.name,count(bb.node_status) as first_pass_count");
		sqlBuff.append(" 	from `current_test_status` aa");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append("	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and aa.first_test_time=passed_time");
		sqlBuff.append(" 	group by ee.name");
		sqlBuff.append(" ) bbb on aaa.name = bbb.name");
		sqlBuff.append(" inner join (");
		sqlBuff.append(" 	select ee.name,count(bb.node_status) as pass_count,avg(aa.passed_score) as avg_passed_score");
		sqlBuff.append(" 	from `current_test_status` aa  ");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id ");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append("	inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and bb.node_status='2'");
		sqlBuff.append(" 	group by ee.name");
		sqlBuff.append(" ) ccc on aaa.name = ccc.name");
		sqlBuff.append(" inner join ( ");
		sqlBuff.append(" 	select ee.name,avg(a.score) avg_score,max(a.score) max_score,min(a.score) min_score,avg(a.mastery_rate) avg_mastery_rate");
		sqlBuff.append(" 	from current_test_status aa");
		sqlBuff.append(" 	left join history_test_status a on aa.node_instance_id=a.node_instance_id");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append("	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join process_training_status c on cc.id = c.process_instance_id");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and ee.node_type='UNITTEST'");
		sqlBuff.append(" 	group by ee.name,dd.id");
		sqlBuff.append(" ) ddd on aaa.name = ddd.name");
		
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * 阶段测试报表显示
	 * 
		select aaa.name,aaa.total_count,ifnull(bbb.nopass_count,0) as nopass_count,ccc.avg_time,
		ddd.avg_score,ddd.avg_accuracy_rate
		from (
		     select ee.name,count(bb.node_status) as total_count
		     from `current_test_status` aa 
		     inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		     inner join asf_node ee on bb.node_id = ee.id  
		     inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		     inner join webuser dd on cc.actor=dd.id
		     inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
		     where c.class_num='100000000001'
		     group by ee.name	
		) aaa
		left join (
		      select ee.name,count(bb.node_status) as nopass_count
		      from `current_test_status` aa 
		      inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		      inner join asf_node ee on bb.node_id = ee.id  
		      inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		      inner join webuser dd on cc.actor=dd.id
		      inner join `process_training_status` c on cc.`id` = c.`process_instance_id`
		      where c.class_num='100000000001' and cc.node_id=ee.id
		      group by ee.name
		) bbb on aaa.name = bbb.name
		inner join (
		      select ee.name,avg(a.time_used) avg_time
		      from current_test_status aa  
		      left join history_test_status a on aa.node_instance_id=a.node_instance_id  
		      inner join asf_node_instance bb on aa.node_instance_id = bb.id  
		      inner join asf_node ee on bb.node_id = ee.id   
		      inner join asf_process_instance cc on bb.process_instance_id = cc.id   
		      inner join webuser dd on cc.actor=dd.id 
		      inner join process_training_status c on cc.id = c.process_instance_id 
		      where c.class_num='100000000001' and ee.node_type='PHASETEST'
		      group by ee.name,dd.id
		)ccc on aaa.name = ccc.name
		inner join (
		      select ee.name,avg(a.score) avg_score,avg(a.accuracy_rate) avg_accuracy_rate
		      from current_test_status aa 
		      left join history_test_status a on aa.node_instance_id=a.node_instance_id 
		      inner join asf_node_instance bb on aa.node_instance_id = bb.id 
		      inner join asf_node ee on bb.node_id = ee.id  
		      inner join asf_process_instance cc on bb.process_instance_id = cc.id  
		      inner join webuser dd on cc.actor=dd.id
		      inner join process_training_status c on cc.id = c.process_instance_id
		      where c.class_num='100000000001' and ee.node_type='PHASETEST'
		      group by ee.name,dd.id
		)ddd on aaa.name = ddd.name	
	 * @return
	 */
	public List getStageTestReport(String classCode,String nodeType) {

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select aaa.id,aaa.name,aaa.total_count,ifnull(bbb.nopass_count,0) as nopass_count,ccc.avg_time,");
		sqlBuff.append(" ddd.avg_score,ddd.avg_accuracy_rate");
		sqlBuff.append(" from (");
		sqlBuff.append("    select ee.id,ee.name,count(bb.node_status) as total_count");
		sqlBuff.append("	from `current_test_status` aa");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append("	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' 	group by ee.name ");
		sqlBuff.append(" ) aaa");
		sqlBuff.append(" left join (  ");
		sqlBuff.append(" 	select ee.name,count(bb.node_status) as nopass_count");
		sqlBuff.append(" 	from `current_test_status` aa");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append("	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join `process_training_status` c on cc.`id` = c.`process_instance_id`");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and cc.node_id=ee.id");
		sqlBuff.append(" 	group by ee.name");
		sqlBuff.append(" ) bbb on aaa.name = bbb.name");
		sqlBuff.append(" inner join (");
		sqlBuff.append(" 	select ee.name,avg(a.time_used) avg_time");
		sqlBuff.append(" 	from `current_test_status` aa  ");
		sqlBuff.append(" 	left join history_test_status a on aa.node_instance_id=a.node_instance_id ");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id ");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append(" 	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append("	inner join process_training_status c on cc.id = c.process_instance_id");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and ee.node_type='");
		sqlBuff.append(nodeType);
		sqlBuff.append("' 	group by ee.name,dd.id");
		sqlBuff.append(" ) ccc on aaa.name = ccc.name");
		sqlBuff.append(" inner join ( ");
		sqlBuff.append(" 	select ee.name,avg(a.score) avg_score,avg(a.accuracy_rate) avg_accuracy_rate");
		sqlBuff.append(" 	from current_test_status aa");
		sqlBuff.append(" 	left join history_test_status a on aa.node_instance_id=a.node_instance_id");
		sqlBuff.append(" 	inner join asf_node_instance bb on aa.node_instance_id = bb.id");
		sqlBuff.append(" 	inner join asf_node ee on bb.node_id = ee.id");
		sqlBuff.append(" 	inner join asf_process_instance cc on bb.process_instance_id = cc.id");
		sqlBuff.append("	inner join webuser dd on cc.actor=dd.id");
		sqlBuff.append(" 	inner join process_training_status c on cc.id = c.process_instance_id");
		sqlBuff.append(" 	where c.class_num='");
		sqlBuff.append(classCode);
		sqlBuff.append("' and ee.node_type='");
		sqlBuff.append(nodeType);
		sqlBuff.append("' 	group by ee.name,dd.id");
		sqlBuff.append(" ) ddd on aaa.name = ddd.name");
		
		//System.out.println(sqlBuff.toString());
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
}
