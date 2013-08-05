package com.ambow.trainingengine.bug.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * BugInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bug_info")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BugInfo implements java.io.Serializable {
	private static final long serialVersionUID = 2607027632460354046L;
	private Integer id;
	private Webuser user;
	private Bug bug;
	private SysUser teacher;
	private String submitInfo;
	private String replyInfo;
	private Date submitTime;
	private Date replyTime;
	private Integer bugSite;
	private Integer status;
	private Set<BugInfoHistoryAnswerStatus> bugInfoHistoryAnswerStatuses = new HashSet<BugInfoHistoryAnswerStatus>(
			0);

	// Constructors

	/** default constructor */
	public BugInfo() {
	}

	/** minimal constructor */
	public BugInfo(Integer id) {
		this.id = id;
	}
	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public Webuser getUser() {
		return this.user;
	}

	public void setUser(Webuser user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bug_id")
	public Bug getBug() {
		return this.bug;
	}

	public void setBug(Bug bug) {
		this.bug = bug;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id")
	public SysUser getTeacher() {
		return this.teacher;
	}

	public void setTeacher(SysUser teacher) {
		this.teacher = teacher;
	}

	@Column(name = "submit_info", length = 500)
	public String getSubmitInfo() {
		return this.submitInfo;
	}

	public void setSubmitInfo(String submitInfo) {
		this.submitInfo = submitInfo;
	}

	@Column(name = "reply_info", length = 500)
	public String getReplyInfo() {
		return this.replyInfo;
	}

	public void setReplyInfo(String replyInfo) {
		this.replyInfo = replyInfo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submit_time", length = 0)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reply_time", length = 0)
	public Date getReplyTime() {
		return this.replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	@Column(name = "bug_site")
	public Integer getBugSite() {
		return bugSite;
	}

	public void setBugSite(Integer bugSite) {
		this.bugSite = bugSite;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bugInfo")
	public Set<BugInfoHistoryAnswerStatus> getBugInfoHistoryAnswerStatuses() {
		return this.bugInfoHistoryAnswerStatuses;
	}

	public void setBugInfoHistoryAnswerStatuses(
			Set<BugInfoHistoryAnswerStatus> bugInfoHistoryAnswerStatuses) {
		this.bugInfoHistoryAnswerStatuses = bugInfoHistoryAnswerStatuses;
	}

}