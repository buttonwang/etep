package com.ambow.trainingengine.web.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.AnnotationSessionFactoryBeanEx;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.PauseInfo;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.policy.domain.NodeGroupPolicy;
import com.ambow.trainingengine.web.data.AccuracyRateVO;
import com.ambow.trainingengine.web.data.LearningProcessRateVO;
import com.ambow.trainingengine.web.data.NodeInstanceInfoVO;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.PracticeListVO;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.StarPaperVO;
import com.ambow.trainingengine.web.data.TotalMasteryRateVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.ambow.trainingengine.web.util.RepuestDictForWeb;
import com.ambow.util.ClassLoaderUtil;

public class MainPageService extends BaseWebService implements Serializable {

	private static final long serialVersionUID = 6748463875577540602L;
	
	private static final Logger logger = Logger.getLogger(AnnotationSessionFactoryBeanEx.class); 
		
	/**
	 * 获取已做节点数量
	 * @param processInstanceId
	 * @return
	 */
	public Integer getNodeCount(Long processInstanceId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(*) from `asf_node_instance` where process_instance_id=? and node_status>0");
		Integer count = this.getJdbcTemplate().queryForInt(sqlBuff.toString(), processInstanceId);
		return count;
	}
	/**
	 * 获取首页当前任务模块信息 ky
	 */
	@SuppressWarnings("unchecked")
	public UserDataVO getCurrentNodeInstanceInfoVO(UserDataVO userData){		
		String sql1="select a.process_definition_id,a.node_id,b.node_group_id,b.name,c.process_instance_id,e.id,f.name as process_name,c.learning_process_rate,"
			+" c.total_score,c.total_accuracy_rate,c.total_mastery_rate,c.sum_incorrect_items,c.sum_correct_items,c.sum_unfinished_items,c.total_items_num,"
			+" c.sum_zero_star_items,c.sum_half_star_items,c.sum_one_star_items,c.sum_two_star_items,c.unsure_mark_items,c.customer_exam_time, "
			+" c.sum_three_star_items,c.sum_four_star_items,c.sum_five_star_items,a.process_status,d.items_num,d.answering_time,c.first_training_time"	
			+" from asf_process_instance a " 
			+" left outer join asf_node b on b.id=a.node_id "
			+" left outer join asf_node_instance e on (e.node_id=b.id and a.id=e.process_instance_id) "
			+" left outer join paper_assembling_policy d on b.id=d.node_id, "
			+" process_training_status c,asf_process_definition f "
			+" where a.id=c.process_instance_id and f.id=a.process_definition_id "
			+" and a.actor=? and a.process_definition_id=?";
			//+" from asf_process_instance a,asf_node b,process_training_status c,paper_assembling_policy d,asf_node_instance e,asf_process_definition f"
			//+" where a.id=c.process_instance_id and b.id=a.node_id and e.node_id=a.node_id and b.id=d.node_id and f.id=a.process_definition_id and a.actor=? and a.process_definition_id=? ";
		List list1=this.getJdbcTemplate().queryForList(sql1, new Object[]{
				userData.getUserID(),Long.parseLong(userData.getRefID())});
		if(list1!=null&&list1.size()>0){			
			Map map=(Map)list1.get(0);
			if(map.get("node_id")!=null)
				userData.setCurrentNodeId(Long.valueOf(map.get("node_id").toString()));
			else
				userData.setCurrentNodeId(-1);
			if(map.get("name")!=null)
				userData.setCurrentNodeName(map.get("name").toString());
			if(map.get("id")!=null)
				userData.setCurrentNodeInstanceId(Long.valueOf(map.get("id").toString()));
			else
				userData.setCurrentNodeInstanceId(-1);
			userData.setProcessInstanceId((Long)map.get("process_instance_id"));
			userData.setTotalAccuracyRate(((Float)map.get("total_accuracy_rate")).intValue());
			userData.setTotalMasteryRate(((Float)map.get("total_mastery_rate")).intValue());
			userData.setSumUnfinishedItems((Integer)map.get("sum_unfinished_items"));//未作总题数
			userData.setSumIncorrectItems((Integer)map.get("sum_incorrect_items"));
			userData.setUnsureMarkItems((Integer)map.get("unsure_mark_items"));
			userData.setTotalScore((Float)map.get("total_score"));
			userData.setTotalItemsNum((Integer)map.get("total_items_num"));//总题数
			userData.setSumCorrectItems((Integer)map.get("sum_correct_items"));
			userData.setLearningProcessRate(((Float)map.get("learning_process_rate")).intValue());
			userData.setSumZeroStarItems((Integer)map.get("sum_zero_star_items"));
			userData.setSumHalfStarItems((Integer)map.get("sum_half_star_items"));
			userData.setSumOneStarItems((Integer)map.get("sum_one_star_items"));
			userData.setSumTwoStarItems((Integer)map.get("sum_two_star_items"));
			userData.setSumThreeStarItems((Integer)map.get("sum_three_star_items"));
			userData.setSumFourStarItems((Integer)map.get("sum_four_star_items"));
			userData.setSumFiveStarItems((Integer)map.get("sum_five_star_items"));
			userData.setFirstTrainingTime((Date)map.get("first_training_time"));
			userData.setProcessDefinitionId((Long)map.get("process_definition_id"));
			userData.setProcessStatus((Integer)map.get("process_status"));
			userData.setProcessName((String)map.get("process_name"));
			if(map.get("customer_exam_time")!=null)
				userData.setCustomerExamTime((Date)map.get("customer_exam_time"));//用户自定义时间
			if(map.get("items_num")!=null)
				userData.setCurrentNodeItemNum((Integer)map.get("items_num"));
			if(map.get("answering_time")!=null)
				userData.setCurrentNodeTime((Integer)map.get("answering_time")/60);//转换成分钟
			userData.setSumfinishedItems(userData.getSumIncorrectItems()+userData.getSumCorrectItems());//已训练题数=正确题数+错误题数
			userData.setShowNodeGroupVOList(this.getShowNodeGroupVOList(userData.getProcessInstanceId()));
			if(map.get("node_group_id")!=null){
				userData.setUnitNodeId(this.getShowNodeGroupId((Long)map.get("node_group_id")));
				List list=this.getJdbcTemplate().queryForList("select name from asf_node where id=?",new Object[]{userData.getUnitNodeId()});
				if(list!=null&&list.size()>0)
					userData.setUnitNodeName(((Map)list.get(0)).get("name").toString());
			}else if(map.get("node_id")==null){
				userData.setUnitNodeId(userData.getShowNodeGroupVOList().get(0).getNodeId());
				userData.setUnitNodeName(userData.getShowNodeGroupVOList().get(0).getNodeName());
			}			
		}
		return userData;
	}
	/**
	 * 获取当前所属节点单元通过在节点显示策略表中判断
	 */
	public Long getShowNodeGroupId(Long nodeId){
		NodeGroupPolicy nodeGroupPolicy=(NodeGroupPolicy) this.getHibernateTemplate().get(NodeGroupPolicy.class, nodeId);
	//	if(nodeGroupPolicy==null)
	//		return null;
		if(nodeGroupPolicy!=null&&nodeGroupPolicy.getIsDisplayModule().intValue()==1)
			return nodeId;
		else{
			Node node=(Node) this.getHibernateTemplate().get(Node.class, nodeId);
			if(node.getNodeGroup()==null)
				return null;
			return getShowNodeGroupId(node.getNodeGroup().getId());
		}
	}
	/**
	 * 根据节点显示策略，装载逻辑显示模块及下面所有子节点数据
	 */
	@SuppressWarnings("unchecked")
	public List<ShowNodeGroupVO> getShowNodeGroupVOList(Long processInstanceId){
		List<ShowNodeGroupVO> outList=new ArrayList<ShowNodeGroupVO>();
		List list=this.getJdbcTemplate().queryForList(
				"select a.id,a.name,c.id node_instance_id from asf_node a,node_group_policy b,asf_node_instance c where a.id=c.node_id and a.id=b.node_id and b.is_display_module=1 "
				+" and a.node_type='GROUP' and c.process_instance_id=? order by a.order_num ",processInstanceId);
		if(list!=null||list.size()>0)
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				ShowNodeGroupVO vo=new ShowNodeGroupVO();
				vo.setNodeId((Long)map.get("id"));
				vo.setNodeName((String)map.get("name"));
				vo.setNodeInstanceId((Long)map.get("node_instance_id"));
				vo=this.getShowNodeGroupVO(vo,vo.getNodeId());
				outList.add(vo);
			}
		return outList;
	}
	
	@SuppressWarnings("unchecked")
	public ShowNodeGroupVO getShowNodeGroupVO(ShowNodeGroupVO vo,Long nodeId){
		List nodeList=this.getHibernateTemplate().find("from Node where nodeGroup.id=? order by orderNum", nodeId);
		if(nodeList!=null&&nodeList.size()>0)
			for(int i=0;i<nodeList.size();i++){
				Node node=(Node)nodeList.get(i);
				if(node.getNodeType().equals(NodeType.GROUP))
					vo=this.getShowNodeGroupVO(vo,node.getId());
				else{
					if(vo.getChildNodeIds()==null||vo.getChildNodeIds().equals(""))
						vo.setChildNodeIds(String.valueOf(node.getId()));
					else
						vo.setChildNodeIds(vo.getChildNodeIds()+","+node.getId());
				}
			}
		return vo;
	}
	
	/**
	 * 获取首页当前节点通过人数
	 */
	public int getPassNum(UserDataVO userData){
		return this.getJdbcTemplate().queryForInt("select count(a.id) from asf_node_instance a,process_training_status b"
				+" where a.process_instance_id=b.process_instance_id and (a.node_status=2 or a.node_status=3) and b.class_num=? and a.node_id=?"
				, new Object[]{userData.getClassCode(),userData.getCurrentNodeId()});	
		
	}
	
	/**
	 * 获取首页当前节点正在训练人数
	 */
	public int getTestingNum(String classNum,long nodeId){
		return  this.getJdbcTemplate().queryForInt("select count(c.id) from process_training_status b,asf_process_instance c"
				+" where b.process_instance_id=c.id and b.class_num=? and c.node_id=?"
				, new Object[]{classNum,nodeId});
	}
	
	/**
	 * 获取总进度完整班级排名
	 */
	
	@SuppressWarnings("unchecked")
	public List<LearningProcessRateVO> getLearningProcessRateList(UserDataVO userData){
		  List list=this.getJdbcTemplate().queryForList("select a.process_instance_id,a.learning_process_rate,c.real_name from process_training_status a,"
				+"asf_process_instance b,webuser c where a.process_instance_id=b.id and b.actor=c.id and a.class_num=? order by a.learning_process_rate desc"				
				, new Object[]{userData.getClassCode()});		  
		  List<LearningProcessRateVO> outList=new ArrayList<LearningProcessRateVO>();
		  if(list!=null&&list.size()>0){
			  for(int i=0;i<list.size();i++){
				  LearningProcessRateVO vo=new LearningProcessRateVO();
				  Map map=(Map)list.get(i);
				  vo.setProcessInstanceId(Long.parseLong(map.get("process_instance_id").toString()));
				  vo.setLearningProcessRate(((Float)map.get("learning_process_rate")).intValue());
				  vo.setUserName(map.get("real_name").toString());
				  outList.add(vo);
			  }
		  }
		  
		  return outList;
	}
	/**
	 * 获取总正确率完整班级排名
	 */
	
	@SuppressWarnings("unchecked")
	public List<AccuracyRateVO> getAccuracyRateList(UserDataVO userData){
		  List list=this.getJdbcTemplate().queryForList("select a.process_instance_id,a.total_accuracy_rate,c.real_name from process_training_status a,"
				+"asf_process_instance b,webuser c where a.process_instance_id=b.id and b.actor=c.id and a.class_num=? order by a.total_accuracy_rate desc"				
				, new Object[]{userData.getClassCode()});		  
		  List<AccuracyRateVO> outList=new ArrayList<AccuracyRateVO>();
		  if(list!=null&&list.size()>0){
			  for(int i=0;i<list.size();i++){
				  AccuracyRateVO vo=new AccuracyRateVO();
				  Map map=(Map)list.get(i);
				  vo.setProcessInstanceId(Long.parseLong(map.get("process_instance_id").toString()));
				  vo.setAccuracyRate(((Float)map.get("total_accuracy_rate")).intValue());
				  vo.setUserName(map.get("real_name").toString());
				  outList.add(vo);
			  }
		  }
		  
		  return outList;
	}
	/**
	 * 获取总正确率排名前10名列表
	 */
	public List<AccuracyRateVO> getAccuracyRateTopList(UserDataVO userData){
		List<AccuracyRateVO> outList=new ArrayList<AccuracyRateVO>();
		List<AccuracyRateVO> inList=this.getAccuracyRateList(userData);
		for(int i=0;i<inList.size();i++){
			outList.add(inList.get(i));
			if(i==9)
				break;
		}
		return outList;
	}
	/**
	 * 获取总成绩班级排名
	 */
	
	@SuppressWarnings("unchecked")
	public int getScoreOrdeNum(UserDataVO userData){
		  int outValue=0;
		List list=this.getJdbcTemplate().queryForList("select a.process_instance_id from process_training_status a,"
				+"asf_process_instance b,webuser c where a.process_instance_id=b.id and b.actor=c.id and a.class_num=? order by a.total_score desc"				
				, new Object[]{userData.getClassCode()});
		  if(list!=null&&list.size()>0){
			  for(int i=0;i<list.size();i++){				 
				  Map map=(Map)list.get(i);
				  outValue++;
				  if(userData.getProcessInstanceId()==Long.parseLong(map.get("process_instance_id").toString()))
					 break;					     
			  }
		  }
		  if(outValue==0)
			  outValue=1;
		  return outValue;
	}
	/**
	 * 获取总进度排名名次
	 */
	public UserDataVO getLearningProcessRateOrder(UserDataVO userData){
		 List<LearningProcessRateVO> list=this.getLearningProcessRateList(userData);
		 int j=0;
		 for(int i=0;i<list.size();i++){
			 j++;
			 if(list.get(i).getProcessInstanceId()==userData.getProcessInstanceId())
				 break;
		 }
		 if(j==0)
			 j=1;
		 userData.setLearningProcessRateOrder(j);
	     return	userData;
	}
	/**
	 * 获取总进度排名前10名列表
	 */
	public List<LearningProcessRateVO> getLearningProcessRateTopList(UserDataVO userData){
		List<LearningProcessRateVO> outList=new ArrayList<LearningProcessRateVO>();
		List<LearningProcessRateVO> inList=this.getLearningProcessRateList(userData);
		for(int i=0;i<inList.size();i++){
			outList.add(inList.get(i));
			if(i==9)
				break;
		}
		return outList;
	}
	
	
	/**
	 * 获取首页总掌握度列表
	 */
	@SuppressWarnings("unchecked")
	public List<TotalMasteryRateVO> getTotalMasteryRateList(UserDataVO userData){
		  List list=this.getJdbcTemplate().queryForList("select a.process_instance_id,a.total_mastery_rate,c.real_name from process_training_status a,"
				+"asf_process_instance b,webuser c where a.process_instance_id=b.id and b.actor=c.id and a.class_num=? order by a.total_mastery_rate desc"				
				, new Object[]{userData.getClassCode()});		  
		  List<TotalMasteryRateVO> outList=new ArrayList<TotalMasteryRateVO>();
		  if(list!=null&&list.size()>0){
			  for(int i=0;i<list.size();i++){
				  TotalMasteryRateVO vo=new TotalMasteryRateVO();
				  Map map=(Map)list.get(i);
				  vo.setProcessInstanceId(Long.parseLong(map.get("process_instance_id").toString()));
				  vo.setTotalMasteryRate(((Float)map.get("total_mastery_rate")).intValue());
				  vo.setUserName(map.get("real_name").toString());
				  outList.add(vo);
			  }
		  }
		  
		  return outList;
	}
	
	/**
	 * 获取总掌握度排名名次
	 */
	public UserDataVO getTotalAccuracyRateOrder(UserDataVO userData){
		 List<AccuracyRateVO> list=this.getAccuracyRateList(userData);
		 int j=0;
		 for(int i=0;i<list.size();i++){
			 j++;
			 if(list.get(i).getProcessInstanceId()==userData.getProcessInstanceId())
				 break;
		 }
		 if(j==0)
			 j=1;
		 userData.setTotalAccuracyRateOrder(j);
	     return	userData;
	}
	
	/**
	 * 获取总掌握度排名名次
	 */
	public UserDataVO getTotalMasteryRateOrder(UserDataVO userData){
		 List<TotalMasteryRateVO> list=this.getTotalMasteryRateList(userData);
		 int j=0;
		 for(int i=0;i<list.size();i++){
			 j++;
			 if(list.get(i).getProcessInstanceId()==userData.getProcessInstanceId())
				 break;
		 }
		 if(j==0)
			 j=1;
		 userData.setTotalMasteryRateOrder(j);
	     return	userData;
	}
	/**
	 * 获取总掌握度排名前10名列表
	 */
	public List<TotalMasteryRateVO> getTotalMasteryRateTopList(UserDataVO userData){
		List<TotalMasteryRateVO> outList=new ArrayList<TotalMasteryRateVO>();
		List<TotalMasteryRateVO> inList=this.getTotalMasteryRateList(userData);
		for(int i=0;i<inList.size();i++){
			outList.add(inList.get(i));
			if(i==9)
				break;
		}
		return outList;
	}	
	
	/**
	 * 获取最后10个训练的最新统计数据
	 */
	@SuppressWarnings("unchecked")
	//public List<PracticeListVO> getPracticeList(UserDataVO userData){
	public UserDataVO getPracticeList(UserDataVO userData){
		 List list=this.getJdbcTemplate().queryForList(
				 "select a.id,b.name,a.node_id,c.accuracy_rate,c.mastery_rate,c.end_time from asf_node_instance a,asf_node b,current_test_status c"
			    +" where a.node_id=b.id and b.node_type='PRACTICE' and a.id=c.node_instance_id and a.process_instance_id=? order by b.order_num desc"				
				, new Object[]{userData.getProcessInstanceId()});
		 List<PracticeListVO> outList=new ArrayList<PracticeListVO>();		 
		 if(list!=null&&list.size()>0){
			 for(int i=0;i<list.size();i++){
				 Map map=(Map)list.get(i);
				 PracticeListVO vo=new PracticeListVO();
				 vo.setNodeInstanceId(Long.parseLong(map.get("id").toString()));
				 vo.setNodeId(Long.parseLong(map.get("node_id").toString()));
				 vo.setName(map.get("name").toString());
				 vo.setAccuracyRate(Float.parseFloat(map.get("accuracy_rate").toString()));
				 vo.setMasteryRate(Float.parseFloat(map.get("mastery_rate").toString()));
				 vo.setDate(new SimpleDateFormat("MM.dd").format((Date)map.get("end_time")));
				 outList.add(vo);
				 if(i==9)
					 break;
				 
			 }
			 Collections.reverse(outList);
		 }
		 userData.setPracticeList(outList);
		return userData;
	} 
	
	/**
	 * 获取首页当前单元节点列表信息
	 */
	@SuppressWarnings("unchecked")
	public UserDataVO getNodeInstanceInfoList(UserDataVO userData) {
		//获取暂停节点列表
		String nodeIds=this.getPauseNodeIds(userData);
		
		userData.setPauseNodeIds(nodeIds);
		String ids="";
		for(ShowNodeGroupVO vo:userData.getShowNodeGroupVOList()){
			if(vo.getNodeId().longValue()==userData.getUnitNodeId()){
				ids=vo.getChildNodeIds();
				break;
			}
		}
		List list=this.getJdbcTemplate().queryForList(
				"select a.id,a.node_id,b.name,b.node_type,a.node_status,c.score,c.mastery_rate,c.node_instance_id "
				+" from asf_node_instance a left join current_test_status c on a.id=c.node_instance_id,asf_node b"
				//+" where a.node_id=b.id and a.process_instance_id=? and b.node_group_id=? order by b.order_num "
				+" where a.node_id=b.id and a.process_instance_id=? and b.id in("+ids+") order by b.order_num "
			   // , new Object[]{userData.getProcessInstanceId(),userData.getUnitNodeId()});
		  		, new Object[]{userData.getProcessInstanceId()});
		 List<NodeInstanceInfoVO> outList=new ArrayList<NodeInstanceInfoVO>();	
		 if(list==null||list.size()==0)
			 return userData;
		 int nodeListDivWidth=0;
		 for(int i=0;i<list.size();i++){
				 Map map=(Map)list.get(i);
				 NodeInstanceInfoVO vo=new NodeInstanceInfoVO();
				 vo.setNodeInstanceId(Long.parseLong(map.get("id").toString()));
				 vo.setNodeId(Long.parseLong(map.get("node_id").toString()));
				 vo.setNodeName(map.get("name").toString());
				 vo.setNodeStatus(Integer.parseInt(map.get("node_status").toString()));
				 if(vo.getNodeStatus()>0)
					vo.setTag(1);//控制页面节点是否显示链接
				 vo.setNodeType(map.get("node_type").toString());
			//	 vo.setPauseStatus(Integer.parseInt(map.get("pause_status").toString()));
			//	 if(vo.getPauseStatus()==0){
			//		 vo.setScore(Float.parseFloat(map.get("score").toString()));
			//		 vo.setMasteryRate(Float.parseFloat(map.get("mastery_rate").toString()));
			//	 }

				String nodeStatus="";
				
				if(nodeIds.indexOf(String.valueOf(vo.getNodeInstanceId()))>-1)
					nodeStatus="PAUSE";
				else if(userData.getCurrentNodeId()==vo.getNodeId())
					nodeStatus="CURRENT";
				else 
					switch(vo.getNodeStatus()){
						case RepuestDictForWeb.NODE_STATUS_UNDO:
							nodeStatus="UNDO";							
							break;
						case RepuestDictForWeb.NODE_STATUS_PASS:
							nodeStatus="PASS";
							break;
						case RepuestDictForWeb.NODE_STATUS_NOPASS:
							nodeStatus="NOPASS";
							break;
						case RepuestDictForWeb.NODE_STATUS_SKIP:
							nodeStatus="PASS";
							break; 
						default:   
							break; 
					}					
							
				Class clazz=ClassLoaderUtil.loadClass("com.ambow.trainingengine.web.util.RepuestDictForWeb");
				String fieldName="NODE_"+vo.getNodeType()+"_"+nodeStatus;
				try{
					
					vo.setCssClassName(
							clazz.getField(fieldName).get("").toString());
				}catch(Exception e){
					logger.error("In class RepuestDictForWeb,Cannot find field:"+fieldName);
					//System.out.println("In class RepuestDictForWeb,Cannot find field:"+fieldName);
				}
				// ****（单元名），通过训练，我的成绩92，掌握度86% 有23人正在训练本单元
				
				if(vo.getNodeType().equals(NodeType.PRACTICE.toString())&&!nodeStatus.equals("UNDO")){
					vo.setScore((Float)map.get("score"));
					vo.setMasteryRate((Float)map.get("mastery_rate"));
					vo.setTestingNum(this.getJdbcTemplate().queryForInt(
							"select count(a.id) from asf_process_instance a,process_training_status b"
							+" where a.id=b.process_instance_id and b.class_num=? and a.node_id=?"
							, new Object[]{userData.getClassCode(),vo.getNodeId()}));
					String titleInfo=vo.getNodeName()+",";
					if(nodeStatus.equals("PASS"))
						titleInfo+="通过训练,";
					else if(nodeStatus.equals("NOPASS"))
						titleInfo+="没有通过训练,";
					if(vo.getScore()!=null)
						titleInfo+="我的成绩"+vo.getScore()+",掌握度"+vo.getMasteryRate()+"%,";
					titleInfo+="有"+vo.getTestingNum()+"人正在训练本单元";
					vo.setTitleInfo(titleInfo);
				}
				//确定当前节点
				if(vo.getNodeId()>0&&vo.getNodeId()==userData.getCurrentNodeId()){
					//更新当前节点正在训练人数
					userData.setCurrentTestingNum(this.getJdbcTemplate().queryForInt(
							"select count(a.id) from asf_process_instance a,process_training_status b"
							+" where a.id=b.process_instance_id and b.class_num=? and a.node_id=?"
							, new Object[]{userData.getClassCode(),vo.getNodeId()}
							));
					//更新当前节点通过训练人数
					userData.setCurrentPassNum(this.getJdbcTemplate().queryForInt(
							"select count(a.id) from asf_process_instance a,process_training_status b,asf_node_instance c"
							+" where a.id=b.process_instance_id and c.process_instance_id=a.id and b.class_num=? and c.node_id=? and node_status=?"
							, new Object[]{userData.getClassCode(),vo.getNodeId(),RepuestDictForWeb.NODE_STATUS_PASS}
							));					
				}
				if(vo.getNodeType().equals(NodeType.PRACTICE.toString())||vo.getNodeType().equals(NodeType.UNITTEST.toString()))
					nodeListDivWidth+=29;
				else
					nodeListDivWidth+=54;
				
				outList.add(vo);
		}
		 nodeListDivWidth+=(outList.size()+1)*10;
		 userData.setNodeListDivWidth(nodeListDivWidth);
		 userData.setNodeInstanceInfoList(outList);
		return userData;
	}
	/**
	 * 获取暂停节点实例id列表
	 */
	@SuppressWarnings("unchecked")
	public String getPauseNodeIds(UserDataVO userData){
		
		List nodeIdsList=new ArrayList();
		if(userData.getProcessStatus()==RepuestDictForWeb.PROCESS_STATUS_PAUSE){			
			nodeIdsList=this.getJdbcTemplate().queryForList(
					"select distinct(a.node_instance_id)"
					+" from pause_answer_status a"
					+" where a.process_instance_id=?"				
				    , new Object[]{userData.getProcessInstanceId()});			
		}		
		String nodeIds="";
		if(nodeIdsList.size()>0)
			for(int j=0;j<nodeIdsList.size();j++){
				if(j>0)
					nodeIds+=",";
				nodeIds+=((Map)nodeIdsList.get(j)).get("node_instance_id").toString();
			}
		return nodeIds;
	}
	
	/**
	 * 获取星卷榜 for ky
	 */
	@SuppressWarnings("unchecked")
	public List<StarPaperVO> getStarPaperList(UserDataVO userData,int lineCount){
		List list=this.getJdbcTemplate().queryForList(
				"select a.id,c.name,b.first_test_accuracy_rate,b.accuracy_rate,b.mastery_rate,b.end_time "
				+"from asf_node_instance a,current_test_status b,asf_node c where "
				+" a.id=b.node_instance_id and a.node_id=c.id and c.node_type='PRACTICE' and a.process_instance_id=? order by b.mastery_rate"
				, new Object[]{userData.getProcessInstanceId()});
		List<StarPaperVO> outList=new ArrayList<StarPaperVO>();
		if(list!=null&&list.size()>0)
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				StarPaperVO vo=new StarPaperVO();
				vo.setInstanceId((Long)map.get("id"));
				vo.setNodeName(map.get("name").toString());
				vo.setFirstAccuracyRate((Float)map.get("first_test_accuracy_rate"));
				vo.setAccuracyRate(((Float)map.get("accuracy_rate")).intValue());
				vo.setMasteryRate(((Float)map.get("mastery_rate")).intValue());
				vo.setTime(new SimpleDateFormat("yyyy-MM-dd")
						.format((Date)map.get("end_time")));
				outList.add(vo);
				if(i==lineCount-1)
					break;
			}
		return outList;
	}
	
	/**
	 * 获取星卷榜 for mpc
	 */
	@SuppressWarnings("unchecked")
	public List<StarPaperVO> getStarPaperListForMpc(UserDataVO userData,int lineCount){
		String sql="select n.name node_group_name,a.name,c.mastery_rate,c.total_score,c.score,c.sum_correct_items,c.sum_incorrect_items,a.order_num,a.node_type "
			+"from asf_node a left outer join asf_node n on a.node_group_id=n.id,asf_node_instance i,current_test_status c "
			+"where a.id=i.node_id and i.id=c.node_instance_id and a.node_type in('PRACTICE','PHASETEST','UNITTEST') "
			+"and i.process_instance_id=? order by c.mastery_rate ";
		List list=this.getJdbcTemplate().queryForList(sql, new Object[]{userData.getProcessInstanceId()});
		List<StarPaperVO> outList=new ArrayList<StarPaperVO>();
		if(list!=null&&list.size()>0)
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				StarPaperVO vo=new StarPaperVO();
				vo.setNodeName(map.get("name").toString());
				vo.setNodeGroupName(map.get("node_group_name").toString());				
				vo.setMasteryRate((int)((Float)map.get("mastery_rate")).floatValue());
				vo.setTotalScore(Float.valueOf(map.get("total_score").toString()));
				vo.setScore((Float)map.get("score"));
				vo.setCorrectItems((Integer)map.get("sum_correct_items"));
				vo.setTotalItems((Integer)map.get("sum_correct_items")+(Integer)map.get("sum_incorrect_items"));
				vo.setOrderNum(map.get("order_num").toString());
				vo.setNodeType(map.get("node_type").toString());
				outList.add(vo);
				if(i==lineCount-1)
					break;
			}
		return outList;
	}
	
	/**
	 * 获取流程所有单元节点
	 */
	@SuppressWarnings("unchecked")
	public List<NodeVO> getGroupNodeIdList(UserDataVO userData){
		List list=this.getJdbcTemplate().queryForList(
				"select a.name,a.id from asf_node a "
				+"where a.node_type='GROUP' and a.process_definition_id=? order by a.order_num"
				, new Object[]{Long.parseLong(userData.getRefID())});
		List<NodeVO> outList=new ArrayList<NodeVO>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				NodeVO vo=new NodeVO();
				vo.setNodeDefinitionId((Long)map.get("id"));
				vo.setNodeName((String)map.get("name"));
				outList.add(vo);
			}			
		}
		return outList;
	}
	/**
	 * 获取特定单元中所有节点试卷信息,总学习任务页面
	 */
	/*@SuppressWarnings("unchecked")
	public List<NodeVO> getPaperInfoList(Long unitNodeId,UserDataVO userData){
		List list=this.getJdbcTemplate().queryForList(
				"select v.node_type,v.name,v.node_id,b.node_instance_id,v.items_num,v.answering_time,v.right_rate_for_pass,b.accuracy_rate,a.node_status"
				+" from v_paperInfo v,asf_node_instance a left outer join current_test_status b on a.id=b.node_instance_id"
				+"where v.node_id=a.id and a.node_group_id=? and a.process_instance_id=? order by v.order_num"
				, new Object[]{Long.parseLong(userData.getRefID())});
		List<NodeVO> outList=new ArrayList<NodeVO>();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map=(Map)list.get(i);
				NodeVO vo=new NodeVO();
				vo.setNodeDefinitionId((Long)map.get("node_id"));
				vo.setNodeName((String)map.get("name"));
				vo.setNodeType((String)map.get("node_type"));
				vo.setNodeInstanceId((Long)map.get("node_instance_id"));
				vo.setItemsNum((Integer)map.get("items_num"));
				vo.setAnsweringTime((Integer)map.get("answering_time")/60);//把秒换算成分钟
				vo.setRightRateForPass((Float)map.get("right_rate_for_pass"));
				vo.setAccuracyRate((Float)map.get("accuracy_rate"));
				vo.setAnsweringTime((Integer)map.get("node_status"));
				outList.add(vo);				
			}			
		}
		return outList;
	}*/
	/**
	 * 获取标题信息
	 * @param pauseInfo
	 * @param userData
	 * @return
	 */
	public String getTitleInfoForKy(PauseInfo pauseInfo,UserDataVO userData){
		String outStr="";
		if(pauseInfo.getNodeInstanceId()==null||pauseInfo.getNodeInstanceId().intValue()==0){
			outStr+="<span class='f26px fonth fB cYellow'>"+userData.getProcessName()
				+"</span>&nbsp;&nbsp;";
		}else{
			NodeInstance nodeInstance=this.get(NodeInstance.class,pauseInfo.getNodeInstanceId());
			Node node=nodeInstance.getNode();
			if(node.getNodeType().equals(NodeType.GROUP.toString())){
				outStr+="<span class='f26px fonth fB cYellow'>"+node.getNodeGroup().getName()
				+"</span>&nbsp;&nbsp;<span class='f16px fonth fB cYellow'>"+node.getName()+"</span> ";
			}else{
				outStr+="<span class='f26px fonth fB cYellow'>"+node.getName()
				+"</span>&nbsp;&nbsp;";
			}
		}
		if(pauseInfo.getTestStatus().intValue()<4)
			return outStr;
		
		float type=pauseInfo.getPaperAssemItemType();
		String typeStr="";
		if(type==0)
			typeStr="0星";
		else if(type==0.5)
			typeStr="半星";
		else if(type==1)
			typeStr="1星";
		else if(type==2)
			typeStr="2星";
		else if(type==3)
			typeStr="3星";
		else if(type==4)
			typeStr="4星";
		else if(type==5)
			typeStr="5星";
		else if(type==11)
			typeStr="未答";
		else if(type==12)
			typeStr="错";
		else if(type==14)
			typeStr="正确";
		else if(type==15)
			typeStr="疑问";
		else if(type==-1)
			typeStr="全部";
		outStr+="<span class='f26px fonth fB cYellow'>"+typeStr+"题重练</span>";
		return outStr;
	}
	
	/**
	 * 获取标题信息
	 * @param pauseInfo
	 * @param userData
	 * @return
	 */
	public String getTitleInfoForMpc(PauseInfo pauseInfo,UserDataVO userData){
		String outStr="";
		if(pauseInfo.getNodeInstanceId()==null||pauseInfo.getNodeInstanceId().intValue()==0){
			outStr+=userData.getProcessName();
		}else{
			NodeInstance nodeInstance=this.get(NodeInstance.class,pauseInfo.getNodeInstanceId());
			Node node=nodeInstance.getNode();
			if(node.getNodeType().equals(NodeType.GROUP.toString())){
				outStr+=node.getNodeGroup().getName()+"&nbsp;&nbsp;"+node.getName();
			}else{
				outStr+=node.getName()+"&nbsp;&nbsp;";
			}
		}
		if(pauseInfo.getTestStatus().intValue()<3)
			return outStr;
		
		float type=pauseInfo.getPaperAssemItemType();
		String typeStr="";
		if(type==0)
			typeStr="0星";
		else if(type==0.5)
			typeStr="半星";
		else if(type==1)
			typeStr="1星";
		else if(type==2)
			typeStr="2星";
		else if(type==3)
			typeStr="3星";
		else if(type==4)
			typeStr="4星";
		else if(type==5)
			typeStr="5星";
		else if(type==11)
			typeStr="未答";
		else if(type==12)
			typeStr="错";
		else if(type==14)
			typeStr="正确";
		else if(type==15)
			typeStr="疑问";
		else if(type==-1)
			typeStr="全部";
		outStr+=typeStr+"题重练";
		return outStr;
	}
	/**
	 * 获取生词数量
	 * @param userData
	 * @return
	 */
	public int getWordNum(UserDataVO userData){
		return this.getJdbcTemplate().queryForInt(
				"select count(id) from fresh_word where process_instance=?"
				, new Object[]{userData.getProcessInstanceId()});
	}
	/**
	 * 获取暂停信息数量
	 */
	public int getPauseInfoNum(UserDataVO userData){
		return this.getJdbcTemplate().queryForInt(
				"select count(process_instance_id) from pause_info where process_instance_id=?"
				, new Object[]{userData.getProcessInstanceId()});
	}
	
	/**
	 * 获取首页当前任务模块信息 mpc
	 */
	@SuppressWarnings("unchecked")
	public UserDataVO getCurrentNodeInstanceInfoVOForMPC(UserDataVO userData){		
		String sql1="select a.process_definition_id,a.node_id,b.node_group_id,b.name,c.process_instance_id,e.id,f.name as process_name,c.learning_process_rate,"
			+" c.total_score,c.total_accuracy_rate,c.total_mastery_rate,c.sum_incorrect_items,c.sum_correct_items,c.sum_unfinished_items,c.total_items_num,"
			+" c.sum_zero_star_items,c.sum_half_star_items,c.sum_one_star_items,c.sum_two_star_items,c.unsure_mark_items,c.customer_exam_time,"
			+" c.sum_three_star_items,c.sum_four_star_items,c.sum_five_star_items,a.process_status,d.items_num,d.answering_time,c.first_training_time"	
			+" from asf_process_instance a " 
			+" left outer join asf_node b on b.id=a.node_id "
			+" left outer join asf_node_instance e on (e.node_id=b.id and a.id=e.process_instance_id) "
			+" left outer join paper_assembling_policy d on b.id=d.node_id, "
			+" process_training_status c,asf_process_definition f "
			+" where a.id=c.process_instance_id and f.id=a.process_definition_id "
			+" and a.actor=? and a.process_definition_id=?";		
		List list1=this.getJdbcTemplate().queryForList(sql1, new Object[]{
				userData.getUserID(),Long.parseLong(userData.getRefID())});
		if(list1!=null&&list1.size()>0){			
			Map map=(Map)list1.get(0);
			if(map.get("node_id")!=null)
				userData.setCurrentNodeId(Long.valueOf(map.get("node_id").toString()));
			else
				userData.setCurrentNodeId(-1);
			if(map.get("name")!=null)
				userData.setCurrentNodeName(map.get("name").toString());
			if(map.get("id")!=null)
				userData.setCurrentNodeInstanceId(Long.valueOf(map.get("id").toString()));
			else
				userData.setCurrentNodeInstanceId(-1);
			userData.setProcessInstanceId((Long)map.get("process_instance_id"));
			userData.setTotalAccuracyRate(((Float)map.get("total_accuracy_rate")).intValue());
			userData.setTotalMasteryRate(((Float)map.get("total_mastery_rate")).intValue());
			userData.setSumUnfinishedItems((Integer)map.get("sum_unfinished_items"));//未作总题数
			userData.setSumIncorrectItems((Integer)map.get("sum_incorrect_items"));
			userData.setUnsureMarkItems((Integer)map.get("unsure_mark_items"));
			userData.setTotalScore((Float)map.get("total_score"));
			userData.setTotalItemsNum((Integer)map.get("total_items_num"));//总题数
			userData.setSumCorrectItems((Integer)map.get("sum_correct_items"));
			userData.setLearningProcessRate(((Float)map.get("learning_process_rate")).intValue());
			userData.setSumZeroStarItems((Integer)map.get("sum_zero_star_items"));
			userData.setSumHalfStarItems((Integer)map.get("sum_half_star_items"));
			userData.setSumOneStarItems((Integer)map.get("sum_one_star_items"));
			userData.setSumTwoStarItems((Integer)map.get("sum_two_star_items"));
			userData.setSumThreeStarItems((Integer)map.get("sum_three_star_items"));
			userData.setSumFourStarItems((Integer)map.get("sum_four_star_items"));
			userData.setSumFiveStarItems((Integer)map.get("sum_five_star_items"));
			userData.setFirstTrainingTime((Date)map.get("first_training_time"));
			userData.setProcessDefinitionId((Long)map.get("process_definition_id"));
			userData.setProcessStatus((Integer)map.get("process_status"));
			userData.setProcessName((String)map.get("process_name"));
			if(map.get("customer_exam_time")!=null)
				userData.setCustomerExamTime((Date)map.get("customer_exam_time"));//用户自定义时间
			if(map.get("items_num")!=null)
				userData.setCurrentNodeItemNum((Integer)map.get("items_num"));
			if(map.get("answering_time")!=null)
				userData.setCurrentNodeTime((Integer)map.get("answering_time")/60);//转换成分钟
			userData.setSumfinishedItems(userData.getSumIncorrectItems()+userData.getSumCorrectItems());//已训练题数=正确题数+错误题数
			//userData.setShowNodeGroupVOList(this.getShowNodeGroupVOList(userData.getProcessInstanceId()));
			if(map.get("node_group_id")!=null){
				userData.setUnitNodeId((Long)map.get("node_group_id"));
				List list=this.getJdbcTemplate().queryForList("select name from asf_node where id=?",new Object[]{userData.getUnitNodeId()});
				if(list!=null&&list.size()>0)
					userData.setUnitNodeName(((Map)list.get(0)).get("name").toString());
			}/*else if(map.get("node_id")==null){
				userData.setUnitNodeId(userData.getShowNodeGroupVOList().get(0).getNodeId());
				userData.setUnitNodeName(userData.getShowNodeGroupVOList().get(0).getNodeName());
			}*/
		}
		return userData;
	}
	
	/**
	 * 获取2级node
	 */
	@SuppressWarnings("unchecked")
	public Node getSecondLevevNode(long nodeId){
		//获取当前（第四级）叶节点node
		Node node=this.get(Node.class,nodeId);
		List<Node> list=find("from Node where processDefinition.id=? and orderNum=?",
				node.getProcessDefinition().getId(),node.getOrderNum().subSequence(0,5));
		Node pNode = list.get(0);
		if (node.equals(pNode)) return pNode.getNodeGroup();
		else return pNode;
	}
	
	/**
	 * 获取节点组id
	 */
	private Node getNodeGroup(Node node,int level){
		//Assert.notNull(node);
		if(level==0)
				return node;
		level--;
		return getNodeGroup(node.getNodeGroup(),level);
	}
	
	/**
	 * 获取本流程所有2级节点组列表
	 * 为节点组
	 */
	@SuppressWarnings("unchecked")
	public List<Node> getSecondLevevNodes(UserDataVO userData){
		List<Node> n = this.find("from NodeGroup n where n.processDefinition.id=? and " +
				"n.nodeGroup.nodeGroup is null  order by n.orderNum",	userData.getProcessDefinitionId());
		
		if (n.size() == 0) n = this.find("from NodeGroup n where n.processDefinition.id=? and " +
				"n.nodeGroup is null  order by n.orderNum",	userData.getProcessDefinitionId());
		
		return n;
	}
	
	/**
	 * 获取当前章下的所有节点包括节点组
	 */
	@SuppressWarnings("unchecked")
	public UserDataVO getNodeInstanceInfoVOList(UserDataVO userData,Node currentSecondLevevNode){
		
		//获取暂停节点列表
		
		String nodeIds=this.getPauseNodeIds(userData);
		
		userData.setPauseNodeIds(nodeIds);
		
		List list=this.getJdbcTemplate().queryForList(
				"select a.id,a.node_id,b.name,b.node_type,b.order_num,a.node_status,c.score,c.mastery_rate,c.node_instance_id "
				+" from asf_node_instance a left join current_test_status c on a.id=c.node_instance_id,asf_node b"
				+" where a.node_id=b.id and a.process_instance_id=? and b.order_num like ? order by b.order_num "
			 
		  		, new Object[]{userData.getProcessInstanceId(),currentSecondLevevNode.getOrderNum()+",%"});
		 List<NodeInstanceInfoVO> outList=new ArrayList<NodeInstanceInfoVO>();
		 if(list==null||list.size()==0)
			 return null;
		 int nodeListDivWidth=0;
		 int p=1;
		 int j=0;
		 for(int i=0;i<list.size();i++){
				 Map map=(Map)list.get(i);
				 NodeInstanceInfoVO vo=new NodeInstanceInfoVO();
				 vo.setNodeInstanceId(Long.parseLong(map.get("id").toString()));
				 vo.setNodeId(Long.parseLong(map.get("node_id").toString()));
				 vo.setTitleInfo(map.get("name").toString());
				 vo.setNodeStatus(Integer.parseInt(map.get("node_status").toString()));
				 if(vo.getNodeStatus()>0)
						vo.setTag(1);//控制页面节点是否显示链接
				 vo.setNodeType(map.get("node_type").toString());
				 vo.setOrderNum(map.get("order_num").toString());
				
				 if(vo.getNodeType().equals("GROUP")){
					 if(vo.getOrderNum()!=null&&vo.getOrderNum().length()<8)
						 continue;
					 j++;
					 if(vo.getNodeStatus()>0){
						 vo.setCssClassName("btn_container part_on");
						 vo.setCssClassName2("cBlack");
					 }else{ 
						 if(i<list.size()-1&&
								 Integer.parseInt(((Map)list.get(i+1)).get("node_status").toString())>0||
								 userData.getCurrentNodeId()==Long.parseLong(((Map)list.get(i+1)).get("node_id").toString())){
							vo.setCssClassName("btn_container part_on");
							vo.setCssClassName2("cBlack");
						 }else{
							 vo.setCssClassName("btn_container part_off");
							 vo.setCssClassName2("cDEGray");
						}
					 }
					vo.setNodeName("第"+j+"节");
					outList.add(vo);
					p=1;
					
					continue;
				 }
				 else if(vo.getNodeType().equals("EVALUATE")){
					 vo.setNodeName("评测");
					 p=1;
				 }
				 else if(vo.getNodeType().equals("PHASETEST")){
					 vo.setNodeName("前测");
					 p=1;
				 }
				 else if(vo.getNodeType().equals("UNITTEST")){
					 vo.setNodeName("后测");
					 p=1;
				 }
				 else if(vo.getNodeType().equals("PRACTICE")){
					 vo.setNodeName(String.valueOf(p));
					 p++;
				 }
					 
					
				if(nodeIds.indexOf(String.valueOf(vo.getNodeInstanceId()))>-1){
						vo.setCssClassName("btn_container Pause_btn fB");
						vo.setCssClassName2("cBlack");
				}
				else if(userData.getCurrentNodeId()==vo.getNodeId()&&nodeIds.length()==0){
					vo.setCssClassName("btn_container on-going_btn fB");
					vo.setCssClassName2("cBlack");
				}else 
					switch(vo.getNodeStatus()){
						case RepuestDictForWeb.NODE_STATUS_UNDO:
							vo.setCssClassName("btn_container off_btn fB");	
							vo.setCssClassName2("cDEGray");
							break;
						case RepuestDictForWeb.NODE_STATUS_PASS:
							vo.setCssClassName("btn_container Pass_btn");
							vo.setCssClassName2("cBlack");
							break;
						case RepuestDictForWeb.NODE_STATUS_NOPASS:
							vo.setCssClassName("btn_container no-go_btn fB");
							vo.setCssClassName2("cBlack");
							break;
						case RepuestDictForWeb.NODE_STATUS_SKIP:
							vo.setCssClassName("btn_container Pass_btn");
							vo.setCssClassName2("cBlack");
							break; 
						default:   
							break; 
					}
				 
				 outList.add(vo);
		 }
		 nodeListDivWidth=outList.size()*50;
		 userData.setNodeListDivWidth(nodeListDivWidth);
		 userData.setNodeInstanceInfoList(outList);
		 return userData;
	}
	
	/**
	 * 更新用户自定义考试时间
	 */
	public void updateExamTime(UserDataVO userData,Date inputDate){
		ProcessTrainingStatus po=this.get(ProcessTrainingStatus.class, userData.getProcessInstanceId());
		po.setCustomerExamTime(inputDate);
		this.save(po);
		userData.setCustomerExamTime(inputDate);
	}
	public boolean isFormulatorTest(UserDataVO userData){
		return this.get(ProcessTrainingStatus.class, userData.getProcessInstanceId()).isFormulatorTest();
	}
	/**
	 * 更新字段，已经进行过公式训练
	 */
	public void updateFormulatorTest(UserDataVO userData){
		ProcessTrainingStatus po=this.get(ProcessTrainingStatus.class, userData.getProcessInstanceId());
		po.setFormulatorTest(true);
		this.save(po);
	}
	
}
