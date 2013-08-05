package com.ambow.trainingengine.itembank.web.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.UtilAndTool_L;

/**************************************************
 * @USE: 用于保存试题基本信息及试题的审校信息
 * @FOR: 试题审校查询页面，得到各题的审校信息，本VO，用于保存试题的部分基本信息及审校情况的信息
 * 
 * @AUTHOR: L.赵亚
 * @DATE: 2010.03.05.11.06
 * 
 */
public class ItemReviseVO {
	private Integer id; //试题id
	private String code; //试题编码
	private ItemType type; //试题类型
	private String content; //题干
	private Integer reviseStatus = 0; //审校状态
	private String reviseStatusName; //审校状态名称
	private SysUser user; //登录人
	private boolean selfTimeShow = false; //是否只显示自己的审校时间（审校记录页面，显示审校时间的时候，是否只显示
	//当前登录用户的审校时间）
	private List<String> reviseList = new ArrayList<String>(); //审校信息id的List
	private Map<String, ReviseVO> reviseMap = new HashMap<String, ReviseVO>(); //审校信息Map
	private List<String> sysUserList = new ArrayList<String>(); //审校人登录id的List
	private Map<String, UserReviseVO> sysUserMap = new HashMap<String, UserReviseVO>(); //审校人审校信息Map
	private Integer reviserstatus; //对某个审校人的审校状态
	public List<ReviseVO> getAllRevises(){
		List<ReviseVO> rvoList = new ArrayList<ReviseVO>();
		
		for(int i=this.size(); i>0; i--){
			rvoList.add(this.get(i-1));
		}
		
		return rvoList;
	}
	
	/*****************************************
	 * @USE: 根据序号得到对应的审校记录信息
	 * @PARAM: index 序号
	 * @RETURN: 对应序号下的审校记录的信息（如果序号超出范围，则返回NULL）
	 * @FOR: 根据序号得到审校记录的信息，一般会在遍历的时候用到
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.13.46
	 * 
	 */
	public ReviseVO get(Integer index){
		if(index<0||index>this.size()-1)
			return null;
		return reviseMap.get(reviseList.get(index));
	}

	/*****************************************
	 * @USE: 根据审校ID得到对应的审校记录信息
	 * @PARAM: idRvo 审校ID
	 * @RETURN: 审校ID的审校记录的信息（如果没有对应的审校记录，则返回NULL）
	 * @FOR: 根据审校ID得到审校记录的信息，
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.13.46
	 * 
	 */
	public ReviseVO get(String idRvo){
		if(UtilAndTool_L.checkNullOrZero(idRvo))
			return null;
		return reviseMap.get(idRvo);
	}
	
	/*****************************************
	 * @USE: 根据序号得到对应的审校人的记录信息
	 * @PARAM: index 序号
	 * @RETURN: 对应序号下的审校人的记录信息（如果序号超出范围，则返回NULL）
	 * @FOR: 根据序号得到审校人的记录信息，一般会在遍历审校人的时候用到
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.14.19
	 * 
	 */
	public UserReviseVO getUser(Integer index){
		if(index<0||index>this.sizeOfUser()-1)
			return null;
		return this.sysUserMap.get(this.sysUserList.get(index));
	}
	
	/*****************************************
	 * @USE: 根据登录ID得到对应的审校人的记录信息
	 * @PARAM: loginId 登录ID
	 * @RETURN: 对应登录ID下的审校人的记录信息（如果序号超出范围，则返回NULL）
	 * @FOR: 根据登录ID得到审校人的记录信息，页面上，从第一题开始向后，就可能会出现一个
	 * 		审校人没有审校另外的一些题目的情况
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.14.19
	 * 
	 */
	public UserReviseVO getUser(String loginId){
		return this.sysUserMap.get(loginId);
	}
	
