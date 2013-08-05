package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/*
 * ItemRevise.java
 * 
 * Created on 2010-3-4 上午10:16:10
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */


@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "item_revise")
public class ItemRevise implements java.io.Serializable {

	private static final long serialVersionUID = 6737666157605402077L;

	private Integer id;
	private Item item;
	private Integer reviser;
	private Date reviseTime;
	private String reviseRecord = "";
	private Date reviseRecordTime;
	private String reviseReply = "";
	private Integer replyType;
	private Integer reviseReplyer;
	private Date reviseReplyTime;
	private Integer reviseStatus = 0;
	private Date revisedTime;
	private String remark;
	
	private Set<ItemReviseAnswers> itemReviseAnswers = new HashSet<ItemReviseAnswers>(0);

	public ItemRevise() {
	}
		
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
	@Column(name="reviser")
	public Integer getReviser() {
		return reviser;
	}

	public void setReviser(Integer reviser) {
		this.reviser = reviser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="revise_time")
	public Date getReviseTime() {
		return reviseTime;
	}

	public void setReviseTime(Date reviseTime) {
		this.reviseTime = reviseTime;
	}

	@Column(name="revise_record")
	public String getReviseRecord() {
		return reviseRecord==null?"":reviseRecord;
	}

	public void setReviseRecord(String reviseRecord) {
		this.reviseRecord = reviseRecord;
	}
	
	@Column(name="revise_record_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReviseRecordTime() {
		return reviseRecordTime;
	}

	public void setReviseRecordTime(Date reviseRecordTime) {
		this.reviseRecordTime = reviseRecordTime;
	}

	@Column(name="revise_reply")
	public String getReviseReply() {
		return reviseReply==null?"":reviseReply;
	}

	public void setReviseReply(String reviseReply) {
		this.reviseReply = reviseReply;
	}

	@Column(name="reply_type")
	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	
	@Column(name="revise_replyer")
	public Integer getReviseReplyer() {
		return reviseReplyer;
	}

	public void setReviseReplyer(Integer reviseReplyer) {
		this.reviseReplyer = reviseReplyer;
	}
	
	@Column(name="revise_reply_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReviseReplyTime() {
		return reviseReplyTime;
	}

	public void setReviseReplyTime(Date reviseReplyTime) {
		this.reviseReplyTime = reviseReplyTime;
	}

	@Column(name="revise_status")
	public Integer getReviseStatus() {
		return reviseStatus;
	}

	public void setReviseStatus(Integer reviseStatus) {
		this.reviseStatus = reviseStatus;
	}

	@Column(name="revised_time")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getRevisedTime() {
		return revisedTime;
	}

	public void setRevisedTime(Date revisedTime) {
		this.revisedTime = revisedTime;
	}
	
	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemRevise")
	public Set<ItemReviseAnswers> getItemReviseAnswers() {
		return itemReviseAnswers;
	}

	public void setItemReviseAnswers(Set<ItemReviseAnswers> itemReviseAnswers) {
		this.itemReviseAnswers = itemReviseAnswers;
	}
	
	@Transient
	public String getReviseStatusName() {
		return this.reviseStatus==0?"未通过":"已通过";
	}

	@Transient
	public String getReplyTypeName() {
		String r = "";
		if (replyType == null) r = "";
		else {
			switch (replyType.intValue()) {
			case 0: r ="尚未回复"; break;
			case 1: r ="纠正了一个严重错误"; break;
			case 2: r ="纠正了一个一般错误"; break;
			case 3: r ="纠正了一个细微错误"; break;
			case 4: r ="纠错无效,试题无误"; break;
			case 5: r ="恶意纠错"; break;
			default: r =""; break;
			}
		}	
		return r;
	}
	
	@Transient
	public String getReplyTypeName2() {
		String r = "";
		if (replyType == null) r = "";
		else if (replyType == 0) 
			r = "";
		else if (replyType < 4)
			r ="有效纠错";
		else 
			r ="无效纠错";
		
		return r;
	}
	
}