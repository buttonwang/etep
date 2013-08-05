package com.ambow.trainingengine.attention.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * TagContent entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "tag_content")
public class TagContent implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 3348365446066091394L;
	private Integer id;
	private Webuser webuser;
	private ItemAttention itemAttention;
	private Item item;
	private String tag;
	private Date insertTime;

	// Constructors

	/** default constructor */
	public TagContent() {
	}

	/** full constructor */
	public TagContent(Webuser webuser, ItemAttention itemAttention, Item item,
			String tag, Date insertTime) {
		this.webuser = webuser;
		this.itemAttention = itemAttention;
		this.item = item;
		this.tag = tag;
		this.insertTime = insertTime;
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
	@JoinColumn(name = "attention_id")
	public ItemAttention getItemAttention() {
		return this.itemAttention;
	}

	public void setItemAttention(ItemAttention itemAttention) {
		this.itemAttention = itemAttention;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "tag", length = 200)
	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_time", length = 0)
	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

}