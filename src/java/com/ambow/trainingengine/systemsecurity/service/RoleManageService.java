package com.ambow.trainingengine.systemsecurity.service;
/**
 * RoleManageService.java
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
 * 系统权限角色管理类
 */
import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;

public class RoleManageService extends HibernateEntityDao<SysRole>{
	/**
	 * 保存角色对象
	 * @param sysRole
	 */
	public void saveSysRole(SysRole sysRole){
		this.save(sysRole);
	}
	/**
	 * 删除指定角色对象
	 * @param sysRole
	 */
	public void deleteSysRole(SysRole sysRole){
		this.remove(sysRole);
	}
	/**
	 * 查询所有的系统角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> getSysRoleList(){
		return this.find("from SysRole s order by s.id desc");
	}
	
	/**
	 * 查询所有和用户有关的系统角色
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> getSysRoleList(Integer uid){
		return this.find("select s from SysRole s left join s.sysUser u where u.id = ?", uid);
	}
	
	/**
	 * 分页查询系统角色对象
	 * @param term
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getSysRolePage(String term,int pageNo,int pageSize){
		String hql="from SysRole s where 1=1 ";
		if(null!=term&&!term.equals("")){
			hql+=" and s."+term;
		}
		hql+="order by s.id desc";
		return this.pagedQuery(hql, pageNo, pageSize);
	}
	/**
	 * 查询指定范围的角色对象
	 * @param roleids
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysRole> getSysRoleList(String roleids) {
		return this.find("from SysRole s where s.id in("+roleids+") order by s.id desc"); 
	}
}
