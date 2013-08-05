package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * RoleManageAction.java
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
 * 系统安全控制层角色控制管理类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.PAGE_SIZE;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;

@SuppressWarnings("serial")
public class RoleManageAction extends AdminBaseAction {
	
	/**
	 * 角色的功能id
	 */
	private String funs;

	/**
	 * 查询角色的条件
	 */
	private String term;
	
	/**
	 * 批量处理的id
	 */
	private String roleids;
	
	/**
	 * 临时的id
	 */
	private String roleid;

	/**
	 * 查询角色的条件值
	 */
	private String termstr;
	/**
	 * 异步请求随机数
	 */
	private String test;

	/**
	 * 分页
	 */
	private int pageNo;

	/**
	 * 分页查询角色信息
	 * @return
	 */
	public String list() {
		if (pageNo < 1) {// 设置分页默认值
			pageNo = 1;
		}
		if (null != term && !term.equals("")) {//拼写查询条件
			term = termstr + " like '%" + term + "%'";
		}
		Page page = this.getRoleManageService().getSysRolePage(term, pageNo,
				PAGE_SIZE);
		this.setRequestAttribute("rolepage", page);
		return "list";
	}

	/**
	 * 添加修改角色信息
	 * @return
	 */
	public String add() {
		Integer tempid=getId();
		if(tempid!=null&&tempid!=0){//判断是添加还是修改
			sysRole=this.getRoleManageService().get(tempid);
		}
		return "add";
	}
	/**
	 * 角色详细信息
	 * @return
	 */
	public String info(){
		sysRole=this.getRoleManageService().get(getId());
		return "info";
	}
	
	/**
	 * 批量删除功能
	 * @return
	 */
	public String delall(){
		List<SysRole> rolelist=this.getRoleManageService().getSysRoleList(roleids);
		this.getRoleManageService().removeAll(rolelist);
		return list();
	}

	/**
	 * 保存角色信息
	 * @return
	 */
	public String save() {
		Integer tempid=getId();
		SysRole sysRoleDB = null;
		if(tempid!=null&&tempid!=0){//判断是添加还是修改
			sysRoleDB = this.getRoleManageService().get(tempid);
		}
		if(sysRoleDB!=null){
			sysRoleDB.setName(sysRole.getName());
			sysRoleDB.setDescription(sysRole.getDescription());
			this.getRoleManageService().saveSysRole(sysRoleDB);
		}else
			this.getRoleManageService().saveSysRole(sysRole);
		return list();
	}
	
	/**
	 * 保存角色功能信息
	 * @return
	 */
	public String addfun() {
		SysRole temprole=this.getRoleManageService().get(getId());
		temprole.setSysFunction(sysRole.getSysFunction());
		this.getRoleManageService().saveSysRole(temprole);
		return list();
	}

	/**
	 * 删除角色信息
	 * @return
	 */
	public String delete() {
		this.getRoleManageService().deleteSysRole(sysRole);
		return list();
	}
	
	public String addFunction(){
		sysRole=this.getRoleManageService().get(getId());
		this.setRequestAttribute("modulelist",this.getModuleManageService().getSysModuleNotSubList());
		return "addFunction";
	}

	public void setId(Integer id) {
		this.sysRole.setId(id);
	}

	public Integer getId() {
		return this.sysRole.getId();
	}

	public void setName(String name) {
		this.sysRole.setName(name);
	}

	public String getName() {
		return this.sysRole.getName();
	}

	public void setDescription(String description) {
		this.sysRole.setDescription(description);
	}

	public String getDescription() {
		return this.sysRole.getDescription();
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getTermstr() {
		return termstr;
	}

	public void setTermstr(String termstr) {
		this.termstr = termstr;
	}

	public String getFuns() {
		return funs;
	}

	public void setFuns(String funs) {
		if(funs!=null&&!funs.equals("")&&!funs.equals("undefined")){
			this.sysRole.setSysFunction(this.getFunctionManageService().getSysFunctionList(funs));
		}
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
}
