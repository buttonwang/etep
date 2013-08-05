package com.ambow.trainingengine.itembank.web.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemTheme;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.domain.TypicalExample;
import com.ambow.trainingengine.itembank.service.AnswerOptionService;
import com.ambow.trainingengine.itembank.service.GradeService;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.ItemThemeService;
import com.ambow.trainingengine.itembank.service.ItemTypeService;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.service.SubItemService;
import com.ambow.trainingengine.itembank.service.UtilForGradeSubjectService;
import com.ambow.trainingengine.itembank.util.ApplicableObject;
import com.ambow.trainingengine.itembank.util.CommonOptions;
import com.ambow.trainingengine.itembank.util.ItemSource;
import com.ambow.trainingengine.itembank.util.ItemStatus;
import com.ambow.trainingengine.itembank.util.MathUtil;
import com.ambow.trainingengine.itembank.web.data.AnswerOptionVO;
import com.ambow.trainingengine.itembank.web.data.ItemVO;
import com.ambow.trainingengine.itembank.web.data.QueryConditions;
import com.ambow.trainingengine.itembank.web.data.SubItemVO;
import com.ambow.trainingengine.util.ParamObject;

/*
 * ItemAction.java
 * 
 * Created on 2008-7-15 上午09:39:54
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
public class ItemAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private int pageNo = 1;
	private ItemService itemService;
	private ItemTypeService itemTypeService;
	private KnowledgePointService knowledgePointService;
	private ItemThemeService itemThemeService;
	
	private String isEdit="false";
			
	private GradeService gradeService;
	private Item item;
	private ItemVO itemVO;
	private Integer id = 0;
	private String ids = "";
	private String queryType = "";// 一般查询 0, 高级查询 1
	private String queryValue = "";
	private QueryConditions queryConditions = new QueryConditions();

	private String queryClick = "0";
	private Integer paperId = 0;
	private Integer assemble = 0;
	private SubItem subItem;
	private SubItemVO subItemVO;
	private AnswerOption answerOption;
	private SubItemService subItemService;
	private AnswerOptionVO answerOptionVO;
	private AnswerOptionService answerOptionService;
	private UtilForGradeSubjectService utilForGradeSubjectService;
	private String grade_code;
	private String subject_code;
	private String[] reviewRound;

	private String returnUrl;
	
	private String knowledgePoint;
	private String difficulty;
	private String type_code;

	// 显示模块信息
	private String title;
	
	//子题答案 
	private String[] correctAnswer;
	private ParamObject p = new ParamObject() ;


	public void gradeSubjectList_toRequestAttribute() {
		setRequestAttribute("subjectList", this.utilForGradeSubjectService
				.find("from Subject"));
		setRequestAttribute("gradeList", this.utilForGradeSubjectService
				.find("from Grade"));
		setRequestAttribute("subject_code", subject_code);
		setRequestAttribute("queryConditions", queryConditions);
	}

	public UtilForGradeSubjectService getUtilForGradeSubjectService() {
		return utilForGradeSubjectService;
	}

	public void setUtilForGradeSubjectService(
			UtilForGradeSubjectService utilForGradeSubjectService) {
		this.utilForGradeSubjectService = utilForGradeSubjectService;
	}

	public String getGrade_code() {
		return grade_code;
	}

	public void setGrade_code(String grade_code) {
		this.grade_code = grade_code;
	}

	public String getSubject_code() {
		return subject_code;
	}

	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;		
	}

	/** 查看列表 */
	public String list() {
		initQueryCondition();
		Page page = null;
		if (subject_code != null) {
			queryConditions.setSubject(subject_code);
		}
		if (grade_code != null && !"".equals(grade_code.trim())) {
			queryConditions.setGrade(grade_code);
			queryType = "1";
		}
		queryConditions.genHQLandValues(queryType);
		page = this.itemService.findByConditions(pageNo, queryConditions.hql,
				queryConditions.queryConditionList.toArray()); 
		
		setRequestAttribute("statusLst",getItemStatusLst());
		gradeSubjectList_toRequestAttribute();
		this.setRequestAttribute("page", page);

		getListUrl();
		
		return "home";
	}
	
	/**
	 * 转换enum类型ItemStatus 为List<Map<String,Object>> 型数据
	 * @return
	 */
	public List<Map<String,Object>> getItemStatusLst( ){
		List<Map<String,Object>> enumLst   = new ArrayList<Map<String,Object>>(); 
		for (ItemStatus v : ItemStatus.values()) {
			Map<String,Object> vMap = new HashMap<String,Object>(2);
			vMap.put("v",v.toInt());
			vMap.put("n",v.toString());
			enumLst.add(vMap);
		}
		return enumLst;
	}
	public List getValueNameFormEnum( ){
		List lst = new ArrayList();
		ItemStatus [] it = ItemStatus.values();
		for (ItemStatus itemStatus : it) {
			Map map = new HashMap(2);
			map.put("name", itemStatus.toString());
			map.put("value", itemStatus.toInt());
			lst.add(map);
			
		}
		return lst;
	}
	/**
	 * 详细信息列表
	 * @return
	 */
	public String detail(){
		initQueryCondition();
		Page page = null;
		if(subject_code!=null){
			queryConditions.setSubject(subject_code);
			queryType = "1";
		}
		if(grade_code!=null&&!"".equals(grade_code.trim())){
			queryConditions.setGrade(grade_code);
			queryType = "1";
		}
		
		queryConditions.setKnowledgePoint(knowledgePoint);
		queryConditions.setDifficultyValueUp(difficulty);
		if(difficulty.equals("39")){
			queryConditions.setDifficultyValueDown("0");
		}else{
			queryConditions.setDifficultyValueDown((Integer.parseInt(difficulty)-9)+"");
		}
		queryConditions.setItemType(type_code);
		queryConditions.genHQLandValues(queryType);
		page = this.itemService.findByConditions(pageNo, queryConditions.hql, queryConditions.queryConditionList.toArray());
		gradeSubjectList_toRequestAttribute();
		this.setRequestAttribute("page", page);
		return "home";
	}
	
	// 装载查询条件
	public void initQueryCondition() {
		this.setRequestAttribute("regionList", this.itemService
				.getAll(Region.class)); // 地区
		this.setRequestAttribute("subjectList", this.itemService
				.getAll(Subject.class)); // 学科
		this.setRequestAttribute("itemTypeList", this.itemTypeService.findBySubject("P")); // 试题类型
		this.setRequestAttribute("itemSourceList", ItemSource.values()); // 试题来源
		this.setRequestAttribute("gradeList", this.itemService.getAll(
				Grade.class, "parentLevel", true)); // 适用学级
		this.setRequestAttribute("knowledgePointList", this.knowledgePointService.findBySubject(subject_code));
		this.setRequestAttribute("itemThemeList", this.itemThemeService.findBySubject(subject_code)); // 题材
		this.setRequestAttribute("commonOptionsList", CommonOptions.values());// 答案选项
		this.setRequestAttribute("applicableObjectList", ApplicableObject
				.values());// 使用对象
		this.setRequestAttribute("itemStatus", ItemStatus.values());// 状态
		this.setRequestAttribute("itemSource", ItemSource.values());//来源
		this.setRequestAttribute("statusLst",getItemStatusLst());

	}

	/** 保存对象 */
	public String save() {
		if( p.get("type")!=null){
			itemVO.setCorrectAnswer(connectCorrectAnswer());//构造开放性答案
		}
		Item item = this.itemService.itemVOToItem(this.getItemVO(),p);
		if(item.getCreatedTime()==null)
			item.setCreatedTime(new Date());
		item.setUpdatedTime(new Date());
		this.itemService.save(item);
		
		setRequestAttribute("subject_code", subject_code);
		this.setItemVO(this.initItemVO(item)); 
		return "redirect";
	}
	 

	/** 修改页面，让用户可以看到已存在的数据并对其进行修改 */
	public String edit() {
		Item item = this.itemService.get(id);

		this.setItem(item);
		String reviewRound = item.getReviewRound();
		if(reviewRound != null){
			this.setRequestAttribute("selectedReviewRound", reviewRound.split(","));
		}
		this.setItemVO(this.initItemVO(item));
		
		subject_code = item.getSubject().getCode();
		initQueryCondition();
		
		gradeSubjectList_toRequestAttribute();
		
		requestInfo("edit");//获取返回需要的信息
		setRequestAttribute("isEdit",isEdit);
		return "edit";
	}

	public String addAnswer(){
		if(!itemService.addAnswer(p)){
			return "addAnswerError";
		}
		return "addAnswerSuccess";
	}
	
	public void requestInfo(String typename) {
		Item item = this.itemService.get(id);
		
		if( null ==item.getItemType()) return;
		String itemTypeCode = item.getItemType().getCode();
		subject_code = item.getSubject().getCode();
		
		String titledetail="修改";
		
		//构造显示标题
		if(typename.equals("show"))
		{
			titledetail ="详情";
		}
		if ("E".equals(subject_code)) {
			returnUrl = typename + "_item_" + itemTypeCode + ".jsp";

			title = "英语试题"+titledetail;
		} else {
			String code = itemTypeCode.substring(itemTypeCode.length() - 2,
					itemTypeCode.length() - 1);
			isEdit="true";
			returnUrl = typename + "_mpc_" + code + "x.jsp";
			
			if ("M".equals(subject_code)) {
				title = "数学试题"+titledetail;
			} else if ("P".equals(subject_code)) {
				title = "物理试题"+titledetail;
			} else if ("C".equals(subject_code)) {
				title = "化学试题"+titledetail;
			}
		}
		
		this.setRequestAttribute("returnUrl", returnUrl);//返回url
		this.setRequestAttribute("title", title);//显示标题
	}

	/** 显示页面，让用户可以看到已存在的数据并对其进行修改 */
	public String show() {
		Item item = this.itemService.get(id);
		 subject_code = item.getSubject().getCode();
		this.setItem(item);
		this.setItemVO(this.initItemVO(item));

		initQueryCondition();

		requestInfo("show");//获取返回信息
		return "show";
	}

	/** 删除对象 */
	public String delete() {
		Item item = this.itemService.get(id);
		
		subject_code = item.getSubject().getCode();
		getListUrl();//获取返回地址

		this.itemService.removeById(id);

		return "redirect";
	}

	/** 批量删除对象 */
	public String deleteBatch() {
		String[] idArray = ids.replaceAll("'", "").split(",");
		for (String id : idArray) {
			this.itemService.removeById(new Integer(id));
		}
		return "redirect";
	}

	/** 审核对象 */
	public String verify() {
		 item = this.itemService.get(id);
		item.setStatus(1);
		item.setVerifier(1);
		item.setVerifiedTime(new Date());

		this.itemService.save(item);
	
		requestInfo("show");
		return "return";
	}

	/** 批量审核对象 */
	public String verifyBatch() {
		String[] idArray = ids.replaceAll("'", "").split(",");
		for (String id : idArray) {
			Item tmppaper = this.itemService.get(new Integer(id));
			tmppaper.setStatus(1);
			tmppaper.setVerifier(1);
			tmppaper.setVerifiedTime(new Date());
			this.itemService.save(tmppaper);
		}
		return "redirect";
	}

	/** 作废对象 */
	public String invalid() {
		Item item = this.itemService.get(id);
		item.setStatus(-1);
		item.setUpdater(1);
		item.setUpdatedTime(new Date());

		this.itemService.save(item);

		requestInfo("show");

		return "return";
	}

	/** 批量作废对象 */
	public String invalidBatch() {
		String[] idArray = ids.replaceAll("'", "").split(",");
		for (String id : idArray) {
			Item tmppaper = this.itemService.get(new Integer(id));
			tmppaper.setStatus(-1);
			tmppaper.setUpdater(1);
			tmppaper.setUpdatedTime(new Date());
			this.itemService.save(tmppaper);
		}
		return "redirect";
	}

	/** 编辑Item */
	public String editItem() {
		Item tempItem=this.itemService.itemVOToItem(this.itemVO,p);
		tempItem.setUpdatedTime(new Date());
		this.itemService.save(tempItem);
		Item item = this.itemService.get(this.itemVO.getId());
		this.setItem(item);
		return "return";
	}

	/** 添加或更新子题方法 */
	public String saveSubItem() {
		item = this.itemService.get(this.subItemVO.getItemId());
		if (p.getStrArr("newSubItemcorrectAnswers") != null)
			subItemVO.setCorrectAnswer(makeSubItemCorrectAnswer(p.getStrArr("newSubItemcorrectAnswers")));//构造开放性答案
		this.subItemService.save(this.subItemService.subItemVOToSubItem(this.subItemVO, item));
		this.setItem(item);
		return "return";
	}

	private String makeSubItemCorrectAnswer(String[] correctAnswerArr) {
		String newSubItemcorrectAnswer="";
			if (correctAnswerArr != null && correctAnswerArr.length > 0) {
			for (int i = 0; i < correctAnswerArr.length; i++) {
				if (null != correctAnswerArr[i]
						&& !"".equals(correctAnswerArr[i])) {
					newSubItemcorrectAnswer += (MathUtil.removeSpaceAndChinaSpace(correctAnswerArr[i]) + "；");
				}
			}
		}
		return newSubItemcorrectAnswer;
	}

	/** 删除子题方法 */
	public String deleteSubItem(){
		Integer subItemId = this.subItem.getId();
		if(subItemId!=null){
			SubItem subItem =  this.subItemService.get(subItemId);
			this.setItem(subItem.getItem());
			this.subItemService.remove(subItem);
		}
		return "return";
	}

	/** 添加或更新选项方法--有子题 */
	public String saveAnswerOptionHasSubItem() {
		Item item = this.itemService.get(this.getAnswerOptionVO().getItemId());
		SubItem subItem = this.subItemService.get(this.getAnswerOptionVO()
				.getSubItemId());
		this.answerOptionService.save(this.answerOptionService
				.answerOptionVOToAnswerOption(this.getAnswerOptionVO(),
						subItem, item));
		this.setItem(item);
		return "return";
	}

	/** 添加或更新选项方法--无子题 */
	public String saveAnswerOptionNoSubItem() {
		Item item = this.itemService.get(this.getAnswerOptionVO().getItemId());
		this.answerOptionService.save(this.answerOptionService
				.answerOptionVOToAnswerOption(this.getAnswerOptionVO(), null,
						item));
		this.setItem(item);
		return "return";
	}
	/** 增加选项方法 */
	public String addAnswerOption(){
		Integer itemid = this.getAnswerOptionVO().getItemId();
		AnswerOption answerOption = new AnswerOption();
		answerOption.setCode(answerOptionVO.getCode());
		answerOption.setContent( answerOptionVO.getContent());
		answerOption.setTranslation( answerOptionVO.getTranslation());		
		Item item = this.itemService.get(itemid);
		answerOption.setItem(item);
		this.itemService.save(answerOption);
		item = this.itemService.get(itemid);
		return "return";
	}
	/** 删除选项方法 */
	public String deleteAnswerOption() {
		Integer itemid = this.getAnswerOptionVO().getItemId();
		this.answerOptionService.removeById(this.getAnswerOptionVO().getId());
		Item item = this.itemService.get(itemid);
		this.setItem(item);
		return "return";
	}

	/** show 页面中的 审核 */
	public String verifyInShowPage() {
		Item item = this.itemService.get(id);
		item.setStatus(1);
		item.setVerifier(1);
		item.setVerifiedTime(new Date());
		this.itemService.save(item);
		this.setItem(item);
		return "return";
	}

	/** show 页面中的 作废 */
	public String invalidInShowPage() {
		Item item = this.itemService.get(id);
		item.setStatus(-1);
		item.setUpdater(1);
		item.setUpdatedTime(new Date());
		this.itemService.save(item);
		this.setItem(item);
		return "return";
	}
	
	/** show 页面中的 启用 */
	public String activationInShowPage() {
		Item item = this.itemService.get(id);
		item.setStatus(0);
		item.setUpdater(1);
		item.setUpdatedTime(new Date());
		this.itemService.save(item);
		this.setItem(item);
		return "return";
	}

	/** 入卷 */
	public String assemble() {
		return "redirect";
	}
	
	/** 设置为典型例题 */
	public String addTypicalExample() {
		Item item = this.itemService.get(id);
		this.setItem(item);
		
		TypicalExample typicalExample = new TypicalExample();
		typicalExample.setItem(item);
		this.itemService.save(typicalExample);
		
		return "return";
	}
	
	/** 取消设置为典型例题 */
	public String delTypicalExample() {
		Item item = this.itemService.get(id);
		this.setItem(item);
		
		TypicalExample typicalExample = item.getTypicalExample();				
		this.itemService.remove(typicalExample);
		
		return "return";
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public ItemVO initItemVO(Item item) {
		
		Iterator it1 = item.getGrades().iterator();
		int i = 1;
		ItemVO itemVO = new ItemVO();
		itemVO.setAnsweringTimeByMin(item.getAnsweringTimeByMin());
		itemVO.setImportFile(item.getImportFile());
		itemVO.setAnswerGroup(item.getAnswerGroup());
		while (it1.hasNext()) {
			Grade grade = (Grade) it1.next();
			if (i == 1) {
				itemVO.setGradeNames(grade.getName());
				itemVO.setGradeCodes(grade.getCode());
			} else {
				itemVO.setGradeNames(itemVO.getGradeNames() + ","
						+ grade.getName());
				itemVO.setGradeCodes(itemVO.getGradeCodes() + ","
						+ grade.getCode());
			}
			i++;
		}
		Iterator it2 = item.getKnowledgePoints().iterator();
		i = 1;
		while (it2.hasNext()) {
			KnowledgePoint knowledgePoint = (KnowledgePoint) it2.next();
			if (i == 1) {
				itemVO.setKnowledgePointNames(knowledgePoint.getName());
				itemVO.setKnowledgePointCodes(knowledgePoint.getCode());
			} else {
				itemVO.setKnowledgePointNames(itemVO.getKnowledgePointNames()
						+ "," + knowledgePoint.getName());
				itemVO.setKnowledgePointCodes(itemVO.getKnowledgePointCodes()
						+ "," + knowledgePoint.getCode());
			}
			i++;
		}
		Iterator kp2 = item.getKnowledgePoints2().iterator();
		i = 1;
		while (kp2.hasNext()) {
			KnowledgePoint knowledgePoint = (KnowledgePoint) kp2.next();
			if (i == 1) {
				itemVO.setKnowledgePointNames2(knowledgePoint.getName());
				itemVO.setKnowledgePointCodes2(knowledgePoint.getCode());
			} else {
				itemVO.setKnowledgePointNames2(itemVO.getKnowledgePointNames2()
						+ "," + knowledgePoint.getName());
				itemVO.setKnowledgePointCodes2(itemVO.getKnowledgePointCodes2()
						+ "," + knowledgePoint.getCode());
			}
			i++;
		}
		Iterator it3 = item.getItemThemes().iterator();
		i = 1;
		while (it3.hasNext()) {
			ItemTheme itemTheme = (ItemTheme) it3.next();
			if (i == 1) {
				itemVO.setItemThemeNames(itemTheme.getName());
				itemVO.setItemThemeCodes(itemTheme.getCode());
			} else {
				itemVO.setItemThemeNames(itemVO.getItemThemeNames() + ","
						+ itemTheme.getName());
				itemVO.setItemThemeCodes(itemVO.getItemThemeCodes() + ","
						+ itemTheme.getCode());
			}
			i++;
		}
		return itemVO;
	}

	private String quote(String str, String quoteflag) {
		return "'" + quoteflag + str + quoteflag + "'";
	}

	/**
	 * 整合跳转页面信息包括页面显示名称
	 */
	public void getListUrl() {

		if ("E".equals(subject_code)) {

			title = "英语试题查询";
		} else if ("M".equals(subject_code)) {
			title = "数学试题查询";
		} else if ("P".equals(subject_code)) {
			title = "物理试题查询";
		} else if ("C".equals(subject_code)) {
			title = "化学试题查询";
		}
		
		this.setRequestAttribute("title", title);
		this.setRequestAttribute("subject_code", subject_code);
	}
	
	/**
	 * 将开放性答案合并成一个字符串以&&分割
	 * @return　String
	 */
	public String connectCorrectAnswer(){
		String correctAnswers="";
		if (correctAnswer != null&& correctAnswer.length>0) {
			
			for (int i = 0; i < correctAnswer.length; i++) {
				
				if( null != correctAnswer[i] && !"".equals(correctAnswer[i]))
				{
					
				correctAnswers+=(MathUtil.removeSpaceAndChinaSpace(correctAnswer[i])+"；");
				}
			}
			
		}
		return correctAnswers;
		
	}

	public Item getPaper() {
		return item;
	}

	public void setPaper(Item item) {
		this.item = item;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
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

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public QueryConditions getQueryConditions() {
		return queryConditions;
	}

	public void setQueryConditions(QueryConditions queryConditions) {
		this.queryConditions = queryConditions;
	}

	public String getQueryClick() {
		return queryClick;
	}

	public void setQueryClick(String queryClick) {
		this.queryClick = queryClick;
	}

	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public ItemVO getItemVO() {
		return itemVO;
	}

	public void setItemVO(ItemVO itemVO) {
		this.itemVO = itemVO;
	}

	public Integer getAssemble() {
		return assemble;
	}

	public void setAssemble(Integer assemble) {
		this.assemble = assemble;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public SubItem getSubItem() {
		return subItem;
	}

	public void setSubItem(SubItem subItem) {
		this.subItem = subItem;
	}

	public AnswerOption getAnswerOption() {
		return answerOption;
	}

	public void setAnswerOption(AnswerOption answerOption) {
		this.answerOption = answerOption;
	}

	public SubItemVO getSubItemVO() {
		return subItemVO;
	}

	public void setSubItemVO(SubItemVO subItemVO) {
		this.subItemVO = subItemVO;
	}

	public SubItemService getSubItemService() {
		return subItemService;
	}

	public void setSubItemService(SubItemService subItemService) {
		this.subItemService = subItemService;
	}

	public AnswerOptionVO getAnswerOptionVO() {
		return answerOptionVO;
	}

	public void setAnswerOptionVO(AnswerOptionVO answerOptionVO) {
		this.answerOptionVO = answerOptionVO;
	}

	public AnswerOptionService getAnswerOptionService() {
		return answerOptionService;
	}

	public void setAnswerOptionService(AnswerOptionService answerOptionService) {
		this.answerOptionService = answerOptionService;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKnowledgePoint() {
		return knowledgePoint;
	}

	public void setKnowledgePoint(String knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}
	public String[] getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String[] correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public ItemThemeService getItemThemeService() {
		return itemThemeService;
	}

	public void setItemThemeService(ItemThemeService itemThemeService) {
		this.itemThemeService = itemThemeService;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}

	public String[] getReviewRound() {
		return reviewRound;
	}

	public void setReviewRound(String[] reviewRound) {
		String str="";
		for (int i = 0; i < reviewRound.length; i++) {
			str = str + reviewRound[i] + ",";
		}
		str= str.substring(0,str.lastIndexOf(","));
		this.itemVO.setReviewRound(str);
	}

	
	
}
