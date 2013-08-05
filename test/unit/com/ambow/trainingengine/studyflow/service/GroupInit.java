package com.ambow.trainingengine.studyflow.service;

import java.util.HashMap;
import java.util.Map;

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

public class GroupInit {
	public static void main(String[] args) {
		System.out.println("start=====================================================");
		 GroupInit p = new GroupInit();
		 p.setUp();
		 // System.out.println("=======================================processId : "+p.initPracticeNode());;
		 long processId = 1;
		 p.initNodeGroupId(processId);
		System.out.println("end=====================================================");
	}
	String[] allNode={"模块测试","学前评测","基础强化I","强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三","基础强化II","强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分","语法集训1","考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分","学后评测"};
	String[] evaluateNode_s={"模块测试","学前评测","学后评测"};
	String[] practiceNode_s={"单元测试1","单元测试2","单元测试_基础强化部分","单元测试_语法集训部分"};
	String[] unitTestNode_s={"单元测试1","单元测试2","单元测试_基础强化部分","单元测试_语法集训部分"};
	String[] phaseTestNode_s={"阶段测试一","阶段测试二","阶段测试三","阶段测试四","阶段测试五","考研语法专项检测"};
	String[] nodeGroup_s={"基础强化I","基础强化II","语法集训1"};
	
	public void initNodeGroupId(long processId){
	 	Map nodeGroupWithNodesNameMap = new HashMap();
	 	nodeGroupWithNodesNameMap.put("基础强化I",new String[] {"强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三"});
	 	nodeGroupWithNodesNameMap.put("基础强化II",new String[] {"强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分"});
	 	nodeGroupWithNodesNameMap.put("语法集训1",new String[] {"考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分"});
		try{
			for (int i = 0; i < nodeGroup_s.length; i++) {
				String  nodeGroupName= nodeGroup_s[i];
				if(nodeGroupName.equals("断点")){
					System.out.println();
				}
				String hql_nodeGroup = "from NodeGroup where name=?  and processDefinition.id=? ";
				NodeGroup ng =(NodeGroup) pAServ.findObjByHql(hql_nodeGroup,nodeGroupName,processId);
				String [] sons = (String [])nodeGroupWithNodesNameMap.get(nodeGroupName);
				for (int j = 0; j < sons.length; j++) {
					String nodeName = sons[j];
					String hql_node = "from Node where name=? and processDefinition.id=?";
					Node node = (Node) pAServ.findObjByHql(hql_node,nodeName,processId);
					node.setNodeGroup(ng);
					pAServ.save(node);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("====================================end save===================================");
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

