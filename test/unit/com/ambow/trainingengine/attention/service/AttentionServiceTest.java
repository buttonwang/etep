/**
 * 
 */
package com.ambow.trainingengine.attention.service;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;


/**
 * @author zhujianmin
 *
 */
public class AttentionServiceTest extends TestCase{

	public void testAttentionService() {
		String devoteOld="test5,test7";
		String addDevote = "test4";
		assertEquals(devoteOld.length()+addDevote.length()+1, new AttentionService().addDevote(devoteOld, addDevote).length()); 
	}
	
	//@Test
	public void testShow() {
		List<Integer> lst = new ArrayList<Integer>();
		for (int i = 1; i < 14; i++) {
			lst.add(i);
		}
		List<Integer>  tempList = AttentionService.getPageNOLst(-7,2,lst);
		assertEquals(tempList.contains(1), true);
		assertEquals(tempList.contains(2), true);
		assertEquals(tempList.contains(3), false);
		
		
		tempList=AttentionService.getPageNOLst(7,2,lst);//最后 一面应该包含一条数据 
		assertEquals(tempList.contains(13), true);
		assertEquals(tempList.contains(14), false);
		 
		tempList=AttentionService.getPageNOLst(8,2,lst);//超过总页面数时，则为最后一页数据 	 
		assertEquals(tempList.contains(13),true);
	}
}
