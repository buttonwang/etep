package com.ambow.trainingengine.systemsecurity.service;
/**
 * FunctionManageService.java
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
 * 系统权限功能管理类
 */
import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysFunction;

public class FunctionManageService extends HibernateEntityDao<SysFunction>{
	/**
	 * 保存功能
	 * @param sysFunction
	 */
	public void saveSysFunction(SysFunction sysFunction){
		this.save(sysFunction);
	}
	/**
	 * 删除功能
	 * @param sysFunction
	 */
	public void deleteSysFunction(SysFunction sysFunction){
		this.remove(sysFunction);
	}
	/**
	 * 查询出所有功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFunction> getSysFunctionList(Integer rid){
		return this.find("select s from SysFunction s left join s.sysRole r where r.id = ?", rid);
	}
	
	/**
	 * 查询出所有和角色有关功能
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFunction> getSysFunctionList(){
		return this.find("from SysFunction");
	}
	/**
	 * 条件分页查询
	 * @param term 查询条件
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getSysFunctionPage(String term,int pageNo,int pageSize){
		String hql="from SysFunction s where 1=1 ";
		if(null!=term&&!term.equals("")){
			hql+=" and s."+term;
		}
		return this.pagedQuery(hql, pageNo, pageSize);
	}

	/**
	 * 查询指定范围的功能
	 * @param funs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysFunction> getSysFunctionList(String funs) {
		return this.find("from SysFunction s where s.id in("+funs+")");
	}
}
