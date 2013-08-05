package com.ambow.trainingengine.studyflow.util;

import java.util.HashMap;
import java.util.Map;

import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.ExamNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.UnitTestNode;

public class ProcessNodeJson {
	public ProcessNodeJson(){}
	static JSONLeanW jw = new JSONLeanW();
	static{
		Map classProps = new HashMap();
		classProps.put(Node.class.getName(), "id,name,nodeGroup");
		classProps.put(PhaseTestNode.class.getName(), "id,name");
		classProps.put(PracticeNode.class.getName(), "id,name");
		classProps.put(UnitTestNode.class.getName(), "id,name");
		classProps.put(ExamNode.class.getName(), "id,name");
		classProps.put(EvaluateNode.class.getName(), "id,name");
		classProps.put(NodeGroup.class.getName(), "id,name,nodes");
		classProps.put(com.ambow.studyflow.dto.NodeDTO.class.getName(), "id,name");
		classProps.put(com.ambow.trainingengine.itembank.domain.Grade.class.getName(), "code,name");
		classProps.put(com.ambow.trainingengine.itembank.domain.Subject.class.getName(), "code,name");
		classProps.put(com.ambow.trainingengine.systemsecurity.domain.SysUser.class.getName(), "id,username");
		classProps.put(com.ambow.trainingengine.itembank.web.data.UserSubjectGradeRole.class.getName(), "subject,grades");
		classProps.put(com.ambow.trainingengine.itembank.domain.Region.class.getName(), "code,name");
		
		jw.setClassProps(classProps);
	}
	public static String getStr(Object object ){
		if(object!=null){
			return jw.write(object);
		}else{
			return "";
		}
	}
}
