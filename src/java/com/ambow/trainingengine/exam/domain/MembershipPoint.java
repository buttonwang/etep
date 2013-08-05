package com.ambow.trainingengine.exam.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.ambow.studyflow.domain.ProcessInstance;

/**
 * ProcessTrainingStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "membership_point")
public class MembershipPoint implements java.io.Serializable {

	private static final long serialVersionUID = 6609269689155144085L;
	
	private Long processInstanceId;
	private ProcessInstance asfProcessInstance;	
	
	private Integer diligence;
	private Integer wisdom;
	private Integer courage;
	private Integer dedicate;
	private Integer percipience;
	
	
	// Constructors

	/** default constructor */
	public MembershipPoint() {
	}

	/** minimal constructor */
	public MembershipPoint(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	/** full constructor */
	public MembershipPoint(ProcessInstance asfProcessInstance,			
			Integer diligence, Integer wisdom,
			Integer courage, Integer dedicate,
			Integer percipience) {
		this.asfProcessInstance = asfProcessInstance;
		this.diligence = diligence;
		this.wisdom = wisdom;
		this.courage = courage;
		this.dedicate = dedicate;
		this.percipience = percipience;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator = "fk")
	@GenericGenerator(strategy = "foreign", name = "fk", parameters = @Parameter(name="property", value="asfProcessInstance"))
	@Column(name="asf_process_instance_id")
	public Long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	@OneToOne(cascade=CascadeType.ALL)
	@PrimaryKeyJoinColumn
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}

	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}
	@Column(name="diligence")
	public Integer getDiligence() {
		return diligence;
	}

	public void setDiligence(Integer diligence) {
		this.diligence = diligence;
	}
	@Column(name="wisdom")
	public Integer getWisdom() {
		return wisdom;
	}

	public void setWisdom(Integer wisdom) {
		this.wisdom = wisdom;
	}
	@Column(name="courage")
	public Integer getCourage() {
		return courage;
	}

	public void setCourage(Integer courage) {
		this.courage = courage;
	}
	@Column(name="dedicate")
	public Integer getDedicate() {
		return dedicate;
	}

	public void setDedicate(Integer dedicate) {
		this.dedicate = dedicate;
	}
	@Column(name="percipience")
	public Integer getPercipience() {
		return percipience;
	}

	public void setPercipience(Integer percipience) {
		this.percipience = percipience;
	}
}