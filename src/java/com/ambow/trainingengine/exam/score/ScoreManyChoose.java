package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ScoreManyChoose.java
 * 
 * Created on 2008-12-8 下午06:13:13
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 *
 *  一对一选择多选题判分类：比较用户答案与标准答案是否相同
 *  判分规则: 全对给满分，有错给零分，部分对给一半分。 
 *  
 * Changes 
 * -------
 * $log$
 */

public class ScoreManyChoose extends ScoreBase implements IScore {

	public void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		beforeItemScore(item, subItem, cuAnswerStatus, subjectScore);
		score(item);
		afterItemScore(cuAnswerStatus);
	}
	
	private void score(Item item) {
		boolean error = false;
		String cAnswer = currentAnswer.replaceAll("[^A-Z]", "");
		String sAnswer = standAnswer.replaceAll("[^A-Z]", "");
		char[] c = cAnswer.toCharArray();
		int i;
		for (i = 0; i<c.length; i++) {
			if (sAnswer.indexOf(c[i])==-1) {
				error = true;
				break;
			}
		}
		
		if (cAnswer.trim().equals("")) error = true;
		
		if (error) {
			rightFlag = false; score = 0f;
		} else if (i<sAnswer.length()) {
			rightFlag = false; score = standScore/2;
		} else {
			rightFlag = true;  score = standScore;
		}
		
		item.getExamProperty().setUserScore(score);
		item.getExamProperty().setIsRight(rightFlag);
	}

}
