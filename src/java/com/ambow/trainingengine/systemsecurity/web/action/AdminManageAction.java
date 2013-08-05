package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * AdminManageAction.java
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
 * 系统安全控制层用户控制管理类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.PAGE_SIZE;

import java.util.Iterator;
import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.core.util.MD5;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;

@SuppressWarnings("serial")
public class AdminManageAction extends AdminBaseAction {

	/**
	 * 批量处理的id
	 */
	private String userids;
	
	/**
	 * 临时的id
	 */
	private String userid;
	
	/**
	 * 数据查询的条件名
	 */
	private String term;

	/**
	 * 数据查询的条件值
	 */
	private String termstr;

	/**
	 * 分页记录
	 */
	private int pageNo;
	
	/**
	 * 用户拥有的角色id
	 */
	private String roleids;
	

	/**
	 * 分页查询用户信息
	 * @return
	 */
	public String list() {
		if (pageNo < 1) {// 设置分页默认值
			pageNo = 1;
		}
		if (null != term && !term.equals("")) {//拼写查询条件
			term = termstr + " like '%" + term + "%'";
		}
		//查询用户记录
		Page page = this.getAdminManageService().getSysUserPage(term, pageNo,
				PAGE_SIZE);
		this.setRequestAttribute("userpage", page);
		return "list";
	}
	
	/**
	 * 修改用户密码信息
	 * @return
	 */
	public String psdeit(){
		Integer id=this.getId();
		String password=this.getPassword();
		if(id!=null&&getId()!=0){
			SysUser suser=this.getAdminManageService().get(id);
			suser.setPassword(MD5.crypt(password));
			this.getAdminManageService().save(suser);
		}
		return "psedit";
	}

	/**
	 * 用户添加修改
	 * @return
	 */
	public String add() {
		Integer tempid=getId();
		if(tempid!=null&&tempid!=0){//判断是添加还是修改
			sysUser=this.getAdminManageService().get(tempid);
		}
		this.setRequestAttribute("rolelist", this.getRoleManageService().getSysRoleList());
		return "add";
	}
	/**
	 * 用户的详细信息
	 * @return
	 */
	public String info(){
		sysUser=this.getAdminManageService().get(getId());
		return "info";
	}
	/**
	 * 保存用户的信息
	 * @return
	 */
	public String save() {
		sysUser.setSysRole(this.getRoleManageService().getSysRoleList(roleids));
		this.getAdminManageService().saveSysUser(sysUser);
		return list();
	}
	
	/**
	 * 批量删除功能
	 * @return
	 */
	public String delall(){
		List<SysUser> userlist=this.getAdminManageService().getSysUserList(userids);
		this.getAdminManageService().removeAll(userlist);
		return list();
	}
	
	/**
	 * 批量启用功能
	 * @return
	 */
	public String qyong(){
		List<SysUser> userlist=this.getAdminManageService().getSysUserList(userids);
		SysUser tempuser=null;
		for(Iterator<SysUser> it=userlist.iterator();it.hasNext();){
			tempuser=it.next();
			tempuser.setStatus(0);
		}
		this.getFunctionManageService().saveOrUpdateAll(userlist);
		return list();
	}
	
	/**
	 * 批量禁用功能
	 * @return
	 */
	public String jyong(){
		List<SysUser> userlist=this.getAdminManageService().getSysUserList(userids);
		SysUser tempuser=null;
		for(Iterator<SysUser> it=userlist.iterator();it.hasNext();){
			tempuser=it.next();
			tempuser.setStatus(1);
		}
		this.getFunctionManageService().saveOrUpdateAll(userlist);
		return list();
	}

	/**
	 * 删除用户的信息
	 * @return
	 */
	public String delete() {
		this.getAdminManageService().deleteSysUser(sysUser);
		return list();
	}

	public void setId(Integer id) {
		this.sysUser.setId(id);
	}

	public Integer getId() {
		return this.sysUser.getId();
	}

	public void setUsername(String username) {
		this.sysUser.setUsername(username);
	}

	public String getUsername() {
		return this.sysUser.getUsername();
	}
	
	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}

	public void setPassword(String password) {
		this.sysUser.setPassword(password);
	}

	public String getPassword() {
		return this.sysUser.getPassword();
	}

	public void setRealName(String realname) {
		this.sysUser.setRealName(realname);
	}

	public String getRealName() {
		return this.sysUser.getRealName();
	}

	public void setStatus(Integer status) {
		this.sysUser.setStatus(status);
	}

	public Integer getStatus() {
		return this.sysUser.getStatus();
	}

	public void setGender(Integer gender) {
		this.sysUser.setGender(gender);
	}

	public Integer getGender() {
		return this.sysUser.getGender();
	}

	public void setEmail(String email) {
		this.sysUser.setEmail(email);
	}

	public String getEmail() {
		return this.sysUser.getEmail();
	}

	public void setPhoneNumber(String phonenumber) {
		this.sysUser.setPhoneNumber(phonenumber);
	}

	public String getPhoneNumber() {
		return this.sysUser.getPhoneNumber();
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

	public String getUserids() {
		return userids;
	}

	public void setUserids(String userids) {
		this.userids = userids;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
}
