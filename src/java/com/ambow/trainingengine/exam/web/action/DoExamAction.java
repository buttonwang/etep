package com.ambow.trainingengine.exam.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.trainingengine.exam.service.ExamAnswerService;
import com.ambow.trainingengine.exam.service.ExamFlowService;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.util.SessionDict;

/* 
 * RecordExamAction.java <br/>
 * 
 * 该Action负责记录考试过程中的用户数据
 * 并负责处理暂停、过滤题等等变态要求
 * Created on Jul 15, 2008,9:11:46 PM <br/>
 *
 * Copyright(C) 2008, by Ambow Research&Development Branch. <br/>
 *
 * Original Author: Li Xin <br/>
 * Contributor(s): 参与者的名称，参与者名称2， <br/>
 *
 * Changes  <br/> 
 * -------
 * $log$ <br/>
 */
public class DoExamAction extends BaseAction {

	private static final long serialVersionUID = 5101276714811641747L;

	Logger log = Logger.getLogger(this.getClass());

	private int currentPageNum;// 当前提交数据的页码

	private int nextPageNum;// 将要跳转到的页码

	private String userAnswers;

	private String nextAct;// 有如下

	private int filterType;

	private String time;// 剩余的时间，秒数..

	private ExamAnswerService examAnswerService;

	private ExamItemService examItemService;

	private ExamFlowService examFlowService;

	private int testPass;
	
	private long nodeInstanceId;

	public String execute() throws IOException {
		log.info("The User Answer:" + userAnswers);
		ViewControlProxy viewControl = (ViewControlProxy) this.getHttpSessionObj(SessionDict.ViewControl);

		if (viewControl.getShowModel() == 1) {
			parseCurrentPage(viewControl);
		}
		
		if ("pause".equalsIgnoreCase(nextAct)) {
			if (viewControl.isExtPractice()) pauseExtPracticeAct(viewControl);
			else pauseAct(viewControl);
		}
		if ("filter".equalsIgnoreCase(nextAct)) {
			filterAct(viewControl);
		}
		if ("overView".equalsIgnoreCase(nextAct)) {
			overViewAct(viewControl);
		}
		if ("submit".equalsIgnoreCase(nextAct)) {
			submitAct(viewControl);
		}

		return nextAct;
	}

	private void submitAct(ViewControlProxy viewControl) {
		// 交卷后直接先跳转到主观题对错判断页面
		// 如果设置为testPass==1或2，测试流程。。
		if (testPass == 1 || testPass == 2) {
			viewControl.setTestPass(testPass);
		}
		nextAct = "subjectScore";
	}

	private void parseCurrentPage(ViewControlProxy viewControl) {
		if (time != null && time.trim().length() > 0) {
			viewControl.setActualTime(Integer.parseInt(time));
		}
		
		List<Page> pages = null;
		if (viewControl.isFilter())	pages = viewControl.getFilterPageList();
		else pages = viewControl.getPageList();
		
		viewControl.setCurrentPageNum(currentPageNum);		
		Page currentPage = pages.get(currentPageNum);
		
		if (userAnswers != null)
		viewControl.parseAnswer(userAnswers, currentPage);
	}

	private void overViewAct(ViewControlProxy viewControl) {
		// 当overView时检测过滤模式
		if (viewControl.isFilter() ) {			
			// 进入overview后filter默认失效
			viewControl.setFilter(false);
			viewControl.setFilterType(1);
			if (viewControl.getShowModel() == 1) {
				//viewControl.setPages();
			}
		}
		viewControl.statPages();	// 统计
	}

	private void filterAct(ViewControlProxy viewControl) {
		int oldFilterType = viewControl.getFilterType();

		if (filterType != 1) {
			viewControl.setFilter(true);
			List<Page> filterPageList = examItemService.getFilteredPages(viewControl.getPageList(), filterType, viewControl);
			viewControl.setFilterPageList(filterPageList);
			viewControl.setFilterType(filterType);
			viewControl.setFilterPages();
		}
		if (filterType == 1) {
			// 全部题出来
			if (oldFilterType != 1) {
				viewControl.cancelFilterPages();
				if (viewControl.getShowModel() == 1) {
					//viewControl.setPages();
					viewControl.statPages();
				}
			}
		}
	}

