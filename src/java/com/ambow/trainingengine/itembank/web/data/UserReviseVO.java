package com.ambow.trainingengine.itembank.web.data;

import java.util.ArrayList;
import java.util.List;

import com.ambow.trainingengine.util.UtilAndTool_L;

/********************************************
 * USE: 试题审校信息相对应的用户信息及审校记录时间
 * FOR: 试题审校时，需要得到用户的信息及用户的审校时间信息，本VO用于保存用户的信息及审校时间的信息
 * 
 * AUTHOR: L.赵亚
 * DATE: 2010.03.05.11.40
 * 
 */
public class UserReviseVO {
	private String loginId; //审校人登录id
	private String userName; //审校人姓名
	private List<String> timeList = new ArrayList<String>(); //审校人审校时间
	
	/**************************************
	 * USE: 添加审校时间
	 * PARAM: timeTemp 审校时间
	 * RETURN: ...
	 * FOR: 页面上面会用到审校人及审校人审校题目的时间，本方法用于把审校人的审校时间保存下来
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.05.13.33
	 * 
	 */
	public void add(String timeTemp){
		if(UtilAndTool_L.checkNotNullOrZero(timeTemp))
			this.timeList.add(timeTemp);
	}
	
	/***************************************
	 * USE: 得到审校时间
	 * PARAM: index 审校时间对应的序号
	 * RETURN: 对应序号上的审校时间
	 * FOR: 部分功能要求，需要得到某个人的所有审校时间，本方法用于按对应的序号取得相应的审校时间
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.05.13.35
	 * 
	 */
	public String get(Integer index){
		if(index<0||index>this.size()-1)
			return null;
		return this.timeList.get(index);
	}
	
	/******************************************
	 * USE: 得到本审校人的审校时间个数（也可以理解为审校次数）
	 * PARAM: ...
	 * RETURN: 已保存下来的审校时间的个数
	 * FOR: 得到审校人的审校次数（一般会应用于遍历的时候）
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.05.13.37
	 * 
	 */
	public Integer size(){
		return this.timeList.size();
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
