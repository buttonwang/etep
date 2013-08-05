package com.ambow.trainingengine.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ambow.trainingengine.bug.domain.ReplyInfoTemplate;
import com.ambow.trainingengine.bug.web.data.ReplyInfoTemplateVO;

/*********************************
 * @USE: 定义开发中遇到的一些工具方法
 * @FOR: ...
 * @AUTHOR: L.赵亚
 * 
 */
public class UtilAndTool_L {
	public static final String SPLIT_STRING = "[L_split_L]";
	
	public static final String SCRIPT_STR_PREFIX = "<script language=\"javascript\">";
	public static final String SCRIPT_STR_SUFFIX = "</script>";
	public static final String SCRIPT_STR_ALER_PREFIX = "alert('";
	public static final String SCRIPT_STR_ALER_SUFFIX = "');";
	public static final String SCRIPT_STR_CONFIRM_PREFIX = "if(confirm(\"";
	public static final String SCRIPT_STR_CONFIRM_MIDFIX = "\")){";
	public static final String SCRIPT_STR_CONFIRM_SUFFIX = "}";
	
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	
	public static List<ReplyInfoTemplateVO> ritList = null;
	public static final String[] BUG_MESSES = {
		"纠正了一个严重错误", "纠正了一个一般错误", "纠正了一个细微错误", "纠错无效，试题无误", "恶意纠错"
	};
	public static final int[] BUG_POINTS = {
		500, 200, 100, 20, 0
	};
	
	/****************************************
	 * @USE: 检查字符串是否是空
	 * @PARAM: str 要检查的字符串
	 * @RETURN: true 得到的字符串为空，且长度等于零；false 得到的字符串不为空或长度大于零
	 * @FOR: 从前台得到的参数可能是空的，此方法用于检查字符串是不是空
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.02.25.11.24
	 * 
	 */
	public static boolean checkNullOrZero(String str){
		if(str==null||str.length()==0||str.equalsIgnoreCase("null"))
			return true;
		else
			return false;
	}

	/****************************************
	 * @USE: 检查字符串是否不为空
	 * @PARAM: str 要检查的字符串
	 * @RETURN: true 得到的字符串不为空或长度大于零；false 得到的字符串为空，且长度等于零
	 * @FOR: 从前台得到的参数可能是空的，此方法用于检查字符串是否不为空
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.02.25.11.24
	 * 
	 */
	public static boolean checkNotNullOrZero(String str){
		return !checkNullOrZero(str);
	}
	
	/*
	 * 将value包装成js代码，以mess为参数名，保存在request里面
	 * 
	 * author: L
	 */
	public static String getMess(String mess){
		if(mess==null||mess.length()==0) return null;
		return (SCRIPT_STR_PREFIX + mess + SCRIPT_STR_SUFFIX);
	}
	
	/*
	 * 将value包装成alert包围的js代码，以mess为参数名，保存在request里面
	 * 
	 * author: L
	 */
	public static String getMessAlert(String mess){
		if(mess==null||mess.length()==0) return null;
		mess = mess.replace("'", "\\'");
		mess = SCRIPT_STR_ALER_PREFIX + mess + SCRIPT_STR_ALER_SUFFIX;
		mess = SCRIPT_STR_PREFIX + mess + SCRIPT_STR_SUFFIX;
		return mess;
	}
	
	/*
	 * 将value包装成confirm包围的js代码，以mess为参数名，保存在request里面
	 * 
	 * author: L
	 */
	public static String getMessConfirm(String mess, String functionStr){
		if(mess==null||mess.length()==0) return null;
		mess = mess.replace("'", "\\'");
		mess = SCRIPT_STR_CONFIRM_PREFIX + mess + SCRIPT_STR_CONFIRM_MIDFIX + functionStr + SCRIPT_STR_CONFIRM_SUFFIX;
		mess = SCRIPT_STR_PREFIX + mess + SCRIPT_STR_SUFFIX;
		return mess;
	}
	
