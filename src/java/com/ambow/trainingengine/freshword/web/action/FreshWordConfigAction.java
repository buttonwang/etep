package com.ambow.trainingengine.freshword.web.action;

import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * FreshWordConfigAction.java
 * 
 * Created on 2008-7-24 上午11:15:11
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: WangLiBin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class FreshWordConfigAction extends FreshWordBaseAction {
	
	private static final long serialVersionUID = 9747L;
	
	/**
	 * 每页多少数据
	 */
	private static final int PAGE_SIZE=1;
	
	/**
	 * 第几分页
	 */
	private int pageNo;
	
	/**
	 * 生词的id
	 */
	private String wordid;
	
	/**
	 * 设置最后一个单词是否学习成功标记
	 */
	private boolean isOk;
	
	/**
	 * 用户id
	 */
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * 接受请求登陆到生词训练功能
	 * @return
	 */
	public String index(){
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		if (pageNo < 1) {//设置分页默认值
			pageNo = 1;
		}
		UserDataVO userDataVO=(UserDataVO) this.getSessionObj(SessionDict.UserData);
		id=userDataVO.getProcessInstanceId();
		String newid=(String)this.getSessionObj("newwordok");
		if(newid==null||newid.equals("")){
			newid="0";
		}
		this.setSessionObj("newwordno",0);//记录学生没学会的生词
		this.setRequestAttribute("newlist", this.getFreshWordService().list(id,newid));//根据用户id查询出所有和用户有关的生词信息List对象装载
		this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id, newid, pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
		return "index";
	}
	
	/**
	 * 转向单词测一测试功能
	 * @return
	 */
	public String test(){
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		if (pageNo < 1) {//设置分页默认值
			pageNo = 1;
			this.setSessionObj("newwordoks",(String)this.getSessionObj("newwordok"));
		}
		String newid=(String)this.getSessionObj("newwordoks");
		this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id, newid,pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
		return "test";
	}
	
	/**
	 * 转向到单词识别功能
	 * @return
	 */
	public String asserts(){
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		if (pageNo < 1) {//设置分页默认值
			pageNo = 1;
		}
		String newid=(String)this.getSessionObj("newwordoks");
		this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id,newid, pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
		return "asserts";
	}
	
	/**
	 * 生词训练结束处理
	 * @return
	 */
	public String newWordOver(){
		if(isOk){//判断学习最后一个生词是否成功
			newWordOk();//成功
		}else{
			newWordNo();//失败
		}
		Integer intno=(Integer)this.getSessionObj("newwordno");
		int nos=0;
		if(intno!=null){
			nos=intno;
		}
		if(nos==0){//如果都学会了
			this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id, pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
			this.getFreshWordService().deleteBatch(id);//从生词表中清楚该用户的生词
			//销毁学习过程中的信息
			this.getSession().remove("newwordno");
			this.getSession().remove("newwordok");
			this.getSession().remove("newwordoks");
			this.getAddWordService().chStatusBack(id);
			isOk=true;
			return "over";
		}else{//有没学会的
			isOk=false;
			return "overno";
		}
	}
	
	/**
	 * 记录已经学会的生词
	 * @return
	 */
	public String newWordOk(){
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		if (pageNo < 1) {//设置分页默认值
			pageNo = 1;
		}
		String newid=(String)this.getSessionObj("newwordok");
		if(newid!=null&&!newid.equals("")){//记录学会的生词的id
			newid+=","+wordid;
		}else{
			newid=wordid;
		}
		this.setSessionObj("newwordok",newid);//记录学生已经学会的生词
		this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id, pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
		return "test";
	}
	
	/**
	 * 记录没有学会的生词个数
	 * @return
	 */
	public String newWordNo(){
		if (super.getAuthStr()==null) {//验证用户是否合法
			return "reLogin";
		}
		if (pageNo < 1) {//设置分页默认值
			pageNo = 1;
		}
		Integer intno=(Integer)this.getSessionObj("newwordno");//得到学生没学会的生词数
		int nos=0;
		if(intno!=null){
			nos=intno;
		}
		nos+=1;
		this.setSessionObj("newwordno",nos);//记录学生没学会的生词
		this.setRequestAttribute("newpage", this.getFreshWordService().findByUserId(id, pageNo, PAGE_SIZE));//根据用户id查询出所有和用户有关的生词信息Page对象装载
		return "test";
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getWordid() {
		return wordid;
	}

	public void setWordid(String wordid) {
		this.wordid = wordid;
	}

	public boolean getIsOk() {
		return isOk;
	}

	public void setIsOk(boolean isOk) {
		this.isOk = isOk;
	}
}
