package com.ambow.trainingengine.policy.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * PaperAssemblingReqTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "paper_assembling_req_template")
public class PaperAssemblingReqTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set<AssemblingPaperReqTemplate> assemblingPaperReqTemplates = new HashSet<AssemblingPaperReqTemplate>(
			0);
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public PaperAssemblingReqTemplate() {
	}

	/** minimal constructor */
	public PaperAssemblingReqTemplate(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public PaperAssemblingReqTemplate(String name,
			Set<AssemblingPaperReqTemplate> assemblingPaperReqTemplates,
			Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
		this.assemblingPaperReqTemplates = assemblingPaperReqTemplates;
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paperAssemblingReqTemplate")
	public Set<AssemblingPaperReqTemplate> getAssemblingPaperReqTemplates() {
		return this.assemblingPaperReqTemplates;
	}

	public void setAssemblingPaperReqTemplates(
			Set<AssemblingPaperReqTemplate> assemblingPaperReqTemplates) {
		this.assemblingPaperReqTemplates = assemblingPaperReqTemplates;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paperAssemblingReqTemplate")
	public Set<NodeGroupPolicyAssembling> getNodeGroupPolicyAssembling() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setNodeGroupPolicyAssembling(Set<NodeGroupPolicyAssembling> policyTemplates) {
		this.nodeGroupPolicyAssembling = policyTemplates;
	}

}