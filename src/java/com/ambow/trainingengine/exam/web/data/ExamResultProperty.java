package com.ambow.trainingengine.exam.web.data;

import java.util.Set;

import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;

/*
 * ExamResultProperty.java
 * 答卷结果属性
 * Created on 2009-2-11 上午11:19:35
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class ExamResultProperty {

	private int itemCount=0;  //卷内试题数	
	private	int rightCount=0; //正确题数
	private	int errorCount=0; //错误题数
	private	int undoCount=0;  //未做题数	
	private	int markCount=0;  //疑问标记数

	//星级题数
	private	int zeroStarCount=0; 
	private	int halfStarCount=0;
	private	int oneStarCount=0;
	private	int twoStarCount=0;
	private	int threeStarCount=0;
	private	int fourStarCount=0;
	private	int fiveStarCount=0;
	
	private	float itemScore=0f;//题目总分
	private	float examScore=0f;//用户总得分	
	private float accuracyRate=0f; //正确率
	private float masteryRate=0f;  //掌握度
	private int spendTime; //实际用时
	
	private Set<EvaluatingAnswerStatus> evaluatingAnswerStatus = null; //用户评测知识点结果
	private int evaluatingAnswerSize;

	private Set<EvaluatingAnswerStatus> allEvaluatingAnswerStatus = null; //章前后评测知识点组合的结果
	private int allEvaluatingAnswerSize;
	
	public int getItemCount() {
		return itemCount;
	}
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	public int getRightCount() {
		return rightCount;
	}
	public void setRightCount(int rightCount) {
		this.rightCount = rightCount;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	public int getUndoCount() {
		return undoCount;
	}
	public void setUndoCount(int undoCount) {
		this.undoCount = undoCount;
	}
	public int getMarkCount() {
		return markCount;
	}
	public void setMarkCount(int markCount) {
		this.markCount = markCount;
	}
	public int getZeroStarCount() {
		return zeroStarCount;
	}
	public void setZeroStarCount(int zeroStarCount) {
		this.zeroStarCount = zeroStarCount;
	}
	public int getHalfStarCount() {
		return halfStarCount;
	}
	public void setHalfStarCount(int halfStarCount) {
		this.halfStarCount = halfStarCount;
	}
	public int getOneStarCount() {
		return oneStarCount;
	}
	public void setOneStarCount(int oneStarCount) {
		this.oneStarCount = oneStarCount;
	}
	public int getTwoStarCount() {
		return twoStarCount;
	}
	public void setTwoStarCount(int twoStarCount) {
		this.twoStarCount = twoStarCount;
	}
	public int getThreeStarCount() {
		return threeStarCount;
	}
	public void setThreeStarCount(int threeStarCount) {
		this.threeStarCount = threeStarCount;
	}
	public int getFourStarCount() {
		return fourStarCount;
	}
	public void setFourStarCount(int fourStarCount) {
		this.fourStarCount = fourStarCount;
	}
	public int getFiveStarCount() {
		return fiveStarCount;
	}
	public void setFiveStarCount(int fiveStarCount) {
		this.fiveStarCount = fiveStarCount;
	}
	public float getItemScore() {
		return itemScore;
	}
	public void setItemScore(float itemScore) {
		this.itemScore = itemScore;
	}
	public float getExamScore() {
		return examScore;
	}
	public void setExamScore(float examScore) {
		this.examScore = examScore;
	}
	//正确率取整
	public int getAccuracyRateInt() {
		return Math.round(accuracyRate);
	}
	public float getAccuracyRate() {
		return accuracyRate;
	}
	public void setAccuracyRate(float accuracyRate) {
		this.accuracyRate = accuracyRate;
	}
	public float getMasteryRate() {
		return masteryRate;
	}
	public void setMasteryRate(float masteryRate) {
		this.masteryRate = masteryRate;
	}
	public int getSpendTime() {
		return spendTime;
	}
	public void setSpendTime(int spendTime) {
		this.spendTime = spendTime;
	}	
	public String getSpendTimeStr(){
		return ExamUtil.getTimeStr(spendTime);
	}
	public String getSpendTimeStr2(){
		return ExamUtil.getTimeStr2(spendTime);
	}
	public Set<EvaluatingAnswerStatus> getEvaluatingAnswerStatus() {
		return evaluatingAnswerStatus;
	}
	public void setEvaluatingAnswerStatus(Set<EvaluatingAnswerStatus> evaluatingAnswerStatus) {
		this.evaluatingAnswerStatus = evaluatingAnswerStatus;
	}
	public int getEvaluatingAnswerSize() {
		if (evaluatingAnswerStatus==null) evaluatingAnswerSize = 0;
		else evaluatingAnswerSize = evaluatingAnswerStatus.size();
		return evaluatingAnswerSize;
	}
	public void setAllEvaluatingAnswerStatus(Set<EvaluatingAnswerStatus> allEvaluatingAnswerStatus) {
		this.allEvaluatingAnswerStatus = allEvaluatingAnswerStatus;
	}
	public Set<EvaluatingAnswerStatus> getAllEvaluatingAnswerStatus() {
		return allEvaluatingAnswerStatus;
	}
	public int getAllEvaluatingAnswerSize() {
		if (allEvaluatingAnswerStatus==null) allEvaluatingAnswerSize = 0;
		else allEvaluatingAnswerSize = allEvaluatingAnswerStatus.size();		
		return allEvaluatingAnswerSize;
	}
	
	public int getAllStarCount() {
		return zeroStarCount + halfStarCount + oneStarCount + twoStarCount
			+ threeStarCount + fourStarCount + fiveStarCount;
	}
}
