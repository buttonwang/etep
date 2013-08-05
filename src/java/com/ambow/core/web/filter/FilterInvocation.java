/* 
 * FilterInvocation.java
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
 * $Log: FilterInvocation.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/12 09:58:19  pengqing
 * enhanced filter
 *
 *
 */
package com.ambow.core.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ambow.core.util.UrlUtils;

/**
 * 封装request response filterchain 的类
 * 
 * @author Peng Qing
 *
 */
public class FilterInvocation {
	private FilterChain chain;

	private HttpServletRequest request;

	private HttpServletResponse response;

	public FilterInvocation(ServletRequest request, ServletResponse response,
			FilterChain chain) {
		if ((request == null) || (response == null) || (chain == null)) {
			throw new IllegalArgumentException(
					"Cannot pass null values to constructor");
		}

		if (!(request instanceof HttpServletRequest)) {
			throw new IllegalArgumentException(
					"Can only process HttpServletRequest");
		}

		if (!(response instanceof HttpServletResponse)) {
			throw new IllegalArgumentException(
					"Can only process HttpServletResponse");
		}

		this.request = (HttpServletRequest) request;
		this.response = (HttpServletResponse) response;
		this.chain = chain;
	}

	/**
	 * 获得FilterChain
	 * 
	 * @return
	 */
	public FilterChain getChain() {
		return chain;
	}

	/**
	 * 得到一个完整的request 请求路径<br>
	 * 包括请求的参数
	 * 
	 * @return String
	 */
	public String getFullRequestUrl() {
		return UrlUtils.getFullRequestUrl(this.request);
	}

	public HttpServletRequest getHttpRequest() {
		return (HttpServletRequest) request;
	}

	public HttpServletResponse getHttpResponse() {
		return (HttpServletResponse) response;
	}

	public ServletRequest getRequest() {
		return request;
	}

	/**
	 * 得到带有ServletContext 的请求路径
	 * 
	 * @return String
	 */
	public String getRequestUrl() {
		return UrlUtils.getRequestUrl(this.request);
	}

	public ServletResponse getResponse() {
		return response;
	}

	public String toString() {
		return "FilterInvocation: URL: " + getRequestUrl();
	}

}