	private void pauseAct(ViewControlProxy viewControl) {
		examAnswerService.savePauseInfo(viewControl);
		examAnswerService.savePauseAnswers(viewControl.getPageList(), 
			viewControl.getAnswerMap(), viewControl.getMarkMap(), viewControl.getProcessInstance());
					
		// 设置流程为暂停, 若流程结束则不改变流程状态
		if (viewControl.getProcessInstance().getProcessStatus()!= ProcessStatus.STOPED)
			examFlowService.updateProcessStatus(viewControl.getProcessInstance(), ProcessStatus.SUSPEND_PAUSE);
		viewControl.setPause(true);
	}

	private void pauseExtPracticeAct(ViewControlProxy viewControl) {
		examAnswerService.saveExtPauseInfo(viewControl);
		examAnswerService.saveExtPauseAnswers(viewControl.getPageList(), 
			viewControl.getAnswerMap(), viewControl.getMarkMap(), viewControl.getExamNodeIns());
		
		this.nextAct ="extpause";
		this.nodeInstanceId = viewControl.getExamNodeIns().getNode().getId();
	}

	/*
	 * 交卷分为 二步提交。由此保证几个统计数据的前后一致性 利用JQuery先期取得数据并统计。。
	 */
	public void doParseAnswer() {
		//System.out.println("answerStr from ajax:" + userAnswers);
		ViewControlProxy viewControl = (ViewControlProxy) this.getSessionObj(SessionDict.ViewControl);
		if (testPass == 1 || testPass == 2) {
			viewControl.setTestPass(testPass);
		}
		if (time != null && time.trim().length() > 0) {
			viewControl.setActualTime(Integer.parseInt(time));
		}
		List<Page> pages = null;
		if (viewControl.isFilter()) {
			pages = viewControl.getFilterPageList();
		} else {
			pages = viewControl.getPageList();
		}

		Page currentPage = pages.get(currentPageNum);
		if (viewControl.getShowModel() == 1 && userAnswers != null && userAnswers.trim().length() > 0) {
			viewControl.parseAnswer(userAnswers, currentPage);
		}
		viewControl.statPages();

		String str = "data2={DoneItemNum:" + viewControl.getDoneItemNum()
				+ ",MarkItemNum:" + viewControl.getMarkItemNum()
				+ ",UndoItemNum:" + viewControl.getUndoItemNum() + "}";
		//System.out.println("The return str:" + str);
		try {
			this.getHttpServletResponse().getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExamAnswerService getExamAnswerService() {
		return examAnswerService;
	}

	public void setExamAnswerService(ExamAnswerService examAnswerService) {
		this.examAnswerService = examAnswerService;
	}

	public int getCurrentPageNum() {
		return currentPageNum;
	}

	public void setCurrentPageNum(int currentPageNum) {
		this.currentPageNum = currentPageNum;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public String getUserAnswers() {
		return userAnswers;
	}

	public void setUserAnswers(String userAnswers) {
		this.userAnswers = userAnswers;
	}

	public String getNextAct() {
		return nextAct;
	}

	public void setNextAct(String nextAct) {
		this.nextAct = nextAct;
	}

	public int getFilterType() {
		return filterType;
	}

	public void setFilterType(int filterType) {
		this.filterType = filterType;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ExamItemService getExamItemService() {
		return examItemService;
	}

	public void setExamItemService(ExamItemService examItemService) {
		this.examItemService = examItemService;
	}

	public ExamFlowService getExamFlowService() {
		return examFlowService;
	}

	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}

	public int getTestPass() {
		return testPass;
	}

	public void setTestPass(int testPass) {
		this.testPass = testPass;
	}

	public long getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}

}
