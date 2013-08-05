package com.ambow.trainingengine.attention.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.attention.domain.ItemExtraInfo;
import com.ambow.trainingengine.attention.domain.LearnNote;
import com.ambow.trainingengine.attention.domain.LearnNoteVote;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.message.domain.Message;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;

/*
 * LearnNoteAction.java
 * 
 * Created on 2009-5-27 下午02:32:59
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class LearnNoteAction extends BaseAction {

	private static final long serialVersionUID = -8685187332940277544L;

	private int itemId;
	private int start=0;
	private int pageSize=5;
	private int noteId;
	private int voteType; //1: 鲜花  2:鸡蛋
	
	private HibernateGenericDao genService;
	
	@SuppressWarnings("unchecked")
	public String list(){
		Webuser webuser = (Webuser) getSessionObj(SessionDict.WebUser);
		
		ItemExtraInfo itemExtraInfo = genService.get(ItemExtraInfo.class, itemId);
		
		String counthql = "select count(*) from LearnNote n where n.item.id = ? " +
				" and n.state>=0 and n.isShare=1 ";
		Query countQuery = genService.createQuery(counthql, itemId);
		Long totalCount = (Long)countQuery.uniqueResult();
		
		String hql = " from LearnNote n where n.item.id = ?" +
				"and n.state>=0 and n.isShare=1 " +
				"order by n.state desc, n.flowerNum desc, n.eggNum, n.insertTime desc ";
		Query query = genService.createQuery(hql, itemId)
			.setFirstResult(start).setMaxResults(pageSize);
		List<LearnNote> learnNoteList = (List<LearnNote>)query.list();
		
		String votehql = "select 1 from LearnNoteVote v where v.learnNote.id =? and v.webuser.id = ?"; 
		String webuserId = webuser.getId();
		//String answerOptionOrder = "";
		for (LearnNote learnNote: learnNoteList) {
			if (learnNote.getWebuser().getId().equals(webuserId)) {
				learnNote.setVoted(true);
			} else {
				learnNote.setVoted(genService.createQuery(votehql, learnNote.getId(), webuserId).uniqueResult() != null);
			}
			
			/*	此功能暂不实现
			 * answerOptionOrder = learnNote.getItemAttention().getHistoryAnswerStatus().getAnswerOptionOrder();
			if ((answerOptionOrder!=null)&&(!answerOptionOrder.equals("")))
				learnNote.getItem().randomAnswerOption(answerOptionOrder);
			*/
		}
		
		this.setRequestAttribute("itemExtraInfo", itemExtraInfo);
		this.setRequestAttribute("noteList", learnNoteList);
		this.setRequestAttribute("totalCount", totalCount);
		this.setRequestAttribute("webuserId", webuserId);
		
		return "list";
	}
	
	public void vote(){
		Webuser webuser = (Webuser) getSessionObj(SessionDict.WebUser);
				
		LearnNote learnNote = genService.get(LearnNote.class, noteId);
		if (voteType==1) learnNote.setFlowerNum(learnNote.getFlowerNum()+1);
		else if (voteType==2) learnNote.setEggNum(learnNote.getEggNum()+1);
		
		LearnNoteVote learnNoteVote = new LearnNoteVote();
		learnNoteVote.setLearnNote(learnNote);
		learnNoteVote.setVote(voteType);
		learnNoteVote.setWebuser(webuser);
		learnNoteVote.setVoteTime(new Date());
		
		genService.save(learnNoteVote);
		genService.save(learnNote);
		
		if (voteType==1) savePoint(learnNoteVote);
		
		sendMessage(voteType, learnNoteVote);
		
		String str = "vote={flowerNum:" + learnNote.getFlowerNum()
			+ ",eggNum:" + learnNote.getEggNum() + "}";
		
		try {
			this.getHttpServletResponse().getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保存积分
	 */
	private void savePoint(LearnNoteVote learnNoteVote) {
		ProcessInstance asfProcessInstance = learnNoteVote.getLearnNote().getItemAttention()
			.getHistoryAnswerStatus().getHistoryTestStatus().getAsfNodeInstance().getProcessInstance();
		
		MembershipPoint membershipPoint = genService.get(MembershipPoint.class, asfProcessInstance.getId());
		Integer dedicate = membershipPoint.getDedicate()==null?0:membershipPoint.getDedicate() + 5; //笔记被别人投一次鲜花，获5分的积分奖励
		membershipPoint.setDedicate(dedicate);
		
		MembershipPointHistory membershipPointHis = new MembershipPointHistory();
		membershipPointHis.setAsfProcessInstance(asfProcessInstance);
		membershipPointHis.setPointType(4);
		membershipPointHis.setPoint(ExamUtil.pointOfNoteVote);
		membershipPointHis.setPointCause(ExamUtil.pointCauseForNoteVote);
		membershipPointHis.setOperateTime(new Date());
		membershipPointHis.setRemark("投票信息：" + 
				learnNoteVote.getWebuser().getLoginName() + " 投鲜花给 " + learnNoteVote.getLearnNote().getWebuser().getLoginName());
		
		genService.save(membershipPoint);
		genService.save(membershipPointHis);
	}
	
	/**
	 * 发送消息
	 */
	private void sendMessage(int voteType, LearnNoteVote learnNoteVote) {
		ProcessInstance asfProcessInstance = learnNoteVote.getLearnNote().getItemAttention()
			.getHistoryAnswerStatus().getHistoryTestStatus().getAsfNodeInstance().getProcessInstance();
		
		Message message = new Message();
		message.setProcessInstanceId(asfProcessInstance.getId());
		message.setUserId(asfProcessInstance.getActor());
		message.setSource(2);
		message.setType(2);
		message.setContent(learnNoteVote.getWebuser().getLoginName() +
				(voteType==1?ExamUtil.messageForNoteVoteFlower:ExamUtil.messageForNoteVoteEgg));
		message.setRefId(learnNoteVote.getLearnNote().getItemAttention().getId());
		message.setPublishTime(new Date());
		message.setState(0);
		genService.save(message);
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	public int getVoteType() {
		return voteType;
	}

	public void setVoteType(int voteType) {
		this.voteType = voteType;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

}
