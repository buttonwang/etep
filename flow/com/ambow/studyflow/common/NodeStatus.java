package com.ambow.studyflow.common;

/**
 * 
 * NodeStatus.java:节点状态类型
 * 
 * Created on 2008-5-9
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
public enum NodeStatus {
	INITIAL(0),	/* 初始状态 */
	NOPASS(1),	/* 未通过 */
	PASSED(2),	/* 通过 */
	SKIP(3);	/* 跳过 */
	
	private int value;
	
    NodeStatus(int value) 
    {
        this.value = value;
    }

    // the identifierMethod
    public int toInt() {
      return value;
    }

     // the valueOfMethod
     public  static NodeStatus fromInt(int value) {    
         switch(value) {
             case 0: return INITIAL;
             case 1: return NOPASS;
             case 2: return PASSED;
             case 3: return SKIP;
             default:
                     return INITIAL;
         }
    }

}
