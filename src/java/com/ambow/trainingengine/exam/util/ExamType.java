package com.ambow.trainingengine.exam.util;

import com.ambow.studyflow.dto.NodeDTO.NodeType;


/*
 * ExamType.java
 * 
 * Created on Jul 10, 2008 10:05:14 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
  public enum ExamType{
	practice(1), //训练
	evaluate(2),   //评测
	weekEnhance(3),//弱项强化
	unitExam(4),   //单元测试；后测
	phaseExam(5),  //阶段测试 ；前测
	extPractice(6),//拓展训练	
	exam(9);		//考试
	
	private int value;
	
	private ExamType(int intValue){
		this.value=intValue;
	}
	
	public  int getValue(){
		return this.value;
	}
	
	public static ExamType getExamType(int intValue){
		ExamType type=null;
		switch(intValue){
			case 1:
				type=practice;
				break;
			case 2:
				type=evaluate;
				break;
			case 3:
				type=weekEnhance;
				break;
			case 4:
				type=unitExam;
				break;
			case 5:
				type=phaseExam;
				break;
			case 6:
				type=extPractice;
				break;
			case 9:
				type=exam;
				break;	
		}
		return type;
	}
	
	public static ExamType getExamTypeByNodeType(NodeType nodeType){
		ExamType type=null;
		switch(nodeType){
			case EVALUATE: 
				type = evaluate;
				break;
			case EXAM:
				type = exam;
				break;
			case GROUP:
				type = weekEnhance;
				break;
			case PHASETEST:
				type = phaseExam;
				break;
			case UNITTEST:
				type = unitExam;
				break;
			case PRACTICE:
				type = practice;
				break;

		}
		return type;
	}
}
