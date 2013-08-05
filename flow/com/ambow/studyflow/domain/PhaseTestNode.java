package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.ambow.studyflow.dto.NodeDTO.NodeType;

/*
 * PhaseTestNode.java
 * 
 * Created on Jul 9, 2008 4:47:01 PM
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
@DiscriminatorValue("PHASETEST")
public class PhaseTestNode extends Node{
	@Override
	@Transient
	public NodeType getNodeType() {
		return NodeType.PHASETEST;
	}
}
