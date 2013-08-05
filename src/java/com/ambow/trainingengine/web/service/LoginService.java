package com.ambow.trainingengine.web.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.domain.MembershipPoint;
import com.ambow.trainingengine.exam.domain.ProcessTrainingStatus;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.systemsecurity.domain.Webuser;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.studyflow.common.NodeStatus;

public class LoginService extends BaseWebService{
	
	
	/**
	 * @功能 检验用户流程信息，添加及更新
	 * @param userID
	 * @param loginName
	 * @param userName
	 * @param classCode
	 * @param moduleID
	 * @param refID
	 * @param gradeID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loginAndJumpTo(
		String userID,
		String loginName,
		String userName,
		String classCode,
		String moduleID,
		String refID,
		String gradeID){
		
		/*验证参数是否为空*/
		if(userID==null||loginName==null||userName==null
				||classCode==null||moduleID==null
				||refID==null||gradeID==null){
			//都等于空的时候跳转到的错误页面流程			
			if(userID==null)
				System.out.println("Error: userID is  null!");	
			if(loginName==null)
				System.out.println("Error: loginName is  null!");
			if(userName==null)
				System.out.println("Error: userName is  null!");
			if(classCode==null)
				System.out.println("Error: classCode is  null!");
			if(moduleID==null)
				System.out.println("Error: moduleID is  null!");
			if(refID==null)
				System.out.println("Error: refID is  null!");
			if(gradeID==null)
				System.out.println("Error: gradeID is  null!");	
			
			return null;
		}
				
		/*判断是否含有此用户信息是否存在*/	
		int i=getJdbcTemplate().queryForInt("select count(id) from webuser where id=\""+userID+"\"");

		if (i==0) {
			//添加用户记录
			/*this.getJdbcTemplate().update("insert into webuser(id,login_name,real_name,first_login_time,login_times) "
					+"values(?,?,?,?,?) ", new Object[]{userID,loginName,userName,new Date(),0});
			*/			
			Webuser webuser=new Webuser();
			webuser.setId(userID);
			webuser.setLoginName(loginName);
			webuser.setRealName(userName);
			webuser.setFirstLoginTime(new Date());
			webuser.setStatus(0);
			this.save(webuser);
		} else {
			Webuser webuser = this.getWebUser(userID);
			webuser.setLoginName(loginName);
			webuser.setRealName(userName);
			this.save(webuser);
		}
		
		/*修改用户系统登录信息*/
		//...
		/*this.getJdbcTemplate().update("update webuser set login_times="
				+((Integer)((Map)list.get(0)).get("login_times")+1)
				+",last_login_time="+new Date()+"where id="+userID);*/
		 
		
		/*判断是否含有此用户的此流程实例信息是否存在*/
		/*int j=getJdbcTemplate().queryForInt(
				"select count(id) from asf_process_instance"
				+" where process_status=0 and actor=? and process_definition_id=?",new Object[]{userID,refID});
				*/
		List list2=this.getJdbcTemplate().queryForList(
				"select b.last_training_time,b.training_times from asf_process_instance a,process_training_status b where b.process_instance_id=a.id "
				+" and a.actor=? and a.process_definition_id=?",new Object[]{userID,refID});
		
