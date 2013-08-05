package com.ambow.trainingengine.util;

import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.dto.NodeDTO.NodeType;

public class NodeJSTree extends JSTree  {	
	public NodeJSTree (Node node){
		this.setId(String.valueOf(node.getId()));
		try{
			this.setPid(String.valueOf(node.getNodeGroup().getId()));
		}catch(NullPointerException  e){
		}
		this.setObj(node);		
	}
	
	@Override
	public String toString() {
		return String.format(getToString(),objToString()) ;
	}
	
	@Override
	public String getTreeN_Name() {
		return ((Node)this.getObj()).getName();
	}
	@Override
	public String objToString() {
		Node node = ((Node)this.getObj()) ;
		Long id = node.getId() ;
		Long pid = -1L;
		try{
			pid=node.getNodeGroup().getId();
		}catch(Exception e){
		}
		String name =  node.getName() ;
		String prid = String.valueOf(node.getProcessDefinition().getId()) ;
		String type = getNodeType( node );
		String orderNum =   node.getOrderNum();
		/*
		if(orderNum!=null&&!"".equals(orderNum.trim())){
			orderNum = orderNum.split(",")[orderNum.split(",").length-1];
		}*/
		
		return String.format("{'id':'%s','pid':'%s','name':'%s','type':'%s','prid':'%s','orderNum':'%s'}",id,pid,name,type,prid,orderNum) ;
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
