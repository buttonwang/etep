package com.ambow.trainingengine.bug.web.data;

import java.util.Set;

import com.ambow.trainingengine.bug.domain.BugInfo;
import com.ambow.trainingengine.bug.domain.BugInfoHistoryAnswerStatus;
import com.ambow.trainingengine.bug.util.ItemAnswerVO;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;

public class BugInfoHistoryAnswerStatusVO extends BugInfoHistoryAnswerStatus {
	public static String DEF_ABCDEFGHIJT = "ABCDEFGHIJT";
	private static final long serialVersionUID = -1525895567514103266L;
	public String correctAnswer = "";
	public Set<AnswerOption> answerOptions  ;
	public ItemAnswerVO itemAnswerVO;
	public BugInfoHistoryAnswerStatusVO (BugInfoHistoryAnswerStatus bihas){
		super(bihas.getBugInfo(),bihas.getHistoryAnswerStatus(),bihas.getPositionInfo());
		setId(bihas.getId());
		initAnswerOptionAndCorrectAnswer();
	}
	public void initAnswerOptionAndCorrectAnswer(){
		BugInfo bi = this.getBugInfo();
		Item item = bi.getBug().getItem();
		//单选是MPC11，多选是MPC12. 一对一选择返回 MPC3X 一对多选择返回 MPC4X
		String rc = ExamUtil.getResultCode(item.getItemType().getCode());
		if("MPC11,MPC12,MPC3X,MPC4X".contains(rc)){
			String answerOptionOrder = this.getHistoryAnswerStatus().getAnswerOptionOrder();
			
			if ((answerOptionOrder==null)||answerOptionOrder.equals("")) {
				answerOptionOrder =DEF_ABCDEFGHIJT;
			}
			answerOptions=Item.getRandomAnswerOption(item.getAnswerOptions(),answerOptionOrder);
		}
	}
	public ItemAnswerVO getItemAnswerVO() {
		return itemAnswerVO;
	}
	public void setItemAnswerVO(ItemAnswerVO itemAnswerVO) {
		this.itemAnswerVO = itemAnswerVO;
	}
}
