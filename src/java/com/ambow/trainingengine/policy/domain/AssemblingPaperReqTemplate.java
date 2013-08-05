package com.ambow.trainingengine.policy.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * AssemblingPaperReqTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "assembling_paper_req_template")
public class AssemblingPaperReqTemplate implements java.io.Serializable {

	// Fields
	private static final long serialVersionUID = 8607132671548394470L;
	private Integer id;
	private PaperAssemblingReqTemplate paperAssemblingReqTemplate;
	private String gradeCode;
	private String regionCode;
	private String subjectCode;
	private String itemTypeCode;
	private String itemTypeName = "";
	private String year;
	private String source;
	private String originalPaperCode;
	private String difficultyValue;
	private String validityValue;
	private Integer amount;
	private String reviewRound;

	
	private String knowledgePointCode;
	private String knowledgePointName="";
	private Integer applicableObject;//'1-文科;2-理科'
	private String courseVersions;//0 新旧版本适用 1新版本适用  2旧版本适用
	private Float opinion;
	// Constructors

	/** default constructor */
	public AssemblingPaperReqTemplate() {
	}

	/** minimal constructor */
	public AssemblingPaperReqTemplate(
			PaperAssemblingReqTemplate paperAssemblingReqTemplate,
			String itemTypeCode) {
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
		this.itemTypeCode = itemTypeCode;
	}

	/** full constructor */
	public AssemblingPaperReqTemplate(
			PaperAssemblingReqTemplate paperAssemblingReqTemplate,
			String regionCode, String subjectCode, String itemTypeCode,
			String year, String source, String originalPaperCode,
			String difficultyValue, String validityValue, Integer amount) {
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
		this.regionCode = regionCode;
		this.subjectCode = subjectCode;
		this.itemTypeCode = itemTypeCode;
		this.year = year;
		this.source = source;
		this.originalPaperCode = originalPaperCode;
		this.difficultyValue = difficultyValue;
		this.validityValue = validityValue;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assem_paper_req_template_id", nullable = false)
	public PaperAssemblingReqTemplate getPaperAssemblingReqTemplate() {
		return this.paperAssemblingReqTemplate;
	}

	public void setPaperAssemblingReqTemplate(
			PaperAssemblingReqTemplate paperAssemblingReqTemplate) {
		this.paperAssemblingReqTemplate = paperAssemblingReqTemplate;
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

	@Column(name = "item_type_code", nullable = false)
	public String getItemTypeCode() {
		return this.itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	@Column(name = "year", length = 30)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
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

	@Column(name = "difficulty_value", length = 30)
	public String getDifficultyValue() {
		return this.difficultyValue;
	}

	public void setDifficultyValue(String difficultyValue) {
		this.difficultyValue = difficultyValue;
	}

	@Column(name = "validity_value", length = 30)
	public String getValidityValue() {
		return this.validityValue;
	}

	public void setValidityValue(String validityValue) {
		this.validityValue = validityValue;
	}

	@Column(name = "amount")
	public Integer getAmount() {
		return this.amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	
	@Column(name = "grade_code")
	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	
	@Column(name = "knowledge_point_code")
	public String getKnowledgePointCode() {
		return knowledgePointCode;
	}

	public void setKnowledgePointCode(String knowledgePointCode) {
		this.knowledgePointCode = knowledgePointCode;
	}

	@Column(name = "applicable_object")
	public Integer getApplicableObject() {
		return applicableObject;
	}

	public void setApplicableObject(Integer applicableObject) {
		this.applicableObject = applicableObject;
	}

	@Column(name = "opinion")
	public Float getOpinion() {
		return opinion;
	}

	public void setOpinion(Float opinion) {
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