package com.ambow.trainingengine.util;

public class SwieBean {
	private String userName;
	private String password;
	private String sysAbbr;
	private String sysCode;
	private String userUrl;
	private String returnUrl;
	private String sysUserType;
	private String title;
	private String direction;
	private String content;
	private String swieUrl;
	private String swiePostUrl;
	private String writeImgUrl;
	private String articleStatusUrl;
	private String use;

	public	String getGetUrl() {
		return swieUrl + "?" + "userName=" + userName + "&sysAbbr=" + 
			sysAbbr + "&sysCode=" + sysCode + "&sysUserType=" + sysUserType;
	}
	
	public	String getPostUrl() {
		return swiePostUrl + "?" + "userName=" + userName + "&sysAbbr=" + 
			sysAbbr + "&sysCode=" + sysCode + "&sysUserType=" + sysUserType;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSysAbbr() {
		return sysAbbr;
	}
	public void setSysAbbr(String sysAbbr) {
		this.sysAbbr = sysAbbr;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getUserUrl() {
		return userUrl;
	}
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getSysUserType() {
		return sysUserType;
	}
	public void setSysUserType(String sysUserType) {
		this.sysUserType = sysUserType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSwieUrl() {
		return swieUrl;
	}
	public void setSwieUrl(String swieUrl) {
		this.swieUrl = swieUrl;
	}
	public String getSwiePostUrl() {
		return swiePostUrl;
	}
	public void setSwiePostUrl(String swiePostUrl) {
		this.swiePostUrl = swiePostUrl;
	}
	public String getWriteImgUrl() {
		return writeImgUrl;
	}
	public void setWriteImgUrl(String writeImgUrl) {
		this.writeImgUrl = writeImgUrl;
	}
	public String getArticleStatusUrl() {
		return articleStatusUrl;
	}
	public void setArticleStatusUrl(String articleStatusUrl) {
		this.articleStatusUrl = articleStatusUrl;
	}	
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}	

}
