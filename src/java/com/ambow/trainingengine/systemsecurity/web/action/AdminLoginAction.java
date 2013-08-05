package com.ambow.trainingengine.systemsecurity.web.action;
/**
 * AdminLoginAction.java
 * 
 * Created on 2008-8-1 上午11:15:11
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
/**
 * 系统安全控制层用户登陆控制类
 */
import static com.ambow.trainingengine.systemsecurity.data.InfoFinalVar.LOGIN_INFO_VERIFYCODE;
import static com.ambow.trainingengine.util.SessionDict.AdminUser;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ambow.core.exception.BusinessException;
import com.ambow.studyflow.common.ServiceLocator;
import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade;
import com.ambow.trainingengine.itembank.web.data.UserSubjectGradeRole;
import com.ambow.trainingengine.systemsecurity.domain.SysFunction;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;
import com.ambow.trainingengine.systemsecurity.service.FunctionManageService;
import com.ambow.trainingengine.systemsecurity.service.RoleManageService;
import com.ambow.trainingengine.systemsecurity.service.RoleSbujectGradeManagerService;
import com.ambow.trainingengine.util.JSTree_UserSubjectGradeRole;

@SuppressWarnings("serial")
public class AdminLoginAction extends AdminBaseAction {
	/**
	 * 验证码
	 */
	private String verifycode;
	private RoleSbujectGradeManagerService roleSbujectGradeManagerService;
	/**
	 * 控制用户登陆`
	 */
	@SuppressWarnings("unchecked")
	public String execute() {
		try {
			String tempcode=(String)this.getSession().get("rand");//得到验证码信息
			if(verifycode==null||!verifycode.equals(tempcode)){
				throw new BusinessException(LOGIN_INFO_VERIFYCODE);//验证码错误
			}
			super.sysUser = this.getAdminManageService().login(super.sysUser);//登陆用户信息
			this.getSession().put(AdminUser, super.sysUser);//存储登陆者信息
			HttpServletRequest request = getHttpServletRequest();
			HttpServletResponse response = getHttpServletResponse();
			/**
			 * 将登陆信息设置到缓存中
			 */
			int maxAges = (365 * 24 * 60 * 60);
			Cookie cookie = new Cookie("username", ""
					+ new java.util.Date().getTime());
			cookie.setPath(request.getContextPath());
			cookie.setMaxAge(maxAges);
			try {
				cookie.setValue(super.sysUser.getUsername());
			} catch (Exception e) {
				cookie.setValue("");
			}
			
			boolean toRevise = false;//是否具有试题审校权限，并且有至少一门学校的权限（须先符合只有试题审校权限）//AUTHOR: L.赵亚
			try{
				List<RoleSubjectGrade> userRSGList = roleSbujectGradeManagerService.getOrSetRoleSubejctGrade(sysUser.getSysRole(),null);
				
				String userSubjectGradeRoleJSTreeJSon = 
					JSTree_UserSubjectGradeRole.
						getUserSubjectGradeRoleJSTreeJSon (roleSbujectGradeManagerService.getUserSubjectGradeRole(sysUser) );
				List<UserSubjectGradeRole> usgrList = roleSbujectGradeManagerService.getUserSubjectGradeRole(sysUser);
				List userSubject = roleSbujectGradeManagerService.userSubject(userRSGList);
				List allGrade = roleSbujectGradeManagerService.getAllGrade();
				List allSubject = roleSbujectGradeManagerService.getAllSubject();
				setHttpSessionObj("m_userSubjectGradeRole_json", userSubjectGradeRoleJSTreeJSon);
				setHttpSessionObj("m_userSubjectGradeRole", usgrList);
				setHttpSessionObj("m_subjects"  , userSubject);
				setHttpSessionObj("m_RSGList"  ,userRSGList);
				setHttpSessionObj("gradeAll", allGrade);
				setHttpSessionObj("subjectAll", allSubject);
				
				/********************试题审校专有权限的用户判断*********AUTHOR: L.赵亚************START***********/
				List<SysRole> roleList=((RoleManageService)ServiceLocator.getService("roleManageService")).getSysRoleList(super.sysUser.getId());
				if(roleList!=null&&roleList.size()==1){
					SysRole sysr = roleList.get(0);
					List<SysFunction> funList=((FunctionManageService)ServiceLocator.getService("functionManageService")).getSysFunctionList(sysr.getId());
					if(funList!=null&&funList.size()==1){//当单个人只有单科的审校权限时，放开此注释//AUTHOR: L.赵亚
						for(int i=0; i<funList.size(); i++){
							SysFunction sysFun = funList.get(i);
							if ("/admin/itembank/itemRevise!toQuery.jhtml".startsWith(sysFun.getPath())&&sysFun.getStatus()==0) {
								//当是审校功能,并且功能是启用的。
//								if(userSubject!=null&&userSubject.size()==1)//当单个人只有单科的审校权限时，放开此注释//AUTHOR: L.赵亚
								toRevise = true;
								break;
							}
						}
					}
				}
				/********************试题审校专有权限的用户判断*********AUTHOR: L.赵亚************END***********/
			}catch(Exception e){
				e.printStackTrace();
			}
			
			response.addCookie(cookie);
			
			if(toRevise){//若用户仅有试题审校权限，则在此做跳转//AUTHOR: L.赵亚
				this.setSessionObj("teacherLogin", "yes");
				return "itemRevise";
			}
			return SUCCESS;
		} catch (BusinessException e) {//用户信息错误
			this.setRequestAttribute("username", super.sysUser.getUsername());
			this.setRequestAttribute("isNameExist", e.getMessage());
			return INPUT;
		}
	}

	public void setUsername(String username) {
		super.sysUser.setUsername(username);
	}

	public void setPassword(String password) {
		super.sysUser.setPassword(password);
	}
	
	public String getVerifycode() {
		return verifycode;
	}

	public void setVerifycode(String verifycode) {
		this.verifycode = verifycode;
	}

	public RoleSbujectGradeManagerService getRoleSbujectGradeManagerService() {
		return roleSbujectGradeManagerService;
	}

	public void setRoleSbujectGradeManagerService(
			RoleSbujectGradeManagerService roleSbujectGradeManagerService) {
		this.roleSbujectGradeManagerService = roleSbujectGradeManagerService;
	}
	
}
