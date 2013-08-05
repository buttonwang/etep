package com.ambow.trainingengine.policy.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "phase_test_policy_req")
public class PhaseTestPolicyReq implements Serializable {
	
	private Integer id;
	
	private PhaseTestPolicyTemplate phaseTestPolicyTemplate;
	
	private Float startValue;
	
	private Float endValue;
	
	private Integer jumpScope;
 
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="phase_test_policy_tmp_id")
	public PhaseTestPolicyTemplate getPhaseTestPolicyTemplate() {
		return phaseTestPolicyTemplate;
	}

	public void setPhaseTestPolicyTemplate(
			PhaseTestPolicyTemplate phaseTestPolicyTemplate) {
		this.phaseTestPolicyTemplate = phaseTestPolicyTemplate;
	}

	@Column(name="start_value")
	public Float getStartValue() {
		return startValue;
	}

	public void setStartValue(Float startValue) {
		this.startValue = startValue;
	}

	@Column(name="end_value")
	public Float getEndValue() {
		return endValue;
	}

	public void setEndValue(Float endValue) {
		this.endValue = endValue;
	}
	
	@Column(name="jump_scope")
	public Integer getJumpScope() {
		return jumpScope;
	}

	public void setJumpScope(Integer jumpScope) {
		this.jumpScope = jumpScope;
	}

}
