package com.ambow.trainingengine.web.interceptor;

import org.apache.log4j.Logger;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;

/*
 * TimeSpendInterceptor.java
 * 
 * Created on Jul 10, 2008 8:48:14 AM
 * 计算每个Action的执行时间
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class TimeSpendInterceptor implements Interceptor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(this.getClass());

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		long before=System.currentTimeMillis();
		String result=invocation.invoke();
		long after=System.currentTimeMillis();
		long spend=after-before;
		//log.info(" "+invocation.getAction().toString()+" Time Spend:"+spend+" ms");
		//System.out.println(" "+invocation.getAction().toString()+" Time spend:"+spend+" ms");
		return result;
	}

}
