package com.ambow.trainingengine.attention.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Tag entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "tag")
public class Tag implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2001615091610014735L;
	private Integer id;
	private String tag;
	private Integer num;
	private Set<ItemTag> itemTags = new HashSet<ItemTag>(0);

	// Constructors

	/** default constructor */
	public Tag() {
	}

	/** full constructor */
	public Tag(String tag, Integer num, Set<ItemTag> itemTags) {
		this.tag = tag;
		this.num = num;
		this.itemTags = itemTags;
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

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Column(name = "num")
	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tag")
	public Set<ItemTag> getItemTags() {
		return this.itemTags;
	}

	public void setItemTags(Set<ItemTag> itemTags) {
		this.itemTags = itemTags;
	}

}