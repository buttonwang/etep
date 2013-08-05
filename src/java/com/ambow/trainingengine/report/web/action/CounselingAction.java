/**
 * 
 */
package com.ambow.trainingengine.report.web.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.ambow.core.util.NumberUtil;
import com.ambow.trainingengine.exam.display.DisplayFactory;
import com.ambow.trainingengine.exam.service.ExamWidgetService;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.report.service.CounselingService;
import com.ambow.trainingengine.util.CalculateUtil;
import com.ambow.trainingengine.util.ExtraHttpClient;
import com.ambow.trainingengine.util.FileUtil;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class CounselingAction extends ReportBaseAction {
	
	private CounselingService counselingService = null;
	
	private Integer categoryId = null;
	
	private String actor = null;
	
	private String teacherID= null;
	
	//parameters for details
	private Integer nodeId;
	
	private Integer statType; //统计类型。0:全部  5：5星题 4：4星题 3：3星题  10：错题 11：疑问题
	
	private String itemIds;

	private String checkItemIds;

	private int start=0;
	private int pageSize=5;

	private ExamWidgetService examWidgetService;
	private DisplayFactory displayFactory;
	
	/**
	 * 获取用户列表
	 * @return
	 * @throws Exception
	 */
	public String actor() throws Exception{
		ExtraHttpClient httpClient = new ExtraHttpClient();
		//InputStream in = httpClient.getResponseBodyAsStream("http://124.42.8.101/red/portal/getUsersByTeacher.jsp?teacherID="+this.teacherID);
		InputStream in = httpClient.getResponseBodyAsStream("http://www.ebopo.com/red/portal/getUsersByTeacher.jsp?teacherID="+this.teacherID);
		
		String content = FileUtil.readInpuStream(in, "utf-8");
		this.getHttpServletRequest().setAttribute("actor", content);
		return "actor";
	}

	/**
	 * 获取流程列表
	 * 
	 * @return
	 */
	public String execute() {
		if(categoryId== null){
			categoryId=8;
		}
		//获取章列表
		List<Map<String,Object>> testList = this.getCounselingService().queryTestList(this.actor, this.categoryId);
		List<Map<String,Object>> actorGroupList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> groupList =new ArrayList<Map<String,Object>>();
		Map<String,Object> allMap = new HashMap<String,Object>();
		Map<String,Object> tempGroupMap = null; 
		for(int i=0;i<testList.size();i++){
			Map<String,Object> testMap=testList.get(i);
			if(tempGroupMap == null){
				tempGroupMap = testMap;
			}else{
				String orderNum = (String)testMap.get("order_num");
				String nodeType = (String)testMap.get("node_type");
				if((orderNum.length()==5 && nodeType.equals("GROUP")) || (orderNum.length()==2 && nodeType.equals("GROUP"))){
					if(tempGroupMap.get("score_temp") != null){
						actorGroupList.add(tempGroupMap);
					}
					tempGroupMap = testMap;
				}else{
					tempGroupMap = calculate(tempGroupMap,testMap,1);
				}
			}
		}
		if(testList.size()>0 && tempGroupMap.get("score_temp") != null){
			actorGroupList.add(tempGroupMap);
		}
		if(actorGroupList.size()>0){
			Map<Long,Map<String,Object>> map = new TreeMap<Long,Map<String,Object>>();
			for(int i=0;i<actorGroupList.size();i++){
				Map<String,Object> tempMap = actorGroupList.get(i);
				Long nodeId = (Long)tempMap.get("node_id");
				Map<String,Object> groupMap = map.get(nodeId);
				Float learningProcessRate = getLearningProcessRate((Integer)tempMap.get("pass_score"),(Integer)tempMap.get("total_score1"));
				Float accuracyRate = getAccuracyRate((Float)tempMap.get("score_temp"),(Integer)tempMap.get("total_score_temp"));
				Float masteryRate = getMasteryRate((Integer)tempMap.get("sum_zero_star_items_temp"),
						(Integer)tempMap.get("sum_half_star_items_temp"),
						(Integer)tempMap.get("sum_one_star_items_temp"),
						(Integer)tempMap.get("sum_two_star_items_temp"),
						(Integer)tempMap.get("sum_three_star_items_temp"),
						(Integer)tempMap.get("sum_four_star_items_temp"),
						(Integer)tempMap.get("sum_five_star_items_temp"));
				if(groupMap==null){
					groupMap = tempMap;
					groupMap.put("num", 1);
					groupMap.put("learning_process_rate", NumberUtil.getDecimal(learningProcessRate,0));
					groupMap.put("accuracy_rate", NumberUtil.getDecimal(accuracyRate,0));
					groupMap.put("mastery_rate", NumberUtil.getDecimal(masteryRate,0));
					map.put(nodeId, groupMap);
				}else{
					groupMap=calculate(groupMap,tempMap,2);
					groupMap.put("num", (Integer)groupMap.get("num")+1);
					groupMap.put("learning_process_rate", (Double)groupMap.get("learning_process_rate")+NumberUtil.getDecimal(learningProcessRate,0));
					groupMap.put("accuracy_rate", (Double)groupMap.get("accuracy_rate")+NumberUtil.getDecimal(accuracyRate,0));
					groupMap.put("mastery_rate", (Double)groupMap.get("mastery_rate")+NumberUtil.getDecimal(masteryRate,0));
	
					map.put(nodeId, groupMap);
				}
			}
	
			for(Map<String,Object> tempMap:map.values()){
	
				allMap = calculate(allMap,tempMap,2);
	
				tempMap.put("learning_process_rate", NumberUtil.getDecimal((Double)tempMap.get("learning_process_rate")/(Integer)tempMap.get("num"),1));
				tempMap.put("accuracy_rate", NumberUtil.getDecimal((Double)tempMap.get("accuracy_rate")/(Integer)tempMap.get("num"),1));
				tempMap.put("mastery_rate", NumberUtil.getDecimal((Double)tempMap.get("mastery_rate")/(Integer)tempMap.get("num"),1));
				if(allMap.get("learning_process_rate") ==null){
					allMap.put("learning_process_rate", tempMap.get("learning_process_rate"));
				}else{
					allMap.put("learning_process_rate", (Double)allMap.get("learning_process_rate")+(Double)tempMap.get("learning_process_rate"));
				}
				if(allMap.get("accuracy_rate") ==null){
					allMap.put("accuracy_rate", tempMap.get("accuracy_rate"));
				}else{
					allMap.put("accuracy_rate", (Double)allMap.get("accuracy_rate")+(Double)tempMap.get("accuracy_rate"));
				}
				if(allMap.get("mastery_rate") ==null){
					allMap.put("mastery_rate", tempMap.get("mastery_rate"));
				}else{
					allMap.put("mastery_rate", (Double)allMap.get("mastery_rate")+(Double)tempMap.get("mastery_rate"));
				}
				groupList.add(tempMap);
			}
			
			allMap.put("learning_process_rate", NumberUtil.getDecimal((Double)allMap.get("learning_process_rate")/map.values().size(),1));
			allMap.put("accuracy_rate", NumberUtil.getDecimal((Double)allMap.get("accuracy_rate")/map.values().size(),1));
			allMap.put("mastery_rate", NumberUtil.getDecimal((Double)allMap.get("mastery_rate")/map.values().size(),1));
		}
		this.getHttpServletRequest().setAttribute("groupList", groupList);
		this.getHttpServletRequest().setAttribute("allMap", allMap);
		this.getHttpServletRequest().setAttribute("categoryId", categoryId);
		
		this.setSessionObj("groupList", groupList);
		return "success";
	}
	
	public String details () {
		itemIds = this.counselingService.queryItemsByNodeAndType(actor, categoryId, statType, nodeId);
		if (!itemIds.equals("")) {
			List<Page> pages = genItemsView();
			genActorItem(pages);
		}
		return "details";
	}

	private void genActorItem(List<Page> pages) {
		Map<Integer, String> actorItemMap = new HashMap<Integer, String>();
		Integer itemId;
		String actores;
		for(Page page: pages) {
			for (Item item: page.getItems()) {
				itemId = item.getId();
				actores = this.counselingService.queryActorByNodeAndType(actor, categoryId, statType, nodeId, itemId);
				actorItemMap.put(itemId, actores);
			}
		}
		this.getHttpServletRequest().setAttribute("actorItemMap", actorItemMap);
	}
	
	//导出全部试题
	public String exportAll () {
		itemIds = this.counselingService.queryItemsByNodeAndType(actor, categoryId, statType, nodeId);
		if (!itemIds.equals("")) {
			pageSize = itemIds.split(",").length;
			genItemsView();
		}
		return "export";
	}
	
	//导出部分试题
	public String exportAny () {
		itemIds = checkItemIds.replaceFirst(",$", "");
		if (!itemIds.equals("")) {
			pageSize = itemIds.split(",").length;
			genItemsView();
		}
		return "export";
	}
	
	/**
	 * 计算星级题和总分
	 * @param tempGroupMap
	 * @param testMap
	 * @return
	 */
	private Map<String,Object> calculate(Map<String,Object> tempGroupMap,Map<String,Object> testMap,Integer num){
		if(testMap.get("node_type").equals("PRACTICE") || num==2){
			if(tempGroupMap.get("sum_incorrect_items") == null){
				tempGroupMap.put("sum_incorrect_items", testMap.get("sum_incorrect_items"));
			}else if(testMap.get("sum_incorrect_items") != null){
				tempGroupMap.put("sum_incorrect_items", (Integer)tempGroupMap.get("sum_incorrect_items") + (Integer)testMap.get("sum_incorrect_items"));
			}
			if(tempGroupMap.get("unsure_mark_items") == null){
				tempGroupMap.put("unsure_mark_items", testMap.get("unsure_mark_items"));
			}else if(testMap.get("unsure_mark_items") != null){
				tempGroupMap.put("unsure_mark_items", (Integer)tempGroupMap.get("unsure_mark_items") + (Integer)testMap.get("unsure_mark_items"));
			}
			if(tempGroupMap.get("sum_five_star_items") == null){
				tempGroupMap.put("sum_five_star_items", testMap.get("sum_five_star_items"));
			}else if(testMap.get("sum_five_star_items") != null){
				tempGroupMap.put("sum_five_star_items", (Integer)tempGroupMap.get("sum_five_star_items") + (Integer)testMap.get("sum_five_star_items"));
			}
			if(tempGroupMap.get("sum_four_star_items") == null){
				tempGroupMap.put("sum_four_star_items", testMap.get("sum_four_star_items"));
			}else if(testMap.get("sum_four_star_items") != null){
				tempGroupMap.put("sum_four_star_items", (Integer)tempGroupMap.get("sum_four_star_items") + (Integer)testMap.get("sum_four_star_items"));
			}
			if(tempGroupMap.get("sum_three_star_items") == null){
				tempGroupMap.put("sum_three_star_items", testMap.get("sum_three_star_items"));
			}else if(testMap.get("sum_three_star_items") != null){
				tempGroupMap.put("sum_three_star_items", (Integer)tempGroupMap.get("sum_three_star_items") + (Integer)testMap.get("sum_three_star_items"));
			}
			if(tempGroupMap.get("sum_two_star_items") == null){
				tempGroupMap.put("sum_two_star_items", testMap.get("sum_two_star_items"));
			}else if(testMap.get("sum_three_star_items") != null){
				tempGroupMap.put("sum_two_star_items", (Integer)tempGroupMap.get("sum_two_star_items") + (Integer)testMap.get("sum_two_star_items"));
			}
			if(tempGroupMap.get("sum_one_star_items") == null){
				tempGroupMap.put("sum_one_star_items", testMap.get("sum_one_star_items"));
			}else if(testMap.get("sum_one_star_items") != null){
				tempGroupMap.put("sum_one_star_items", (Integer)tempGroupMap.get("sum_one_star_items") + (Integer)testMap.get("sum_one_star_items"));
			}
			if(tempGroupMap.get("sum_half_star_items") == null){
				tempGroupMap.put("sum_half_star_items", testMap.get("sum_half_star_items"));
			}else if(testMap.get("sum_half_star_items") != null){
				tempGroupMap.put("sum_half_star_items", (Integer)tempGroupMap.get("sum_half_star_items") + (Integer)testMap.get("sum_half_star_items"));
			}
			if(tempGroupMap.get("sum_zero_star_items") == null){
				tempGroupMap.put("sum_zero_star_items", testMap.get("sum_zero_star_items"));
			}else if(testMap.get("sum_zero_star_items") != null){
				tempGroupMap.put("sum_zero_star_items", (Integer)tempGroupMap.get("sum_zero_star_items") + (Integer)testMap.get("sum_zero_star_items"));
			}
			if(tempGroupMap.get("score") == null){
				tempGroupMap.put("score", testMap.get("score"));
			}else if(testMap.get("score") != null){
				tempGroupMap.put("score", (Float)tempGroupMap.get("score") + (Float)testMap.get("score"));
			}
			if(tempGroupMap.get("total_score") == null){
				tempGroupMap.put("total_score", testMap.get("total_score"));
			}else if(testMap.get("total_score") != null){
				tempGroupMap.put("total_score", (Integer)tempGroupMap.get("total_score") + (Integer)testMap.get("total_score"));
			}
		}
		if(tempGroupMap.get("sum_incorrect_items_temp") == null){
			tempGroupMap.put("sum_incorrect_items_temp", testMap.get("sum_incorrect_items"));
		}else if(testMap.get("sum_incorrect_items") != null){
			tempGroupMap.put("sum_incorrect_items_temp", (Integer)tempGroupMap.get("sum_incorrect_items_temp") + (Integer)testMap.get("sum_incorrect_items"));
		}
		if(tempGroupMap.get("unsure_mark_items_temp") == null){
			tempGroupMap.put("unsure_mark_items_temp", testMap.get("unsure_mark_items"));
		}else if(testMap.get("unsure_mark_items") != null){
			tempGroupMap.put("unsure_mark_items_temp", (Integer)tempGroupMap.get("unsure_mark_items_temp") + (Integer)testMap.get("unsure_mark_items"));
		}
		if(tempGroupMap.get("sum_five_star_items_temp") == null){
			tempGroupMap.put("sum_five_star_items_temp", testMap.get("sum_five_star_items"));
		}else if(testMap.get("sum_five_star_items") != null){
			tempGroupMap.put("sum_five_star_items_temp", (Integer)tempGroupMap.get("sum_five_star_items_temp") + (Integer)testMap.get("sum_five_star_items"));
		}
		if(tempGroupMap.get("sum_four_star_items_temp") == null){
			tempGroupMap.put("sum_four_star_items_temp", testMap.get("sum_four_star_items"));
		}else if(testMap.get("sum_four_star_items") != null){
			tempGroupMap.put("sum_four_star_items_temp", (Integer)tempGroupMap.get("sum_four_star_items_temp") + (Integer)testMap.get("sum_four_star_items"));
		}
		if(tempGroupMap.get("sum_three_star_items_temp") == null){
			tempGroupMap.put("sum_three_star_items_temp", testMap.get("sum_three_star_items"));
		}else if(testMap.get("sum_three_star_items") != null){
			tempGroupMap.put("sum_three_star_items_temp", (Integer)tempGroupMap.get("sum_three_star_items_temp") + (Integer)testMap.get("sum_three_star_items"));
		}
		if(tempGroupMap.get("sum_two_star_items_temp") == null){
			tempGroupMap.put("sum_two_star_items_temp", testMap.get("sum_two_star_items"));
		}else if(testMap.get("sum_three_star_items") != null){
			tempGroupMap.put("sum_two_star_items_temp", (Integer)tempGroupMap.get("sum_two_star_items_temp") + (Integer)testMap.get("sum_two_star_items"));
		}
		if(tempGroupMap.get("sum_one_star_items_temp") == null){
			tempGroupMap.put("sum_one_star_items_temp", testMap.get("sum_one_star_items"));
		}else if(testMap.get("sum_one_star_items") != null){
			tempGroupMap.put("sum_one_star_items_temp", (Integer)tempGroupMap.get("sum_one_star_items_temp") + (Integer)testMap.get("sum_one_star_items"));
		}
		if(tempGroupMap.get("sum_half_star_items_temp") == null){
			tempGroupMap.put("sum_half_star_items_temp", testMap.get("sum_half_star_items"));
		}else if(testMap.get("sum_half_star_items") != null){
			tempGroupMap.put("sum_half_star_items_temp", (Integer)tempGroupMap.get("sum_half_star_items_temp") + (Integer)testMap.get("sum_half_star_items"));
		}
		if(tempGroupMap.get("sum_zero_star_items_temp") == null){
			tempGroupMap.put("sum_zero_star_items_temp", testMap.get("sum_zero_star_items"));
		}else if(testMap.get("sum_zero_star_items") != null){
			tempGroupMap.put("sum_zero_star_items_temp", (Integer)tempGroupMap.get("sum_zero_star_items_temp") + (Integer)testMap.get("sum_zero_star_items"));
		}
		if(tempGroupMap.get("score_temp") == null){
			tempGroupMap.put("score_temp", testMap.get("score"));
		}else if(testMap.get("score") != null){
			tempGroupMap.put("score_temp", (Float)tempGroupMap.get("score_temp") + (Float)testMap.get("score"));
		}
		if(tempGroupMap.get("total_score_temp") == null){
			tempGroupMap.put("total_score_temp", testMap.get("total_score"));
		}else if(testMap.get("total_score") != null){
			tempGroupMap.put("total_score_temp", (Integer)tempGroupMap.get("total_score_temp") + (Integer)testMap.get("total_score"));
		}
		if(tempGroupMap.get("total_score1") == null){
			tempGroupMap.put("total_score1", testMap.get("total_score1"));
		}else if(testMap.get("total_score1") != null){
			tempGroupMap.put("total_score1", (Integer)tempGroupMap.get("total_score1") + (Integer)testMap.get("total_score1"));
		}
		if(num==1){
			tempGroupMap = testHandle(tempGroupMap,testMap);
		}else if(num==2){
			tempGroupMap = groupHandle(tempGroupMap,testMap);
		}
		return tempGroupMap;
	}
	
	private Map<String,Object> testHandle(Map<String,Object> tempGroupMap,Map<String,Object> testMap){
		if(tempGroupMap.get("pass_score") == null){
			if(testMap.get("node_status").equals(2) || testMap.get("node_status").equals(3)){
				tempGroupMap.put("pass_score", testMap.get("total_score1"));
			}else{
				tempGroupMap.put("pass_score", 0);
			}
		}else if(tempGroupMap.get("pass_score") != null){
			if((testMap.get("node_status").equals(2) || testMap.get("node_status").equals(3)) && testMap.get("total_score1")!=null){
				tempGroupMap.put("pass_score", (Integer)tempGroupMap.get("pass_score")+(Integer)testMap.get("total_score1"));
			}
		}
		return tempGroupMap;
	}
	
	private Map<String,Object> groupHandle(Map<String,Object> tempGroupMap,Map<String,Object> testMap){
		if(tempGroupMap.get("pass_score") == null){
			tempGroupMap.put("pass_score", testMap.get("pass_score"));
		}else if(tempGroupMap.get("pass_score") != null && testMap.get("pass_score")!=null){
			tempGroupMap.put("pass_score", (Integer)tempGroupMap.get("pass_score")+(Integer)testMap.get("pass_score"));
		}
		return tempGroupMap;
	}
	
	private float getLearningProcessRate(Integer passScore,Integer totalScore){
		if (totalScore == null || totalScore == 0) {
			return 0;
		}
		float learningProcessRate = passScore.floatValue() / totalScore * 100;
		return learningProcessRate;
	}
	
	private float getAccuracyRate(Float score, Integer totalScore) {
		if (totalScore == null || totalScore ==null || totalScore == 0) {
			return 0;
		}
		float accuracyRate = score / totalScore * 100;
		return accuracyRate;
	}

	private float getMasteryRate(Integer zeroItems, Integer halfItems,
			Integer oneItems, Integer twoItems, Integer threeItems,
			Integer fourItems, Integer fiveItems) {
		if(zeroItems==null){
			zeroItems=0;
		}
		if(halfItems==null){
			halfItems=0;
		}
		if(oneItems==null){
			oneItems=0;
		}
		if(twoItems==null){
			twoItems=0;
		}
		if(threeItems==null){
			threeItems=0;
		}
		if(fourItems==null){
			fourItems=0;
		}
		if(fiveItems==null){
			fiveItems=0;
		}
		int totalNum = zeroItems + halfItems + oneItems + twoItems + threeItems
				+ fourItems + fiveItems;
		return CalculateUtil.masteryRate(zeroItems, halfItems, oneItems,
				twoItems, threeItems, fourItems, fiveItems, totalNum);
	}

	private List<Page> genItemsView() {
		Integer totalCount = itemIds.split(",").length;
	    this.setRequestAttribute("totalCount", totalCount);
	    
		ViewControlProxy viewControl =  new ViewControlProxy(1, "mpc", 0);

		viewControl.setShowModel(4);	//4是Widget
		
	    List<Item> items = examWidgetService.getItems(itemIds, start, pageSize);
	   
	    viewControl.generateWidget2(items, 0);
	    
	    List<Page> pages = viewControl.getPageList();
	    
	    for(Page page: viewControl.getPageList()) {
			for (Item item: page.getItems()) {
				item.setItemNum(item.getItemNum() + start);
			}
		}
	    
	    this.getHttpServletRequest().setAttribute("pages", pages);
	    return pages;
	}
	
	public CounselingService getCounselingService() {
		return counselingService;
	}

	public void setCounselingService(CounselingService counselingService) {
		this.counselingService = counselingService;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public String getTeacherID() {
		return teacherID;
	}

	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	
	public Integer getNodeId() {
		return nodeId;
	}

	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
	}

	public Integer getStatType() {
		return statType;
	}

	public void setStatType(Integer statType) {
		this.statType = statType;
	}
	
	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}
	
	public ExamWidgetService getExamWidgetService() {
		return examWidgetService;
	}

	public void setExamWidgetService(ExamWidgetService examWidgetService) {
		this.examWidgetService = examWidgetService;
	}

	public DisplayFactory getDisplayFactory() {
		return displayFactory;
	}

	public void setDisplayFactory(DisplayFactory displayFactory) {
		this.displayFactory = displayFactory;
	}
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCheckItemIds() {
		return checkItemIds;
	}

	public void setCheckItemIds(String checkItemIds) {
		this.checkItemIds = checkItemIds;
	}
	
}
