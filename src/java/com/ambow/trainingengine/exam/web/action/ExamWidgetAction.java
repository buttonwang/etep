package com.ambow.trainingengine.exam.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.exam.display.DisplayFactory;
import com.ambow.trainingengine.exam.display.IDisplay;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.service.ExamWidgetService;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.itembank.domain.ItemReviseAnswers;
import com.ambow.trainingengine.itembank.service.ItemReviseService;
import com.ambow.trainingengine.itembank.web.data.ItemPageIDsVO;
import com.ambow.trainingengine.itembank.web.data.ItemReviseAllVO;
import com.ambow.trainingengine.itembank.web.data.ItemReviseVO;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.AdminManageService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.UtilAndTool_L;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * WidgetAction.java
 * 
 * Created on 2009-5-20 下午04:41:42
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 2009-5-20 试题增加Widget模式，显示试题相关信息
 */

public class ExamWidgetAction extends BaseAction {

	private static final long serialVersionUID = -7221325097596706666L;
	
	Integer mode=1;
	Integer width=0;
	Integer itemId = 0;
	String  itemIds;
	Integer sysUserId = 0;
	long hisId = 0;
	private String fromPage;
	
	private ExamWidgetService examWidgetService;
	private AdminManageService adminManageService;
	private ItemReviseService itemReviseService;
	private DisplayFactory displayFactory;
	private HibernateGenericDao genService;
	
	private int showCompareAnswer = 0;
	
	@Override
	public String execute(){
		return genWidget(1);
	}
	
	/**
	 * 查看前台用户作答情况的方法
	 * 
	 */
	public String historyAnswerWidget() {
		showCompareAnswer = 1;
		genWidget(2);
		return "reviseSimple";
	}
	
	/**
	 * 学习指导的widget模式
	 * @return
	 */
	public String StudyGuideWidget() {
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		ViewControlProxy viewControl =  new ViewControlProxy(
				userDataVO.getProcessPolicy().getIsShowAnswer(),
				userDataVO.getProcessPolicy().getProjectVersion(),
				userDataVO.getProcessInstanceId());

		viewControl.setShowModel(4);	//4是Widget
		
	    List<Item> items = examWidgetService.getItems(itemIds);
	   
	    viewControl.generateWidget2(items, 0);
	    
	    List<Page> pages = viewControl.getPageList();
	    for(Page page: pages) {
	    	displayFactory.getDisplayImpl(page.getTypeAlias()).
	    		doDisplay(page, viewControl, this.getHttpServletRequest());
	    }
		
		this.getHttpServletRequest().setAttribute("pages", pages);
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		
		return "StudyGuideWidget";
	}
	
	/**
	 * 试题审校的简单Widget模式
	 * @return
	 */
	public String itemReviseSimpleWidget() {
		genReviseWidget();
		return "reviseSimple";
	}

	/**
	 * 试题审校的widget模式
	 * @return
	 */
	public String itemReviseWidget() {
		genReviseWidget();
		
		//将itemID的信息保存到session中的ItemPageIDsVO中去，好取出上一题及下一题 的ID信息
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		if(ipidsVO!=null) ipidsVO.setId(itemId+"");
		this.setRequestAttribute("itemId", itemId);
		this.setSessionObj("ipidsVO", ipidsVO);
		
		return "revise";
	}

