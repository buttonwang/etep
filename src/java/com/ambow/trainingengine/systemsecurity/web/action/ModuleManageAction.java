package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * ModuleManageAction.java
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
 * 系统安全控制层模块控制管理类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.PAGE_SIZE;

import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysModule;

@SuppressWarnings("serial")
public class ModuleManageAction extends AdminBaseAction {

	private String aid;
	/**
	 * 查询功能的条件
	 */
	private String term;

	/**
	 * 查询功能的条件值
	 */
	private String termstr;

	/**
	 * 分页
	 */
	private int pageNo;

	/**
	 * 分页查询功能信息
	 * @return
	 */
	public String list() {
		if (pageNo < 1) {// 设置分页默认值
			pageNo = 1;
		}
		if (null != term && !term.equals("")) {
			term = termstr + " like '%" + term + "%'";
		}
		Page page = this.getModuleManageService().getSysModulePage(term, pageNo,
				PAGE_SIZE);
		this.setRequestAttribute("modulepage", page);
		return "list";
	}

	/**
	 * 添加修改功能信息
	 * @return
	 */
	public String add() {
		if(aid!=null&&!aid.equals("")){
			SysModule tempmodule=this.getModuleManageService().get(Integer.valueOf(aid));
			if(tempmodule.getSysFunction()==null||tempmodule.getSysFunction().size()<=0){
				tempmodule.setIsLeaf(0);
				this.getModuleManageService().save(tempmodule);
			}else{
				this.setRequestAttribute("error", "此模块已存在功能，不可以增加子模块！");
				return list();
			}
		}
		List<SysModule> modlist=this.getModuleManageService().getSysModuleList();
		Integer tempid=getId();
		if(tempid!=null&&tempid!=0){
			sysModule=this.getModuleManageService().get(tempid);
		}
		this.setRequestAttribute("modulelist", modlist);
		return "add";
	}
	
	/**
	 * 功能详细信息
	 * @return
	 */
	public String info(){
		sysModule=this.getModuleManageService().get(getId());
		return "info";
	}

	/**
	 * 保存功能信息
	 * @return
	 */
	public String save() {
		this.getModuleManageService().saveSysModule(sysModule);
		return list();
	}

	/**
	 * 删除模块信息
	 * @return
	 */
	public String delete() {
		this.getModuleManageService().deleteSysModule(sysModule);
		return list();
	}
	
	/**
	 * 删除所有模块信息
	 * @return
	 */
	public String delall() {
		List<SysModule> modlist=this.getModuleManageService().getSysModuleList();
		this.getModuleManageService().removeAll(modlist);
		return list();
	}

	public void setId(Integer id) {
		this.sysModule.setId(id);
	}

	public Integer getId() {
		return this.sysModule.getId();
	}

	public void setName(String name) {
		this.sysModule.setName(name);
	}

	public String getName() {
		return this.sysModule.getName();
	}
	
	public void setPath(String path){
		this.sysModule.setPath(path);
	}
	
	public String getPath(){
		return sysModule.getPath();
	}
	
	public void setParentId(Integer parentId){
		if(parentId!=0){
			this.sysModule.setSubModule(this.getModuleManageService().get(parentId));
		}
	}
	
	public Integer getParentId(){
		return this.sysModule.getSubModule().getId();
	}

	public void setDescription(String description) {
		this.sysModule.setDescription(description);
	}

	public String getDescription() {
		return this.sysModule.getDescription();
	}
	
	public void setStatus(Integer status){
		this.sysModule.setStatus(status);
	}
	
	public Integer getStatus(){
		return this.sysModule.getStatus();
	}
	
	public void setIsLeaf(Integer isLeaf){
		this.sysModule.setIsLeaf(isLeaf);
	}

	public Integer getIsLeaf(){
		return this.sysModule.getIsLeaf();
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

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
}
