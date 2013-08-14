package com.ambow.trainingengine.exam.web.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.util.AnswerInstruction;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.util.SplitCompareable;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ViewControlForMPC.java
 * 
 * Created on 2009-1-8 下午04:23:34
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

public class ViewControlForMPC implements IViewControl {
	
	protected static final Log logger = LogFactory.getLog(ViewControlForMPC.class);
	
	private ViewControl viewControl;
	
	public ViewControlForMPC() {
	}

	public ViewControlForMPC(ViewControl viewControl) {
		this.viewControl = viewControl;
	}
	
	public void generateMap() {
	}

	public void generateMapForView() {
	}

	public void initPages() {
		int num = 1;
		int pageNum = 0;
		int disableItemNum = 0;
		float answeringTime = 0f;// 考试时长
		float examValue = 0f;// 考试分数
		
		for (Page page: viewControl.getPageList()) {
			page.setPageNum(pageNum);
			pageNum++;
						
			page.setHasSubItem(false);
			for (Item item : page.getItems()) {
				String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
				
				int subnum = 1;
				for(SubItem subItem: item.getSubItems()) {
					subItem.setExamProperty(new ExamProperty(typeCode, subItem.getCorrectAnswer(), 
							subItem.getScore2(), subItem.getScore(), subItem.getAnswerGroup()));
					subItem.setItemNum(subnum);
					subnum++;
				}
				
				examValue += item.getScore();
				answeringTime += item.getAnsweringTime();
				if (item.getReadingTime() != null)
					answeringTime += item.getReadingTime();
				item.setItemNum(num);
				
				if (typeCode.startsWith("MPC1")) {
					item.setExamProperty(new ExamProperty());					
				} else if (typeCode.equals("MPC3X")) {
					item.setExamProperty(new ExamProperty(typeCode, item.getCorrectAnswer(), 
							item.getScore2(), item.getScore(), item.getAnswerGroup()));
				} else {
					item.setExamProperty(new ExamProperty());
				}	
				num++;
			}
		}

		viewControl.setExamValue(examValue);			
		viewControl.setExamTime(Float.valueOf(answeringTime).intValue());
		if (viewControl.getStandardAnsweringTime()>0) viewControl.setExamTime(viewControl.getStandardAnsweringTime());
		if (!viewControl.isPause) viewControl.setActualTime(viewControl.getExamTime());
		viewControl.setExamItemNum(num - 1 - disableItemNum);
		viewControl.setDisableItemNum(disableItemNum);	// 统计到的未出题的个数
	}
	
	public List<Page> itemPaging(List<Item> items) {
		return null;
	}

	public void parseAnswer(String userAnswer, Page page) {
		parseAnswer(userAnswer, page, viewControl.getAnswerMap(), viewControl.getMarkMap());
	}

	public void setPages() {
		for (Page page: viewControl.getPageList())
			setPage(page);		
	}		
	
	private void setPage(Page page) {
		setPage(page, viewControl.getAnswerMap(),
				viewControl.getMarkMap(),  viewControl.getStarMap(),
				viewControl.getRightMap(), viewControl.getDoneMap(), 
				viewControl.getScoreMap(), viewControl.getScore2Map(),
				viewControl.getAnswerOptionOrderMap());		
	}
	
	public void setFilterPages() {
		for (Page page: viewControl.getFilterPageList())
			setFilterPage(page);
	}
	
