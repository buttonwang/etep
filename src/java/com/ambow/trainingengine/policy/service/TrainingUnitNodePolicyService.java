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
import com.ambow.trainingengine.policy.domain.NodeGroupPolicy;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;

public class TrainingUnitNodePolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(TrainingUnitNodePolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from TrainingUnitNodePolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from TrainingUnitNodePolicy where id="+id);
		}catch(Exception e){
			logger.error("［TrainingUnitNodePolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from TrainingUnitNodePolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［TrainingUnitNodePolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新TrainingUnitNodePolicy 对象
	 * @param trainingUnitNodePolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(TrainingUnitNodePolicy trainingUnitNodePolicy){
		List errorList = new ArrayList(0);
		try{
			save(trainingUnitNodePolicy);
		}catch(Exception e){
			String errorMsg = "［TrainingUnitNodePolicyService:add(TrainingUnitNodePolicy trainingUnitNodePolicy)］"+trainingUnitNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 TrainingUnitNodePolicy 对象，默认与add行为相似
	 * @param trainingUnitNodePolicy
	 * @return 更新 TrainingUnitNodePolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(TrainingUnitNodePolicy trainingUnitNodePolicy){
		List errorList = new ArrayList(0);
		try{
			//update TrainingUnitNodePolicy前的(如果有子对象)对其子对象进行相关处理
			Node node =  get(Node.class,trainingUnitNodePolicy.getAsfNode().getId());
			if(node!=null&&node.getId()!=0){
				TrainingUnitNodePolicy policyDb = get(TrainingUnitNodePolicy.class,node.getId());
				if(policyDb==null){
					trainingUnitNodePolicy.setAsfNode(node);
					save(trainingUnitNodePolicy);
				}else{
					policyDb.setFailed(trainingUnitNodePolicy.getFailed());
					policyDb.setPass(trainingUnitNodePolicy.getPass());
					save(policyDb);
				}
			}else{
				errorList.add("没有指定节点");
			}
			save(trainingUnitNodePolicy);
		}catch(Exception e){
			String errorMsg = "［TrainingUnitNodePolicyService:update(TrainingUnitNodePolicy trainingUnitNodePolicy)］"+trainingUnitNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		map.put("TrainingUnitNodePolicy", get(TrainingUnitNodePolicy.class, id));
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(TrainingUnitNodePolicy.class) ;
	}
	
	public TrainingUnitNodePolicy get(long id){
		return super.get(TrainingUnitNodePolicy.class,id) ;
	}
}
