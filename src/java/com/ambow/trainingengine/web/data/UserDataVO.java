package com.ambow.trainingengine.web.data;

import java.util.Date;
import java.util.List;

import com.ambow.trainingengine.policy.domain.ProcessPolicy;

public class UserDataVO {
	/*登录时的变量*/
	private String userID;	
	private String loginName;	
	private String userName;	
	private String classCode;	
	private String moduleID;	
	private String refID;	
	private String gradeID;
	
	/*登录首页面的数据信息*/
	private String lastTrainingTime;
	private String trainingTimes;
	
	private String unitNodeName;//单元名称
	private String currentNodeName;//节点名称
	private long currentNodeId=0;//当前节点定义id
	private long currentNodeInstanceId=0;//当前节点实例id
	private long unitNodeId=0;//单元节点定义id
	
	private int currentNodeItemNum;//当前节点题数#
	private int currentNodeTime;//当前节点限时#
	private int currentTestingNum;//当前节点训练人数
	private int currentPassNum;//当前节点训练通过人数
	
	private int processStatus;//流程状态0-正常运行;1暂停;2生词训练;-1-终止
	private long processInstanceId;//流程实例id
	private String processName;//流程实例名字
	private long processDefinitionId;//流程定义id
	
	private Integer totalItemsNum;//总题数#
	private Integer sumCorrectItems;//正确题数
	private Integer sumIncorrectItems;//错误题数
	private Integer sumUnfinishedItems;//未作题数
	private Integer sumfinishedItems;//已作题数
	private Integer unsureMarkItems;//疑问题总数
	private Integer sumZeroStarItems;
	private Integer sumHalfStarItems;
	private Integer sumOneStarItems;
	private Integer sumTwoStarItems;
	private Integer sumThreeStarItems;
	private Integer sumFourStarItems;
	private Integer sumFiveStarItems;
	private Float totalScore;
	private int totalMasteryRate;//总掌握度
	private int totalAccuracyRate;//总正确率
	private int learningProcessRate;//学习总进度
	
	private int totalMasteryRateOrder;//总掌握度排名
	private int totalAccuracyRateOrder;//总正确率排名
	private int learningProcessRateOrder;//学习总进度排名
	
	private Integer totalTrainingTime;//总训练时间
	private Date firstTrainingTime;
	
	private List<PracticeListVO> practiceList;//最近10个训练节点列表
	private List<NodeInstanceInfoVO> nodeInstanceInfoList;//当前单元的所有子节点列表
	private List<ShowNodeGroupVO> showNodeGroupVOList;//显示节点组列表
	
	private int nodeListDivWidth=0;
	
	private String pauseNodeIds;
	
	private Date customerExamTime;
	
	private String Logo;
	
	private String processCategoryName;
	
	//private String processCategoryFolderName;//产品类型对应文件夹类型
	//public Integer processCategoryId;//产品类型Id
	//private String projectVersion;//项目版本对应的文件夹类型
	private ProcessPolicy processPolicy;//流程策略
	
	public UserDataVO(String userID,String loginName,
			String userName,String classCode,String moduleID,
			String refID,String gradeID){
		this.userID=userID;
		this.loginName=loginName ;
		this.userName=userName ;
		this.classCode=classCode;
		this.moduleID=moduleID;
		this.refID=refID;
		this.gradeID=gradeID;
	}
	
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public String getGradeID() {
		return gradeID;
	}
	public void setGradeID(String gradeID) {
		this.gradeID = gradeID;
	}


	public String getLastTrainingTime() {
		return lastTrainingTime;
	}


	public void setLastTrainingTime(String lastTrainingTime) {
		this.lastTrainingTime = lastTrainingTime;
	}


	public String getTrainingTimes() {
		return trainingTimes;
	}


	public void setTrainingTimes(String trainingTimes) {
		this.trainingTimes = trainingTimes;
	}


	public String getUnitNodeName() {
		return unitNodeName;
	}


	public void setUnitNodeName(String unitNodeName) {
		this.unitNodeName = unitNodeName;
	}


	public String getCurrentNodeName() {
		return currentNodeName;
	}


	public void setCurrentNodeName(String currentNodeName) {
		this.currentNodeName = currentNodeName;
	}


	public long getCurrentNodeId() {
		return currentNodeId;
	}


	public void setCurrentNodeId(long currentNodeId) {
		this.currentNodeId = currentNodeId;
	}


