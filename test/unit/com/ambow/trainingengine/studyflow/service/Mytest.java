package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.policy.domain.NodeGroupPolicy;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;

public class Mytest extends TestCase {
	/**
	 * 取得与传入节点同级的所有训练节点 不包含单元测试 
	 * @param unitTestNode
	 * @param type
	 * @return
	 */
	public List getPracticeNodeListInSamelevel(UnitTestNode unitTestNode ){
		return getPracticeNodeListInSamelevel(unitTestNode,null);
		 
	}
	
	
	/**
	 * 根据策略取本级或上上级 训练节点及单元测试  
	 * @param unitTestNode 单元测试节点
	 * @param type 不为空：则取 所有单元测试 ＋ 所有训练节点 
	 * @return
	 */
	 public List getPracticeNodeListInSamelevel(UnitTestNode unitTestNode,String type){
		List findList = new ArrayList(0);
		if(unitTestNode!=null){
			//默认为查找同级训练节点及单元测试  
			NodeGroup ng = unitTestNode.getNodeGroup();
			UnitTestNodePolicy utnp = (UnitTestNodePolicy)genService.findObjByHql("from UnitTestNodePolicy where asfNode.id=? ",unitTestNode.getId());
			if(utnp!=null){
				Integer retrainingScope = utnp.getRetrainingScope();
				if(retrainingScope!=null){
					if(retrainingScope==1){
						if(ng!=null){
							//上级
							if(ng.getNodeGroup()!=null){
								ng = ng.getNodeGroup();
							}
						}
					}
					if(retrainingScope==2){
						if(ng!=null){
							//上级 
							NodeGroup png = ng.getNodeGroup();
							if(png!=null){
								ng=png;
								//上上级 爷爷级
								NodeGroup gng = png.getNodeGroup();
								if(gng!=null){
									ng = gng;
								}	
							}
						}
					}
				}
			}
			if(ng!=null){
				List ngSons =  nodeSons(ng);
				for (Iterator iterator = ngSons.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					if(node.getNodeType()==NodeType.PRACTICE){
						findList.add(node);
					}
					if(type!=null){
						if(node.getNodeType()==NodeType.UNITTEST){
							findList.add(node);
						}
					} 
				}
			}
		}	
		return findList;
	}
	 
	 /**
		 * 取得一个节点的所有儿子
		 * @param node
		 * @return
		 */

		public List nodeSons(Node node){
			List sonLst=new ArrayList();
			if(node instanceof NodeGroup){
				for (Iterator iterator = ((NodeGroup)node).getNodes().iterator(); iterator.hasNext();) {
					Node fnode = (Node) iterator.next();
					if(fnode instanceof NodeGroup){
						sonLst.addAll(nodeSons(fnode));
						sonLst.add(fnode);
					}else{
						sonLst.add(fnode);
					}
				}
			}
			return sonLst;
		}
	
	public static void main(String[] args) {
		Mytest m = new Mytest();
		m.setUp();
		UnitTestNode unitTestNode = new UnitTestNode();
		unitTestNode.setId(21);
		
		NodeGroup nodeGroup = m.genService.get(NodeGroup.class, 18l);
		 
		
		unitTestNode.setNodeGroup(nodeGroup);
		
	 
		 List findList = m.getPracticeNodeListInSamelevel(unitTestNode ) ;
		 for (Iterator iterator = findList.iterator(); iterator.hasNext();) {
			Node name = (Node) iterator.next();
			System.out.println(name.getName()+"  type: "+name.getNodeType() );
		}
		/*List lst = m.genService.getAll(ProcessCategory.class );
		Set sonSet = m.allSons(lst);
		lst.removeAll(sonSet);
		System.out.println("=========================================="+lst.size());
		
		ProcessCategory pc = m.genService.get(ProcessCategory.class,1 );
		Set sons = m.findSon( pc);
		for (Iterator iterator = sons.iterator(); iterator.hasNext();) {
			ProcessCategory name = (ProcessCategory) iterator.next();
			System.out.println(name.getName());
		}*/ 
		/*List lst =  m.genService.find("from ProcessCategory where parentCategory=null");
		for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
			ProcessCategory pc = (ProcessCategory) iterator.next();
			System.out.println(""+pc.getName()+"11111111111111111111111");
			for (Iterator iterator2 = pc.getChildrenCategories().iterator(); iterator2.hasNext();) {
				ProcessCategory pcSon = (ProcessCategory) iterator2.next();
				System.out.println("=="+pcSon.getName()+"11111111111111111111111");
			}
		}
		JSONLeanW jw = new JSONLeanW();
		System.out.println("==================="+jw.write(null));*/
		System.out.println("===================end");
	}
	
