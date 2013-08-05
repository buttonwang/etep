/**
 * 
 */
package com.ambow.trainingengine.itembank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * @author yuanjunqi
 *
 */
public class ItemDuplicateService {

	private SimpleJdbcTemplate jdbcTemplate ;

	/**
	 * 保存记录
	 * @param data
	 */
	public void save(Object[] data){
		String sql = "insert into item_duplicate(item1,item1_code,item2,item2_code,sim,subject_code,grade_code,type_code) values(?,?,?,?,?,?,?,?)";
		this.getJdbcTemplate().update(sql, data);
	}
	
	/**
	 * 查询记录
	 * @return
	 */
	public List<Map<String,Object>> queryForList(int pageNo,int size,String subjectCode,String gradeCode,String typeCode){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select id,item1,item1_code,item2,item2_code,sim,subject_code,grade_code,type_code from item_duplicate ");
		if(subjectCode != null){
			sqlBuff.append("where subject_code='"+subjectCode+"' ");
		}
		if(gradeCode != null){
			sqlBuff.append("and grade_code='"+gradeCode+"' ");
		}
		if(typeCode != null){
			sqlBuff.append("and type_code='"+typeCode+"' ");
		}
		sqlBuff.append("order by subject_code asc,type_code asc,sim desc limit ");
		sqlBuff.append((pageNo-1)*size);
		sqlBuff.append(",");
		sqlBuff.append(size);
		List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap<String,Object>());
		return list;
	}
	
	/**
	 * 获取记录数
	 * @param subjectCode
	 * @param typeCode
	 * @return
	 */
	public Integer getRecordCount(String subjectCode,String gradeCode,String typeCode) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(id) from item_duplicate ");
		if(subjectCode != null){
			sqlBuff.append(" where subject_code='"+subjectCode+"'");
		}
		if(gradeCode != null){
			sqlBuff.append(" and grade_code='"+gradeCode+"'");
		}
		if(typeCode != null){
			sqlBuff.append(" and type_code='"+typeCode+"'");
		}
		
		Integer amount=this.getJdbcTemplate().queryForInt(sqlBuff.toString(),new HashMap<String,Object>());
		return amount;
	}
	
	/**
	 * 清理数据记录
	 */
	public void clear(String subjectCode,String gradeCode,String typeCode){
		String sql = "delete from item_duplicate ";
		if(subjectCode != null){
			sql = sql + " where subject_code='"+subjectCode+"'";
		}
		if(gradeCode != null){
			sql = sql + " and grade_code='"+gradeCode+"'";
		}
		if(typeCode != null){
			sql = sql + " and type_code='"+typeCode+"'";
		}
		this.getJdbcTemplate().update(sql, new HashMap<String,Object>());
	}
	
	/**
	 * 删除重复数据信息
	 * @param ids
	 */
	public void delete(String ids){
		String sql = "delete from item_duplicate where id in(?)";
		this.getJdbcTemplate().update(sql, ids);
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
