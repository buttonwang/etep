package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ScoreUserScore.java
 * 
 * Created on 2008-12-8 下午06:15:28
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 适用于考研主观题的对错判断。
 * Changes 
 * -------
 * $log$
 */

public class ScoreUserScore extends ScoreBase implements IScore {

	public void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		beforeItemScore(item, subItem, cuAnswerStatus, subjectScore);
		
		rightFlag = (cuScore>=standScore*0.6);
		score = cuScore;
		
		afterItemScore(cuAnswerStatus);
	}

}
