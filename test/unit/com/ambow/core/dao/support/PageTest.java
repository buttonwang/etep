/* 
 * PageTest.java
 * 
 * Created on 2007-4-2
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: PageTest.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * �2⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/10 01:36:04  pengqing
 * ��ʼ��Ŀ������ambow-core
 *
 *
 */
package com.ambow.core.dao.support;

import org.apache.commons.collections.CollectionUtils;

import junit.framework.TestCase;

/**
 * 测试Page 类
 * @author Peng Qing
 *
 */
public class PageTest extends TestCase {

	public void testWithDefaultData() throws Exception {
		Page page = new Page();
		assertEquals(0, page.getTotalPageCount());
		assertEquals(1, page.getCurrentPageNo());
		assertFalse(page.hasNextPage());
		assertFalse(page.hasPreviousPage());
	}

	public void testWithSimpleData() throws Exception {
		Page page = new Page(0, 100, 20, CollectionUtils.EMPTY_COLLECTION);
		assertEquals(5, page.getTotalPageCount());
		assertEquals(1, page.getCurrentPageNo());
		assertTrue(page.hasNextPage());
		assertFalse(page.hasPreviousPage());
	}

	public void testWithNormalData() throws Exception {
		Page page = new Page(316, 1512, 25, CollectionUtils.EMPTY_COLLECTION);
		assertEquals(61, page.getTotalPageCount());
		assertEquals(13, page.getCurrentPageNo());
		assertTrue(page.hasNextPage());
		assertTrue(page.hasPreviousPage());
	}

	public void testGetStartOfPage() throws Exception {
		assertEquals(0, Page.getStartOfPage(1, 20));
	}

}
