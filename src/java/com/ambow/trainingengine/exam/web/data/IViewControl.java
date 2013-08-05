package com.ambow.trainingengine.exam.web.data;

import java.util.List;

import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.itembank.domain.Item;



/*
 * IViewControlProxy.java
 * 
 * Created on 2008-12-31 下午08:36:46
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * ViewControl 对象的操作代理
 * Changes 
 * -------
 * $log$
 */

public interface IViewControl {
		
	/**
	 * 为答题、逐题浏览模式生成Map数据
	 */
	void generateMap();
	
	/**
	 *  为View模式生成Map数据
	 */
	void generateMapForView();
	
	/**
	 *  为Widget模式生成试题数据
	 *  mode 1: 关注  2：捉虫    3: 试题校验前 4：试题校验后
	 */
	void generateWidget(Item item, List<HistoryAnswerStatus> historyAnswerStatusList, int mode);
	
	/**
	 *  为Widget模式生成试题数据,多题模式
	 *  mode 留用
	 */
	void generateWidget2(List<Item> items, int mode);
	
	/**
	 * 初始化页面数据
	 */
	void initPages();
	
	/**
	 * 设置常规情况下的页面
	 */
	void setPages();
	
	/**
	 * 设置过滤情况下的页面
	 */
	void setFilterPages();
	
	/**
	 * 统计页面数据
	 */
	void statPages();
	
	/**
	 * 解析答案
	 * @param userAnswer 用户答案
	 * @param page	需要保存答案的页面
	 */
	void parseAnswer(String userAnswer, Page page);	
	
	/**
	 * 将题目分页
	 * @param items 试题
	 * @return 答题页面
	 */
	public List<Page> itemPaging(List<Item> items);
	
	/**
	 * 将页数分到不同的Section
	 * @param pages 答题页面
	 * @return 试题overview页面
	 */
	public List<Section> toSections(List<Page> pages);
	
	/**
	 * 节点重做的情况下换算正确率
	 * MPC: X = (重做训练通过率 * 节点所有题的总分 - 剩余题实际得分之和 ) / 按策略应出题的题分之和
	 */
	public void convertRightRate();
	
}
