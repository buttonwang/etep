package com.ambow.trainingengine.itembank.web.data;

import java.util.Date;


public class ItemVO{
	private Integer id;
	private String code = "";
	private String year = "";
	private String source = "";
	private String sourceBook;
	private String sourceFile;
	private String originalPaperCode = "";
	private String originalItemNum = "";
	private Float score;
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
	private String keywords;
	private String keySentances;
	private Integer creater;
	private Date createdTime;
	private Integer updater;
	private Date updatedTime;
	private Integer verifier;
	private Date verifiedTime;
	private Integer status;
	private Float opinion;
	private Float itemValue;
    private String abilityValue;
   
    private String importFile;
	
	private String gradeNames;
	private String gradeCodes;
	private String regionCode;
	private String itemTypeCode;
	private String subjectCode;
	private String knowledgePointCodes;
	private String knowledgePointCodes2;
	private String knowledgePointNames;
	private String knowledgePointNames2;
	private String itemThemeNames;
	private String itemThemeCodes;
	private String answerPrototype;
	private String score2;
	private Integer courseVersion;
	 private Integer answeringTimeByMin;
	 private Integer readingTimeByMin;
	 private String answerGroup;
	 private String reviewRound;
	 public String getReviewRound() {
		return reviewRound;
	}
	public void setReviewRound(String reviewRound) {
		this.reviewRound = reviewRound;
	}
	public String getAnswerPrototype() {
		return answerPrototype;
	}
	public void setAnswerPrototype(String answerPrototype) {
		this.answerPrototype = answerPrototype;
	}
	private String[] answerOptionIds;
	private String[] answerOptionCodes;
	private String[] answerOptionContents;
	private String[] answerOptionTranslations;
	