	private String genWidget(int type) {
		ViewControlProxy viewControl = null;
		Item item = examWidgetService.getItem(itemId);
		item.setItemNum(1);
		
		if (type ==1) {
			UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
			viewControl =  new ViewControlProxy(
					userDataVO.getProcessPolicy().getIsShowAnswer(),
					userDataVO.getProcessPolicy().getProjectVersion(),
					userDataVO.getProcessInstanceId());
		} else if (type == 2) {
			String subjectCode = item.getSubject().getCode();
			String projectVersion = "";
			if (subjectCode.endsWith("M")||subjectCode.endsWith("P")||subjectCode.endsWith("C")) projectVersion = "mpc";
			else if (subjectCode.endsWith("E")) projectVersion = "ky";
		
			viewControl = new ViewControlProxy(0, projectVersion, 0);
		}
		
		viewControl.setShowModel(4);	//4是Widget
	    HistoryAnswerStatus historyAnswerStatus = null;
	    
	    historyAnswerStatus = examWidgetService.getHistoryAnswerStatus(hisId);
	    List<HistoryAnswerStatus> historyAnswerStatusList = new ArrayList<HistoryAnswerStatus>(0);
	    if (historyAnswerStatus!=null) {
	    	historyAnswerStatusList = examWidgetService.getHistoryAnswersStatus(
	    			historyAnswerStatus.getHistoryTestStatus().getId(), itemId);
	    }
	    viewControl.generateWidget(item, historyAnswerStatusList, mode);
	    
	    setViewProperty(viewControl, historyAnswerStatus);
	    viewControl.setCompareAnswerPolicy(showCompareAnswer);
	    
	    Page page = viewControl.getPageList().get(0);
		String resultCode = ExamUtil.getResultCode(viewControl.getProjectVersion(), item.getItemType().getCode());
		IDisplay display = displayFactory.getDisplayImpl(resultCode);
		display.doDisplay(page, viewControl, this.getHttpServletRequest());
		
		this.getHttpServletRequest().setAttribute("currentPage", page);
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		this.getHttpServletRequest().setAttribute("widgetPage", ExamUtil.getwidgetPage(resultCode));
		this.getHttpServletRequest().setAttribute("width", width);
		
		return resultCode;
	}
		
	private void genReviseWidget() {
		Item item = examWidgetService.getItem(itemId);
		item.setItemNum(1);
		
		String subjectCode = item.getSubject().getCode();
		String projectVersion = "";
		if (subjectCode.endsWith("M")||subjectCode.endsWith("P")||subjectCode.endsWith("C")) projectVersion = "mpc";
		else if (subjectCode.endsWith("E")) projectVersion = "ky";
		
		ViewControlProxy viewControl = new ViewControlProxy(0, projectVersion, 0);
		setReviseProperty(viewControl, item);
		
		Page page = viewControl.getPageList().get(0);
	    page.setSize(1);
	    page.setItemType(item.getItemType());
	    
		String resultCode = ExamUtil.getResultCode(viewControl.getProjectVersion(), item.getItemType().getCode());
		IDisplay display = displayFactory.getDisplayImpl(resultCode);
		display.doDisplay(page, viewControl, this.getHttpServletRequest());
		
		this.getHttpServletRequest().setAttribute("currentPage", page);
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		this.getHttpServletRequest().setAttribute("widgetPage", ExamUtil.getwidgetPage(resultCode));
		setSessionObj(SessionDict.ViewControl, viewControl);
	}

	/******************************************************
	 * @USE: 跳转到审校留言回复页面
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 跳转到审校留言回复页面，这里可以查看到所有兼职老师针对此题目的留言信息，可以进行回复
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.06
	 * 
	 */
	public String itemReviseReplyWidget() {
		genReviseWidget();
		
		/******************** AUTHOR: L.赵亚 ***********START**************************/
		//将itemID的信息保存到session中的ItemPageIDsVO中去，好取出上一题及下一题 的ID信息
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		if(ipidsVO!=null) ipidsVO.setId(itemId+"");
		this.setSessionObj("ipidsVO", ipidsVO);
		
		this.setRequestAttribute("pageInclude", "reply");
		//标志是试题审校页面的功能调用，因为页面要显示试题的审校状态信息，如果是审校页面调用，则要显示
		//兼职老师的题目审校状态；如果是试题纠错回复页面的调用，则显示试题本身真正的审校状态
		/******************** AUTHOR: L.赵亚 ***********START**************************/
		
		Item item = examWidgetService.getItem(itemId);
		List<ItemRevise> irList = itemReviseService.getAllItemRevises(itemId);
		ItemReviseVO irvo = ItemReviseAllVO.getIRVO(item, adminManageService, irList);
		this.setRequestAttribute("irvo", irvo);
		if(UtilAndTool_L.checkNotNullOrZero(fromPage))
			this.setRequestAttribute("fromPage", fromPage);
		
		return "reviseReplay";
	}
	
