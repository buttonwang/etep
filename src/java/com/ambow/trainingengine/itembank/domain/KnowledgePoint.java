package com.ambow.trainingengine.itembank.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;

/**
 * KnowledgePoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "knowledge_point")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class KnowledgePoint implements java.io.Serializable {

	// Fields

	private String code;
	private String name;
	private KnowledgePoint parentKnowledgePoint;
	private String parentLevel;
	
	private Set<EvaluatingAnswerStatus> evaluatingAnswerStatuses = new HashSet<EvaluatingAnswerStatus>(0);
	private Set<SubItem> subItems = new HashSet<SubItem>(0);
	private Set<Item> items = new HashSet<Item>(0);
	private Set<Paper> papers = new HashSet<Paper>(0);
		
	private List<KnowledgePoint> childrenKnowledgePoints = new ArrayList<KnowledgePoint>(0);
	
	private String levelFlag= "";
	private Integer state;
	// Constructors

	/** default constructor */
	public KnowledgePoint() {
	}

	/** minimal constructor */
	public KnowledgePoint(String code) {
		this.code = code;
	}

	/** full constructor */
	public KnowledgePoint(String name, KnowledgePoint parentKnowledgePoint, String parentLevel,
			Set<EvaluatingAnswerStatus> evaluatingAnswerStatuses, Set<SubItem> subItems, Set<Item> items, Set<Paper> papers) {
		this.name = name;
		this.parentKnowledgePoint = parentKnowledgePoint;
		this.parentLevel = parentLevel;
		this.evaluatingAnswerStatuses = evaluatingAnswerStatuses;
		this.subItems = subItems;
		this.items = items;
		this.papers = papers;
	}

	// Property accessors
	@Id
	@Column(name = "code", unique = true, nullable = false, length = 20)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "parent_code")
	@NotFound(action=NotFoundAction.IGNORE)
	public KnowledgePoint getParentKnowledgePoint() {
		return this.parentKnowledgePoint;
	}

	public void setParentKnowledgePoint(KnowledgePoint parentKnowledgePoint) {
		this.parentKnowledgePoint = parentKnowledgePoint;
	}

	@Column(name = "parent_level", length = 50)
	public String getParentLevel() {
		return this.parentLevel;
	}

	public void setParentLevel(String parentLevel) {
		this.parentLevel = parentLevel;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "knowledgePoint")
	public Set<EvaluatingAnswerStatus> getEvaluatingAnswerStatuses() {
		return this.evaluatingAnswerStatuses;
	}

	public void setEvaluatingAnswerStatuses(Set<EvaluatingAnswerStatus> evaluatingAnswerStatuses) {
		this.evaluatingAnswerStatuses = evaluatingAnswerStatuses;
	}

	@ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "knowledge_point_subitem_ref",
			joinColumns = @JoinColumn(name="knowledge_point_code"),
			inverseJoinColumns = @JoinColumn(name="sub_item_id")
	)	
	public Set<SubItem> getSubItems() {
		return this.subItems;
	}

	public void setSubItems(Set<SubItem> subItems) {
		this.subItems = subItems;
	}

	@ManyToMany(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "knowledge_point_item_ref",
			joinColumns = @JoinColumn(name="knowledge_point_code"),
			inverseJoinColumns = @JoinColumn(name="item_id")
	)
	public Set<Item> getItems() {
		return this.items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@OneToMany(cascade=CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinTable(
			name = "knowledge_point_paper_ref",
			joinColumns = @JoinColumn(name="knowledge_point_code"),
			inverseJoinColumns = @JoinColumn(name="paper_id")
	)
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}
	
	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY, mappedBy="parentKnowledgePoint")	
	public List<KnowledgePoint> getChildrenKnowledgePoints() {
		return childrenKnowledgePoints;
	}

	public void setChildrenKnowledgePoints(List<KnowledgePoint> childrenKnowledgePoints) {
		this.childrenKnowledgePoints = childrenKnowledgePoints;
	}

	@Transient
	public String getLevelFlag() {
		if (this.parentKnowledgePoint!=null) this.levelFlag = this.parentKnowledgePoint.levelFlag + "----";
		return levelFlag;
	}

	public void setLevelFlag(String levelFlag) {
		this.levelFlag = levelFlag;
	}
	
	/** 生成parentLevel字段 */
	public void genparentLevel(){
		this.parentLevel = (this.parentKnowledgePoint==null)?this.code:this.parentKnowledgePoint.parentLevel+","+this.code;
	}
	
	private Subject subject;
	private Grade grade;
	@ManyToOne
	@JoinColumn(name = "subject_code")
	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	@ManyToOne
	@JoinColumn(name = "grade_code")
	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	@Column(name="state")
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
}