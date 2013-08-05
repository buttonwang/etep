/* 
 * StudyGuideService.java
 * 
 * Created on 2009-8-3
 * 
 * Copyright(C) 2009, by ambow Develope & Research Branch.
 * 
 * Original Author: gaochao
 * Contributor(s):
 * 
 * Changes 
 * -------
 * $Log$
 */
package com.ambow.trainingengine.studyguide.service;

import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
import com.ambow.trainingengine.studyguide.domain.StudyGuideItem;
import com.ambow.trainingengine.studyguide.domain.StudyGuideParagraph;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
 
public class StudyGuideService {
	
	private HibernateGenericDao genService;

	
	/**
	 * 根据知识点得到学习指导
	 * @param code
	 * @return
	 */
	public StudyGuide getStudyGuideByKnowledge(String code){
		return genService.findUniqueBy(StudyGuide.class, "kpCode", code);
	}
	
	/**
	 * 根据知识点得到学习指导
	 * @param code
	 * @return
	 */
	public StudyGuide getStudyGuideByCode(String code){
		return genService.findUniqueBy(StudyGuide.class, "code", code);
	}
	
	public void save(StudyGuide sg){
		genService.save(sg);
	}
	
	/**
	 * 验证学习指导
	 * @param studyGuideId
	 * @param user
	 */
	public void verifyStudyGuide(Integer studyGuideId,SysUser user){
		StudyGuide sg = genService.get(StudyGuide.class,studyGuideId);
		sg.setVerifiedTime(new Date());
		sg.setVerifier(user);
		sg.setStatus(1);
		genService.save(sg);
	}
	
	/**
	 * 废弃学习指导
	 * @param studyGuideId
	 */
	public void abandonStudyGuide(Integer studyGuideId){
		StudyGuide sg = genService.get(StudyGuide.class,studyGuideId);
		sg.setStatus(-1);
		genService.save(sg);
	}
	
	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public List<StudyGuide> getStudyGuideBySubjectAndGrade(String subjectCode,String gradeCode) {
		String hql = "from StudyGuide sg where sg.subject.code = '"+subjectCode+"'";
		if(gradeCode!=null && !"".equals(gradeCode.trim())) {
			hql += " and sg.grade.code = '"+gradeCode+"'";
		}
		hql += " order by sg.code";
		return this.genService.find(hql);
	}

	public StudyGuide getById(int id) {
		return this.genService.get(StudyGuide.class, id);
	}
	
	public List<StudyGuide> getByIds(String ids) {
		return this.genService.find("from StudyGuide where id in (" + ids + ") order by code");
	}
	
	public void deleteAll(String subjectCode,String gradeCode) {
		String hql = "delete from StudyGuide sg where sg.subject.code = '"+subjectCode+"'";
		if(gradeCode!=null && !"".equals(gradeCode.trim())) {
			hql += " and sg.grade.code = '"+gradeCode+"'";
		}
		this.genService.excuteHql(hql);
	}

	public List<StudyGuide> getAllExceptThis(StudyGuide sg) {
		return this.genService.find("from StudyGuide sg where sg.id != ? and sg.subject.code = ? order by parentLevel asc", sg.getId(), sg.getSubject().getCode());
	}

	public Integer saveVo(StudyGuide sg,SysUser sysUser) {
		if(sg.getId() == null) {
			StudyGuide s = new StudyGuide();
			s.setCode(sg.getCode());
			s.setName(sg.getName());
			s.setStatus(sg.getStatus());
			if(sg.getParent().getId()!= null) {
				s.setParent(this.genService.get(StudyGuide.class, sg.getParent().getId()));
			}else {
				s.setParent(null);
			}
			s.setSubject(this.genService.get(Subject.class, sg.getSubject().getCode()));
			s.setGrade(this.genService.get(Grade.class, sg.getGrade().getCode()));
			s.setContent(sg.getContent());
			s.setCreater(sysUser);
			s.setCreatedTime(new Date());
			sg = s;
		}else {
			StudyGuide s = this.genService.get(StudyGuide.class, sg.getId());
			s.setCode(sg.getCode());
			s.setName(sg.getName());
			s.setStatus(sg.getStatus());
			if(sg.getParent().getId()!= null) {
				s.setParent(this.genService.get(StudyGuide.class, sg.getParent().getId()));
			}else {
				s.setParent(null);
			}
			s.setSubject(this.genService.get(Subject.class, sg.getSubject().getCode()));
			s.setGrade(this.genService.get(Grade.class, sg.getGrade().getCode()));
			s.setContent(sg.getContent());
			s.setUpdater(sysUser);
			s.setUpdatedTime(new Date());
			sg = s;
		}
		sg.generateParentLevel();
		return (Integer)(this.getGenService().save1(sg));
	}

	public void saveParagraphVo(StudyGuideParagraph paragraph) {
		if(paragraph.getId() != null) {
			StudyGuideParagraph p = this.genService.get(StudyGuideParagraph.class, paragraph.getId());
			p.setTitle(paragraph.getTitle());
			p.setContent(paragraph.getContent());
			p.setOrderNum(paragraph.getOrderNum());
			p.setStudyGuide(this.getGenService().get(StudyGuide.class, paragraph.getStudyGuide().getId()));
			paragraph = p;
		}
		this.getGenService().save(paragraph);
	}

	public void saveItemVo(StudyGuideItem item) {
		if(item.getId() != null) {
			StudyGuideItem i = this.getGenService().get(StudyGuideItem.class, item.getId());
			i.setContent(item.getContent());
			i.setAnswer(item.getAnswer());
			i.setAnalys(item.getAnalys());
			i.setOrderNum(item.getOrderNum());
			i.setType(item.getType());
			i.setStudyGuideParagraph(this.getGenService().get(StudyGuideParagraph.class, item.getStudyGuideParagraph().getId()));
			item = i;
		}
		this.getGenService().save(item);
		
	}
	
	public void deleteParagraph(Integer id) {
		this.genService.excuteHql("delete from StudyGuideItem i where i.studyGuideParagraph.id = ?", id);
		this.genService.excuteHql("delete from StudyGuideParagraph where id = ?", id);
	}

	public void deleteItem(Integer id) {
		this.genService.excuteHql("delete from StudyGuideItem where id = ?", id);
	}
}

 