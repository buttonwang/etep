package com.ambow.trainingengine.systemsecurity.domain;
/**
 * SysModule.java
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
 * 系统权限模块对象
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_module")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysModule implements java.io.Serializable{
	// Fields
	private Integer id;
	private String name;
	private String path;
	private String description;
	private SysModule subModule;//模块所属的父模块
	private String parentLevel;
	private Integer isLeaf;
	private Integer status;
	private List<SysFunction> sysFunction;//模块下的所有功能
	private List<SysModule> modules;//模块下的所有子模块
	private String levelFlag= "";
	
	
	@OneToMany(cascade=CascadeType.REMOVE,mappedBy="subModule", 
            fetch=FetchType.LAZY)
	public List<SysModule> getModules() {
		return modules;
	}

	public void setModules(List<SysModule> modules) {
		this.modules = modules;
	}

	public SysModule() {

	}

	public SysModule(String name,String path, String description,SysModule subModule,List<SysFunction> sysFunction,String parentLevel,Integer isLeaf,Integer status) {
		this.name = name;
		this.description = description;
		this.subModule=subModule;
		this.parentLevel=parentLevel;
		this.isLeaf=isLeaf;
		this.status=status;
		this.sysFunction=sysFunction;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY,mappedBy="sysModule")
	public List<SysFunction> getSysFunction() {
		return sysFunction;
	}

	public void setSysFunction(List<SysFunction> sysFunction) {
		this.sysFunction = sysFunction;
	}

	@Column(name = "name", length = 20)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "path", length = 200)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "parent_level", length = 200)
	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	@Column(name = "is_leaf", length = 200)
	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}

	@Column(name = "status", length = 200)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "parent_id")
	@NotFound(action=NotFoundAction.IGNORE)
	public SysModule getSubModule() {
		return subModule;
	}

	public void setSubModule(SysModule subModule) {
		this.subModule = subModule;
	}
	
	@Transient
	public String getLevelFlag() {
		if (this.subModule!=null) this.levelFlag = this.subModule.levelFlag + "----";
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	
}