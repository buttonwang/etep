/**
 * 
 */
package com.ambow.trainingengine.report.web.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.report.service.ConsolidateService;
import com.ambow.trainingengine.util.CalculateUtil;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.PauseInfoVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.service.MainPageService;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class ConsolidateAction extends ReportBaseAction {
	
	private MainPageService mainPageService;
	
	private ConsolidateService consolidateService;
	
	protected HibernateGenericDao genService;
	
	private String orderNum;
	
	private String nodeType;

	private Set<String> add(Set<String> set ,String orderNum){
		String[] orderNumArr = orderNum.split(",");
		for(int i=0;i<orderNumArr.length;i++){
			set.add("'"+orderNum.substring(0,3*(i+1)-1)+"'");
		}
		return set;
	}
	
	public String toString(Set<String> set){
		String retStr = "";
		Object[] objArr = set.toArray();
		for(int i=0;i<objArr.length;i++){
			Object obj = objArr[i];
			if(i==0){
				retStr = retStr + obj;
			}else{
				retStr = retStr +","+ obj;
			}
		}
		return retStr;
	}
	
	/**
	 * 获取OrderNum字符串
	 * @return
	 */
	private String getOrderNumArr(Long processInstanceId){
		List<Map<String,Object>> orderNumList =this.getConsolidateService().getOrderNumList(processInstanceId, orderNum);
		String orderNumArr = null;
		if(orderNumList.size()>0){

			Set<String> set = new HashSet<String>();
			for(int i=0;i<orderNumList.size();i++){
				Map<String,Object> nodeMap = orderNumList.get(i);
				String orderNum = (String)nodeMap.get("order_num");
				add(set,orderNum);
			}
			orderNumArr = toString(set);
		}
		return orderNumArr;
	}
	
	/**
	 * TAB 标题初始化
	 * @param processInstanceId
	 */
	private void init(Long processInstanceId,String orderNumArr){
		Integer practiceNum = this.getConsolidateService().getNodeCount(processInstanceId, orderNumArr, "PRACTICE");
		Integer evaluateNum = this.getConsolidateService().getNodeCount(processInstanceId, orderNumArr, "EVALUATE");
		Integer phasetestNum = this.getConsolidateService().getNodeCount(processInstanceId, orderNumArr, "PHASETEST");
		Integer unittestNum = this.getConsolidateService().getNodeCount(processInstanceId, orderNumArr, "UNITTEST");
		this.setRequestAttribute("practiceNum", practiceNum);
		this.setRequestAttribute("evaluateNum", evaluateNum);
		this.setRequestAttribute("phasetestNum", phasetestNum);
		this.setRequestAttribute("unittestNum", unittestNum);
	}
	/**
	 * 所有
	 */
	public String execute() {
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		String orderNumArr = this.getOrderNumArr(userData.getProcessInstanceId());
		Integer practiceNum = this.getConsolidateService().getNodeCount(userData.getProcessInstanceId(), orderNumArr, "PRACTICE");
		if(practiceNum ==0){
			this.nodeType="EVALUATE";
			return test();
		}
		init(userData.getProcessInstanceId(),orderNumArr);
		//获得掌握度排名
		userData = this.getMainPageService().getTotalMasteryRateOrder(userData);
		//获得正确率排名
		userData = this.getMainPageService().getTotalAccuracyRateOrder(userData);
		
		this.setSessionObj(SessionDict.UserData, userData);
		NodeInstance nodeInstance = this.getConsolidateService().getNodeInstance(userData.getProcessInstanceId(), orderNum);
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		ProcessInstance processInstance = this.getConsolidateService().getProcessInstance(userData.getProcessInstanceId());
		
		this.setRequestAttribute("processInstance", processInstance);
		
		PauseInfoVO vo = (PauseInfoVO)this.getSessionObj("pauseInfoVO");
		if(vo == null){
			this.setRequestAttribute("processStatus", 0);
		}else{
			this.setRequestAttribute("processStatus", 1);
		}
		
		//总的统计
		List<Map<String,Object>> totalList = null;
		if(orderNum !=null && !orderNum.equals("")){
			 String totalNodeIds = this.getConsolidateService().getNodeIds(userData.getProcessInstanceId(),orderNum,nodeType);
			 totalList = this.getConsolidateService().getStatReportList(userData.getProcessInstanceId(), totalNodeIds,nodeType);
		}else{
			 totalList = this.getConsolidateService().getStatReportList(userData.getProcessInstanceId(), null,nodeType);
		}
		
		this.setRequestAttribute("totalReport", totalList.get(0));
		
		List<Map<String,Object>> newReportList = new ArrayList<Map<String,Object>>();
		
		//下属节点统计
		List<Map<String,Object>> nodeList = this.getConsolidateService().getNodeList(userData.getProcessInstanceId(), orderNum,nodeType);
		for(int i=0;i<nodeList.size();i++){
			Map<String,Object> nodeMap = nodeList.get(i);
			Long id = (Long)nodeMap.get("id");
			String name = (String)nodeMap.get("name");
			String orderNum = (String)nodeMap.get("order_num");
			Long nodeInstanceId = (Long)nodeMap.get("node_instance_id"); 
			String nodeIds = this.getConsolidateService().getNodeIds(userData.getProcessInstanceId(),orderNum,nodeType);
			List<Map<String,Object>> reportList = this.getConsolidateService().getStatReportList(userData.getProcessInstanceId(), nodeIds,nodeType);
			Map<String,Object> reportMap = reportList.get(0);
			if(reportMap.get("first_test_score") == null){
				continue;
			}
			reportMap.put("name", name);
			reportMap.put("id",id);
			reportMap.put("order_num", orderNum);
			int sumZero = reportMap.get("sum_zero_star_items")==null?0:((BigDecimal)reportMap.get("sum_zero_star_items")).intValue();
			int sumHalf = reportMap.get("sum_half_star_items")==null?0:((BigDecimal)reportMap.get("sum_half_star_items")).intValue();
			int sumOne = reportMap.get("sum_one_star_items")==null?0:((BigDecimal)reportMap.get("sum_one_star_items")).intValue();
			int sumTwo = reportMap.get("sum_two_star_items")==null?0:((BigDecimal)reportMap.get("sum_two_star_items")).intValue();
			int sumThree = reportMap.get("sum_three_star_items")==null?0:((BigDecimal)reportMap.get("sum_three_star_items")).intValue();
			int sumFour = reportMap.get("sum_four_star_items")==null?0:((BigDecimal)reportMap.get("sum_four_star_items")).intValue();
			int sumFive = reportMap.get("sum_five_star_items")==null?0:((BigDecimal)reportMap.get("sum_five_star_items")).intValue();
			int itemsNum = reportMap.get("items_num")==null?0:((BigDecimal)reportMap.get("items_num")).intValue();
			Integer masteryRate = CalculateUtil.masteryRate(sumZero,sumHalf,sumOne,sumTwo,sumThree,sumFour,sumFive,itemsNum);
			reportMap.put("mastery_rate", masteryRate);
			reportMap.put("node_instance_id", nodeInstanceId);
			newReportList.add(reportMap);
		}
		this.setRequestAttribute("reportList", newReportList);
		this.setRequestAttribute("orderNum", orderNum);
		this.setRequestAttribute("nodeType", nodeType);
		
		return SUCCESS;
	}
	
	/**
	 * 训练
	 * @return
	 */
	public String  training(){
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		Map<String,Object> nodeInstanceMap = this.getConsolidateService().getNodeInstanceId(userData.getProcessInstanceId(),orderNum);
		NodeInstance nodeInstance = this.getConsolidateService().getNodeInstance(userData.getProcessInstanceId(), orderNum);
		Map<String,Object> statHistoryMap = this.getConsolidateService().getHistoryStat(userData.getProcessInstanceId(),orderNum);
		Double accuracyRate = this.getConsolidateService().getAccuracyRate(userData.getProcessInstanceId(),orderNum);
		Map<String,Object> passMap = this.getConsolidateService().getPassStat(userData.getProcessInstanceId(),orderNum);
		
		//Map<String,Object> allStatMap = this.getConsolidateService().getHistoryAllStat(userData.getProcessInstanceId(),orderNum);
		CurrentTestStatus currentTestStatus=this.genService.get(CurrentTestStatus.class, nodeInstance.getId());
		List<Map<String,Object>> historyList = this.getConsolidateService().getHistoryList(userData.getProcessInstanceId(),orderNum);
		this.setRequestAttribute("nodeInstance", nodeInstance);
		this.setRequestAttribute("statHistoryMap", statHistoryMap);
		this.setRequestAttribute("accuracyRate", accuracyRate);
		this.setRequestAttribute("passMap", passMap);
		this.setRequestAttribute("currentTestStatus", currentTestStatus);
		this.setRequestAttribute("historyList", historyList);
		this.setRequestAttribute("orderNum", orderNum);
		this.setRequestAttribute("nodeType", nodeType);
		this.setRequestAttribute("nodeInstanceMap", nodeInstanceMap);
		return "training";
	}
	
	/**
	 * 评测
	 * @return
	 */
	public String test(){
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		String orderNumArr = this.getOrderNumArr(userData.getProcessInstanceId());
		init(userData.getProcessInstanceId(),orderNumArr);
		
		NodeInstance nodeInstance = this.getConsolidateService().getNodeInstance(userData.getProcessInstanceId(), orderNum);
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		//总的统计
		List<Map<String,Object>> reportList = null;
		List<Map<String,Object>> amountList = null;

		if(orderNum !=null && !orderNum.equals("")){
			String totalNodeIds = this.getConsolidateService().getNodeIds(userData.getProcessInstanceId(),orderNum,nodeType);
			reportList = this.getConsolidateService().getTestReportList(userData.getProcessInstanceId(), totalNodeIds,nodeType);
			amountList = this.getConsolidateService().getTestAmount(userData.getProcessInstanceId(), totalNodeIds, nodeType);
		}else{
			reportList = this.getConsolidateService().getTestReportList(userData.getProcessInstanceId(), null,nodeType);
			amountList = this.getConsolidateService().getTestAmount(userData.getProcessInstanceId(), null, nodeType);
		}
		Map<String,Object> amountMap = new HashMap<String,Object>();
		for(int i=0;i<amountList.size();i++){
			Map<String,Object> map = amountList.get(i);
			amountMap.put(map.get("name").toString(), map.get("amount"));
		}
		
		this.setRequestAttribute("reportList", reportList);
		this.setRequestAttribute("amountMap", amountMap);
		this.setRequestAttribute("nodeType", nodeType);
		this.setRequestAttribute("orderNum", orderNum);
		
		return "test";
	}
	
	/**
	 * 前测和后测
	 * @return
	 */
	public String otherTest(){
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		String orderNumArr = this.getOrderNumArr(userData.getProcessInstanceId());
		init(userData.getProcessInstanceId(),orderNumArr);
		
		NodeInstance nodeInstance = this.getConsolidateService().getNodeInstance(userData.getProcessInstanceId(), orderNum);
		
		this.setRequestAttribute("nodeInstance", nodeInstance);
		
		//总的统计
		List<Map<String,Object>> reportList = null;
		List<Map<String,Object>> amountList = null;

		if(orderNum !=null && !orderNum.equals("")){
			String totalNodeIds = this.getConsolidateService().getNodeIds(userData.getProcessInstanceId(),orderNum,nodeType);
			reportList = this.getConsolidateService().getOtherTestReportList(userData.getProcessInstanceId(), totalNodeIds,nodeType);
			amountList = this.getConsolidateService().getAmount(userData.getProcessInstanceId(), totalNodeIds, nodeType);
		}else{
			reportList = this.getConsolidateService().getOtherTestReportList(userData.getProcessInstanceId(), null,nodeType);
			amountList = this.getConsolidateService().getAmount(userData.getProcessInstanceId(), null, nodeType);
		}
		Map<String,Object> amountMap = new HashMap<String,Object>();
		for(int i=0;i<amountList.size();i++){
			Map<String,Object> map = amountList.get(i);
			if (map.get("name") != null) amountMap.put(map.get("name").toString(), map.get("amount"));
		}
		
		this.setRequestAttribute("reportList", reportList);
		this.setRequestAttribute("amountMap", amountMap);
		this.setRequestAttribute("nodeType", nodeType);
		this.setRequestAttribute("orderNum", orderNum);
		if(nodeType.equals("UNITTEST")){
			return "postest";
		}else{
			return "pretest";
		}
	}
	
	public MainPageService getMainPageService() {
		return mainPageService;
	}

	public void setMainPageService(MainPageService mainPageService) {
		this.mainPageService = mainPageService;
	}

	public ConsolidateService getConsolidateService() {
		return consolidateService;
	}

	public void setConsolidateService(ConsolidateService consolidateService) {
		this.consolidateService = consolidateService;
	}
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
}
