package com.ambow.core.module.search.bean;

import java.util.Date;

public class DifferentRecord {
	private String table;

	private Integer targetId;

	private OperationType operationType;

	private Date modifiedTime;

	public DifferentRecord(String table, Integer targetId,
			OperationType operationType, Date modifiedTime) {
		super();
		this.table = table;
		this.targetId = targetId;
		this.operationType = operationType;
		this.modifiedTime = modifiedTime;
	}

	public DifferentRecord() {
		super();
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
}