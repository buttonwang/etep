package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.AssemblingPaperReqTemplate;
import com.ambow.trainingengine.policy.domain.PaperAssemblingReqTemplate;

public class AssemblingPaperReqTemplateService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(AssemblingPaperReqTemplateService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from AssemblingPaperReqTemplate",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public Map delete(Integer id) {		
		Map map = new HashMap();
		List errorList = new ArrayList(0);
		try{ 
			AssemblingPaperReqTemplate abprt = (AssemblingPaperReqTemplate) findObjByHql(" from AssemblingPaperReqTemplate where id=?",id);
		
			if(abprt!=null){
				map.put("paperAssemblingReqTemplateId", abprt.getPaperAssemblingReqTemplate().getId());
				remove(abprt);
			}else{
				errorList.add("此策略不存在或已经被删除");
			}
		}catch(Exception e){
			logger.error("［AssemblingPaperReqTemplateService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		map.put("errorList", errorList);
		return map;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from AssemblingPaperReqTemplate where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［AssemblingPaperReqTemplateService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteAll(Integer pid) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from AssemblingPaperReqTemplate where paperAssemblingReqTemplate.id = ?", pid);
		}catch(Exception e){
			logger.error("［AssemblingPaperReqTemplateService:deleteBatch(String ids)］全部删除时出现异常"+pid+"  "+e.toString());
			errorList.add("全部删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新AssemblingPaperReqTemplate 对象
	 * @param assemblingPaperReqTemplate
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(AssemblingPaperReqTemplate assemblingPaperReqTemplate){
		List errorList = new ArrayList(0);
		try{
			PaperAssemblingReqTemplate p = get(PaperAssemblingReqTemplate.class,assemblingPaperReqTemplate.getPaperAssemblingReqTemplate().getId());
			if(p!=null){
				assemblingPaperReqTemplate.setPaperAssemblingReqTemplate(p);
				save(assemblingPaperReqTemplate);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			String errorMsg = "［AssemblingPaperReqTemplateService:add(AssemblingPaperReqTemplate assemblingPaperReqTemplate)］"+assemblingPaperReqTemplate+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 AssemblingPaperReqTemplate 对象，默认与add行为相似
	 * @param assemblingPaperReqTemplate
	 * @return 更新 AssemblingPaperReqTemplate 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(AssemblingPaperReqTemplate assemblingPaperReqTemplate){
		List errorList = new ArrayList(0);
		try{
			//update AssemblingPaperReqTemplate前的(如果有子对象)对其子对象进行相关处理
			
			save(assemblingPaperReqTemplate);
		}catch(Exception e){
			String errorMsg = "［AssemblingPaperReqTemplateService:update(AssemblingPaperReqTemplate assemblingPaperReqTemplate)］"+assemblingPaperReqTemplate+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(Integer id){
		Map map = new HashMap();
		map.put("AssemblingPaperReqTemplate", get(AssemblingPaperReqTemplate.class, id));
		return map;
	}
	
	public Map showViewMap(Integer id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(AssemblingPaperReqTemplate.class) ;
	}
	
	public AssemblingPaperReqTemplate get(long id){
		return super.get(AssemblingPaperReqTemplate.class,id) ;
	}
}
