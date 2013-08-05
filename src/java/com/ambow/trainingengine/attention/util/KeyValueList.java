package com.ambow.trainingengine.attention.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyValueList {
	public static List<Map<String,Object>> getList(String key,String value){
		List<Map<String,Object>> lst = new ArrayList<Map<String,Object>> (3);
		String keyArr[]=key.split(" |,|;");
		String valArr[]=value.split(" |,|;");
		for (int i = 0; i < keyArr.length; i++) {
			Map<String,Object> map = new HashMap<String,Object>(1);
			try{
				map.put("k",keyArr[i]);
				try{
					map.put("v",valArr[i]);
				}catch(Exception e){
					map.put("v",keyArr[i]);
				}
				lst.add(map);
			}catch(Exception e){
			}
		}
		return lst;
	}
}
