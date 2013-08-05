package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.List;
	
import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.*;

public class ProcessPolicyService extends HibernateGenericDao{
	/**
	 * 用于更新及增加
	 * @param processPolicy
	 * @return
	 */
	public List save(ProcessPolicy processPolicy){
		List errorList = new ArrayList(0);
		super.save(processPolicy);
		return errorList;
	}

 	public Page list(int pageNo) {
		return this.pagedQuery("from ProcessPolicy", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}

	public ProcessPolicy get(long id) {
		return super.get(ProcessPolicy.class,id);
	}

	public void delete(long id) {
		this.excuteHql("delete from ProcessPolicy where id =" + id );
	}
	
	public void deleteBatch(String ids) {
		if(ids!=null&&ids.trim().equals("")){
			this.excuteHql("delete from ProcessPolicy where id in (" + ids + ")");
		}
	}
}
