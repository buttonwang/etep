package com.ambow.trainingengine.itembank.web.action;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade;
import com.ambow.trainingengine.itembank.service.ItemReviseService;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.web.data.ItemPageIDsVO;
import com.ambow.trainingengine.itembank.web.data.ItemReviseAllVO;
import com.ambow.trainingengine.itembank.web.data.ItemStatusVO;
import com.ambow.trainingengine.itembank.web.data.ReviseQueryVO;
import com.ambow.trainingengine.itembank.web.data.UserReviseVO;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.UtilAndTool_L;

/*****************************************
 * @USE: 试题审校ACTION
 * @FOR: 试题审校相关功能的实现
 * @INFOR: ...
 * 
 * @AUTHOR: L.赵亚
 * @DATE: 2010.03.04.13.20
 * 
 */
@SuppressWarnings("serial")
public class ItemReviseAction extends BaseAction {
	private int pageNo = 1; //分布页码
	private String pageInit = "yes"; //是否是点击左侧菜单进入的
	private KnowledgePointService knowledgePointService; //知识点service
	private ItemReviseService itemReviseService; //试题审校Service
	private String title; //标题：M'数学试题' P '物理试题' C '化学试题'
	private String subject_code; //课程码
	private ReviseQueryVO rqvo; //查询条件VO
	private int itemId;
	private String fromPage;
	private String newPage;
	/***********************************************
	 * @USE: 初始化及保存页面参数
	 * @PARAM: ...
	 * @RETURN: ...
	 * @FOR: 试题审校查询页面中，知识点等，需要一些初始化参数，本方法里面对所需要参数进行初始化，
	 * 		并将用户提交过来的查询条件进行保存
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.04.15.34
	 *  
	 */
	public void initParas(){
//		List<KnowledgePoint> kplist = this.knowledgePointService.findBySubject(subject_code);

		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		if(ipidsVO!=null){
			if(ipidsVO.isJump()){
				this.subject_code = ipidsVO.getRqvo().getSubject_code();
				this.pageNo = ipidsVO.getRqvo().getPageNo();
				this.rqvo = ipidsVO.getRqvo();
				this.pageInit = "no";
			}
			this.setSessionObj("ipidsVO", null);
		}
//		this.setRequestAttribute("subject_code", subject_code);
		if ("E".equals(subject_code)) {
			title = "英语试题";
		} else if ("M".equals(subject_code)) {
			title = "数学试题";
		} else if ("P".equals(subject_code)) {
			title = "物理试题";
		} else if ("C".equals(subject_code)) {
			title = "化学试题";
		}
		if(rqvo==null)
			rqvo = new ReviseQueryVO();
		if(UtilAndTool_L.checkNotNullOrZero(subject_code))
			rqvo.setSubject_code(subject_code);
		rqvo.setPageNo(pageNo);
		if(rqvo.getReviseRecNum()==null)
			rqvo.setReviseRecNum(0);
		
		SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
		if(sysUser==null) System.out.println("用户登录信息未取到！");
		else rqvo.setSu(sysUser);
		
		this.setRequestAttribute("knowledgePointList", 
				this.knowledgePointService.findBySubject(subject_code));
//		if(this.rqvo.getReplyType()==null) this.rqvo.setReplyType(0);
		this.setRequestAttribute("rqvo", this.rqvo);
		this.setRequestAttribute("newPage", newPage);
		this.setRequestAttribute("title", title);
		this.setRequestAttribute("pageInit", pageInit);
		this.setRequestAttribute("subject_code", subject_code);
	}

	/***************************************************
	 * @USE: 查询审校记录
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 审校记录查询页面，查询得到习题的审校记录 
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.10.14.11
	 * 
	 */
	public String toQuery(){
		initParas();
		String mess = "";
		if(UtilAndTool_L.checkNullOrZero(this.subject_code)){
			mess = "没有得到课程代码！请重新登录！";
			this.setRequestAttribute("mess", UtilAndTool_L.getMessAlert(mess));
			return "error";
		}
		if(!"yes".equalsIgnoreCase(pageInit)) {
			ItemReviseAllVO iravo = itemReviseService.getAllItemRevise(pageNo, rqvo, true);
//			ItemReviseAllVO iravo = new ItemReviseAllVO();
//			iravo.setSu(rqvo.getSu());
//			iravo.testMethod();
//			this.setRequestAttribute("page", iravo.getPage());
			iravo.setCourse(subject_code);
			iravo.setRqvo(rqvo);
			this.setRequestAttribute("iravo", iravo);
			this.setSessionObj("ipidsVO", iravo.getIPIDsVO());
		}
		
		return "list";
	}
	
