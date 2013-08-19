package com.ambow.trainingengine.exam.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;

/*
 * ExamUtil.java
 * 
 * Created on Jul 3, 2008 6:22:59 PM
 * Util类,包括一些分页算法
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * Wang Wei 重构： 业务相关函数移到ViewcontrolProxy 中
 */
public class ExamUtil {

	public static final String itemBreak = "@;@";	//试题分割

	public static final String multiSignBreak = "#;#"; //试题属性分割 ： 标记疑问，答题信息

	public static final String multiAnswerBreak = "@:@"; //一题多空的分割
	
	//*************** 积分相关
	//关注
	public static final String pointCauseForExam = "正常做题，获得积分"; //做题时所获积分
	
	public static final String pointCauseForNoteVote = "笔记被投鲜花"; 
	public static final String pointCauseForNoteShare= "共享学习笔记"; 
	public static final String pointCauseForNoteCancelShare= "取消共享学习笔记"; 
	public static final String pointCauseForNoteDelete= "删除共享学习笔记"; 
	public static final String pointCauseForNoteStar = "笔记被引用为精华"; 
	public static final String pointCauseForNoteCancel = "笔记取消精华标识"; 
	
	
	public static final int pointOfNoteVote  = 5;	// 笔记被投鲜花所获积分
	public static final int pointOfNoteShare = 5;	// 共享学习笔记
	public static final int pointOfNoteCancelShare = -5;	// 取消共享学习笔记
	public static final int pointOfNoteDelete = -5;	// 删除共享学习笔记
	public static final int pointOfNoteStar  = 50;	// 笔记被引用为精华
	public static final int pointOfNoteStarCancel = -50;	// 笔记取消精华标识
	
	//捉虫
	//共有5种情况：1）纠错成功，纠正了一个严重错误（对应500分）；2）纠错成功，纠正了一般错误（对应200分）；
	//3）纠错成功，纠正了细微错误（对应100分）；4）纠错无效，试题无误（20分）；5）恶意纠错（不扣分）。
	public static final String pointCauseForBug1 = "纠正了一个严重错误"; 
	public static final String pointCauseForBug2 = "纠正了一般错误"; 
	public static final String pointCauseForBug3 = "纠正了细微错误"; 
	public static final String pointCauseForBug4 = "纠错无效，试题无误"; 
	public static final String pointCauseForBug5 = "恶意纠错"; 
	
	public static final int pointOfBug1  = 500;	
	public static final int pointOfBug2  = 200;	
	public static final int pointOfBug3  = 100;	
	public static final int pointOfBug4  = 20;	
	public static final int pointOfBug5  = 0;	
	
	//*************** 消息内容
	//关注
	public static final String messageForNoteStar = "管理员回复：你笔记的部分内容引用为精华笔记。作为贡献人，你因此获得50分贡献奖励。"; 
	public static final String messageForNoteCancel = "笔记取消精华标识，扣减50分贡献奖励。"; 
	public static final String messageForNoteVoteFlower = "投鲜花给你。"; 
	public static final String messageForNoteVoteEgg = "扔鸡蛋给你。"; 
	public static final String messageForNoteShare = "共享学习笔记。"; 
	public static final String messageForNoteCancelShare= "取消共享学习笔记。"; 
	public static final String messageForNoteDelete= "删除共享学习笔记。"; 
	
	//捉虫
	public static final String messageForAddBug = "提交一条捉虫记录。";
	public static final String messageForBug1 = "纠错成功，纠正了一个严重错误。"; 
	public static final String messageForBug2 = "纠错成功，纠正了一个一般错误。"; 
	public static final String messageForBug3 = "纠错成功，纠正了一个细微错误。"; 
	public static final String messageForBug4 = "纠错无效，试题无误。"; 
	public static final String messageForBug5 = "恶意纠错。"; 
	
	/*
	 * 根据题型对选出来得题进行排序
	 */
	@SuppressWarnings("unchecked")
	public static void sortList(List list) {
		Collections.sort(list);
	}

	/*
	/*
	 * 处理星级的函数
	 */
	public static int[] getStarArray(Float star) {
		int[] starArr = new int[2];
		String starStr = star.toString();
		int starInt = Integer.parseInt(starStr.substring(0, 1));
		String starHalfStr = starStr.substring(2, 3);
		int starHalf = 0;
		if (!"0".equals(starHalfStr)) {
			starHalf = 1;
		}
		starArr[0] = starInt;
		starArr[1] = starHalf;
		return starArr;
	}

