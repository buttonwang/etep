package com.ambow.trainingengine.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/**
 * CurrentAnswersStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "current_answers_status")
public class CurrentAnswersStatus implements java.io.Serializable {

	private static final long serialVersionUID = 5345404421421522769L;
	// Fields
	private Integer id;
	private Item item;
	private NodeInstance asfNodeInstance;
	private SubItem subItem;
	private String answer;
	private boolean isCorrect;	
	private Float starGrade;
	private boolean isUnsureMarking;
	private Float score;
	private Integer upgradeRate;
	private Integer continueCorrectTimes;
	private Integer continueFailureTimes;
	private Integer doTimes;
	private String correctAnswer;
	private String answerAnalysis;
	private String answerOptionOrder;
	private Integer dataSource;
	private String itemScore2;

	private boolean isDone; //已做
	// Constructors

	/** default constructor */
	public CurrentAnswersStatus() {
	}

	/** minimal constructor */
	public CurrentAnswersStatus(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	/** full constructor */
	public CurrentAnswersStatus(Item item, NodeInstance asfNodeInstance,
			SubItem subItem, String answer, boolean isCorrect, Float starGrade,
			boolean isUnsureMarking, Float score, Integer upgradeRate,
			Integer continueCorrectTimes, String correctAnswer,
			String answerAnalysis, String answerOptionOrder, String itemScore2) {
		this.item = item;
		this.asfNodeInstance = asfNodeInstance;
		this.subItem = subItem;
		this.answer = answer;
		this.isCorrect = isCorrect;
		this.starGrade = starGrade;
		this.isUnsureMarking = isUnsureMarking;
		this.score = score;
		this.upgradeRate = upgradeRate;
		this.continueCorrectTimes = continueCorrectTimes;
		this.correctAnswer = correctAnswer;
		this.answerAnalysis = answerAnalysis;
		this.answerOptionOrder = answerOptionOrder;
		this.itemScore2 = itemScore2;
	}

	// Property accessors
	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	@ManyToOne
	@JoinColumn(name="node_instance_id")
	public NodeInstance getAsfNodeInstance() {
		return this.asfNodeInstance;
	}

	public void setAsfNodeInstance(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_item_id")
	public SubItem getSubItem() {
		return this.subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
	}
	@Column(name="answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name="is_correct")
	public boolean getIsCorrect() {
		return this.isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	@Column(name="star_grade")
	public Float getStarGrade() {
		return this.starGrade;
	}

	public void setStarGrade(Float starGrade) {
		this.starGrade = starGrade;
	}
	
	@Column(name="is_unsure_marking")
	public boolean getIsUnsureMarking() {
		return this.isUnsureMarking;
	}

	public void setIsUnsureMarking(boolean isUnsureMarking) {
		this.isUnsureMarking = isUnsureMarking;
	}

	@Column(name="score")
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name="upgrade_rate")
	public Integer getUpgradeRate() {
		return this.upgradeRate;
	}

	public void setUpgradeRate(Integer upgradeRate) {
		this.upgradeRate = upgradeRate;
	}
	
	@Column(name="continue_correct_times")
	public Integer getContinueCorrectTimes() {
		return this.continueCorrectTimes;
	}

	public void setContinueCorrectTimes(Integer continueCorrectTimes) {
		this.continueCorrectTimes = continueCorrectTimes;
	}

	@Column(name="correct_answer")
	public String getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Column(name="answer_analysis")
	public String getAnswerAnalysis() {
		return this.answerAnalysis;
	}

	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}

	@Column(name="answer_option_order")
	public String getAnswerOptionOrder() {
		return this.answerOptionOrder;
	}

	public void setAnswerOptionOrder(String answerOptionOrder) {
		this.answerOptionOrder = answerOptionOrder;
	}

	@Column(name="continue_failure_times")
	public Integer getContinueFailureTimes() {
		return continueFailureTimes;
	}

	public void setContinueFailureTimes(Integer continueFailureTimes) {
		this.continueFailureTimes = continueFailureTimes;
	}
	
	@Column(name="do_times")
	public Integer getDoTimes() {
		return doTimes;
	}

	public void setDoTimes(Integer doTimes) {
		this.doTimes = doTimes;
	}
	
	@Column(name="data_source")
	public Integer getDataSource() {
		return dataSource;
	}

	public void setDataSource(Integer dataSource) {
		this.dataSource = dataSource;
	}
	
	@Column(name="item_score2")
	public String getItemScore2() {
		return itemScore2;
	}

	public void setItemScore2(String itemScore2) {
		this.itemScore2 = itemScore2;
	}
	
	@Column(name="is_done")
	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	

}