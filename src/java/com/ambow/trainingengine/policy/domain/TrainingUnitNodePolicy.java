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
 * TrainingUnitNodePolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "training_unit_node_policy")
public class TrainingUnitNodePolicy implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Node asfNode;
	private Integer failed;
	private Integer pass;

	// Constructors

	/** default constructor */
	public TrainingUnitNodePolicy() {
	}

	/** minimal constructor */
	public TrainingUnitNodePolicy(Node asfNode) {
		this.asfNode = asfNode;
	}

	/** full constructor */
	public TrainingUnitNodePolicy(Node asfNode, Integer failed, Integer pass) {
		this.asfNode = asfNode;
		this.failed = failed;
		this.pass = pass;
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

	@Column(name = "failed")
	public Integer getFailed() {
		return this.failed;
	}

	public void setFailed(Integer failed) {
		this.failed = failed;
	}

	@Column(name = "pass")
	public Integer getPass() {
		return this.pass;
	}

	public void setPass(Integer pass) {
		this.pass = pass;
	}

}