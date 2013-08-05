package com.ambow.trainingengine.point.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.util.DateCompute;
import com.ambow.core.util.DateUtil;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.MembershipPointHistory;
import com.ambow.trainingengine.point.web.data.PointStatVO;

public class MyPointService extends HibernateEntityDao<MembershipPoint> {

	private SimpleJdbcTemplate jdbcTemplate;

	@SuppressWarnings( { "unchecked", "static-access" })
	public List getMembershipPoint(String userId, long processInstanceId) {
		//init
		List<PointStatVO> pointStatList = new ArrayList<PointStatVO>();
		
		pointStatList.add(new PointStatVO(1));
		pointStatList.add(new PointStatVO(2));
		pointStatList.add(new PointStatVO(3));
		pointStatList.add(new PointStatVO(4));
		pointStatList.add(new PointStatVO(5));
		
		//last week and last month point
		DateUtil dateUtil = new DateUtil();
		DateCompute dateCompute = new DateCompute();
		
		String sql = "select point_type as type, sum(point) as sumpoint from membership_point_history "
				+ " where asf_process_instance_id=? and operate_time >=? and operate_time <=? group by point_type";
		List<Map<String, Object>> lastWeekList = jdbcTemplate.queryForList(sql, processInstanceId, 
				dateCompute.getPreviousMonday(), dateUtil.getBeforeDay(dateCompute.getCurrentMonday()));
		for (Map<String, Object> lastWeekMap: lastWeekList) {
			Integer type = (Integer)lastWeekMap.get("type");
			Integer point = ((BigDecimal)lastWeekMap.get("sumpoint")).intValue(); 
			pointStatList.get(type-1).setLastWeekPoint(point);
		}
		
		String beforeMonthfist = dateUtil.format(dateUtil.getFirstDayOfMonth(dateCompute.getBeforeMonthDay()));
		String beforeMonthlast = dateUtil.format(dateUtil.getLastDayOfMonth(dateCompute.getBeforeMonthDay()));
		List<Map<String, Object>> lastMonthList = jdbcTemplate.queryForList(sql, processInstanceId, beforeMonthfist, beforeMonthlast);
		for (Map<String, Object> lastMonthMap: lastMonthList) {
			Integer type = (Integer)lastMonthMap.get("type");
			Integer point = ((BigDecimal)lastMonthMap.get("sumpoint")).intValue();
			pointStatList.get(type-1).setLastMonthPoint(point);
		}
		
		//min, max datatime and all points
		sql = "select point_type as type, sum(point) as countpoint, min(operate_time) as mintime, max(operate_time) as maxtime"
			+ " from membership_point_history "
			+ " where asf_process_instance_id=? group by point_type";
		List<Map<String, Object>> statList = jdbcTemplate.queryForList(sql, processInstanceId);
		for (Map<String, Object> statMap: statList) {
			Integer type = (Integer)statMap.get("type");
			Integer countPoint = ((BigDecimal)statMap.get("countpoint")).intValue();
			Date minTime = (Date)statMap.get("mintime");
			Date maxTime = (Date)statMap.get("maxtime");
			PointStatVO p = pointStatList.get(type-1);
			p.setCountPoint(countPoint);
			p.setMinOperateTime(minTime);
			p.setMaxOperateTime(maxTime);
		}
		
		//remove courage point
		pointStatList.remove(3-1);

		return pointStatList;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPointOrder(long processInstanceId,
			long processDefinitionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		String hql = "from MembershipPoint order by ";
		MembershipPoint mpView = this.get(MembershipPoint.class,
				processInstanceId);
		hql = "select count(*) FROM  MembershipPoint p where p.asfProcessInstance.processDefinition.id =?"
				+ "  and p.diligence>=? order by p.diligence desc";
		if (mpView != null) {

			map.put("diligence", findObjByHql(hql, processDefinitionId, mpView
					.getDiligence()));
			map.put("courage", findObjByHql(hql.replaceAll("diligence",
					"courage"), processDefinitionId, mpView.getCourage()));
			map.put("wisdom", findObjByHql(hql
					.replaceAll("diligence", "wisdom"), processDefinitionId,
					mpView.getWisdom()));
			map.put("dedicate", findObjByHql(hql.replaceAll("diligence",
					"dedicate"), processDefinitionId, mpView.getDedicate()));
			map.put("percipience", findObjByHql(hql.replaceAll("diligence",
					"percipience"), processDefinitionId, mpView
					.getPercipience()));

			map.put("mpView", mpView);
		}
		List<Object> list = null;
		String sql = "select wuser.real_name, mp.diligence "
				+ " from Membership_Point mp,webuser wuser, asf_process_instance api "
				+ " where wuser.id=api.actor and api.id=mp.asf_process_instance_id"
				+ " and api.process_definition_id = "
				+ String.valueOf(processDefinitionId);

		list = this.findExecute(sql + "	order by mp.diligence desc limit 10");
		map.put("diligenceList", list);

		list = this.findExecute(sql.replace("diligence", "courage")
				+ " order by mp.courage desc limit 10");
		map.put("courageList", list);

		list = this.findExecute(sql.replace("diligence", "wisdom")
				+ " order by mp.wisdom desc limit 10");
		map.put("wisdomList", list);

		list = this.findExecute(sql.replace("diligence", "dedicate")
				+ " order by mp.dedicate desc limit 10");
		map.put("dedicateList", list);

		list = this.findExecute(sql.replace("diligence", "percipience")
				+ " order by mp.percipience desc limit 10");
		map.put("percipienceList", list);
		return map;
	}

	@SuppressWarnings( { "static-access", "unchecked" })
	public List getPagePointHistory(long processInstanceId) {
		String hql = "  from MembershipPointHistory where asfProcessInstance.id=? order by operateTime desc  ";
		List<MembershipPointHistory> list = this.find(hql, processInstanceId);
		DateUtil dateUtil = new DateUtil();
		ArrayList listmap = new ArrayList();
		Map<String, Object> map = new HashMap();
		if (list != null) {

			for (int i = 0; i < list.size(); i++) {

				MembershipPointHistory mphView = (MembershipPointHistory) list
						.get(i);
				if (i == 0) {
					map = new HashMap();
					map.put("cause", mphView.getPointCause());
					map.put("point" + mphView.getPointType(), mphView
							.getPoint());
					map.put("timestr", dateUtil.format(
							mphView.getOperateTime(), "yyy-MM-dd hh:mm:ss"));
					if (list.size()==1) listmap.add(map);

				} else {
					MembershipPointHistory mphPre = (MembershipPointHistory) list
							.get(i - 1);
					String curr = dateUtil.format(mphPre.getOperateTime(),
							"yyy-MM-dd hh:mm:ss");
					String pre = dateUtil.format(mphView.getOperateTime(),
							"yyy-MM-dd hh:mm:ss");
					if (curr.equals(pre)
							&& mphPre.getPointType() != mphView.getPointType()) {
						map.put("cause", mphView.getPointCause());
						map.put("point" + mphView.getPointType(), mphView
								.getPoint());
						map.put("timestr", dateUtil.format(mphView
								.getOperateTime(), "yyy-MM-dd hh:mm:ss"));
						if (i == list.size() - 1) {
							listmap.add(map);
						}
						continue;
					} else {
						listmap.add(map);
						map = new HashMap();
						map.put("cause", mphView.getPointCause());
						map.put("point" + mphView.getPointType(), mphView
								.getPoint());
						map.put("timestr", dateUtil.format(mphView
								.getOperateTime(), "yyy-MM-dd hh:mm:ss"));
						if (i == list.size() - 1) {
							listmap.add(map);
						}
					}
				}

			}
		}
		return listmap;
	}

	@SuppressWarnings("unchecked")
	public List pagination(int pageNo, int pageSize, List list) {
		int end = pageNo * pageSize;
		List subList = null;
		if (list.size() >= end) {
			subList = list.subList((pageNo - 1) * pageSize, end);
		} else {
			subList = list.subList((pageNo - 1) * pageSize, list.size());
		}
		
		return subList;
	}

	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
