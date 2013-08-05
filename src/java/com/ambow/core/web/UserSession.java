/* 
 * UserSession.java
 * 
 * Created on 2007-4-2
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: UserSession.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.2  2007/04/17 05:00:41  pengqing
 * fix bug
 *
 * Revision 1.1  2007/04/10 01:36:04  pengqing
 * ��ʼ��Ŀ������ambow-core
 *
 *
 */
package com.ambow.core.web;

import java.util.HashMap;
import java.util.Map;

/**
 * 用ThreadLocal提供一个存储线程内变量的地方. <p/> 客户端代码可以用静态方法存储和获取线程内变量,不需要依赖于HttpSession.
 * web层的Action可通过此处向business层传入user_id之类的变量
 * 
 * @author Peng Qing
 */
@SuppressWarnings("unchecked")
public class UserSession {
	private static final ThreadLocal sessionMap = new ThreadLocal();

	public static Object get(String attribute) {
		if (sessionMap.get() == null) {
			sessionMap.set(new HashMap());
		}
		Map map = (Map) sessionMap.get();
		return map.get(attribute);
	}

	public static <T> T get(String attribute, Class<T> clazz) {
		return (T) get(attribute);
	}

	public static void set(String attribute, Object value) {
		if (sessionMap.get() == null) {
			sessionMap.set(new HashMap());
		}
		Map map = (Map) sessionMap.get();
		map.put(attribute, value);
	}

	public static void clear() {
		sessionMap.remove();
	}
}
