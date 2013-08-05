package com.ambow.trainingengine.policy.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * ModuleEvaluatingNodePolicy entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "module_evaluating_node_policy")
public class ModuleEvaluatingNodePolicy implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private Node asfNode;

	// Constructors

	/** default constructor */
	public ModuleEvaluatingNodePolicy() {
	}

	/** full constructor */
	public ModuleEvaluatingNodePolicy(Node asfNode) {
		this.asfNode = asfNode;
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

}