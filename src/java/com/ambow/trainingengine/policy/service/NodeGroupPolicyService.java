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

public class NodeGroupPolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(NodeGroupPolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from NodeGroupPolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from NodeGroupPolicy where id="+id);
		}catch(Exception e){
			logger.error("［NodeGroupPolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from NodeGroupPolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［NodeGroupPolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新NodeGroupPolicy 对象
	 * @param nodeGroupPolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(NodeGroupPolicy nodeGroupPolicy){
		List errorList = new ArrayList(0);
		try{
			save(nodeGroupPolicy);
		}catch(Exception e){
			String errorMsg = "［NodeGroupPolicyService:add(NodeGroupPolicy nodeGroupPolicy)］"+nodeGroupPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 NodeGroupPolicy 对象，默认与add行为相似
	 * @param nodeGroupPolicy
	 * @return 更新 NodeGroupPolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(NodeGroupPolicy nodeGroupPolicy){
		List errorList = new ArrayList(0);
		try{
			Node node = get(Node.class,nodeGroupPolicy.getAsfNode().getId()) ;
			if(node!=null&&node.getId()!=0){				
				NodeGroupPolicy ngpDB = (NodeGroupPolicy)findObjByHql("from NodeGroupPolicy where asfNode.id=?"  ,node.getId());
				if(ngpDB==null){
					nodeGroupPolicy.setAsfNode(node);
					save(nodeGroupPolicy);
				}else{
					ngpDB.setIsDisplayModule(nodeGroupPolicy.getIsDisplayModule());
					ngpDB.setStudyGuide( nodeGroupPolicy.getStudyGuide());
					save(ngpDB);
				}
			}else{
				errorList.add("没有指定节点");
			}
			
		}catch(Exception e){
			String errorMsg = "［NodeGroupPolicyService:update(NodeGroupPolicy nodeGroupPolicy)］"+nodeGroupPolicy+" 保存对象时出现异常!"+e.toString();
			e.printStackTrace();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		map.put("NodeGroupPolicy", get(NodeGroupPolicy.class, id));
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(NodeGroupPolicy.class) ;
	}
	
	public NodeGroupPolicy get(long id){
		return super.get(NodeGroupPolicy.class,id) ;
	}
}
