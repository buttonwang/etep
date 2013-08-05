package com.ambow.trainingengine.web.util;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;

public class RequestSessionApplicationUtil {	
	private static final String Application_knowledgePointList = "knowledgePointList";
	private static final String Application_itemThemeList = "itemThemeList";
	private static final String Application_itemTypeList = "itemTypeList";

	/**
	 * 向application中设置 知识点列表，题材列表，题型列表
	 * 
	 * @param dao 
	 * @param baseAction application 所在的容器
	 * @param re_get_It 如果为真，则重新读取数据
	 */
	public static void init(HibernateGenericDao dao,BaseAction baseAction,boolean re_get_It){
		if(getA(Application_itemTypeList,baseAction)==null||re_get_It){
			setA(Application_itemTypeList,dao.getAll(ItemType.class) , baseAction);
		}
		if(getA(Application_itemThemeList,baseAction)==null||re_get_It){
			setA(Application_itemThemeList,dao.getAll(ItemTheme.class) , baseAction);
		}
		if(getA(Application_knowledgePointList,baseAction)==null||re_get_It){
			setA(Application_knowledgePointList,dao.getAll(KnowledgePoint.class),baseAction);
		}
	}
	public static void setA(String key,Object value,BaseAction baseAction ){
		if(baseAction!=null){
			baseAction.getHttpServletRequest().getSession().getServletContext().setAttribute(key, value);	
		}		
	}

	public static void setS(String key,Object value,BaseAction baseAction ){
		if(baseAction!=null){
			baseAction.getHttpServletRequest().getSession().setAttribute(key, value);	
		}		
	}
	
	public static void setR(String key,Object value,BaseAction baseAction ){
		if(baseAction!=null){
			baseAction.getHttpServletRequest().setAttribute(key, value);	
		}		
	}
	
	public static Object getA(String key,BaseAction baseAction ){
		if(baseAction!=null){
			return baseAction.getHttpServletRequest().getSession().getServletContext().getAttribute(key);	
		}
		return null;
	}

	public static Object getS(String key,BaseAction baseAction ){
		if(baseAction!=null){
			return baseAction.getHttpServletRequest().getSession().getAttribute(key);	
		}
		return null;
	}
	
	public static Object getR(String key,BaseAction baseAction ){
		if(baseAction!=null){
			return baseAction.getHttpServletRequest().getAttribute(key);	
		}
		return null;
	}
	
}
