package com.ambow.trainingengine.itembank.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Grade entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@Entity
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
@Table(name = "role_subject_grade")
public class RoleSubjectGrade implements java.io.Serializable {

	RoleSubjectGrade_pk rsg_pk;

	// 必须重新定义equals()与hashCode()

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (!(obj instanceof RoleSubjectGrade)) {
			return false;
		}

		RoleSubjectGrade roleSubjectGrade = (RoleSubjectGrade) obj;

		return new EqualsBuilder()
		.append(this.rsg_pk, roleSubjectGrade.getRsg_pk())
		.isEquals();
	}

	public int hashCode() {
		return rsg_pk.hashCode();
	}
	
	@EmbeddedId
	public RoleSubjectGrade_pk getRsg_pk() {
		return rsg_pk;
	}

	public void setRsg_pk(RoleSubjectGrade_pk rsg_pk) {
		this.rsg_pk = rsg_pk;
	}
}
