package com.ambow.trainingengine.exam.web.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.CurrentAnswersStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.util.ExamType;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.util.LayoutUtil;
import com.ambow.trainingengine.exam.util.SplitCompareable;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ViewControlProxy.java
 * 
 * Created on 2009-1-4 上午09:50:33
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

public class ViewControlProxy extends ViewControl implements IViewControl {
	
	private IViewControl viewControlInstance;
	
	/**
	 * 构造函数1 
	 * @param showAnswer	 是否显示答案
	 * @param projectVersion 项目版本号	ky, mpc
	 */
	public  ViewControlProxy(Integer showAnswer, String projectVersion,long processInstanceId){
		if(showAnswer==null)
			this.showAnswer=false;
		else
			this.showAnswer = showAnswer==1;
		//for test: this.showAnswer = 1==1;
		this.projectVersion = projectVersion;
		this.processInstanceId=processInstanceId;
		
		if (projectVersion.equals("ky"))  viewControlInstance = new ViewControlForKY(this);
		if (projectVersion.equals("mpc")) viewControlInstance = new ViewControlForMPC(this);
	}
	
	
	/**
	 * 根据流程和节点的数据，
	 * 生成流程名称、考试名称、 章节名称、考试类型等信息
	 *   
	 */
	public void initExamData() {
		setFlowName(processInstance.getProcessDefinition().getName());
		
		if (examNodeIns != null) {
			if (examNodeIns.getNode().getNodeType()==NodeType.GROUP) {
				setExamName("");
				setExamTask("");
				setExamType(ExamUtil.getTypeCHName(getProjectVersion(), examNodeIns.getNode().getNodeType()));
				setExamTypePara(ExamType.getExamTypeByNodeType(examNodeIns.getNode().getNodeType()));
				setSectionName(examNodeIns.getNode().getName());
				if (examNodeIns.getNode().getNodeGroup()==null)
					setChapterName(flowName);
				else
					setChapterName(examNodeIns.getNode().getNodeGroup().getName());				
			} else {
				setExamName(examNodeIns.getNode().getName());
				setExamTask(examNodeIns.getNode().getName());
				setExamType(ExamUtil.getTypeCHName(getProjectVersion(), examNodeIns.getNode().getNodeType()));
				setExamTypePara(ExamType.getExamTypeByNodeType(examNodeIns.getNode().getNodeType()));
				setSectionName(examNodeIns.getNode().getNodeGroup().getName());
				if (examNodeIns.getNode().getNodeGroup().getNodeGroup()==null)
					setChapterName(flowName);
				else 
					setChapterName(examNodeIns.getNode().getNodeGroup().getNodeGroup().getName());
			}
		}
		
		if (isWeaknessEnhance()) {
			if ((examName==null)||examName.equals("")) setExamName("弱项强化");
			setExamTask(ExamUtil.getScopeCHName(itemType) +"重练 ");
			setExamType(ExamUtil.getTypeCHName(getProjectVersion(), NodeType.GROUP));
			setExamTypePara(ExamType.weekEnhance);
		}
		if (isExtPractice()) {
			setExamName("拓展练习");
			setExamTask(ExamUtil.getScopeCHName(itemType) +"重练 ");
			setExamType(ExamUtil.getTypeCHName(getProjectVersion(), NodeType.EXTPRACTICE));
			setExamTypePara(ExamType.extPractice);
		}
	}
	
	
	/**
	 * 根据组卷的数据，初始化viewControl的展示数据
	 * 组合 generateMap, itemPaging, initPages(), setPages(), statPages(),
	 * 换算正确率
	 * 开始答题  
	 */
	public void initViewData(List<Item> examItems) {
		pageList = itemPaging(examItems);//分页	
		initPages();//初始化试题
		setPages(); //初始化page的各种数据		
		statPages();//计算各种统计数据
		toSections();//分段
		
		if (getRedoType()>0) convertRightRate();
		
		if (showModel==1) startTime = new Date();
		entranceExam = false;
	}
	
