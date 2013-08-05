/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.adviser.AdviserService;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class AdviserStageTestAction extends AdviserBaseAction {
	
	private String nodeType=null;
	
	protected AdviserService adviserService;

	@Override
	public String execute() {
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());
		setRequestAttribute("nodeType", this.getNodeType());
		List<Map> stageList = this.adviserService.getStageTestReport(this.getClassCode(), this.getNodeType());
		setRequestAttribute("stageList", stageList);
		return SUCCESS;
	}
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public AdviserService getAdviserService() {
		return adviserService;
	}

	public void setAdviserService(AdviserService adviserService) {
		this.adviserService = adviserService;
	}

}
