package com.ambow.trainingengine.web.action;

import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.AccessUrl;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.util.SwieBean;
import com.ambow.trainingengine.web.data.UserDataVO;
@SuppressWarnings("serial")
public class SwieArticleStatusAction  extends WebBaseAction {
	
	private SwieBean swieBean;
	private String xmlStr="";
	private String itemId="";
	@SuppressWarnings("static-access")
	public String execute() {	
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		Webuser webUser=(Webuser)this.getSessionObj(SessionDict.WebUser);
		//加载写作信息
		SwieBean swieBean=this.getSwieBean();	
		swieBean.setUserName(userData.getLoginName());
		swieBean.setPassword(null);
		swieBean.setTitle(null);
		swieBean.setDirection(null);
		swieBean.setContent(null);
		
		if (swieBean.getUse().equals("false")) return "unused"; 
		
		AccessUrl au=new AccessUrl();
		
		String url=swieBean.getArticleStatusUrl()+"?userName="+swieBean.getUserName() + "&sysAbbr="
		+swieBean.getSysAbbr()+"&sysCode="+swieBean.getSysCode()+"&sysUserType="+swieBean.getSysUserType()
		+"&sysId="+webUser.getId()+","+this.getItemId();
		
		this.setXmlStr(au.getPageContent(url).replaceAll("'", "’"));
		//System.out.println(au.getPageContent(url));
		//System.out.println(url);
		return SUCCESS;
	}
	
	/* 
	 * 生成传送到客户端的SWIEbean信息。提交文章给外教的action 
	 */
	private void genSWIEInfo() {
		swieBean.setUserName(((Webuser)this.getSessionObj("webuser")).getLoginName());
		this.getHttpServletRequest().setAttribute("swie", swieBean);
	}

	public SwieBean getSwieBean() {
		return swieBean;
	}

	public void setSwieBean(SwieBean swieBean) {
		this.swieBean = swieBean;
	}
	public String getXmlStr() {
		return xmlStr;
	}
	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
}
