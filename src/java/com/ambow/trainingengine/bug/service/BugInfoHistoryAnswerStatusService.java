package com.ambow.trainingengine.bug.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.bug.domain.BugInfo;
import com.ambow.trainingengine.bug.domain.BugInfoHistoryAnswerStatus;
import com.ambow.trainingengine.bug.util.HQLUtil;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.UtilAndTool_L;

public class BugInfoHistoryAnswerStatusService extends HibernateEntityDao<BugInfoHistoryAnswerStatus>{
	public ItemService itemService;
	
	public List<BugInfoHistoryAnswerStatus > listDifferentItemBugInfoHistoryAnswerStatus(Object user ,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus){
		//过滤已经作废的捉虫记录
		return listDifferentItemBugInfoHistoryAnswerStatus(user,bugInfoHistoryAnswerStatus,true);
	};
	
	/**
	 * 取出所有有捉虫记录的题的最后一条捉虫记录
	 * 注：查找已回复题目时,只有题的所有捉虫记录都被回复时才显示
	 * @param user
	 * @param bugInfoHistoryAnswerStatus 查询条件
	 * @param filterAbadon 不包含已作废捉虫记录
	 * @return
	 */
	public List<BugInfoHistoryAnswerStatus > listDifferentItemBugInfoHistoryAnswerStatus(Object user ,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus,boolean filterAbadon){
		List<BugInfoHistoryAnswerStatus> lst = makeHQLUtil(user,bugInfoHistoryAnswerStatus).queryList(this);
		Map<Integer,Boolean> mapItem = new HashMap<Integer,Boolean>();
		Map<Integer,BugInfoHistoryAnswerStatus> mapBugInfoHistoryAnswerStatus = new HashMap<Integer,BugInfoHistoryAnswerStatus>();
		Integer status = null;
		try{
			status = bugInfoHistoryAnswerStatus.getBugInfo().getStatus();
		}catch(Exception e){
		}
		for (BugInfoHistoryAnswerStatus _bIHAS : lst) {
			if(filterAbadon&&_bIHAS.getBugInfo().getStatus()==-1){
				continue;
			}
			Integer itemId = _bIHAS.getHistoryAnswerStatus().getItem().getId();
			//记录此题的最后一条捉虫信息
			if(mapItem.get(itemId)==null){
				mapItem.put(itemId,true);
				mapBugInfoHistoryAnswerStatus.put(itemId, _bIHAS);
			}
			//某题有一条没有回复,则为没有回复状态;
			if(status!=null&&status==1){
				BugInfoHistoryAnswerStatus notReply = new BugInfoHistoryAnswerStatus();
				BugInfo bugInfo = new BugInfo();
				bugInfo.setStatus(0); 
				notReply.setBugInfo(bugInfo);
				List<BugInfoHistoryAnswerStatus> lstHasOneNotReply =makeHQLUtil(user,notReply).queryList(this); 
				for (BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus2 : lstHasOneNotReply) {
					mapBugInfoHistoryAnswerStatus.remove(bugInfoHistoryAnswerStatus2.getBugInfo().getBug().getItem().getId());
				}
			}
		}
		lst.clear();
		if(mapBugInfoHistoryAnswerStatus.size()>0){
			List<Long>  bugInfoHistoryAnswerStatusIds = new ArrayList<Long>(mapBugInfoHistoryAnswerStatus.size());
			for (BugInfoHistoryAnswerStatus b : mapBugInfoHistoryAnswerStatus.values()) {
				bugInfoHistoryAnswerStatusIds.add(b.getId());
			}
			return find("from BugInfoHistoryAnswerStatus where id in("+bugInfoHistoryAnswerStatusIds.toString().replaceAll("\\[|\\]","") +") order by bugInfo.submitTime desc ");
		}else{
			return lst;
		}
	};
	
