package com.ambow.trainingengine.policy.domain;

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

import com.ambow.studyflow.domain.ProcessDefinition;

/**
 * ProcessPolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "process_policy")
public class ProcessPolicy implements java.io.Serializable {

	// Fields

	private Long processId;
	private ProcessDefinition asfProcessDefinition;
	private Integer reloginSameDayPolicy;
	private Integer reloginAnotherDayPolicy;
	private Integer freshWordSum;
	private String suggestTrainingTime;
	private String studyDirection;

	private Integer hasStudyGuide;
	private Integer defaultTestTime;
	private Integer isShowAnswer;
	private Integer isUsedDms;
	private String projectVersion;//考研为"ky"，数理化为"mpc"
	private String studyGuideCodes;
	
	/**考试时间*/
	private Date defaultExamTime;
	@Column(name = "default_exam_time")
	public Date getDefaultExamTime() {
		return defaultExamTime;
	}

	public void setDefaultExamTime(Date defaultExamTime) {
		this.defaultExamTime = defaultExamTime;
	}
	/**学习信息*/
	private String studyInfo;
	@Column(name = "study_info")
	public String getStudyInfo() {
		return studyInfo;
	}

	public void setStudyInfo(String studyInfo) {
		this.studyInfo = studyInfo;
	}
	 
	
	// Constructors

	/** default constructor */
	public ProcessPolicy() {
	}

	/** minimal constructor */
	public ProcessPolicy(ProcessDefinition asfProcessDefinition) {
		this.asfProcessDefinition = asfProcessDefinition;
	}

	/** full constructor */
	public ProcessPolicy( 
			ProcessDefinition asfProcessDefinition,
			Integer reloginSameDayPolicy, Integer reloginAnotherDayPolicy,
			Integer freshWordSum, String suggestTrainingTime,
			String studyDirection) {
		 
		this.asfProcessDefinition = asfProcessDefinition;
		this.reloginSameDayPolicy = reloginSameDayPolicy;
		this.reloginAnotherDayPolicy = reloginAnotherDayPolicy;
		this.freshWordSum = freshWordSum;
		this.suggestTrainingTime = suggestTrainingTime;
		this.studyDirection = studyDirection;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="asfProcessDefinition"))
	@Column(name="process_id")
	public Long getProcessId() {
		return this.processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}
	
	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public ProcessDefinition getAsfProcessDefinition() {
		return this.asfProcessDefinition;
	}

	public void setAsfProcessDefinition(
			ProcessDefinition asfProcessDefinition) {
		this.asfProcessDefinition = asfProcessDefinition;
	}

	@Column(name = "relogin_same_day_policy")
	public Integer getReloginSameDayPolicy() {
		return this.reloginSameDayPolicy;
	}

	public void setReloginSameDayPolicy(Integer reloginSameDayPolicy) {
		this.reloginSameDayPolicy = reloginSameDayPolicy;
	}

	@Column(name = "relogin_another_day_policy")
	public Integer getReloginAnotherDayPolicy() {
		return this.reloginAnotherDayPolicy;
	}

	public void setReloginAnotherDayPolicy(Integer reloginAnotherDayPolicy) {
		this.reloginAnotherDayPolicy = reloginAnotherDayPolicy;
	}

	@Column(name = "fresh_word_sum")
	public Integer getFreshWordSum() {
		return this.freshWordSum;
	}

	public void setFreshWordSum(Integer freshWordSum) {
		this.freshWordSum = freshWordSum;
	}

	@Column(name = "suggest_training_time", length = 50)
	public String getSuggestTrainingTime() {
		return this.suggestTrainingTime;
	}

	public void setSuggestTrainingTime(String suggestTrainingTime) {
		this.suggestTrainingTime = suggestTrainingTime;
	}

	@Column(name = "study_direction", length = 65535)
	public String getStudyDirection() {
		return this.studyDirection;
	}

	public void setStudyDirection(String studyDirection) {
		this.studyDirection = studyDirection;
	}

	@Column(name = "has_study_guide")
	public Integer getHasStudyGuide() {
		return hasStudyGuide;
	}

	public void setHasStudyGuide(Integer hasStudyGuide) {
		this.hasStudyGuide = hasStudyGuide;
	}

	@Column(name = "default_test_time")
	public Integer getDefaultTestTime() {
		return defaultTestTime;
	}

	public void setDefaultTestTime(Integer defaultTestTime) {
		this.defaultTestTime = defaultTestTime;
	}

	@Column(name = "is_show_answer")
	public Integer getIsShowAnswer() {
		return isShowAnswer;
	}

	public void setIsShowAnswer(Integer isShowAnswer) {
		this.isShowAnswer = isShowAnswer;
	}
	
	@Column(name = "is_used_dms")
	public Integer getIsUsedDms() {
		return isUsedDms;
	}

	public void setIsUsedDms(Integer isUsedDms) {
		this.isUsedDms = isUsedDms;
	}
	
	@Column(name = "project_version")
	public String getProjectVersion() {
		return projectVersion;
	}

	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
	
	@Column(name = "study_guide")
	public String getStudyGuideCodes() {
		return studyGuideCodes;
	}

	public void setStudyGuideCodes(String studyGuideCodes) {
		this.studyGuideCodes = studyGuideCodes;
	}
	
}