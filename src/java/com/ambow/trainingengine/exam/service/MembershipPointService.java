package com.ambow.trainingengine.exam.service;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.bug.util.HQLUtil;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.ParamObject;

public class MembershipPointService  extends HibernateGenericDao {
	
	public MembershipPoint userMembershipPoint(ParamObject p){
		String userId = p.get("userId"); 
		MembershipPoint msp = null;
		if(userId!=null&&!"".equals(userId.trim())){
			msp=(MembershipPoint)this.findObjByHql("from MembershipPoint where asfProcessInstance.actor = ?",userId);
		} 
		return msp;
	};
	
	public  List<MembershipPointHistory> userMembershipPointHistoryLst(ParamObject p){
		String userId = p.get("userId");
		if(userId!=null&&!"".equals(userId.trim())){
			return this.find("from MembershipPointHistory where asfProcessInstance.actor = ?",userId);
		}else{
			return new ArrayList(0);
		}
	};

	public Page webUserPage(ParamObject p,int pageNo){ 
		Integer pageSize = Constants.DEFAULT_PAGE_SIZE;
		if(p.getInteger("pageSize")!=null){
			pageSize = p.getInteger("pageSize");
		}
		HQLUtil<Webuser> hqlUtil = new HQLUtil<Webuser>();
		hqlUtil.add("from Webuser where 1=1 ");
		if(p.get("userLoginName")!=null&&!"".equals(p.get("userLoginName").trim())){
			hqlUtil.add(" and loginName like '%"+p.get("userLoginName")+"%'" );
		}
		if(p.get("userRealName")!=null&&!"".equals(p.get("userRealName").trim())){
			hqlUtil.add(" and realName like '%"+p.get("userRealName")+"%'" );
		}
		if(p.get("userId")!=null&&!"".equals(p.get("userId").trim())){
			hqlUtil.add(" and id like '%"+p.get("userId")+"%'" );
		}
		return hqlUtil.queryPage(this, pageNo, pageSize ) ; 
	}
}
