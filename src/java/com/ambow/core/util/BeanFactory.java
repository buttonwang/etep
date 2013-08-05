/* 
 * BeanFactory.java
 * 
 * Created on 2007-12-11
 * 
 * Copyright(C) 2007, by ambow Develope & Research Branch.
 * 
 * Original Author: 孟德俞
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: BeanFactory.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 */
package com.ambow.core.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 
 *
 * @author <a href="mailto:deyuaccp@hotmail.com">mengdeyu</a>
 *
 * @version $Revision: 1.1 $
 *
 * @since 2007-12-11
 */
public class BeanFactory {

public static Logger logger = Logger.getLogger(BeanFactory.class);
	
	private static BeanFactory beanFacotory;
	private static ApplicationContext applicationContext;	
	
	private BeanFactory(ServletContext sc){
		try {
			applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		} 
		catch (Exception exception) {
			logger.error("载入ApplicationContext文件发生错误.",exception);
		}		
	}
	
	public static BeanFactory getInstance(ServletContext sc){
		if(beanFacotory==null){
			beanFacotory = new BeanFactory(sc);
		}
		return beanFacotory;
	}

	/**
	 * 取得BEAN的实例
	 * @param name 实例名称
	 * @return bean对应的对象
	 */
	public Object getBean(String name){
		return applicationContext.getBean(name);
	}
}