	/**
	 * 根据ShowModel判断所需的是哪种AnswersStatus 主要提供给Test过滤题 与逐题浏览使用
	 */
	public void generateMap() {
		Map<String, Float> starMap = new HashMap<String, Float>();
		Map<String, Boolean> rightMap = new HashMap<String, Boolean>();
		Map<String, Integer> markMap = new HashMap<String, Integer>();
		Map<String, Float> scoreMap = new HashMap<String, Float>();
		Map<String, Boolean> doneMap = new HashMap<String, Boolean>();

		Set<String> mapkeys = preAnswersStatus.keySet();
		Iterator<String> keyIterator = mapkeys.iterator();
		String mapKey = null;
		CurrentAnswersStatus answerStatus = null;
		
		while (keyIterator.hasNext()) {
			mapKey = keyIterator.next();
			answerStatus = preAnswersStatus.get(mapKey);
			if (answerStatus != null) {
				starMap.put(mapKey, answerStatus.getStarGrade());
				rightMap.put(mapKey, answerStatus.getIsCorrect());	
				doneMap.put(mapKey, answerStatus.isDone());
				markMap.put(mapKey, answerStatus.getIsUnsureMarking()?1:0);
				scoreMap.put(mapKey, answerStatus.getScore());								
			}
		}

		this.setStarMap(starMap);
		this.setRightMap(rightMap);
		this.setMarkMap(markMap);
		this.setScoreMap(scoreMap);		
		this.setDoneMap(doneMap);
	}

	public void generateMapForView() {
		Map<String, String> answerMap = new HashMap<String, String>();
		Map<String, Integer> markMap = new HashMap<String, Integer>();
		Map<String, Float> starMap = new HashMap<String, Float>();
		Map<String, Boolean> rightMap = new HashMap<String, Boolean>();		
		Map<String, Boolean> doneMap = new HashMap<String, Boolean>();		
		Map<String, Float> scoreMap = new HashMap<String, Float>();
		Map<String, String> score2Map = new HashMap<String, String>();
		Map<String,String> answerOptionOrderMap = new HashMap<String,String>();
		
		if (historyTestStatusId==0) {
			Set<String> mapkeys = currentAnswersStatus.keySet();
			Iterator<String> keyIterator = mapkeys.iterator();		
			String mapKey = null;
			CurrentAnswersStatus answerStatus = null;
			
			while (keyIterator.hasNext()) {
				mapKey = keyIterator.next();
				answerStatus = currentAnswersStatus.get(mapKey);
						
				if (answerStatus != null) {
					starMap.put(mapKey,   answerStatus.getStarGrade());
					answerMap.put(mapKey, answerStatus.getAnswer());					
					markMap.put(mapKey,   answerStatus.getIsUnsureMarking()?1:0);								
					rightMap.put(mapKey,  answerStatus.getIsCorrect());
					doneMap.put(mapKey,   answerStatus.isDone());
					scoreMap.put(mapKey,  answerStatus.getScore());
					score2Map.put(mapKey, answerStatus.getItemScore2());
					answerOptionOrderMap.put(mapKey, answerStatus.getAnswerOptionOrder());
				}
			}
		} else {
			Set<String> mapkeys = historyStatusMap.keySet();
			Iterator<String> keyIterator = mapkeys.iterator();		
			String mapKey = null;
			HistoryAnswerStatus answerStatus = null;
			
			while (keyIterator.hasNext()) {
				mapKey = keyIterator.next();
				answerStatus = historyStatusMap.get(mapKey);
						
				if (answerStatus != null) {
					starMap.put(mapKey,   answerStatus.getStarGrade());
					answerMap.put(mapKey, answerStatus.getAnswer());					
					markMap.put(mapKey,   answerStatus.getIsUnsureMarking()?1:0);								
					rightMap.put(mapKey,  answerStatus.getIsCorrect());
					doneMap.put(mapKey,   answerStatus.isDone());
					scoreMap.put(mapKey,  answerStatus.getScore());
					score2Map.put(mapKey, answerStatus.getItemScore2());
					answerOptionOrderMap.put(mapKey, answerStatus.getAnswerOptionOrder());
				}
			}
		}
		
		this.setStarMap(starMap);
		this.setAnswerMap(answerMap);
		this.setMarkMap(markMap);
		this.setRightMap(rightMap);
		this.setDoneMap(doneMap);
		this.setScoreMap(scoreMap);
		this.setScore2Map(score2Map);
		this.setAnswerOptionOrderMap(answerOptionOrderMap);
	}
	
	public void generateWidget(Item item, List<HistoryAnswerStatus> historyAnswerStatusList, int mode) {
		viewControlInstance.generateWidget(item, historyAnswerStatusList, mode);
	}

	public void generateWidget2(List<Item> items, int mode) {
		viewControlInstance.generateWidget2(items, mode);
	}
	
	public void initPages() {
		if (isPause==false) clearMarkDoneMap();
		
		viewControlInstance.initPages();
		cacuDiffValue();
	}

	public void parseAnswer(String userAnswer, Page page) {
		viewControlInstance.parseAnswer(userAnswer, page);
	}

	public void setPages() {
		viewControlInstance.setPages();
	}

	public void setFilterPages() {
		viewControlInstance.setFilterPages();
	}
	