	/***********************************************
	 * @USE: 兼职的老师登录时，跳转到审校页面的时候，用到的方法
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 兼职老师登录的时候，会直接跳转到对应的试题审校页面，别的页面不予显示
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.25.14.09
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String toQueryTeacher() {
		this.pageInit = "yes";
		List userSubject = (List)this.getSessionObj("m_subjects");
		String mess = "";
		if(userSubject==null||userSubject.size()==0){
			mess = "没有得到课程代码！请重新打开本页或重新登录！";
		} else if(UtilAndTool_L.checkNullOrZero(this.subject_code)){
			RoleSubjectGrade rsg = (RoleSubjectGrade)userSubject.get(0);
			if(rsg==null)
				mess = "没有得到课程代码！请重新打开本页或重新登录！";
			else
				this.subject_code = rsg.getRsg_pk().getSubjectCode();
		}
		if(mess.length()>0){
			this.setRequestAttribute("mess", UtilAndTool_L.getMessAlert(mess));
			return "error";
		}
		
		return toQuery();
	}
	
	/***********************************************
	 * @USE: 回复信息查询
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 所有含有审校问题信息的列表
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.25.14.09
	 * 
	 */
	public String toQueryReply() {
		if("reviseStatus".equals(fromPage))
			return this.queryStatus();
		initParas();
		String mess = "";
		if(UtilAndTool_L.checkNullOrZero(this.subject_code)){
			mess = "没有得到课程代码！请重新打开本页或重新登录！";
			this.setRequestAttribute("mess", UtilAndTool_L.getMessAlert(mess));
			return "error";
		}
		ItemReviseAllVO iravo = itemReviseService.getAllItemReply(pageNo, rqvo, true);
		iravo.setCourse(subject_code);
		iravo.setRqvo(rqvo);
		this.setRequestAttribute("iravo", iravo);
		this.setSessionObj("ipidsVO", iravo.getIPIDsVO());
		
		return "listReply";
	}
	
	/***********************************************
	 * @USE: 查询审校情况（跳转到查询页面）
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 所有试题的审校情况信息的列表《跳转到查询页面）
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.05.21.14.00
	 * 
	 */
	public String toQueryStatus() {
		initParas();
		this.setRequestAttribute("init", "yes");
		BigDecimal[] counts = itemReviseService.getAllItemPass(rqvo);
		if(counts!=null&&counts.length==4){
			this.setRequestAttribute("allItemCount", counts[0]);
			this.setRequestAttribute("passCount", counts[1]);
			this.setRequestAttribute("unPassCount", (counts[0].subtract(counts[1])));
			rqvo.setMaxRevise(counts[2]);
			rqvo.setMaxRevised(counts[3]);
			if(rqvo.getReviseNum()==null)
				rqvo.setReviseNum(counts[2]);
			if(rqvo.getRevisedNum()==null)
				rqvo.setRevisedNum(counts[3]);
		}

		return "listStatus";
	}
	
	/***********************************************
	 * @USE: 查询审校情况
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 所有试题的审校情况信息的列表
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.05.21.14.00
	 * 
	 */
	public String queryStatus() {
		initParas();
		String mess = "";
		if(UtilAndTool_L.checkNullOrZero(this.subject_code)){
			mess = "没有得到课程代码！请重新打开本页或重新登录！";
			this.setRequestAttribute("mess", UtilAndTool_L.getMessAlert(mess));
			return "error";
		}
		Page page = itemReviseService.getAllItemStatus(pageNo, rqvo);
		this.setRequestAttribute("page", page);
		ItemPageIDsVO ipidsVO = ItemStatusVO.getISVOIds(page.getResult());
		if(ipidsVO!=null){
			ipidsVO.setRqvo(rqvo);
			this.setSessionObj("ipidsVO", ipidsVO);
		}
		BigDecimal[] counts = itemReviseService.getAllItemPass(rqvo);
		if(counts!=null&&counts.length==4){
			if(counts[0]!=null)
				this.setRequestAttribute("allItemCount", counts[0]);
			else
				this.setRequestAttribute("allItemCount", "0");
			if(counts[1]!=null)
				this.setRequestAttribute("passCount", counts[1]);
			else
				this.setRequestAttribute("passCount", "0");
			if(counts[0]!=null&&counts[1]!=null)
				this.setRequestAttribute("unPassCount", (counts[0].subtract(counts[1])));
			else
				this.setRequestAttribute("unPassCount", "0");
			rqvo.setMaxRevise(counts[2]);
			rqvo.setMaxRevised(counts[3]);
			if(rqvo.getReviseNum()==null)
				rqvo.setReviseNum(counts[2]);
			if(rqvo.getRevisedNum()==null)
				rqvo.setRevisedNum(counts[3]);
		}
		
		return "listStatus";
	}
	
	public String showReply(){
		if(UtilAndTool_L.checkNotNullOrZero(fromPage))
			this.setRequestAttribute("fromPage", fromPage);
		this.setRequestAttribute("itemId", this.itemId);
		return "mpcReply";
	}
	
	public String show() {
		this.setRequestAttribute("itemId", this.itemId);
		if (subject_code.equals("E")) return "ky";
		else if (subject_code.equals("M")||subject_code.equals("P")||subject_code.equals("C")) return "mpc";
		return "show";
	}
	
