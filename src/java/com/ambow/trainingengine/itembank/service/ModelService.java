/**
 * 
 */
package com.ambow.trainingengine.itembank.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Model;
import com.ambow.trainingengine.itembank.domain.Subject;

/**
 * @author yuanjunqi
 *
 */
public class ModelService extends HibernateEntityDao<Model>{
	
	private SimpleJdbcTemplate jdbcTemplate ;

	public Model queryForObject(Integer id) {
		return this.get(id);
	}
	
	
	public void deleteAll() {
		this.excuteHql("delete from Model");
	}
	
	/**
	 * 获取模块列表
	 * @param subject
	 * @param grade
	 * @return
	 */
	public List<Model> queryForList(String subject,String grade){
		String hsql = "from Model a where a.subject.code=? and a.grade.code=?";
		List<Model> modelList = this.find(hsql, subject,grade);
		return modelList;
	}
	
	/**
	 * 增加模块
	 * @param name
	 * @param subject
	 * @param grade
	 */
	public void add(String name,String subject,String grade){
		String sql = "insert into model(name,subject_code,grade_code) values(?,?,?)";
		this.getJdbcTemplate().update(sql, name,subject,grade);
	}
	
	/**
	 * 更新模块
	 * @param name
	 * @param subject
	 * @param grade
	 * @param id
	 */
	public void update(String name,String subject,String grade,Integer id){
		String sql = "update model set name=?,subject_code=?,grade_code=? where id=?";
		this.getJdbcTemplate().update(sql, name,subject,grade,id);
	}
	
	/**
	 * 获取最后插入的ID
	 * @return
	 */
	public Integer getLastId(){
		String sql = "SELECT LAST_INSERT_ID()";
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	/**
	 * 保存
	 * @param name
	 * @param subject
	 * @param grade
	 * @param knowledgePoints
	 */
	public void save(Integer id,String name,String subject,String grade,String[] knowledgePoints){
		Model model = new Model();
		if(id == null){
			model = new Model();
		}else{
			model = get(id);
		}
		model.setName(name);
		model.setSubject(get(Subject.class,subject));
		model.setGrade(get(Grade.class,grade));
		if(knowledgePoints !=null){
			Set<KnowledgePoint> set = new HashSet<KnowledgePoint>();
			for(int i=0;i<knowledgePoints.length;i++){
				KnowledgePoint knowledgePoint = get(KnowledgePoint.class,knowledgePoints[i].trim());
				if(knowledgePoint.getParentKnowledgePoint() != null ){
					set.add(get(KnowledgePoint.class,knowledgePoints[i].trim()));
				}
			}
			model.setKnowledgePoints(set);
		}
		this.save(model);
	}
	
	/**
	 * 删除对象
	 * @param id
	 */
	public void del(Integer id){
		String hsql = "delete from Model where id=?";
		this.excuteHql(hsql, id);
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
