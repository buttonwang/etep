package com.ambow.trainingengine.systemsecurity.domain;
/**
 * SysFunction.java
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
 * 系统权限功能对象
 */
import static javax.persistence.GenerationType.IDENTITY;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_function")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysFunction implements java.io.Serializable{
	// Fields
	private Integer id;
	private String name;
	private String description;
	private String path;
	private String action;
	private Integer status;
	private SysModule sysModule;//所属模块
	private List<SysRole> sysRole;//所属角色

	public SysFunction() {

	}

	public SysFunction(String name, String description,String path,String action,Integer status,SysModule sysModule,List<SysRole> sysRole) {
		this.name = name;
		this.description = description;
		this.path=path;
		this.action=action;
		this.status=status;
		this.sysModule=sysModule;
		this.sysRole=sysRole;
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
	
	@Column(name = "path", length = 100)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "action", length = 50)
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_id")
	public SysModule getSysModule() {
		return sysModule;
	}

	public void setSysModule(SysModule sysModule) {
		this.sysModule = sysModule;
	}

	@ManyToMany(targetEntity=SysRole.class,
			fetch = FetchType.LAZY)
	@JoinTable(
			name = "sys_role_function_ref",
			joinColumns = @JoinColumn(name="function_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
	)
	public List<SysRole> getSysRole() {
		return sysRole;
	}

	public void setSysRole(List<SysRole> sysRole) {
		this.sysRole = sysRole;
	}
}