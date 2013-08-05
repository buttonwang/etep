/* 
 * ConfigurableContants.java
 * 
 * Created on 2007-4-13
 * 
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 * 
 * Original Author: Peng Qing
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log: ConfigurableConstants.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * ���⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.2  2007/05/28 01:31:23  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/13 05:45:51  pengqing
 * ʹException ��i18n ���
 *
 *
 */
package com.ambow.core.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 可用Properties文件配置的Contants基类 <p/> 子类可如下编写
 * 
 * <pre>
 *     public class Constants extends ConfigurableContants {
 *      static {
 *        init(&quot;system.properties&quot;);
 *     }
 *     &lt;p/&gt;
 *     public final static String ERROR_BUNDLE_KEY = getProperty(&quot;constant.error_bundle_key&quot;, &quot;errors&quot;); }
 * </pre>
 * 
 * @author Peng Qing
 * @see org.springside.core.Constants
 */
public class ConfigurableConstants {

	private static final Logger logger = Logger
			.getLogger(ConfigurableConstants.class);

	protected static Properties p = new Properties();

	protected static void init(String propertyFileName) {
		InputStream in = null;
		try {
			in = ConfigurableConstants.class
					.getResourceAsStream(propertyFileName);
			if (in != null)
				p.load(in);
		} catch (IOException e) {
			logger.error("load " + propertyFileName + " into Contants error");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("载入系统配置文件时出错，文件路径：" + propertyFileName);
				}
			}
		}
	}

	protected static String getProperty(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}

}
