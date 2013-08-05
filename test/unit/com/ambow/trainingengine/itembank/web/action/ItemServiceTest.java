package com.ambow.trainingengine.itembank.web.action;

import junit.framework.TestCase;

//import org.junit.AfterClass;
//import org.junit.BeforeClass;

import com.ambow.trainingengine.itembank.service.ItemService;

public class ItemServiceTest extends TestCase{
	//@BeforeClass
	public void set() {
	System.out.println("in set");
	}
	///@AfterClass
	public void af() {
	System.out.println("in after");
	}
	//@org.junit.Test
	/**
	 * 向指定位置增加答案
	 */
	public void testGetAddCorrectAnswerStr () {
		ItemService itemService = new ItemService();
		String correctAnswerStrOld="甲；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu；Cu$Hg";
		
		String addAnswerContent="test";
		
		//开始位置
		Integer addAnswerIndex =0 ;
		assertEquals("甲$test；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu；Cu$Hg", itemService.getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, correctAnswerStrOld));
	
		addAnswerIndex = -2;
		//前越界
		assertEquals("甲；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu；Cu$Hg", itemService.getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, correctAnswerStrOld)); 
		
		addAnswerIndex = 6;
		//后越界
		assertEquals("甲；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu；Cu$Hg", itemService.getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, correctAnswerStrOld)); 
		
		//结束位置
		addAnswerIndex = 5;
		assertEquals("甲；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu；Cu$Hg$test", itemService.getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, correctAnswerStrOld)); 
		
		//中间位置
		addAnswerIndex = 4;
		assertEquals("甲；Hg$Cu$Fe；Cu$Hg$Fe；Fe$Cu$Hg；Hg$Cu$test；Cu$Hg", itemService.getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, correctAnswerStrOld));
	}
}
