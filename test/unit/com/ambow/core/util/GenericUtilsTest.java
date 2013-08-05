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
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/10 01:35:59  pengqing
 * ��ʼ��Ŀ������ambow-core
 *
 *
 */
package com.ambow.core.util;

import junit.framework.TestCase;

public class GenericUtilsTest extends TestCase {
	/**
	 * 测试GenericsUtils的各种情况
	 */
	public void testGetGenericClass() {
		// 测试取出第1,2个范型类定义
		assertEquals(TestBean.class, GenericUtils
				.getSuperClassGenricType(TestActualGenericsBean.class));
		assertEquals(TestBean2.class, GenericUtils.getSuperClassGenricType(
				TestActualGenericsBean.class, 1));

		// 数组越界的时候返回Object.class
		assertEquals(Object.class, GenericUtils.getSuperClassGenricType(
				TestActualGenericsBean.class, 2));

		// 测试无范型定义时返回Object.class
		assertEquals(Object.class, GenericUtils
				.getSuperClassGenricType(TestActualGenericsBean2.class));
	}

	/**
	 * 带范型定义的父类
	 */
	public static class TestGenericsBean<T, T2> {

	}

	/**
	 * T1用到的类
	 */
	public static class TestBean {

	}

	/**
	 * T2用到的类
	 */
	public static class TestBean2 {

	}

	/**
	 * 定义了父类范型的子类
	 */
	public static class TestActualGenericsBean extends
			TestGenericsBean<TestBean, TestBean2> {

	}

	/**
	 * 没有定义父类范型的子类
	 */
	public class TestActualGenericsBean2 extends TestGenericsBean {

	}

}
