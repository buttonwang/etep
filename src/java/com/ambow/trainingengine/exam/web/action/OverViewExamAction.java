package com.ambow.trainingengine.exam.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.exam.service.ExamItemService;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.util.SessionDict;

/*
 * OverViewExamAction.java
 * 
 * Created on Jul 2, 2008 10:18:17 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class OverViewExamAction extends BaseAction {

	private static final long serialVersionUID = -4603296054049272314L;
	
	private ExamItemService examItemService;
	
	private Integer itemNum ;

	public String execute(){
		
		ViewControlProxy viewControl=(ViewControlProxy)this.getSessionObj(SessionDict.ViewControl);
		
		viewControl.setCurrentPageNum(0);//执行全卷浏览后将当前页面置为0..

		repaging(viewControl);
		
		this.getHttpServletRequest().setAttribute("viewControl", viewControl);
		
		if(viewControl.getShowModel()!=1) buttonControl(viewControl);
		
		return SUCCESS;
	}

	/**
	 * 组卷后用户若选择了做部分题，则重新分页。
	 */
	private void repaging(ViewControlProxy viewControl) {
		List<Item> itemList = viewControl.toItems();

		if (itemNum != null && itemNum<itemList.size()) {
			List<Item> subItems = itemList.subList(0, itemNum);
			viewControl.initViewData(subItems);//分页
		}
	}

		
	/**
	 * 判断是否显示“继续训练”按钮、以及相对应的链接  --考研项目适用
	 * 继续训练 
	 *../freshword/freshWordFace.jhtml
	 */
	@SuppressWarnings("unchecked")
	private void buttonControl(ViewControl viewControl) {
		boolean flag=false;
		String trainUrl="";
		long processInstanceId=viewControl.getProcessInstance().getId();
		String hql="select pIns.node,pIns.processStatus from ProcessInstance pIns where pIns.id="+processInstanceId;
		List<Object[]> results=examItemService.find(hql);
		for(Object[] result:results){
			Node node=(Node)result[0];
			ProcessStatus processStatus=(ProcessStatus)result[1];
			if(processStatus==ProcessStatus.RUNNING){
				flag=true;
				String hql2="select nodeIns.id from NodeInstance nodeIns where nodeIns.node.id="+node.getId()+" and nodeIns.processInstance.id="+processInstanceId;
				List<Long> insIds=examItemService.find(hql2);
				trainUrl="../exam/goExam.jhtml?nodeInstanceId="+insIds.get(0).toString();
			}else if(processStatus==ProcessStatus.SUSPEND_FRESHWORD){
				//触发生词训练
				flag=true;
				trainUrl="../freshword/freshWordFace.jhtml";				
			}
		}
		this.getHttpServletRequest().setAttribute("showNextButton", flag);
		this.getHttpServletRequest().setAttribute("trainUrl",trainUrl);		
	}

	public ExamItemService getExamItemService() {
		return examItemService;
	}

	public void setExamItemService(ExamItemService examItemService) {
		this.examItemService = examItemService;
	}

	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getItemNum() {
		return itemNum;
	}

	public void setItemNum(Integer itemNum) {
		this.itemNum = itemNum;
	}

}
