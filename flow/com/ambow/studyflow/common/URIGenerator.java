/**
 * 
 */
package com.ambow.studyflow.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/*
 * URIGenerator.java:url生成器
 * 
 * Created on 2008-5-19
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */

public class URIGenerator {
	private String protocol="http";
	private String port="80";
	private String host;
	private String contextPath;
	private String fileName;
	private String encoding="UTF-8";
	private Map<String,Object> queryData;
	
	/**
	 * 渲染最终链接
	 * @return
	 */
	public String render()
	{
		Assert.notNull("主机名不能为空", host);
		String resultUrl;
		StringBuffer sb = new StringBuffer(protocol);
		sb.append("://").append(host);
		if (!port.equals("80"))
			sb.append(":").append(port);
		if (!contextPath.startsWith("/"))
			;
		sb.append("/");
		if (contextPath != null) {
			sb.append(contextPath);
		}
		if (!contextPath.endsWith("/"))
			sb.append("/");
		if (fileName != null) {
			sb.append(fileName);
		}
		if (queryData != null) {
			sb.append("?");
			buildQueryData(sb);
			resultUrl = sb.substring(0, sb.length() - 1);

		} else {
			resultUrl = sb.toString();
		}
		return resultUrl;
	}
	
	/**
	 * 处理query参数
	 * @param sb
	 */
	private void buildQueryData(StringBuffer sb) {
		for (Map.Entry<String, Object> entry : queryData.entrySet()) {
			sb.append(entry.getKey()).append("=").append(
					getEncodingData(entry.getValue().toString())).append("&");
		}
	}
	/**
	 * 转换编码
	 * @param value
	 * @return
	 */
	private String getEncodingData(String value)
	{
		try {
			return URLEncoder.encode(value, encoding);
		} catch (UnsupportedEncodingException e) {
			return value;
		}
	}
	public void addQueryData(String key,Object value)
	{
		if(queryData==null)
			queryData=new HashMap<String,Object>();
		
		queryData.put(key, value);
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public static void main(String args[])
	{
		URIGenerator ug=new URIGenerator();
		ug.setHost("test.com");
		ug.setFileName("test.html");
		ug.setContextPath("ebopo");
		System.out.println(ug.render());
		ug.addQueryData("userid", 1);
		ug.addQueryData("name", "苏小勇");
		ug.addQueryData("threadid", "1111");
		System.out.println(ug.render());
	}

}
