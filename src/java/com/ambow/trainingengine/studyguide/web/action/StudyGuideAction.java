package com.ambow.trainingengine.studyguide.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelKnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelService;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
import com.ambow.trainingengine.studyguide.domain.StudyGuideItem;
import com.ambow.trainingengine.studyguide.domain.StudyGuideParagraph;
import com.ambow.trainingengine.studyguide.service.StudyGuideService;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.SessionDict;

/**
 * @author gaochao
 *
 */
public class StudyGuideAction extends BaseAction {

	private static final long serialVersionUID = 1415016254750793460L;
	private String subject ;
	private String grade;
	private String point;
	private Integer model;
	/** 知识点编码 */
	private String code;
	private StudyGuide studyGuide;
	private StudyGuideParagraph paragraph;
	private StudyGuideItem item;
	private String type;
	
	private KnowledgePointService knowledgePointService = null;
	private ItemService itemService = null;
	private ModelService modelService;
	private ModelKnowledgePointService modelKnowledgePointService;
	private StudyGuideService studyGuideService;
	private String subjectCode;
	private String gradeCode;
	private int id;
	private String ids;

	public String list() {
		List<StudyGuide> studyGuideList = this.studyGuideService.getStudyGuideBySubjectAndGrade(subjectCode,gradeCode);
		this.setRequestAttribute("studyGuideList", studyGuideList);
		return "list";
	}
	
	public String getInfos() {
		List<StudyGuide> sgList = studyGuideService.getByIds(ids);
		this.setRequestAttribute("sgList", sgList);
		return "infoList";
	}
	
	public String getInfo(){
		StudyGuide sg = studyGuideService.getById(id);
		this.setRequestAttribute("s", sg);
		return "info";
	}
	
	public String get(){
		StudyGuide sg = studyGuideService.getById(id);
		this.setRequestAttribute("studyGuide", sg);
		return "query";
	}
	
	public String add(){
		if(studyGuide.getId()!= null) {
			StudyGuide sg = studyGuideService.getGenService().get(StudyGuide.class,studyGuide.getId());
			this.setRequestAttribute("gradeCode", sg.getGrade().getCode());
		}
		this.setRequestAttribute("sgList", this.studyGuideService.getStudyGuideBySubjectAndGrade(subjectCode, null));
		return "add";
	}
	
	/**删除全部对象*/
	public String deleteAll(){
		this.studyGuideService.deleteAll(subjectCode,gradeCode);
		return "redirect";
	}
	/**
	 * 节点列表
	 */
	public String menu(){
		List<KnowledgePoint> list = this.getKnowledgePointService().list(subject,grade);
		this.setRequestAttribute("list", list);
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		return "menu";
	}
	
	/**
	 * 根据知识点编码查出学习指导
	 * @return
	 */
	public String query(){
		StudyGuide sg = studyGuideService.getGenService().get(StudyGuide.class,studyGuide.getId());
		this.setRequestAttribute("subject", sg.getSubject());
		this.setRequestAttribute("grade", sg.getGrade());
		this.setRequestAttribute("studyGuide", sg);
		return "query";
	}
	
	/**
	 * 修改学习指导之前
	 * @return
	 */
	public String modifyBefore(){
		StudyGuide sg = studyGuideService.getGenService().get(StudyGuide.class,studyGuide.getId());
		this.setRequestAttribute("studyGuide", sg);
		this.setRequestAttribute("sgList", this.studyGuideService.getAllExceptThis(sg));
		return "modifyBefore";
	}
	
	public String delParagraph() {
		this.studyGuideService.deleteParagraph(paragraph.getId());
		return "abandon";
	}
	
	public String delItem() {
		this.studyGuideService.deleteItem(item.getId());
		return "abandon";
	}
	
	/**
	 * 修改学习指导
	 * @return
	 */
	public String save(){
		if("P".equalsIgnoreCase(type)) {
			this.studyGuideService.saveParagraphVo(paragraph);
		}else if("I".equalsIgnoreCase(type)) {
			this.studyGuideService.saveItemVo(item);
		}else {
			SysUser sysUser = (SysUser)this.getSessionObj("adminuser");
			studyGuide.setId(this.studyGuideService.saveVo(studyGuide,sysUser));
		}
		return "abandon";
	}
	
	public String editParagraph() {
		if(paragraph.getId()!= null) {
			paragraph = studyGuideService.getGenService().get(StudyGuideParagraph.class, paragraph.getId());
		}
		this.setRequestAttribute("paragraph", paragraph);
		return "editP";
	}
	
	public String editItem() {
		if(item.getId() != null) {
			item = studyGuideService.getGenService().get(StudyGuideItem.class, item.getId());
		}
		this.setRequestAttribute("item", item);
		return "editI";
	}
	
	
	
	/**
	 * 验证学习指导
	 * @return
	 */
	public String verify(){
		SysUser sysuser = (SysUser)this.getSessionObj(SessionDict.AdminUser);
		if(sysuser != null){
			studyGuideService.verifyStudyGuide(studyGuide.getId(), sysuser);
		}
		return "abandon";
	}
	
	/**
	 * 废弃学习指导
	 * @return
	 */
	public String abandon(){
		SysUser sysuser = (SysUser)this.getSessionObj(SessionDict.AdminUser);
		if(sysuser != null){
			studyGuideService.abandonStudyGuide(studyGuide.getId());
		}
		return "abandon";
	}
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public ModelKnowledgePointService getModelKnowledgePointService() {
		return modelKnowledgePointService;
	}

	public void setModelKnowledgePointService(
			ModelKnowledgePointService modelKnowledgePointService) {
		this.modelKnowledgePointService = modelKnowledgePointService;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public StudyGuideService getStudyGuideService() {
		return studyGuideService;
	}

	public void setStudyGuideService(StudyGuideService studyGuideService) {
		this.studyGuideService = studyGuideService;
	}

	public StudyGuide getStudyGuide() {
		return studyGuide;
	}

	public void setStudyGuide(StudyGuide studyGuide) {
		this.studyGuide = studyGuide;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	public StudyGuideParagraph getParagraph() {
		return paragraph;
	}

	public void setParagraph(StudyGuideParagraph paragraph) {
		this.paragraph = paragraph;
	}

	public StudyGuideItem getItem() {
		return item;
	}

	public void setItem(StudyGuideItem item) {
		this.item = item;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