	/*
	 * 一对多的题型,根据题号生成所需的MapKey
	 */
	public static String getMapKey(Item item, SubItem subItem) {
		String key = null;
		if (subItem != null)
			key = item.getId() + "::" + subItem.getId();
		else
			key = item.getId().toString();
		return key;
	}

	public static String getChineseTimeStr(int second) {
		String str = "";
		int totalMin = second / 60;
		if (totalMin * 60 < second)
			totalMin = totalMin + 1;
		int hour = 0;
		int min = 0;
		if (totalMin > 60) {
			min = totalMin % 60;
			hour = (totalMin - min) / 60;
		} else {
			min = totalMin;
		}
		if (hour > 0) {
			str = hour + "小时 ";
		}
		str = str + min + "分钟";
		return str;
	}

	public static String getTimeStr(int timeInt) {
		String str = "";
		int second = timeInt % 60;
		int min = 0;
		if (second == 0) {
			if (timeInt > 60) {
				min = timeInt / 60;
			}
		} else {
			if (timeInt > 60) {
				min = (timeInt - second) / 60;
			}
		}
		if (min > 0) {
			str = min + "'";
		}
		if (second > 0) {
			if (second < 10)
				str = str + "0" + second + "''";
			else
				str = str + second + "''";
		}
		return str;
	}
	
	public static String getTimeStr2(int timeInt) {
		String str = "";
		int second = timeInt % 60;
		int min = 0;
		if (second == 0) {
			if (timeInt > 60) {
				min = timeInt / 60;
			}
		} else {
			if (timeInt > 60) {
				min = (timeInt - second) / 60;
			}
		}
		if (min > 0) {
			str = min + "分";
		}
		if (second > 0) {
			if (second < 10)
				str = str + "0" + second + "秒";
			else
				str = str + second + "秒";
		}
		return str;
	}
	
	/**
	 * 小数点后取两位，四舍五入，如取3位请用1000
	 */
	public static Float roundFloat(Float num) {
		float b = (float) (Math.round(num * 100)) / 100;
		return b;
	}
	
	
	public static int roundInteger(Float num) {
		int b = roundFloat(num).intValue();
		return b;
	}
	
	/**
	 * 凑整。 2.1 = 3
	 */
	public static int ceilInteger(Float num) {
		int b = (int)Math.ceil(num);
		return b;
	}
	
