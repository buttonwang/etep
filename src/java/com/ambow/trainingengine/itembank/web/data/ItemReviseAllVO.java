package com.ambow.trainingengine.itembank.web.data;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.service.AdminManageService;
import com.ambow.trainingengine.util.UtilAndTool_L;

public class ItemReviseAllVO {
	private List<String> irvoList = new ArrayList<String>(); //保存Item 的ID字符串
	private Map<String, ItemReviseVO> irvoMap = new HashMap<String, ItemReviseVO>(); //保存ItemReviseVO，KEY为Item的ID
	private List<ItemReviseVO> result = new ArrayList<ItemReviseVO>(); //页面上面输出时，为方便遍历，本属性存放整理过后的数据
	private Boolean hasSorted = false; //是否已经整理过遍历的数据信息（是否已经把irvoMap中的内容整理到result中
	private SysUser su; //用户的信息
	private Page page; //分页信息
	private String course; //科目信息
	private ReviseQueryVO rqvo; //查询条件VO
	private boolean selfTimeShow = false; //是否只显示自己的审校时间（审校记录页面，显示审校时间的时候，是否只显示
											//当前登录用户的审校时间）
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //工具属性，用于整理审校日期
	
	/***************************************************
	 * @USE: 将保存起来的信息进行整理，以便输出
	 * @PARAM: ...
	 * @RETURN: ...
	 * @FOR: 所有信息拿到页面上面去的时候，不能直接用标签输出，此方法用于把保存下来的所有信息进行整理，
	 * 		保存到另一个List里面，以便页面输出
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2009.03.08.09.53
	 * 
	 */
	public void sort(){
		if(hasSorted) return ; //如果已经初始化过了，就不用再初始化了
		for(String str : irvoList){
			result.add(this.get(str));
		}
		hasSorted = true; //将初始化标志置为已初始化true状态
	}
	
	/***********************************************************
	 * @USE: 封装一个Item对象，得到 ItemReviseVO
	 * @PARAM: item Item对象, adminManageService 用户service，irList 排序好的ItemRevise信息
	 * @RETURN: ItemReviseVO
	 * @FOR: 审校页面兼职老师的纠错信息，进入审校页面查看回复兼职老师的纠错信息时，看到各位老师
	 * 		的纠错信息，并进行回复。这个时候，需要把各兼职老师的纠错信息列表显示出来。本方法用
	 * 		于将Item对象封装成数据已经格式化整理好的ItemReviseVO对象，同时，Item对象中的ItemRevise
	 * 		虽然已经有了审校人的信息，可是，并没有审校人的名字显示，所以，本方法也同样传入用户Service
	 * 		并且在数据封装的过程中，得到审校人的名字等相关信息
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.05.24.16.05
	 * 
	 */
	public static ItemReviseVO getIRVO(Item item, AdminManageService adminManageService, List<ItemRevise> irList){
		if(item==null) return null;
		
		ItemReviseVO irvo = new ItemReviseVO();
		irvo.setId(item.getId());
		irvo.setCode(item.getCode());
		irvo.setContent(item.getContent());
		irvo.setId(item.getId());
		irvo.setType(item.getItemType());
//		irvo.setReviseStatus(item.getReviseStatus());
		irvo.setReviseStatusName(item.getReviseStatusName());
		if(irList==null||irList.size()==0)
			return irvo;
		Iterator<ItemRevise> it = irList.iterator();
		while(it.hasNext()){
			ItemRevise ir = it.next();
			if(ir==null||ir.getReviseRecordTime()==null
					||UtilAndTool_L.checkNullOrZero(ir.getReviseRecord()))
				continue;
			ReviseVO rvo = new ReviseVO();
			rvo.setId(ir.getId());
			rvo.setItemId(item.getId());
			rvo.setRevisor(ir.getReviser());
			try{
				SysUser su  = adminManageService.getSysUserList(ir.getReviser()+"").get(0);
				rvo.setReviseName(su.getRealName());
				rvo.setRevisePerson(su.getUsername());
			} catch (Exception e){
				
			}
			rvo.setReplyType(ir.getReplyType());
			rvo.setReviseRecord(ir.getReviseRecord());
			rvo.setReviseRecordTime(ir.getReviseRecordTime());
			rvo.setReviseReply(ir.getReviseReply());
			rvo.setReviseReplyer(ir.getReviseReplyer());
			if(ir.getReviseReplyer()!=null){
				try{
					SysUser su  = adminManageService.getSysUserList(ir.getReviseReplyer()+"").get(0);
					rvo.setReviseReplyerName(su.getRealName());
				} catch (Exception e){
					
				}
			}
			rvo.setReviseReplyTime(ir.getReviseReplyTime());
			if(ir.getRevisedTime()!=null)
				rvo.setReviseTime(sdf.format(ir.getReviseTime()));
			rvo.setReviseStatus(ir.getReviseStatus());
			rvo.setRemark(ir.getRemark());
			irvo.addRevise(rvo);
		}
		
		return irvo;
	}
	
