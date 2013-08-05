package com.ambow.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * 处理日期函数
 */
@SuppressWarnings("unchecked")
public class DateCompute {

	public DateCompute() {
	}

	/*
	 * 获得某日期的第n天后的日期
	 */
	public String getAddDay(String t1, int days) {
		int yy = getInputYear(t1);
		int mm = getInputMonth(t1);
		int dd = getInputDay(t1);
		GregorianCalendar d = new GregorianCalendar(yy, mm - 1, dd);
		d.add(5, days);
		yy = d.get(1);
		mm = d.get(2) + 1;
		dd = d.get(5);
		return String.valueOf(yy) + "-" + mm + "-" + dd;
	}

	/*
	 * 获得输入日期在全年中是第几周
	 */
	public int getWeekNo(String t1) {
		int yy = getInputYear(t1);
		int mm = getInputMonth(t1);
		int dd = getInputDay(t1);
		// GregorianCalendar d=new GregorianCalendar(yy,mm-1,dd);
		int r[] = { 0, 0, 3, 3, 6, 1, 4, 6, 2, 5, 0, 3, 5 };
		int c = 0;
		int w = 0;
		yy %= 400;
		if ((yy == 0 || yy % 4 == 0 && yy % 100 != 0) && mm < 3)
			c = 5;
		else
			c = 6;
		w = (yy + yy / 4 - yy / 100 + r[mm] + dd + c) % 7;
		return w;
	}

	/*
	 * 获得输入日期所在的年份
	 */
	public int getInputYear(String t1) {
		int yy = 0;
		if (t1 != null) {
			int point = t1.indexOf("-");
			if (point > 0)
				yy = Integer.parseInt(t1.substring(0, point));
		}
		return yy;
	}

	/*
	 * 获得输入日期所在的月份
	 */
	public int getInputMonth(String t1) {
		int mm = 0;
		if (t1 != null) {
			int point = t1.indexOf("-");
			if (point > 0) {
				t1 = t1.substring(point + 1);
				point = t1.indexOf("-");
				if (point > 0)
					mm = Integer.parseInt(t1.substring(0, point));
			}
		}
		return mm;
	}

	/*
	 * 获得输入日期的天数
	 */
	public int getInputDay(String t1) {
		int dd = 0;
		if (t1 != null) {
			int point = t1.indexOf("-");
			if (point > 0) {
				t1 = t1.substring(point + 1);
				point = t1.indexOf("-");
				if (point > 0) {
					t1 = t1.substring(point + 1);
					point = t1.indexOf(" ");
					if (point > 0)
						dd = Integer.parseInt(t1.substring(0, point));
					else
						dd = Integer.parseInt(t1);
				}
			}
		}
		return dd;
	}

	/*
	 * 获得输入时间的小时时间
	 */
	public int getInputHour(String t1) {
		int h = 0;
		if (t1 != null) {
			int point = t1.indexOf(" ");
			if (point > 0) {
				t1 = t1.substring(point + 1);
				point = t1.indexOf(":");
				if (point > 0)
					h = Integer.parseInt(t1.substring(0, point));
			}
		}
		return h;
	}

	/*
	 * 获得输入时间的分钟时间
	 */
	public int getInputMinute(String t1) {
		int m = 0;
		if (t1 != null) {
			int point = t1.indexOf(" ");
			if (point > 0) {
				t1 = t1.substring(point + 1);
				point = t1.indexOf(":");
				if (point > 0) {
					t1 = t1.substring(point + 1);
					point = t1.indexOf(":");
					if (point > 0)
						m = Integer.parseInt(t1.substring(0, point));
				}
			}
		}
		return m;
	}

	/*
	 * 获得输入的两个日期的间隔天数
	 */
	public int getDays(String t1, String t2) {
		GregorianCalendar d1 = null;
		GregorianCalendar d2 = null;

		int yy = getInputYear(t1);
		int mm = getInputMonth(t1);
		int dd = getInputDay(t1);
		int hour = getInputHour(t1);

		int y = getInputYear(t2);
		int m = getInputMonth(t2);
		int d = getInputDay(t2);
		int h = getInputHour(t2);

		d1 = new GregorianCalendar(yy, mm - 1, dd, hour, 0, 0);
		d2 = new GregorianCalendar(y, m - 1, d, h, 0, 0);

		long n1 = d1.getTime().getTime();
		long n2 = d2.getTime().getTime();

		long n = (n2 - n1) / 86400000;
		return (int) n;
	}

