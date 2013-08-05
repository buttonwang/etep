package com.ambow.trainingengine.itembank.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Grade entity.
 * 
 * @author MyEclipse Persistence Tools
 */

 
public class RoleSubjectGrade_pk implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1025887553375659299L;
	// Fields
	private String subjectCode;
	private String gradeCode;
	private int roleId;

	@Column(name = "subject_code", length = 10)
	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	@Column(name = "grade_code", length = 10)
	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	@Column(name = "role_id")
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	// 必须重新定义equals()与hashCode()

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof RoleSubjectGrade)) {

			return false;

		}

		RoleSubjectGrade_pk roleSubjectGrade_pk = (RoleSubjectGrade_pk) obj;

		return new EqualsBuilder()
		.append(this.subjectCode, roleSubjectGrade_pk.getSubjectCode())
		.append(this.gradeCode, roleSubjectGrade_pk.getGradeCode())
		.append(this.roleId,roleSubjectGrade_pk.getRoleId())
		.isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder()
			.append(this.gradeCode)
			.append(this.subjectCode)
			.append(this.roleId)
			.toHashCode();

	}

}
