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

/**
 * NodeGroupPolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "node_group_policy")
public class NodeGroupPolicy implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Node asfNode;
	private Integer isDisplayModule;

	
	private String studyGuide;
	// Constructors

	/** default constructor */
	public NodeGroupPolicy() {
	}

	/** minimal constructor */
	public NodeGroupPolicy(Node asfNode) {
		this.asfNode = asfNode;
	}

	/** full constructor */
	public NodeGroupPolicy(Node asfNode, Integer isDisplayModule) {
		this.asfNode = asfNode;
		this.isDisplayModule = isDisplayModule;
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

	@Column(name = "is_display_module")
	public Integer getIsDisplayModule() {
		return this.isDisplayModule;
	}

	public void setIsDisplayModule(Integer isDisplayModule) {
		this.isDisplayModule = isDisplayModule;
	}

	@Column(name = "study_guide")
	public String getStudyGuide() {
		return studyGuide;
	}

	public void setStudyGuide(String studyGuide) {
		this.studyGuide = studyGuide;
	}

}