		String lastTrainingTime=null;
		String lastLoginDate=null;
		String trainingTimes=null;
		ProcessTrainingStatus processTrainingStatus=null;
		MembershipPoint membershipPoint=null;
		ProcessDefinition processDefinition=this.get(ProcessDefinition.class, Long.valueOf(refID));
		if(list2==null||list2.size()==0){
		//if(j==0){
			//添加用户流程实例记录,起始节点从
			/*this.getJdbcTemplate().update("insert into asf_process_instance(actor,process_definition_id,start_time,node_id,process_status) "
					+"values(?,?,?,(select start_node_id from asf_process_definition where id=? ),?) ",
					new Object[]{userID,refID,new Date(),refID,0});
			*/
			ProcessInstance asfProcessInstance=new ProcessInstance();
			asfProcessInstance.setActor(userID);
			asfProcessInstance.setStartTime(new Date());
			
			if(processDefinition!=null){
				asfProcessInstance.setNode(processDefinition.getStartNode());
				asfProcessInstance.setProcessDefinition(processDefinition);	
			}
			asfProcessInstance.setProcessStatus(ProcessStatus.RUNNING);
			//this.save(asfProcessInstance);
			
			processTrainingStatus=new ProcessTrainingStatus();
			//processTrainingStatus.setProcessInstanceId(asfProcessInstance.getId());
			processTrainingStatus.setGrade(gradeID);
			processTrainingStatus.setClassNum(classCode);
			processTrainingStatus.setModule(moduleID);
			processTrainingStatus.setFirstTrainingTime(new Date());
			processTrainingStatus.setTrainingTimes(0);
			processTrainingStatus.setTotalMasteryRate(0f);
			processTrainingStatus.setTotalAccuracyRate(0f);
			processTrainingStatus.setLearningProcessRate(0f);
			processTrainingStatus.setTotalScore(0f);
			
			//训练卷子题总题数
			ProcessPolicy processPolicy=this.get(ProcessPolicy.class,new Long(refID));
			if(processPolicy.getProjectVersion().equals("mpc"))
				processTrainingStatus.setTotalItemsNum(this.getJdbcTemplate().queryForInt(
						"select sum(b.big_items_num) from asf_node a,paper_assembling_policy b where b.node_id=a.id "
						+" and a.process_definition_id=?",new Object[]{refID}));
			else 
				processTrainingStatus.setTotalItemsNum(this.getJdbcTemplate().queryForInt(
						"select sum(b.items_num) from asf_node a,paper_assembling_policy b where b.node_id=a.id "
						+" and a.process_definition_id=? and a.node_type='PRACTICE'",new Object[]{refID}));
			
			
			processTrainingStatus.setTrainingTimes(0);
			processTrainingStatus.setSumCorrectItems(0);
			processTrainingStatus.setSumIncorrectItems(0);	
			processTrainingStatus.setSumUnfinishedItems(0);	
			processTrainingStatus.setUnsureMarkItems(0);
			processTrainingStatus.setSumZeroStarItems(0);
			processTrainingStatus.setSumHalfStarItems(0);
			processTrainingStatus.setSumOneStarItems(0);
			processTrainingStatus.setSumTwoStarItems(0);
			processTrainingStatus.setSumThreeStarItems(0);
			processTrainingStatus.setSumFourStarItems(0);
			processTrainingStatus.setSumFiveStarItems(0);
			processTrainingStatus.setAsfProcessInstance(asfProcessInstance);
			
			processTrainingStatus.setLayout1("5");
			processTrainingStatus.setLayout2("5");
			processTrainingStatus.setLayout3("1");
			processTrainingStatus.setFont("M");//字号 S小号，M中号，L大号
			
			processTrainingStatus.setFormulatorTest(false);
			
			
			membershipPoint=new MembershipPoint();
			membershipPoint.setCourage(0);
			membershipPoint.setDedicate(0);
			membershipPoint.setDiligence(0);
			membershipPoint.setPercipience(0);
			membershipPoint.setWisdom(0);
			membershipPoint.setAsfProcessInstance(asfProcessInstance);
			
			//查询出刚插入的流程实例id并插入到流程实例状态表
			/*this.getJdbcTemplate().update(
					"insert into process_training_status(process_instance_id,grade,class,module,first_training_time,training_times,"
					+"total_mastery_rate,total_accuracy_rate,learning_process_rate,total_score,total_items_num,total_training_time,"
					+"sum_correct_items,sum_incorrect_items,sum_unfinished_items,sum_zero_star_items,sum_half_star_items,"
					+"sum_one_star_items,sum_two_star_items,sum_three_star_items,sum_four_star_items,sum_five_star_items)"
					+"values((select id from asf_process_instance where actor=? and process_definition_id=?),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
					new Object[]{
							userID,
							refID,
							gradeID,
							classCode,
							moduleID,
							new Date(),
							1,//training_times
							0,//total_mastery_rate
							0,//total_accuracy_rate
							0,//learning_process_rate
							0,//total_score
							0,//total_items_num  *总题数
							0,//total_training_time
							0,//sum_correct_items
							0,//sum_incorrect_items
							0,//sum_unfinished_items
							
							0,//sum_zero_star_items
							0,//sum_half_star_items
							0,//sum_one_star_items
							0,//sum_two_star_items
							0,//sum_three_star_items
							0,//sum_four_star_items
							0,//sum_five_star_items
							});
			*/	
			
			//this.save(processTrainingStatus);
			
			trainingTimes="1";
		}else{
			
			Map map=(Map)list2.get(0);
			if(map.get("training_times")!=null)
				trainingTimes=map.get("training_times").toString();
			if(map.get("last_training_time")!=null){
				lastTrainingTime=new SimpleDateFormat("yyyy年MM月dd日HH时mm分").format((Date)map.get("last_training_time"));
				lastLoginDate=new SimpleDateFormat("yyyyMMdd").format((Date)map.get("last_training_time"));
			}
			//通过状态字段检验用户流程实例是否还有效
			//...
			
		}
		
