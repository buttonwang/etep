package com.ambow.trainingengine.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;

/**
 * EvaluatingAnswerStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "evaluating_answer_status")
public class EvaluatingAnswerStatus implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = -8289057701481997735L;
	
	private Integer id;
	private KnowledgePoint knowledgePoint;
	private NodeInstance asfNodeInstance;
	private Float score;
	private Float totalScore;
	private Float rightRate;
	
	// 后测的成绩 临时字段
	private Float score2;
	private Float totalScore2;
	private Float rightRate2;
		
	// Constructors
	
	/** default constructor */
	public EvaluatingAnswerStatus() {
	}

	/** full constructor */
	public EvaluatingAnswerStatus(KnowledgePoint knowledgePoint,
			NodeInstance asfNodeInstance, Float score) {
		this.knowledgePoint = knowledgePoint;
		this.asfNodeInstance = asfNodeInstance;
		this.score = score;
	}

	// Property accessors

	@Id
	@GeneratedValue
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne
	@JoinColumn(name="knowledge_point_code")
	public KnowledgePoint getKnowledgePoint() {
		return this.knowledgePoint;
	}

	public void setKnowledgePoint(KnowledgePoint knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}
	@OneToOne
	@JoinColumn(name="node_instance_id")
	public NodeInstance getAsfNodeInstance() {
		return this.asfNodeInstance;
	}

	public void setAsfNodeInstance(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	@Column(name="score")
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}
	
	@Column(name="total_score")
	public Float getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}
	
	@Column(name="right_rate")
	public Float getRightRate() {
		return rightRate;
	}

	public void setRightRate(Float rightRate) {
		this.rightRate = rightRate;
	}
	
	@Transient
	public Float getScore2() {
		return score2;
	}

	public void setScore2(Float score2) {
		this.score2 = score2;
	}

	@Transient
	public Float getTotalScore2() {
		return totalScore2;
	}

	public void setTotalScore2(Float totalScore2) {
		this.totalScore2 = totalScore2;
	}

	@Transient
	public Float getRightRate2() {
		return rightRate2;
	}

	public void setRightRate2(Float rightRate2) {
		this.rightRate2 = rightRate2;
	}
		
}