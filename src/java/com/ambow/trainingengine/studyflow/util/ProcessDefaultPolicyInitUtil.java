package com.ambow.trainingengine.studyflow.util;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.EvaluateNode;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.PhaseTestNode;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.studyflow.domain.UnitTestNode;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
/***
 * 设置流程默认策略
 * @author zhujianmin
 *
 */
public class ProcessDefaultPolicyInitUtil {
	
	/**
	 * 设置流程默认策略
	 * @param processId
	 */
	public static String setDefaultPolicyToProcess(long processId,HibernateGenericDao dao,StringBuilder sb ){
		ProcessDefinition pd = dao.get(ProcessDefinition.class, processId);
		List<Node> allNodes = dao.find("from Node where processDefinition.id=?",processId); 
		for (Node node : allNodes) {
			 initNodePolicy(node,pd,dao,sb);
			 //System.out.println("id:"+node.getId());
		}
		return sb.toString();
	}
	/**
	 * 是否是要设置的节点类型 
	 * @param node
	 * @return
	 */
	public static boolean allowSetPoliy(Node node ){
		if(node instanceof PracticeNode||node instanceof PhaseTestNode||node instanceof UnitTestNode||node instanceof EvaluateNode||node instanceof Node){
			return true;
		}
		return false;
	}
	/**
	 * 根据节点类型设置 训练策略，组卷策略，流转策略
	 * @param node
	 * @param pd
	 */
	private static void initNodePolicy(Node node,ProcessDefinition pd,HibernateGenericDao dao,StringBuilder sb){
		 
		if(node!=null&&allowSetPoliy(node)){
			sb.append( "\n"+node.getClass().getSimpleName()+"  id:"+ node.getId()+"name : "+node.getName() +"\n " );
			long nid = node.getId();
			// 训练策略
			TrainingPolicy trainingPolicy = dao.get(TrainingPolicy.class,nid);
			if(trainingPolicy==null){
				trainingPolicy = new TrainingPolicy();
				trainingPolicy.setAsfNode(node);
				//全卷预览时间
				trainingPolicy.setOverviewTime(0);
				//允许设置疑问标记
				trainingPolicy.setAllowUnsureMark(1);//1"允许" 0"不允许"
				//何时允许查看解析
				trainingPolicy.setWhenToSeeAnalysis(2);//1随时 2做题后 3正确后
				//何时允许查看答案
				trainingPolicy.setWhenToCheckAnswer(2);//1随时 2做题后 3正确后
				//是否随机出题
				trainingPolicy.setIsRandom(0);
				//是否颠倒答案顺序
				trainingPolicy.setIsRandomAnswerOptions(0);
				//通过正确率
				trainingPolicy.setRightRateForPass(60f);
				//重做通过正确率
				trainingPolicy.setRightRateRetraining(60f);
				//重做出题类型
				trainingPolicy.setRetrainingItemType(13f);
				//重做出题顺序
				trainingPolicy.setRetrainingItemOrder(null);
				//测试未通过重做通过正确率
				trainingPolicy.setRetrainingRightRateTestFaile(60f);
				//测试未通过重做出题类型
				trainingPolicy.setRetrainingItemTypeTestFaile(4f);
				//测试未通过重做出题顺序
				trainingPolicy.setRetrainingItemOrderTestFaile(null);
				//测试未通过是否动态出题
				trainingPolicy.setRandomAssemItemsTestFaile(1);
				//测试未通过动态出题类型
				trainingPolicy.setAssemItemsTypeTestFaile(-2f);
				//测试未通过动态出题比率
				trainingPolicy.setAssemItemsRateTestFaile(20f);
				if(node instanceof UnitTestNode){
					//后测
					trainingPolicy.setRightRateForPass(80f);
					trainingPolicy.setRightRateRetraining(80f);
				}
				sb.append("\n\t[训练策略]...");
				dao.save(trainingPolicy);
				sb.append("[设置成功！]"); 
			}
			
			//组卷策略
			PaperAssemblingPolicy paperAssemblingPolicy = dao.get(PaperAssemblingPolicy.class,nid);
			if(paperAssemblingPolicy==null){
				paperAssemblingPolicy=new PaperAssemblingPolicy();
				paperAssemblingPolicy.setAsfNode(node);
				paperAssemblingPolicy.setPaperAssemblingMode(1);//动态组卷
				if(node instanceof PracticeNode){				
					//动态出题（过滤本级）
					paperAssemblingPolicy.setPaperAssemblingMode(21);
				}
				sb.append("\n\t[组卷策略]...");
				dao.save(paperAssemblingPolicy);
				sb.append("[设置成功！]"); 
			}
		
			//流转策略
			sb.append("\n\t[流转策略]...");
			if(node instanceof PracticeNode){
				
				//训练
				TrainingUnitNodePolicy trainingUnitNodePolicy =dao.get(TrainingUnitNodePolicy.class,nid);
				if(trainingUnitNodePolicy==null){
					trainingUnitNodePolicy= new TrainingUnitNodePolicy();				 
					trainingUnitNodePolicy.setPass(0);/*	1 返回 0 向前*/
					trainingUnitNodePolicy.setFailed(0);/*	1 返回 0 向前*/
					trainingUnitNodePolicy.setAsfNode(node);
					
					dao.save(trainingUnitNodePolicy);
					sb.append("[设置成功！]"); 
				}				
			}else if(node instanceof PhaseTestNode){
				//前测
				PhaseTestNodePolicy phaseTestNodePolicy=(PhaseTestNodePolicy)dao.findObjByHql("from PhaseTestNodePolicy where asfNode.id=?",nid);
				if(phaseTestNodePolicy==null){
					phaseTestNodePolicy=new PhaseTestNodePolicy();
					phaseTestNodePolicy.setAsfNode(node);
					phaseTestNodePolicy.setStartValue(0f);
					phaseTestNodePolicy.setEndValue(90f);
					String nextId = null;
					try{
						//跳转位置 由于是
						nextId = String.valueOf(node.getNextNode().getId());
						phaseTestNodePolicy.setJumpPosition(Integer.valueOf(nextId));
					}catch(Exception e){/*如果异常则说明没有下一节点了或超出了整形的范围*/}
					phaseTestNodePolicy.setStartValue("0");
					phaseTestNodePolicy.setEndValue("90");
					
					PhaseTestNodePolicy phaseTestNodePolicy_second=new PhaseTestNodePolicy();
					phaseTestNodePolicy_second.setAsfNode(node);
					phaseTestNodePolicy_second.setStartValue(90f);
					phaseTestNodePolicy_second.setEndValue(100f);
					phaseTestNodePolicy_second.setStartValue("90");
					phaseTestNodePolicy_second.setEndValue("100");
					dao.save(phaseTestNodePolicy);
					dao.save(phaseTestNodePolicy_second);
					sb.append("[设置成功！]"); 
				}
				 
			}else if(node instanceof UnitTestNode){
				//后测
				UnitTestNodePolicy unitTestNodePolicy = dao.get( UnitTestNodePolicy.class,nid);
				if(unitTestNodePolicy==null){
					unitTestNodePolicy = new UnitTestNodePolicy();
					unitTestNodePolicy.setAsfNode(node);
					unitTestNodePolicy.setRetrainingScope(0);//0本级 1上级 2上上级
					dao.save(unitTestNodePolicy);
					sb.append("[设置成功！]"); 
				}				
			}else if(node instanceof EvaluateNode){
				//评测
				sb.append("[设置成功！]"); 
			}
		}
	}
}