	private void setFilterPage(Page page) {
		int pageItemSize = 0;
		pageItemSize = page.getItems().size();
		page.setSize(pageItemSize);
		
		//页面title
		page.setTitle(genPageTitle( page.getItemType().getName()));
		//答题说明
		page.setInstruction(AnswerInstruction.getInstructions (
			ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode())));
	}
	
	public void statPages() {		
	}
	
	public List<Section> toSections(List<Page> pages) {
		return null;
	}

	/**
	 *  X = (重做训练通过率 * 节点所有题的总分 - 剩余题实际得分之和 ) / 按策略应出题的题分之和
	 *  若X<0 	则X=0
	 */
	public void convertRightRate() {
		float allScore  = 0f;
		float overScore = 0f;;
		float pageScore = 0f;
		float convertRate = 0f;
		
		List<Item> pageItem = new ArrayList<Item>(0);
		List<Item> allItem 	= new ArrayList<Item>(0);
		
		for (CurrentAnswersStatus currentAnswersStatus: viewControl.getPreAnswersStatus().values()) {
			if (currentAnswersStatus.getSubItem()==null) {
				allItem.add(currentAnswersStatus.getItem());
				allScore += currentAnswersStatus.getItem().getScore();							
			}
		}
		
		for (Page page: viewControl.getPageList()) {
			for (Item item: page.getItems()) {
				pageItem.add(item);
				pageScore += item.getScore();
				
				if (!allItem.contains(item)) {
					allItem.add(item);
					allScore += item.getScore();
				}						
			}
		}
		
		for (Item item: allItem) {
			if (!pageItem.contains(item)) 
				overScore += item.getScore();
		}
		
		convertRate = (viewControl.getRequireRightRate()*1.0f/100 * allScore - overScore) / pageScore;
		if (convertRate < 0f) convertRate = 0f;
		viewControl.setRequireRightRate(ExamUtil.roundInteger(convertRate*100));
	}
	
	/*
	 * mpc: 更新当页数据。。 并将数据放入答题的currentMap
	 * 答题数据并且放到Item.examProperty 中
	 */
	private void parseAnswer(String userAnswer, Page page,
			Map<String, String> answerMap, Map<String, Integer> markMap) {
		
		if (userAnswer.trim().length() == 0) return;
		String[] itemAnswers = userAnswer.split(ExamUtil.itemBreak);
	
		String answerStr;
		Integer markInt;
		for (String itemAnswer: itemAnswers) {
			if (itemAnswer.trim().length() == 0)
				continue;
			String[] itemSigns = itemAnswer.split(ExamUtil.multiSignBreak);			
			if(itemSigns.length!=3) logger.error("parseAnswer:	用户答题数据错误!" + itemAnswer);			
			String mapKey = itemSigns[0].trim();
			markInt = Integer.parseInt(itemSigns[1]);
			answerStr = itemSigns[2];
			answerMap.put(mapKey, answerStr);
			markMap.put(mapKey, markInt);
		}

		String pageType = ExamUtil.getResultCode(this.viewControl.projectVersion, page.getItemType().getCode());
		int pageDoneNum = 0;
		int pageMarkNum = 0;
		String mapKey;
		String answer;
		
		for (Item item : page.getItems()) {
			
			if (pageType.equals("MPC11")||pageType.equals("MPC12")||pageType.equals("MPC13")) {
				mapKey = ExamUtil.getMapKey(item, null);
				
				markInt = markMap.get(mapKey);
				if (markInt==null) markInt = 0;				
				item.getExamProperty().setIsMark(markInt==1);				
				
				answer = (String) answerMap.get(mapKey);			
				if (answer != null) {		
					if (answer.trim().length() > 0 ) {
						item.getExamProperty().setIsDone(true);
						item.getExamProperty().setUserAnswer(answer);							
					} else {
						item.getExamProperty().setIsDone(false);
					}
				}	
			}
			if (pageType.equals("MPC3X")) {
				mapKey = ExamUtil.getMapKey(item, null);
				
				markInt = markMap.get(mapKey);
				if (markInt==null) markInt = 0;
				item.getExamProperty().setIsMark(markInt==1);
				
				answer = (String) answerMap.get(mapKey);
				if (ExamUtil.isDoneAnswer(answer)) {
					parseAnswerFor3XItem(item, answer);	
					item.getExamProperty().setIsDone(true);	
				} else {
					item.getExamProperty().setUserAnswer("");
					item.getExamProperty().setIsDone(false);				
				}
								
			}
			if (pageType.equals("MPC4X")) {
				boolean itemMark = false;
				boolean itemDone = false;
				for (SubItem subItem : item.getSubItems()) {
					// 考虑未出题的处理，它的已作，未作不计入统计
					if (subItem.getEnable() == false) {						
						continue;
					}
					mapKey = ExamUtil.getMapKey(item, subItem);
					
					markInt = (Integer) markMap.get(mapKey);
					if (markInt==null) markInt = 0;
					if (markInt == 1) {
						subItem.getExamProperty().setIsMark(true);
						itemMark = true;
					} else {
						subItem.getExamProperty().setIsMark(false);
					}
					
					answer = (String) answerMap.get(mapKey);
					if (ExamUtil.isDoneAnswer(answer)) {
						parseAnswerFor4XSubItem(subItem, answer);	
						subItem.getExamProperty().setIsDone(true);
						itemDone = true;
					} else {
						subItem.getExamProperty().setUserAnswer("");
						subItem.getExamProperty().setIsDone(false);				
					}
				}
				if (itemMark) {
					item.getExamProperty().setIsMark(true);
					markMap.put(ExamUtil.getMapKey(item, null), 1);
				}
				if (itemDone) {
					item.getExamProperty().setIsDone(true);
				}
			}
			
			if (item.getExamProperty().getIsDone()) pageDoneNum++;
			if (item.getExamProperty().getIsMark()) pageMarkNum++;
		}
		
		page.setDoneNum(pageDoneNum);
		page.setMarkNum(pageMarkNum);

	}
	
	private void setPage(Page page, Map<String, String> answerMap,
			Map<String, Integer> markMap,  Map<String, Float> starMap,    
			Map<String, Boolean> rightMap, Map<String, Boolean> doneMap, 
			Map<String, Float> scoreMap,   Map<String, String> score2Map,
			Map<String, String> answerOptionOrderMap) {
		
		int pageItemSize = 0;
		pageItemSize = page.getItems().size();		
		page.setSize(pageItemSize);

		int doneCount = 0;
		int markCount = 0;
		int rightCount = 0;

		String mapKey = null;
		String answerStr;
		Integer markInt;
		Boolean rightFlag;
		Float star;
		Float score;
		String score2;
		String answerOptionOrder;
		
		String pageType = ExamUtil.getResultCode(this.viewControl.projectVersion, page.getItemType().getCode());
		
		for (Item item : page.getItems()) {
			mapKey = ExamUtil.getMapKey(item, null);
			
			answerStr = answerMap.get(mapKey);
			if (ExamUtil.isDoneAnswer(answerStr)) {
				if (pageType.equals("MPC11")||pageType.equals("MPC12")||pageType.equals("MPC13")) {
					item.getExamProperty().setUserAnswer(answerStr);
				} else if (pageType.equals("MPC3X")) {
					parseAnswerFor3XItem(item, answerStr);
				}
				item.getExamProperty().setIsDone(true);
				doneCount++;
			} else {
				item.getExamProperty().setUserAnswer("");
				item.getExamProperty().setIsDone(false);
			}
			
			markInt = markMap.get(mapKey);
			if (markInt != null && markInt == 1) {
				item.getExamProperty().setIsMark(true);			
				markCount++;
			} else {
				item.getExamProperty().setIsMark(false);
			}

			star = starMap.get(mapKey);
			item.getExamProperty().setStar(star);
		
			rightFlag = rightMap.get(mapKey);
			if (rightFlag != null && rightFlag == true) {
				item.getExamProperty().setIsRight(true);					
				rightCount++;
			} else {
				item.getExamProperty().setIsRight(false);
			}
			
			score = scoreMap.get(mapKey);
			if (score != null) {
				item.getExamProperty().setUserScore(score);				
			}
			
			if (viewControl.getShowModel()==1) {
				if (pageType.startsWith("MPC1")) {
					if (viewControl.isPause) {
						answerOptionOrder = answerOptionOrderMap.get(mapKey);
						parseAnswerOptionOrder(answerOptionOrder, item);
					} else {
						//viewControl.setRandomAnswerOptionsPolicy(1);	
						parseAnswerOptionOrder(item);
					}
				} else if (pageType.equals("MPC3X")) {
					adjustAnalysisForFill(item);
				}
			}
			
			if (viewControl.getShowModel()==3) {
				if (pageType.startsWith("MPC1")) {
					answerOptionOrder = answerOptionOrderMap.get(mapKey);
					parseAnswerOptionOrder(answerOptionOrder, item);
				} else if (pageType.equals("MPC3X")) {
					score2 = score2Map.get(mapKey);
					if (score2 != null) parseRightFor3XItem(item, score2);
					adjustAnalysisForFill(item);
				}
			}
			
			if (ExamUtil.hasSubItem(item)) {
				//一对多题型重新计算试题的对错和疑问标记
				boolean itemDone = false;
				boolean itemRight= true;
				boolean itemMark = false;
								
				for (SubItem subItem : item.getSubItems()) {
					if (subItem.getEnable() == false) {	
						continue;
					}
	
					mapKey = ExamUtil.getMapKey(item, subItem);
					
					answerStr = answerMap.get(mapKey);
					if (ExamUtil.isDoneAnswer(answerStr)) {
						parseAnswerFor4XSubItem(subItem, answerStr);						
						subItem.getExamProperty().setIsDone(true);
						itemDone = true;
					} else {
						subItem.getExamProperty().setUserAnswer("");
						subItem.getExamProperty().setIsDone(false);						
					}
					
					markInt = markMap.get(mapKey);
					if (markInt != null && markInt == 1) {
						subItem.getExamProperty().setIsMark(true);
						itemMark = true;
					} else {
						subItem.getExamProperty().setIsMark(false);
					}
	
					star = starMap.get(mapKey);
					subItem.getExamProperty().setStar(star);
	
					rightFlag = rightMap.get(mapKey);
					if (rightFlag != null && rightFlag == true) {
						subItem.getExamProperty().setIsRight(true);					
					} else {
						subItem.getExamProperty().setIsRight(false);
						itemRight = false;
					}
	
					score = scoreMap.get(mapKey);
					if (score != null) {
						subItem.getExamProperty().setUserScore(score);
					}
					
					if (viewControl.getShowModel()==3) {
						score2 = score2Map.get(mapKey);
						if (score2 != null) {
							parseRightFor4XSubItem(subItem, score2);									
						}
					}
					adjustAnalysisForFill(subItem);
				}
				
				item.getExamProperty().setIsDone(itemDone);
				item.getExamProperty().setIsRight(itemRight);
				item.getExamProperty().setIsMark(itemMark);							
			}
			
		}

		page.setDoneNum(doneCount);
		page.setMarkNum(markCount);
		page.setRightNum(rightCount);
		
		//页面title
		page.setTitle(genPageTitle( page.getItemType().getName()));
		//答题说明
		page.setInstruction(AnswerInstruction.getInstructions (
			ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode())));
	}

	private void parseAnswerOptionOrder(Item item) {
		if (viewControl.getRandomAnswerOptionsPolicy()==1) {		
			String randomCodes = ExamUtil.randomAnswerOptionCode();
			item.getExamProperty().setAnswerOptionOrder(randomCodes);
			item.randomAnswerOption(randomCodes);
			adjustOptionCode(item);
		} else {
			item.getExamProperty().setCorrectAnswer(item.getCorrectAnswer());
			adjustAnalysis(item);
		}
	}

	private void parseAnswerOptionOrder(String answerOptionOrder, Item item) {
		if (answerOptionOrder != null && answerOptionOrder.length()>=2) {					
			item.getExamProperty().setAnswerOptionOrder(answerOptionOrder);
			item.randomAnswerOption(answerOptionOrder);
			adjustOptionCode(item);
		} else {
			item.getExamProperty().setCorrectAnswer(item.getCorrectAnswer());
			adjustAnalysis(item);
		}
	}
	
	private void parseAnswerFor3XItem(Item item, String userAnswer) {
		String[] answers = userAnswer.trim().split(ExamUtil.multiAnswerBreak);
		int i = 0;
		for (String answer: answers) {
			if (item.getExamProperty().getSubAnswers().size()>i) {
				SubAnswer subAnswer = item.getExamProperty().getSubAnswers().get(i); 						
				subAnswer.setUserAnswer(answer.trim());
			} else {
				logger.info(item.getCode() + ":" + "-> 正确答案与题干中的空不符合." );
			}
			i++;
		}
	}
	
	private void parseAnswerFor4XSubItem(SubItem subItem, String userAnswer) {
		String[] answers = userAnswer.trim().split(ExamUtil.multiAnswerBreak);
		int i = 0;
		for (String answer: answers) {
			if (subItem.getExamProperty().getSubAnswers().size()>i) {
				SubAnswer subAnswer = subItem.getExamProperty().getSubAnswers().get(i); 						
				subAnswer.setUserAnswer(answer.trim());
			} else {
				logger.info(subItem.getItem().getCode() + ":" + subItem.getOrderNum().toString() + "-> 正确答案与题干中的空不符合." );
			}
			i++;
		}
	}
	
	private void parseRightFor3XItem(Item item, String examScore2) {		
		String[] score2s = examScore2.trim().split("；");
		int i = 0;
		for (String score2: score2s) {
			if (item.getExamProperty().getSubAnswers().size()>i) {
				SubAnswer subAnswer = item.getExamProperty().getSubAnswers().get(i);
				if (score2.trim().equals("0"))
					subAnswer.setIsRight(false);
				else
					subAnswer.setIsRight(true);
			} else {
				logger.info(item.getCode() + ":" + "-> 正确答案与题干中的空不符合." );
			}
			i++;
		}
	}
	
	private void parseRightFor4XSubItem(SubItem subItem, String examScore2) {
		String[] score2s = examScore2.trim().split("；");
		int i = 0;
		for (String score2: score2s) {
			if (subItem.getExamProperty().getSubAnswers().size()>i) {
				SubAnswer subAnswer = subItem.getExamProperty().getSubAnswers().get(i);
				if (score2.trim().equals("0"))
					subAnswer.setIsRight(false);
				else
					subAnswer.setIsRight(true);
			} else {
				logger.info(subItem.getItem().getCode() + ":" + subItem.getOrderNum().toString() + "-> 正确答案与题干中的空不符合." );
			}
			i++;
		}
	}
	
	/**
	 * 颠倒选项顺序后调整正确答案 详解 的显示。
	 * 提示不处理
	 * @param item
	 */
	private void adjustOptionCode(Item item) {		
		String regOptionCode;
		String repOptionCode;
		
		String standAnswer  = item.getCorrectAnswer()==null?"":item.getCorrectAnswer().replaceAll("[^a-zA-Z]", "");
		String adjustAnswer = "";
		
		/*String hint =  item.getHint();
		hint = hint.replace("｛", "{").replace("｝", "}");
		hint = hint.replaceAll("([^\\{])([A-D])([^\\}]?)", "$1{$2}$3");*/
		
		String a1 = item.getAnalysisAtLarge1();
		if (a1 == null) a1 = "";
		a1 = a1.replace("｛", "{").replace("｝", "}");
		a1 = a1.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$1$3$5");
		
		String a2 = item.getAnalysisAtLarge2();
		if (a2 == null) a2 = "";
		a2 = a2.replace("｛", "{").replace("｝", "}");
		a2 = a2.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$1$3$5");
		
		String a3 = item.getAnalysisAtLarge3();
		if (a3 == null) a3 = "";
		a3 = a3.replace("｛", "{").replace("｝", "}");
		a3 = a3.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$1$3$5");
		
		for (AnswerOption answerOption: item.getAnswerOptions()) {
			regOptionCode = "{" + answerOption.getCode() + "}";
			repOptionCode = answerOption.getCode();			
			
			if (answerOption.getPseudoCode()!=null) {
				if (standAnswer.contains(answerOption.getCode())) {
					adjustAnswer += answerOption.getPseudoCode()+ ";" ;	
				}
				repOptionCode = answerOption.getPseudoCode();
			}

			if (item.getAnalysisAtLarge1()!=null) {
				a1 = a1.replace(regOptionCode, repOptionCode);	
			}
			if (item.getAnalysisAtLarge2()!=null) {
				a2 = a2.replace(regOptionCode, repOptionCode);				
			}
			if (item.getAnalysisAtLarge3()!=null) {
				a3 = a3.replace(regOptionCode, repOptionCode);				
			}
		}
		item.getExamProperty().setAnalysisAtLarge(a1+a2+a3);		
		item.getExamProperty().setCorrectAnswer(adjustAnswer.replaceFirst(";$", ""));		
	}
	
	/**
	 * 调整解析中带{}标注的选项Code
	 * @param item
	 */
	private void adjustAnalysis(Item item) {
		String a1 = item.getAnalysisAtLarge1();
		if (a1 == null) a1 = "";
		a1 = a1.replace("｛", "{").replace("｝", "}");
		a1 = a1.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$3");
		
		String a2 = item.getAnalysisAtLarge2();
		if (a2 == null) a2 = "";
		a2 = a2.replace("｛", "{").replace("｝", "}");
		a2 = a2.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$3");
		
		String a3 = item.getAnalysisAtLarge3();
		if (a3 == null) a3 = "";
		a3 = a3.replace("｛", "{").replace("｝", "}");
		a3 = a3.replaceAll("(\\{)(<[^>]+>)*([A-D])(<[^>]+>)*(\\})", "$3");
				
		item.getExamProperty().setAnalysisAtLarge(a1+a2+a3);
	}
	
	private void adjustAnalysisForFill(Item item) {
		String a1 = item.getAnalysisAtLarge1();
		if (a1 == null) a1 = "";
	
		String a2 = item.getAnalysisAtLarge2();
		if (a2 == null) a2 = "";
		
		String a3 = item.getAnalysisAtLarge3();
		if (a3 == null) a3 = "";
		
		item.getExamProperty().setAnalysisAtLarge(a1+a2+a3);
	}
	
	private void adjustAnalysisForFill(SubItem subItem) {
		String a1 = subItem.getAnalysisAtLarge1();
		if (a1 == null) a1 = "";
	
		String a2 = subItem.getAnalysisAtLarge2();
		if (a2 == null) a2 = "";
		
		String a3 = subItem.getAnalysisAtLarge3();
		if (a3 == null) a3 = "";
		
		subItem.getExamProperty().setAnalysisAtLarge(a1+a2+a3);
	}
	
	private String genPageTitle(String typeName) {
		String r = typeName.replace("一对一", "").replace("一对多", "");
		return r;
	}

	/**
	 * 生成Widget
	 * @param item
	 * @param hisAnswerStatus
	 * @param mode
	 */
	public void generateWidget(Item item, List<HistoryAnswerStatus> hisAnswerStatusList, int mode) {
		initItem(item);
		
		if (mode<3) {
			if ((hisAnswerStatusList==null)||(hisAnswerStatusList.size()==0)) setItem(item);
			else setItem(item, hisAnswerStatusList);
		} else if (mode==3) {
			if ((hisAnswerStatusList==null)||(hisAnswerStatusList.size()==0)) setSimpleItem(item);
			else setSimpleItem(item, hisAnswerStatusList);
		} else if (mode==4) {
			setSimpleItem(item);
			return;
		}
		
		Page page = new Page();
		page.setItems(new ArrayList<Item>(1));
		page.getItems().add(item);
		
		viewControl.setPageList(new ArrayList<Page>(1));
		viewControl.getPageList().add(page);
	}
	
	/**
	 * 生成Widget, 多题
	 * @param item
	 * @param mode
	 */
	public void generateWidget2(List<Item> items, int mode) {
		for(Item item: items) {
			initItem(item);
			setItem(item);
		}
		
		ExamUtil.sortList(items);
		viewControl.setPageList(splitToPageByItemType(items));
		
		int itemNum = 1;
		for(Page page: viewControl.getPageList()) {
			page.setTitle(genPageTitle( page.getItemType().getName()));
			page.setInstruction(AnswerInstruction.getInstructions (
				ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode())));
			page.setTypeAlias(ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode()));
			
			for (Item item: page.getItems()) {
				item.setItemNum(itemNum);
				itemNum++;
			}
		}
	}
	
	/**
	 * 初始化试题
	 * @param item
	 */
	private void initItem(Item item) {
		if (item.getExamProperty()!=null) return;
		
		String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
		
		int subnum = 1;
		for(SubItem subItem: item.getSubItems()) {
			subItem.setExamProperty(new ExamProperty(typeCode, subItem.getCorrectAnswer(), 
					subItem.getScore2(), subItem.getScore(), subItem.getAnswerGroup()));
			subItem.setItemNum(subnum);
			subnum++;
		}
		
		if (typeCode.startsWith("MPC1")) {
			item.setExamProperty(new ExamProperty());					
		} else if (typeCode.equals("MPC3X")) {
			item.setExamProperty(new ExamProperty(typeCode, item.getCorrectAnswer(), 
					item.getScore2(), item.getScore(), item.getAnswerGroup()));
		} else {
			item.setExamProperty(new ExamProperty());
		}
	}
	
	/**
	 * 设置试题信息 有答题信息
	 * @param item
	 */
	private void setItem(Item item, List<HistoryAnswerStatus> hisAnswerStatusList) {
		String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
		
		HistoryAnswerStatus hisAnswerStatus = getItemAnswerStatus(hisAnswerStatusList);
		String answerStr = hisAnswerStatus.getAnswer();
		if (ExamUtil.isDoneAnswer(answerStr)) {
			if (typeCode.equals("MPC11")||typeCode.equals("MPC12")||typeCode.equals("MPC13")) {
				item.getExamProperty().setUserAnswer(answerStr);
			} else if (typeCode.equals("MPC3X")) {
				parseAnswerFor3XItem(item, answerStr);
			}
			item.getExamProperty().setIsDone(true);
		} else {
			item.getExamProperty().setUserAnswer("");
			item.getExamProperty().setIsDone(false);
		}
	
		item.getExamProperty().setIsMark(hisAnswerStatus.getIsUnsureMarking());			
	
		item.getExamProperty().setStar(hisAnswerStatus.getStarGrade());
	
		item.getExamProperty().setIsRight(hisAnswerStatus.getIsCorrect());					
		
		item.getExamProperty().setUserScore(hisAnswerStatus.getScore());				
		
		if (viewControl.getShowModel()==1) {
			if (typeCode.startsWith("MPC1")) {
				if (viewControl.isPause) {
					//answerOptionOrder = answerOptionOrderMap.get(mapKey);
					//parseAnswerOptionOrder(answerOptionOrder, item);
				} else {
					parseAnswerOptionOrder(item);
				}
			} else if (typeCode.equals("MPC3X")) {
				adjustAnalysisForFill(item);
			}
		}
		
		if (viewControl.getShowModel()>=3) {
			if (typeCode.startsWith("MPC1")) {
				String answerOptionOrder = hisAnswerStatus.getAnswerOptionOrder();
				parseAnswerOptionOrder(answerOptionOrder, item);
			} else if (typeCode.equals("MPC3X")) {
				String score2 = hisAnswerStatus.getItemScore2();
				if (score2 != null) parseRightFor3XItem(item, score2);
				adjustAnalysisForFill(item);
			}
		}
		
		if (ExamUtil.hasSubItem(item)) {
			//一对多题型重新计算试题的对错和疑问标记
			boolean itemDone = false;
			boolean itemRight= true;
			boolean itemMark = false;
							
			for (SubItem subItem : item.getSubItems()) {
				if (subItem.getEnable() == false) {	
					continue;
				}
				hisAnswerStatus = getSubItemAnswerStatus(hisAnswerStatusList, subItem.getId());
				answerStr = hisAnswerStatus.getAnswer();
				if (ExamUtil.isDoneAnswer(answerStr)) {
					parseAnswerFor4XSubItem(subItem, answerStr);						
					subItem.getExamProperty().setIsDone(true);
					itemDone = true;
				} else {
					subItem.getExamProperty().setUserAnswer("");
					subItem.getExamProperty().setIsDone(false);						
				}
				
				subItem.getExamProperty().setIsMark(hisAnswerStatus.getIsUnsureMarking());			
				
				subItem.getExamProperty().setStar(hisAnswerStatus.getStarGrade());
			
				subItem.getExamProperty().setIsRight(hisAnswerStatus.getIsCorrect());					
		
				subItem.getExamProperty().setUserScore(hisAnswerStatus.getScore());
				
				if (viewControl.getShowModel()>=3) {
					String score2 = hisAnswerStatus.getItemScore2();
					if (score2 != null) {
						parseRightFor4XSubItem(subItem, score2);									
					}
				}
				adjustAnalysisForFill(subItem);
			}
			
			item.getExamProperty().setIsDone(itemDone);
			item.getExamProperty().setIsRight(itemRight);
			item.getExamProperty().setIsMark(itemMark);
		}
	}
	
	/**
	 * 设置试题信息 无答题信息
	 * @param item
	 */
	private void setItem(Item item) {
		String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
		
		if (viewControl.getShowModel()>=3) {
			item.getExamProperty().setCorrectAnswer(item.getCorrectAnswer());
			if (typeCode.startsWith("MPC1")) {
				adjustAnalysis(item);
			} else if (typeCode.equals("MPC3X")) {
				adjustAnalysisForFill(item);
			} else if (typeCode.equals("MPC4X")) {
				for (SubItem subItem : item.getSubItems()) {
					subItem.getExamProperty().setCorrectAnswer(subItem.getCorrectAnswer());
					adjustAnalysisForFill(subItem);
				}
			}
		}
	}
	
	/**
	 * 简单设置试题信息 无答题信息
	 * @param item
	 */
	private void setSimpleItem(Item item) {
		String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
		
		item.getExamProperty().setCorrectAnswer(item.getCorrectAnswer());
		adjustAnalysisForFill(item);
		if (typeCode.equals("MPC4X")) {
			for (SubItem subItem : item.getSubItems()) {
				subItem.getExamProperty().setCorrectAnswer(subItem.getCorrectAnswer());
				adjustAnalysisForFill(subItem);
			}
		}
	}
	
	/**
	 * 简单设置试题信息 有答题信息
	 * @param item
	 */
	private void setSimpleItem(Item item, List<HistoryAnswerStatus> hisAnswerStatusList) {
		String typeCode = ExamUtil.getResultCode(viewControl.projectVersion, item.getItemType().getCode());
		
		HistoryAnswerStatus hisAnswerStatus = getItemAnswerStatus(hisAnswerStatusList);
		String answerStr = hisAnswerStatus.getAnswer();
		if (ExamUtil.isDoneAnswer(answerStr)) {
			if (typeCode.equals("MPC11")||typeCode.equals("MPC12")||typeCode.equals("MPC13")) {
				item.getExamProperty().setUserAnswer(answerStr);
			} else if (typeCode.equals("MPC3X")) {
				parseAnswerFor3XItem(item, answerStr);
			}
			item.getExamProperty().setIsDone(true);
		} else {
			item.getExamProperty().setUserAnswer("");
			item.getExamProperty().setIsDone(false);
		}
	
		item.getExamProperty().setIsRight(hisAnswerStatus.getIsCorrect());					
		
		item.getExamProperty().setUserScore(hisAnswerStatus.getScore());				
		
		if (viewControl.getShowModel()==1) {
			if (typeCode.startsWith("MPC1")) {
				adjustAnalysisForFill(item);
			} else if (typeCode.equals("MPC3X")) {
				adjustAnalysisForFill(item);
			}
		}
		
		if (viewControl.getShowModel()>=3) {
			if (typeCode.startsWith("MPC1")) {
				//String answerOptionOrder = hisAnswerStatus.getAnswerOptionOrder();
				//parseAnswerOptionOrder(answerOptionOrder, item);
				item.getExamProperty().setCorrectAnswer(item.getCorrectAnswer());
				adjustAnalysisForFill(item);
			} else if (typeCode.equals("MPC3X")) {
				String score2 = hisAnswerStatus.getItemScore2();
				if (score2 != null) parseRightFor3XItem(item, score2);
				adjustAnalysisForFill(item);
			}
		}
		
		if (ExamUtil.hasSubItem(item)) {
			//一对多题型重新计算试题的对错和疑问标记
			boolean itemDone = false;
			boolean itemRight= true;
							
			for (SubItem subItem : item.getSubItems()) {
				if (subItem.getEnable() == false) {	
					continue;
				}
				hisAnswerStatus = getSubItemAnswerStatus(hisAnswerStatusList, subItem.getId());
				answerStr = hisAnswerStatus.getAnswer();
				if (ExamUtil.isDoneAnswer(answerStr)) {
					parseAnswerFor4XSubItem(subItem, answerStr);						
					subItem.getExamProperty().setIsDone(true);
					itemDone = true;
				} else {
					subItem.getExamProperty().setUserAnswer("");
					subItem.getExamProperty().setIsDone(false);						
				}
				
				subItem.getExamProperty().setIsRight(hisAnswerStatus.getIsCorrect());					
				subItem.getExamProperty().setUserScore(hisAnswerStatus.getScore());
				
				if (viewControl.getShowModel()>=3) {
					String score2 = hisAnswerStatus.getItemScore2();
					if (score2 != null) {
						parseRightFor4XSubItem(subItem, score2);									
					}
				}
				adjustAnalysisForFill(subItem);
			}
			
			item.getExamProperty().setIsDone(itemDone);
			item.getExamProperty().setIsRight(itemRight);
		}
	}
	
	/**
	 * pre function:在调用该算法进行分页计算时,请注意该list必须是排序完毕的list
	 * 只按题型分页。
	 */
	private List<Page> splitToPageByItemType(List<Item> listObject) {
		List<Item> currentList = new ArrayList<Item>();
		List<Page> listPages = new ArrayList<Page>();
		for (Item compareObject : listObject) {
			if (currentList.size() < 1) {
				currentList.add(compareObject);
			} else {
				SplitCompareable preObject = currentList.get(currentList.size() - 1);
				if (preObject.splitCompareTo(compareObject)) {
					currentList.add(compareObject);
				} else {
					Page page = new Page();
					page.setItems(currentList);
					page.setItemType(currentList.get(0).getItemType());
					listPages.add(page);
					currentList = new ArrayList<Item>();
					currentList.add(compareObject);
				}
			}
		}
		if (currentList.size() > 0) {
			Page page = new Page();
			page.setItems(currentList);
			page.setItemType(currentList.get(0).getItemType());
			listPages.add(page);
		}
		
		return listPages;
	}
	
	private HistoryAnswerStatus getItemAnswerStatus(List<HistoryAnswerStatus> hisAnswerStatusList) {
		HistoryAnswerStatus historyAnswerStatus = null;
		for (HistoryAnswerStatus his: hisAnswerStatusList) {
			if (his.getSubItem()==null) {
				historyAnswerStatus = his;
				break;
			}
		}
		return historyAnswerStatus;
	}
	
	private HistoryAnswerStatus getSubItemAnswerStatus(List<HistoryAnswerStatus> hisAnswerStatusList, Integer subItemId) {
		HistoryAnswerStatus historyAnswerStatus = null;
		for (HistoryAnswerStatus his: hisAnswerStatusList) {
			if ((his.getSubItem()!=null)&&(his.getSubItem().getId()==subItemId)) {
				historyAnswerStatus = his;
				break;
			}
		}
		return historyAnswerStatus;
	}
}
