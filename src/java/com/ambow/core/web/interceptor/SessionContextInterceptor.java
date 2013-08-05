/* 
 * SessionContextInterceptor.java
 * 
 * Created on 2007-4-25
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: SessionContextInterceptor.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ÆÀ²â¿¼ÑÐÏîÄ¿
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/25 02:09:46  pengqing
 * the code about user in session
 *
 *
 */
package com.ambow.core.web.interceptor;

import org.apache.log4j.Logger;

import com.ambow.core.web.UserSession;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

import static com.ambow.core.configuration.Constants.USER_IN_SESSION;

@SuppressWarnings("serial")
public class SessionContextInterceptor implements Interceptor {

	private static final Logger log = Logger.getLogger(SessionContextInterceptor.class);
	
	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		Object user = invocation.getInvocationContext().getSession().get(
				USER_IN_SESSION);
		
		if (null == user) {
			return invocation.invoke();
		}
		
		UserSession.set(USER_IN_SESSION, user);
		try {
			String result = invocation.invoke();
			
			return result;
		} catch (Exception e) {
			log.warn(e.getMessage());
			
			throw e;
		} finally {
			UserSession.clear();
		}
	}

}
