package com.ambow.trainingengine.util;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestAttributeByMap{
	public static void setAttributeByMap(HttpServletRequest request,Map<String, Object> map){
		if(request!=null&& map!=null){
			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
				String key =(String)iterator.next();
				request.setAttribute((String)key, map.get(key));
			}
		}
	}
}
