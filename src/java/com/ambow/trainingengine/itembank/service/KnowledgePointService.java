package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.Grade;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Subject;
import com.ambow.trainingengine.itembank.web.data.HQLObject;

/*
 * KnowledgePointService.java
 * 
 * Created on 2008-7-11 下午04:04:02
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
public class KnowledgePointService extends HibernateEntityDao<KnowledgePoint> {
	private SimpleJdbcTemplate jdbcTemplate ;
	private List<KnowledgePoint> gList = new ArrayList<KnowledgePoint>(0);

	public void abandon(String code ){
		KnowledgePoint knowledgePointDB = get(KnowledgePoint.class,code);
		abandon(knowledgePointDB);
	}
	public void abandon(KnowledgePoint kp ){
		kp.setState(-1);		
		List<KnowledgePoint> childrenList = kp.getChildrenKnowledgePoints();
		save(kp);
		if(childrenList!=null&&childrenList.size()>0){
			for(KnowledgePoint knowledgePoint : childrenList) {
				abandon(knowledgePoint);
			}
		}
	}
	
	/**
	 * 将废弃的知识点状态设置回正常(注：如果有子知识点,则子知识点一同被设置为正常)
	 * @param knowledgePoint
	 */
	public void toNomal(KnowledgePoint knowledgePoint ){
		knowledgePoint.setState(0);
		save(knowledgePoint);
		List<KnowledgePoint> childrenList = knowledgePoint.getChildrenKnowledgePoints();
		if(childrenList!=null&&childrenList.size()>0){
			for(KnowledgePoint kp : childrenList) {
				toNomal(kp);
			}
		}
	}
	
	public Page list(int pageNo) {
		return this.pagedQuery("from KnowledgePoint", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 获取知识点列表
	 * @param subjectCode
	 * @return
	 */
	public List<KnowledgePoint> list(String subjectCode,String gradeCode){
		String hql = "from KnowledgePoint a where a.subject.code=? and a.grade.code=? order by a.code";
		List<KnowledgePoint> list = this.find(hql,subjectCode,gradeCode);
		return list;
	}
	
	/**
	 * 获取知识点列表
	 * @param subjectCode
	 * @return
	 */
	public List<KnowledgePoint> listOrderByCode(String subjectCode,String gradeCode){
		String hql = "from KnowledgePoint a where a.subject.code=? and a.grade.code=? order by a.code";
		List<KnowledgePoint> list = this.find(hql,subjectCode,gradeCode);
		return list;
	}
	
	public List<Map<String,Object>> getList(String code){
		String sql = "select code,name from knowledge_point where parent_code=?";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql, code);
		
		return list;
	}
	
	public List<Map<String,Object>> getListWithModel(String code,Integer model){
		String sql = "select b.code,b.name  from model_knowledge_point_ref a " +
				"inner join knowledge_point b on a.knowledge_point_code = b.code " +
				"where parent_code=? and a.model_id=?";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql, code,model); 
		return list;
	}
	
	public List<Map<String,Object>> getListWithIn(String code){
		String sql = "select code,name from knowledge_point where parent_code in ("+code+") or code in("+code+") and parent_code is not null ";
		List<Map<String,Object>> list = this.getJdbcTemplate().queryForList(sql);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<KnowledgePoint> findTop() {
		return this.find("from KnowledgePoint where parent_code is null ");
	}
	
	@SuppressWarnings("unchecked")
	public List<KnowledgePoint> findOther(KnowledgePoint knowledgePoint) {
		List<KnowledgePoint> knowledgePointList = this.getAll("parentLevel", true);
		if (knowledgePoint!=null) {
			synchronized (gList) {
				gList.clear();
				getAllChildrenGrades(knowledgePoint);
				knowledgePointList.removeAll(gList);
				knowledgePointList.remove(knowledgePoint);
				gList.clear();
			}
		}
		return knowledgePointList;
	}
	
	/**获取全部子节点*/
	private void getAllChildrenGrades(KnowledgePoint knowledgePoint) {		
		for(KnowledgePoint tmpKnowledgePoint: knowledgePoint.getChildrenKnowledgePoints()) {
			gList.add(tmpKnowledgePoint);
			getAllChildrenGrades(tmpKnowledgePoint);
		}
	}
	
	@SuppressWarnings("unchecked")
	public String getPCode(KnowledgePoint knowledgePoint) {
		String PCode = "";
		if (knowledgePoint!=null)
			if (knowledgePoint.getParentKnowledgePoint()!=null) PCode = knowledgePoint.getParentKnowledgePoint().getCode();
		return PCode;
	}

	public void saveVO(KnowledgePoint knowledgePoint) {
		KnowledgePoint actKnowledgePoint = get(knowledgePoint.getCode());
		
		if (actKnowledgePoint == null) {
			actKnowledgePoint = knowledgePoint;
		} else {
			if(knowledgePoint.getState()==0){
				toNomal(actKnowledgePoint);
			}
			if(knowledgePoint.getState()==-1){
				abandon(actKnowledgePoint);
			}
			actKnowledgePoint.setCode(knowledgePoint.getCode());
			actKnowledgePoint.setName(knowledgePoint.getName());
			actKnowledgePoint.setSubject(get(Subject.class,knowledgePoint.getSubject().getCode()));
			actKnowledgePoint.setGrade(get(Grade.class,knowledgePoint.getGrade().getCode()));
		}
		actKnowledgePoint.setParentKnowledgePoint(get(knowledgePoint.getParentKnowledgePoint().getCode()));
		actKnowledgePoint.genparentLevel();
		save(actKnowledgePoint);
	}
	
	public void deleteAll() {
		this.excuteHql("delete from KnowledgePoint");
	}
	
	public void deleteBatch(String codes) {
		this.excuteHql("delete from KnowledgePoint where code in (" + codes + ")");		
	}
	
	public Page findByCode(int pageNo, String code) {
		return this.pagedQuery("from KnowledgePoint R where R.code = ?", pageNo, Constants.DEFAULT_PAGE_SIZE, code);
	}
	
	public Page findByName(int pageNo, String name) {
		return this.pagedQuery("from KnowledgePoint R where R.name like ?", pageNo, Constants.DEFAULT_PAGE_SIZE, name);
	}
	public Page listOrSearch(String queryType ,String grade_code,String subject_code,String queryValue,int pageNo ){ 
		HQLObject hqlObject= UtilForGradeSubjectService.getHQLForGradeSubject( queryType , grade_code, subject_code, queryValue, pageNo, "KnowledgePoint");
		return this.pagedQuery(hqlObject.getHql() , pageNo, Constants.DEFAULT_PAGE_SIZE,hqlObject.getQueryValueList().toArray());
	}
	public List<KnowledgePoint> findBySubject(String subject)
	{
		
		List<KnowledgePoint> list = this.find("from KnowledgePoint where subject.code=?", subject);
		return list;
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//声明一个全局变量这样递归时可反复添加
	//private List<KnowledgePoint> retList = new LinkedList<KnowledgePoint>();
	/**
	 * 获取某个学科的所有知识点
	 * WeiShaoying 2009年11月25日14:02:39
	 * @param subject_code
	 * @return
	 */
	public List<KnowledgePoint> getAllKpsBySubject(String subject_code) {
		List<KnowledgePoint> retList = new LinkedList<KnowledgePoint>();
		String hql = "from KnowledgePoint k where k.subject.code = ? order by k.code";
		List<KnowledgePoint> roots = this.find(hql, subject_code);
		retList.addAll(roots);
		for (KnowledgePoint kp : roots) {
			List<KnowledgePoint>itsChildren = this.getKpAllChildrenByRecu(kp);
			if(itsChildren.size()>0) {
				retList.addAll(itsChildren);
			}
		}
		return retList;
	}
	
	/**
	 * 递归获取某个节点的所有子节点
	 * 2009年11月25日14:04:28
	 * @param kp
	 * @return
	 */
	private List<KnowledgePoint> getKpAllChildrenByRecu(KnowledgePoint kp) {
		List<KnowledgePoint>ret = kp.getChildrenKnowledgePoints();
		for (int i = 0; i < ret.size(); i++) {
			KnowledgePoint k = (KnowledgePoint)ret.get(i);
			if(k.getChildrenKnowledgePoints()!= null && k.getChildrenKnowledgePoints().size()>0) {
				ret.addAll(this.getKpAllChildrenByRecu(k));
			}
		}
		return ret;
	}
	
	public List<KnowledgePoint> getAllKpsBySubjAndGrade(String subject_code,String grade_code) {
		List<KnowledgePoint> retList = new LinkedList<KnowledgePoint>();
		List<KnowledgePoint> roots = this.list(subject_code, grade_code);
		retList.addAll(roots);
		for (KnowledgePoint kp : roots) {
			List<KnowledgePoint>itsChildren = this.getKpAllChildrenByRecu(kp);
			if(itsChildren.size()>0) {
				retList.addAll(itsChildren);
			}
		}
		return retList;
	}
	
}
