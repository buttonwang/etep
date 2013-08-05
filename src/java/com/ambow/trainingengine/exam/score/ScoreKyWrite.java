package com.ambow.trainingengine.exam.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.util.ExamkyUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ScoreKyWrite.java
 * 
 * Created on 2008-12-9 下午03:14:02
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 考研英语写作的用户评分页面的逻辑
 * 
 * Changes 
 * -------
 * $log$
 */

public class ScoreKyWrite implements IUserScore {
	
	private ScoreFactory scoreFactory; 
	
	ScoreUtil scoreUtil;

	public String userScore(Page page, ViewControl viewControl, HttpServletRequest request) {
		List<Page> pageList=viewControl.getPageList();
		List<Item> writingItems=new ArrayList<Item>();
		List<Float> scoreList=new ArrayList<Float>();
		List<String> userAnswers=new ArrayList<String>();

		for(Page vpage: pageList){
			if (scoreFactory.hasUserScore(vpage.getItemType().getCode()))
			writingItems.addAll(vpage.getItems());
		}
		
		String mapKey=null;
		Map<String, String> answerMap=viewControl.getAnswerMap();
		String userAnswer=null;
		Float score=null;
		String keyWordStr="";
		int requireSize=30;//默认为30
		Float itemValue=0f;
		
		for(Item item:writingItems){
			if(item.getSubItems().size()>0){
				itemValue=item.getScore()/item.getSubItems().size();
				for(SubItem subItem:item.getSubItems()){
					mapKey=ExamUtil.getMapKey(item, subItem);
					userAnswer=answerMap.get(mapKey);
					userAnswers.add(userAnswer);
					keyWordStr=subItem.getKeyAnswerWords();
					score=scoreUtil.compositionScore(keyWordStr, requireSize, itemValue, userAnswer);
					scoreList.add(score);
					//同时存入Map
					//subjectScoreMap.put(mapKey, score);
				}
				
			}else{
				mapKey=ExamUtil.getMapKey(item, null);
				userAnswer=answerMap.get(mapKey);
				userAnswers.add(userAnswer);
				keyWordStr=item.getScoringKeywords();
				itemValue=item.getScore();
				score=scoreUtil.compositionScore(keyWordStr, requireSize, itemValue, userAnswer);
				scoreList.add(score);
				//subjectScoreMap.put(mapKey, score);
			}
		}
		request.setAttribute("itemSize", writingItems.size());
		request.setAttribute("writingItems", writingItems);
		String[] answers=new String[userAnswers.size()];
		String[] answers2=ExamkyUtil.replaceNewLine(userAnswers.toArray(answers));//处理换行的问题
		request.setAttribute("userAnswers", answers2);
		request.setAttribute("systemScores",scoreList);
		return "writingScore";

	}

	public ScoreFactory getScoreFactory() {
		return scoreFactory;
	}

	public void setScoreFactory(ScoreFactory scoreFactory) {
		this.scoreFactory = scoreFactory;
	}
		
	public ScoreUtil getScoreUtil() {
		return scoreUtil;
	}

	public void setScoreUtil(ScoreUtil scoreUtil) {
		this.scoreUtil = scoreUtil;
	}
}