	/*
	 * 全角转半角的函数
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/*
	 * 全角转半角的函数, 且转化为小写
	 */
	public static String ToDBCLower(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c).toLowerCase();
	}
	
	/*
	 * 节点类型名字
	 */
	public static String getTypeCHName(String projectVerson, NodeType nodeType) {
		String name = null;
		if (projectVerson.equalsIgnoreCase("ky")) {
			switch (nodeType) {
			case PRACTICE:
				name = "训练"; break;
			case EXAM:
				name = "测试"; break;
			case GROUP:
				name = "节点组"; break;
			case EVALUATE:
				name = "评测"; break;
			case PHASETEST:
				name = "阶段测试"; break;
			case UNITTEST:
				name = "单元测试"; break;
			case EXTPRACTICE:
				name = "拓展练习"; break;
			}
		} else if (projectVerson.equalsIgnoreCase("mpc")) {
			switch (nodeType) {
			case PRACTICE:
				name = "训练"; break;
			case EXAM:
				name = "测试"; break;
			case GROUP:
				name = "弱项强化"; break;
			case EVALUATE:
				name = "评测"; break;
			case PHASETEST:
				name = "测试"; break;
			case UNITTEST:
				name = "测试"; break;
			case EXTPRACTICE:
				name = "拓展练习"; break;
			}
		}
		return name;
	}
	
	/*
	 * 节点类型名字
	 */
	public static String getTypeCHName(NodeType nodeType) {
		String name = null;		
		switch (nodeType) {
		case PRACTICE:
			name = "训练";
			break;
		case EXAM:
			name = "测试";
			break;
		case GROUP:
			name = "节点组";
			break;
		case EVALUATE:
			name = "评测";
			break;
		case PHASETEST:
			name = "阶段测试";
			break;
		case UNITTEST:
			name = "单元测试";
			break;
		case EXTPRACTICE:
			name = "拓展练习";
			break;
		}
		return name;
	}

	public static String getScopeCHName(Float scope) {		
		String r = "";
		if (scope==null) r = "全部题";
		if (scope==0.5f) r = "半星题";
		if (scope==1f) 	 r = "一星题";
		if (scope==2f)	 r = "二星题";
		if (scope==3f)	 r = "三星题";
		if (scope==4f)	 r = "四星题";
		if (scope==5f)	 r = "五星题";
		if (scope==11f)  r = "未答题";
		if (scope==12f)  r = "错题";
		if (scope==13f)  r = "未答题和错题";
		if (scope==14f)  r = "正确题";
		if (scope==15f)  r = "疑问题";
		if (scope==-1f)  r = "全部题";		
		return r;
	}
	/*
	 * false表示此题未做 check for sub answers
	 */
	public static boolean isSubAnswer(String answer) {
		if (answer.indexOf(ExamUtil.multiAnswerBreak) == -1)
			return false;
		String[] subAnswers = answer.split(multiAnswerBreak);
		boolean flag = false;
		for (String subAnswer : subAnswers) {
			if (subAnswer.trim().length() > 0) {
				flag = true;
				break;
			}
		}

		return flag;
	}
	
	public static boolean hasSubItem(Item item) {
		return item.getSubItems()!=null&&item.getSubItems().size()>0;
	}
	
	// test case: TestPickTypeCode.java
	public static String getResultCode(String projectVerson, String typeCode) {
		String r = "";
		if (projectVerson.equals("ky")) r = typeCode;
		else if (projectVerson.equals("mpc"))
			r = typeCode.replaceAll("([\\S]{3})([\\S]{2})", "MPC$2").replaceAll("(MPC[34])(.)", "$1X");
		
		return r;
	}
	
	// test case: TestPickTypeCode.java
	public static String getResultCode(String typeCode) {
		String r = "";
		if (typeCode.startsWith("Z1")) r = typeCode;
		else if (typeCode.startsWith("J4")||typeCode.startsWith("S4"))
			r = typeCode.replaceAll("([\\S]{3})([\\S]{2})", "MPC$2").replaceAll("(MPC[34])(.)", "$1X");
		
		return r;
	}
	
	
	/**
	 * 是否做过的答案
	 * @param answer
	 * @return true: 已做 false: 未做
	 */
	public static boolean isDoneAnswer(String answer) {
		if (answer == null) return false;
		
		return answer.replaceAll(ExamUtil.multiAnswerBreak, "").replaceAll("<matn></math>", "").trim().equals("")==false;
	}
	
	/**
	 * 
	 * @optionSize 选择题中选项的个数
	 * @return 颠倒后的答案顺序
	 * 
	 */
	public static String randomAnswerOptionCode(Integer optionSize) {
		String output = "";
		Random r = new Random();
		
		StringBuffer buffer = new StringBuffer();
		for (int i=0; i<30; i++) {
			buffer.append(r.nextInt(optionSize));
		}
		CharSequence charSeq = buffer.subSequence(3, 29);
		
		for (int i=0; i<charSeq.length(); i++) {
			if (output.length()==optionSize) break;
			if ((charSeq.charAt(i)==48)&&(!output.contains("A"))) output+="A";
			if ((charSeq.charAt(i)==49)&&(!output.contains("B"))) output+="B";
			if ((charSeq.charAt(i)==50)&&(!output.contains("C"))) output+="C";
			if ((charSeq.charAt(i)==51)&&(!output.contains("D"))) output+="D";
		}
		if (output.length()<optionSize) output="ABCDEFG".substring(0, optionSize) ;
		return output;
	}
	
	public static String getwidgetPage(String resultCode) {
		String widgetPage = "";
		if (resultCode.equals("MPC11")) widgetPage = "widget_single_choose";
		else if (resultCode.equals("MPC12")) widgetPage = "widget_many_choose";
		else if (resultCode.equals("MPC3X")) widgetPage = "widget_single_fill";
		else if (resultCode.equals("MPC4X")) widgetPage = "widget_many_fill";
		else if (resultCode.equals("MPC13")) widgetPage = "widget_true_fill";
		return widgetPage;
	}
}
