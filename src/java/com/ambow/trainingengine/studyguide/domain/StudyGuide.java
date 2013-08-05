package com.ambow.trainingengine.studyguide.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;

/*
 * StudyGuide.java
 * 
 * Created on 2009-7-23 下午03:21:12
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "study_guide")
public class StudyGuide implements java.io.Serializable {

	private static final long serialVersionUID = 3949148076935129163L;

	private Integer id;
	private String code;
	private String name;
	private StudyGuide parent;
	private List<StudyGuide> children;
	private String parentLevel;
	private String content;
	private SysUser creater;
	private Date createdTime;
	private SysUser updater;
	private Date updatedTime;
	private SysUser verifier;
	private Date verifiedTime;
	private String importFile;
	private Integer status;
	private Grade grade;
	private Subject subject;
	private String level = "";
	private List<StudyGuideParagraph> paragraphs = new ArrayList<StudyGuideParagraph>(0);
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "content")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public StudyGuide getParent() {
		return parent;
	}

	public void setParent(StudyGuide parent) {
		this.parent = parent;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "parent")
	public List<StudyGuide> getChildren() {
		return children;
	}

	public void setChildren(List<StudyGuide> children) {
		this.children = children;
	}

	
	
	@Column(name = "parent_level", length = 50)
	public String getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	@ManyToOne()
	@JoinColumn(name = "creater")
	public SysUser getCreater() {
		return this.creater;
	}

	public void setCreater(SysUser creater) {
		this.creater = creater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", length = 0)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@ManyToOne()
	@JoinColumn(name = "updater")
	public SysUser getUpdater() {
		return this.updater;
	}

	public void setUpdater(SysUser updater) {
		this.updater = updater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time", length = 0)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@ManyToOne()
	@JoinColumn(name = "verifier")
	public SysUser getVerifier() {
		return this.verifier;
	}

	public void setVerifier(SysUser verifier) {
		this.verifier = verifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "verified_time", length = 0)
	public Date getVerifiedTime() {
		return this.verifiedTime;
	}

	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
	}

	@Column(name = "import_file")
	public String getImportFile() {
		return importFile;
	}

	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_code", nullable = true)
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subject_code", nullable = true)
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	//虽然该字段没有持久化但是当需要显示层次时会即时调用该方法得到其对应的字段前缀
	@Transient
	public String getLevel() {
		if(this.parent!= null) {
			this.level = this.parent.level + "----";
		}
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void generateParentLevel() {
		if(this.parent == null) {
			this.parentLevel = this.code;
		}else {
			this.parentLevel = this.parent.parentLevel + "_" + this.code;
		}
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "studyGuide")
	@OrderBy("orderNum")
	public List<StudyGuideParagraph> getParagraphs() {
		return paragraphs;
	}

	public void setParagraphs(List<StudyGuideParagraph> paragraphs) {
		this.paragraphs = paragraphs;
	}
	
}
