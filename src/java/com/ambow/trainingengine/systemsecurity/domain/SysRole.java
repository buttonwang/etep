package com.ambow.trainingengine.systemsecurity.domain;
/**
 * SysRole.java
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
 * 系统权限角色对象
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Subject;

@SuppressWarnings("serial")
@Entity
@Table(name = "sys_role")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysRole implements java.io.Serializable{
	// Fields
	private Integer id;
	private String name;
	private String description;
	private List<SysUser> sysUser;//角色所属的用户
	private List<SysFunction> sysFunction;//角色下的所有功能
	private List<Subject> subjects;//角色能管理的学科
	private List<Grade> grades;//角色能管理的学科

	public SysRole() {

	}

	public SysRole(String name, String description,List<SysUser> sysUser,List<SysFunction> sysFunction) {
		this.name = name;
		this.description = description;
		this.sysUser=sysUser;
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

	@ManyToMany(targetEntity=SysUser.class,
			fetch = FetchType.LAZY)
	@JoinTable(
			name = "sys_user_role_ref",
			joinColumns = @JoinColumn(name="role_id"),
			inverseJoinColumns = @JoinColumn(name="user_id")
	)
	public List<SysUser> getSysUser() {
		return sysUser;
	}

	public void setSysUser(List<SysUser> sysUser) {
		this.sysUser = sysUser;
	}

	@ManyToMany(targetEntity=SysFunction.class,
			cascade=CascadeType.REFRESH,
			fetch = FetchType.LAZY)
	@JoinTable(
			name = "sys_role_function_ref",
			joinColumns = @JoinColumn(name="role_id"),
			inverseJoinColumns = @JoinColumn(name="function_id")
	)
	public List<SysFunction> getSysFunction() {
		return sysFunction;
	}

	public void setSysFunction(List<SysFunction> sysFunction) {
		this.sysFunction = sysFunction;
	}
	
	@ManyToMany(targetEntity=Subject.class,
			cascade=CascadeType.REFRESH 
			,fetch = FetchType.LAZY
			 )
	@JoinTable(
			name = "sys_role_subject_ref",
			joinColumns = @JoinColumn(name="role_id"),
			inverseJoinColumns = @JoinColumn(name="subject_code"
			) 
	)
	public List<Subject> getSubjects() {
		return subjects;
	}
	
	
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@ManyToMany(targetEntity=Grade.class,
			cascade= {CascadeType.REFRESH, CascadeType.REMOVE} 
			,fetch = FetchType.LAZY
			 )
	@JoinTable(
			name = "sys_role_grade_ref",
			joinColumns = @JoinColumn(name="role_id"),
			inverseJoinColumns = @JoinColumn(name="grade_code")
	)
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
}