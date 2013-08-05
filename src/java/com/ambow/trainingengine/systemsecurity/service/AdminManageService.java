package com.ambow.trainingengine.systemsecurity.service;
/**
 * AdminManageService.java
 * 
 * Created on 2008-8-1 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
/**
 * 系统权限用户管理类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.*;

import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.core.exception.BusinessException;
import com.ambow.core.util.MD5;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;

public class AdminManageService extends HibernateEntityDao<SysUser>{
	/**
	 * 管理员登陆
	 * 
	 * @param loginName
	 *            登录名
	 * @param password
	 *            密码
	 */
	@SuppressWarnings("unchecked")
	public SysUser login(SysUser sysUser) {

		List<SysUser> list = this.find("from SysUser a where a.username=?", sysUser.getUsername());

		if (list.size() == 0) {
			throw new BusinessException(LOGIN_INFO_USERNAME);
		} else {
			SysUser tempuser = list.get(0);
			if (tempuser.getPassword().equals(MD5.crypt(sysUser.getPassword()))) {//密码加密完进行判断
				if (tempuser.getStatus() == 0) {
					return tempuser; 
				} else {
					throw new BusinessException(LOGIN_INFO_USERSTATUS);//用户名错误
				}
			} else {
				throw new BusinessException(LOGIN_INFO_PASSWORD);//密码错误
			}
		}
	}
	
	/**
	 * 保存用户对象
	 * @param sysUser
	 */
	public void saveSysUser(SysUser sysUser){
		SysUser tempUser=null;
		if(sysUser.getId()!=null&&sysUser.getId()!=0){//是否是更新
			tempUser=this.get(sysUser.getId());
			tempUser.setEmail(sysUser.getEmail());
			tempUser.setGender(sysUser.getGender());
			tempUser.setPassword(MD5.crypt(sysUser.getPassword()));
			tempUser.setPhoneNumber(sysUser.getPhoneNumber());
			tempUser.setRealName(sysUser.getRealName());
			tempUser.setStatus(sysUser.getStatus());
			tempUser.setSysRole(sysUser.getSysRole());
			tempUser.setUsername(sysUser.getUsername());
		}else{
			sysUser.setCreateTime(new Date());
			sysUser.setPassword(MD5.crypt(sysUser.getPassword()));
			tempUser=sysUser;
		}
		this.save(tempUser);
	}
	/**
	 * 删除指定用户对象
	 * @param sysUser
	 */
	public void deleteSysUser(SysUser sysUser){
		this.remove(sysUser);
	}
	/**
	 * 查询出所有用户对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> getSysUserList(){
		return this.find("from SysUser  s order by s.createTime desc");
	}
	
	/**
	 * 条件分页查询
	 * @param term 查询条件
	 * @param pageNo 页码
	 * @param pageSize 记录数
	 * @return
	 */
	public Page getSysUserPage(String term,int pageNo,int pageSize){
		String hql="from SysUser s where 1=1 ";
		if(null!=term&&!term.equals("")){
			hql+=" and s."+term;
		}
		hql+=" order by s.createTime desc";
		return this.pagedQuery(hql, pageNo, pageSize);
	}

	/**
	 * 查询指定范围的用户对象
	 * @param roleids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUser> getSysUserList(String userids) {
		return this.find("from SysUser s where s.id in("+userids+") order by s.createTime desc"); 
	}
}
