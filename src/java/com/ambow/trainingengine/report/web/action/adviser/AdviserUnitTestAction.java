package com.ambow.trainingengine.report.web.action.adviser;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.adviser.AdviserService;

@SuppressWarnings("serial")
public class AdviserUnitTestAction extends AdviserBaseAction {
	
	protected AdviserService adviserService;

	@Override
	public String execute() {
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());
		List<Map> unitList = this.adviserService.getUnitTestReport(this.getClassCode());
		setRequestAttribute("unitList", unitList);
		return SUCCESS;
	}

	public AdviserService getAdviserService() {
		return adviserService;
	}

	public void setAdviserService(AdviserService adviserService) {
		this.adviserService = adviserService;
	}

}
