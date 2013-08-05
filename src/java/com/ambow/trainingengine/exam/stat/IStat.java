package com.ambow.trainingengine.exam.stat;

import com.ambow.trainingengine.exam.web.data.ViewControl;

/*
 * IStat.java
 * 
 * Created on 2009-2-5 下午06:06:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 对试题、试卷、流程、评测数据进行统计。
 * Changes 
 * -------
 * $log$
 */

public interface IStat {
	
	/*
	 * 保存试题信息
	 */
	public void saveAnswers(ViewControl viewControl);
	
	/*
	 * 保存试题校验信息
	 */
	public void saveReviseAnswers(ViewControl viewControl);
	
	/*
	 * 根据考试结果保存知识点 
	 */
	public void statKPoint(ViewControl viewControl);
	
}
