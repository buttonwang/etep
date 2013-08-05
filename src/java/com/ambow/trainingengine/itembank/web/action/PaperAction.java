package com.ambow.trainingengine.itembank.web.action;

import java.io.File;
import java.util.Date;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Paper;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.service.GradeService;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.ItemTypeService;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.service.PaperCategoryService;
import com.ambow.trainingengine.itembank.service.PaperService;
import com.ambow.trainingengine.itembank.service.PaperTypeService;
import com.ambow.trainingengine.itembank.service.ParseItemService;
import com.ambow.trainingengine.itembank.service.RegionService;
import com.ambow.trainingengine.itembank.service.SubjectService;
import com.ambow.trainingengine.itembank.service.UtilForGradeSubjectService;
import com.ambow.trainingengine.itembank.util.PaperStatus;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.ParamObject;
import com.ambow.trainingengine.util.SessionDict;

/*
 * PaperAction.java
 * 
 * Created on 2008-7-14 上午10:13:40
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
public class PaperAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private PaperService paperService;	
	private ItemService itemService;	
	private RegionService regionService;	
	private SubjectService subjectService;	
	private GradeService gradeService;	
	private PaperCategoryService paperCategoryService;	
	private PaperTypeService paperTypeService;	
	private KnowledgePointService knowledgePointService;	
	private ItemTypeService itemTypeService;
	
	//传值参数
	private Paper paper;
	private int pageNo = 1;
	private Integer id = 0;
	private String ids = "";	
	private String itemids = "";
	private String errorInfo = "";
	
	//多对多 题型 知识点 学级 参数
	private String gradeCodes = "";
	private String gradeNames = "";
	private String subjectCodes = "";
	private String subjectNames = "";
	private String knowledgePointCodes = "";
	private String knowledgePointNames = "";
	private String itemTypeCodes = "";
	private String itemTypeNames = "";
	
	//页面查询参数
	private String queryType = "";
	private String queryValue = "";
	private String name = "";
	private String status = "";
	private String minDifficultyValue = "";
	private String maxDifficultyValue = "";
	private String typeCode = "";
	private String categoryCode = "";
	private String subjectCode = "";
	private String gradeCode = "";
	private String regionCode = "";
	private String knowledgePointCode = "";
	private String itemTypeCode = "";
	private ParamObject p;
	// 导入文件信息
	private File file;	
	private String fileContentType;	
	private String fileFileName;	
	private String fullUri;		
	private ParseItemService parseItemService; 
	
	private String to;

	private UtilForGradeSubjectService utilForGradeSubjectService;
	private String grade_code  ;	
	private String subject_code ;
	public void gradeSubjectList_toRequestAttribute(){
		setRequestAttribute("subjectList",  this.utilForGradeSubjectService.find("from Subject" ));
		setRequestAttribute("gradeList",  this.utilForGradeSubjectService.find("from Grade"));
		setRequestAttribute("subject_code",subject_code);
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
	public String list(){
		Page page = null;
		String querystr = "";
		
		//简单查询
		if (queryValue.equals(""))
			page = this.paperService.list(pageNo);
		else if (queryType.equals("1")) //试卷名称
			querystr += " and P.name like " + quote(queryValue, "%");
		else if (queryType.equals("2")) //适用地区
			querystr += " and P.region.name like " + quote(queryValue, "%");
		else if (queryType.equals("3")) //所属学科
			querystr += " and " + quote(queryValue, "") + " in elements(P.subjects) ";
		else if (queryType.equals("4")) //适用学级
			querystr += " and " + quote(queryValue, "") + " in elements(P.grades) ";
		else if (queryType.equals("5")) //试卷分类
			querystr += " and P.paperCategory.name like " + quote(queryValue, "%");
		else if (queryType.equals("6")) //试卷类型		
			querystr += " and P.paperType.name like " + quote(queryValue, "%");
		else if (queryType.equals("7")) //试卷状态
			querystr += " and P.status = " + quote(queryValue, "");
		
		if(subject_code!=null&&!"".equals(subject_code.trim())){
			subjectCode = subject_code ;
		}
		if(grade_code!=null&&!"".equals(grade_code.trim())){
			gradeCode =  grade_code;
		}

		//组合查询
		if (!name.equals("")) //试卷名称
			querystr += " and P.name like " + quote(name, "%");
		if (!status.equals("")) //试卷状态
			querystr += " and P.status = " + status;
		if (!categoryCode.equals("")) //试卷分类
			querystr += " and P.paperCategory.code = " + quote(categoryCode, "");
		if (!typeCode.equals("")) //试卷类型
			querystr += " and P.paperType.code = " + quote(typeCode, "");
		if (!subjectCode.equals("")) //所属学科
			querystr +=  " and " + quote(subjectCode, "") + " in elements(P.subjects) ";
		if (!gradeCode.equals("")) //适用学级
			querystr +=  " and " + quote(gradeCode, "") + " in elements(P.grades) ";
		if (!regionCode.equals("")) //适用地区
			querystr  += " and P.region.code = " + quote(regionCode, "");
		if (!minDifficultyValue.equals("")) //试卷难度-最小
			querystr  += " and P.difficultyValue >= " + getFValue(minDifficultyValue);
		if (!maxDifficultyValue.equals("")) //试卷难度-最大
			querystr  += " and P.difficultyValue <= " + getFValue(maxDifficultyValue);
		
		page = this.paperService.findByConditions(pageNo, querystr);
		
		this.setRequestAttribute("page", page);
		this.setRequestAttribute("region", this.regionService.getAll());
		this.setRequestAttribute("subject", this.subjectService.getAll());
		this.setRequestAttribute("grade", this.gradeService.getAll());
		this.setRequestAttribute("paperCategory", this.paperCategoryService.getAll());
		this.setRequestAttribute("paperType", this.paperTypeService.getAll());
		
		if("selectPaper".equals(to)){
			this.setRequestAttribute("p",p);
			this.setRequestAttribute("pid",p.get("pid"));
			return to;
		}else{
			return "home";
		}
	}
	
	/** 保存对象 */
	public String save(){
		Paper actPaper = null;
		if (this.paper.getAnsweringTime()!=null)
			this.paper.setAnsweringTime(this.paper.getAnsweringTime()*60);
		
		boolean hasSameName = this.paperService.find("from Paper where id<>? and name=?", 
													  this.id, this.paper.getName()).size()>0;
		if (hasSameName) {
			this.errorInfo = "SAMENAME";
			return saveError();			
		}
		
		if (this.paper.getId()==null) {			
			this.paper.setStatus(0);
			this.paper.setCreater(getAuthId());
			this.paper.setCreatedTime(new Date());
		} else {
			actPaper = paperService.get(id);
			actPaper.setName(this.paper.getName());
			actPaper.setDescription(this.paper.getDescription());
			actPaper.setRegion(this.paper.getRegion());
			actPaper.setPaperType(this.paper.getPaperType());
			actPaper.setPaperCategory(this.paper.getPaperCategory());
			actPaper.setDifficultyValue(this.paper.getDifficultyValue());
			actPaper.setAnsweringTime(this.paper.getAnsweringTime());	
			actPaper.setItemsNum(this.paper.getItemsNum());
			actPaper.setBigItemsNum(this.paper.getBigItemsNum());
			actPaper.setTotalScore(this.paper.getTotalScore());
			actPaper.setUpdater(getAuthId());
			actPaper.setUpdatedTime(new Date());
			actPaper.setStatus(this.paper.getStatus());			
			this.paper = actPaper;
		}
		
		try {
			preSave(this.paper);
			this.paperService.save(this.paper);
		} catch (Exception e) {
			this.errorInfo = "保存失败！" + "<br/>" + e.getLocalizedMessage();
			return saveError();
		}
		gradeSubjectList_toRequestAttribute();
		return "redirect";
	}
	
	/** 保存对象--整卷导入 */
	public String saveImport(){	
		
		boolean hasSameName = this.paperService.find("from Paper where id<>? and name=?", 
													  this.id, this.paper.getName()).size()>0;
		if (hasSameName) {
			this.errorInfo = "SAMENAME";
			return saveError("importPaper");			
		}
		
		//导入试题
		ImportItemAction importItemAction = new ImportItemAction();
		importItemAction.file = this.file;
		importItemAction.fileContentType = this.fileContentType;
		importItemAction.fileFileName = this.fileFileName;
		importItemAction.fullUri = this.fullUri;
		importItemAction.setParseItemService(this.parseItemService);
		importItemAction.setItemService(this.itemService);
		
		boolean imp = importItemAction.importPaper();
		if (imp) {
			this.paper.getItems().addAll(importItemAction.getItems());			
		} else {
			this.errorInfo = "导入失败！";
			return saveError("importPaper");			
		}
		
		try {
			this.paper.setStatus(0);
			this.paper.setCreater(getAuthId());
			this.paper.setCreatedTime(new Date());
			if (this.paper.getAnsweringTime()!=null) this.paper.setAnsweringTime(this.paper.getAnsweringTime()*60);						
			this.paperService.calculatePaperParams(this.paper);
			preSave(this.paper);
			this.paperService.save(this.paper);
		} catch (Exception e) {
			this.errorInfo = "保存失败！" + "<br/>" + e.getLocalizedMessage();			
		} finally {
			importItemAction.getParseItemService().clear();
			importItemAction = null;
		}
		
		if (!this.errorInfo.equals("")) return saveError("importPaper");
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据并对其进行浏览*/
	public String show(){
		this.paper = this.paperService.get(id);
		init(this.paper);
		
		this.setRequestAttribute("paper", this.paper);
		
		Page page = this.paperService.findItems(pageNo, this.paper.getId());			
		this.setRequestAttribute("page", page);
		this.setRequestAttribute("subject_code", subject_code);
		
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.paper = this.paperService.get(id);
		if (this.paper==null) this.paper = new Paper();
		init(this.paper);
		this.setRequestAttribute("paper", this.paper);
		gradeSubjectList_toRequestAttribute();
		preListData();
		return INPUT;
	}
	
	/**整卷导入页面*/
	public String importPaper(){
		this.paper = new Paper();
		this.setRequestAttribute("paper", this.paper);				
		
		preListData();
		return "importPaper";
	}
	
	/**保存出错后的错误页面，让用户可以看到错误数据并对其进行修改*/
	public String saveError(){
		init(this.paper);
		this.setRequestAttribute("paper", this.paper);
		preListData();
		return INPUT;
	}
	
	/**保存出错后的错误页面，让用户可以看到错误数据并对其进行修改*/
	public String saveError(String retString){
		init(this.paper);
		this.setRequestAttribute("paper", this.paper);		
		preListData();
		return retString;
	}
	
	/** 显示组卷页面，让用户进行组卷操作*/
	public String assembling(){
		this.paper = this.paperService.get(id);
		init(this.paper);
		this.setRequestAttribute("paper", this.paper);
		this.setRequestAttribute("subject_code",subject_code);
		return "assembling";
	}
	
	/**删除对象*/
	public String delete(){
		this.paperService.removeById(id);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		String[] idArray = ids.split(",");		
		for(String paperid: idArray){
			this.paperService.removeById(new Integer(paperid));
		}

		return "redirect";
	}
	
	/**审核对象*/
	public String verify(){
		Paper actPaper = paperService.get(id);		
		if (actPaper.getStatus()==0) {
			actPaper.setStatus(1);
			actPaper.setVerifier(getAuthId());
			actPaper.setVerifiedTime(new Date());
		
			this.paperService.save(actPaper);
		}
		return "redirect";
	}
	
	/**批量审核对象*/
	public String verifyBatch(){
		String[] idArray = ids.split(",");
		for(String paperid: idArray){
			Paper tmppaper = this.paperService.get(Integer.parseInt(paperid));
			if (tmppaper.getStatus()!=0) continue;	//只有未审核状态的对象才可以审核
			tmppaper.setStatus(1);
			tmppaper.setVerifier(getAuthId());
			tmppaper.setVerifiedTime(new Date());
			this.paperService.save(tmppaper);
		}
		return "redirect";
	}
	
	/**作废对象*/
	public String invalid(){
		Paper actPaper = paperService.get(id);
		actPaper.setStatus(-1);
		actPaper.setUpdater(getAuthId());
		actPaper.setUpdatedTime(new Date());
		
		this.paperService.save(actPaper);
		return "redirect";
	}
	
	/**批量作废对象*/
	public String invalidBatch(){
		String[] idArray = ids.split(",");
		for(String paperid: idArray){
			Paper tmppaper = this.paperService.get(Integer.parseInt(paperid));
			tmppaper.setStatus(-1);
			tmppaper.setUpdater(getAuthId());
			tmppaper.setUpdatedTime(new Date());
			this.paperService.save(tmppaper);
		}
		
		return "redirect";
	}
	
	/**拆卷*/
	public String split(){
		this.paper = this.paperService.get(id);
		
		String[] idArray = itemids.split(",");
		for(String itemid: idArray){			
			for (Item item: this.paper.getItems()){
				if (item.getId().equals(Integer.parseInt(itemid))) {
					this.paper.getItems().remove(item);
					break;
				}
			}
		}
		this.paper.setUpdater(getAuthId());
		this.paper.setUpdatedTime(new Date());
		paperService.calculatePaperParams(this.paper);
		this.paperService.save(this.paper);
		
		this.setRequestAttribute("paper", this.paper);
		Page page = this.paperService.findItems(pageNo, this.paper.getId());			
		this.setRequestAttribute("page", page);
		
		return "view";
	}
	
	/**组卷*/
	public String assemble(){
		assembleing(id);		
		return show();
	}
	
	/**批量组卷 -- 选择多套试卷组卷*/
	public String assembleBatch(){
		String[] paperIdArray = ids.split(",");
		for(String paperId: paperIdArray){
			assembleing(Integer.parseInt(paperId));
		}
		if ((paperIdArray.length>0) && !paperIdArray[0].equals(""))
			this.id = Integer.parseInt(paperIdArray[0]);
		return show();
		//return "redirect";
	}
	
	/**选择试卷 */
	public String choose(){
		list();
		return "choose";
	}
	
	@Override
	public String execute(){
		return SUCCESS;
	}
	
	private String quote(String str, String quoteflag) {
		return "'" + quoteflag + str + quoteflag + "'";
	}
	
	private void assembleing(Integer paperId){
		this.paper = this.paperService.get(paperId);
		
		if (!itemids.equals("")) {
			String[] idArray = itemids.split(",");
			for(String itemid: idArray){							
				Item tmpitem = this.itemService.get(Integer.parseInt(itemid));
				if (tmpitem!=null) {
					if (!this.paper.getItems().contains(tmpitem)) 
						this.paper.getItems().add(tmpitem);
				}
			}
		}
		
		//if (this.paper.getStatus()<2) this.paper.setStatus(3);
		this.paper.setUpdater(getAuthId());
		this.paper.setUpdatedTime(new Date());
		paperService.calculatePaperParams(this.paper);
		this.paperService.save(this.paper);
	}
	
	//读取试题的一些关联表信息
	private void init(Paper paper) {
		for (Grade grade: paper.getGrades()) {
			this.gradeCodes += grade.getCode() + ",";
			this.gradeNames += grade.getName() + ",";
		}
		gradeCodes = gradeCodes.replaceAll(",$", "");
		gradeNames = gradeNames.replaceAll(",$", "");
		
		for (Subject subject: paper.getSubjects()) {
			this.subjectCodes += subject.getCode() + ",";
			this.subjectNames += subject.getName() + ",";
		}
		subjectCodes = subjectCodes.replaceAll(",$", "");
		subjectNames = subjectNames.replaceAll(",$", "");
		
		for (KnowledgePoint knowledgePoint: paper.getKnowledgePoints()) {
			this.knowledgePointCodes += knowledgePoint.getCode() + ",";
			this.knowledgePointNames += knowledgePoint.getName() + ",";
		}
		knowledgePointCodes = knowledgePointCodes.replaceAll(",$", "");
		knowledgePointNames = knowledgePointNames.replaceAll(",$", "");
		
		for (ItemType itemType: paper.getItemTypes()) {
			this.itemTypeCode += itemType.getCode() + ",";
			this.itemTypeNames += itemType.getName() + ",";
		}
		itemTypeCodes = itemTypeCode.replaceAll(",$", "");
		itemTypeNames = itemTypeNames.replaceAll(",$", "");		
	}
	
	//保存之前的准备
	private void preSave(Paper paper) {
		//学级		
		paper.getGrades().clear();
		if ((gradeCodes!=null) && !gradeCodes.equals("")) {
			String[] gradeArray = gradeCodes.split(",");		
			for (int i = 0; i < gradeArray.length; i++) {
				paper.getGrades().add(this.gradeService.get(gradeArray[i]));
			}
		}
		//学科		
		paper.getSubjects().clear();
		if ((subjectCodes!=null) && !subjectCodes.equals("")) {
			String[] subjectArray = subjectCodes.split(",");
			for (int i = 0; i < subjectArray.length; i++) {
				paper.getSubjects().add(this.subjectService.get(subjectArray[i]));
			}
		}
		//知识点
		paper.getKnowledgePoints().clear();
		if ((knowledgePointCodes!=null) && !knowledgePointCodes.equals("")) {
			String[] knowledgePointArray = knowledgePointCodes.split(",");
			for (int i = 0; i < knowledgePointArray.length; i++) {
				paper.getKnowledgePoints().add(this.knowledgePointService.get(knowledgePointArray[i]));
			}
		}
		//题型
		paper.getItemTypes().clear();
		if ((itemTypeCodes!=null) && !itemTypeCodes.equals("")) {
			String[] itemThemeArray = itemTypeCodes.split(",");
			for (int i = 0; i < itemThemeArray.length; i++) {
				paper.getItemTypes().add(this.itemTypeService.get(itemThemeArray[i]));
			}
		}
	}
	
	private void preListData(){
		this.setRequestAttribute("region", this.regionService.getAll());		
		this.setRequestAttribute("paperCategory", this.paperCategoryService.getAll());
		this.setRequestAttribute("paperType", this.paperTypeService.getAll());
		this.setRequestAttribute("paperStatusList", PaperStatus.values());
		this.setRequestAttribute("subjectList", this.subjectService.getAll());		
		this.setRequestAttribute("gradeList", this.gradeService.getAll());
		this.setRequestAttribute("knowledgePointList", this.knowledgePointService.getAll());
		this.setRequestAttribute("itemTypeList", this.itemTypeService.getAll());		
	}
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		if (getSessionObj(SessionDict.AdminUser)==null)
			return null;
		else 
			return ((SysUser)getSessionObj(SessionDict.AdminUser)).getUsername();
	}
	
	public Integer getAuthId() {
		if (getSessionObj(SessionDict.AdminUser)==null)
			return null;
		else 
			return ((SysUser)getSessionObj(SessionDict.AdminUser)).getId();
	}
	
	public PaperService getPaperService() {
		return paperService;
	}

	public void setPaperService(PaperService paperService) {
		this.paperService = paperService;
	}

	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getMinDifficultyValue() {
		return minDifficultyValue;
	}

	public void setMinDifficultyValue(String minDifficultyValue) {
		this.minDifficultyValue = minDifficultyValue;
	}

	public String getMaxDifficultyValue() {
		return maxDifficultyValue;
	}

	public void setMaxDifficultyValue(String maxDifficultyValue) {
		this.maxDifficultyValue = maxDifficultyValue;
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

	public PaperCategoryService getPaperCategoryService() {
		return paperCategoryService;
	}

	public void setPaperCategoryService(PaperCategoryService paperCategoryService) {
		this.paperCategoryService = paperCategoryService;
	}

	public PaperTypeService getPaperTypeService() {
		return paperTypeService;
	}

	public void setPaperTypeService(PaperTypeService paperTypeService) {
		this.paperTypeService = paperTypeService;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}	

	public String getItemids() {
		return itemids;
	}

	public void setItemids(String itemids) {
		this.itemids = itemids;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getKnowledgePointCode() {
		return knowledgePointCode;
	}

	public void setKnowledgePointCode(String knowledgePointCode) {
		this.knowledgePointCode = knowledgePointCode;
	}

	public String getItemTypeCode() {
		return itemTypeCode;
	}

	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}

	public String getGradeCodes() {
		return gradeCodes;
	}

	public void setGradeCodes(String gradeCodes) {
		this.gradeCodes = gradeCodes;
	}

	public String getGradeNames() {
		return gradeNames;
	}

	public void setGradeNames(String gradeNames) {
		this.gradeNames = gradeNames;
	}

	public String getKnowledgePointCodes() {
		return knowledgePointCodes;
	}

	public void setKnowledgePointCodes(String knowledgePointCodes) {
		this.knowledgePointCodes = knowledgePointCodes;
	}

	public String getKnowledgePointNames() {
		return knowledgePointNames;
	}

	public void setKnowledgePointNames(String knowledgePointNames) {
		this.knowledgePointNames = knowledgePointNames;
	}

	public String getItemTypeCodes() {
		return itemTypeCodes;
	}

	public void setItemTypeCodes(String itemTypeCodes) {
		this.itemTypeCodes = itemTypeCodes;
	}

	public String getItemTypeNames() {
		return itemTypeNames;
	}

	public void setItemTypeNames(String itemTypeNames) {
		this.itemTypeNames = itemTypeNames;
	}

	public String getSubjectCodes() {
		return subjectCodes;
	}

	public void setSubjectCodes(String subjectCodes) {
		this.subjectCodes = subjectCodes;
	}

	public String getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFullUri() {
		return fullUri;
	}

	public void setFullUri(String fullUri) {
		this.fullUri = fullUri;
	}
	
	public ParseItemService getParseItemService() {
		return parseItemService;
	}

	public void setParseItemService(ParseItemService parseItemService) {
		this.parseItemService = parseItemService;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public ParamObject getP() {
		return p;
	}

	public void setP(ParamObject p) {
		this.p = p;
	}
	
	public Integer getIValue(String value){
		int r = -100;
		try {
			r = Integer.valueOf(value);
		} catch (Exception e ){			
		}				
		return r;
	}
	
	public Float getFValue(String value){
		float r = -100;
		try {
			r = Float.valueOf(value);
		} catch (Exception e ){
		}				
		return r;
	}
}
