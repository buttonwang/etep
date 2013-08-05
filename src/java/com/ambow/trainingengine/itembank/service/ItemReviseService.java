package com.ambow.trainingengine.itembank.service;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemRevise;
import com.ambow.trainingengine.itembank.web.data.ItemReviseAllVO;
import com.ambow.trainingengine.itembank.web.data.ItemReviseVO;
import com.ambow.trainingengine.itembank.web.data.ItemStatusVO;
import com.ambow.trainingengine.itembank.web.data.ReviseQueryVO;
import com.ambow.trainingengine.itembank.web.data.ReviseVO;
import com.ambow.trainingengine.itembank.web.data.UserReviseVO;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.UtilAndTool_L;

public class ItemReviseService extends HibernateEntityDao<ItemRevise> {
	private SimpleJdbcTemplate jdbcTemplate ;

	public GradeService gradeService;
	public KnowledgePointService knowledgePointService;
	public ItemService itemService;
	
	/**************************************************
	 * @USE: 审校记录的查询
	 * @PARAM: pageNO 页码，rqvo 查询条件VO，selfTimeShow 是否只显示登录用户本人的审核记录信息
	 * @RETURN: ItemReviseAllVO
	 * @FOR: 试题审校记录查询页面，查询得到所有符合条件的记录。同时，记录的审校时间，实现是显示所有审校时间，
	 * 		还是显示自己审校对应题目的时间
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.22.13.25
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ItemReviseAllVO getAllItemRevise(Integer pageNo, ReviseQueryVO rqvo, boolean selfTimeShow){
		if(pageNo==0) pageNo = 1;
		Page page = this.pagedQuery(rqvo.generateHQL(), pageNo, Constants.DEFAULT_PAGE_SIZE);
		if(page!=null) {
			int totalPage = new Long(page.getTotalPageCount()).intValue();
			if(totalPage>0&&totalPage<pageNo.intValue()){
				page = null;
				page = this.pagedQuery(rqvo.generateHQL(), totalPage, Constants.DEFAULT_PAGE_SIZE);
			}
		}
		ItemReviseAllVO iravo = new ItemReviseAllVO();
		iravo.setSelfTimeShow(selfTimeShow);
		iravo.setPage(page);
		iravo.setSu(rqvo.getSu());
		Collection result = page.getResult();
		if(result==null||result.size()==0) return iravo;
		for(Object o : result)
			iravo.add((Item)((Object[])o)[0]);
		
		return iravo;
	}
	
	/**************************************************
	 * @USE: 审校记录的查询
	 * @PARAM: pageNO 页码，rqvo 查询条件VO，selfTimeShow 是否只显示登录用户本人的审核记录信息
	 * @RETURN: ItemReviseAllVO
	 * @FOR: 试题审校记录查询页面，查询得到所有符合条件的记录。同时，记录的审校时间，实现是显示所有审校时间，
	 * 		还是显示自己审校对应题目的时间
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.22.13.25
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ItemReviseAllVO getAllItemReply(Integer pageNo, ReviseQueryVO rqvo, boolean selfTimeShow){
		if(pageNo==0) pageNo = 1;
//		Page page = this.pagedQuery(rqvo.generateReplayHQL(), pageNo, Constants.DEFAULT_PAGE_SIZE);
		Page page = this.pagedExecute(rqvo.generateReplayHQL2(), pageNo, Constants.DEFAULT_PAGE_SIZE);
		if(page!=null) {
			int totalPage = new Long(page.getTotalPageCount()).intValue();
			if(totalPage>0&&totalPage<pageNo.intValue()){
				page = null;
				page = this.pagedExecute(rqvo.generateReplayHQL2(), totalPage, Constants.DEFAULT_PAGE_SIZE);
			}
		}
		ItemReviseAllVO iravo = new ItemReviseAllVO();
		iravo.setSelfTimeShow(selfTimeShow);
		iravo.setPage(page);
		Collection result = page.getResult();
		if(result==null||result.size()==0) return iravo;
		for(Object o : result){
			Integer itemId = (Integer)o;
			iravo.add(this.itemService.get(itemId));
		}
		
		return iravo;
	}
	

	/**************************************************
	 * @USE: 审校统计的查询
	 * @PARAM: pageNO 页码，rqvo 查询条件VO，reviser 审校人，correctnum 纠错数
	 * @RETURN: ItemReviseAllVO
	 * @FOR: 试题审校统计记录查询页面，查询得到所有符合条件的记录。
	 * 		
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.10.00
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ItemReviseAllVO getItemReviseByReviser(Integer pageNo, ReviseQueryVO rqvo,String reviser,String correctnum){
		if(pageNo==0) pageNo = 1;
		Page page = this.pagedExecute(rqvo.statisticsHQL(reviser,correctnum,true), pageNo, Constants.DEFAULT_PAGE_SIZE);
		if(page!=null) {
			int totalPage = new Long(page.getTotalPageCount()).intValue();
			if(totalPage>0&&totalPage<pageNo.intValue()){
				page = null;
				page = this.pagedExecute(rqvo.statisticsHQL(reviser,correctnum,true), totalPage, Constants.DEFAULT_PAGE_SIZE);
			}
		}
		ItemReviseAllVO iravo = new ItemReviseAllVO();
//		iravo.setSelfTimeShow(selfTimeShow);
		iravo.setPage(page);
//		iravo.setSu(rqvo.getSu());
		Collection result = page.getResult();
		if(result==null||result.size()==0) return iravo;
		for(Object o : result){
			Object[] oArr = (Object[])o;
			
			ItemReviseVO irvo = new ItemReviseVO();
			Item item=this.get(Item.class, (Integer)oArr[0]);
			irvo.setContent(item.getContent());
			irvo.setId(item.getId());
			irvo.setType(item.getItemType());
			irvo.setReviserstatus((Integer)oArr[1]);
			iravo.add(irvo);
			ReviseVO rvo =new ReviseVO();
			if(oArr[2]!=null)
				rvo.setReviseRecordTime((Date)oArr[2]);
			if(oArr[3]!=null)
				rvo.setReviseReplyTime((Date)oArr[3]);
			rvo.setId((Integer)oArr[4]);
			irvo.addRevise(rvo);
			iravo.add(rvo);
			
		}
		return iravo;
	}
	
	/**************************************************
	 * @USE: 审校人的查询
	 * @PARAM: rqvo 查询条件VO
	 * @RETURN: List<UserReviseVO>
	 * @FOR: 审校人。
	 * 		
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.10.00
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<UserReviseVO> getReviser(ReviseQueryVO rqvo){
		
		List<UserReviseVO> irvo = new ArrayList<UserReviseVO>();

		Collection result = this.findExecute(rqvo.reviserHQL(),null);
		if(result==null||result.size()==0) return irvo;
		UserReviseVO urvo=null;
//		SysUser sysUser=null;
		for(Object o : result){
//			sysUser=(SysUser)o;
			Object[] oArr = (Object[])o;
			urvo=new UserReviseVO();
			urvo.setLoginId(String.valueOf((Integer)oArr[0]));
			urvo.setUserName((String)oArr[1]);
			irvo.add(urvo);
		}
		return irvo;
	}
	

	/********************************************
	 * USE: 查询试题审校状态
	 * PARAM: pageNo 页码, rqvo 保存所有查询条件信息得到查询信息的VO
	 * RETURN: Page 查询得到的结果
	 * FOR: 试题审校状态，查询得到每道试题的审校信息，及审校人数，审校通过的人数，审校纠错人数
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.05.21.16.49
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Page getAllItemStatus(Integer pageNo, ReviseQueryVO rqvo) {
		if(pageNo==0) pageNo = 1;
		Page page = this.pagedExecute(rqvo.generateStatusSQL(), pageNo, Constants.DEFAULT_PAGE_SIZE);
		if(page!=null){
			int totalPage = new Long(page.getTotalPageCount()).intValue();
			if(totalPage>0&&totalPage<pageNo.intValue()){
				page = null;
				page = this.pagedExecute(rqvo.generateStatusSQL(), totalPage, Constants.DEFAULT_PAGE_SIZE);
			}
		}
		Collection result = page.getResult();
		if(result==null||result.size()==0) return page;
		List<ItemStatusVO> isvoList = new ArrayList<ItemStatusVO>();
		for(Object o : result){
			Object[] oArr = (Object[])o;
			if(oArr==null||oArr.length<4) continue;
			Integer itemId = (Integer)oArr[0];
			BigDecimal allPasses = (BigDecimal)oArr[1];
			BigDecimal allPass = (BigDecimal)oArr[2];
			BigDecimal reviseRec = (BigDecimal)oArr[3];
			
			Item item = this.get(Item.class, itemId);
			
			ItemStatusVO isvo = new ItemStatusVO();
			isvo.setItem(item);
			isvo.setAllPass(allPass);
			isvo.setAllPasses(allPasses);
			isvo.setReviseRec(reviseRec);
			
			isvoList.add(isvo);
		}
		page.setResult(null);
		page.setResult(isvoList);
		
		return page;
	}
	
	/************************************************
	 * USE: 得到课程下所有有效题目的数量，及审校通过的题目的数量
	 * PARAM: course 课程信息
	 * RETURN: Object[] 第一个元素为所有题目的数量，第二个元素为审校通过的题目的数量
	 * FOR: 页面查询时，需要显示所有题目的数目及审校通过的题目的数量
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.05.21.17.19
	 * 
	 */
	public BigDecimal[] getAllItemPass(ReviseQueryVO rqvo) {
////		Object[] o = (Object[])this.getSession().createSQLQuery(rqvo.generateStatusSQLCount()).list().get(0);
//		BigDecimal[] resArr = new BigDecimal[4];
////		resArr[0] = (BigDecimal)o[0];
////		resArr[1] = (BigDecimal)o[1];
//		String sql = "select count(*) " + rqvo.generateStatusSQLCount();
////		String sql = "select count(*) from item where status>0 and subject_code='" + course + "' ";
//		resArr[0]  = (BigDecimal)this.getSession().createSQLQuery(sql).list().get(0);
//		sql = "select count(*) " + rqvo.generateStatusSQLCount() + " and item.revise_status=1 ";
////		sql = "select count(*) from item where status>0 and revise_status=1 and subject_code='" + course + "' ";
//		resArr[1]  = (BigDecimal)this.getSession().createSQLQuery(sql).list().get(0);
////			String sql = "select max(countall), max(count_revise) from ";
////			sql += " (select sum(1) countall, ";
////			sql += " sum(case when revise_status is null then 0 else revise_status end) count_revise ";
////			sql += " from item_revise group by item_id) ct";
////			o = (Object[])this.getSession().createSQLQuery(sql).list().get(0);
////			resArr[2] = (BigDecimal)o[0];
////			resArr[3] = (BigDecimal)o[1];
//		sql = "select max(count) from (select count(*) count from item_revise where revise_status=1 group by item_id) ct ";
//		resArr[3] = (BigDecimal)this.getSession().createSQLQuery(sql).list().get(0);
		BigDecimal[] resArr = new BigDecimal[4];
		Object[] o = (Object[])this.getSession().createSQLQuery(rqvo.generateStatusSQLCount()).list().get(0);
		resArr[0] = new BigDecimal((BigInteger)o[0]);
		resArr[1] = (BigDecimal)o[1];
		String sql = "select max(all_count), max(all_revised) from (";
		sql += " select count(ir.reviser) all_count, ";
		sql += " SUM(case when ir.revised_time is not null then 1 else 0 end) all_revised ";
		sql += " from item_revise ir left outer join item on item.id=ir.item_id ";
		sql += " where item.subject_code='M' and item.status>0 ";
		sql += " group by item_id) allcount;";
		o = (Object[])this.getSession().createSQLQuery(sql).list().get(0);
		resArr[2] = new BigDecimal((BigInteger)o[0]);
		resArr[3] = (BigDecimal)o[1];
		
		return resArr;
	}
	
