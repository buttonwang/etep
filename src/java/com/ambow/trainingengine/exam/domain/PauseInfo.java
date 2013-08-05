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
 * PauseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "pause_info")
public class PauseInfo implements java.io.Serializable {

	private static final long serialVersionUID = -2988898600149153065L;
	
	// Fields
	private Long processInstanceId;
	private ProcessInstance asfProcessInstance;
	private Long nodeInstanceId;
	private Integer timeLeft;
	private Integer totalItemsNum;
	private Integer unfinishedItemsNum;
	private Integer unsureItemsNum;
	private Integer currentTestPageNum;
	private Date pauseTime;
	private Date startTestTime;
	private Integer answeringTime;	
	private Float paperAssemItemType;
	private Integer testStatus;
	private Integer isTested;
	
	
	// Constructors

	/** default constructor */
	public PauseInfo() {
	}

	/** minimal constructor */
	public PauseInfo(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	/** full constructor */
	public PauseInfo(ProcessInstance asfProcessInstance, Integer timeLeft,
			Integer totalItemsNum, Integer unfinishedItemsNum,
			Integer unsureItemsNum, Integer currentTestPageNum, Date pauseTime) {
		this.asfProcessInstance = asfProcessInstance;
		this.timeLeft = timeLeft;
		this.totalItemsNum = totalItemsNum;
		this.unfinishedItemsNum = unfinishedItemsNum;
		this.unsureItemsNum = unsureItemsNum;
		this.currentTestPageNum = currentTestPageNum;
		this.pauseTime = pauseTime;
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
	
	@OneToOne(cascade=CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}

	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	@Column(name="time_left")
	public Integer getTimeLeft() {
		return this.timeLeft;
	}

	public void setTimeLeft(Integer timeLeft) {
		this.timeLeft = timeLeft;
	}

	@Column(name="total_items_num")
	public Integer getTotalItemsNum() {
		return this.totalItemsNum;
	}

	public void setTotalItemsNum(Integer totalItemsNum) {
		this.totalItemsNum = totalItemsNum;
	}

	@Column(name="unfinished_items_num")
	public Integer getUnfinishedItemsNum() {
		return this.unfinishedItemsNum;
	}

	public void setUnfinishedItemsNum(Integer unfinishedItemsNum) {
		this.unfinishedItemsNum = unfinishedItemsNum;
	}

	@Column(name="unsure_items_num")
	public Integer getUnsureItemsNum() {
		return this.unsureItemsNum;
	}

	public void setUnsureItemsNum(Integer unsureItemsNum) {
		this.unsureItemsNum = unsureItemsNum;
	}

	@Column(name="current_test_page_num")
	public Integer getCurrentTestPageNum() {
		return this.currentTestPageNum;
	}

	public void setCurrentTestPageNum(Integer currentTestPageNum) {
		this.currentTestPageNum = currentTestPageNum;
	}

	@Column(name="pause_time")
	public Date getPauseTime() {
		return this.pauseTime;
	}

	public void setPauseTime(Date pauseTime) {
		this.pauseTime = pauseTime;
	}
	
	@Column(name="start_test_time")
	public Date getStartTestTime() {
		return startTestTime;
	}

	public void setStartTestTime(Date startTestTime) {
		this.startTestTime = startTestTime;
	}
	
	@Column(name="test_status")
	public Integer getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(Integer testStatus) {
		this.testStatus = testStatus;
	}

	@Column(name="is_tested")
	public Integer getIsTested() {
		return isTested;
	}

	public void setIsTested(Integer isTested) {
		this.isTested = isTested;
	}
	
	@Column(name="node_instance_id")
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	@Column(name="paper_assem_item_type")
	public Float getPaperAssemItemType() {
		return paperAssemItemType;
	}

	public void setPaperAssemItemType(Float paperAssemItemType) {
		this.paperAssemItemType = paperAssemItemType;
	}
	@Column(name="answering_time")
	public Integer getAnsweringTime() {
		return answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
}