	/***********************************************************
	 * @USE: 封装一个Item对象，得到 ItemReviseVO
	 * @PARAM: item Item对象, adminManageService 用户servic
	 * @RETURN: ItemReviseVO
	 * @FOR: 审校页面兼职老师的纠错信息，进入审校页面查看回复兼职老师的纠错信息时，看到各位老师
	 * 		的纠错信息，并进行回复。这个时候，需要把各兼职老师的纠错信息列表显示出来。本方法用
	 * 		于将Item对象封装成数据已经格式化整理好的ItemReviseVO对象，同时，Item对象中的ItemRevise
	 * 		虽然已经有了审校人的信息，可是，并没有审校人的名字显示，所以，本方法也同样传入用户Service
	 * 		并且在数据封装的过程中，得到审校人的名字等相关信息
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.07.10.55
	 * 
	 */
	public static ItemReviseVO getIRVO(Item item, AdminManageService adminManageService){
		if(item==null) return null;
		
		ItemReviseVO irvo = new ItemReviseVO();
		irvo.setId(item.getId());
		irvo.setCode(item.getCode());
		irvo.setContent(item.getContent());
		irvo.setId(item.getId());
		irvo.setType(item.getItemType());
//		irvo.setReviseStatus(item.getReviseStatus());
		irvo.setReviseStatusName(item.getReviseStatusName());
		Set<ItemRevise> irs = item.getItemRevises();
		if(irs==null||irs.size()==0)
			return irvo;
		Iterator<ItemRevise> it = irs.iterator();
		while(it.hasNext()){
			ItemRevise ir = it.next();
			if(ir==null||ir.getReviseRecordTime()==null
					||UtilAndTool_L.checkNullOrZero(ir.getReviseRecord()))
				continue;
			ReviseVO rvo = new ReviseVO();
			rvo.setId(ir.getId());
			rvo.setItemId(item.getId());
			rvo.setRevisor(ir.getReviser());
			try{
				SysUser su  = adminManageService.getSysUserList(ir.getReviser()+"").get(0);
				rvo.setReviseName(su.getRealName());
				rvo.setRevisePerson(su.getUsername());
			} catch (Exception e){
				
			}
			rvo.setReplyType(ir.getReplyType());
			rvo.setReviseRecord(ir.getReviseRecord());
			rvo.setReviseRecordTime(ir.getReviseRecordTime());
			rvo.setReviseReply(ir.getReviseReply());
			rvo.setReviseReplyer(ir.getReviseReplyer());
			if(ir.getReviseReplyer()!=null){
				try{
					SysUser su  = adminManageService.getSysUserList(ir.getReviseReplyer()+"").get(0);
					rvo.setReviseReplyerName(su.getRealName());
				} catch (Exception e){
					
				}
			}
			rvo.setReviseReplyTime(ir.getReviseReplyTime());
			if(ir.getRevisedTime()!=null)
				rvo.setReviseTime(sdf.format(ir.getReviseTime()));
			rvo.setReviseStatus(ir.getReviseStatus());
			rvo.setRemark(ir.getRemark());
			irvo.addRevise(rvo);
		}
		
		return irvo;
	}
	
