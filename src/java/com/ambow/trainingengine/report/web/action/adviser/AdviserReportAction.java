/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.ReportService;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.LoginService;
import com.ambow.trainingengine.web.service.MainPageService;

/**
 * @author yuanjunqi
 *
 */
public class AdviserReportAction extends AdviserBaseAction {

	private String userId = null;
	
	private String nodeId = null;
	
	private ReportService reportService;
	
	private MainPageService mainPageService;

	@Override
	public String execute() {
		setRequestAttribute("userId", this.getUserId());
		setRequestAttribute("nodeId", this.getNodeId());
		Map userMap = this.getWebUserService().getUserDataById(this.getUserId());
		UserDataVO userData = new UserDataVO(
				this.getUserId(),
				(String)userMap.get("login_name"),
				(String)userMap.get("real_name"),
				(String)userMap.get("class_num"),
				(String)userMap.get("module"),
				((Long)userMap.get("process_definition_id")).toString(),
				(String)userMap.get("grade")
				);
		userData.setProcessInstanceId((Long)userMap.get("process_instance_id"));
		this.getMainPageService().getCurrentNodeInstanceInfoVO(userData);
		
		this.setRequestAttribute("userDataVO", userData);

		List<NodeVO> nodeList=this.reportService.getNodeListForTree(userData);

		this.setRequestAttribute("nodeList",nodeList);
		return SUCCESS;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}
}
