package com.ambow.trainingengine.exam.score;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;

/*
 * IUserScore.java
 * 
 * Created on 2008-12-9 下午03:00:31
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * 主观题用户自评分接口
 * 
 * Changes 
 * -------
 * $log$
 */

public interface IUserScore {
	
	/**
	 * @param page 需要处理的答题页面
	 * @param viewControl 用户的View视图
	 * @param request 需要装载到UI的Http接口
	 * @return 不同的自评分定义的ResultName
	 */
	
	public String userScore(Page page, ViewControl viewControl, HttpServletRequest request);
	
}
