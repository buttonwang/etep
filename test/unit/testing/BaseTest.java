package testing;

import junit.framework.TestCase;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ambow.core.dao.HibernateGenericDao;

public abstract class BaseTest extends TestCase {
	
	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
	private Session session;
	protected HibernateGenericDao genService;
	private String[] xmlClassPath={
			"spring/applicationContext.xml",
			"spring/applicationContext-hibernate.xml",
			"spring/applicationContext-service.xml"
			};
	/*
	 * 复写xmlClassPath
	 */
	protected abstract void  setPath();
	
	public void  setUp(){
		setPath();
		applicationContext=new ClassPathXmlApplicationContext(xmlClassPath);
		 sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");  
		         session = SessionFactoryUtils.getSession(sessionFactory, true);  
		         session.setFlushMode(FlushMode.AUTO);  
		         TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		         genService=(HibernateGenericDao)this.getBean("genService");
	}
	
	protected void tearDown() throws Exception {  
        TransactionSynchronizationManager.unbindResource(sessionFactory);  
         SessionFactoryUtils.releaseSession(session, sessionFactory);  
     }
	
	protected Object getBean(String beanName){
		Object object=applicationContext.getBean(beanName);
		String str=null;
		return object;
	}

}
