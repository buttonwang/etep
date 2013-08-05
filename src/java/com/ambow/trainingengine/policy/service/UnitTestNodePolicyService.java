package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;

public class UnitTestNodePolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(UnitTestNodePolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from UnitTestNodePolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from UnitTestNodePolicy where id="+id);
		}catch(Exception e){
			logger.error("［UnitTestNodePolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from UnitTestNodePolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［UnitTestNodePolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新UnitTestNodePolicy 对象
	 * @param unitTestNodePolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(UnitTestNodePolicy unitTestNodePolicy){
		List errorList = new ArrayList(0);
		try{
			save(unitTestNodePolicy);
		}catch(Exception e){
			String errorMsg = "［UnitTestNodePolicyService:add(UnitTestNodePolicy unitTestNodePolicy)］"+unitTestNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 UnitTestNodePolicy 对象，默认与add行为相似
	 * @param unitTestNodePolicy
	 * @return 更新 UnitTestNodePolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(UnitTestNodePolicy unitTestNodePolicy){
		List errorList = new ArrayList(0);
		try{
			//update UnitTestNodePolicy前的(如果有子对象)对其子对象进行相关处理
			if(unitTestNodePolicy!=null&&unitTestNodePolicy.getAsfNode()!=null){
				Node node =get(Node.class,unitTestNodePolicy.getAsfNode().getId());
				if(node!=null&&node.getId()!=0){
					UnitTestNodePolicy unitTestNodePolicyDB = get(UnitTestNodePolicy.class,node.getId());
					Integer retrainingScope = unitTestNodePolicy.getRetrainingScope() ;
					if(unitTestNodePolicyDB==null){
						unitTestNodePolicy.setAsfNode(node);
						save(unitTestNodePolicy);
					}else{
						unitTestNodePolicyDB.setRetrainingScope(retrainingScope);					
						save(unitTestNodePolicyDB);
					}
				}else{
					errorList.add("没有指定节点");
				}
			}
		}catch(Exception e){
			String errorMsg = "［UnitTestNodePolicyService:update(UnitTestNodePolicy unitTestNodePolicy)］"+unitTestNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		map.put("UnitTestNodePolicy", get(UnitTestNodePolicy.class, id));
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(UnitTestNodePolicy.class) ;
	}
	
	public UnitTestNodePolicy get(long id){
		return super.get(UnitTestNodePolicy.class,id) ;
	}
}
