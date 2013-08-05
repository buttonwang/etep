package com.ambow.trainingengine.itembank.web.data;
/**@author zhujianmin
 * 当查询中有多个参数，且参数可能不出现在HQL中时（即位置不定）一种封装
 * 主要是将自己组装的HQL语句及参数列表做一个封装，方便组装与业务逻辑分离
 * 
 */
import java.util.ArrayList;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
public class HQLObject {
	String hql="";
	List<Object> queryValueList = new ArrayList<Object>();
	public String getHql() {
		return hql;
	}
	public void setHql(String hql) {
		this.hql = hql;
	}
	public List<Object> getQueryValueList() {
		return queryValueList;
	}
	public void setQueryValueList(List<Object> coditionList) {
		this.queryValueList = coditionList;
	}
	public static List queryList(HibernateGenericDao dao,HQLObject hqlObject){
		if(hqlObject!=null&&hqlObject.getHql()!=null){
			return  dao.find( hqlObject.hql,hqlObject.queryValueList.toArray());
		}
		return new ArrayList<Object>(0);
	}
	public static Page queryPage(HibernateGenericDao dao,HQLObject hqlObject,int pageNo,int pageSize){
		if(hqlObject!=null&&hqlObject.getHql()!=null){
			return dao.pagedQuery(hqlObject.hql, pageNo, pageSize, hqlObject.getQueryValueList().toArray());	
		}
		return new Page();
	}
	 
	
}
