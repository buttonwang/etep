package com.ambow.trainingengine.itembank.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Subject entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "subject")
public class Subject implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	
	private Set<Paper> papers = new HashSet<Paper>(0);
	private Set<Item> items = new HashSet<Item>(0);
	// Constructors

	/** default constructor */
	public Subject() {
	}

	/** minimal constructor */
	public Subject(String code) {
		this.code = code;
	}

	/** full constructor */
	public Subject(String name, Set<Paper> papers, Set<Item> items) {
		this.name = name;
		this.papers = papers;
		this.items = items;
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

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(
			name = "subject_paper_ref",
			joinColumns = @JoinColumn(name="subject_code"),
			inverseJoinColumns = @JoinColumn(name="paper_id")
	)
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "subject")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

}