/*
 * CheckLoginInterceptor.java
 *
 * Created on Oct 5, 2007
 *
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 *
 * Original Author: Johnny Yang
 * Contributor(s):
 *
 * Changes
 * -------
 * $Log: CheckLoginInterceptor.java,v $
 * Revision 1.4  2008/02/21 08:07:48  lixin
 * ok
 *
 * Revision 1.3  2008/01/16 13:23:59  lixin
 * dfdf
 *
 * Revision 1.2  2008/01/08 13:43:47  lixin
 * 111
 *
 * Revision 1.1  2007/12/28 08:11:42  lixin
 * OK
 *
 * Revision 1.4  2007/11/11 05:25:40  yangchao
 * *** empty log message ***
 *
 * Revision 1.3  2007/10/11 06:04:25  leilin
 * *** empty log message ***
 *
 * Revision 1.2  2007/10/10 02:58:39  yangchao
 * *** empty log message ***
 *
 * Revision 1.1  2007/10/05 10:13:11  yangchao
 * *** empty log message ***
 *
 *
 */
package com.ambow.trainingengine.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.util.SessionDict;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

@SuppressWarnings("serial")
public class CheckLoginInterceptor implements Interceptor {
	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {
		// arlin 07/10/11 如果是顾问，掠过 
		if (invocation.getProxy().getActionName().indexOf("ad_")==0)
			return invocation.invoke();
		
		Object userObj = invocation.getInvocationContext().getSession().get(SessionDict.WebUser);
		//Object sysUserObj = invocation.getInvocationContext().getSession().get(SessionDict.AdminUser);

		ActionContext actionContext=invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) (Object) actionContext.get(WebWorkStatics.HTTP_REQUEST);
		String url=request.getRequestURL().toString();
		//后台访问掠过，因为后统一使用fliter验证
		if (url.indexOf("admin")>-1) return invocation.invoke();
		//Widget模式访问掠过, 试题校验访问掠过
		if (url.contains("Widget")) return invocation.invoke();
		if (url.contains("revise")) return invocation.invoke();
		//前台验证
		if (userObj == null)
			return "timeout";
		else 
			return invocation.invoke();
		
	}
}
