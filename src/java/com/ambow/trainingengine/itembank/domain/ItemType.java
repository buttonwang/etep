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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ambow.trainingengine.exam.util.TypeOrder;

/**
 * ItemType entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item_type")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class ItemType implements java.io.Serializable,Comparable {

	private static final long serialVersionUID = 3717094262444258042L;
	
	// Fields
	private String code;
	private String name;	
	private Integer itemNumPerpage;
	private Subject subject;
	private Grade grade;
	private String projectVersion;
	private Set<Item> items = new HashSet<Item>(0);
	private Set<Paper> papers = new HashSet<Paper>(0);

	// Constructors

	/** default constructor */
	public ItemType() {
	}

	/** minimal constructor */
	public ItemType(String code) {
		this.code = code;
	}

	/** full constructor */
	public ItemType(String code,String name,int num, Set<Item> items, Set<Paper> papers) {
		this.code = code;
		this.name = name;
		this.items = items;
		this.papers = papers;
		this.itemNumPerpage = num;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "itemType")
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "item_type_paper_ref",
			joinColumns = @JoinColumn(name="item_type_code"),
			inverseJoinColumns = @JoinColumn(name="paper_id")
	)
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
	
	@Column(name="item_num_perpage")
	public Integer getItemNumPerpage() {
		return itemNumPerpage;
	}
	
	public void setItemNumPerpage(Integer itemNumPerpage) {
		this.itemNumPerpage = itemNumPerpage;
	}
	
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
	
	/*
	 * code为三位前两位为数字,后一位为字母
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if(o instanceof ItemType){
			String code2=((ItemType)o).getCode();
			int typeValue=TypeOrder.getOrderValue(code);
			int typeValue2=TypeOrder.getOrderValue(code2);
			if(typeValue>typeValue2){
				return 1;
			}
			if(typeValue<typeValue2){
				return -1;
			}

		}
		return 0;
	}

	@Column(name = "project_version")
	public String getProjectVersion() {
		return projectVersion;		
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}

}