/* 
 * FilterDefinition.java
 * 
 * Created on 2007-4-12
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: FilterDefinition.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ÆÀ²â¿¼ÑÐÏîÄ¿
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/12 09:58:19  pengqing
 * enhanced filter
 *
 *
 */
package com.ambow.core.web.filter;

import java.util.ArrayList;
import java.util.List;

public class FilterDefinition {
	
	private List<String> filterNames = new ArrayList<String>();
	
	public List<String> getDefinedFilters() {
		return filterNames;
	}

	public void addConfigAttribute(String string) {
		this.filterNames.add(string);
	}

}
