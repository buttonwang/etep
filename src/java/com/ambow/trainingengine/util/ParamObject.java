package com.ambow.trainingengine.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamObject implements java.io.Serializable {
	private static final long serialVersionUID = -278350853473041683L;
	public Map<String,Object> para=new HashMap<String,Object>(0);
	public String dateFormat="YYYY-MM-DD";
	
	/**获取为 Integet 型值*/
	public Integer getInteger(String keyName){
		try{
			String [] item = (String[])para.get(keyName);
			if(item!=null){
				return Integer.valueOf( item[0] );
			}
		}catch(Exception e){
		}
		return null;
	}
	
	/**获取为Long型对象*/
	public Long getLong(String keyName){
		try{
			String [] item = (String[])para.get(keyName);
			if(item!=null){
				return Long.valueOf( item[0] );
			}
		}catch(Exception e){
		}
		return null;
	}
	
	/**获取为 'string'型值*/
	public String get(Object keyName){
		try{
			String [] item = (String[])para.get(keyName);
			if(item!=null){
				return item[0];
			}
		}catch(Exception e){
		}
		return null;
	}
	
	public String[] getStrArr(Object keyName){
		try{ 
			return (String[])para.get(keyName);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**获取为 对象，不做任何处理*/
	public Object getObject(Object keyName){
		try{
			Object[] item = (Object[])para.get(keyName);
			if(item!=null){
				return item[0];
			}
		}catch(Exception e){
		}
		return null;
	}
	
	public void set(String keyName,Object value){
		List lst = new ArrayList();
		lst.add(value);		
		para.put(keyName,lst.toArray());
	}
	
	public void add(String keyName, Object value){
		String[] item = new String[1];
		if (value == null) item[0] = null;
		else item[0] = value.toString();
		para.put(keyName, item);
	}
	
	/**
	 *
	 * @param keyName
	 * @return
	 */
	public Date getDate(String keyName){
		//TODO 
		try{
			return  null;
		}catch(Exception e){
			return null;
		}
	}

	public Map<String, Object> getPara() {
		return para;
	}

	public void setPara(Map<String, Object> para) {
		this.para = para;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	
}
