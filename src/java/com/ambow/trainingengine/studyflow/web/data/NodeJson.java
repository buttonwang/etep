package com.ambow.trainingengine.studyflow.web.data;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;

public class NodeJson {
 
	public static String getNodeJson(HibernateGenericDao dao, Long processId) {
		if (processId != null&&dao!=null) {
			List<Node> lst = dao.find("from Node where processDefinition.id = ?", processId);			
		 
			if(lst!=null&&lst.size()>0){
				StringBuffer  sb = new StringBuffer();
				int size = lst.size();
				for (int i = 0; i < size; i++) {
					Node n =(Node)lst.get(i);
					long id =  n.getId();
					long pid = -1;
					if (n.getNodeGroup() != null) {
						 pid= n .getNodeGroup().getId();
					}
					if(i!=0){
						sb.append(","); 
					}
					sb.append(String.format("{'id':'%s','pid':'%s','obj':{'id':'%s','name':'%s'}}", id,pid,id,n.getName()));
				}
				return "["+sb.toString()+"]" ;
			}
		}
		return "";
	}
	public static String getNodeJsonForPhaseTestlicy(HibernateGenericDao dao, Long processId) {
		if (processId != null&&dao!=null) {
			List<Node> lst = dao.find("from Node where processDefinition.id = ?", processId);			
		 
			if(lst!=null&&lst.size()>0){
				StringBuffer  sb = new StringBuffer();
				int size = lst.size();
				for (int i = 0; i < size; i++) {
					Node n =(Node)lst.get(i);
					long id =  n.getId();
					long pid = -1;
					if (n.getNodeGroup() != null) {
						 pid= n .getNodeGroup().getId();
						 if(!(n instanceof NodeGroup)){
							 n.setName(n.getNodeGroup().getName()+"/"+n.getName());
						 }
					}
					if(i!=0){
						sb.append(","); 
					}
					sb.append(String.format("{'id':'%s','pid':'%s','obj':{'id':'%s','name':'%s'}}", id,pid,id,n.getName()));
				}
				return "["+sb.toString()+"]" ;
			}
		}
		return "";
	}
}
