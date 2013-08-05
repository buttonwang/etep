package com.ambow.trainingengine.exam.web.data;

import java.util.Set;

import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;

/*
 * ExamBeginProperty.java
 * 答卷预告属性
 * Created on 2009-3-19 下午06:12:39
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

public class ExamBeginProperty {
		
	private Set<EvaluatingAnswerStatus> evaluatingAnswerStatus = null; //用户评测知识点预告
	private int evaluatingAnswerSize;

	public Set<EvaluatingAnswerStatus> getEvaluatingAnswerStatus() {
		return evaluatingAnswerStatus;
	}

	public void setEvaluatingAnswerStatus(
			Set<EvaluatingAnswerStatus> evaluatingAnswerStatus) {
		this.evaluatingAnswerStatus = evaluatingAnswerStatus;
	}
	
	public int getEvaluatingAnswerSize() {
		evaluatingAnswerSize = evaluatingAnswerStatus.size();
		return evaluatingAnswerSize;
	}

}
