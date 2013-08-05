package com.ambow.trainingengine.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/*
 * Tool.java: 对action或service中常用方法的封装
 * 
 * Created on 2009-7-7
 * 
 * Copyright(C) 2009, by Ambow Research&Development Branch.
 * 
 * Original Author: WeiShaoying
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $Log$
 */

public class CommonTool {
	
	
	/**
	 * 得到精确到日的format
	 * @return
	 */
	public static SimpleDateFormat getDateFormat2Day() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
	
	public static SimpleDateFormat getDateFormat2Second() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 根据指定路径创建目录
	 * @param path
	 * @return
	 */
	public static boolean makeDir(String path) {
		boolean flag = false;
		try {
			File dir = new File(path);
			if(!dir.exists()) {
				if(dir.mkdirs()) {
					flag = true;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	
	/**
	 * 根据文件的路径和文件名创建文件
	 * @param path
	 * @param name
	 * @return
	 */
	public static File makeFile(String path,String name) {
		File f = null;
		try {
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
			System.out.println("---- file full path = " + path+name);
			f = new File(path+name);
			if(!f.exists()) {
				f.createNewFile();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	
	/**
	 * 判断某个字符串是否非空
	 * 
	 * @param str
	 * @return true：不为空 flase：为空
	 */
	public static boolean notEmptyStr(String str) {
		return str != null && !"".equals(str.trim()) && !"null".equals(str.trim());
	}

	/**
	 * 去除字符串中间、两边的空格
	 * @param str
	 * @return
	 */
	public static String ridStrAllSpaces(String str) {
		if(notEmptyStr(str)) {
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<str.length();i++) {
				String temp = str.substring(i, i+1);
				if(!"".equals(temp.trim())) {
					sb.append(temp); 
				}
			}
			return sb.toString();
		}else {
			return "";
		}
	}

	/**
	 * 求date1,date2相距多少天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int diffDate(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		if(time1 >= time2) {
			return (int)((time1 - time2)/(24*60*60*1000));
		}
		return (int)((time2 - time1)/(24*60*60*1000));
	}	
	
	/**
	 * 获取与给定日期相差指定天数的日期(正数向后 负数向前)
	 * @param date
	 * @param day
	 * @return
	 */
	  public static java.util.Date getAddDayDate(java.util.Date date, int day) {
		  Calendar c = Calendar.getInstance();
		  c.setTime(date);
		  c.set(Calendar.DATE, c.get(Calendar.DATE)+day);
		  return c.getTime();
	  }
	  
	/**
	 * 获取与给定日期相差 cMonth(正数向后 负数向前) 月的日期
	 * @param date
	 * @param cmonth
	 * @return
	 */
	public static java.util.Date getAddMonthDate(java.util.Date date, int cmonth) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      calendar.add(Calendar.MONTH,cmonth);
      return calendar.getTime();
  }

	/**
	 * 获取与给定日期相差year年(正数向后 负数向前)的日期
	 * @param date
	 * @param year
	 * @return
	 */
	public static java.util.Date getAddYearDate(java.util.Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR,year);
		return calendar.getTime();
	}
	
	/**
	 * 得到当前日期返回值是String 格式如: 2009-09-01
	 * @return
	 */
	public static String getNowDate() {
		Calendar calendar = Calendar.getInstance();
		String NowYear = Integer.toString(calendar.get(Calendar.YEAR));
		String NowMonth = Integer.toString( (calendar.get(Calendar.MONTH) + 1));
		String NowDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String NowDate = NowYear + "-" +
						(NowMonth.length() == 1 ? "0" + NowMonth : NowMonth) + "-" +
						(NowDay.length() == 1 ? "0" + NowDay : NowDay);
		return NowDate;
	}
	
	/**
	 * 得到Date格式的当前日期 格式如：2009-09-01
	 * @return
	 */
	public static Date getNowDateForDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(CommonTool.getNowDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	/**
	 * 得到当前时间 格式如: 2009-09-01 08:15:09
	 * @return
	 */
	public static String getNow2Second() {
		Calendar calendar = Calendar.getInstance();
		String NowYear = Integer.toString(calendar.get(Calendar.YEAR));
		String NowMonth = Integer.toString((calendar.get(Calendar.MONTH) + 1));
		String NowDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String NowHour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
		String NowMinute = Integer.toString(calendar.get(Calendar.MINUTE));
		String NowSecond = Integer.toString(calendar.get(Calendar.SECOND));
		String NowDate = NowYear + "-"
				+ (NowMonth.length() == 1 ? "0" + NowMonth : NowMonth) + "-"
				+ (NowDay.length() == 1 ? "0" + NowDay : NowDay) + " "
				+ (NowHour.length() == 1 ? "0" + NowHour : NowHour) + ":"
				+ (NowMinute.length() == 1 ? "0" + NowMinute : NowMinute) + ":"
				+ (NowSecond.length() == 1 ? "0" + NowSecond : NowSecond);
		return NowDate;
	}
	
	/**
	 * 取得当前时间-到分
	 * 
	 * @return
	 */
	public static String getNow2Minute() {
		return getNow2Second().substring(0, 16);
	}
	
	/**
	 * 比较两个Date日期的大小
	 * 如果第一个参数大于第二个参数返回1 小于否则返回-1 相等返回0 
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static int compareDate(Date dateStart, Date dateEnd) {
		int flag = 0;
		if (dateStart.after(dateEnd)) {
		  flag = 1;
		}else if (dateStart.before(dateEnd)) {
		  flag = -1;
		}else {
		  flag = 0;
		}
		return flag;
	}
	     
	 public static int hasCount(String big,String small) {
		 int ret = 0;
		 int p = 0;
		 while(big.indexOf(small, p)>=0) {
			 ret ++;
			 p = big.indexOf(small, p) + 1;
		 }
		 return ret;
	 }
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(date));
		//Date date2 = CommonTool.getAddDayDate(date, 66);
		Date date2 = CommonTool.getAddMonthDate(date, 10);
		String date2Str = new SimpleDateFormat("yyyy-MM-dd").format(date2);
		System.out.println(date2Str);
//		String temp = ridStrAllSpaces(" Wei ---- Shao   ---  Ying    ---   is Testing !! ");
//		System.out.println(temp);   
//		System.out.println(temp.length()); 
//		System.out.println(CommonTool.getNow2Minute());
//		System.out.println(CommonTool.getNow2Second()); 
//		System.out.println(CommonTool.hasCount("aabcabcabccedgfeababc", "abc")); 
	}
}
