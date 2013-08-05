/* 
 * Page.java
 * 
 * Created on 2007-3-30
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: Page.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * �2⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.3  2007/04/27 06:53:33  yaoshunxiang
 * ���setResult ����
 *
 * Revision 1.2  2007/04/25 06:45:31  xiaosa
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/10 01:36:01  pengqing
 * ��ʼ��Ŀ������ambow-core
 *
 *
 */
package com.ambow.core.dao.support;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

/**
 * 用于分页支持的类<br>
 * 可以通过{@link #getCurrentPageNo()} 得到当前页数；{@link #getTotalPageCount()} 得到总页数。<br>
 * 默认每页的条数为20 条，如果需要自行设置，可以用{@link #Page(long, long, int, Collection)} 构建新页设置。<br>
 * 通过{@link #getResult()} 方法可以得到该页的数据。
 * 
 * @author Peng Qing
 *
 */
public class Page {

	private static  int DEFAULT_PAGE_SIZE = 20;

	private int pageSize = DEFAULT_PAGE_SIZE; //每页的记录数

	private long start;  //当前页第一条数据在List中的位置,从0开始

	private Collection data;  //当前页中存放的记录,类型一般为List

	private long totalCount; //总记录数

	/**
	 * 构造方法，只构造空页
	 */
	public Page() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList(0));
	}

	/**
	 * 默认构造方法
	 *
	 * @param start	 本页数据在数据库中的起始位置
	 * @param totalSize 数据库中总记录条数
	 * @param pageSize  本页容量
	 * @param data	  本页包含的数据
	 */
	public Page(long start, long totalSize, int pageSize, Collection data) {
		this.pageSize = pageSize;
		this.start = start;
		this.totalCount = totalSize;
		this.data = data;
	}

	/**
	 * 取总记录数.
	 */
	public long getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 取总页数.
	 */
	public long getTotalPageCount() {
		if (totalCount % pageSize == 0)
			return totalCount / pageSize;
		else
			return totalCount / pageSize + 1;
	}

	/**
	 * 取每页数据容量.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页中的记录.
	 */
	public Collection getResult() {
		return data;
	}
	
	/**
	 * 设置数据集合
	 * @param data
	 */
	public void setResult(Collection data) {
		this.data = data;
	}

	/**
	 * 取该页当前页码,页码从1开始.
	 */
	public long getCurrentPageNo() {
		return start / pageSize + 1;
	}

	/**
	 * 该页是否有下一页.
	 */
	public boolean hasNextPage() {
		return this.getCurrentPageNo() < this.getTotalPageCount();
	}

	/**
	 * 该页是否有上一页.
	 */
	public boolean hasPreviousPage() {
		return this.getCurrentPageNo() > 1;
	}

	/**
	 * 获得此页的下一页页数
	 */
	public long getNextPageNo() {
		if (hasNextPage()) {
			return this.getCurrentPageNo() + 1;
		}
		
		return this.getCurrentPageNo();
	}
	
	/**
	 * 获得此页上一页的页数
	 */
	public long getPreviousPageNo() {
		if (hasPreviousPage()) {
			return this.getCurrentPageNo() - 1;
		}
		
		return this.getCurrentPageNo();
	}
	/**
	 * 
	 * 获取任一页第一条数据在数据集的位置
	 *
	 * @param pageNo   从1开始的页号
	 * @param pageSize 每页记录条数
	 * @return 该页第一条数据
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	
	/**
	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
	 *
	 * @see #getStartOfPage(int,int)
	 */
	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}
}
