package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.policy.domain.ModuleEvalPolicyTemplate;
import com.ambow.trainingengine.util.ParamObject;

public class ModuleEvalPolicyTemplateService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(ModuleEvalPolicyTemplateService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from ModuleEvalPolicyTemplate",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Integer id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ModuleEvalPolicyTemplate where id="+id);
		}catch(Exception e){
			logger.error("［ModuleEvalPolicyTemplateService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from ModuleEvalPolicyTemplate where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［ModuleEvalPolicyTemplateService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新ModuleEvalPolicyTemplate 对象
	 * @param moduleEvalPolicyTemplate
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(ModuleEvalPolicyTemplate moduleEvalPolicyTemplate){
		List errorList = new ArrayList(0);
		try{
			save(moduleEvalPolicyTemplate);
		}catch(Exception e){
			String errorMsg = "［ModuleEvalPolicyTemplateService:add(ModuleEvalPolicyTemplate moduleEvalPolicyTemplate)］"+moduleEvalPolicyTemplate+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	/**
	 * 向系统中添加 新ModuleEvalPolicyTemplate 对象
	 * @param p 包新对象属性的文件
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public Map add(ParamObject p){
		Map map = new HashMap();
		List errorList = new ArrayList(0);
		ModuleEvalPolicyTemplate moduleEvalPolicyTemplate = new ModuleEvalPolicyTemplate();
		try{
			String name = p.get("name");
			if(name !=null && !name.trim().equals("")){
				moduleEvalPolicyTemplate.setName(name);
				save(moduleEvalPolicyTemplate);
				map.put("message", "成功保存对象");
				map.put("moduleEvalPolicyTemplate", "moduleEvalPolicyTemplate");
			}else{
				errorList.add("名称不能为空");
			}			
		}catch(Exception e){
			String errorMsg = "［ModuleEvalPolicyTemplateService:add(ModuleEvalPolicyTemplate moduleEvalPolicyTemplate)］"+moduleEvalPolicyTemplate+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		map.put("errorLst",errorList);
		return map;
	}
	
	/**
	 * 用于更新 ModuleEvalPolicyTemplate 对象，默认与add行为相似
	 * @param moduleEvalPolicyTemplate
	 * @return 更新 ModuleEvalPolicyTemplate 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(ModuleEvalPolicyTemplate moduleEvalPolicyTemplate){
		List errorList = new ArrayList(0);
		try{
			//update ModuleEvalPolicyTemplate前的(如果有子对象)对其子对象进行相关处理
			
			save(moduleEvalPolicyTemplate);
		}catch(Exception e){
			String errorMsg = "［ModuleEvalPolicyTemplateService:update(ModuleEvalPolicyTemplate moduleEvalPolicyTemplate)］"+moduleEvalPolicyTemplate+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(Integer id){
		Map map = new HashMap();
		map.put("ModuleEvalPolicyTemplate", findObjByHql("from ModuleEvalPolicyTemplate where id =?", id));
		return map;
	}
	
	public Map showViewMap(Integer id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(ModuleEvalPolicyTemplate.class) ;
	}
	
	public ModuleEvalPolicyTemplate get(long id){
		return super.get(ModuleEvalPolicyTemplate.class,id) ;
	}
}
