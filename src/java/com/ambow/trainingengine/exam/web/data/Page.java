package com.ambow.trainingengine.exam.web.data;

import java.util.List;

import com.ambow.trainingengine.exam.util.SplitCompareable;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;

/*
 * Page.java
 * 
 * Created on Jul 2, 2008 9:20:55 PM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 2008-12-31 hasSubItem mpc 项目都按false来处理 。
 */
public class Page implements SplitCompareable{
	
	private List<Item> items;
	
	private int pageNum;
	
	private int size;//本页题数
	
	private ItemType itemType;
	
	private Integer[] isMark;
	
	private String[] userAnswer;
	
	private Integer[] starInt;//星级整数部分
	
	private Integer[] starHalf;//星级小数部分1表示0.5星
	
	private Integer[] isDone;//是否做了 0为未做1为做了
	
	private Integer[] isRight;//0错误1正确
	
	private int markNum;
	
	private int doneNum;
	
	private int rightNum;
		
	private boolean hasSubItem;//True有子题 false无子题
	
	private String title;

	private String instruction;
	
	private String typeAlias; //题型别名。 如MPC11, MPC12， MPC3X

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	
	/*
	 * Page不计数量,所以此处留空
	 * @see com.ambow.trainingengine.itembank.util.SplitCompareable#getCountSize()
	 */
	public int getCountSize() {
		return 0;
	}
	
	/*
	 * 此处要实现
	 * 如要实现不同题型的页面属于一个Section,修改此处设置
	 * @see com.ambow.trainingengine.itembank.util.SplitCompareable#splitCompareTo(java.lang.Object)
	 */
	public boolean splitCompareTo(Object obj) {
		if(obj instanceof Page){
			if(this.itemType.compareTo(((Page)obj).getItemType())==0) return true;
		}
		return false;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String[] getUserAnswer() {
		return userAnswer;
	}

	public void setUserAnswer(String[] userAnswer) {
		this.userAnswer = userAnswer;
	}

	public Integer[] getStarInt() {
		return starInt;
	}

	public void setStarInt(Integer[] starInt) {
		this.starInt = starInt;
	}

	public Integer[] getStarHalf() {
		return starHalf;
	}

	public void setStarHalf(Integer[] starHalf) {
		this.starHalf = starHalf;
	}

	public Integer[] getIsMark() {
		return isMark;
	}

	public void setIsMark(Integer[] isMark) {
		this.isMark = isMark;
	}

	public Integer[] getIsDone() {
		return isDone;
	}

	public void setIsDone(Integer[] isDone) {
		this.isDone = isDone;
	}

	public Integer[] getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer[] isRight) {
		this.isRight = isRight;
	}

	public int getMarkNum() {
		return markNum;
	}

	public void setMarkNum(int markNum) {
		this.markNum = markNum;
	}

	public int getDoneNum() {
		return doneNum;
	}

	public void setDoneNum(int doneNum) {
		this.doneNum = doneNum;
	}
	
	public boolean isHasSubItem() {
		return hasSubItem;
	}

	public void setHasSubItem(boolean hasSubItem) {
		this.hasSubItem = hasSubItem;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRightNum() {
		return rightNum;
	}

	public void setRightNum(int rightNum) {
		this.rightNum = rightNum;
	}	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
		
	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getTypeAlias() {
		return typeAlias;
	}

	public void setTypeAlias(String typeAlias) {
		this.typeAlias = typeAlias;
	}

}
