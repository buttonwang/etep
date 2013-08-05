package com.ambow.trainingengine.policy.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TrainingPolicyTemplate entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@Table(name = "training_policy_template")
public class TrainingPolicyTemplate implements java.io.Serializable {

	// Fields

	private Integer id;
	private String name;
	private Integer overviewTime;
	private Integer whenToSeeAnalysis;
	private Integer whenToCheckAnswer;
	private Integer allowUnsureMark;
	private Integer isRandom;
	private Integer isRandomAnswerOptions;
	private Float rightRateForPass;
	private Float rightRateRetraining;
	private Float retrainingItemType;
	private String retrainingItemOrder;
	private Float retrainingRightRateTestFaile;
	private Float retrainingItemTypeTestFaile;
	private String retrainingItemOrderTestFaile;
	private Integer randomAssemItemsTestFaile;
	private Float assemItemsTypeTestFaile;
	private Float assemItemsRateTestFaile;
	private String clueContentFirstFaile;
	private Integer clueRelActFirstFaile;
	private String translationContentFirstFaile;
	private Integer translationRelActFirstFaile;
	private String clueContentSecondFaile;
	private Integer clueRelActSecondFaile;
	private String translationContentSecondFaile;
	private Integer translationRelActSecondFaile;
	private String clueContentThirdFaile;
	private Integer clueRelActThirdFaile;
	private String translationContentThirdFaile;
	private Integer translationRelActThirdFaile;
	private Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling = new HashSet<NodeGroupPolicyAssembling>(0);

	// Constructors

	/** default constructor */
	public TrainingPolicyTemplate() {
	}

	/** full constructor */
	public TrainingPolicyTemplate(String name, Integer overviewTime,
			Integer whenToSeeAnalysis, Integer whenToCheckAnswer,
			Integer allowUnsureMark, Integer isRandom,
			Integer isRandomAnswerOptions, Float rightRateForPass,
			Float rightRateRetraining, Float retrainingItemType,
			String retrainingItemOrder, Float retrainingRightRateTestFaile,
			Float retrainingItemTypeTestFaile,
			String retrainingItemOrderTestFaile,
			Integer randomAssemItemsTestFaile, Float assemItemsTypeTestFaile,
			Float assemItemsRateTestFaile, String clueContentFirstFaile,
			Integer clueRelActFirstFaile, String translationContentFirstFaile,
			Integer translationRelActFirstFaile, String clueContentSecondFaile,
			Integer clueRelActSecondFaile,
			String translationContentSecondFaile,
			Integer translationRelActSecondFaile, String clueContentThirdFaile,
			Integer clueRelActThirdFaile, String translationContentThirdFaile,
			Integer translationRelActThirdFaile,
			Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.name = name;
		this.overviewTime = overviewTime;
		this.whenToSeeAnalysis = whenToSeeAnalysis;
		this.whenToCheckAnswer = whenToCheckAnswer;
		this.allowUnsureMark = allowUnsureMark;
		this.isRandom = isRandom;
		this.isRandomAnswerOptions = isRandomAnswerOptions;
		this.rightRateForPass = rightRateForPass;
		this.rightRateRetraining = rightRateRetraining;
		this.retrainingItemType = retrainingItemType;
		this.retrainingItemOrder = retrainingItemOrder;
		this.retrainingRightRateTestFaile = retrainingRightRateTestFaile;
		this.retrainingItemTypeTestFaile = retrainingItemTypeTestFaile;
		this.retrainingItemOrderTestFaile = retrainingItemOrderTestFaile;
		this.randomAssemItemsTestFaile = randomAssemItemsTestFaile;
		this.assemItemsTypeTestFaile = assemItemsTypeTestFaile;
		this.assemItemsRateTestFaile = assemItemsRateTestFaile;
		this.clueContentFirstFaile = clueContentFirstFaile;
		this.clueRelActFirstFaile = clueRelActFirstFaile;
		this.translationContentFirstFaile = translationContentFirstFaile;
		this.translationRelActFirstFaile = translationRelActFirstFaile;
		this.clueContentSecondFaile = clueContentSecondFaile;
		this.clueRelActSecondFaile = clueRelActSecondFaile;
		this.translationContentSecondFaile = translationContentSecondFaile;
		this.translationRelActSecondFaile = translationRelActSecondFaile;
		this.clueContentThirdFaile = clueContentThirdFaile;
		this.clueRelActThirdFaile = clueRelActThirdFaile;
		this.translationContentThirdFaile = translationContentThirdFaile;
		this.translationRelActThirdFaile = translationRelActThirdFaile;
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "overview_time")
	public Integer getOverviewTime() {
		return this.overviewTime;
	}

