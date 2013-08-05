package com.ambow.trainingengine.itembank.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ParseItemFile {
	
	protected static final Log logger = LogFactory.getLog(ParseItemFile.class);
	
	public ParseItemFile() {
	}

	public String read(String fileName) throws IOException {
		FileInputStream fileIn = new FileInputStream(fileName);;
		StringBuffer sb = new StringBuffer();	
		
		//BufferedReader in = new BufferedReader(new FileReader(fileName));
		BufferedReader in = new BufferedReader(new InputStreamReader(fileIn, "GBK"));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s + " "); //增加" " 避免<span 分割不清
		}
		
		in.close();
		fileIn.close();
		return sb.toString();
	}
	
	public String read(FileInputStream fileIn) throws IOException {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = new BufferedReader(new InputStreamReader(fileIn));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		return sb.toString();
	}
	
	public String trimHead(String str) {
		int ind = str.indexOf("</head>");
		return str.substring(ind, str.length());
	}
	
	public String[] splitItems(String filename) throws IOException{				
		return  trimHead(read(filename)).split("/hr/");
	}

	public String[] splitItems(FileInputStream fileIn) throws IOException{				
		return  trimHead(read(fileIn)).split("/hr/");
	}
	
	/** 判断试题中是否有选项的另类方法，以方便在子题中若没有选项的话对[A-Z]标识以保护原始状态 */
	private boolean hasOption(String str) {
		return str.contains("A:")&&str.contains("B:")&&str.contains("C:")&&str.contains("D:")&&
			str.contains("[A]")&&str.contains("[B]")&&str.contains("[C]")&&str.contains("[D]");
	}
	
	/** 试题标签分割之前的处理 */
	private String befoRerepairLables(String repstr) {
		String retString = "";
		if (ItemLable.projectVersion.equalsIgnoreCase("ky"))  retString = beforeRepaikyLables(repstr);
		if (ItemLable.projectVersion.equalsIgnoreCase("mpc")) retString = beforeRepairmpcLables(repstr);
		return retString;
	}
	
	/** 试题标签分割之后的处理 */
	private String afterRepairLables(String repstr) {
		String retString = "";
		if (ItemLable.projectVersion.equalsIgnoreCase("ky"))  retString = afterRepairkyLables(repstr);
		if (ItemLable.projectVersion.equalsIgnoreCase("mpc")) retString = afterRepairmpcLables(repstr);
		return retString;
	}
	
	public String[] splitLables(String[] ss) {
		int i = 0 ;
		for(String itemstr: ss) {
			itemstr = befoRerepairLables(itemstr);
			for(String labstr: ItemLable.itemlables) {
				itemstr = itemstr.replace(ItemLable.Sign(labstr), ItemLable.adornSign(labstr));
			}
			itemstr = afterRepairLables(itemstr);
			ss[i] = itemstr;
			i++;
		}
		return ss;
	}
	
	public String[] parse(String filename) throws IOException {
		return splitLables(splitItems(filename));
	}
	
	public String[] parse(FileInputStream fileIn) throws IOException {
		return splitLables(splitItems(fileIn));
	}
	
	public static void main(String[] args) throws IOException {
		String filename = "aa";
		ParseItemFile p = new ParseItemFile();
		String[] testitems = p.parse(filename);
		
		for(String s: testitems) {
			//System.out.println(s);
		}
	}	
	
	private String beforeRepaikyLables(String repstr) {
		String retstr = repstr;										
		return retstr;
	}
	
	/** 
	 * 1: 对详解1，详解2，详解3的处理 详解</span>1<span style='font-family:宋体'>：</span>  详解</span><span lang=EN-US>3</span><span style='font-family:宋体'>：</span>
	 * 2: 对标签分离的情况处理 试题</SPAN><SPAN style="FONT-FAMILY: 宋体">来源文件：
	 * 3: 题干标签分离的情况  题干</SPAN><SPAN style="FONT-FAMILY: 宋体">：</SPAN>
	 * 4: 题干标签分离的情况2 题</span><span style='font-family:宋体'>干：</span>
	 * 5: 方法与技巧</SPAN><SPAN style="FONT-FAMILY: 宋体">：
	 */
	private String beforeRepairmpcLables(String repstr) {
		String ret = repstr;	
		ret = ret.replaceAll("(详解)(<[^>]*>)*([1-9])(<[^>]*>)*(：)", "$1$3$5");
		ret = ret.replaceAll("(试题)(</[^>]*>)(<[^>]*>)(来源文件：)", "$1$4");
		ret = ret.replaceAll("(题干)(</[^>]*>)(<[^>]*>)(：)", "$1$4");
		ret = ret.replaceAll("(题)(</[^>]*>)(<[^>]*>)(干：)", "$1$4");
		ret = ret.replaceAll("(方法与技巧)(</[^>]*>)(<[^>]*>)(：)", "$1$4");
		logger.debug("Repair 详解" + ret);
		return ret;
	}
	
	/*
	 * 	修正一些替换标记时错误的标记 
	 *  1:  带上标的[A]符号，剔除"||" 分割标识 ; 如 "||[A] </span></sup>" 替换为 "[A] </span></sup>"
	 *  2:  阅读B类 中文章内容和文章译文中带有 [A] 标志的，剔除"||" 分割标识
	 *  3:  阅读B类 中子题中带有 [A] 标志的，剔除"||" 分割标识
	 *  4:  写作题干中有带有 [A] 标志的，剔除"||" 分割标识
	 *  5:  完型填空中子题答案解析带有 [A] 标志的，剔除"||" 分割标识; 要求子题答案解析在答案选项的后面。
	 *  **** 无效****  翻译变形作为选择题处理 2008-7-30 3:  翻译变形(15A) 题干中的 "||A: </span>" 替换为 A: </span> --old 34B 
	 */
	private String afterRepairkyLables(String repstr) {
		String retstr = repstr.replaceAll("(\\|\\|\\|)(\\[[A-Z]\\])(\\s)*(</span></sup>)", "$2$3$4");
		if (retstr.contains("47A")||retstr.contains("47B")||retstr.contains("47C")||retstr.contains("47D")) {
			retstr = retstr.replaceAll("(\\|\\|\\|)(\\[[A-Z]\\])", "$2");
		}
		if (retstr.contains("子题原始题号")) {
			if (!hasOption(retstr)) retstr = retstr.replaceAll("(\\|\\|\\|)(\\[[A-Z]\\])", "$2");
		}
		if (retstr.contains("39A")||retstr.contains("39B")) {
			retstr = retstr.replaceAll("(\\|\\|\\|)(\\[[A-Z]\\])", "$2");
		}
		if (retstr.contains("子题原始题号")) {
			if (hasOption(retstr)) {
				retstr = retstr.replaceAll("(\\|\\|\\|)(\\[[A-Z]\\])", "$2");
				retstr = retstr.replaceFirst("\\[A\\]",  "\\|\\|\\|\\[A\\]");
				retstr = retstr.replaceFirst("\\[B\\]",  "\\|\\|\\|\\[B\\]");
				retstr = retstr.replaceFirst("\\[C\\]",  "\\|\\|\\|\\[C\\]");
				retstr = retstr.replaceFirst("\\[D\\]",  "\\|\\|\\|\\[D\\]");
			}
		}
		//if (retstr.contains("15A")) retstr = retstr.replaceAll("(\\|\\|\\|)([A-Z])", "$2");									
		return retstr;
	}
	
	private String afterRepairmpcLables(String repstr) {
		String retstr = repstr;										
		return retstr;
	}
}
