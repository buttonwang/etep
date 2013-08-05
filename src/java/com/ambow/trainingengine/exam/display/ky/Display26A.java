package com.ambow.trainingengine.exam.display.ky;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.util.ExamkyUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;

/*
 * DisplayKy26A.java
 * 
 * Created on 2008-11-13 下午06:32:52
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class Display26A extends Displayky {

	
	@Override
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);		
		multiExplainPolicy(page, viewControl, request);		
		replaceContentNums(page);
	}
	
	/*
	 * 2008-10-33	wangwei
	 * 完型填空的第一次第二次错误的处理逻辑
	 */
	private void multiExplainPolicy(Page page,ViewControl viewControl, HttpServletRequest request) {
		TrainingPolicy tPolicy=viewControl.getTrainPolicy();
		
		String firstClueStr  = clearReamrkStr(tPolicy.getClueContentFirstFaile());	//第一次错的提示
		String secondClueStr = clearReamrkStr(tPolicy.getClueContentSecondFaile());	//第二次错的提示
		String firstTransStr = clearReamrkStr(tPolicy.getTranslationContentFirstFaile());	//第一次错的译文		
		String secondTransStr= clearReamrkStr(tPolicy.getTranslationContentSecondFaile());	//第二次错的译文
		
		Integer[] errorTimes=new Integer[page.getSize()];	//错了几次
		
		int showModel=viewControl.getShowModel();		
		Map<String,CurrentAnswersStatus> answerMap=null;
		Map<String,HistoryAnswerStatus> hisAnswerMap=null;
		
		if(viewControl.getShowModel()!=3) answerMap=viewControl.getPreAnswersStatus();
		if(viewControl.getShowModel()==3) hisAnswerMap=viewControl.getHistoryStatusMap();
		
		String key;
		CurrentAnswersStatus answerStatus=null;
		HistoryAnswerStatus  hisStatus=null;

		int i=0;
		for(Item item:page.getItems()){
			for(SubItem subItem:item.getSubItems()){
				key=ExamUtil.getMapKey(item, subItem);
				int continueFailureTimes=0;
				if(showModel!=3){
					answerStatus=answerMap.get(key);
					if(answerStatus!=null&&answerStatus.getContinueFailureTimes()!=null)
						continueFailureTimes=answerStatus.getContinueFailureTimes();
				}
				if(showModel==3){
					hisStatus=hisAnswerMap.get(key);
					if(hisStatus!=null&&hisStatus.getContinueFailureTimes()!=null)
						continueFailureTimes=hisStatus.getContinueFailureTimes();
				}
				if(continueFailureTimes>=0)
					errorTimes[i]=continueFailureTimes;//取值应该局限在0...1...2...
				i++;
			}
		}
						
		request.setAttribute("firstClueStr", firstClueStr);
		request.setAttribute("secondClueStr", secondClueStr);
		request.setAttribute("firstTransStr", firstTransStr);
		request.setAttribute("secondTransStr", secondTransStr);
		request.setAttribute("errorTimes", errorTimes);
		
	}

	
	/*
	 * 处理提示语句，去除{***}信息
	 */
	private String clearReamrkStr(String remarkStr){
		if(remarkStr == null)	remarkStr ="";
		else remarkStr = remarkStr.replaceFirst("\\{\\S*\\}", "");		
		return remarkStr;
	}
	
	/*
	 * 用正则来替换完型填空中的空以及题号
	 */
	public void replaceContentNums(Page page){
		String regex="<u>\\s*\\D*\\s*\\d{1,3}\\s*\\D*\\s*</u>";
		//String result=null;
		for(Item item:page.getItems()){
			String content=item.getContent();
			for(SubItem subItem:item.getSubItems()){
				String replacement="<ww>&nbsp;"+subItem.getItemNum()+"&nbsp;</ww>";
				content = ExamkyUtil.replaceOneExpress(content,regex,replacement);
			}
			content=content.replaceAll("<ww>", "<u>");
			content=content.replaceAll("</ww>", "</u>");
			item.setContentView(content);
		}		
	}
}
