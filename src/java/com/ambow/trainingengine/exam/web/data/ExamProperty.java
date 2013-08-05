package com.ambow.trainingengine.exam.web.data;

import java.util.ArrayList;
import java.util.List;

import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.itembank.util.MathUtil;

/*
 * ExamProperty.java
 * 
 * Created on 2009-1-8 下午03:27:21
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 试题的答卷属性，此类属性，试题、子题用同样的类。
 * isMark: 是否标记疑问
 * isDone: 是否已做题
 * isRight：是否正确
 * userAnswer: 用户答案
 * star: 星级
 * starInt: 星级整数部分
 * starHalf: 星级小数部分 1表示0.5星
 * itemNum: 题号	
 * filterShow: 默认为false 值为false的题filter时不显示
 * enable：是否可修改
 * subAnswers: 一题多空的试题，把每个空转化为一个SubAnswer对象
 * contentView: 题干的试图形式，对题干内容的处理和加工
 * userScore：答题分值 
 * answerOptionOrder：选项颠倒顺序
 * correctAnswer: 正确答案，选择题调序后的正确答案。
 * analysisAtLarge：详解信息，在选项颠倒的情况下详解中的选项标志也要颠倒。
 * diligence: 勤奋积分 做第1次给题分，第2次1/2题分，第3次1/3题分，第4次1/4题分，第5次及以上0
 * wisdom:    智慧积分 关联第一次做正确所用次数，1次做对给5*题分，2次做对给3*题分，3次做对给2*题分，4次及以上给1*题分。
 * TODO: 考虑把Item SubItem 中和答题有关的属性都移到此类中 如 ItemNum
 * Changes 
 * -------
 * $log$
 * 
 */


public class ExamProperty {
	
	private Boolean isMark;
	
	private Boolean isDone;
	
	private Boolean isRight;
	
	private String userAnswer;

	private Float star;

	private Integer starInt;
	
	private Integer starHalf;
	
	private Integer itemNum;

	private Boolean filterShow;

	private Boolean enable;
	
	private Float userScore;	

	private String contentView;
	
	private List<SubAnswer> subAnswers;	
	
	private String answerOptionOrder;
	
	private String correctAnswer;
	
	private String analysisAtLarge;
	
	private Float diligence;
	
	private Float wisdom;

	public ExamProperty() {
		this.isMark = false;
		this.isDone = false;
		this.isRight = false;
		this.filterShow = false;
		this.enable = true;
		this.userAnswer = "";
		this.userScore = 0f;
		this.answerOptionOrder = "";
		this.correctAnswer = "";
		this.analysisAtLarge = "";
		this.diligence = 0f;
		this.wisdom = 0f;
	}
	
	/**
	 * 生成试题答题属性
	 * MPC3X, MPC4X 有subAnswers
	 * @param typeCode
	 * @param correctAnswer
	 * @param score2
	 * @param score
	 */
	public ExamProperty(String typeCode, String correctAnswer, String score2, Float score, String answerGroup) {
		this.isMark = false;
		this.isDone = false;
		this.isRight = false;
		this.filterShow = false;
		this.enable = true;
		this.userAnswer = "";
		this.userScore = 0f;
		this.answerOptionOrder = "";
		this.correctAnswer = "";
		this.analysisAtLarge = "";
		this.diligence = 0f;
		this.wisdom = 0f;
		
		if (typeCode.equals("MPC3X")||typeCode.equals("MPC4X"))
			genSubAnswers(correctAnswer, score2, score, answerGroup);
	}
	
	/**
	 * 设置一题多空的每个空的正确答案和分值
	 *  若没有设置分值分布，则每个空的分值 = 试题分值 / 空的数目 	 
	 * @param correctAnswer：试题的正确大拿
	 * @param score2： 试题的分值分布
	 * @param score    试题的总分值
	 */
	private void genSubAnswers(String correctAnswer, String score2, Float score, String answerGroup) {
		
		this.subAnswers = new ArrayList<SubAnswer>(0);
		
		if (!correctAnswer.contains("；")) {
			subAnswers.add(new SubAnswer(correctAnswer, score));
		} else {
			String[] answers = correctAnswer.split("；");
			String[] score2s = score2.split("；");
			
			int i = 0;
			Float subScore;
			for (String answer: answers) {
				if ((score2s==null)||(score2s.length<1)) subScore = score / answers.length;
				if (i > score2s.length-1) subScore = 0f;
				else subScore = Float.valueOf(score2s[i].trim().equals("")?"0":score2s[i]);
				subAnswers.add(new SubAnswer(answer, subScore));
				i++;
			}
		}
		
		if ((answerGroup!=null)&&(subAnswers.size()>1)) 
			genSubAnswersGroup(answerGroup);
	}
	
