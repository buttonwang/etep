package com.ambow.trainingengine.freshword.service;

import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.wordbank.domain.FreshWord;

/**
 * FreshWordService.java
 * 
 * Created on 2008-7-24 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */


public class FreshWordService extends HibernateEntityDao<FreshWord>{
	/**
	 * 当用户学会了的时候将生词删除
	 * @param id 生词id
	 */
	public void deleteBatch(Long id) {
		List<FreshWord> templist=this.list(id);
		this.removeAll(templist);
	}
	
	/**
	 * 查询出用户的某一个指定的生词信息
	 * @param id 生词的id
	 * @return FreshWord
	 */
	public FreshWord findById(String id) {
		return get(FreshWord.class,id);
	}
	
	/**
	 * 查询出所有跟用户有关的生词信息并分页
	 * @param id 用户的id
	 * @param pageNo 第几页
	 * @param pageSize 分页数
	 * @return Page
	 */
	public Page findByUserId(Long id, int pageNo, int pageSize) {
		return this.pagedQuery("from FreshWord f where f.processInstance in (" + id + ")", pageNo, pageSize);
	}
	
	/**
	 * 查询出所有跟用户有关的生词信息
	 * @param id 用户的id
	 * @return List<FreshWord>
	 */
	@SuppressWarnings("unchecked")
	public List<FreshWord> list(Long id){
		return  this.createQuery("from FreshWord f where f.processInstance in (?)", id).list();
	}
	
	/**
	 * 查询出所有跟用户有关的生词信息
	 * @param id 用户的id
	 * @return List<FreshWord>
	 */
	@SuppressWarnings("unchecked")
	public List<FreshWord> list(Long id ,String cond){
		return  this.createQuery("from FreshWord f where  f.id not in("+cond+") and f.processInstance in (?)", id).list();
	}
	
	/**
	 * 查询出所有跟用户有关没有学会的生词信息
	 * @param id 用户的id
	 * @param pageNo 第几页
	 * @param pageSize 分页数
	 * @return Page
	 */
	public Page findByUserId(Long id, String cond, int pageNo, int pageSize) {
		String hql="from FreshWord f where f.processInstance in ('" + id + "')";
		if(cond!=null&&!cond.equals("")){
			hql="from FreshWord f where f.id not in("+cond+") and f.processInstance in (" + id + ")";
		}
		return this.pagedQuery(hql, pageNo, pageSize);
	}
	
}
