/*
 * CreateObjTokenInterceptor.java
 *
 * Created on 2007-6-26
 *
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 *
 * Original Author: yaoshunxiang
 * Contributor(s):
 *
 * Changes
 * -------
 * $Log: ImprovedTokenInterceptor.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ÆÀ²â¿¼ÑÐÏîÄ¿
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/06/26 10:35:23  yaoshunxiang
 * *** empty log message ***
 *
 */
package com.ambow.core.web.interceptor;

import java.util.Map;

import com.opensymphony.webwork.interceptor.TokenSessionStoreInterceptor;
import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

@SuppressWarnings("serial")
public class ImprovedTokenInterceptor extends TokenSessionStoreInterceptor {

	private static boolean token = false;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		token = false;
		Map parameters = ActionContext.getContext().getParameters();
		if (parameters.containsKey("webwork.token.name")) {
			token = true;
		}
		if (token) {
			Map session = ActionContext.getContext().getSession();
			synchronized (session) {
				if (!TokenHelper.validToken()) {
					return handleInvalidToken(invocation);
				}
			}
		}
		return handleValidToken(invocation);
	}

}