		/*修改用户流程实例登录信息*/		
		/*this.getJdbcTemplate().update("update process_training_status a set a.login_times=?,a.last_training_time=? where "
				+"a.process_instance_id=(select b.id from asf_process_instance b where b.actor=? and b.process_definition_id=?)",
				new Object[]{Integer.parseInt(trainingTimes)+1,new Date(),userID,refID});
		*/
		if(processTrainingStatus==null){
			String hql="select a from ProcessTrainingStatus a,ProcessInstance b where a.processInstanceId=b.id "
				+" and b.actor=? and b.processDefinition.id=?";
			List list=this.getHibernateTemplate().find(hql,new Object[]{userID,new Long(refID)});
			if(list!=null&&list.size()>0){
				processTrainingStatus=(ProcessTrainingStatus)list.get(0);
								
			}
		}
		
		//解决换班问题
		processTrainingStatus.setClassNum(classCode);
		
		processTrainingStatus.setLastTrainingTime(new Date());
		processTrainingStatus.setTrainingTimes(Integer.parseInt(trainingTimes)+1);		
		this.save(processTrainingStatus);
		
		if(membershipPoint!=null)
			this.save(membershipPoint);
		
		/* 启动流程策略*/
		ProcessPolicy processPolicy=this.get(ProcessPolicy.class,processDefinition.getId());
		if(processPolicy!=null&&lastLoginDate!=null){
			String currentDate=new SimpleDateFormat("yyyyMMdd").format(new Date());
			int tag;
			if(lastLoginDate.equals(currentDate))
				tag=processPolicy.getReloginSameDayPolicy();
			else
				tag=processPolicy.getReloginAnotherDayPolicy();
			//tag,0-原有流程;1-第一个未通过节点
			if(tag==1)
				this.updateNextNode(refID, userID);
		}
		
		
		return "success,"+trainingTimes+","+lastTrainingTime;
	}
	
	
	/**
	 * 检验流程节点实例，添加流程节点实例
	 * @param userID
	 * @param refID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkAndGetFlowitem(String userID,String refID){
		//判断是否含有此用户的节点实例列表	
		int i=this.getJdbcTemplate().queryForInt(
				"select count(b.id) from asf_process_instance a,asf_node_instance b"
				+" where a.id=b.process_instance_id and a.process_definition_id="
				+refID+" and a.actor='"+userID+"'");
		
		if(i>0)
			return "success";
		
			/*添加节点实例,在此可添加可选节点功能*/			
			//查询对应流程定义的节点定义列表
		/*List list=this.getJdbcTemplate().queryForList(
					"select b.id from asf_process_definition a,asf_node b "
					+"where b.process_definition_id=a.id and a.id=? order by b.order_num",new Object[]{refID});
						
			//查询流程实例id
			List list2=this.getJdbcTemplate().queryForList(
					"select id from asf_process_instance "
					+"where actor=? and process_definition_id=?",new Object[]{userID,refID});
			
			List objectList=new ArrayList();
			for(int j=0;j<list.size();j++){
				Object[] o=new Object[5];
				o[0]=((Map)list2.get(0)).get("id");//流程实例id				
				o[1]=((Map)list.get(j)).get("id");				
				o[2]=new Date();
				o[3]=new Byte("1");//必选节点类型
				o[4]=NodeStatus.INITIAL.toInt();//节点状态
				
				objectList.add(o);
			}*/
			//添加节点实例
			/*this.getJdbcTemplate().batchUpdate(
					"insert into asf_node_instance(process_instance_id,node_id,"
					+"start_time,is_necessary,node_status) values(?,?,?,?,?)", objectList);
			*/
		
		String hql2="from ProcessInstance a where a.actor=? and a.processDefinition.id=?";
		List<ProcessInstance> list2=this.getHibernateTemplate().find(hql2,new Object[]{userID,new Long(refID)});
		
		
		String hql="from Node where processDefinition.id=? order by orderNum";
		List<Node> list=this.getHibernateTemplate().find(hql,new Long(refID));
		if(list==null||list.size()==0)
			return "failure";
		List<NodeInstance> nodeInstanceList=new ArrayList<NodeInstance>();		
		for(int j=0;j<list.size();j++){
			Node node=list.get(j);
			NodeInstance nodeInstance=new NodeInstance();
			nodeInstance.setStartTime(new Date());
			nodeInstance.setNecessary(true);
			nodeInstance.setNode(node);
			if(list2!=null&&list2.size()>0)
				nodeInstance.setProcessInstance(list2.get(0));
			nodeInstanceList.add(nodeInstance);
		}
		
		this.saveOrUpdateAll(nodeInstanceList);
		
		return "success";
	}
	
