package com.ambow.trainingengine.itembank.web.data;



public class AnswerOptionVO {
	private Integer itemId;
	private Integer subItemId;
	private Integer id;	
	private String code;
	private String content;
	private String translation;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTranslation() {
		return translation;
	}
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getSubItemId() {
		return subItemId;
	}
	public void setSubItemId(Integer subItemId) {
		this.subItemId = subItemId;
	}
}
