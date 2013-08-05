package com.ambow.trainingengine.attention.domain;

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

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;

/**
 * LearnNote entity. @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "learn_note")
public class LearnNote implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 750485204731042077L;
	private Integer id;
	private Webuser webuser;
	private ItemAttention itemAttention;
	private Item item;
	private Long answerId;
	private String content;
	private Integer isShare;
	private Integer flowerNum=0;
	private Integer eggNum=0;
	private Date insertTime;
	private Integer state;
	private Set<LearnNoteVote> learnNoteVotes = new HashSet<LearnNoteVote>(0);

	private boolean voted = false;
	// Constructors

	/** default constructor */
	public LearnNote() {
	}

	/** full constructor */
	public LearnNote(Webuser webuser, ItemAttention itemAttention, Item item,
			Long answerId, String content, Integer isShare,
			Integer flowerNum, Integer eggNum, Date insertTime,
			Integer state, Set<LearnNoteVote> learnNoteVotes) {
		this.webuser = webuser;
		this.itemAttention = itemAttention;
		this.item = item;
		this.answerId = answerId;
		this.content = content;
		this.isShare = isShare;
		this.flowerNum = flowerNum;
		this.eggNum = eggNum;
		this.insertTime = insertTime;
		this.state = state;
		this.learnNoteVotes = learnNoteVotes;
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

	@Column(name = "answer_id")
	public Long getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "is_share")
	public Integer getIsShare() {
		return this.isShare;
	}

	public void setIsShare(Integer isShare) {
		this.isShare = isShare;
	}

	@Column(name = "flower_num")
	public Integer getFlowerNum() {
		return this.flowerNum;
	}

	public void setFlowerNum(Integer flowerNum) {
		this.flowerNum = flowerNum;
	}

	@Column(name = "egg_num")
	public Integer getEggNum() {
		return this.eggNum;
	}

	public void setEggNum(Integer eggNum) {
		this.eggNum = eggNum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "insert_time", length = 0)
	public Date getInsertTime() {
		return this.insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "learnNote")
	public Set<LearnNoteVote> getLearnNoteVotes() {
		return this.learnNoteVotes;
	}

	public void setLearnNoteVotes(Set<LearnNoteVote> learnNoteVotes) {
		this.learnNoteVotes = learnNoteVotes;
	}
	
	@Transient
	public boolean getVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

}