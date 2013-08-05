package com.ambow.trainingengine.policy.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.itembank.domain.Paper;



/**
 * PaperAssemblingPolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "paper_assembling_policy")
public class PaperAssemblingPolicy implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Node asfNode;
	private Paper paper;
	/*0-手工组卷;3-手工组卷-多卷随机1-动态组卷;11-动态组卷(过滤本级已做题);
	 * 12-动态组卷(过滤上一级已做题);13-动态组卷(过滤上两级已做题);
	 * 14-动态组卷(从所属训练中过滤题);2-动态出题;21-动态出题（过滤本级）;
	 * 22-动态出题（过滤上一级）;23-动态出题（过滤上两级）
	 */
	private Integer paperAssemblingMode;
	private Integer standardAnsweringTime ;

	private Integer answeringTime;
	private  Float difficultyValue;
	private Integer  items_num;
	private Integer big_items_num;
	private Integer  totalScore;
	private String paperList;
	/** default constructor */
	public PaperAssemblingPolicy() {
	}

	/** minimal constructor */
	public PaperAssemblingPolicy(Node asfNode) {
		this.asfNode = asfNode;
	}

	/** full constructor */
	public PaperAssemblingPolicy(Node asfNode, Paper paper,
			Integer paperAssemblingMode) {
		this.asfNode = asfNode;
		this.paper = paper;
		this.paperAssemblingMode = paperAssemblingMode;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="asfNode"))
	@Column(name="node_id")
	public Long getNodeId() {
		return this.nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public Node getAsfNode() {
		return this.asfNode;
	}

	public void setAsfNode(Node asfNode) {
		this.asfNode = asfNode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paper_id")
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}
	
	@Column(name = "paper_assembling_mode")
	public Integer getPaperAssemblingMode() {
		return this.paperAssemblingMode;
	}

	public void setPaperAssemblingMode(Integer paperAssemblingMode) {
		this.paperAssemblingMode = paperAssemblingMode;
	}
	
	@Column(name = "answering_time")
	public Integer getAnsweringTime() {
		return answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
	
	@Column(name = "difficulty_value")
	public Float getDifficultyValue() {
		return difficultyValue;
	}

	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}
	
	@Column(name = "items_num")
	public Integer getItems_num() {
		return items_num;
	}

	public void setItems_num(Integer items_num) {
		this.items_num = items_num;
	}
	
	@Column(name = "total_score")
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	
	@Column(name = "standard_answering_time ")
	public Integer getStandardAnsweringTime() {
		return standardAnsweringTime;
	}

	public void setStandardAnsweringTime(Integer standardAnsweringTime) {
		this.standardAnsweringTime = standardAnsweringTime;
	}
	@Column(name = "paper_list ")
	public String getPaperList() {
		return paperList;
	}

	public void setPaperList(String paperList) {
		this.paperList = paperList;
	}

	@Column(name = "big_items_num")
	public Integer getBig_items_num() {
		return big_items_num;
	}

	public void setBig_items_num(Integer big_items_num) {
		this.big_items_num = big_items_num;
	}

}