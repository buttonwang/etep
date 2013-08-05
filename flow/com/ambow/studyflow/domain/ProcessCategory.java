package com.ambow.studyflow.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/*
 * ProcessCategory.java:流程定义分类
 * 
 * Created on 2008-5-30
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name="asf_category")
public class ProcessCategory {
	
	private long id;
	private String name;
	private ProcessCategory parentCategory;
	private List<ProcessCategory> childrenCategories;
	
	public ProcessCategory() {
	}	
	
	public ProcessCategory(String name) {
		super();
		this.name = name;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


    @ManyToOne
	@JoinColumn(name = "parent_category_id")
	public ProcessCategory getParentCategory() {
		return parentCategory;
	}
	public void setParentCategory(ProcessCategory parentCategory) {
		this.parentCategory = parentCategory;
	}
	
	@OneToMany(mappedBy="parentCategory",
            cascade=CascadeType.REMOVE, 
            fetch=FetchType.LAZY)
	public List<ProcessCategory> getChildrenCategories() {
		return childrenCategories;
	}
	public void setChildrenCategories(List<ProcessCategory> childrenCategories) {
		this.childrenCategories = childrenCategories;
	}
	
}
