package com.ambow.trainingengine.attention.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * ItemTag entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "item_tag")
public class ItemTag implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -4984449664794821236L;
	private Integer id;
	private Webuser webuser;
	private Item item;
	private Tag tag;

	// Constructors

	/** default constructor */
	public ItemTag() {
	}

	/** minimal constructor */
	public ItemTag(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ItemTag(Integer id, Webuser webuser, Item item, Tag tag) {
		this.id = id;
		this.webuser = webuser;
		this.item = item;
		this.tag = tag;
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
	public Webuser getWebuser() {
		return this.webuser;
	}

	public void setWebuser(Webuser webuser) {
		this.webuser = webuser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	public Tag getTag() {
		return this.tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

}