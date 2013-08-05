package com.ambow.trainingengine.bug.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.bug.domain.Bug;
import com.ambow.trainingengine.bug.domain.BugInfo;
import com.ambow.trainingengine.bug.domain.BugInfoHistoryAnswerStatus;
import com.ambow.trainingengine.bug.domain.ReplyInfoTemplate;
import com.ambow.trainingengine.bug.util.HQLUtil;
import com.ambow.trainingengine.bug.util.ItemAnswerVO;
import com.ambow.trainingengine.bug.util.SubItemAnswerVO;
import com.ambow.trainingengine.bug.web.data.BugInfoHistoryAnswerStatusVO;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.util.MathUtil;
import com.ambow.trainingengine.message.domain.Message;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.UtilAndTool_L;

public class BugService extends HibernateEntityDao<Bug>{
	private static final int PointType_Percipience = 5; //1.勤奋积分 2.智慧积分  3.勇气（暂时不启用） 4.奉献积分   5.洞察积分  
	private static final String User_Answer_Split = "@:@";
//	public Integer getLastHisTestStatusIdByNodeInstanceId(Long nodeInstanceId){
//		if(nodeInstanceId!=null){
//			return (Integer)findObjByHql("select max(id)as id from HistoryTestStatus where asfNodeInstance.id=?  ",nodeInstanceId); 
//		}
//		return null;
//	}
	@SuppressWarnings("unchecked")
	public List<Integer> getHisTestStatusIdList(Long nodeInstanceId){
		List<Integer> historyTestStatusIdLst = find ("select id from HistoryTestStatus where asfNodeInstance.id=?  ",nodeInstanceId);
		return historyTestStatusIdLst;
	}
	
	public long getBugCount(Webuser user,Integer itemId){
		if(user!=null&&itemId!=null&&itemId!=0){
			return (Long )findObjByHql("select count(*) from BugInfo where bug.status!=-1 and  bug.item.id=? and user.id=? and (status=0 or status=1)",itemId,user.getId());
		}
		return 0;
	}
	
