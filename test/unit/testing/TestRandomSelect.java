package testing;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.ambow.studyflow.common.ProcessReleaseStatus;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.Test;
import com.ambow.trainingengine.exam.domain.Test3;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.itembank.domain.DynamicAssembledPaper;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;

/*
 * TestRandomSelect.java
 * 
 * Created on Jul 4, 2008 2:38:20 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class TestRandomSelect extends BaseTest{
	
	public void testInsert(){
		Test test;
		long before=System.currentTimeMillis();
		for(int i=0;i<6000;i++){
			test=new Test();
			test.setName(" sort:"+i);
			genService.save(test);
		
		}
		long after=System.currentTimeMillis();
		long spend=after-before;
		System.out.println("Time Spend: "+spend+" ms");
		
	}
	public void testJdbcInsert(){
		for(int i=0;i<10;i++){
			testBatchInsert();
		}
	}
	public void testAssBuInsert(){
		for(int i=0;i<10;i++){
			testAssInsert();
		}
	}
	/*
	 * 测试带关联的对象的保存
	 */
	public void testAssInsert(){
		Test test=new Test();
		Test3 test3;
		for(int i=0;i<200;i++){
			test3=new Test3();
			test3.setValue("soso"+i);
			test.getTests().add(test3);
			test3.setTest(test);
		}
		
		StatelessSession session=genService.getTemplate().getSessionFactory().openStatelessSession();
		Transaction transaction=session.beginTransaction();
		long before=System.currentTimeMillis();
		
		genService.save(test);
		//session.insert(test);
		
		transaction.commit();
		session.close();
		long after=System.currentTimeMillis();
		long spend=after-before;
		System.out.println("Time Spend: "+spend+" ms");
	}
	
	public void testBatchInsert(){
		Test test;
		Session session=genService.getTemplate().getSessionFactory().openSession();
		
		//StatelessSession session=genService.getTemplate().getSessionFactory().openStatelessSession();
		Transaction transaction=session.beginTransaction();
		long before=System.currentTimeMillis();
		for(int i=0;i<600;i++){
			test=new Test();
			test.setName(" sort:"+i);
			session.save(test);
			if ( i % 30 == 0 ) {
				//20，与JDBC批量设置相同
				//将本批插入的对象立即写入数据库并释放内存
				session.flush();
				session.clear();
				} 
		}
		
		transaction.commit();
		session.close();
		long after=System.currentTimeMillis();
		long spend=after-before;
		System.out.println("Time Spend: "+spend+" ms");
	}
	
	public void testSelect(){
		String hql="from Test test order by rand()";
		HibernateTemplate template = genService.getTemplate();
		assertNotNull(template);
		SessionFactory sessionFactory=genService.getTemplate().getSessionFactory();
		assertNotNull(sessionFactory);
		Session session=sessionFactory.openSession();
		//List<Test> tests=genService.find(hql);
		List<Test> tests=session.createQuery(hql).setFetchSize(20).setMaxResults(20).list();
		for(Test test : tests){
			System.out.println(" "+test.getName());
		}
		
	}
	
	public void testSelectItem(){
		String hql="from Item";
		List<Item> items=genService.find(hql);
		
		for(Item item: items){
			System.out.println("The name:"+item.getCode());
			if(item.getSubItems().size()>0){
				List<SubItem> subItems=item.getSubItems();
				for(SubItem subItem:subItems){
					//System.out.println("The name of subItem:"+subItem.getId());
				}
				
			}
		}
	}
	
	public void testGenPaperReq(){
		String hql="from ItemType";
		List<ItemType> types=genService.find(hql);
		PaperAssemblingRequirements requirement;
		Node asfNode=genService.get(Node.class,5l);
		for(ItemType type:types){
			System.out.println("code:"+type.getCode());
			//System.out.println("name:"+type.getName());
			requirement=new PaperAssemblingRequirements();
			requirement.setItemTypeCode(type.getCode());
			requirement.setValidityValue("30");
			requirement.setDifficultyValue("25");
			requirement.setAsfNode(asfNode);
			requirement.setAmount(type.getItemNumPerpage());
			requirement.setYear("2000");
			requirement.setSource(" ");
			genService.save(requirement);
		}
		
		//PaperAssemblingRequirements requirement=new PaperAssemblingRequirements();
		
	}
	
	
	/*
	 * 
	 */
	public void testProcessGen(){
		Date createTime=new Date();
		Node node=new Node();
		node.setName("1");
		node.setCreator("creator");
		node.setCreateTime(createTime);
		node.setOrderNum("11");
		node.setUpdator("up");
		node.setUpdateTime(createTime);
		node.setIsStartNode('Y');
		
		
		
		ProcessDefinition processDefinition=new ProcessDefinition();
		processDefinition.setName("测试");
		processDefinition.setStartNode(node);
		processDefinition.setCreator("lixin");
		processDefinition.setCreateTime(createTime);
		processDefinition.setUpdator("lx");
		processDefinition.setUpdateTime(createTime);
		processDefinition.setReleaseStatus(ProcessReleaseStatus.UNRELEASE);
		processDefinition.setCategoryId(1l);
		//processDefinition.setDefVersion(version)
		processDefinition.setDescription("xxx");
		processDefinition.addNode(node);
		//processDefinition.set
		node.setProcessDefinition(processDefinition);
		genService.save(processDefinition);
	}
	
	public void testPickItem(){
		ExamItemService examItemService=(ExamItemService) this.getBean("examItemService");
		List<PaperAssemblingRequirements> requirements=genService.find("from PaperAssemblingRequirements");
		for(PaperAssemblingRequirements require:requirements){
			if(require.getAmount()>1){
				List<Item> items=examItemService.pickRandomItems(require);
				for(Item item:items){
					System.out.println(" id:"+item.getId()+" code:"+item.getCode()+" size:"+require.getAmount());
				}
			//break;	
			}
		}
		
	}
	
	 public void testDynamicPaper(){
		 ExamItemService examItemService=(ExamItemService)this.getBean("examItemService");
		 NodeInstance nodeInstance=genService.get(NodeInstance.class, 1l);
		 List<PaperAssemblingRequirements> requirementsList=genService.find("from PaperAssemblingRequirements");
		 DynamicAssembledPaper dynamicPaper=examItemService.makeDynamicPaper(nodeInstance,null, requirementsList);
		 assertNotNull(dynamicPaper);
		 assertNotNull(dynamicPaper.getId());
		 assertNotNull(dynamicPaper.getItems().size());
	 }
	 
	 
	 public void testInitPreocessInstance(){
		 Date startTime = new Date();
		 Node node=genService.get(Node.class, 1l);
		 ProcessDefinition processDefinition=genService.get(ProcessDefinition.class, 1l);
		 ProcessInstance processInstance=new ProcessInstance();
		 processInstance.setActor("xx");
		 processInstance.setStartTime(startTime);
		 processInstance.setNode(node);
		 processInstance.setProcessDefinition(processDefinition);
		 processInstance.setProcessStatus(ProcessStatus.RUNNING);
		 NodeInstance nodeInstance=new NodeInstance();
		 nodeInstance.setNecessary(true);
		 nodeInstance.setNode(node);
		 nodeInstance.setStartTime(startTime);
		 nodeInstance.setProcessInstance(processInstance);
		 nodeInstance.setUpdateTime(startTime);
		 
		 //processInstance.
		 genService.save(processInstance);
		 genService.save(nodeInstance);
		 
		 
	 }

	@Override
	protected void setPath() {
		// TODO Auto-generated method stub
		
	}

}
