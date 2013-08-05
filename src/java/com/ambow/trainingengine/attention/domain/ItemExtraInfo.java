package com.ambow.trainingengine.attention.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;

/**
 * ItemExtraInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "item_extra_info")
public class ItemExtraInfo implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -6257968712076540802L; 
	private Integer id;
	private Item item;
	private SysUser sysUser;
	private Long popularity;
	private Long noteAmount;
	private Double positiveRate;
	private String noteSummary;
	private String devote;
	private Date insertTime;

	// Constructors

	/** default constructor */
	public ItemExtraInfo() {
	}

	/** minimal constructor */
	public ItemExtraInfo(Integer id, Item item) {
		this.id = id;
		this.item = item;
	}

	/** full constructor */
	public ItemExtraInfo(Integer id, Item item, SysUser sysUser,
			Long popularity, Long noteAmount, Double positiveRate,
			String noteSummary, String devote, Date insertTime) {
		this.id = id;
		this.item = item;
		this.sysUser = sysUser;
		this.popularity = popularity;
		this.noteAmount = noteAmount;
		this.positiveRate = positiveRate;
		this.noteSummary = noteSummary;
		this.devote = devote;
		this.insertTime = insertTime;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id", unique = true, nullable = false, insertable = false, updatable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id")
	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	@Column(name = "popularity")
	public Long getPopularity() {
		return this.popularity;
	}

	public void setPopularity(Long popularity) {
		this.popularity = popularity;
	}

	@Column(name = "note_amount")
	public Long getNoteAmount() {
		return this.noteAmount;
	}

	public void setNoteAmount(Long noteAmount) {
		this.noteAmount = noteAmount;
	}

	@Column(name = "positive_rate", precision = 10)
	public Double getPositiveRate() {
		return this.positiveRate;
	}

	public void setPositiveRate(Double positiveRate) {
		this.positiveRate = positiveRate;
	}

	@Column(name = "note_summary", length = 65535)
	public String getNoteSummary() {
		return this.noteSummary;
	}

	public void setNoteSummary(String noteSummary) {
		this.noteSummary = noteSummary;
	}

	@Column(name = "devote", length = 200)
	public String getDevote() {
		return this.devote;
	}

	public void setDevote(String devote) {
		this.devote = devote;
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