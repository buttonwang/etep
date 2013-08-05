package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.web.data.HQLObject;
import com.ambow.trainingengine.itembank.web.data.UserSubjectGradeRole;
/**
 * 
 * @author zhujianmin
 *用于生成gradeSubject的条件·
 */
public class UtilForGradeSubjectService  extends HibernateEntityDao{
	
	
	public Page listOrSearch (String queryType ,String grade_code,String subject_code,String queryValue,int pageNo,String classType ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, classType);
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
	
	public String addSubjectCodeRange( List userSubjectGradeRoleList ){
		String str = "";
		if(userSubjectGradeRoleList!=null&&userSubjectGradeRoleList.size()>0 ){
			int i = 0;
			for (Object  userSubjectGradeRole : userSubjectGradeRoleList) {
				if(i++>0){
					str += ",";
				}
				str+="'"+((UserSubjectGradeRole)userSubjectGradeRole).getSubject().getCode()+"'";
			}
		}
		return str;
	}
	
	public static HQLObject getHQLForGradeSubject(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo,String classType){
		HQLObject hqlObject = new HQLObject();
		String hql = "from "+classType+" R where 1=1 ";
		List qList = new ArrayList();
		if (queryValue.equals("")){
		}else if (queryType.equals("1")){
			hql += " and R.code = ? ";
			qList.add(queryValue);
		}else if (queryType.equals("2")){
			hql += " and R.name like ? ";
			qList.add("%"+queryValue+"%");
		}
		
		if("".equals(grade_code)||grade_code==null){			
		}else{
			hql += " and R.grade.code in(?) ";
			qList.add(grade_code);
		}
		
		if("".equals( subject_code )||subject_code==null){			
		}else{
			hql += " and R.subject.code in(?)";
			qList.add(subject_code);
		}
		hqlObject.setHql(hql);
		hqlObject.setQueryValueList(qList);
		return hqlObject;
	}

}
