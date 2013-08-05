/* 
 * Constants.java
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
 * $Log: Constants.java,v $
 * Revision 1.1  2008/05/09 05:57:52  lixin
 * �2⿼����Ŀ
 *
 * Revision 1.1  2007/07/19 06:53:09  pengqing
 * *** empty log message ***
 *
 * Revision 1.15  2007/07/13 05:40:01  xiaosa
 * *** empty log message ***
 *
 * Revision 1.14  2007/07/06 06:16:25  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.13  2007/07/04 03:00:48  xiaosa
 * *** empty log message ***
 *
 * Revision 1.12  2007/06/27 08:52:33  xiaosa
 * *** empty log message ***
 *
 * Revision 1.11  2007/06/26 07:40:43  xiaosa
 * *** empty log message ***
 *
 * Revision 1.10  2007/06/26 07:19:36  xiaosa
 * *** empty log message ***
 *
 * Revision 1.9  2007/06/21 02:54:12  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.8  2007/06/18 01:13:18  xiaosa
 * *** empty log message ***
 *
 * Revision 1.7  2007/06/14 05:42:43  xiaosa
 * *** empty log message ***
 *
 * Revision 1.6  2007/06/12 01:45:13  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.5  2007/06/12 01:40:28  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.4  2007/06/10 02:49:44  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.3  2007/06/07 07:17:53  zhangrui
 * *** empty log message ***
 *
 * Revision 1.2  2007/06/06 11:03:12  zhangrui
 * *** empty log message ***
 *
 * Revision 1.1  2007/04/07 18:37:40  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.13  2007/05/28 01:31:23  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.12  2007/05/25 08:33:08  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.11  2007/05/21 05:32:50  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.10  2007/05/15 02:13:51  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.9  2007/04/26 07:32:40  yaoshunxiang
 * ���
 * TWO_WEEK_REPORT
 *
 * Revision 1.8  2007/04/26 07:11:23  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.7  2007/04/26 07:08:21  yaoshunxiang
 * ���
 * NO_REPORT
 * �ܲð�org_id
 * �ܲ�user_id
 *
 * Revision 1.6  2007/04/26 06:51:10  yaoshunxiang
 * ��� DAY_REPORT,WEEK_REPORT
 *
 * Revision 1.5  2007/04/25 05:55:17  pengqing
 * refactor
 *
 * Revision 1.4  2007/04/25 02:09:47  pengqing
 * the code about user in session
 *
 * Revision 1.3  2007/04/17 10:00:40  pengqing
 * add user in session constants
 *
 * Revision 1.2  2007/04/13 05:46:49  pengqing
 * change properties file's position
 *
 * Revision 1.1  2007/04/13 05:45:51  pengqing
 * ʹException ��i18n ���
 *
 *
 */
package com.ambow.core.configuration;

public class Constants extends ConfigurableConstants {

	static {
		init("/conf/system.properties");
	}
	
	public final static String ERROR_BUNDLE_KEY = getProperty(
			"constant.error_bundle_key", "i18n/errors");

	public final static String USER_IN_SESSION = getProperty(
			"constant.user_in_session", "user");

	public final static String ADMIN_IN_SESSION = getProperty(
			"constant.admin_in_session", "adminTag");

	public final static String LOGIN_ACTION = getProperty(
			"constant.login_action", "login");

	public final static String LAST_URL = getProperty("constant.last_url",
			"__LAST_URL__");
	
	/**
	 * 默认每页显示的数量
	 */
	public final static int DEFAULT_PAGE_SIZE = 20;

}
