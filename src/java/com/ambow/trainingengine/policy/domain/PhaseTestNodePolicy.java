package com.ambow.trainingengine.policy.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ambow.studyflow.domain.Node;

/**
 * PhaseTestNodePolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "phase_test_node_policy")
public class PhaseTestNodePolicy implements java.io.Serializable {

	// Fields

	private Integer id;
	private Node asfNode;
	private Float startValue;
	private Float endValue;
	private Integer jumpPosition;

	private String studyGuide;
	private String studyTip;
	// Constructors

	/** default constructor */
	public PhaseTestNodePolicy() {
	}

	/** minimal constructor */
	public PhaseTestNodePolicy(Node asfNode) {
		this.asfNode = asfNode;
	}

	/** full constructor */
	public PhaseTestNodePolicy(Node asfNode, Float startValue,
			Float endValue, Integer jumpPosition) {
		this.asfNode = asfNode;
		this.startValue = startValue;
		this.endValue = endValue;
		this.jumpPosition = jumpPosition;
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

	@ManyToOne
	@JoinColumn(name="node_id")
	public Node getAsfNode() {
		return this.asfNode;
	}

	public void setAsfNode(Node asfNode) {
		this.asfNode = asfNode;
	}

	@Column(name = "start_value", precision = 12, scale = 0)
	public Float getStartValue() {
		return this.startValue;
	}

	public void setStartValue(Float startValue) {
		this.startValue = startValue;
	}
	
	public void setStartValue(String startValue) {
		this.startValue = new Float(startValue);
	}

	@Column(name = "end_value", precision = 12, scale = 0)
	public Float getEndValue() {
		return this.endValue;
	}
	public void setEndValue(String endValue) {
		this.endValue = new Float(endValue);
	}
	public void setEndValue(Float endValue) {
		this.endValue = endValue;
	}

	@Column(name = "jump_position")
	public Integer getJumpPosition() {
		return this.jumpPosition;
	}

	public void setJumpPosition(Integer jumpPosition) {
		this.jumpPosition = jumpPosition;
	}
	
	@Column(name = "study_guide")
	public String getStudyGuide() {
		return studyGuide;
	}

	public void setStudyGuide(String studyGuide) {
		this.studyGuide = studyGuide;
	}
	
	@Column(name = "study_tip")
	public String getStudyTip() {
		return studyTip;
	}

	public void setStudyTip(String studyTip) {
		this.studyTip = studyTip;
	}

}