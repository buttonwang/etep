package com.ambow.trainingengine.systemsecurity.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ambow.studyflow.domain.ProcessInstance;

/**
 * Webuser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "webuser")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Webuser implements java.io.Serializable {

	// Fields

	private String id;
	private String loginName;
	private String realName;
	private String password;
	private String email;
	private String branch;
	private String dept;
	private Date registerTime;
	private Date firstLoginTime;
	private Date lastLoginTime;
	private Integer loginTimes;
	private Integer status;	
	private Set<ProcessInstance> asfProcessInstances = new HashSet<ProcessInstance>(0);

	// Constructors

	/** default constructor */
	public Webuser() {
	}

	/** minimal constructor */
	public Webuser(String loginName, String realName) {
		this.loginName = loginName;
		this.realName = realName;
	}

	/** full constructor */
	@SuppressWarnings("unchecked")
	public Webuser(String loginName, String realName, Date firstLoginTime,
			Date lastLoginTime, Integer loginTimes, Integer status,			
			Set asfProcessInstances) {
		this.loginName = loginName;
		this.realName = realName;
		this.firstLoginTime = firstLoginTime;
		this.lastLoginTime = lastLoginTime;
		this.loginTimes = loginTimes;
		this.status = status;
		
		//this.asfProcessInstances = asfProcessInstances;
	}

	// Property accessors
	@Id
	//@GeneratedValue(generator="assigned")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name="login_name")
	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name="real_name")
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name="password")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="branch")
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Column(name="dept")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}
	
	@Column(name="register_time")
	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	@Column(name="first_login_time")
	public Date getFirstLoginTime() {
		return this.firstLoginTime;
	}
	
	public void setFirstLoginTime(Date firstLoginTime) {
		this.firstLoginTime = firstLoginTime;
	}

	@Column(name="last_login_time")
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	@Column(name="login_times")
	public Integer getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	
	@Column(name="status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}	
	
	//TODO 此处是单向
	@Transient
	public Set<ProcessInstance> getAsfProcessInstances() {
		return this.asfProcessInstances;
	}

	public void setAsfProcessInstances(Set<ProcessInstance> asfProcessInstances) {
		this.asfProcessInstances = asfProcessInstances;
	}

}