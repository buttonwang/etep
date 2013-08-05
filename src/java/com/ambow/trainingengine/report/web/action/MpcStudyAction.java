/**
 * 
 */
package com.ambow.trainingengine.report.web.action;

import java.util.List;
import java.util.Map;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.report.service.ConsolidateService;
import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.PauseInfoVO;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class MpcStudyAction extends ReportBaseAction {
	
	private ReportService reportService;
	
	private ConsolidateService consolidateService;
	
	protected HibernateGenericDao genService;
	
	//节点实例Id
	private long nodeInstanceId=0;//0为整个流程
	
	private Integer hisId;
	
	private Integer num;

	/**
	 * 回顾复习
	 * @return
	 */
	public String review(){
		if ((hisId != null) && (hisId != 0) ){
			return hisReview();
		}
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, this.nodeInstanceId);
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		List<Map<String,Object>> nodeStatusList = this.getConsolidateService().getNodeStatusList(userData.getProcessInstanceId(), nodeInstance.getNode().getNodeGroup().getId());
		
		PauseInfoVO vo = (PauseInfoVO)this.getSessionObj("pauseInfoVO");
		if(vo == null){
			this.setRequestAttribute("processStatus", 0);
		}else{
			this.setRequestAttribute("processStatus", 1);
		}
		Integer nodeStatus = this.getNodeStatus(nodeStatusList);
		this.setRequestAttribute("nodeStatus", nodeStatus);
		
		CurrentTestStatus currentTestStatus=this.genService.get(CurrentTestStatus.class, nodeInstance.getId());
		if(currentTestStatus==null)
			currentTestStatus=new CurrentTestStatus();
		this.setRequestAttribute("currentTestStatus",currentTestStatus);
		int historyId=this.reportService.gethistoryTestStatusMaxId(nodeInstanceId);
		HistoryTestStatus historyTestStatus=this.genService.get(HistoryTestStatus.class,historyId);
		this.setRequestAttribute("historyTestStatus",historyTestStatus);
		Map<String,Object> allStatMap = this.getConsolidateService().getHistoryAllStat(userData.getProcessInstanceId(),nodeInstance.getNode().getOrderNum());
		this.setRequestAttribute("orderNum", nodeInstance.getNode().getOrderNum());
		this.setRequestAttribute("nodeType", nodeInstance.getNode().getNodeType().name());
		this.setRequestAttribute("allStatMap", allStatMap);
		return "review";
	}
	
	/**
	 * 历史回顾复习
	 * @return
	 */
	public String hisReview(){
		NodeInstance nodeInstance=this.reportService.get(NodeInstance.class, this.nodeInstanceId);
		this.setRequestAttribute("nodeInstance", nodeInstance);
		HistoryTestStatus historyTestStatus=this.genService.get(HistoryTestStatus.class,hisId);
		this.setRequestAttribute("orderNum", nodeInstance.getNode().getOrderNum());
		this.setRequestAttribute("nodeType", nodeInstance.getNode().getNodeType().name());
		this.setRequestAttribute("historyTestStatus", historyTestStatus);
		this.setRequestAttribute("num", num);
		
		return "hisReview";
	}

	/**
	 * 判断节点组下的所有节点状态
	 * @param nodeStatusList
	 * @return
	 */
	private Integer getNodeStatus(List<Map<String,Object>> nodeStatusList){
		for(Map<String,Object> nodeStatusMap : nodeStatusList){
			Integer nodeStatus = (Integer)nodeStatusMap.get("node_status");
			if(nodeStatus != 2 && nodeStatus !=3){
				return 1;
			}
		}
		return 2;
	}
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public ConsolidateService getConsolidateService() {
		return consolidateService;
	}

	public void setConsolidateService(ConsolidateService consolidateService) {
		this.consolidateService = consolidateService;
	}

	public long getNodeInstanceId() {
		return nodeInstanceId;
	}

	public void setNodeInstanceId(long nodeInstanceId) {
		this.nodeInstanceId = nodeInstanceId;
	}
	
	public Integer getHisId() {
		return hisId;
	}

	public void setHisId(Integer hisId) {
		this.hisId = hisId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
}
