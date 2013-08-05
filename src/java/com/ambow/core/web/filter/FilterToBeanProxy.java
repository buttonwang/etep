/* 
 * FilterToBeanProxy.java
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
 * $Log: FilterToBeanProxy.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ∆¿≤‚øº—–œÓƒø
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

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * ÂØπ{@link FilterChainProxy} ÁöÑÂ∞ÅË£Ö<br>
 * Âú®Servlet ‰∏≠‰ΩøÁî®Ê≠§Á±ª
 * 
 * @author Peng Qing
 *
 */
public class FilterToBeanProxy implements Filter {

	private Filter delegate;

	private FilterConfig filterConfig;

	private boolean initialized = false;

	private boolean servletContainerManaged = false;

	public void destroy() {
		if ((delegate != null) && servletContainerManaged) {
			delegate.destroy();
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!initialized) {
			doInit();
		}

		delegate.doFilter(request, response, chain);
	}

	private synchronized void doInit() throws ServletException {
		if (initialized) {
			// already initialized, so don't re-initialize
			return;
		}

		String targetBean = filterConfig.getInitParameter("targetBean");

		if ("".equals(targetBean)) {
			targetBean = null;
		}

		String lifecycle = filterConfig.getInitParameter("lifecycle");

		if ("servlet-container-managed".equals(lifecycle)) {
			servletContainerManaged = true;
		}

		ApplicationContext ctx = this.getContext(filterConfig);

		String beanName = null;

		if ((targetBean != null) && ctx.containsBean(targetBean)) {
			beanName = targetBean;
		} else if (targetBean != null) {
			throw new ServletException("targetBean '" + targetBean
					+ "' not found in context");
		} else {
			String targetClassString = filterConfig
					.getInitParameter("targetClass");

			if ((targetClassString == null) || "".equals(targetClassString)) {
				throw new ServletException(
						"targetClass or targetBean must be specified");
			}

			Class targetClass;

			try {
				targetClass = Thread.currentThread().getContextClassLoader()
						.loadClass(targetClassString);
			} catch (ClassNotFoundException ex) {
				throw new ServletException("Class of type " + targetClassString
						+ " not found in classloader");
			}

			Map beans = BeanFactoryUtils.beansOfTypeIncludingAncestors(ctx,
					targetClass, true, true);

			if (beans.size() == 0) {
				throw new ServletException(
						"Bean context must contain at least one bean of type "
								+ targetClassString);
								
			}

			beanName = (String) beans.keySet().iterator().next();
		}

		Object object = ctx.getBean(beanName);

		if (!(object instanceof Filter)) {
			throw new ServletException("Bean '" + beanName
					+ "' does not implement javax.servlet.Filter");
		}

		delegate = (Filter) object;

		if (servletContainerManaged) {
			delegate.init(filterConfig);
		}

		// Set initialized to true at the end of the synchronized method, so
		// that invocations of doFilter() before this method has completed will
		// not
		// cause NullPointerException
		initialized = true;
	}

	/**
	 * Allows test cases to override where application context obtained from.
	 * 
	 * @param filterConfig
	 *            which can be used to find the <code>ServletContext</code>
	 * 
	 * @return the Spring application context
	 */
	protected ApplicationContext getContext(FilterConfig filterConfig) {
		return WebApplicationContextUtils
				.getRequiredWebApplicationContext(filterConfig
						.getServletContext());
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;

		String strategy = filterConfig.getInitParameter("init");

		if ((strategy != null) && strategy.toLowerCase().equals("lazy")) {
			return;
		}

		doInit();
	}

}
