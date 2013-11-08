package com.ambow.trainingengine.systemsecurity.data;
/**
 * InfoFinalVar.java
 * 
 * Created on 2008-8-1 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
/**
 * 系统权限常量类
 */
public class InfoFinalVar {
	/**
	 * 系统权限所有列表页面的每页记录数
	 */
	public static final int PAGE_SIZE=10;
	/**
	 * 登陆错误提示信息。为了方便国际化
	 */
	public static final String LOGIN_INFO_VERIFYCODE="验证码不匹配";
	public static final String LOGIN_INFO_USERNAME="该用户不存在";
	public static final String LOGIN_INFO_PASSWORD="密码不匹配";
	public static final String LOGIN_INFO_USERSTATUS="对不起,您的帐号已经被禁用.";
	
	public static final String LOGIN_INFO_EXIST_USERNAME="该用户名已存在，请重新选择！";
}
