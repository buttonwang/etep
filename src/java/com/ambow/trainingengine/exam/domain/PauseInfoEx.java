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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.util.ExamUtil;

/**
 * PauseInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "pause_info_ex")
public class PauseInfoEx implements java.io.Serializable {

	private static final long serialVersionUID = -4222744407823492611L;
	// Fields
	private Long nodeInstanceId;
	private NodeInstance nodeInstance;
	
	private Integer timeLeft;
	private Integer totalItemsNum;
	private Integer unfinishedItemsNum;
	private Integer unsureItemsNum;
	private Integer currentTestPageNum;
	private Date pauseTime;
	private Date startTestTime;
	private Integer answeringTime;
	private Float paperAssemItemType;
	private Integer isTested;
	
	/** default constructor */
	public PauseInfoEx() {
	}


	/** full constructor */
	public PauseInfoEx(NodeInstance nodeInstance, Integer timeLeft,
			Integer totalItemsNum, Integer unfinishedItemsNum,
			Integer unsureItemsNum, Integer currentTestPageNum, Date pauseTime) {
		this.nodeInstance = nodeInstance;
		this.timeLeft = timeLeft;
		this.totalItemsNum = totalItemsNum;
		this.unfinishedItemsNum = unfinishedItemsNum;
		this.unsureItemsNum = unsureItemsNum;
		this.currentTestPageNum = currentTestPageNum;
		this.pauseTime = pauseTime;
	}


	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="nodeInstance"))
	@Column(name="node_instance_id")
	public Long getNodeInstanceId() {
		return this.nodeInstanceId;
	}

	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	
	@OneToOne(cascade=CascadeType.MERGE)
	@PrimaryKeyJoinColumn
	public NodeInstance getNodeInstance() {
		return this.nodeInstance;
	}

	public void setNodeInstance(NodeInstance nodeInstance) {
		this.nodeInstance = nodeInstance;
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
	
	@Column(name="answering_time")
	public Integer getAnsweringTime() {
		return answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
	
	@Column(name="paper_assem_item_type")
	public Float getPaperAssemItemType() {
		return paperAssemItemType;
	}

	public void setPaperAssemItemType(Float paperAssemItemType) {
		this.paperAssemItemType = paperAssemItemType;
	}
	
	@Column(name="is_tested")
	public Integer getIsTested() {
		return isTested;
	}

	public void setIsTested(Integer isTested) {
		this.isTested = isTested;
	}
	
	@Transient
	public String getPaperAssemItemTypeName() {
		String assemName = ExamUtil.getScopeCHName(paperAssemItemType)+"重练";
		if (isTested==0) assemName = "新题拓展";
		return assemName;
	}
}