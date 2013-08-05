package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.Paper;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;

public class PaperAssemblingPolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(PaperAssemblingPolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from PaperAssemblingPolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public List setPaperById(PaperAssemblingPolicy paperAssemblingPolicy ){
		List errorList = new ArrayList();
		try{
			Long id = paperAssemblingPolicy.getAsfNode().getId();
			Integer paperId = paperAssemblingPolicy.getPaper().getId();
			if(id!=null && paperId!=null){
				PaperAssemblingPolicy palp = get(PaperAssemblingPolicy.class,id);
				if(palp!=null){
					palp.setPaper(get(Paper.class,paperId));
					save(palp);
				}else{
					errorList.add("没有找到或策略已经删除");
				}
			}	 
		}catch(Exception e){
			errorList.add("设置试卷失败");
		}
		return errorList;
	}
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PaperAssemblingPolicy where id="+id);
		}catch(Exception e){
			logger.error("［PaperAssemblingPolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PaperAssemblingPolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［PaperAssemblingPolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新PaperAssemblingPolicy 对象
	 * @param paperAssemblingPolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(PaperAssemblingPolicy paperAssemblingPolicy){
		List errorList = new ArrayList(0);
		try{
			save(paperAssemblingPolicy);
		}catch(Exception e){
			String errorMsg = "［PaperAssemblingPolicyService:add(PaperAssemblingPolicy paperAssemblingPolicy)］"+paperAssemblingPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 PaperAssemblingPolicy 对象，默认与add行为相似
	 * @param paperAssemblingPolicy
	 * @return 更新 PaperAssemblingPolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(PaperAssemblingPolicy paperAssemblingPolicy,Integer paperId){
		List errorList = new ArrayList(0);
		try{
			//update PaperAssemblingPolicy前的(如果有子对象)对其子对象进行相关处理
			
			Node node = paperAssemblingPolicy.getAsfNode();
			if(node!=null&&node.getId()!=0){
				node = get(Node.class,node.getId());
				if(node!=null){
					paperAssemblingPolicy.setAsfNode(node);
					 
					Integer answeringTime = paperAssemblingPolicy.getAnsweringTime();
					if(answeringTime!=null){
						paperAssemblingPolicy.setAnsweringTime(answeringTime*60);
					}
					Integer standardAnsweringTime = paperAssemblingPolicy.getStandardAnsweringTime();
					if(standardAnsweringTime!=null){
						paperAssemblingPolicy.setStandardAnsweringTime(standardAnsweringTime*60);
					}
					
					PaperAssemblingPolicy policyDb = get(PaperAssemblingPolicy.class,node.getId());
					if(paperId!=null){
						paperAssemblingPolicy.setPaper(get(Paper.class,paperId));
					}
					if(policyDb==null){
						save(paperAssemblingPolicy);
					}else{					 
						policyDb.setPaperAssemblingMode(paperAssemblingPolicy.getPaperAssemblingMode());
						policyDb.setPaper(paperAssemblingPolicy.getPaper());						
						policyDb.setAnsweringTime(paperAssemblingPolicy.getAnsweringTime());
						policyDb.setItems_num(paperAssemblingPolicy.getItems_num());
						policyDb.setTotalScore(paperAssemblingPolicy.getTotalScore());
						policyDb.setDifficultyValue(paperAssemblingPolicy.getDifficultyValue());
						policyDb.setStandardAnsweringTime(paperAssemblingPolicy.getStandardAnsweringTime());
						policyDb.setPaperList( paperAssemblingPolicy.getPaperList());
						policyDb.setBig_items_num( paperAssemblingPolicy.getBig_items_num());
						save(policyDb);
					}
				}else{
					errorList.add("指定的节点存在");
				}
				
			}else{
				errorList.add("没有指定节点");
			}
		}catch(Exception e){
			e.printStackTrace();
			String errorMsg = "［PaperAssemblingPolicyService:update(PaperAssemblingPolicy paperAssemblingPolicy)］"+paperAssemblingPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	public Map editViewMap(long id){
		Map map = new HashMap();
		PaperAssemblingPolicy paperAssemblingPolicy= get(PaperAssemblingPolicy.class, id);
		map.put("PaperAssemblingPolicy",paperAssemblingPolicy);
		String names = "";
		String ids = "";
		if(paperAssemblingPolicy!=null){
			if(paperAssemblingPolicy.getPaper()!=null){
				names =paperAssemblingPolicy.getPaper().getName() ;
				ids = paperAssemblingPolicy.getPaper().getId().toString();
			}
			if(paperAssemblingPolicy.getPaperList()!=null&& !"".equals(paperAssemblingPolicy.getPaperList().trim() )) {
				names = find("select name from Paper where id in(?)",paperAssemblingPolicy.getPaperList()).toString().replace("[","").replace("]","");
				ids = find("select id from Paper where id in(?)",paperAssemblingPolicy.getPaperList()).toString().replace("[","").replace("]","");
				
			}
		}
		map.put("ids",ids);	
		map.put("names",names);		
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}
	public Map getPaperIdsAndNames(PaperAssemblingPolicy paperAssemblingPolicy){
		Map map = new HashMap();
		if(paperAssemblingPolicy!=null){
			String paperIds = "";
			String paperNames = "";
			
			if(paperAssemblingPolicy.getPaper()!=null){
				paperNames =paperAssemblingPolicy.getPaper().getName() ;
				paperIds = paperAssemblingPolicy.getPaper().getId().toString();
			}
			if(paperAssemblingPolicy.getPaperList()!=null&& !"".equals(paperAssemblingPolicy.getPaperList().trim() )) {
				String paperIdsStr  = paperAssemblingPolicy.getPaperList(); 
				paperNames = find("select name from Paper where id in("+paperIdsStr+")",null).toString().replace("[","").replace("]","").replace(", ", ",");
				paperIds = find("select id from Paper where id in("+paperIdsStr+")",null).toString().replace("[","").replace("]","").replace(", ", ",");
				
			}
			map.put("paperIds",paperIds);
			map.put("paperNames",paperNames);
		}
		return map;
	}
	
	
	public List getAll( ){
		return super.getAll(PaperAssemblingPolicy.class) ;
	}
	
	public PaperAssemblingPolicy get(long id){
		return super.get(PaperAssemblingPolicy.class,id) ;
	}
	
}
