/**
 * 
 */
package com.ambow.studyflow.decision;

import java.util.Map;

import com.ambow.studyflow.common.NodeStatus;
import com.ambow.studyflow.domain.Node;

/*
 * IDecision.java:决策器接口
 * 
 * 决定下一步怎么走,所有决策器需要实现此接口
 * 
 * Created on 2008-5-12
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

public interface IDecision {

	public Node excute(Map map, NodeStatus nodeStatus);
}
