package com.ambow.trainingengine.wordbank.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.wordbank.domain.ChineseMeaning;
import com.ambow.trainingengine.wordbank.domain.ExampleSentence;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.domain.WordExtension;
import com.ambow.trainingengine.wordbank.domain.WordType;
import com.ambow.trainingengine.wordbank.service.WordBasicService;
import com.ambow.trainingengine.wordbank.service.WordExtensionService;
import com.ambow.trainingengine.wordbank.util.WordCategory;
import com.ambow.trainingengine.wordbank.util.WordLevel;

/*
 * WordExtensionAction.java
 * 
 * Created on 2008-7-22 下午04:32:36
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class WordExtensionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private WordBasicService wordBasicService;
	private WordExtensionService wordExtensionService;
	private WordExtension wordExtension;
	private WordType wordType;
	private ChineseMeaning chineseMeaning;
	private ExampleSentence exampleSentence;
	
	private Integer id = 0;
	private String ids = "";
	private String queryValue = "";
	private String gradeCode = "";
	private int pageNo = 1;
	private int wordTypeId = 0;
	private int chineseMeaningId = 0;
	private int exampleSentenceId = 0;
	private String errorInfo = "";

	private void saveWordPO() {
		if (this.wordExtension.getId()!=null){
			WordExtension actWordExtension = this.wordExtensionService.get(this.wordExtension.getId());
			actWordExtension.setGrade(this.wordExtension.getGrade());
			actWordExtension.setWordCategory(this.wordExtension.getWordCategory());
			actWordExtension.setWordLevel(this.wordExtension.getWordLevel());
			
			if (this.wordExtension.getWordBasic()!=null) this.wordExtensionService.save(this.wordExtension.getWordBasic());
			this.wordExtensionService.save(actWordExtension);
			this.gradeCode = actWordExtension.getGrade().getCode();
			this.id = actWordExtension.getId();
		} else {
			if (!this.wordBasicService.hasSameName(this.wordExtension.getWordBasic())) {
				this.wordBasicService.save(this.wordExtension.getWordBasic());
			} else {
				this.wordExtension.setWordBasic(this.wordBasicService.findBy("word", this.wordExtension.getWordBasic().getWord()).get(0));
			}
			this.wordExtensionService.save(this.wordExtension);
			if (this.wordExtension.getGrade()!=null) gradeCode = this.wordExtension.getGrade().getCode();	
		}
	}
	
	private void saveWordTypePO() {
		this.wordExtension = this.wordExtensionService.get(this.wordExtension.getId());
		if (this.wordType.getId()!=null){
			WordType actWordType = this.wordExtensionService.get(WordType.class, this.wordType.getId());
			actWordType.setCode(this.wordType.getCode());
			actWordType.setName(this.wordType.getName());
			actWordType.setHasDifferentPronunciations(this.wordType.getHasDifferentPronunciations());		
			actWordType.setPhoneticSymbol(this.wordType.getPhoneticSymbol());
			this.wordExtensionService.save(actWordType);
		} else {
			this.wordType.setWordExtension(this.wordExtension);
			this.wordExtension.getWordTypes().add(this.wordType);
			this.wordExtensionService.save(this.wordExtension);
			this.wordExtensionService.save(this.wordType);
		}
		this.id = wordExtension.getId();
	}
	
	private void saveChineseMeaningPO() {
		//this.wordExtension = this.wordExtensionService.get(this.wordExtension.getId());
		this.wordType = this.wordExtensionService.get(WordType.class, this.wordType.getId());
		if (this.chineseMeaning.getId()!=null){
			ChineseMeaning actChineseMeaning = this.wordExtensionService.get(ChineseMeaning.class, this.chineseMeaning.getId());
			actChineseMeaning.setMeaning(this.chineseMeaning.getMeaning());
			actChineseMeaning.setSynonym(this.chineseMeaning.getSynonym());
			actChineseMeaning.setConfusableWord(this.chineseMeaning.getConfusableWord());
			actChineseMeaning.setConfusableWordAnalysis(this.chineseMeaning.getConfusableWordAnalysis()); 			
			this.wordExtensionService.save(actChineseMeaning);
		} else {
			this.chineseMeaning.setWordType(this.wordType);
			this.wordType.getChineseMeanings().add(this.chineseMeaning);			
			this.wordExtensionService.save(this.wordType);
			this.wordExtensionService.save(this.chineseMeaning);
		}
		this.id = this.wordExtension.getId();
	}
	
	private void saveExampleSentencePO() {
		//this.wordExtension = this.wordExtensionService.get(this.wordExtension.getId());
		this.chineseMeaning = this.wordExtensionService.get(ChineseMeaning.class, this.chineseMeaning.getId());
		if (this.exampleSentence.getId()!=null){
			ExampleSentence actExampleSentence = this.wordExtensionService.get(ExampleSentence.class, this.exampleSentence.getId());
			actExampleSentence.setContent(this.exampleSentence.getContent());
			actExampleSentence.setTranslation(this.exampleSentence.getTranslation());
			actExampleSentence.setSource(this.exampleSentence.getSource());			
			this.wordExtensionService.save(actExampleSentence);
		} else {
			this.exampleSentence.setChineseMeaning(this.chineseMeaning);
			this.chineseMeaning.getExampleSentences().add(this.exampleSentence);			
			this.wordExtensionService.save(this.chineseMeaning);	
			this.wordExtensionService.save(this.exampleSentence);	
		}
		this.id = this.wordExtension.getId();
	}
	
	/** 查看列表 */
	public String list(){
		Page page = null;
		if (queryValue.equals("")) {
			if (gradeCode.equals("")) page = this.wordExtensionService.list(pageNo);
			else page = this.wordExtensionService.findByGrade(pageNo, gradeCode);
		} else {
			String queryStr =" and W.wordBasic.word like '%" + queryValue.trim() + "%' ";
			if (!gradeCode.equals("")) queryStr += " and W.grade.code='" + gradeCode + "' ";	
			 
			page  = this.wordExtensionService.findByConditions(pageNo, queryStr);
		}
		
		this.setRequestAttribute("page", page);
		return "home";
	}
	
	/** 保存对象 */
	public String save(){
		if (wordExtension.getWordBasic()!=null) {
			WordBasic tmpWordBasic = this.wordBasicService.findBy("word", this.wordExtension.getWordBasic().getWord()).get(0);
			for (WordExtension tmpWordExtension: tmpWordBasic.getWordExtensions()) {
				if (tmpWordExtension.getGrade().getCode().equals(this.wordExtension.getGrade().getCode())) {
					errorInfo = "此单词扩展已经存在，请不要重复添加。";
					saveError();
					return "add";
				}
			}
		}
		
		saveWordPO();
		return "redirect";
	}
	
	/**保存出错后的错误页面，让用户可以看到错误数据并对其进行修改*/
	public void saveError(){
		this.setRequestAttribute("wordExtension", this.wordExtension);		
		this.setRequestAttribute("gradeList", this.wordExtensionService.getAll(Grade.class, "parentLevel", true));				
		this.setRequestAttribute("wordCategoryList", WordCategory.values());
		this.setRequestAttribute("wordLevelList", WordLevel.values());				
	}
	
	/** 显示浏览页面，让用户可以看到已存在的数据 -- 并对子项进行维护 */
	public String show(){
		this.wordExtension = this.wordExtensionService.get(id);
		this.setRequestAttribute("wordExtension", this.wordExtension);
		this.setRequestAttribute("wordType", this.wordType);
		this.setRequestAttribute("chineseMeaning", this.chineseMeaning);
		this.setRequestAttribute("exampleSentence", this.exampleSentence);
		
		this.setRequestAttribute("gradeList", this.wordExtensionService.getAll(Grade.class, "parentLevel", true));		
		this.setRequestAttribute("wordCategoryList", WordCategory.values());
		this.setRequestAttribute("wordLevelList", WordLevel.values());
		
		return "view";
	}
	
	/**显示修改页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.wordExtension = this.wordExtensionService.get(id);
		this.setRequestAttribute("wordExtension", this.wordExtension);
		
		this.setRequestAttribute("gradeList", this.wordExtensionService.getAll(Grade.class, "parentLevel", true));			
		this.setRequestAttribute("wordCategoryList", WordCategory.values());
		this.setRequestAttribute("wordLevelList", WordLevel.values());		
		return INPUT;
	}
	
	/**显示新增页面，让用户可以看到已存在的数据并对其进行修改*/
	public String add(){
		this.wordExtension = new WordExtension();
		WordBasic wordbasic = new WordBasic();
		this.wordExtension.setWordBasic(wordbasic);
		wordbasic.getWordExtensions().add(this.wordExtension);
		
		this.setRequestAttribute("wordExtension", this.wordExtension);
		
		this.setRequestAttribute("gradeList", this.wordExtensionService.getAll(Grade.class, "parentLevel", true));		
		this.setRequestAttribute("wordCategoryList", WordCategory.values());
		this.setRequestAttribute("wordLevelList", WordLevel.values());
				
		return "add";
	}
	
	/**删除对象*/
	public String delete(){
		this.wordExtensionService.removeById(id);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		String[] idArray = ids.split(",");
		for(String id: idArray){
			this.wordExtensionService.removeById(Integer.parseInt(id));
		}
		
		return "redirect";
	}
		 	
	/**朗读单词*/
	public String readAloud(){
		return SUCCESS;
	}
	
	/**保存单词*/
	public String saveWord(){
		saveWordPO();
		return "review";
	}
	
	/**保存词性*/
	public String  saveWordType(){
		saveWordTypePO();
		return "review";
	}
	
	/**保存中文释义*/
	public String  saveChineseMeaning(){
		saveChineseMeaningPO();
		return "review";
	}
	
	/**保存中文例句*/
	public String  saveExampleSentence(){
		saveExampleSentencePO();
		return "review";
	}
	
	/**删除单词*/
	public String  deleteWord(){
		this.wordExtensionService.removeById(id);
		return "review";
	}
	
	/**删除词性*/
	public String  deleteWordType(){
		this.wordExtensionService.removeById(WordType.class, this.wordTypeId);
		return "review";
	}
	
	/**删除中文释义*/
	public String  deleteChineseMeaning(){
		this.wordExtensionService.removeById(ChineseMeaning.class, this.chineseMeaningId);
		return "review";
	}
	
	/**删除中文例句*/
	public String  deleteExampleSentence(){
		this.wordExtensionService.removeById(ExampleSentence.class, this.exampleSentenceId);
		return "review";
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public WordBasicService getWordBasicService() {
		return wordBasicService;
	}

	public void setWordBasicService(WordBasicService wordBasicService) {
		this.wordBasicService = wordBasicService;
	}
	
	public WordExtension getwordExtension() {
		return wordExtension;
	}

	public void setwordExtension(WordExtension wordExtension) {
		this.wordExtension = wordExtension;
	}

	public WordExtensionService getwordExtensionService() {
		return wordExtensionService;
	}

	public void setwordExtensionService(WordExtensionService wordExtensionService) {
		this.wordExtensionService = wordExtensionService;
	}
	
	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public WordExtension getWordExtension() {
		return wordExtension;
	}

	public void setWordExtension(WordExtension wordExtension) {
		this.wordExtension = wordExtension;
	}

	public WordType getWordType() {
		return wordType;
	}

	public void setWordType(WordType wordType) {
		this.wordType = wordType;
	}

	public ChineseMeaning getChineseMeaning() {
		return chineseMeaning;
	}

	public void setChineseMeaning(ChineseMeaning chineseMeaning) {
		this.chineseMeaning = chineseMeaning;
	}

	public ExampleSentence getExampleSentence() {
		return exampleSentence;
	}

	public void setExampleSentence(ExampleSentence exampleSentence) {
		this.exampleSentence = exampleSentence;
	}

	public WordExtensionService getWordExtensionService() {
		return wordExtensionService;
	}

	public void setWordExtensionService(WordExtensionService wordExtensionService) {
		this.wordExtensionService = wordExtensionService;
	}

	public int getWordTypeId() {
		return wordTypeId;
	}

	public void setWordTypeId(int wordTypeId) {
		this.wordTypeId = wordTypeId;
	}

	public int getChineseMeaningId() {
		return chineseMeaningId;
	}

	public void setChineseMeaningId(int chineseMeaningId) {
		this.chineseMeaningId = chineseMeaningId;
	}

	public int getExampleSentenceId() {
		return exampleSentenceId;
	}

	public void setExampleSentenceId(int exampleSentenceId) {
		this.exampleSentenceId = exampleSentenceId;
	}
	
	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

}
