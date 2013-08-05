/**
 * 
 */
package com.ambow.trainingengine.attention.web.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.htmlparser.util.ParserException;

import com.ambow.trainingengine.attention.service.AttentionService;
import com.ambow.trainingengine.util.ExtraHtmlParser;
import com.ambow.trainingengine.util.PaginationSupport;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
public class MyAttentionAction extends AttentionBaseAction {

	private static final long serialVersionUID = -7107096094021769078L;

	public AttentionService attentionService;
	
	protected String orderNum;
	
	protected String order = null;
	
	protected String type;
	
	protected String tag;

	protected Integer page;

	/**
	 * 树型菜单
	 */
	public String execute() {
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long processInstanceId = userData.getProcessInstanceId();
		String userId = userData.getUserID();
		List<Map<String,Object>> mapList = this.attentionService.getOrderNumList(processInstanceId,userId);
		if(mapList.size()>0){
			Set<String> set = new HashSet<String>();
			for(int i=0;i<mapList.size();i++){
				Map<String,Object> nodeMap = mapList.get(i);
				String orderNum = (String)nodeMap.get("order_num");
				add(set,orderNum);
			}
			String orderNumArr = toString(set);

			List<Map<String,Object>> nodeList = this.attentionService.getNodeList(processInstanceId,orderNumArr);
			this.setRequestAttribute("mapList", nodeList);
		} 
		
		this.setRequestAttribute("orderNum", orderNum);
		this.setRequestAttribute("order", order);
		this.setRequestAttribute("type", type);
		this.setRequestAttribute("tag", tag);
		this.setRequestAttribute("pag", page);
		
		return "main";
	}
	
	/**
	 * 获取关注的试题列表
	 * @return
	 * @throws ParserException 
	 */
	public String list() throws ParserException{
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);

