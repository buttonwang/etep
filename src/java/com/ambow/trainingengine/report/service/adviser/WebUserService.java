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
public class WebUserService extends ReportBaseService{
	/**
	 * 根据用户班级编码取得用户列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getUserListByClassCode(String ClassCode){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" SELECT a.id,a.login_name,a.real_name from webuser a ");
		sqlBuff.append(" inner JOIN `asf_process_instance` b  on a.id = b.actor ");
		sqlBuff.append(" inner join `process_training_status` c on b.`id` = c.`process_instance_id` ");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(ClassCode);
		sqlBuff.append("'");
		List list=this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		return list;
	}
	
	/**
	 * 根据班级编码取得成员数量和班级名称
	 * @param ClassCode
	 * @return
	 */
	public Map getUserCountByClassCode(String ClassCode){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" SELECT count(a.id) as amount,d.name from webuser a ");
		sqlBuff.append(" inner JOIN asf_process_instance b  on a.id = b.actor ");
		sqlBuff.append(" inner join process_training_status c on b.id = c.process_instance_id ");
		sqlBuff.append(" inner join asf_process_definition d on b.process_definition_id = d.id ");
		sqlBuff.append(" where c.class_num='");
		sqlBuff.append(ClassCode);
		sqlBuff.append("'");
		sqlBuff.append(" group by c.class_num");
		Map map = null;
		List list = this.getJdbcTemplate().queryForList(sqlBuff.toString(),new HashMap());
		if(list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}
	
	/**
	 * 根据用户编码取得用户对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getUserDataById(String userId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select a.id,a.login_name,a.real_name,c.class_num,c.module,c.grade,c.process_instance_id,");
		sqlBuff.append(" b.process_definition_id,d.name from webuser a ");
		sqlBuff.append(" inner join asf_process_instance b on b.actor=a.id");
		sqlBuff.append(" inner join `process_training_status` c on b.`id` = c.`process_instance_id` ");
		sqlBuff.append(" inner join asf_process_definition d on b.process_definition_id = d.id ");
		sqlBuff.append(" where a.id='");
		sqlBuff.append(userId);
		sqlBuff.append("'");
		Map userMap=this.getJdbcTemplate().queryForMap(sqlBuff.toString(),new HashMap());
		return userMap;
	}
	
	public List getAllWebUser(){
		String hql ="from Webuser w where w.status=0";
		return this.find(hql);
	}

}
