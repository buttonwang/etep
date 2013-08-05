package com.ambow.trainingengine.report.web.action.adviser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.adviser.AdviserService;

@SuppressWarnings("serial")
public class AdviserUnitTrainAction extends AdviserBaseAction {
	
	private String orderNum = null;
	
	protected AdviserService adviserService;

	@Override
	public String execute() {
		String orderNum = "";
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());
		if(this.getOrderNum() == null || this.getOrderNum().equals("-1")){
			orderNum = "and INSTR(a.order_num,',')=0";
		}else{
			orderNum = "and INSTR(a.order_num,'"+this.getOrderNum()+",')=1";
		}
		List<Map> unitList = this.getAdviserService().getNodeListByOrderNum(this.getClassCode(), orderNum);
		List<Map> newUnitList = new ArrayList<Map>();
		for(Map unitMap:unitList){
			List dataList = this.adviserService.getUnitTrainReport(this.getClassCode(),(String)unitMap.get("order_num"));
			if(dataList.size()>0){
				Map dataMap = (Map)dataList.get(0);
				unitMap.put("total_count", dataMap.get("total_count"));
				unitMap.put("pass_count", dataMap.get("pass_count"));
				unitMap.put("nopass_count", dataMap.get("nopass_count"));
				unitMap.put("avg_total_time", dataMap.get("avg_total_time"));
				unitMap.put("avg_pass_time", dataMap.get("avg_pass_time"));
				unitMap.put("avg_mastery_rate", dataMap.get("avg_mastery_rate"));
				unitMap.put("avg_star_count", dataMap.get("avg_star_count"));
				newUnitList.add(unitMap);
			}
		}
		
		//List<Map> unitList = this.adviserService.getUnitTrainReport(this.getClassCode(),orderNum);
		setRequestAttribute("unitList", newUnitList);
		return SUCCESS;
	}

	public AdviserService getAdviserService() {
		return adviserService;
	}

	public void setAdviserService(AdviserService adviserService) {
		this.adviserService = adviserService;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
}