//	/**
//	 * 获取用户的webuser的po
//	 */
//	@SuppressWarnings("unchecked")
//	public Webuser getWebUser(String loginName){
//		Webuser webuser=null;
//		String hql="from Webuser where loginName=?";
//		List<Webuser> list=this.getHibernateTemplate().find(hql,loginName);
//		if(null!=list&&0<list.size())
//			webuser=list.get(0);
//		return webuser;
//	}
	
	/**
	 * 获取用户的webuser的po
	 */
	@SuppressWarnings("unchecked")
	public Webuser getWebUser(String userId){
		Webuser webuser=null;
		String hql="from Webuser where id=?";
		List<Webuser> list=this.getHibernateTemplate().find(hql,userId);
		if(null!=list&&0<list.size())
			webuser=list.get(0);
		return webuser;
	}
	/**
	 * 获取流程第一个未通过节点，并更新为当前节点
	 */
	@SuppressWarnings("unchecked")
	public void updateNextNode(String refID,String userID){
		String hql="from NodeInstance where processInstance.actor=? "
			+" and processInstance.processDefinition.id=? order by node.orderNum";
		List<NodeInstance> list=this.find(hql,userID,Long.valueOf(refID));		
		if(list==null)
			return;
		for(int i=0;i<list.size();i++){
			NodeInstance nodeInstance=list.get(i);
			if(nodeInstance.getNode().getNodeType()==NodeType.GROUP)
				continue;
			if(nodeInstance.getNodeStatus().toInt()<NodeStatus.PASSED.toInt()){
				ProcessInstance processInstance=nodeInstance.getProcessInstance();
				processInstance.setNode(nodeInstance.getNode());
				this.save(processInstance);
				return;
			}
		}
	}
}