	public long getUnitNodeId() {
		return unitNodeId;
	}


	public void setUnitNodeId(long unitNodeId) {
		this.unitNodeId = unitNodeId;
	}


	public int getCurrentNodeItemNum() {
		return currentNodeItemNum;
	}


	public void setCurrentNodeItemNum(int currentNodeItemNum) {
		this.currentNodeItemNum = currentNodeItemNum;
	}


	public int getCurrentNodeTime() {
		return currentNodeTime;
	}


	public void setCurrentNodeTime(int currentNodeTime) {
		this.currentNodeTime = currentNodeTime;
	}


	public long getProcessInstanceId() {
		return processInstanceId;
	}


	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public Integer getTotalItemsNum() {
		return totalItemsNum;
	}


	public void setTotalItemsNum(Integer totalItemsNum) {
		this.totalItemsNum = totalItemsNum;
	}


	public Integer getSumCorrectItems() {
		return sumCorrectItems;
	}


	public void setSumCorrectItems(Integer sumCorrectItems) {
		this.sumCorrectItems = sumCorrectItems;
	}


	public Integer getSumIncorrectItems() {
		return sumIncorrectItems;
	}


	public void setSumIncorrectItems(Integer sumIncorrectItems) {
		this.sumIncorrectItems = sumIncorrectItems;
	}


	public Integer getSumUnfinishedItems() {
		return sumUnfinishedItems;
	}


	public void setSumUnfinishedItems(Integer sumUnfinishedItems) {
		this.sumUnfinishedItems = sumUnfinishedItems;
	}


	public Integer getSumZeroStarItems() {
		return sumZeroStarItems;
	}


	public void setSumZeroStarItems(Integer sumZeroStarItems) {
		this.sumZeroStarItems = sumZeroStarItems;
	}


	public Integer getSumHalfStarItems() {
		return sumHalfStarItems;
	}


	public void setSumHalfStarItems(Integer sumHalfStarItems) {
		this.sumHalfStarItems = sumHalfStarItems;
	}


	public Integer getSumOneStarItems() {
		return sumOneStarItems;
	}


	public void setSumOneStarItems(Integer sumOneStarItems) {
		this.sumOneStarItems = sumOneStarItems;
	}


	public Integer getSumTwoStarItems() {
		return sumTwoStarItems;
	}


	public void setSumTwoStarItems(Integer sumTwoStarItems) {
		this.sumTwoStarItems = sumTwoStarItems;
	}


	public Integer getSumThreeStarItems() {
		return sumThreeStarItems;
	}


	public void setSumThreeStarItems(Integer sumThreeStarItems) {
		this.sumThreeStarItems = sumThreeStarItems;
	}


	public Integer getSumFourStarItems() {
		return sumFourStarItems;
	}


	public void setSumFourStarItems(Integer sumFourStarItems) {
		this.sumFourStarItems = sumFourStarItems;
	}


	public Integer getSumFiveStarItems() {
		return sumFiveStarItems;
	}


	public void setSumFiveStarItems(Integer sumFiveStarItems) {
		this.sumFiveStarItems = sumFiveStarItems;
	}


	public int getTotalMasteryRate() {
		return totalMasteryRate;
	}


	public void setTotalMasteryRate(int totalMasteryRate) {
		this.totalMasteryRate = totalMasteryRate;
	}


	public int getTotalAccuracyRate() {
		return totalAccuracyRate;
	}


	public void setTotalAccuracyRate(int totalAccuracyRate) {
		this.totalAccuracyRate = totalAccuracyRate;
	}


	public int getLearningProcessRate() {
		return learningProcessRate;
	}


	public void setLearningProcessRate(int learningProcessRate) {
		this.learningProcessRate = learningProcessRate;
	}


	public Integer getTotalTrainingTime() {
		return totalTrainingTime;
	}


	public void setTotalTrainingTime(Integer totalTrainingTime) {
		this.totalTrainingTime = totalTrainingTime;
	}


	public long getCurrentNodeInstanceId() {
		return currentNodeInstanceId;
	}


	public void setCurrentNodeInstanceId(long currentNodeInstanceId) {
		this.currentNodeInstanceId = currentNodeInstanceId;
	}


	public Float getTotalScore() {
		return totalScore;
	}


	public void setTotalScore(Float totalScore) {
		this.totalScore = totalScore;
	}


	public Integer getSumfinishedItems() {
		return sumfinishedItems;
	}


