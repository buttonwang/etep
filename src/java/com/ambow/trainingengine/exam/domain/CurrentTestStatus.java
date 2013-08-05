package com.ambow.trainingengine.exam.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ambow.studyflow.domain.NodeInstance;

/**
 * CurrentTestStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "current_test_status")
public class CurrentTestStatus implements java.io.Serializable {

	// Fields

	private Long nodeInstanceId;
	private NodeInstance asfNodeInstance;
	private Integer timeUsed=0;
	private Date startTime;
	private Date endTime;
	private Float score=0f;
	private Float masteryRate=0f;
	private Float accuracyRate=0f;
	private Integer sumCorrectItems=0;
	private Integer sumIncorrectItems=0;
	private Integer sumUnfinishedItems=0;
	private Integer sumZeroStarItems=0;
	private Integer sumHalfStarItems=0;
	private Integer sumOneStarItems=0;
	private Integer sumTwoStarItems=0;
	private Integer sumThreeStarItems=0;
	private Integer sumFourStarItems=0;
	private Integer sumFiveStarItems=0;
	private Integer pauseStatus;
	private Integer testStatus;
	private Float firstTestScore=0f;
	private Float firstTestAccuracyRate=0f;
	private Float firstTestMasteryRate=0f;
	
	private Date passedTime;
	private Float passedScore=0f;
	private Float passedAccuracyRate=0f;
	private Float passedMasteryRate=0f;
	// Constructors
	private Date firstTestTime;
	private Float paperAssemItemType;
	
	private Integer unsureMarkItems;
	
	private Integer totalScore;
	/** default constructor */
	
	private boolean isTest;
	
	private Integer timeUsedTotal;
	
	public CurrentTestStatus() {
	}

	/** minimal constructor */
	public CurrentTestStatus(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	/** full constructor */
	public CurrentTestStatus(NodeInstance asfNodeInstance, Integer timeUsed,
			Date startTime, Date endTime, Float score, Float masteryRate,
			Float accuracyRate, Integer sumCorrectItems,
			Integer sumIncorrectItems, Integer sumUnfinishedItems,
			Integer sumZeroStarItems, Integer sumHalfStarItems,
			Integer sumOneStarItems, Integer sumTwoStarItems,
			Integer sumThreeStarItems, Integer sumFourStarItems,
			Integer sumFiveStarItems, Integer pauseStatus, Integer testStatus) {
		this.asfNodeInstance = asfNodeInstance;
		this.timeUsed = timeUsed;
		this.startTime = startTime;
		this.endTime = endTime;
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
		this.pauseStatus = pauseStatus;
		this.testStatus = testStatus;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="asfNodeInstance"))
	@Column(name="node_instance_id")
	public Long getNodeInstanceId() {
		return this.nodeInstanceId;
	}

	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	
	@OneToOne(cascade=CascadeType.MERGE)
	@PrimaryKeyJoinColumn
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

	@Column(name="pause_status")
	public Integer getPauseStatus() {
		return this.pauseStatus;
	}

	public void setPauseStatus(Integer pauseStatus) {
		this.pauseStatus = pauseStatus;
	}

	@Column(name="test_status")
	public Integer getTestStatus() {
		return this.testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}
	
	@Column(name="first_test_score")
	public Float getFirstTestScore() {
		return firstTestScore;
	}

	public void setFirstTestScore(Float firstTestScore) {
		this.firstTestScore = firstTestScore;
	}
	@Column(name="first_test_accuracy_rate")
	public Float getFirstTestAccuracyRate() {
		return firstTestAccuracyRate;
	}

	public void setFirstTestAccuracyRate(Float firstTestAccuracyRate) {
		this.firstTestAccuracyRate = firstTestAccuracyRate;
	}
	@Column(name="first_test_mastery_rate")
	public Float getFirstTestMasteryRate() {
		return firstTestMasteryRate;
	}

	public void setFirstTestMasteryRate(Float firstTestMasteryRate) {
		this.firstTestMasteryRate = firstTestMasteryRate;
	}
	@Column(name="passed_time")
	public Date getPassedTime() {
		return passedTime;
	}

	public void setPassedTime(Date passedTime) {
		this.passedTime = passedTime;
	}
	@Column(name="passed_score")
	public Float getPassedScore() {
		return passedScore;
	}

	public void setPassedScore(Float passedScore) {
		this.passedScore = passedScore;
	}
	@Column(name="passed_accuracy_rate")
	public Float getPassedAccuracyRate() {
		return passedAccuracyRate;
	}

	public void setPassedAccuracyRate(Float passedAccuracyRate) {
		this.passedAccuracyRate = passedAccuracyRate;
	}
	@Column(name="passed_mastery_rate")
	public Float getPassedMasteryRate() {
		return passedMasteryRate;
	}

	public void setPassedMasteryRate(Float passedMasteryRate) {
		this.passedMasteryRate = passedMasteryRate;
	}
	@Column(name="first_test_time")
	public Date getFirstTestTime() {
		return firstTestTime;
	}

	public void setFirstTestTime(Date firstTestTime) {
		this.firstTestTime = firstTestTime;
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
	
	@Column(name="time_used_total")
	public Integer getTimeUsedTotal() {
		return timeUsedTotal;
	}

	public void setTimeUsedTotal(Integer timeUsedTotal) {
		this.timeUsedTotal = timeUsedTotal;
	}

	@Column(name="total_score")
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

}