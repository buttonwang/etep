package com.ambow.trainingengine.studyflow.service;

import com.ambow.studyflow.domain.Node;

public class TestUpdateTable {
	private String updateTable(String tableName,String coumlun,Object value,String coditionColumn,Object coditionValue){
		return "update " +tableName+" set "+coumlun+" = "+getValueInSQL(value)+" where "+coditionColumn+" = "+ getValueInSQL(coditionValue);
	}
	
	public String getValueInSQL(Object value){
		String vtype = value.getClass().getSimpleName();
		return "String".equals(vtype)? "'"+value+"'":""+value;
	};
	
	private String  changeNodeType(Node node,String type){
		return updateTable( "asf_node", "node_type", type, "id", node.getId()); 
	}
	public static void main(String[] args) {
		TestUpdateTable t = new TestUpdateTable();
		Node node = new Node();
		node.setId(101);
		t.changeNodeType(node, "group");
		
		System.out.println(t.changeNodeType(node, "GROUP"));
		
	}
}
