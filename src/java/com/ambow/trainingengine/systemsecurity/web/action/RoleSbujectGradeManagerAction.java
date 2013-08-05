package com.ambow.trainingengine.systemsecurity.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade;
import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade_pk;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.RoleSbujectGradeManagerService;
import com.ambow.trainingengine.systemsecurity.util.JSONReader;
import com.ambow.trainingengine.web.action.WebBaseAction;

public class RoleSbujectGradeManagerAction extends WebBaseAction {
	private static final long serialVersionUID = 751782968924889176L;
	/**
	 * 用户学科学级权限管理
	 */
	String roles;
	Integer rid;
	RoleSbujectGradeManagerService roleSbujectGradeManagerService;
  
	/**
	 * 读取权限和设置权限
	 */
	@Override
	public String execute() {
		setRequestAttribute("rid",rid);
		//读取权限和设置权限
		List<RoleSubjectGrade> rolesList = null;
		if(rid!=null){
			if (roles != null && !"".equals(roles.trim())) {
				//将传递过来的json格式的权限数据转换为 权限对象列表
				ArrayList<HashMap> data = (ArrayList) new JSONReader().read(roles);
				if (data.size() > 0) {
					rolesList = new ArrayList<RoleSubjectGrade>();
					for (HashMap obj : data) {
						RoleSubjectGrade roleSubjectGrade = new RoleSubjectGrade();
						RoleSubjectGrade_pk rpk = new RoleSubjectGrade_pk();
						rpk.setGradeCode((String) obj.get("gradeCode"));
						rpk.setSubjectCode((String) obj.get("subjectCode"));
						rpk.setRoleId(new Integer((String)obj.get("roleId")));
						roleSubjectGrade.setRsg_pk(rpk);
						rolesList.add(roleSubjectGrade);
					}
				}
			}
			//如果传递了 json格式的权限数据，则先删除原来权限，后设置新权限
			if(rolesList != null||"[]".equals(roles)){
				roleSbujectGradeManagerService.excuteHql("delete RoleSubjectGrade r where r.rsg_pk.roleId=?", rid);
				if(rolesList!=null){
					for (RoleSubjectGrade roleSubjectGrade : rolesList) {
						roleSbujectGradeManagerService.save(roleSubjectGrade);
					}
				}
				setHttpSessionObj("m_userSubjectGradeRole",roleSbujectGradeManagerService.getUserSubjectGradeRole(roleSbujectGradeManagerService.get(SysUser.class, rid)));
				setSessionObj("m_subjects",rolesList != null?roleSbujectGradeManagerService.userSubject(rolesList):new ArrayList() );
				
			}else{
				rolesList = roleSbujectGradeManagerService.find("from RoleSubjectGrade r where r.rsg_pk.roleId=?", rid) ;
			}
		}
		setRequestAttribute("rolesList",rolesList);
		//TODO spring配置注入的事务为只读问题 setRequestAttribute("rolesList",roleSbujectGradeManagerService.getOrSetRoleSubejctGrade(rid,roles));
		setRequestAttribute("gradeAll", roleSbujectGradeManagerService.getAllGrade());
		setRequestAttribute("subjectAll", roleSbujectGradeManagerService.getAllSubject());
		return SUCCESS;
	}
	
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}
	public RoleSbujectGradeManagerService getRoleSbujectGradeManagerService() {
		return roleSbujectGradeManagerService;
	}

	public void setRoleSbujectGradeManagerService(
			RoleSbujectGradeManagerService roleSbujectGradeManagerService) {
		this.roleSbujectGradeManagerService = roleSbujectGradeManagerService;
	}
}
