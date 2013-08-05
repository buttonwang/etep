package com.ambow.trainingengine.bug.web.action;

import java.io.PrintWriter;
import java.util.List;

import com.ambow.trainingengine.bug.domain.BugInfo;
import com.ambow.trainingengine.bug.domain.BugInfoHistoryAnswerStatus;
import com.ambow.trainingengine.bug.domain.ReplyInfoTemplate;
import com.ambow.trainingengine.bug.service.BugInfoHistoryAnswerStatusService;
import com.ambow.trainingengine.bug.service.BugService;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.RequestAttributeByMap;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.UtilAndTool_L;
import com.ambow.trainingengine.web.data.UserDataVO;

public class BugAction extends BugBaseAction {	
	private static final long serialVersionUID = -4945113015857252332L;
	private BugService bugService;
	public BugInfoHistoryAnswerStatusService bugInfoHistoryAnswerStatusService;
	public String delBugAddForm;
	private int pageNo = 1; //分布页码
	 
	public String showItemAnswerVO (){
		ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
		setRequestAttribute("itemAnswerVO",bugService.getItemAnswerVO(p,viewControl) );
		return to("showItemAnswerVO");
	}
	
	public String showUserItemBihasLstLst(){
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),bugService.getItemBugInfoHistoryAnswerStatusGroupByUser(p));
		setRequestAttribute("b",b);
		this.setSessionObj("ipidsVO", null);
		return to("showUserItemBihasLstLst");
	}
	
	public String showReplyInfoTemplate(){
		if(p.getInteger("replyInfoTemplateId")!=null)
			setRequestAttribute("replyInfoTemplate", bugService.get(ReplyInfoTemplate.class,p.getInteger("replyInfoTemplateId")));
		return to("showReplyInfoTemplate");
	}
	
	public String editReplyInfoTemplate(){
		if(p.getInteger("replyInfoTemplateId")!=null)
			setRequestAttribute("replyInfoTemplate", bugService.get(ReplyInfoTemplate.class,p.getInteger("replyInfoTemplateId")));
		return to("editReplyInfoTemplate");
	}
	
	public String listReplyInfoTemplate(){
		setRequestAttribute("replyInfoTemplates", bugService.getReplayInfoTemplates());
		return to("listReplyInfoTemplate");
	}
	
	public String updateReplyInfoTemplate(){
		if(p.get ("replyInfoTemplateContent")!=null||p.get ("replyInfoTemplateName")!=null||p.getInteger("replyInfoTemplateId")!=null){
			ReplyInfoTemplate replyInfoTemplate = new ReplyInfoTemplate();
			replyInfoTemplate.setId(p.getInteger("replyInfoTemplateId"));
			replyInfoTemplate.setReplyContent(p.get ("replyInfoTemplateContent"));
			replyInfoTemplate.setTempalteName(p.get ("replyInfoTemplateName")); 
			replyInfoTemplate.setReplyPoint(p.getInteger("replyPoint")); 
			bugService.newOrUpdateReplyInfoTemplate(replyInfoTemplate);
		}
		setRequestAttribute("replyInfoTemplates", bugService.getReplayInfoTemplates());
		return to("listReplyInfoTemplate");
	}

	/**
	 * 取得某试题的所有捉虫记录 
	 */
	public String showItemBugInfoHistoryAnswerStatus (){ 
		Integer itemId=p.getInteger("itemId");

		if(itemId!=null){
			ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
			setRequestAttribute("itemAnswerVO",bugService.getItemAnswerVO(p,viewControl));
			List<BugInfoHistoryAnswerStatus> bugInfoHistoryAnswerStatusLst = bugInfoHistoryAnswerStatusService.showItemBugInfoHistoryAnswerStatus(getUser(p.get("userType")), b);
			setRequestAttribute("bugInfoHistoryAnswerStatusList",bugService.getBugInfoHistoryAnswerStatusVOLst( bugInfoHistoryAnswerStatusLst));
		}
		return to("showItemAnswerVO");
	}
	
	/**
	 * 通过BugInfo，取得某试题的捉虫记录。
	 */
	public String showItemBug(){ 
		Integer bugInfoId = p.getInteger("bugInfoId");
		BugInfo bugInfo = bugService.getBugInfo(bugInfoId);
		if(bugInfo!=null){
			Integer itemId = bugInfo.getBug().getItem().getId();
			Integer historyTestStatusId = bugInfo.getBugInfoHistoryAnswerStatuses().iterator().next()
										  .getHistoryAnswerStatus().getHistoryTestStatus().getId();
			p.add("itemId", String.valueOf(itemId));
			p.add("historyTestStatusId", String.valueOf(historyTestStatusId));
			
			b = new BugInfoHistoryAnswerStatus();
			b.setHistoryAnswerStatus(new HistoryAnswerStatus());
			b.getHistoryAnswerStatus().setItem(new Item()); 
			b.getHistoryAnswerStatus().getItem().setId(itemId);
		} else {
			this.setRequestAttribute("delBugFlag", "yes");
		}
		
		return showItemBugInfoHistoryAnswerStatus();
	}
	
	/**
	 * 取得当前用户所有有捉虫信息的题 最后一条捉虫信息;
	 */
	public String listDifferentItemBugInfoHistoryAnswerStatus(){
	
		Object user = getUser(p.get("userType"));
		UserDataVO userDataVO = (UserDataVO)this.getSessionObj(SessionDict.UserData);
		if(userDataVO!=null&&UtilAndTool_L.checkNotNullOrZero(userDataVO.getClassCode())){
			if(b.getHistoryAnswerStatus()==null){
				Subject subject = new Subject();
				Item item = new Item();
				item.setSubject(subject);
				HistoryAnswerStatus has = new HistoryAnswerStatus();
				has.setItem(item);
				b.setHistoryAnswerStatus(has);
			}
			if("t00000000001".equals(userDataVO.getClassCode())){
				b.getHistoryAnswerStatus().getItem().getSubject().setCode("M");
			} else if ("t00000000002".equals(userDataVO.getClassCode())){
				b.getHistoryAnswerStatus().getItem().getSubject().setCode("C");
			} else if ("t00000000003".equals(userDataVO.getClassCode())){
				b.getHistoryAnswerStatus().getItem().getSubject().setCode("P");
			}
		}
		setRequestAttribute("differentItemBugInfoHistoryAnswerStatusLst", bugInfoHistoryAnswerStatusService.listDifferentItemBugInfoHistoryAnswerStatus(user,b));
		setRequestAttribute("b",b);
		return to("listDifferentItemBugInfoHistoryAnswerStatus");
	}
	
	public String getBugCount(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		setRequestAttribute("bugCount", bugService.getBugCount(user,p.getInteger("itemId")));
		return to("bugCount");
	}
	/**
	 * 取得当前用户所有有捉虫信息的题 最后一条捉虫信息;
	 */
	public String listDifferentItemBugInfoHistoryAnswerStatusByTeacher(){
		SysUser user =(SysUser)getSessionObj(SessionDict.AdminUser);
		try{
			String subjectCode = b.getHistoryAnswerStatus().getItem().getSubject().getCode();
			if(subjectCode!=null&&!"".equals(subjectCode.trim())){
				String title = "";
				if ("E".equals(subjectCode)) {
					title = "英语试题";
				} else if ("M".equals(subjectCode)) {
					title = "数学试题";
				} else if ("P".equals(subjectCode)) {
					title = "物理试题";
				} else if ("C".equals(subjectCode)) {
					title = "化学试题";
				}
				this.setRequestAttribute("title", title);
//				setRequestAttribute("differentItemBugInfoHistoryAnswerStatusLst", bugInfoHistoryAnswerStatusService.listDifferentItemBugInfoHistoryAnswerStatus(user,b,false));
				setRequestAttribute("page", bugInfoHistoryAnswerStatusService.listDifferentItemBugInfoHistoryAnswerStatusPaged(user,b,false, pageNo));
				setRequestAttribute("b",b);
			}
		}catch(Exception e){
		}
		return to("listDifferentItemBugInfoHistoryAnswerStatusByTeacher");
	}
	public String abadonBugsByItemIds(){
	 	bugService.abadonBugsByItemIds(getUser(p.get("type")),p);
		return to("success");
	}
	
	public String abadonBugInfos(){
		 bugService.abadonBugInfos(getUser(p.get("type")),p);
		 
		return to("success");
	}
	
	/********************************************
	 * @USE: 前台捉虫页面删除提交错误的虫
	 * @PARAM: ...
	 * @RETURN: NULL
	 * @FOR: 前台捉虫的时候，可能会出现捉虫出错用户想要删除的情况。这个时候，可使用此删除功能
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.23.18.13
	 * 
	 */
	public String abadonBugInfosAddBug(){
		String retStr = "";
		try{
		 	bugService.abadonBugInfosAddBug(getUser(p.get("type")),p);
		 	retStr = "yes";
		} catch (Exception e){
			retStr = "no";
		}
		PrintWriter pw = null; 
		try{
			pw = this.getHttpServletResponse().getWriter();
			pw.write(retStr);
		} catch (Exception e) {
			
		} finally {
			if(pw!=null) pw.close();
		}
		
		return null;
	}

	/**
	 * 增加一条捉虫信息
	 */
	public String addBugInfo(){
		Webuser webuser = (Webuser) getSessionObj(SessionDict.WebUser);
		ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
	 	bugService.addBugInfo(webuser,p,viewControl);
		return to( "success");
	}
	
	public String replyBugInfo( ){
		SysUser sysUser =(SysUser)getSessionObj(SessionDict.AdminUser);
		bugService.replyBugInfo(sysUser,p);
		return to( "success");
	}
	
	/*********************************************
	 * USE: 修改BUG的回复信息
	 * PARAM: ...
	 * RETURN: ...
	 * FOR: BUG管理查看页面，管理员修改回复信息时，保存修改后的数据
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.10.01
	 * 
	 */
	public String modiReplyBugInfo(){
		String retStr = "";
		try{
			SysUser sysUser =(SysUser)getSessionObj(SessionDict.AdminUser);
			retStr = "yes" + UtilAndTool_L.SPLIT_STRING;
//			retStr += p.get("replyInfoPrefix") + ". " + p.get("replyInfo");
			retStr += bugService.modiReplyBugInfo(sysUser,p);
		} catch (Exception e){
			retStr = "no";
		}
		PrintWriter pw = null; 
		try{
			pw = this.getHttpServletResponse().getWriter();
			pw.write(retStr);
		} catch (Exception e) {
			
		} finally {
			if(pw!=null) pw.close();
		}
		
		return null;
	}
	
	public String bugInfoHistoryAnswerStatus(){
		jsp="show_bugInfoHistoryAnswerStatus"; 
		setRequestAttribute("bugInfoHistoryAnswerStatus", bugService.get(BugInfoHistoryAnswerStatus.class,p.getLong("bugInfoHistoryAnswerStatusId")));
		return to();
	}
	
	public BugService getBugService() {
		return bugService;
	}

	public void setBugService(BugService bugService) {
		this.bugService = bugService;
	}

	public BugInfoHistoryAnswerStatusService getBugInfoHistoryAnswerStatusService() {
		return bugInfoHistoryAnswerStatusService;
	}

	public void setBugInfoHistoryAnswerStatusService(
			BugInfoHistoryAnswerStatusService bugInfoHistoryAnswerStatusService) {
		this.bugInfoHistoryAnswerStatusService = bugInfoHistoryAnswerStatusService;
	}
	Integer bugInfoStatus;
	Integer bugStatus ;
	public Integer getBugInfoStatus() {
		return bugInfoStatus;
	}

	public void setBugInfoStatus(Integer bugInfoStatus) {
		this.bugInfoStatus = bugInfoStatus;
	}

	public Integer getBugStatus() {
		return bugStatus;
	}

	public void setBugStatus(Integer bugStatus) {
		this.bugStatus = bugStatus;
	}

	public String getDelBugAddForm() {
		return delBugAddForm;
	}

	public void setDelBugAddForm(String delBugAddForm) {
		this.delBugAddForm = delBugAddForm;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
}
