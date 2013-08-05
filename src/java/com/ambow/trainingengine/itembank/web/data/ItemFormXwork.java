package com.ambow.trainingengine.itembank.web.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.util.MathUtil;
import com.ambow.trainingengine.util.ParamObject;

public class ItemFormXwork {
	private static final String SubItem_CorrectAnswers = "subItemCorrectAnswers";
	private static final String SubItem_KnowledgePointCodes = "subItemKnowledgePointCodes";
	private static final String SubItem_KnowledgePointCodes2 = "subItemKnowledgePointCodes2";
	private static final String Item_ItemThemeCodes = 	   "itemItemThemeCodes";
	private static final String Item_KnowledgePointCodes = "itemKnowledgePointCodes";
	private static final String Item_KnowledgePointCodes2 = "itemKnowledgePointCodes2";	
	private static final String Item_GradeCodes = 		   "itemGradeCodes";

	public static Item makeToSaveItem(Item itemFromXwork,ParamObject p,HibernateEntityDao dao){
		if(itemFromXwork!=null&&itemFromXwork.getId()!=null){
			Item item=dao.get(Item.class,itemFromXwork.getId());
			if(item!=null){
				if(itemFromXwork.getAnswerGroup()!=null)
					item.setAnswerGroup(itemFromXwork.getAnswerGroup());
				if(itemFromXwork.getCourseVersion()!=null)
					item.setCourseVersion(itemFromXwork.getCourseVersion());
				if(itemFromXwork.getCode()!=null)
					item.setCode(itemFromXwork.getCode());
				if(itemFromXwork.getYear()!=null)
					item.setYear(itemFromXwork.getYear());
				if(itemFromXwork.getSource()!=null)
					item.setSource(itemFromXwork.getSource());
				if(itemFromXwork.getSourceBook()!=null)
					item.setSourceBook(itemFromXwork.getSourceBook());
				if(itemFromXwork.getSourceFile()!=null)
					item.setSourceFile(itemFromXwork.getSourceFile());
				if(itemFromXwork.getOriginalItemNum()!=null)
					item.setOriginalItemNum(itemFromXwork.getOriginalItemNum());
				if(itemFromXwork.getScore()!=null)
					item.setScore(itemFromXwork.getScore());
				if(itemFromXwork.getScore2()!=null)
					item.setScore2(itemFromXwork.getScore2());				
				if(itemFromXwork.getDifficultyValue()!=null)
					item.setDifficultyValue(itemFromXwork.getDifficultyValue());
				if(itemFromXwork.getValidityValue()!=null)
					item.setValidityValue(itemFromXwork.getValidityValue());
				if(itemFromXwork.getAnsweringTime()!=null)
					item.setAnsweringTime(itemFromXwork.getAnsweringTime());
				if(itemFromXwork.getAnsweringTimeByMin() !=null)
					item.setAnsweringTimeByMin(itemFromXwork.getAnsweringTimeByMin());
				if(itemFromXwork.getContent()!=null)
					item.setContent(itemFromXwork.getContent());
				if(itemFromXwork.getContentTranslation()!=null)
					item.setContentTranslation(itemFromXwork.getContentTranslation());
				if(itemFromXwork.getWordCount()!=null)
					item.setWordCount(itemFromXwork.getWordCount());
				if(itemFromXwork.getReadingTime()!=null)
					item.setReadingTime(itemFromXwork.getReadingTime());
				if(itemFromXwork.getReadingTimeByMin()!=null)
					item.setReadingTimeByMin(itemFromXwork.getReadingTimeByMin());
				if(itemFromXwork.getCorrectAnswer()!=null)
					item.setCorrectAnswer(itemFromXwork.getCorrectAnswer());
				if(itemFromXwork.getAnswerAnalysis()!=null)
					item.setAnswerAnalysis(itemFromXwork.getAnswerAnalysis());
				if(itemFromXwork.getAnalysisAtLarge1()!=null)
					item.setAnalysisAtLarge1(itemFromXwork.getAnalysisAtLarge1());
				if(itemFromXwork.getAnalysisAtLarge2()!=null)
					item.setAnalysisAtLarge2(itemFromXwork.getAnalysisAtLarge2());
				if(itemFromXwork.getAnalysisAtLarge3()!=null)
					item.setAnalysisAtLarge3(itemFromXwork.getAnalysisAtLarge3());
				if(itemFromXwork.getThinkingAnalyse()!=null)
					item.setThinkingAnalyse(itemFromXwork.getThinkingAnalyse());
				if(itemFromXwork.getSkills()!=null)
					item.setSkills(itemFromXwork.getSkills());
				if(itemFromXwork.getDifficultSentance()!=null)
					item.setDifficultSentance(itemFromXwork.getDifficultSentance());
				if(itemFromXwork.getScoringKeywords()!=null)
					item.setScoringKeywords(itemFromXwork.getScoringKeywords());
				if(itemFromXwork.getWritingTemplate()!=null)
					item.setWritingTemplate(itemFromXwork.getWritingTemplate());
				if(itemFromXwork.getScoringNorm()!=null)
					item.setScoringNorm(itemFromXwork.getScoringNorm());
				if(itemFromXwork.getApplicableObject()!=null)
					item.setApplicableObject(itemFromXwork.getApplicableObject());
				if(itemFromXwork.getKeywords()!=null)
					item.setKeywords(itemFromXwork.getKeywords());
				if(itemFromXwork.getKeySentances()!=null)
					item.setKeySentances(itemFromXwork.getKeySentances());
				if(itemFromXwork.getCreater()!=null)
					item.setCreater(itemFromXwork.getCreater());
				if(itemFromXwork.getCreatedTime()!=null)
					item.setCreatedTime(itemFromXwork.getCreatedTime());
				if(itemFromXwork.getUpdater()!=null)
					item.setUpdater(itemFromXwork.getUpdater());
				if(itemFromXwork.getUpdatedTime()!=null)
					item.setUpdatedTime(itemFromXwork.getUpdatedTime());
				if(itemFromXwork.getVerifier()!=null)
					item.setVerifier(itemFromXwork.getVerifier());
				if(itemFromXwork.getVerifiedTime()!=null)
					item.setVerifiedTime(itemFromXwork.getVerifiedTime());
				if(itemFromXwork.getStatus()!=null)
					item.setStatus(itemFromXwork.getStatus());
				if(itemFromXwork.getOpinion()!=null)
					item.setOpinion(itemFromXwork.getOpinion());
				if(itemFromXwork.getItemValue()!=null)
					item.setItemValue(itemFromXwork.getItemValue());	
				if(itemFromXwork.getAbilityValue()!=null)
					item.setAbilityValue(itemFromXwork.getAbilityValue());
				if(itemFromXwork.getOriginalPaperCode()!=null)
					item.setOriginalPaperCode(itemFromXwork.getOriginalPaperCode());
				if(itemFromXwork.getAnswerPrototype()!=null)
					item.setAnswerPrototype(itemFromXwork.getAnswerPrototype());
				if(itemFromXwork.getReviewRound()!=null)
					item.setReviewRound(itemFromXwork.getReviewRound());
				
				if(itemFromXwork.getHint()!=null)
					item.setHint(itemFromXwork.getHint());
				
				if(itemFromXwork.getRegion()!=null){
					item.setRegion(dao.get(Region.class,itemFromXwork.getRegion().getCode()));
				}
				if(itemFromXwork.getItemType()!=null&&itemFromXwork.getItemType().getCode()!=null){
					ItemType  itemType = dao.get(ItemType.class,itemFromXwork.getItemType().getCode());
					item.setItemType(itemType);
				}
				
				//设置题的学级
				String itemGradeCodes  =p.get(Item_GradeCodes );
				if(itemGradeCodes!=null) {
					String[] codes=itemGradeCodes.split(",");
					Set<Grade> itemGrades=item.getGrades();
					if(itemGrades==null){
						itemGrades=new HashSet<Grade>();
						item.setGrades(itemGrades);
					}else{
						itemGrades.clear();
					}
					if (!itemGradeCodes.equals(""))
						for(int i=0;i<codes.length;i++){
							if(!"".equals(codes[i].trim())){
								item.getGrades().add(dao.get(Grade.class,codes[i]));
							}
						}
				}
				//设置题的主知识点
				String itemKnowledgePointCodes  =p.get(Item_KnowledgePointCodes );
				if(itemKnowledgePointCodes!=null&&!"".equals(itemKnowledgePointCodes.trim())) {
					String[] codes=itemKnowledgePointCodes.split(",");
					Set<KnowledgePoint> itemKnowledgePoints=item.getKnowledgePoints();
					if(itemKnowledgePoints==null){
						itemKnowledgePoints=new HashSet<KnowledgePoint>();
						item.setKnowledgePoints(itemKnowledgePoints);
					}else{
						itemKnowledgePoints.clear();
					}
					for(int i=0;i<codes.length;i++){
						if(!"".equals(codes[i].trim())){
							item.getKnowledgePoints().add(dao.get(KnowledgePoint.class,codes[i]));
						}
					}
				}
				//设置题的次知识点
				String itemKnowledgePointCodes2  =p.get(Item_KnowledgePointCodes2 );
				if(itemKnowledgePointCodes2!=null&&!"".equals(itemKnowledgePointCodes2.trim())) {
					String[] codes=itemKnowledgePointCodes2.split(",");
					Set<KnowledgePoint> itemKnowledgePoints2=item.getKnowledgePoints2();
					if(itemKnowledgePoints2==null){
						itemKnowledgePoints2=new HashSet<KnowledgePoint>();
						item.setKnowledgePoints2(itemKnowledgePoints2);
					}else{
						itemKnowledgePoints2.clear();
					}
					for(int i=0;i<codes.length;i++){
						if(!"".equals(codes[i].trim())){
							item.getKnowledgePoints2().add(dao.get(KnowledgePoint.class,codes[i]));
						}
					}
				}
				//设置题的题材
				String itemItemThemeCodes  =p.get(Item_ItemThemeCodes );
				if(itemItemThemeCodes!=null&&!"".equals(itemItemThemeCodes.trim())) {
					String[] codes=itemItemThemeCodes.split(",");
					Set<ItemTheme> itemItemThemes=item.getItemThemes();
					if(itemItemThemes==null){
						itemItemThemes=new HashSet<ItemTheme>();
						item.setItemThemes(itemItemThemes);
					}else{
						itemItemThemes.clear();
					}
					for(int i=0;i<codes.length;i++){
						if(!"".equals(codes[i].trim())){
							item.getItemThemes().add(dao.get(ItemTheme.class,codes[i]));
						}
					}
				}
				//设置答案子选项
				if(itemFromXwork.getAnswerOptions()!= null) {
					Set<AnswerOption> itemAnswerOptions = item.getAnswerOptions();
					if(itemAnswerOptions==null){
						itemAnswerOptions = new HashSet<AnswerOption>();
						item.setAnswerOptions(itemAnswerOptions);
					}
					for (AnswerOption itemAnswerOption : itemAnswerOptions) {
						for (AnswerOption itemFromHtmlAnswerOptions : itemFromXwork.getAnswerOptions()) {
							if(itemAnswerOption.getCode().equals(itemFromHtmlAnswerOptions.getCode())){
								if(itemFromHtmlAnswerOptions.getContent()!=null){
									itemAnswerOption.setContent(itemFromHtmlAnswerOptions.getContent());
								}
								if(itemFromHtmlAnswerOptions.getTranslation()!=null){
									itemAnswerOption.setTranslation(itemFromHtmlAnswerOptions.getTranslation());
								}
							}
						}
					}
				}
				initItemSubItems(itemFromXwork.getSubItems(),item,p,dao);
				//通过子题计算试题分数
				initItemScoreScore2 (item );
				//通过子题计算答题时间
				initItemAnswerTime (item );
				item.genItemCode();
			}
			return item;
		}
		return null;
	}/**
	 * 通过子题计算试题答题时间
	 * @param item
	 */
	public static void initItemAnswerTime(Item item){
		if(item!=null&&item.getSubItems()!=null&&item.getSubItems().size()>0){
			int answeringTimeByMin = 0;
			for (SubItem subItem : item.getSubItems()) {
				answeringTimeByMin += subItem.getAnsweringTimeByMin();
			}
			item.setAnsweringTimeByMin(answeringTimeByMin);
		}
	}
	/**
	 * 通过子题计算试题分数
	 * @param item
	 */
	public static void initItemScoreScore2(Item item){
		if(item!=null&&item.getSubItems()!=null&&item.getSubItems().size()>0){
			String score2 = "";
			int score_index = 0;
			Float score = 0f;
			for (SubItem subItem : item.getSubItems()) {
				if(++score_index>1){
					score2 += ",";
				}
				score2 += subItem.getScore();
				try{
					score += subItem.getScore();
				}catch(Exception e){}
			}
			item.setScore(score);
			item.setScore2(score2);
		}
	}
	public static void initItemScore(Item item){
		if(item!=null){
			Float itemScore =0f;
			List<SubItem> suItems= item.getSubItems();
			//如果有子题
			if(suItems!=null&&suItems.size()>0){
				for (SubItem subItem : suItems){
					Float subItem_score = 0f;
					if(subItem.getScore2()!=null&&!subItem.getScore2().trim().equals("")){
						String [] score2Arr = subItem.getScore2().split("；");
						if(score2Arr.length>0){
							for (int i = 0; i < score2Arr.length; i++){
								try {
									subItem_score += Float.valueOf(score2Arr[i].trim());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						subItem.setScore(subItem_score);
					}
				}
				for (SubItem subItem : suItems){
					try{
						itemScore += Float.valueOf(subItem.getScore());
					}catch(Exception e){
					}
				}
				item.setScore(itemScore);
			}			
		}
	} 
	
	public static void initItemSubItems(List<SubItem> subItemLstFromXwork,Item item,ParamObject p,HibernateEntityDao dao){	
		if(subItemLstFromXwork!= null&&subItemLstFromXwork.size()>0){
			List<SubItem> itemSubItems = item.getSubItems();
			if(itemSubItems==null){
				itemSubItems=new ArrayList<SubItem>();
				item.setSubItems(itemSubItems);
			}
			for (SubItem subItem : itemSubItems) {
				for (SubItem subItemFromXwork : subItemLstFromXwork){
					if(subItem.getId().equals(subItemFromXwork.getId())){  
						if(subItemFromXwork.getId()!=null){
							subItem=dao.get(SubItem.class,subItemFromXwork.getId());
							subItem.getKnowledgePoints().clear();
						}else
							subItem=new SubItem();
						
						subItem.setItem(item);
						
						if(subItemFromXwork.getAnswerGroup()!=null)
							subItem.setAnswerGroup(subItemFromXwork.getAnswerGroup());
						if(subItemFromXwork.getOrderNum()!=null)
							subItem.setOrderNum(subItemFromXwork.getOrderNum());
						if(subItemFromXwork.getOriginalSubItemNum()!=null)
							subItem.setOriginalSubItemNum(subItemFromXwork.getOriginalSubItemNum());
						if(subItemFromXwork.getContent()!=null)
							subItem.setContent(subItemFromXwork.getContent());
						if(subItemFromXwork.getContentTranslation()!=null)
							subItem.setContentTranslation(subItemFromXwork.getContentTranslation());
						if(subItemFromXwork.getCorrectAnswer()!=null)
							subItem.setCorrectAnswer(subItemFromXwork.getCorrectAnswer());
						if(subItemFromXwork.getAnswerAnalysis()!=null)
							subItem.setAnswerAnalysis(subItemFromXwork.getAnswerAnalysis());
						if(subItemFromXwork.getRelatedKeyPoints()!=null)
							subItem.setRelatedKeyPoints(subItemFromXwork.getRelatedKeyPoints());
						if(subItemFromXwork.getRelatedArticle()!=null)
							subItem.setRelatedArticle(subItemFromXwork.getRelatedArticle());
						if(subItemFromXwork.getKeyAnswerWords()!=null)
							subItem.setKeyAnswerWords(subItemFromXwork.getKeyAnswerWords());
						if(subItemFromXwork.getKeySentanceAnalysis()!=null)
							subItem.setKeySentanceAnalysis(subItemFromXwork.getKeySentanceAnalysis());
						if(subItemFromXwork.getKeySentances()!=null)
							subItem.setKeySentances(subItemFromXwork.getKeySentances());
						if(subItemFromXwork.getDifficultyValue()!=null)
							subItem.setDifficultyValue(subItemFromXwork.getDifficultyValue());
						if(subItemFromXwork.getKeyWords()!=null)
							subItem.setKeyWords(subItemFromXwork.getKeyWords());
						
						
						if(subItemFromXwork.getHint()!=null)
							subItem.setHint(subItemFromXwork.getHint());
						
						if(subItemFromXwork.getSkills()!=null)
							subItem.setSkills(subItemFromXwork.getSkills());
						
						if(subItemFromXwork.getAnswerPrototype()!=null)
							subItem.setAnswerPrototype(subItemFromXwork.getAnswerPrototype());
						if(subItemFromXwork.getAnalysisAtLarge1()!=null)
							subItem.setAnalysisAtLarge1(subItemFromXwork.getAnalysisAtLarge1());
						if(subItemFromXwork.getAnalysisAtLarge2()!=null)
							subItem.setAnalysisAtLarge2(subItemFromXwork.getAnalysisAtLarge2());
						if(subItemFromXwork.getAnalysisAtLarge3()!=null)
							subItem.setAnalysisAtLarge3(subItemFromXwork.getAnalysisAtLarge3());
						
						//计算子题分值
						if(subItemFromXwork.getScore2()!=null&&!"".equals(subItemFromXwork.getScore2().trim())){
							subItem.setScore2(subItemFromXwork.getScore2());
							String scoreArr[] = subItemFromXwork.getScore2().split(",|;|；");
							Float score = 0f;
							for (int i = 0; i < scoreArr.length; i++) {
								String string = scoreArr[i];
								try{
									score += Float.valueOf(string);
								}catch(Exception e){
								}
							}
							subItem.setScore(score);
						}else{
							subItem.setScore(subItemFromXwork.getScore());
						}
							
						if(subItemFromXwork.getAnsweringTime()!=null)
							subItem.setAnsweringTime(subItemFromXwork.getAnsweringTime());
						if(subItemFromXwork.getAnsweringTimeByMin() !=null)
							subItem.setAnsweringTimeByMin(subItemFromXwork.getAnsweringTimeByMin());
											
						//设置子题主知识点
						String kpCode  =p.get(SubItem_KnowledgePointCodes+subItemFromXwork.getId());
						if(kpCode!=null&&!"".equals(kpCode.trim())) {
							String[] codes=kpCode.trim().split(",");
							Set<KnowledgePoint> subItemKnowledgePoints = subItem.getKnowledgePoints();
							if(subItemKnowledgePoints==null){
								subItemKnowledgePoints=new HashSet<KnowledgePoint>();
								subItem.setKnowledgePoints(subItemKnowledgePoints);
							}
							for(int i=0;i<codes.length;i++){
								if(!"".equals(codes[i].trim())){
									subItemKnowledgePoints.add(dao.get(KnowledgePoint.class,codes[i]));
								}
							}
						}
						//设置子题次知识点
						String kpCode2  =p.get(SubItem_KnowledgePointCodes2+subItemFromXwork.getId());
						if(kpCode2!=null&&!"".equals(kpCode2.trim())) {
							String[] codes=kpCode2.trim().split(",");
							Set<KnowledgePoint> subItemKnowledgePoints2 = subItem.getKnowledgePoints2();
							if(subItemKnowledgePoints2==null){
								subItemKnowledgePoints2=new HashSet<KnowledgePoint>();
								subItem.setKnowledgePoints(subItemKnowledgePoints2);
							}
							for(int i=0;i<codes.length;i++){
								if(!"".equals(codes[i].trim())){
									subItemKnowledgePoints2.add(dao.get(KnowledgePoint.class,codes[i]));
								}
							}
						}
						
						String[] correctAnswerArr = p.getStrArr(SubItem_CorrectAnswers+subItemFromXwork.getId());
						String correctAnswers="";
						if (correctAnswerArr != null&& correctAnswerArr.length>0) {
							
							for (int i = 0; i < correctAnswerArr.length; i++) {
								if(i>0){
									correctAnswers+="；";
								}
								if( null != correctAnswerArr[i] && !"".equals(correctAnswerArr[i])){
									correctAnswers+=(MathUtil.removeSpaceAndChinaSpace(correctAnswerArr[i]));
								}
							}
							subItem.setCorrectAnswer(correctAnswers) ;
						}
						
					};
				}
			}	
			 
		}
	}
}
