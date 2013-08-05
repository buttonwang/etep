package com.ambow.trainingengine.studyflow.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeGroup;
import com.ambow.studyflow.domain.PracticeNode;
import com.ambow.studyflow.domain.ProcessDefinition;
import com.ambow.trainingengine.policy.domain.AssemblePaperPolicyTemplate;
import com.ambow.trainingengine.policy.domain.AssemblingPaperReqTemplate;
import com.ambow.trainingengine.policy.domain.NodeGroupPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingReqTemplate;
import com.ambow.trainingengine.policy.domain.PhaseTestPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingUnitPolicyTemplate;
import com.ambow.trainingengine.policy.domain.UnitTestPolicyTemplate;

public class Init {
	String[] allNode={"模块测试","学前评测","基础强化","基础强化I","强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三","基础强化II","强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分","语法集训","语法集训1","考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分","学后评测"};
	String[] evaluateNode_s={"模块测试","学前评测","学后评测"};
	String[] practiceNode_s={"单元测试1","单元测试2","单元测试_基础强化部分","单元测试_语法集训部分"};
	String[] unitTestNode_s={"单元测试1","单元测试2","单元测试_基础强化部分","单元测试_语法集训部分"};
	String[] phaseTestNode_s={"阶段测试一","阶段测试二","阶段测试三","阶段测试四","阶段测试五","考研语法专项检测"};
	String[] nodeGroup_s={"基础强化","基础强化I","基础强化II","语法集训","语法集训1"};
	
	public static void main(String[] args) {
		System.out.println("start======================================="); 
		 Init p = new Init();
		 p.setUp();
		 
		 p.initNodeGroupId(1);
		 
		 /*
		 // System.out.println("=======================================processId : "+p.initPracticeNode());;
		 //=============执行前注意先执行一下sql语句
		 long processId = 1;
		// p.initNodeGroupId(processId);
		 //p.initNodeGroupPolicyAssembling_policy(processId,0);
		 
		p.phaseTestPolicyTemplate_add();
		p.trainingUnitPolicyTemplate_add();
		p.unitTestPolicyTemplate_add();
		p.trainingPolicyTemplate_add();
	
		p.paperAssemblingReqTemplate_add();	
		
		
		System.out.println("end======================================="); 
		p.assemblePaperPolicyTemplate_add();
		p.assemblingPaperReqTemplate_add();
		p.initAllNodeTrainPolicy();*/
	}
	