	public ItemAnswerVO getItemAnswerVO(ParamObject p,ViewControl viewControl){
		Integer historyTestStatusId = p.getInteger("historyTestStatusId");
		Integer itemId = p.getInteger("itemId");
		Long nodeInstanceId = p.getLong("nodeInstanceId");
		
		if(nodeInstanceId != null && nodeInstanceId==0){
			NodeInstance currentNodeInstance=viewControl.getExamNodeIns();
			nodeInstanceId = currentNodeInstance.getId();
		}
		HistoryAnswerStatus historyAnswerStatus = null;
		if(historyTestStatusId!=null&&historyTestStatusId!=0){
			//如果前台已经取到
			historyAnswerStatus=(HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id=?", itemId,historyTestStatusId) ;
		}else{
			//前台没有取到，则通过 nodeInstanceId 找到最后一次测试
			//historyTestStatusId = getLastHisTestStatusIdByNodeInstanceId(nodeInstanceId);
			List<Integer>  historyTestStatusIdLst= this.getHisTestStatusIdList(nodeInstanceId);
			String historyTestStatusIdLstStr = historyTestStatusIdLst.toString().replaceAll("\\[|\\]", "");
			historyAnswerStatus = (HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id in ("+historyTestStatusIdLstStr+") order by historyTestStatus.id desc", itemId) ;
			//p.getPara().put("historyTestStatusId",new Integer[]{historyTestStatusId});
		}
		
		ItemAnswerVO itemAnswerVO = new ItemAnswerVO();
		itemAnswerVO = getItemAnswerVO(itemAnswerVO,historyAnswerStatus, itemId);
		if(itemAnswerVO.getSubItemAnswerVOLst()!=null&&itemAnswerVO.getSubItemAnswerVOLst().size()>0){
			int stars = itemAnswerVO.getStars();
			for (SubItemAnswerVO svo : itemAnswerVO.getSubItemAnswerVOLst()) {
				if(svo.getStars()!=null&&svo.getStars()>stars){
					stars=svo.getStars();
				}
			}
			itemAnswerVO.setStars( stars);
		}
		return itemAnswerVO;
	}
	
	
	public List<String> getAnswers(String correctAnswers,String split){
		List<String> answers = new ArrayList<String>();
		if(correctAnswers!=null)
			for (String answer : correctAnswers.split(split)) {
				answers.add(MathUtil.addMathXMLIfMathAnswer(answer));
			}
		return answers;
	}
	
	/**
	 * 删除回复模板
	 * @param replyInfoTemplate
	 */
	public void remove(Integer replyInfoTemplateId){
		if(replyInfoTemplateId!=null){
			ReplyInfoTemplate replyInfoTemplateDB = this.get(ReplyInfoTemplate.class,replyInfoTemplateId);
			if(replyInfoTemplateDB!=null)
				this.remove(replyInfoTemplateDB);
		}
	}
	
	public List<ReplyInfoTemplate> getReplayInfoTemplates(){
		return this.getAll(ReplyInfoTemplate.class	);
	}
	
	
	/**
	 * 如果没有id 则为 创建一条新数据 
	 * 如果有id 则为更新回复模板
	 * @param replyInfoTemplate
	 */
	public ReplyInfoTemplate newOrUpdateReplyInfoTemplate(ReplyInfoTemplate replyInfoTemplate){
		if(replyInfoTemplate!=null){
			Integer replyInfoTemplateId = replyInfoTemplate.getId();
			ReplyInfoTemplate replyInfoTemplateDB=null;
			if(replyInfoTemplateId!=null){
				replyInfoTemplateDB = this.get(ReplyInfoTemplate.class,replyInfoTemplateId);
			}
			if(replyInfoTemplateDB==null){
				replyInfoTemplateDB = replyInfoTemplate;
			}else{
				if(replyInfoTemplate.getReplyContent()!=null)
					replyInfoTemplateDB.setReplyContent(replyInfoTemplate.getReplyContent());
				if(replyInfoTemplate.getTempalteName()!=null)
					replyInfoTemplateDB.setTempalteName(replyInfoTemplate.getTempalteName());
				if(replyInfoTemplate.getTempalteName()!=null)
					replyInfoTemplateDB.setReplyPoint(replyInfoTemplate.getReplyPoint() );
			}
			this.save(replyInfoTemplateDB);
			return replyInfoTemplateDB;
		}
		return replyInfoTemplate;
	}
	public List<BugInfoHistoryAnswerStatusVO> getBugInfoHistoryAnswerStatusVOLst(List<BugInfoHistoryAnswerStatus> bugInfoHistoryAnswerStatusLst){
		List<BugInfoHistoryAnswerStatusVO> bVOList=new ArrayList<BugInfoHistoryAnswerStatusVO>();
		if(bugInfoHistoryAnswerStatusLst!=null&&bugInfoHistoryAnswerStatusLst.size()>0){
			for (BugInfoHistoryAnswerStatus  bihas : bugInfoHistoryAnswerStatusLst) {
				bVOList.add(getBugInfoHistoryAnswerStatusVO(bihas));
			}
		}
		return bVOList;
	};
	
	public BugInfoHistoryAnswerStatusVO getBugInfoHistoryAnswerStatusVO(BugInfoHistoryAnswerStatus bihas){
		BugInfoHistoryAnswerStatusVO bVO = new  BugInfoHistoryAnswerStatusVO(bihas);
		bVO.setItemAnswerVO( getItemAnswerVO(bVO.getHistoryAnswerStatus().getHistoryTestStatus().getId(),bVO.getBugInfo().getBug().getItem().getId()));
		return bVO;
	}
	
	public ItemAnswerVO getItemAnswerVO(ItemAnswerVO itemAnswerVO,HistoryAnswerStatus his,Integer itemId){
		if(his!=null){
			Item item = his.getItem();
			if(item!=null){
				itemAnswerVO.setItem(item);
				List<SubItem> subItems = item.getSubItems();
				if(subItems!=null&&subItems.size()>0){
					List<SubItemAnswerVO> subItemVOLst = new ArrayList<SubItemAnswerVO>();
					itemAnswerVO.setSubItemAnswerVOLst(subItemVOLst);
					for (SubItem subItem : subItems) {
						
						HistoryAnswerStatus hisSub = (HistoryAnswerStatus)findObjByHql("from HistoryAnswerStatus where historyTestStatus.id=? and item.id=? and subItem.id=?",his.getHistoryTestStatus().getId(),itemId,subItem.getId());
						if(hisSub!=null){
							String answerOrder = hisSub.getAnswerOptionOrder();
							if(answerOrder==null||"".equals(answerOrder.trim())){
								answerOrder =BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT.substring(0,subItem.getAnswerOptions().size());
							}
							SubItemAnswerVO sVO = new SubItemAnswerVO();
							sVO.setSubItem(subItem);
							sVO.setAnswerViews(getAnswers( hisSub.getAnswer(), User_Answer_Split));
							sVO.setAnswerStr(getUserAnswerFormWangWeiAnswerOrder(his.getAnswerOptionOrder(),hisSub.getAnswer()));
							sVO.setAnswerOptionOrder(his.getAnswerOptionOrder());
							String analysis = getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge1());
							analysis += getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge2());
							analysis += getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge3());
							
							sVO.setAnalysisAtLarge1(getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge1()));
							sVO.setAnswerOptions(Item.getRandomAnswerOption(subItem.getAnswerOptions(), answerOrder));
							sVO.setCorrectAnswer(getUserAnswerFormWangWeiAnswerOrder(answerOrder, subItem.getCorrectAnswer()));
							sVO.setAnswers( subItem.getAnswers() );
							try{sVO.setStars(hisSub.getStarGrade().intValue());}catch(Exception e){};
							//sVO.setAnalysisAtLarge2(getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge2()));
							//sVO.setAnalysisAtLarge3(getAnalysisAtLarge(hisSub.getAnswerOptionOrder(),subItem.getAnalysisAtLarge3()));
							subItemVOLst.add(sVO);
						}
					}
				}else{
					String answerOrder = his.getAnswerOptionOrder();
					if(answerOrder==null||"".equals(answerOrder.trim())){
						answerOrder =BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT.substring(0,item.getAnswerOptions().size());
					}
					itemAnswerVO.setAnswerViews(getAnswers( his.getAnswer(), User_Answer_Split));
					itemAnswerVO.setAnswerStr(getUserAnswerFormWangWeiAnswerOrder(his.getAnswerOptionOrder(),his.getAnswer()));
					itemAnswerVO.setAnswerOptionOrder(answerOrder);
					
					String analysis = getAnalysisAtLarge(his.getAnswerOptionOrder(),item.getAnalysisAtLarge1());
					analysis+= getAnalysisAtLarge(his.getAnswerOptionOrder(),item.getAnalysisAtLarge2());
					analysis+= getAnalysisAtLarge(his.getAnswerOptionOrder(),item.getAnalysisAtLarge3());
					itemAnswerVO.setAnalysisAtLarge1( analysis);
					itemAnswerVO.setAnswerOptions(Item.getRandomAnswerOption(item.getAnswerOptions(), answerOrder));
					itemAnswerVO.setCorrectAnswer(getUserAnswerFormWangWeiAnswerOrder(answerOrder, item.getCorrectAnswer()));
					itemAnswerVO.setAnswers( item.getAnswers() );
					try{itemAnswerVO.setStars(his.getStarGrade().intValue());}catch(Exception e){};
					//itemAnswerVO.setAnalysisAtLarge2(getA(his.getAnswerOptionOrder(),item.getAnalysisAtLarge2()));
					//itemAnswerVO.setAnalysisAtLarge3(getA(his.getAnswerOptionOrder(),item.getAnalysisAtLarge3()));
				}
			}
		}
		return itemAnswerVO;
	}
	/**
	 * 获取某次测试中某题当时的答案
	 * @param historyTestStatusId
	 * @param itemId
	 * @return
	 */
	public ItemAnswerVO getItemAnswerVO(Integer historyTestStatusId, Integer itemId) {
		ItemAnswerVO itemAnswerVO=new ItemAnswerVO();
		if(itemId!=null){
			if(historyTestStatusId!=null ){
				HistoryAnswerStatus his = (HistoryAnswerStatus)findObjByHql("from HistoryAnswerStatus where historyTestStatus.id=? and item.id=? ",historyTestStatusId,itemId );
				itemAnswerVO = getItemAnswerVO(itemAnswerVO,his,itemId);
			}
		}
		if(itemAnswerVO.getSubItemAnswerVOLst()!=null&&itemAnswerVO.getSubItemAnswerVOLst().size()>0){
			int stars = itemAnswerVO.getStars();
			for (SubItemAnswerVO svo : itemAnswerVO.getSubItemAnswerVOLst()) {
				if(svo.getStars()!=null&&svo.getStars()>stars){
					stars=svo.getStars();
				}
			}
			itemAnswerVO.setStars( stars);
		}
		
		return  itemAnswerVO ;
	} 
	
	/**
	 * 通过答案顺序重新组织 详解
	 * @param aOrder 答案
	 * @param analysisStr
	 * @return
	 */
	public static String getAnalysisAtLarge(String aOrder, String analysisStr) {
		if(analysisStr!=null){
			String clientShowABCD = BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT;
			if(aOrder!=null&&!"".equals(aOrder.trim())){
				clientShowABCD =aOrder;
			}
			String reAnlysis =analysisStr;
			reAnlysis = reAnlysis.replaceAll("｛", "{").replaceAll("｝", "}");
			for (int i = 0,j=65; i < clientShowABCD.length(); i++,j++) { 
				try{reAnlysis = reAnlysis.replaceAll("(\\{)(<[^>]+>)*(["+String.valueOf((char)j)+"])(<[^>]+>)*(\\})", "$1"+i+"$5");}catch(Exception e){}
			}
			for (int i = 0,j=65; i < clientShowABCD.length(); i++,j++) { 
				try{reAnlysis = reAnlysis.replaceAll("(\\{)(<[^>]+>)*(["+i+"])(<[^>]+>)*(\\})", " "+clientShowABCD.substring(i,i+1)+" ");}catch(Exception e){}
			}
			return reAnlysis;
		}else{
			if(analysisStr==null){
				return "";
			}
			return analysisStr;	
		}
	}

	/**
	 * 
	 * @param wangWeiAnswerOrder 王伟的随机排序
	 * @param sourceAnswer  题目原始选项的 存储的用户答案（对应的是真实选项）
	 * @return
	 */
	public static String getUserAnswerFormWangWeiAnswerOrder(String wangWeiAnswerOrder, String sourceAnswer) {
		String realAnswerOrder = getRealAnswerOrder(wangWeiAnswerOrder);
		String userAnser = "";
		Set<String> set = new TreeSet<String>();
		if(sourceAnswer!=null&&sourceAnswer.trim().length()>0){
			for (int i = 0; i < sourceAnswer.length(); i++) {
				boolean b = false;
				for (int j = 0; j < realAnswerOrder.length(); j++) {
					if(sourceAnswer.substring(i,i+1).toUpperCase().equals(realAnswerOrder.substring(j,j+1).toUpperCase())){
						try{set.add(BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT.substring(j,j+1));}catch(Exception e){}
					}
				}
			}
		}
		for (String string : set) {
			userAnser+=string+" ";
		}
		return userAnser;
	}

	private static String getRealAnswerOrder(String wangWeiAnswerOrder) {
		if(wangWeiAnswerOrder!=null&&!"".equals(wangWeiAnswerOrder.trim())){
			String realAnswerOrder ="";
			int indexArr[] = new int[ wangWeiAnswerOrder.length()];
			for (int i = 65,j=0; i < 65+wangWeiAnswerOrder.length(); i++,j++) {
				indexArr[j]=wangWeiAnswerOrder.indexOf((char)i);
			}
			for (int i = 0; i < indexArr.length; i++) {
				if(indexArr[i]>=0){
					 realAnswerOrder+=BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT.substring(indexArr[i],indexArr[i]+1) ;
				}
			}
			return realAnswerOrder;
		}
		return BugInfoHistoryAnswerStatusVO.DEF_ABCDEFGHIJT;
	}
	/**
	 * 某个题 的所有捉虫信息及 当时的答案
	 * @param itemId
	 * @return
	 */
	public List<Map<String,Object> > getItemAllBugInfoHistoryAnswerStatus(ParamObject p){
		Integer itemId = p.getInteger("itemId");
	 	Integer bugInfoStatus = p.getInteger("bugInfoStatus");
	 	Integer bugStatus = p.getInteger("bugStatus");
	 	String userId = p.get("userId");
		List<Map<String,Object> > lst =null;
		
		if(itemId!=null && !itemId.equals(0)){
			HQLUtil<BugInfoHistoryAnswerStatus>  hqlUtilB =new HQLUtil<BugInfoHistoryAnswerStatus> ();
			hqlUtilB.add("from BugInfoHistoryAnswerStatus where 1=1 ");
			hqlUtilB.add(" and historyAnswerStatus.item.id = ? ",itemId);
			hqlUtilB.add(" and bugInfo.status = ? ",bugInfoStatus);
			hqlUtilB.add(" and bugInfo.bug.status = ? ",bugStatus);
			hqlUtilB.add(" and bugInfo.user.id = ? ",userId);
			hqlUtilB.add(" order by  bugInfo.submitTime desc ");
			List<BugInfoHistoryAnswerStatus> bihasList = hqlUtilB.queryList(this);
			lst = new ArrayList<Map<String,Object> >(bihasList.size());
			if(bihasList!=null&&bihasList.size()>0){
				for (BugInfoHistoryAnswerStatus bihas : bihasList) {
					Map<String,Object>  map = new HashMap<String,Object>(1);
					map.put("bihas",getBugInfoHistoryAnswerStatusVO(bihas) );
					map.put("itemAnswerVO", getItemAnswerVO(bihas.getHistoryAnswerStatus().getHistoryTestStatus().getId(),itemId));
					lst.add(map);
				}
			}
		}
		return lst==null?new ArrayList<Map<String,Object> >(0):lst;
	}
	/**
	 * 某次测试某个题 的所有捉虫信息及答案
	 * 
	 */	 
	public List<ItemAnswerVO> getItemAnswerVOList(String userId,ParamObject p,ViewControl viewControl){		
		List<ItemAnswerVO> itemAnswerVOLst = new ArrayList<ItemAnswerVO>() ;
		 	Integer itemId = p.getInteger("itemId");
		 	Integer bugInfoStatus = p.getInteger("bugInfoStatus");
		 	Integer bugStatus = p.getInteger("bugStatus");
		 	
			HQLUtil<Long>  hqlUtilB =new HQLUtil<Long> ();
			hqlUtilB.add("select distinct(historyAnswerStatus.id) from BugInfoHistoryAnswerStatus where 1=1 ");
			hqlUtilB.add(" and historyAnswerStatus.item.id = ? ",itemId);
			hqlUtilB.add(" and bugInfo.status = ? ",bugInfoStatus);
			hqlUtilB.add(" and bugInfo.bug.status = ? ",bugStatus);
			hqlUtilB.add(" and bugInfo.user.id= ? ",userId);
			List<Long> historyAnswerStatusIdList = hqlUtilB.queryList(this);
			
			for (Long historyAnswerStatusId : historyAnswerStatusIdList) {
				ParamObject pForItemAnswerVO = new ParamObject();
				pForItemAnswerVO.set("historyAnswerStatusId",historyAnswerStatusId);
				pForItemAnswerVO.set("itemId",itemId);
				itemAnswerVOLst.add(getItemAnswerVO(p,viewControl));
			}
		return itemAnswerVOLst;
	}
	
	/**
	 * 某个题 的所有捉虫信息及 当时的答案（按用户分组）
	 * @param itemId
	 * @return
	 */
	public Map<String,Object> getItemBugInfoHistoryAnswerStatusGroupByUser(ParamObject p){ 
			Map<String,Object> map = new HashMap<String,Object>(0); 
			Integer itemId = p.getInteger("itemId");
			Integer bugInfoStatus = p.getInteger("bugInfoStatus");
			Integer bugStatus = p.getInteger("bugStatus");
			List<Map<String,Object>> userItemBihasLstLst = null;
			if(itemId!=null){
				Item item = get(Item.class,itemId);
				map.put("item",item);
				HQLUtil<String>  hqlUtil=new HQLUtil<String>();
				hqlUtil.add("select distinct(bugInfo.user.id) from BugInfoHistoryAnswerStatus where 1=1 ");
				hqlUtil.add(" and historyAnswerStatus.item.id = ? ",itemId);
				hqlUtil.add(" and bugInfo.status = ? ",bugInfoStatus);
				hqlUtil.add(" and bugInfo.bug.status = ? ",bugStatus);
				hqlUtil.add(" order by bugInfo.user.id, bugInfo.submitTime desc ");
				List<String> userIds = hqlUtil.queryList(this);
				userItemBihasLstLst = new ArrayList<Map<String,Object>>();
				for (String userId : userIds) {
					p.getPara().put("userId", userId);// +++
					List<Map<String,Object>> bihasItemAnswerVOLst = getItemAllBugInfoHistoryAnswerStatus(p);
					if(bihasItemAnswerVOLst!=null&&bihasItemAnswerVOLst.size()>0){
						Map<String,Object> oneUserBihaMap = new HashMap<String,Object>(1);
						oneUserBihaMap.put("user", this.get(Webuser.class,userId));
						oneUserBihaMap.put("bihasItemAnswerVOLst",bihasItemAnswerVOLst);
						userItemBihasLstLst.add(oneUserBihaMap);
					}
				}
				map.put("userBihasItemAnswerVOLstLst", userItemBihasLstLst);
				map.put("replyInfoTemplates", getReplayInfoTemplates());
			}
			return map;
		}
	
	public Map<String,Object> showUserItemAnswerVOList(ParamObject p,ViewControl viewControl){
		Map<String,Object> map = new HashMap<String,Object>(0); 
		Integer itemId = p.getInteger("itemId");
		Integer bugInfoStatus = p.getInteger("bugInfoStatus");
		Integer bugStatus = p.getInteger("bugStatus");
		
		if(itemId!=null){
			Item item = get(Item.class,itemId);
			map.put("item",item);
			HQLUtil<String>  hqlUtil=new HQLUtil<String>  ();
			hqlUtil.add("select distinct(bugInfo.user.id) from BugInfoHistoryAnswerStatus where 1=1 ");
			hqlUtil.add(" and historyAnswerStatus.item.id = ? ",itemId);
			hqlUtil.add(" and bugInfo.status = ? ",bugInfoStatus);
			hqlUtil.add(" and bugInfo.bug.status = ? ",bugStatus);
			List<String> userIds = hqlUtil.queryList(this);
			List<Map<String,Object>> userItemAnswerVOList = new ArrayList<Map<String,Object>> ();
			for (String userId : userIds) {
				Map<String,Object> oneUserBihaList = new HashMap<String,Object>(1);
				oneUserBihaList.put("user", this.get(Webuser.class,userId));
				oneUserBihaList.put("itemAnswerVOList",getItemAnswerVOList(userId,p,viewControl));
				userItemAnswerVOList.add(oneUserBihaList);
			}
			map.put("userItemAnswerVOList", userItemAnswerVOList);
		}
		return map;
	}
	/**
	 * 增加一条bug信息
	 * @param webuser
	 * @param p 必需包含 itemId(试题id),nodeInstanceId(节点实例id),submitInfo(捉虫内容),positionIfo(位置内容)
	 * @return
	 */
	public void addBugInfo(Webuser webuser,ParamObject p,ViewControl viewControl){
		//ItemAnswerVO vo=getItemAnswerVO(p,viewControl);
		Integer historyTestStatusId = p.getInteger("historyTestStatusId");
		Integer itemId = p.getInteger("itemId");
		//Integer subItemId = p.getInteger("subItemId");
		String submitInfo = p.get("submitInfo");
		String positionInfo = p.get("positionInfo");
		String bugSite = p.get("bugSite");
		Long nodeInstanceId = p.getLong("nodeInstanceId");
		HistoryAnswerStatus historyAnswerStatusDB =null;
//		if (nodeInstanceId==0) nodeInstanceId = (Long)findObjByHql("select asfNodeInstance.id from HistoryTestStatus where id=?", historyTestStatusId);
//		if(nodeInstanceId!=null){
//			List<Integer>  historyTestStatusIdLst=  find ("select id from HistoryTestStatus where asfNodeInstance.id=?",nodeInstanceId);
//			String historyTestStatusIdLstStr = historyTestStatusIdLst.toString().replaceAll("\\[|\\]", "");
//			historyAnswerStatusDB = (HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id in ("+historyTestStatusIdLstStr+")", itemId) ;
//		}
		/////////////////
		if(historyTestStatusId!=null&&historyTestStatusId.intValue()!=0){
			historyAnswerStatusDB = (HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id in ("+historyTestStatusId+")", itemId) ;
		} else {
			if (nodeInstanceId==0) nodeInstanceId = (Long)findObjByHql("select asfNodeInstance.id from HistoryTestStatus where id=?", historyTestStatusId);
			if(nodeInstanceId!=null){
				List<Integer>  historyTestStatusIdLst=  find ("select id from HistoryTestStatus where asfNodeInstance.id=?",nodeInstanceId);
				String historyTestStatusIdLstStr = historyTestStatusIdLst.toString().replaceAll("\\[|\\]", "");
				historyAnswerStatusDB = (HistoryAnswerStatus)this.findObjByHql("from HistoryAnswerStatus where item.id=? and historyTestStatus.id in ("+historyTestStatusIdLstStr+") order by id desc", itemId) ;
			}
		}
		////////////////////
		if(webuser!=null&&itemId!=null&&submitInfo!=null&&!"".equals(submitInfo.trim())
				&&UtilAndTool_L.checkNotNullOrZero(bugSite)){
			if(historyAnswerStatusDB!=null){
				Item itemDB = historyAnswerStatusDB.getItem();
				Integer bugInfoId = 0;
				ProcessInstance processInstance  = historyAnswerStatusDB.getHistoryTestStatus().getAsfNodeInstance().getProcessInstance();
				if(processInstance!=null){
					//在库中查找Bug
					Bug bugDB = (Bug)findObjByHql("from Bug where item.id=? and asfProcessInstance.id=?",historyAnswerStatusDB.getItem().getId(),processInstance.getId());
					Set<BugInfo> bugInfoSet = new HashSet<BugInfo>(1);
					//不存在则创建
					if(bugDB==null){
						bugDB = new Bug();
						bugDB.setAsfProcessInstance(processInstance);
						bugDB.setItem(itemDB);
						bugDB.setBugInfos(bugInfoSet);
						bugDB.setStatus(1);//1使用 -1逻辑删除
					}else{
						bugDB.setStatus(1);//1使用 -1逻辑删除
					}
					save(bugDB);
					/*能用下列方法同步？？？
					bugInfoSet.add(toSaveBugInfo);
					bugDB.setBugInfos(bugInfoSet);
					save(bugDB);
					*/
					BugInfo toSaveBugInfo=null;
					toSaveBugInfo = new BugInfo();
					toSaveBugInfo.setBug(bugDB);
					toSaveBugInfo.setSubmitInfo(submitInfo );
					try{
						toSaveBugInfo.setBugSite(Integer.parseInt(bugSite));
					} catch (Exception e) {
						e.printStackTrace();
					}
					toSaveBugInfo.setSubmitTime( new Date());
					toSaveBugInfo.setUser(webuser);
					toSaveBugInfo.setStatus(0);//0.待回复1.已回复	-1.逻辑删除
					bugInfoId = (Integer)save1(toSaveBugInfo);//保存捉虫信息 
					//try{}catch(Exception e){erroLst.add("历史答题状态不存在！！！");}
					BugInfoHistoryAnswerStatus toSaveBugInfoHistoryAnswerStatus = new BugInfoHistoryAnswerStatus();
					toSaveBugInfoHistoryAnswerStatus.setPositionInfo(positionInfo);
					toSaveBugInfoHistoryAnswerStatus.setBugInfo(toSaveBugInfo);
					toSaveBugInfoHistoryAnswerStatus.setHistoryAnswerStatus (historyAnswerStatusDB );
					save(toSaveBugInfoHistoryAnswerStatus);
				}	
				//TODO 生成消息记录
				Message message = new Message();
				message.setProcessInstanceId(processInstance.getId());
				message.setUserId(processInstance.getActor());
				message.setSource(3);
				message.setType(2);
				message.setContent(ExamUtil.messageForAddBug);
				message.setRefId(bugInfoId);
				message.setPublishTime(new Date());
				message.setState(0);
				this.save(message);
			}
		}
	}

	/**
	 * 回复虫子
	 * @param bugInfo
	 */
	public void replyBugInfo(SysUser sysUser,ParamObject p){
		Integer bugInfoId = p.getInteger("bugInfoId");
		String replyInfo = p.get("replyInfo");
		if(sysUser!=null&&bugInfoId!=null&&replyInfo!=null){
			BugInfo bugInfoDB = get(BugInfo.class,bugInfoId);
			if(bugInfoDB !=null){
				bugInfoDB.setTeacher(sysUser);
				String prefix = "";
				if( p.get("replyInfoPrefix")!=null){
					prefix = p.get("replyInfoPrefix");
				}
				Date dd = new Date();
				if( p.getInteger("replyPoint")!=null){
					Integer replyPoint = p.getInteger("replyPoint");
					if(replyPoint!=null){
						//TODO 相应增加洞察积分 
						Long asfProcessInstanceId = bugInfoDB.getBug().getAsfProcessInstance().getId();
						MembershipPoint membershipPoint =(MembershipPoint)this.findObjByHql(
								"from MembershipPoint where asfProcessInstance.id=?",asfProcessInstanceId);
						if(membershipPoint!=null){
							membershipPoint.setPercipience(membershipPoint.getPercipience()+replyPoint);
							this.save(membershipPoint);
							MembershipPointHistory membershipPointHistory = new MembershipPointHistory();
							membershipPointHistory.setPointType(PointType_Percipience);
							membershipPointHistory.setPoint(replyPoint);
							membershipPointHistory.setOperateTime(dd);
							membershipPointHistory.setRefId(bugInfoDB.getId());
							membershipPointHistory.setAsfProcessInstance(bugInfoDB.getBug().getAsfProcessInstance() );
							membershipPointHistory.setPointCause(getCauseFromPoint(replyPoint));
							membershipPointHistory.setRemark("捉虫获得积分" );
							this.save(membershipPointHistory);
						}
						
						//TODO 生成消息记录
						Message message = new Message();
						message.setProcessInstanceId(asfProcessInstanceId);
						message.setUserId( bugInfoDB.getBug().getAsfProcessInstance().getActor());
						message.setSource(3);
						message.setType(2);
						message.setContent(getMessageFromPoint(replyPoint));
						message.setRefId(bugInfoDB.getId());
						message.setPublishTime(dd);
						message.setState(0);
						this.save(message);
					}
				}
				bugInfoDB.setReplyInfo(prefix+". "+replyInfo);
				bugInfoDB.setReplyTime(dd);
				bugInfoDB.setStatus(1);
				save(bugInfoDB);
			}
		}
	}

	/**********************************************************
	 * USE: 回复虫子信息修改
	 * PARAM: sysUser 修改信息提交人, p 修改信息有关参数保存的工具类, d 提交时间
	 * RETURN: 虫子的完整回复信息
	 * FOR: 试题虫子查看页面，修改虫子回复信息
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.11.31
	 * 
	 */
	public String modiReplyBugInfo(SysUser sysUser, ParamObject p){
		Integer bugInfoId = p.getInteger("bugInfoId");
		String replyInfo = p.get("replyInfo");
		String replyMess = "";
		if(sysUser!=null&&bugInfoId!=null&&replyInfo!=null){
			BugInfo bugInfoDB = get(BugInfo.class,bugInfoId);
			if(bugInfoDB !=null){
//				bugInfoDB.setTeacher(sysUser);
				String prefix = "";
				if( p.get("replyInfoPrefix")!=null){
					prefix = p.get("replyInfoPrefix");
				}
				if( p.getInteger("replyPoint")!=null){
					Integer replyPoint = p.getInteger("replyPoint");
					if(replyPoint!=null){
						//TODO 相应增加洞察积分 
						Long asfProcessInstanceId = bugInfoDB.getBug().getAsfProcessInstance().getId();
						MembershipPoint membershipPoint =(MembershipPoint)this.findObjByHql(
								"from MembershipPoint where asfProcessInstance.id=?",asfProcessInstanceId);
						if(membershipPoint!=null){
							String repInfo = bugInfoDB.getReplyInfo();
							int point = 0;
							if(UtilAndTool_L.checkNotNullOrZero(repInfo)){
								point = UtilAndTool_L.getBugReplyInfoPoint(repInfo);
							}
							membershipPoint.setPercipience(membershipPoint.getPercipience()-point+replyPoint);
							this.save(membershipPoint);
							MembershipPointHistory membershipPointHistory = (MembershipPointHistory)this.findObjByHql(
									"from MembershipPointHistory where asfProcessInstance.id=? and ref_id=?",asfProcessInstanceId, bugInfoDB.getId());
							membershipPointHistory.setPoint(replyPoint);
							membershipPointHistory.setPointCause(getCauseFromPoint(replyPoint));
							this.save(membershipPointHistory);
						}
						
						//TODO 生成消息记录
						Message message = (Message)this.findObjByHql(
								"from Message where refId=? and processInstanceId=? ",bugInfoDB.getId(),asfProcessInstanceId);
						message.setContent(getMessageFromPoint(replyPoint));
						this.save(message);
					}
				}
				replyMess = prefix+". "+replyInfo;
				bugInfoDB.setReplyInfo(replyMess);
				save(bugInfoDB);
			}
		}
		
		return replyMess;
	}
	
	/**
	 *  逻辑删除虫子信息
	 * @param user 操作者是谁
	 * @param p 要删除那些
	 */
	public void abadonBugInfos(Object user,ParamObject p){
			String bugInfoIds = p.get("bugInfoIds");
			if(bugInfoIds!=null&&!"".equals(bugInfoIds)){
				List<BugInfo> bugInfoLst = find("from BugInfo where id in ("+bugInfoIds+")");
				delBugInfos(user,bugInfoLst);
			}
	}
	
	/**********************************************************
	 * USE: 删除虫子信息
	 * PARAM: user 操作人，p 删除信息
	 * RETURN: ...
	 * FOR: 捉虫时，提交的捉虫信息有误，想删除掉的时候，调用此方法。如果信息还没有回复，则直
	 * 		接删除，否则做删除标记
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.26.10.07 
	 * 
	 */
	public void abadonBugInfosAddBug(Object user,ParamObject p){
			String bugInfoIds = p.get("bugInfoIds");
			if(bugInfoIds!=null&&!"".equals(bugInfoIds)){
				List<BugInfo> bugInfoLst = find("from BugInfo where id in ("+bugInfoIds+")");
				delBugInfosAddBug(user,bugInfoLst);
			}
	}
	
	/**
	 * 将传入题目的所有捉虫信息状态转换为逻辑删除 
	 * @param user
	 * @param p
	 */
	public void abadonBugsByItemIds(Object user,ParamObject p){
		String itemIds = p.get("itemIds");
		if(itemIds!=null&&!"".equals(itemIds)){
			List<Bug> bugLst = find("from Bug where item.id in ("+itemIds+")");
			delBugs(user,bugLst);
		}
	}
	
	/**
	 * 批量删除 捉虫
	 * @param user
	 * @param p
	 */
	public void abadonBugsByBugIds(Object user,ParamObject p){
		String bugIds = p.get("bugIds");
		if(bugIds!=null&&!"".equals(bugIds)){
			List<Bug> bugLst = find("from Bug where bug.id in ("+bugIds+")");
			delBugs(user,bugLst);
		}
	}
	
	public BugInfo getBugInfo(Integer id) {
		return (BugInfo)this.get(BugInfo.class, id);
	}
	
	private void delBugInfos(Object user,List<BugInfo> bugInfoLst ){		
		if(user!=null&&bugInfoLst!=null){
			for (BugInfo bugInfo : bugInfoLst) {
				bugInfo.setStatus(-1);
				save(bugInfo);
				//当所有捉足信息被作废时捉足也跟着作废
				HQLUtil<BugInfo> hqlUtil = new HQLUtil<BugInfo>();
				hqlUtil.add("from BugInfo where bug.id=? and status=0",bugInfo.getBug().getId());
				if(user instanceof Webuser){
					hqlUtil.add("and user.id=?",((Webuser)user).getId());
				}else{
					hqlUtil.add("and teacher.id=?",((SysUser)user).getId());
				} 
				List<BugInfo> notReplayBugInfo = hqlUtil.queryList(this);
				if(notReplayBugInfo!=null&&notReplayBugInfo.size()==0){
					Bug bug = bugInfoLst.get(0).getBug();
					bug.setStatus(-1);
					save(bug);
				}
			}
		}
	}
	
	/*******************************************************
	 * USE: 删除BUG调用的方法
	 * PARAM: user 删除人, bugInfoLst 要删除的BUG的LIST
	 * RETURN: ...
	 * FOR: 页面提交了有误的BUG信息，想删除的时候，调用此方法
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.26.10.17
	 * 
	 */
	private void delBugInfosAddBug(Object user,List<BugInfo> bugInfoLst ){		
		if(user!=null&&bugInfoLst!=null){
			for (BugInfo bugInfo : bugInfoLst) {
				if(bugInfo.getReplyTime()==null){
					this.remove(bugInfo);
//					this.remove(bugInfo.getBug());
					continue;
				} else {
					bugInfo.setStatus(-1);
					save(bugInfo);
				}
				//当所有捉足信息被作废时捉足也跟着作废
				HQLUtil<BugInfo> hqlUtil = new HQLUtil<BugInfo>();
				hqlUtil.add("from BugInfo where bug.id=? and status=0",bugInfo.getBug().getId());
				if(user instanceof Webuser){
					hqlUtil.add("and user.id=?",((Webuser)user).getId());
				}else{
					hqlUtil.add("and teacher.id=?",((SysUser)user).getId());
				} 
				List<BugInfo> notReplayBugInfo = hqlUtil.queryList(this);
				if(notReplayBugInfo!=null&&notReplayBugInfo.size()==0){
					Bug bug = bugInfoLst.get(0).getBug();
					bug.setStatus(-1);
					save(bug);
				}
			}
		}
	}
	
	private void delBugs(Object user,List<Bug> bugLst ){		
		if(user!=null&&bugLst!=null){
			for (Bug bug : bugLst) {
				bug.setStatus(-1);
				List<BugInfo> bugInfoLst = find("from BugInfo where bug.id =?",bug.getId());
				delBugInfos(user,bugInfoLst);
				save(bug);
			}
		}	
	}
	
	private String getCauseFromPoint(Integer point) {
		String r = "";
		switch (point) {
			case 500: r=ExamUtil.pointCauseForBug1;	break;
			case 200: r=ExamUtil.pointCauseForBug2;	break;
			case 100: r=ExamUtil.pointCauseForBug3;	break;
			case 20:  r=ExamUtil.pointCauseForBug4;	break;
			case 0:   r=ExamUtil.pointCauseForBug5;	break;
			default:  break;
		}
		return r;
	}
	
	private String getMessageFromPoint(Integer point) {
		String r = "";
		switch (point) {
			case 500: r=ExamUtil.messageForBug1;	break;
			case 200: r=ExamUtil.messageForBug2;	break;
			case 100: r=ExamUtil.messageForBug3;	break;
			case 20:  r=ExamUtil.messageForBug4;	break;
			case 0:   r=ExamUtil.messageForBug5;	break;
			default:  break;
		}
		return r;
	}
}
