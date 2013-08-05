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
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.util.ParamObject;

public class TrainingPolicyService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(TrainingPolicyService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from TrainingPolicy",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	
	public Map sadd(ParamObject p){
		Map m = new HashMap(0);
		List errorLst = new ArrayList();
		Node node = null;
		try{
			Long nodeId = p.getLong("nid");
			node = get(Node.class,nodeId);
		}catch(Exception e){
		}
		if(node!=null){
			m.put("TrainingPolicy", get(TrainingPolicy.class,node.getId()));
			m.put("nid", node.getId());
			
		}else{
			errorLst.add("必须指定一个节点");
		}
		return m;	
	}
	
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Long id) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from TrainingPolicy where id="+id);
		}catch(Exception e){
			logger.error("［TrainingPolicyService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from TrainingPolicy where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［TrainingPolicyService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新TrainingPolicy 对象
	 * @param trainingPolicy
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(TrainingPolicy trainingPolicy){
		List errorList = new ArrayList(0);
		try{
			save(trainingPolicy);
		}catch(Exception e){
			String errorMsg = "［TrainingPolicyService:add(TrainingPolicy trainingPolicy)］"+trainingPolicy+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		return errorList;
	}
	
	/**
	 * 用于更新 TrainingPolicy 对象，默认与add行为相似
	 * @param trainingPolicy
	 * @return 更新 TrainingPolicy 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public Map update(ParamObject p){
		Map m = new HashMap();
		List errorLst = new ArrayList(0);
		try{
			if(p.getLong("nid")!=null){
				TrainingPolicy tp = (TrainingPolicy) p.getObject("tp");
				if(tp!=null){
					tp.setNodeId(p.getLong("nid"));
					save(tp);
				}else{
					errorLst.add("没有策略数据可供数据！");
				}
			}
		}catch(Exception e){
			String errorMsg = "［TrainingPolicyService:update(TrainingPolicy trainingPolicy)］ 保存对象时出现异常!"+e.toString();
			errorLst.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		m.put("errorLst", errorLst);//m.get("errorLst")
		return m;
	}
	
	public Map update(TrainingPolicy tp){
		Map m = new HashMap();
		List errorList = new ArrayList(0);
		try{
			Node node = get(Node.class,tp.getAsfNode().getId());
			if(node!=null ){
				m.put("nodeId", node.getId());
				tp.setAsfNode(node);
				TrainingPolicy tpDB =(TrainingPolicy)findObjByHql("from TrainingPolicy where asfNode.id=?" ,node.getId());
				if(tpDB==null){ 
					save(tp);
				}else{
					tpDB.setOverviewTime(tp.getOverviewTime());
					tpDB.setWhenToSeeAnalysis(tp.getWhenToSeeAnalysis());
					tpDB.setWhenToCheckAnswer(tp.getWhenToCheckAnswer());
					tpDB.setAllowUnsureMark(tp.getAllowUnsureMark());
					tpDB.setIsRandom(tp.getIsRandom());
					tpDB.setIsRandomAnswerOptions(tp.getIsRandomAnswerOptions());
					tpDB.setRightRateForPass(tp.getRightRateForPass());
					tpDB.setRightRateRetraining(tp.getRightRateRetraining());
					tpDB.setRetrainingItemType(tp.getRetrainingItemType());
					tpDB.setRetrainingItemOrder(tp.getRetrainingItemOrder());
					tpDB.setRetrainingRightRateTestFaile(tp.getRetrainingRightRateTestFaile());
					tpDB.setRetrainingItemTypeTestFaile(tp.getRetrainingItemTypeTestFaile());
					tpDB.setRetrainingItemOrderTestFaile(tp.getRetrainingItemOrderTestFaile());
					tpDB.setRandomAssemItemsTestFaile(tp.getRandomAssemItemsTestFaile());
					tpDB.setAssemItemsTypeTestFaile(tp.getAssemItemsTypeTestFaile());
					tpDB.setAssemItemsRateTestFaile(tp.getAssemItemsRateTestFaile());
					tpDB.setClueContentFirstFaile(tp.getClueContentFirstFaile());
					tpDB.setClueRelActFirstFaile(tp.getClueRelActFirstFaile());
					tpDB.setTranslationContentFirstFaile(tp.getTranslationContentFirstFaile());
					tpDB.setTranslationRelActFirstFaile(tp.getTranslationRelActFirstFaile());
					tpDB.setClueContentSecondFaile(tp.getClueContentSecondFaile());
					tpDB.setClueRelActSecondFaile(tp.getClueRelActSecondFaile());
					tpDB.setTranslationContentSecondFaile(tp.getTranslationContentSecondFaile());
					tpDB.setTranslationRelActSecondFaile(tp.getTranslationRelActSecondFaile());
					tpDB.setClueContentThirdFaile(tp.getClueContentThirdFaile());
					tpDB.setClueRelActThirdFaile(tp.getClueRelActThirdFaile());
					tpDB.setTranslationContentThirdFaile(tp.getTranslationContentThirdFaile());
					tpDB.setTranslationRelActThirdFaile(tp.getTranslationRelActThirdFaile());
					tpDB.setStudyGuideCodes(tp.getStudyGuideCodes());
					save(tpDB);
				}
			}else{
				errorList.add("预配置策略的结点没有找到或已经被删除");
			}
			
		}catch(Exception e){
			String errorMsg = "［NodeGroupPolicyService:update(NodeGroupPolicy nodeGroupPolicy)］"+tp+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			e.printStackTrace();
			logger.error(errorMsg);
		}
		m.put("errorList", errorList);//m.get("errorLst")
		return m;
	}
	
	public Map editViewMap(long nid){
		Map map = new HashMap(0);
		map.put("TrainingPolicy", get(TrainingPolicy.class, nid));//map.get("TrainingPolicy")
		map.put("nid", nid );//map.get("TrainingPolicy")
		return map;
	}
	
	public Map showViewMap(long id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(TrainingPolicy.class) ;
	}
	
	public TrainingPolicy get(long id){
		return super.get(TrainingPolicy.class,id) ;
	}
}
