package com.ambow.trainingengine.bug.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;

/**
 * BugInfoHistoryAnswerStatus entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bug_info_history_answer_status")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class BugInfoHistoryAnswerStatus implements java.io.Serializable {
	private static final long serialVersionUID = -4543877023510240535L;
	private Long id;
	private BugInfo bugInfo;
	private HistoryAnswerStatus historyAnswerStatus;
	private String positionInfo;

	// Constructors

	/** default constructor */
	public BugInfoHistoryAnswerStatus() {
	}

	/** full constructor */
	public BugInfoHistoryAnswerStatus(BugInfo bugInfo,
			HistoryAnswerStatus historyAnswerStatus, String positionInfo) {
		this.bugInfo = bugInfo;
		this.historyAnswerStatus = historyAnswerStatus;
		this.positionInfo = positionInfo;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bug_info_id")
	public BugInfo getBugInfo() {
		return this.bugInfo;
	}

	public void setBugInfo(BugInfo bugInfo) {
		this.bugInfo = bugInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "history_answer_status_id")
	public HistoryAnswerStatus getHistoryAnswerStatus() {
		return this.historyAnswerStatus;
	}

	public void setHistoryAnswerStatus(HistoryAnswerStatus historyAnswerStatus) {
		this.historyAnswerStatus = historyAnswerStatus;
	}

	@Column(name = "positionInfo", length = 30)
	public String getPositionInfo() {
		return this.positionInfo;
	}

	public void setPositionInfo(String positionInfo) {
		this.positionInfo = positionInfo;
	}

}