package com.ambow.trainingengine.exam.web.action;

import java.util.Map;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.score.IUserScore;
import com.ambow.trainingengine.exam.score.ScoreFactory;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.util.SessionDict;

/*
 * SubjectiveIyScoreAction.java
 * 
 * Created on Jul 2, 2008 10:28:02 AM
 * 给作文完成系统打分
 * 处理主观题自我判分的Action 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 王伟  2008-12-09 翻译句子短文翻译重构到ExamAnswerAction中判分
 * 王伟  2008-12-09 主观题的判分有IUserScore定义
 */

@SuppressWarnings("serial")
public class SubjectiveIyScoreAction extends BaseAction {
	
	String writingScore;
	
	private ScoreFactory scoreFactory;
	
	public String execute(){
		ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
		IUserScore userScore = scoreFactory.getUserScoreImpl(viewControl);
		if (userScore==null) return SUCCESS;
		else return userScore.userScore(null, viewControl, getHttpServletRequest());
	}
	
	/*
	 * 解析打分的结果
	 */
	public String parseResult(){
		//System.out.println("from:::"+writingScore);
		String[] itemScores=writingScore.split(";");
		ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
		Map<String,Float> writingScoreMap=viewControl.getSubjectScoreMap();
		for(String itemScore:itemScores){
			String[] itemValue=itemScore.split(":");
			writingScoreMap.put(itemValue[0], Float.valueOf(itemValue[1]));
		}
		
		return SUCCESS;
	}

	@Override
	public String getAuthStr() {
		return null;
	}
	
	public String getWritingScore() {
		return writingScore;
	}

	public void setWritingScore(String writingScore) {
		this.writingScore = writingScore;
	}
	
	public ScoreFactory getScoreFactory() {
		return scoreFactory;
	}

	public void setScoreFactory(ScoreFactory scoreFactory) {
		this.scoreFactory = scoreFactory;
	}

}
