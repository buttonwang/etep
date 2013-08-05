package com.ambow.studyflow.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.ambow.studyflow.common.NodeStatus;

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name="asf_node_instance")
public class NodeInstance {

	/**
	 * 记录每个节点实例的状态
	 */
	private long id;
	
	/**
	 * 相关节点
	 */
	private Node node;
	private NodeStatus nodeStatus=NodeStatus.INITIAL;
	private Date startTime;
	private Date updateTime;
	/**
	 * 是否必做节点
	 */
	private boolean isNecessary=true;
	
	/**
	 * 所属流程实例
	 */
	private ProcessInstance processInstance;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="node_id")
	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}
    @Column(name= "node_status", columnDefinition="integer")
    @Type(
        type = "com.ambow.studyflow.common.GenericEnumUserType",
        parameters = {
                @Parameter(
                    name  = "enumClass",                      
                    value = "com.ambow.studyflow.common.NodeStatus"),
                @Parameter(
                    name  = "identifierMethod",
                    value = "toInt"),
                @Parameter(
                    name  = "valueOfMethod",
                    value = "fromInt")
                }
    )
	public NodeStatus getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(NodeStatus nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	@ManyToOne
	@JoinColumn(name="process_instance_id")
	public ProcessInstance getProcessInstance() {
		return processInstance;
	}
	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
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
	@Column(name="update_time")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	@Column(name="is_necessary",nullable=false)
	public boolean isNecessary() {
		return isNecessary;
	}
	
	public void setNecessary(boolean isNecessary) {
		this.isNecessary = isNecessary;
	}

	
	

	
}