	/*
	 * 总题数的计算方法--必须除去enable==false的题算全卷总数时的方法
	 */
	public void statPages() {
		int totalMarkNum = 0;
		int totalDoneNum = 0;
		int totalRightNum = 0;

		for (Page page : pageList) {
			// totalItemNum=totalItemNum+page.getSize();
			totalMarkNum = totalMarkNum + page.getMarkNum();
			totalDoneNum = totalDoneNum + page.getDoneNum();
			totalRightNum = totalRightNum + page.getRightNum();
		}
		setMarkItemNum(totalMarkNum);
		setDoneItemNum(totalDoneNum);		
		setUndoItemNum(getExamItemNum()-getDoneItemNum());
		setRightItemNum(totalRightNum);		
		setErrorItemNum(totalDoneNum - totalRightNum);		
	}
		
	public void convertRightRate() {
		viewControlInstance.convertRightRate();
	}
	
	/*
	 * 计算试卷平均难度
	 */
	private void cacuDiffValue() {
		int itemCount = 0;
		Float totalDiffValue = 0f;
		for (Page page : pageList) {
			for (Item item : page.getItems()) {
				itemCount = itemCount + 1;
				totalDiffValue = totalDiffValue + 
					(item.getDifficultyValue()==null?0f:item.getDifficultyValue());
			}
		}
		Float evalDiffValue = ExamUtil.roundFloat(totalDiffValue / itemCount);
		Float intValue = (float) evalDiffValue.intValue();
		this.difficultyValue = intValue;
	}
	
	public List<Page> itemPaging(List<Item> items) {
		ExamUtil.sortList(items);
		return splitToPage(items);
	}

	public List<Section> toSections(List<Page> pages) {
		List<Section> sections=splitToSection(pages);
		return sections;
	}
	
	public List<Section> toSections() {
		List<Section> sections=splitToSection(this.pageList);
		return sections;
	}
	
	public List<Item> toItems(){
		List<Item> items = this.combineToItem(pageList);
		return items;
	}
	
	public void cancelFilterPages() {
		setFilter(false);
		setFilterType(1);
		for (Page page : getFilterPageList()) {
			for (Item item : page.getItems()) {
				if ( ExamUtil.hasSubItem(item)) {
					for (SubItem subItem : item.getSubItems()) {
						subItem.setFilterShow(true);
					}
				}
				item.setFilterShow(true);
			}
		}
	}
		
	/**
	 * pre function:在调用该算法进行分页计算时,请注意该list必须是排序完毕的list
	 */
	private List<Page> splitToPage(List<Item> listObject) {
		List<Item> currentList = new ArrayList<Item>();
		List<Page> listPages = new ArrayList<Page>();
		for (Item compareObject : listObject) {
			if (currentList.size() < 1) {				
				currentList.add(compareObject);
			} else {
				SplitCompareable preObject = currentList.get(currentList.size() - 1);
				if (preObject.splitCompareTo(compareObject)
						&& LayoutUtil.getCountSize(compareObject.getItemType().getCode(),
								processTrainingStatus.getLayout1(),
								processTrainingStatus.getLayout2(),
								processTrainingStatus.getLayout3(),
								compareObject.getCountSize() ) > currentList.size()) {
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
	
	/**
	 * 将分页试题组合成大的列表
	 * @param pageList
	 * @return
	 */
	private List<Item> combineToItem(List<Page> pageList){
		List<Item> itemList = new ArrayList<Item>();
		for(Page page : pageList){
			List<Item> currentList = page.getItems();
			itemList.addAll(currentList);
		}
		return itemList;
	}
	
	
	private List<Section> splitToSection(List<Page> pages) {
		List<Page> currentList = new ArrayList<Page>();
		List<Section> listSections = new ArrayList<Section>();
		for (Page compareObject : pages) {
			if (currentList.size() < 1) {
				currentList.add(compareObject);
			} else {
				SplitCompareable preObject = currentList.get(currentList.size() - 1);
				
				if (preObject.splitCompareTo(compareObject)) {
					currentList.add(compareObject);
				} else {
					Section section = new Section();
					section.setPages(currentList);
					listSections.add(section);
					currentList = new ArrayList<Page>();
					currentList.add(compareObject);
				}
			}
		}
		if (currentList.size() > 0) {
			Section section = new Section();
			section.setPages(currentList);			
			listSections.add(section);
		}
		this.sectionList = listSections;
		return listSections;
	}
	
	/**
	 * 答题模式先清空疑问标记和是否已做
	 * 2009-07-08 去掉疑问标记不合理
	 */
	private void clearMarkDoneMap() {
		if (showModel==1) {
			//for (String key: markMap.keySet()) {
			//	markMap.put(key, 0);
			//}
			for (String key: doneMap.keySet()) {
				doneMap.put(key, false);
			}
		}
	}
	
}
