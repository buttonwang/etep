package com.ambow.trainingengine.exam.web.data;

import java.util.List;
import java.util.Map;

import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.util.AnswerInstruction;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ViewControlForKY.java
 * 
 * Created on 2009-1-8 下午04:22:37
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 考研项目的 IViewControl 实现
 * Changes 
 * -------
 * $log$
 */

public class ViewControlForKY implements IViewControl {

	private ViewControl viewControl;

	
	public ViewControlForKY() {
	}

	public ViewControlForKY(ViewControl viewControl) {
		this.viewControl = viewControl;
	}

	public void generateMap() {
	}
	
	public void generateMapForView() {
	}
		
	public void initPages() {
		// TODO Auto-generated method stub
		int num = 1;
		int pageNum = 0;
		int disableItemNum = 0;
		float answeringTime = 0f;// 考试时长
		float examValue = 0f;// 考试分数
		for (Page page : viewControl.getPageList()) {
			page.setPageNum(pageNum);
			pageNum++;
			// int i=0;
			int pageItemSize = 0;
			if (page.getItems().get(0).getSubItems().size() > 0) {

				for (int ii = 0; ii < page.getItems().size(); ii++) {
					pageItemSize = pageItemSize
							+ page.getItems().get(ii).getSubItems().size();
				}
				page.setHasSubItem(true);
				for (Item item : page.getItems()) {
					float avTime = (float) item.getAnsweringTime()
							/ (float) item.getSubItems().size();
					float avValue = (float) item.getScore()
							/ (float) item.getSubItems().size();
					for (SubItem subItem : item.getSubItems()) {
						subItem.getKnowledgePoints().size();
						// TODO:if subItem is not Enable out...cotinue
						if (subItem.getEnable() == false) {
							subItem.setItemNum(num);
							num++;
							disableItemNum++;
							continue;
						}// 如果Enable为False...
						answeringTime = answeringTime + avTime;
						examValue = examValue + avValue;
						subItem.setItemNum(num);
						num++;
					}
					// 对于一对多题型来说。。在此加上读题时间。。2008-09-26修改
					if (item.getReadingTime() != null)
						answeringTime = answeringTime + item.getReadingTime();
				}
			} else {
				pageItemSize = page.getItems().size();
				page.setHasSubItem(false);
				for (Item item : page.getItems()) {
					answeringTime = answeringTime + item.getAnsweringTime();
					examValue = examValue + item.getScore();
					item.setItemNum(num);
					item.getSubItems().size();// 初始化一下
					item.getKnowledgePoints().size();
					num++;
				}
			}
			page.setSize(pageItemSize);
			// 初始化数组为Enable与Unable
			Integer[] isMark = new Integer[pageItemSize];
			Integer[] isRight = new Integer[pageItemSize];
			Integer[] isDone = new Integer[pageItemSize];
			Integer[] starHalf = new Integer[pageItemSize];
			Integer[] starInt = new Integer[pageItemSize];
			String[] userAnswer = new String[pageItemSize];
			for (int i = 0; i < pageItemSize; i++) {
				isMark[i] = 0;
				isRight[i] = 0;
				isDone[i] = 0;
				starHalf[i] = 0;
				starInt[i] = 0;
				userAnswer[i] = "";
			}
			page.setIsDone(isDone);
			page.setIsMark(isMark);
			page.setIsRight(isRight);
			page.setStarHalf(starHalf);
			page.setStarInt(starInt);
			page.setUserAnswer(userAnswer);

		}

		viewControl.setExamValue(examValue);			
		viewControl.setExamTime(Float.valueOf(answeringTime).intValue());
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
	
	
	public void setFilterPages() {
		for (Page page: viewControl.getFilterPageList())
			setPage(page);
	}
	
	public void setPage(Page page) {
		setPage(page, viewControl.getAnswerMap(), viewControl.getMarkMap(), 
				viewControl.getStarMap(), viewControl.getRightMap());		
	}

	
	public void statPages() {
	}
	
	public List<Section> toSections(List<Page> pages) {
		return null;
	}
		
	public ViewControl getViewControl() {
		return viewControl;
	}

	public void setViewControl(ViewControl viewControl) {
		this.viewControl = viewControl;
	}
	
	public void convertRightRate() {
		// TODO Auto-generated method stub		
	}
	
	/*
	 * ky: 更新当页数据。。 并将数据放入答题的currentMap
	 */
	private void parseAnswer(String userAnswer, Page page,
			Map<String, String> answerMap, Map<String, Integer> markMap) {
		if (userAnswer.trim().length() == 0)
			return;
		String[] itemAnswers = userAnswer.split(ExamUtil.itemBreak);
		List<Item> items = page.getItems();
		Integer[] isDone = page.getIsDone();
		Integer[] isMark = page.getIsMark();
		String[] userAnswerArr = page.getUserAnswer();
		String answerStr;

		for (String itemAnswer : itemAnswers) {
			if (itemAnswer.trim().length() == 0)
				continue;
			String[] itemSigns = itemAnswer.split(ExamUtil.multiSignBreak);
			// if(itemSigns.length!=3)log.error("错误地后台数据!");
			String mapKey = itemSigns[0].trim();
			Integer markInt = Integer.parseInt(itemSigns[1]);
			answerStr = ExamUtil.ToDBC(itemSigns[2]);// 确保全部为半角字符
			// if(answerStr.indexOf("\r\n")>-1){
			// answerStr=answerStr.replaceAll("\r\n", "\n");
			// }
			answerMap.put(mapKey, answerStr);
			markMap.put(mapKey, markInt);
			// currentAnswer.set
			// i++;
		}
		int i = 0;
		int pageDoneNum = 0;
		int pageMarkNum = 0;
		if (page.isHasSubItem()) {
			// has child item
			String mapKey = null;
			for (Item item : page.getItems()) {
				for (SubItem subItem : item.getSubItems()) {
					// 考虑未出题的处理，它的已作，未作不计入统计
					if (subItem.getEnable() == false) {
						isDone[i] = 0;
						isMark[i] = 0;
						i++;
						continue;
					}
					mapKey = ExamUtil.getMapKey(item, subItem);
					String answer = (String) answerMap.get(mapKey);
					if (answer != null) {
						if (answer.trim().length() > 0 && ! ExamUtil.isSubAnswer(answer)) {
							isDone[i] = 1;
							pageDoneNum = pageDoneNum + 1;
							userAnswerArr[i] = answer;
						} else {
							isDone[i] = 0;
						}
					}
					Integer markInt = (Integer) markMap.get(mapKey);
					isMark[i] = markInt;
					if (isMark[i] != null && isMark[i].intValue() == 1)
						pageMarkNum = pageMarkNum + 1;

					i++;
				}
			}
		}
		if (!page.isHasSubItem()) {
			String mapKey = null;
			for (Item item : page.getItems()) {
				mapKey = item.getId().toString();
				String answer = (String) answerMap.get(mapKey);
				if (answer != null) {
					if (answer.trim().length() > 0 && ! ExamUtil.isSubAnswer(answer)) {
						isDone[i] = 1;
						pageDoneNum = pageDoneNum + 1;
						userAnswerArr[i] = answer;
					} else {
						isDone[i] = 0;
					}
				}
				// String markStr=(String)markMap.get(mapKey);
				isMark[i] = markMap.get(mapKey);
				if (isMark[i].intValue() == 1)
					pageMarkNum = pageMarkNum + 1;
				i++;

			}
		}
		page.setDoneNum(pageDoneNum);
		page.setMarkNum(pageMarkNum);

	}
	
	private void setPage(Page page, Map<String, String> answerMap,
			Map<String, Integer> markMap, Map<String, Float> starMap,
			Map<String, Boolean> rightMap) {
		int i = 0;
		int pageItemSize = 0;
		if (page.getItems().get(0).getSubItems().size() > 0) {

			for (int ii = 0; ii < page.getItems().size(); ii++) {
				pageItemSize = pageItemSize
						+ page.getItems().get(ii).getSubItems().size();
			}
			page.setHasSubItem(true);
		} else {
			pageItemSize = page.getItems().size();
			page.setHasSubItem(false);
		}
		page.setSize(pageItemSize);
		Integer[] isDone = new Integer[pageItemSize];
		Integer[] isMark = new Integer[pageItemSize];
		Integer[] isRight = new Integer[pageItemSize];
		String[] userAnswer = new String[pageItemSize];
		Integer[] starInt = new Integer[pageItemSize];
		Integer[] starHalf = new Integer[pageItemSize];
		int doneCount = 0;
		int markCount = 0;
		int rightCount = 0;

		String mapKey = null;
		String answerStr;
		Integer markInt;
		Boolean rightFlag;
		Float star;
		if (page.isHasSubItem()) {
			for (Item item : page.getItems()) {
				for (SubItem subItem : item.getSubItems()) {
					if (subItem.getEnable() == false) {
						i = i + 1;
						continue;
					}
					//2008-12-12 wangwei 注释掉先观察
					//if (item.getItemType().getCode().equals("25A")) subItem.getKnowledgePoints().size();// 初始化知识点集合
					mapKey = ExamUtil.getMapKey(item, subItem);
					answerStr = answerMap.get(mapKey);
					if (answerStr != null && answerStr.trim().length() > 0) {
						userAnswer[i] = answerStr;
						isDone[i] = 1;
						doneCount = doneCount + 1;
					} else {
						userAnswer[i] = "";
						isDone[i] = 0;
					}

					star = starMap.get(mapKey);
					if (star != null) {
						int[] starArr = ExamUtil.getStarArray(star);
						starInt[i] = starArr[0];
						starHalf[i] = starArr[1];
					} else {
						starInt[i] = 0;
						starHalf[i] = 0;
					}

					rightFlag = rightMap.get(mapKey);
					if (rightFlag != null && rightFlag == true) {
						isRight[i] = 1;
						rightCount = rightCount + 1;
					} else {
						isRight[i] = 0;
					}

					markInt = markMap.get(mapKey);
					if (markInt != null && markInt == 1) {
						isMark[i] = 1;
						markCount = markCount + 1;
					} else {
						isMark[i] = 0;
					}

					i++;
				}

			}
		}
		if (!page.isHasSubItem()) {

			for (Item item : page.getItems()) {
				mapKey = ExamUtil.getMapKey(item, null);
				answerStr = answerMap.get(mapKey);
				if (answerStr != null && answerStr.trim().length() > 0) {
					userAnswer[i] = answerStr;
					isDone[i] = 1;
					doneCount = doneCount + 1;
				} else {
					userAnswer[i] = "";
					isDone[i] = 0;
				}

				star = starMap.get(mapKey);
				if (star != null) {
					int[] starArr = ExamUtil.getStarArray(star);
					starInt[i] = starArr[0];
					starHalf[i] = starArr[1];
				} else {
					starInt[i] = 0;
					starHalf[i] = 0;
				}

				rightFlag = rightMap.get(mapKey);
				if (rightFlag != null && rightFlag == true) {
					isRight[i] = 1;
					rightCount = rightCount + 1;
				} else {
					isRight[i] = 0;
				}

				markInt = markMap.get(mapKey);
				if (markInt != null && markInt == 1) {
					isMark[i] = 1;
					markCount = markCount + 1;
				} else {
					isMark[i] = 0;
				}

				i++;
			}

		}
		page.setIsDone(isDone);
		page.setIsMark(isMark);
		page.setIsRight(isRight);
		page.setStarInt(starInt);
		page.setStarHalf(starHalf);
		page.setUserAnswer(userAnswer);
		page.setDoneNum(doneCount);
		page.setMarkNum(markCount);
		page.setRightNum(rightCount);
		page.setInstruction(AnswerInstruction.getInstructions(	//答题说明
			ExamUtil.getResultCode(viewControl.getProjectVersion(), page.getItemType().getCode())));
		
	}

	@Override
	public void generateWidget(Item item, List<HistoryAnswerStatus> hisAnswerStatusList, int mode) {
		// TODO Auto-generated method stub
	}

	@Override
	public void generateWidget2(List<Item> items, int mode) {
		// TODO Auto-generated method stub
	}
}
