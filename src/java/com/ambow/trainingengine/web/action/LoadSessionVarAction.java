package com.ambow.trainingengine.web.action;


import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.PauseInfo;
import com.ambow.trainingengine.exam.service.ExamFlowService;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.util.DateUtil;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.PauseInfoVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;
import com.ambow.trainingengine.web.util.RepuestDictForWeb;
@SuppressWarnings("serial")
public class LoadSessionVarAction  extends WebBaseAction {
	
	private MainPageService mainPageService;
	private HibernateGenericDao genService;
	private ExamFlowService examFlowService;
	private int jumpType=0; //0是默认进入首页，1是由弱项强化结束返回弱项强化页面
	private long nodeInstanceId=0;
	private int showListType=0;
	
	/**
	 * 装载用户信息
	 */
	public String ky() {
		//清空答题数据
		clearPara();
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		//装载用户统计信息
		//装载当前节点信息
		userData=this.getMainPageService().getCurrentNodeInstanceInfoVO(userData);		
		
		//装载近10次训练节点列表
		userData=this.getMainPageService().getPracticeList(userData);
		//this.setSessionObj(SessionDict.UserData,userData);
			
		
		if(userData.getProcessStatus()<1){
			this.getSession().remove("pauseInfoVO");
			this.getSession().remove("wordNum");
			this.getSession().remove("pauseInfoNum");
			//return SUCCESS;
		}
		//判断是暂停还是生词训练
		else if(userData.getProcessStatus()==RepuestDictForWeb.PROCESS_STATUS_PAUSE){
		//装载暂停信息
			this.setPauseInfo(userData);
		}
		
		else if(userData.getProcessStatus()==RepuestDictForWeb.PROCESS_STATUS_WORDTRAINING){
			
			this.setSessionObj("wordNum",this.mainPageService.getWordNum(userData));
			//return SUCCESS;	
		}
		
		if(userData.getProcessStatus()==RepuestDictForWeb.PROCESS_STATUS_FINISH){
			this.setSessionObj("wordNum",this.mainPageService.getWordNum(userData));
			int pauseInfoNum=this.mainPageService.getPauseInfoNum(userData);
			this.setSessionObj("pauseInfoNum",pauseInfoNum);
			if(pauseInfoNum>0)
				this.setPauseInfo(userData);
		}
		
		ProcessInstance processInstance = (ProcessInstance)genService.get(ProcessInstance.class, userData.getProcessInstanceId());
		int i= processInstance.getProcessDefinition().getCategoryId().intValue();
		ProcessCategory pc=genService.get(ProcessCategory.class, Long.valueOf(i));
		userData.setProcessCategoryName(pc.getName());
		
		if(this.jumpType==1)
			return "report";
		return SUCCESS;
	}
	
	public void setPauseInfo(UserDataVO userData){
		PauseInfo pauseInfo=this.genService.get(PauseInfo.class, userData.getProcessInstanceId());
		PauseInfoVO vo=new PauseInfoVO();
		vo.setTotalItemsNum(pauseInfo.getTotalItemsNum());
		vo.setFinishedItemsNum(pauseInfo.getTotalItemsNum()-pauseInfo.getUnfinishedItemsNum());
		
		vo.setTotalAnsweringTime(DateUtil.timeFormat(pauseInfo.getAnsweringTime()));
		vo.setConsumeTime(DateUtil.timeFormat(pauseInfo.getAnsweringTime()-pauseInfo.getTimeLeft()));
		pauseInfo.getNodeInstanceId();
		String projectVersion=userData.getProcessPolicy().getProjectVersion();
		if(projectVersion.equals("ky"))
			vo.setTitleInfo(this.mainPageService.getTitleInfoForKy(pauseInfo, userData));
		else if(projectVersion.equals("mpc"))
			vo.setTitleInfo(this.mainPageService.getTitleInfoForMpc(pauseInfo, userData));
		
		String pauseType="";
		if(pauseInfo.getTestStatus()==3)
			pauseType="弱项强化";
		else
			pauseType="训练";
		vo.setPauseType(pauseType);
		if(pauseInfo.getNodeInstanceId()==null)
			vo.setNodeInstanceId(0l);
		else
			vo.setNodeInstanceId(pauseInfo.getNodeInstanceId());
		this.setSessionObj("pauseInfoVO", vo);
	}
	
	public void clearPara(){
		//清空答题数据
		ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
		if(viewControl!=null){
			this.setSessionObj(SessionDict.ViewControl, null);		
			//2%概率启动垃圾回收
			int random=(int)(Math.random()*100);
			if(random==1||random==2)
				System.gc();
		}
	}
	
	public MainPageService getMainPageService() {
		return mainPageService;
	}
	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
	public HibernateGenericDao getGenService() {
		return genService;
	}
	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
	public int getJumpType() {
		return jumpType;
	}
	public void setJumpType(int jumpType) {
		this.jumpType = jumpType;
	}
	public long getNodeInstanceId() {
		return nodeInstanceId;
	}
	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	public int getShowListType() {
		return showListType;
	}
	public void setShowListType(int showListType) {
		this.showListType = showListType;
	}
	/**
	 * 数理化主方法
	 * @return
	 */
	public String mpc() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		//装载用户统计信息
		//装载当前节点信息
		userData=this.getMainPageService().getCurrentNodeInstanceInfoVOForMPC(userData);		
		//if(userData.getProcessStatus()<1){
						
		//}
		//判断是暂停还是生词训练
		//else if(userData.getProcessStatus()==RepuestDictForWeb.PROCESS_STATUS_PAUSE){
		//装载暂停信息
		ProcessInstance processInstance = 
			(ProcessInstance)genService.get(ProcessInstance.class, userData.getProcessInstanceId());
		if (examFlowService.isPause(processInstance)) {		
			this.setPauseInfo(userData);
		}
		else
			this.getSession().remove("pauseInfoVO");
		//加载logo图片
		int i=processInstance.getProcessDefinition().getCategoryId().intValue();
		if(i==6)
			userData.setLogo("logo_c.gif");
		else if(i==7)
			userData.setLogo("logo_p.gif");
		else if(i==8)
			userData.setLogo("logo_m.gif");
		ProcessCategory pc=genService.get(ProcessCategory.class, new Long(i));
		userData.setProcessCategoryName(pc.getName());
		
		return SUCCESS;
	}

	public ExamFlowService getExamFlowService() {
		return examFlowService;
	}

	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}
	
}
