package com.ambow.trainingengine.util;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;

public class JSTreeAmbowUtil {
	public static String getNodesJson(Long processId,HibernateGenericDao dao){
		List<Node> lst =dao.find("from Node where processDefinition.id = ? order by orderNum", processId);
		// Date s = new Date();
		 List<JSTree> nodeJsTreeLst = new ArrayList<JSTree>();
		 for (Node node : lst) {
			 NodeJSTree nodeJsTree = new NodeJSTree(node);
			 nodeJsTreeLst.add(nodeJsTree);
		}
		//Date e = new Date();
		//System.out.println("用时"+(s.getTime()-s.getTime()));
		return nodeJsTreeLst.toString();
	}
	
	public static String getNodeJSTreeJSon(Long processId,HibernateGenericDao dao){
		return getNodesJson(processId,dao).replace("[", "[{'pid_id':'0_-1','obj':{'name':''}},");
	}
}
