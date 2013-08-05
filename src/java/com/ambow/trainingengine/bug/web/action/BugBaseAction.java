package com.ambow.trainingengine.bug.web.action;

import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.bug.domain.Bug;
import com.ambow.trainingengine.bug.domain.BugInfo;
import com.ambow.trainingengine.bug.domain.BugInfoHistoryAnswerStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.SessionDict;

public class BugBaseAction extends BaseAction {
	private static final long serialVersionUID = -8170583880488040622L;
	public static JSONLeanW json = new JSONLeanW();
	static{
		json.put(Bug.class.getName(), "id,asfProcessInstance,item,status,bugInfos");
		json.put(Item.class.getName(), "id");
		json.put(ProcessInstance.class.getName(), "id");
		json.put(BugInfo.class.getName(), "id,user,teacher,submitInfo,replyInfo,submitTime,replyTime,status,bugInfoHistoryAnswerStatuses");
		json.put(BugInfoHistoryAnswerStatus.class.getName(),"id,historyAnswerStatus");
	}
	 
	public BugInfoHistoryAnswerStatus b;
	public int pageNo = 1;
	
	public ParamObject p = new ParamObject();
	public static String default_Jsp="list";
	public static String default_To="jsp";
	public String to = default_To;
	public String jsp = default_Jsp;	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	protected Object getUser(String type){
		Object user = null;
		if("teacher".equals(type) ){
			user =  getSessionObj(SessionDict.AdminUser);
		}else {//userif(!"user".equals(p.get("type")) )
			user =  getSessionObj(SessionDict.WebUser);
		}
		setRequestAttribute("type",type);
		return user;
	}

 
	protected String to(){
		return to( p, null , null);
	}
	/**
	 * @param to 默认转向，如果p中没有转向
	 * @return
	 */
	protected String toTo(String to){
		return to( p , null, to);
	}
	protected String madeTo(String to){
		return toTo(to);
	}
	
	/**
	 * @param defaultJsp  默认jsp转向页面，如果p没有转向页面
	 * @return
	 */
	protected String to(String defaultJsp){
		return to( p,defaultJsp, null );
	}
	
	/**
	 * 从参数 p中找转向，及转向jsp页面
	 * @param p
	 * @param defaultJsp 默认jsp转向页面，如果p没有转向页面
	 * @param defaultTo  默认jsp转向页面，如果p没有转向
	 * @return
	 */
	protected String to(ParamObject p,String defaultJsp,String defaultTo){
		if(p!=null){
			String returnTo=p.get("to");
			String toJsp =p.get("jsp");
			if(returnTo!=null&&!"".equals(returnTo.trim())){
			}else{
				returnTo = defaultTo;
			}
			if(toJsp!=null&&!"".equals(toJsp.trim())){
			}else{
				toJsp = defaultJsp;
			}
			if(toJsp!=null&&!default_Jsp.equals(toJsp)){
				jsp = toJsp;
			}
			if(returnTo!=null&&!default_To.equals(returnTo)){
				to = returnTo;
			}
		}
		setDefaultRequestAttribute();
		return to;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
	public String getJsp() {
		return jsp;
	}
	 
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setJsp(String jsp) {
		this.jsp = jsp;
	}

	public BugInfoHistoryAnswerStatus getB() {
		return b;
	}

	public void setB(BugInfoHistoryAnswerStatus b) {
		this.b = b;
	}
	public void setDefaultRequestAttribute(){
		setRequestAttribute("p", p);
		setRequestAttribute("b",b);
	}
}
