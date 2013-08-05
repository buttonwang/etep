package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;

/**
 * Paper entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "paper")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Paper implements java.io.Serializable {

	// Fields

	private Integer id;
	private Region region;
	private PaperCategory paperCategory;
	private PaperType paperType;
	private String name;
	private String description;
	private Float difficultyValue;
	private Integer answeringTime;
	private Integer itemsNum;
	private Integer totalScore;
	private Integer creater;
	private Date createdTime;
	private Integer updater;
	private Date updatedTime;
	private Integer verifier;
	private Date verifiedTime;
	private Integer status;
	
	private Set<Item> items = new HashSet<Item>(0);
	private Set<Grade> grades = new HashSet<Grade>(0);
	private Set<PaperAssemblingPolicy> paperAssemblingPolicies = new HashSet<PaperAssemblingPolicy>(0);
	private Set<Subject> subjects = new HashSet<Subject>(0);
	private Set<ItemType> itemTypes = new HashSet<ItemType>(0);
	private Set<KnowledgePoint> knowledgePoints = new HashSet<KnowledgePoint>(0);
	
	private String statusName;
	private Integer bigItemsNum;
	// Constructors

	/** default constructor */
	public Paper() {
	}

	/** minimal constructor */
	public Paper(String name) {
		this.name = name;
	}

	/** full constructor */
	public Paper(Region region, PaperType paperType,
			PaperCategory paperCategory, String name, String description,
			Float difficultyValue, Integer answeringTime, Integer itemsNum,
			Integer totalScore, Integer creater, Date createdTime,
			Integer updater, Date updatedTime, Integer verifier,
			Date verifiedTime, Integer status, Set<Item> items, Set<Grade> grades,
			Set<PaperAssemblingPolicy> paperAssemblingPolicies, Set<Subject> subjects, Set<ItemType> itemTypes,
			Set<KnowledgePoint> knowledgePoints) {
		this.region = region;
		this.paperType = paperType;
		this.paperCategory = paperCategory;
		this.name = name;
		this.description = description;
		this.difficultyValue = difficultyValue;
		this.answeringTime = answeringTime;
		this.itemsNum = itemsNum;
		this.totalScore = totalScore;
		this.creater = creater;
		this.createdTime = createdTime;
		this.updater = updater;
		this.updatedTime = updatedTime;
		this.verifier = verifier;
		this.verifiedTime = verifiedTime;
		this.status = status;
		this.items = items;
		this.grades = grades;
		this.paperAssemblingPolicies = paperAssemblingPolicies;
		this.subjects = subjects;
		this.itemTypes = itemTypes;
		this.knowledgePoints = knowledgePoints;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false, insertable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_code")
	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_code")
	@NotFound(action=NotFoundAction.IGNORE)
	public PaperCategory getPaperCategory() {
		return this.paperCategory;
	}

	public void setPaperCategory(PaperCategory paperCategory) {
		this.paperCategory = paperCategory;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_code")
	@NotFound(action=NotFoundAction.IGNORE)
	public PaperType getPaperType() {
		return this.paperType;
	}

	public void setPaperType(PaperType paperType) {
		this.paperType = paperType;
	}

	@Column(name = "name", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "difficulty_value", precision = 12, scale = 0)
	public Float getDifficultyValue() {
		return this.difficultyValue;
	}

	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
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

	@Column(name = "creater")
	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", length = 0, updatable=false)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "updater")
	public Integer getUpdater() {
		return this.updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_time", length = 0)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Column(name = "verifier")
	public Integer getVerifier() {
		return this.verifier;
	}

	public void setVerifier(Integer verifier) {
		this.verifier = verifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "verified_time", length = 0)
	public Date getVerifiedTime() {
		return this.verifiedTime;
	}

	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
	@ManyToMany(targetEntity=Item.class, cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.LAZY)
	@JoinTable(
			name = "paper_item_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="item_id")
	)
	public Set<Item> getItems() {
		return this.items;
	}
	
	public void setItems(Set<Item> items) {
		this.items = items;
	}

	@ManyToMany(targetEntity=Grade.class, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
			name = "grade_paper_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="grade_code")
	)
	public Set<Grade> getGrades() {
		return this.grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "paper")
	public Set<PaperAssemblingPolicy> getPaperAssemblingPolicies() {
		return this.paperAssemblingPolicies;
	}

	public void setPaperAssemblingPolicies(Set<PaperAssemblingPolicy> paperAssemblingPolicies) {
		this.paperAssemblingPolicies = paperAssemblingPolicies;
	}

	@ManyToMany(targetEntity=Subject.class, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
			name = "subject_paper_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="subject_code")
	)
	public Set<Subject> getSubjects() {
		return this.subjects;
	}

	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	@OneToMany()
	@JoinTable(
			name = "item_type_paper_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="item_type_code")
	)
	public Set<ItemType> getItemTypes() {
		return this.itemTypes;
	}

	public void setItemTypes(Set<ItemType> itemTypes) {
		this.itemTypes = itemTypes;
	}

	@ManyToMany(targetEntity=KnowledgePoint.class, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinTable(
			name = "knowledge_point_paper_ref",
			joinColumns = @JoinColumn(name="paper_id"),
			inverseJoinColumns = @JoinColumn(name="knowledge_point_code")
	)
	public Set<KnowledgePoint> getKnowledgePoints() {
		return this.knowledgePoints;
	}

	public void setKnowledgePoints(Set<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}
	
	@Transient
	public String getStatusName() {
		if (this.status == null )
			statusName = "";
		else
			switch (this.status.intValue()) {
				case 0: statusName = "未审核"; break;
				case 1: statusName = "已审核"; break;
				case 2: statusName = "已组卷"; break;
				case 3: statusName = "已使用"; break;
				case -1: statusName = "已废弃"; break;			
				default: statusName = "未设置"; break;
			}
		return statusName;
	}
	
	@Column(name = "big_items_num")
	public Integer getBigItemsNum(){
		return bigItemsNum;
	}

	public void setBigItemsNum(Integer bigItemsNum) {
		this.bigItemsNum = bigItemsNum;
	}
}