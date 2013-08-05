package com.ambow.studyflow.dto;

import java.io.Serializable;

/**
 * 
 * NodeDTOCollection.java:本类功能简介
 * 
 * Created on 2008-5-21
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
public interface NodeDTOCollection extends Serializable {
	/**
	 * adds the given node to this node-collection.
	 * 
	 * @return the added node.
	 * @throws IllegalArgumentException
	 *             if node is null.
	 */
	NodeDTO addNode(NodeDTO node);
}