	/***************************************************
	 * @USE: 查询审校统计记录
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 审校统计记录查询页面，查询得到习题的审校记录 
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.21.11.11
	 * 
	 */
	public String toStatistics(){
		initParas();
		String mess = "";
		if(UtilAndTool_L.checkNullOrZero(this.subject_code)){
			mess = "没有得到课程代码！请重新登录！";
			this.setRequestAttribute("mess", UtilAndTool_L.getMessAlert(mess));
			return "error";
		}
		List<UserReviseVO> urlist= itemReviseService.getReviser(rqvo);
		this.setRequestAttribute("urlist", urlist);
		
		if(!"yes".equalsIgnoreCase(pageInit)) {
			ItemReviseAllVO iravo = itemReviseService.getItemReviseByReviser(pageNo, rqvo, String.valueOf(rqvo.getReviser()), String.valueOf(rqvo.getReviseRecNum()));

			iravo.setCourse(subject_code);
			iravo.setRqvo(rqvo);
			this.setRequestAttribute("iravo", iravo);
			this.setSessionObj("ipidsVO", iravo.getIPIDsVO());
			Map<String, BigInteger> errormap= itemReviseService.getErrorstatusList(rqvo, String.valueOf(rqvo.getReviser()), String.valueOf(rqvo.getReviseRecNum()));
			this.setRequestAttribute("errormap", errormap);
			
			Map<String, BigInteger> statusmap= itemReviseService.getVerifystatusList(rqvo, String.valueOf(rqvo.getReviser()), String.valueOf(rqvo.getReviseRecNum()));
			this.setRequestAttribute("statusmap", statusmap);
			double rate=itemReviseService.getScorerate(rqvo, String.valueOf(rqvo.getReviser()), String.valueOf(rqvo.getReviseRecNum()));
			this.setRequestAttribute("scorerate", String.valueOf(rate).substring(2));
		}
		
		return "statistics";
	}
	/***************************************************
	 * @USE: 跳转到某道题的详情页面
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 审校统计某道题的详情，查询得到习题的审校记录 
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.21.11.11
	 * 
	 */
	public String showReplyreviser(){
//		if("yes".equals(statusPage))
//			this.setRequestAttribute("statusPage", "yes");
		this.setRequestAttribute("itemId", this.itemId);
		return "mpcReplyreviser";
	}
	
	/***************************************************
	 * @USE: 查询审校统计记录
	 * @PARAM: ...
	 * @RETURN: result
	 * @FOR: 审校统计记录查询页面，查询得到习题的审校记录 
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.21.11.11
	 * 
	 */
	public String showDetail(){
		
		ItemPageIDsVO ipidsVO = (ItemPageIDsVO)this.getSessionObj("ipidsVO");
		if(ipidsVO!=null){
				this.subject_code = ipidsVO.getRqvo().getSubject_code();
				this.rqvo = ipidsVO.getRqvo();
				
		}
		if ("E".equals(subject_code)) {
			title = "英语试题";
		} else if ("M".equals(subject_code)) {
			title = "数学试题";
		} else if ("P".equals(subject_code)) {
			title = "物理试题";
		} else if ("C".equals(subject_code)) {
			title = "化学试题";
		}
		
		if(rqvo==null)
			rqvo = new ReviseQueryVO();
		if(UtilAndTool_L.checkNotNullOrZero(subject_code))
			rqvo.setSubject_code(subject_code);
	
		this.setRequestAttribute("rqvo", this.rqvo);
		this.setRequestAttribute("subject_code", subject_code);
		
		String cond="";
		String status=this.getHttpServletRequest().getParameter("status");
		String reply=this.getHttpServletRequest().getParameter("reply");
		Integer condnum=new Integer(0);
		if(status==null || "".equals(status)){
			cond="reply";
			condnum=Integer.valueOf(reply);
		}if(reply==null || "".equals(reply)){
			cond="status";
			condnum=Integer.valueOf(status);
		}
		
		ItemReviseAllVO iravo1 =itemReviseService.getItemByCondition(pageNo, rqvo, String.valueOf(rqvo.getReviser()), String.valueOf(rqvo.getReviseRecNum()), cond, condnum);
		
		iravo1.setCourse(subject_code);
		iravo1.setRqvo(rqvo);
		this.setRequestAttribute("iravo", iravo1);
		this.setRequestAttribute("status", status);
		this.setRequestAttribute("reply", reply);
		return "statisticsdetail";
	}
	
		
	public ItemReviseService getItemReviseService() {
		return itemReviseService;
	}

	public void setItemReviseService(ItemReviseService itemReviseService) {
		this.itemReviseService = itemReviseService;
	}

	public String getPageInit() {
		return pageInit;
	}

	public void setPageInit(String pageInit) {
		this.pageInit = pageInit;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ReviseQueryVO getRqvo() {
		return rqvo;
	}

	public void setRqvo(ReviseQueryVO rqvo) {
		this.rqvo = rqvo;
	}
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getFromPage() {
		return fromPage;
	}

	public void setFromPage(String fromPage) {
		this.fromPage = fromPage;
	}

	public String getNewPage() {
		return newPage;
	}

	public void setNewPage(String newPage) {
		this.newPage = newPage;
	}
}