	/******************************************
	 * @USE: 添加试题审校记录
	 * @PARAM: rvo 试题审校记录信息VO
	 * @RETURN: ...
	 * @FOR: 将试题的审校记录信息保存到本题下面
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.11.11
	 * 
	 */
	public void addRevise(ReviseVO rvo){
		if(rvo==null||rvo.getId()==null)
			return;
		String idRvo = rvo.getId() + "";
		if(reviseMap.get(idRvo)!=null)
			return ;
		reviseList.add(idRvo);
		reviseMap.put(idRvo, rvo);
		if(rvo.getReviseStatus()!=null&&rvo.getReviseStatus()==1)
			this.reviseStatus = 1;
		String loginId = rvo.getRevisePerson();
		if(UtilAndTool_L.checkNullOrZero(loginId))
			return;
		UserReviseVO urvo = sysUserMap.get(loginId);
		if(urvo==null){
			sysUserList.add(loginId);
			urvo = new UserReviseVO();
			urvo.setLoginId(rvo.getRevisePerson());
			urvo.setUserName(rvo.getReviseName());
		}
		urvo.add(rvo.getReviseTime());
		sysUserMap.put(loginId, urvo);
	}
	
	/*******************************************
	 * @USE: 检查审校记录是否是本试题的审校记录
	 * @PARAM: rvoId 审校记录VO的id
	 * @RETURN: true 是本试题的审校记录，false 不是本试题的审校记录
	 * @FOR: 检查某个审校记录是不是本试题的审校记录，以便进行跳过，或者保存等操作
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.11.17
	 * 
	 */
	public boolean isRevise(Integer rvoId){
		if(id==null||rvoId==null) return false;
		if(rvoId==id) return true;
		return false;
	}
	
	/*******************************************
	 * @USE: 得到试题下审校记录的个数
	 * @PARAM: ...
	 * @RETURN: 试题下审校记录的个数
	 * @FOR: 查看本试题里面到底有多少条审校记录
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.05.11.34
	 * 
	 */
	public Integer size(){
		return this.reviseList.size();
	}
	
	/*******************************************
	 * @USE: 得到审校信息的最新一条审校时间
	 * @PARAM: ...
	 * @RETURN: 审校信息整理后的最新两条信息的字符串
	 * @FOR: 页面上面得到某个试题的审校信息，取得审校时间。审校页面，只显示他自已的审校时间
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.09.17.00
	 * 
	 */
	public String getReviseInfo(){
		if(this.size()==0)
			return null;
		String rvoStr = null;
		int times = 0;
		for(int i=0; i<this.size(); i++){
			ReviseVO rvo = this.get(this.size()-1-i);
			if(rvo==null) continue;
			String rvoStrSingle = rvo.getReviseTime();
			if(rvoStrSingle==null) continue;
			if(this.isSelfTimeShow()){
				if(this.user==null||!((rvo.getRevisor()+"").equals(""+this.user.getId()))) continue;
				if(UtilAndTool_L.checkNotNullOrZero(rvoStrSingle))
					if(rvoStr!=null&&rvoStr.length()>0)
						rvoStr += "<br>";
					else rvoStr = "";
				return rvoStrSingle;
			} else {
				if(UtilAndTool_L.checkNotNullOrZero(rvoStrSingle))
					if(rvoStr!=null&&rvoStr.length()>0)
						rvoStr += "<br>";
				return rvoStrSingle;
			}
		}
		
		return rvoStr;
//		for(int i=0; i<this.size(); i++){
//			ReviseVO rvo = this.get(this.size()-1-i);
//			if(rvo==null) continue;
//			String rvoStrSingle = rvo.getReviseTime();
//			if(rvoStrSingle==null) continue;
//			if(this.isSelfTimeShow()){
//				if(this.user==null||!((rvo.getRevisor()+"").equals(""+this.user.getId()))) continue;
//				if(UtilAndTool_L.checkNotNullOrZero(rvoStrSingle))
//					if(rvoStr!=null&&rvoStr.length()>0)
//						rvoStr += "<br>";
//					else rvoStr = "";
//				rvoStr += rvoStrSingle;
//				times++;
//			} else {
//				if(UtilAndTool_L.checkNotNullOrZero(rvoStrSingle))
//					if(rvoStr!=null&&rvoStr.length()>0)
//						rvoStr += "<br>";
//				rvoStr += rvoStrSingle;
//				times++;
//			}
//			if(times==2) break;
//		}
//		
//		return rvoStr;
//		if(this.size()==1){
//			return this.get(0).getReviseTime();
//		}
//		if(this.reviseList.size()>=2){
//			ReviseVO rvo = this.get(this.size()-1);
//			String rvoStr = rvo.getReviseTime();
//			rvo = null;
//			rvo = this.get(this.size()-2);
//			rvoStr += "<br>" + rvo.getReviseTime();
//			return rvoStr;
//		}
	}