	/**
	 * 取出所有有捉虫记录的题的最后一条捉虫记录
	 * 注：查找已回复题目时,只有题的所有捉虫记录都被回复时才显示
	 * @param user
	 * @param bugInfoHistoryAnswerStatus 查询条件
	 * @param filterAbadon 不包含已作废捉虫记录
	 * @return
	 * 
	 * 本方法实现了BUG记录查询的分页
	 */
	@SuppressWarnings("unchecked")
	public Page listDifferentItemBugInfoHistoryAnswerStatusPaged(Object user ,
			BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus,boolean filterAbadon, int pageNo){
		HQLUtil<BugInfoHistoryAnswerStatus> hu = makeHQLUtil_L(user,bugInfoHistoryAnswerStatus);
		Page page = this.pagedExecute(hu.getHqlSB().toString(), pageNo, Constants.DEFAULT_PAGE_SIZE, hu.getParaList().toArray());
		List<Integer> lst = (List<Integer>)page.getResult();
		List result = new ArrayList();
		for(int i=0; i<lst.size(); i++){
			Integer itemId = lst.get(i);
			result.add(this.itemService.get(itemId));
		}
		page.setResult(result);
		
		return page;
	};
	/**
	 * 取得该用户所有符合条件的所有虫子
	 * @param sysUser
	 * @param historyId 
	 * @param itemId
	 * @param userType
	 * @return
	 */ 
	public List<BugInfoHistoryAnswerStatus>showItemBugInfoHistoryAnswerStatus(Object user,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus ){
		HQLUtil<BugInfoHistoryAnswerStatus> hqlUtil = makeHQLUtil(user,bugInfoHistoryAnswerStatus);
		//hqlUtil.add(" order by  bugInfo.submitTime desc");
		return hqlUtil.queryList(this);
	}
	
	public Map<String,Object> getItemBS(Object user,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus ){
		Map<String,Object> map = new HashMap<String,Object>(0);
		Integer itemId = null;
		try{
			itemId = bugInfoHistoryAnswerStatus.getHistoryAnswerStatus().getItem().getId();
			
		}catch(Exception e){
			
		}
		try{
			if(itemId==null){
				itemId = bugInfoHistoryAnswerStatus.getBugInfo().getBug().getItem().getId();
			}
		}catch(Exception e){
			
		}
		if(user!=null&&itemId!=null){
			List<BugInfoHistoryAnswerStatus> findLst = makeHQLUtil(user, bugInfoHistoryAnswerStatus).queryList(this);
			if(findLst!=null&&findLst.size()>0){
				BugInfoHistoryAnswerStatus  firstB= findLst.get(0);
				Item item = firstB.getHistoryAnswerStatus().getItem();
				if(item!=null){
					map.put("item", item);
					List<SubItem> subItems = item.getSubItems();
					if(subItems!=null&&subItems.size()>0){
						//如果是一对多
						List<Map<String,Object>> subItemBSList = new ArrayList<Map<String,Object>> (); 
						for (SubItem subItem : subItems) { 
							subItemBSList.add(getSubItemBS(user, subItem));
						}
						map.put("subItemBSList",subItemBSList);
					}else{
						//如果是一对一
						Integer status = null;
						try{
							status = bugInfoHistoryAnswerStatus.getBugInfo().getStatus();
						}catch(Exception e){
						}
						List<BugInfoHistoryAnswerStatus>  realLst = new ArrayList<BugInfoHistoryAnswerStatus>();
						if(status!=null&&status==1){
							BugInfoHistoryAnswerStatus notReply = new BugInfoHistoryAnswerStatus();
							BugInfo bugInfo = new BugInfo();
							bugInfo.setStatus(0); 
							notReply.setBugInfo(bugInfo);
							List<BugInfoHistoryAnswerStatus> lstHasOneNotReply =makeHQLUtil(user,notReply).queryList(this); 
							for (BugInfoHistoryAnswerStatus findBS : findLst) {
								boolean isNotReply=true;
								for (BugInfoHistoryAnswerStatus notReplyBS : lstHasOneNotReply) {
									if(findBS.getHistoryAnswerStatus().getItem().getId()==notReplyBS.getHistoryAnswerStatus().getItem().getId()){
										isNotReply=false;
									};
								}
								if(isNotReply){
									realLst.add(findBS);
								}
							}
							map.put("itemBSes", realLst);
						}else{
							map.put("itemBSes", findLst);
						}
						
					}
				}
			}
		}
		return map;
	}
	 
	public Map<String, Object> getSubItemBS(Object user, SubItem  subItem ) {
		Map<String,Object> subItemBS= new HashMap<String,Object>();
		subItemBS.put("subItem", subItem);
		BugInfoHistoryAnswerStatus _bs = new BugInfoHistoryAnswerStatus();
		HistoryAnswerStatus his = new HistoryAnswerStatus();
		his.setSubItem(subItem);
		_bs.setHistoryAnswerStatus(his );
		subItemBS.put("subItemBSes",makeHQLUtil(user,_bs).queryList(this));
		return subItemBS;
	}
	
