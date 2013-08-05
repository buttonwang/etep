/* 
 * FilterChainProxy.java
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
 * $Log: FilterChainProxy.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.2  2007/04/25 02:10:12  pengqing
 * trival changes
 *
 * Revision 1.1  2007/04/12 09:58:19  pengqing
 * enhanced filter
 *
 *
 */
package com.ambow.core.web.filter;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

public class FilterChainProxy implements Filter, InitializingBean,
		ApplicationContextAware {
	private static final Logger logger = Logger
			.getLogger(FilterChainProxy.class);

	public static final String TOKEN_NONE = "#NONE#";

	/**
	 * spring 的ApplicationContext
	 */
	private ApplicationContext applicationContext;

	/**
	 * 配置文件中定义的URL 路径对应的Filter 配置<br>
	 * 在配置文件中，是以String 的形式配置的，最后通过Ant 路径匹配，获得相应的Filter 过滤顺序
	 */
	private FilterDefinitionMap filterDefinitionMap;

	/**
	 * 验证Filter 定义是否正确
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(filterDefinitionMap,
				"filterDefinitionMap must be specified");
	}

	/**
	 * 获得所有在filter chain 中定义的filter, 并且调用其destroy 方法
	 */
	public void destroy() {
		Filter[] filters = getAllDefinedFilters();

		for (Filter filter : filters) {
			if (filter != null) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("Destroying Filter defined in ApplicationContext: '"
									+ filter.toString() + "'");
				}

				filter.destroy();
			}
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);

		// 获得该请求的URL 下，所定义的Filter
		FilterDefinition fd = this.filterDefinitionMap.getFilterDefinition(fi);

		if (fd == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(fi.getRequestUrl() + " has no matching filters");
			}

			// 如果配置文件里，没有匹配该URL 的设置，那么直接往下执行
			chain.doFilter(request, response);
			return;
		}

		Filter[] filters = getDefinedFilters(fd);

		if (filters.length == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(fi.getRequestUrl() + " has an empty filter list");
			}

			chain.doFilter(request, response);

			return;
		}

		VirtualFilterChain virtualFilterChain = new VirtualFilterChain(fi,
				filters);
		virtualFilterChain.doFilter(fi.getRequest(), fi.getResponse());
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		Filter[] filters = getAllDefinedFilters();

		for (int i = 0; i < filters.length; i++) {
			if (filters[i] != null) {
				if (logger.isDebugEnabled()) {
					logger
							.debug("Initializing Filter defined in ApplicationContext: '"
									+ filters[i].toString() + "'");
				}

				filters[i].init(filterConfig);
			}
		}
	}

	private Filter[] getAllDefinedFilters() {
		List<FilterDefinition> filterDefinitionList = this.filterDefinitionMap
				.getFilterDefinitions();
		Set<Filter> list = new LinkedHashSet<Filter>();

		for (FilterDefinition fd : filterDefinitionList) {
			Filter[] filters = getDefinedFilters(fd);

			for (int i = 0; i < filters.length; i++) {
				list.add(filters[i]);
			}
		}

		return (Filter[]) list.toArray(new Filter[0]);
	}

	/**
	 * 通过获得的Filter 定义，得到所需要处理的Filter 列表
	 * 
	 * @return Filter[]
	 */
	private Filter[] getDefinedFilters(FilterDefinition filterDefinition) {
		List<Filter> list = new Vector<Filter>();
		List<String> filters = filterDefinition.getDefinedFilters();

		for (String filterName : filters) {

			if (filterName == null) {
				throw new IllegalArgumentException(
						"Configuration attribute: '"
								+ filterName
								+ "' returned null to the getAttribute() method, which is invalid when used with FilterChainProxy");
			}

			if (!filterName.equals(TOKEN_NONE)) {
				list.add((Filter) this.applicationContext.getBean(filterName,
						Filter.class));
			}
		}

		return (Filter[]) list.toArray(new Filter[list.size()]);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public FilterDefinitionMap getFilterDefinitionMap() {
		return filterDefinitionMap;
	}

	public void setFilterDefinitionMap(FilterDefinitionMap filterDefinitionMap) {
		this.filterDefinitionMap = filterDefinitionMap;
	}

	/**
	 * A <code>FilterChain</code> that records whether or not {@link
	 * FilterChain#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse)} is called.
	 * <p>
	 * This <code>FilterChain</code> is used by <code>FilterChainProxy</code>
	 * to determine if the next <code>Filter</code> should be called or not.
	 * </p>
	 */
	private static class VirtualFilterChain implements FilterChain {
		private FilterInvocation fi;

		private Filter[] additionalFilters;

		private int currentPosition = 0;

		public VirtualFilterChain(FilterInvocation filterInvocation,
				Filter[] additionalFilters) {
			this.fi = filterInvocation;
			this.additionalFilters = additionalFilters;
		}

		private VirtualFilterChain() {
		}

		public void doFilter(ServletRequest request, ServletResponse response)
				throws IOException, ServletException {
			if (currentPosition == additionalFilters.length) {
				if (logger.isDebugEnabled()) {
					logger
							.debug(fi.getRequestUrl()
									+ " reached end of additional filter chain; proceeding with original chain");
				}

				fi.getChain().doFilter(request, response);
			} else {
				currentPosition++;

				if (logger.isDebugEnabled()) {
					logger.debug(fi.getRequestUrl() + " at position "
							+ currentPosition + " of "
							+ additionalFilters.length
							+ " in additional filter chain; firing Filter: '"
							+ additionalFilters[currentPosition - 1] + "'");
				}

				additionalFilters[currentPosition - 1].doFilter(request,
						response, this);
			}
		}
	}

}
