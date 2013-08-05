/*
 * DateUtil.java
 *
 * Created on 2007-4-26
 *
 * Copyright(C) 2007, by Ambow Research&Development Branch.
 *
 * Original Author: liuhaiming
 * Contributor(s): 
 *
 * Changes
 * -------
 * $Log: DateUtil.java,v $
 * Revision 1.2  2008/03/24 13:12:55  zhangtao
 * *** empty log message ***
 *
 * Revision 1.1  2007/12/11 06:55:22  lixin
 * evaluating 2.0 ��Ŀ�ļ���
 *
 * Revision 1.2  2007/08/17 03:15:40  wangxiaodong
 * *** empty log message ***
 *
 * Revision 1.1  2007/08/14 10:22:37  wangxiaodong
 * *** empty log message ***
 *
 * Revision 1.1  2007/08/13 09:34:58  xiaosa
 * *** empty log message ***
 *
 * Revision 1.5  2007/05/17 08:40:45  pengqing
 * add comments
 *
 * Revision 1.4  2007/05/17 07:18:14  pengqing
 * DateUtil and it's TestClass
 *
 * Revision 1.3  2007/05/16 06:59:13  yaoshunxiang
 * *** empty log message ***
 *
 * Revision 1.2  2007/05/16 06:49:06  pengqing
 * change DateUtil
 *
 * Revision 1.1  2007/05/08 09:38:57  liuhaiming
 * *** empty log message ***
 *
 */
package com.ambow.trainingengine.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 包含了字符串和日期之间转换的方法
 * 
 * @see <a
 *      href="http://java.sun.com/docs/books/tutorial/i18n/format/simpleDateFormat.html">Java
 *      Tutorial</a>
 * @see java.util.Calendar
 * @see java.util.TimeZone
 * @see DateFormat
 * @see DateFormatSymbols
 * 
 * @author zhangrui
 */
public class DateUtil extends com.ambow.core.util.DateUtil{
	private final static Logger log = Logger.getLogger(DateUtil.class);

	private static String defaultDatePattern = "yyyy-MM-dd";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return defaultDatePattern;
	}

	/**
	 * 返回预设Format的当前日期字符串
	 */
	public static String getToday() {
		Date today = new Date();
		return format(today);
	}

	/**
	 * 返回当前日期
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 使用预设Format格式化Date成字符串
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";

		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}

		return (returnValue);
	}

	/**
	 * 使用预设格式将字符串转为Date
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用参数Format将字符串转为Date
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			log.error(e.getMessage());

			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.DAY_OF_MONTH, 1);

		return calendar.getTime();
	}

	public static Date getLastDayOfMonth(Date date) {
		Date resultDate = getFirstDayOfMonth(addMonth(date, 1));

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(resultDate);

		calendar.add(Calendar.DAY_OF_YEAR, -1);

		return calendar.getTime();
	}
	
	public static String timeFormat(int seconds){
		int hours=seconds/3600;
		int mins=seconds%3600/60;
		seconds=seconds%60;
		String outStr="";
		if(hours>0)
			outStr+=hours+"小时";
		if(mins>0)
			outStr+=mins+"分钟";
		if(seconds>0||(hours==0&&mins==0))
			outStr+=seconds+"秒";		
		return outStr;
	}
}