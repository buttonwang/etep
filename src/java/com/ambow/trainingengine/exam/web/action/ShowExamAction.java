package com.ambow.trainingengine.exam.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.display.DisplayFactory;
import com.ambow.trainingengine.exam.display.IDisplay;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.util.SessionDict;

/*
 * OverViewExamAction.java
 * 
 * Created on Jul 2, 2008 10:18:17 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 2008-11 王伟 重构IDisplay
 */
public class ShowExamAction extends BaseAction {

	private static final long serialVersionUID = 4435072692572274991L;

	Integer pageNum;
	
	private DisplayFactory displayFactory;
	
	@Override
	public String execute(){
		//正式进入考试的标记
		this.setSessionObj("startExamTag", true);
		
		ViewControlProxy viewControl=(ViewControlProxy)this.getHttpSessionObj(SessionDict.ViewControl);
		
		Page page = getShowPage(viewControl);
		
		//计算总题数
		viewControl.statPages();
		
		//处理页面显示的逻辑
		if (page==null) {
			viewControl.setFilter(false);
			return "overView";
		}
		
		String resultCode = ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode());
		IDisplay display = displayFactory.getDisplayImpl(resultCode);
		display.doDisplay(page, viewControl, this.getHttpServletRequest());
		viewControl.setEntranceExam(true);
		return resultCode;
	}
	
	private Page getShowPage(ViewControl viewControl) {
		List<Page> pageList = null;
		Page page = null;
		if(viewControl.isFilter()){
			pageList=viewControl.getFilterPageList();
		}else{
			pageList=viewControl.getPageList();
		}
		
		viewControl.setShowNextButton(false);
		viewControl.setShowPreButton(false);
		int prePageNum=pageNum-1;
		int nextPageNum=pageNum+1;
		int prePageSize=0;
		int nextPageSize=0;
		
		if (pageList.size()>0) {
			if ((prePageNum>-1) && (prePageNum < pageList.size())) {
				viewControl.setShowPreButton(true);
				viewControl.setPrePageNum(prePageNum);
				prePageSize =  pageList.get(prePageNum).getSize();
			}
			if ((nextPageNum>-1) && (nextPageNum < pageList.size())) {
				viewControl.setShowNextButton(true);
				viewControl.setNextPageNum(nextPageNum);
				nextPageSize = pageList.get(nextPageNum).getSize();
			}		
			if(pageNum<pageList.size())
				page=pageList.get(pageNum);
		}
		this.setSessionObj("currentPage", page);
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		this.getHttpServletRequest().setAttribute("currentPageNum", pageNum.toString());
		this.getHttpServletRequest().setAttribute("prePageSize", prePageSize);
		this.getHttpServletRequest().setAttribute("nextPageSize", nextPageSize);
		
		return page;
	}

	@Override
	public String getAuthStr() {
		return null;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public DisplayFactory getDisplayFactory() {
		return displayFactory;
	}

	public void setDisplayFactory(DisplayFactory displayFactory) {
		this.displayFactory = displayFactory;
	}

}
