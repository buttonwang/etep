/**
 * 
 */
package com.ambow.trainingengine.exam.service;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;

/**
 * @author yuanjunqi
 *
 */
public class SetpageService extends HibernateEntityDao<ProcessTrainingStatus> {
	
	private SimpleJdbcTemplate jdbcTemplate ;

	/**
	 * 获取流程训练状态
	 * @param processInstanceId
	 * @return
	 */
	public ProcessTrainingStatus queryForObj(long processInstanceId) {
		String hsql = "from ProcessTrainingStatus where processInstanceId=?";
		ProcessTrainingStatus processTrainingStatus = (ProcessTrainingStatus)this.findObjByHql(hsql,processInstanceId);
		return processTrainingStatus;
	}
	
	public void update(long processInstanceId,String layout1,String layout2,String layout3,String font){
		String sql = "update process_training_status set layout1=?,layout2=?,layout3=?,font=? where process_instance_id=?";
		this.getJdbcTemplate().update(sql, layout1,layout2,layout3,font,processInstanceId);
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
