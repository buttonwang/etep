package com.ambow.trainingengine.bug.util;

import java.util.List;
import java.util.Set;

import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;

public class ItemAnswerVO {
	Item item ;
	List<String> answerViews;
	List<SubItemAnswerVO> subItemAnswerVOLst;
	String type;
	String answerStr;
	String answerOptionOrder;
	String correctAnswer;
	List answers;
	Set<AnswerOption> answerOptions;
	String analysisAtLarge1;
	String analysisAtLarge2;
	String analysisAtLarge3;
	Integer stars=0;
	
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
	public String getAnswerOptionOrder() {
		return answerOptionOrder;
	}
	public void setAnswerOptionOrder(String answerOptionOrder) {
		this.answerOptionOrder = answerOptionOrder;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<String> getAnswerViews() {
		return answerViews;
	}
	public void setAnswerViews(List<String> itemAnswerViews) {
		this.answerViews = itemAnswerViews;
	}
	public List<SubItemAnswerVO> getSubItemAnswerVOLst() {
		return subItemAnswerVOLst;
	}
	public void setSubItemAnswerVOLst(List<SubItemAnswerVO> subItemAnswerVOLst) {
		this.subItemAnswerVOLst = subItemAnswerVOLst;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAnswerStr() {
		return answerStr;
	}
	public void setAnswerStr(String answerStr) {
		this.answerStr = answerStr;
	}
	public Set<AnswerOption> getAnswerOptions() {
		return answerOptions;
	}
	public void setAnswerOptions(Set<AnswerOption> answerOptions) {
		this.answerOptions = answerOptions;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer starts) {
		this.stars = starts;
	}
	public List getAnswers() {
		return answers;
	}
	public void setAnswers(List answers) {
		this.answers = answers;
	}
}
