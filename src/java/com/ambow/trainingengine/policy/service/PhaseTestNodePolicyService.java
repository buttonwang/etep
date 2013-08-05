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
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;

public class PhaseTestNodePolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(PhaseTestNodePolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from PhaseTestNodePolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Integer id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PhaseTestNodePolicy where id="+id);
		}catch(Exception e){
			logger.error("［PhaseTestNodePolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PhaseTestNodePolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［PhaseTestNodePolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新PhaseTestNodePolicy 对象
	 * @param phaseTestNodePolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(PhaseTestNodePolicy phaseTestNodePolicy){
		List errorList = new ArrayList(0);
		try{
			save(phaseTestNodePolicy);
		}catch(Exception e){
			String errorMsg = "［PhaseTestNodePolicyService:add(PhaseTestNodePolicy phaseTestNodePolicy)］"+phaseTestNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 PhaseTestNodePolicy 对象，默认与add行为相似
	 * @param phaseTestNodePolicy
	 * @return 更新 PhaseTestNodePolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(PhaseTestNodePolicy phaseTestNodePolicy){
		List errorList = new ArrayList(0);
		try{
			//update PhaseTestNodePolicy前的(如果有子对象)对其子对象进行相关处理
			
			save(phaseTestNodePolicy);
		}catch(Exception e){
			String errorMsg = "［PhaseTestNodePolicyService:update(PhaseTestNodePolicy phaseTestNodePolicy)］"+phaseTestNodePolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(Integer id){
		Map map = new HashMap();
		map.put("PhaseTestNodePolicy", get(PhaseTestNodePolicy.class, id));
		return map;
	}
	
	public Map showViewMap(Integer id){
		return editViewMap(id);
	}
	
	public Map showByNodeIdViewMap(Long nodeId){
		Map map = new HashMap();
		List errorList = new ArrayList(0);
		if(nodeId==null||nodeId==0){
			errorList.add("没有指定节点id或该节点相关数据已被删除"); 
			
		}else{
			
			Node node = get(Node.class ,nodeId);
			List jumpToLst = find("from Node n where n.processDefinition.id=?",node.getProcessDefinition().getId());
			
			map.put("jumpToLst", jumpToLst);
			map.put("phaseTestNodePolicyLst",find("from PhaseTestNodePolicy p where p.asfNode.id=?",nodeId));
			map.put("phaseTestNodePolicyLst",find("from PhaseTestNodePolicy p where p.asfNode.id=?",nodeId));
		}
		map.put("errorList", errorList);
		 
		return map;
	}

	public List getAll( ){
		return super.getAll(PhaseTestNodePolicy.class) ;
	}
	
	public PhaseTestNodePolicy get(long id){
		return super.get(PhaseTestNodePolicy.class,id) ;
	}
}
