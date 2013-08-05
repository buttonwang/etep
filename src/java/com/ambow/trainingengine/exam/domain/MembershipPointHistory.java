package com.ambow.trainingengine.exam.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ambow.studyflow.domain.ProcessInstance;

/**
 * ProcessTrainingStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "membership_point_history")
public class MembershipPointHistory implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 2696608292813317107L;
	private Long id;
	private ProcessInstance asfProcessInstance;	
	private Integer point;
	private Integer pointType;
	private String pointCause;
	private String remark;
	private Date operateTime;

	/** 引用标识 */
	private Integer refId;
	
	// Constructors

	/** default constructor */
	public MembershipPointHistory() {
	}

	/** minimal constructor */
	public MembershipPointHistory(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	/** full constructor */
	public MembershipPointHistory(ProcessInstance asfProcessInstance,			
			Integer point, Integer pointType,
			String remark, Date operateTime,
			String pointCause
			) {
		this.asfProcessInstance = asfProcessInstance;
		this.point = point;
		this.pointType = pointType;
		this.remark = remark;
		this.operateTime = operateTime;
		this.pointCause=pointCause;
	}

	// Property accessors

	@Id	
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="asf_process_instance_id")
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}
	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}
	
	@Column(name="point")
	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
	
	@Column(name="point_type")
	public Integer getPointType() {
		return pointType;
	}

	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}
	
	@Column(name="point_cause")
	public String getPointCause() {
		return pointCause;
	}

	public void setPointCause(String pointCause) {
		this.pointCause = pointCause;
	}

	@Column(name="remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operate_time", length = 0)
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "ref_id")
	public Integer getRefId() {
		return refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}
	
}