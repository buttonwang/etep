package com.ambow.trainingengine.exam.display.mpc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ambow.trainingengine.exam.display.Display;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.SubAnswer;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * DisplayManyFill.java
 * 
 * Created on 2009-1-9 下午05:31:49
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

public class DisplayManyFill extends Display {

	protected static final Log logger = LogFactory.getLog(DisplayManyFill.class);
	
	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		super.doDisplay(page, viewControl, request);
		genarateInput(page, viewControl, request);
	}
	
	/*
	 * 
	 */
	private void genarateInput(Page page, ViewControl viewControl, HttpServletRequest request){
		Boolean filter=viewControl.isFilter();
		int mode = viewControl.getShowModel();
		
				
		String inputStr="<input id='userAnswer_#id#_#aid#' name='userAnswer#id#' " +
						"type='text' class='input_text #answerType# #rightType#'" +
						"value=\"\" #disable#  /> " + 				 
						"<script>document.getElementById('userAnswer_#id#_#aid#').value=\"#userAnswerStr#\";</script>";
				
		int i = 0;
		for(Item item: page.getItems()){
			for(SubItem subItem: item.getSubItems()) {
				String content=null;
				content=subItem.getContent();
				
				String replacement = inputStr;
				
				// 替换题号
				replacement=replacement.replaceAll("#id#", String.valueOf(i));
				
				//disabled 属性
				if ((filter&&subItem.getFilterShow()==false)||(subItem.getExamProperty().getEnable()==false))
					replacement=replacement.replaceFirst("#disable#", "disabled");
				else
					replacement=replacement.replaceFirst("#disable#", "");
				
				content = content.replaceAll("　", "&nbsp;"); //中文空格
				content = content.replaceAll("<u>\\s*", "<u>"); //<u>后的空格，读入文件时所加，会导致空格不能输入。
				content = content.replaceAll("\\s+(&nbsp;)", "$1");
				content = content.replaceAll("(&nbsp;)\\s+", "$1");
				content = content.replaceAll("&nbsp;", "@");
							
				//Pattern p = Pattern.compile("<u>(<[^>]*>)?([^<]*)(</[^>]*>)?</u>");
				Pattern p = Pattern.compile("<u>(<span[^>]*>)?(@*\\s?)(</span>)?</u>");
				Matcher m = p.matcher(content);
				
				int j = 0;
				
				while (m.find()) {
					String subReplacsment = replacement;
					SubAnswer subAnswer;
					if (j > subItem.getExamProperty().getSubAnswers().size()-1) {
						subAnswer = new SubAnswer();
						logger.debug("试题题干录入点与试题答案个数不符合");
					}
					else {
						subAnswer = subItem.getExamProperty().getSubAnswers().get(j);
					}
					
					subReplacsment = subReplacsment.replace("#aid#", String.valueOf(j));
					subReplacsment = subReplacsment.replace("#userAnswerStr#", subAnswer.getUserAnswerTrans());
					subReplacsment = subReplacsment.replace("#answerType#", subAnswer.getAnswerType());	
					if (mode==1) {
						subReplacsment = subReplacsment.replace("#rightType#", "");
					} else if(mode!=1) {
						subReplacsment = subReplacsment.replace("#rightType#", subAnswer.getIsRight()?"correct":"error");
					}
					
					content = m.replaceFirst(subReplacsment);
					m.reset(content);
					j++;
				}
				
				content = content.replaceAll("@", "&nbsp;");
				
				subItem.getExamProperty().setContentView(content);
				logger.info(subItem.getExamProperty().getContentView());
				i++;
			}
			item.getExamProperty().setContentView(item.getContent());
		}
		
		request.setAttribute("itemSize", String.valueOf(i));
	}
	
}
