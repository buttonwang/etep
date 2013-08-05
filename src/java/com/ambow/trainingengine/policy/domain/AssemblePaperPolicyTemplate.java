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
 * AssemblePaperPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "assemble_paper_policy_template")
public class AssemblePaperPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer paperAssemblingMode;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public AssemblePaperPolicyTemplate() {
	}

	/** full constructor */
	public AssemblePaperPolicyTemplate(String name,
			Integer paperAssemblingMode, Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
		this.paperAssemblingMode = paperAssemblingMode;
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

	@Column(name = "paper_assembling_mode")
	public Integer getPaperAssemblingMode() {
		return this.paperAssemblingMode;
	}

	public void setPaperAssemblingMode(Integer paperAssemblingMode) {
		this.paperAssemblingMode = paperAssemblingMode;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assemblePaperPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getNodeGroupPolicyAssembling() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setNodeGroupPolicyAssembling(Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
	}

}