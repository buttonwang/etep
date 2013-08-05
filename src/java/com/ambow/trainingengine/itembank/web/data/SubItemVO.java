package com.ambow.trainingengine.itembank.web.data;

import com.ambow.trainingengine.itembank.domain.Item;

public class SubItemVO {

	private Integer itemId;
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
	private Float  difficultyValue;

	private String knowledgePointCodes;
	private String knowledgePointNames;

	// 数理化
	private String hint;
	private String answerPrototype;
	private String analysisAtLarge1;
	private String analysisAtLarge2;
	private String analysisAtLarge3;
	private String skills;
	private Float score;
	private String score2;
	private Integer answeringTime;
	
	private Integer answeringTimeByMin;
	private Integer readingTimeByMin;
	private String  answerGroup;
	public Integer getAnsweringTimeByMin() {
		return answeringTimeByMin;
	}

	public void setAnsweringTimeByMin(Integer answeringTimeByMin) {
		this.answeringTimeByMin = answeringTimeByMin;
	}

	public Integer getReadingTimeByMin() {
		return readingTimeByMin;
	}
	
	public void setReadingTimeByMin(Integer readingTimeByMin) {
		this.readingTimeByMin = readingTimeByMin;
	}
	
	public String getAnalysisAtLarge1() {
		return analysisAtLarge1;
	}

	public String getAnalysisAtLarge2() {
		return analysisAtLarge2;
	}

	public String getAnalysisAtLarge3() {
		return analysisAtLarge3;
	}

	public String getAnswerAnalysis() {
		return answerAnalysis;
	}

	public Integer getAnsweringTime() {
		return answeringTime;
	}

	public String getAnswerPrototype() {
		return answerPrototype;
	}

	public String getContent() {
		return content;
	}

	public String getContentTranslation() {
		return contentTranslation;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public Float getDifficultyValue() {
		return difficultyValue;
	}

	public String getHint() {
		return hint;
	}

	public Integer getId() {
		return id;
	}

	public Item getItem() {
		return item;
	}

	public Integer getItemId() {
		return this.itemId;
	}

	public String getKeyAnswerWords() {
		return keyAnswerWords;
	}

	public String getKeySentanceAnalysis() {
		return keySentanceAnalysis;
	}

	public String getKeySentances() {
		return keySentances;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public String getKnowledgePointCodes() {
		return knowledgePointCodes;
	}

	public String getKnowledgePointNames() {
		return knowledgePointNames;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public String getOriginalSubItemNum() {
		return originalSubItemNum;
	}

	public String getRelatedArticle() {
		return relatedArticle;
	}

	public String getRelatedKeyPoints() {
		return relatedKeyPoints;
	}

	public Float getScore() {
		return score;
	}

	public String getScore2() {
		return score2;
	}

	public String getSkills() {
		return skills;
	}

	public void setAnalysisAtLarge1(String analysisAtLarge1) {
		this.analysisAtLarge1 = analysisAtLarge1;
	}

	public void setAnalysisAtLarge2(String analysisAtLarge2) {
		this.analysisAtLarge2 = analysisAtLarge2;
	}

	public void setAnalysisAtLarge3(String analysisAtLarge3) {
		this.analysisAtLarge3 = analysisAtLarge3;
	}

	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}

	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}

	public void setAnswerPrototype(String answerPrototype) {
		this.answerPrototype = answerPrototype;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentTranslation(String contentTranslation) {
		this.contentTranslation = contentTranslation;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setKeyAnswerWords(String keyAnswerWords) {
		this.keyAnswerWords = keyAnswerWords;
	}

	public void setKeySentanceAnalysis(String keySentanceAnalysis) {
		this.keySentanceAnalysis = keySentanceAnalysis;
	}

	public void setKeySentances(String keySentances) {
		this.keySentances = keySentances;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public void setKnowledgePointCodes(String knowledgePointCodes) {
		this.knowledgePointCodes = knowledgePointCodes;
	}

	public void setKnowledgePointNames(String knowledgePointNames) {
		this.knowledgePointNames = knowledgePointNames;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public void setOriginalSubItemNum(String originalSubItemNum) {
		this.originalSubItemNum = originalSubItemNum;
	}

	public void setRelatedArticle(String relatedArticle) {
		this.relatedArticle = relatedArticle;
	}

	public void setRelatedKeyPoints(String relatedKeyPoints) {
		this.relatedKeyPoints = relatedKeyPoints;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public void setScore2(String score2) {
		this.score2 = score2;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getAnswerGroup() {
		return answerGroup;
	}

	public void setAnswerGroup(String answerGroup) {
		this.answerGroup = answerGroup;
	}

	
}