		long processDefinitionId  = userData.getProcessDefinitionId();
		String userId = userData.getUserID();
		Integer noteAmount = null;
		List<Map<String,Object>> attentionList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> tagList = null;
		List<Map<String,Object>> moreTagList = null;
		String orderStr = this.getOrderStr(order);
		String typeStr = this.getTypeStr();
		if(page == null){
			page = 1;
		}
		List<Map<String,Object>> itemList = this.attentionService.getAttentionAmount(processDefinitionId,userId,orderNum,typeStr,tag);
		Integer attentionAmount = itemList.size();
		String itemIdStr = this.getItemIdStr(itemList, page);
		noteAmount = this.attentionService.getNoteAmount(processDefinitionId,userId,orderNum,typeStr,tag);
		List<Map<String,Object>> attList = this.attentionService.getItemAttentionList(processDefinitionId,userId,orderNum,orderStr,typeStr,tag,page,null,itemIdStr);
		for(int i=0;i<attList.size();i++){
			Map<String,Object> attentionMap = attList.get(i);
			String content = (String)attentionMap.get("content");
			content = ExtraHtmlParser.extractTextByVisitor("<div>"+content+"</div>", "utf-8");
			if(content.length()>14){
				content = content.substring(0,12)+"...";
			}
			attentionMap.put("content", content);
			attentionList.add(attentionMap);
		}
		attList = null;
		tagList = this.attentionService.getTagList(processDefinitionId, userId, orderNum,null);
		moreTagList = this.attentionService.getTagList(processDefinitionId, userId, orderNum,1);
		PaginationSupport pag = new PaginationSupport(attentionAmount,page);
		String subjectCode = this.getSubject(userData.getProcessCategoryName());
		List<String> itemTypeList = this.attentionService.getItemType(subjectCode);
		List<String[]> itemTypesList = new ArrayList<String[]>();
		for(String itemType:itemTypeList){
			String[] itemTypeArr = new String[2];
			String code = this.getTypeCode(itemType);
			itemTypeArr[0]=code;
			itemTypeArr[1]=itemType;
			itemTypesList.add(itemTypeArr);
		}
		this.setRequestAttribute("itemTypeList", itemTypesList);
		this.setRequestAttribute("orderNum", orderNum);
		this.setRequestAttribute("order", order);
		this.setRequestAttribute("type", type);
		this.setRequestAttribute("attentionAmount", attentionAmount);
		this.setRequestAttribute("noteAmount", noteAmount);
		this.setRequestAttribute("attentionList", attentionList);
		this.setRequestAttribute("tagList", tagList);
		this.setRequestAttribute("moreTagList", moreTagList);
		this.setRequestAttribute("pag", pag);
		setAttentionSession(attentionList);
		return "list";
	}
	
	
	public AttentionService getAttentionService() {
		return attentionService;
	}

	public void setAttentionService(AttentionService attentionService) {
		this.attentionService = attentionService;
	}
	
	protected String getSubject(String processCategoryName){
		String subject = null;
		if(processCategoryName.contains("化学")){
			subject = "C";
		}else if(processCategoryName.contains("英语")){
			subject = "E";
		}else if(processCategoryName.contains("数学")){
			subject = "M";
		}else if(processCategoryName.contains("物理")){
			subject = "P";
		}
		return subject;
	}
	protected String getOrderStr(String order){
		if(order == null || order.equals("")){
			return null;
		}
		String orderStr = null;
		String[] orderArr = order.split("_");
		if(orderArr[0].equals("time")){
			orderStr = " b.insert_time "+orderArr[1];
		}else if(orderArr[0].equals("attention")){
			orderStr = " b.popularity "+orderArr[1];
		}else if(orderArr[0].equals("amount")){
			orderStr = " b.note_amount "+orderArr[1];
		}else if(orderArr[0].equals("state")){
			orderStr = " a.state "+orderArr[1];
		}else if(orderArr[0].equals("me")){
			orderStr = " note_id "+orderArr[1];
		}else if(orderArr[0].equals("type")){
			orderStr = " d.name "+orderArr[1];
		}else if(orderArr[0].equals("difficulty")){
			orderStr = " c.difficulty_value "+orderArr[1];
		}
		return orderStr;
	}
	
	protected String getTypeStr(){
		if(type == null || type.equals("")){
			return null;
		}
		String typeStr = null;

		if(type.equals("1")){
			typeStr = "and d.name='单选' ";
		}else if(type.equals("2")){
			typeStr = "and d.name='多选' ";
		}else if(type.equals("3")){
			typeStr = "and (d.name='一对一填空' or d.name='一对多填空') ";
		}else if(type.equals("4")){
			typeStr = "and (d.name='一对一实验' or d.name='一对多实验') ";
		}else if(type.equals("5")){
			typeStr = "and (d.name='一对一计算' or d.name='一对多计算') ";
		}else if(type.equals("6")){
			typeStr = "and (d.name='一对一解答' or d.name='一对多解答') ";
		}else if(type.equals("7")){
			typeStr = "and (d.name='一对一判断' or d.name='一对多判断') ";
		}else if(type.equals("8")){
			typeStr = "and (d.name='一对一作图' or d.name='一对多作图') ";
		}

		return typeStr;
	}
	
	protected String getTypeCode(String str){
		if(str == null || str.equals("")){
			return null;
		}
		String typeCode = null;
		if(str.equals("单选")){
			typeCode = "1";
		}else if(str.equals("多选")){
			typeCode = "2";
		}else if(str.equals("填空")){
			typeCode = "3";
		}else if(str.equals("实验")){
			typeCode = "4";
		}else if(str.equals("计算")){
			typeCode = "5";
		}else if(str.equals("解答")){
			typeCode = "6";
		}else if(str.equals("判断")){
			typeCode = "7";
		}else if(str.equals("作图")){
			typeCode = "8";
		}
		return typeCode;
	}
	
	protected Set<String> add(Set<String> set ,String orderNum){
		String[] orderNumArr = orderNum.split(",");
		for(int i=0;i<orderNumArr.length;i++){
			set.add("'"+orderNum.substring(0,3*(i+1)-1)+"'");
		}
		return set;
	}
	
	protected String toString(Set<String> set){
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

	@SuppressWarnings("unchecked")
	protected void setAttentionSession(List attentionList) {
		ArrayList attentionIds = new ArrayList();
		for(Map<String,Object> attention:  (List<Map<String,Object>>)attentionList) {
			attentionIds.add(attention.get("id"));
		}
		this.setSessionObj("attentionIds", attentionIds);
		
		if (orderNum==null) orderNum="";
		if (order==null) order="";
		if (type==null) type="";
		if (tag==null)  tag="";
		if (page==null) page=1;
		String attentionParams = "orderNum="+orderNum+"&order="+order+"&type="+type+"&tag="+tag+"&page="+page.toString();
		this.setSessionObj("attentionParams", attentionParams);
	}
	
	protected String getItemIdStr(List<Map<String,Object>> itemList,Integer page){
		String itemIdStr = "";
		Integer end = itemList.size();
		if(page*10<end){
			end = page*10;
		}
		for(int i=(page-1)*10;i<end;i++){
			Map<String,Object> itemMap = itemList.get(i);
			itemIdStr = itemIdStr + itemMap.get("item_id")+",";
		}
		if(itemIdStr.length()>0){
			itemIdStr = itemIdStr.substring(0, itemIdStr.length()-1);
		}
		return itemIdStr;
	}
	
	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
