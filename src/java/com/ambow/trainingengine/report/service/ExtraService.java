package com.ambow.trainingengine.report.service;

import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;
import com.ambow.trainingengine.web.data.UserDataVO;

/**
 * @author yuanjunqi
 *
 */
public class ExtraService extends ReportBaseService {
	
	/**
	 * 获取试题列表
	 * @param ids
	 * @return
	 */
	public List<Item> fetchItemListForMpc(String ids){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("from Item where id in ("+ids+")");
		List<Item> itemList = this.find(sqlBuff.toString());
		return itemList;
	}
	
	/**
	 * 获取节点组下所有的节点实例
	 * @param nodeInstanceId
	 * @return
	 */
	public List<NodeInstance> fetchNodeInstanceList(Long processInstanceId,Long nodeGroupId){
		String hsql = "from NodeInstance a where a.node.nodeGroup.id=? and a.processInstance.id=? ";
		List<NodeInstance> nodeInstanceList = this.find(hsql, nodeGroupId,processInstanceId);
		return nodeInstanceList;
	} 
	
	/**
	 * 获取组卷条件列表
	 * @param nodeId
	 * @return
	 */
	public List<PaperAssemblingRequirements> fetchPaperAssemblingRequirementsList(Long nodeId){
		String hsql = "from PaperAssemblingRequirements a where a.asfNode.id=?";
		List<PaperAssemblingRequirements> paperAssemblingRequirementsList = this.find(hsql,nodeId);
		return paperAssemblingRequirementsList;
	}
	
	/**
	 * 获取节点对象
	 * @param nodeId
	 * @return
	 */
	public NodeInstance fectchNodeInstance(Long processInstanceId , Long nodeId){
		String hsql = "from NodeInstance a where a.node.id=? and a.processInstance.id=? ";
		NodeInstance nodeInstance = (NodeInstance)this.findObjByHql(hsql, nodeId,processInstanceId);
		return nodeInstance;
	}
	
