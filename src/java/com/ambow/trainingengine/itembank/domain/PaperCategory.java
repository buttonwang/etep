package com.ambow.trainingengine.itembank.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PaperCategory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "paper_category")
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
public class PaperCategory implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	
	private Set<Paper> papers = new HashSet<Paper>(0);

	// Constructors

	/** default constructor */
	public PaperCategory() {
	}

	/** minimal constructor */
	public PaperCategory(String code) {
		this.code = code;
	}

	/** full constructor */
	public PaperCategory(String code, String name, Set<Paper> papers) {
		this.code = code;
		this.name = name;
		this.papers = papers;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 10)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "paperCategory")
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
	
	private Subject subject;
	private Grade grade;
	@ManyToOne
	@JoinColumn(name = "subject_code")
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	@ManyToOne
	@JoinColumn(name = "grade_code")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
}