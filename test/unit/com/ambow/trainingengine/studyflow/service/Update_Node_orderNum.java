package com.ambow.trainingengine.studyflow.service;

import java.util.Iterator;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;

public class Update_Node_orderNum {
	public static void main(String[] args) {
		System.out.println("start=====================================================");
		Update_Node_orderNum p = new Update_Node_orderNum();
		 p.setUp();
		 
		 //要更新的流程 id
 		 long processId = 1;
 		 p.initNodeOrderNum(processId);
		System.out.println("end=====================================================");
	} 
	
	public void initNodeOrderNum(long processId){
		List nodes = pAServ.getAll(Node.class);
		for (Iterator iterator = nodes.iterator(); iterator.hasNext();) {
			Node node = (Node) iterator.next();
			node.setOrderNum(computeOrderNum(node,null));
			pAServ.save(node);
		}
		System.out.println("====================================end save===================================");
	}
	
	public String computeOrderNum(Node node,String orderNum){
		if(node!=null){
			if(orderNum==null||orderNum.equals("")){
				orderNum =""+ node.getId();
			}
			NodeGroup parentNode = node.getNodeGroup();
			if(parentNode!=null&&parentNode.getId()!=0){
				orderNum =parentNode.getId()+","+ orderNum;
				computeOrderNum( parentNode.getNodeGroup(),orderNum );
			}
		}
		
		return orderNum;
	}
	/*===================================================================*/
	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
	private Session session;
	public  HibernateGenericDao genService;
	
	public ProcessAdminService pAServ ;
	
	private String[] xmlClassPath={
			"spring/applicationContext.xml",
			"spring/applicationContext-hibernate.xml",
			"spring/applicationContext-service.xml",
			"spring/applicationContext-admin-studyflow-service.xml"
			};
 
	public void  setUp(){
		applicationContext=new ClassPathXmlApplicationContext(xmlClassPath);
		 sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		         session = SessionFactoryUtils.getSession(sessionFactory,true);
		         session.setFlushMode(FlushMode.AUTO);
		         TransactionSynchronizationManager.bindResource(sessionFactory,new SessionHolder(session));
		         genService=(HibernateGenericDao)this.getBean("genService");
		         pAServ=(ProcessAdminService)getBean("processAdminService");
	}
	
	public void tearDown() throws Exception {  
        TransactionSynchronizationManager.unbindResource(sessionFactory);
         SessionFactoryUtils.releaseSession(session,sessionFactory);
    }
	
	public Object getBean(String beanName){
		Object object=applicationContext.getBean(beanName);
		String str=null;
		return object;
	}
	/*===================================================================*/

}

