/* 
 * UrlUtils.java
 * 
 * Created on 2007-4-11
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: UrlUtils.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/12 05:08:46  pengqing
 * URL ��Ӧ��util ��
 *
 *
 */
package com.ambow.core.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

	/**
	 * 得到web程序路径
	 * 
	 * @param httpRequest
	 * @return
	 */
	public static String getWebPath(HttpServletRequest httpRequest) {
		String scheme = httpRequest.getScheme();
		String serverName = httpRequest.getServerName();
		int serverPort = httpRequest.getLocalPort();
		String contextPath = httpRequest.getContextPath();
		return buildFullRequestUrl(scheme, serverName, serverPort, contextPath);
	}

	/**
	 * 根据传入的reqeust 获得一个全部的请求路径
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @return
	 */
	public static String getFullRequestUrl(HttpServletRequest request) {
		HttpServletRequest r = request;

		return buildFullRequestUrl(r.getScheme(), r.getServerName(), r
				.getServerPort(), r.getContextPath(), r.getRequestURL()
				.toString(), r.getServletPath(), r.getRequestURI(), r
				.getPathInfo(), r.getQueryString());
	}

	/**
	 * 根据传入的request 构建一个相对的请求路径<br>
	 * 和 {@link #getFullRequestUrl(HttpServletRequest)} 相比，少了前面的url 路径
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestUrl(HttpServletRequest request) {
		HttpServletRequest r = request;

		return buildRequestUrl(r.getServletPath(), r.getRequestURI(), r
				.getContextPath(), r.getPathInfo(), r.getQueryString());
	}

	private static String buildFullRequestUrl(String scheme, String serverName,
			int serverPort, String contextPath, String requestUrl,
			String servletPath, String requestURI, String pathInfo,
			String queryString) {
		boolean includePort = true;

		if ("http".equals(scheme.toLowerCase()) && (serverPort == 80)) {
			includePort = false;
		}

		if ("https".equals(scheme.toLowerCase()) && (serverPort == 443)) {
			includePort = false;
		}

		return scheme
				+ "://"
				+ serverName
				+ ((includePort) ? (":" + serverPort) : "")
				+ contextPath
				+ buildRequestUrl(servletPath, requestURI, contextPath,
						pathInfo, queryString);
	}

	private static String buildFullRequestUrl(String scheme, String serverName,
			int serverPort, String contextPath) {
		boolean includePort = true;

		if ("http".equals(scheme.toLowerCase()) && (serverPort == 80)) {
			includePort = false;
		}

		if ("https".equals(scheme.toLowerCase()) && (serverPort == 443)) {
			includePort = false;
		}

		return scheme + "://" + serverName
				+ ((includePort) ? (":" + serverPort) : "") + contextPath;

	}

	/**
	 * Obtains the web application-specific fragment of the URL.
	 * 
	 * @return the URL, excluding any server name, context path or servlet path
	 */
	private static String buildRequestUrl(String servletPath,
			String requestURI, String contextPath, String pathInfo,
			String queryString) {
		String uri = servletPath;

		if (uri == null) {
			uri = requestURI;
			uri = uri.substring(contextPath.length());
		}

		return uri + ((pathInfo == null) ? "" : pathInfo)
				+ ((queryString == null) ? "" : ("?" + queryString));
	}
	
	/**
	 * 判断此请求是否是Multipart 格式的，如果是，需要做特殊处理才能得到参数
	 * 
	 * @param request
	 * @return
	 * 
	 * @author Schweigen
	 */
	public static boolean isMultipart(HttpServletRequest request) {
		String content_type = request.getHeader("Content-Type");

		return ((content_type == null) || !content_type
				.startsWith("multipart/form-data")) ? false : true;
	}


}