	public String getGradeNames() {
		return gradeNames;
	}
	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}
	public String getGradeCodes() {
		return gradeCodes;
	}
	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public String getAbilityValue() {
		return abilityValue;
	}
	public void setAbilityValue(String abilityValue) {
		this.abilityValue = abilityValue;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceBook() {
		return sourceBook;
	}
	public void setSourceBook(String sourceBook) {
		this.sourceBook = sourceBook;
	}
	public String getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public String getOriginalPaperCode() {
		return originalPaperCode;
	}
	public void setOriginalPaperCode(String originalPaperCode) {
		this.originalPaperCode = originalPaperCode;
	}
	public String getOriginalItemNum() {
		return originalItemNum;
	}
	public void setOriginalItemNum(String originalItemNum) {
		this.originalItemNum = originalItemNum;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getDifficultyValue() {
		return difficultyValue;
	}
	public void setDifficultyValue(Float difficultyValue) {
		this.difficultyValue = difficultyValue;
	}
	public Float getValidityValue() {
		return validityValue;
	}
	public void setValidityValue(Float validityValue) {
		this.validityValue = validityValue;
	}
	public Integer getAnsweringTime() {
		return answeringTime;
	}
	public void setAnsweringTime(Integer answeringTime) {
		this.answeringTime = answeringTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContentTranslation() {
		return contentTranslation;
	}
	public void setContentTranslation(String contentTranslation) {
		this.contentTranslation = contentTranslation;
	}
	public Integer getWordCount() {
		return wordCount;
	}
	public void setWordCount(Integer wordCount) {
		this.wordCount = wordCount;
	}
	public Integer getReadingTime() {
		return readingTime;
	}
	public void setReadingTime(Integer readingTime) {
		this.readingTime = readingTime;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public String getAnswerAnalysis() {
		return answerAnalysis;
	}
	public void setAnswerAnalysis(String answerAnalysis) {
		this.answerAnalysis = answerAnalysis;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public String getAnalysisAtLarge1() {
		return analysisAtLarge1;
	}
	public void setAnalysisAtLarge1(String analysisAtLarge1) {
		this.analysisAtLarge1 = analysisAtLarge1;
	}
	public String getAnalysisAtLarge2() {
		return analysisAtLarge2;
	}
	public void setAnalysisAtLarge2(String analysisAtLarge2) {
		this.analysisAtLarge2 = analysisAtLarge2;
	}
	public String getAnalysisAtLarge3() {
		return analysisAtLarge3;
	}
	public void setAnalysisAtLarge3(String analysisAtLarge3) {
		this.analysisAtLarge3 = analysisAtLarge3;
	}
	public String getThinkingAnalyse() {
		return thinkingAnalyse;
	}
	public void setThinkingAnalyse(String thinkingAnalyse) {
		this.thinkingAnalyse = thinkingAnalyse;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public String getDifficultSentance() {
		return difficultSentance;
	}
	public void setDifficultSentance(String difficultSentance) {
		this.difficultSentance = difficultSentance;
	}
	public String getScoringKeywords() {
		return scoringKeywords;
	}
	public void setScoringKeywords(String scoringKeywords) {
		this.scoringKeywords = scoringKeywords;
	}
	public String getWritingTemplate() {
		return writingTemplate;
	}
	public void setWritingTemplate(String writingTemplate) {
		this.writingTemplate = writingTemplate;
	}
	public String getScoringNorm() {
		return scoringNorm;
	}
	public void setScoringNorm(String scoringNorm) {
		this.scoringNorm = scoringNorm;
	}
	public Integer getApplicableObject() {
		return applicableObject;
	}
	public void setApplicableObject(Integer applicableObject) {
		this.applicableObject = applicableObject;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeySentances() {
		return keySentances;
	}
	public void setKeySentances(String keySentances) {
		this.keySentances = keySentances;
	}
	public Integer getCreater() {
		return creater;
	}
	public void setCreater(Integer creater) {
		this.creater = creater;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Integer getUpdater() {
		return updater;
	}
	public void setUpdater(Integer updater) {
		this.updater = updater;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public Integer getVerifier() {
		return verifier;
	}
	public void setVerifier(Integer verifier) {
		this.verifier = verifier;
	}
	public Date getVerifiedTime() {
		return verifiedTime;
	}
	public void setVerifiedTime(Date verifiedTime) {
		this.verifiedTime = verifiedTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Float getOpinion() {
		return opinion;
	}
	public void setOpinion(Float opinion) {
		this.opinion = opinion;
	}
	public Float getItemValue() {
		return itemValue;
	}
	public void setItemValue(Float itemValue) {
		this.itemValue = itemValue;
	}
	public String getKnowledgePointCodes() {
		return knowledgePointCodes;
	}
	public void setKnowledgePointCodes(String knowledgePointCodes) {
		this.knowledgePointCodes = knowledgePointCodes;
	}
	public String getKnowledgePointNames() {
		return knowledgePointNames;
	}
	public void setKnowledgePointNames(String knowledgePointNames) {
		this.knowledgePointNames = knowledgePointNames;
	}
	public String getItemThemeNames() {
		return itemThemeNames;
	}
	public void setItemThemeNames(String itemThemeNames) {
		this.itemThemeNames = itemThemeNames;
	}
	public String getItemThemeCodes() {
		return itemThemeCodes;
	}
	public void setItemThemeCodes(String itemThemeCodes) {
		this.itemThemeCodes = itemThemeCodes;
	}
	public String[] getAnswerOptionIds() {
		return answerOptionIds;
	}
	public void setAnswerOptionIds(String[] answerOptionIds) {
		this.answerOptionIds = answerOptionIds;
	}
	public String[] getAnswerOptionCodes() {
		return answerOptionCodes;
	}
	public void setAnswerOptionCodes(String[] answerOptionCodes) {
		this.answerOptionCodes = answerOptionCodes;
	}
	public String[] getAnswerOptionContents() {
		return answerOptionContents;
	}
	public void setAnswerOptionContents(String[] answerOptionContents) {
		this.answerOptionContents = answerOptionContents;
	}
	public String[] getAnswerOptionTranslations() {
		return answerOptionTranslations;
	}
	public void setAnswerOptionTranslations(String[] answerOptionTranslations) {
		this.answerOptionTranslations = answerOptionTranslations;
	}
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
	public String getScore2() {
		return score2;
	}
	public void setScore2(String score2) {
		this.score2 = score2;
	}
	public String getKnowledgePointCodes2() {
		return knowledgePointCodes2;
	}
	public void setKnowledgePointCodes2(String knowledgePointCodes2) {
		this.knowledgePointCodes2 = knowledgePointCodes2;
	}
	public String getKnowledgePointNames2() {
		return knowledgePointNames2;
	}
	public void setKnowledgePointNames2(String knowledgePointNames2) {
		this.knowledgePointNames2 = knowledgePointNames2;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String importFile) {
		this.importFile = importFile;
	}
	public Integer getCourseVersion() {
		return courseVersion;
	}
	public void setCourseVersion(Integer courseVersion) {
		this.courseVersion = courseVersion;
	}
	public String getAnswerGroup() {
		return answerGroup;
	}
	public void setAnswerGroup(String answerGroup) {
		this.answerGroup = answerGroup;
	}
	
	
	
}