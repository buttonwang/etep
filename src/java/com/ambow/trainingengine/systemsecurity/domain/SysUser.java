package com.ambow.trainingengine.systemsecurity.domain;
/**
 * SysUser.java
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
 * 系统权限用户对象
 */
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
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

@Entity
@Table(name = "sys_user")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SysUser implements java.io.Serializable{

	private static final long serialVersionUID = 3674487620102405365L;
	// Fields
	private Integer id;
	private String username;
	private String password;
	private String realName;
	private Integer gender;
	private String phoneNumber;
	private String email;
	private Date createTime;
	private Date validPeriodStart;
	private Date validPeriodEnd;
	private Integer status;
	private List<SysRole> sysRole;//用户的所有角色
	
	@ManyToMany(targetEntity=SysRole.class,cascade=CascadeType.REFRESH,
			fetch = FetchType.EAGER)
	@JoinTable(
			name = "sys_user_role_ref",
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="role_id")
	)
//	@Fetch(FetchMode.SELECT)
	public List<SysRole> getSysRole() {
		return sysRole;
	}

	public void setSysRole(List<SysRole> sysRole) {
		this.sysRole = sysRole;
	}

	public SysUser() {

	}

	public SysUser(String username, String password, String realName,
			Integer gender, String phoneNumber, String email, Date createTime,
			Date validPeriodStart, Date validPeriodEnd, Integer status,List<SysRole> sysRole) {
		this.username = username;
		this.password = password;
		this.realName = realName;
		this.gender = gender;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.createTime = createTime;
		this.validPeriodStart = validPeriodStart;
		this.validPeriodEnd = validPeriodEnd;
		this.status = status;
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

	@Column(name = "login_name", length = 20)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "real_name", length = 50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(name = "gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	@Column(name = "phone_number", length = 50)
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "created_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "valid_period_start")
	public Date getValidPeriodStart() {
		return validPeriodStart;
	}

	public void setValidPeriodStart(Date validPeriodStart) {
		this.validPeriodStart = validPeriodStart;
	}

	@Column(name = "valid_period_end")
	public Date getValidPeriodEnd() {
		return validPeriodEnd;
	}

	public void setValidPeriodEnd(Date validPeriodEnd) {
		this.validPeriodEnd = validPeriodEnd;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}