	/**************************************************
	 * @USE: 将完整的试题信息保存起来
	 * @PARAM: item 完整的试题信息
	 * @RETURN: ...
	 * @FOR: 从数据库中查询出来的信息是试题及其相关信息的完整信息，本方法将试题的完整信息进行
	 * 		整理，并保存下来
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.08.09.45
	 * 
	 */
	public void add(Item item){
		if(item==null) return ;
		ItemReviseVO irvo = new ItemReviseVO();
		irvo.setCode(item.getCode());
		irvo.setContent(item.getContent());
		irvo.setId(item.getId());
		irvo.setUser(su);
		irvo.setType(item.getItemType());
		irvo.setSelfTimeShow(this.selfTimeShow);
//		irvo.setReviseStatus(item.getReviseStatus());
		irvo.setReviseStatusName(item.getReviseStatusName());
		this.add(irvo);
		Set<ItemRevise> irs = item.getItemRevises();
		if(irs==null||irs.size()==0)
			return ;
		Iterator<ItemRevise> it = irs.iterator();
		while(it.hasNext()){
			ItemRevise ir = it.next();
			if(ir==null) continue;
			ReviseVO rvo = new ReviseVO();
			rvo.setId(ir.getId());
			rvo.setItemId(item.getId());
			rvo.setRevisor(ir.getReviser());
//			rvo.setReviseName(su.getRealName());
//			rvo.setRevisePerson(su.getUsername());
			rvo.setReplyType(ir.getReplyType());
			rvo.setReviseRecord(ir.getReviseRecord());
			rvo.setReviseRecordTime(ir.getReviseRecordTime());
			rvo.setReviseReply(ir.getReviseReply());
			rvo.setReviseReplyer(ir.getReviseReplyer());
			rvo.setReviseReplyTime(ir.getReviseReplyTime());
			if (ir.getReviseTime()!=null)
				rvo.setReviseTime(sdf.format(ir.getReviseTime()));
			rvo.setReviseStatus(ir.getReviseStatus());
			rvo.setRemark(ir.getRemark());
			irvo.addRevise(rvo);
			this.add(rvo);
		}
	}

	/*******************************************************
	 * @USE: 保存试题信息
	 * @PARAM: irvo 试题信息
	 * @RETURN: ...
	 * @FOR: 从库中得到试题信息，此方法把试题的信息保存下来
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2009.03.05.15.39
	 * 
	 */
	public void add(ItemReviseVO irvo){
		if(irvo==null||irvo.getId()==null) return ;
		String id = irvo.getId() + "";
		if(irvoMap.get(id)!=null) return ;
		irvoList.add(id);
		irvoMap.put(id, irvo);
	}
	
	/*********************************************
	 * @USE: 根据试题的序号，得到试题及相关信息
	 * @PARAM: i 试题序号
	 * @RETURN: 得到的试题的信息（如果序号超出范围，则返回NULL）
	 * @FOR: 该方法用于读取出所有试题信息时候的遍历操作
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.15.41
	 * 
	 */
	public ItemReviseVO get(Integer i){
		return this.irvoMap.get(this.irvoList.get(i));
	}
	
	/**********************************************
	 * @USE: 根据试题的ID得到对应的试题信息
	 * @PARAM: id 试题的ID
	 * @RETURN: 得到的试题信息
	 * @FOR: 根据试题的ID得到某个试题的信息
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.15.43
	 *  
	 */
	public ItemReviseVO get(String id){
		if(UtilAndTool_L.checkNullOrZero(id))
			return null;
		return irvoMap.get(id);
	}
	
