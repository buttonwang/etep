package com.ambow.trainingengine.attention.util;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;


public class KeyValueListTest extends TestCase {

	 

	public void testGetList() {
		List<Map<String,Object >>  lst = KeyValueList.getList("-1 0 1","好 不好 一般");
		for (Map<String,Object > map : lst) {
			System.out.println(map.get("k")+":"+map.get("v"));
		}
		assertEquals (lst.size()==3,true);
	}
}
