package com.ambow.trainingengine.systemsecurity.service;
/**
 * ModuleManageService.java
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
 * 系统权限模块管理类
 */
import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysModule;

public class ModuleManageService extends HibernateEntityDao<SysModule>{
	/**
	 * 保存模块对象
	 * @param sysModule
	 */
	public void saveSysModule(SysModule sysModule){
		this.save(sysModule);
		if(sysModule.getSubModule()!=null&&sysModule.getSubModule().getId()!=0){
			sysModule.setParentLevel(sysModule.getSubModule().getParentLevel()+","+sysModule.getId());
		}else{
			sysModule.setParentLevel(sysModule.getId().toString());
		}
		this.save(sysModule);
	}
	/**
	 * 删除指定模块对象
	 * @param sysModule
	 */
	public void deleteSysModule(SysModule sysModule){
		this.removeById(sysModule.getId());
	}
	/**
	 * 查询不是末节点的模块
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModule> getSysModuleList(){
		return this.find("from SysModule s where s.isLeaf!='1' order by s.parentLevel");
	}
	/**
	 * 查询根节点的模块
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModule> getSysModuleNotSubList(){
		return this.find("from SysModule s where s.subModule is null");
	}
	/**
	 * 查询末节点模块
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysModule> getSysModuleIsLeafList(){
		return this.find("from SysModule s where s.isLeaf='1'");
	}
	
	/**
	 * 条件分页查询模块对象
	 * @param term
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page getSysModulePage(String term,int pageNo,int pageSize){
		String hql="from SysModule s where 1=1 ";
		if(null!=term&&!term.equals("")){
			hql+=" and s."+term;
		}
		hql+=" and s.subModule is null order by s.parentLevel";
		return this.pagedQuery(hql, pageNo, pageSize);
	}
}
