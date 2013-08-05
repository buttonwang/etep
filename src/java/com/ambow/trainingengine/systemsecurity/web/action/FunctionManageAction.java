package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * FunctionManageAction.java
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
 * 系统安全控制层功能控制管理类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.PAGE_SIZE;

import java.util.Iterator;
import java.util.List;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.systemsecurity.domain.SysFunction;

@SuppressWarnings("serial")
public class FunctionManageAction extends AdminBaseAction {
	
	/**
	 * 批量处理的id
	 */
	private String funids;
	
	/**
	 * 临时的id
	 */
	private String funid;
	
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
		if (null != term && !term.equals("")) {//拼写查询条件
			term = termstr + " like '%" + term + "%'";
		}
		Page page = this.getFunctionManageService().getSysFunctionPage(term, pageNo,
				PAGE_SIZE);
		this.setRequestAttribute("functionpage", page);
		return "list";
	}

	/**
	 * 添加修改功能信息
	 * @return
	 */
	public String add() {
		Integer tempid=getId();
		if(tempid!=null&&tempid!=0){//判断是添加还是修改
			sysFunction=this.getFunctionManageService().get(tempid);
		}
		this.setRequestAttribute("modulelist", this.getModuleManageService().getSysModuleIsLeafList());
		return "add";
	}
	
	/**
	 * 功能详细信息
	 * @return
	 */
	public String info(){
		sysFunction=this.getFunctionManageService().get(getId());
		return "info";
	}

	/**
	 * 保存功能信息
	 * @return
	 */
	public String save() {
		this.getFunctionManageService().saveSysFunction(sysFunction);
		SecurityFilter.checkURLList=this.getFunctionManageService().getSysFunctionList();
		return list();
	}
	
	/**
	 * 批量删除功能
	 * @return
	 */
	public String delall(){
		List<SysFunction> funlist=this.getFunctionManageService().getSysFunctionList(funids);
		this.getFunctionManageService().removeAll(funlist);
		return list();
	}
	
	/**
	 * 批量启用功能
	 * @return
	 */
	public String qyong(){
		List<SysFunction> funlist=this.getFunctionManageService().getSysFunctionList(funids);
		SysFunction tempfun=null;
		for(Iterator<SysFunction> it=funlist.iterator();it.hasNext();){
			tempfun=it.next();
			tempfun.setStatus(0);
		}
		this.getFunctionManageService().saveOrUpdateAll(funlist);
		return list();
	}
	
	/**
	 * 批量禁用功能
	 * @return
	 */
	public String jyong(){
		List<SysFunction> funlist=this.getFunctionManageService().getSysFunctionList(funids);
		SysFunction tempfun=null;
		for(Iterator<SysFunction> it=funlist.iterator();it.hasNext();){
			tempfun=it.next();
			tempfun.setStatus(1);
		}
		this.getFunctionManageService().saveOrUpdateAll(funlist);
		return list();
	}
	
	/**
	 * 删除功能信息
	 * @return
	 */
	public String delete() {
		this.getFunctionManageService().deleteSysFunction(sysFunction);
		return list();
	}

	public void setId(Integer id) {
		this.sysFunction.setId(id);
	}

	public Integer getId() {
		return this.sysFunction.getId();
	}

	public void setName(String name) {
		this.sysFunction.setName(name);
	}

	public String getName() {
		return this.sysFunction.getName();
	}

	public void setDescription(String description) {
		this.sysFunction.setDescription(description);
	}

	public String getDescription() {
		return this.sysFunction.getDescription();
	}
	
	public void setPath(String path){
		this.sysFunction.setPath(path);
	}
	
	public String getPath(){
		return this.sysFunction.getPath();
	}
	
	public void setAction(String action){
		this.sysFunction.setAction(action);
	}
	
	public String getAction(){
		return this.sysFunction.getAction();
	}
	
	public void setStatus(Integer status){
		this.sysFunction.setStatus(status);
	}
	
	public Integer getStatus(){
		return this.sysFunction.getStatus();
	}
	
	public void setModuleId(Integer moduleid){
		this.sysFunction.setSysModule(this.getModuleManageService().get(moduleid));
	}

	public Integer getModuleId(){
		return this.sysFunction.getSysModule().getId();
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

	public String getFunids() {
		return funids;
	}

	public void setFunids(String funids) {
		this.funids = funids;
	}

	public String getFunid() {
		return funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}
}