	public List<BugInfoHistoryAnswerStatus>showSubItemBugInfoHistoryAnswerStatus(Object user,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus){
		if(user!=null){
			List<BugInfoHistoryAnswerStatus> findLst =  makeHQLUtil(user, bugInfoHistoryAnswerStatus).queryList(this);				
			Map<Integer,Boolean> mapSubItem = new HashMap<Integer,Boolean>();
			Map<Integer,BugInfoHistoryAnswerStatus> mapBugInfoHistoryAnswerStatus = new HashMap<Integer,BugInfoHistoryAnswerStatus>();
			Integer status = null;
			try{
				status = bugInfoHistoryAnswerStatus.getBugInfo().getStatus();
			}catch(Exception e){
			}
			for (BugInfoHistoryAnswerStatus _bIHAS : findLst) {
				if(_bIHAS.getBugInfo().getStatus()==-1){
					continue;
				}
				Integer subItemId = null;
				try{
					subItemId = _bIHAS.getHistoryAnswerStatus().getSubItem().getId();
				}catch(Exception e){
					
				}
				
				//记录此题的最后一条捉虫信息
				if(subItemId!=null&&mapSubItem.get(subItemId)==null){
					mapSubItem.put(subItemId,true);
					mapBugInfoHistoryAnswerStatus.put(subItemId, _bIHAS);
				}
				//某题有一条没有回复,则为没有回复状态;
				if(status==1){
					BugInfoHistoryAnswerStatus notReply = new BugInfoHistoryAnswerStatus();
					BugInfo bugInfo = new BugInfo();
					bugInfo.setStatus(0);
					notReply.setBugInfo(bugInfo);
					List<BugInfoHistoryAnswerStatus> lstHasOneNotReply =makeHQLUtil(user,notReply).queryList(this); 
					for (BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus2 : lstHasOneNotReply) {
						mapBugInfoHistoryAnswerStatus.remove(bugInfoHistoryAnswerStatus2.getHistoryAnswerStatus().getSubItem().getId());
					}
				}
			}
			findLst.clear();
			findLst.addAll(mapBugInfoHistoryAnswerStatus.values());
			return findLst;
		}
		return new ArrayList<BugInfoHistoryAnswerStatus>(0);
	}
	
	 
	/**
	 * 取得该用户所有符合条件的所有虫子
	 * @param sysUser
	 * @param historyId 
	 * @param itemId
	 * @param userType
	 * @return
	 */
	public List<BugInfoHistoryAnswerStatus>list(Object user,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus ){
		return makeHQLUtil(user,bugInfoHistoryAnswerStatus).queryList(this);
	}
	
