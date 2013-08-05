package com.ambow.trainingengine.exam.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.exam.service.MembershipPointService;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.ParamObject;

public class MembershipPointAction extends BaseAction {
	private static final long serialVersionUID = 4670752799461745244L;	 
	private int pageNo =1;
	private MembershipPointService membershipPointService;
	public ParamObject p = new ParamObject();
	
	@Override
	public String execute(){
		MembershipPoint now = membershipPointService.userMembershipPoint( p);
		List<MembershipPointHistory> lst = membershipPointService.userMembershipPointHistoryLst(p);
		setRequestAttribute("userMembershipPointHistoryLst", lst) ;
		setRequestAttribute("userMembershipPoint",now);
		if(p.get("userId")!=null&&!"".equals(p.get("userId").trim())){
			setRequestAttribute("webuser",membershipPointService.get(Webuser.class,p.get("userId")));
		} 
		setRequestAttribute("p",p);
		return "showUserMembershipPoint";
	}
	
	public String userList() {
		setRequestAttribute("page", membershipPointService.webUserPage(p,pageNo));
		setRequestAttribute("p",p);
		return "listWebuser";
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public MembershipPointService getMembershipPointService() {
		return membershipPointService;
	}

	public void setMembershipPointService(
			MembershipPointService membershipPointService) {
		this.membershipPointService = membershipPointService;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
}
