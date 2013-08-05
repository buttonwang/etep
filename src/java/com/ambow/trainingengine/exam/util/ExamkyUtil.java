package com.ambow.trainingengine.exam.util;

import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;

/*
 * ExamKyUtil.java
 * 
 * Created on 2008-11-13 下午06:57:54
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 考研考试的工具类
 * Changes 
 * -------
 * $log$
 */
public class ExamkyUtil {

	/*
	 * 处理换行的问题
	 */
	public static void handWritingView(Page page,ViewControl viewControl){
		if(viewControl.getShowModel()==1)return;
		String[] answers=page.getUserAnswer();
		String[] answers2=replaceNewLine(answers);
		page.setUserAnswer(answers2);
	}
	
	public static String[] replaceNewLine(String[] answers) {
		for (int i = 0; i < answers.length; i++) {
			if (answers[i] == null)
				continue;
			if (answers[i].indexOf("\r\n") > -1) {
				String answer2 = answers[i].replaceAll("\r\n", "<br>");
				answers[i] = answer2;
			}

		}
		return answers;
	}
	
	/*
	 * 利用正则表达式来搜寻替换第一个符合要求的地方
	 */
	public static String replaceOneExpress(String source, String regex,
			String replacement) {
		String afterReplace = null;
		afterReplace = source.replaceFirst(regex, replacement);
		return afterReplace;
	}
}