	/**
	 * 设置每个空的组属性若无则是0 
	 * @param answerGroup
	 */
	private void genSubAnswersGroup(String answerGroup) {
		answerGroup = answerGroup.replace("；", ";");
		answerGroup = answerGroup.replace("，", ",");
		answerGroup = answerGroup.replace("(", "").replace(")", "");
		answerGroup = answerGroup.replace("（", "").replaceAll("）", "");
		answerGroup = answerGroup.replaceAll("\\s", "");
		if (answerGroup.equals("")) return;
		String[] groups = answerGroup.split(";");
		
		int groupId = 1;
		for (String group: groups) {
			String[] groupAnswers = group.split(",");
			for (String groupAnswer: groupAnswers) {
				groupAnswer = groupAnswer.replaceAll("\\D", "");
				if (groupAnswer.trim().equals("")) continue;
				int groupAnswerId = Integer.valueOf(groupAnswer.trim()) - 1;
				if ((groupAnswerId>-1) && (groupAnswerId<subAnswers.size()))
					this.subAnswers.get(groupAnswerId).setGroupId(groupId);
			}
			groupId++;
		}
	}
	
	/**
	 * 移除开放性可互换答案组中的已答对的答案。
	 * @param groupId
	 * @param answer
	 */
	public void removeRightAnswerByGroupId(int groupId, String answer) {
		if (groupId==0) return;
		
		for (SubAnswer subAnswer: this.subAnswers) {
			if (subAnswer.getGroupId()==groupId) {
				String cutAnswer = subAnswer.getCorrectAnswer();
				
				if (cutAnswer.startsWith(answer+"$"))
					cutAnswer = cutAnswer.replaceFirst("^"+answer+"\\$", "");
				else if (cutAnswer.endsWith("$"+answer))
					cutAnswer = cutAnswer.replaceFirst("\\$"+answer+"$" , "");
				else
					cutAnswer = cutAnswer.replaceFirst("\\$"+answer+"\\$" , "\\$");				
				subAnswer.setCorrectAnswer(cutAnswer);
			}
		}
	}

	public Boolean getIsMark() {
		return isMark;
	}

	public void setIsMark(Boolean isMark) {
		this.isMark = isMark;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public Boolean getIsRight() {
		return isRight;
	}

	public void setIsRight(Boolean isRight) {
		this.isRight = isRight;
	}

	public String getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String userAnswer) {
		this.userAnswer = userAnswer;
	}	
	
	public Float getStar() {
		return star;
	}

	public void setStar(Float star) {
		this.star = star;
				
		if (star != null) {
			int[] starArr = ExamUtil.getStarArray(star);
			starInt = starArr[0];
			starHalf = starArr[1];
		} else {
			starInt = 0;
			starHalf = 0 ;
		}
	}

	public Integer getStarInt() {
		return starInt;
	}

	public void setStarInt(Integer starInt) {
		this.starInt = starInt;
	}

	public Integer getStarHalf() {
		return starHalf;
	}

	public void setStarHalf(Integer starHalf) {
		this.starHalf = starHalf;
	}

	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

	public Boolean getFilterShow() {
		return filterShow;
	}

	public void setFilterShow(Boolean filterShow) {
		this.filterShow = filterShow;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
		
	public Float getUserScore() {
		return userScore;
	}

	public void setUserScore(Float userScore) {
		this.userScore = userScore;
	}
	
	public String getContentView() {
		return contentView;
	}

	public void setContentView(String contentView) {
		this.contentView = contentView;
	}
	
	public List<SubAnswer> getSubAnswers() {
		return subAnswers;
	}

	public void setSubAnswers(List<SubAnswer> subAnswers) {
		this.subAnswers = subAnswers;
	}
	
	public String getAnswerOptionOrder() {
		return answerOptionOrder;
	}

	public void setAnswerOptionOrder(String answerOptionOrder) {
		this.answerOptionOrder = answerOptionOrder;
	}
	
	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getAnalysisAtLarge() {
		return analysisAtLarge;
	}

	public void setAnalysisAtLarge(String analysisAtLarge) {
		this.analysisAtLarge = analysisAtLarge;
	}
	
	public Float getDiligence() {
		return diligence;
	}

	public void setDiligence(Float diligence) {
		this.diligence = diligence;
	}

	public Float getWisdom() {
		return wisdom;
	}

	public void setWisdom(Float wisdom) {
		this.wisdom = wisdom;
	}
	
	// 返回填空题的正确答案，用户答对返回用户的试题，用户答错返回正确答案中的第一个正确答案。
	public List<String> getFillCorrectAnswer() {
		List<String> fillAnswer = new ArrayList<String>();
		for (SubAnswer subAnswer: this.getSubAnswers()) {
			String sub ="";
			if (subAnswer.getIsRight()) {
				sub = subAnswer.getUserAnswer();
			} else {
				sub = subAnswer.getCorrectAnswer().replace("\r\n", "").replaceAll("\\$.*", "");
			}
			if (subAnswer.getAnswerType().equals("formula")) {
				sub = MathUtil.addMathXMLIfMathAnswer(sub);
			}
			fillAnswer.add(sub.trim());
		}
		return fillAnswer;
	}
}
