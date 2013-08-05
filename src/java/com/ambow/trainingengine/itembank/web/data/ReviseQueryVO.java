package com.ambow.trainingengine.itembank.web.data;

import java.math.BigDecimal;

import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.UtilAndTool_L;

/*****************************************************
 * @USE: 试题审校页面，查询试题的审校信息的时候，获取到的查询条件及对查询条件的封装
 * @FOR: 试题审校页面，查看试题审校信息的时候，页面上面提交的查询条件的信息保存于此类当中，
 * 		同时，根据所有的查询条件，得到对这些查询条件进行判断后的HQL语句
 * 
 * @AUTHOR: L.赵亚
 * @DATE: 2010.04.02.16.07
 * 
 */
public class ReviseQueryVO {
	private int pageNo = 1; //分布页码
	private String title; //标题：M'数学试题' P '物理试题' C '化学试题'
	private String knowledgePoint; //知识点
	private String grade; //学级
	private String subject_code; //课程码
	private String reviewRound; //复习轮次
	private String reviseStatus; //审校状态
	private String reviseTimeBegin; //审校查询开始时间
	private String reviseTimeEnd; //审校查询结束时间
	private String reviseRecordTimeBegin; //纠错查询开始时间
	private String reviseRecordTimeEnd; //纠错查询结束时间
	private BigDecimal reviseNum; //审校人数
	private BigDecimal revisedNum; //审校通过人数
	private Integer reviseRecNum; //纠错人数
	private String code; //试题编码
	private String content; //试题题干
	private String importFile; //导入文件
	private Integer loginId; //登录ID
	private String replyType; //回复类型
	private SysUser su; //用户信息
	private Integer reviser;

