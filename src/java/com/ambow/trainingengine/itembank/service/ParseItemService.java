package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.util.ItemLable;
import com.ambow.trainingengine.itembank.util.ItemRegx;


public class ParseItemService {

	protected static final Log logger = LogFactory.getLog(ParseItemService.class);
	
	private RegionService regionService;
	private SubjectService subjectService;	
	private GradeService gradeService;
	private ItemThemeService itemThemeService;	
	private KnowledgePointService knowledgePointService;
	
	private String[] parseStr;

	private ArrayList<Item> items = new ArrayList<Item>(0);
	private Item curItem;	
	private SubItem curSubItem;	
	private AnswerOption curAnswerOption;	
	private String[] curItemstr;	
	private String importFileName;
	
	public ParseItemService() {
	}

	public void clear() {
		parseStr = null;
		items.clear();
		curItem = null;
		curSubItem = null;
		curAnswerOption = null;
		curItemstr = null;
		importFileName = "";
	}
	
	public void parse() {
		for(String pstr: parseStr) {
			curItemstr = pstr.split("\\|\\|\\|");		
			parsecurItemstr();
			if (!items.contains(curItem)) this.items.add(curItem);
		}
	}
	
	public String[] getParseStr() {
		return parseStr;
	}

	public void setParseStr(String[] parseStr) {
		this.parseStr = parseStr;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public Item getCurItem() {
		if (curItem==null) curItem = new Item();
		return curItem;
	}

	public void setCurItem(Item curItem) {
		this.curItem = curItem;
	}

	public SubItem getCurSubItem() {
		if (curSubItem==null) curSubItem = new SubItem();
		if (curSubItem.getItem()==null) {
			curSubItem.setItem(curItem);
			if (curItem!=null) curItem.getSubItems().add(curSubItem);
		}
		return curSubItem;
	}

	public void setCurSubItem(SubItem curSubItem) {
		this.curSubItem = curSubItem;
	}

	public AnswerOption getCurAnswerOption() {
		return curAnswerOption;
	}

	public void setCurAnswerOption(AnswerOption curAnswerOption) {
		this.curAnswerOption = curAnswerOption;
	}
	
	public String[] getCurItemstr() {
		return curItemstr;
	}

	public void setCurItemstr(String[] curItemstr) {
		this.curItemstr = curItemstr;
	}
	
	public ItemThemeService getItemThemeService() {
		return itemThemeService;
	}

	public void setItemThemeService(ItemThemeService itemThemeService) {
		this.itemThemeService = itemThemeService;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}
	
	public String getImportFileName() {
		return importFileName;
	}

	public void setImportFileName(String importFileName) {
		this.importFileName = importFileName;
	}
	
	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
		
	private void createItem(){
		if (ItemLable.projectVersion.equals("ky")) {
			if (curItemstr[1].startsWith("子题原始题号")) {		
				setCurSubItem(null);
				getCurSubItem();
			} else if (curItemstr[1].startsWith("试题题型")) {			
				setCurItem(null);
				setCurSubItem(null);
				getCurItem();
			}
		} else if (ItemLable.projectVersion.equals("mpc")) {
			if (curItemstr[1].startsWith("子题")) {	
				setCurSubItem(null);
				getCurSubItem();
			} else if (curItemstr[1].startsWith("试题")) {	
				setCurItem(null);
				setCurSubItem(null);
				getCurItem();
			}
		}
	}
	

	// 全角转半角的函数
	private String ToDBC(String input) {
		if (ItemLable.projectVersion.equals("mpc")) return input;
		char[] c = input.toCharArray();
		for (int i = 0; i< c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i]> 65280&& c[i]< 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
	
	/**
	 * 考研项目47类型的题替换题号
	 * 数理化项目对选择题的答案清除HTML标签，非选择题不清除。
	 * 数理化项目对试题分值的计算
	 */
	private void AfterParseItem() {
		if (ItemLable.projectVersion.equals("ky")) {
			if (curItem.getItemType().getCode().contains("47")) {
				curItem.setContent(ItemRegx.underLine2Html(curItem.getContent()));
			}
		}
		if (ItemLable.projectVersion.equals("mpc")) {
			if (curItem!=null) {
				if ((curItem.getSubItems()!=null)&&(curItem.getSubItems().size()>0)) {
					Float f = 0f;
					Integer t = 0;
					for (SubItem sItem: curItem.getSubItems()) {
						f += (sItem.getScore()==null)?0f:sItem.getScore();
						t += (sItem.getAnsweringTime()==null)?0:sItem.getAnsweringTime();
					}
					curItem.setScore(f);
					curItem.setAnsweringTime(t.intValue());
				} else {
					if ((curItem.getScore()==null)||(curItem.getScore()==0f)) {
						if (curItem.getScore2()!=null&&!curItem.getScore2().equals("")) { 				
							curItem.setScore(getAddScore(curItem.getScore2()));
						}
					}
				}
			}
		}
	}
	
	private void AfterParseSubItem() {
		if (ItemLable.projectVersion.equals("ky")) {		
		}
		if (ItemLable.projectVersion.equals("mpc")) {
			if (curSubItem!=null) {
				if ((curSubItem.getScore()==null)||(curSubItem.getScore()==0f)) {
					if (curSubItem.getScore2()!=null&&!curSubItem.getScore2().equals(""))
						curSubItem.setScore(getAddScore(curSubItem.getScore2()));					
				}
			}
		}
	}
	
	//分值按空计分的总分值 , split后去除空格和中文全角空格
	private Float getAddScore(String score) {
		float f =0f;
		if (score.trim().equals("")) return 0f;
		
		String[] fArray = score.trim().split("；");
		for(String s: fArray) {
			f += Float.valueOf(s.replace("　", "").trim());
		}
		return f;
	}
	// 分钟转化成秒
	private Integer M2S(Float fvalue) {
		if (fvalue==null) fvalue = Float.valueOf(0);
		else fvalue = fvalue*60;
		
		return Math.round(fvalue);
		//return fvalue.intValue();
	}
	
	//清除标签内的HTML标记代码 如 <span> </span> ..  并且把内容中的全角替换为半角
	private String clearItemHtmlSign(String itemstr){
		return ToDBC(itemstr.replaceAll("(<(/||[^>])*>)", "").replace("&quot;", "\"").replace("&nbsp;", " ").trim());
	}
	
	/*
	 * 清除标签内容开头的无效HTML标记 如 </span> </p> ..
	 * 清除标签内容结尾的无效HTML标记如  </p>  <p class=MsoNormal>
	 * 								  </p>  <p class=MsoNormal><span lang=EN-US> 
	 * 								  </p>  <p class=MsoNormal><span style='font-family:宋体'>
	 * 								  </p>  <p class=MsoNormal align=left style='text-align:left'><span lang=EN-US>
	 * 								  </p>  <p class=MsoNormal align=left style='text-align:left'><span style='font-family: 宋体'>
	 * 清除无内容标签内的无效字符（如：详解3）  <p class=MsoNormal align=left style='text-align:left;text-autospace:none; vertical-align:middle'><span style='font-family:宋体'>
	 * 替换标签内容中IMG图片的路径 + projectRescoure Path
	 */	  
	 private String clearInvalidSign(String itemstr){
		String retstr = itemstr.replaceFirst("</span>", "").trim();
		if (retstr.startsWith("</p>")) retstr = retstr.replaceFirst("</p>", "").trim();
		retstr = retstr.replaceFirst("</p>\\s+<p[^>]+>$", "");
		retstr = retstr.replaceFirst("</p>\\s+<p[^>]+><span[^>]+>$", "");
		retstr = retstr.replaceFirst("<p[^>]+><span[^>]+>$", "");
		retstr = retstr.replaceAll("(src=\")(\\S*.files)", "$1" + ItemLable.projectResource + "/" + "$2");
		return ToDBC(retstr);
	}
	
	//取得ITEM的String类型值，并且保留原生的HTML标记
	private String getRawItemSV(String labstr, String itemstr){
		logger.info(labstr + ":" + clearInvalidSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));		
		return clearInvalidSign(itemstr.replace(ItemLable.endSign(labstr), "").trim());
	}
	
	//取得ITEM的String类型值
	private String getItemSV(String labstr, String itemstr){
		logger.info(labstr + ":" + clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));		
		return clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim());
	}
	
	//取得ITEM的String类型值, 若为空则返回默认值
	private String getItemSV(String labstr, String itemstr, String defaultstr){
		logger.info(labstr + ":" + clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));		
		String r = clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim());
		if (r.equals("")) r = defaultstr;
		return r;
	}
	
	//取得ITEM的Float类型值
	private Float getItemFV(String labstr, String itemstr){
		if (itemstr.trim().contains("分钟")||itemstr.trim().contains("秒")) {
			itemstr = itemstr.replace("分钟", "").replace("秒", "");			
		}
		itemstr = itemstr.replace("。", "").replace(".", "");
		logger.info(labstr + ":" + clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));
		try {
			return Float.valueOf(clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(labstr + ":" + "getItemFV exception");
			return Float.valueOf(0);
		}
	}
	
	//取得ITEM的Integer类型值
	private Integer getItemIV(String labstr, String itemstr){
		if (itemstr.trim().contains("分钟")||itemstr.trim().contains("秒")) {
			itemstr = itemstr.replace("分钟", "").replace("秒", "");			
		}
		//if (labstr.endsWith("用时")) timeflag = true;*/
		logger.info(labstr + ":" + clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));
		try {
			return Integer.valueOf(clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));
			//else return Integer.valueOf(clearItemHtmlSign(itemstr.replace(ItemLable.endSign(labstr), "").trim()));
		} catch (Exception e) {
			// TODO: handle exception
			logger.info(labstr + ":" + " getItemIV exception");
			return Integer.valueOf(0);
		}
	}
	
	//解析标签中的地区字段
	private Region parseRegion(String regionstr) {
		logger.info("地区：" + regionstr);
		Region r = regionService.get(regionstr);	
		if (r==null) curItem.setRegionCode2(regionstr);
		return r;
	}
	
	//解析标签中的试题来源
	private String parseSource(String sourcestr) {
		logger.info("试题来源：" + sourcestr);
		String r = sourcestr.replaceFirst("0", "");
		if (r.equals("")) r = "9";
		r = r.substring(0, 1);
		return r;
	}
	
	//解析标签中的知识点字段
	private void parseKPoint(String kpointstr, Object obj) {
		if (ItemLable.projectVersion.equals("ky")) {
			parseKYKPoint(kpointstr, obj);
		}
		if (ItemLable.projectVersion.equals("mpc")) {
			parseMPCKPoint(kpointstr, obj);
		}
	}
	
	//解析考研标签中的知识点字段
	private void parseKYKPoint(String kpointstr, Object obj) {
		if (kpointstr.trim().equals("")) return;
		Set<KnowledgePoint> knowledgepoints = new HashSet<KnowledgePoint>();		
		String[] points = kpointstr.split(";");
		for(String str: points){
			String[] pointcodes = str.split("/");
			String pointcode = "";
			if (pointcodes.length>1) {
				pointcode = pointcodes[1].trim();		
				if (!pointcode.equals("")){
					KnowledgePoint knowledgepoint = knowledgePointService.get(pointcode);
					if (knowledgepoint!=null) knowledgepoints.add(knowledgepoint);
				}
			}
			logger.info("考点：" + pointcode);
		}
		
		if (obj instanceof Item)    ((Item)obj).setKnowledgePoints(knowledgepoints);
		if (obj instanceof SubItem) ((SubItem)obj).setKnowledgePoints(knowledgepoints);		
	}
	
	//解析数理化标签中的知识点字段
	private void parseMPCKPoint(String kpointstr, Object obj) {
		if (kpointstr.trim().equals("")) return;
		Set<KnowledgePoint> knowledgepoints  = new HashSet<KnowledgePoint>();
		Set<KnowledgePoint> knowledgepoints2 = new HashSet<KnowledgePoint>();
	
		kpointstr = kpointstr.replace(";", "；").replace(",", "；").replace("，", "；")
							  .replace(":", "；").replace("：", "；").replace(" ", "");
		kpointstr = kpointstr.replaceAll("\\s", "");
		String[] points = kpointstr.split("；");
		for(String kp: points){
			String curKP = kp.trim().replace("S8", "S4");	
			if (isMainKP(curKP, knowledgepoints)) {
				KnowledgePoint knowledgepoint = knowledgePointService.get(curKP);
				if (knowledgepoint!=null) knowledgepoints.add(knowledgepoint);
				else logger.error(importFileName + " -> 知识点未发现：" + curKP);
				logger.info("知识点：" + curKP);
			} else {
				KnowledgePoint knowledgepoint = knowledgePointService.get(curKP);
				if (knowledgepoint!=null) knowledgepoints2.add(knowledgepoint);
				else logger.error(importFileName + " -> 知识点未发现：" + curKP);
				logger.info("知识点：" + curKP);
			}
		}
		
		if (obj instanceof Item) {
			((Item)obj).setKnowledgePoints(knowledgepoints);
			((Item)obj).setKnowledgePoints2(knowledgepoints2);
		}
		if (obj instanceof SubItem) {
			((SubItem)obj).setKnowledgePoints(knowledgepoints);
			((SubItem)obj).setKnowledgePoints2(knowledgepoints2);
		}

	}
	
	//判断知识点是否主知识点
	private boolean isMainKP(String kp, Set<KnowledgePoint> kpSet) {
		boolean b = true;
		for (KnowledgePoint mkp: kpSet) {
			if (mkp.getCode().substring(1, 5).equalsIgnoreCase(kp.substring(1, 5))) {
				b = false; break;				
			}
		}
		return b;
	}
	
	//解析标签中的题材字段
	private Set<ItemTheme> parseItemTheme(String itemthemestr) {
		Set<ItemTheme> itemthemes = new HashSet<ItemTheme>();
		if (itemthemestr.trim().equals("")) return null;
		String[] themes = itemthemestr.split(";");
		for(String str: themes){
			String[] themecodes = str.split("/");
			String themecode = "";
			if (themecodes.length>1) {
				themecode = themecodes[1].trim();
				if (!themecode.equals("")) {
					ItemTheme itemtheme = itemThemeService.get(themecode);
					if (itemtheme!=null) itemthemes.add(itemtheme);
				}
			}
			logger.info("题材："+ themecode);
		}
		
		return itemthemes;
	}
		
	private boolean startWithLable(String Itemstr, String labstr) {
		return Itemstr.startsWith(ItemLable.endSign(labstr));
	}
	
	private void parsecurItemstr() {
		if (curItemstr.length<2) return;
		createItem();
		logger.info("试题：开始---------------------------------");
		String itemtypecode = "";
		String subjectcode = "";
		String gradecode = "";
		
		for(String pstr: curItemstr) {
			pstr = pstr.trim();
						
			if	(startWithLable(pstr, "试题编码")) curItem.setCode(getItemSV("试题编码", pstr));
			else if (startWithLable(pstr, "地区")) curItem.setRegion(parseRegion(getItemSV("地区", pstr)));
			else if (startWithLable(pstr, "地区代码")) curItem.setRegion(parseRegion(getItemSV("地区代码", pstr)));			
			else if (startWithLable(pstr, "适用地区")) curItem.setRegion(parseRegion(getItemSV("适用地区", pstr)));
			else if	(startWithLable(pstr, "试题题型")) itemtypecode = getItemSV("试题题型", pstr);
			else if	(startWithLable(pstr, "试题类型")) itemtypecode = getItemSV("试题类型", pstr);
			else if	(startWithLable(pstr, "试题年份")) curItem.setYear(getItemSV("试题年份", pstr, ItemLable.curYear));
			else if	(startWithLable(pstr, "试题来源")) curItem.setSource(parseSource(getItemSV("试题来源", pstr)));
			else if	(startWithLable(pstr, "试题来源书目")) curItem.setSourceBook(getItemSV("试题来源书目", pstr));
			else if	(startWithLable(pstr, "试题来源文件")) curItem.setSourceFile(getItemSV("试题来源文件", pstr));
			else if	(startWithLable(pstr, "原始套卷编码")) curItem.setOriginalPaperCode(getItemSV("原始套卷编码", pstr));
			else if	(startWithLable(pstr, "原始题号")) curItem.setOriginalItemNum(getItemSV("原始题号", pstr));
			else if	(startWithLable(pstr, "文章原始号")) curItem.setOriginalItemNum(getItemSV("文章原始号", pstr));			
			else if	(startWithLable(pstr, "试题难度")) curItem.setDifficultyValue(getItemFV("试题难度", pstr));
			else if	(startWithLable(pstr, "试题难易度")) curItem.setDifficultyValue(getItemFV("试题难易度", pstr));
			else if	(startWithLable(pstr, "试题效度")) curItem.setValidityValue(getItemFV("试题效度", pstr));
			else if	(startWithLable(pstr, "直观评价")) curItem.setOpinion(getItemFV("直观评价", pstr));
			else if	(startWithLable(pstr, "价值度")) curItem.setItemValue(getItemFV("价值度", pstr));
			else if	(startWithLable(pstr, "答题用时")) curItem.setAnsweringTime(M2S(getItemFV("答题用时", pstr)));
			else if	(startWithLable(pstr, "做题用时")) curItem.setAnsweringTime(M2S(getItemFV("做题用时", pstr)));
			else if	(startWithLable(pstr, "做题时间")) curItem.setAnsweringTime(M2S(getItemFV("做题时间", pstr)));
			else if	(startWithLable(pstr, "子题做题总用时")) curItem.setAnsweringTime(M2S(getItemFV("子题做题总用时", pstr)));
			else if	(startWithLable(pstr, "阅读用时")) curItem.setReadingTime(M2S(getItemFV("阅读用时", pstr)));
			else if	(startWithLable(pstr, "文章阅读用时")) curItem.setReadingTime(M2S(getItemFV("文章阅读用时", pstr)));
			else if	(startWithLable(pstr, "题干")) curItem.setContent(getRawItemSV("题干", pstr));
			else if	(startWithLable(pstr, "文章内容")) curItem.setContent(getRawItemSV("文章内容", pstr));
			else if	(startWithLable(pstr, "题干译文")) curItem.setContentTranslation(getRawItemSV("题干译文", pstr));
			else if	(startWithLable(pstr, "文章译文")) curItem.setContentTranslation(getRawItemSV("文章译文", pstr));
			else if	(startWithLable(pstr, "题干字数")) curItem.setWordCount(getItemIV("题干字数", pstr));
			else if	(startWithLable(pstr, "文章字数")) curItem.setWordCount(getItemIV("文章字数", pstr));
			else if	(startWithLable(pstr, "答案解析")) curItem.setAnswerAnalysis(getRawItemSV("答案解析", pstr));
			else if	(startWithLable(pstr, "提示")) curItem.setHint(getRawItemSV("提示", pstr));
			else if	(startWithLable(pstr, "详解")) curItem.setAnalysisAtLarge1(getRawItemSV("详解", pstr));
			else if	(startWithLable(pstr, "详解1")) curItem.setAnalysisAtLarge1(getRawItemSV("详解1", pstr));
			else if	(startWithLable(pstr, "详解2")) curItem.setAnalysisAtLarge2(getRawItemSV("详解2", pstr));
			else if	(startWithLable(pstr, "详解3")) curItem.setAnalysisAtLarge3(getRawItemSV("详解3", pstr));
			else if	(startWithLable(pstr, "思路分析")) curItem.setThinkingAnalyse(getRawItemSV("思路分析", pstr));
			else if	(startWithLable(pstr, "做题技巧")) curItem.setSkills(getRawItemSV("做题技巧", pstr));
			else if	(startWithLable(pstr, "文章难句")) curItem.setDifficultSentance(getItemSV("文章难句", pstr));			
			else if	(startWithLable(pstr, "判分关键词")) curItem.setScoringKeywords(getItemSV("判分关键词", pstr));			
			else if	(startWithLable(pstr, "正确答案判分关键词")) curItem.setScoringKeywords(getItemSV("正确答案判分关键词", pstr));
			else if	(startWithLable(pstr, "写作模板")) curItem.setWritingTemplate(getRawItemSV("写作模板", pstr));
			else if	(startWithLable(pstr, "评分标准")) curItem.setScoringNorm(getRawItemSV("评分标准", pstr));
			else if	(startWithLable(pstr, "适用对象")) curItem.setApplicableObject(getItemIV("适用对象", pstr));
			else if	(startWithLable(pstr, "关键词")) curItem.setKeywords(getItemSV("关键词", pstr));
			else if	(startWithLable(pstr, "关键句")) curItem.setKeySentances(getItemSV("关键句", pstr));			
			else if	(startWithLable(pstr, "试题分值")) {
				curItem.setScore(getItemFV("试题分值", pstr));
				curItem.setScore2(getItemSV("试题分值", pstr).replaceAll(";", "；").replaceAll(",", "；").replaceAll("，", "；"));
			}
			
			// add for mpc
			else if	(startWithLable(pstr, "答案原型")) curItem.setAnswerPrototype(getRawItemSV("答案原型", pstr));
			else if	(startWithLable(pstr, "能力要求")) curItem.setAbilityValue(getItemSV("能力要求", pstr));
			else if	(startWithLable(pstr, "方法与技巧")) curItem.setSkills(getItemSV("方法与技巧", pstr));			
			else if	(startWithLable(pstr, "适用版本")) curItem.setCourseVersion(getItemIV("适用版本", pstr));
			else if	(startWithLable(pstr, "复习轮次")) curItem.setReviewRound(getItemSV("复习轮次", pstr));
			else if	(startWithLable(pstr, "正确答案")) {
				if (ItemLable.projectVersion.equals("ky")) curItem.setCorrectAnswer(getItemSV("正确答案", pstr));
				else if (ItemLable.projectVersion.equals("mpc")) curItem.setCorrectAnswer(getItemSV("正确答案", pstr));
			}
			
			else if	(startWithLable(pstr, "知识点")) {
				parseKPoint(getItemSV("知识点", pstr), curItem);				
			}
			else if	(startWithLable(pstr, "试题考点")) {
				parseKPoint(getItemSV("试题考点", pstr), curItem);				
			}
			else if	(startWithLable(pstr, "文章考点")) {
				parseKPoint(getItemSV("文章考点", pstr), curItem);	
			}
			else if	(startWithLable(pstr, "试题题材")) {
				curItem.setItemThemes(parseItemTheme(getItemSV("试题题材", pstr)));
			}
			else if	(startWithLable(pstr, "文章题材")) {
				curItem.setItemThemes(parseItemTheme(getItemSV("文章题材", pstr)));
			}
			
			else if	(startWithLable(pstr, "子题序号")) {
				if (curSubItem==null) getCurSubItem();
				curSubItem.setOrderNum(getItemIV("子题序号", pstr));
			}
			else if	(startWithLable(pstr, "子题原始题号")) {
				if (curSubItem==null) getCurSubItem();
				curSubItem.setOriginalSubItemNum(getItemSV("子题原始题号", pstr));
			}
			// add for mpc
			else if	(startWithLable(pstr, "子题提示")) curSubItem.setHint(getRawItemSV("子题提示", pstr));
			else if	(startWithLable(pstr, "子题答案原型")) curSubItem.setAnswerPrototype(getRawItemSV("子题答案原型", pstr));
			else if	(startWithLable(pstr, "子题详解")) curSubItem.setAnalysisAtLarge1(getRawItemSV("子题详解", pstr));
			else if	(startWithLable(pstr, "子题详解1")) curSubItem.setAnalysisAtLarge1(getRawItemSV("子题详解1", pstr));
			else if	(startWithLable(pstr, "子题详解2")) curSubItem.setAnalysisAtLarge2(getRawItemSV("子题详解2", pstr));
			else if	(startWithLable(pstr, "子题详解3")) curSubItem.setAnalysisAtLarge3(getRawItemSV("子题详解3", pstr));
			else if	(startWithLable(pstr, "子题方法与技巧")) curSubItem.setSkills(getRawItemSV("子题方法与技巧", pstr));			
			else if	(startWithLable(pstr, "子题答题时间")) curSubItem.setAnsweringTime(M2S(getItemFV("子题答题时间", pstr)));		
			else if	(startWithLable(pstr, "子题做题时间")) curSubItem.setAnsweringTime(M2S(getItemFV("子题做题时间", pstr)));		
			else if	(startWithLable(pstr, "子题做题用时")) curSubItem.setAnsweringTime(M2S(getItemFV("子题做题用时", pstr)));		
			//old for ky
			else if	(startWithLable(pstr, "子题难度")) curSubItem.setDifficultyValue(getItemFV("子题难度", pstr));
			else if	(startWithLable(pstr, "子题难易度")) curSubItem.setDifficultyValue(getItemFV("子题难易度", pstr));
			else if	(startWithLable(pstr, "子题题干")) curSubItem.setContent(getRawItemSV("子题题干", pstr));
			else if	(startWithLable(pstr, "子题题干译文")) curSubItem.setContentTranslation(getRawItemSV("子题题干译文", pstr));		
			else if	(startWithLable(pstr, "子题答案解析")) curSubItem.setAnswerAnalysis(getRawItemSV("子题答案解析", pstr));
			else if	(startWithLable(pstr, "子题考点相关处")) curSubItem.setRelatedKeyPoints(getItemSV("子题考点相关处", pstr));
			else if	(startWithLable(pstr, "子题与文章相关处")) curSubItem.setRelatedArticle(getItemSV("子题与文章相关处", pstr));
			else if	(startWithLable(pstr, "子题答案关键词")) curSubItem.setKeyAnswerWords(getItemSV("子题答案关键词", pstr));
			else if	(startWithLable(pstr, "子题答案判分关键词")) curSubItem.setKeyAnswerWords(getItemSV("子题答案判分关键词", pstr));
			else if	(startWithLable(pstr, "子题关键句分析")) curSubItem.setKeySentanceAnalysis(getItemSV("子题关键句分析", pstr));
			else if	(startWithLable(pstr, "子题关键词")) curSubItem.setKeyWords(getItemSV("子题关键词", pstr));
			else if	(startWithLable(pstr, "子题关键句")) curSubItem.setKeySentances(getItemSV("子题关键句", pstr));			
			else if	(startWithLable(pstr, "子题关键句")) curSubItem.setKeySentances(getItemSV("子题关键句", pstr));
			else if	(startWithLable(pstr, "子题分值")) {
				curSubItem.setScore(getItemFV("子题分值", pstr));
				curSubItem.setScore2(getItemSV("子题分值", pstr).replaceAll(";", "；").replaceAll(",", "；").replaceAll("，", "；"));
			}
			
			else if	(startWithLable(pstr, "子题答案")) {
				if (ItemLable.projectVersion.equals("ky")) curSubItem.setCorrectAnswer(getItemSV("子题答案", pstr));
				else if (ItemLable.projectVersion.equals("mpc"))curSubItem.setCorrectAnswer(getItemSV("子题答案", pstr));				
			}
			
			else if	(startWithLable(pstr, "子题考点")) {
				parseKPoint(getItemSV("子题考点", pstr), curSubItem);
			}
			else if	(startWithLable(pstr, "子题知识点")) {
				parseKPoint(getItemSV("子题知识点", pstr), curSubItem);
			}
			
			else if	(startWithLable(pstr, "A:")) {
				AnswerOption aoption = new AnswerOption();
				aoption.setItem(curItem);
				aoption.setSubItem(curSubItem);
				aoption.setCode("A");
				if (ItemLable.projectVersion.equals("ky")) aoption.setContent(getItemSV("A:", pstr));
				else if (ItemLable.projectVersion.equals("mpc")) aoption.setContent(getRawItemSV("A:", pstr));
				if (curSubItem!=null) curSubItem.getAnswerOptions().add(aoption);
				else curItem.getAnswerOptions().add(aoption);
				
				curAnswerOption = aoption;
			}
			else if	(startWithLable(pstr, "[A]")) curAnswerOption.setTranslation(getItemSV("[A]", pstr));
			
			else if	(startWithLable(pstr, "B:")) {
				AnswerOption boption = new AnswerOption();
				boption.setItem(curItem);
				boption.setSubItem(curSubItem);
				boption.setCode("B");
				if (ItemLable.projectVersion.equals("ky")) boption.setContent(getItemSV("B:", pstr));
				else if (ItemLable.projectVersion.equals("mpc")) boption.setContent(getRawItemSV("B:", pstr));
				if (curSubItem!=null) curSubItem.getAnswerOptions().add(boption);
				else curItem.getAnswerOptions().add(boption);
				
				curAnswerOption = boption;
			}
			else if	(startWithLable(pstr, "[B]"))  curAnswerOption.setTranslation(getItemSV("[B]", pstr));
			
			else if	(startWithLable(pstr, "C:")) {
				AnswerOption coption = new AnswerOption();
				coption.setItem(curItem);
				coption.setSubItem(curSubItem);
				coption.setCode("C");
				if (ItemLable.projectVersion.equals("ky")) coption.setContent(getItemSV("C:", pstr));
				else if (ItemLable.projectVersion.equals("mpc")) coption.setContent(getRawItemSV("C:", pstr));
				if (curSubItem!=null) curSubItem.getAnswerOptions().add(coption);
				else curItem.getAnswerOptions().add(coption);
				
				curAnswerOption = coption;
			}
			else if	(startWithLable(pstr, "[C]"))	curAnswerOption.setTranslation(getItemSV("[C]", pstr));
			
			else if	(startWithLable(pstr, "D:"))  {
				AnswerOption doption = new AnswerOption();
				doption.setItem(curItem);
				doption.setSubItem(curSubItem);
				doption.setCode("D");
				if (ItemLable.projectVersion.equals("ky")) doption.setContent(getItemSV("D:", pstr));
				else if (ItemLable.projectVersion.equals("mpc")) doption.setContent(getRawItemSV("D:", pstr));
				if (curSubItem!=null) curSubItem.getAnswerOptions().add(doption);
				else curItem.getAnswerOptions().add(doption);
				
				curAnswerOption = doption;
			}
			else if	(startWithLable(pstr, "[D]")) curAnswerOption.setTranslation(getItemSV("[D]", pstr));
			
			AfterParseSubItem();
		}
		
		if (curItem.getItemType()==null) {
		
			//不在标签中的试题属性
			if (ItemLable.projectVersion.equals("ky")) {
				gradecode = "Z1";
				subjectcode = "E";
			} else if (ItemLable.projectVersion.equals("mpc")) {
				String firstKPCode;
				if (itemtypecode.startsWith("4")) {					
					for (SubItem sItem: curItem.getSubItems()) {
						if ((sItem!=null)&&(sItem.getKnowledgePoints()!=null))
							for (KnowledgePoint kp: sItem.getKnowledgePoints()) {
								if (!curItem.getKnowledgePoints().contains(kp)) 
									curItem.getKnowledgePoints().add(kp);
							}
					}
					
				}
				if ((curItem.getKnowledgePoints()==null)||(curItem.getKnowledgePoints().size()==0)) {				
					logger.error(importFileName +" -> 知识点: 没有 " + " 所属试题题干： " + curItem.getContent());			
				}
				firstKPCode = curItem.getKnowledgePoints().iterator().next().getCode();
				
				gradecode = firstKPCode.substring(0, 2);
				if (gradecode.equals("S8")) gradecode = "S4";
				if (gradecode.equals("J8")) gradecode = "J4";
				subjectcode = firstKPCode.substring(2, 3);
				if (itemtypecode.length()>2) itemtypecode = itemtypecode.substring(0, 2);
			}
			curItem.setSubject(new Subject(subjectcode));
			curItem.setGrades(new HashSet<Grade>(0));
			curItem.getGrades().add(new Grade(gradecode));
			String typeCode = gradecode+subjectcode+itemtypecode;
			logger.info("题型:" + typeCode);
			if (typeCode.length()<5) {
				logger.error(importFileName + " -> 题型: 有错误  " + typeCode + " 所属试题题干： " + curItem.getContent());			
			} else {
				curItem.setItemType(new ItemType(typeCode));
			}
			
			curItem.genItemCode();
			curItem.setCreatedTime(new Date());
			curItem.setCreater(null);
			curItem.setStatus(-2); //刚导入
			curItem.setImportFile(importFileName);
		}
		
		AfterParseItem();
		logger.info("试题：结束---------------------------------");
	}
	
}