	/**
	 * 获取拓展训练试题列表
	 * @param paperAssemblingRequirementsList
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> fetchItemListForMpc(List<PaperAssemblingRequirements> paperAssemblingRequirementsList,String nodeInstanceIds,Long nodeGroupInstanceId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select distinct a.id,a.score,a.answering_time from item a ");
		sqlBuff.append(" left join grade_item_ref b on a.id=b.item_id ");
		sqlBuff.append(" left join knowledge_point_item_ref c on a.id=c.item_id ");
		sqlBuff.append(" where a.status >0 and ( ");

		int num =0;
		for(PaperAssemblingRequirements po:paperAssemblingRequirementsList){
			StringBuffer poBuff = new StringBuffer();
			if (po.getYear() != null && !po.getYear().equals("")) {
				String[] year = po.getYear().split("-");
				if (year.length == 2) {
					if (!year[0].equals(""))
						poBuff.append(" and a.year>='" + year[0].trim() + "'");
					if (!year[1].equals(""))
						poBuff.append(" and a.year<='" + year[1].trim() + "'");
				}
			}
			if (po.getValidityValue() != null && !po.getValidityValue().equals("")) {
				String[] validityValue = po.getValidityValue().split("-");
				if (validityValue.length == 2) {
					if (!validityValue[0].equals(""))
						poBuff.append(" and a.validity_Value>=" + validityValue[0].trim());
					if (!validityValue[1].equals(""))
						poBuff.append(" and a.validity_Value<=" + validityValue[1].trim());
				}
			}
			if (po.getDifficultyValue() != null && !po.getDifficultyValue().equals("")) {
				String[] difficultyValue = po.getDifficultyValue().split("-");
				if (difficultyValue.length == 2) {
					if (!difficultyValue[0].equals(""))
						poBuff.append(" and a.difficulty_Value>="+ difficultyValue[0].trim());
					if (!difficultyValue[1].equals(""))
						poBuff.append(" and a.difficulty_Value<="+ difficultyValue[1].trim());
				}
			}
			if (po.getItemTypeCode() != null && !po.getItemTypeCode().equals("")) {
				String[] itemTypeCodeArr = po.getItemTypeCode().split(",");
				if(itemTypeCodeArr.length>0){
					poBuff.append(" and ( ");
					for(int i=0;i<itemTypeCodeArr.length;i++){
						if(i==0){
							poBuff.append(" a.item_type_code='"+itemTypeCodeArr[i].trim()+"' ");
						}else{
							poBuff.append(" or a.item_type_code='"+itemTypeCodeArr[i].trim()+"' ");
						}
					}
					poBuff.append(" )");
				}
			}
			if (po.getSource() != null && !po.getSource().equals("")){
				String[] sourceArr = po.getSource().split(",");
				if(sourceArr.length>0){
					poBuff.append(" and ( ");
					for(int i=0;i<sourceArr.length;i++){
						if(i==0){
							poBuff.append(" a.source='"+sourceArr[i].trim()+"' ");
						}else{
							poBuff.append(" or a.source='"+sourceArr[i].trim()+"' ");
						}
					}
					poBuff.append(" )");
				}
			}
			if (po.getOriginalPaperCode() != null && !po.getOriginalPaperCode().equals("")){
				poBuff.append(" and a.original_Paper_Code ='" + po.getOriginalPaperCode().trim() + "'");
			}
			if (po.getRegionCode() != null && !po.getRegionCode().equals("")){
				poBuff.append(" and a.region_code ='" + po.getRegionCode().trim() + "'");
			}
			if (po.getSubjectCode() != null && !po.getSubjectCode().equals("")){
				poBuff.append(" and a.subject_code ='" + po.getSubjectCode().trim() + "'");
			}
			if (po.getGradeCode() != null && !po.getGradeCode().equals("")){
				poBuff.append(" and b.grade_code = '"+po.getGradeCode().trim()+"'");
			}
			if (po.getKnowledgePointCode() != null && !po.getKnowledgePointCode().equals("")) {
				String[] knowledgePoints = po.getKnowledgePointCode().split(",");
				if(knowledgePoints.length>0){
					poBuff.append(" and ( ");
					for(int i=0;i<knowledgePoints.length;i++){
						if(i==0){
							poBuff.append(" c.knowledge_point_code='"+knowledgePoints[i].trim()+"' ");
						}else{
							poBuff.append(" or c.knowledge_point_code='"+knowledgePoints[i].trim()+"' ");
						}
					}
					poBuff.append(" )");
				}
			}
			/*适用对象：文、理科*/
			if(po.getApplicableObject()!=null&&!po.getApplicableObject().equals("")){
				poBuff.append(" and a.applicable_object in (" + po.getApplicableObject().trim() + ") ");
			}
			/* 课程版本 */
			if (po.getCourseVersions() != null && !po.getCourseVersions().equals("")) {
				String[] courseVersions = po.getCourseVersions().split(",");
				if(courseVersions.length>0){
					poBuff.append(" and ( ");
					for(int i=0;i<courseVersions.length;i++){
						if(i==0){
							poBuff.append(" a.course_Versions='"+courseVersions[i].trim()+"' ");
						}else{
							poBuff.append(" or a.course_Versions='"+courseVersions[i].trim()+"' ");
						}
					}
					poBuff.append(" )");
				}
			}
			/* 试题轮次 */
			if(po.getReviewRound()!=null&&!po.getReviewRound().equals("")){
				poBuff.append(" and a.review_round in (" + po.getReviewRound().trim() + ") ");
			}
			if(num==0){
				sqlBuff.append(" ( "+poBuff.substring(4)+" ) ");
			}else{
				sqlBuff.append(" or ( "+poBuff.substring(4)+" ) ");
			}
			num = num +1;
		}
		sqlBuff.append(" ) and a.id not in ( ");
		sqlBuff.append(" 	select item_id from current_answers_status where node_instance_id in ("+nodeInstanceIds+") ");
		sqlBuff.append(" 	or node_instance_id="+nodeGroupInstanceId);
		sqlBuff.append(" )");
		List<Map<String,Object>> itemList =this.getJdbcTemplate().queryForList(sqlBuff.toString());

		return itemList;
	}
	
	/**
	 * 获取当前的考试状态
	 * @param nodeInstance
	 */
	public CurrentTestStatus getCurrentTestStatus(NodeInstance nodeInstance) {
		CurrentTestStatus currentTestStatus = this.get(CurrentTestStatus.class, nodeInstance.getId());
		return currentTestStatus;
	}
	
	/**
	 * 获取历史的考试状态
	 * @param nodeInstance
	 */
	public List<HistoryTestStatus> getHisTestStatus(NodeInstance nodeInstance) {

		List<HistoryTestStatus> historyTestStatusList = 
			(List<HistoryTestStatus>)this.find("from HistoryTestStatus h where h.asfNodeInstance.id=?", nodeInstance.getId());
		return historyTestStatusList;
	}
	
	/**
	 * 获取流程实例
	 * @param userDataVO
	 * @return
	 */
	public ProcessInstance getProcessInstance(UserDataVO userDataVO) {
		ProcessInstance processInstance = 
			(ProcessInstance)this.get(ProcessInstance.class, userDataVO.getProcessInstanceId());
		return processInstance;
	}
	
	/**
	 * 获取流程策略状态
	 * @param userDataVO
	 * @return
	 */
	public ProcessTrainingStatus getProcessTrainingStatus(UserDataVO userDataVO) {
		ProcessTrainingStatus processTrainingStatus = 
			(ProcessTrainingStatus)this.get(ProcessTrainingStatus.class, userDataVO.getProcessInstanceId());
		return processTrainingStatus;
	}
}
