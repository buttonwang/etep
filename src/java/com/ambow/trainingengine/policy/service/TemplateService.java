package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.policy.domain.AssemblePaperPolicyTemplate;
import com.ambow.trainingengine.policy.domain.AssemblingPaperReqTemplate;
import com.ambow.trainingengine.policy.domain.ModuleEvalPolicyTemplate;
import com.ambow.trainingengine.policy.domain.ModuleEvaluatingNodePolicy;
import com.ambow.trainingengine.policy.domain.NodeGroupPolicyAssembling;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingReqTemplate;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;
import com.ambow.trainingengine.policy.domain.PhaseTestNodePolicy;
import com.ambow.trainingengine.policy.domain.PhaseTestPolicyReq;
import com.ambow.trainingengine.policy.domain.PhaseTestPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;
import com.ambow.trainingengine.policy.domain.TrainingPolicyTemplate;
import com.ambow.trainingengine.policy.domain.TrainingUnitNodePolicy;
import com.ambow.trainingengine.policy.domain.TrainingUnitPolicyTemplate;
import com.ambow.trainingengine.policy.domain.UnitTestNodePolicy;
import com.ambow.trainingengine.policy.domain.UnitTestPolicyTemplate;
import com.ambow.trainingengine.util.ParamObject;

public class TemplateService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(UnitTestPolicyTemplateService.class);
	public Map search(String searchType,String nameForSearch,ParamObject p){
		Map map = new HashMap();
		map.put("searchType", searchType);
		map.put("nameForSearch", nameForSearch);
		List searchList = new ArrayList(0);
		String objClassName = "";
		if("training_policy_template".equals(searchType)){
			objClassName = "TrainingPolicyTemplate";
		}else if("assemble_paperPolicy_template".equals(searchType)){
			objClassName = "AssemblePaperPolicyTemplate";
		}else if("paper_assembling_req_template".equals(searchType)){
			objClassName = "PaperAssemblingReqTemplate";
		}else if("PRACTICE".equals(searchType)){
			objClassName = "TrainingUnitPolicyTemplate";
		}else if("EVALUATE".equals(searchType)){
			objClassName = "ModuleEvalPolicyTemplate";
		}else if("PHASETEST".equals(searchType)){
			objClassName = "PhaseTestPolicyTemplate";
		}else if("UNITTEST".equals(searchType)){
			objClassName = "UnitTestPolicyTemplate"; 
		}
		if(!"".equals(objClassName)&&nameForSearch!=null&&!"".equals(nameForSearch.trim())){
			try{
				searchList = find("from "+objClassName+" where name like '%"+nameForSearch+"%'");
			}catch(Exception e ){
				e.printStackTrace();
			}
		}else {
			if(p!=null){
				searchList = find("from "+objClassName);
			}
		}
		map.put("searchList", searchList);
		return map;
		
	}
	/**
	 * 继承父亲的策略（只继承父亲的策略，即父亲没有策略则直接返回不在寻找上级父亲）
	 * @param templateType 策略类型
	 * @param nid 节点id
	 * @param p 其它零散参数
	 * @return
	 */
	public Map inheritTemplate(String templateType,Long nid,ParamObject p){
		Map map = new HashMap();
		List errorLst = new ArrayList();
		try{
			Node node = get(Node.class,nid);
			
			//节点必需存在
			if(node!=null){
				NodeGroupPolicyAssembling ngpa = null;
				try{
					ngpa = get(NodeGroupPolicyAssembling.class,node.getNodeGroup().getId());
					if(ngpa!=null){
						boolean theTemplateExists=false;
						
						if("training_policy_template".equals(templateType)){
							//^|如果是训练策略=========================================================
							TrainingPolicyTemplate t= ngpa.getTrainingPolicyTemplate();
							if(t==null){
								excuteHql("delete from TrainingPolicy as t where t.asfNode.id=?", nid);
							}else{
								TrainingPolicy trainingPolicy =(TrainingPolicy) findObjByHql("from TrainingPolicy as t where t.asfNode.id=?", nid);
								if(trainingPolicy==null){
									//如果没有则创建一个
									trainingPolicy = new TrainingPolicy();
									trainingPolicy.setAsfNode(node);
								}
								initTrainingPolicy(trainingPolicy,t ,null);
								save(trainingPolicy);
							}					
						}else if("assemble_paperPolicy_template".equals(templateType)){
							//^|如果是组卷策略=========================================================
							AssemblePaperPolicyTemplate dbt= ngpa.getAssemblePaperPolicyTemplate();
							if(dbt==null){
								excuteHql("delete from PaperAssemblingPolicy as t where t.asfNode.id=?", nid);
							}else{
								PaperAssemblingPolicy db =(PaperAssemblingPolicy) findObjByHql("from PaperAssemblingPolicy as t where t.asfNode.id=?", nid);
								if(db==null){
									//如果没有则创建一个
									db = new PaperAssemblingPolicy();
									db.setAsfNode(node);
									
								}
								db.setPaperAssemblingMode(dbt.getPaperAssemblingMode());
								save(db);
							}
						}else if("paper_assembling_req_template".equals(templateType)){
							//^|如果是组卷条件=========================================================
							PaperAssemblingReqTemplate dbt= ngpa.getPaperAssemblingReqTemplate();
							excuteHql("delete from PaperAssemblingRequirements as p where p.asfNode.id=?",nid);
							if(dbt!=null){
								//删除原来的所有模板
								Set set = dbt.getAssemblingPaperReqTemplates();
								//使用新模板的数据
								for (Iterator iterator = set.iterator(); iterator.hasNext();) {
									AssemblingPaperReqTemplate aprt = (AssemblingPaperReqTemplate) iterator.next();
									PaperAssemblingRequirements par = new PaperAssemblingRequirements();
									par.setAsfNode(node);
									par.setAmount(aprt.getAmount());
									par.setDifficultyValue(aprt.getDifficultyValue());
									par.setItemTypeCode(aprt.getItemTypeCode());
									par.setOriginalPaperCode(aprt.getOriginalPaperCode());
									par.setRegionCode(aprt.getRegionCode());
									par.setSource(aprt.getSource());
									par.setSubjectCode(aprt.getSubjectCode());
									par.setValidityValue(aprt.getValidityValue());
									par.setYear(aprt.getYear());
									save(par);
								}
							}
						}else if("PRACTICE".equals(templateType)){
							//^|如果是训练单元=========================================================
							TrainingUnitPolicyTemplate  dbt = ngpa.getTrainingUnitPolicyTemplate();							 
							if(dbt==null){
								excuteHql("delete from TrainingUnitNodePolicy as t where t.asfNode.id=?", nid);
							}else{
								TrainingUnitNodePolicy db =(TrainingUnitNodePolicy) findObjByHql("from TrainingUnitNodePolicy as t where t.asfNode.id=?", nid);
								if(db==null){
									//如果没有则创建一个
									db = new TrainingUnitNodePolicy();
									db.setAsfNode(node);
								}
								db.setFailed(dbt.getFailed());
								db.setPass(dbt.getPass());
								save(db);
							}
						}else if("EVALUATE".equals(templateType)){
							//暂时没有策略模板
						}else if("PHASETEST".equals(templateType)){
							//^| 如果是阶段测试 =========================================================
							PhaseTestPolicyTemplate  dbt = ngpa.getPhaseTestPolicyTemplate();
							excuteHql("delete from PhaseTestNodePolicy as p where p.asfNode.id=?",nid);
							if(dbt!=null){
								//删除原来的阶段测试条件
								Set set = dbt.getPhaseTestPolicyReqs();
								if(set!=null){
									for (Iterator iterator = set.iterator(); iterator
											.hasNext();) {
										PhaseTestPolicyReq phaseTestPolicyReq = (PhaseTestPolicyReq)iterator.next();
										PhaseTestNodePolicy ptnp = new PhaseTestNodePolicy();
										ptnp.setAsfNode(node);
										ptnp.setEndValue(phaseTestPolicyReq.getEndValue());
										ptnp.setStartValue(phaseTestPolicyReq.getStartValue());
										save(ptnp);
									}
								}
							}					
						}else if ("UNITTEST".equals(templateType)){
							//^|如果是单元测试========================================================= 
							UnitTestPolicyTemplate  dbt = ngpa.getUnitTestPolicyTemplate();							 
							if(dbt==null){
								excuteHql("delete from UnitTestNodePolicy as t where t.asfNode.id=?", nid);
							}else{
								UnitTestNodePolicy db =(UnitTestNodePolicy) findObjByHql("from UnitTestNodePolicy as t where t.asfNode.id=?", nid);
								if(db==null){
									//如果没有则创建一个
									db = new UnitTestNodePolicy();
									db.setAsfNode(node);
									
								}
								db.setRetrainingScope(dbt.getRetrainingScope());
								save(db);
							}
						}
					}
				}catch(Exception e){
					errorLst.add("继承时出现异常");
				}
				
			}else{
				errorLst.add("节点不存在或已经被删除");
			}
		}catch(Exception e){
			errorLst.add("要设置模板的节点不存在或已经被删除");
			e.printStackTrace();
			logger.debug("TemplateService:initTemplate() 错误：\n "+e.toString());
		}
		map.put("tType", templateType);
		map.put("nid", nid);
		map.put("errorLst",errorLst);
		return map;
	} 
	/**
	 * 以分页方式获取某一类模板。
	 * @param templateType 模板类型
	 * @param pageNo 页码
	 * @param p 其它参数
	 * @return
	 */
	public Map getTemplates(String templateType,Long nid,Integer pageNo,ParamObject p){
		Map map = new HashMap();
		Page page = null;
		String showAction="";
		List errorlst = new ArrayList();
		if("training_policy_template".equals(templateType)){
			page =  pagedQuery("from TrainingPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "trainingPolicyTemplate" ;
		}else if("assemble_paperPolicy_template".equals(templateType)){
			page =  pagedQuery("from AssemblePaperPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction=  "assemblePaperPolicyTemplate" ;
		}else if("paper_assembling_req_template".equals(templateType)){
			page =  pagedQuery("from PaperAssemblingReqTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "paperAssemblingReqTemplate" ;
		}else if("PRACTICE".equals(templateType)){
			page =  pagedQuery("from TrainingUnitPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "trainingUnitPolicyTemplate";
		}else if("GROUP".equals(templateType)){
			//TODO ？？？？？？节点组
			//page =  pagedQuery("from PaperAssemblingReqTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			//viewAction= "trainingUnitPolicyTemplate";
		}else if("EVALUATE".equals(templateType)){
			page =  pagedQuery("from ModuleEvalPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "moduleEvalPolicyTemplate";
		}else if("PHASETEST".equals(templateType)){
			page =  pagedQuery("from PhaseTestPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "phaseTestPolicyTemplate";
		}else if ("UNITTEST".equals(templateType)){
			page =  pagedQuery("from UnitTestPolicyTemplate", pageNo, Constants.DEFAULT_PAGE_SIZE);
			showAction= "unitTestPolicyTemplate";
		}else{
			errorlst.add("没有对应的类型可供提取模板数据");
			
		}
		
		map.put("errorlst",errorlst);
		map.put("page",page);
		map.put("tType",templateType);
		map.put("pageNo",pageNo);
		map.put("showAction", showAction);
		map.put("nid", nid);
		map.put("p",p);
		return map;
	}
	
	public Map initTemplate(String templateType,Long nid,Integer tid,ParamObject p){
		Map map = new HashMap();
		List errorLst = new ArrayList();
		try{
			Node node = get(Node.class,nid);
			//节点必需存在
			if(node!=null){
				//没有指定则为节点类型的相关策略模板
				if(templateType==null){
					templateType=node.getNodeType().toString();
				}
				//模板id必须存在
				if(tid!=null&&tid!=0){
					boolean theTemplateExists=false;
					
					if("training_policy_template".equals(templateType)){
						//^|如果是训练策略=========================================================
						TrainingPolicyTemplate dbt =  get(TrainingPolicyTemplate.class, tid);
						if(dbt!=null){
							//找该节点配过策略
							TrainingPolicy trainingPolicy =(TrainingPolicy) findObjByHql("from TrainingPolicy as t where t.asfNode.id=?", nid);
							if(trainingPolicy==null){
								//如果没有则增加策略
								trainingPolicy = new TrainingPolicy();
								trainingPolicy.setAsfNode(node);
							}
							//设置相关属性
							initTrainingPolicy(trainingPolicy,dbt,null);
							save(trainingPolicy);
							theTemplateExists=true;
						}
					}else if("assemble_paperPolicy_template".equals(templateType)){
						//^|如果是组卷策略=========================================================
						AssemblePaperPolicyTemplate  dbt =  get(AssemblePaperPolicyTemplate.class, tid);
						if(dbt!=null){
							//找该节点配过策略
							PaperAssemblingPolicy db =(PaperAssemblingPolicy) findObjByHql("from PaperAssemblingPolicy as t where t.asfNode.id=?", nid);
							if(db==null){
								//如果没有则创建一个
								db = new PaperAssemblingPolicy();
								db.setAsfNode(node);
								
							}
							//设置相关属性
							db.setPaperAssemblingMode(dbt.getPaperAssemblingMode());
							save(db);
							theTemplateExists=true;
						}else{
							errorLst.add("指定的模板的不存在或已经被删除");
						}
					
					}else if("paper_assembling_req_template".equals(templateType)){
						//^|如果是组卷条件=========================================================
						PaperAssemblingReqTemplate  dbt =  get(PaperAssemblingReqTemplate.class, tid);
						if(dbt!=null){
							//删除原来的所有模板
							excuteHql("delete from PaperAssemblingRequirements as p where p.asfNode.id=?",nid);
							Set set = dbt.getAssemblingPaperReqTemplates();
							//使用新模板的数据
							for (Iterator iterator = set.iterator(); iterator.hasNext();) {
								AssemblingPaperReqTemplate aprt = (AssemblingPaperReqTemplate) iterator.next();
								PaperAssemblingRequirements par = new PaperAssemblingRequirements();
								par.setAsfNode(node);
								par.setAmount(aprt.getAmount());
								par.setDifficultyValue(aprt.getDifficultyValue());
								par.setItemTypeCode(aprt.getItemTypeCode());
								par.setOriginalPaperCode(aprt.getOriginalPaperCode());
								par.setRegionCode(aprt.getRegionCode());
								par.setSource(aprt.getSource());
								par.setSubjectCode(aprt.getSubjectCode());
								par.setValidityValue(aprt.getValidityValue());
								par.setYear(aprt.getYear());
								save(par);
							}
							theTemplateExists=true;
						}
					}else if("PRACTICE".equals(templateType)){
						//^|如果是训练单元=========================================================
						TrainingUnitPolicyTemplate  dbt =  get(TrainingUnitPolicyTemplate.class, tid);
						if(dbt!=null){
							//找该节点配过策略
							TrainingUnitNodePolicy db =(TrainingUnitNodePolicy) findObjByHql("from TrainingUnitNodePolicy as t where t.asfNode.id=?", nid);
							if(db==null){
								//如果没有则创建一个
								db = new TrainingUnitNodePolicy();
								db.setAsfNode(node);
							}
							//设置相关属性
							db.setFailed(dbt.getFailed());
							db.setPass(dbt.getPass());
							save(db);
							theTemplateExists=true;
						}
					
					}else if("GROUP".equals(templateType)){
						//^| 如果是节点组=========================================================
						theTemplateExists=true;
					
					}else if("EVALUATE".equals(templateType)){
						//^| 如果是模块评测=========================================================
						ModuleEvalPolicyTemplate  dbt =  get(ModuleEvalPolicyTemplate.class, tid);
						if(dbt!=null){
							//找该节点配过策略
							ModuleEvaluatingNodePolicy db =(ModuleEvaluatingNodePolicy) findObjByHql("from ModuleEvaluatingNodePolicy as t where t.asfNode.id=?", nid);
							if(db==null){
								//如果没有则创建一个
								db = new ModuleEvaluatingNodePolicy();
								db.setAsfNode(node);
							}
							save(db);
							theTemplateExists=true;
						} 
					
					}else if("PHASETEST".equals(templateType)){
						//^| 如果是阶段测试 =========================================================
						PhaseTestPolicyTemplate  dbt =  get(PhaseTestPolicyTemplate.class, tid);
						if(dbt!=null){
							//删除原来的阶段测试条件
							excuteHql("delete from PhaseTestNodePolicy as p where p.asfNode.id=?",nid);
							List lst = find("from PhaseTestPolicyReq as p where p.phaseTestPolicyTemplate.id=?",tid);
							if(lst!=null){
								for (Iterator iterator = lst.iterator(); iterator
										.hasNext();) {
									PhaseTestPolicyReq phaseTestPolicyReq = (PhaseTestPolicyReq)iterator.next();
									PhaseTestNodePolicy ptnp = new PhaseTestNodePolicy();
									ptnp.setAsfNode(node);
									ptnp.setEndValue(phaseTestPolicyReq.getEndValue());
									ptnp.setStartValue(phaseTestPolicyReq.getStartValue());
									save(ptnp);
								}
							}
							theTemplateExists=true;
						}
				
					}else if ("UNITTEST".equals(templateType)){
						//^|如果是单元测试========================================================= 
						UnitTestPolicyTemplate  dbt =  get(UnitTestPolicyTemplate.class, tid);
						if(dbt!=null){
							//找该节点配过策略
							UnitTestNodePolicy db =(UnitTestNodePolicy) findObjByHql("from UnitTestNodePolicy as t where t.asfNode.id=?", nid);
							if(db==null){
								//如果没有则创建一个
								db = new UnitTestNodePolicy();
								db.setAsfNode(node);
							}
							db.setRetrainingScope(dbt.getRetrainingScope());
							save(db);
							theTemplateExists=true;
						}
					}
					if(theTemplateExists!=true ){
						errorLst.add("指定的模板的不存在或已经被删除");
					}
				}else{
					errorLst.add("模板类型不存在");
				}
			}else{
				errorLst.add("节点不存在或已经被删除");
			}
		}catch(Exception e){
			errorLst.add("要设置模板的节点不存在或已经被删除");
			e.printStackTrace();
			logger.debug("TemplateService:initTemplate() 错误：\n "+e.toString());
		}
		map.put("tType", templateType);
		map.put("nid", nid);
		map.put("errorLst",errorLst);
		return map;
	};
	private void initPaperAssemblingPolicyByParent(PaperAssemblingPolicy paperAssemblingPolicy,PaperAssemblingPolicy parentPaperAssemblingPolicy,Boolean alsoSetNull ){
		paperAssemblingPolicy.setAnsweringTime(parentPaperAssemblingPolicy.getAnsweringTime());
		paperAssemblingPolicy.setDifficultyValue(parentPaperAssemblingPolicy.getDifficultyValue());
		paperAssemblingPolicy.setItems_num(parentPaperAssemblingPolicy.getItems_num());
		paperAssemblingPolicy.setPaper(parentPaperAssemblingPolicy.getPaper());
		paperAssemblingPolicy.setPaperAssemblingMode(parentPaperAssemblingPolicy.getPaperAssemblingMode());
		paperAssemblingPolicy.setTotalScore(parentPaperAssemblingPolicy.getTotalScore());
		paperAssemblingPolicy.setBig_items_num(parentPaperAssemblingPolicy.getBig_items_num());
	}
	private void initTrainingPolicyByParent(TrainingPolicy tp,TrainingPolicy tpParent,Boolean alsoSetNull){
		if(alsoSetNull==null||alsoSetNull==false){
			tp.setOverviewTime(tpParent.getOverviewTime());
		 	tp.setWhenToSeeAnalysis(tpParent.getWhenToSeeAnalysis());
		 	tp.setWhenToCheckAnswer(tpParent.getWhenToCheckAnswer());
		 	tp.setAllowUnsureMark(tpParent.getAllowUnsureMark());
		 	tp.setIsRandom(tpParent.getIsRandom());
		 	tp.setIsRandomAnswerOptions(tpParent.getIsRandomAnswerOptions());
		 	tp.setRightRateForPass(tpParent.getRightRateForPass());
		 	tp.setRightRateRetraining(tpParent.getRightRateRetraining());
		 	tp.setRetrainingItemType(tpParent.getRetrainingItemType());
		 	tp.setRetrainingItemOrder(tpParent.getRetrainingItemOrder());
		 	tp.setRetrainingRightRateTestFaile(tpParent.getRetrainingRightRateTestFaile());
		 	tp.setRetrainingItemTypeTestFaile(tpParent.getRetrainingItemTypeTestFaile());
		 	tp.setRetrainingItemOrderTestFaile(tpParent.getRetrainingItemOrderTestFaile());
		 	tp.setRandomAssemItemsTestFaile(tpParent.getRandomAssemItemsTestFaile());
		 	tp.setAssemItemsTypeTestFaile(tpParent.getAssemItemsTypeTestFaile());
		 	tp.setAssemItemsRateTestFaile(tpParent.getAssemItemsRateTestFaile());
		 	tp.setClueContentFirstFaile(tpParent.getClueContentFirstFaile());
		 	tp.setClueRelActFirstFaile(tpParent.getClueRelActFirstFaile());
		 	tp.setTranslationContentFirstFaile(tpParent.getTranslationContentFirstFaile());
		 	tp.setTranslationRelActFirstFaile(tpParent.getTranslationRelActFirstFaile());
		 	tp.setClueContentSecondFaile(tpParent.getClueContentSecondFaile());
		 	tp.setClueRelActSecondFaile(tpParent.getClueRelActSecondFaile());
		 	tp.setTranslationContentSecondFaile(tpParent.getTranslationContentSecondFaile());
		 	tp.setTranslationRelActSecondFaile(tpParent.getTranslationRelActSecondFaile());
		 	tp.setClueContentThirdFaile(tpParent.getClueContentThirdFaile());
		 	tp.setClueRelActThirdFaile(tpParent.getClueRelActThirdFaile());
		 	tp.setTranslationContentThirdFaile(tpParent.getTranslationContentThirdFaile());
		 	tp.setTranslationRelActThirdFaile(tpParent.getTranslationRelActThirdFaile());
		}
	}
	 
	/**
	 * 
	 * @param tp TrainingPolicy 对象 
	 * @param tpt TrainingPolicyTemplate 模板对象
	 * @param alsoSetNull  模板对象某属性为空时是否设置对象的对应属性
	 */
	private void initTrainingPolicy(TrainingPolicy tp,TrainingPolicyTemplate tpt,Boolean alsoSetNull){
		if(alsoSetNull==null||alsoSetNull==false){
			tp.setOverviewTime(tpt.getOverviewTime());
		 	tp.setWhenToSeeAnalysis(tpt.getWhenToSeeAnalysis());
		 	tp.setWhenToCheckAnswer(tpt.getWhenToCheckAnswer());
		 	tp.setAllowUnsureMark(tpt.getAllowUnsureMark());
		 	tp.setIsRandom(tpt.getIsRandom());
		 	tp.setIsRandomAnswerOptions(tpt.getIsRandomAnswerOptions());
		 	tp.setRightRateForPass(tpt.getRightRateForPass());
		 	tp.setRightRateRetraining(tpt.getRightRateRetraining());
		 	tp.setRetrainingItemType(tpt.getRetrainingItemType());
		 	tp.setRetrainingItemOrder(tpt.getRetrainingItemOrder());
		 	tp.setRetrainingRightRateTestFaile(tpt.getRetrainingRightRateTestFaile());
		 	tp.setRetrainingItemTypeTestFaile(tpt.getRetrainingItemTypeTestFaile());
		 	tp.setRetrainingItemOrderTestFaile(tpt.getRetrainingItemOrderTestFaile());
		 	tp.setRandomAssemItemsTestFaile(tpt.getRandomAssemItemsTestFaile());
		 	tp.setAssemItemsTypeTestFaile(tpt.getAssemItemsTypeTestFaile());
		 	tp.setAssemItemsRateTestFaile(tpt.getAssemItemsRateTestFaile());
		 	tp.setClueContentFirstFaile(tpt.getClueContentFirstFaile());
		 	tp.setClueRelActFirstFaile(tpt.getClueRelActFirstFaile());
		 	tp.setTranslationContentFirstFaile(tpt.getTranslationContentFirstFaile());
		 	tp.setTranslationRelActFirstFaile(tpt.getTranslationRelActFirstFaile());
		 	tp.setClueContentSecondFaile(tpt.getClueContentSecondFaile());
		 	tp.setClueRelActSecondFaile(tpt.getClueRelActSecondFaile());
		 	tp.setTranslationContentSecondFaile(tpt.getTranslationContentSecondFaile());
		 	tp.setTranslationRelActSecondFaile(tpt.getTranslationRelActSecondFaile());
		 	tp.setClueContentThirdFaile(tpt.getClueContentThirdFaile());
		 	tp.setClueRelActThirdFaile(tpt.getClueRelActThirdFaile());
		 	tp.setTranslationContentThirdFaile(tpt.getTranslationContentThirdFaile());
		 	tp.setTranslationRelActThirdFaile(tpt.getTranslationRelActThirdFaile());
		}else{
		 	if(tpt.getOverviewTime()!=null){
				tp.setOverviewTime(tpt.getOverviewTime());
			}
			if(tpt.getWhenToSeeAnalysis()!=null){
				tp.setWhenToSeeAnalysis(tpt.getWhenToSeeAnalysis());
			}
			if(tpt.getWhenToCheckAnswer()!=null){
				tp.setWhenToCheckAnswer(tpt.getWhenToCheckAnswer());
			}
			if(tpt.getAllowUnsureMark()!=null){
				tp.setAllowUnsureMark(tpt.getAllowUnsureMark());
			}
			if(tpt.getIsRandom()!=null){
				tp.setIsRandom(tpt.getIsRandom());
			}
			if(tpt.getIsRandomAnswerOptions()!=null){
				tp.setIsRandomAnswerOptions(tpt.getIsRandomAnswerOptions());
			}
			if(tpt.getRightRateForPass()!=null){
				tp.setRightRateForPass(tpt.getRightRateForPass());
			}
			if(tpt.getRightRateRetraining()!=null){
				tp.setRightRateRetraining(tpt.getRightRateRetraining());
			}
			if(tpt.getRetrainingItemType()!=null){
				tp.setRetrainingItemType(tpt.getRetrainingItemType());
			}
			if(tpt.getRetrainingItemOrder()!=null){
				tp.setRetrainingItemOrder(tpt.getRetrainingItemOrder());
			}
			if(tpt.getRetrainingRightRateTestFaile()!=null){
				tp.setRetrainingRightRateTestFaile(tpt.getRetrainingRightRateTestFaile());
			}
			if(tpt.getRetrainingItemTypeTestFaile()!=null){
				tp.setRetrainingItemTypeTestFaile(tpt.getRetrainingItemTypeTestFaile());
			}
			if(tpt.getRetrainingItemOrderTestFaile()!=null){
				tp.setRetrainingItemOrderTestFaile(tpt.getRetrainingItemOrderTestFaile());
			}
			if(tpt.getRandomAssemItemsTestFaile()!=null){
				tp.setRandomAssemItemsTestFaile(tpt.getRandomAssemItemsTestFaile());
			}
			if(tpt.getAssemItemsTypeTestFaile()!=null){
				tp.setAssemItemsTypeTestFaile(tpt.getAssemItemsTypeTestFaile());
			}
			if(tpt.getAssemItemsRateTestFaile()!=null){
				tp.setAssemItemsRateTestFaile(tpt.getAssemItemsRateTestFaile());
			}
			if(tpt.getClueContentFirstFaile()!=null){
				tp.setClueContentFirstFaile(tpt.getClueContentFirstFaile());
			}
			if(tpt.getClueRelActFirstFaile()!=null){
				tp.setClueRelActFirstFaile(tpt.getClueRelActFirstFaile());
			}
			if(tpt.getTranslationContentFirstFaile()!=null){
				tp.setTranslationContentFirstFaile(tpt.getTranslationContentFirstFaile());
			}
			if(tpt.getTranslationRelActFirstFaile()!=null){
				tp.setTranslationRelActFirstFaile(tpt.getTranslationRelActFirstFaile());
			}
			if(tpt.getClueContentSecondFaile()!=null){
				tp.setClueContentSecondFaile(tpt.getClueContentSecondFaile());
			}
			if(tpt.getClueRelActSecondFaile()!=null){
				tp.setClueRelActSecondFaile(tpt.getClueRelActSecondFaile());
			}
			if(tpt.getTranslationContentSecondFaile()!=null){
				tp.setTranslationContentSecondFaile(tpt.getTranslationContentSecondFaile());
			}
			if(tpt.getTranslationRelActSecondFaile()!=null){
				tp.setTranslationRelActSecondFaile(tpt.getTranslationRelActSecondFaile());
			}
			if(tpt.getClueContentThirdFaile()!=null){
				tp.setClueContentThirdFaile(tpt.getClueContentThirdFaile());
			}
			if(tpt.getClueRelActThirdFaile()!=null){
				tp.setClueRelActThirdFaile(tpt.getClueRelActThirdFaile());
			}
			if(tpt.getTranslationContentThirdFaile()!=null){
				tp.setTranslationContentThirdFaile(tpt.getTranslationContentThirdFaile());
			}
			if(tpt.getTranslationRelActThirdFaile()!=null){
				tp.setTranslationRelActThirdFaile(tpt.getTranslationRelActThirdFaile());
			}
		}
	
	}
}
