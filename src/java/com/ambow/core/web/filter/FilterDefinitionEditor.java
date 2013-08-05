/* 
 * FilterDefinitionEditor.java
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
 * $Log: FilterDefinitionEditor.java,v $
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

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

public class FilterDefinitionEditor extends PropertyEditorSupport {

	public void setAsText(String s) throws IllegalArgumentException {
		if (StringUtils.isBlank(s)) {
			setValue(null);

			return;
		}

		String[] tokens = StringUtils.split(s, ',');
		FilterDefinition fd = new FilterDefinition();

		for (int i = 0; i < tokens.length; i++) {
			fd.addConfigAttribute(tokens[i].trim());
		}

		setValue(fd);
	}

}
