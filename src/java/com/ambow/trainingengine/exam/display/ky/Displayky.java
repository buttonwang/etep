package com.ambow.trainingengine.exam.display.ky;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ambow.trainingengine.exam.display.IDisplay;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * DisplayKy.java
 * 
 * Created on 2008-11-13 下午07:57:13
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
public class Displayky implements IDisplay {

	public void doDisplay(Page page, ViewControl viewControl, HttpServletRequest request) {
		
		//取得试题分值信息
		List<Float> scoreList=null;
		if(viewControl.getShowModel()!=1){
			scoreList=getScoreList(page, viewControl.getScoreMap());
			request.setAttribute("scoreList", scoreList);
		}
	}
	
	/*
	 * 根据已有条件，生成Float List
	 */
	private List<Float> getScoreList(Page page, Map<String, Float> scoreMap) {
		List<Float> scoreList=new ArrayList<Float>();
		String mapKey=null;
		Float score=null;
		for(Item item:page.getItems()){
			if(item.getSubItems().size()>0){
				for(SubItem subItem :item.getSubItems()){
					mapKey=ExamUtil.getMapKey(item, subItem);
					score=scoreMap.get(mapKey);
					scoreList.add(score);
				}
			}else{
				mapKey=ExamUtil.getMapKey(item, null);
				score=scoreMap.get(mapKey);
				scoreList.add(score);
			}
		}
		return scoreList;
	}

}
