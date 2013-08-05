package testing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.exam.domain.Test;
import com.ambow.trainingengine.exam.domain.Test2;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.util.SplitCompareable;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.domain.WordExtension;

/*
 * TestDomainObject.java
 * 
 * Created on Jun 27, 2008 4:57:11 PM
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
public class TestDomainObject extends BaseTest{
	
	/*
	 * 
	 */
	public void DomainTest(){
		WordBasic wordBasic=new WordBasic();
		wordBasic.setAssociationMemory("hei hua fei");
		WordExtension wordExtension1=new WordExtension();
		wordExtension1.setWordBasic(wordBasic);
		wordExtension1.setWordCategory("dong");
		WordExtension wordExtension2=new WordExtension();
		wordExtension2.setWordBasic(wordBasic);
		wordExtension2.setWordCategory("xi");
		Set<WordExtension> exSet=new HashSet<WordExtension>();
		exSet.add(wordExtension1);
		exSet.add(wordExtension2);
		wordBasic.setWordExtensions(exSet);
		HibernateGenericDao genService=(HibernateGenericDao)getBean("genService");
		genService.save(wordBasic);
		
	}
	
	public void Domain2Test(){
		HibernateGenericDao genService=(HibernateGenericDao)getBean("genService");
		List<Test> tests=genService.find("from Test");
		for(Test test:tests){
			System.out.println(" :"+test.getId()+" :"+test.getName());
		}
	}
	
	public void TestOkAn(){
		Test test=new Test();
		test.setName("qirui");
		Test2 test2=new Test2();
		test2.setTest(test);
		test2.setValue("why me?");
		HibernateGenericDao genService=(HibernateGenericDao)getBean("genService");
		genService.save(test2);
		
	}
	
	public void deleteObject(){
		Long lValue=Long.valueOf(3);
		genService.removeById(Test.class, lValue);
	}
	
	public void testTypeSort(){
		List<ItemType> typeList=new ArrayList<ItemType>();
		ItemType type=new ItemType("11A",null,1,null,null);
		ItemType type1=new ItemType("11C",null,1,null,null);
		ItemType type2=new ItemType("14A",null,1,null,null);
		ItemType type3=new ItemType("16A",null,1,null,null);
		ItemType type4=new ItemType("21A",null,1,null,null);
		ItemType type5=new ItemType("11B",null,1,null,null);
		ItemType type6=new ItemType("16B",null,1,null,null);
		ItemType type7=new ItemType("11A",null,1,null,null);
		typeList.add(type);
		typeList.add(type1);
		typeList.add(type2);
		typeList.add(type3);
		typeList.add(type4);
		typeList.add(type5);
		typeList.add(type6);
		typeList.add(type7);
		for(ItemType item:typeList){
			System.out.println(item.getCode());
		}
		ExamUtil.sortList(typeList);
		for(ItemType item:typeList){
			System.out.println(item.getCode());
		}
	}
	
	public void testItemPagelize(){
		ItemType type=new ItemType("11A",null,5,null,null);
		ItemType type1=new ItemType("11C",null,5,null,null);
		ItemType type2=new ItemType("14A",null,6,null,null);
		ItemType type4=new ItemType("16A",null,1,null,null);
		ItemType type5=new ItemType("21A",null,1,null,null);
		//ItemType type6=new ItemType("22A",null,1,null,null);
		Item item;
		List<Item> items=new ArrayList<Item>();
		for(int i=0;i<5;i++){
			item=new Item();
			item.setContent("16A "+i);
			item.setItemType(type4);
			items.add(item);
		}
		for(int i=0;i<3;i++){
			item=new Item();
			item.setContent("21A "+i);
			item.setItemType(type5);
			items.add(item);
		}
		for(int i=0;i<8;i++){
			item=new Item();
			item.setContent("11A "+i);
			item.setItemType(type);
			items.add(item);
		}
		for(int i=0;i<20;i++){
			item=new Item();
			item.setContent("11C "+i);
			item.setItemType(type1);
			items.add(item);
		}
		
		for(int i=0;i<20;i++){
			item=new Item();
			item.setContent("14A "+i);
			item.setItemType(type2);
			items.add(item);
		}
		ExamUtil.sortList(items);
		for(SplitCompareable litem:items){
			System.out.println(" "+((Item)litem).getContent());
			//sysout
		}
		System.out.println("after pagelize ");
		//List<Page> pages=ExamUtil.splitToPageForKY(items);
		//for(Page page:pages){
		//	List<Item> itemList=page.getItems();
		//	for(Item ritem:itemList){
		//		System.out.println(" "+ritem.getContent());
		//		//sysout
		//	}
		//	System.out.println("one page end ");
		//}
		
		
	}
	
	public void testSelectMap(){
		String hql="select new map(count(*) as n ) from Test test";
		Map map=(Map)genService.findObjByHql(hql);
		System.out.println("count:"+map.get("n"));
	}

	@Override
	protected void setPath() {
		// TODO Auto-generated method stub
		
	}

}
