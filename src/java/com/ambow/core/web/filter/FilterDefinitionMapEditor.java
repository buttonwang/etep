/* 
 * FilterDefinitionMapEditor.java
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
 * $Log: FilterDefinitionMapEditor.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FilterDefinitionMapEditor extends PropertyEditorSupport {

	private static final Logger logger = Logger
			.getLogger(FilterDefinitionMapEditor.class);

	public static final String IGNORE_CASES = "IGNORE_CASES";

	public void setAsText(String s) throws IllegalArgumentException {
		FilterDefinitionMap source = new FilterDefinitionMap();

		if (StringUtils.isBlank(s)) {
			throw new IllegalArgumentException("FilterMap was NOT Defined");
		}
		
		if (s.lastIndexOf(IGNORE_CASES) != -1) {
			if (logger.isDebugEnabled()) {
				logger.debug("Match URL Ignore Cases");
			}
			
			source.setIgnoreCase(true);
		}
		
		BufferedReader br = new BufferedReader(new StringReader(s));
		int counter = 0;
		String line;
		
		while (true) {
			counter++;
			try {
				line = br.readLine();
			} catch (IOException ioe) {
				throw new IllegalArgumentException(ioe.getMessage());
			}

			if (line == null) {
				break;
			}

			line = line.trim();

			if (logger.isDebugEnabled()) {
				logger.debug("Line " + counter + ": " + line);
			}

			if (line.startsWith("//")) {
				continue;
			}
			
			if (line.lastIndexOf(IGNORE_CASES) != -1 || line.lastIndexOf('=') == -1) {
				// 当这一行为忽略大小写定义，和没有等号的时候，忽略这一行
				continue;
			}

			String name = StringUtils.substringBeforeLast(line, "=");
			String value = StringUtils.substringAfterLast(line, "=");
			if (StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
				throw new IllegalArgumentException(
						"Failed to parse a valid name/value pair from "
								+ line);
			}
			
			if (source.isIgnoreCase()) {
				
				// 如果定义了不区分大小写的话，那么在配置文件里URL 必须都是小写
				for (int i = 0; i < name.length(); i++) {
					String character = name.substring(i, i + 1);
					if (!character.toLowerCase().equals(character)) {
						throw new IllegalArgumentException(
								"You are using the IGNORE_CASES with Ant Paths, " +
								"yet you have specified an uppercase character in line: "
										+ line + " (character '"
										+ character + "')");
					}
				}
			}
			
			FilterDefinitionEditor fdEditor = new FilterDefinitionEditor();
			fdEditor.setAsText(value);
			FilterDefinition fd = (FilterDefinition) fdEditor.getValue();
			
			source.addSecureUrl(name, fd);
		}
		
		setValue(source);
	}

}
