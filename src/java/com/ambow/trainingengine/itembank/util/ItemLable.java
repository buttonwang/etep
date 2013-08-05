package com.ambow.trainingengine.itembank.util;

public class ItemLable {
	
	public static String resourcePath = "/resource/";
	
	public static String projectVersion  = "mpc"; //ky: 考研英语; mpc: 数理化中高考;

	public static String projectResource  = resourcePath + projectVersion;

	public static String curSubjectName  = "英语";
	
	public static String curYear = "2009";
	
	static String itemlablessign = "|||";
	static String itemlablestartsign = ">";
	static String itemlableendsign  = "：";		
	
	static String[] itemlables = {
			"试题标识",
			"地区代码",
			"地区",
			"适用地区",
			"学科代码",
			"试题题型",
			"试题类型",
			"试题编码",
			"试题年份",
			"试题来源",
			"试题来源书目",
			"试题来源文件",
			"原始套卷编码",
			"原始题号",
			"试题分值",
			"试题考点",
			"试题题材",
			"试题难度",
			"试题难易度",
			"试题效度",
			"直观评价",
			"价值度",
			"答题用时",
			"做题用时",
			"做题时间",
			"题干",
			"题干译文",
			"题干字数",
			"阅读用时",
			"正确答案",
			"答案解析",
			"提示",
			"详解",
			"详解1",
			"详解2",
			"详解3",			
			"思路分析",
			"做题技巧",
			"文章难句",
			"正确答案判分关键词",
			"判分关键词",
			"写作模板",
			"评分标准",
			"适用对象",
			"关键词",
			"关键句",

			"文章原始号",
			"文章题材",
			"文章内容",
			"文章译文",
			"文章字数",
			"文章阅读用时",
			"文章难句", 	
			"子题做题总用时",	

			"子题原始题号",
			"子题难度",
			"子题难易度",
			"子题题干",
			"子题题干译文",
			"子题答案",
			"子题答案解析",
			"子题考点相关处",
			"子题与文章相关处",
			"子题答案关键词",
			"子题答案判分关键词",
			"子题关键句分析",
			"子题关键词",
			"子题关键句",
			"子题考点",
			"子题知识点",

			//数理化
			"知识点",
			"答案原型",
			"能力要求",
			"方法与技巧",
			"适用版本",
			"复习轮次",
			
			"子题序号", 
			"子题提示", 
			"子题答案原型",
			"子题详解",
			"子题详解1",
			"子题详解2",
			"子题详解3",
			"子题做题技巧",
			"子题方法与技巧",
			"子题分值",
			"子题答题时间",
			"子题做题用时",
			"子题做题时间",
			
			"A:",
			"B:",
			"C:",
			"D:",

			"[A]",
			"[B]",
			"[C]",
			"[D]"
	};
	
	static public String Sign(String labstr) {
		if (labstr.endsWith(":")||labstr.endsWith("]"))
			return ItemLable.itemlablestartsign + labstr;
		else return ItemLable.itemlablestartsign + labstr + ItemLable.itemlableendsign;
	}
	
	static public String endSign(String labstr) {
		if (labstr.endsWith(":")||labstr.endsWith("]"))
			return labstr;
		else return labstr + ItemLable.itemlableendsign;
	}
	
	static public String adornSign(String labstr) {
		if (labstr.endsWith(":")||labstr.endsWith("]"))
			 return ItemLable.itemlablestartsign + ItemLable.itemlablessign + labstr;
		else return ItemLable.itemlablestartsign + ItemLable.itemlablessign + labstr + ItemLable.itemlableendsign;
	}
	
	static public String clearSign(String labstr) {
		return labstr.replaceAll("\\|\\|\\|", "");
	}
}
