package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.ProcessCategory;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.trainingengine.studyflow.util.JSONLeanW;

public class ProcessCategoryService extends HibernateGenericDao{
	public static Map processCategoryMap;
	public String getProcessCategoryName   (ProcessDefinition pr){
		ProcessCategory pc = getProcessCategory(pr);
		if(pc!=null){
			return pc.getName();
		}
		return "";
	}
	public ProcessCategory getProcessCategory(ProcessDefinition pr){
		ProcessCategory pc = new ProcessCategory();
		if(pr!=null){
			pc = get(ProcessCategory.class,pr.getCategoryId());
		}
		return pc;
		
	}
	
	Logger logger = Logger.getLogger(ProcessCategoryService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from ProcessCategory",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public Map allProcessCategory(){
		List lst = getAll(ProcessCategory.class );
		
		Set sonSet =  allSons(lst);
		lst.removeAll(sonSet);
		
		Map viewMap = new HashMap();
		viewMap.put("processCategoryLst",lst);
		return viewMap;
	} 
	
	public Set allSons(List processCategoryLst){
		Set sonSet = new HashSet();
		for (Iterator iterator = processCategoryLst.iterator(); iterator.hasNext();) {
			ProcessCategory name = (ProcessCategory) iterator.next();
			sonSet.addAll(findSon(name) );
		}
		return sonSet;
	}
	public Set findSon(ProcessCategory pc){
		Set set = new HashSet();
		if(pc!=null){
			List children  = pc.getChildrenCategories();
			if(children!=null&&children.size()>0){
				set.addAll(children);
				for (Iterator iterator = children.iterator(); iterator.hasNext();) {
					ProcessCategory name = (ProcessCategory) iterator.next();
					set.addAll(findSon(name));
				}
			}
		}
		return set;
	}
	
	
	
	
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			ProcessCategory pc = get(ProcessCategory.class,id);
			if(pc!=null){
				Set sonSet = findSon(pc);
				String categoryIds =  makeProcessCategoryStrIds(sonSet);
				categoryIds += (categoryIds==""?"":",")+pc.getId();
				List processLst = find("from ProcessDefinition where categoryId in (" + categoryIds+")");
				if(processLst==null||processLst.size()==0){
					for (Iterator iterator = sonSet.iterator(); iterator.hasNext();) {
						ProcessCategory itpc = (ProcessCategory) iterator.next();
						remove(itpc);
					}
					remove(pc);
				}else{
					errorList.add("分类已经被使用，不能被删除！");
				}
			}else{
				errorList.add("要删除的流程分类不存在或者已经被删除");
			}
			
		}catch(Exception e){
			logger.error("［ProcessCategoryService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**新删除全部*/
	public List deleteAll(){
		List lst = getAll(ProcessCategory.class );
		List errorList = new ArrayList(0);
		String categoryIds =  makeProcessCategoryStrIds(lst);
		List processLst =null;
		if(categoryIds!=""){
			processLst = find("from ProcessDefinition where categoryId in (" + categoryIds+")");
		}
		if(processLst==null||processLst.size()==0){
			for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
				ProcessCategory itpc = (ProcessCategory) iterator.next();
				remove(itpc);
			}
		}else{
			errorList.add("删除全部 操作失败。原因：已经在使用中的分类不能被删除！");
		}
		return errorList;
	}
	public String makeProcessCategoryStrIds (List lst){
		String strIds="";
		if(lst!=null){
			long length = lst.size();
			long i =0;
			for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
				ProcessCategory pc = (ProcessCategory) iterator.next();
				strIds += ""+ pc.getId();
				if(++i!=length){
					strIds+=",";
				}
			}
		}
		return strIds;
	}
	
	public String makeProcessCategoryStrIds (Set set ){
		String strIds="";
		if(set!=null){
			long length = set.size();
			long i =0;
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				ProcessCategory pc = (ProcessCategory) iterator.next();
				strIds += ""+ pc.getId();
				if(++i!=length){
					strIds+=",";
				}
			}
		}
		return strIds;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ProcessCategory where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［ProcessCategoryService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	 
	public Map sadd(Long parentId){
		Map map = new HashMap();
		if(parentId!=null){
			map.put("parentCategory", get(ProcessCategory.class,parentId));
		}
		map.put("pcListJson",getProcessCategoryJson(this));
		map.put("parentCategoryLst", getAll(ProcessCategory.class));
		return map;
	}
	
	public static String getProcessCategoryJson(HibernateGenericDao dao){
			if (dao != null ) {
				List<ProcessCategory> lst = dao.getAll(ProcessCategory.class);
				if(lst!=null&&lst.size()>0){
					StringBuffer  sb = new StringBuffer();
					int size = lst.size();
					for (int i = 0; i < size; i++) {
						ProcessCategory pc =(ProcessCategory)lst.get(i);
						long id =  pc.getId();
						long pid = -1;
						if (pc.getParentCategory() != null) {
							 pid= pc.getParentCategory().getId();
						}
						if(i!=0){
							sb.append(","); 
						}
						sb.append(String.format("{'id':'%s','pid':'%s','obj':{'id':'%s','name':'%s'}}", id,pid,id,pc.getName()));
					}
					return "["+sb.toString()+"]";
				}
			}
			return "";
	}
	/**
	 * 向系统中添加 新ProcessCategory 对象
	 * @param processCategory
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(ProcessCategory processCategory){
		List errorList = new ArrayList(0);
		try{
			ProcessCategory processCategory_temp = new ProcessCategory();
			processCategory_temp.setName(processCategory.getName());			
			ProcessCategory p = processCategory.getParentCategory();
			if(p!=null){
				ProcessCategory parent =(ProcessCategory) findObjByHql("from ProcessCategory where id=?", p.getId());
				processCategory_temp.setParentCategory(parent);
			}else{
				processCategory_temp.setParentCategory(null);
			}
			save(processCategory_temp);
		}catch(Exception e){
			String errorMsg = "［ProcessCategoryService:add(ProcessCategory processCategory)］"+processCategory+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 ProcessCategory 对象，默认与add行为相似
	 * @param processCategory
	 * @return 更新 ProcessCategory 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(ProcessCategory processCategory){
		List errorList = new ArrayList(0);
		try{
			
			long parentid = processCategory.getParentCategory().getId();
			String parentIdStr =  new String(""+parentid);
			//update ProcessCategory前的(如果有子对象)对其子对象进行相关处理
			if( processCategory.getParentCategory()!=null&&processCategory.getParentCategory().getId()==0){	
				processCategory.setParentCategory(null);
			}else{
				processCategory.setParentCategory(get(ProcessCategory.class,parentid));
			}
			ProcessCategory pc = get(ProcessCategory.class,processCategory.getId());
			Set sonSet = findSon(pc);
			String ids = "";
			int i=0;
			for (Iterator iterator = sonSet.iterator(); iterator.hasNext();) {
				ProcessCategory tpPc = (ProcessCategory) iterator.next();
				if(++i>1&&i<sonSet.size()){
					ids+=","; 
				} 
				ids += ""+tpPc.getId();
			}
			
			
			
			if(ids.indexOf(parentIdStr)!=-1){
				errorList.add("分类不能指向它的儿子节点。因为那是就有点像瑜珈的最高境界,不容易达成哦！");
			}else{
				ProcessCategory processCategoryDB = get(ProcessCategory.class,processCategory.getId());
				if(processCategoryDB==null){
					errorList.add("要更新的分类不存在或已经被删除");
				}else{
					processCategoryDB.setName(processCategory.getName());
					if(processCategory.getId()!=parentid){
						processCategoryDB.setParentCategory(processCategory.getParentCategory());
					}
					save(processCategoryDB);
				}
			}
		}catch(Exception e){
			String errorMsg = "［ProcessCategoryService:update(ProcessCategory processCategory)］"+processCategory+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			e.printStackTrace();
			logger.error(errorMsg);
		}
		return errorList;
	}
	public List getParentList(Long id ){
		ProcessCategory pc = get(ProcessCategory.class,id);
		List lst = getAll(ProcessCategory.class );
		lst.remove(pc);
		lst.removeAll(findSon(pc));
		return lst;
	}
	public Map editViewMap(Long id,Long parentId){
		Map map = new HashMap();
		if(id!=null){
			map.put("processCategory", findObjByHql("from ProcessCategory where id=?", id));
		}
		if(parentId!=null){
			map.put("parentCategory", findObjByHql("from ProcessCategory where id=?", parentId));
		}
		map.put("parentCategoryLst", getParentList(id ));
		map.put("pcListJson",ProcessCategoryService.getProcessCategoryJson(this));
		map.put("processLst", find("from ProcessDefinition where categoryId  =?",id));
		return map;
	}
	
	public Map showViewMap(Long id){
		return editViewMap(id,null);
	}
	
	/***********************************************
	 * USE: 根据流程类别ID查询流程类别信息及流程列表等相关信息
	 * PARAM: id 流程类别ID
	 * RETURN: 流程类别信息及流程信息、分页信息等相关信息
	 * FOR: 通过类别进入流程类别页面的时候，要对类别下流程进行查询，并且，查询结果也要进行分页
	 * 		原来进入流程类别页面的时候用的是： public Map showViewMap(Long id)
	 * 
	 * AUTHOR: L.赵亚
	 * DATE: 2010.03.01.18.23
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Map showViewMapForPage(Long id){
		Map map = new HashMap();
		if(id!=null){
			map.put("processCategory", findObjByHql("from ProcessCategory where id=?", id));
		}
		map.put("parentCategoryLst", getParentList(id ));
		map.put("pcListJson",ProcessCategoryService.getProcessCategoryJson(this));
		Page p = this.pagedQuery("from ProcessDefinition where categoryId="+id, 1, Constants.DEFAULT_PAGE_SIZE);
		map.put("page", p);
		return map;
	}

	public List getAll( ){
		return super.getAll(ProcessCategory.class) ;
	}
	
	public ProcessCategory get(long id){
		return super.get(ProcessCategory.class,id) ;
	}

	public Map getProcessCategoryForMap() {
		List lst = getAll(ProcessCategory.class);
		Map map = new HashMap();
		for (Iterator iterator = lst.iterator(); iterator.hasNext();) {
			ProcessCategory pc = (ProcessCategory) iterator.next();
			map.put(pc.getId(), pc.getName());
		}
		return map;
	}
	public List<Integer> getVersions(Long id) {
		String hql = "select distinct pd.defVersion from ProcessDefinition pd where pd.categoryId = ? order by pd.defVersion desc";
		//String hql = "select distinct pd.defVersion from ProcessDefinition pd,ProcessCategory pc where pd.categoryId = pc.id and pc.id = ? order by pd.defVersion desc";
		return this.find(hql, id);
	}
}
