package com.ambow.trainingengine.point.web.action;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ambow.core.util.DateCompute;
import com.ambow.core.util.DateUtil;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.point.service.MyPointService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ShowStudyGuideAction.java
 * 
 * Created on 2009-8-3 下午05:44:27
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

public class MyPointAction extends BaseAction {

	private static final long serialVersionUID = 6465418667199078964L;
	private MyPointService myPointService;
	private int pageSize=10;
	private int pageNo=1;
	private int start=0;

	@SuppressWarnings("unchecked")
	public String execute() {
		if (pageNo < 1) pageNo = 1;
		UserDataVO userVo=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long processInstanceId=userVo.getProcessInstanceId();
		long processDefinitionId=userVo.getProcessDefinitionId();
		
		List pointList=myPointService.getMembershipPoint(userVo.getUserID(),processInstanceId);
		Map orderMap=myPointService.getPointOrder(processInstanceId, processDefinitionId);
		
		this.setRequestAttribute("pointList", pointList);
		this.setRequestAttribute("orderMap", orderMap);
		setPointStatDate();
		
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String historyPoints() throws IOException{
		UserDataVO userVo=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long processInstanceId=userVo.getProcessInstanceId();
		pageNo = start/pageSize + 1;
		List list 	 = myPointService.getPagePointHistory( processInstanceId);
		List hisList = myPointService.pagination(pageNo, pageSize, list);
		
		this.setRequestAttribute("totalCount", list.size());
		this.setRequestAttribute("historyPoints", hisList);
		return "historyPoints";
	}

	@SuppressWarnings("static-access")
	private void setPointStatDate() {
		//DateUtil dateUtil=new DateUtil();
		DateCompute dateCompute=new DateCompute();
		
		String beforeWeekfirst = dateCompute.getPreviousMonday();
		String beforeWeeklast = DateUtil.getBeforeDay(dateCompute.getCurrentMonday());
		
		String beforeMonthfist = DateUtil.format(DateUtil.getFirstDayOfMonth(dateCompute.getBeforeMonthDay()));
		String beforeMonthlast = DateUtil.format(DateUtil.getLastDayOfMonth(dateCompute.getBeforeMonthDay()));
		
		this.setRequestAttribute("beforeWeekfirst", beforeWeekfirst);
		this.setRequestAttribute("beforeWeeklast", beforeWeeklast);
		this.setRequestAttribute("beforeMonthfist", beforeMonthfist);
		this.setRequestAttribute("beforeMonthlast", beforeMonthlast);
	}

	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public MyPointService getMyPointService() {
		return myPointService;
	}

	public void setMyPointService(MyPointService myPointService) {
		this.myPointService = myPointService;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
