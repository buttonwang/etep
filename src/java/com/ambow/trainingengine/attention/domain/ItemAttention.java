package com.ambow.trainingengine.attention.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.SimpleDateFormat;
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

import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * ItemAttention entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "item_attention")
public class ItemAttention implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 4720902498860514089L;
	private Integer id;
	private Webuser webuser;
	private Item item;
	private HistoryAnswerStatus historyAnswerStatus;
	private Date insertTime;
	private Integer state;
	private Set<LearnNote> learnNotes = new HashSet<LearnNote>(0);
	private Set<Evaluation> evaluations = new HashSet<Evaluation>(0);
	private Set<TagContent> tagContents = new HashSet<TagContent>(0);

	// Constructors

	/** default constructor */
	public ItemAttention() {
	}

	/** full constructor */
	public ItemAttention(Webuser webuser, Item item,
			HistoryAnswerStatus historyAnswerStatus, Date insertTime,
			Integer state, Set<LearnNote> learnNotes,
			Set<Evaluation> evaluations, Set<TagContent> tagContents) {
		this.webuser = webuser;
		this.item = item;
		this.historyAnswerStatus = historyAnswerStatus;
		this.insertTime = insertTime;
		this.state = state;
		this.learnNotes = learnNotes;
		this.evaluations = evaluations;
		this.tagContents = tagContents;
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
	@JoinColumn(name = "history_answer_status_id")
	public HistoryAnswerStatus getHistoryAnswerStatus() {
		return this.historyAnswerStatus;
	}

	public void setHistoryAnswerStatus(HistoryAnswerStatus historyAnswerStatus) {
		this.historyAnswerStatus = historyAnswerStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_time", length = 0)
	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	
	@Transient
	public String getInsertTimeFmt() {
		SimpleDateFormat formatter = new SimpleDateFormat ("yy-MM-dd HH:mm");
	    String ctime = formatter.format(this.insertTime); 
	    return ctime;
	}
	
	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemAttention")
	public Set<LearnNote> getLearnNotes() {
		return this.learnNotes;
	}

	public void setLearnNotes(Set<LearnNote> learnNotes) {
		this.learnNotes = learnNotes;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemAttention")
	public Set<Evaluation> getEvaluations() {
		return this.evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "itemAttention")
	public Set<TagContent> getTagContents() {
		return this.tagContents;
	}

	public void setTagContents(Set<TagContent> tagContents) {
		this.tagContents = tagContents;
	}

}