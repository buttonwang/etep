/**
 * 
 */
package com.ambow.trainingengine.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanjunqi
 *
 */
public class DataObject {
	private List<String> list = new ArrayList<String>();
	private static final DataObject m_instance = new DataObject();

	private DataObject() {
		
	}

	public static DataObject getInstance() {

		return m_instance;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

}