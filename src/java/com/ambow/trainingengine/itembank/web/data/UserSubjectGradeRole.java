package com.ambow.trainingengine.itembank.web.data;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;

public class UserSubjectGradeRole implements java.io.Serializable {
	private static final long serialVersionUID = -5354218858285143060L;
	private SysUser sysUser;
	private Subject	subject;
	private List<Grade> grades;
	public SysUser getSysUser() {
		return sysUser;
	}
	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	 
	 
}
