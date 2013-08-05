/**
 * 
 */
package com.ambow.studyflow.common;

import java.util.HashMap;
import java.util.Map;

import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.PracticeNode;

/**
 * 节点类型管理
 * 
 * @author suxiaoyong
 * 
 */
public class NodeTypes {

	public static Map<String,Class> nodeTypeMap =new HashMap();
	
	static
	{
		nodeTypeMap.put("node", Node.class);
		nodeTypeMap.put("practice-node", PracticeNode.class);
		nodeTypeMap.put("exam-node", ExamNode.class);
	}

	public static Class getNodeType(String nodeName) {
		return nodeTypeMap.get(nodeName);
	}

	public static Map getNodeTypeMap() {
		return nodeTypeMap;
	}

	public static void setNodeTypeMap(Map nodeTypeMap) {
		NodeTypes.nodeTypeMap = nodeTypeMap;
	}

	
}
