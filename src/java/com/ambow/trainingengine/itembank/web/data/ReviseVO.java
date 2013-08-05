package com.ambow.trainingengine.itembank.web.data;

import java.text.SimpleDateFormat;
import java.util.Date;

/******************************************
 * USE: 保存试题审校基本信息
 * FOR: 试题审校查询页面，得到每题的审校情况。本VO用于保存从库中得到的各题的审校情况的基本信息
 * 
 * AUTHOR: L.赵亚
 * DATE: 2010.03.05.11.06
 * 
 */
public class ReviseVO {
	private Integer id; //审校记录id
	private Integer itemId; //试题id
	private Integer revisor; //审校人
	private Integer reviseStatus; //审校状态
	private String revisePerson; //审校人
	private String reviseName; //审校人的姓名
	private String reviseTime; //审校时间
	private Integer replyType; //回复类型
	private String reviseRecord; //纠错信息
	private Date reviseRecordTime; //纠错时间
	private String reviseReply; //纠错回复
	private Integer reviseReplyer; //纠错回复人
	private String reviseReplyerName; //纠错回复人的姓名
	private Date reviseReplyTime; //纠错回复时间
	private String remark; //

	public Integer getRevisor() {
		return revisor;
	}
	public void setRevisor(Integer revisor) {
		this.revisor = revisor;
	}
	public String getReviseName() {
		return reviseName;
	}
	public void setReviseName(String reviseName) {
		this.reviseName = reviseName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public String getRevisePerson() {
		return revisePerson;
	}
	public void setRevisePerson(String revisePerson) {
		this.revisePerson = revisePerson;
	}
	public String getReviseTime() {
		return reviseTime;
	}
	public void setReviseTime(String reviseTime) {
		this.reviseTime = reviseTime;
	}
	public Integer getReviseStatus() {
		return reviseStatus;
	}
	public void setReviseStatus(Integer reviseStatus) {
		this.reviseStatus = reviseStatus;
	}
	public String getReviseStatusName() {
		if(this.getReviseStatus()==1)
			return "已通过";
		else
			return "未通过";
	}
	public Integer getReplyType() {
		return replyType;
	}
	public void setReplyType(Integer replyType) {
		this.replyType = replyType;
	}
	public String getReviseRecord() {
		return reviseRecord;
	}
	public void setReviseRecord(String reviseRecord) {
		this.reviseRecord = reviseRecord;
	}
	public Date getReviseRecordTime() {
		return reviseRecordTime;
	}
	public void setReviseRecordTime(Date reviseRecordTime) {
		this.reviseRecordTime = reviseRecordTime;
	}
	public String getReviseReply() {
		return reviseReply;
	}
	public void setReviseReply(String reviseReply) {
		this.reviseReply = reviseReply;
	}
	public Integer getReviseReplyer() {
		return reviseReplyer;
	}
	public void setReviseReplyer(Integer reviseReplyer) {
		this.reviseReplyer = reviseReplyer;
	}
	public Date getReviseReplyTime() {
		return reviseReplyTime;
	}
	public void setReviseReplyTime(Date reviseReplyTime) {
		this.reviseReplyTime = reviseReplyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReviseReplyerName() {
		return reviseReplyerName;
	}
	public void setReviseReplyerName(String reviseReplyerName) {
		this.reviseReplyerName = reviseReplyerName;
	}
}
