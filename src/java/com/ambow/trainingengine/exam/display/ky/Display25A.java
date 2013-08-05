package com.ambow.trainingengine.exam.display.ky;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;

/*
 * DisplayKy25A.java
 * 
 * Created on 2008-11-13 下午06:32:02
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
public class Display25A extends Displayky  {

	@Override
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		multiExplainPolicy(page, viewControl, request);
	}

	/*
	 * 2008-09-15
	 * 变态的第一次第二次第三次错误的处理逻辑
	 */
	private void multiExplainPolicy(Page page,ViewControl viewControl, HttpServletRequest request) {
		TrainingPolicy tPolicy=viewControl.getTrainPolicy();
		List<String[]> clueStr=new ArrayList<String[]>();//每道题三个clueStr
		List<String[]> translationStr=new ArrayList<String[]>();//每道题三个译文选项。。
		List<String[]> heightStr=new ArrayList<String[]>();//每道题三句高亮语句？
		Integer[] errorTimes=new Integer[page.getSize()];//几个小题。。
		int showModel=viewControl.getShowModel();
		
		Map<String,CurrentAnswersStatus> answerMap=null;
		if(viewControl.getShowModel()!=3)answerMap=viewControl.getPreAnswersStatus();
		Map<String,HistoryAnswerStatus> hisAnswerMap=null;
		if(viewControl.getShowModel()==3) hisAnswerMap=viewControl.getHistoryStatusMap();
		String key;
		CurrentAnswersStatus answerStatus=null;
		HistoryAnswerStatus hisStatus=null;
		String contentTransAlertStr=null;
		int i=0;
		for(Item item:page.getItems()){
			for(SubItem subItem:item.getSubItems()){
//				if(subItem.getEnable()==false){
//					errorTimes[i]=0;//未出的题不让看解析。。。暂时这样处理。。
//					i++;
//					continue;
//				}
				key=ExamUtil.getMapKey(item, subItem);
				int continueFailureTimes=0;
				if(showModel!=3){
					answerStatus=answerMap.get(key);
					if(answerStatus!=null&&answerStatus.getContinueFailureTimes()!=null)continueFailureTimes=answerStatus.getContinueFailureTimes();
				}
				if(showModel==3){
					hisStatus=hisAnswerMap.get(key);
					if(hisStatus!=null&&hisStatus.getContinueFailureTimes()!=null)continueFailureTimes=hisStatus.getContinueFailureTimes();
				}
				if(continueFailureTimes>=0)
						errorTimes[i]=continueFailureTimes;//取值应该局限在0...1...2...3...
				String[] subClueStr=new String[3];
				//三种提示..tobe comfirm...
				subClueStr[0]=makeClueStr(tPolicy.getClueContentFirstFaile(),makeKpStr(subItem.getKnowledgePoints()),1);
				subClueStr[1]=makeClueStr(tPolicy.getClueContentSecondFaile(),"",2);
				subClueStr[2]=makeClueStr(tPolicy.getClueContentThirdFaile(),subItem.getAnswerAnalysis(),3);
				String[] subTranslationStr=new String[3];
				//译文出的三种分别是 题干、选项、答案解析？
				subTranslationStr[0]=makeTranslationStr(tPolicy.getTranslationContentFirstFaile(),subItem.getContentTranslation(),1);
				subTranslationStr[1]=makeTranslationStr(tPolicy.getTranslationContentSecondFaile(),makeAnswerOptionStr(subItem.getAnswerOptions()),2);
				subTranslationStr[2]=makeTranslationStr(tPolicy.getTranslationContentThirdFaile()," ",3);
				if(contentTransAlertStr==null)contentTransAlertStr=subTranslationStr[2];
				String[] heighStr=new String[4];
				//高亮的六个选项。。提示1 译文1; 提示2 译文2; 提示3 译文3;
				heighStr[0]=subItem.getRelatedKeyPoints();//子题考点相关处
				heighStr[1]=subItem.getContent();//高亮试题
				heighStr[2]=subItem.getRelatedArticle();//高亮子题与文章相关处
				heighStr[3]=makeOptionStr(subItem.getAnswerOptions());//高亮选项
				
				clueStr.add(subClueStr);
				translationStr.add(subTranslationStr);
				heightStr.add(heighStr);
				i++;
				
			}
		}
		
		request.setAttribute("contentTransAlertStr",contentTransAlertStr);
		request.setAttribute("clueStrs", clueStr);
		request.setAttribute("translationStrs", translationStr);
		request.setAttribute("heightStrs", heightStr);
		request.setAttribute("errorTimes", errorTimes);		
	}
	
	/*
	 * 处理提示语句。。
	 */
	private String makeClueStr(String clueStr,String str,int times){
		if(clueStr==null) clueStr=" ";
		if(times==1){
			clueStr=clueStr.replaceFirst("\\{\\s*考点\\s*\\}", str);
		}else if(times==2){
			//无需处理
		}else if(times==3){
			clueStr=clueStr.replaceFirst("\\{\\s*答案解析\\s*\\}", "");
			clueStr=clueStr+"<br>"+str;
		}
		
		return clueStr;		
	}
	
	private String makeKpStr(Set<KnowledgePoint> kpList){
		String kpStr="";
		for(KnowledgePoint kPoint:kpList){
			kpStr=kpStr+kPoint.getName()+" ";
		}
		return kpStr;
	}
	
	/*
	 * 译文， 加一个参数表示译文的次数。。
	 */
	private String makeTranslationStr(String tranStr,String str,int times){
		if(tranStr==null)tranStr="";
		if(times==1){
			tranStr=tranStr.replaceFirst("\\{\\s*试题译文\\s*\\}", " ");
		}else if(times==2){
			tranStr=tranStr.replaceFirst("\\{\\s*选项译文\\s*\\}", " ");
		}else if(times==3){
			tranStr=tranStr.replaceFirst("\\{\\s*文章译文\\s*\\}", " ");
		}
		return tranStr+"<br>"+str;
	}
	
	/*
	 * 构造选项译文
	 */
	private String makeAnswerOptionStr(Set<AnswerOption> options){
		String transStr=" ";
		for(AnswerOption option:options){
			transStr=transStr+" ["+option.getCode()+"] "+option.getTranslation()+" <br>";
		}
		return transStr;		
	}
	
	/*
	 * 用来高亮的选项。。。
	 */
	private String makeOptionStr(Set<AnswerOption> options){
		String str="";
		for(AnswerOption option:options){
			if(str.trim().length()==0)str=str+option.getContent();
			str=str+"*^^*"+option.getContent();
		}
		return str;
	}
}