	public void setOverviewTime(Integer overviewTime) {
		this.overviewTime = overviewTime;
	}

	@Column(name = "when_to_see_analysis")
	public Integer getWhenToSeeAnalysis() {
		return this.whenToSeeAnalysis;
	}

	public void setWhenToSeeAnalysis(Integer whenToSeeAnalysis) {
		this.whenToSeeAnalysis = whenToSeeAnalysis;
	}

	@Column(name = "when_to_check_answer")
	public Integer getWhenToCheckAnswer() {
		return this.whenToCheckAnswer;
	}

	public void setWhenToCheckAnswer(Integer whenToCheckAnswer) {
		this.whenToCheckAnswer = whenToCheckAnswer;
	}

	@Column(name = "allow_unsure_mark")
	public Integer getAllowUnsureMark() {
		return this.allowUnsureMark;
	}

	public void setAllowUnsureMark(Integer allowUnsureMark) {
		this.allowUnsureMark = allowUnsureMark;
	}

	@Column(name = "is_random")
	public Integer getIsRandom() {
		return this.isRandom;
	}

	public void setIsRandom(Integer isRandom) {
		this.isRandom = isRandom;
	}

	@Column(name = "is_random_answer_options")
	public Integer getIsRandomAnswerOptions() {
		return this.isRandomAnswerOptions;
	}

	public void setIsRandomAnswerOptions(Integer isRandomAnswerOptions) {
		this.isRandomAnswerOptions = isRandomAnswerOptions;
	}

	@Column(name = "right_rate_for_pass", precision = 12, scale = 0)
	public Float getRightRateForPass() {
		return this.rightRateForPass;
	}

	public void setRightRateForPass(Float rightRateForPass) {
		this.rightRateForPass = rightRateForPass;
	}

	@Column(name = "right_rate_retraining", precision = 12, scale = 0)
	public Float getRightRateRetraining() {
		return this.rightRateRetraining;
	}

	public void setRightRateRetraining(Float rightRateRetraining) {
		this.rightRateRetraining = rightRateRetraining;
	}

	@Column(name = "retraining_item_type", precision = 12, scale = 0)
	public Float getRetrainingItemType() {
		return this.retrainingItemType;
	}

	public void setRetrainingItemType(Float retrainingItemType) {
		this.retrainingItemType = retrainingItemType;
	}

	@Column(name = "retraining_item_order", length = 20)
	public String getRetrainingItemOrder() {
		return this.retrainingItemOrder;
	}

	public void setRetrainingItemOrder(String retrainingItemOrder) {
		this.retrainingItemOrder = retrainingItemOrder;
	}

	@Column(name = "retraining_right_rate_test_faile", precision = 12, scale = 0)
	public Float getRetrainingRightRateTestFaile() {
		return this.retrainingRightRateTestFaile;
	}

	public void setRetrainingRightRateTestFaile(
			Float retrainingRightRateTestFaile) {
		this.retrainingRightRateTestFaile = retrainingRightRateTestFaile;
	}

	@Column(name = "retraining_item_type_test_faile", precision = 12, scale = 0)
	public Float getRetrainingItemTypeTestFaile() {
		return this.retrainingItemTypeTestFaile;
	}

	public void setRetrainingItemTypeTestFaile(Float retrainingItemTypeTestFaile) {
		this.retrainingItemTypeTestFaile = retrainingItemTypeTestFaile;
	}

	@Column(name = "retraining_item_order_test_faile", length = 20)
	public String getRetrainingItemOrderTestFaile() {
		return this.retrainingItemOrderTestFaile;
	}

	public void setRetrainingItemOrderTestFaile(
			String retrainingItemOrderTestFaile) {
		this.retrainingItemOrderTestFaile = retrainingItemOrderTestFaile;
	}

	@Column(name = "random_assem_items_test_faile")
	public Integer getRandomAssemItemsTestFaile() {
		return this.randomAssemItemsTestFaile;
	}

	public void setRandomAssemItemsTestFaile(Integer randomAssemItemsTestFaile) {
		this.randomAssemItemsTestFaile = randomAssemItemsTestFaile;
	}

	@Column(name = "assem_items_type_test_faile", precision = 12, scale = 0)
	public Float getAssemItemsTypeTestFaile() {
		return this.assemItemsTypeTestFaile;
	}

	public void setAssemItemsTypeTestFaile(Float assemItemsTypeTestFaile) {
		this.assemItemsTypeTestFaile = assemItemsTypeTestFaile;
	}

