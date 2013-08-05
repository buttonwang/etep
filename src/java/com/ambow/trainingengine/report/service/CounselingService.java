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
public class CounselingService extends ReportBaseService{
	
	/**
	 * 获取所有训练数据的列表
	 * @param actor
	 * @param categoryId
	 * @return
	 */
	public List<Map<String,Object>> queryTestList(String actor,Integer categoryId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.actor,d.id as node_id,d.name as node_name,d.order_num,d.node_type,c.node_status, ");
		sqlBuff.append(" e.sum_five_star_items,e.sum_four_star_items,e.sum_three_star_items,e.sum_two_star_items, ");
		sqlBuff.append(" e.sum_one_star_items,e.sum_half_star_items,e.sum_zero_star_items,e.score,e.total_score, ");
		sqlBuff.append("  f.total_score as total_score1,e.sum_incorrect_items,e.unsure_mark_items ");
		sqlBuff.append(" from asf_process_instance a ");
		sqlBuff.append(" left join asf_process_definition b on a.process_definition_id=b.id ");
		sqlBuff.append(" left join asf_node_instance c on c.process_instance_id=a.id ");
		sqlBuff.append(" left join asf_node d on c.node_id = d.id ");
		sqlBuff.append(" left join current_test_status e on c.id=e.node_instance_id ");
		sqlBuff.append(" left join paper_assembling_policy f on d.id=f.node_id ");
		sqlBuff.append(" where a.actor in("+actor+") ");
		sqlBuff.append(" and b.category_id="+categoryId);
		sqlBuff.append(" order by a.actor,a.id,d.order_num ");
		List<Map<String,Object>> testList = this.getJdbcTemplate().queryForList(sqlBuff.toString());
		return testList;
	}
	
	/**
	 * 根据学生的Id、流程分类、节点属性、试题属性获取所做试题的编号。 
	 * statType: 统计类型。 5：5星题 4：4星题 3：3星题  10：错题 11：疑问题
	 * @return 试题编号 
	 */
	public String queryItemsByNodeAndType(String actor,Integer categoryId, Integer statType, Integer nodeId) {
		String sql = " select distinct f.item_id " +
				" from asf_process_instance a " +
				" left join asf_process_definition b on a.process_definition_id=b.id " +
				" left join asf_node_instance c on c.process_instance_id=a.id " +
				" left join asf_node d on c.node_id = d.id " +
				" left join current_answers_status f on c.id = f.node_instance_id " +
				" left join asf_node g on d.node_group_id = g.id" +
				" where a.actor in ("+ actor +")  " +
				" and d.node_type = 'PRACTICE'" +
				" and b.category_id=?" +
				" and f.star_grade= ? " +
				" and ( d.node_group_id=?  or g.node_group_id=? )";
		
		if (statType==0)  {sql = sql.replace("f.star_grade=", "f.star_grade<>"); statType=99999;}
		if (statType==10) {sql = sql.replace("star_grade", "is_correct"); statType=0;}
		if (statType==11) {sql = sql.replace("star_grade", "is_unsure_marking"); statType=1;}
		if (nodeId==0)  {sql = sql.replace("d.node_group_id=?  or g.node_group_id=?", "d.node_group_id<>?  or g.node_group_id<>?");}
		List<Map<String,Object>> queryList = this.getJdbcTemplate().queryForList(sql, categoryId, statType, nodeId, nodeId);
		String items ="";
		for(Map<String,Object> map: queryList) {
			items += ((Integer)map.get("item_id")).toString() + ",";
		}
		items = items.replaceFirst(",$", "");
		System.out.println(items);
		return items;
	}
	
	/**
	 * 根据学生的Id、流程分类、节点属性、试题编号获取所做做某道试题的学生。 
	 * statType: 统计类型。 5：5星题 4：4星题 3：3星题  10：错题 11：疑问题
	 * @return 试题编号 
	 */
	public String queryActorByNodeAndType(String actor,Integer categoryId, Integer statType, Integer nodeId, Integer itemId) {
		String sql = " select distinct h.real_name " +
				" from asf_process_instance a " +
				" left join asf_process_definition b on a.process_definition_id=b.id " +
				" left join asf_node_instance c on c.process_instance_id=a.id " +
				" left join asf_node d on c.node_id = d.id " +
				" left join current_answers_status f on c.id = f.node_instance_id " +
				" left join asf_node g on d.node_group_id = g.id" +
				" left join webuser  h on a.actor = h.id" +
				" where a.actor in ("+ actor +")  " +
				" and d.node_type = 'PRACTICE'" +
				" and b.category_id=?" +
				" and f.star_grade= ? " +
				" and ( d.node_group_id=?  or g.node_group_id=? )" +
				" and f.item_id = ? ";
		if (statType==0)  {sql = sql.replace("f.star_grade=", "f.star_grade<>"); statType=99999;}
		if (statType==10) {sql = sql.replace("star_grade", "is_correct"); statType=0;}
		if (statType==11) {sql = sql.replace("star_grade", "is_unsure_marking"); statType=1;}
		if (nodeId==0)  {sql = sql.replace("d.node_group_id=?  or g.node_group_id=?", "d.node_group_id<>?  or g.node_group_id<>?");}
		List<Map<String,Object>> queryList = this.getJdbcTemplate().queryForList(sql, categoryId, statType, nodeId, nodeId, itemId);
		String actores ="";
		for(Map<String,Object> map: queryList) {
			actores += (String)map.get("real_name") + ",";
		}
		actores = actores.replaceFirst(",$", "");
		System.out.println(actores);
		return actores;
	}
}
