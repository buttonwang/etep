package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * scoreSimple.java
 * 
 * Created on 2008-12-8 下午05:53:56
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 简单判分类：比较用户答案与标准答案是否相同 
 * 
 * Changes 
 * -------
 * $log$
 */


public class ScoreSimple extends ScoreBase implements IScore {
	
	public void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		beforeItemScore(item, subItem, cuAnswerStatus, subjectScore);
		
		rightFlag = standAnswer.trim().equalsIgnoreCase(currentAnswer.trim());
		score = rightFlag?standScore:0f;
		
		afterItemScore(cuAnswerStatus);
	}
}