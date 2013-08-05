package com.ambow.trainingengine.bug.util;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;

public class HQLUtil<T>{
	StringBuilder hqlSB = new StringBuilder();
	List<Object> paraList = new ArrayList<Object>();
	
	/**
	 * 增加不需要参数的查询条件
	 * @param str
	 */
	public void add(String str){
		if(str!=null){
			hqlSB.append(str);
		}
	}
	/**
	 *  增加带参数的查询条件，如果参数为空，则什么都不增加 
	 * @param queryStr
	 * @param param
	 * @return
	 */
	public boolean add(String queryStr ,Object param){
		if(queryStr!=null&&!"".equals(queryStr.trim())&&param!=null){
			hqlSB.append(queryStr);
			paraList.add(param);
			return true;
		}
		return false;
	}

	public List<T> queryList(HibernateGenericDao dao){
		if(!"".equals(hqlSB.toString().trim()) ){
			return dao.find( hqlSB.toString(),paraList.toArray());
		}
		return new ArrayList<T>(0);
	}
	
	public Page queryPage(HibernateGenericDao dao,int pageNo,int pageSize){
		if(!"".equals(hqlSB.toString().trim()) ){
			return dao.pagedQuery(hqlSB.toString(), pageNo, pageSize,paraList.toArray());
		}
		return new Page();
	}
	public StringBuilder getHqlSB() {
		return hqlSB;
	}
	public void setHqlSB(StringBuilder hqlSB) {
		this.hqlSB = hqlSB;
	}
	public List<Object> getParaList() {
		return paraList;
	}
	public void setParaList(List<Object> paraList) {
		this.paraList = paraList;
	}
}
