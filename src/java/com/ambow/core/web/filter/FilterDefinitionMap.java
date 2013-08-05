/* 
 * FilterDefinitionMap.java
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
 * $Log: FilterDefinitionMap.java,v $
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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class FilterDefinitionMap {
	
	private boolean ignoreCase = true;
	
	private Map<String, FilterDefinition> urlMap = new Hashtable<String, FilterDefinition>();
	
	private PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 得到所有的URL Filter 定义
	 * 
	 * @return
	 */
	public List<FilterDefinition> getFilterDefinitions() {
		List<FilterDefinition> definitionList = new ArrayList<FilterDefinition>();
		
		for (Object value : urlMap.values()) {
			FilterDefinition fd = (FilterDefinition) value;
			
			definitionList.add(fd);
		}
		
		return definitionList;
	}

	public FilterDefinition getFilterDefinition(FilterInvocation fi) {
		String url = fi.getRequestUrl();

		return lookupDefinition(url);
	}
	
	/**
	 * 在urlMap 中查找Filter 定义
	 * 
	 * @param url
	 * @return FilterDefinition
	 */
	public FilterDefinition lookupDefinition(String url) {
		int firstQuestionMarkIndex = url.lastIndexOf("?");

		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}

		// 是否忽略大小写
		if (ignoreCase) {
			url = url.toLowerCase();
		}
		
		for (String path : urlMap.keySet()) {
			boolean matched = pathMatcher.match(path, url);
			
			if (matched) {
				return urlMap.get(path);
			}
		}
		
		return null;
	}

	public void setIgnoreCase(boolean b) {
		this.ignoreCase = b;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	public void addSecureUrl(String name, FilterDefinition fd) {
		urlMap.put(name, fd);
		
	}

}
