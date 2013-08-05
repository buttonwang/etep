package com.ambow.trainingengine.itembank.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.wordbank.domain.WordExtension;

/**
 * Grade entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "grade")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Grade implements java.io.Serializable {
	
	// Fields
	
	private String code;
	private String name;
	private Grade  parentGrade;
	private String parentLevel;
	
	private Set<Item> items = new HashSet<Item>(0);
	private Set<Paper> papers = new HashSet<Paper>(0);
	private Set<WordExtension> wordExtensions = new HashSet<WordExtension>(0);

	private List<Grade> childrenGrades = new ArrayList<Grade>(0);

	private String levelFlag= "";
	// Constructors

	/** default constructor */
	public Grade() {
	}

	/** minimal constructor */
	public Grade(String code) {
		this.code = code;
	}

	/** full constructor */
	public Grade(String name, Grade parentGrade, String parentLevel, Set<Item> items,
			Set<Paper> papers, Set<WordExtension> wordExtensions) {
		this.name = name;
		this.parentGrade = parentGrade;
		this.parentLevel = parentLevel;
		this.items = items;
		this.papers = papers;
		this.wordExtensions = wordExtensions;
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

	@ManyToOne()
	@JoinColumn(name = "parent_code")
	@NotFound(action=NotFoundAction.IGNORE)
	public Grade getParentGrade() {
		return this.parentGrade;
	}

	public void setParentGrade(Grade parentGrade) {
		this.parentGrade = parentGrade;
	}

	@Column(name = "parent_level", length = 50)
	public String getParentLevel() {
		return this.parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}
	
	@ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(
			name = "grade_item_ref",
			joinColumns = @JoinColumn(name="grade_code"),
			inverseJoinColumns = @JoinColumn(name="item_id")
	)	
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "grade_paper_ref",
			joinColumns = @JoinColumn(name="grade_code"),
			inverseJoinColumns = @JoinColumn(name="paper_id")
	)
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "grade")
	public Set<WordExtension> getWordExtensions() {
		return this.wordExtensions;
	}

	public void setWordExtensions(Set<WordExtension> wordExtensions) {
		this.wordExtensions = wordExtensions;
	}
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="parentGrade")
	public List<Grade> getChildrenGrades() {
		return this.childrenGrades;
	}

	public void setChildrenGrades(List<Grade> childrenGrades) {
		this.childrenGrades = childrenGrades;
	}

	@Transient
	public String getLevelFlag() {
		if (this.parentGrade!=null) this.levelFlag = this.parentGrade.levelFlag + "----";
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	
	/** 生成parentLevel字段 */
	public void genparentLevel(){
		this.parentLevel = (this.parentGrade==null)?this.code:this.parentGrade.parentLevel+","+this.code;
	}
	
}