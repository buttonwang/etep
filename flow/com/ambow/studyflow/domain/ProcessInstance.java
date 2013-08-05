/**
 * 
 */
package com.ambow.studyflow.domain;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import com.ambow.studyflow.common.ProcessStatus;


/**
 * @author suxiaoyong
 * 
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name="asf_process_instance")
public class ProcessInstance {
	private long id;
	protected String actor = null;
	protected Date startTime = null;
	protected Date end_Time = null;
	protected ProcessDefinition processDefinition = null;
	private Node node;
	private ProcessStatus processStatus;
	public ProcessInstance(Map params, ProcessDefinition processDefinition) {
		Assert.notNull(params.get("actor"));
		this.actor = params.get("actor").toString();
		this.startTime = new Date();
		this.processDefinition = processDefinition;
		this.node=processDefinition.getStartNode();
	}

	public ProcessInstance() {
		super();
	}
	@Column(length=50)
	public String getActor() {
		return actor;
	}
	public void setId(long id) {
		this.id = id;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="start_time")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="end_time")
	public Date getEnd_Time() {
		return end_Time;
	}

	public void setEnd_Time(Date endTime) {
		this.end_Time = endTime;
	}
	@ManyToOne
	@JoinColumn(name="process_definition_id")
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	/**
	 * @return the node
	 */
	@OneToOne
	@JoinColumn(name = "node_id")
	@Cascade(org.hibernate.annotations.CascadeType.PERSIST)  
	public Node getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the processStatus
	 */
    @Column(name= "process_status", columnDefinition="integer")
    @Type(
        type = "com.ambow.studyflow.common.GenericEnumUserType",
        parameters = {
                @Parameter(
                    name  = "enumClass",                      
                    value = "com.ambow.studyflow.common.ProcessStatus"),
                @Parameter(
                    name  = "identifierMethod",
                    value = "toInt"),
                @Parameter(
                    name  = "valueOfMethod",
                    value = "fromInt")
                }
    )
	public ProcessStatus getProcessStatus() {
		return processStatus;
	}

	/**
	 * @param processStatus the processStatus to set
	 */
	public void setProcessStatus(ProcessStatus processStatus) {
		this.processStatus = processStatus;
	}

}
