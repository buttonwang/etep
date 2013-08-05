package com.ambow.trainingengine.report.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.exam.web.data.ResultShowVO;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.report.web.data.ReportShowVO;
import com.ambow.trainingengine.util.CalculateUtil;
import com.ambow.trainingengine.util.DateUtil;
import com.ambow.trainingengine.util.MathUtil;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.ShowNodeGroupVO;
import com.ambow.trainingengine.web.data.UserDataVO;



public class ReportService extends ReportBaseService{
	
	/**
	 * 获取节点实例
	 * @param nodeInstanceId
	 * @return
	 */
	public NodeInstance getNodeInstance(Long nodeInstanceId){
		String hsql = "from NodeInstance a where a.id=?";
		
		NodeInstance nodeInstance = (NodeInstance)this.findObjByHql(hsql, nodeInstanceId);
		return nodeInstance;
	}
	
	/**
	 * 获取节点列表
	 * @param processInstanceId
	 * @return
	 */
	public List<Map<String,Object>> getNodeList(long processInstanceId){

		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select b.node_group_id,b.name,a.id,a.node_status,b.node_type,b.order_num");
		sqlBuff.append(" from asf_node_instance a");
		sqlBuff.append(" left outer join asf_node b on a.node_id=b.id");
		sqlBuff.append(" where a.process_instance_id=? and a.node_status !=0");
		sqlBuff.append(" order by b.order_num");
		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sqlBuff.toString(), processInstanceId);
		return mapList;
	}
	
	/**
	 * 获取节点列表
	 * @param processInstanceId
	 * @return
	 */
	public List<Map<String,Object>> getNodeList(long processInstanceId,String orderNumArr){
		String sql="select a.node_id,b.node_group_id,b.name,a.id,a.node_status,b.node_type,b.order_num "
			+" from asf_node_instance a "
			+" left outer join asf_node b on a.node_id = b.id "
			+" where  a.process_instance_id=? ";
		if(orderNumArr != null && !orderNumArr.equals("")){
			sql=sql+" and b.order_num in("+orderNumArr+") ";
		}	
		sql=sql+" order by b.order_num";
		List<Map<String,Object>> mapList = this.getJdbcTemplate().queryForList(sql, processInstanceId);
		return mapList;
	}
	
	/**
	 * 获取未通过的节点数量
	 * @param processInstanceId
	 * @param nodeGroupId
	 * @return
	 */
	public Integer getNodeCount(Long processInstanceId,Long nodeGroupId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select count(a.id) ");
		sqlBuff.append(" from asf_node_instance a ");
		sqlBuff.append(" left outer join asf_node b on a.node_id = b.id ");
		sqlBuff.append(" where b.node_group_id="+nodeGroupId+" and a.`process_instance_id`="+processInstanceId);
		sqlBuff.append(" and a.node_status !=2 and a.node_status !=3 ");
		Integer amount = this.getJdbcTemplate().queryForInt(sqlBuff.toString());
		return amount;
	}
	
	/**
	 * 获取未通过的节点数量
	 * @param processInstanceId
	 * @param nodeGroupId
	 * @return
	 */
	public Integer getMaxNodeGrounpLength(Long processInstanceId){
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append(" select max(length(b.order_num))");
		sqlBuff.append(" from asf_node_instance a ");
		sqlBuff.append(" left outer join asf_node b on a.node_id = b.id ");
		sqlBuff.append(" where b.node_type = 'GROUP' and a.process_instance_id="+processInstanceId);
		Integer amount = this.getJdbcTemplate().queryForInt(sqlBuff.toString());
		return amount;
	}
	
	/**
	 * 获取完整流程节点列表
	 */
	@SuppressWarnings("unchecked")
	public List<NodeVO> getNodeListForTree(UserDataVO userData){
		String sql="select a.node_id,b.node_group_id,b.name,a.id,a.node_status,b.node_type,c.first_test_score,d.is_display_module"
			+" from asf_node_instance a left outer join current_test_status c on a.id=c.node_instance_id,asf_node b left outer join node_group_policy d on b.id=d.node_id"
			+" where b.id=a.node_id and a.process_instance_id=? and (b.node_type='GROUP' or a.node_status>0) order by b.order_num";
			//+" where b.id=a.node_id and a.process_instance_id=? order by b.order_num";
		List list=this.getJdbcTemplate().queryForList(sql, new Object[]{
				userData.getProcessInstanceId()});
		List<NodeVO> outList=new ArrayList<NodeVO>();
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			//过滤没有做过的子节点的单元节点			
			if(map.get("node_type").toString().equals(NodeType.GROUP.toString())){
				/*int tag=this.getJdbcTemplate().queryForInt(
						"select is_display_module from node_group_policy where node_id=?",(Long)map.get("node_id"));
				if(tag==0)
					continue;*/
				if(map.get("is_display_module")!=null){
					if(Integer.valueOf(map.get("is_display_module").toString())==0)
						continue;
				   //判断其单元是否有做过或跳过的训练，如果有，就统计单元数据
				    String ids="";
				    for(ShowNodeGroupVO vo:userData.getShowNodeGroupVOList()){
						if(vo.getNodeId().longValue()==Long.valueOf(map.get("node_id").toString())){
							ids=vo.getChildNodeIds();
							break;
						}
					}
				   int tag= this.getJdbcTemplate().queryForInt(
				    		"select count(a.id) from asf_node a,asf_node_instance b where a.id=b.node_id "
				    		+" and b.process_instance_id=? and b.node_status>0 and a.id in ("+ids+")",new Object[]{userData.getProcessInstanceId()});
				   if(tag==0)
					   continue;
				}
				else
					continue;
			}			
			NodeVO vo=new NodeVO();
			vo.setNodeDefinitionId((Long)map.get("node_id"));
			vo.setNodeInstanceId((Long)map.get("id"));
			vo.setNodeName(map.get("name").toString());
			
			if((Long)map.get("node_group_id")==null)
				vo.setNodeGroupId(0l);
			else
				vo.setNodeGroupId((Long)map.get("node_group_id"));
			
	/*		if((Float)map.get("first_test_score")==null)
				vo.setFirstTestScore(-1f);
			else*/
			vo.setFirstTestScore((Float)map.get("first_test_score"));
			
			vo.setNodeStatus((Integer)map.get("node_status"));
			vo.setNodeType(map.get("node_type").toString());
			
			outList.add(vo);	
				
		}
		return outList;
	}	
	
	/**
	 * 获取训练卷数
	 * nodeGroupId 为null时便取整个流程的训练个数
	 */
	public int getPracticeNum(UserDataVO userData,Long nodeGroupId){
		
		String sql="select count(a.id) from asf_node_instance a,asf_node b"
			+" where a.node_id=b.id and b.node_type='PRACTICE'";
		Object[] o=null;
		if(nodeGroupId==null){
			sql+=" and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		else{
			String ids="";
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().longValue()==nodeGroupId.longValue()){
					ids=showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql+=" and b.id in("+ids+") and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		return this.getJdbcTemplate().queryForInt(sql,o);
	}
	
	/**
	 * 获取通过训练卷数
	 * nodeGroupId 为null时便取整个流程的通过训练个数
	 */
	public int getPracticePassNum(UserDataVO userData,Long nodeGroupId){
		
		String sql="select count(a.id) from asf_node_instance a,asf_node b"
			+" where a.node_id=b.id and b.node_type='PRACTICE' and a.node_status in(2,3)";
		Object[] o=null;
		if(nodeGroupId==null){
			sql+=" and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		else{
			String ids="";
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().longValue()==nodeGroupId.longValue()){
					ids=showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql+=" and b.id in("+ids+") and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		return this.getJdbcTemplate().queryForInt(sql,o);
	}
	
	/**
	 * 获取已经训练过的训练卷数
	 * nodeGroupId 为null时便取整个流程的已经训练过的训练个数
	 */
	public int getPracticeDoNum(UserDataVO userData,Long nodeGroupId){
	
		String sql="select count(b.id) from asf_node_instance a,asf_node b"
			+" where a.node_id=b.id and b.node_type='PRACTICE' and a.node_status in(1,2,3)";
		Object[] o=null;
		if(nodeGroupId==null){
			sql+=" and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		else {
			String ids="";
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().longValue()==nodeGroupId.longValue()){
					ids=showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql+=" and b.id in("+ids+") and a.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		return this.getJdbcTemplate().queryForInt(sql,o);
	}
	/**
	 * 获取用户训练答案列表
	 * 此方法用于分析强化整个流程统计数据的
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getProcessReportShowVOList(UserDataVO userData){
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList()){
			
			String sql="select sum(d.first_test_score) first_test_score,"
				+"sum(d.score) score,sum(c.total_score) total_score,sum(c.items_num) items_num,"
				+"sum(d.sum_incorrect_items) sum_incorrect_items,sum(d.sum_unfinished_items) sum_unfinished_items,"
				+"sum(d.sum_zero_star_items) sum_zero_star_items,sum(d.sum_half_star_items) sum_half_star_items,"
				+"sum(d.sum_one_star_items) sum_one_star_items,sum(d.sum_two_star_items) sum_two_star_items,"
				+"sum(d.sum_three_star_items) sum_three_star_items,sum(d.sum_four_star_items) sum_four_star_items,"
				+"sum(d.sum_five_star_items) sum_five_star_items "
				+" from asf_node a left outer join asf_node e on a.node_group_id=e.id,asf_node_instance b,"
				+"paper_assembling_policy c,current_test_status d "
				+"  where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id and a.node_type='PRACTICE' and b.process_instance_id=?"
				+" and a.id in("+showNodeGroupVO.getChildNodeIds()+") ";
			List list=this.getJdbcTemplate().queryForList(sql,new Object[]{userData.getProcessInstanceId()});
			if(list==null||list.size()==0)				
				continue;
			
				Map map=(Map)list.get(0);
				if(map.get("score")==null)
					continue;
				
				ReportShowVO vo=new ReportShowVO();
				vo.setFirstTestScore(Float.valueOf(map.get("first_test_score").toString()));
				vo.setScore(Float.valueOf(map.get("score").toString()));
				vo.setTotalScore(this.getTotalScore(userData.getProcessInstanceId(), showNodeGroupVO.getChildNodeIds(),NodeType.PRACTICE.toString()));
				vo.setItemsNum(Integer.valueOf(map.get("items_num").toString()));
				vo.setSumIncorrectItems(Integer.valueOf(map.get("sum_incorrect_items").toString()));
				vo.setSumUnfinishedItems(Integer.valueOf(map.get("sum_unfinished_items").toString()));
				vo.setSumZeroStarItems(Integer.valueOf(map.get("sum_zero_star_items").toString()));
				vo.setSumHalfStarItems(Integer.valueOf(map.get("sum_half_star_items").toString()));
				vo.setSumOneStarItems(Integer.valueOf(map.get("sum_one_star_items").toString()));
				vo.setSumTwoStarItems(Integer.valueOf(map.get("sum_two_star_items").toString()));
				vo.setSumThreeStarItems(Integer.valueOf(map.get("sum_three_star_items").toString()));
				vo.setSumFourStarItems(Integer.valueOf(map.get("sum_four_star_items").toString()));
				vo.setSumFiveStarItems(Integer.valueOf(map.get("sum_five_star_items").toString()));
				//vo.setNodeInstanceId((Long)map.get("id"));
				//vo.setNodeId((Long)map.get("node_group_id"));
				//vo.setNodeName((String)map.get("name"));
				vo.setNodeId(showNodeGroupVO.getNodeId());
				vo.setNodeName(showNodeGroupVO.getNodeName());
				vo.setNodeInstanceId(showNodeGroupVO.getNodeInstanceId());
				
				vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(), vo.getTotalScore())));
				vo.setFirstTestAccuracyRate(Float.valueOf(MathUtil.percent(vo.getFirstTestScore(), vo.getTotalScore())));
				vo.setMasteryRate(Float.valueOf(CalculateUtil.masteryRate(vo.getSumZeroStarItems(), vo.getSumHalfStarItems(),
						vo.getSumOneStarItems(), vo.getSumTwoStarItems(), vo.getSumThreeStarItems(),
						vo.getSumFourStarItems(),vo.getSumFiveStarItems(), vo.getItemsNum())));
				outList.add(vo);
		//	}
		}
		return outList;
	}
	
	/**
	 * 获取测试列表
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getTestShowVOList(UserDataVO userData,Long nodeGroupId){
		String sql="select d.first_test_score,d.score,c.total_score,c.items_num,c.answering_time,d.first_test_score,"
			+"d.sum_incorrect_items,d.sum_unfinished_items,d.sum_zero_star_items,d.sum_half_star_items,b.node_status,"
			+"d.sum_one_star_items,d.sum_two_star_items,d.sum_three_star_items,d.sum_four_star_items,d.end_time,d.node_instance_id,b.node_id,"
			+"d.sum_five_star_items,a.node_group_id,a.name,d.first_test_accuracy_rate,d.accuracy_rate,d.mastery_rate,e.right_rate_for_pass"
			+" from asf_node a ,asf_node_instance b,paper_assembling_policy c,current_test_status d,training_policy e "
			+" where b.node_id=a.id and a.id=e.node_id and a.node_type='UNITTEST' and b.node_status in(1,2,3) "
			+" and c.node_id=a.id and d.node_instance_id=b.id ";
		Object[] o=null;
		String ids="";
		if(nodeGroupId==null||nodeGroupId==0){
			sql+=" and b.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		else{			
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().intValue()==nodeGroupId.intValue()){
					ids=showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql+=" and b.process_instance_id=? and a.id in("+ids+")";
			o=new Object[]{userData.getProcessInstanceId()};
		}
		List list=this.getJdbcTemplate().queryForList(sql,o);
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		if(list==null)
			return outList;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			ReportShowVO vo=new ReportShowVO();
			vo.setNodeInstanceId((Long)map.get("node_instance_id"));
			vo.setFirstTestScore((Float)map.get("first_test_score"));
			vo.setScore((Float)map.get("score"));
			vo.setTotalScore(this.getTotalScore(userData.getProcessInstanceId(),map.get("node_id").toString(),NodeType.UNITTEST.toString()));
			vo.setItemsNum((Integer)map.get("items_num"));
			vo.setSumIncorrectItems((Integer)map.get("sum_incorrect_items"));
			vo.setSumUnfinishedItems((Integer)map.get("sum_unfinished_items"));
			vo.setSumZeroStarItems((Integer)map.get("sum_zero_star_items"));
			vo.setSumHalfStarItems((Integer)map.get("sum_half_star_items"));
			vo.setSumOneStarItems((Integer)map.get("sum_one_star_items"));
			vo.setSumTwoStarItems((Integer)map.get("sum_two_star_items"));
			vo.setSumThreeStarItems((Integer)map.get("sum_three_star_items"));
			vo.setSumFourStarItems((Integer)map.get("sum_four_star_items"));
			vo.setSumFiveStarItems((Integer)map.get("sum_five_star_items"));
			vo.setNodeId((Long)map.get("node_group_id"));
			vo.setNodeName((String)map.get("name"));
			vo.setFirstTestScore((Float)map.get("first_test_score"));
			vo.setMasteryRate((Float)map.get("mastery_rate"));
			vo.setRightRateForPass((Float)map.get("right_rate_for_pass"));
			vo.setAnsweringTime((Integer)map.get("answering_time")/60);
			vo.setNodeStatus((Integer)map.get("node_status"));
			vo.setStarNum(vo.getSumHalfStarItems()*0.5+vo.getSumOneStarItems()+vo.getSumTwoStarItems()*2+
					vo.getSumThreeStarItems()*3+vo.getSumFourStarItems()*4+vo.getSumFiveStarItems()*5);
			vo.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format((Date)map.get("end_time")));
			outList.add(vo);
		}
		return outList;
	}
	
	/**
	 * 获取用户训练答案列表
	 * 此方法用于分析强化整个流程统计数据的
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getUnitReportShowVOList(UserDataVO userData,Long nodeGroupId){
		String ids="";
		for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
			if(showNodeGroupVO.getNodeId().intValue()==nodeGroupId.intValue()){
				ids=showNodeGroupVO.getChildNodeIds();
				break;
			}
		
		
		String sql="select b.id,a.name,d.first_test_score,d.score,c.total_score,c.items_num,d.sum_correct_items,d.unsure_mark_items,"
				+"d.sum_incorrect_items,d.sum_unfinished_items,d.sum_zero_star_items,d.sum_half_star_items,"
				+"d.sum_one_star_items,d.sum_two_star_items,d.sum_three_star_items,d.sum_four_star_items,"
				+"d.sum_five_star_items,b.node_id from asf_node a ,asf_node_instance b,"
				+"paper_assembling_policy c,current_test_status d "
				+" where a.id=b.node_id and b.id=d.node_instance_id and c.node_id=a.id and a.node_type='PRACTICE' "
				//+"and b.process_instance_id=? and a.node_group_id=?";
				+"and b.process_instance_id=? and a.id in("+ids+")";
		List list=this.getJdbcTemplate().queryForList(sql,new Object[]{userData.getProcessInstanceId()});
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		if(list==null)
			return outList;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			ReportShowVO vo=new ReportShowVO();
			vo.setFirstTestScore((Float)map.get("first_test_score"));
			vo.setScore((Float)map.get("score"));
			vo.setTotalScore(this.getTotalScore(userData.getProcessInstanceId(),map.get("node_id").toString(),NodeType.PRACTICE.toString()));
			vo.setItemsNum((Integer)map.get("items_num"));
			vo.setSumIncorrectItems((Integer)map.get("sum_incorrect_items"));
			vo.setSumUnfinishedItems((Integer)map.get("sum_unfinished_items"));
			vo.setSumZeroStarItems((Integer)map.get("sum_zero_star_items"));
			vo.setSumHalfStarItems((Integer)map.get("sum_half_star_items"));
			vo.setSumOneStarItems((Integer)map.get("sum_one_star_items"));
			vo.setSumTwoStarItems((Integer)map.get("sum_two_star_items"));
			vo.setSumThreeStarItems((Integer)map.get("sum_three_star_items"));
			vo.setSumFourStarItems((Integer)map.get("sum_four_star_items"));
			vo.setSumFiveStarItems((Integer)map.get("sum_five_star_items"));
			vo.setSumCorrectItems((Integer)map.get("sum_correct_items"));
			vo.setUnsureMarkItems((Integer)map.get("unsure_mark_items"));
			vo.setNodeId((Long)map.get("node_id"));
			vo.setNodeName((String)map.get("name"));
			vo.setNodeInstanceId((Long)map.get("id"));
			vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(), vo.getTotalScore())));
			vo.setFirstTestAccuracyRate(Float.valueOf(MathUtil.percent(vo.getFirstTestScore(), vo.getTotalScore())));
			vo.setMasteryRate(Float.valueOf(CalculateUtil.masteryRate(vo.getSumZeroStarItems(), vo.getSumHalfStarItems(),
					vo.getSumOneStarItems(), vo.getSumTwoStarItems(), vo.getSumThreeStarItems(),
					vo.getSumFourStarItems(),vo.getSumFiveStarItems(), vo.getItemsNum())));
			outList.add(vo);
		}
		return outList;
	}
	/**
	 * 获取次单元中的题型类型列表
	 */
	@SuppressWarnings("unchecked")
	public String getNodeTypesStr(UserDataVO userData,Long nodeGroupId){
		
		String outStr="";
		String sql="";
		String ids="";
		if(nodeGroupId!=null&&nodeGroupId.intValue()>0){
			ids+=nodeGroupId;
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().intValue()==nodeGroupId.intValue()){
					ids+=","+showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql="select distinct a.node_type from asf_node a where a.node_type in('PRACTICE','EVALUATE','PHASETEST','UNITTEST') and a.id in ("+ids+")";
		}else
			sql="select distinct a.node_type from asf_node a where a.node_type in('PRACTICE','EVALUATE','PHASETEST','UNITTEST')"
				+" and a.process_definition_id="+userData.getProcessDefinitionId();
		List list=this.getJdbcTemplate().queryForList(sql);
		if(list!=null&&list.size()>0)
			for(int i=0;i<list.size();i++)
				outStr+=((Map)list.get(i)).get("node_type").toString()+",";
		return outStr;
	}
	
	
	
	/**
	 * 获取训练的答题历史记录列表
	 * @param nodeInstanceId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getPracticeHistoryReportShowVOList(Long nodeInstanceId){
		String sql="select a.mastery_rate,a.accuracy_rate,a.sum_incorrect_items,a.sum_unfinished_items,"
				+"a.sum_zero_star_items,a.sum_half_star_items,a.sum_one_star_items,a.sum_two_star_items,"
				+"a.sum_three_star_items,a.sum_four_star_items,a.sum_five_star_items"
				+" from history_test_status a where a.node_instance_id=? order by a.id";
		List list=this.getJdbcTemplate().queryForList(sql,new Object[]{nodeInstanceId});
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		if(list==null)
			return outList;
		for(int i=list.size()-1;i>=0;i--){
			Map map=(Map)list.get(i);
			ReportShowVO vo=new ReportShowVO();
			vo.setSumIncorrectItems((Integer)map.get("sum_incorrect_items"));
			vo.setSumUnfinishedItems((Integer)map.get("sum_unfinished_items"));
			vo.setSumZeroStarItems((Integer)map.get("sum_zero_star_items"));
			vo.setSumHalfStarItems((Integer)map.get("sum_half_star_items"));
			vo.setSumOneStarItems((Integer)map.get("sum_one_star_items"));
			vo.setSumTwoStarItems((Integer)map.get("sum_two_star_items"));
			vo.setSumThreeStarItems((Integer)map.get("sum_three_star_items"));
			vo.setSumFourStarItems((Integer)map.get("sum_four_star_items"));
			vo.setSumFiveStarItems((Integer)map.get("sum_five_star_items"));
			vo.setMasteryRate((Float)map.get("mastery_rate"));
			vo.setAccuracyRate((Float)map.get("accuracy_rate"));
			vo.setTimes(i+1);
			outList.add(vo);
		}
		return outList;
	}
	
	public int gethistoryTestStatusMaxId(Long nodeInstanceId){		
		return this.getJdbcTemplate().queryForInt(
				"select max(a.id) from history_test_status a where a.node_instance_id=?"
				, new Object[]{nodeInstanceId});
	}
	
	/**
	 * 获取阶段测试列表
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getPhasetestShowVOList(UserDataVO userData,Long nodeId){
		String sql="select d.score,c.total_score,c.items_num,c.answering_time,a.name,d.end_time,b.node_id"			
			+" from asf_node a ,asf_node_instance b,paper_assembling_policy c,current_test_status d "
			+" where b.node_id=a.id and a.id=c.node_id and b.id=d.node_instance_id and a.node_type='PHASETEST' and b.node_status in(1,2,3) ";
		Object[] o=null;
		sql+=" and b.process_instance_id=?";
		o=new Object[]{userData.getProcessInstanceId()};
		if(nodeId!=null&&nodeId>0){
			String ids=nodeId+"";
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getNodeId().intValue()==nodeId.intValue()){
					ids+=","+showNodeGroupVO.getChildNodeIds();
					break;
				}
			sql+=" and a.id in("+ids+")";			
		}
		List list=this.getJdbcTemplate().queryForList(sql,o);
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		if(list==null)
			return outList;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			ReportShowVO vo=new ReportShowVO();
			vo.setScore((Float)map.get("score"));
			vo.setTotalScore(this.getTotalScore(userData.getProcessInstanceId(),map.get("node_id").toString(),NodeType.PHASETEST.toString()));
			vo.setItemsNum((Integer)map.get("items_num"));
			//vo.setNodeId((Long)map.get("node_id"));
			vo.setNodeName((String)map.get("name"));
			vo.setAnsweringTime((Integer)map.get("answering_time")/60);	
			vo.setAccuracyRate(Float.valueOf(MathUtil.percent(vo.getScore(), vo.getTotalScore())));
			vo.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format((Date)map.get("end_time")));
			outList.add(vo);
		}
		return outList;
	}
	/**
	 * 获取评测知识点列表
	 */
	@SuppressWarnings("unchecked")
	public List<ReportShowVO> getEvluatingShowVOList(UserDataVO userData,Long nodeId){
		/*String sql="select a.id,sum(d.score) score,sum(d.total_score) total_score,c.name,d.node_instance_id,a.node_group_id,mid(d.knowledge_point_code,1,4) code"			
			+" from asf_node a ,asf_node_instance b,knowledge_point c,evaluating_answer_status d "
			+" where b.node_id=a.id and c.code=mid(d.knowledge_point_code,1,4) and b.id=d.node_instance_id and a.node_type='EVALUATE' and b.node_status in(1,2,3) ";*/
		String sql="select a.id,sum(d.score) score,sum(d.total_score) total_score,c.name,d.node_instance_id,a.node_group_id,d.knowledge_point_code code"			
			+" from asf_node a ,asf_node_instance b,knowledge_point c,evaluating_answer_status d "
			+" where b.node_id=a.id and c.code=d.knowledge_point_code and b.id=d.node_instance_id and a.node_type='EVALUATE' and b.node_status in(1,2,3) ";
		Object[] o=null;
	//	if(nodeId==null||nodeId==0){
			sql+=" and b.process_instance_id=?";
			o=new Object[]{userData.getProcessInstanceId()};
	//	}
	//	else{
		if(nodeId!=null&&nodeId>0){
			String ids=nodeId+"";
			if (userData.getShowNodeGroupVOList()!=null) {
				for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList()) {
					if(showNodeGroupVO.getNodeId().intValue()==nodeId.intValue()){
						ids+=","+showNodeGroupVO.getChildNodeIds();
						break;
					}
				}
			}	
			sql+=" and a.id in("+ids+")";			
		}
//		sql+=" group by a.node_group_id,c.name,d.node_instance_id,mid(d.knowledge_point_code,1,4) order by a.order_num";
		sql+=" group by a.node_group_id,c.name,d.node_instance_id,d.knowledge_point_code order by a.order_num";
		List list=this.getJdbcTemplate().queryForList(sql,o);
		List<ReportShowVO> outList=new ArrayList<ReportShowVO>();
		if(list==null)
			return outList;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			ReportShowVO vo=new ReportShowVO();
			vo.setScore(Float.valueOf(map.get("score").toString()));
			vo.setTotalScore(Float.valueOf(map.get("total_score").toString()));
		//	vo.setAccuracyRate((Float)map.get("right_rate"));
			vo.setAccuracyRate((float)MathUtil.percent(vo.getScore(), vo.getTotalScore()));
			vo.setKnowledgePointName(map.get("name").toString()); 
			vo.setKnowledgePointCode(map.get("code").toString());
			vo.setNodeId((Long)map.get("id"));
			if((Long)map.get("node_group_id")!=null)
				vo.setNodeGroupId((Long)map.get("node_group_id"));
			else
				vo.setNodeGroupId(0l);
			vo.setNodeInstanceId((Long)map.get("node_instance_id"));
			outList.add(vo);
		}
		return outList;
	}
	/**
	 * 统计当前答卷情况中所有用户联系数据存到流程实例训练情况表中
	 */
	@SuppressWarnings("unchecked")
	public ResultShowVO updateProcessTrainingStatus(UserDataVO userData,ResultShowVO resultShowVO){
		
		//获取 流程实例训练情况 po
		ProcessTrainingStatus po=this.get(ProcessTrainingStatus.class,userData.getProcessInstanceId());
		
		String sql="select sum(a.score) score,sum(a.time_used) time_used,sum(a.sum_correct_items) sum_correct_items,"
			+" sum(a.sum_unfinished_items) sum_unfinished_items,sum(a.unsure_mark_items) unsure_mark_items,sum(a.sum_incorrect_items) sum_incorrect_items,"
			+" sum(a.sum_zero_star_items) sum_zero_star_items,sum(a.sum_half_star_items) sum_half_star_items,"
			+" sum(a.sum_one_star_items) sum_one_star_items,sum(a.sum_two_star_items) sum_two_star_items,"
			+" sum(a.sum_three_star_items) sum_three_star_items,sum(a.sum_four_star_items) sum_four_star_items,"
			+" sum(a.sum_five_star_items) sum_five_star_items ,sum(d.items_num) items_num"
			+" from current_test_status a,asf_node_instance b,asf_node c,paper_assembling_policy d"
			+" where a.node_instance_id=b.id and b.node_id=c.id and c.id=d.node_id and c.node_type='PRACTICE'"
			+" and b.process_instance_id=?";
		Map<String,Object> map=this.getJdbcTemplate().queryForMap(sql, new Object[]{userData.getProcessInstanceId()});
		po.setTotalScore(Float.valueOf(map.get("score").toString()));
		po.setTotalTrainingTime(Integer.valueOf(map.get("time_used").toString()));
		po.setSumCorrectItems(Integer.valueOf(map.get("sum_correct_items").toString()));
		po.setSumIncorrectItems(Integer.valueOf(map.get("sum_incorrect_items").toString()));
		po.setSumUnfinishedItems(Integer.valueOf(map.get("sum_unfinished_items").toString()));
		po.setUnsureMarkItems(Integer.valueOf(map.get("unsure_mark_items").toString()));
		po.setSumZeroStarItems(Integer.valueOf(map.get("sum_zero_star_items").toString()));
		po.setSumHalfStarItems(Integer.valueOf(map.get("sum_half_star_items").toString()));
		po.setSumOneStarItems(Integer.valueOf(map.get("sum_one_star_items").toString()));
		po.setSumTwoStarItems(Integer.valueOf(map.get("sum_two_star_items").toString()));
		po.setSumThreeStarItems(Integer.valueOf(map.get("sum_three_star_items").toString()));
		po.setSumFourStarItems(Integer.valueOf(map.get("sum_four_star_items").toString()));
		po.setSumFiveStarItems(Integer.valueOf(map.get("sum_five_star_items").toString()));
		//计算总的掌握度
		po.setTotalMasteryRate(Float.valueOf(CalculateUtil.masteryRate(po.getSumZeroStarItems(),
				po.getSumHalfStarItems(), po.getSumOneStarItems(), po.getSumTwoStarItems(), 
				po.getSumThreeStarItems(), po.getSumFourStarItems(), po.getSumFiveStarItems(),
				po.getSumCorrectItems()+po.getSumIncorrectItems())));
				
		//计算总正确率
		po.setTotalAccuracyRate(Float.valueOf(MathUtil.percent(po.getTotalScore(), this.getTotalScore(userData.getProcessInstanceId(),null,NodeType.PRACTICE.toString()))));
		
		//统计通过训练节点的试卷总分数
		int passTotalScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b,asf_node_instance c"
				+" where a.id=b.node_id and a.id=c.node_id and c.node_status in(2,3) and c.process_instance_id=? "
				, new Object[]{userData.getProcessInstanceId()});
		//统计整个流程试卷训练节点的总分数
		int totalPaperScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b"
				+" where a.id=b.node_id and a.process_definition_id=?"
				, new Object[]{userData.getProcessDefinitionId()});
		//计算总进度
		po.setLearningProcessRate(Float.valueOf(MathUtil.percent(passTotalScore, totalPaperScore)));
		this.save(po);
		//下面数据用于ky的jsp页面显示
		resultShowVO.setProcessTimeUsed(DateUtil.timeFormat(po.getTotalTrainingTime()));
		resultShowVO.setProcessItemsNum(Integer.valueOf(map.get("items_num").toString()));
		resultShowVO.setProcessFinishItemsNum(po.getSumCorrectItems()+po.getSumIncorrectItems());
		resultShowVO.setProcessScore(po.getTotalScore());
		resultShowVO.setProcessAccuracyRate(po.getTotalAccuracyRate());
		resultShowVO.setProcessMasteryRate(po.getTotalMasteryRate());
		return resultShowVO;
	}
	
	
	@SuppressWarnings("unchecked")
	public void updateProcessTrainingStatusForMpc(UserDataVO userData){
		
		//获取 流程实例训练情况 po
		ProcessTrainingStatus po=this.get(ProcessTrainingStatus.class,userData.getProcessInstanceId());
		
		String sql="select sum(a.score) score,sum(a.total_score) total_score,sum(a.time_used) time_used,sum(a.sum_correct_items) sum_correct_items,"
			+" sum(a.sum_unfinished_items) sum_unfinished_items,sum(a.unsure_mark_items) unsure_mark_items,sum(a.sum_incorrect_items) sum_incorrect_items,"
			+" sum(a.sum_zero_star_items) sum_zero_star_items,sum(a.sum_half_star_items) sum_half_star_items,"
			+" sum(a.sum_one_star_items) sum_one_star_items,sum(a.sum_two_star_items) sum_two_star_items,"
			+" sum(a.sum_three_star_items) sum_three_star_items,sum(a.sum_four_star_items) sum_four_star_items,"
			+" sum(a.sum_five_star_items) sum_five_star_items"
			+" from current_test_status a,asf_node_instance b,asf_node c,paper_assembling_policy d"
			+" where a.node_instance_id=b.id and b.node_id=c.id and c.id=d.node_id and c.node_type in('PRACTICE','PHASETEST','UNITTEST','EVALUATE')"
			+" and b.process_instance_id=?";
		Map<String,Object> map=this.getJdbcTemplate().queryForMap(sql, new Object[]{userData.getProcessInstanceId()});
		po.setTotalScore(Float.valueOf(map.get("score").toString()));
		po.setTotalTrainingTime(Integer.valueOf(map.get("time_used").toString()));
		po.setSumCorrectItems(Integer.valueOf(map.get("sum_correct_items").toString()));
		po.setSumIncorrectItems(Integer.valueOf(map.get("sum_incorrect_items").toString()));
		po.setSumUnfinishedItems(Integer.valueOf(map.get("sum_unfinished_items").toString()));
		po.setUnsureMarkItems(Integer.valueOf(map.get("unsure_mark_items").toString()));
		po.setSumZeroStarItems(Integer.valueOf(map.get("sum_zero_star_items").toString()));
		po.setSumHalfStarItems(Integer.valueOf(map.get("sum_half_star_items").toString()));
		po.setSumOneStarItems(Integer.valueOf(map.get("sum_one_star_items").toString()));
		po.setSumTwoStarItems(Integer.valueOf(map.get("sum_two_star_items").toString()));
		po.setSumThreeStarItems(Integer.valueOf(map.get("sum_three_star_items").toString()));
		po.setSumFourStarItems(Integer.valueOf(map.get("sum_four_star_items").toString()));
		po.setSumFiveStarItems(Integer.valueOf(map.get("sum_five_star_items").toString()));
		//计算总的掌握度
		po.setTotalMasteryRate(Float.valueOf(CalculateUtil.masteryRate(po.getSumZeroStarItems(),
				po.getSumHalfStarItems(), po.getSumOneStarItems(), po.getSumTwoStarItems(), 
				po.getSumThreeStarItems(), po.getSumFourStarItems(), po.getSumFiveStarItems(),
				po.getSumCorrectItems()+po.getSumIncorrectItems())));
				
		//计算总正确率
		//po.setTotalAccuracyRate(Float.valueOf(MathUtil.percent(po.getTotalScore(), this.getTotalScoreForMpc(userData.getProcessInstanceId(),null))));
		po.setTotalAccuracyRate(Float.valueOf(MathUtil.percent(po.getTotalScore(),Float.valueOf(map.get("total_score").toString()))));
		//统计通过训练节点的试卷总分数
		int passTotalScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b,asf_node_instance c"
				+" where a.id=b.node_id and a.id=c.node_id and c.node_status in(2,3) and c.process_instance_id=? "
				, new Object[]{userData.getProcessInstanceId()});
		//统计整个流程试卷训练节点的总分数
		int totalPaperScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b"
				+" where a.id=b.node_id and a.process_definition_id=?"
				, new Object[]{userData.getProcessDefinitionId()});
		//计算总进度
		po.setLearningProcessRate(Float.valueOf(MathUtil.percent(passTotalScore, totalPaperScore)));
		this.save(po);
	
	}
	
	
	
	public ResultShowVO getGroupReport(UserDataVO userData,ResultShowVO resultShowVO,List<NodeInstance> examNodeInstances){
		//获取其中一个节点id
		long nodeId=examNodeInstances.get(0).getNode().getId();
		String ids="";
		if (userData.getShowNodeGroupVOList()!=null)
			for(ShowNodeGroupVO showNodeGroupVO:userData.getShowNodeGroupVOList())
				if(showNodeGroupVO.getChildNodeIds().indexOf(String.valueOf(nodeId))>-1){
					ids=showNodeGroupVO.getChildNodeIds();
					break;
				}
		String sql="select sum(a.score) score,sum(a.time_used) time_used,sum(a.sum_correct_items) sum_correct_items,"
			+" sum(a.sum_unfinished_items) sum_unfinished_items,sum(a.unsure_mark_items) unsure_mark_items,sum(a.sum_incorrect_items) sum_incorrect_items,"
			+" sum(a.sum_zero_star_items) sum_zero_star_items,sum(a.sum_half_star_items) sum_half_star_items,"
			+" sum(a.sum_one_star_items) sum_one_star_items,sum(a.sum_two_star_items) sum_two_star_items,"
			+" sum(a.sum_three_star_items) sum_three_star_items,sum(a.sum_four_star_items) sum_four_star_items,"
			+" sum(a.sum_five_star_items) sum_five_star_items ,sum(e.total_score) total_paper_score,sum(d.items_num) items_num"
			+" from current_test_status a,asf_node_instance b,asf_node c,paper_assembling_policy d,dynamic_assembled_paper e"
			+" where a.node_instance_id=b.id and b.id=e.node_instance_id and b.node_id=c.id and c.id=d.node_id and c.node_type='PRACTICE'"
			+" and b.process_instance_id=? and c.id in ("+ids+")";
		Map<String,Object> map=this.getJdbcTemplate().queryForMap(sql, new Object[]{userData.getProcessInstanceId()});
		resultShowVO.setGroupTimeUsed(DateUtil.timeFormat(Integer.valueOf(map.get("time_used").toString())));
		resultShowVO.setGroupItemsNum(Integer.valueOf(map.get("items_num").toString()));
		resultShowVO.setGroupFinishItemsNum(Integer.valueOf(map.get("sum_correct_items").toString())+Integer.valueOf(map.get("sum_incorrect_items").toString()));
		resultShowVO.setGroupScore(Float.valueOf(map.get("score").toString()));
		resultShowVO.setGroupAccuracyRate(Float.valueOf(MathUtil.percent(resultShowVO.getGroupScore(), this.getTotalScore(userData.getProcessInstanceId(),ids,NodeType.PRACTICE.toString()))));
		resultShowVO.setGroupStarNum(Integer.valueOf(map.get("sum_half_star_items").toString())*0.5f+
			Integer.valueOf(map.get("sum_one_star_items").toString())+
			Integer.valueOf(map.get("sum_two_star_items").toString())*2+
			Integer.valueOf(map.get("sum_three_star_items").toString())*3+
			Integer.valueOf(map.get("sum_four_star_items").toString())*4+
			Integer.valueOf(map.get("sum_five_star_items").toString())*5);
		resultShowVO.setGroupMasteryRate(Float.valueOf(CalculateUtil.masteryRate(
				Integer.valueOf(map.get("sum_zero_star_items").toString()),
				Integer.valueOf(map.get("sum_half_star_items").toString()),
				Integer.valueOf(map.get("sum_one_star_items").toString()),
				Integer.valueOf(map.get("sum_two_star_items").toString()),
				Integer.valueOf(map.get("sum_three_star_items").toString()),
				Integer.valueOf(map.get("sum_four_star_items").toString()),
				Integer.valueOf(map.get("sum_five_star_items").toString()),
				resultShowVO.getGroupFinishItemsNum())));
		return resultShowVO;
	}
	
	/**
	 * 验证所有节点是否全为通过状态，全通过则更新流程状态为“完成”状态（-1）
	 */
	@SuppressWarnings("unchecked")
	public boolean hasProcessInstanceStop(UserDataVO userData){
		String sql="select a.node_status from asf_node_instance a, asf_node b " +
				   "where  a.node_id=b.id and b.node_type in('EVALUATE','PHASETEST','PRACTICE','UNITTEST') and a.process_instance_id=?";
		List list=this.getJdbcTemplate().queryForList(sql, new Object[]{userData.getProcessInstanceId()});
		boolean tag=false;
		if(list!=null&&list.size()>0)
			for(int i=0;i<list.size();i++) {
				Map map=(Map)list.get(i);
				if((Integer)map.get("node_status")<2){
					tag=true;
					break;
				}
		}
		if(tag) return false;
		
		//更新流程实例状态
		ProcessInstance po=this.get(ProcessInstance.class, userData.getProcessInstanceId());		
		po.setProcessStatus(ProcessStatus.STOPED);
		po.setEnd_Time(new Date());
		this.save(po);
		return true;
	}
	/**
	 * 除训练类型测试更新整个流程进度
	 */
	public void updateProcessNoTrainingStatus(UserDataVO userData){
		//获取 流程实例训练情况 po
		ProcessTrainingStatus po=this.get(ProcessTrainingStatus.class,userData.getProcessInstanceId());
		//统计通过训练节点的试卷总分数
		int passTotalScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b,asf_node_instance c"
				+" where a.id=b.node_id and a.id=c.node_id and c.node_status in(2,3) and c.process_instance_id=? "
				, new Object[]{userData.getProcessInstanceId()});
		//统计整个流程试卷训练节点的总分数
		int totalPaperScore=this.getJdbcTemplate().queryForInt(
				"select sum(b.total_score) total_score from asf_node a,paper_assembling_policy b"
				+" where a.id=b.node_id and a.process_definition_id=?"
				, new Object[]{userData.getProcessDefinitionId()});
		//计算总进度
		po.setLearningProcessRate(Float.valueOf(MathUtil.percent(passTotalScore, totalPaperScore)));
		this.save(po);
	}

	/**
	 * 根据子节点nodeIds获取其对应的总分数，如果参数为null即获取整个流程总的总分数
	 */
	@SuppressWarnings("unchecked")
	public float getTotalScore(Long processInstanceId,String nodeIds,String nodeType){
		String sql="select c.paper_assembling_mode,c.total_score total_score_1,e.total_score total_score_2 from "
			 +" asf_node_instance a left outer join dynamic_assembled_paper e on a.id=e.node_instance_id,"
			 +" asf_node b,paper_assembling_policy c,current_test_status d where d.node_instance_id=a.id and a.node_id=b.id and b.id=c.node_id and a.process_instance_id=? "
			 +" and a.node_status in(1,2,3) and b.node_type=?";
		if(nodeIds!=null&&!nodeIds.equals(""))
			sql+="and b.id in("+nodeIds+")";
		float outValue=0;
		List list=this.getJdbcTemplate().queryForList(sql, new Object[]{processInstanceId,nodeType});
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);			
			if(map.get("total_score_2")!=null)
				outValue+=(Float.valueOf(map.get("total_score_2").toString())).floatValue();
			else
				outValue+=(Float.valueOf(map.get("total_score_1").toString())).floatValue();			
		}
		return outValue;
	}
	
	/**
	 * 根据子节点nodeIds获取其对应的总分数，如果参数为null即获取整个流程总的总分数
	 */
	@SuppressWarnings("unchecked")
	public float getTotalScoreForMpc(Long processInstanceId,String nodeIds){
		String sql="select c.paper_assembling_mode,c.total_score total_score_1,e.total_score total_score_2 from "
			 +" asf_node_instance a left outer join dynamic_assembled_paper e on a.id=e.node_instance_id,"
			 +" asf_node b,paper_assembling_policy c,current_test_status d where d.node_instance_id=a.id and a.node_id=b.id and b.id=c.node_id and a.process_instance_id=? "
			 +" and a.node_status in(1,2,3)";
		if(nodeIds!=null&&!nodeIds.equals(""))
			sql+="and b.id in("+nodeIds+")";
		float outValue=0;
		List list=this.getJdbcTemplate().queryForList(sql, new Object[]{processInstanceId});
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);			
			if(map.get("total_score_2")!=null)
				outValue+=(Float.valueOf(map.get("total_score_2").toString())).floatValue();
			else
				outValue+=(Float.valueOf(map.get("total_score_1").toString())).floatValue();			
		}
		return outValue;
	}
	
	/**
	 * 判断阶段测试是往哪里跳转
	 * ahead
	 * backward
	 * next
	 */
	@SuppressWarnings("unchecked")
	public String instanceNodeWhereToJump(NodeInstance currentNodeIns,Integer rightRate){
		long nodeId=0;
		@SuppressWarnings("unused")
		String outString="ahead";
		List<PhaseTestNodePolicy> list=this.find(
				"from PhaseTestNodePolicy where asfNode.id=? ",currentNodeIns.getNode().getId());
		int i=0;
		if(!list.isEmpty())
			for(PhaseTestNodePolicy po:list){				
				/*if((po.getStartValue()<rightRate&&po.getEndValue()>rightRate)
						||(rightRate==0&&po.getStartValue()==0)
						||(rightRate==100&&po.getEndValue()==100)){
					nodeId=po.getJumpPosition();
					break;
				}*/
				boolean tag=true;
				if((rightRate>=po.getStartValue()&&rightRate<po.getEndValue())
						||(i==list.size()-1&&rightRate==100&&po.getEndValue()==100))
					tag=false;
				i++;
				if(tag)
					continue;
				nodeId=po.getJumpPosition();
			}
		
		/*List<Node> nodeList=this.find("from Node where processDefinition.id=? order by orderNum"
					,currentNodeIns.getNode().getProcessDefinition().getId());
	//	List<Node> outList=new ArrayList<Node>();
		Node jumpTo=null;
		boolean tag=true;
		for(Node node:nodeList){
			if(node.getNodeType()==NodeType.GROUP)
				continue;
			if(node.getId()==currentNodeIns.getNode().getId()&&tag){
				outString="ahead";
				tag=false;
				continue;
			}
			else if(node.getId()==nodeId&&tag){
				outString="backward";
				tag=false;				
			//	outList.add(node);
				jumpTo=node;
				continue;
			}
			else if(node.getId()==currentNodeIns.getNode().getId()&&!tag){				
				tag=true;
			//	outList.add(node);
				break;
			}else if(node.getId()==nodeId&&!tag){				
				tag=true;
			//	outList.add(node);
				break;
			}
			
			if(!tag)
				outList.add(node);
				
		}*/
		//if(outString.equals("ahead")&&outList.size()==1)
		Node jumpTo=this.get(Node.class,nodeId);
		if(outString.equals("ahead")
				&&jumpTo!=null
				&&currentNodeIns.getNode().getNextNode()!=null
				&&jumpTo.getId()==currentNodeIns.getNode().getNextNode().getId())
			outString="next";
	/*	Object[] objects=new Object[2];
		objects[0]=outString;
		objects[1]=outList;*/
		return outString;
	}
	
	/**
	 * 获取此用户所有节点实例
	 */
