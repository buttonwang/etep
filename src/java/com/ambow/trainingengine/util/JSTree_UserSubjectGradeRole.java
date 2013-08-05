package com.ambow.trainingengine.util;

import java.util.ArrayList;
import java.util.List;

import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.web.data.UserSubjectGradeRole;

public class JSTree_UserSubjectGradeRole extends JSTree {
	public JSTree_UserSubjectGradeRole (UserSubjectGradeRole userSubjectGradeRole){
		this.setId( userSubjectGradeRole.getSubject().getCode());
		this.setObj(userSubjectGradeRole);		
	}
	public static String getUserSubjectGradeRoleJSTreeJSon( List<UserSubjectGradeRole>   userSubjectGradeRoleLst ){
		if(userSubjectGradeRoleLst!=null&&userSubjectGradeRoleLst.size()>0){
			List<JSTree> jsTreeLst = new ArrayList<JSTree>();
			for (UserSubjectGradeRole userSubjectGradeRole : userSubjectGradeRoleLst) {
				jsTreeLst.add(new JSTree_UserSubjectGradeRole(userSubjectGradeRole) );
			}
			return jsTreeLst.toString();
		}
		return "[]";
	}
	
	@Override
	public String toString() {
		return String.format(getToString().replaceAll("'" ,"\\\""),objToString()) ;
	}
	class GradeExt extends Grade{
		private static final long serialVersionUID = -1547530363540426172L;
		public GradeExt (Grade grade){
			this.setCode(grade.getCode());
			this.setName(grade.getName());
		}
		@Override
		public String toString(){
			return String.format("{\"c\":\"%s\",\"n\":\"%s\"}",this.getCode(),this.getName());
		}
	}
	@Override
	public String objToString() {
		UserSubjectGradeRole usg = ((UserSubjectGradeRole)this.getObj());
		Subject s = usg.getSubject();
		String gradesStr = "";
		if(usg.getGrades()!=null&&usg.getGrades().size()>0){
			List<GradeExt> gExtLst = new ArrayList<GradeExt>();
			for (Grade g : usg.getGrades()) {
				gExtLst.add(new GradeExt(g));
			}
			gradesStr = gExtLst.toString();
			
		};
		return String.format("{\"subject\":{\"c\":\"%s\",\"n\":\"%s\"},\"grades\":%s}",s.getCode(),s.getName(),gradesStr) ;
	}
	
	public String getNodeType(Node node ){
		if (node.getNodeType().equals(NodeType.GROUP)) {
			return "节点组";
		}
		if (node.getNodeType().equals(NodeType.PHASETEST)) {
			return "阶段测试";
		}
		if (node.getNodeType().equals(NodeType.PRACTICE)) {
			return "训练单元";
		}
		if (node.getNodeType().equals(NodeType.UNITTEST)) {
			return "单元测试";
		}
		if (node.getNodeType().equals(NodeType.EVALUATE)) {
			return "模块评测";
		}
		return "";
	}
}