	/*******************************************
	 * @USE: 得到已保存的试题的数目
	 * @PARAM: ...
	 * @RETURN: 试题的数目
	 * @FOR: 得到已保存下来的试题的个数
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.15.44
	 * 
	 */
	public Integer size(){
		return this.irvoList.size();
	}
	
	/******************************************
	 * @USE: 保存审校信息
	 * @PARAM: rvo 试题审校信息
	 * @RETURN: ...
	 * @FOR: 从库中得到试题的审校信息以后，保存审校信息到对应的试题下面
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.15.47
	 * 
	 */
	public void add(ReviseVO rvo){
		if(rvo==null) return ;
		String itemId = rvo.getItemId() + "";
		ItemReviseVO irvo = irvoMap.get(itemId);
		if(irvo==null) return ;
		irvo.addRevise(rvo);
	}

	/**********************************************
	 * @USE: 将本页的item的ID信息列表保存起来
	 * @PARAM: ...
	 * @RETURN: 含有分页信息及ID的List的信息VO
	 * @FOR: 当页面从试题信息页面跳转到试题审校页面的时候，需要将分页信息及查询条件信息，还有
	 * 		所有本页试题的ID信息保存下来，以便在跳转后的页面使用
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.18.15.48
	 * 
	 */
	public ItemPageIDsVO getIPIDsVO(){
		ItemPageIDsVO ipidsVO = new ItemPageIDsVO();
		ipidsVO.setRqvo(rqvo);
		ipidsVO.setIdList(this.irvoList);
		
		return ipidsVO;
	}
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public SysUser getSu() {
		return su;
	}

	public void setSu(SysUser su) {
		this.su = su;
	}
	
	public List<String> getIrvoList() {
		return irvoList;
	}

	public void setIrvoList(List<String> irvoList) {
		this.irvoList = irvoList;
	}

	public Map<String, ItemReviseVO> getIrvoMap() {
		return irvoMap;
	}

	public void setIrvoMap(Map<String, ItemReviseVO> irvoMap) {
		this.irvoMap = irvoMap;
	}

	public List<ItemReviseVO> getResult() {
		this.sort();
		return result;
	}

	public void setResult(List<ItemReviseVO> result) {
		this.result = result;
	}
	
	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	/*******************************************
	 * @USE: 测试方法
	 * @PARAM: ...
	 * @RETURN: ...
	 * @FOR: 目前数据库中还没有数据，而却需要对页面上面的功能进行显示和测试，
	 * 		此方法用于生成部分的测试数据，以支持页面显示
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.09.18.12
	 * 
	 */
	public void testMethod(){
		ItemType it = new ItemType();
		it.setCode("HNN001");
		it.setName("测试选择题");
		int j = 100;
		int step = 1;
		for(int i=1; i<21; i++){
			Item item = new Item();
			item.setCode("2010_"+i);
			item.setId(i);
			item.setContent(i+"content");
			item.setItemType(it);
			Set<ItemRevise> irSet = new HashSet<ItemRevise>();
			for(; j<100+step*(2); j++){
				if(j%4==0){
					j++;
					j++;
					break;
				}
				ItemRevise ir = new ItemRevise();
				ir.setId(j);
				ir.setReviser(su.getId());
				ir.setReviseTime(new java.util.Date());
				irSet.add(ir);
			}
			item.setItemRevises(irSet);
			step++;
			this.add(item);
		}
		this.page = new Page(0, 20, 20, this.irvoList);
	}
	
	public ReviseQueryVO getRqvo() {
		return rqvo;
	}

	public void setRqvo(ReviseQueryVO rqvo) {
		this.rqvo = rqvo;
	}

	public boolean isSelfTimeShow() {
		return selfTimeShow;
	}

	public void setSelfTimeShow(boolean selfTimeShow) {
		this.selfTimeShow = selfTimeShow;
	}

	public static void main(String... args){
		int a = 8;
		int b = 0;
		int result = 0;
		for(int i=0; i<5; i++){
			b = b*10 + a;
			result = result + b;
		}
		System.out.println(result);
	}
}