	/*******************************************************
	 * @USE: 得到审校状态
	 * @PARAM: ...
	 * @RETURN: 1 已通过, 0 未通过
	 * @FOR: 得到题目的审校状态时，由于一道题目可能会有多个老师进行审校。当任一老师将此题的
	 * 		审校状态变更为已审校时，此题都算是已审校
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.26.17.42
	 * 
	 */
	public int getReviseStatus() {
		for(int i=0; i<this.size(); i++){
			ReviseVO rvo = this.get(i);
			if(rvo.getReviseStatus()!=null&&rvo.getReviseStatus()==1
					&&rvo.getRevisor()!=null&&this.getUser()!=null
					&&rvo.getRevisor().intValue()==this.getUser().getId().intValue()){
				return 1;
			}
		}
		return 0;
	}
	
	/******************************************************
	 * @USE: 得到当前状态的中文显示
	 * @PARAM: ...
	 * @RETURN: reviseStatus 1 返回已通过, reviseStatus 0 或者  null 返回未通过
	 * @FOR: 得到通过状态。从数据库中得到的是数字reviseStatus 0 或者 1 或者NULL，现在是把题目的审校状态和
	 * 		老师们的审校信息绑定在一起，只要有一位老师审校通过了，就算这道题目通过了。
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.29.09.47
	 * 
	 */
	public String getReviseStatusName() {
		this.reviseStatus = this.getReviseStatus();
		if(this.getReviseStatus()==1)
			return "已通过";
		else
			return "未通过";
	}
	
	/**********************************************************
	 * @USE: 获取回复类型
	 * @PARAM: ...
	 * @RETURN: 得到兼职老师对一个题目审校时提出的问题，是否已得到AMBOW的回复
	 * @FOR: 兼职老师审校题目的时候，可能会发现题目的一些小问题，然后提出来。提出以后，题目
	 * 		的状态显示时，要显示出本题是否有老师已经提出过问题，是新提出的问题（页面上面显
	 * 		示？），还是已经由ＡＭＢＯＷ进行过回复的问题（页面上面显示'-'【笑脸】）
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.03.29.10.10
	 * 
	 */
	public int getReplyType() {
		Integer flag = -1;
		for(int i=0; i<this.size(); i++){
			ReviseVO rvo = this.get(i);
			if(this.user==null){
				if(rvo.getReviseRecordTime()==null)
					continue;
				flag = 1;
				if(rvo.getReviseReplyTime()==null)
					return 0;
			}else if((rvo.getRevisor()+"").equals(this.user.getId()+"")){
				if(rvo.getReviseRecordTime()==null)
					continue;
				flag = 1;
				if(rvo.getReviseReplyTime()==null)
					return 0;
			}
		}
//		for(int i=0; i<this.size(); i++){
//			ReviseVO rvo = this.get(i);
//			if(this.user==null){
//				if(rvo.getReplyType()==null)
//					continue;
//				flag = 1;
//				if(rvo.getReplyType().intValue()==0)
//					return 0;
//			}else if((rvo.getRevisor()+"").equals(this.user.getId()+"")){
//				if(rvo.getReplyType()==null)
//					continue;
//				flag = 1;
//				if(rvo.getReplyType().intValue()==0)
//					return 0;
//			}
//		}
		
		return flag;
	}
	
	public Integer sizeOfUser(){
		return this.sysUserList.size();
	}
	
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
	public ItemType getType() {
		return type;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public void setReviseStatusName(String reviseStatusName) {
		this.reviseStatusName = reviseStatusName;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public boolean isSelfTimeShow() {
		return selfTimeShow;
	}

	public void setSelfTimeShow(boolean selfTimeShow) {
		this.selfTimeShow = selfTimeShow;
	}
	
	public static void main(String... args) {
		Integer a = new Integer(0);
		Integer b = new Integer(0);
		System.out.println(a.intValue()==b.intValue());
	}

	public Integer getReviserstatus() {
		return reviserstatus;
	}

	public void setReviserstatus(Integer reviserstatus) {
		this.reviserstatus = reviserstatus;
	}
}
