package com.ambow.trainingengine.exam.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/**
 * PauseAnswerStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "pause_answer_status")
public class PauseAnswerStatus implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -451687162774269261L;
	private Long id;
	private Item item;
	private Long nodeInstanceId;
	private SubItem subItem;
	private ProcessInstance asfProcessInstance;
	private String answer;
	private boolean isUnsureMarking;
	private String answerOptionOrder;

	// Constructors

	/** default constructor */
	public PauseAnswerStatus() {
	}

	/** minimal constructor */
	public PauseAnswerStatus(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	/** full constructor */
	public PauseAnswerStatus(Item item, long asfNodeInstance,
			SubItem subItem, ProcessInstance asfProcessInstance,
			String answer, boolean isUnsureMarking) {
		this.item = item;
		this.nodeInstanceId = asfNodeInstance;
		this.subItem = subItem;
		this.asfProcessInstance = asfProcessInstance;
		this.answer = answer;
		this.isUnsureMarking = isUnsureMarking;
	}

	// Property accessors

	@Id
	@GeneratedValue
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="item_id")
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}		

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="sub_item_id")
	public SubItem getSubItem() {
		return this.subItem;
	}

	@Column(name="node_instance_id")
	public Long getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(Long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
	}

	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="process_instance_id")
	public ProcessInstance getAsfProcessInstance() {
		return this.asfProcessInstance;
	}

	public void setAsfProcessInstance(ProcessInstance asfProcessInstance) {
		this.asfProcessInstance = asfProcessInstance;
	}

	@Column(name="answer")
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name="is_unsure_marking")
	public boolean getIsUnsureMarking() {
		return this.isUnsureMarking;
	}

	public void setIsUnsureMarking(boolean isUnsureMarking) {
		this.isUnsureMarking = isUnsureMarking;
	}

	@Column(name="answer_option_order")
	public String getAnswerOptionOrder() {
		return this.answerOptionOrder;
	}

	public void setAnswerOptionOrder(String answerOptionOrder) {
		this.answerOptionOrder = answerOptionOrder;
	}
	
}