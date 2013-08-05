package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ambow.studyflow.domain.NodeInstance;

/**
 * DynamicAssembledPaper entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "dynamic_assembled_paper")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class DynamicAssembledPaper implements java.io.Serializable {

	private static final long serialVersionUID = -6893862182841970957L;
	
	// Fields
	private Integer id;
	private NodeInstance asfNodeInstance;
	private Integer answeringTime;
	private Integer itemsNum;
	private Integer totalScore;
	private Date assemblingTime;
	private Integer status;
	
	private List<Item> items = new ArrayList<Item>(0);

	// Constructors

	/** default constructor */
	public DynamicAssembledPaper() {
	}

	/** full constructor */
	public DynamicAssembledPaper(NodeInstance asfNodeInstance,
			Integer answeringTime, Integer itemsNum, Integer totalScore,
			Date assemblingTime, Integer status, List<Item> items) {
		this.asfNodeInstance = asfNodeInstance;
		this.answeringTime = answeringTime;
		this.itemsNum = itemsNum;
		this.totalScore = totalScore;
		this.assemblingTime = assemblingTime;
		this.status = status;
		this.items = items;
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
	@JoinColumn(name = "node_instance_id")
	public NodeInstance getAsfNodeInstance() {
		return this.asfNodeInstance;
	}

	public void setAsfNodeInstance(NodeInstance asfNodeInstance) {
		this.asfNodeInstance = asfNodeInstance;
	}

	@Column(name = "answering_time")
	public Integer getAnsweringTime() {
		return this.answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}

	@Column(name = "items_num")
	public Integer getItemsNum() {
		return this.itemsNum;
	}

	public void setItemsNum(Integer itemsNum) {
		this.itemsNum = itemsNum;
	}

	@Column(name = "total_score")
	public Integer getTotalScore() {
		return this.totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "assembling_time", length = 0)
	public Date getAssemblingTime() {
		return this.assemblingTime;
	}

	public void setAssemblingTime(Date assemblingTime) {
		this.assemblingTime = assemblingTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinTable(
			name = "dynamic_assembled_paper_item_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="item_id")
	)
	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	

}