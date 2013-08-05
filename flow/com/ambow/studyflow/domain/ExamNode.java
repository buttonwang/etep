package com.ambow.studyflow.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.ambow.studyflow.dto.NodeDTO.NodeType;

@Entity
@org.hibernate.annotations.Entity(
		dynamicInsert = true, dynamicUpdate = true)
@DiscriminatorValue("EXAM")
public class ExamNode extends Node {

	/* (non-Javadoc)
	 * @see com.ambow.studyflow.domain.Node#getNodeType()
	 */
	@Override
	@Transient
	public NodeType getNodeType() {
		// TODO Auto-generated method stub
		return NodeType.EXAM;
	}

}
