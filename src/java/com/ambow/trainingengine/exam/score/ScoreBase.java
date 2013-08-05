package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.web.data.SubAnswer;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * Score.java
 * 
 * Created on 2008-12-8 下午03:50:00
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 答题情况的基础类
 * Changes 
 * -------
 * $log$
 */

public class ScoreBase {

	String typeCode; 	  //题型代码
	String standAnswer="";   //标准答案
	String currentAnswer=""; //用户答案
	String standScorePart="";//标准答案试题分值分布
	String scorePart="";     //打分结果试题分值分布
	
	boolean rightFlag=false; //对错结果
	float standScore=0f; //试题分值  
	float score=0f;      //打分分值
	float cuScore=0f;    //主观题用户自评分	

	public void beforeItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		typeCode=item.getItemType().getCode();		
		if (cuAnswerStatus.getAnswer()!=null) currentAnswer=cuAnswerStatus.getAnswer();
		if (subjectScore==null) subjectScore = 0f;
		cuScore=subjectScore;
		
		if(subItem!=null){
			//有子题的情况，标准答案存放在子题中
			standAnswer=subItem.getCorrectAnswer()==null?"":subItem.getCorrectAnswer();
			
			//子题有分值的情况先取子题分值，没有的情况按父题的分值换算。
			if ((subItem.getScore()!=null) && (subItem.getScore()!=0f))
				standScore=subItem.getScore();
			else
				standScore=item.getScore()/item.getSubItems().size();
			
			//子题分值分布
			standScorePart = subItem.getScore2();
			
			if (subItem.getExamProperty() != null) {
				if (subItem.getExamProperty().getSubAnswers() != null) {
					for (SubAnswer subAnswer: subItem.getExamProperty().getSubAnswers()) {
						subAnswer.setIsRight(false);
						subAnswer.setCorrectAnswerUsed(false);
					}
				}
			}
		}else{
			standAnswer=item.getCorrectAnswer()==null?"":item.getCorrectAnswer();
			standScore=item.getScore();
			standScorePart = item.getScore2();
			
			if (item.getExamProperty() != null) {
				if (item.getExamProperty().getSubAnswers() != null) {
					for (SubAnswer subAnswer: item.getExamProperty().getSubAnswers()) {
						subAnswer.setIsRight(false);
						subAnswer.setCorrectAnswerUsed(false);
					}
				}
			}	
		}
	}
	
	public void afterItemScore(CurrentAnswersStatus cuAnswerStatus) {
		if (rightFlag) {
			//做对的情况下
			cuAnswerStatus.setIsCorrect(true);
			
			Integer conCorrect=cuAnswerStatus.getContinueCorrectTimes();
			if(conCorrect==null){
				cuAnswerStatus.setContinueCorrectTimes(1);
			}else{
				if(conCorrect<2)cuAnswerStatus.setContinueCorrectTimes(conCorrect+1);
			}
		}else{
			cuAnswerStatus.setIsCorrect(false);
			
			cuAnswerStatus.setContinueCorrectTimes(0);//设置连续对连续错属性
			Integer conFailure=cuAnswerStatus.getContinueFailureTimes();
			if(conFailure==null){
				cuAnswerStatus.setContinueFailureTimes(1);
			}else{
				if (conFailure<3) cuAnswerStatus.setContinueFailureTimes(conFailure+1);
			}
		}
		
		cuAnswerStatus.setScore(score);
		cuAnswerStatus.setItemScore2(scorePart);
		
		clearAnswerStatus();
	}
	
	private void clearAnswerStatus() {
		typeCode="";
		standAnswer="";
		currentAnswer="";
		standScorePart="";
		scorePart="";
		
		rightFlag=false;
		standScore=0f;
		score=0f;
		cuScore=0f;
	}

}
