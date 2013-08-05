/**
 * 
 */
package com.ambow.trainingengine.itembank.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author yuanjunqi
 *
 */
public class ModelKnowledgePointService {
	private SimpleJdbcTemplate jdbcTemplate ;
	
	/**
	 * 增加
	 * @param modelId
	 * @param knowledgePointCode
	 */
	public void add(Integer modelId,String knowledgePointCode){
		String sql = "insert into model_knowledge_point_ref(model_id,knowledge_point_code) values(?,?)";
		//System.out.println(sql);
		//System.out.println(modelId +" "+knowledgePointCode);
		this.getJdbcTemplate().update(sql, modelId,knowledgePointCode);
	}
	
	/**
	 * 删除
	 * @param modelId
	 */
	public void delete(Integer modelId){
		String sql = "delete from model_knowledge_point_ref where model_id=?";
		this.getJdbcTemplate().update(sql, modelId);
	}
	
	/**
	 * 删除
	 * @param modelId
	 */
	public void deleteAll(){
		String sql = "delete from model_knowledge_point_ref";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 获取知识点列表
	 * @param modelId
	 * @return
	 */
	public List<Map<String,Object>> getList(Integer modelId){
		String sql = "select knowledge_point_code  from model_knowledge_point_ref where model_id=?";
		//System.out.println("sql="+sql);
		List<Map<String,Object>> codeList = this.jdbcTemplate.queryForList(sql, modelId);
		return codeList;
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
