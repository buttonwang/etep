package com.ambow.studyflow.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * DecisionRecord.java:<strong>决策器记录</strong><br/>
 * <p>
 * 每个节点可以有多个决策器记录，根据决策器的优先级来决定哪个有效<br/>
 * </p>
 * 
 * <p>
 * Created on 2008-5-12 <br/>
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 * </p>
 * 
 * @author: Su Xiaoyong <br/>
 * Contributor(s): 参与者的名称，参与者名称2，<br/>
 * 
 * Changes <br/>
 * -------<br/>
 * $Log$
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name="asf_decision_record")
public class DecisionRecord {

	private long id;
	private int priority=0;
	private String decisionName;
	private Node node;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(nullable=false)
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Column(name="decision_name",length=50)
	public String getDecisionName() {
		return decisionName;
	}
	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}
	@ManyToOne
	@JoinColumn(name = "node_id")
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	
	
	
}
