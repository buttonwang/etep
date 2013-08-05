package com.ambow.trainingengine.exam.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.display.DisplayFactory;
import com.ambow.trainingengine.exam.display.IDisplay;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.stat.IStat;
import com.ambow.trainingengine.exam.stat.StatFactory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.itembank.service.ItemReviseService;
import com.ambow.trainingengine.itembank.web.data.ItemReviseAllVO;
import com.ambow.trainingengine.itembank.web.data.ItemReviseVO;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.AdminManageService;
import com.ambow.trainingengine.util.SessionDict;

/*
 * ReviseExamAction.java
 * 
 * Created on 2010-3-17 下午01:42:28
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

public class ReviseExamAction extends BaseAction {

	private static final long serialVersionUID = 984934042243481392L;

	private String userAnswers;
	private HibernateGenericDao genService;
	private StatFactory statFactory;
	private DisplayFactory displayFactory;
	private String reviseReply;
	private Integer replyType;
	private Integer reviseId; 
	private int id;
	private String fromPage;
	private String reviseRecord;
	private ItemReviseService itemReviseService;
	private AdminManageService adminManageService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public String execute() {
		ViewControlProxy viewControl = (ViewControlProxy) this.getHttpSessionObj(SessionDict.ViewControl);
		
		if (userAnswers != null) viewControl.
					parseAnswer(userAnswers, 
							viewControl.
								getPageList().
								get(0));
		viewControl.setPreAnswersStatus(new HashMap<String, CurrentAnswersStatus>());
		
		Page page = viewControl.getPageList().get(0);
		Item item = page.getItems().get(0);
		SysUser sysUser = (SysUser) this.getHttpSessionObj(SessionDict.AdminUser);
		
		updateItemRevise(viewControl, item, sysUser);
		
		IStat stat = statFactory.getStatImpl(viewControl.getProjectVersion());
		stat.saveReviseAnswers(viewControl);
		
		viewControl.generateWidget(item, null, 4);
		viewControl.setShowModel(4); //Widget逐题浏览 mode
		String resultCode = ExamUtil.getResultCode(viewControl.getProjectVersion(), item.getItemType().getCode());
		IDisplay display = displayFactory.getDisplayImpl(resultCode);
		display.doDisplay(page, viewControl, this.getHttpServletRequest());
		
		this.setSessionObj("currentPage", page);
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		this.getHttpServletRequest().setAttribute("widgetPage", ExamUtil.getwidgetPage(resultCode));
		this.setRequestAttribute("itemId", item.getId());
		
		if("reviseReply".equals(this.fromPage)||"reviseStatus".equals(this.fromPage)){
			Item it = genService.get(Item.class, item.getId());
			ItemReviseVO irvo = ItemReviseAllVO.getIRVO(it, adminManageService);
			this.setRequestAttribute("irvo", irvo);
			this.setRequestAttribute("fromPage", this.fromPage);
			
			return "reviseReplay";
		}
		
		return "revise";
	}
	
	public void revised() {
		ViewControlProxy viewControl = (ViewControlProxy) this.getHttpSessionObj(SessionDict.ViewControl);	
		Page page = viewControl.getPageList().get(0);
		Item item = page.getItems().get(0);
		item.setReviseStatus(1);
		genService.save(item);
		
		ItemRevise itemRevise = itemReviseService.get(viewControl.getItemRevise().getId());
		itemRevise.setReviseStatus(1);
		itemRevise.setRevisedTime(new Date());
		genService.save(itemRevise);
		try {
			this.getHttpServletResponse().getWriter().write("isSuccess");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/******************************************************
	 * @USE: 保存回复信息
	 * @PARAM: ...
	 * @RETURN: yes 回复成功, no_0 回复保存失败--没有得到要回复的纠错信息的ID
	 * 			no_1 没有得到对应的审校纠错信息, no_2 保存回复过程中发生错误
	 * @FOR: 回复兼职老师的纠错信息
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.06.18.57
	 * 
	 */
	public String reply() {
		String retStr = ""; //返回字符串
		if(this.reviseId==null)
			 retStr = "no_0";
		SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
		if(sysUser==null) System.out.println("用户登录信息未取到！");
		String hql = "from ItemRevise i where i.id=?";
		try{
			ItemRevise itemRevise = (ItemRevise)genService.findObjByHql(hql, reviseId);
			if(itemRevise==null) return "no_1";
			itemRevise.setReplyType(this.replyType);
			itemRevise.setReviseReply(this.reviseReply);
			itemRevise.setReviseReplyer(sysUser.getId());
			Date dd = new Date();
			itemRevise.setReviseReplyTime(dd);
			genService.save(itemRevise);
			 retStr = ("yes;" + sdf.format(dd) + ";" + sysUser.getRealName());
		} catch (Exception e) {
			e.printStackTrace();
			 retStr = "no_2";
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
	
	public void queryReviseRecord() {
		ItemRevise itemRevise = itemReviseService.get(id);
		
		String str = "{\"reviseRecord\":" + "\"" + itemRevise.getReviseRecord().replace("\"", "\\\"") + "\""
					+",\"reviseReplyType\":"  + "\"" + itemRevise.getReplyTypeName2() + "\""
					+",\"reviseReply\":"  + "\"" + itemRevise.getReviseReply().replace("\"", "\\\"")  + "\""
					+"}";
		str = str.replace("\r\n", "\\r");
		try {
			this.getHttpServletResponse().setContentType("application/json;charset=UTF-8");
			this.getHttpServletResponse().getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveReviseRecord() {
		ItemRevise itemRevise;
		if (id == 0) {
			itemRevise = new ItemRevise();
			ViewControlProxy viewControl = (ViewControlProxy) this.getHttpSessionObj(SessionDict.ViewControl);
			SysUser sysUser = (SysUser) this.getHttpSessionObj(SessionDict.AdminUser);
			Page page = viewControl.getPageList().get(0);
			Item item = page.getItems().get(0);
			itemRevise.setItem(item);
			itemRevise.setReviser(sysUser.getId());
		} else {
			itemRevise = itemReviseService.get(id);
		}
		itemRevise.setReviseRecord(reviseRecord);
		itemRevise.setReviseRecordTime(new Date());
		//itemRevise.setReplyType(0);	//save old type
		itemRevise.setReviseReply("");
		itemRevise.setReviseReplyer(null);
		itemRevise.setReviseReplyTime(null);
		itemReviseService.save(itemRevise);
		
		try {
			this.getHttpServletResponse().getWriter().write("isSuccess");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateItemRevise(ViewControl viewControl, Item item, SysUser sysUser) {
		String hql = "from ItemRevise i where i.item.id=? and i.reviser=?";
		ItemRevise itemRevise = (ItemRevise)genService.findObjByHql(hql, item.getId(), sysUser.getId());
		
		if (itemRevise==null) {
			itemRevise = new ItemRevise();
			itemRevise.setReviser(sysUser.getId());
			itemRevise.setReviseTime(new Date());
			itemRevise.setItem(item);
			Integer reviseId = (Integer)genService.save1(itemRevise);
			itemRevise = genService.get(ItemRevise.class, reviseId);
		} else {
			itemRevise.setReviseTime(new Date());
			genService.save(itemRevise);
			genService.excuteHql("delete from ItemReviseAnswers a where a.itemRevise.id=? ", itemRevise.getId());
		}
		viewControl.setItemRevise(itemRevise);
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserAnswers() {
		return userAnswers;
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public void setUserAnswers(String userAnswers) {
		this.userAnswers = userAnswers;
	}

	public StatFactory getStatFactory() {
		return statFactory;
	}

	public void setStatFactory(StatFactory statFactory) {
		this.statFactory = statFactory;
	}
	
	public DisplayFactory getDisplayFactory() {
		return displayFactory;
	}

	public void setDisplayFactory(DisplayFactory displayFactory) {
		this.displayFactory = displayFactory;
	}

	public String getReviseReply() {
		return reviseReply;
	}

	public void setReviseReply(String reviseReply) {
		this.reviseReply = reviseReply;
	}

	public Integer getReplyType() {
		return replyType;
	}

	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}

	public Integer getReviseId() {
		return reviseId;
	}

	public void setReviseId(Integer reviseId) {
		this.reviseId = reviseId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getReviseRecord() {
		return reviseRecord;
	}

	public void setReviseRecord(String reviseRecord) {
		this.reviseRecord = reviseRecord;
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

	public AdminManageService getAdminManageService() {
		return adminManageService;
	}

	public void setAdminManageService(AdminManageService adminManageService) {
		this.adminManageService = adminManageService;
	}
}
