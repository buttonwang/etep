package com.ambow.studyflow.domain;

import java.io.Serializable;

/**
 * is a common supertype for a ProcessDefinition and a SuperState.
 */
public interface NodeCollection extends Serializable {
	/**
	 * adds the given node to this node-collection.
	 * 
	 * @return the added node.
	 * @throws IllegalArgumentException
	 *             if node is null.
	 */
	Node addNode(Node node);
}
