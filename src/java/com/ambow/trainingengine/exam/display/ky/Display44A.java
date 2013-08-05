package com.ambow.trainingengine.exam.display.ky;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.util.ExamkyUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * DisplayKy44A.java
 * 
 * Created on 2008-11-13 下午06:33:50
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
public class Display44A extends Displayky {

	@SuppressWarnings("unchecked")
	@Override
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		
		List<Float> scoreList = (List<Float>)request.getAttribute("scoreList"); 		
		genarateViewFor44A(page, scoreList, viewControl);
		
		ExamkyUtil.handWritingView(page, viewControl);
	}

	/*
	 * 注意Page 已带有所需的各种信息!~
	 * 标红题号、 译-图片、 mark标记、  星级信息
	 */
	public void genarateViewFor44A(Page page,List<Float> scoreList, ViewControl viewControl){
		Boolean filter=viewControl.isFilter();
		int mode=viewControl.getShowModel();
		String mapKeyStr=" <input type='hidden' name='mapkey#id#' id='mapkey#id#' value='itemId::subItemId'/>";
		String markStr="<img src='../images/question.gif' value='1' border='0' align='absmiddle' style='cursor: hand' onclick='chgmark(this);' id='mark#id#'/>" ;
		String unmarkStr="<img src='../images/noquestion.gif' value='0' border='0' align='absmiddle' style='cursor: hand' onclick='chgmark(this);' id='mark#id#'/> ";
		//String inputStr="<input type='text'  name='userAnswer#id#' value='' #replaceMe# /> ";
		//String inputAnswerStr="<input type='text'  name='userAnswer#id#' value='userAnswerStr' #replaceMe# /> ";
		String noTranStr="<img src='../images/file_yi1.gif' id=\"transImgButton#id#\" alt='译' width='24' height='24' border='0' align='absmiddle'  style='cursor: hand' onclick=\"popup_show('popup#id#','popup_drag#id#','popup_exit#id#')\" />";
		String tranStr="<img src='../images/file_yi.gif' id=\"transImgButton#id#\" alt='译' width='24' height='24' border='0' align='absmiddle'  style='cursor: hand' onclick=\"popup_show('popup#id#','popup_drag#id#','popup_exit#id#')\" />";
		String rightStr="<img src='../images/true.gif' width='16' height='15' align='texttop' /> ";
		String falseStr="<img src='../images/false.gif' width='16' height='15' align='texttop' />";
		//String resolveStr="<a href=\"###\" id=\"resolveInfo#id#\" onclick=showResolveInfo(#id#)><img src=\"../images/icon_jiexi.gif\" align=\"absmiddle\" border=\"0\" alt=\"查看解析\" /></a>";
		//String resolveDiv="<DIV class=\"tips2\" style=\"display:none\" id=\"resolveDiv#id#\" onmouseout=hideResolveDiv(#id#)> <IMG  src=\"../images/tips1.gif\"> <ul class=\"tips2bg\">#resolveContent#</ul> <IMG src=\"../images/tips3.gif\"></DIV>";
		String inputDiv="<div class=\"sample_popup\" id=\"popup#id#\" style=\"visibility: hidden; display:none\">" +
				"<div class=\"menu_form_header\" id=\"popup_drag#id#\">" +
				"<img src=../images/delete.gif alt=\"关闭\" name=\"popup_exit\" align=\"absmiddle\" class=\"menu_form_exit\"   id=\"popup_exit#id#\" />" +
				"&nbsp;&nbsp;第#itemNum#题 &nbsp; 翻译</div><div class=\"menu_form_body\">" +
				"<form method=\"post\" action=\"\"><table><tr><td colspan=\"2\">" +
				"<textarea name=\"textarea\" id=\"userAnswer#id#\" onChange=\"javascript:checkTextArea(this)\" cols=\"45\" rows=\"5\" style=\"width:522px; height:80px; #replaceMe# \">#content#</textarea>" +
				"</td></tr><tr><td width=\"220\">&nbsp;</td><td width=\"109\"></td></tr></table>" +
				"</form></div></div>";
		//viewDiv 用于主题浏览模式
		String viewDiv="<div class=\"sample_popup\" id=\"popup#id#\" style=\"visibility: hidden; display:none \">" +
				"<div class=\"menu_form_header\" id=\"popup_drag#id#\">" +
				"<img src=../images/delete.gif alt=\"关闭\" name=\"popup_exit\" align=\"absmiddle\" class=\"menu_form_exit\"   id=\"popup_exit#id#\" />" +
				"&nbsp;&nbsp;第#itemNum#题 &nbsp; 翻译</div>" +
				"<div class=\"menu_form_body\">" +
				"<form method=\"post\" action=\"\">" +
				"<div><div style=\"background-color:#f5f5f5;font-size:14px; height:25px; padding:5px 10px 0 10px;\"> <strong>我的译文： </strong>" +
				"得分（#myScore#分） #jiexiimg# &nbsp; #daanimg# </div>" +				
				"<div style=\"border:1px solid #cccccc;padding:10px;font-size:14px;\">#content#</div></div>" +
				
				"<div id=\"fanwen#id#\" style=\"display:none;\" class=\"fanwen1\">" +
				"<div style=\"background-color:#f5f5f5;font-size:14px;line-height:21px;height:25px; padding:5px 10px 0 10px;\">" +
				" <strong> 解析： </strong></div>" +
				"<div style=\"border:1px solid #cccccc;padding:10px;font-size:14px;\">#fanwenContent#</div>" +
				"</div>" +
				
				"<div id=\"daan#id#\" style=\"display:none;\" class=\"fanwen1\">" +
				"<div style=\"background-color:#f5f5f5;font-size:14px;line-height:21px;height:25px; padding:5px 10px 0 10px;\">" +
				" <strong> 参考答案： </strong></div>" +
				"<div style=\"border:1px solid #cccccc;padding:10px;font-size:14px;\">#daanContent#</div>" +
				"</div>" +
								
				"</form></div></div>";
		
		//if(page.getItemType().getCode().equals("Z1E44A")){
				Integer[] isMark=page.getIsMark();
				Integer[] isRight=page.getIsRight();
				Integer[] starInt=page.getStarInt();
				Integer[] starHalf=page.getStarHalf();
				Integer[] isDone=page.getIsDone();
				
				String[] userAnswer=page.getUserAnswer();
				
				//替换思路。。将(\d)<u> 先期逐个替换为 (\d)<v> 待全部替换完毕后将全部的<v>替换为<u>
				for(Item item:page.getItems()){
					String content=null;
					content=item.getContent();
					
					int i=0;
					for(SubItem subItem:item.getSubItems()){
						String replacement="";
						//制造用于替换的字符串
						//题号
						replacement=replacement+"<span class='cRed f16px fB'>"+item.getSubItems().get(i).getItemNum()+")</span>";
						
						//answer
						if(userAnswer[i]!=null&&userAnswer[i].trim().length()>0){
							//System.out.println("userAnswer:"+userAnswer[i]);
							replacement=replacement+tranStr;
						}else{
							replacement=replacement+noTranStr;
						}
						if(mode==1){
							replacement=replacement+inputDiv;
						}else{
							replacement=replacement+viewDiv;
							replacement=replacement.replaceAll("#myScore#", scoreList.get(i).toString());
						}
						
						if (userAnswer[i]==null) userAnswer[i] = "";
						replacement=replacement.replaceFirst("#content#", userAnswer[i]);
						replacement=replacement.replaceAll("#itemNum#", subItem.getItemNum().toString());
						if(filter&&subItem.getFilterShow()==false) replacement=replacement.replaceFirst("#replaceMe#", "disabled");
						if(subItem.getEnable()==false) replacement=replacement.replaceFirst("#replaceMe#", "disabled");//未出时不允许做
						
						//疑问标记
						if(isMark[i]!=null&&isMark[i]==1){
							replacement=replacement+markStr;
						}else{
							replacement=replacement+unmarkStr;
						}
						
						//对错符号
//						if((mode==2||mode==3)&&isRight[i]!=null&&isRight[i]==1){
//							replacement=replacement+rightStr;
//						}else if((mode==2||mode==3)&&isRight[i]==0){
//							replacement=replacement+falseStr;
//						}
						//星级
						String starStr="";
						if (starInt[i]!=null) {
							for(int j=0;j<starInt[i];j++){
								starStr=starStr+"★";
							}
						}						
						for(int k=0; k< (starHalf[k]==null?0:starHalf[k]); k++){
							starStr=starStr+"☆";
						}
							
						if(starStr.length()>0)
							replacement=replacement+"(<span class='cRed'>"+starStr+"</span>)";
						
						//解析在这里加上
						String jiexiimg="<a href=\"javascript:void(null)\" onclick=\"openShutManager(this,'fanwen#id#'); hideDiv('daan#id#'); \">" +
								"<img src=\"../images/icon_jiexi.gif\" align=\"absmiddle\" border=\"0\" alt=\"查看解析\" /></a>";
						
						if(viewControl.getAnalysisPolicy()==1){//随时
							//是否放出范文按钮replacement=replacement+resolveStr+resolveDiv;
							replacement=replacement.replaceFirst("#jiexiimg#", jiexiimg);
						}else if(viewControl.getAnalysisPolicy()==2&&isDone[i]!=null&&isDone[i]==1){//做题后
							replacement=replacement.replaceFirst("#jiexiimg#", jiexiimg);
						}else if(viewControl.getAnalysisPolicy()==3&&isRight[i]!=null&&isRight[i]==1){//正确后
							replacement=replacement.replaceFirst("#jiexiimg#", jiexiimg);	
						}else{
							replacement=replacement.replaceFirst("#jiexiimg#", "");
						}
						replacement=replacement.replaceFirst("#fanwenContent#", subItem.getAnswerAnalysis());
						
						//参考答案在这里加上
						String daanimg="<a href=\"javascript:void(null)\" onclick=\"openShutManager(this,'daan#id#');  hideDiv('fanwen#id#'); \">" +
								"<img src=\"../images/icon_daan.gif\" align=\"absmiddle\" border=\"0\" alt=\"参考答案\" /></a>";
						
						if(viewControl.getAnalysisPolicy()==1){//随时
							//是否放出范文按钮replacement=replacement+resolveStr+resolveDiv;
							replacement=replacement.replaceFirst("#daanimg#", daanimg);
						}else if(viewControl.getAnalysisPolicy()==2&&isDone[i]!=null&&isDone[i]==1){//做题后
							replacement=replacement.replaceFirst("#daanimg#", daanimg);
						}else if(viewControl.getAnalysisPolicy()==3&&isRight[i]!=null&&isRight[i]==1){//正确后
							replacement=replacement.replaceFirst("#daanimg#", daanimg);	
						}else{
							replacement=replacement.replaceFirst("#daanimg#", "");
						}
						replacement=replacement.replaceFirst("#daanContent#", subItem.getCorrectAnswer());
						
						//mapKey的处理
						replacement=replacement+mapKeyStr;
						replacement=replacement.replaceFirst("itemId", item.getId().toString());
						replacement=replacement.replaceFirst("subItemId", subItem.getId().toString());
						
						Integer in=i;
						replacement=replacement.replaceAll("#id#", in.toString());
						replacement=replacement+"<u>";
						String rep="\\(?\\s*\\d{1,3}\\s*\\)?\\s*<u>";
						//replacement="<B> AAAAA </B>";
						content=content.replaceFirst(rep, replacement);// \\(?\\s*\\D*\\s*\\d{1,3}\\s*\\D*\\s*\\)?\\s*<u>         
						i=i+1;
					}

					//取得替换后的字符串后,设置入item...
					item.setContentView(content);
					
				}
				
			//}
			
	}
}