	/*********************************************
	 * USE: 根据BUG得分，得到对应的得分提示信息
	 * PARAM: point BUG得到
	 * RETURN: 对应得分的BUG信息
	 * FOR: 根据BUG提交的程度轻重的得分，得到对应的得分提示信息
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.11.35
	 * 
	 */
	public static String getBugReplyInfoMess(int point){
		for(int i=0; i<BUG_POINTS.length; i++){
			if(BUG_POINTS[i]==point)
				return BUG_MESSES[i];
		}
		
		return null;
	}
	
	/*********************************************
	 * USE: 根据BUG信息，得到对应的得分
	 * PARAM: repInfo BUG信息
	 * RETURN: 对应得分
	 * FOR: 根据BUG的信息，得到程度轻重的得分
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.13.29
	 * 
	 */
	public static int getBugReplyInfoPoint(String repInfo){
		for(int i=0; i<BUG_MESSES.length; i++){
			if(repInfo.indexOf(BUG_MESSES[i])>-1)
				return BUG_POINTS[i];
		}
		
		return 0;
	}

	/*********************************************
	 * USE: 根据BUG得分，得到对应的得分提示信息
	 * PARAM: point BUG得到
	 * RETURN: 对应得分的BUG信息
	 * FOR: 根据BUG提交的程度轻重的得分，得到对应的得分提示信息
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.30.14.49
	 * 
	 */
	public static int getBugReplyInfoPointAfterInit(String repInfo) {
		if(checkNullOrZero(repInfo)) return 0;
		for(int i=0; i<ritList.size(); i++) {
			ReplyInfoTemplateVO ritvo = ritList.get(i);
			if(repInfo.indexOf(ritvo.getContent())>-1)
				return ritvo.getPoint();
		}
		return 0;
	}

	/*********************************************
	 * USE: 根据BUG得分，得到对应的得分提示信息
	 * PARAM: point BUG得到
	 * RETURN: 对应得分的BUG信息
	 * FOR: 根据BUG提交的程度轻重的得分，得到对应的得分提示信息
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.30.14.49
	 * 
	 */
	public static String getBugReplyInfoPointAfterInit(int point) {
		for(int i=0; i<ritList.size(); i++) {
			ReplyInfoTemplateVO ritvo = ritList.get(i);
			if(ritvo.getPoint()!=null&&ritvo.getPoint().intValue()==point)
				return ritvo.getContent();
		}
		return null;
	}
	
	/*************************************************
	 * USE: 将BUG等级类型的信息初始化到本类中，以便使用
	 * PARAM: ritList 所有的BUG等级类别信息
	 * RETURN: ...
	 * FOR: 才发现，原来，BUG的提出等级信息是可以变的，在页面上有处理的地方，所以，提供此方法，
	 * 		以备以后要求实现对应BUG具体等级的挂钩的功能
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.30.14.44
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static void initReplyInfoTemplate(List ritList){
		if(ritList==null||ritList.size()==0) return ;
		ritList = null;
		ritList = new ArrayList<ReplyInfoTemplateVO>();
		for(int i=0; i<ritList.size(); i++){
			ReplyInfoTemplate rit = (ReplyInfoTemplate)ritList.get(i);
			if(rit==null) continue;
			ReplyInfoTemplateVO ritvo = new ReplyInfoTemplateVO();
			ritvo.copyData(rit);
			ritList.add(ritvo);
		}
	}
	
	/*************************************************
	 * USE: 根据日期得到对应的年月日时分秒格式的字符串信息
	 * PARAM: date 日期
	 * RETURN: 输入日期的年月日时分秒的字符串
	 * FOR: 根据输入的日期，得到它的对应的年月日时分秒的字符串
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.13.33
	 * 
	 */
	public static String formatDate(Date date){
		if(date==null) return null;
		return sdf.format(date);
	}
	
	/*************************************************
	 * USE: 根据年月日时分秒的字符串，得到对应的日期
	 * PARAM: dateString 年月日时分秒的字符串
	 * RETURN: 输入的字符串的对应日期
	 * FOR: 根据输入的年月日时分秒的字符串，得到对应的日期
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.04.29.13.35
	 * 
	 */
	public static Date parseDate(String dateStr){
		if(checkNullOrZero(dateStr)) return null;
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
		}
		return null;
	}
}
