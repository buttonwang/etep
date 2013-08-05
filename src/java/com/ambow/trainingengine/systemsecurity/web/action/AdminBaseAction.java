package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * AdminBaseAction.java
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
 * 系统安全控制层基类
 */
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.systemsecurity.domain.SysFunction;
import com.ambow.trainingengine.systemsecurity.domain.SysModule;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.AdminManageService;
import com.ambow.trainingengine.systemsecurity.service.FunctionManageService;
import com.ambow.trainingengine.systemsecurity.service.ModuleManageService;
import com.ambow.trainingengine.systemsecurity.service.RoleManageService;


@SuppressWarnings("serial")
public class AdminBaseAction extends BaseAction {
	/**
	 * 对象原来的名字为了控制对象名的唯一性
	 */
	private String rname;
	/**
	 * 系统用户页面信息包装对象
	 */
	protected SysUser sysUser=new SysUser();
	/**
	 * 系统角色页面信息包装对象
	 */
	protected SysRole sysRole=new SysRole();
	/**
	 * 系统模块页面信息包装对象
	 */
	protected SysModule sysModule=new SysModule();
	/**
	 * 系统功能页面信息包装对象
	 */
	protected SysFunction sysFunction=new SysFunction();
	/**
	 * 系统用户管理服务对象
	 */
	private AdminManageService adminManageService;
	/**
	 * 系统角色管理服务对象
	 */
	private RoleManageService roleManageService;
	/**
	 * 系统模块管理服务对象
	 */
	private ModuleManageService moduleManageService;
	/**
	 * 系统功能管理服务对象
	 */
	private FunctionManageService functionManageService;
	
	public ModuleManageService getModuleManageService() {
		return moduleManageService;
	}

	public void setModuleManageService(ModuleManageService moduleManageService) {
		this.moduleManageService = moduleManageService;
	}

	public FunctionManageService getFunctionManageService() {
		return functionManageService;
	}

	public void setFunctionManageService(FunctionManageService functionManageService) {
		this.functionManageService = functionManageService;
	}

	public RoleManageService getRoleManageService() {
		return roleManageService;
	}

	public void setRoleManageService(RoleManageService roleManageService) {
		this.roleManageService = roleManageService;
	}

	public AdminManageService getAdminManageService() {
		return adminManageService;
	}

	public void setAdminManageService(AdminManageService adminManageService) {
		this.adminManageService = adminManageService;
	}

	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	public SysFunction getSysFunction() {
		return sysFunction;
	}

	public void setSysFunction(SysFunction sysFunction) {
		this.sysFunction = sysFunction;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}
}
