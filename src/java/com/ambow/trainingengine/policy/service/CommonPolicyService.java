package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.util.ParamObject;

public class CommonPolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(AssemblingPaperReqTemplateService.class);
	public Map getAction(Integer nodeId ){
		Map map = new HashMap();
		List errorLst = new ArrayList();
		String action = null;
		if(nodeId!=null){
			Node node = get(Node.class,Long.valueOf(nodeId));
			if(node!=null){
				NodeType  nodeType = node.getNodeType();
				switch(nodeType){
				case PRACTICE:
					action = "trainingUnitNodePolicy";
					break;
				case GROUP:
					action = "nodeGroupPolicy";
					
					break;
				case EVALUATE:
					action = "moduleEvaluatingNodePolicy";
					
					break;
				case PHASETEST:
					action = "phaseTestNodePolicy";
					
					map.put("defaultRtype", "showByNodeId");
					
					break;
				case UNITTEST:
					action = "unitTestNodePolicy";
					break;
				/*case NODE:
					
					
					break;
				case EXAM:
					actionUrl = "nodeGroupPolicy.jhtml?atype=show&id="+node.getId() ;
					
					break;*/
				}
			}else{
				errorLst .add("指定的节点不存在或已经被删除");
			}
		}else{
			errorLst .add("没能指定节点");
		}
		map.put("errorLst", errorLst);
		map.put("action",action);
		return map;
	}
}
