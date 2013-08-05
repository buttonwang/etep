/**
 * 
 */
package com.ambow.trainingengine.attention.web.action;
                
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.htmlparser.util.ParserException;

import com.ambow.trainingengine.util.ExtraHtmlParser;
import com.ambow.trainingengine.util.PaginationSupport;
import com.ambow.trainingengine.util.SessionDict;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
public class TopAttentionAction extends MyAttentionAction {

	private static final long serialVersionUID = -2560175669272809294L;

	/**
	 * 树型菜单
	 */
	public String execute() {
		
		UserDataVO userData=(UserDataVO)this.getSessionObj(SessionDict.UserData);
		long processInstanceId = userData.getProcessInstanceId();
		
		List<Map<String,Object>> mapList = this.attentionService.getOrderNumList(processInstanceId,null);
		
		if(mapList.size()>0){
			Set<String> set = new HashSet<String>();
			for(int i=0;i<mapList.size();i++){
				Map<String,Object> nodeMap = mapList.get(i);
				String orderNum = (String)nodeMap.get("order_num");
				add(set,orderNum);
			}
			String orderNumArr = toString(set);

			List<Map<String,Object>> nodeList = this.attentionService.getNodeList(processInstanceId, orderNumArr);
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
		List<Map<String,Object>> attentionList = new LinkedList<Map<String,Object>>();
		List<Map<String,Object>> tagList = null;
		String orderStr = this.getOrderStr(order);
		String typeStr = this.getTypeStr();
		if(page == null){
			page = 1;
		}
		List<Map<String,Object>> itemList = this.attentionService.getAttentionAmount(processDefinitionId,null,orderNum,typeStr,tag);
		Integer attentionAmount = itemList.size();
		String itemIdStr = this.getItemIdStr(itemList, page);
		List<Map<String,Object>> attList = this.attentionService.getItemAttentionList(processDefinitionId,null, orderNum,orderStr,typeStr,tag,page,userId,itemIdStr);
		tagList = this.attentionService.getTagListTop(processDefinitionId,orderNum);
		Map<String,Object> attMap = null;
		//数据查重
		if(order == null || (!order.equals("me_asc") && !order.equals("me_desc"))){
			for(int i=0;i<attList.size();i++){
				Map<String,Object> attentionMap = attList.get(i);
				if(attMap != null && attMap.get("item_id").equals(attentionMap.get("item_id"))){
					if(attentionMap.get("note_id") !=null){
						attentionList.remove(attentionList.size()-1);
						attMap.put("note_id",attentionMap.get("note_id")); 
						attMap.put("history_answer_status_id", attentionMap.get("history_answer_status_id"));
						attentionList.add(attMap);
					}
				}else{
					attMap = attentionMap;
					String content = (String)attentionMap.get("content");
					String noteId = (String)attentionMap.get("note_id");
					content = ExtraHtmlParser.extractTextByVisitor("<div>"+content+"</div>", "utf-8");
					if(content.length()>18){
						content = content.substring(0,16)+"...";
					}
					attentionMap.put("content", content);
					if(noteId != null && !noteId.equals(userData.getUserID())){
						attentionMap.put("note_id", null);
					}
					attentionList.add(attentionMap);
				}
			}
		}else if(order != null && order.equals("me_asc")){
			String itemIdObj= "";
			for(int i=attList.size()-1;i>=0;i--){
				Map<String,Object> attentionMap = attList.get(i);
				Integer itemId = (Integer)attentionMap.get("item_id");
				if(itemIdObj.contains(itemId.toString())){
					continue;
				}else{
					
					if(attMap != null && attMap.get("item_id").equals(attentionMap.get("item_id"))){
						if(attentionMap.get("note_id") !=null){
							attentionList.remove(attentionList.size()-1);
							attMap.put("note_id",attentionMap.get("note_id")); 
							attMap.put("history_answer_status_id", attentionMap.get("history_answer_status_id"));
							attentionList.add(0, attMap);
						}
					}else{
						attMap = attentionMap;
						String content = (String)attentionMap.get("content");
						String noteId = (String)attentionMap.get("note_id");
						content = ExtraHtmlParser.extractTextByVisitor("<div>"+content+"</div>", "utf-8");
						if(content.length()>18){
							content = content.substring(0,16)+"...";
						}
						attentionMap.put("content", content);
						if(noteId != null && !noteId.equals(userData.getUserID())){
							attentionMap.put("note_id", null);
						}
						attentionList.add(0, attMap);
					}
				}
				if(attentionMap.get("note_id") !=null){
					itemIdObj = itemIdObj + ","+attentionMap.get("item_id");
				}
			}
		}else if(order != null && order.equals("me_desc")){
			String itemIdObj= "";
			for(int i=0;i<attList.size();i++){
				Map<String,Object> attentionMap = attList.get(i);
				Integer itemId = (Integer)attentionMap.get("item_id");
				if(itemIdObj.contains(itemId.toString())){
					continue;
				}else{
					if(attMap != null && attMap.get("item_id").equals(attentionMap.get("item_id"))){
						if(attentionMap.get("note_id") !=null){
							attentionList.remove(attentionList.size()-1);
							attMap.put("note_id",attentionMap.get("note_id")); 
							attMap.put("history_answer_status_id", attentionMap.get("history_answer_status_id"));
							attentionList.add(attMap);
						}
					}else{
						attMap = attentionMap;
						String content = (String)attentionMap.get("content");
						String noteId = (String)attentionMap.get("note_id");
						content = ExtraHtmlParser.extractTextByVisitor("<div>"+content+"</div>", "utf-8");
						if(content.length()>18){
							content = content.substring(0,16)+"...";
						}
						attentionMap.put("content", content);
						if(noteId != null && !noteId.equals(userData.getUserID())){
							attentionMap.put("note_id", null);
						}
						attentionList.add(attentionMap);
					}
				}
				if(attentionMap.get("note_id") !=null){
					itemIdObj = itemIdObj + ","+attentionMap.get("item_id");
				}
			}
		}
		attList = null;
		
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
		this.setRequestAttribute("attentionList", attentionList);
		this.setRequestAttribute("tagList", tagList);
		this.setRequestAttribute("pag", pag);
		setAttentionSession(attentionList);
		return "list";
	}
	

	
}
