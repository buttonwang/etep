package com.ambow.trainingengine.bug.web.data;

import com.ambow.trainingengine.bug.domain.ReplyInfoTemplate;

public class ReplyInfoTemplateVO {
	private String name = null;
	private String content = null;
	private Integer point = null;
	
	public void copyData(ReplyInfoTemplate rit) {
		if(rit==null) return ;
		this.name = rit.getTempalteName();
		this.content = rit.getReplyContent();
		this.point = rit.getReplyPoint();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
}
