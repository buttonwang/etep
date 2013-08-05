package com.ambow.trainingengine.exam.domain;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * AnswerStatus.java
 * 
 * Created on Jul 31, 2008 10:47:37 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public interface AnswerStatus {
	
	String getAnswer();
	Item getItem();
	SubItem getSubItem();
	boolean getIsCorrect();
	Float getStarGrade();
	
	

}
