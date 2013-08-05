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
 * UnitTestNodePolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "unit_test_node_policy")
public class UnitTestNodePolicy implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Node asfNode;
	private Integer retrainingScope;

	// Constructors

	/** default constructor */
	public UnitTestNodePolicy() {
	}

	/** minimal constructor */
	public UnitTestNodePolicy(Node asfNode) {
		this.asfNode = asfNode;
	}

	/** full constructor */
	public UnitTestNodePolicy(Node asfNode, Integer retrainingScope) {
		this.asfNode = asfNode;
		this.retrainingScope = retrainingScope;
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

	@Column(name = "retraining_scope")
	public Integer getRetrainingScope() {
		return this.retrainingScope;
	}

	public void setRetrainingScope(Integer retrainingScope) {
		this.retrainingScope = retrainingScope;
	}

}