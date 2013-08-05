package com.ambow.trainingengine.attention.web.action;

import java.io.IOException;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.trainingengine.attention.service.AttentionService;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.RequestAttributeByMap;
import com.ambow.trainingengine.util.SessionDict;


public class AttentionAction extends AttentionBaseAction {
	
	private static final long serialVersionUID = 4479775375902519612L;
	
	int attentionId;
	int mode;
	String ids;
	public AttentionService attentionService;
	
	public String itemAttentionCount(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		setRequestAttribute("itemAttentionCount", attentionService.getItemAttentionCount(user,p.getInteger("itemId")));
		return to("itemAttentionCount");
	}

	public String saveOrUpdate(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		attentionService.addOrUpdate(p,user);
		return to("success");
	}
	
	public String disAttentionBatch(){
		if(ids != null && !ids.trim().equals("")){
			if(ids.indexOf(",")>0){
				ids = ids.substring(0, ids.length()-1);
			}
			this.getAttentionService().disAttentionBatch(ids);
		}
		return "attention_list";
	}
	
	public String disAttention(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		attentionService.disAttention(user,p);
		return to("success");
	}
	
	public String addFlower(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		setRequestAttribute("flowerNum", attentionService.addFlower(p,user));
		return to("show");
	}
	
	public String throwEgg(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		setRequestAttribute("eggNum", attentionService.throwEgg(p,user));
		return to("show");
	}

	public String show(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		Long nodeInstanceId = p.getLong("nodeInstanceId");
		if(nodeInstanceId != null && nodeInstanceId==0){
			ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
			NodeInstance currentNodeInstance=viewControl.getExamNodeIns();
			nodeInstanceId = currentNodeInstance.getId();
		}
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.show(p,user,pageNo,nodeInstanceId));
		setRequestAttribute("webuser", user);
		setRequestAttribute("attentionId", attentionId);
		setRequestAttribute("mode", mode);
		return to("show");
	}
	
	public String addOrEdit(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		Long nodeInstanceId = p.getLong("nodeInstanceId");
		if(nodeInstanceId != null && nodeInstanceId==0){
			ViewControl viewControl=(ViewControl)this.getSessionObj(SessionDict.ViewControl);
			NodeInstance currentNodeInstance=viewControl.getExamNodeIns();
			nodeInstanceId = currentNodeInstance.getId();
			//System.out.println("id="+currentNodeInstance.getId());
			//System.out.println("name="+currentNodeInstance.getNode().getName());
		}
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.show(p,user,pageNo,nodeInstanceId));
		return to("addOrEdit");
	}
	
	public String list(){
		Webuser user =(Webuser) getSessionObj(SessionDict.WebUser);
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.list(p,user,pageNo));
		return to("list");
	}

	public String showAdmin(){
		SysUser user =(SysUser) getSessionObj(SessionDict.AdminUser);
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.showAdmin(p,user,pageNo));
		setRequestAttribute("webuser", user);
		return to("showAdmin");
	}
	
	public void forbiddenOrReviewLearnNote(){
		SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
		if (attentionService.forbiddenOrReviewLearnNote(sysUser,p)) {
			try {
				this.getHttpServletResponse().getWriter().write("isSuccess");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String reviewLearnNote(){
		SysUser user =(SysUser) getSessionObj(SessionDict.AdminUser);
		RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.showAdmin(p,user,pageNo));
		setRequestAttribute("webuser", user);
		return to("success");
	}
	
	public String listAdmin(){
		SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
		if(sysUser!=null){
			RequestAttributeByMap.setAttributeByMap(this.getHttpServletRequest(),attentionService.listAdmin(p,sysUser,pageNo));
		}
		return to("listAdmin");
	}
	
	public void saveNoteSummary(){
		SysUser sysUser =(SysUser) getSessionObj(SessionDict.AdminUser);
		if (attentionService.saveNoteSummary(p,sysUser)) {
			try {
				this.getHttpServletResponse().getWriter().write("isSuccess");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public AttentionService getAttentionService() {
		return attentionService;
	}

	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}
	
	public int getAttentionId() {
		return attentionId;
	}

	public void setAttentionId(int attentionId) {
		this.attentionId = attentionId;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
	
	String height;
	String width;
	String random;
	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
	 
	
	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
}
