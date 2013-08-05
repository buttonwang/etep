package com.ambow.trainingengine.web.data;
/*
 * GroupNodeStatVO.java
 * 节点组统计信息
 * 
 * Created on 2008-7-31 下午03:00:17
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
public class GroupNodeStatVO {
	protected long id;
	protected String name;
	
	private long nodeCount;
	private long userNodeCount;
	private long nodeTotalScoreSum;
	private long userNodeTotalScoreSum;
	private long userProgressRate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getNodeCount() {
		return nodeCount;
	}
	public void setNodeCount(long nodeCount) {
		this.nodeCount = nodeCount;
	}
	public long getUserNodeCount() {
		return userNodeCount;
	}
	public void setUserNodeCount(long userNodeCount) {
		this.userNodeCount = userNodeCount;
	}
	public long getNodeTotalScoreSum() {
		return nodeTotalScoreSum;
	}
	public void setNodeTotalScoreSum(long nodeTotalScoreSum) {
		this.nodeTotalScoreSum = nodeTotalScoreSum;
	}
	public long getUserNodeTotalScoreSum() {
		return userNodeTotalScoreSum;
	}
	public void setUserNodeTotalScoreSum(long userNodeTotalScoreSum) {
		this.userNodeTotalScoreSum = userNodeTotalScoreSum;
	}
	public long getUserProgressRate() {
		return userProgressRate;
	}
	public void setUserProgressRate(long userProgressRate) {
		this.userProgressRate = userProgressRate;
	} 
}