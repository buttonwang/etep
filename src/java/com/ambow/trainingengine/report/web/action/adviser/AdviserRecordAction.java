/**
 * 
 */
package com.ambow.trainingengine.report.web.action.adviser;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.report.service.adviser.UserAnswerService;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class AdviserRecordAction extends AdviserBaseAction {

	private String userId;
	
	private int pageNo;
	
	protected UserAnswerService userAnswerService;
	
	@Override
	public String execute() {
		this.setClassCode((String) this.getParameterValue("ClassCode"));
		setRequestAttribute("ClassCode", this.getClassCode());

		// 取班级学生
		List<Map> list = this.webUserService.getUserListByClassCode(this
				.getClassCode()
				+ "");
		setRequestAttribute("listUser", list);
		
		// 如果没有传入用户编号：返回 返回的是0
		if (getUserId() == null)
			return SUCCESS;
		
		setRequestAttribute("userId", this.getUserId());
		
		// 取学习记录
		int size = 10;
		
		Integer total = this.userAnswerService.getRecordCountByUser(userId);
		setRequestAttribute("total", total);
		
		if (pageNo < 1) {
			pageNo = 1;
		}
		List<Map> recordList = this.userAnswerService.getRecordByUser(pageNo, userId,size);
		if (recordList != null) {
			setRequestAttribute("recordList", recordList);
		}
		
		Integer totalPage = total/size+1;
		setRequestAttribute("totalPage", totalPage);
		setRequestAttribute("pageNo", pageNo);
		
		return SUCCESS;
	}

	public UserAnswerService getUserAnswerService() {
		return userAnswerService;
	}

	public void setUserAnswerService(UserAnswerService userAnswerService) {
		this.userAnswerService = userAnswerService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}