	@Column(name = "assem_items_rate_test_faile", precision = 12, scale = 0)
	public Float getAssemItemsRateTestFaile() {
		return this.assemItemsRateTestFaile;
	}

	public void setAssemItemsRateTestFaile(Float assemItemsRateTestFaile) {
		this.assemItemsRateTestFaile = assemItemsRateTestFaile;
	}

	@Column(name = "clue_content_first_faile", length = 200)
	public String getClueContentFirstFaile() {
		return this.clueContentFirstFaile;
	}

	public void setClueContentFirstFaile(String clueContentFirstFaile) {
		this.clueContentFirstFaile = clueContentFirstFaile;
	}

	@Column(name = "clue_rel_act_first_faile")
	public Integer getClueRelActFirstFaile() {
		return this.clueRelActFirstFaile;
	}

	public void setClueRelActFirstFaile(Integer clueRelActFirstFaile) {
		this.clueRelActFirstFaile = clueRelActFirstFaile;
	}

	@Column(name = "translation_content_first_faile", length = 200)
	public String getTranslationContentFirstFaile() {
		return this.translationContentFirstFaile;
	}

	public void setTranslationContentFirstFaile(
			String translationContentFirstFaile) {
		this.translationContentFirstFaile = translationContentFirstFaile;
	}

	@Column(name = "translation_rel_act_first_faile")
	public Integer getTranslationRelActFirstFaile() {
		return this.translationRelActFirstFaile;
	}

	public void setTranslationRelActFirstFaile(
			Integer translationRelActFirstFaile) {
		this.translationRelActFirstFaile = translationRelActFirstFaile;
	}

	@Column(name = "clue_content_second_faile", length = 200)
	public String getClueContentSecondFaile() {
		return this.clueContentSecondFaile;
	}

	public void setClueContentSecondFaile(String clueContentSecondFaile) {
		this.clueContentSecondFaile = clueContentSecondFaile;
	}

	@Column(name = "clue_rel_act_second_faile")
	public Integer getClueRelActSecondFaile() {
		return this.clueRelActSecondFaile;
	}

	public void setClueRelActSecondFaile(Integer clueRelActSecondFaile) {
		this.clueRelActSecondFaile = clueRelActSecondFaile;
	}

	@Column(name = "translation_content_second_faile", length = 200)
	public String getTranslationContentSecondFaile() {
		return this.translationContentSecondFaile;
	}

	public void setTranslationContentSecondFaile(
			String translationContentSecondFaile) {
		this.translationContentSecondFaile = translationContentSecondFaile;
	}

	@Column(name = "translation_rel_act_second_faile")
	public Integer getTranslationRelActSecondFaile() {
		return this.translationRelActSecondFaile;
	}

	public void setTranslationRelActSecondFaile(
			Integer translationRelActSecondFaile) {
		this.translationRelActSecondFaile = translationRelActSecondFaile;
	}

	@Column(name = "clue_content_third_faile", length = 200)
	public String getClueContentThirdFaile() {
		return this.clueContentThirdFaile;
	}

	public void setClueContentThirdFaile(String clueContentThirdFaile) {
		this.clueContentThirdFaile = clueContentThirdFaile;
	}

	@Column(name = "clue_rel_act_third_faile")
	public Integer getClueRelActThirdFaile() {
		return this.clueRelActThirdFaile;
	}

	public void setClueRelActThirdFaile(Integer clueRelActThirdFaile) {
		this.clueRelActThirdFaile = clueRelActThirdFaile;
	}

	@Column(name = "translation_content_third_faile", length = 200)
	public String getTranslationContentThirdFaile() {
		return this.translationContentThirdFaile;
	}

	public void setTranslationContentThirdFaile(
			String translationContentThirdFaile) {
		this.translationContentThirdFaile = translationContentThirdFaile;
	}

	@Column(name = "translation_rel_act_third_faile")
	public Integer getTranslationRelActThirdFaile() {
		return this.translationRelActThirdFaile;
	}

	public void setTranslationRelActThirdFaile(
			Integer translationRelActThirdFaile) {
		this.translationRelActThirdFaile = translationRelActThirdFaile;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "trainingPolicyTemplate")
	public Set<NodeGroupPolicyAssembling> getPolicyTemplates() {
		return this.nodeGroupPolicyAssembling;
	}

	public void setPolicyTemplates(Set<NodeGroupPolicyAssembling> nodeGroupPolicyAssembling) {
		this.nodeGroupPolicyAssembling = nodeGroupPolicyAssembling;
	}

}