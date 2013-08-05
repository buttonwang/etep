package com.ambow.trainingengine.exam.display;

import java.util.HashMap;
import java.util.Map;

/*
 * DisplayFactory.java
 * 
 * Created on 2008-11-13 下午04:33:55
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
public class DisplayFactory {
	
	private Map<String, IDisplay> displayClass = new HashMap<String, IDisplay>();
	
	public IDisplay getDisplayImpl(String itemType) {		
		IDisplay display = displayClass.get(itemType);
		if (display==null) display = new Display();
		return display;
	}

	public Map<String, IDisplay> getDisplayClass() {
		return displayClass;
	}

	public void setDisplayClass(Map<String, IDisplay> displayClass) {
		this.displayClass = displayClass;
	}

}