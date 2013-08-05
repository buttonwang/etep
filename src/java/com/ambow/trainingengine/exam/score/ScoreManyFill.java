package com.ambow.trainingengine.exam.score;

import java.util.List;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.web.data.SubAnswer;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ScoreManyFill.java
 * 
 * Created on 2008-12-8 下午06:13:55
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 *  一对多填空判分类：比较用户答案与标准答案是否相同，开放性答案的处理。
 *  判分规则: 
 * Changes 
 * -------
 * $log$
 */

public class ScoreManyFill extends ScoreBase implements IScore {

	ScoreUtil scoreUtil;
	
	public void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		beforeItemScore(item, subItem, cuAnswerStatus, subjectScore);
		if (subItem!=null) scoreSubItem(subItem);
		if (subItem==null) scoreItem(item);
		afterItemScore(cuAnswerStatus);
	}

	private void scoreSubItem(SubItem subItem) {
		boolean error = false;
		for (SubAnswer subAnswer: subItem.getExamProperty().getSubAnswers()) {
			String cAnswer = scoreUtil.answerPrepare(subAnswer.getAnswerType(), subAnswer.getCorrectAnswer());
			String uAnswer = scoreUtil.answerPrepare(subAnswer.getAnswerType(), subAnswer.getUserAnswer());
			int groupId = subAnswer.getGroupId();
			if (scoreUtil.answerCompare(subAnswer.getAnswerType(), cAnswer, uAnswer, subAnswer.isCorrectAnswerUsed())) {
				subAnswer.setIsRight(true);
				score += subAnswer.getScore();
				scorePart += subAnswer.getScore().toString() + "；"; 				
				
				subAnswer.setCorrectAnswerUsed(true);
			} else {
				subAnswer.setIsRight(false);
				if (groupScore(subItem, groupId, uAnswer)) {
					subAnswer.setIsRight(true);
					score += subAnswer.getScore();
					scorePart += subAnswer.getScore().toString() + "；";					
					continue;
				}
				error = true;
				scorePart += "0" + "；";
			}
		}
		rightFlag = !error;
		scorePart = scorePart.trim().replaceFirst("；$", "").trim();
		
		subItem.getExamProperty().setUserScore(score);
		subItem.getExamProperty().setIsRight(rightFlag);
	}
	
	/**
	 * 分组性答案从分组中继续查找并判分，若对则把判对的SubAnswer的groupId置为0
	 * @param item
	 * @param groupId
	 * @param userAnswer
	 * @return 在分组中有正确答案返回true，否则返回false
	 */
	private boolean groupScore(SubItem subItem, int groupId, String uAnswer) {
		if (groupId<1) return false;
		
		boolean right = false;
		List<SubAnswer> subAnswers = subItem.getExamProperty().getSubAnswers();
		for (SubAnswer subAnswer: subAnswers) {
			if (groupId==subAnswer.getGroupId()) {
				String cAnswer = scoreUtil.answerPrepare(subAnswer.getAnswerType(), subAnswer.getCorrectAnswer());
				if (scoreUtil.answerCompare(subAnswer.getAnswerType(), cAnswer, uAnswer, subAnswer.isCorrectAnswerUsed())) {					
					subAnswer.setCorrectAnswerUsed(true);
					right = true;
					break;
				}
			}
		}
		return right;
	}	
	
	private void scoreItem(Item item) {
		boolean error = false;
		for (SubItem subItem: item.getSubItems()) {
			if (subItem.getExamProperty().getIsRight()) {
			} else {
				error = true;
			}
			score += subItem.getExamProperty().getUserScore();
		}
		rightFlag = !error;
	
		item.getExamProperty().setUserScore(score);
		item.getExamProperty().setIsRight(rightFlag);
	}
	
	public ScoreUtil getScoreUtil() {
		return scoreUtil;
	}

	public void setScoreUtil(ScoreUtil scoreUtil) {
		this.scoreUtil = scoreUtil;
	}
}