/*	@SuppressWarnings("unchecked")
	public List<NodeInstanceVO> getNodeInstanceVO(NodeInstance nodeInstance){
		List<NodeInstanceVO> outList=new ArrayList<NodeInstanceVO>();
		List<NodeInstance> nodeInstanceList=this.find("from NodeInstance where processInstance.id=?",nodeInstance.getProcessInstance().getId());
		if(!nodeInstanceList.isEmpty())
			for(NodeInstance po:nodeInstanceList){
				if(po.getNode().getNodeType()==NodeType.GROUP)
					continue;
				NodeInstanceVO vo=new NodeInstanceVO();
				vo.setId(po.getId());
				vo.setNodeStatus(po.getNodeStatus());
				outList.add(vo);
			}
		return outList;
	}*/
	/**
	 * 更新回旧的节点状态，并修改当前节点未通过状态，把流程默认的当前节点的下一个节点作为流程新的当前节点。
	 */
/*	@SuppressWarnings("unchecked")
	public void updateInfo(NodeInstance currentNodeIns,List<NodeInstanceVO> list){
		List<NodeInstance> nodeInstanceList=this.find("from NodeInstance where processInstance.id=?",
				currentNodeIns.getProcessInstance().getId());
		if(!nodeInstanceList.isEmpty())
			for(NodeInstance po:nodeInstanceList){
				
				if(po.getNode().getNodeType()==NodeType.GROUP)
					continue;
				for(NodeInstanceVO vo:list){
					
					if(po.getId()!=vo.getId().longValue())
						continue;
					if(po.getNodeStatus()!=vo.getNodeStatus()){
						
						if(po.getId()==currentNodeIns.getId())
							po.setNodeStatus(NodeStatus.PASSED);
						else
							po.setNodeStatus(vo.getNodeStatus());
						this.save(po);
						break;
					}else
						continue;
					
				}
			}
		//获取系统流程默认的下一个节点，并更新流程当前节点
		ProcessInstance processInstance=currentNodeIns.getProcessInstance();
		processInstance.setNode(currentNodeIns.getNode().getNextNode());
		this.save(processInstance);
	}*/
	/**
	 * 获取当前节点的所包括的训练及测试节点
	 */
	public List<Node> getNodesOfPhaseTest(NodeInstance currentNodeIns){
		
		Node groupNode=currentNodeIns.getNode().getNextNode();
		if (groupNode==null) return null;		
		else return groupNode.getNodeGroup().getNodes();
	}
}
