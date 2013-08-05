/**
 * 
 */
package com.ambow.trainingengine.itembank.web.action;

import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Model;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelKnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelService;

/**
 * @author yuanjunqi
 *
 */
public class ModelAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private String grade;	
	private String subject;
	private Integer id;
	private String name;
	private String sel;
	
	private KnowledgePointService knowledgePointService;
	private ModelService modelService;
	private ModelKnowledgePointService modelKnowledgePointService;
	
	/**
	 * 显示模块列表
	 */
	public String execute() {
		List<Model> modelList = this.getModelService().queryForList(subject, grade);
		this.getHttpServletRequest().setAttribute("modelList", modelList);
		this.getHttpServletRequest().setAttribute("subject", subject);
		this.getHttpServletRequest().setAttribute("grade", grade);
		return SUCCESS;
	}
	
	/**
	 * 添加的预操作
	 * @return
	 */
	public String preAdd(){
		List<KnowledgePoint> knowledgePointList = this.getKnowledgePointService().list(subject, grade);
		this.getHttpServletRequest().setAttribute("subject", subject);
		this.getHttpServletRequest().setAttribute("grade", grade);
		this.getHttpServletRequest().setAttribute("knowledgePointList", knowledgePointList);
		return "preAdd";
	}
	
	/**
	 * 编辑的预操作
	 * @return
	 */
	public String preEdit(){
		List<KnowledgePoint> knowledgePointList = this.getKnowledgePointService().list(subject, grade);
		//System.out.println("id="+id);
		Model model = null;
		if(id != null){
			model = this.getModelService().queryForObject(id);
		}
		this.getHttpServletRequest().setAttribute("subject", subject);
		this.getHttpServletRequest().setAttribute("grade", grade);
		this.getHttpServletRequest().setAttribute("knowledgePointList", knowledgePointList);
		this.getHttpServletRequest().setAttribute("model", model);
		return "preEdit";
	}
	
	/**
	 * 添加操作
	 * @return
	 */
	public String save(){
		if(sel == null){
			this.getModelService().save(id,name,subject,grade,null);
		}else{
			String[] knowledgePoints = sel.split(",");
			this.getModelService().save(id,name,subject,grade,knowledgePoints);
		}
		return "list";
	}
	
	/**
	 * 删除操作
	 * @return
	 */
	public String del(){
		this.modelKnowledgePointService.delete(id);
		this.getModelService().del(id);
		return "list";
	}
	
	/**
	 * 删除所有
	 * @return
	 */
	public String deleteAll(){
		this.getModelKnowledgePointService().deleteAll();
		this.getModelService().deleteAll();
		return "list";
	}

	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ModelKnowledgePointService getModelKnowledgePointService() {
		return modelKnowledgePointService;
	}

	public void setModelKnowledgePointService(
			ModelKnowledgePointService modelKnowledgePointService) {
		this.modelKnowledgePointService = modelKnowledgePointService;
	}

	public String getSel() {
		return sel;
	}

	public void setSel(String sel) {
		this.sel = sel;
	}

}
