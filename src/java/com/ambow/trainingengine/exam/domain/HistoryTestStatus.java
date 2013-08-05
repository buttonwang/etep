package com.ambow.trainingengine.exam.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.util.ExamUtil;

/**
 * HistoryTestStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "history_test_status")
public class HistoryTestStatus implements java.io.Serializable {
	
	private static final long serialVersionUID = -9068126242754871526L;
	
	// Fields
	private Integer id;
	private NodeInstance asfNodeInstance;
	private Integer timeUsed;
	private Date startTime;
	private Date endTime;
	private Float requireAccuracyRate;
	private Float score;
	private Float masteryRate;
	private Float accuracyRate;
	private Integer sumCorrectItems;
	private Integer sumIncorrectItems;
	private Integer sumUnfinishedItems;
	private Integer sumZeroStarItems;
	private Integer sumHalfStarItems;
	private Integer sumOneStarItems;
	private Integer sumTwoStarItems;
	private Integer sumThreeStarItems;
	private Integer sumFourStarItems;
	private Integer sumFiveStarItems;
	private List<HistoryAnswerStatus> historyAnswerStatuses = new ArrayList<HistoryAnswerStatus>(0);

	private Integer testStatus;
	private Float paperAssemItemType;
	private Integer unsureMarkItems;
	private Integer totalScore;
	private boolean isTest;
	// Constructors

	/** default constructor */
	public HistoryTestStatus() {
	}

	/** minimal constructor */
	public HistoryTestStatus(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	/** full constructor */
	public HistoryTestStatus(NodeInstance asfNodeInstance, Integer timeUsed,
			Date startTime, Date endTime, Float score, Float masteryRate, Float requireAccuracyRate,
			Float accuracyRate, Integer sumCorrectItems,
			Integer sumIncorrectItems, Integer sumUnfinishedItems,
			Integer sumZeroStarItems, Integer sumHalfStarItems,
			Integer sumOneStarItems, Integer sumTwoStarItems,
			Integer sumThreeStarItems, Integer sumFourStarItems,
			Integer sumFiveStarItems, List<HistoryAnswerStatus> historyAnswerStatuses) {
		this.asfNodeInstance = asfNodeInstance;
		this.timeUsed = timeUsed;
		this.startTime = startTime;
		this.endTime = endTime;
		this.requireAccuracyRate = requireAccuracyRate;
		this.score = score;
		this.masteryRate = masteryRate;
		this.accuracyRate = accuracyRate;
		this.sumCorrectItems = sumCorrectItems;
		this.sumIncorrectItems = sumIncorrectItems;
		this.sumUnfinishedItems = sumUnfinishedItems;
		this.sumZeroStarItems = sumZeroStarItems;
		this.sumHalfStarItems = sumHalfStarItems;
		this.sumOneStarItems = sumOneStarItems;
		this.sumTwoStarItems = sumTwoStarItems;
		this.sumThreeStarItems = sumThreeStarItems;
		this.sumFourStarItems = sumFourStarItems;
		this.sumFiveStarItems = sumFiveStarItems;
		this.historyAnswerStatuses = historyAnswerStatuses;
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

	@ManyToOne
	@JoinColumn(name="node_instance_id")
	@Cascade(org.hibernate.annotations.CascadeType.MERGE)
	public NodeInstance getAsfNodeInstance() {
		return this.asfNodeInstance;
	}

	public void setAsfNodeInstance(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	@Column(name="time_used")
	public Integer getTimeUsed() {
		return this.timeUsed;
	}

	public void setTimeUsed(Integer timeUsed) {
		this.timeUsed = timeUsed;
	}

	@Column(name="start_time")
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="end_time")
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="require_accuracy_rate")
	public Float getRequireAccuracyRate() {
		return requireAccuracyRate;
	}

	public void setRequireAccuracyRate(Float requireAccuracyRate) {
		this.requireAccuracyRate = requireAccuracyRate;
	}
	
	@Column(name="score")
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name="mastery_rate")
	public Float getMasteryRate() {
		return this.masteryRate;
	}

	public void setMasteryRate(Float masteryRate) {
		this.masteryRate = masteryRate;
	}

	@Column(name="accuracy_rate")
	public Float getAccuracyRate() {
		return this.accuracyRate;
	}

	public void setAccuracyRate(Float accuracyRate) {
		this.accuracyRate = accuracyRate;
	}

	@Column(name="sum_correct_items")
	public Integer getSumCorrectItems() {
		return this.sumCorrectItems;
	}

	public void setSumCorrectItems(Integer sumCorrectItems) {
		this.sumCorrectItems = sumCorrectItems;
	}

	@Column(name="sum_incorrect_items")
	public Integer getSumIncorrectItems() {
		return this.sumIncorrectItems;
	}

	public void setSumIncorrectItems(Integer sumIncorrectItems) {
		this.sumIncorrectItems = sumIncorrectItems;
	}

	@Column(name="sum_unfinished_items")
	public Integer getSumUnfinishedItems() {
		return this.sumUnfinishedItems;
	}

	public void setSumUnfinishedItems(Integer sumUnfinishedItems) {
		this.sumUnfinishedItems = sumUnfinishedItems;
	}

	@Column(name="sum_zero_star_items")
	public Integer getSumZeroStarItems() {
		return this.sumZeroStarItems;
	}

	public void setSumZeroStarItems(Integer sumZeroStarItems) {
		this.sumZeroStarItems = sumZeroStarItems;
	}

	@Column(name="sum_half_star_items")
	public Integer getSumHalfStarItems() {
		return this.sumHalfStarItems;
	}

	public void setSumHalfStarItems(Integer sumHalfStarItems) {
		this.sumHalfStarItems = sumHalfStarItems;
	}

	@Column(name="sum_one_star_items")
	public Integer getSumOneStarItems() {
		return this.sumOneStarItems;
	}

	public void setSumOneStarItems(Integer sumOneStarItems) {
		this.sumOneStarItems = sumOneStarItems;
	}

	@Column(name="sum_two_star_items")
	public Integer getSumTwoStarItems() {
		return this.sumTwoStarItems;
	}

	public void setSumTwoStarItems(Integer sumTwoStarItems) {
		this.sumTwoStarItems = sumTwoStarItems;
	}

	@Column(name="sum_three_star_items")
	public Integer getSumThreeStarItems() {
		return this.sumThreeStarItems;
	}

	public void setSumThreeStarItems(Integer sumThreeStarItems) {
		this.sumThreeStarItems = sumThreeStarItems;
	}

	@Column(name="sum_four_star_items")
	public Integer getSumFourStarItems() {
		return this.sumFourStarItems;
	}

	public void setSumFourStarItems(Integer sumFourStarItems) {
		this.sumFourStarItems = sumFourStarItems;
	}

	@Column(name="sum_five_star_items")
	public Integer getSumFiveStarItems() {
		return this.sumFiveStarItems;
	}

	public void setSumFiveStarItems(Integer sumFiveStarItems) {
		this.sumFiveStarItems = sumFiveStarItems;
	}

	@OneToMany(cascade={CascadeType.ALL},mappedBy="historyTestStatus",fetch = FetchType.EAGER)
	//@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	public List<HistoryAnswerStatus> getHistoryAnswerStatuses() {
		return this.historyAnswerStatuses;
	}

	public void setHistoryAnswerStatuses(List<HistoryAnswerStatus> historyAnswerStatuses) {
		this.historyAnswerStatuses = historyAnswerStatuses;
	}
	@Column(name="test_status")
	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}
	
	@Column(name="paper_assem_item_type")
	public Float getPaperAssemItemType() {
		return paperAssemItemType;
	}

	public void setPaperAssemItemType(Float paperAssemItemType) {
		this.paperAssemItemType = paperAssemItemType;
	}

	@Column(name="unsure_mark_items")
	public Integer getUnsureMarkItems() {
		return unsureMarkItems;
	}

	public void setUnsureMarkItems(Integer unsureMarkItems) {
		this.unsureMarkItems = unsureMarkItems;
	}
	
	@Column(name="is_tested")
	public boolean getIsTest() {
		return isTest;
	}

	public void setIsTest(boolean isTest) {
		this.isTest = isTest;
	}
	
	@Column(name="total_score")
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	@Transient
	public String getPaperAssemItemTypeName() {
		String assemName = ExamUtil.getScopeCHName(paperAssemItemType)+"重练";
		if ((testStatus==4)&&(isTest==false)) assemName = "拓展练习";
		return assemName;
	}
}