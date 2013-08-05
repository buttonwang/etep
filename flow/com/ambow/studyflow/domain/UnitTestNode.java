package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.ambow.studyflow.dto.NodeDTO.NodeType;

/*
 * UnitTestNode.java
 * 
 * Created on Jul 9, 2008 4:47:27 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@DiscriminatorValue("UNITTEST")
public class UnitTestNode extends Node{
	@Override
	@Transient
	public NodeType getNodeType() {
		return NodeType.UNITTEST;
	}
}
