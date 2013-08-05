package com.ambow.trainingengine.attention.web.data;

import java.util.Date;

/*
 * AttentionVO.java
 * 
 * Created on 2009-5-31 下午10:43:54
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

public class AttentionVO {

	private Integer attentionId;
	
	private Integer evaluation;
	
	private String tag;
	
	private String noteContent="";
	
	private Integer noteShare;
	
	private Integer noteState;
	
	private Integer state;
	
	private Date insertDate;

	public AttentionVO() {
	}

	public Integer getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(Integer attentionId) {
		this.attentionId = attentionId;
	}

	public Integer getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Integer evaluation) {
		this.evaluation = evaluation;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}

	public Integer getNoteShare() {
		return noteShare;
	}

	public void setNoteShare(Integer noteShare) {
		this.noteShare = noteShare;
	}
	
	public Integer getNoteState() {
		return noteState;
	}

	public void setNoteState(Integer noteState) {
		this.noteState = noteState;
	}
	
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
}