	public String toReviseList(){
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		ipidsVO.setJump(true);
		this.setRequestAttribute("ipidsVO", ipidsVO);
		this.setRequestAttribute("fromPage", this.fromPage);
		
		return "reviseList";
	}
	
	/*******************************************************
	 * @USE: 跳转到纠错信息列表页面
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 纠错信息回复页面，点返回列表时，返回纠错信息列表页面
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.07.20.20
	 * 
	 */
	public String toReviseReplyList(){
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		ipidsVO.setJump(true);
		this.setRequestAttribute("ipidsVO", ipidsVO);
		if(UtilAndTool_L.checkNotNullOrZero(fromPage))
			this.setRequestAttribute("fromPage", fromPage);
		
		return "reviseReplyList";
	}
	
	private void setReviseProperty(ViewControlProxy viewControl, Item item) {
		viewControl.setShowModel(1);
		viewControl.setRevising(1);
		viewControl.setCompareAnswerPolicy(1);
	    viewControl.setAnalysisPolicy(1);
	    viewControl.setAnswerPolicy(1);
	    viewControl.setSectionName(item.getKnowledgePoints().iterator().next().getName());	    
	    if (item.getKnowledgePoints().iterator().next().getParentKnowledgePoint()!=null)
	    viewControl.setChapterName(item.getKnowledgePoints().iterator().next().getParentKnowledgePoint().getName());
	    
	    setItemRevise(viewControl, item);
	    
	    Set<ItemReviseAnswers> itemReviseAnswers = viewControl.getItemRevise().getItemReviseAnswers();
	    if ((itemReviseAnswers!=null)&&(itemReviseAnswers.size()>0)) {
	    	viewControl.setShowModel(4);
	    	viewControl.generateWidget(item, genReviseHisAnswerList(itemReviseAnswers), 3);
	    } else {
	    	viewControl.generateWidget(item, null, 3);
	    }
	}
	
	private List<HistoryAnswerStatus> genReviseHisAnswerList(Set<ItemReviseAnswers> itemReviseAnswers) {
		List<HistoryAnswerStatus> hisList = new ArrayList<HistoryAnswerStatus>(0);
		for (ItemReviseAnswers answer: itemReviseAnswers) {
			HistoryAnswerStatus historyAnswerStatus = new HistoryAnswerStatus();
			historyAnswerStatus.setItem(answer.getItem());
			historyAnswerStatus.setSubItem(answer.getSubItem());
			historyAnswerStatus.setAnswer(answer.getAnswer());
			historyAnswerStatus.setScore(answer.getScore());
			historyAnswerStatus.setItemScore2(answer.getItemScore2());
			historyAnswerStatus.setIsCorrect(answer.isCorrect());
			hisList.add(historyAnswerStatus);
		}
		return hisList;
	}
	
	private void setViewProperty(ViewControlProxy viewControl, HistoryAnswerStatus historyAnswerStatus) {
		Node node;
		if (historyAnswerStatus!=null) {
			node = historyAnswerStatus.getHistoryTestStatus().getAsfNodeInstance().getNode();
		    examWidgetService.setTrainingPolicy(viewControl, node.getId());
		} else {
			examWidgetService.setTrainingPolicyNone(viewControl);
			historyAnswerStatus = examWidgetService.getFirstHistoryAnswerStatus(itemId);
			node = historyAnswerStatus.getHistoryTestStatus().getAsfNodeInstance().getNode();
		}
		if (node.getNodeGroup() != null) {
			viewControl.setSectionName(node.getNodeGroup().getName());
			if (node.getNodeGroup().getNodeGroup() != null)
				viewControl.setChapterName(node.getNodeGroup().getNodeGroup().getName());
			else
				viewControl.setChapterName(node.getNodeGroup().getName());
		} else {
			viewControl.setSectionName(node.getName());
			viewControl.setChapterName(node.getName());
		}
	}
	
