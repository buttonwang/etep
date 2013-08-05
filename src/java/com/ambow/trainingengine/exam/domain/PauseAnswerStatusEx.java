package com.ambow.trainingengine.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/**
 * PauseAnswerStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "pause_answer_status_ex")
public class PauseAnswerStatusEx implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -7056007152030653951L;
	private Long id;
	private Item item;	
	private NodeInstance nodeInstance;
	private SubItem subItem;	
	private String answer;
	private boolean isUnsureMarking;
	private String answerOptionOrder;

	// Constructors

	/** default constructor */
	public PauseAnswerStatusEx() {
	}

	
	/** full constructor */
	public PauseAnswerStatusEx(Item item,
			SubItem subItem, NodeInstance nodeInstance,
			String answer, boolean isUnsureMarking) {
		this.item = item;		
		this.subItem = subItem;
		this.nodeInstance = nodeInstance;
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

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
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
	
	@ManyToOne
	@JoinColumn(name="node_instance_id")
	@Cascade(org.hibernate.annotations.CascadeType.MERGE)
	public NodeInstance getNodeInstance() {
		return this.nodeInstance;
	}

	public void setNodeInstance(NodeInstance nodeInstance) {
		this.nodeInstance = nodeInstance;
	}
	
	@Column(name="answer_option_order")
	public String getAnswerOptionOrder() {
		return this.answerOptionOrder;
	}

	public void setAnswerOptionOrder(String answerOptionOrder) {
		this.answerOptionOrder = answerOptionOrder;
	}

}