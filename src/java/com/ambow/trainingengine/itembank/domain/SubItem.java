package com.ambow.trainingengine.itembank.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.PauseAnswerStatus;
import com.ambow.trainingengine.exam.web.data.ExamProperty;
import com.ambow.trainingengine.itembank.util.MathUtil;

/**
 * SubItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sub_item")
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public class SubItem implements java.io.Serializable {

	private static final long serialVersionUID = 106989486499013175L;

	// Fields
	private Integer id;
	private Integer orderNum;
	private Item item;
	private String originalSubItemNum;
	private String content;
	private String contentTranslation;
	private String correctAnswer;
	private String answerAnalysis;
	private String relatedKeyPoints;
	private String relatedArticle;
	private String keyAnswerWords;
	private String keySentanceAnalysis;
	private String keyWords;
	private String keySentances;
	private Float difficultyValue;

	// 数理化
	private String hint;
	private String answerPrototype;
	private String answerGroup;
	private String analysisAtLarge1;
	private String analysisAtLarge2;
	private String analysisAtLarge3;
	private String skills;
	private Float score;
	private String score2;
	private Integer answeringTime;
	
	private Set<CurrentAnswersStatus> currentAnswersStatuses = new HashSet<CurrentAnswersStatus>(0);
	private Set<HistoryAnswerStatus> historyAnswerStatuses = new HashSet<HistoryAnswerStatus>(0);
	private Set<AnswerOption> answerOptions = new HashSet<AnswerOption>(0);
	private Set<PauseAnswerStatus> pauseAnswerStatuses = new HashSet<PauseAnswerStatus>(0);
	private Set<KnowledgePoint> knowledgePoints = new HashSet<KnowledgePoint>(0);
	private Set<KnowledgePoint> knowledgePoints2 = new HashSet<KnowledgePoint>(0);

	private ExamProperty examProperty; //供答题业务使用的属性 

	// 控制显示时的一些属性
	private Integer itemNum;	
	private Boolean filterShow = false;// 默认为false 值为false的题filter时不显示!
	private Boolean enable = true;
	private String code = "";
	private String statusName = "";

	private Integer answeringTimeByMin;
	
	private List answers;
	// Constructors

	/** default constructor */
	public SubItem() {
	}

	/** minimal constructor */
	public SubItem(Item item) {
		this.item = item;
	}

	/** full constructor */
	public SubItem(Integer orderNum, Set<KnowledgePoint> knowledgePoints,
			Item item, String originalSubItemNum, String content,
			String contentTranslation, String correctAnswer,
			String answerAnalysis, String relatedKeyPoints,
			String relatedArticle, String keyAnswerWords,
			String keySentanceAnalysis, String keyWords, String keySentances,
			Set<CurrentAnswersStatus> currentAnswersStatuses,
			Set<HistoryAnswerStatus> historyAnswerStatuses,
			Set<AnswerOption> answerOptions,
			Set<PauseAnswerStatus> pauseAnswerStatuses, String hint,
			String answerPrototype, String skills, String analysisAtLarge1,
			String analysisAtLarge2, String analysisAtLarge3, Float score,
			String score2, Integer answeringTime) {
		this.knowledgePoints = knowledgePoints;
		this.orderNum = orderNum;
		this.item = item;
		this.originalSubItemNum = originalSubItemNum;
		this.content = content;
		this.contentTranslation = contentTranslation;
		this.correctAnswer = correctAnswer;
		this.answerAnalysis = answerAnalysis;
		this.relatedKeyPoints = relatedKeyPoints;
		this.relatedArticle = relatedArticle;
		this.keyAnswerWords = keyAnswerWords;
		this.keySentanceAnalysis = keySentanceAnalysis;
		this.keyWords = keyWords;
		this.keySentances = keySentances;
		this.currentAnswersStatuses = currentAnswersStatuses;
		this.historyAnswerStatuses = historyAnswerStatuses;
		this.answerOptions = answerOptions;
		this.pauseAnswerStatuses = pauseAnswerStatuses;
		this.hint = hint;
		this.answerPrototype = answerPrototype;
		this.skills = skills;
		this.analysisAtLarge1 = analysisAtLarge1;
		this.analysisAtLarge2 = analysisAtLarge2;
		this.analysisAtLarge3 = analysisAtLarge3;
		this.score = score;
		this.score2 = score2;
		this.answeringTime = answeringTime;
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
	@JoinColumn(name = "item_id", nullable = false)
	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Column(name = "original_sub_item_num", length = 20)
	public String getOriginalSubItemNum() {
		return this.originalSubItemNum;
	}

	public void setOriginalSubItemNum(String originalSubItemNum) {
		this.originalSubItemNum = originalSubItemNum;
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

	@Column(name = "correct_answer", length = 65535)
	public String getCorrectAnswer() {
		return this.correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	@Column(name = "answer_analysis", length = 65535)
	public String getAnswerAnalysis() {
		return this.answerAnalysis;
	}

	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}

	@Column(name = "related_key_points", length = 1500)
	public String getRelatedKeyPoints() {
		return this.relatedKeyPoints;
	}

	public void setRelatedKeyPoints(String relatedKeyPoints) {
		this.relatedKeyPoints = relatedKeyPoints;
	}

	@Column(name = "related_article", length = 1500)
	public String getRelatedArticle() {
		return this.relatedArticle;
	}

	public void setRelatedArticle(String relatedArticle) {
		this.relatedArticle = relatedArticle;
	}

	@Column(name = "key_answer_words", length = 1500)
	public String getKeyAnswerWords() {
		return this.keyAnswerWords;
	}

	public void setKeyAnswerWords(String keyAnswerWords) {
		this.keyAnswerWords = keyAnswerWords;
	}

	@Column(name = "key_sentance_analysis", length = 1500)
	public String getKeySentanceAnalysis() {
		return this.keySentanceAnalysis;
	}

	public void setKeySentanceAnalysis(String keySentanceAnalysis) {
		this.keySentanceAnalysis = keySentanceAnalysis;
	}

	@Column(name = "key_words", length = 65535)
	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	@Column(name = "key_sentances", length = 65535)
	public String getKeySentances() {
		return this.keySentances;
	}

	public void setKeySentances(String keySentances) {
		this.keySentances = keySentances;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "subItem")
	public Set<CurrentAnswersStatus> getCurrentAnswersStatuses() {
		return this.currentAnswersStatuses;
	}

	public void setCurrentAnswersStatuses(
			Set<CurrentAnswersStatus> currentAnswersStatuses) {
		this.currentAnswersStatuses = currentAnswersStatuses;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "subItem")
	public Set<HistoryAnswerStatus> getHistoryAnswerStatuses() {
		return this.historyAnswerStatuses;
	}

	public void setHistoryAnswerStatuses(
			Set<HistoryAnswerStatus> historyAnswerStatuses) {
		this.historyAnswerStatuses = historyAnswerStatuses;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "subItem")
	@OrderBy("code")
	public Set<AnswerOption> getAnswerOptions() {
		return this.answerOptions;
	}

	public void setAnswerOptions(Set<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "subItem")
	public Set<PauseAnswerStatus> getPauseAnswerStatuses() {
		return this.pauseAnswerStatuses;
	}

	public void setPauseAnswerStatuses(
			Set<PauseAnswerStatus> pauseAnswerStatuses) {
		this.pauseAnswerStatuses = pauseAnswerStatuses;
	}

	@Column(name = "difficulty_value")
	public Float getDifficultyValue() {
		return difficultyValue;
	}

	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}

	@Transient
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	@ManyToMany(targetEntity = KnowledgePoint.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "knowledge_point_subitem_ref", joinColumns = @JoinColumn(name = "sub_item_id"), inverseJoinColumns = @JoinColumn(name = "knowledge_point_code"))
	public Set<KnowledgePoint> getKnowledgePoints() {
		return knowledgePoints;
	}

	public void setKnowledgePoints(Set<KnowledgePoint> knowledgePoints) {
		this.knowledgePoints = knowledgePoints;
	}

	@ManyToMany(targetEntity = KnowledgePoint.class, cascade = {
			CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "sub_item_sub_knowledge_point", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "code"))
	public Set<KnowledgePoint> getKnowledgePoints2() {
		return knowledgePoints2;
	}

	public void setKnowledgePoints2(Set<KnowledgePoint> knowledgePoints2) {
		this.knowledgePoints2 = knowledgePoints2;
	}

	@Column(name = "order_num", length = 20)
	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	@Transient
	public Boolean getFilterShow() {
		return filterShow;
	}

	public void setFilterShow(Boolean filterShow) {
		this.filterShow = filterShow;
	}

	@Transient
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Transient
	public String getKnowledgePointsName() {
		String retValue = "";
		if (knowledgePoints != null) {
			for (KnowledgePoint knowledgePoint : knowledgePoints) {
				retValue += knowledgePoint.getName() + ";";
			}
			retValue = retValue.replaceAll(";$", "");
		}
		return retValue;
	}

	@Column(name = "hint")
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
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
	
	@Column(name = "analysis_at_large_1")
	public String getAnalysisAtLarge1() {
		return analysisAtLarge1;
	}

	public void setAnalysisAtLarge1(String analysisAtLarge1) {
		this.analysisAtLarge1 = analysisAtLarge1;
	}

	@Column(name = "analysis_at_large_2")
	public String getAnalysisAtLarge2() {
		return analysisAtLarge2;
	}

	public void setAnalysisAtLarge2(String analysisAtLarge2) {
		this.analysisAtLarge2 = analysisAtLarge2;
	}

	@Column(name = "analysis_at_large_3")
	public String getAnalysisAtLarge3() {
		return analysisAtLarge3;
	}

	public void setAnalysisAtLarge3(String analysisAtLarge3) {
		this.analysisAtLarge3 = analysisAtLarge3;
	}

	@Column(name = "skills")
	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	@Column(name = "score")
	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	@Column(name = "score2")
	public String getScore2() {
		return score2;
	}

	public void setScore2(String score2) {
		this.score2 = score2;
	}

	@Column(name = "answering_time")
	public Integer getAnsweringTime() {
		return answeringTime;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
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
		
		/*else{
			String str [] =  getAnswerPrototype().split("；");
			for (int i = 0; i < str.length; i++) {
				Map map = new HashMap();
				if(str[i].indexOf("<img")>-1){
					map.put("type","gongShi");
				}else{
					map.put("type","text");
				}
				map.put("value",str[i].replaceAll("/<img(\\s|\\S)+?>/igm","|____|").replaceAll("/<(\\s|\\S)+?>/igm",""));
				System.out.println(map.get("value"));
				answers.add(map);
			}
			
			
		}*/
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
		if(answeringTime==null)
			answeringTime=0;
		return this.answeringTime/60;
	}

	public void setAnsweringTimeByMin(Integer answeringTimeByMin) {
		if(answeringTimeByMin==null)
			answeringTimeByMin=0;
		this.answeringTime = answeringTimeByMin*60;
	}
	
	@Transient
	public String getCode() {
		return item.getCode();
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Transient
	public String getStatusName() {
		return item.getStatusName();
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}