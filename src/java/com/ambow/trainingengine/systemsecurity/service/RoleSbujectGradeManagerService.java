package com.ambow.trainingengine.systemsecurity.service;
/**
 * 角色的学科权限管理 
 * @author zhujianmin
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade;
import com.ambow.trainingengine.itembank.domain.RoleSubjectGrade_pk;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.web.data.UserSubjectGradeRole;
import com.ambow.trainingengine.systemsecurity.domain.SysRole;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.util.JSONReader;

public class RoleSbujectGradeManagerService extends HibernateEntityDao<SysRole>{
	public List<UserSubjectGradeRole> getUserSubjectGradeRole(SysUser sysUser){
		List<UserSubjectGradeRole> lst = new ArrayList<UserSubjectGradeRole>();
		if(sysUser!=null&& sysUser.getSysRole()!=null&& sysUser.getSysRole().size()>0){
			RoleSubjectGrade s;
			String hql = "select distinct (r.rsg_pk.subjectCode) from  RoleSubjectGrade r where r.rsg_pk.roleId = ?";
			for (SysRole sysRole : sysUser.getSysRole()) {
				Integer roleId = sysRole.getId();
				List subjectCodes = find(hql, roleId);
				if(subjectCodes!=null&&subjectCodes.size()>0){
					for (Object subjectCode : subjectCodes){
						UserSubjectGradeRole userSubjectGradeRole = new UserSubjectGradeRole(); 
						List gradeCodes =  find("select distinct (r.rsg_pk.gradeCode) from  RoleSubjectGrade r where r.rsg_pk.subjectCode = ? and r.rsg_pk.roleId = ?",subjectCode, roleId);
						//.toString().replace(" ", "").replace("[", "").replace(",", "','") .replace("]", "").trim()
						List<Grade> grades = new ArrayList<Grade>();
						if(gradeCodes!=null&&gradeCodes.size()>0){
							for (Object gradeCode : gradeCodes) {
								grades.add((Grade)findObjByHql("from Grade where code =?",gradeCode));
							}
						}
						userSubjectGradeRole.setGrades(grades);
						userSubjectGradeRole.setSubject((Subject)findObjByHql( "from Subject where code = ?",subjectCode));
						lst.add(userSubjectGradeRole);
					}
				}
			}
		}
		return lst;
	};
	public List getAllSubject(){
		return getAll(Subject.class);
	}
	public List getAllGrade(){
		return getAll(Grade.class);
	}
	public List userSubject(List  userRSGList){
		Map map  = new HashMap();
		for (Object roleSubjectGrade : userRSGList) {
			map.put(((RoleSubjectGrade)roleSubjectGrade).getRsg_pk().getSubjectCode(),roleSubjectGrade );					
		}
		List userSubjects =  new ArrayList();
		userSubjects.addAll(map.values() );
		return userSubjects;
	}
	 
	/**
	 * 
	 * 读取权限和设置权限，<BR>
	 * 如果roles为空或为空字符串时则为读取权限<BR>
	 * 如果roles(是一个json字符串,如果存在且不为空是此方法表现为设置权限)<BR>
	 * 
	 * <!--有一个问题是 是由于spring事务配置引起的
	 *	2008-12-02 13:23:37,534 WARN [org.hibernate.util.JDBCExceptionReporter] - <SQL Error: 0, SQLState: S1009>
	 *	2008-12-02 13:23:37,534 ERROR [org.hibernate.util.JDBCExceptionReporter] - <Connection is read-only. Queries leading to data modification are not allowed>
	 * --> 
	 * @return
	 */
	public List getOrSetRoleSubejctGrade(Integer rid,String roles){
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
				excuteHql("delete RoleSubjectGrade r where r.rsg_pk.roleId=?", rid);
				if(rolesList!=null){
					for (RoleSubjectGrade roleSubjectGrade : rolesList) {
						save(roleSubjectGrade);
					}
				}
			}else{
				rolesList = find("from RoleSubjectGrade r where r.rsg_pk.roleId=?", rid) ;
			}
		}
		return rolesList;
		 
	}
	
	public List getOrSetRoleSubejctGrade(List<SysRole> lst,String roles){
		List allRoleSubjectGradeList = new ArrayList();
		for (SysRole sysRole : lst) {
			allRoleSubjectGradeList.addAll(getOrSetRoleSubejctGrade(sysRole.getId(),  roles));
		}
		return allRoleSubjectGradeList;		
	}
	public static String getInCode(Set<Object > test){
		String str = "";
		if(test!=null&&test.size()>0){
			int i = 0;
			for (Object object : test) {
				boolean isGradeOrSubejct = false;
				if(object instanceof Grade){
					str+="'"+((Grade)object).getCode()+"'";
					isGradeOrSubejct=true;
				} 
				if(object instanceof Subject){
					str+="'"+((Subject)object).getCode()+"'";
					isGradeOrSubejct=true;
				}
				if(isGradeOrSubejct&&++i<test.size()){
					str+=",";
				}
			}
		}
		return str;
	}
	
	/**
	 * 获取用户的所能管理的学科
	 * @param sysUser
	 * @return
	 */
	public Set getSubjectSet(SysUser sysUser1 ){
		Set subjectSet = null;
		if(sysUser1!=null){
			SysUser sysUser = get(SysUser.class,sysUser1.getId());
			if(sysUser!=null&&sysUser.getSysRole()!=null){
				List sysRoles = sysUser.getSysRole() ;
				if(sysRoles!=null){
					subjectSet=new HashSet();
					for (Iterator iterator = sysRoles.iterator(); iterator.hasNext();) {
						SysRole sysRole = (SysRole) iterator.next();
						subjectSet.addAll(sysRole.getSubjects());					
					}
				} 
			}
		}
		return subjectSet ;	
	}
	/**
	 * 获取用户的所能管理的年级 
	 * @param sysUser
	 * @return
	 */
	public Set getGradeSet(SysUser sysUser1 ){
		Set gradeSet = null;
		if(sysUser1!=null){
			SysUser sysUser = get(SysUser.class,sysUser1.getId());
			if(sysUser!=null&&sysUser.getSysRole()!=null){
				List<SysRole> sysRoles = sysUser.getSysRole() ;
				if(sysRoles!=null){
					gradeSet=new HashSet();
					for (SysRole sysRole:sysRoles) {
						gradeSet.addAll(sysRole.getGrades());
					}
				} 
			}
		}
		return gradeSet ;
	}
	/**
	 * 给角色增加某学科权限
	 * @param sys
	 * @param subjectCode
	 * @return
	 */
	public void addSubjectToRoles(Integer roleId, String subjectCode) {
		Object subjectInDB = findObjByHql("from Subject where code=? ",subjectCode);
		if (subjectInDB != null) {
			SysRole sysRoleInDB = (SysRole)findObjByHql("from SysRole where id=? ",roleId);
			List subjects = sysRoleInDB.getSubjects();
			if (subjects == null) {
				subjects = new ArrayList();
			}
			subjects.add(subjectInDB);
			sysRoleInDB.setSubjects(subjects);
			save(sysRoleInDB);
		}	 
	}
	
	/**
	 * 移除角色的学科权限
	 * @param sys
	 * @param subjectCode
	 * @return
	 */
	public void removeSubjectFromRoles(Integer roleId , String subjectCode) {
		SysRole sysRoleInDB = (SysRole)findObjByHql("from SysRole where id=? ",roleId);
		List<Subject> subjects = sysRoleInDB.getSubjects();
		if (subjects != null) {
			for (Subject subject: subjects) {
				if (subject.getCode().equals(subjectCode)) {
					subjects.remove(subject);
					break;
				}
			}
		}
		save(sysRoleInDB);	
	}
	
	/**
	 * 给角色增加年级权限
	 * @param sys
	 * @param gradeCode
	 */
	public void addGradeToRoles(Integer roleId, String gradeCode) {
		Object gradeInDB = findObjByHql("from Grade where code=? ",gradeCode);
		if (gradeInDB != null) {
			SysRole sysRoleInDB = (SysRole)findObjByHql("from SysRole where id=? ",roleId);
			List grades = sysRoleInDB.getGrades();
			if (grades == null) {
				grades = new ArrayList();
			}
			grades.add(gradeInDB);
			sysRoleInDB.setGrades(grades);
			save(sysRoleInDB);
		}
	}
	
	/**
	 * 移除角色年级权限
	 * @param sysRoleInDB
	 * @param gradeCode
	 */
	public void removeGradeFromRoles(Integer roleId, String gradeCode) {
		SysRole sysRoleInDB = (SysRole)findObjByHql("from SysRole where id=? ",roleId);
		List<Grade> grades = sysRoleInDB.getGrades();
		if (grades != null) {
			for (Grade grade: grades) {
				if (grade.getCode().equals(gradeCode)) {
					grades.remove(grade);
					break;
				}
			}
		}
		save(sysRoleInDB);	
	}
}