	private BigDecimal maxRevised;
	private BigDecimal maxRevise;
	
	
	/********************************************
	 * @USE: 根据查询条件，得到查询语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.07.15.46
	 * 
	 */
	public String generateHQL(){
		String hql = "from Item ite ";
		hql += " left join ite.itemRevises iir with iir.reviser=" + this.su.getId();
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint))
			hql += " left join ite.knowledgePoints ikp ";
		if(UtilAndTool_L.checkNotNullOrZero(this.grade))
			hql += " left join ite.grades igr ";
		hql += " where ite.subject.code='" + this.subject_code + "' and ite.status>0 ";
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint))
			hql += " and ikp.code like '" + this.knowledgePoint.trim() + "%' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.grade))
			hql += " and igr.code='" + this.grade + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviewRound)){
			String[] strArr = this.reviewRound.split(",");
			String strS = "";
			for(String str : strArr){
				if(strS.length()>0)
					strS += ",";
				strS += "'" + str + "'";
			}
			hql += " and ite.reviewRound in (" + this.reviewRound.trim() + ") ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and iir.reviseTime>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql += " and iir.reviseTime<='" + this.reviseTimeEnd.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.code))
			hql += " and ite.code like '" + this.code.trim() + "%' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.content))
			hql += " and ite.content like '%" + this.content.trim() + "%' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.importFile))
			hql += " and ite.importFile like '%" + this.importFile.trim() + "%' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseStatus)){
			if(this.reviseStatus.equals("0")){
				hql += " and (iir.reviseStatus is null or (iir.reviseStatus!=1 ";
				if(this.su!=null)
					hql += " and iir.reviser=" + this.su.getId();
				hql += "))";
			}else if(this.reviseStatus.equals("1")&&this.su!=null){
				if(this.su!=null)
					hql += " and iir.reviser=" + this.su.getId();
				hql += " and iir.reviseStatus>0 ";
			}
		}
		if(this.replyType!=null||UtilAndTool_L.checkNotNullOrZero(this.reviseRecordTimeBegin)
				||UtilAndTool_L.checkNotNullOrZero(this.reviseRecordTimeEnd)){
			hql += " and (iir.replyType>-1 or iir.replyType is null ";
			hql += " and length(iir.reviseRecord)>0) ";
			if("0".equals(this.replyType))
				hql += " and (iir.replyType=0 or iir.replyType is null)";
			else if("1".equals(this.replyType))
				hql += " and iir.replyType>0 ";
			if(UtilAndTool_L.checkNotNullOrZero(this.reviseRecordTimeBegin))
				hql += " and iir.reviseRecordTime>='" + this.reviseRecordTimeBegin + "' ";
			if(UtilAndTool_L.checkNotNullOrZero(this.reviseRecordTimeEnd))
				hql += " and iir.reviseRecordTime<='" + this.reviseRecordTimeEnd + "' ";
			
			hql += " order by iir.reviseReplyTime desc, iir.reviseRecordTime desc ";
		}
		hql += " order by iir.reviseReplyTime desc, iir.reviseRecordTime desc ";
		
		return hql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到查询语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校纠错页面查询，得到查询条件，此方法把查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.07.15.46
	 * 
	 */
	public String generateReplayHQL(){
//		String hql = " select it from Item it, ( select ir.item.id as iteId ";
//		hql += " max(ir.reviseReplyTime) as iRepTime, min(ir.reviseRecordTime) as iRecTime from ItemRevise ir ";
//		hql += " where length(ir.reviseRecord)>0 and ir.reviseRecordTime!=null ";
//		hql += " and ir.item.subject.code='" + this.subject_code + "' ";
//		hql += " (ir.reviseReplyTime=max(ir.reviseReplyTime) or ";
//		hql += " ir.reviseRecordTime=max(ir.reviseRecordTime)) ";
//		if(this.replyType==null) this.replyType = "0";
//		if("0".equals(this.replyType))
//			hql += " and (ir.replyType=0 or ir.replyType is null) ";
//		else if("1".equals(this.replyType))
//			hql += " and ir.replyType>0 ";
//		hql += " group by ir.item.id ) as itSub ";
//		hql += " where itSub.id=it.id ";
//		hql += " order by itSub.iRepTime desc, itSub.iRecTime asc ";
		
		String hql = " from Item it ";
		hql += " where it.id in (select ite.id from Item ite ";
		hql += " left join ite.itemRevises iteir ";
		hql += " where length(iteir.reviseRecord)>0 and iteir.reviseRecordTime!=null ";
		hql += " and ite.subject.code='" + this.subject_code + "' ";
		if(this.replyType==null) this.replyType = "0";
		if("0".equals(this.replyType))
			hql += " and (iteir.replyType=0 or iteir.replyType is null)";
		else if("1".equals(this.replyType))
			hql += " and iteir.replyType>0 ";
		if(UtilAndTool_L.checkNotNullOrZero(code))
			hql += " and ite.code='" + code + "' ";
//		hql += " order by iteir.reviseReplyTime desc, iteir.reviseRecordTime desc) itemResult ";
		hql += " ) ";
//		hql += " order by iirOut.reviseReplyTime desc, ";
//		hql += " iirOut.reviseRecordTime desc";
		
		return hql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到查询语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校纠错页面查询，得到查询条件，此方法把查询条件进行整理，生成SQL语句，
	 * 		以便后台程序查询。因为用HQL语句，不能实现查询出符合条件的题目的信息，所以，
	 * 		这里实现了先查询出符合条件的题目的ID的语句。然后再通过题目ID得到题目的完整信息
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.04.07.15.46
	 * 
	 */
	public String generateReplayHQL2() {
		String sql = "select distinct ir.item_id ";
		sql += " from item_revise ir left outer join item on ir.item_id=item.id";
		sql +=" where length(ir.revise_record)>0 and ir.revise_record_time is not null ";
		sql += " and item.subject_code='" + this.subject_code + "' and item.status>0 ";
		if(this.replyType==null) this.replyType = "0";
//		if("0".equals(this.replyType))
//			sql += " and (ir.reply_type=0 or ir.reply_type is null) ";
//		else if("1".equals(this.replyType))
//			sql += " and ir.reply_type>0 ";
		if("0".equals(this.replyType))
			sql += " and (ir.revise_reply_time is null and ir.revise_record_time is not null) ";
		else if("1".equals(this.replyType))
			sql += " and (ir.revise_reply_time is not null and ir.revise_record_time is not null) ";
		
		if(UtilAndTool_L.checkNotNullOrZero(code))
			sql += " and item.code='" + code + "' ";
		sql += " order by ir.revise_reply_time desc, ir.revise_record_time asc ";
		
		return sql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到统计查询语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.09.30
	 * 
	 */
	public String statisticsHQL(String reviser,String correctnum,boolean flag){
		String hql = "select item.id";
		if(flag)
			hql+=",ir.revise_status,ir.revise_record_time,ir.revise_reply_time,ir.id ";
		hql+=" from item  left outer join grade_item_ref gir on item.id=gir.item_id  ";
		hql+= " left outer join   "; 
		if(Integer.parseInt(correctnum)>0){ 
			hql +=" (select id,item_id,reviser,revise_time,revise_status,revise_record_time,revise_reply_time from item_revise ir where ir.revise_record_time is not null group by ir.item_id having count(ir.reviser)>="+Integer.parseInt(correctnum)+") ir";
		}else{
			hql+=" item_revise ir ";
		}
		hql+=" on  item.id=ir.item_id ";
		hql+=" where item.subject_code='" + this.subject_code + "' and item.status>0 ";
		if(UtilAndTool_L.checkNotNullOrZero(this.grade))
			hql += " and gir.grade_code='" + this.grade + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint))
			hql += " and item.id in (select item_id from knowledge_point_item_ref where knowledge_point_code = '" + this.knowledgePoint.trim() + "') ";
		hql +="  and ir.reviser= "+reviser;
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and ir.revise_time>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql += " and ir.revise_time<='" + this.reviseTimeEnd.trim() + "' ";
		
		
		return hql;
	}
	
	/********************************************
	 * @USE: 得到审校人查询语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 审校人查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.09.30
	 * 
	 */
	public String reviserHQL(){
		String hql = "select id,real_name from sys_user where id in ( select ir.reviser from item_revise ir left join item ite on ir.item_id=ite.id where ite.subject_code='" + this.subject_code + "' and ite.status>0 group by ir.reviser ) ";
//		String hql = " from SysUser where id in ( select ir.reviser from ItemRevise ir left join ir.item ite  where ite.subject.code='" + this.subject_code + "' and ite.status>0 group by ir.reviser ) ";
		return hql;
	}
	
	
	/**************************************************
	 * @USE: 试题审校情况查询
	 * @PARAM: ...
	 * @RETURN: sql语句
	 * @FOR: 试题审校情况查询时的SQL语句生成，查询得到Item的ID信息，审校总人数，审校通过总人数，纠错数
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.05.25.14.14
	 * 
	 */
	public String generateStatusSQL() {
		String sql = "select item.id item_id, ";
	    sql += " sum(case when ir.id is not null then 1 else 0 end) count_ir, ";
	    sql += " sum(case when ir.revise_status is null then 0 else ir.revise_status end) count_rs, "; 
	    sql += " sum(case when ir.revise_record_time is null then 0 else 1 end) count_record ";
		String sqlLeft = " from item item ";
		sqlLeft += " left outer join item_revise ir on ir.item_id=item.id ";
		String sqlHaving = " having 1=1 ";
		String sqlGroup = " group by item.id ";
		
		String sqlWhere = " where item.subject_code='" + this.subject_code + "'  ";
		sqlWhere += " and item.status>0 ";
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint)){
			sqlLeft += " left outer join knowledge_point_item_ref kir on kir.item_id=item.id ";
			sqlWhere += " and kir.knowledge_point_code='" + this.knowledgePoint + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.grade)){
			sqlLeft += " left outer join grade_item_ref gir on gir.item_id=item.id ";
			sqlWhere += " and gir.grade_code='" + this.grade + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviewRound)){
			String[] strArr = this.reviewRound.split(",");
			String strS = "";
			for(String str : strArr){
				if(strS.length()>0)
					strS += ",";
				strS += "'" + str + "'";
			}
			sqlWhere += " and item.review_round in (" + this.reviewRound.trim() + ") ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseStatus)){
			if(this.reviseStatus.equals("0")){
				sqlWhere += " and (item.revise_status is null or (item.revise_status!=1))";
			}else if(this.reviseStatus.equals("1")){
				sqlWhere += " and item.revise_status>0 ";
			}
		}
		if(this.reviseNum!=null&&(this.reviseNum.compareTo(new BigDecimal("0"))>=0)){
			sqlHaving += " and sum(case when ir.id is not null then 1 else 0 end)<=" + this.reviseNum;
		}
		if(this.revisedNum!=null&&(this.revisedNum.compareTo(new BigDecimal("0"))>=0)){
			sqlHaving += " and sum(case when ir.revise_status is null then 0 else ir.revise_status end)<=" + this.revisedNum;
		}
		if(this.reviseRecNum!=null&&reviseRecNum>=0){
			sqlHaving += " and sum(case when ir.revise_record_time is null then 0 else 1 end)>=" + this.reviseRecNum;
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.code)){
			sqlWhere += " and item.code='" + this.code + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.content)){
			sqlWhere += " and item.content like '%" + this.content + "%' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.code)){
			sqlWhere += " and item.code='" + this.code + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.importFile)){
			sqlWhere += " and item.import_file='" + this.importFile + "' ";
		}
		
		sql = sql + sqlLeft + sqlWhere + sqlGroup + sqlHaving;
		
		return sql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到纠错类型语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.15.30
	 * 
	 */
	public String errorStatusHQL(String reviser,String correctnum){
		String hql = "select reply_type,count(reply_type) count from item_revise where item_id in ( ";//item_id in (
		hql+=statisticsHQL(reviser,correctnum,false) ;
		hql+= " ) and reply_type!=0  "; 
		if(Integer.parseInt(correctnum)>0){
			hql +=" and item_id in (select ir.item_id from item_revise ir where ir.revise_record_time is not null group by ir.item_id having count(ir.reviser)>="+Integer.parseInt(correctnum)+")";
		}
		hql+=" and reviser= "+reviser;//) and
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and revise_time>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql += " and revise_time<='" + this.reviseTimeEnd.trim() + "' ";
		hql+=" group by reply_type ";
		return hql;
	}

	/**************************************************
	 * @USE: 试题审校情况查询
	 * @PARAM: ...
	 * @RETURN: sql语句
	 * @FOR: 试题审校情况查询时的SQL语句生成，查询得到Item的ID信息，审校总人数，审校通过总人数，纠错数
	 * 
	 * @AUTHOR: L.赵亚
	 * @DATE: 2010.05.25.14.14
	 * 
	 */
	public String generateStatusSQLCount() {
	    String sql = " select count(*) allcount, ";
//	    sql += " sum(case when count_ir>0 then 1 else 0 end) all_revise, ";
		sql += " sum(case when count_rs>0 then 1 else 0 end) all_rs, ";
		sql += " max(count_ir) max_ir, ";
		sql += " max(count_rs) max_rs ";
//		sql += " sum(case when count_record>0 then 1 else 0 end) all_record ";

		sql += " from (select item.id,  ";
		sql += " sum(case when ir.id is not null then 1 else 0 end) count_ir, ";
		sql += " sum(case when ir.revise_status is null then 0 else ir.revise_status end) count_rs,  ";
		sql += " sum(case when ir.revise_record_time is null then 0 else 1 end) count_record ";
		String sqlLeft = " from item item ";
		sqlLeft += " left outer join item_revise ir on ir.item_id=item.id ";
		
		String sqlHaving = " having 1=1 ";
		String sqlGroup = " group by item.id ";
		
		String sqlWhere = " where item.subject_code='" + this.subject_code + "'  ";
		sqlWhere += " and item.status>0 ";
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint)){
			sqlLeft += " left outer join knowledge_point_item_ref kir on kir.item_id=item.id ";
			sqlWhere += " and kir.knowledge_point_code='" + this.knowledgePoint + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.grade)){
			sqlLeft += " left outer join grade_item_ref gir on gir.item_id=item.id ";
			sqlWhere += " and gir.grade_code='" + this.grade + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviewRound)){
			String[] strArr = this.reviewRound.split(",");
			String strS = "";
			for(String str : strArr){
				if(strS.length()>0)
					strS += ",";
				strS += "'" + str + "'";
			}
			sqlWhere += " and item.review_round in (" + this.reviewRound.trim() + ") ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseStatus)){
			if(this.reviseStatus.equals("0")){
				sqlWhere += " and (item.revise_status is null or (item.revise_status!=1))";
			}else if(this.reviseStatus.equals("1")){
				sqlWhere += " and item.revise_status>0 ";
			}
		}
		if(this.reviseNum!=null&&(this.reviseNum.compareTo(new BigDecimal("0"))>=0)){
			sqlHaving += " and sum(case when ir.id is not null then 1 else 0 end)<=" + this.reviseNum;
		}
		if(this.revisedNum!=null&&(this.revisedNum.compareTo(new BigDecimal("0"))>=0)){
			sqlHaving += " and sum(case when ir.revise_status is null then 0 else ir.revise_status end)<=" + this.revisedNum;
		}
		if(this.reviseRecNum!=null&&reviseRecNum>=0){
			sqlHaving += " and sum(case when ir.revise_record_time is null then 0 else 1 end)>=" + this.reviseRecNum;
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.code)){
			sqlWhere += " and item.code='" + this.code + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.content)){
			sqlWhere += " and item.content like '%" + this.content + "%' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.code)){
			sqlWhere += " and item.code='" + this.code + "' ";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.importFile)){
			sqlWhere += " and item.import_file='" + this.importFile + "' ";
		}

		sql = sql + sqlLeft + sqlWhere + sqlGroup + sqlHaving + ") cs ";
		
		return sql;
	}

	/********************************************
	 * @USE: 根据查询条件，得到审核状态语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.15.30
	 * 
	 */
	public String verifystatusHQL(String reviser,String correctnum){
		String hql = "select revise_status,count(revise_status) count  from item_revise  where item_id in ( ";
		hql+=statisticsHQL(reviser,correctnum,false) ;
		hql+=") and reviser= "+reviser;
		if(Integer.parseInt(correctnum)>0){
			hql +=" and item_id in (select item_id from item_revise ir where ir.revise_record_time is not null group by ir.item_id having count(ir.reviser)>="+Integer.parseInt(correctnum)+")";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and revise_time>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql += " and revise_time<='" + this.reviseTimeEnd.trim() + "' ";
		
		hql+="  group by revise_status ";
		return hql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到审计人相关的得分语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.26.10.30
	 * 
	 */
	public String getscore1HQL(String reviser,String correctnum){
		String hql = "select ifnull(sum(ira.score) ,0) from item_revise_answers ira	left outer join (select id,reviser from item_revise where item_id in ( ";
		hql+=statisticsHQL(reviser,correctnum,false) ;
		hql+=" )) ir on ir.id=ira.item_revise_id where ira.sub_item_id is null and ir.reviser= "+reviser;
		return hql;
	}
	
	/********************************************
	 * @USE: 根据查询条件，得到审计人相关的总分语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.26.10.30
	 * 
	 */
	public String getscore2HQL(String reviser,String correctnum){
		String hql = "select ifnull(sum(item.score),0) from item_revise ir_f left outer join item on item.id=ir_f.item_id left outer join grade_item_ref gir on ir_f.item_id=gir.item_id, " ;
		hql+=" (select ira.item_id item_id from item_revise_answers ira left outer join item_revise ir on ir.id=ira.item_revise_id left outer join knowledge_point_item_ref kpi on ira.item_id=kpi.item_id " ;
		hql+=" where ira.sub_item_id is null ";
		if(UtilAndTool_L.checkNotNullOrZero(this.knowledgePoint))
			hql+=" and kpi.knowledge_point_code='"+this.knowledgePoint.trim()+"' ";
		if(Integer.parseInt(correctnum)>0){
			hql+=" and ir.revise_record_time is not null ";
		}
		hql+=" group by ira.item_id ";
		if(Integer.parseInt(correctnum)>0){
			hql+=" having count(ir.reviser)>= "+correctnum;
		}
		hql+=" ) item_scores where  ir_f.item_id=item_scores.item_id and item.subject_code='"+this.subject_code.trim()+"' and item.status>0 and ir_f.reviser= " +reviser;
		if(UtilAndTool_L.checkNotNullOrZero(this.grade))
			hql+=" and gir.grade_code='"+this.grade+"'" ;
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and ir_f.revise_time>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql+=" and ir_f.revise_time<='"+ this.reviseTimeEnd.trim() + "'" ;
		return hql;
	}
	
	/********************************************
	 * @USE: 根据状态查询条件，得到语句
	 * @PARAM: ...
	 * @RETURN: 组装好的HQL语句
	 * @FOR: 试题审校统计页面查询，得到各查询条件，此方法把各查询条件进行整理，生成HQL语句，
	 * 		以便后台程序查询
	 * 
	 * @AUTHOR: 邓新宇
	 * @DATE: 2010.05.24.17.30
	 * 
	 */
	public String getHQLByCondition(String reviser,String correctnum,String condition,Integer status){
		String hql = "select id from item where id in (select item_id  from item_revise  where item_id in ( ";
		hql+=statisticsHQL(reviser,correctnum,false) ;
		hql+=" )  and ";
		if(condition.equals("status"))
			hql+=" revise_status="+status+" ";
		else if(condition.equals("reply"))
			hql+=" reply_type="+status+" ";
		hql+=" and reviser= "+reviser;
		if(Integer.parseInt(correctnum)>0){
			hql +=" and item_id in (select item_id from item_revise ir where ir.revise_record_time is not null group by ir.item_id having count(ir.reviser)>="+Integer.parseInt(correctnum)+")";
		}
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeBegin))
			hql += " and revise_time>='" + this.reviseTimeBegin.trim() + "' ";
		if(UtilAndTool_L.checkNotNullOrZero(this.reviseTimeEnd))
			hql += " and revise_time<='" + this.reviseTimeEnd.trim() + "' ";
		hql+=")";
		return hql;
	}
	

	public SysUser getSu() {
		return su;
	}

	public void setSu(SysUser su) {
		this.su = su;
	}

	public Integer getLoginId() {
		return loginId;
	}

	public void setLoginId(Integer loginId) {
		this.loginId = loginId;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKnowledgePoint() {
		return knowledgePoint;
	}
	public void setKnowledgePoint(String knowledgePoint) {
		this.knowledgePoint = knowledgePoint;
	}
	public String getSubject_code() {
		return subject_code;
	}
	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}
	public String getReviewRound() {
		return reviewRound;
	}
	public void setReviewRound(String reviewRound) {
		this.reviewRound = reviewRound;
	}
	public String getReviseStatus() {
		return reviseStatus;
	}

	public void setReviseStatus(String reviseStatus) {
		this.reviseStatus = reviseStatus;
	}

	public String getReviseTimeBegin() {
		return reviseTimeBegin;
	}
	public void setReviseTimeBegin(String reviseTimeBegin) {
		this.reviseTimeBegin = reviseTimeBegin;
	}
	public String getReviseTimeEnd() {
		return reviseTimeEnd;
	}
	public void setReviseTimeEnd(String reviseTimeEnd) {
		this.reviseTimeEnd = reviseTimeEnd;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImportFile() {
		return importFile;
	}
	public void setImportFile(String sourceFile) {
		this.importFile = sourceFile;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getReplyType() {
		return replyType;
	}

	public void setReplyType(String replyType) {
		this.replyType = replyType;
	}

	public String getReviseRecordTimeBegin() {
		return reviseRecordTimeBegin;
	}

	public void setReviseRecordTimeBegin(String reviseRecordTimeBegin) {
		this.reviseRecordTimeBegin = reviseRecordTimeBegin;
	}

	public String getReviseRecordTimeEnd() {
		return reviseRecordTimeEnd;
	}

	public void setReviseRecordTimeEnd(String reviseRecordTimeEnd) {
		this.reviseRecordTimeEnd = reviseRecordTimeEnd;
	}


	public BigDecimal getReviseNum() {
		return reviseNum;
	}

	public void setReviseNum(BigDecimal reviseNum) {
		this.reviseNum = reviseNum;
	}

	public BigDecimal getRevisedNum() {
		return revisedNum;
	}

	public void setRevisedNum(BigDecimal revisedNum) {
		this.revisedNum = revisedNum;
	}

	public Integer getReviseRecNum() {
		return reviseRecNum;
	}

	public void setReviseRecNum(Integer reviseRecNum) {
		this.reviseRecNum = reviseRecNum;
	}

	public BigDecimal getMaxRevised() {
		return maxRevised;
	}

	public void setMaxRevised(BigDecimal maxRevised) {
		this.maxRevised = maxRevised;
	}

	public BigDecimal getMaxRevise() {
		return maxRevise;
	}

	public void setMaxRevise(BigDecimal maxRevise) {
		this.maxRevise = maxRevise;
	}

	public Integer getReviser() {
		return reviser;
	}

	public void setReviser(Integer reviser) {
		this.reviser = reviser;
	}
}