	/********************************************************
	 * USE: 按时期倒顺序，取出某题下的所有纠错信息
	 * PARAM: itemId 试题ID
	 * RETURN: 查询知得到试题下所有的纠错信息
	 * FOR: 试题纠错页面，查看题目下面所有的纠错信息时，排序有些混乱。故使用此方法，依次按时期的倒序取出
	 * 		各条纠错信息
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.05.24.16.14
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ItemRevise> getAllItemRevises(Integer itemId) {
		String hql = " from ItemRevise ir where ir.item.id=? order by reviseRecordTime asc";
		List<ItemRevise> irList = this.find(hql, itemId);
		
		return irList;
	}
	
	/**************************************************
	 * @USE: 纠错类型的查询
	 * @PARAM: rqvo 查询条件VO，reviser 审校人，correctnum 纠错数
	 * @RETURN: Map
	 * @FOR: 试题审校统计记录查询页面，查询得到所有符合条件的记录。
	 * 		
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.16.00
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,BigInteger> getErrorstatusList( ReviseQueryVO rqvo,String reviser,String correctnum){
		Collection result = this.findExecute(rqvo.errorStatusHQL(reviser, correctnum),null);
		Map<String,BigInteger> errormap=new HashMap<String,BigInteger>();

		errormap.put("1", new BigInteger("0"));
		errormap.put("2", new BigInteger("0"));
		errormap.put("3", new BigInteger("0"));
		errormap.put("4", new BigInteger("0"));
		errormap.put("5", new BigInteger("0"));
		if(result==null||result.size()==0) return errormap;
		for(String dataKey : errormap.keySet()){    
			for(Object o : result){
				Object[] oArr = (Object[])o;
				
				if(dataKey.equals( ((Integer)oArr[0] ).toString()) ){
					errormap.put(dataKey, (BigInteger)oArr[1]);
				}
				
			}
		}
		return errormap;
	}
	
	/**************************************************
	 * @USE: 审核状态的查询
	 * @PARAM: rqvo 查询条件VO，reviser 审校人，correctnum 纠错数
	 * @RETURN: Map
	 * @FOR: 试题审校统计记录查询页面，查询得到所有符合条件的记录。
	 * 		
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.16.00
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map<String,BigInteger> getVerifystatusList( ReviseQueryVO rqvo,String reviser,String correctnum){
		
		Collection result = this.findExecute(rqvo.verifystatusHQL(reviser, correctnum),null);
		Map<String,BigInteger> statusmap=new HashMap<String,BigInteger>();

		statusmap.put("0", new BigInteger("0"));
		statusmap.put("1", new BigInteger("0"));
		if(result==null||result.size()==0) return statusmap;
		for(String dataKey : statusmap.keySet()){    
			for(Object o : result){
				Object[] oArr = (Object[])o;
				
				if(dataKey.equals( ((Integer)oArr[0] ).toString()) ){
					statusmap.put(dataKey, (BigInteger)oArr[1]);
				}
				
			}
		}
		return statusmap;
		
	}
	
	/************************************************
	 * USE: 得到做题的得分与总分的比例
	 * PARAM: rqvo 查询条件VO，reviser 审校人，correctnum 纠错数
	 * RETURN: 比例
	 * FOR: 试题审校统计记录查询页面，查询得到所有符合条件的记录。
	 * 
	 * AUTHOR: 邓新宇
	 * DATE: 2010.05.26.10.19
	 * 
	 */
	public double getScorerate(ReviseQueryVO rqvo,String reviser,String correctnum) {
		Double[] resArr = new Double[2];
		String sql = rqvo.getscore1HQL(reviser, correctnum);

		resArr[0]  = (Double)this.getSession().createSQLQuery(sql).list().get(0);
		sql = rqvo.getscore2HQL(reviser, correctnum);

		resArr[1]  = (Double)this.getSession().createSQLQuery(sql).list().get(0);
		
		double rate=0.00;
		if(resArr[1].compareTo(new Double(0))!=0  )
			rate=new  BigDecimal(Double.toString(resArr[0])).divide( new  BigDecimal(Double.toString(resArr[1])),2,BigDecimal.ROUND_HALF_UP).doubleValue();
		
		return rate;
	}
	
