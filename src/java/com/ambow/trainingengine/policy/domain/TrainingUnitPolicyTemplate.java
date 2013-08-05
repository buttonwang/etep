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
 * TrainingUnitPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "training_unit_policy_template")
public class TrainingUnitPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer failed;
	private Integer pass;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public TrainingUnitPolicyTemplate() {
	}

	/** full constructor */
	public TrainingUnitPolicyTemplate(String name, Integer failed,
			Integer pass, Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
		this.failed = failed;
		this.pass = pass;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingUnitPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getPolicyTemplates() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setPolicyTemplates(Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
	}

}