	public Set allSons(List processCategoryLst){
		Set sonSet = new HashSet();
		for (Iterator iterator = processCategoryLst.iterator(); iterator.hasNext();) {
			ProcessCategory name = (ProcessCategory) iterator.next();
			sonSet.addAll(findSon(name) );
		}
		return sonSet;
	}
	public Set findSon(ProcessCategory pc){
		Set set = new HashSet();
		List children  = pc.getChildrenCategories();
		if(children!=null&&children.size()>0){
			set.addAll(children);
			for (Iterator iterator = children.iterator(); iterator.hasNext();) {
				ProcessCategory name = (ProcessCategory) iterator.next();
				set.add(findSon(name));
			}
		}
		return set;
	}
 
	public void saveNodeGroupTest(){
		
		NodeGroup nodeGroup = new NodeGroup();
		nodeGroup.setName("节点组");
		
		Node node3= new Node();
		node3.setName("testNode3");
		node3.setNodeGroup(nodeGroup);
		
		nodeGroup.addNode(node3);
		genService.save(nodeGroup);
		
	}
		
	public void saveNodeTest(){
		
		
		NodeGroup nodeGroup = genService.get(NodeGroup.class, 4L);
		
		Node node1 = new Node();
		node1.setName("testNode1");		
		node1.setNodeGroup(nodeGroup);
		
		Node node2 = new Node();
		node2.setName("testNode2");
		node2.setNodeGroup(nodeGroup);
		
		
		Node node3= new Node();
		node3.setName("testNode3");
		node3.setNodeGroup(nodeGroup);
		
		
		 
		
		genService.save(node1);
		genService.save(node2);
		genService.save(node3);
		
	}
	public void testSelectParentIdTest(){
		try{
			Node node =new Node();
			node.setId(1);
		String hsql = "from Node as n where n.nodeGroup.id =  "+node.getId();
		List lst = genService.find(hsql, null) ;
		System.out.println("lst size :"+lst.size());
		for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
			Node inode = (Node) iterator.next();
			System.out.println(inode.getName());
			
		}}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	
	 
	
	protected void saveProcessCategory(){
//		ProcessCategory parentCategoryParent=new ProcessCategory();
//		parentCategoryParent.setId(1);
//
//		ProcessCategory processCatagory=new ProcessCategory();
//		processCatagory.setId(2);
//		processCatagory.setName("测试");			
//		processCatagory.setParentCategory(parentCategoryParent);

		ProcessCategory item = (ProcessCategory)session.load(ProcessCategory.class, 1);
		item.setName("测试");
		session.update (item);
	}
	public  void saveProcessTest(){
		/*ProcessDefinition process =new ProcessDefinition();
		process.setName("flowHibernateTest11");
		process.setCategoryId(1);

		process.addNode(node2);
		process.addNode(node3);

		process.setDescription("用于测试 hibernate annomation11"); 
		session.save(process);
		System.out.println("test successful");*/
	}
	
	public  void updateNode(){
		Node node2 = new Node();
		node2.setId(15);
		node2.setName("testNode15");
		 
		session.saveOrUpdate(node2);
		System.out.println("test successful");
		
	}
	
	public  void saveNodeGroupPolicyTest(){
		NodeGroupPolicy ngp = new NodeGroupPolicy();
		ngp.setIsDisplayModule(1);//1 表示不显示  , 0 表示显示
		ngp.setNodeId(15L);
		
		/*String hql="select * from Node n where n.id=15" ;
		Query q = session.createQuery( hql );
		System.out.println(q.toString());
		Node node=(Node)session.createQuery( hql );
		System.out.println(node.getName()); 
		ngp.setAsfNode(node);		
		*/
		session.save(ngp);
		System.out.println("test successful");
		
	}
	
	/*===================================================================*/
	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
	private Session session;
	public  HibernateGenericDao genService;
	private String[] xmlClassPath={
			"spring/applicationContext.xml",
			"spring/applicationContext-hibernate.xml",
			"spring/applicationContext-service.xml"
			};
 
	public void  setUp(){
		applicationContext=new ClassPathXmlApplicationContext(xmlClassPath);
		 sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");  
		         session = SessionFactoryUtils.getSession(sessionFactory, true);  
		         session.setFlushMode(FlushMode.AUTO);  
		         TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		         genService=(HibernateGenericDao)this.getBean("genService");
	}
	
	public void tearDown() throws Exception {  
        TransactionSynchronizationManager.unbindResource(sessionFactory);  
         SessionFactoryUtils.releaseSession(session, sessionFactory);  
     }
	
	public Object getBean(String beanName){
		Object object=applicationContext.getBean(beanName);
		String str=null;
		return object;
	}
	/*===================================================================*/
}