	public void setSumfinishedItems(Integer sumfinishedItems) {
		this.sumfinishedItems = sumfinishedItems;
	}


	public List<PracticeListVO> getPracticeList() {
		return practiceList;
	}


	public void setPracticeList(List<PracticeListVO> practiceList) {
		this.practiceList = practiceList;
	}


//	public List<NodeInstanceInfoVO> getNodeInstanceInfoList() {
//		return nodeInstanceInfoList;
//	}


//	public void setNodeInstanceInfoList(
//			List<NodeInstanceInfoVO> nodeInstanceInfoList) {
//		this.nodeInstanceInfoList = nodeInstanceInfoList;
//	}


	public int getCurrentTestingNum() {
		return currentTestingNum;
	}


	public void setCurrentTestingNum(int currentTestingNum) {
		this.currentTestingNum = currentTestingNum;
	}


	public int getCurrentPassNum() {
		return currentPassNum;
	}


	public void setCurrentPassNum(int currentPassNum) {
		this.currentPassNum = currentPassNum;
	}


	public int getProcessStatus() {
		return processStatus;
	}


	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}


	public int getTotalMasteryRateOrder() {
		return totalMasteryRateOrder;
	}


	public void setTotalMasteryRateOrder(int totalMasteryRateOrder) {
		this.totalMasteryRateOrder = totalMasteryRateOrder;
	}


	public int getNodeListDivWidth() {
		return nodeListDivWidth;
	}


	public void setNodeListDivWidth(int nodeListDivWidth) {
		this.nodeListDivWidth = nodeListDivWidth;
	}


	public List<NodeInstanceInfoVO> getNodeInstanceInfoList() {
		return nodeInstanceInfoList;
	}


	public void setNodeInstanceInfoList(
			List<NodeInstanceInfoVO> nodeInstanceInfoList) {
		this.nodeInstanceInfoList = nodeInstanceInfoList;
	}


	public String getPauseNodeIds() {
		return pauseNodeIds;
	}


	public void setPauseNodeIds(String pauseNodeIds) {
		this.pauseNodeIds = pauseNodeIds;
	}


	public int getLearningProcessRateOrder() {
		return learningProcessRateOrder;
	}


	public void setLearningProcessRateOrder(int learningProcessRateOrder) {
		this.learningProcessRateOrder = learningProcessRateOrder;
	}


	public String getProcessName() {
		return processName;
	}


	public void setProcessName(String processName) {
		this.processName = processName;
	}


	public long getProcessDefinitionId() {
		return processDefinitionId;
	}


	public void setProcessDefinitionId(long processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}


	public List<ShowNodeGroupVO> getShowNodeGroupVOList() {
		return showNodeGroupVOList;
	}


	public void setShowNodeGroupVOList(List<ShowNodeGroupVO> showNodeGroupVOList) {
		this.showNodeGroupVOList = showNodeGroupVOList;
	}


	public Integer getUnsureMarkItems() {
		return unsureMarkItems;
	}


	public void setUnsureMarkItems(Integer unsureMarkItems) {
		this.unsureMarkItems = unsureMarkItems;
	}


	public Date getFirstTrainingTime() {
		return firstTrainingTime;
	}


	public void setFirstTrainingTime(Date firstTrainingTime) {
		this.firstTrainingTime = firstTrainingTime;
	}


/*	public String getProjectVersion() {
		return projectVersion;
	}


	public void setProjectVersion(String projectVersion) {
		this.projectVersion = projectVersion;
	}
*/

	public ProcessPolicy getProcessPolicy() {
		return processPolicy;
	}


	public void setProcessPolicy(ProcessPolicy processPolicy) {
		this.processPolicy = processPolicy;
	}


	public int getTotalAccuracyRateOrder() {
		return totalAccuracyRateOrder;
	}


	public void setTotalAccuracyRateOrder(int totalAccuracyRateOrder) {
		this.totalAccuracyRateOrder = totalAccuracyRateOrder;
	}


	public Date getCustomerExamTime() {
		return customerExamTime;
	}


	public void setCustomerExamTime(Date customerExamTime) {
		this.customerExamTime = customerExamTime;
	}


	public String getLogo() {
		return Logo;
	}


	public void setLogo(String logo) {
		Logo = logo;
	}


	public String getProcessCategoryName() {
		return processCategoryName;
	}


	public void setProcessCategoryName(String processCategoryName) {
		this.processCategoryName = processCategoryName;
	}


}