	/**
	 * 以分页方式取得该用户所有符合条件的虫子
	 * @param sysUser
	 * @param bugInfoHistoryAnswerStatusId
	 * @param bugInfoHistoryAnswerStatus
	 * @param userType
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page page(Object user,BugInfoHistoryAnswerStatus bugInfoHistoryAnswerStatus,int pageNo,Integer pageSize){
		int pSize = 20;
		if(pageSize!=null&&pSize>0){
			pSize=pageSize;
		}
		return makeHQLUtil(user, bugInfoHistoryAnswerStatus).queryPage(this,pageNo,pSize);
	}
	
	private HQLUtil<BugInfoHistoryAnswerStatus> makeHQLUtil(Object user,BugInfoHistoryAnswerStatus bIHAS) {
		HQLUtil<BugInfoHistoryAnswerStatus> hqlUtil=new HQLUtil<BugInfoHistoryAnswerStatus> ();
		if(user !=null){
			hqlUtil.add("from BugInfoHistoryAnswerStatus where 1=1 ");
			if(user instanceof Webuser){ //if("user".equals(userType))
				hqlUtil.add(" and bugInfo.user.id = ? ",((Webuser) user).getId());
			}
			
			try{
				//查询条件 试题id  
				hqlUtil.add(" and historyAnswerStatus.item.id = ? ",bIHAS.getHistoryAnswerStatus().getItem().getId());
			}catch(Exception e){
			}
			
			
			try{
				//查询条件 试题id  
				hqlUtil.add(" and bugInfo.bug.item.id = ? ",bIHAS.getBugInfo().getBug().getItem().getId());
			}catch(Exception e){
			}
			try{
				//查询条件 试题编码
				Integer historyTestStatusId = bIHAS.getHistoryAnswerStatus().getHistoryTestStatus().getId();
				hqlUtil.add(" and historyAnswerStatus.historyTestStatus.id=? ",historyTestStatusId);
			}catch(Exception e){
			}
			try{
				//查询条件 试题编码
				String code = bIHAS.getHistoryAnswerStatus().getItem().getCode();
				if(code!=null&&!"".equals(code)){
					hqlUtil.add(" and historyAnswerStatus.item.code  like('%"+code+"%')");
				}
			}catch(Exception e){
			}
			 
			try{
				//查询条件 试题题干
				String content = bIHAS.getHistoryAnswerStatus().getItem().getContent();
				if(content!=null&&!"".equals(content)){
					hqlUtil.add(" and historyAnswerStatus.item.content like('%"+content+"%')");
				}
			}catch(Exception e){
			}
			
			try{
				//查询条件  子题id  
				hqlUtil.add(" and historyAnswerStatus.subItem.id = ? ",bIHAS.getHistoryAnswerStatus().getSubItem().getId());
			}catch(Exception e){
			}
			
			try{
				//学科
				String subjectCode = bIHAS.getHistoryAnswerStatus().getItem().getSubject().getCode();
				if(subjectCode!=null&&!"".equals(subjectCode.trim())){
					hqlUtil.add(" and historyAnswerStatus.item.subject.code = ?",subjectCode);
				}
			}catch(Exception e){
			}
			
			try{
				//学科
				String subjectCode = bIHAS.getBugInfo().getBug().getItem().getSubject().getCode();
				if(subjectCode!=null&&!"".equals(subjectCode.trim())){
					hqlUtil.add(" and historyAnswerStatus.item.subject.code = ?",subjectCode);
				}
			}catch(Exception e){
			}
						
			try{
				//查询条件  流程实例id 
				if(bIHAS.getBugInfo().getBug().getAsfProcessInstance().getId()!=0){
					hqlUtil.add(" and bugInfo.bug.asfProcessInstance.id = ? ",bIHAS.getBugInfo().getBug().getAsfProcessInstance().getId());
				}
			}catch(Exception e){
			}
			
			try{
				//查询条件  历史状态id  
				hqlUtil.add(" and historyAnswerStatus.id = ? ",bIHAS.getHistoryAnswerStatus().getId());
			}catch(Exception e){
			}

			try{
				//查询条件 试题虫子状态
				hqlUtil.add(" and bugInfo.bug.status = ? ",bIHAS.getBugInfo().getBug().getStatus());
			}catch(Exception e){
			}

			try{
				//查询条件 虫子回复状态
				hqlUtil.add(" and bugInfo.status = ? ",bIHAS.getBugInfo().getStatus());
			}catch(Exception e){
			}
			
			hqlUtil.add(" order by  bugInfo.submitTime desc");
		}
		return hqlUtil;
	}
	
	/**************************************************************
	 * USE: 查询BUG信息
	 * PARAM: user 执行操作的人，bIHAS 查询条件所保存的类
	 * RETURN: 返回生成的HQL语句所保存的类
	 * FOR: BUG查询页面，查询得到BUG信息。因为原来的BUG页面没有实现分页功能，现在要实现分页的功能，
	 * 		故生成此方法，以生成对应的查询语句
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.27.14.23
	 * 
	 */
	private HQLUtil<BugInfoHistoryAnswerStatus> makeHQLUtil_L(Object user,BugInfoHistoryAnswerStatus bIHAS) {
		HQLUtil<BugInfoHistoryAnswerStatus> hqlUtil=new HQLUtil<BugInfoHistoryAnswerStatus> ();
		if(user !=null){
			hqlUtil.add(" select distinct his_ans_sta.item_id ");
			hqlUtil.add(" from bug_info_history_answer_status ");
			hqlUtil.add(" bug_info_history_answer_status ");
			
			hqlUtil.add(" left outer join ( ");
			hqlUtil.add(" select item.id item_id, ");
			hqlUtil.add(" item.code item_code, ");
			hqlUtil.add(" item.content item_content, ");
			hqlUtil.add(" item.subject_code subject_code, ");
			hqlUtil.add(" sub_item.id sub_item_id, ");
			hqlUtil.add(" history_answer_status.id history_answer_status_id, ");
			hqlUtil.add(" history_answer_status.history_test_status_id history_test_status_id ");
			hqlUtil.add(" from history_answer_status history_answer_status ");
			hqlUtil.add(" left outer join item item ");
			hqlUtil.add(" on item.id = history_answer_status.item_id ");
			hqlUtil.add(" left outer join sub_item sub_item ");
			hqlUtil.add(" on sub_item.id=history_answer_status.sub_item_id ) his_ans_sta ");
			hqlUtil.add(" on his_ans_sta.history_answer_status_id=bug_info_history_answer_status.history_answer_status_id ");
			
			hqlUtil.add(" left outer join ( ");
			hqlUtil.add(" select bug.id bug_id, ");
			hqlUtil.add(" bug.status bug_status, ");
			hqlUtil.add(" bug.asf_process_instance_id asf_process_instance_id, ");
			hqlUtil.add(" bug_info.id bug_info_id, ");
			hqlUtil.add(" bug_info.reply_info bug_reply_info, ");
			hqlUtil.add(" bug_info.status bug_info_status, ");
			hqlUtil.add(" bug_info.user_id user_id, ");
			hqlUtil.add(" bug_info.submit_time submit_time ");
			hqlUtil.add(" from bug bug, bug_info bug_info ");
			hqlUtil.add(" where bug_info.bug_id=bug.id ) bug_and_info ");
			hqlUtil.add(" on bug_and_info.bug_info_id=bug_info_history_answer_status.bug_info_id ");
			
			hqlUtil.add(" where 1=1 ");
			if(user instanceof Webuser){ //if("user".equals(userType))
				hqlUtil.add(" and bug_and_info.user_id = ? ",((Webuser) user).getId());
			}
			
			try{
				//查询条件 试题id  
				hqlUtil.add(" and his_ans_sta.item_id = ? ",bIHAS.getHistoryAnswerStatus().getItem().getId());
			}catch(Exception e){
			}
			
			try{
				//查询条件 试题编码
				Integer historyTestStatusId = bIHAS.getHistoryAnswerStatus().getHistoryTestStatus().getId();
				hqlUtil.add(" and his_ans_sta.history_test_status_id=? ",historyTestStatusId);
			}catch(Exception e){
			}
			try{
				//查询条件 试题编码
				String code = bIHAS.getHistoryAnswerStatus().getItem().getCode();
				if(code!=null&&!"".equals(code)){
					hqlUtil.add(" and his_ans_sta.item_code  like ('%"+code+"%')");
				}
			}catch(Exception e){
			}
			 
			try{
				//查询条件 试题题干
				String content = bIHAS.getHistoryAnswerStatus().getItem().getContent();
				if(content!=null&&!"".equals(content)){
					hqlUtil.add(" and his_ans_sta.item_content like('%"+content+"%')");
				}
			}catch(Exception e){
			}
			
			try{
				//查询条件  子题id  
				hqlUtil.add(" and his_ans_sta.sub_item_id = ? ",bIHAS.getHistoryAnswerStatus().getSubItem().getId());
			}catch(Exception e){
			}
			
			try{
				//学科
				String subjectCode = bIHAS.getHistoryAnswerStatus().getItem().getSubject().getCode();
				if(subjectCode!=null&&!"".equals(subjectCode.trim())){
					hqlUtil.add(" and his_ans_sta.subject_code = ?",subjectCode);
				}
			}catch(Exception e){
			}
						
			try{
				//查询条件  流程实例id 
				if(bIHAS.getBugInfo().getBug().getAsfProcessInstance().getId()!=0){
					hqlUtil.add(" and bug_and_info.asf_process_instance_id = ? ",bIHAS.getBugInfo().getBug().getAsfProcessInstance().getId());
				}
			}catch(Exception e){
			}
			
			try{
				//查询条件  历史状态id  
				hqlUtil.add(" and his_ans_sta.history_answer_status_id = ? ",bIHAS.getHistoryAnswerStatus().getId());
			}catch(Exception e){
			}

			try{
				//查询条件 试题虫子状态
				hqlUtil.add(" and bug_and_info.bug_status = ? ",bIHAS.getBugInfo().getBug().getStatus());
			}catch(Exception e){
			}

			try{
				//查询条件 虫子回复状态
				hqlUtil.add(" and bug_and_info.bug_info_status = ? ",bIHAS.getBugInfo().getStatus());
			}catch(Exception e){
			}

			try{
				//查询条件 虫子回复状态
				String rpInfo = bIHAS.getBugInfo().getReplyInfo();
				String likeStr = "";
				if("yzcw".equals(rpInfo))
					likeStr = "纠正了一个严重错误";
				else if("ybcw".equals(rpInfo))
					likeStr = "纠正了一个一般错误";
				else if("xwcw".equals(rpInfo))
					likeStr = "纠正了一个细微错误";
				else if("ww".equals(rpInfo))
					likeStr = "纠错无效，试题无误";
				else if("ey".equals(rpInfo))
					likeStr = "恶意纠错";
				if(UtilAndTool_L.checkNotNullOrZero(likeStr))
					hqlUtil.add(" and bug_and_info.bug_reply_info like '" + likeStr + "%' ");
			}catch(Exception e){
			}

			hqlUtil.add(" order by  bug_and_info.submit_time desc ");
		}
		return hqlUtil;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}
