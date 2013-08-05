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

/**
 * ItemTheme entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "item_theme")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ItemTheme implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	private ItemTheme parentItemTheme;
	private String parentLevel;
	
	private Set<Item> items = new HashSet<Item>(0);

	private List<ItemTheme> childrenItemThemes = new ArrayList<ItemTheme>(0);
	
	private String levelFlag= "";
	// Constructors

	/** default constructor */
	public ItemTheme() {
	}

	/** minimal constructor */
	public ItemTheme(String code) {
		this.code = code;
	}

	/** full constructor */
	public ItemTheme(String name, ItemTheme parentItemTheme, String parentLevel,
			Set<Item> items) {
		this.name = name;
		this.parentItemTheme = parentItemTheme;
		this.parentLevel = parentLevel;
		this.items = items;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 20)
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
	public ItemTheme getParentItemTheme() {
		return this.parentItemTheme;
	}

	public void setParentItemTheme(ItemTheme parentItemTheme) {
		this.parentItemTheme = parentItemTheme;
	}

	@Column(name = "parent_level", length = 50)
	public String getParentLevel() {
		return this.parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	@ManyToMany(cascade=CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "item_theme_ref",
			joinColumns = @JoinColumn(name="item_theme_code"),
			inverseJoinColumns = @JoinColumn(name="item_id")
	)
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="parentItemTheme")	
	public List<ItemTheme> getChildrenItemThemes() {
		return childrenItemThemes;
	}

	public void setChildrenItemThemes(List<ItemTheme> childrenItemThemes) {
		this.childrenItemThemes = childrenItemThemes;
	}

	@Transient
	public String getLevelFlag() {
		if (this.parentItemTheme!=null) this.levelFlag = this.parentItemTheme.levelFlag + "----";
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	
	/** 生成parentLevel字段 */
	public void genparentLevel(){
		this.parentLevel = (this.parentItemTheme==null)?this.code:this.parentItemTheme.parentLevel+","+this.code;
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