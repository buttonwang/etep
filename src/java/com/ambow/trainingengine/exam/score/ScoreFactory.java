package com.ambow.trainingengine.exam.score;

import java.util.HashMap;
import java.util.Map;

import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;


/*
 * ScoreFactory.java
 * 
 * Created on 2008-12-9 上午09:20:43
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 打分实现类的类工厂
 * Changes 
 * -------
 * $log$
 */

public class ScoreFactory {

	private Map<String, IScore> scoreClass = new HashMap<String, IScore>();

	private Map<String, IUserScore> userScoreClass = new HashMap<String, IUserScore>();
	
	public IScore getScoreImpl(String itemType) {
		IScore score = scoreClass.get(itemType);
		if (score==null) score = new ScoreSimple();
		return score;
	}
		
	public IUserScore getUserScoreImpl(ViewControl viewControl) {
		IUserScore userScore = null;
				
		for(Page page: viewControl.getPageList()){
			userScore = userScoreClass.get(page.getItemType().getCode());
			if (userScore!=null) break;			
		}
		
		return userScore;
	}
	
	public boolean hasUserScore(String itemType) {
		return userScoreClass.containsKey(itemType);
	}
	
	public Map<String, IScore> getScoreClass() {
		return scoreClass;
	}

	public void setScoreClass(Map<String, IScore> scoreClass) {
		this.scoreClass = scoreClass;
	}
	
	public Map<String, IUserScore> getUserScoreClass() {
		return userScoreClass;
	}

	public void setUserScoreClass(Map<String, IUserScore> userScoreClass) {
		this.userScoreClass = userScoreClass;
	}

}
