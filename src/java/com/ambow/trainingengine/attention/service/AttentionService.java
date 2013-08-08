package com.ambow.trainingengine.attention.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.attention.domain.Evaluation;
import com.ambow.trainingengine.attention.domain.ItemAttention;
import com.ambow.trainingengine.attention.domain.ItemExtraInfo;
import com.ambow.trainingengine.attention.domain.ItemTag;
import com.ambow.trainingengine.attention.domain.LearnNote;
import com.ambow.trainingengine.attention.domain.Tag;
import com.ambow.trainingengine.attention.domain.TagContent;
import com.ambow.trainingengine.attention.util.KeyValueList;
import com.ambow.trainingengine.bug.service.BugService;
import com.ambow.trainingengine.bug.util.HQLUtil;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.message.domain.Message;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.ParamObject;

public class AttentionService extends HibernateEntityDao<ItemAttention> {
	
	private static final int PointType_dedicate = 4;//积分类型之奉献积分
	
	public static int Page_SIZE =20;
	private SimpleJdbcTemplate jdbcTemplate ;

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * 根据指定条件显示 关注
	 * @param p
	 * @param user
	 * 
	 * @return
	 */
	public Map<String,Object>show(ParamObject p,Webuser user,Integer pageNo,Long newNodeInstanceId){
		Map<String,Object> map = new HashMap<String,Object>(4);
		if(p!=null&&user!=null&&user.getId()!=null){
			Integer itemId = p.getInteger("itemId");
			Integer historyTestStatusId = p.getInteger("historyTestStatusId");
			Long nodeInstanceId = p.getLong("nodeInstanceId");
			String userId = user.getId();
			
			HistoryAnswerStatus historyAnswerStatus=null;
			if(nodeInstanceId != null && nodeInstanceId ==0){
				nodeInstanceId = newNodeInstanceId;
			}
			if(historyTestStatusId==null||historyTestStatusId==0&&nodeInstanceId!=null){
				List<Integer>  historyTestStatusIdLst=  find ("select id from HistoryTestStatus where asfNodeInstance.id=?  ",nodeInstanceId);
				//System.out.println("test="+historyTestStatusIdLst.toString());
				String historyTestStatusIdLstStr = historyTestStatusIdLst.toString().replaceAll("\\[|\\]", "");
				historyAnswerStatus = (HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id in ("+historyTestStatusIdLstStr+")", itemId) ;
			}else{
				historyAnswerStatus=(HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id=?", itemId,historyTestStatusId) ;
			}
			
			Item item = null;
			if(itemId!=null){
				item=this.get(Item.class,itemId);
			}
			
			if(item!=null && historyAnswerStatus!=null){
				map.put("historyAnswerStatus", historyAnswerStatus);
					map.put("historyTestStatusId", historyAnswerStatus.getHistoryTestStatus().getId());
					//用户的关注，如果为空说明 用户还没有关注
					ItemAttention itemAttention = (ItemAttention) this.findObjByHql("from ItemAttention where webuser.id =? and item.id=? ",userId,itemId);
					map.put("itemAttention",itemAttention);
					
					Evaluation evaluation = null;
					LearnNote learnNote = null;
					TagContent tagContent =null;
					  
					if(itemAttention!=null){
						evaluation=(Evaluation)this.findObjByHql("from Evaluation where webuser.id=? and itemAttention.id=?",userId,itemAttention.getId());
						learnNote= (LearnNote) this.findObjByHql("from LearnNote  where webuser.id=? and itemAttention.id=?",userId,itemAttention.getId());
						tagContent = (TagContent)this.findObjByHql("from TagContent  where webuser.id=? and itemAttention.id=?", userId,itemAttention.getId());
					}
					map.put("evaluation",evaluation);
					map.put("learnNote",learnNote);
					map.put("tagContent",tagContent);
					map.put("evaluationLst",KeyValueList.getList("1 -1 0", "好评 差评 说不清楚 "));
					map.put("renqi" ,this.findObjByHql("select count(*) from ItemAttention where  item.id =?",item.getId() ));
					map.put("evaluationGood" ,this.findObjByHql("select count(*) from Evaluation where  evaluation =1" ));
					map.put("evaluationBad"  ,this.findObjByHql("select count(*) from Evaluation where  evaluation =-1"));
					map.put("evaluationNomal",this.findObjByHql("select count(*) from Evaluation where  evaluation =0" ));
					map.put("recentTagSet", getRecentItemTag(itemId));
					if(!"no".equals(p.get("more"))){
						map.put("itemExtraInfoLst",this.find("from ItemExtraInfo where  item.id=?", itemId));
						HQLUtil<LearnNote> hqlUtil = new HQLUtil<LearnNote>();
						hqlUtil.add("from LearnNote where item.id=? order by insertTime desc", itemId);
						if(pageNo==null||pageNo<1){
							pageNo=1;
						}
						map.put("page",hqlUtil.queryPage(this,pageNo,20));
					}
					map.put("item", item);
			}
		}
		return map;
	}
	
	/**
	 * 前台用户  关注列表 
	 * @param p
	 * @param webuser
	 * @return
	 */
	public Map<String,Object> list(ParamObject p,Webuser webuser,Integer pageNo){
		Map<String,Object> map = new HashMap<String,Object>(4);
		if(webuser!=null){
			HQLUtil<ItemAttention> hqlUtil = new HQLUtil<ItemAttention>();
			hqlUtil.add( "from ItemAttention where webuser.id=? and state!=-1",webuser.getId());
			hqlUtil.add("and state=?",p.getInteger("state"));
			hqlUtil.add("order by insertTime desc" );
			
			
			if(pageNo==null||pageNo<0){
				pageNo=1;
			}
			Page pageItemAttention = hqlUtil.queryPage(this, pageNo, 20);
			map.put("pageItemAttention", pageItemAttention);
		}
		return map;
	}
	void filterByShowType(String showType,List<Integer> itemIdLst){
		if(showType!=null&&!"全部".equals(showType)){
			if(showType.equals("无精华")){
				//只保留有精华的
				itemIdLst.retainAll(this.find("select distinct(item.id) from ItemExtraInfo where noteSummary is null or noteSummary =''"));
			}else if(showType.equals("无笔记")){
				//除去有笔记的
				itemIdLst.removeAll(this.find("select distinct(item.id) from LearnNote"));				
			}else if(showType.equals("有精华")){
				//只保留有精华的
				itemIdLst.retainAll(this.find("select distinct(item.id) from ItemExtraInfo where noteSummary is not null and noteSummary !=''" ));
			}
		}
	};
	
	void filterByTagIds(String[] tagIdsArr,List<Integer> itemIdLst){
		String idBySplit = "";
		if(tagIdsArr!=null){
			for (int i = 0; i < tagIdsArr.length; i++) {
				if(i>0){
					idBySplit+=",";
				}
				idBySplit+= tagIdsArr[i];	
			}
			itemIdLst.retainAll(this.find ("select distinct(item.id) from ItemTag where tag.id in ("+idBySplit+")"));
		}
	}
	void filterByItemType(String itemType,List<Integer>itemIdLst){
		if(itemType!=null&&!"全部".equals(itemType)){
			itemIdLst.retainAll(this.find("select id from Item where itemType.name like('%"+itemType+"%')"));
		}
	}
	
	List<Integer> orderBy(String orderBy ,String descOrAsc,List idLst ){
		if(idLst!=null&&idLst.size()>0){
			String sql = "SELECT DISTINCT  item_id "
				+"FROM item_attention i "
				+"LEFT JOIN  (SELECT popularity,note_Amount,note_Summary IS NOT NULL AS  hasSummary ,id FROM item_extra_info) ie "
				+"ON i.item_id = ie.id  "
				+"LEFT JOIN (SELECT id,item_type_code FROM item) it "
				+"ON i.item_id=it.id  "
				+"LEFT JOIN (SELECT NAME,CODE FROM  item_type ) iii "
				+"ON it.item_type_code = iii.code ";
			String order = "ORDER BY popularity DESC,note_Amount DESC,hasSummary DESC ,name DESC";
			String str = idLst.toString().replaceAll("\\[|\\]", "").trim();
			String where = "";
			if(!"".equals(str)){
				where =" where it.id in("+str+")";
				sql+=where;
			}
			if(orderBy!=null&&!"".equals(orderBy.trim())){
				String da = "desc";
				if(descOrAsc!=null&&descOrAsc.toLowerCase().equals("asc")){
					da="asc";
				};
				if("popularity".equals(orderBy)){
					order = " ORDER BY popularity "+da+",note_Amount DESC,hasSummary DESC ,name DESC "; 
				}else if("totalNote".equals(orderBy)){
					order = " ORDER BY note_Amount "+da+",popularity DESC,hasSummary DESC ,name DESC ";  					
				}else if("noteSummary".equals(orderBy)){
					order = " ORDER BY hasSummary "+da+" ,popularity DESC,note_Amount DESC ,name DESC";
				}else if("itemType".equals(orderBy)){
					order = " ORDER BY name "+da+", popularity DESC,note_Amount DESC ,hasSummary DESC "; 
				}else if("itemDiffculty".equals(orderBy)){
					if(!"".equals(str)){
						sql+=" where it.id in("+str+")";
					}
					return this.findExecute("SELECT DISTINCT it.id from item it"+where+" order by it.difficulty_value "+da);
				}
			}
			return this.findExecute(sql+order);
		}
		return  new ArrayList<Integer>(0);
	}
	
	public static List<Integer> getPageNOLst(int pageNo,int pageSize,List<Integer> all){
		List<Integer> lst = new ArrayList<Integer>(pageSize);
		if(all!=null){
			if(pageNo<1){
				pageNo=1;
			}
			int end = pageNo*pageSize;
			int start = (pageNo-1)*pageSize;
			if(all.size()<end){
				end = all.size();
			}
			if(start>=all.size()){
				int page = all.size()/pageSize;
				int pageDiv = all.size()%pageSize;;
				if(pageDiv==0&&page!=0){
					page-=1;
				}
				start=(page)*pageSize;
			}
			if(start<end){
				for (int i = start; i < end; i++) {
					lst.add(all.get(i));
				}
			}
		}
		return lst;
	}
	
	/** 
	 *  后台管理员 关注列表 
	 * @param p
	 * @param sysUser
	 * @return
	 */
	public Map<String,Object> listAdmin(ParamObject p,SysUser sysUser,Integer pageNo){
		Map<String,Object> map = new HashMap<String,Object>(4);
		String subjectCode = p.get("subjectCode");
		if(subjectCode!=null&&!"".equals(subjectCode.trim())){
			List<Integer> itemIds_HasAttention= this.find("select distinct(item.id) from ItemAttention where state!=-1 and item.subject.code=?",subjectCode);
			filterByShowType(p.get("showType"),itemIds_HasAttention);
			filterByTagIds(p.getStrArr("tags"),itemIds_HasAttention);
			filterByItemType(p.get("itemTypeP"),itemIds_HasAttention);
			List<Integer> orderedItemIdLst = orderBy(p.get("orderBy"),p.get("descOrAsc"),itemIds_HasAttention);
			if(pageNo==null ||pageNo<1){
				pageNo=1;
			}
			List<Integer> pageOrderIdLst = getPageNOLst(pageNo,Page_SIZE,orderedItemIdLst);
			List<Map<String,Object>> dataLst = new ArrayList <Map<String,Object>>(pageOrderIdLst.size()); 
			for (Integer itemId : pageOrderIdLst) {
				Item ia =this.get(Item.class,itemId);
				Map<String ,Object> iaVO = new HashMap<String ,Object> ();
				
				iaVO.put("item", ia);
				iaVO.put("itemExtraInfo", this.findObjByHql("from ItemExtraInfo where  item.id=?", ia.getId()));
				dataLst.add(iaVO);
			}
			map.put("page", new Page(Page.getStartOfPage(pageNo, Page_SIZE), orderedItemIdLst.size(), Page_SIZE, dataLst));
		}
		map.put("Tags", getTags(subjectCode));
		map.put("itemTypeNameLst", getItemType(subjectCode));
		return map;
	}
	
	public Map<String,Object>showAdmin(ParamObject p,SysUser sysUser,Integer pageNo){
		Map<String,Object> map = new HashMap<String,Object>(4);
		if(p!=null&&sysUser!=null){ 
			Integer itemId=p.getInteger("itemId");
			Item item = this.get(Item.class,itemId);
			if(item!=null){
				List<Map<String ,Object>> lst = new ArrayList<Map<String ,Object>>();				
				HQLUtil<ItemAttention> hqlUtil = new HQLUtil<ItemAttention>();
				hqlUtil.add("select distinct a from ItemAttention a join a.learnNotes n where a.item.id=? " +
						"order by n.state desc, n.flowerNum desc, n.eggNum, n.insertTime desc  ",
						item.getId());
				Page page = hqlUtil.queryPage(this, pageNo, 10);
				Collection<ItemAttention>  itemAttentionLst = page.getResult();
				for (ItemAttention ia : itemAttentionLst) {
						Map<String ,Object> iaVO = new HashMap<String ,Object> ();
						iaVO.put("itemAttention", ia);
						HistoryAnswerStatus historyAnswerStatus=ia.getHistoryAnswerStatus() ;
						if(historyAnswerStatus!=null){
							iaVO.put("itemAnswerVO",  bugSerivce.getItemAnswerVO(historyAnswerStatus.getHistoryTestStatus().getId(),itemId));
						}
						iaVO.put("learnNote",this.findObjByHql("from LearnNote where item.id = ? and webuser.id=? and isShare=1 ", itemId,ia.getWebuser().getId()));
						lst.add(iaVO);
				}
				page.setResult(lst);
				map.put("pageIaVO", page);
				map.put("itemExtraInfo", this.findObjByHql("from ItemExtraInfo where item.id=?", itemId) );
				map.put("item", item);
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param sysUser
	 * @param p 
	 * @return
	 */
	public boolean forbiddenOrReviewLearnNote(SysUser sysUser,ParamObject p){
		Integer learnNoteId = p.getInteger("learnNoteId");
		String type = p.get("type");
		Webuser webUser =null;
		if(p.get("userId")!=null){
			webUser = this.get(Webuser.class,p.get("userId"));
		}
		//如果 webuser存在表明是屏蔽或显示 所有用户笔记 
		if(webUser!=null){
			List<LearnNote>learnNoteLst = this.find("from LearnNote where webuser.id=? and state!=-1 and state!=-3", webUser.getId());//所有非单条屏蔽
			for (LearnNote learnNote : learnNoteLst) {
				if("show".equals(type)){
					if (isDevote(learnNote)) learnNote.setState(1);
					else learnNote.setState(0);
				}else{
					learnNote.setState(-2);//多条屏蔽
				}
				save(learnNote);
			}
		}else if(learnNoteId!=null){
			LearnNote learnNote =(LearnNote)this.findObjByHql("from LearnNote where id=?", learnNoteId);
			if(learnNote!=null){
				if("show".equals(type)){
					if (isDevote(learnNote)) learnNote.setState(1);
					else learnNote.setState(0);
				}else{
					learnNote.setState(-1);//单条屏蔽
				}
				save(learnNote);
			}
		}
		return true;
	}

	private boolean isDevote(LearnNote learnNote) {
		ItemExtraInfo itemExtraInfo = this.get(ItemExtraInfo.class, learnNote.getItem().getId());
		String devote = (itemExtraInfo.getDevote()==null)?"":itemExtraInfo.getDevote();
		return devote.contains(learnNote.getWebuser().getLoginName());
	}
	
	public boolean saveNoteSummary(ParamObject p, SysUser sysUser){
		Integer itemId=p.getInteger("itemId");
		if(p!=null&&sysUser!=null&&itemId!=null){
			saveOrUpdateItemExtraInfo(itemId, p.get("noteSummary"), p.get("devote"));
			return true;
		}
		return false;
	}
	
	public String addDevote(String devoteOld,String addDevote ){
		String str []= (devoteOld==null?"":devoteOld).split(";|,");
		String addDeVote[] = (addDevote==null?"":addDevote).split(";|,");
		Set<String> set = new HashSet<String>(str.length+addDeVote.length);
		for (int i = 0; i < str.length; i++) {
			if(!str[i].trim().equals(""))
				set.add(str[i]);
		}
		for (int i = 0; i < addDeVote.length; i++) {
			if(!addDeVote[i].trim().equals(""))
				set.add(addDeVote[i]);
		}
		return set.toString().replaceAll(", ", ",").replaceAll("\\[|\\]", "");
	}
	
	public long getItemAttentionCount(Webuser user,Integer itemId){
		if(user!=null&&itemId!=null&&itemId!=0){
			return (Long )findObjByHql("select count(*) from ItemAttention where item.id=? and webuser.id=? and (state>=0)",itemId,user.getId());
		}
		return 0;
	}
	
	public void disAttention(Webuser user,ParamObject p){
		if(p.getInteger("itemId")!=null&&user!=null&&user.getId()!=null){
			ItemAttention ia = (ItemAttention)this.findObjByHql("from ItemAttention where item.id=? and webuser.id=?", p.getInteger("itemId"),user.getId());
			if(ia!=null){
				cancelAttention(ia.getId());
			}
		}
	}

	// 取消关注
	public void cancelAttention(Integer attentionId){
		ItemAttention attention = this.get(attentionId);
		if(attention!=null){
			LearnNote ln = (LearnNote)this.findObjByHql("from LearnNote where itemAttention.id=?", attention.getId());
			if(ln!=null){
				ln.setState(-3);
				int itemAttionId = ln.getItemAttention().getId();
				long processId = ln.getItemAttention().getHistoryAnswerStatus().getHistoryTestStatus()
					.getAsfNodeInstance().getProcessInstance().getId();
				setMembershipPoint(processId,"取消关注减奉献积分 ItemAttention id is "+itemAttionId,
						ExamUtil.pointCauseForNoteCancelShare, ExamUtil.pointOfNoteCancelShare, PointType_dedicate);//减奉献积分
				save(ln);
				
				sendMessage(processId, ExamUtil.messageForNoteCancelShare, 0);
			}
		}
		attention.setState(-1);
		save(attention);
	}

	// 重新关注
	public ItemAttention redoAttention(Integer attentionId) {
		ItemAttention attention = this.get(attentionId);
		if(attention!=null){
			LearnNote ln = (LearnNote)this.findObjByHql("from LearnNote where itemAttention.id=?", attention.getId());
			if(ln!=null&&(ln.getState()!=-1||ln.getState()!=-2)){
				ln.setState(0);
				save(ln);
			}
		}
		attention.setState(0);
		save(attention);
		
		return attention;
	}
	
	/**
	 * 设置用户积分
	 * @param processId 流程实例id
	 * @param remark 操作说明 
	 * @param cause  积分原因
	 * @param point 点数
	 * @param pointType 点数类型
	 */
	public void setMembershipPoint(long processId,String remark, String cause, int point,int pointType){
		MembershipPoint mp = get(MembershipPoint.class,processId);
		ProcessInstance asfProcessInstance=get(ProcessInstance.class,processId);
		if(asfProcessInstance!=null){
			if(mp==null){
				mp = new MembershipPoint();
				mp.setAsfProcessInstance(asfProcessInstance);
			}
			Integer p = 0;
			if(pointType==1||pointType==2||pointType==3||pointType==4){
				switch(pointType){
					case 1:
						p=mp.getDiligence();break;
					case 2:
						p=mp.getWisdom();break;
					case 3:
						p=mp.getCourage();break;
					case 4:
						p=mp.getDedicate();break;
					case 5:
						p=mp.getPercipience();break;
				}
				
				if(p==null){
					p = 0;
				}
				switch(pointType){
					case 1:
						mp.setDiligence(p+point);break;
					case 2:
						mp.setWisdom(p+point);break;
					case 3:
						mp.setCourage(p+point);break;
					case 4:
						mp.setDedicate(p+point);break;
					case 5:
						mp.setPercipience(p+point);break;
				}
				save(mp);
				MembershipPointHistory mph = new MembershipPointHistory();
				mph.setAsfProcessInstance(asfProcessInstance);
				mph.setOperateTime(new Date());
				mph.setPoint(point);
				mph.setPointType(pointType);
				mph.setPointCause(cause);
				mph.setRemark(remark);
				save(mph);
			}
		}
	}
	
	
	private void sendMessage(long processId, String content, int refId) {
		ProcessInstance asfProcessInstance=get(ProcessInstance.class, processId);
		
		Message message = new Message();
		message.setProcessInstanceId(asfProcessInstance.getId());
		message.setUserId(asfProcessInstance.getActor());
		message.setSource(2);
		message.setType(2);
		message.setContent(content);
		message.setRefId(refId);
		message.setPublishTime(new Date());
		message.setState(0);
		this.save(message);
	}
	
	/**
	 * 增加或修改一条关注
	 * @param p 相关输入
	 * @param webuser 用户
	 * @return
	 */
	public boolean addOrUpdate(ParamObject p,Webuser webuser){
		if(p!=null&&webuser!=null){
			Integer itemId = p.getInteger("itemId");
			Integer historyTestStatusId = p.getInteger("historyTestStatusId");
			
			if(itemId!=null&&historyTestStatusId!=null){
				Item item = this.get(Item.class, itemId);
				HistoryAnswerStatus historyAnswerStatus=(HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id=?", itemId,historyTestStatusId) ;
				if(item!=null&&historyAnswerStatus!=null){
					Date nowDate= new Date();
					ItemAttention itemAttention=null;
					
					//已经存储过？
					Integer itemAttentionId =p.getInteger("itemAttentionId");
					if(itemAttentionId!=null){
						itemAttention = this.get(ItemAttention.class,itemAttentionId);
					}
					if(itemAttention==null){
						itemAttention = new ItemAttention();
					}
					itemAttention.setInsertTime(nowDate);
					itemAttention.setState(0);
					itemAttention.setItem(item);
					itemAttention.setWebuser(webuser);
					itemAttention.setHistoryAnswerStatus(historyAnswerStatus);
					itemAttentionId = (Integer)save1(itemAttention);
					//设置评价
					if(p.getInteger("evaluation")!=null){
						Evaluation evaluation = null;
						Integer evaluationId = p.getInteger("evaluationId");
						if(evaluationId!=null){
							evaluation = this.get(Evaluation.class,evaluationId);
						}
						if(evaluation==null){
							evaluation = new Evaluation();
						}
						evaluation.setEvaluation(p.getInteger("evaluation"));
						evaluation.setInsertTime(nowDate);
						evaluation.setItem(item);
						evaluation.setItemAttention(itemAttention);
						evaluation.setWebuser(webuser);
						save(evaluation);
					}
					//设置learnNote
					String learnContent = p.get("content");
					long processId = historyAnswerStatus.getHistoryTestStatus().getAsfNodeInstance().getProcessInstance().getId();
					if(learnContent!=null){
						LearnNote learnNote = null;
						Integer learnNoteId = p.getInteger("learnNoteId");
						boolean isNewLearnNote = true;
						if(learnNoteId!=null){
							learnNote = this.get(LearnNote.class,learnNoteId);
						}
						//如果不为空则 保存笔记
						if(!"".equals(learnContent.trim())){
							if(learnNote==null){
								learnNote = new LearnNote();
							}else{
								isNewLearnNote=false;
							}
							if(learnNote.getState()==null){
								learnNote.setState(0);
							}else if(learnNote.getState()==-3){
								if (isDevote(learnNote)) learnNote.setState(1);
								else learnNote.setState(0);
							}
							learnNote.setAnswerId(historyAnswerStatus.getId());
							learnNote.setContent(learnContent);
							Integer isShare = p.getInteger("isShare");
							if(isShare==null) isShare=0;
							learnNote.setIsShare(isShare); 
							learnNote.setInsertTime(nowDate);
							learnNote.setItem(item);
							learnNote.setItemAttention(itemAttention);
							learnNote.setWebuser(webuser);
							save(learnNote);
							if(isNewLearnNote){//是新笔记
								if(isShare==1){//且共享 
									setMembershipPoint(processId,"共享笔记加奉献积分 ItemAttention id is "+learnNote.getItemAttention().getId(),
											ExamUtil.pointCauseForNoteShare, ExamUtil.pointOfNoteShare, PointType_dedicate);//加奉献积分
									sendMessage(processId, ExamUtil.messageForNoteShare, itemAttentionId);
								}
							}else{//不是新笔记
								if(isShare==0){//且不共享
									setMembershipPoint(processId,"取消共享减奉献积分 ItemAttention id is "+learnNote.getItemAttention().getId(),
											ExamUtil.pointCauseForNoteCancelShare, ExamUtil.pointOfNoteCancelShare, PointType_dedicate);//减奉献积分
									sendMessage(processId, ExamUtil.messageForNoteCancelShare, itemAttentionId);
								}
							}
						}else{
							//如果为空则删除笔记
							if(learnNote!=null){
								setMembershipPoint(processId,"ItemAttention id is "+learnNote.getItemAttention().getId(),
										ExamUtil.pointCauseForNoteDelete,  ExamUtil.pointOfNoteDelete, PointType_dedicate);//扣除奉献积分
								this.remove(learnNote);
								sendMessage(processId, ExamUtil.messageForNoteDelete, itemAttentionId);
							}
						}
					}

					String tags = p.get("tags");
					//向标签增加标签或增加标签计数 
					
					TagContent tagContent=null;
					Integer tagContentId= p.getInteger("tagContentId");
					if(tagContentId!=null){
						tagContent = this.get(TagContent.class, tagContentId);
					}
					if(tagContent==null){
						tagContent = new TagContent();
					}
					tagContent.setInsertTime(nowDate);
					tagContent.setItem(item);
					tagContent.setItemAttention(itemAttention);
					Set<String> tagSet = getStringSet(tags);
					tagContent.setTag(tagSet.toString().replaceAll("\\[|,|\\]", ""));
					tagContent.setWebuser(webuser);
					save(tagContent);
					excuteHql("delete from  ItemTag where item.id=? and webuser.id=?", itemId,webuser.getId());					
					if(tags!=null&&!"".equals(tags.trim())){
						for (String tagstr : tagSet) {
							if(!"".equals(tagstr)){
								Tag tagInDB = (Tag)this.findObjByHql("from Tag where tag=?", tagstr);							
								if(tagInDB==null){
									tagInDB =new Tag();
									tagInDB.setTag(tagstr);
									tagInDB.setNum(1);
								}else{
									//如果存在则标签增加 一个;
									tagInDB.setNum(tagInDB.getNum()+1);	
								}
								save(tagInDB);
								ItemTag itemTag =(ItemTag)this.findObjByHql("from ItemTag where item.id=? and tag.tag=? and webuser.id=?", itemId,tagstr,webuser.getId());
								if(itemTag==null){
									itemTag=new ItemTag();
									itemTag.setItem(item);
									itemTag.setTag(tagInDB);
									itemTag.setWebuser(webuser);
									save(itemTag);
								}
							}
						}
					}
					saveOrUpdateItemExtraInfo(itemId);
					return true;	
				}
			}
		}
		return false;
	}
	public static Set<String> getStringSet(String tags){
		Set<String> set = new HashSet<String>();
		if(tags!=null){
			String[] arr = tags.split(",| |;|，");
			for(int i =0;i<arr.length;i++){
				if(!"".equals(arr[i].trim()))
					set.add(arr[i].trim());
			}
		}
		return set;
	}
	
	// 前台的保存，不care精华笔记
	private ItemExtraInfo saveOrUpdateItemExtraInfo(Integer itemId){
		ItemExtraInfo  itemExtraInfo =(ItemExtraInfo) this.findObjByHql("from ItemExtraInfo where item.id=?",itemId);
		Date nowDate = new Date();
		if(itemExtraInfo==null){
			itemExtraInfo = new ItemExtraInfo();
			itemExtraInfo.setId(itemId);
			itemExtraInfo.setItem(this.get(Item.class,itemId));
		}
		
		Long popularity =(Long)this.findObjByHql("select count(*)from ItemAttention where item.id=?", itemId);
		itemExtraInfo.setPopularity(popularity);//人气
		Long goodEvaluation = (Long)this.findObjByHql("select count(*)from Evaluation where item.id=? and evaluation=1", itemId);
		double positiveRate=0;
		if(popularity>0){
			positiveRate = goodEvaluation/popularity;
		}
		itemExtraInfo.setPositiveRate(positiveRate);//好评率
		itemExtraInfo.setNoteAmount((Long)this.findObjByHql("select count(*) from LearnNote where item.id=? and state>=0", itemId));//笔记数
		itemExtraInfo.setInsertTime(nowDate);
		save(itemExtraInfo);
		return itemExtraInfo ;
	}
	
	// 前台的保存，care精华笔记, 不care别的信息
	private ItemExtraInfo saveOrUpdateItemExtraInfo(Integer itemId, String noteSummary, String devote){
		ItemExtraInfo itemExtraInfo =(ItemExtraInfo) this.findObjByHql("from ItemExtraInfo where item.id=?",itemId);
		Set<String> origDevoteSet = getStringSet(itemExtraInfo.getDevote());
		Set<String> newDevoteSet  = getStringSet(devote);
		
		// 先把所有精华笔记设置为非精华，稍后再逐个设置
		//String hsql = "update LearnNote a set state=0 where item.id=? and state=1";
		//this.excuteHql(hsql, itemId);
					
		itemExtraInfo.setNoteSummary(noteSummary.trim());
		itemExtraInfo.setDevote(devote);
		itemExtraInfo.setInsertTime(new Date());
		save(itemExtraInfo);
		
		for(String origDevote: origDevoteSet) {
			//原始贡献人不在新的贡献人中则扣减积分和发取消精华笔记的消息
			if (!newDevoteSet.contains(origDevote)) {
				removeStarNote(itemId, origDevote);
			}
		}
		
		for(String newDevote: newDevoteSet) {
			//新贡献人不在原始贡献人中则加积分和发设为精华笔记的消息
			if (!origDevoteSet.contains(newDevote)) {
				joinStarNote(itemId, newDevote);
			}
		}
		
		return itemExtraInfo ;
	}
	
	private void removeStarNote(Integer itemId, String origDevote) {
			LearnNote ln = (LearnNote)this.findObjByHql("from LearnNote where webuser.loginName=? and item.id=?",
					origDevote, itemId);
					
			if(ln.getState()==1) {
				long processId = ln.getItemAttention().getHistoryAnswerStatus().getHistoryTestStatus()
					.getAsfNodeInstance().getProcessInstance().getId();
				ln.setState(0);
				save(ln);
				
				setMembershipPoint(processId, "取消精华减去奉献积分 ItemAttention id is "+ln.getItemAttention().getId(), 
						ExamUtil.pointCauseForNoteCancel, ExamUtil.pointOfNoteStarCancel, PointType_dedicate);//加奉献积分
				sendMessage(processId, ExamUtil.messageForNoteCancel, ln.getItemAttention().getId());
			}
	}
	
	private void joinStarNote(Integer itemId, String newDevote) {
		LearnNote ln = (LearnNote)this.findObjByHql("from LearnNote where webuser.loginName=? and item.id=?",
				newDevote, itemId);
		
		
		if(ln.getState()!=null) {
			long processId = ln.getItemAttention().getHistoryAnswerStatus().getHistoryTestStatus()
				.getAsfNodeInstance().getProcessInstance().getId();
			ln.setState(1);
			save(ln);
			
			setMembershipPoint(processId, "精华加奉献积分 ItemAttention id is "+ln.getItemAttention().getId(), 
					ExamUtil.pointCauseForNoteStar, ExamUtil.pointOfNoteStar, PointType_dedicate);//加奉献积分
			
			sendMessage(processId, ExamUtil.messageForNoteStar, ln.getItemAttention().getId());
		}
	}
	
	/**
	 * 扔鸡蛋
	 * @param p （需包含learnNoteId）
	 * @return
	 */
	public int throwEgg(ParamObject p,Webuser webuser){
		//TODO 如何实现一个用户只能对一条笔记扔一次鸡蛋 
		Integer learnNoteId = p.getInteger("learnNoteId");
		if(learnNoteId!=null){
			LearnNote learnNote = this.get(LearnNote.class,learnNoteId);
			if(learnNote!=null){
				Integer eggNum = learnNote.getEggNum();
				if(eggNum==null){
					learnNote.setEggNum(1);
				}else{
					learnNote.setEggNum(eggNum+1);
				}
				save(learnNote);
				return eggNum;
			}
		}		
		return 0;
	}
	
	/**
	 * 献花
	 * @param p （需包含learnNoteId）
	 * @return
	 */
	public int addFlower(ParamObject p,Webuser webuser){
		//TODO 如何实现一个用户对一条笔记只能献一次花 
		Integer learnNoteId = p.getInteger("learnNoteId");
		if(learnNoteId!=null){
			LearnNote learnNote = this.get(LearnNote.class,learnNoteId);
			if(learnNote!=null){
				Integer flowerNum = learnNote.getFlowerNum();
				if(flowerNum==null){
					learnNote.setFlowerNum(1);
				}else{
					learnNote.setFlowerNum(flowerNum+1);
				}
				save(learnNote);
				return flowerNum;
			}
		}		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	private List<Tag> getTags(String subjectCode) {
		String hsql = "select distinct t from ItemTag i join i.tag t join i.item m where m.subject.code=? order by t.num desc ";
		Query query = this.createQuery(hsql, subjectCode).setMaxResults(10);
		return (List<Tag>)query.list();
	}
	
	public List<String> getItemType(String subjectCode) {
		List<String> r = new ArrayList<String>();
		String sql = "select distinct replace(replace(name, '一对一', ''), '一对多', '') as name2" +
				" from item_type where subject_code = ?";
		List<Map<String, Object>> itemTypeList = this.getJdbcTemplate().queryForList(sql, subjectCode);
		for(Map<String, Object> itemType: itemTypeList) {
			r.add((String)itemType.get("name2"));
		}
		return r;
	}
	
	public BugService bugSerivce ;
	public BugService getBugSerivce() {
		return bugSerivce;
	}

	public void setBugSerivce(BugService bugSerivce) {
		this.bugSerivce = bugSerivce;
	}
	
	private List<String> getRecentItemTag(Integer itemId) {
		Query query = createQuery("select distinct t.tag from ItemTag i join i.tag t where i.item.id=? order by t.num desc ", itemId)
					 .setFirstResult(0).setMaxResults(10);
		return (List<String>)query.list();
	}
	 	
	
	/**
	 * 获取OrderNum列表(用于创建菜单树)
	 * 
	 * @param processInstanceId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getOrderNumList(long processInstanceId,String userId) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select distinct d.order_num ");
		sqlBuff.append("from item_attention a ");
		sqlBuff.append("left join current_answers_status b on a.item_id=b.item_id ");
		sqlBuff.append("left join asf_node_instance c on b.node_instance_id=c.id ");
		sqlBuff.append("left join asf_node d on c.node_id=d.id ");
		sqlBuff.append("where c.process_instance_id=? ");
		if(userId != null){
			sqlBuff.append("and a.user_id='"+userId+"' ");
		}
		sqlBuff.append("order by order_num ");

		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(),processInstanceId);
		return mapList;
	}
	
	/**
	 * 获取节点列表(用于创建菜单树)
	 * 
	 * @param processInstanceId
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getNodeList(long processInstanceId,String orderNumArr) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select b.id,b.name,b.node_type,b.order_num,b.node_group_id ");
		sqlBuff.append("from asf_node_instance a ");
		sqlBuff.append("left join asf_node b on a.node_id=b.id ");
		sqlBuff.append("where a.process_instance_id=? and b.node_type='GROUP' ");
		if(orderNumArr != null && !orderNumArr.equals("")){
			sqlBuff.append(" and b.order_num in("+orderNumArr+") ");
		}
		//sqlBuff.append("and b.order_num in("+orderNumArr+") ");
		sqlBuff.append("order by order_num ");

		List<Map<String, Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		return mapList;
	}

	/**
	 * 获取关注的试题数量
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getAttentionAmount(Long processDefinitionId,String userId,String orderNum,String type,String tag) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select distinct a.item_id  from item_attention a ");

		sqlBuff.append("left join current_answers_status b on a.item_id = b.item_id ");
		sqlBuff.append("left join item_extra_info e on a.item_id=e.id ");
		if(type != null ){
			sqlBuff.append("left join item c on a.item_id=c.id ");
			sqlBuff.append("left join item_type d on c.item_type_code=d.code ");
		}
		if(tag != null && !tag.trim().equals("")){
			sqlBuff.append("left join item_tag f on a.item_id = f.item_id ");	
		}
		if(userId != null){
			sqlBuff.append("where a.user_id='"+userId+"' ");
		}else{
			sqlBuff.append("where 1=1 ");
		}
		sqlBuff.append("and a.state>=0 ");

		sqlBuff.append("and b.node_instance_id in ");
		sqlBuff.append("( ");
		sqlBuff.append("	select a.id as node_instance_id ");
		sqlBuff.append("	from  asf_node_instance a ");
		sqlBuff.append("	left join asf_node b on a.node_id=b.id ");

		sqlBuff.append("	where b.process_definition_id="+processDefinitionId);

		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%' ");
		}
		sqlBuff.append(") ");
		if(type != null ){
			sqlBuff.append(type);
		}
		if(tag != null && !tag.trim().equals("")){
			sqlBuff.append(" and f.tag_id="+tag+" ");
		}
		if(userId == null){
			sqlBuff.append(" order by e.popularity desc,e.note_amount desc, e.sys_user_id desc,e.insert_time desc ");
			sqlBuff.append(" limit 0,500 ");
		}else{
			sqlBuff.append(" order by e.insert_time desc,e.popularity desc,e.note_amount desc, e.sys_user_id desc ");
		}

		List<Map<String,Object>> itemList = this.getJdbcTemplate().queryForList(sqlBuff.toString());
		return itemList;
	}

	/**
	 * 获取笔记的试题数量
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getNoteAmount(long processDefinitionId,String userId,String orderNum,String typeStr,String tag) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select count(distinct a.id) as amount from learn_note a ");
		sqlBuff.append("left join item_attention g on a.attention_id = g.id ");
		sqlBuff.append("left join current_answers_status b on a.item_id = b.item_id ");
		if(typeStr != null ){
			sqlBuff.append("left join item c on a.item_id=c.id ");
			sqlBuff.append("left join item_type d on c.item_type_code=d.code ");
		}
		if(tag != null && !tag.trim().equals("")){
			sqlBuff.append("left join item_tag f on a.item_id = f.item_id ");
			
		}
		sqlBuff.append("where a.user_id=? and a.state >=0 ");

		sqlBuff.append("and b.node_instance_id in ");
		sqlBuff.append("( ");
		sqlBuff.append("	select a.id as node_instance_id ");
		sqlBuff.append("	from  asf_node_instance a ");
		sqlBuff.append("	left join asf_node b on a.node_id=b.id ");
		sqlBuff.append("	where b.process_definition_id="+processDefinitionId);
		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%' ");
		}
		sqlBuff.append(") and g.state>=0  ");

		if(typeStr != null ){
			sqlBuff.append(typeStr);
		}
		if(tag != null && !tag.trim().equals("")){
			sqlBuff.append(" and f.tag_id="+tag+" ");
		}
		
		Integer amount = this.getJdbcTemplate().queryForInt(sqlBuff.toString(),
				userId);
		return amount;
	}

	/**
	 * 获取试题关注的列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getItemAttentionList(Long processDefinitionId,String userId,String orderNum,String orderStr,String type,String tag,Integer page,String user,String itemIdStr) {
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select distinct a.id,a.item_id,c.content,b.popularity,");
		sqlBuff.append("case when h.note_amount is null then 0 when h.note_amount is not null then h.note_amount end as amount");
		sqlBuff.append(",b.insert_time,");
		sqlBuff.append("c.difficulty_value,d.name,b.note_summary as state,a.history_answer_status_id,");
		if(userId == null ){
			sqlBuff.append("case when e.user_id ='"+user+"' and e.state>=0 then e.user_id  when e.user_id !='"+user+"' then null end as note_id ");
		}else{
			sqlBuff.append("case when e.state>=0 then e.user_id when e.state<0 then null end as note_id ");
		}
		sqlBuff.append("from item_attention a ");
		sqlBuff.append("left join item_extra_info b on a.item_id=b.id ");
		sqlBuff.append("left join item c on a.item_id=c.id ");
		sqlBuff.append("left join item_type d on c.item_type_code=d.code ");
		sqlBuff.append("left join learn_note e on a.id=e.attention_id ");
		sqlBuff.append("left join current_answers_status g on a.item_id = g.item_id ");
		//add by yjq 2009-6-16 
		//通过再次计算取代试题扩展表中note_amount字段
		sqlBuff.append("left join ( ");
		sqlBuff.append("select a.item_id,count(a.id) as note_amount from learn_note a ");
		sqlBuff.append("left join item_attention b on a.attention_id = b.id ");
		if(itemIdStr.length()>0){
			sqlBuff.append("where a.item_id in("+itemIdStr+") and b.state>=0 and a.state>=0 ");
		}else{
			sqlBuff.append("where b.state>=0 and a.state>=0 ");
		}

		sqlBuff.append(" and a.is_share=1 ");

		sqlBuff.append("group by a.item_id ");
		sqlBuff.append(") h on a.item_id = h.item_id ");
		
		if(tag != null && !tag.trim().equals("")){
			sqlBuff.append("left join item_tag f on a.item_id = f.item_id ");
			sqlBuff.append("where f.tag_id="+tag+" ");
		}else{
			sqlBuff.append("where 1=1 ");
		}
		
		sqlBuff.append("and a.state>=0 ");

		sqlBuff.append("and g.node_instance_id in ");
		sqlBuff.append("( ");
		sqlBuff.append("	select a.id as node_instance_id ");
		sqlBuff.append("	from  asf_node_instance a ");
		sqlBuff.append("	left join asf_node b on a.node_id=b.id ");

		sqlBuff.append("	where b.process_definition_id="+processDefinitionId);

		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%' ");
		}
		sqlBuff.append(") ");

		if(userId != null){
			sqlBuff.append("and a.user_id='"+userId+"' ");
		}
		if(type != null ){
			sqlBuff.append(type);
		}
		if(itemIdStr.length()>0){
			sqlBuff.append(" and a.item_id in("+itemIdStr+") ");
		}
		if(orderStr == null || orderStr.equals("")){
			if(userId == null){
				sqlBuff.append("order by b.popularity desc,amount desc, state desc,item_id asc,note_id desc,b.insert_time desc ");
			}else{
				sqlBuff.append("order by b.insert_time desc,b.popularity desc,amount desc, state desc,item_id asc,note_id desc ");
			}
		}else{
			sqlBuff.append("order by "+orderStr+", item_id asc");
		}

		List<Map<String, Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString());
		return mapList;
	}
	
	/**
	 * 获取标签列表
	 * @param processInstanceId
	 * @param userId
	 * @param orderNum
	 * @return
	 */
	public List<Map<String,Object>> getTagList(long processDefinitionId,String userId,String orderNum,Integer more){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select distinct a.id,a.tag from item_attention d ");
		sqlBuff.append("right join item_tag c on c.item_id = d.item_id ");
		sqlBuff.append("left join tag a on a.id = c.tag_id ");
		
		sqlBuff.append("left join current_answers_status b on c.item_id = b.item_id ");
		sqlBuff.append("where b.node_instance_id in ");
		sqlBuff.append("( ");
		sqlBuff.append("	select a.id as node_instance_id ");
		sqlBuff.append("	from  asf_node_instance a ");
		sqlBuff.append("	left join asf_node b on a.node_id=b.id ");
		sqlBuff.append("	where b.process_definition_id="+processDefinitionId);
		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%' ");
		}
		sqlBuff.append(") ");
		sqlBuff.append("and c.user_id =? ");

		sqlBuff.append("and d.state>=0 ");
		if(more == null){
			sqlBuff.append("limit 0,50  ");
		}else{
			sqlBuff.append("limit 51,50");
		}
		List<Map<String,Object>> tagList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), userId);
		return tagList;
	}
	
	/**
	 * 获取最热标签
	 * @return
	 */
	public List<Map<String,Object>> getTagListTop(long processDefinitionId,String orderNum){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select a.id,a.tag,count(a.tag) as amount from tag a  ");
		sqlBuff.append("left join item_tag b on a.id=b.tag_id ");
		sqlBuff.append("where b.item_id in ( ");
		sqlBuff.append("	select c.item_id from current_answers_status c");
		sqlBuff.append("	left join item_attention d on c.item_id = d.item_id");
		sqlBuff.append("	where c.node_instance_id in ( 	");
		sqlBuff.append("		select a.id as node_instance_id ");
		sqlBuff.append("		from asf_node_instance a ");
		sqlBuff.append("		left join asf_node b on a.node_id=b.id 	");
		sqlBuff.append("		where b.process_definition_id="+processDefinitionId);
		if(orderNum != null && !orderNum.equals("")){
			sqlBuff.append(" and b.order_num like '"+orderNum+"%' ");
		}
		sqlBuff.append("	) and d.state>=0 ");
		sqlBuff.append(") ");
		sqlBuff.append("group by a.tag ");
		sqlBuff.append("order by amount desc limit 0,50  ");
		List<Map<String,Object>> tagList = this.getJdbcTemplate().queryForList(sqlBuff.toString());
		return tagList;
	}
	
	/**
	 * 批量处理关注
	 * @param user
	 * @param ids
	 */
	public void disAttentionBatch(String userId,String ids){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("update item_attention a set a.state=-1 ");
		sqlBuff.append("where a.user_id=? and a.id in("+ids+") ");
		this.getJdbcTemplate().update(sqlBuff.toString(),userId);
	}
	
	public void disAttentionBatch(String ids){
		String[] idArray = ids.split(",");
		for(String id: idArray) {
			cancelAttention(Integer.valueOf(id));
		}
	}
	
	/**
	 * 获取首页的关注列表。
	 * @return
	 */
	public List<Map<String,Object>> getMainPageAttention(long processInstanceId){
		String sql = "select a.id, t.content " +
				"from item_attention a inner join history_answer_status ha on a.history_answer_status_id = ha.id " +
				"inner join history_test_status ht on ha.history_test_status_id = ht.id " +
				"inner join asf_node_instance n on ht.node_instance_id = n.id  " +
				"inner join item t on a.item_id = t.id " +
				"where a.state>=0 and n.process_instance_id = ? " +
				"order by a.insert_time desc limit 4";
		
		List<Map<String,Object>> attentionList = this.getJdbcTemplate().queryForList(sql.toString(), processInstanceId);
		for (Map<String,Object> map: attentionList) {
			String content = (String)map.get("content");
			content = content.replaceAll("(<(/||[^>])*>)", "").replace("&quot;", "\"").trim();			if(content.length()>20) content = content.substring(0,20)+"...";
			map.put("content", content);
		}
		return attentionList;
	}
	
}
