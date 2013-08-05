package com.ambow.trainingengine.web.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.attention.service.AttentionService;
import com.ambow.trainingengine.message.service.MessageService;
import com.ambow.trainingengine.studyguide.service.ShowStudyGuideService;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.SwieBean;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.RepuestDictForWeb;
import com.ambow.trainingengine.web.util.StudyInfoUtil;
@SuppressWarnings("serial")
public class MainPageAction extends WebBaseAction {
	private MainPageService mainPageService;
	
	private String studyInfoFirst;
	private SwieBean swieBean;
	private String studyInfoSecond;
	private boolean formulatorTest;
	private ShowStudyGuideService showStudyGuideService;
	private AttentionService attentionService;
	private MessageService messageService;

	@SuppressWarnings("static-access")
	public String ky() {		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		
		//装载当前单元所有子节点列表信息
		userData=this.getMainPageService().getNodeInstanceInfoList(userData);		
		//总进度排名列表
		this.setRequestAttribute(RepuestDictForWeb.LEARNING_PROCESS_RATE_TOP_LIST,
				this.getMainPageService().getLearningProcessRateTopList(userData));
		//获得掌握度排名
		userData=this.getMainPageService().getTotalMasteryRateOrder(userData);
		
		//获得总进度排名
		userData=this.getMainPageService().getLearningProcessRateOrder(userData);
		
		this.setSessionObj(SessionDict.UserData, userData);
		
		
		//装载星卷列表	
		if(userData.getProcessStatus()==-1)
			this.setRequestAttribute(RepuestDictForWeb.STAR_PPAPER_LIST,
					this.getMainPageService().getStarPaperList(userData,3));
		
		
		//获取完成训练的日期
		//this.getFinishDays(userData);
		this.setRequestAttribute("finishDate", StudyInfoUtil.getFinishDate(userData));
		
		//获取距离考研天数及年份
		this.getDate(RepuestDictForWeb.KY_DATE);
		genSWIEInfo();
		return SUCCESS;
	} 

	/**
	 * 获取距离特定日期天数
	 * @return
	 */
	public void getDate(String monthAndDay){
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate=new Date();
		Date tempDate=null;
		int tempYear=Integer.valueOf(new SimpleDateFormat("yyyy").format(currentDate));
		try {
			tempDate=sdf.parse(tempYear+"-"+monthAndDay);
			if(tempDate.getTime()<currentDate.getTime()){
				tempYear++;
				tempDate=sdf.parse(tempYear+"-"+monthAndDay);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//算据考研日期
		long days=0;
		
		days=(tempDate.getTime()-currentDate.getTime())/(1000*3600*24);
		this.setRequestAttribute("days", days);		
		this.setRequestAttribute("year",String.valueOf(tempYear).substring(2));
	}
	
	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
	/**
	 * 数理化主方法	 
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public String mpc() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		String userId = userData.getUserID();
		long processDefinitionId = ((UserDataVO)this.getSessionObj(SessionDict.UserData)).getProcessDefinitionId();
		long processInstanceId = userData.getProcessInstanceId();
		
		Integer nodeCount = this.getMainPageService().getNodeCount(userData.getProcessInstanceId());
		this.setRequestAttribute("nodeCount", nodeCount);
		
		//获得掌握度排名
		userData=this.getMainPageService().getTotalMasteryRateOrder(userData);
		
		//获得总进度排名
		userData=this.getMainPageService().getLearningProcessRateOrder(userData);
		
		if(userData.getProcessStatus()!=1){
			int passNum = this.getMainPageService().getPassNum(userData);
			int testingNum = this.getMainPageService().getTestingNum(userData.getClassCode(),userData.getCurrentNodeId());
			userData.setCurrentPassNum(passNum);
			userData.setCurrentTestingNum(testingNum);
		}
		//装载星卷列表	
		if(userData.getProcessStatus()==-1)
			this.setRequestAttribute(RepuestDictForWeb.STAR_PPAPER_LIST,
					this.getMainPageService().getStarPaperListForMpc(userData,3));
		
		
		//获取完成训练的日期
		String finishDate=StudyInfoUtil.getFinishDate(userData);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		if(finishDate==null){			
			this.setStudyInfoSecond("考试时间为:");			
		}
		else
			this.setStudyInfoSecond("以你目前的平均学习速度计，你将于"+finishDate+"学完本课程。");
		this.setRequestAttribute("examTime", 10);
		//是否显示公式编辑器训练层
		this.setFormulatorTest(this.getMainPageService().isFormulatorTest(userData));
		
		//学习指导链接
		List<Map<String,Object>> mapList = this.showStudyGuideService.
				getUserCurrentNode(userData.getProcessInstanceId(), userData.getProcessDefinitionId());
		this.setRequestAttribute("mapList", mapList);
		
		//试题关注
		List<Map<String,Object>> attentionList = this.attentionService.getMainPageAttention(userData.getProcessInstanceId());
		this.setRequestAttribute("attentionList", attentionList);
		
		//消息
		List messageList = messageService.getMainPageMessage(processInstanceId, userId);
		this.setRequestAttribute("messageList", messageList);
		return SUCCESS;
	}
	
	public void updateFormulatorTest(){
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		this.getMainPageService().updateFormulatorTest(userData);
	}

	public String getStudyInfoFirst() {
		return studyInfoFirst;
	}

	public void setStudyInfoFirst(String studyInfoFirst) {
		this.studyInfoFirst = studyInfoFirst;
	}

	public String getStudyInfoSecond() {
		return studyInfoSecond;
	}

	public void setStudyInfoSecond(String studyInoSecond) {
		this.studyInfoSecond = studyInoSecond;
	}
	/* 
	 * 生成传送到客户端的SWIEbean信息。提交文章给外交的action 
	 */
	private void genSWIEInfo() {
		swieBean.setUserName(((Webuser)this.getSessionObj("webuser")).getLoginName());
		this.getHttpServletRequest().setAttribute("swie", swieBean);
	}

	public SwieBean getSwieBean() {
		return swieBean;
	}

	public void setSwieBean(SwieBean swieBean) {
		this.swieBean = swieBean;
	}

	public boolean isFormulatorTest() {
		return formulatorTest;
	}

	public void setFormulatorTest(boolean formulatorTest) {
		this.formulatorTest = formulatorTest;
	}

	public ShowStudyGuideService getShowStudyGuideService() {
		return showStudyGuideService;
	}

	public void setShowStudyGuideService(ShowStudyGuideService showStudyGuideService) {
		this.showStudyGuideService = showStudyGuideService;
	}

	public AttentionService getAttentionService() {
		return attentionService;
	}

	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
