package com.ambow.trainingengine.exam.display.ky;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * DisplayKy47.java
 * 
 * Created on 2008-11-13 下午06:34:31
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
public class Display47 extends Displayky {

	@Override
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		genarateInput(page, viewControl);		
	}
	
	/*
	 * 注意Page 已带有所需的各种信息!~
	 */
	private void genarateInput(Page page, ViewControl viewControl){
		Boolean filter=viewControl.isFilter();
		int mode=viewControl.getShowModel();
		String mapKeyStr=" <input type='hidden' name='mapkey#id#' id='mapkey#id#' value='itemId::subItemId'/>";
		String markStr="<img src='../images/question.gif' value='1' border='0' align='absmiddle' style='cursor: hand' onclick='chgmark(this);' id='mark#id#'/>" ;
		String unmarkStr="<img src='../images/noquestion.gif' value='0' border='0' align='absmiddle' style='cursor: hand' onclick='chgmark(this);' id='mark#id#'/> ";
		String inputStr="<input type='text' class='bont_on' name='userAnswer#id#' value='' #replaceMe# /> ";
		String inputAnswerStr="<input type='text' class='bont_on'  name='userAnswer#id#' value='userAnswerStr' #replaceMe# /> ";
		String rightStr="<img src='../images/true.gif' width='16' height='15' align='texttop' /> ";
		String falseStr="<img src='../images/false.gif' width='16' height='15' align='texttop' />";
		String resolveStr="<a href=\"###\" id=\"resolveInfo#id#\" onclick=showResolveInfo(#id#)><img src=\"../images/icon_jiexi.gif\" align=\"absmiddle\" border=\"0\" alt=\"查看解析\" /></a>";
		String resolveDiv="<DIV class=\"tips2\" style=\"display:none\" id=\"resolveDiv#id#\" onmouseout=hideResolveDiv(#id#)> <IMG  src=\"../images/tips1.gif\"> <ul class=\"tips2bg\">#resolveContent#</ul> <IMG src=\"../images/tips3.gif\"></DIV>";
		
		//if(page.getItemType().getCode().equals("47A")||page.getItemType().getCode().equals("47B")||page.getItemType().getCode().equals("47C")||page.getItemType().getCode().equals("47D")){
				Integer[] isMark=page.getIsMark();
				Integer[] isRight=page.getIsRight();
				Integer[] starInt=page.getStarInt();
				Integer[] starHalf=page.getStarHalf();
				Integer[] isDone=page.getIsDone();
				String[] userAnswer=page.getUserAnswer();
				
				for(Item item:page.getItems()){
					String content=null;
					content=item.getContent();
					//content=replaceWriteSpace(content);
					int i=0;
					 
					for(SubItem subItem:item.getSubItems()){
						//SubItem subItem=item.getSubItems().get(i);
						String replacement="";
						//制造用于替换的字符串
						if(isMark[i]!=null&&isMark[i]==1){
							replacement=replacement+markStr;
						}else{
							replacement=replacement+unmarkStr;
						}
						//题号
						replacement=replacement+"<span class='cRed f16px fB'>"+subItem.getItemNum()+"</span>.";
						//answer
						if(userAnswer[i]!=null){
							replacement=replacement+inputAnswerStr;
							replacement=replacement.replaceFirst("userAnswerStr", userAnswer[i]);
							if(filter&&subItem.getFilterShow()==false) replacement=replacement.replaceFirst("#replaceMe#", "disabled");
							if(subItem.getEnable()==false) replacement=replacement.replaceFirst("#replaceMe#", "disabled");
							
						}else{
							//检查这段代码TODO:...
							replacement=replacement+"<div class=\"li4 cc\">"+inputStr+"</div></div>";
						}
						
						//对错符号
						if((mode==2||mode==3)&&isRight[i]!=null&&isRight[i]==1){
							replacement=replacement+rightStr;
						}else if((mode==2||mode==3)&&isRight[i]==0){
							replacement=replacement+falseStr;
						}
						//星级
						String starStr="";
						for(int j=0;j<starInt[i];j++){
							starStr=starStr+"★";
						}
						for(int k=0;k<starHalf[k];k++){
							starStr=starStr+"☆";
						}
						if(starStr.length()>0)
							replacement=replacement+"(<span class='cRed'>"+starStr+"</span>)";
						
						//解析在这里加上
						
						if(viewControl.getAnalysisPolicy()==1){//随时
							replacement=replacement+resolveStr+resolveDiv;
						}else if(viewControl.getAnalysisPolicy()==2&&isDone[i]!=null&&isDone[i]==1){//做题后
							replacement=replacement+resolveStr+resolveDiv;
						}else if(viewControl.getAnalysisPolicy()==3&&isRight[i]!=null&&isRight[i]==1){//正确后
							replacement=replacement+resolveStr+resolveDiv;	
						}
						String analysis=subItem.getAnswerAnalysis();
						if(analysis.indexOf("$")>0)analysis=analysis.replaceAll("\\u0024", "USD");
						replacement=replacement.replaceFirst("#resolveContent#", analysis);
						//replacement=replacement.replaceAll("#USD#", "\\$");
						
						//mapKey的处理
						replacement=replacement+mapKeyStr;
						replacement=replacement.replaceFirst("itemId", item.getId().toString());
						//System.out.println("The SubItem Id is:"+item.getSubItems().get(i).getId().toString());
						replacement=replacement.replaceFirst("subItemId", subItem.getId().toString());
						
						Integer in=i;
						replacement=replacement.replaceAll("#id#", in.toString());
						content=content.replaceFirst("\\(*\\d{1,3}\\)*\\s*\\.?\\s*<u>\\D*</u>", replacement);
						//对于47C改变其题干中的题号08-09-26
						if(item.getItemType().getCode().contains("47C")){
							String itemNumStr="<span class='cRed f16px fB'>"+subItem.getItemNum()+")</span>.";
							content=content.replaceFirst("\\(\\d{1,3}\\)\\s*\\.?", itemNumStr);
						}
						i=i+1;
					}

					//取得替换后的字符串后,设置入item...
					item.setContentView(content);
					
				}
				
			//}
			
	}

}
