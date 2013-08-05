package com.ambow.trainingengine.bug.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ReplyInfoTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "reply_info_template")
public class ReplyInfoTemplate implements java.io.Serializable {
	private static final long serialVersionUID = -1968618365342067062L;
	private Integer id;
	private String replyContent;
	private String tempalteName;
	private Integer replyPoint; 

	// Constructors

	/** default constructor */
	public ReplyInfoTemplate() {
	}

	/** minimal constructor */
	public ReplyInfoTemplate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public ReplyInfoTemplate(Integer id, String replyContent,
			String tempalteName) {
		this.id = id;
		this.replyContent = replyContent;
		this.tempalteName = tempalteName;
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

	@Column(name = "reply_content", length = 500)
	public String getReplyContent() {
		return this.replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	@Column(name = "tempalte_name", length = 50)
	public String getTempalteName() {
		return this.tempalteName;
	}

	public void setTempalteName(String tempalteName) {
		this.tempalteName = tempalteName;
	}
	
	@Column(name = "reply_point")
	public Integer getReplyPoint() {
		return replyPoint;
	}

	public void setReplyPoint(Integer replyPoint) {
		this.replyPoint = replyPoint;
	}
	
	 

}