package com.ambow.trainingengine.itembank.web.data;

import java.util.List;

import com.ambow.trainingengine.util.UtilAndTool_L;

/***********************************************
 * 
 * USE: 在页面从审校列表页面跳转到题目审校页面的时候，有上一题下一题的功能
 * 		本类用于保存上一题下一题的相关ID信息及科目信息等等
 * FOR: 当页面从审校列表页面跳转到题目的审校页面的时候，把科目信息及当前页的所有题目
 * 		ID的列表保存下来，
 * INFO: 当从SESSION里面得到的本VO的信息中，没有要显示的题目的ID的时候，上一题下一题
 * 		的功能是不可用的
 * 
 * AUTHOR: L.赵亚
 * DATE: 2010.03.18.13.20 
 * 
 */
public class ItemPageIDsVO {
	private List<String> idList = null;
	private String id = null;
	private boolean jump = false;
	private ReviseQueryVO rqvo; //查询条件VO
	
	/**********************************************
	 * USE: 得到前一题目的ID
	 * PARAM: id 当前题目的ID
	 * RETURN: 前一题目的ID
	 * FOR: 根据当前题目的ID信息得到前一题目的ID信息，如果当前题目为当前页的第一个题目，
	 * 		则返回空
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.18.13.29
	 * 
	 */
	public String getPrev(){
		if(UtilAndTool_L.checkNullOrZero(id)) return "";
		Integer idN = idList.indexOf(id);
		if(idN==0) return "";
		return idList.get(idN-1);
	}
	
	/**********************************************
	 * USE: 得到后一题目的ID
	 * PARAM: id 当前题目的ID
	 * RETURN: 后一题目的ID
	 * FOR: 根据当前题目的ID信息得到后一题目的ID信息，如果当前题目为当前页的最后一个题目，
	 * 		则返回空
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.18.13.29
	 * 
	 */
	public String getNext(){
		if(UtilAndTool_L.checkNullOrZero(id)) return "";
		Integer idN = idList.indexOf(id);
		if(idN==this.size()-1) return "";
		return idList.get(idN+1);
	}
	
	/***********************************************
	 * USE: 得到当前题目做在的页面的题目的数目
	 * PARAM: ...
	 * RETURN: 当前题目所在页面题目的数目
	 * FOR: 根据当前题目信息得到它的前一题目的ID及后一题目的ID的时候，需要判断是不是当前
	 * 		题目是第一题或者最后一题，此时会用到当前页面题目的数目。由于当前题目所在页可能
	 * 		不足一页的题目，所以，不能单纯地用页面大小来确定题目的数目
	 * 
	 * AUTHOR: L.赵亚
	 * DATE; 2010.03.18.13.32
	 * 
	 */
	public Integer size(){
		if(idList==null) return 0;
		return this.idList.size();
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ReviseQueryVO getRqvo() {
		return rqvo;
	}

	public void setRqvo(ReviseQueryVO rqvo) {
		this.rqvo = rqvo;
	}

	public boolean isJump() {
		return jump;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}
