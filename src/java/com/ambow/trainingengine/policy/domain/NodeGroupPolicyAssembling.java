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
 * NodeGroupPolicyAssembling entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "node_group_policy_assembling")
public class NodeGroupPolicyAssembling implements java.io.Serializable {

	// Fields

	private Long nodeId;
	private UnitTestPolicyTemplate unitTestPolicyTemplate;
	private ModuleEvalPolicyTemplate moduleEvalPolicyTemplate;
	private TrainingPolicyTemplate trainingPolicyTemplate;
	private TrainingUnitPolicyTemplate trainingUnitPolicyTemplate;
	private Node asfNode;
	private AssemblePaperPolicyTemplate assemblePaperPolicyTemplate;
	private PhaseTestPolicyTemplate phaseTestPolicyTemplate;
	private PaperAssemblingReqTemplate paperAssemblingReqTemplate;

	// Constructors

	/** default constructor */
	public NodeGroupPolicyAssembling() {
	}

	/** minimal constructor */
	public NodeGroupPolicyAssembling(Long nodeId, Node asfNode) {
		this.nodeId = nodeId;
		this.asfNode = asfNode;
	}

	/** full constructor */
	public NodeGroupPolicyAssembling(Long nodeId,
			UnitTestPolicyTemplate unitTestPolicyTemplate,
			ModuleEvalPolicyTemplate moduleEvalPolicyTemplate,
			TrainingPolicyTemplate trainingPolicyTemplate,
			TrainingUnitPolicyTemplate trainingUnitPolicyTemplate,
			Node asfNode,
			AssemblePaperPolicyTemplate assemblePaperPolicyTemplate,
			PhaseTestPolicyTemplate phaseTestPolicyTemplate,
			PaperAssemblingReqTemplate paperAssemblingReqTemplate) {
		this.nodeId = nodeId;
		this.unitTestPolicyTemplate = unitTestPolicyTemplate;
		this.moduleEvalPolicyTemplate = moduleEvalPolicyTemplate;
		this.trainingPolicyTemplate = trainingPolicyTemplate;
		this.trainingUnitPolicyTemplate = trainingUnitPolicyTemplate;
		this.asfNode = asfNode;
		this.assemblePaperPolicyTemplate = assemblePaperPolicyTemplate;
		this.phaseTestPolicyTemplate = phaseTestPolicyTemplate;
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_test_policy_template_id")
	public UnitTestPolicyTemplate getUnitTestPolicyTemplate() {
		return this.unitTestPolicyTemplate;
	}

	public void setUnitTestPolicyTemplate(
			UnitTestPolicyTemplate unitTestPolicyTemplate) {
		this.unitTestPolicyTemplate = unitTestPolicyTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "module_eval_policy_template_id")
	public ModuleEvalPolicyTemplate getModuleEvalPolicyTemplate() {
		return this.moduleEvalPolicyTemplate;
	}

	public void setModuleEvalPolicyTemplate(
			ModuleEvalPolicyTemplate moduleEvalPolicyTemplate) {
		this.moduleEvalPolicyTemplate = moduleEvalPolicyTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_policy_template_id")
	public TrainingPolicyTemplate getTrainingPolicyTemplate() {
		return this.trainingPolicyTemplate;
	}

	public void setTrainingPolicyTemplate(
			TrainingPolicyTemplate trainingPolicyTemplate) {
		this.trainingPolicyTemplate = trainingPolicyTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_unit_policy_template_id")
	public TrainingUnitPolicyTemplate getTrainingUnitPolicyTemplate() {
		return this.trainingUnitPolicyTemplate;
	}

	public void setTrainingUnitPolicyTemplate(
			TrainingUnitPolicyTemplate trainingUnitPolicyTemplate) {
		this.trainingUnitPolicyTemplate = trainingUnitPolicyTemplate;
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
	@JoinColumn(name = "paper_assem_policy_template_id")
	public AssemblePaperPolicyTemplate getAssemblePaperPolicyTemplate() {
		return this.assemblePaperPolicyTemplate;
	}

	public void setAssemblePaperPolicyTemplate(
			AssemblePaperPolicyTemplate assemblePaperPolicyTemplate) {
		this.assemblePaperPolicyTemplate = assemblePaperPolicyTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "phase_test_policy_template_id")
	public PhaseTestPolicyTemplate getPhaseTestPolicyTemplate() {
		return this.phaseTestPolicyTemplate;
	}

	public void setPhaseTestPolicyTemplate(
			PhaseTestPolicyTemplate phaseTestPolicyTemplate) {
		this.phaseTestPolicyTemplate = phaseTestPolicyTemplate;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paper_assembling_req_template_id")
	public PaperAssemblingReqTemplate getPaperAssemblingReqTemplate() {
		return this.paperAssemblingReqTemplate;
	}

	public void setPaperAssemblingReqTemplate(
			PaperAssemblingReqTemplate paperAssemblingReqTemplate) {
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
	}

}