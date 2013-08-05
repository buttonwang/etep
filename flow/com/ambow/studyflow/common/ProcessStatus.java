package com.ambow.studyflow.common;

/**
 * 
 * NodeStatus.java:流程实例状态类型
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
public enum ProcessStatus {
	
	STOPED(-1),         /* 终止 */
	RUNNING(0),	/* 运行中 */
	SUSPEND_PAUSE(1),	/* 挂起 用户暂停 */
	SUSPEND_FRESHWORD(2);/* 挂起 生词训练 */	
	
	private int value;
	
    ProcessStatus(int value) 
    {
        this.value = value;
    }

    // the identifierMethod
    public int toInt() {
      return value;
    }

     // the valueOfMethod
     public  static ProcessStatus fromInt(int value) {    
         switch(value) {
             case -1: return STOPED;
             case 0: return RUNNING;
             case 1: return SUSPEND_PAUSE;
             case 2: return SUSPEND_FRESHWORD;
             default:
                     return RUNNING;
         }
    }

}
