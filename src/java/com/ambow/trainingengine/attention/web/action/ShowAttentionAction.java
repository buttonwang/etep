package com.ambow.trainingengine.attention.web.action;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.attention.domain.ItemAttention;
import com.ambow.trainingengine.attention.service.AttentionService;
import com.ambow.trainingengine.attention.web.data.AttentionVO;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/*
 * ShowAttentionAction.java
 * 
 * Created on 2009-5-31 下午01:37:39
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

public class ShowAttentionAction extends BaseAction {

	private static final long serialVersionUID = -4021595657234401190L;

	private int attentionId=0; //试题编号
	private int itemId=0; //试题编号
	private long hisId=0;  //历史答题情况标识
	
	private String type;	//关注类型：my, top
	
	private AttentionVO attentionVO;

	private HibernateGenericDao genService;
	private AttentionService attentionService;

	public String execute() {
		Webuser webuser = (Webuser) getSessionObj(SessionDict.WebUser);
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long pid = userDataVO.getProcessInstanceId();
	
		getAttention(webuser, pid);
		
		getEvaluation(itemId);
		
		getPreAndNextAttention(attentionId);
		
		this.setRequestAttribute("itemId",itemId);
		this.setRequestAttribute("hisId",hisId);
		this.setRequestAttribute("type", type);
		
		return SUCCESS;
	}
	
	public String save() {
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		UserDataVO userDataVO=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long pid = userDataVO.getProcessInstanceId();
		
		saveAttention(pid, user);

		this.setRequestAttribute("itemId",itemId);
		this.setRequestAttribute("hisId",hisId);
		this.setRequestAttribute("type", type);
		
		return "refresh";
	}
	
	public String cancel() {
		attentionService.cancelAttention(attentionVO.getAttentionId());
		return type;
	}
	
	public String redo() {
		attentionService.redoAttention(attentionVO.getAttentionId());
		return type;
	}
	
	//试题评价信息
	@SuppressWarnings("unchecked")
	private void getEvaluation(int itemId) {
		long egood=0, ebad=0, edim=0, eall=0;
		
		String hql = "select e.evaluation, count(*) as ecount from Evaluation e where e.item.id =? group by e.evaluation ";
		Query query = genService.createQuery(hql, itemId);
		List<Object[]> tuples = query.list();
		for (Object[] tuple: tuples) {
			if ((Integer)tuple[0] == 0 ) edim = (Long)tuple[1];
			if ((Integer)tuple[0] == -1) ebad = (Long)tuple[1];
			if ((Integer)tuple[0] == 1 ) egood= (Long)tuple[1];			
		}
		
		String ahql = "select popularity from ItemExtraInfo where id = ?";
		eall = (Long)genService.findObjByHql(ahql, itemId);
		
		this.setRequestAttribute("egood",egood);
		this.setRequestAttribute("ebad", ebad);
		this.setRequestAttribute("edim", edim);
		this.setRequestAttribute("eall", eall);
	}
	
	private void getAttention(Webuser webuser, long pid) {
		ItemAttention attention = null;
		if (type.equals("my")) {
			attention = (ItemAttention)genService.findObjByHql("from ItemAttention a where a.id=?", attentionId);
			this.itemId = attention.getItem().getId();
			this.hisId  = attention.getHistoryAnswerStatus().getId().intValue();
			getAttentionVO(attention, webuser);
		} else if (type.equals("top")) {
			this.itemId = (Integer)genService.findObjByHql("select a.item.id from ItemAttention a where a.id=?", attentionId);
			attention = (ItemAttention)genService.findObjByHql(
					"from ItemAttention a where a.item.id=? and a.webuser.id=?", itemId, webuser.getId());
			
			if (attention==null) canAttention(pid);
			else {
				getAttentionVO(attention, webuser);
				this.hisId  = attention.getHistoryAnswerStatus().getId().intValue();
			}
		}
		this.setRequestAttribute("attention", attention);
		
		List<String> recentItemTag = getRecentItemTag(this.itemId);
		this.setRequestAttribute("recentItemTag", recentItemTag);
	}

	//用户关注信息
	private void getAttentionVO(ItemAttention attention, Webuser webuser) {
		Integer attentionId = attention.getId();
		
		Integer evaluation = (Integer)genService.findObjByHql("select evaluation from Evaluation e where e.itemAttention.id=?", attentionId);
		String tag = (String)genService.findObjByHql("select tag from TagContent t where t.itemAttention.id=?", attentionId);
		Object[] noteTuple = (Object[])genService.findObjByHql("select content, isShare, state from LearnNote l where l.itemAttention.id=?", attentionId);
		
		AttentionVO attentionVO	= new AttentionVO();
		attentionVO.setAttentionId(attentionId);
		attentionVO.setEvaluation(evaluation);
		attentionVO.setTag(tag);
		if (noteTuple!=null) {
			attentionVO.setNoteContent((String)noteTuple[0]);
			attentionVO.setNoteShare((Integer)noteTuple[1]);
			attentionVO.setNoteState((Integer)noteTuple[2]);
		}
		attentionVO.setState(attention.getState());
		attentionVO.setInsertDate(attention.getInsertTime());
		
		this.setRequestAttribute("attentionVO", attentionVO);
		this.setRequestAttribute("canAttention", true);
	}

	private void canAttention(long pid) {
		boolean canAttention;
		String hql = "select a.id from HistoryAnswerStatus a join a.historyTestStatus t join t.asfNodeInstance n join n.processInstance p " +
					"where a.item.id=? and p.id =? ";
		Long hisAnswerId = (Long)genService.findObjByHql(hql, itemId, pid);
		canAttention = hisAnswerId != null;
		if (canAttention) {
			this.hisId = hisAnswerId.intValue();
		} else {
			this.hisId = 0;
		}
		this.setRequestAttribute("canAttention", canAttention);
	}
	
	@SuppressWarnings("unchecked")
	private void getPreAndNextAttention(Integer curId) {
		ArrayList attentionIds = (ArrayList)getSessionObj("attentionIds");
		if (attentionIds == null) {
			attentionIds = new ArrayList(); attentionIds.add(curId);
		}
		Integer curAttentionId = attentionIds.indexOf(curId);
		
		Integer preAttentionId  = (curAttentionId<=0)?0:(Integer)attentionIds.get(curAttentionId - 1);
		Integer nextAttentionId = (curAttentionId==attentionIds.size()-1)?0:(Integer)attentionIds.get(curAttentionId + 1);
		this.setRequestAttribute("preAttentionId", preAttentionId);
		this.setRequestAttribute("nextAttentionId", nextAttentionId);
	}
	
	@SuppressWarnings("unchecked")
	private List<String> getRecentItemTag(Integer itemId) {
		Query query = genService.createQuery("select distinct t.tag from ItemTag i join i.tag t where i.item.id=? order by t.num desc ", itemId)
					 .setFirstResult(0).setMaxResults(10);
		return (List<String>)query.list();
	}
	
	private void saveAttention(long pid, Webuser webuser) {
		ParamObject p = new ParamObject();
		
		Integer itemAttentionId, itemId, evaluationId, learnNoteId, tagContentId, historyTestStatusId;
		
		if (attentionVO.getAttentionId()>0) {
			ItemAttention itemAttention = genService.get(ItemAttention.class, attentionVO.getAttentionId());
			itemAttentionId = itemAttention.getId();
			itemId = itemAttention.getItem().getId();
			historyTestStatusId = itemAttention.getHistoryAnswerStatus().getHistoryTestStatus().getId();
			
			evaluationId = (Integer)genService.findObjByHql("select e.id from Evaluation e where e.itemAttention.id=?", itemAttentionId);
			tagContentId = (Integer)genService.findObjByHql("select t.id from TagContent t where t.itemAttention.id=?", itemAttentionId);
			learnNoteId  = (Integer)genService.findObjByHql("select n.id from LearnNote  n where n.itemAttention.id=?", itemAttentionId);
			
			p.add("itemAttentionId", attentionVO.getAttentionId());
			p.add("evaluationId", evaluationId);
			p.add("tagContentId", tagContentId);
			p.add("learnNoteId", learnNoteId);
			
		} else {
			itemId = this.itemId;
			String hql = "select t.id from HistoryAnswerStatus a join a.historyTestStatus t join t.asfNodeInstance n join n.processInstance p" +
					" where a.item.id=? and p.id = ? order by a.id desc";
			historyTestStatusId = (Integer)genService.findObjByHql(hql, this.itemId, pid);
		}
		p.add("itemId", itemId);
		p.add("historyTestStatusId", historyTestStatusId);
		
		p.add("evaluation", attentionVO.getEvaluation());
		p.add("learnNoteState", attentionVO.getNoteState());
		p.add("isShare", attentionVO.getNoteShare());
		p.add("content", attentionVO.getNoteContent());
		p.add("tags", attentionVO.getTag());
		
		attentionService.addOrUpdate(p, webuser);
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(int attentionId) {
		this.attentionId = attentionId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	public long getHisId() {
		return hisId;
	}

	public void setHisId(long hisId) {
		this.hisId = hisId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AttentionVO getAttentionVO() {
		return attentionVO;
	}

	public void setAttentionVO(AttentionVO attentionVO) {
		this.attentionVO = attentionVO;
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
	
	public AttentionService getAttentionService() {
		return attentionService;
	}

	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}
}
