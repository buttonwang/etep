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
 * ModuleEvalPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "module_eval_policy_template")
public class ModuleEvalPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public ModuleEvalPolicyTemplate() {
	}

	/** full constructor */
	public ModuleEvalPolicyTemplate(String name,
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "moduleEvalPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getNodeGroupPolicyAssembling() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setNodeGroupPolicyAssembling(Set<NodeGroupPolicyAssembling> policyTemplates) {
		this.nodeGroupPolicyAssembling = policyTemplates;
	}

}