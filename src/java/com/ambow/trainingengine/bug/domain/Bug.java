package com.ambow.trainingengine.bug.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.itembank.domain.Item;

/**
 * Bug entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bug")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Bug implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6046497920796133574L;
	private Integer id;
	private ProcessInstance asfProcessInstance;
	private Item item;
	private Integer status;
	private Set<BugInfo> bugInfos = new HashSet<BugInfo>(0);

	// Constructors

	/** default constructor */
	public Bug() {
	}

	/** minimal constructor */
	public Bug(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public Bug(Integer id, ProcessInstance asfProcessInstance, Item item,
			Integer status, Set<BugInfo> bugInfos) {
		this.id = id;
		this.asfProcessInstance = asfProcessInstance;
		this.item = item;
		this.status = status;
		this.bugInfos = bugInfos;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asf_process_instance_id")
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}

	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "bug")
	public Set<BugInfo> getBugInfos() {
		return this.bugInfos;
	}

	public void setBugInfos(Set<BugInfo> bugInfos) {
		this.bugInfos = bugInfos;
	}

}