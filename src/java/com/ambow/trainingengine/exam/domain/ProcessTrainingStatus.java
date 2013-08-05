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

import com.ambow.studyflow.domain.ProcessInstance;

/**
 * ProcessTrainingStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "process_training_status")
public class ProcessTrainingStatus implements java.io.Serializable {
	
	private static final long serialVersionUID = 3183019813592289133L;
	
	// Fields
	private Long processInstanceId;
	private ProcessInstance asfProcessInstance;
	private String grade;
	private String classNum;
	private String module;
	private Float totalMasteryRate;
	private Float totalAccuracyRate;
	private Float learningProcessRate;
	private Float totalScore;
	private Integer totalItemsNum;
	private Integer totalTrainingTime;
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
	private Date firstTrainingTime;
	private Date lastTrainingTime;
	private Integer trainingTimes;
	private Integer unsureMarkItems;
	
	private String skins;
	private String layout1;
	private String layout2;
	private String layout3;
	private String font;
	private String backgroundColor;
	private String foreColor;
	/**自定义考试时间*/
	private Date customerExamTime;
	/*是否做过公式编辑器录入训练*/
	private boolean isFormulatorTest;
	@Column(name = "customer_exam_time")
	public Date getCustomerExamTime() {
		return customerExamTime;
	}

	public void setCustomerExamTime(Date customerExamTime) {
		this.customerExamTime = customerExamTime;
	}
	
	// Constructors

	/** default constructor */
	public ProcessTrainingStatus() {
	}

	/** minimal constructor */
	public ProcessTrainingStatus(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	/** full constructor */
	public ProcessTrainingStatus(ProcessInstance asfProcessInstance,
			String grade, String class_, String module, Float totalMasteryRate,
			Float totalAccuracyRate, Float learningProcessRate,
			Float totalScore, Integer totalItemsNum, Integer totalTrainingTime,
			Integer sumCorrectItems, Integer sumIncorrectItems,
			Integer sumUnfinishedItems, Integer sumZeroStarItems,
			Integer sumHalfStarItems, Integer sumOneStarItems,
			Integer sumTwoStarItems, Integer sumThreeStarItems,
			Integer sumFourStarItems, Integer sumFiveStarItems) {
		this.asfProcessInstance = asfProcessInstance;
		this.grade = grade;
		this.classNum = class_;
		this.module = module;
		this.totalMasteryRate = totalMasteryRate;
		this.totalAccuracyRate = totalAccuracyRate;
		this.learningProcessRate = learningProcessRate;
		this.totalScore = totalScore;
		this.totalItemsNum = totalItemsNum;
		this.totalTrainingTime = totalTrainingTime;
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
	}

	// Property accessors

	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="asfProcessInstance"))
	@Column(name="process_instance_id")
	public Long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}

	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}
	@Column(name="grade")
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	

	@Column(name="module")
	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	@Column(name="total_mastery_rate")
	public Float getTotalMasteryRate() {
		return this.totalMasteryRate;
	}

	public void setTotalMasteryRate(Float totalMasteryRate) {
		this.totalMasteryRate = totalMasteryRate;
	}

	@Column(name="total_accuracy_rate")
	public Float getTotalAccuracyRate() {
		return this.totalAccuracyRate;
	}

	public void setTotalAccuracyRate(Float totalAccuracyRate) {
		this.totalAccuracyRate = totalAccuracyRate;
	}
	
	@Column(name="learning_process_rate")
	public Float getLearningProcessRate() {
		return this.learningProcessRate;
	}

	public void setLearningProcessRate(Float learningProcessRate) {
		this.learningProcessRate = learningProcessRate;
	}

	@Column(name="total_score")
	public Float getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}

	@Column(name="total_items_num")
	public Integer getTotalItemsNum() {
		return this.totalItemsNum;
	}

	public void setTotalItemsNum(Integer totalItemsNum) {
		this.totalItemsNum = totalItemsNum;
	}
	
	@Column(name="total_training_time")
	public Integer getTotalTrainingTime() {
		return this.totalTrainingTime;
	}

	public void setTotalTrainingTime(Integer totalTrainingTime) {
		this.totalTrainingTime = totalTrainingTime;
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

	@Column(name="class_num")
	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	@Column(name="first_training_time")
	public Date getFirstTrainingTime() {
		return firstTrainingTime;
	}

	public void setFirstTrainingTime(Date firstTrainingTime) {
		this.firstTrainingTime = firstTrainingTime;
	}

	@Column(name="last_training_time")
	public Date getLastTrainingTime() {
		return lastTrainingTime;
	}

	public void setLastTrainingTime(Date lastTrainingTime) {
		this.lastTrainingTime = lastTrainingTime;
	}

	@Column(name="training_times")
	public Integer getTrainingTimes() {
		return trainingTimes;
	}

	public void setTrainingTimes(Integer trainingTimes) {
		this.trainingTimes = trainingTimes;
	}
	@Column(name="unsure_mark_items")
	public Integer getUnsureMarkItems() {
		return unsureMarkItems;
	}

	public void setUnsureMarkItems(Integer unsureMarkItems) {
		this.unsureMarkItems = unsureMarkItems;
	}
	@Column(name="skins")
	public String getSkins() {
		return skins;
	}

	public void setSkins(String skins) {
		this.skins = skins;
	}
	@Column(name="layout1")
	public String getLayout1() {
		return layout1;
	}

	public void setLayout1(String layout1) {
		this.layout1 = layout1;
	}
	@Column(name="layout2")
	public String getLayout2() {
		return layout2;
	}

	public void setLayout2(String layout2) {
		this.layout2 = layout2;
	}
	@Column(name="layout3")
	public String getLayout3() {
		return layout3;
	}

	public void setLayout3(String layout3) {
		this.layout3 = layout3;
	}
	@Column(name="font")
	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}
	@Column(name="background_color")
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	@Column(name="fore_color")
	public String getForeColor() {
		return foreColor;
	}

	public void setForeColor(String foreColor) {
		this.foreColor = foreColor;
	}

	@Column(name="is_formulator_test",nullable=false)
	public boolean isFormulatorTest() {
		return isFormulatorTest;
	}

	public void setFormulatorTest(boolean isFormulatorTest) {
		this.isFormulatorTest = isFormulatorTest;
	}
}