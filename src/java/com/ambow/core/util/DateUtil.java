/*
 * 
 * .java
 * 
 * Created on 2007-11-15
 * 
 * Copyright(C) 2007, by ambow Develope & Research Branch.
 * 
 * Original Author: 朱妙文
 * Contributor(s):
 * 
 * Changes 
 * *Log*
 */
package com.ambow.core.util;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
public class DateUtil {
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
	
    /**
	 * 得到N小时后的日期
	 * 
	 * @param theDate
	 *            the the date
	 * @param hous
	 *            the hous
	 * @param pattern
	 *            the pattern
	 * 
	 * @return the date
	 */
    public static Date afterNHoursDate(Date theDate, int hous) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(theDate);
            cal.add(Calendar.HOUR_OF_DAY, hous);
            return cal.getTime();
        } catch (Exception e) {
            return getCurrentDate(); // 如果无法转化，则返回默认格式的时间。
        }
    }	
    /**
     * 获得明天的日期 指定日期的前一天 格式:YYYY-MM-DD
     * @param date
     * @return
     * @author mengdeyu
     */
    public static synchronized String getBeforeDay(String date) {
    	Date date1 = toSimpleDate(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date1);
        gc.add(Calendar.DATE, -1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    } 
    
    /**
     * 获得明天的日期 指定日期的前一天 格式:YYYY-MM-DD
     * @param date
     * @return
     * @author mengdeyu
     */
    public static synchronized String getBeforeSomeDay(String date,int days) {
    	Date date1 = toSimpleDate(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date1);
        gc.add(Calendar.DATE, -days);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    } 
    
    public static synchronized String getNextDay(String date) {
    	Date date1 = toSimpleDate(date);
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date1);
        gc.add(Calendar.DATE, 1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    } 
    /**
     * 获得明天的日期
     * 
     * @return 指定日期的下一天 格式:YYYY-MM-DD
     */
    public static synchronized String getDayAfterToday() {
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, 1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    }
    
    public static synchronized String getDayBeforeToday() {
        Date date = new Date(System.currentTimeMillis());
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        gc.add(Calendar.DATE, -1);
        return doTransform(toString(gc.getTime(),getOraExtendDateTimeFormat())).substring(0,10);
    }
    
    public static synchronized Date toSimpleDate(String dateStr){
        String[] list = dateStr.split("-");
        int year = new Integer(list[0]).intValue();
        int month = new Integer(list[1]).intValue();
        int day = new Integer(list[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day,0,0,0);
        return cale.getTime();    
    }
    private static synchronized DateFormat getOraExtendDateTimeFormat() {
        SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_EXTENDED_FORMAT
                .clone();
        theDateTimeFormat.setLenient(false);
        return theDateTimeFormat;
    }
    
    public static synchronized String toString(Date theDate,
            DateFormat theDateFormat) {
        if (theDate == null) {
            return "";
        } else {
            return theDateFormat.format(theDate);
        }
    }
    public static String doTransform(String date){
        StringBuffer buffer = new StringBuffer();
        buffer.append(date.substring(0,4));
        buffer.append("-");
        buffer.append(date.substring(4,6));
        buffer.append("-");
        buffer.append(date.substring(6,8));
        buffer.append(" ");
        buffer.append(date.substring(8,10));
        buffer.append(":");
        buffer.append(date.substring(10,12));
        buffer.append(":");
        buffer.append(date.substring(12,14));
        
        return buffer.toString();
    }
    
    public static synchronized Date toDate(String dateStr){
        String[] list0 = dateStr.split(" ");
        String date = list0[0];
        String time = list0[1];
        String[] list1 = date.split("-");
        int year = new Integer(list1[0]).intValue();
        int month = new Integer(list1[1]).intValue();
        int day = new Integer(list1[2]).intValue();
        String[] list2 = time.split(":");
        int hour = new Integer(list2[0]).intValue();
        int min = new Integer(list2[1]).intValue();
        int second = new Integer(list2[2]).intValue();
        Calendar cale =  Calendar.getInstance();
        cale.set(year,month-1,day,hour,min,second);
        return cale.getTime();    
    }
    private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat(
    "yyyyMMddHHmmss");
    
    public static void main(String[] args){

    	System.out.println(getBeforeDay("2007-11-9"));
    }
}