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

import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * LearnNoteVote entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "learn_note_vote")
public class LearnNoteVote implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = -7102186870217239675L;
	private Integer id;
	private Webuser webuser;
	private LearnNote learnNote;
	private Integer vote;
	private Date voteTime;

	// Constructors

	/** default constructor */
	public LearnNoteVote() {
	}

	/** full constructor */
	public LearnNoteVote(Webuser webuser, LearnNote learnNote, Integer vote,
			Date voteTime) {
		this.webuser = webuser;
		this.learnNote = learnNote;
		this.vote = vote;
		this.voteTime = voteTime;
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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public Webuser getWebuser() {
		return this.webuser;
	}

	public void setWebuser(Webuser webuser) {
		this.webuser = webuser;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "learn_note_id", nullable = false)
	public LearnNote getLearnNote() {
		return this.learnNote;
	}

	public void setLearnNote(LearnNote learnNote) {
		this.learnNote = learnNote;
	}

	@Column(name = "vote", nullable = false)
	public Integer getVote() {
		return this.vote;
	}

	public void setVote(Integer vote) {
		this.vote = vote;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "vote_time", nullable = false, length = 0)
	public Date getVoteTime() {
		return this.voteTime;
	}

	public void setVoteTime(Date voteTime) {
		this.voteTime = voteTime;
	}

}