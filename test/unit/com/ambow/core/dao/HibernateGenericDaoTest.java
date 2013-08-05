/* 
 * HibernateGenericDaoTest.java
 * 
 * Created on 2007-4-3
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: HibernateGenericDaoTest.java,v $
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
package com.ambow.core.dao;

import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;

/**
 * Unit Test Case
 * @author Peng Qing
 *
 */
public class HibernateGenericDaoTest extends TestCase {

	/**
	 * 测试分页是否正确<br>
	 * 采用Easy Mock Framework 对Hibernate 的方法进行了隔离，保证只测试分页逻辑。
	 *
	 */
	public void testPagedQueryHql() {
		int totalNumber = 25;
		int pageNo = 1;
		int pageSize = 25;
		int firstResult = 0;
		int pageCount = 1;

		String hql = "from Cat cat where cat.name like ? ";
		String sHql = " select count (*) from Cat cat where cat.name like ? ";
		Object[] args = {"fri%"};

		HibernateGenericDao dao = new HibernateGenericDao();
		SessionFactory mockSessionFactory = EasyMock.createMock(SessionFactory.class);
		Session mockSession = EasyMock.createMock(Session.class);
		Query mockQuery = EasyMock.createMock(Query.class);
		HibernateTemplate mockHT = EasyMock.createMock(HibernateTemplate.class);
		List mockList = EasyMock.createMock(List.class);

		// for query
		EasyMock.expect(mockSessionFactory.openSession()).andReturn(mockSession);
		EasyMock.expect(mockSession.getSessionFactory()).andReturn(mockSessionFactory);
		EasyMock.expect(mockSession.createQuery(hql)).andReturn(mockQuery);
		for (int i = 0; i < args.length; i++) {
			EasyMock.expect(mockQuery.setParameter(i, args[i])).andReturn(mockQuery);
		}
		
		// for mock HibernateTemplate
		EasyMock.expect(mockSessionFactory.openSession()).andReturn(mockSession);
		EasyMock.expect(mockSession.getSessionFactory()).andReturn(mockSessionFactory);
		EasyMock.expect(mockSession.createQuery(sHql)).andReturn(mockQuery);
		for (int i = 0; i < args.length; i++) {
			EasyMock.expect(mockQuery.setParameter(i, args[i])).andReturn(mockQuery);
		}
		mockSession.flush();
		EasyMock.expect(mockQuery.list()).andReturn(mockList);
		EasyMock.expect(mockHT.find(sHql, args)).andReturn(mockList);
		EasyMock.expect(mockList.get(0)).andReturn((long) totalNumber);
		
		// for query
		EasyMock.expect(mockQuery.setFirstResult(firstResult)).andReturn(mockQuery);
		EasyMock.expect(mockQuery.setMaxResults(pageSize)).andReturn(mockQuery);
		EasyMock.expect(mockQuery.list()).andReturn(mockList);
		EasyMock.expect(mockList.size()).andReturn(totalNumber);

		EasyMock.replay(mockSessionFactory);
		EasyMock.replay(mockSession);
		EasyMock.replay(mockQuery);
		EasyMock.replay(mockHT);
		EasyMock.replay(mockList);

		dao.setHibernateTemplate(mockHT);
		dao.setSessionFactory(mockSessionFactory);

		Page page = dao.pagedQuery(hql, pageNo, pageSize, args);

		assertEquals(pageCount, page.getTotalPageCount());
		assertEquals(pageNo, page.getCurrentPageNo());
		assertFalse(page.hasNextPage());
		assertFalse(page.hasPreviousPage());
	}

}
