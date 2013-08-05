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
public class AdviserStuAction extends AdviserBaseAction {
	
	private String unitID;
	
	private String status;
	
	protected AdviserService adviserService;
	
	@Override
	public String execute() {
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());
		setRequestAttribute("unitID", this.getUnitID());
		setRequestAttribute("status", this.getStatus());
		
		List<Map> nodeList = this.adviserService.getNodeList(this.getClassCode());
		setRequestAttribute("nodeList", nodeList);
		String subSql = "";
		if(this.getUnitID() != null && !this.getUnitID().equals("-1")){
			Map nodeMap = this.getAdviserService().getNodeMapById(this.getUnitID());
			//System.out.println(nodeMap.get("node_type"));
			if(nodeMap != null && nodeMap.get("node_type").equals("GROUP")){
				subSql = " where INSTR(e.order_num,'"+nodeMap.get("order_num")+"')=1";
			}else if(nodeMap != null && !nodeMap.get("node_type").equals("GROUP")){
				subSql = " where e.id="+this.getUnitID();
			}
		}
		
		List<Map> stuUserList = this.adviserService.getStuUserListByNodeId(subSql,this.getStatus());
		setRequestAttribute("stuUserList", stuUserList);
		
		return SUCCESS;
	}

	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}

	public AdviserService getAdviserService() {
		return adviserService;
	}

	public void setAdviserService(AdviserService adviserService) {
		this.adviserService = adviserService;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
