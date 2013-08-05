package com.ambow.trainingengine.exam.stat;

import java.util.Map;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.score.IScore;
import com.ambow.trainingengine.exam.score.ScoreFactory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * StatBase.java
 * 
 * Created on 2009-2-5 下午07:29:48
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

public class StatBase {

	private ScoreFactory scoreFactory;
	
	/*
	 * 重构后的打分方法
	 */
	public void itemScore(Item item,SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore){
		IScore score = scoreFactory.getScoreImpl(ExamUtil.getResultCode(item.getItemType().getCode()));
		score.ItemScore(item, subItem, cuAnswerStatus, subjectScore);
	}
	
	/*
	 * 生成试题答题情况
	 */
	public void putAnswersMap(ViewControl viewControl, Map<CurrentAnswersStatus, Integer> currentMap, 
			Item item, SubItem subItem) {
		String key=null;
		String answer=null;
		Integer mark=null;
		CurrentAnswersStatus currentAnswersStatus=null;
		
		key = ExamUtil.getMapKey(item, subItem);
		
		currentAnswersStatus = viewControl.getPreAnswersStatus().get(key);
		if(currentAnswersStatus==null) {
			currentAnswersStatus = new CurrentAnswersStatus();
			currentAnswersStatus.setAsfNodeInstance(viewControl.getExamNodeIns());
			
			//若当前答题数据中没有此试题则加入
			viewControl.getPreAnswersStatus().put(key, currentAnswersStatus);
		}
		
		answer = (String)viewControl.getAnswerMap().get(key);
		currentAnswersStatus.setAnswer(answer);
		
		mark = (Integer)viewControl.getMarkMap().get(key);
		if (mark==null) mark = 0;
		currentAnswersStatus.setIsUnsureMarking(mark==1);
		
		currentAnswersStatus.setItem(item);
		currentAnswersStatus.setSubItem(subItem);
		currentAnswersStatus.setDone(item.getExamProperty().getIsDone());
		currentAnswersStatus.setAnswerOptionOrder(item.getExamProperty().getAnswerOptionOrder());
		
		currentMap.put(currentAnswersStatus, 1);
	}
	
	public ScoreFactory getScoreFactory() {
		return scoreFactory;
	}

	public void setScoreFactory(ScoreFactory scoreFactory) {
		this.scoreFactory = scoreFactory;
	}

	
}
