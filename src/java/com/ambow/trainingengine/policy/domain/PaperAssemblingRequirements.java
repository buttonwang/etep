package com.ambow.trainingengine.policy.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ambow.studyflow.domain.Node;

/**
 * PaperAssemblingRequirements entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "paper_assembling_requirements")
public class PaperAssemblingRequirements implements java.io.Serializable {

	// Fields

	private Integer id;
	private Node asfNode;
	private String year = "";
	private String validityValue = "";
	private String difficultyValue = "";
	private String itemTypeCode = "";
	
	private String source  = "";
	private String originalPaperCode = "";
	private String regionCode = "";
	private String subjectCode = "";
	private String gradeCode = "";
	private Integer amount;	 
	private String knowledgePointCode;
	private String applicableObject;//'0全部;1-文科;2-理科'
	private String courseVersions;//0 新旧版本适用 1新版本适用  2旧版本适用 (可多选)
	private String opinion;
	private String reviewRound;
	// Constructors
	private String itemTypeName = "";
	private String knowledgePointName="";
	/** default constructor */
	public PaperAssemblingRequirements() {
	}

	/** minimal constructor */
	public PaperAssemblingRequirements(Node asfNode, String itemTypeCode) {
		this.asfNode = asfNode;
		this.itemTypeCode = itemTypeCode;
	}

	/** full constructor */
	public PaperAssemblingRequirements(Node asfNode, String validityValue,
			String year, String difficultyValue, String itemTypeCode,
			String source, String originalPaperCode, String regionCode,
			String subjectCode, Integer amount) {
		this.asfNode = asfNode;
		this.validityValue = validityValue;
		this.year = year;
		this.difficultyValue = difficultyValue;
		this.itemTypeCode = itemTypeCode;
		this.source = source;
		this.originalPaperCode = originalPaperCode;
		this.regionCode = regionCode;
		this.subjectCode = subjectCode;
		this.amount = amount;
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
	
	@ManyToOne
	@JoinColumn(name="node_id")
	public Node getAsfNode() {
		return this.asfNode;
	}

	public void setAsfNode(Node asfNode) {
		this.asfNode = asfNode;
	}

	@Column(name = "validity_value", length = 30)
	public String getValidityValue() {
		return this.validityValue;
	}

	public void setValidityValue(String validityValue) {
		this.validityValue = validityValue;
	}

	@Column(name = "year", length = 30)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "difficulty_value", length = 30)
	public String getDifficultyValue() {
		return this.difficultyValue;
	}

	public void setDifficultyValue(String difficultyValue) {
		this.difficultyValue = difficultyValue;
	}

	@Column(name = "item_type_code", nullable = false)
	public String getItemTypeCode() {
		return this.itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	@Column(name = "source", length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "original_paper_code", length = 50)
	public String getOriginalPaperCode() {
		return this.originalPaperCode;
	}

	public void setOriginalPaperCode(String originalPaperCode) {
		this.originalPaperCode = originalPaperCode;
	}

	@Column(name = "region_code", length = 10)
	public String getRegionCode() {
		return this.regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "subject_code", length = 10)
	public String getSubjectCode() {
		return this.subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	
	@Column(name = "grade_code", length = 10)
	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	@Column(name = "amount")
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	@Column(name = "knowledge_point_code")
	public String getKnowledgePointCode() {
		return knowledgePointCode;
	}

	public void setKnowledgePointCode(String knowledgePointCode) {
		this.knowledgePointCode = knowledgePointCode;
	}

	@Column(name = "applicable_object")
	public String getApplicableObject() {
		return applicableObject;
	}

	public void setApplicableObject(String applicableObject) {
		this.applicableObject = applicableObject;
	}

	@Column(name = "opinion")
	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
	
	@Transient
	public String getItemTypeName() {
		return itemTypeName;
	}

	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	
	@Transient
	public String getKnowledgePointName() {
		return knowledgePointName;
	}

	public void setKnowledgePointName(String knowledgePointName) {
		this.knowledgePointName = knowledgePointName;
	}

	@Column(name = "course_versions")
	public String getCourseVersions() {
		return courseVersions;
	}

	public void setCourseVersions(String courseVersions) {
		this.courseVersions = courseVersions;
	}

	@Column(name = "review_round", length = 50)
	public String getReviewRound() {
		return reviewRound;
	}

	public void setReviewRound(String reviewRound) {
		this.reviewRound = reviewRound;
	}

}