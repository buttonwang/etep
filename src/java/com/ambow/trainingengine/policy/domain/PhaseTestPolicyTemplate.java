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
 * PhaseTestPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "phase_test_policy_template")
public class PhaseTestPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	private Set<PhaseTestPolicyReq> phaseTestPolicyReqs = new HashSet<PhaseTestPolicyReq>(0);
	// Constructors

	/** default constructor */
	public PhaseTestPolicyTemplate() {
	}

	/** full constructor */
	public PhaseTestPolicyTemplate(String name,
			Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "phaseTestPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getPolicyTemplates() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setPolicyTemplates(Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "phaseTestPolicyTemplate")
	public Set<PhaseTestPolicyReq> getPhaseTestPolicyReqs() {
		return phaseTestPolicyReqs;
	}

	public void setPhaseTestPolicyReqs(Set<PhaseTestPolicyReq> phaseTestPolicyReqs) {
		this.phaseTestPolicyReqs = phaseTestPolicyReqs;
	}

}