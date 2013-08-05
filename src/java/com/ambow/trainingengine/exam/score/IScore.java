package com.ambow.trainingengine.exam.score;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * IScore.java
 * 
 * Created on 2008-12-8 下午03:39:51
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 定义试题打分的接口
 * 试题打分按照题型来实现对应的接口
 * Changes 
 * -------
 * $log$
 */

public interface IScore {
	
	/**
	 * @param item 需要打分的试题
	 * @param subItem 需要打分的子题
	 * @param cuAnswerStatus 当前答题状态数据
	 * @param subjectScore 主观题用户自评的分数
	 */
	void ItemScore(Item item, SubItem subItem, CurrentAnswersStatus cuAnswerStatus, Float subjectScore);
	
}
