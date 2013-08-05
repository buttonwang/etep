/* 
 * BaseAction.java
 * 
 * Created on 2007-4-12
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: BaseAction.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * �2⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.4  2007/05/16 07:42:45  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.3  2007/04/27 01:39:55  zhangrui
 * add<code>setRequestAttribute(String,Object)</code>;
 * add<code>getSessionObj(Object)</code>;
 * add<code>setSessionObj(Object,Object)</code>
 *
 * Revision 1.2  2007/04/24 01:51:17  pengqing
 * add getSession method
 *
 * Revision 1.1  2007/04/12 10:08:59  pengqing
 * Webwork Action base
 *
 *
 */
package com.ambow.core.web.action;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ambow.core.util.UrlUtils;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.config.DelegatingConfiguration;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionSupport;

public abstract class BaseAction extends ActionSupport {

	/**
	 * 获得当前Action 所需要的权限认证字段
	 * 
	 * @return AuthStr
	 */
	public abstract String getAuthStr();

	/**
	 * 获得当前Http Servlet Request
	 */
	public HttpServletRequest getHttpServletRequest() {
		return (HttpServletRequest) ActionContext.getContext().get(
				WebWorkStatics.HTTP_REQUEST);
	}
	
	/**
	 * 获得 Http Servlet Response
	 */
	public HttpServletResponse getHttpServletResponse() {
		return (HttpServletResponse) ActionContext.getContext().get(
				WebWorkStatics.HTTP_RESPONSE);
	}

	/**
	 * 获得当前Action 请求路径
	 * 
	 */
	public String getRequestUrl() {
		return UrlUtils.getRequestUrl(getHttpServletRequest());
	}

	/**
	 * 获得当前Action 请求的全部路径
	 */
	public String getFullRequestUrl() {
		return UrlUtils.getFullRequestUrl(getHttpServletRequest());
	}

	/**
	 * 获得当前Session
	 * 
	 */
	public Map getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 单个对象
	 */
	protected Object getParameterValue(String parameter) {
		Object[] parameterArray = getParamenterArray(parameter);
		if (parameterArray != null && parameterArray.length == 1) {
			return parameterArray[0];
		} else {
			return getHttpServletRequest().getAttribute(parameter);
		}
	}

	/**
	 * 根据指定的页面参数名称，获取页面传递来的参数值
	 * 
	 * @param parameter
	 * @return 数组对象
	 */
	protected Object[] getParamenterArray(String parameter) {
		return (Object[]) (ActionContext.getContext().getParameters()
				.get(parameter));
	}

	/**
	 * 获取配置信息，来源为webwork.properties以及webwork.properties中<br>
	 * webwork.custom.properties指定的properties文件<br>
	 * 暂未使用
	 */
	protected String getApplicationProperty(String key) {
		return DelegatingConfiguration.getString(key);
	}
	
	/**
	 * 向request对象添加attribute
	 * 
	 * @param key
	 * @param value
	 */
	public void setRequestAttribute(String key, Object value) {
		getHttpServletRequest().setAttribute(key, value);
	}

	/**
	 * 从session中取得相应的值
	 * 
	 * @param key
	 * @return
	 */
	public Object getSessionObj(Object key) {
		return getSession().get(key);
	}
	
	/**
	 * 获得项目rootDir
	 * 
	 * @return
	 */
	protected String getRootDir() {
		String workDir = getHttpServletRequest().getSession()
				.getServletContext().getRealPath("login.jsp");

		int len = workDir.indexOf("login.jsp");
		workDir = workDir.substring(0, len);

		StringBuffer sb = new StringBuffer();
		sb.append(workDir).append("upload").append(File.separator);

		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public void setSessionObj(Object key,Object value){
		getSession().put(key, value);
	}
	/*
	 * 存HttpSession
	 */
	public void setHttpSessionObj(String key,Object value){
		getHttpServletRequest().getSession().setAttribute(key, value);
	}
	
	/*
	 * 取HttpSession
	 */
	public Object getHttpSessionObj(String key){
		return getHttpServletRequest().getSession().getAttribute(key);
	}
}