	/**
	 * 组卷策略 模板
	 */
	public void assemblePaperPolicyTemplate_add(){
		String []names = {"组卷策略 模板4","组卷策略 模板3","组卷策略 模板2","组卷策略 模板1"};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int paperAssemblingMode = 0;//'0-手工组卷;1-动态组卷;2-动态出题'
			//////////////////////
			AssemblePaperPolicyTemplate assemblePaperPolicyTemplate =(AssemblePaperPolicyTemplate)pAServ.findObjByHql("from AssemblePaperPolicyTemplate where name=?", name); 
			if(assemblePaperPolicyTemplate==null){
				assemblePaperPolicyTemplate = new AssemblePaperPolicyTemplate();
			}
			assemblePaperPolicyTemplate.setName(name);
			assemblePaperPolicyTemplate.setPaperAssemblingMode(paperAssemblingMode);
			pAServ.save(assemblePaperPolicyTemplate);
			/////////////////////
		}
	}
	
	/**
	 *组卷条件模板组卷条件 模板
	 */
	public void assemblingPaperReqTemplate_add(){
		String []regionCodes = {"全国I","全国II","北京","上海"};	
		for (int i = 0; i < regionCodes.length; i++) {
			int amount = 0;
			String difficultyValue ="0.6";
			String itemTypeCode = "wx";
			String originalPaperCode = "原始套卷";
			int paperAssemblingReqTemplateId = 1;
			String regionCode = regionCodes[i];
			String source = "Y";
			String subjectCode = "考研英语";
			String validityValue = "0.8";
			String year = "2008";
			
			
			int paperAssemblingMode = 0;//'0-手工组卷;1-动态组卷;2-动态出题'
			//////////////////////
			AssemblingPaperReqTemplate assemblingPaperReqTemplate = (AssemblingPaperReqTemplate)pAServ.findObjByHql("from AssemblingPaperReqTemplate where regionCode=?", regionCode); 
			if(assemblingPaperReqTemplate==null){
				assemblingPaperReqTemplate = new AssemblingPaperReqTemplate();
			}
		 
			assemblingPaperReqTemplate.setAmount(amount);
			assemblingPaperReqTemplate.setDifficultyValue(difficultyValue);
			assemblingPaperReqTemplate.setItemTypeCode(itemTypeCode);
			assemblingPaperReqTemplate.setOriginalPaperCode(originalPaperCode);
			
			assemblingPaperReqTemplate.setPaperAssemblingReqTemplate(pAServ.get(PaperAssemblingReqTemplate.class,paperAssemblingReqTemplateId));
			assemblingPaperReqTemplate.setRegionCode(regionCode);
			assemblingPaperReqTemplate.setSource(source);
			assemblingPaperReqTemplate.setSubjectCode(subjectCode);
			assemblingPaperReqTemplate.setValidityValue(validityValue);
			assemblingPaperReqTemplate.setYear(year);
			pAServ.save(assemblingPaperReqTemplate);
			/////////////////////
		}
		
		String []itemTypeCodes = {"wx","s","ms"};
		for (int i = 0; i < itemTypeCodes.length; i++) {
			int amount = 0;
			String difficultyValue = "0.6";
			String itemTypeCode = itemTypeCodes[i];
			String originalPaperCode = "原始套卷";
			//int paperAssemblingReqTemplate = 0;
			String regionCode = "全国I";
			String source = "Z";
			String subjectCode = "考研英语";
			String validityValue = "0.8";
			String year = "2008";

			int paperAssemblingMode = 0;//'0-手工组卷;1-动态组卷;2-动态出题'
			//////////////////////
			AssemblingPaperReqTemplate assemblingPaperReqTemplate = (AssemblingPaperReqTemplate)pAServ.findObjByHql("from AssemblingPaperReqTemplate where itemTypeCode=?", itemTypeCode); 
			if(assemblingPaperReqTemplate==null){
				assemblingPaperReqTemplate = new AssemblingPaperReqTemplate();
			}
		 
			assemblingPaperReqTemplate.setAmount(amount);
			assemblingPaperReqTemplate.setDifficultyValue(difficultyValue);
			assemblingPaperReqTemplate.setItemTypeCode(itemTypeCode);
			assemblingPaperReqTemplate.setOriginalPaperCode(originalPaperCode);
			
			//TODO 
			assemblingPaperReqTemplate.setPaperAssemblingReqTemplate(pAServ.get(PaperAssemblingReqTemplate.class,2));
			assemblingPaperReqTemplate.setRegionCode(regionCode);
			assemblingPaperReqTemplate.setSource(source);
			assemblingPaperReqTemplate.setSubjectCode(subjectCode);
			assemblingPaperReqTemplate.setValidityValue(validityValue);
			assemblingPaperReqTemplate.setYear(year);
			pAServ.save(assemblingPaperReqTemplate);
			/////////////////////
		}
	}
	
	
	/**
	 *组卷条件 模板
	 */
	public void paperAssemblingReqTemplate_add(){		
		String []names = {"组卷策略 模板4","组卷策略 模板3","组卷策略 模板2","组卷策略 模板1"};	
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int paperAssemblingMode = 0;//'0-手工组卷;1-动态组卷;2-动态出题'
			//////////////////////
			PaperAssemblingReqTemplate paperAssemblingReqTemplate =(PaperAssemblingReqTemplate)pAServ.findObjByHql("from PaperAssemblingReqTemplate where name=?", name); 
			if(paperAssemblingReqTemplate==null){
				paperAssemblingReqTemplate = new PaperAssemblingReqTemplate();
			}
			paperAssemblingReqTemplate.setName(name);
		 
			/*
			List assemblingPaperReqTemplateLst = pAServ.getAll(AssemblingPaperReqTemplate.class);
			Set assemblingPaperReqTemplates=new HashSet();
			for (Iterator iterator = assemblingPaperReqTemplateLst.iterator(); iterator.hasNext();) {
				AssemblingPaperReqTemplate assemblingPaperReqTemplate = (AssemblingPaperReqTemplate) iterator.next();
				assemblingPaperReqTemplates.add(assemblingPaperReqTemplate);
				
			}
			paperAssemblingReqTemplate.setAssemblingPaperReqTemplates(assemblingPaperReqTemplates);
			*/
			pAServ.save(paperAssemblingReqTemplate);
			/////////////////////
		}
	}
	
	

	/**
	 * 训练单元类节点流转策略 模板
	 */
	public void trainingUnitPolicyTemplate_add(){
		String []names = {"训练单元类节点流转策略 模板4","训练单元类节点流转策略 模板3","训练单元类节点流转策略 模板2","训练单元类节点流转策略 模板1"};	
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int pass = 0;//'0-往前;1-返回'
			int failed = 0;//'0-往前;1-返回'			
			//////////////////////
			TrainingUnitPolicyTemplate trainingUnitPolicyTemplate = (TrainingUnitPolicyTemplate)pAServ.findObjByHql("from TrainingUnitPolicyTemplate where name=?", name); 
			if(trainingUnitPolicyTemplate==null){
				trainingUnitPolicyTemplate = new TrainingUnitPolicyTemplate();
			}
			trainingUnitPolicyTemplate.setName(name);
			trainingUnitPolicyTemplate.setPass(pass);
			trainingUnitPolicyTemplate.setFailed(failed);
			pAServ.save(trainingUnitPolicyTemplate);
			/////////////////////
		}
	}
	 
	/**
	 * 单元测试类节点流转策略 模板
	 */
	public void unitTestPolicyTemplate_add(){
		String []names = {"单元测试类节点流转策略 模板4","单元测试类节点流转策略 模板3","单元测试类节点流转策略 模板2","单元测试类节点流转策略 模板1"};		
		
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int retrainingScope = 1; //'0-本级;1-上一级;2-上两级'
			
			//////////////////////
			UnitTestPolicyTemplate  unitTestPolicyTemplate= (UnitTestPolicyTemplate)pAServ.findObjByHql("from UnitTestPolicyTemplate where name=?", name); 
			if(unitTestPolicyTemplate==null){
				unitTestPolicyTemplate = new UnitTestPolicyTemplate();
			}
			unitTestPolicyTemplate.setName(name);
			unitTestPolicyTemplate.setRetrainingScope(retrainingScope);
			pAServ.save(unitTestPolicyTemplate);
			/////////////////////
		}
	}
	
	/**
	 * 训练策略 模板
	 */
	
	public void trainingPolicyTemplate_add(){
		String []names = {" 训练策略 模板4"," 训练策略 模板3"," 训练策略 模板2"," 训练策略 模板1"};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			int retrainingScope = 1; //'0-本级;1-上一级;2-上两级'
			int overviewTime = 120;//
			int whenToSeeAnalysis = 1;//'1-随时;2-做题后;3-正确后'
			int whenToCheckAnswer = 1;//'1-随时;2-做题后;3-正确后'
			int allowUnsureMark = 0;//'0-不允许;1-允许'
			int isRandom = 0;//'0-否;1-是'
			int isRandomAnswerOptions = 0;//'0-否;1-是'
			float rightRateForPass = 0.6f;//
			float rightRateRetraining = 0.8f;//
			float retrainingItemType = 13f;//retraining_item_type'0,0.5,1,2,3,4,5-*星级题;11-未答题;12-错题;13-未答题&错题;-1-全部'
			String retrainingItemOrder = "错题-未做-新题";//
			float retrainingRightRateTestFaile = 0.8f;//
			float retrainingItemTypeTestFaile = 12f;//'0,0.5,1,2,3,4,5-*星级题;11-未答题;12-错题;13-未答题&错题;-1-全部'
			String retrainingItemOrderTestFaile = "错题-未做-新题";//
			int randomAssemItemsTestFaile = 0;//'0-不动态出题;1-动态出题'
			float assemItemsTypeTestFaile = -1;//'0,0.5,1,2,3,4,5-*星级题;11-未答题;12-错题;13-未答题&错题;-1-全部'
			float assemItemsRateTestFaile = 0.3f;//
			int  clueRelActFirstFaile= 1;//'1-高亮子题考点相关处;2-高亮试题;3-高亮子题与文章相关处;4-高亮选项'
			String clueContentFirstFaile= "请查看高亮子题考点相关处";//
			String translationContentFirstFaile = "请查看高亮子题考点相关处";//
			int translationRelActFirstFaile = 1;//"1-高亮子题考点相关处;2-高亮试题;3-高亮子题与文章相关处;4-高亮选项"
			String clueContentSecondFaile = "请查看高亮子题与文章相关处部分";//
			int clueRelActSecondFaile = 2;//
			String translationContentSecondFaile = "";//
			int translationRelActSecondFaile = 4;//"1-高亮子题考点相关处;2-高亮试题;3-高亮子题与文章相关处;4-高亮选项"
			String clueContentThirdFaile = "请根据答案做相关思考";//
			int clueRelActThirdFaile = 4;//"1-高亮子题考点相关处;2-高亮试题;3-高亮子题与文章相关处;4-高亮选项"
			String translationContentThirdFaile = "";//
			int translationRelActThirdFaile = 1;//"1-高亮子题考点相关处;2-高亮试题;3-高亮子题与文章相关处;4-高亮选项"			
			
			//////////////////////
			TrainingPolicyTemplate trainingPolicyTemplate =(TrainingPolicyTemplate)pAServ.findObjByHql("from TrainingPolicyTemplate where name=?", name); 
			if(trainingPolicyTemplate==null){
				trainingPolicyTemplate = new TrainingPolicyTemplate();
			}
			trainingPolicyTemplate.setName(name);
			trainingPolicyTemplate.setOverviewTime(overviewTime);
			trainingPolicyTemplate.setWhenToSeeAnalysis(whenToSeeAnalysis);
			trainingPolicyTemplate.setWhenToCheckAnswer(whenToCheckAnswer);
			trainingPolicyTemplate.setAllowUnsureMark(allowUnsureMark);
			trainingPolicyTemplate.setIsRandom(isRandom);
			trainingPolicyTemplate.setIsRandomAnswerOptions(isRandomAnswerOptions);
			trainingPolicyTemplate.setRightRateForPass(rightRateForPass);
			trainingPolicyTemplate.setRightRateRetraining(rightRateRetraining);
			trainingPolicyTemplate.setRetrainingItemType(retrainingItemType);
			trainingPolicyTemplate.setRetrainingItemOrder(retrainingItemOrder);
			trainingPolicyTemplate.setRetrainingRightRateTestFaile(retrainingRightRateTestFaile);
			trainingPolicyTemplate.setRetrainingItemTypeTestFaile(retrainingItemTypeTestFaile);
			trainingPolicyTemplate.setRetrainingItemOrderTestFaile(retrainingItemOrderTestFaile);
			trainingPolicyTemplate.setRandomAssemItemsTestFaile(randomAssemItemsTestFaile);
			trainingPolicyTemplate.setAssemItemsTypeTestFaile(assemItemsTypeTestFaile);
			trainingPolicyTemplate.setAssemItemsRateTestFaile(assemItemsRateTestFaile);
			trainingPolicyTemplate.setClueContentFirstFaile(clueContentFirstFaile);
			trainingPolicyTemplate.setClueRelActFirstFaile(clueRelActFirstFaile);
			trainingPolicyTemplate.setTranslationContentFirstFaile(translationContentFirstFaile);
			trainingPolicyTemplate.setTranslationRelActFirstFaile(translationRelActFirstFaile);
			trainingPolicyTemplate.setClueContentSecondFaile(clueContentSecondFaile);
			trainingPolicyTemplate.setClueRelActSecondFaile(clueRelActSecondFaile);
			trainingPolicyTemplate.setTranslationContentSecondFaile(translationContentSecondFaile);
			trainingPolicyTemplate.setTranslationRelActSecondFaile(translationRelActSecondFaile);
			trainingPolicyTemplate.setClueContentThirdFaile(clueContentThirdFaile);
			trainingPolicyTemplate.setClueRelActThirdFaile(clueRelActThirdFaile);
			trainingPolicyTemplate.setTranslationContentThirdFaile(translationContentThirdFaile);
			trainingPolicyTemplate.setTranslationRelActThirdFaile(translationRelActThirdFaile);
			
			pAServ.save(trainingPolicyTemplate);
			/////////////////////
		}
	}
	
	/**
	 * 阶段测试类节点流转策略 模板
	 */
	public void phaseTestPolicyTemplate_add(){
		PhaseTestPolicyTemplate phaseTestPolicyTemplate = new PhaseTestPolicyTemplate();
		phaseTestPolicyTemplate.setName("阶段测试类节点流转策略模板a");		
	
		PhaseTestPolicyTemplate phaseTestPolicyTemplate2 = new PhaseTestPolicyTemplate();
		phaseTestPolicyTemplate2.setName("阶段测试类节点流转策略模板b");		

		PhaseTestPolicyTemplate phaseTestPolicyTemplate3 = new PhaseTestPolicyTemplate();
		phaseTestPolicyTemplate3.setName("阶段测试类节点流转策略模板c");		

		pAServ.save(phaseTestPolicyTemplate);
		pAServ.save(phaseTestPolicyTemplate2);
		pAServ.save(phaseTestPolicyTemplate3);		
		/*
		String []names = {"阶段测试类节点流转策略模板a","阶段测试类节点流转策略模板b","阶段测试类节点流转策略模板c"};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			float startValue = 0f;
			float endValue = 50f;
			int jumpPosition = 2;
			//////////////////////
			PhaseTestPolicyTemplate phaseTestPolicyTemplate = (PhaseTestPolicyTemplate)pAServ.findObjByHql("from PhaseTestPolicyTemplate where name=?", name);
			if(phaseTestPolicyTemplate == null){
				new PhaseTestPolicyTemplate();
			}
			phaseTestPolicyTemplate.setStartValue(startValue);
			phaseTestPolicyTemplate.setStartValue(endValue);
			phaseTestPolicyTemplate.setJumpPosition(jumpPosition);
			pAServ.save(phaseTestPolicyTemplate);
			/////////////////////
		}*/
	}
	public void initNodeGroupId(long processId){
		/*Date createTime = new Date();
		String creator ="朱建民";
		ProcessDefinition process = new ProcessDefinition();process.setName("09 考研冲刺");process.setDescription("专用于2008年考研冲刺");process.setCreateTime(createTime);process.setCreator(creator); 
		pAServ.save(process);
		String[] nodeGroup_s={"模块测试","学前评测","学后评测"};
		*/
	 	Map nodeGroupWithNodesNameMap = new HashMap();
	 	nodeGroupWithNodesNameMap.put("基础强化",new String[] {"基础强化I","基础强化II"});
	 	nodeGroupWithNodesNameMap.put("基础强化I",new String[] {"强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三"});
	 	nodeGroupWithNodesNameMap.put("基础强化II",new String[] {"强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分"});
	 	nodeGroupWithNodesNameMap.put("语法集训",new String[] {"语法集训1"});
	 	nodeGroupWithNodesNameMap.put("语法集训1",new String[] {"考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分"});
		try{
			for (int i = 0; i < nodeGroup_s.length; i++) {
				String  nodeGroupName= nodeGroup_s[i];
				if(nodeGroupName.equals("断点")){
					System.out.println();
				}
				String hql_nodeGroup = "from NodeGroup where name=?  and processDefinition.id=? ";
				NodeGroup ng =(NodeGroup) pAServ.findObjByHql(hql_nodeGroup, nodeGroupName,processId);
				String [] sons = (String [])nodeGroupWithNodesNameMap.get(nodeGroupName);
				for (int j = 0; j < sons.length; j++) {
					String nodeName = sons[j];
					String hql_node = "from Node where name=? and processDefinition.id=?";
					Node node = (Node) pAServ.findObjByHql(hql_node, nodeName,processId);
					node.setNodeGroup(ng);
					pAServ.save(node);			
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("====================================end save===================================");
	}
	
	public void initNodeGroupPolicyAssembling_policy(long processId,int isDisplayModule){
		for (int i = 0; i < nodeGroup_s.length; i++) {
				String  nodeGroupName= nodeGroup_s[i];
				String hql_nodeGroup = "from NodeGroup where name=?  and processDefinition.id=? ";
				NodeGroup ng =(NodeGroup) pAServ.findObjByHql(hql_nodeGroup, nodeGroupName,processId);
				NodeGroupPolicy ngpa=pAServ.get(NodeGroupPolicy.class,ng.getId());
				if(ngpa==null){
					ngpa = new  NodeGroupPolicy(ng,isDisplayModule);//设置节点，并设置显示模块为是
				}
				ngpa.setIsDisplayModule(isDisplayModule);
				pAServ.save(ngpa);
		}
	}
	
	public void initAllNodeTrainPolicy(){
		Date createTime = new Date();
		String creator ="朱建民";
		ProcessDefinition process = new ProcessDefinition();process.setName("09 考研冲刺");process.setDescription("专用于2008年考研冲刺");process.setCreateTime(createTime);process.setCreator(creator); 
		pAServ.save(process);
		String[] names={"模块测试","学前评测","基础强化","基础强化I","强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三","基础强化II","强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分","语法集训","语法集训1","考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分","学后评测"};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			Node node  = (Node)pAServ.findObjByHql("from Node where name=?", name); 
			if(node!=null){
				TrainingPolicy tp = pAServ.get(TrainingPolicy.class,node.getId());
				if(tp==null){
					tp =getFromTrainingPolicyTemplate(node,pAServ.get(TrainingPolicyTemplate.class, 1)) ;
					if(tp!=null){
						pAServ.save(tp) ;
					}
				}
				PaperAssemblingPolicy paperAssemblingPolicy = pAServ.get(PaperAssemblingPolicy.class,node.getId());
				
				if(paperAssemblingPolicy==null){
					paperAssemblingPolicy =new PaperAssemblingPolicy();
					paperAssemblingPolicy.setAsfNode(node);
				}
			}
		}		
	}
	public static PaperAssemblingPolicy getFrom_AssemblePaperPolicyTemplate(Node asfNode,AssemblePaperPolicyTemplate app){		
		return null;
	}
		
	public static TrainingPolicy getFromTrainingPolicyTemplate(Node asfNode,TrainingPolicyTemplate tpt){
		if(tpt==null){
			return null;
		}
		return new TrainingPolicy(asfNode,
				tpt.getOverviewTime(),
				tpt.getWhenToSeeAnalysis(),
				tpt.getWhenToCheckAnswer(),
				tpt.getAllowUnsureMark(),
				tpt.getIsRandom(),
				tpt.getIsRandomAnswerOptions(),
				tpt.getRightRateForPass(),
				tpt.getRightRateRetraining(),
				tpt.getRetrainingItemType(),
				tpt.getRetrainingItemOrder(),
				tpt.getRetrainingRightRateTestFaile(),
				tpt.getRetrainingItemTypeTestFaile(),
				tpt.getRetrainingItemOrderTestFaile(),
				tpt.getRandomAssemItemsTestFaile(),
				tpt.getAssemItemsTypeTestFaile(),
				tpt.getAssemItemsRateTestFaile(),
				tpt.getClueContentFirstFaile(),
				tpt.getClueRelActFirstFaile(),
				tpt.getTranslationContentFirstFaile(),
				tpt.getTranslationRelActFirstFaile(),
				tpt.getClueContentSecondFaile(),
				tpt.getClueRelActSecondFaile(),
				tpt.getTranslationContentSecondFaile(),
				tpt.getTranslationRelActSecondFaile(),
				tpt.getClueContentThirdFaile(),
				tpt.getClueRelActThirdFaile(),
				tpt.getTranslationContentThirdFaile(),
				tpt.getTranslationRelActThirdFaile()
			);
	}
	
	public void initPracticeNode(){
		Date createTime = new Date();
		String creator ="朱建民";
		ProcessDefinition process = new ProcessDefinition();process.setName("09 考研冲刺");process.setDescription("专用于2008年考研冲刺");process.setCreateTime(createTime);process.setCreator(creator); 
		pAServ.save(process);
		String[] names={"模块测试","学前评测","基础强化","基础强化I","强化训练1","强化训练2","强化训练3","阶段测试一","强化训练4","强化训练5","强化训练6","阶段测试二","强化训练7","强化训练8","强化训练9","阶段测试三","基础强化II","强化训练10","强化训练11","强化训练12","阶段测试四","强化训练13","强化训练14","强化训练15","阶段测试五","单元测试1","单元测试2","单元测试_基础强化部分","语法集训","语法集训1","考研语法专项集训1","考研语法专项集训2","考研语法专项集训3","考研语法专项集训4","考研语法专项集训5","考研语法专项集训6","考研语法专项检测","单元测试_语法集训部分","学后评测"};
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			PracticeNode practiceNode = new PracticeNode();
			practiceNode.setName(name);
			practiceNode.setCreateTime(createTime );
			practiceNode.setCreator(creator);
			practiceNode.setProcessDefinition(process);
			pAServ.save(practiceNode);
		}
	}
	
	/*===================================================================*/
	private ApplicationContext applicationContext;
	private SessionFactory sessionFactory;
	private Session session;
	public  HibernateGenericDao genService;
	
	public ProcessAdminService pAServ ;
	
	private String[] xmlClassPath={
			"spring/applicationContext.xml",
			"spring/applicationContext-hibernate.xml",
			"spring/applicationContext-service.xml",
			"spring/applicationContext-admin-studyflow-service.xml"
			};
 
	public void  setUp(){
		applicationContext=new ClassPathXmlApplicationContext(xmlClassPath);
		 sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");  
		         session = SessionFactoryUtils.getSession(sessionFactory, true);  
		         session.setFlushMode(FlushMode.AUTO);  
		         TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		         genService=(HibernateGenericDao)this.getBean("genService");
		         pAServ=(ProcessAdminService)getBean("processAdminService");		      
	}
	
	public void tearDown() throws Exception {  
        TransactionSynchronizationManager.unbindResource(sessionFactory);  
         SessionFactoryUtils.releaseSession(session, sessionFactory);  
    }
	
	public Object getBean(String beanName){
		Object object=applicationContext.getBean(beanName);
		String str=null;
		return object;
	}
	/*===================================================================*/	
}
