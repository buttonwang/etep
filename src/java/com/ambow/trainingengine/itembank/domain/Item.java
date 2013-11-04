package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatus;
import com.ambow.trainingengine.exam.util.SplitCompareable;
import com.ambow.trainingengine.exam.web.data.ExamProperty;
import com.ambow.trainingengine.itembank.util.ItemLable;
import com.ambow.trainingengine.itembank.util.MathUtil;

/**
 * Item entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "item")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class Item implements java.io.Serializable, SplitCompareable, Comparable<Object> {

	private static final long serialVersionUID = 3350033181592439013L;

	// Fields
	private Integer id;
	private Region region;
	private ItemType itemType;
	private Subject subject;
	private String code = "";
	private String year = "";
	private String source = "";
	private String sourceBook;
	private String sourceFile;
	private String originalPaperCode = "";
	private String originalItemNum = "";
	private Float score;
	private String score2;
	private Float difficultyValue;
	private Float validityValue;
	private Integer answeringTime;
	private String content;
	private String contentTranslation;
	private Integer wordCount;
	private Integer readingTime;
	private String correctAnswer;
	private String answerAnalysis;
	private String hint;
	private String analysisAtLarge1;
	private String analysisAtLarge2;
	private String analysisAtLarge3;
	private String thinkingAnalyse;
	private String skills;
	private String difficultSentance;
	private String scoringKeywords;
	private String writingTemplate;
	private String scoringNorm;
	private Integer applicableObject;
	private Integer courseVersion;
	private String reviewRound;
	private String keywords;
	private String keySentances;
	private Integer creater;
	private Date createdTime;
	private Integer updater;
	private Date updatedTime;
	private Integer verifier;
	private Date verifiedTime;
	private Float opinion;
	private Float itemValue;
	private String answerPrototype;
	private String answerGroup;
	private String abilityValue;
	private String importFile;
	private String regionCode2;
	private TypicalExample typicalExample;
	private Integer reviseStatus=0;
	private Integer status;

	private Set<Paper> papers = new HashSet<Paper>(0);
	private Set<Grade> grades = new HashSet<Grade>(0);
	private Set<CurrentAnswersStatus> currentAnswersStatuses = new HashSet<CurrentAnswersStatus>(0);
	private List<SubItem> subItems = new ArrayList<SubItem>(0);
	private Set<PauseAnswerStatus> pauseAnswerStatuses = new HashSet<PauseAnswerStatus>(0);
	private Set<AnswerOption> answerOptions = new HashSet<AnswerOption>(0);
	private Set<HistoryAnswerStatus> historyAnswerStatuses = new HashSet<HistoryAnswerStatus>(0);
	private Set<ItemTheme> itemThemes = new HashSet<ItemTheme>(0);
	private Set<KnowledgePoint> knowledgePoints = new HashSet<KnowledgePoint>(0);
	private Set<KnowledgePoint> knowledgePoints2 = new HashSet<KnowledgePoint>(0);
	private Set<DynamicAssembledPaper> dynamicAssembledPapers = new HashSet<DynamicAssembledPaper>(0);
	private Set<ItemRevise> itemRevises = new HashSet<ItemRevise>(0);
	
	// 专门为显示所准备的一些VO属性
	
	private ExamProperty examProperty; //供答题业务使用的属性 
	
	private Boolean filterShow = false;// 默认值为false,filterShow时不显示
	private String contentView = null;// 默认为空..
	private Integer itemNum;
	
	private String knowledgePointsName;
	private String itemThemesName;
	private String statusName;
	private String sourceName;
	private long nodeInstanceId;

	private Integer answeringTimeByMin;
	private Integer readingTimeByMin;
	
	private List answers;
		
	// Constructors

	/** default constructor */
	public Item() {
	}

	/** minimal constructor */
	public Item(ItemType itemType, String code) {
		this.itemType = itemType;
		this.code = code;
	}

	/** full constructor */
	public Item(Region region, ItemType itemType, Subject subject, String code,
			String year, String source, String sourceBook, String sourceFile,
			String originalPaperCode, String originalItemNum, Float score,
			Float difficultyValue, Float validityValue, Integer answeringTime,
			String content, String contentTranslation, Integer wordCount,
			Integer readingTime, String correctAnswer, String answerAnalysis,
			String analysisAtLarge1, String analysisAtLarge2,
			String analysisAtLarge3, String thinkingAnalyse, String skills,
			String difficultSentance, String scoringKeywords,
			String writingTemplate, String scoringNorm,
			Integer applicableObject, String keywords, String keySentances,
			Integer creater, Date createdTime, Integer updater,
			Date updatedTime, Integer verifier, Date verifiedTime,
			Integer status, Set<Paper> papers, Set<Grade> grades,
			Set<CurrentAnswersStatus> currentAnswersStatuses,
			List<SubItem> subItems, Set<PauseAnswerStatus> pauseAnswerStatuses,
			Set<AnswerOption> answerOptions,
			Set<HistoryAnswerStatus> historyAnswerStatuses,
			Set<ItemTheme> itemThemes, Set<KnowledgePoint> knowledgePoints,
			Set<KnowledgePoint> knowledgePoints2,
			Set<DynamicAssembledPaper> dynamicAssembledPapers,
			String answerPrototype, String abilityValue, String score2) {
		this.region = region;
		this.itemType = itemType;
		this.subject = subject;
		this.code = code;
		this.year = year;
		this.source = source;
		this.sourceBook = sourceBook;
		this.sourceFile = sourceFile;
		this.originalPaperCode = originalPaperCode;
		this.originalItemNum = originalItemNum;
		this.score = score;
		this.difficultyValue = difficultyValue;
		this.validityValue = validityValue;
		this.answeringTime = answeringTime;
		this.content = content;
		this.contentTranslation = contentTranslation;
		this.wordCount = wordCount;
		this.readingTime = readingTime;
		this.correctAnswer = correctAnswer;
		this.answerAnalysis = answerAnalysis;
		this.analysisAtLarge1 = analysisAtLarge1;
		this.analysisAtLarge2 = analysisAtLarge2;
		this.analysisAtLarge3 = analysisAtLarge3;
		this.thinkingAnalyse = thinkingAnalyse;
		this.skills = skills;
		this.difficultSentance = difficultSentance;
		this.scoringKeywords = scoringKeywords;
		this.writingTemplate = writingTemplate;
		this.scoringNorm = scoringNorm;
		this.applicableObject = applicableObject;
		this.keywords = keywords;
		this.keySentances = keySentances;
		this.creater = creater;
		this.createdTime = createdTime;
		this.updater = updater;
		this.updatedTime = updatedTime;
		this.verifier = verifier;
		this.verifiedTime = verifiedTime;
		this.status = status;
		this.papers = papers;
		this.grades = grades;
		this.currentAnswersStatuses = currentAnswersStatuses;
		this.subItems = subItems;
		this.pauseAnswerStatuses = pauseAnswerStatuses;
		this.answerOptions = answerOptions;
		this.historyAnswerStatuses = historyAnswerStatuses;
		this.itemThemes = itemThemes;
		this.knowledgePoints = knowledgePoints;
		this.knowledgePoints2 = knowledgePoints2;
		this.dynamicAssembledPapers = dynamicAssembledPapers;
		this.answerPrototype = answerPrototype;
		this.abilityValue = abilityValue;
		this.score2 = score2;
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
	@JoinColumn(name = "region_code")
	@NotFound(action = NotFoundAction.IGNORE)
	public Region getRegion() {
		return this.region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "item_type_code", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	public ItemType getItemType() {
		return this.itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subject_code")
	@NotFound(action = NotFoundAction.IGNORE)
	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	@Column(name = "code", nullable = true, length = 20, updatable = true)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "year", length = 4)
	public String getYear() {
		return this.year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "source", length = 1)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "source_book", length = 50)
	public String getSourceBook() {
		return this.sourceBook;
	}

	public void setSourceBook(String sourceBook) {
		this.sourceBook = sourceBook;
	}

	@Column(name = "source_file", length = 50)
	public String getSourceFile() {
		return this.sourceFile;
	}

	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}

	@Column(name = "original_paper_code", length = 50)
	public String getOriginalPaperCode() {
		return this.originalPaperCode;
	}

	public void setOriginalPaperCode(String originalPaperCode) {
		this.originalPaperCode = originalPaperCode;
	}

	@Column(name = "original_item_num", length = 20)
	public String getOriginalItemNum() {
		return this.originalItemNum;
	}

	public void setOriginalItemNum(String originalItemNum) {
		this.originalItemNum = originalItemNum;
	}

	@Column(name = "score", precision = 12, scale = 0)
	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name = "difficulty_value", precision = 12, scale = 0)
	public Float getDifficultyValue() {
		return this.difficultyValue;
	}

	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}

	@Column(name = "validity_value", precision = 12, scale = 0)
	public Float getValidityValue() {
		return this.validityValue;
	}

	public void setValidityValue(Float validityValue) {
		this.validityValue = validityValue;
	}

	@Column(name = "answering_time")
	public Integer getAnsweringTime() {
		if (this.answeringTime == null)
			return 0;
		else
			return this.answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "content_translation", length = 65535)
	public String getContentTranslation() {
		return this.contentTranslation;
	}

	public void setContentTranslation(String contentTranslation) {
		this.contentTranslation = contentTranslation;
	}

	@Column(name = "word_count")
	public Integer getWordCount() {
		if (this.wordCount == null)
			return 0;
		else
			return this.wordCount;
	}

	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}

	@Column(name = "reading_time")
	public Integer getReadingTime() {
		if (this.readingTime == null)
			return 0;
		else
			return this.readingTime;
	}

	public void setReadingTime(Integer readingTime) {
		this.readingTime = readingTime;
	}

	@Column(name = "correct_answer")
	public String getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Column(name = "answer_analysis")
	public String getAnswerAnalysis() {
		return this.answerAnalysis;
	}

	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}

	@Column(name = "hint")
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Column(name = "analysis_at_large_1")
	public String getAnalysisAtLarge1() {
		return this.analysisAtLarge1;
	}

	public void setAnalysisAtLarge1(String analysisAtLarge1) {
		this.analysisAtLarge1 = analysisAtLarge1;
	}

	@Column(name = "analysis_at_large_2")
	public String getAnalysisAtLarge2() {
		return this.analysisAtLarge2;
	}

	public void setAnalysisAtLarge2(String analysisAtLarge2) {
		this.analysisAtLarge2 = analysisAtLarge2;
	}

	@Column(name = "analysis_at_large_3")
	public String getAnalysisAtLarge3() {
		return this.analysisAtLarge3;
	}

	public void setAnalysisAtLarge3(String analysisAtLarge3) {
		this.analysisAtLarge3 = analysisAtLarge3;
	}

	@Column(name = "thinking_analyse")
	public String getThinkingAnalyse() {
		return this.thinkingAnalyse;
	}

	public void setThinkingAnalyse(String thinkingAnalyse) {
		this.thinkingAnalyse = thinkingAnalyse;
	}

	@Column(name = "skills")
	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	@Column(name = "difficult_sentance")
	public String getDifficultSentance() {
		return this.difficultSentance;
	}

	public void setDifficultSentance(String difficultSentance) {
		this.difficultSentance = difficultSentance;
	}

	@Column(name = "scoring_keywords", length = 1500)
	public String getScoringKeywords() {
		return this.scoringKeywords;
	}

	public void setScoringKeywords(String scoringKeywords) {
		this.scoringKeywords = scoringKeywords;
	}

	@Column(name = "writing_template")
	public String getWritingTemplate() {
		return this.writingTemplate;
	}

	public void setWritingTemplate(String writingTemplate) {
		this.writingTemplate = writingTemplate;
	}

	@Column(name = "scoring_norm")
	public String getScoringNorm() {
		return this.scoringNorm;
	}

	public void setScoringNorm(String scoringNorm) {
		this.scoringNorm = scoringNorm;
	}

	@Column(name = "applicable_object")
	public Integer getApplicableObject() {
		return this.applicableObject;
	}

	public void setApplicableObject(Integer applicableObject) {
		this.applicableObject = applicableObject;
	}

	@Column(name = "course_version")
	public Integer getCourseVersion() {
		return courseVersion;
	}

	public void setCourseVersion(Integer courseVersion) {
		this.courseVersion = courseVersion;
	}
	
	@Column(name = "review_round")
	public String getReviewRound() {
		return reviewRound;
	}

	public void setReviewRound(String reviewRound) {
		this.reviewRound = reviewRound;
	}
	
	@Column(name = "keywords", length = 5000)
	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	@Column(name = "key_sentances", length = 5000)
	public String getKeySentances() {
		return this.keySentances;
	}

	public void setKeySentances(String keySentances) {
		this.keySentances = keySentances;
	}

	@Column(name = "creater")
	public Integer getCreater() {
		return this.creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_time", length = 0)
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

	@ManyToMany(targetEntity = Paper.class, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinTable(name = "paper_item_ref", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "paper_id"))
	public Set<Paper> getPapers() {
		return this.papers;
	}

	public void setPapers(Set<Paper> papers) {
		this.papers = papers;
	}

	@ManyToMany(targetEntity = Grade.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "grade_item_ref", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "grade_code"))
	public Set<Grade> getGrades() {
		return this.grades;
	}

	public void setGrades(Set<Grade> grades) {
		this.grades = grades;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
	public Set<CurrentAnswersStatus> getCurrentAnswersStatuses() {
		return this.currentAnswersStatuses;
	}

	public void setCurrentAnswersStatuses(
			Set<CurrentAnswersStatus> currentAnswersStatuses) {
		this.currentAnswersStatuses = currentAnswersStatuses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
	@OrderBy("orderNum,originalSubItemNum")
	public List<SubItem> getSubItems() {
		return this.subItems;
	}

	public void setSubItems(List<SubItem> subItems) {
		this.subItems = subItems;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "item")
	public Set<PauseAnswerStatus> getPauseAnswerStatuses() {
		return this.pauseAnswerStatuses;
	}

	public void setPauseAnswerStatuses(
			Set<PauseAnswerStatus> pauseAnswerStatuses) {
		this.pauseAnswerStatuses = pauseAnswerStatuses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "item")
	@OrderBy("code")
	public Set<AnswerOption> getAnswerOptions() {
		return this.answerOptions;
	}

	public void setAnswerOptions(Set<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "item")
	public Set<HistoryAnswerStatus> getHistoryAnswerStatuses() {
		return this.historyAnswerStatuses;
	}

	public void setHistoryAnswerStatuses(
			Set<HistoryAnswerStatus> historyAnswerStatuses) {
		this.historyAnswerStatuses = historyAnswerStatuses;
	}

	@ManyToMany(targetEntity = ItemTheme.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "item_theme_ref", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "item_theme_code"))
	public Set<ItemTheme> getItemThemes() {
		return this.itemThemes;
	}

	public void setItemThemes(Set<ItemTheme> itemThemes) {
		this.itemThemes = itemThemes;
	}

	@ManyToMany(targetEntity = KnowledgePoint.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "knowledge_point_item_ref", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "knowledge_point_code"))
	public Set<KnowledgePoint> getKnowledgePoints() {
		return this.knowledgePoints;
	}

	public void setKnowledgePoints(Set<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

	@ManyToMany(targetEntity = KnowledgePoint.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "item_sub_knowledge_point", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "code"))
	public Set<KnowledgePoint> getKnowledgePoints2() {
		return knowledgePoints2;
	}

	public void setKnowledgePoints2(Set<KnowledgePoint> knowledgePoints2) {
		this.knowledgePoints2 = knowledgePoints2;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "dynamic_assembled_paper_item_ref", joinColumns = @JoinColumn(name = "item_id"), inverseJoinColumns = @JoinColumn(name = "paper_id"))
	public Set<DynamicAssembledPaper> getDynamicAssembledPapers() {
		return this.dynamicAssembledPapers;
	}

	public void setDynamicAssembledPapers(
			Set<DynamicAssembledPaper> dynamicAssembledPapers) {
		this.dynamicAssembledPapers = dynamicAssembledPapers;
	}

	@Column(name = "opinion")
	public Float getOpinion() {
		return opinion;
	}

	public void setOpinion(Float opinion) {
		this.opinion = opinion;
	}

	@Column(name = "item_value")
	public Float getItemValue() {
		return itemValue;
	}

	public void setItemValue(Float itemValue) {
		this.itemValue = itemValue;
	}

	@Column(name = "answer_prototype")
	public String getAnswerPrototype() {
		return answerPrototype;
	}

	public void setAnswerPrototype(String answerPrototype) {
		this.answerPrototype = answerPrototype;
	}

	@Column(name = "answer_group")
	public String getAnswerGroup() {
		return answerGroup;
	}

	public void setAnswerGroup(String answerGroup) {
		this.answerGroup = answerGroup;
	}
	
	@Column(name = "score2")
	public String getScore2() {
		return score2;
	}

	public void setScore2(String score2) {
		this.score2 = score2;
	}

	@Column(name = "ability_value")
	public String getAbilityValue() {
		return abilityValue;
	}

	public void setAbilityValue(String abilityValue) {
		this.abilityValue = abilityValue;
	}

	@Column(name = "import_file")
	public String getImportFile() {
		return importFile;
	}

	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}

	@Column(name = "region_code2")
	public String getRegionCode2() {
		return regionCode2;
	}

	public void setRegionCode2(String regionCode2) {
		this.regionCode2 = regionCode2;
	}

	@OneToOne(mappedBy = "item")	
	public TypicalExample getTypicalExample() {
		return typicalExample;
	}

	public void setTypicalExample(TypicalExample typicalExample) {
		this.typicalExample = typicalExample;
	}
	/*
	 * 
	 * @see
	 * com.ambow.trainingengine.itembank.util.SplitCompareable#getPageSize()
	 */
	@Transient
	public int getCountSize() {
		return this.itemType.getItemNumPerpage();
	}

	public boolean splitCompareTo(Object obj) {
		if (obj instanceof Item) {
			if (this.itemType.compareTo(((Item) obj).getItemType()) == 0)
				return true;
		}
		return false;
	}

	// 为了相似类型排序,所以需要实现comparable接口
	public int compareTo(Object o) {
		if (o instanceof Item) {
			Item oItem = (Item) o;
			int sortValue = this.itemType.compareTo(oItem.getItemType());
			if (sortValue == 0) {
				if (this.id > oItem.getId()) {
					sortValue = 1;
				} else {
					sortValue = -1;
				}
			}
			return sortValue;
		}
		return 0;
	}

	@Transient
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	@Transient
	public String getStatusName() {
		if (this.status == null)
			statusName = "未设置";
		else
			switch (this.status.intValue()) {
			case 0:
				statusName = "未审核";
				break;
			case 1:
				statusName = "已审核";
				break;
			case 2:
				statusName = "已组卷";
				break;
			case 3:
				statusName = "已使用";
				break;
			case -1:
				statusName = "已废弃";
				break;
			default:
				statusName = "未设置";
				break;
			}
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	@Transient
	public String getSourceName() {
		if (this.source == null || "".equals(source))
			sourceName = "";
		else
			switch (Integer.parseInt(this.source)) {
			case 1:
				sourceName = "真题";
				break;
			case 2:
				sourceName = "模拟";
				break;
			case 3:
				sourceName = "自编";
				break;
			case 4:
				sourceName = "专项";
				break;
			case 9:
				sourceName = "其它";
				break;
			default:
				statusName = "未设置";
				break;
			}
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String fillZero(String str, int length) {
		String zero = "000000000000";
		if (str != null)
			zero += str.trim();
		return zero.substring(zero.length() - length);
	}

	// 在保存ITEM之前，生成试题代码, 待改进
	public void genItemCode() {
		if (ItemLable.projectVersion.equals("ky"))
			this.code = ItemLable.projectVersion
					+ fillZero(this.getSource(), 2)
					+ fillZero(this.getOriginalPaperCode(), 2)
					+ fillZero(this.getItemType().getCode(), 3)
					+ fillZero(this.getYear(), 4)
					+ (this.originalItemNum.equals("") ? "000"
							: this.originalItemNum);
		if (ItemLable.projectVersion.equals("mpc")) {
			if (subject.getCode().equals("EP")) {
				this.code = "dd_ag_" + originalPaperCode + '_' + originalItemNum;
			} else {
				if ((this.code==null) || this.code.equals("")) {
					String firstKPCode = this.getKnowledgePoints().iterator().next().getCode();
					this.code = firstKPCode + "0000";
				}	
			}
		}
	}

	@Transient
	public Boolean getFilterShow() {
		return filterShow;
	}

	public void setFilterShow(Boolean filterShow) {
		this.filterShow = filterShow;
	}

	@Transient
	public String getContentView() {
		return contentView;
	}

	public void setContentView(String contentView) {
		this.contentView = contentView;
	}

	@Transient
	public String getKnowledgePointsName() {
		String retValue = "";
		if (knowledgePoints != null) {
			for (KnowledgePoint knowledgePoint : knowledgePoints) {
				retValue += knowledgePoint.getName() + "/"
						+ knowledgePoint.getCode() + ";";
			}
			retValue = retValue.replaceAll(";$", "");
		}
		knowledgePointsName = retValue;
		return knowledgePointsName;
	}

	public void setKnowledgePointsName(String knowledgePointsName) {
		this.knowledgePointsName = knowledgePointsName;
	}

	@Transient
	public String getItemThemesName() {
		String retValue = "";
		if (itemThemes != null) {
			for (ItemTheme itemTheme : itemThemes) {
				retValue += itemTheme.getName() + "/" + itemTheme.getCode()
						+ ";";
			}
			retValue = retValue.replaceAll(";$", "");
		}
		itemThemesName = retValue;
		return itemThemesName;
	}

	public void setItemThemesName(String itemThemesName) {
		this.itemThemesName = itemThemesName;
	}

	@Transient
	public long getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	
	@Transient
	public List getAnswers() {
		// 构造开放性答案
		answers = new ArrayList();
		if (null != getCorrectAnswer() && !"".equals(getCorrectAnswer())) {
			String [] correctAnswers = getCorrectAnswer().split("；");
			for (int i = 0; i < correctAnswers.length; i++) {
				Map map = new HashMap();
				if(correctAnswers[i].indexOf("<img")>-1||correctAnswers[i].indexOf("math")>-1){
					map.put("type","gongShi");
				}else{
					map.put("type","text");
				}
				map.put("value",MathUtil.addMathXMLIfMathAnswer(correctAnswers[i]));
				answers.add(map);
			}
		}
		return answers;
	}
		
	@Transient
	public List getAnswersView() {
		List answersView = getAnswers();
		for(Object answer: answersView) {
			String type = (String)((Map)answer).get("type");
			if (type.equals("text")) {
				String value = (String)((Map)answer).get("value");
				value = value.replaceAll(">", "&gt;").replaceAll("<", "&lt;");
				((Map)answer).put("value", value);
			}
		}
		return answersView;
	}

	public void setAnswers(List answers) {
		this.answers = answers;
	}

	@Transient
	public ExamProperty getExamProperty() {
		return examProperty;
	}

	public void setExamProperty(ExamProperty examProperty) {
		this.examProperty = examProperty;
	}
	
	@Transient
	public Integer getAnsweringTimeByMin(){
		if(this.answeringTime!=null){
			return this.answeringTime/60;
		}
		return null;
	}

	public void setAnsweringTimeByMin(Integer answeringTimeByMin) {
		if(answeringTimeByMin==null)
			answeringTimeByMin=0;
		this.answeringTime= answeringTimeByMin*60;
	}
	
	@Transient
	public Integer getReadingTimeByMin() {
		if(this.readingTime!=null){
			return this.readingTime/60;
		}
		return null;
	}

	public void setReadingTimeByMin(Integer readingTimeByMin) {
		if(readingTimeByMin==null)
			readingTimeByMin=0;
		this.readingTime= readingTimeByMin*60;
	}

	/**
	 * 答案选项按伪代码重新排序
	 * @param randomCodes
	 */
	public void randomAnswerOption(String randomCodes) {
		this.answerOptions = getRandomAnswerOption(answerOptions,randomCodes);
	}
	
	/**
	 * 重新排序
	 * @param randomCodes
	 */
	public static Set<AnswerOption> getRandomAnswerOption(Set<AnswerOption> answerOptions,String randomCodes) {
		Set<AnswerOption> options = new TreeSet<AnswerOption>(AnswerOption.getAnswerOptionSorter());		
		int index = 0;
		for (AnswerOption answerOption: answerOptions) {
			answerOption.setPseudoCode(String.valueOf(randomCodes.charAt(index)));
			options.add(answerOption);
			index++;
		}
		return options;
	}
	
	/**
	 * 重新排序答案
	 * @param randomCodes
	 */
	public static String getRandomCrrectAnswer(String randomCodes,String correctAnswer) {
		String defOrder = "ABCDEFGHIJK";
		String order = randomCodes;
		if(order==null||"".equals(order.trim())){
			order = defOrder;
		}
		String newCorrectAnswer="";
		for(int i=0;i<correctAnswer.length();i++){
			int index =  order.indexOf(correctAnswer.substring(i,i+1));
			if(index!=-1){
				newCorrectAnswer += defOrder.substring(index,index+1);
			}
		}
		return newCorrectAnswer; 
	}
	
	/**
	 * 试题审校状态
	 * @return
	 */
	@Column(name="revise_status")
	public Integer getReviseStatus() {
		return reviseStatus;
	}

	public void setReviseStatus(Integer reviseStatus) {
		this.reviseStatus = reviseStatus;
	}
	
	@Transient
	public String getReviseStatusName() {
		return this.reviseStatus==0?"未通过":"已通过";
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "item")
	@OrderBy("reviseTime")
	public Set<ItemRevise> getItemRevises() {
		return this.itemRevises;
	}

	public void setItemRevises(Set<ItemRevise> itemRevises) {
		this.itemRevises = itemRevises;
	}

}