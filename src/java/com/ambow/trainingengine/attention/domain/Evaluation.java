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
 * Evaluation entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "evaluation")
public class Evaluation implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2316013282619640430L;
	private Integer id;
	private Webuser webuser;
	private ItemAttention itemAttention;
	private Item item;
	private Integer evaluation;
	private Date insertTime;

	// Constructors

	/** default constructor */
	public Evaluation() {
	}

	/** full constructor */
	public Evaluation(Webuser webuser, ItemAttention itemAttention, Item item,
			Integer evaluation, Date insertTime) {
		this.webuser = webuser;
		this.itemAttention = itemAttention;
		this.item = item;
		this.evaluation = evaluation;
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

	@Column(name = "evaluation")
	public Integer getEvaluation() {
		return this.evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
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