package com.ambow.trainingengine.itembank.service;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Region;

/*
 * RegionService.java
 * 
 * Created on 2008-7-9 下午07:32:53
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class RegionService extends HibernateEntityDao<Region> {

	public Page list(int pageNo) {
		return this.pagedQuery("from Region", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from Region where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from Region R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from Region R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
}
