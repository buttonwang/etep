package com.ambow.trainingengine.studyguide.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;

/*
 * ShowStudyGuideService.java
 * 
 * Created on 2009-8-3 下午05:43:51
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

public class ShowStudyGuideService extends HibernateGenericDao {

	private SimpleJdbcTemplate jdbcTemplate;

	/**
	 * 获取节点列表(用于创建菜单树)
	 * 
	 * @param processInstanceId
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StudyGuide> getStudyGuideListFromProcessDefinitionId(long processDefinitionId) {
		String hql = "select studyGuideCodes from ProcessPolicy where processId = ?";
		String ids = (String)this.findObjByHql(hql, processDefinitionId);
		if ((ids == null) || (ids.trim().equals(""))) return null;
		String hql2 = "from StudyGuide where id in (" + ids + ") order by code";
		List<StudyGuide> list = this.find(hql2);
		return list;
	}

	/**
	 * 最出上一个，下一个内容
	 * 
	 * @author yangdawei
	 * @param processDefinitionId
	 * @param nodeId
	 * @param nodeGroupId
	 * @return
	 */
	public Map<String, Object> getTreePage(long processDefinitionId,
			int nodeId, Long nodeGroupId) {
		String sql = "select id, name, node_group_id, order_num "
				+ " from asf_node "
				+ " where process_definition_id = ? and node_type = 'GROUP' and node_group_id =?  order by order_num  ";
		Long nextId = null;
		Long nextGroupId = null;
		Long previousId = null;
		Long previousGroupId = null;
		String nodeName = null;
		String nextName = null;
		String perviousName = null;
		String orderNum = null;
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql,
				processDefinitionId, nodeGroupId);
		Map<String, Object> pagemap = new HashMap<String, Object>();
		for (int i = 0; i < mapList.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) mapList.get(i);
			Long id = (Long) map.get("id");
			if (nodeId == id) {
				map = (Map<String, Object>) mapList.get(i);
				nodeName = (String) map.get("name");
				orderNum = (String) map.get("order_num");
				if (mapList.size() == 1) {
					break;
				} else if (i == 0) {
					map = (Map<String, Object>) mapList.get(i + 1);
					nextId = (Long) map.get("id");
					nextGroupId = (Long) map.get("node_group_id");
					nextName = (String) map.get("name");
					previousId = null;

				} else if (i == mapList.size() - 1) {
					nextId = null;

					map = (Map<String, Object>) mapList.get(i - 1);
					previousId = (Long) map.get("id");
					perviousName = (String) map.get("name");
					previousGroupId = (Long) map.get("node_group_id");
				} else {
					map = (Map<String, Object>) mapList.get(i + 1);
					nextId = (Long) map.get("id");
					nextGroupId = (Long) map.get("node_group_id");
					nextName = (String) map.get("name");

					map = (Map<String, Object>) mapList.get(i - 1);
					previousId = (Long) map.get("id");
					previousGroupId = (Long) map.get("node_group_id");
					perviousName = (String) map.get("name");
				}

			}
		}
		if (orderNum == null) {
			orderNum = "";
		}
		int treefloor = (orderNum.split(",")).length;
		pagemap.put("nextId", nextId);
		pagemap.put("nextGroupId", nextGroupId);
		pagemap.put("previousId", previousId);
		pagemap.put("previousGroupId", previousGroupId);
		pagemap.put("perviousName", perviousName);
		pagemap.put("nextName", nextName);
		pagemap.put("nodeName", nodeName);
		pagemap.put("treefloor", treefloor);
		return pagemap;

	}

	/**
	 * 返回字符串 'valeu1','value2',....
	 * 
	 * @author yangdawei
	 * @param lds
	 * @param key
	 * @return
	 */
	private String transferStr(List<Map<String, Object>> lds, String key) {
		String str = "";
		for (Map<String, Object> map : lds) {
			str += map.get(key) + ",";
		}
		if (lds.size() >= 1) {
			str = str.substring(0, str.length() - 1);
		}
		str = "'" + str.replaceAll(",", "','") + "'";
		return str;
	}

	/**
	 * 当前知识点内容
	 * 
	 * @param nodeId
	 * @param processDefinitionId
	 * @param nodeGroupId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getChapterDesc(int nodeId,
			long processDefinitionId, Long nodeGroupId, String nodeType) {

		String sql = "select par.knowledge_point_code from paper_assembling_requirements par,asf_node an "
				+ "where par.node_id=an.id and an.process_definition_id=? and an.node_group_id=? and an.node_type like ? group by par.knowledge_point_code";
		String str = "";
		List<Map<String, Object>> lds = jdbcTemplate.queryForList(sql,
				processDefinitionId, nodeId, nodeType);
		str = this.transferStr(lds, "knowledge_point_code");

		  if (nodeType.equals("EVALUATE")) {
			// 当章下没有前后测时，遍历所有节下对应的知识点
			if (lds == null || lds.size() == 0) {
				sql = "select par.knowledge_point_code from paper_assembling_requirements par,asf_node an"
						+ " where  par.node_id=an.id and an.process_definition_id=? and an.node_group_id in( select id from  asf_node where  node_group_id=?) group by par.knowledge_point_code;";
				List<Map<String, Object>> allCode = jdbcTemplate.queryForList(
						sql, processDefinitionId, nodeId);
				str = this.transferStr(allCode, "knowledge_point_code");

			}

			sql = "select parent_code from knowledge_point where code in("
					+ str + ") group by parent_code ";
			List<Map<String, Object>> codes = jdbcTemplate.queryForList(sql);
			str = "";
			str = this.transferStr(codes, "parent_code");
		} else if (nodeType.equals("%")) {
			//   节下对应的知识点
			sql = "select par.knowledge_point_code from paper_assembling_requirements par,asf_node an "
					+ "where par.node_id=an.id and an.process_definition_id=? and an.node_group_id=?  group by par.knowledge_point_code";

			lds = jdbcTemplate.queryForList(sql, processDefinitionId, nodeId);
			str = this.transferStr(lds, "knowledge_point_code");
			sql="select name,code from knowledge_point where code in("+str+")";
			
//			sql = "select an.name,an.id from knowledge_point kp,paper_assembling_requirements par,asf_node an "
//					+ "where  par.node_id=an.id and an.process_definition_id=? and an.node_group_id=? group by an.name order by an.order_num   ";
			lds = jdbcTemplate.queryForList(sql);
		} else {
			//从知识点直接进入
			 str=nodeType;
			 lds=null;
		}

		sql = "select * from study_guide where kp_code in(" + str
				+ ") and status<>-1";
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		HashMap map=new HashMap();
		map.put("other", lds);//节下的训练
		map.put("thisknow", list);//当前节的知道点
		return map;
	}

	/**
	 * 取该章下的节
	 * 
	 * @author yangdawei
	 * @param processDefinitionId
	 * @param nodeId
	 * @return
	 */
	public List<Map<String, Object>> getSections(long processDefinitionId,
			int nodeId) {
		String sql = "select id, name, node_group_id, order_num "
				+ " from asf_node "
				+ " where process_definition_id = ? and node_type = 'GROUP' and node_group_id =?  order by order_num  ";
		List<Map<String, Object>> ls = jdbcTemplate.queryForList(sql,
				processDefinitionId, nodeId);
		return ls;
	}

	public List<Map<String, Object>> getSubMenu(int nodeId,
			long processDefinitionId) {

		String sql = "select id, name, node_group_id, order_num "
				+ " from asf_node "
				+ " where process_definition_id = ?   and node_type = 'GROUP'  and node_group_id =?  order by order_num  ";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql,
				processDefinitionId, nodeId);

		return lists;

	}

	public List getTree(int nodeId, long processDefinitionId) {
		List lstmp = new ArrayList();
		List<Map<String, Object>> list = this.getSubMenu(nodeId,
				processDefinitionId);
		for (Map map : list) {
			Map mapTmp = new HashMap();

			int id = ((Long) map.get("id")).intValue();
			List<Map<String, Object>> subls = this.getSubMenu(id,
					processDefinitionId);
			mapTmp.put("map", map);
			mapTmp.put("list", subls);
			lstmp.add(mapTmp);

		}
		return lstmp;
	}

	public List getUserCurrentNode(String actor, long processDefinitionId) {
		String sql = "select node.* from asf_node_instance an ,asf_node node " +
					 " where an.node_id=node.id  and node_type = 'GROUP' " +
					 " and an.process_instance_id in" +
						" (select id from asf_process_instance" +
						" where actor=? and process_definition_id=?)" +
						" order by order_num limit 4";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, actor,
				processDefinitionId);

		return lists;
	}
	
	public List getUserCurrentNode(long processInstanceId, long processDefinitionId) {
		String sql = " select node.id, concat(sg.name, '[', ng.name, '—', node.name, ']') as name, " +
					 " trim(substring_index(tp.study_guide, ',', 1)) as study_guide " +
					 " from asf_node_instance an inner join asf_node node on an.node_id = node.id " +
					 " inner join asf_node ng on node.node_group_id = ng.id " +
					 " inner join training_policy tp on node.id = tp.node_id " +
					 " inner join study_guide sg on sg.id = trim(substring_index(tp.study_guide, ',', 1)) " +
					 " where an.process_instance_id = ? " +
					 " and an.node_status > 0" +
					 " and node.node_type <> 'GROUP' " +
					 " order by node.order_num desc limit 4";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, processInstanceId);

		if (lists.size()==0) {
			sql = "select study_guide from process_policy where process_id = ? ";
			String sgId = (String) jdbcTemplate.queryForMap(sql, processDefinitionId).get("study_guide");
			if (sgId == null) sgId = "";
			if (!sgId.trim().equals("")) {
				sql = " select name, id as study_guide " +
					  " from study_guide where id in ( " + sgId + " ) order by code limit 4 ";
				lists = jdbcTemplate.queryForList(sql);
			}
		}
		return lists;
	}

	public String getItemIdsByKpCodes(String kpCodes) {
		String r = "";
		String sql = "select t.item_id from typical_example t inner join knowledge_point_item_ref k on t.item_id=k.item_id "
				+ "where k.knowledge_point_code in ( " + kpCodes + ")";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : lists) {
			r += map.get("item_id") + ",";
		}
		r = r.replaceFirst(",$", "");
		return r;
	}
	
	public String getkpCodes(long processDefinitionId) {
		String sql = "select par.knowledge_point_code " +
				"from paper_assembling_requirements par,asf_node an " +
				"where par.node_id=an.id and an.process_definition_id=? group by par.knowledge_point_code";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql, processDefinitionId);

		String str = this.transferStr(lists, "knowledge_point_code");
		return str;
	}
	
	public List getParentStudyGuide(String kpCodes) {
		String sql = "select distinct parent_code from knowledge_point where code in (" + kpCodes + ") and parent_code is not null";
		List<Map<String, Object>> lists = jdbcTemplate.queryForList(sql);
		String parentKpCodes = this.transferStr(lists, "parent_code");
		
		sql = "select * from study_guide where kp_code in(" + parentKpCodes + ") and status<>-1";
		List<Map<String, Object>> studyGuideList = jdbcTemplate.queryForList(sql);
		return studyGuideList;
	}
	
	public List getStudyGuide(String kpCode) {
		String sql = "select * from study_guide where kp_code = '" + kpCode + "' and status<>-1";
		List<Map<String, Object>> studyGuideList = jdbcTemplate.queryForList(sql);
		return studyGuideList;
	}

	public List getKnowlegerPointByCode(String kpCodes) {
		String sql = "select code, name from knowledge_point where code in (" + kpCodes + ") and parent_code is not null";
		List<Map<String, Object>> kpList = jdbcTemplate.queryForList(sql);
		return kpList;
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
