/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class AdviserTopAction extends AdviserBaseAction {

	@Override
	public String execute() {
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());

		// 取班级学生
		Map map = this.webUserService.getUserCountByClassCode(this.getClassCode());
		if(map == null){
			map = new HashMap();
			map.put("amount", 0);
			map.put("name", "");
		}
		setRequestAttribute("user", map);
		return SUCCESS;
	}

}
