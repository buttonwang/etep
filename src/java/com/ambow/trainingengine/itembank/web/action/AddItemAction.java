package com.ambow.trainingengine.itembank.web.action;

import java.util.Date;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.util.ApplicableObject;
import com.ambow.trainingengine.itembank.util.CommonOptions;
import com.ambow.trainingengine.itembank.util.ItemSource;
import com.ambow.trainingengine.itembank.util.MathUtil;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.trainingengine.util.SessionDict;

/*
 * AddItemAction.java
 * 
 * Created on 2008-7-17 上午09:32:19
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
public class AddItemAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6624761476223382910L;

	private ItemService itemService;

	private Item item;
	
	private String itemTypeCode;

	private String[] answerOptionCode;

	private String[] answerOptionContent;

	private String[] answerOptionTranslation;

	private String gradeCodes;

	private String gradeNames;

	private String knowledgePointCodes;

	private String knowledgePointNames;

	private String itemThemeCodes;

	private String itemThemeNames;
	
	private String subject_code;
	
	//子题答案 
	private String[] correctAnswer;
	
	
	/**定义动态跳转匹配页面名称*/
	private String returnUrl;
	/**定义页面显示名称*/
	private String title; 

	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}

	/** 保存对象 */
	public String choose() {
		this.setRequestAttribute("itemTypeList", this.itemService
				.getAll(ItemType.class));
		this.setRequestAttribute("subject_code",subject_code);
		return "choose";
	}

	/** 保存对象 */
	public String save() {
		this.item.genItemCode();
		this.item.setStatus(0);
		this.item.setCreatedTime(new Date());
		this.item.setCreater(null);
		
		//学级		
		if ((gradeCodes!=null) && !gradeCodes.equals("")) {
			String[] gradeArray = gradeCodes.split(",");		
			for (int i = 0; i < gradeArray.length; i++) {
				this.item.getGrades().add(this.itemService.getGradeService().get(gradeArray[i]));
			}
		}
		//知识点
		if ((knowledgePointCodes!=null) && !knowledgePointCodes.equals("")) {
			String[] knowledgePointArray = knowledgePointCodes.split(",");
			for (int i = 0; i < knowledgePointArray.length; i++) {
				this.item.getKnowledgePoints().add(this.itemService.getKnowledgePointService().get(knowledgePointArray[i]));
			}
		}
		//题材
		if ((itemThemeCodes!=null) && !itemThemeCodes.equals("")) {
			String[] itemThemeArray = itemThemeCodes.split(",");
			for (int i = 0; i < itemThemeArray.length; i++) {
				this.item.getItemThemes().add(this.itemService.getItemThemeService().get(itemThemeArray[i]));
			}
		}
		
		//答案选项
		if (answerOptionCode != null) {
			for (int i = 0; i < answerOptionCode.length; i++) {
				AnswerOption tmpAnswerOpton = new AnswerOption();
				tmpAnswerOpton.setCode(answerOptionCode[i]);
				tmpAnswerOpton.setContent(answerOptionContent[i]);
				if(answerOptionTranslation != null &&answerOptionTranslation.length>i) tmpAnswerOpton.setTranslation(answerOptionTranslation[i]);
				tmpAnswerOpton.setItem(this.item);
				this.item.getAnswerOptions().add(tmpAnswerOpton);
			}
		}
		
		if( null ==item.getCorrectAnswer()||"".equals(item.getCorrectAnswer()))
		{
			String correctAnswers="";
			if (correctAnswer != null&& correctAnswer.length>0) {
				
				for (int i = 0; i < correctAnswer.length; i++) {
					
					if( null != correctAnswer[i] && !"".equals(correctAnswer[i]))
					{
						
					correctAnswers+=(MathUtil.removeSpaceAndChinaSpace(correctAnswer[i])+"；");
					}
				}
				
			}
		this.item.setCorrectAnswer(correctAnswers);
		}
		this.itemService.save(this.item);
		this.setRequestAttribute("subject_code",subject_code);
		return "redirect";
	}

	/** 显示新增页面，根据不同题型显示不同的新增页面 */
	public String add() {
		this.setRequestAttribute("regionList", this.itemService.getAll(Region.class));
		this.setRequestAttribute("subjectList", this.itemService.getAll(Subject.class));
		this.setRequestAttribute("gradeList", this.itemService.getAll(Grade.class));
		this.setRequestAttribute("itemTypeList", this.itemService.getAll(ItemType.class));
		this.setRequestAttribute("knowledgePointList", this.itemService.getAll(KnowledgePoint.class));
		this.setRequestAttribute("itemThemeList", this.itemService.getAll(ItemTheme.class));
		this.setRequestAttribute("itemTypeCode", this.itemTypeCode);
		this.setRequestAttribute("itemSourceList", ItemSource.values());
		this.setRequestAttribute("commonOptionsList", CommonOptions.values());
		this.setRequestAttribute("subject_code",subject_code);
		this.setRequestAttribute("applicableObjectList", ApplicableObject.values());
		
		getPageurl();
		this.setRequestAttribute("returnUrl",returnUrl);
		this.setRequestAttribute("title",title);
		return "add";
	}
	
	/**
	 * 整合跳转页面信息包括页面显示名称
	 */
	public void getPageurl()
	{
		
		if("E".equals(subject_code))
		{
			returnUrl="add_item_"+itemTypeCode+".jsp";	
			
			title ="添加英语试题";
		}
		else 
		{
			String code =itemTypeCode.substring(itemTypeCode.length()-2, itemTypeCode.length()-1);
			 returnUrl="add_mpc_"+code+"x.jsp";
			 
			 if("M".equals(subject_code))
			 {
				 title ="添加数学试题";
			 }
			 else  if("P".equals(subject_code))
			 {
				title ="添加物理试题";
			 }
			 else  if("C".equals(subject_code))
			 {
				title ="添加化学试题";
			 }
		}
		
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String[] getAnswerOptionCode() {
		return answerOptionCode;
	}

	public void setAnswerOptionCode(String[] answerOptionCode) {
		this.answerOptionCode = answerOptionCode;
	}

	public String[] getAnswerOptionContent() {
		return answerOptionContent;
	}

	public void setAnswerOptionContent(String[] answerOptionContent) {
		this.answerOptionContent = answerOptionContent;
	}

	public String[] getAnswerOptionTranslation() {
		return answerOptionTranslation;
	}

	public void setAnswerOptionTranslation(String[] answerOptionTranslation) {
		this.answerOptionTranslation = answerOptionTranslation;
	}
	
	public String getGradeCodes() {
		return gradeCodes;
	}

	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getKnowledgePointCodes() {
		return knowledgePointCodes;
	}

	public void setKnowledgePointCodes(String knowledgePointCodes) {
		this.knowledgePointCodes = knowledgePointCodes;
	}

	public String getKnowledgePointNames() {
		return knowledgePointNames;
	}

	public void setKnowledgePointNames(String knowledgePointNames) {
		this.knowledgePointNames = knowledgePointNames;
	}

	public String getItemThemeCodes() {
		return itemThemeCodes;
	}

	public void setItemThemeCodes(String itemThemeCodes) {
		this.itemThemeCodes = itemThemeCodes;
	}

	public String getItemThemeNames() {
		return itemThemeNames;
	}

	public void setItemThemeNames(String itemThemeNames) {
		this.itemThemeNames = itemThemeNames;
	}
	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String[] getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String[] correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public void setReviewRound(String[] reviewRound) {
		String str = "";
		for (int i = 0; i < reviewRound.length; i++) {
			str = str + reviewRound[i]+",";
		}
		str = str.substring(0,str.lastIndexOf(","));
		this.item.setReviewRound(str);
	}

}