	/*
	 * 获得日期中月，日的汉字串
	 */
	public String getChinese(int i) {
		String[] cn = new String[] { "一", "二", "三", "四", "五", "六", "七", "八",
				"九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九",
				"二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八",
				"二十九", "三十", "三十一" };
		if (i > 0 && i < 32)
			return cn[i - 1];
		else
			return "";
	}

	/*
	 * 返回年月日日期显示
	 */
	public String getDateStr(String d) {
		if (d != null) {
			int yy = getInputYear(d);
			if (yy != 1900) {
				String dd = yy + "-" + getInputMonth(d) + "-" + getInputDay(d);
				return dd;
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	/*
	 * 返回年月日日期显示
	 */
	public String getDateTime(String senddate) {
		if (senddate != null) {
			Calendar date = Calendar.getInstance();
			senddate = getInputYear(senddate) + "-" + getInputMonth(senddate)
					+ "-" + getInputDay(senddate) + " "
					+ String.valueOf(date.get(11)) + ":"
					+ String.valueOf(date.get(12)) + ":00";
		}
		return senddate;
	}

	/*
	 * 返回n年后的该年月日日期显示
	 */
	public String getAddYearDate(String senddate, int n) {
		if (senddate != null) {
			int yy = getInputYear(senddate) + n;
			senddate = yy + "-" + getInputMonth(senddate) + "-"
					+ getInputDay(senddate);
		}
		return senddate;
	}

	/*
	 * 返回年月日日期显示
	 */
	public String getDate(String senddate) {
		if (senddate != null)
			senddate = getInputYear(senddate) + "-" + getInputMonth(senddate)
					+ "-" + getInputDay(senddate);
		else
			senddate = "&nbsp;";

		return senddate;
	}

	@SuppressWarnings("deprecation")
	public Date addSecend(Date senddate, int n) {
		try {
			senddate.setSeconds(senddate.getSeconds() + n);
		} catch (Exception e) {
			return null;
		}

		return senddate;

	}

	 private static int weeks = 0;
	    
	    // 获得当前日期与本周一相差的天数
	    private int getMondayPlus() {
	        Calendar cd = Calendar.getInstance();
	        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
	        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
	        if (dayOfWeek == 1) {
	            return -6;
	        } else {
	            return 2 - dayOfWeek;
	        }
	    }

	    // 获得上周星期一的日期
	    public String getPreviousMonday() {
	        weeks--;
	        int mondayPlus = this.getMondayPlus();
	        GregorianCalendar currentDate = new GregorianCalendar();
	        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
	        Date monday = currentDate.getTime();
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        String preMonday = df.format(monday);
	        return preMonday;
	    }
	    public String getPreviousSunday() {
	        weeks--;
	        int mondayPlus = this.getMondayPlus();
	        GregorianCalendar currentDate = new GregorianCalendar();
	        currentDate.add(GregorianCalendar.DATE, 6+mondayPlus + 7 * weeks);
	        Date monday = currentDate.getTime();
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	        String preMonday = df.format(monday);
	        return preMonday;
	    }
	    String defaultDatePattern = "yyyy-MM-dd";
	    // 获得本周星期一的日期
	    public String getCurrentMonday() {
	        weeks = 0;
	        int mondayPlus = this.getMondayPlus();
	        GregorianCalendar currentDate = new GregorianCalendar();
	        currentDate.add(GregorianCalendar.DATE, mondayPlus);
	        Date monday = currentDate.getTime();
	        SimpleDateFormat df = new SimpleDateFormat(defaultDatePattern);
	        String preMonday = df.format(monday);
	        return preMonday;
	    }

	   public Date getBeforeMonthDay(){
		    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMdd");
	        GregorianCalendar c1 = new GregorianCalendar();
	        c1.add(GregorianCalendar.MONTH,-1);
	        return c1.getTime();
	   }
	public static void main(String[] args) {
	}
}