	/**************************************************
	 * @USE: 状态统计的查询
	 * @PARAM: pageNO 页码，rqvo 查询条件VO，reviser 审校人，correctnum 纠错数
	 * @RETURN: ItemReviseAllVO
	 * @FOR: 试题审校统计记录查询页面，查询得到所有符合条件的记录。
	 * 		
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.10.00
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ItemReviseAllVO getItemByCondition(Integer pageNo, ReviseQueryVO rqvo,String reviser,String correctnum,String cond,Integer condition){
		if(pageNo==0) pageNo = 1;
		Page page = this.pagedExecute(rqvo.getHQLByCondition(reviser,correctnum,cond,condition), pageNo, Constants.DEFAULT_PAGE_SIZE);
		if(page!=null) {
			int totalPage = new Long(page.getTotalPageCount()).intValue();
			if(totalPage>0&&totalPage<pageNo.intValue()){
				page = null;
				page = this.pagedExecute(rqvo.getHQLByCondition(reviser,correctnum,cond,condition), totalPage, Constants.DEFAULT_PAGE_SIZE);
			}
		}
		ItemReviseAllVO iravo = new ItemReviseAllVO();

		iravo.setPage(page);

		Collection result = page.getResult();
		if(result==null||result.size()==0) return iravo;
		for(Object o : result){
			Integer itemId = (Integer)o;
			Item item = this.get(Item.class, itemId);
			iravo.add(item);
		}
		return iravo;
	}
	

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public GradeService getGradeService() {
		return gradeService;
	}
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}
	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}