	private void setItemRevise(ViewControl viewControl, Item item) {
		SysUser sysUser = null;
		if (sysUserId==0) {
			sysUser = (SysUser) this.getHttpSessionObj(SessionDict.AdminUser);
		} else {
			sysUser = genService.get(SysUser.class, sysUserId);
		}
		String hql = "from ItemRevise i where i.item.id=? and i.reviser=?";
		ItemRevise itemRevise = (ItemRevise)genService.findObjByHql(hql, item.getId(), sysUser.getId());
		
		if (itemRevise==null) {
			itemRevise = new ItemRevise();
			itemRevise.setId(0);
			itemRevise.setReviser(sysUser.getId());
			itemRevise.setReviseTime(new Date());
			itemRevise.setReviseStatus(0);
			itemRevise.setItem(item);
		} else {
		}
		viewControl.setItemRevise(itemRevise);
	}
	
	/******************************************************
	 * @USE: 跳转到审校统计回复页面
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 跳转到审校统计回复页面，这里可以查看到某个审校人针对此题目的留言信息，可以进行回复
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.26
	 * 
	 */
	public String itemReviseWidgetByreviser() {
		genReviseWidget();
		
		
		//将itemID的信息保存到session中的ItemPageIDsVO中去，好取出上一题及下一题 的ID信息
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		if(ipidsVO!=null) ipidsVO.setId(itemId+"");
		this.setSessionObj("ipidsVO", ipidsVO);
		
		this.setRequestAttribute("pageInclude", "reply");
		//标志是试题审校页面的功能调用，因为页面要显示试题的审校状态信息，如果是审校页面调用，则要显示
		//兼职老师的题目审校状态；如果是试题纠错回复页面的调用，则显示试题本身真正的审校状态
		
		
		Item item = examWidgetService.getItem(itemId);
		List<ItemRevise> irList = itemReviseService.getAllItemRevises(itemId);
		ItemReviseVO irvo = ItemReviseAllVO.getIRVO(item, adminManageService, irList);
		this.setRequestAttribute("irvo", irvo);
//		if("yes".equals(statusPage))
//			this.setRequestAttribute("statusPage", "yes");

		
		return "reviseReplayreviser";
	}
	
	/*******************************************************
	 * @USE: 跳转到审校统计列表页面
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 纠错信息回复页面，点返回列表时，返回纠错信息列表页面
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.04.07.20.20
	 * 
	 */
	public String toStatisticsList(){
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		ipidsVO.setJump(true);
		this.setRequestAttribute("ipidsVO", ipidsVO);
//		if("yes".equals(statusPage))
//			this.setRequestAttribute("statusPage", "yes");
		
		return "statisticsList";
	}
	
	@Override
	public String getAuthStr() {
		return null;
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public long getHisId() {
		return hisId;
	}

	public void setHisId(long hisId) {
		this.hisId = hisId;
	}
	
	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	public ExamWidgetService getExamWidgetService() {
		return examWidgetService;
	}

	public void setExamWidgetService(ExamWidgetService examWidgetService) {
		this.examWidgetService = examWidgetService;
	}

	public DisplayFactory getDisplayFactory() {
		return displayFactory;
	}

	public void setDisplayFactory(DisplayFactory displayFactory) {
		this.displayFactory = displayFactory;
	}

	public AdminManageService getAdminManageService() {
		return adminManageService;
	}

	public void setAdminManageService(AdminManageService adminManageService) {
		this.adminManageService = adminManageService;
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public ItemReviseService getItemReviseService() {
		return itemReviseService;
	}

	public void setItemReviseService(ItemReviseService itemReviseService) {
		this.itemReviseService = itemReviseService;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	
}
