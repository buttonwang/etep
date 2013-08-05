package com.ambow.trainingengine.report.service;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.util.DateUtil;
import com.ambow.trainingengine.web.data.NodeVO;
import com.ambow.trainingengine.web.data.UserDataVO;
import com.generationjava.io.xml.SimpleXmlWriter;
import com.generationjava.io.xml.XmlWriter;

/*
 * TeacherManagerService.java
 * 
 * Created on 2010-1-7 上午10:40:47
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class TeacherManagerService extends ReportBaseService {

	
	@SuppressWarnings("unchecked")
	public Long getProcessInstanceId(int refID, String userID){
		String sql = "select id from asf_process_instance where process_definition_id=? and actor=?";
		List list = getJdbcTemplate().queryForList(sql, refID, userID);
		if (list.size()==0) return null;
		else {
			Map map = (Map)list.get(0);
			return (Long)map.get("id");
		}
	}
	
	/**
	 * 获取流程实例下的所有答题节点
	 */
	@SuppressWarnings("unchecked")
	public List<NodeVO> getNodeVOList(Long processInstanceId){
		String sql = " select concat(ng.name, '--', n.name) as node_name, ts.start_time, ts.end_time, " +
				  	 " ifnull(ap.standard_answering_time, ap.answering_time) as answering_time," +
				  	 " ts.time_used_total, ts.accuracy_rate " +
					 " from asf_process_instance pi inner join asf_node_instance ni on pi.id= ni.process_instance_id " +
					 " inner join asf_node n  on n.id = ni.node_id " +
					 " inner join asf_node ng on n.node_group_id = ng.id " +
					 " inner join current_test_status ts on ni.id = ts.node_instance_id " +
					 " left  join paper_assembling_policy ap on n.id = ap.node_id " +
					 " where pi.id = ? and n.node_type != 'GROUP' " +
					 " order by n.order_num";
		List mapList=this.getJdbcTemplate().queryForList(sql, processInstanceId);
		
		List<NodeVO> nodeVOList=new ArrayList<NodeVO>();
		for(int i=0;i<mapList.size();i++){
			Map map=(Map)mapList.get(i);
			NodeVO nodeVO = new NodeVO();
			nodeVO.setNodeName(map.get("node_name").toString());
			nodeVO.setStartTime((Date)map.get("start_time"));
			nodeVO.setEndTime((Date)map.get("end_time"));
			nodeVO.setAnsweringTime(((Long)map.get("answering_time")).intValue());
			nodeVO.setUsedTime((Integer)map.get("time_used_total"));
			nodeVO.setAccuracyRate((Float)map.get("accuracy_rate"));
			nodeVOList.add(nodeVO);
		}
		return nodeVOList;
	}

	public String UserTestListToXML(List<NodeVO> nodeList) throws IOException {
        Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeEntity("Ambow");
        xmlwriter.writeEntity("UserTests");
        for (NodeVO nodeVO: nodeList) {
        	xmlwriter.writeEntity("UserTest");
        	xmlwriter.writeEntityWithText("TestName", nodeVO.getNodeName());
            xmlwriter.writeEntityWithText("TestDate", DateUtil.format(nodeVO.getStartTime()));
            xmlwriter.writeEntityWithText("TestBeginTime", DateUtil.format(nodeVO.getStartTime(), "HH:mm"));
            xmlwriter.writeEntityWithText("TestEndTime", DateUtil.format(nodeVO.getEndTime(), "HH:mm"));
            xmlwriter.writeEntityWithText("FixTime", DateUtil.timeFormat(nodeVO.getAnsweringTime()));
            xmlwriter.writeEntityWithText("RealTime", DateUtil.timeFormat(nodeVO.getUsedTime()));
            xmlwriter.writeEntityWithText("RightRate", nodeVO.getAccuracyRate()==null?"":nodeVO.getAccuracyRate().toString()+"%");
            xmlwriter.endEntity();
        }
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.close();
        return writer.toString();
	}
	
	public String UserTestEmptyXML() throws IOException {
		Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeEntity("Ambow");
        xmlwriter.writeEntity("UserTests");
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.close();
        return writer.toString();
	}
	
	
	/**
	 * 获取班级的学习情况
	 */
	@SuppressWarnings("unchecked")
	public List getCoursesTestAnalyseList(int refID, String classID){
		String sql = " select n.id, max(concat(ng.name, '--', n.name)) as node_name, " +
				"    sum(case when ni.node_status > 0 then 1 else 0 end) as study_num, " +
				"    sum(case when ni.node_status = 2 then 1 else 0 end) as pass_num " +
				"    from  asf_process_definition pd " +
				"    inner join asf_node n on pd.id = n.process_definition_id " +
				"	 left  join asf_node ng on n.node_group_id = ng.id " +
				"	 left  join asf_node_instance ni on ni.node_id = n.id " +
				" 	 left  join asf_process_instance pi on ni.process_instance_id = pi.id " +
				"	 left  join current_test_status ts on ni.id = ts.node_instance_id " +
				"	 left  join process_training_status ps on ps.process_instance_id = pi.id" +
				"	 where pd.id = ? and ps.class_num = ? " +
				"	 and n.node_type != 'GROUP' " +
				"	 group by n.id " +
				"	 order by n.order_num";
		List mapList=this.getJdbcTemplate().queryForList(sql, refID, classID);
		
		return mapList;
	}
	
	@SuppressWarnings("unchecked")
	public String CoursesTestAnalyseToXML(List mapList) throws IOException {
        Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeEntity("Ambow");
        xmlwriter.writeEntity("TestLists");
        
        for(int i=0; i<mapList.size();i++){
			Map map=(Map)mapList.get(i);
			
			xmlwriter.writeEntity("Test");
        	xmlwriter.writeEntityWithText("TestID",   map.get("id").toString());
        	xmlwriter.writeEntityWithText("TestName", map.get("node_name").toString());
        	xmlwriter.writeEntityWithText("StudyNum", map.get("study_num").toString());
        	xmlwriter.writeEntityWithText("PassNum",  map.get("pass_num").toString());
        	xmlwriter.endEntity();
		}
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.close();
        return writer.toString();
	}
	
	/**
	 * 获取某节点的规定学习时间
	 */
	@SuppressWarnings("unchecked")
	public Integer getFixTime(int testID){
		String sql = "select ifnull(standard_answering_time, answering_time) as fix_time " +
				"	from asf_node n left  join paper_assembling_policy ap on n.id = ap.node_id " +
				"	where n.id = ?";
		List mapList=this.getJdbcTemplate().queryForList(sql, testID);
		
		if (mapList.size()==0) return 0;
		else {
			Map map = (Map)mapList.get(0);
			return (Integer)map.get("fix_time");
		}
	}
	
	/**
	 * 获取某班级某节点的学习情况
	 */
	@SuppressWarnings("unchecked")
	public List getTestTestList( int testID, String classID){
		String sql = " select u.real_name, ts.start_time, ts.end_time, ts.time_used_total, ts.accuracy_rate" +
				"	from   asf_node n" +
				"	inner  join asf_node_instance ni on ni.node_id = n.id" +
				"	inner join asf_process_instance pi on ni.process_instance_id = pi.id" +
				"	inner join current_test_status ts on ni.id = ts.node_instance_id" +
				"	inner  join process_training_status ps on ps.process_instance_id = pi.id" +
				"	inner join webuser u on pi.actor = u.id" +
				"	where  n.id = ? and ps.class_num = ?" +
				"	order by ts.start_time";
		List mapList=this.getJdbcTemplate().queryForList(sql, testID, classID);
		
		return mapList;
	}
	
	@SuppressWarnings("unchecked")
	public String TestTestToXML(int fixTime, List mapList) throws IOException {
        Writer writer = new java.io.StringWriter();
        XmlWriter xmlwriter = new SimpleXmlWriter(writer);
        xmlwriter.writeXmlVersion("1.0", "utf-8");
        xmlwriter.writeEntity("Ambow");
        xmlwriter.writeEntity("TestLists");
        xmlwriter.writeEntityWithText("FixTiem",  DateUtil.timeFormat(fixTime));
        for(int i=0; i<mapList.size();i++){
			Map map=(Map)mapList.get(i);
			
			xmlwriter.writeEntity("Test");
        	xmlwriter.writeEntityWithText("UserName", map.get("real_name").toString());
        	xmlwriter.writeEntityWithText("TestDate", DateUtil.format((Date)map.get("start_time")));
        	xmlwriter.writeEntityWithText("TestBeginTime",DateUtil.format((Date)map.get("start_time"), "HH:mm"));
        	xmlwriter.writeEntityWithText("TestEndTime",  DateUtil.format((Date)map.get("end_time"), "HH:mm"));
        	xmlwriter.writeEntityWithText("RealTime",  DateUtil.timeFormat((Integer)map.get("time_used_total")));
        	xmlwriter.writeEntityWithText("RightRate", map.get("accuracy_rate")==null?"":map.get("accuracy_rate").toString()+"%");
        	xmlwriter.endEntity();
		}
        xmlwriter.endEntity();
        xmlwriter.endEntity();
        xmlwriter.close();
        return writer.toString();
	}
}
