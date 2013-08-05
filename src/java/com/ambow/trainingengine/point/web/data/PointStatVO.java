package com.ambow.trainingengine.point.web.data;

import java.util.Date;

import org.hibernate.hql.ast.tree.Case2Node;

/*
 * PointStatVO.java
 * 
 * Created on 2009-9-28 下午04:18:32
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

public class PointStatVO {
	
	private Integer pointType;
	private String pointName;
	private Date minOperateTime;
	private Date maxOperateTime;
	private Integer countPoint=0;
	private Integer lastWeekPoint=0;
	private Integer lastMonthPoint=0;
	
	public PointStatVO(Integer pointType) {
		this.pointType = pointType;
		
		switch (pointType) {
			case 1: this.pointName = "勤奋"; break;
			case 2: this.pointName = "智慧"; break;
			case 3: this.pointName = "勇气"; break;
			case 4: this.pointName = "奉献"; break;
			case 5: this.pointName = "洞察"; break;
	
			default: this.pointName = ""; break;
		}
	}
	
	public Integer getPointType() {
		return pointType;
	}
	public void setPointType(Integer pointType) {
		this.pointType = pointType;
	}
	public String getPointName() {
		return pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	public Date getMinOperateTime() {
		return minOperateTime;
	}
	public void setMinOperateTime(Date minOperateTime) {
		this.minOperateTime = minOperateTime;
	}
	public Date getMaxOperateTime() {
		return maxOperateTime;
	}
	public void setMaxOperateTime(Date maxOperateTime) {
		this.maxOperateTime = maxOperateTime;
	}
	public Integer getCountPoint() {
		return countPoint;
	}
	public void setCountPoint(Integer countPoint) {
		this.countPoint = countPoint;
	}
	public Integer getLastWeekPoint() {
		return lastWeekPoint;
	}
	public void setLastWeekPoint(Integer lastWeekPoint) {
		this.lastWeekPoint = lastWeekPoint;
	}
	public Integer getLastMonthPoint() {
		return lastMonthPoint;
	}
	public void setLastMonthPoint(Integer lastMonthPoint) {
		this.lastMonthPoint = lastMonthPoint;
	}
	
}
