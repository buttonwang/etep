/* 
 * GenericUtilsTest.java
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
 * $Log: GenericUtilsTest.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * 评测考研项目
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/10 01:35:59  pengqing
 * 初始项目，导入ambow-core
 *
 *
 */
package com.ambow.core.util;

import junit.framework.TestCase;

public class GenericUtilsTest extends TestCase {
	/**
	 * 娴嬭瘯GenericsUtils鐨勫悇绉嶆儏鍐�
	 */
	public void testGetGenericClass() {
		// 娴嬭瘯鍙栧嚭绗�1,2涓寖鍨嬬被瀹氫箟
		assertEquals(TestBean.class, GenericUtils
				.getSuperClassGenricType(TestActualGenericsBean.class));
		assertEquals(TestBean2.class, GenericUtils.getSuperClassGenricType(
				TestActualGenericsBean.class, 1));

		// 鏁扮粍瓒婄晫鐨勬椂鍊欒繑鍥濷bject.class
		assertEquals(Object.class, GenericUtils.getSuperClassGenricType(
				TestActualGenericsBean.class, 2));

		// 娴嬭瘯鏃犺寖鍨嬪畾涔夋椂杩斿洖Object.class
		assertEquals(Object.class, GenericUtils
				.getSuperClassGenricType(TestActualGenericsBean2.class));
	}

	/**
	 * 甯﹁寖鍨嬪畾涔夌殑鐖剁被
	 */
	public static class TestGenericsBean<T, T2> {

	}

	/**
	 * T1鐢ㄥ埌鐨勭被
	 */
	public static class TestBean {

	}

	/**
	 * T2鐢ㄥ埌鐨勭被
	 */
	public static class TestBean2 {

	}

	/**
	 * 瀹氫箟浜嗙埗绫昏寖鍨嬬殑瀛愮被
	 */
	public static class TestActualGenericsBean extends
			TestGenericsBean<TestBean, TestBean2> {

	}

	/**
	 * 娌℃湁瀹氫箟鐖剁被鑼冨瀷鐨勫瓙绫�
	 */
	public class TestActualGenericsBean2 extends TestGenericsBean {

	}

}
