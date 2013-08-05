package com.ambow.trainingengine.policy.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * UnitTestPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "unit_test_policy_template")
public class UnitTestPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer retrainingScope;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public UnitTestPolicyTemplate() {
	}

	/** full constructor */
	public UnitTestPolicyTemplate(String name, Integer retrainingScope,
			Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
		this.retrainingScope = retrainingScope;
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

	@Column(name = "retraining_scope")
	public Integer getRetrainingScope() {
		return this.retrainingScope;
	}

	public void setRetrainingScope(Integer retrainingScope) {
		this.retrainingScope = retrainingScope;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "unitTestPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getPolicyTemplates() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setPolicyTemplates(Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
	}

}