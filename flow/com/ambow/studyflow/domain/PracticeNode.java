/**
 * 
 */
package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.ambow.studyflow.dto.NodeDTO.NodeType;

/*
 * PracticeNode.java:本类功能简介
 * 
 * Created on 2008-5-13
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Su Xiaoyong
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@DiscriminatorValue("PRACTICE")
public class PracticeNode extends Node {
	@Override
	@Transient
	public NodeType getNodeType() {
		return NodeType.PRACTICE;
	}
}
