package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ScoreSentence.java
 * 
 * Created on 2008-12-8 下午06:14:48
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

public class ScoreSentence extends ScoreBase implements IScore {
	
	ScoreUtil scoreUtil;

	public void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore) {
		beforeItemScore(item, subItem, cuAnswerStatus, subjectScore);
		
		score(item, subItem, cuAnswerStatus);
		
		rightFlag = (score>=standScore*0.6);
				
		afterItemScore(cuAnswerStatus);
	}

	private void score(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus) {
		if(subItem!=null) {						
			//ky 44A 短文翻译			
			score=scoreUtil.scoreSubject(subItem.getKeyAnswerWords(), currentAnswer, standScore);				
		}else {
			//ky 34C 句子翻译			
			score=scoreUtil.scoreSubject(item.getScoringKeywords(), currentAnswer, standScore);			
		}
	}

	public ScoreUtil getScoreUtil() {
		return scoreUtil;
	}

	public void setScoreUtil(ScoreUtil scoreUtil) {
		this.scoreUtil = scoreUtil;
	}
}
