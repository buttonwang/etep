package com.ambow.trainingengine.exam.web.data;

/*
 * SubAnswer.java
 * 
 * Created on 2009-1-15 下午02:13:59
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * correctAnswer: 正确答案
 * correctAnswer：答案类型
 * userAnswer：用户答案
 * isRight：对错
 * score：分值
 * userScore：答题分值
 * groupId: 组编号
 * correctAnswerUsed: 正确答案是否可用，与组编号组合来判分
 * Changes 
 * -------
 * $log$
 */

public class SubAnswer {

	private String correctAnswer;

	private String answerType;	//normal: 字符类   formula：公式类

	private String userAnswer;
	
	private Boolean isRight;

	private Float score;

	private Float userScore;
	
	private int groupId;
	
	private boolean correctAnswerUsed;

	public SubAnswer() {
		this.userAnswer = "";
		this.correctAnswer = "";
		this.answerType = "normal";
		this.isRight = false;
		this.groupId = 0;
		this.correctAnswerUsed = false;
	}
	
	public SubAnswer(String correctAnswer, Float score) {
		this.userAnswer = "";
		this.correctAnswer = correctAnswer;
		if (correctAnswer.contains("<math")) this.answerType = "formula";
		else this.answerType = "normal";
		this.isRight = false;
		this.score = score;
		this.groupId = 0;
		this.correctAnswerUsed = false;
	}

	public String getUserAnswerTrans() {
		String ret = this.userAnswer;
		if (this.answerType.equals("formula")) {
			ret = ret.replaceAll("\"", "'");
			ret = ret.replaceAll(">&lt;<", ">&amp;lt;<");
			ret = ret.replaceAll(">&gt;<", ">&amp;gt;<");
		} else {
			ret = ret.replace("$", "");
		}
		return ret;
	}
	
	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public Float getScore() {
		return score;
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public Float getUserScore() {
		return userScore;
	}

	public void setUserScore(Float userScore) {
		this.userScore = userScore;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	

	public boolean isCorrectAnswerUsed() {
		return correctAnswerUsed;
	}

	public void setCorrectAnswerUsed(boolean correctAnswerUsed) {
		this.correctAnswerUsed = correctAnswerUsed;
	}
	
}
