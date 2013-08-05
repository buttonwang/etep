package com.ambow.trainingengine.studyflow.service;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ambow.core.dao.HibernateGenericDao;

public class CreateDB {

	public static void main(String[] args) {
		System.out.println("start=======================================");
		CreateDB cDB = new CreateDB();
		cDB.setUp();
		System.out.println("end=======================================");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/* =================================================================== */
	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
	private Session session;
	public HibernateGenericDao genService;

	public ProcessAdminService pAServ;

	private String[] xmlClassPath = { "spring/applicationContext.xml",
			"spring/applicationContext-hibernate.xml",
			"spring/applicationContext-service.xml",
			"spring/applicationContext-admin-studyflow-service.xml",
			"spring/applicationContext-admin-policy-service.xml"
			};

	public void setUp() {
		applicationContext = new ClassPathXmlApplicationContext(xmlClassPath);
		sessionFactory = (SessionFactory) applicationContext
				.getBean("sessionFactory");
		session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.setFlushMode(FlushMode.AUTO);
		TransactionSynchronizationManager.bindResource(sessionFactory,
				new SessionHolder(session));
		genService = (HibernateGenericDao) this.getBean("genService");
		pAServ = (ProcessAdminService) getBean("processAdminService");
	}

	public void tearDown() throws Exception {
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}

	public Object getBean(String beanName) {
		Object object = applicationContext.getBean(beanName);
		String str = null;
		return object;
	}
	/* =================================================================== */
}
