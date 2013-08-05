package com.ambow.trainingengine.exam.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.Assert;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.Node;
import com.ambow.studyflow.domain.NodeInstance;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.studyflow.dto.NodeDTO.NodeType;
import com.ambow.trainingengine.exam.domain.CurrentTestStatus;
import com.ambow.trainingengine.exam.domain.EvaluatingAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryAnswerStatus;
import com.ambow.trainingengine.exam.domain.HistoryTestStatus;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.exam.web.data.ExamBeginProperty;
import com.ambow.trainingengine.exam.web.data.Page;
import com.ambow.trainingengine.exam.web.data.ViewControl;
import com.ambow.trainingengine.exam.web.data.ViewControlProxy;
import com.ambow.trainingengine.itembank.domain.DynamicAssembledPaper;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Paper;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;
import com.ambow.trainingengine.policy.domain.TrainingPolicy;


/*
 * ExamItemService.java
 * 
 * Created on Jul 2, 2008 8:34:53 PM
 * 该Service负责组卷\过滤题
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class ExamItemService extends HibernateGenericDao {
	
	//private ExamAnswerService examAnswerService;
	public List<Item> fetchItemList(List<Item> examItemsList,ViewControl viewControl){
		if(viewControl.getProjectVersion().equals("ky"))
			return fetchItemListForKy(examItemsList,viewControl);
		else if(viewControl.getProjectVersion().equals("mpc"))
			//流程驱动考试要与弱项强化考试区别开
			return fetchItemListForMpc(examItemsList,viewControl);			
		else
			return null;
	}
	
	/*
	 * TODO:另两种组卷方式待实现..过滤题的逻辑
	 * 08.08.07重做时的取题逻辑..
	 */
	private List<Item> fetchItemListForKy(List<Item> examItemsList,ViewControl viewControl){
		
		for(NodeInstance nodeInstance:viewControl.getNodeInstances()){
			Node node=nodeInstance.getNode();
			PaperAssemblingPolicy assPolicy=fetchAssemblePolicy(node);//0手工1动态组卷2动态出题?
			List<Item> items=new ArrayList<Item>();
			int mode=assPolicy.getPaperAssemblingMode();
			if(mode==0){
				Paper paper=assPolicy.getPaper();
				if(paper!=null){
					items.addAll(paper.getItems());
				}
			}else if(mode==1||(mode>10&&mode<20)){
				DynamicAssembledPaper dyPaper=getCurrentDynamicPaper(nodeInstance);
				if(dyPaper==null){
					//List<PaperAssemblingRequirements> requirementsList=getAssembleRequirements(node);
					dyPaper=this.getAssemblingItemListForKy(nodeInstance,assPolicy);					
				} 
				items.addAll(dyPaper.getItems());
			}else if(mode==3){				
				Paper paper=assPolicy.getPaper();
				if(paper==null){
					String[] ids=assPolicy.getPaperList().split(",");
					Random r=new Random(); 
					int j=r.nextInt(ids.length); 					
					Paper tempPpaper=this.get(Paper.class, new Long(ids[j]));
					if(paper!=null){
						assPolicy.setPaper(tempPpaper);
						this.save(assPolicy);
						items.addAll(tempPpaper.getItems());
					}
				}else{
					if(paper!=null)
						items.addAll(paper.getItems());
				}
			}else if(mode==2||(mode>20&&mode<30)){
				/*考研没有此出题模式*/
			}
			//试题与节点实例配上数据关联
			for(Item item:items){
				item.setNodeInstanceId(nodeInstance.getId());
			}
			
			//过滤题
			if(viewControl.isWeaknessEnhance()||viewControl.getRedoType()!=0
					&&viewControl.getExamNodeIns().getNode().getNodeType()!=NodeType.EVALUATE
					&&viewControl.getExamNodeIns().getNode().getNodeType()!=NodeType.PHASETEST){
				items=this.filterItemsForFy(items,viewControl, viewControl.getItemType());
			}
			if(items!=null)
				examItemsList.addAll(items);
		}
		return examItemsList;
	}
	
	
	
	private List<Item> fetchItemListForMpc(List<Item> examItemsList,ViewControl viewControl){
		if (viewControl.isExtPractice()) {
			return fetchExtPracticeItems(examItemsList, viewControl);
		}
		
		for(NodeInstance nodeInstance:viewControl.getNodeInstances()){
			//System.out.println("nodeInstance:"+nodeInstance.getId());
			boolean tag=false; //判断动态出题标记
			int num=0;//动态出题的题数
			Node node=nodeInstance.getNode();
			PaperAssemblingPolicy assPolicy=fetchAssemblePolicy(node);//0手工1动态组卷2动态出题?
			//设置标准答题时间
			viewControl.setStandardAnsweringTime(assPolicy.getStandardAnsweringTime()==null?0:assPolicy.getStandardAnsweringTime());
			
			List<Item> items=new ArrayList<Item>();
			List<Item> tempItems=null;
			int mode=assPolicy.getPaperAssemblingMode();
			if(mode==0){
				Paper paper=assPolicy.getPaper();
				if(paper!=null){
					items.addAll(paper.getItems());
				}
			}else if(mode==1||(mode>10&&mode<20)){
				DynamicAssembledPaper dyPaper=getCurrentDynamicPaper(nodeInstance);
				if(dyPaper==null){
					dyPaper=this.getAssemblingItemListForMpc(nodeInstance,assPolicy,null,null);					
				} 
				items.addAll(dyPaper.getItems());
			}else if(mode==3){				
				Paper paper=assPolicy.getPaper();
				if(paper==null){
					String[] ids=assPolicy.getPaperList().split(",");
					Random r=new Random(); 
					int j=r.nextInt(ids.length); 					
					Paper tempPpaper=this.get(Paper.class, new Long(ids[j]));
					if(paper!=null){
						assPolicy.setPaper(tempPpaper);
						this.save(assPolicy);
						items.addAll(tempPpaper.getItems());
					}
				}else{
					if(paper!=null)
						items.addAll(paper.getItems());
				}
			}else if(mode==2||(mode>20&&mode<30)){
				DynamicAssembledPaper dyPaper=getCurrentDynamicPaper(nodeInstance);
				if(!viewControl.isWeaknessEnhance()){
					if(dyPaper==null&&viewControl.getPreAnswersStatus().isEmpty()){
						//第一次生成答题试卷
						dyPaper=this.getAssemblingItemListForMpc(nodeInstance,assPolicy,null,null);	
						items.addAll(dyPaper.getItems());
					}else if(dyPaper==null&&!viewControl.getPreAnswersStatus().isEmpty()){
						//经过后测后再次生成答题试卷
						tag=true;
						dyPaper=getCurrentLastDynamicPaper(nodeInstance);
						tempItems=dyPaper.getItems();
						//tempItems=this.filterItemsForMpc(dyPaper.getItems(),viewControl, viewControl.getItemType());					
						TrainingPolicy trainingPolicy=this.get(TrainingPolicy.class, node.getId());
						num=(int)(assPolicy.getBig_items_num()*(trainingPolicy.getAssemItemsRateTestFaile()/(float)100));
						if(num==0)
							num=1;
						DynamicAssembledPaper tempDdyPaper=this.getAssemblingItemListForMpc(nodeInstance,assPolicy,num,tempItems);
						/*if((tempItems==null||tempItems.size()==0)&&dyPaper==null){
							
							List<Item> tempItemList=tempDdyPaper.getItems();
							Collections.shuffle(tempItemList);
							tempItems=tempItemList.subList(0, num);
							
							//把往次生成的动态试卷记录状态置成0
							tempDdyPaper.setStatus(0);
							this.save(tempDdyPaper);
							DynamicAssembledPaper tempDyPaper=new DynamicAssembledPaper();
							int answeringTime=0,items_num=0;
							float totalScore=0;
							for(Item item:tempItems){
								if(item.getAnsweringTime()==null)
									item.setAnsweringTime(0);
								if(item.getSubItems()!=null&&item.getSubItems().size()>0){
									if(item.getReadingTime()==null)
										item.setReadingTime(0);
									answeringTime+=item.getAnsweringTime()+item.getReadingTime();
								}else
									answeringTime+=item.getAnsweringTime();
								//mpc出题策略中试题个数为大题数量
								items_num++;
								totalScore+=item.getScore();
							}
							
							//生成新的动态试卷记录
							tempDyPaper.setItems(tempItems);
							tempDyPaper.setAnsweringTime(answeringTime);
							tempDyPaper.setAssemblingTime(new Date());
							tempDyPaper.setTotalScore((int)totalScore);
							tempDyPaper.setItemsNum(items_num);
							tempDyPaper.setStatus(1);
							tempDyPaper.setAsfNodeInstance(nodeInstance);
							this.save(tempDyPaper);
							
						}*/
						//if(tempDdyPaper!=null)
							items.addAll(tempDdyPaper.getItems());
						//else 
						//	items.addAll(tempItems);
					 }
					else
						items.addAll(dyPaper.getItems());
				}
				else{//用于弱项强化
					if(dyPaper==null)//后测不通过会出现此状况
						dyPaper=getLastDynamicPaper(nodeInstance);
					items.addAll(dyPaper.getItems());	
				}
			}
			//试题与节点实例配上数据关联
			for(Item item:items){
				item.setNodeInstanceId(nodeInstance.getId());
			}
			//过滤题
			if(viewControl.isWeaknessEnhance()||viewControl.getRedoType()!=0
						&&viewControl.getExamNodeIns().getNode().getNodeType()!=NodeType.EVALUATE){
				viewControl.setStandardAnsweringTime(0);
				List<Item> tempItemList=this.filterItemsForMpc(items,viewControl, viewControl.getItemType());				
				if(tempItemList!=null)
					items=tempItemList;
				if(tag&&tempItemList==null){
					//动态出题出不出来题，就随机取
					Collections.shuffle(items);
					items=items.subList(0, num);
				}
				if(tempItemList==null&&viewControl.isWeaknessEnhance())
					continue;
			}
			examItemsList.addAll(items);
			//System.out.println(items.size()+","+nodeInstance.getId()+","+nodeInstance.getNode().getNodeType());
		}
		return examItemsList;
	}

	@SuppressWarnings("unchecked")
	private List<Item> fetchExtPracticeItems(List<Item> examItemsList, ViewControl viewControl) {
		List<Item> items = this.find("select distinct c.item from CurrentAnswersStatus c where c.asfNodeInstance.id=?",
								viewControl.getExamNodeIns().getId());
		List<Item> tempItemList=this.filterItemsForMpc(items, viewControl, viewControl.getItemType());				
		if (tempItemList!=null) examItemsList.addAll(tempItemList);
		return examItemsList;
	}
	
	/*
	 * 根据以往的记录来决定
	 * 通过率
	 */
	@SuppressWarnings("unchecked")
	public void setTrainPolicy(ViewControl viewControl){
		List<NodeInstance> nodeInsList= viewControl.getNodeInstances();
		Map<NodeInstance,CurrentTestStatus> testStatusMap=viewControl.getPreStatusMap();
		Map<NodeInstance,Float> passRateMap=viewControl.getPassRateMap();
		
		String hql="from TrainingPolicy policy where policy.nodeId=?";
		List<TrainingPolicy> policys=null;
		TrainingPolicy trainingPolicy=null;
		CurrentTestStatus testStatus=null;
		Float passRate=null;
		for(NodeInstance nodeInstance:nodeInsList){
			policys=this.find(hql, nodeInstance.getNode().getId());
			testStatus=testStatusMap.get(nodeInstance);
			
			if(policys!=null&&policys.size()>0){
				trainingPolicy=policys.get(0);
				viewControl.setTrainPolicy(trainingPolicy);//设置默认的变态训练策略
				if(testStatus==null)passRate=trainingPolicy.getRightRateForPass();
				if(testStatus!=null&&testStatus.getTestStatus()>=1&&testStatus.getIsTest()==false)passRate=trainingPolicy.getRightRateRetraining();
				if(testStatus!=null&&testStatus.getIsTest()==true)passRate=trainingPolicy.getRetrainingRightRateTestFaile();

				passRateMap.put(nodeInstance, passRate);
				if(viewControl.getRedoType()!=0&&!viewControl.isExtPractice()&&
					nodeInstance.getNode().getNodeType()!=NodeType.EXAM&&
					nodeInstance.getNode().getNodeType()!=NodeType.PHASETEST&&
					nodeInstance.getNode().getNodeType()!=NodeType.UNITTEST){
					Float redoScope=null;
					if(viewControl.getRedoType()==1)
						redoScope=trainingPolicy.getRetrainingItemType();
					if(viewControl.getRedoType()==2)
						redoScope=trainingPolicy.getRetrainingItemTypeTestFaile();
					viewControl.setItemType(redoScope);
				}
			}
		}
		if((nodeInsList.size()>=1) && (trainingPolicy!=null) ) {
			Float rightRate=passRateMap.get(nodeInsList.get(0));
			if(rightRate!=null)viewControl.setRequireRightRate(rightRate.intValue());
			else viewControl.setRequireRightRate(0);
			
			if (trainingPolicy.getWhenToSeeAnalysis()==null) trainingPolicy.setWhenToSeeAnalysis(1);
			if (trainingPolicy.getWhenToCheckAnswer()==null) trainingPolicy.setWhenToCheckAnswer(1);
			if (trainingPolicy.getAllowUnsureMark()==null) trainingPolicy.setAllowUnsureMark(0);
			if (trainingPolicy.getIsRandomAnswerOptions()==null) trainingPolicy.setIsRandomAnswerOptions(0);
			
			viewControl.setAnalysisPolicy(trainingPolicy.getWhenToSeeAnalysis());
			viewControl.setAnswerPolicy(trainingPolicy.getWhenToCheckAnswer());
			viewControl.setMarkPolicy(trainingPolicy.getAllowUnsureMark());
			viewControl.setRandomAnswerOptionsPolicy(trainingPolicy.getIsRandomAnswerOptions());
		}else{
			viewControl.setAnalysisPolicy(2);
			viewControl.setAnswerPolicy(2);
			viewControl.setMarkPolicy(1);
			viewControl.setRandomAnswerOptionsPolicy(0);
		}
	}
	
	
	/*
	 * 根据组卷条件,组成所需的试卷
	 */
	public DynamicAssembledPaper makeDynamicPaper(NodeInstance nodeInstance,PaperAssemblingPolicy assPolicy,List<PaperAssemblingRequirements> paperAssemblingRequirementsList){
		DynamicAssembledPaper dynamicPaper=new DynamicAssembledPaper();
		for(PaperAssemblingRequirements requirements:paperAssemblingRequirementsList){
			List<Item> reqItems=pickRandomItems(requirements);
			for(Item item:reqItems){
				dynamicPaper.getItems().add(item);
			}
			
		}
		//取出题少于等于0时,异常需要
		if(dynamicPaper.getItems().size()>0){
			int totalScore=0;
			int answeringTime=0;
			int itemCount=0;
			for(Item item:dynamicPaper.getItems()){
				totalScore=(int) (totalScore+item.getScore());
				answeringTime=answeringTime+item.getAnsweringTime();
				if(item.getSubItems().size()>0){
					itemCount=itemCount+item.getSubItems().size();
				}else{
					itemCount=itemCount+1;
				}
			}
			Date assemblingTime=new Date();
			dynamicPaper.setAnsweringTime(answeringTime);
			dynamicPaper.setAssemblingTime(assemblingTime);
			dynamicPaper.setTotalScore(totalScore);
			dynamicPaper.setItemsNum(dynamicPaper.getItems().size());
			dynamicPaper.setStatus(1);
			dynamicPaper.setAsfNodeInstance(nodeInstance);
			
			/*assPolicy.setAnsweringTime(answeringTime);
			assPolicy.setItems_num(itemCount);
			assPolicy.setTotalScore(totalScore);
			this.save(assPolicy);*/
			this.save(dynamicPaper);
		}
		
		return dynamicPaper;
	}

	/*
	 * 根据组卷条件,随机取题,目前只支持mysql.
	 */
	@SuppressWarnings("unchecked")
	public List<Item> pickRandomItems(PaperAssemblingRequirements requirements){
		
		String hql="select item from Item item inner join item.itemType type where" +
				" type.code='"+requirements.getItemTypeCode()+"' " +
				" and item.difficultyValue >="+requirements.getDifficultyValue() +
				" order by rand()";
		SessionFactory sessionFactory=this.getTemplate().getSessionFactory();
		Session session=sessionFactory.openSession();
		List<Item> items=session.createQuery(hql).setMaxResults(requirements.getAmount()).list();
		for(Item item:items){
			item.getSubItems().size();//此处初始化子题
		}
		session.close();
		return items;
	}
	
	/*
	 * 根据星级等标签标记 来过滤题目
	 * 0 0.5 1 2 3 4 5 星级 11未答 12错题 14正确 15疑问 -1 全部
	 * 
	 */
	public List<Item> filterItems(List<Item> items,ViewControl viewControl, Float scope){
		if(scope==-1) return items;
		boolean isWeaknessEnhance=viewControl.getIsWeaknessEnhance();
		Map<String,String> answerMap=viewControl.getAnswerMap();
		Map<String,Integer> markMap=viewControl.getMarkMap();
		Map<String,Boolean> rightMap=viewControl.getRightMap();
		Map<String,Float> starMap=viewControl.getStarMap();
		List<Item> selectedList=new ArrayList<Item>();
		Set<Item> selectedSet=new HashSet<Item>();
		String mapKey=null;
		for(Item item:items){
			if(item.getSubItems().size()>0){
				for(SubItem subItem:item.getSubItems()){
					mapKey=ExamUtil.getMapKey(item, subItem);
					subItem.setEnable(false);
					Float star=starMap.get(mapKey);
					/*if(scope<=5){						
						if(isWeaknessEnhance){
							if(star.equals(scope)){
								selectedSet.add(item);
								subItem.setEnable(true);
							}
						}else{
							if(star>=scope){
								selectedSet.add(item);
								subItem.setEnable(true);
							}
						}
						
					}*/
					if(scope<=5&&isWeaknessEnhance){
						if(star.equals(scope)){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
					}
					else if(scope<=5&&!isWeaknessEnhance){
						Boolean rightFlag=rightMap.get(mapKey);
						if(star>=scope||(rightFlag!=null&&!rightFlag)){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
					}
					else if(scope==11){
						String answerStr=answerMap.get(mapKey);
						if(answerStr==null||answerStr.trim().length()==0){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
					}
					else if(scope==12){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==false){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
					else if(scope==13){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==false){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
					else if(scope==14){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==true){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
					else if(scope==15){
						Integer markInt=markMap.get(mapKey);
						if(markInt!=null&&markInt==1){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
				}
			}else{
				mapKey=ExamUtil.getMapKey(item, null);
				Float star=starMap.get(mapKey);
				if(scope<=5&&isWeaknessEnhance){
					if(star.equals(scope))
							selectedSet.add(item);
				}
				else if(scope<=5&&!isWeaknessEnhance){
					Boolean rightFlag=rightMap.get(mapKey);
					if(star>=scope||(rightFlag!=null&&!rightFlag))							
						selectedSet.add(item);
				}
				else if(scope==11){
					String answerStr=answerMap.get(mapKey);
					if(answerStr==null||answerStr.trim().length()==0){
						selectedSet.add(item);
					}
				}
				else if(scope==12){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				else if(scope==13){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				else if(scope==14){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==true){
						selectedSet.add(item);
					}
					
				}
				else if(scope==15){
					Integer markInt=markMap.get(mapKey);
					if(markInt!=null&&markInt==1){
						selectedSet.add(item);
					}
					
				}
				
			}
		}
		if(selectedSet.size()>0){
			selectedList.addAll(selectedSet);
		}
		else{
			//针对取不到题的时候用的
			if(scope>5)
				scope=5f;
			else if(scope==0.5f||scope==1f)
				scope=scope-0.5f;
			else
				scope--;
			selectedList=this.filterItems(items,viewControl,scope);			
			if(scope==-1)
				for(Item item:selectedList){
					for(SubItem subItem:item.getSubItems()){
						subItem.setEnable(true);
					}
				}
			/*selectedList=items;
			for(Item item:selectedList){
				for(SubItem subItem:item.getSubItems()){
					subItem.setEnable(true);
				}
			}*/
		}
		return selectedList;
	}
	
	
	/*
	 * 将题目的选项进行乱序,新的顺序保存在item的field中
	 */
	public List<Item> randomOption(List<Item> items){
		return null;
	}
	
	/*
	 * 根据NodeInstance取得所有的题目
	 */
	public List<Item> getItemsByNodeInstance(NodeInstance nodeInstance){
		List<Item> items=new ArrayList<Item>();
		PaperAssemblingPolicy mode=fetchAssemblePolicy(nodeInstance.getNode());
		if(mode.getPaperAssemblingMode()==0){
			Paper paper=mode.getPaper();
			if(paper!=null){
				items.addAll(paper.getItems());
			}
		}else if(mode.getPaperAssemblingMode()>=1){
			DynamicAssembledPaper dyPaper=getCurrentDynamicPaper(nodeInstance);
			items.addAll(dyPaper.getItems());
		}
		
		return items;
		
	}
	
	/*
	 *根据节点实例取得所有的动态卷 
	 */
	@SuppressWarnings("unchecked")
	public DynamicAssembledPaper getCurrentDynamicPaper(NodeInstance nodeInstance){
		String hql="select dyPaper from DynamicAssembledPaper dyPaper inner join dyPaper.asfNodeInstance nodeIns where dyPaper.status=1 and nodeIns.id="+nodeInstance.getId();
		List<DynamicAssembledPaper> dyPapers=this.find(hql);
		DynamicAssembledPaper paper=null;
		if(dyPapers!=null&&dyPapers.size()>0){
			paper=dyPapers.get(0);
			//paper.getItems().size();
		}
		return paper;
	}
	
	@SuppressWarnings("unchecked")
	public DynamicAssembledPaper getLastDynamicPaper(NodeInstance nodeInstance){
		String hql="select dyPaper from DynamicAssembledPaper dyPaper inner join dyPaper.asfNodeInstance nodeIns where dyPaper.status=0 and nodeIns.id="+nodeInstance.getId()+" order by dyPaper.id desc";
		List<DynamicAssembledPaper> dyPapers=this.find(hql);
		DynamicAssembledPaper paper=null;
		if(dyPapers!=null&&dyPapers.size()>0){
			paper=dyPapers.get(0);
			//paper.getItems().size();
		}
		return paper;
	}
	
	/*
	 * 取得某一节点所有的组卷策略
	 */
	@SuppressWarnings("unchecked")
	public List<PaperAssemblingRequirements> getAssembleRequirements(Node node){
		String hql="select requirements from PaperAssemblingRequirements requirements inner join requirements.asfNode node where node.id="+node.getId();
		List<PaperAssemblingRequirements> requirements=this.find(hql);
		return requirements;
	}
	
	/*
	 * 取得节点的组卷模式
	 * 
	 */
	public PaperAssemblingPolicy fetchAssemblePolicy(Node node){
		String hql="select policy from PaperAssemblingPolicy policy inner join policy.asfNode node where "+
			" node.id="+node.getId();
		PaperAssemblingPolicy mode=(PaperAssemblingPolicy)this.createQuery(hql).uniqueResult();
		return mode;
	}
	
	/*
	 * 遍历列表过滤题目 是否标记\是否已做
	 * 过滤时不应采用Page自带的数据,因为很可能是过期数据,应从Session Map中取
	 * 生成符合要求的PageList
	 * filterType==4 为Marked
	 * filterType==3 为undo的 
	 * filterType==2 为错误的
	 * filterType==1 为全部
	 * 
	 */
	public List<Page> getFilteredPages(List<Page> pages,Integer filterType, ViewControlProxy viewControl){
		if(viewControl.getProjectVersion().equals("ky"))
			return getFilteredPagesForKY(pages, filterType, viewControl);
		else if(viewControl.getProjectVersion().equals("mpc"))			
			return getFilteredPagesForMPC(pages, filterType, viewControl);			
		else
			return null;
	}
	
	public List<Page> getFilteredPagesForKY(List<Page> pages,Integer filterType, ViewControlProxy viewControl){
		Set<Item> selectedSet=new HashSet<Item>();
		List<Item> selectedList=new ArrayList<Item>();
		Map<String,String> answerMap=viewControl.getAnswerMap();
		Map<String,Integer> markMap=viewControl.getMarkMap();
		Map<String,Boolean> rightMap=viewControl.getRightMap();
		if(filterType==1){//取全部题
			return pages;
		}
		for(Page page:pages){
			//Integer[] isMark=page.getIsMark();
			//Integer[] isDone=page.getIsDone();
			int itemSizeCount=0;
			String mapKey=null;
			for(Item item:page.getItems()){
				if(item.getSubItems().size()>0){
					for(SubItem subItem:item.getSubItems()){
						mapKey=ExamUtil.getMapKey(item,subItem);
						subItem.setFilterShow(false);//先取消上次的赋值
						if(filterType==4){
							Integer markInt=markMap.get(mapKey);
							if(markInt!=null&&markInt==1){
								selectedSet.add(item);
								subItem.setFilterShow(true);
							}
						}else if(filterType==3){
							String answerStr=answerMap.get(mapKey);
							if(answerStr==null||answerStr.trim().length()==0){
								selectedSet.add(item);
								subItem.setFilterShow(true);
							}
						}else if(filterType==2){
							//mapKey=ExamUtil.getMapKey(item, subItem);
							Boolean rightFlag=rightMap.get(mapKey);
							String answer=answerMap.get(mapKey);
							if(answer!=null&&answer.trim().length()>0&&rightFlag==false){
								selectedSet.add(item);
								subItem.setFilterShow(true);
							}
						}
						
						itemSizeCount=itemSizeCount+1;
					}
					
					
				}else{
					item.setFilterShow(false);
					mapKey=ExamUtil.getMapKey(item,null);
					if(filterType==4){
						Integer markInt=markMap.get(mapKey);
						if(markInt!=null&&markInt==1){
							selectedSet.add(item);
							item.setFilterShow(true);
						}
					}else if(filterType==3){
						String answerStr=answerMap.get(mapKey);
						if(answerStr==null||answerStr.trim().length()==0){
							selectedSet.add(item);
							item.setFilterShow(true);
						}
					}else if(filterType==2){
						Boolean rightFlag=rightMap.get(mapKey);
						String answer=answerMap.get(mapKey);
						if(answer!=null&&answer.trim().length()>0&&rightFlag==false){
							selectedSet.add(item);
							item.setFilterShow(true);
						}
					}
					
					itemSizeCount=itemSizeCount+1;
				}	
			}
		}
		// 循环取题结束..
		if(selectedSet.isEmpty()==true){
			//空..
			return pages;
		}
		selectedList.addAll(selectedSet);
		List<Page> newPages=viewControl.itemPaging(selectedList);
		
		return newPages;
	}
	
	public List<Page> getFilteredPagesForMPC(List<Page> pages,Integer filterType, ViewControlProxy viewControl){
		List<Item> selectedList=new ArrayList<Item>();
		
		
		if(filterType==1){
			return pages;	//取全部题
		}
		for(Page page:	pages){
			for(Item item: page.getItems()){			
				item.setFilterShow(false);
				if(filterType==4){
					if (item.getExamProperty().getIsMark()==true) {
						selectedList.add(item);
						item.setFilterShow(true);
					}
				}else if(filterType==3){
					if (item.getExamProperty().getIsDone()==false) {
						selectedList.add(item);
						item.setFilterShow(true);
					}
				}else if(filterType==2){
					if (item.getExamProperty().getIsRight()==false) {
						selectedList.add(item);
						item.setFilterShow(true);
					}
				}							
			}
		}

		List<Page> newPages=viewControl.itemPaging(selectedList);
		
		return newPages;
	}
	
	/*
	 * 从历史记录中取出当时出的题
	 */
	@SuppressWarnings("unchecked")
	public List<Page> fetchHistoryItem(Integer historyTestStatusId, ViewControlProxy  viewControl){
		List<Page> pages=null;
		String hql="from HistoryTestStatus testStatus where testStatus.id="+historyTestStatusId;
		List<HistoryTestStatus> testStatuses=this.find(hql);
		HistoryTestStatus testStatus=null;
		if(testStatuses!=null&&testStatuses.size()>0){
			testStatus=testStatuses.get(0);
		}
		List<HistoryAnswerStatus> answerStatuses=testStatus.getHistoryAnswerStatuses();
		Set<Item> itemsSet=new HashSet<Item>();
		Map<String,String> answerMap=new HashMap<String,String>();
		Map<String,Integer> markMap=new HashMap<String,Integer>();
		//Map<String,Boolean> rightMap=new HashMap<String,Boolean>();
		Map<String,Float> starMap=new HashMap<String,Float>();
		String mapKey=null;
		for(HistoryAnswerStatus historyAnswer:answerStatuses){
			if(historyAnswer.getDataSource()==1){
			itemsSet.add(historyAnswer.getItem());
			mapKey=ExamUtil.getMapKey(historyAnswer.getItem(), historyAnswer.getSubItem());
			answerMap.put(mapKey, historyAnswer.getAnswer());
			Integer markInt=0;
			if(historyAnswer.getIsUnsureMarking()==true)markInt=1;
			markMap.put(mapKey, markInt);
			starMap.put(mapKey, historyAnswer.getStarGrade());
			}
		}
		List<Item> items=new ArrayList<Item>();
		items.addAll(itemsSet);
		//ExamUtil.sortList(items);
		//pages=ExamUtil.splitToPage(items);
		pages= viewControl.itemPaging(items);
		//TODO:各种显示VO..
		return pages;
	}
	
	
	/**
	 * 自动组卷抓取item列表
	 */
	private DynamicAssembledPaper getAssemblingItemListForKy(
			NodeInstance nodeInstance,
			PaperAssemblingPolicy paperAssemblingPolicy){
	
		/*获取节点实例*/
		//NodeInstance nodeInstance=this.get(NodeInstance.class, nodeInstanceId);
		/*获取组卷策略*/
	//	PaperAssemblingPolicy paperAssemblingPolicy=this.get(PaperAssemblingPolicy.class, nodeInstance.getNode().getId());
		String nodeIds=null;
		/*获取组卷策略组卷方式*/
		int paperAssemblingMode=paperAssemblingPolicy.getPaperAssemblingMode().intValue();
//		if(paperAssemblingMode>20)
//			paperAssemblingMode=paperAssemblingMode-10;
		String itemIds=null;
		//大于10的类型才考虑，训练过试题的范围了
		if(paperAssemblingMode>10){
			int tempMode=paperAssemblingMode;
			if(paperAssemblingMode==14)
				tempMode=11;
			//根据策略获取对应节点范围
			nodeIds=this.getNodeIdsForKy(nodeIds, this.getNodeGroup(nodeInstance.getNode(), tempMode-10).getId());
			Assert.notNull(nodeIds);
			//获取已做过题的范围
			itemIds=this.getItemIds(nodeIds, nodeInstance.getProcessInstance());
		}
		
		return this.getItemListForKy(itemIds, nodeInstance, paperAssemblingPolicy);
	}
	
	private DynamicAssembledPaper getAssemblingItemListForMpc(
			NodeInstance nodeInstance,
			PaperAssemblingPolicy paperAssemblingPolicy,
			Integer itemNum,
			List<Item> tempItems){
	
		/*获取节点实例*/
		//NodeInstance nodeInstance=this.get(NodeInstance.class, nodeInstanceId);
		/*获取组卷策略*/
	//	PaperAssemblingPolicy paperAssemblingPolicy=this.get(PaperAssemblingPolicy.class, nodeInstance.getNode().getId());
		String nodeIds=null;
		/*获取组卷策略组卷方式*/
		int paperAssemblingMode=paperAssemblingPolicy.getPaperAssemblingMode().intValue();
		if(paperAssemblingMode>20)
			paperAssemblingMode=paperAssemblingMode-10;
		String itemIds=null;
		//大于10的类型才考虑，训练过试题的范围了
		if(paperAssemblingMode>10){
			int tempMode=paperAssemblingMode;
			if(paperAssemblingMode==14)
				tempMode=11;
			//根据策略获取对应节点范围
			nodeIds=this.getNodeIdsForMpc(nodeIds, this.getNodeGroup(nodeInstance.getNode(), tempMode-10).getId());
			Assert.notNull(nodeIds);
			//获取已做过题的范围
			itemIds=this.getItemIds(nodeIds, nodeInstance.getProcessInstance());
		}
		return this.getItemListForMpc(itemIds, nodeInstance, paperAssemblingPolicy,itemNum,tempItems);
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
	 * 遍历该节点组下所有节点
	 * 数理化版本 --取训练、前测、后侧节点
	 */
	@SuppressWarnings("unchecked")
	private String getNodeIdsForMpc(String nodeIds,Long nodeId){
		List nodeList=this.getHibernateTemplate().find("from Node where nodeGroup.id=? ", nodeId);
		if(nodeList!=null&&nodeList.size()>0)
			for(int i=0;i<nodeList.size();i++){
				Node node=(Node)nodeList.get(i);
				if(node.getNodeType().equals(NodeType.GROUP))
					nodeIds=this.getNodeIdsForMpc(nodeIds,node.getId());
				//过滤所有评测和阶段测试节点，他们都不属于过滤范围内的节点
				else if(node.getNodeType().equals(NodeType.EVALUATE))
					//	||node.getNodeType().equals(NodeType.PHASETEST))
					continue;				
				else{
					if(nodeIds==null||nodeIds.equals(""))
						nodeIds=String.valueOf(node.getId());
					else
						nodeIds+=","+node.getId();
				}
			}
		return nodeIds;
	}
	

	/**
	 * 遍历该节点组下所有节点
	 * 考研版本 --取训练 单元测试节点
	 */
	@SuppressWarnings("unchecked")
	private String getNodeIdsForKy(String nodeIds,Long nodeId){
		List nodeList=this.getHibernateTemplate().find("from Node where nodeGroup.id=? ", nodeId);
		if(nodeList!=null&&nodeList.size()>0)
			for(int i=0;i<nodeList.size();i++){
				Node node=(Node)nodeList.get(i);
				if(node.getNodeType().equals(NodeType.GROUP))
					nodeIds=this.getNodeIdsForKy(nodeIds,node.getId());
				//过滤所有评测和阶段测试节点，他们都不属于过滤范围内的节点
				else if(node.getNodeType().equals(NodeType.EVALUATE)
						||node.getNodeType().equals(NodeType.PHASETEST))
					continue;				
				else{
					if(nodeIds==null||nodeIds.equals(""))
						nodeIds=String.valueOf(node.getId());
					else
						nodeIds+=","+node.getId();
				}
			}
		return nodeIds;
	}
	
	/**
	 * 获取node范围训练已经做过的题
	 */
	@SuppressWarnings("unchecked")
	private String getItemIds(String nodeIds,ProcessInstance processInstance){
		Assert.notNull(nodeIds);
		Assert.notNull(processInstance);
		List list=this.find(
				"select distinct a.item.id from CurrentAnswersStatus a"
				+" where a.asfNodeInstance.processInstance.id=? and a.asfNodeInstance.node.id in("
				+nodeIds+")",processInstance.getId());
		if(list==null||list.isEmpty())
			return null;
		StringBuffer outString=new StringBuffer();
		for(int i=0;i<list.size();i++){
			if(i>0)
				outString.append(",");
			outString.append(list.get(i).toString());
		}
		return outString.toString();
	}
	/**
	 * 根据组卷条件及策略生成试题for KY
	 */
	@SuppressWarnings("unchecked")
	private DynamicAssembledPaper getItemListForKy(			
			String itemIds,
			NodeInstance nodeInstance,
			PaperAssemblingPolicy paperAssemblingPolicy){
		
		//获取组卷条件列表
		List<PaperAssemblingRequirements> paperAssemblingRequirementsList=this.find(
				"from PaperAssemblingRequirements a where a.asfNode.id=?", nodeInstance.getNode().getId());
		Assert.notEmpty(paperAssemblingRequirementsList);
		List<Item> items=new ArrayList<Item>();
		int answeringTime=0;		
		int items_num=0;
		float totalScore=0;
		DynamicAssembledPaper dyPaper=new DynamicAssembledPaper();
		for(PaperAssemblingRequirements po:paperAssemblingRequirementsList){
			//List<Object> queryConditionList=new ArrayList<Object>();
			StringBuffer hql=new StringBuffer();
			hql.append("from Item where status >0");  
			if(po.getYear()!=null&&!po.getYear().equals("")){
				String[] year=po.getYear().split("-");
				if(year.length==2){
					if(!year[0].equals(""))
						hql.append(" and year>='"+year[0]+"'");
					if(!year[1].equals(""))
						hql.append(" and year<='"+year[1]+"'");
				}
			} 
			if(po.getValidityValue()!=null&&!po.getValidityValue().equals("")){
				String[] validityValue=po.getValidityValue().split("-");
				if(validityValue.length==2){
					if(!validityValue[0].equals(""))
						hql.append(" and validityValue>="+validityValue[0]);
					if(!validityValue[1].equals(""))
						hql.append(" and validityValue<="+validityValue[1]);
				}
			}
			if(po.getDifficultyValue()!=null&&!po.getDifficultyValue().equals("")){
				String[] difficultyValue=po.getDifficultyValue().split("-");
				if(difficultyValue.length==2){
					if(!difficultyValue[0].equals(""))
						hql.append(" and difficultyValue>="+difficultyValue[0]);
					if(!difficultyValue[1].equals(""))
						hql.append(" and difficultyValue<="+difficultyValue[1]);
				}
			}
			if(po.getItemTypeCode()!=null&&!po.getItemTypeCode().equals("")){			
				//hql.append(" and itemType.code in(?)");					
				//queryConditionList.add(po.getItemTypeCode());
				hql.append(" and itemType.code in('");
				hql.append(po.getItemTypeCode().replaceAll(",", "','"));
				hql.append("')");
			}
			
			if(po.getSource()!=null&&!po.getSource().equals(""))				
					hql.append(" and source in ("+po.getSource()+")");
			
			if(po.getOriginalPaperCode()!=null&&!po.getOriginalPaperCode().equals(""))			
				hql.append(" and originalPaperCode ='"+po.getOriginalPaperCode()+"'");
			
			if(po.getRegionCode()!=null&&!po.getRegionCode().equals(""))			
				hql.append(" and region.code ='"+po.getRegionCode()+"'");
			
			if(po.getSubjectCode()!=null&&!po.getSubjectCode().equals(""))			
				hql.append(" and subject.code ='"+po.getSubjectCode()+"'");
			if(po.getGradeCode()!=null&&!po.getGradeCode().equals(""))
				hql.append(" and '"+po.getGradeCode()+"' in elements(grades)");
			if(po.getKnowledgePointCode()!=null&&!po.getKnowledgePointCode().equals("")){
				//hql.append(" and '"+po.getKnowledgePointCode()+"'in elements(knowledgePoints)");
				String[] knowledgePoints=po.getKnowledgePointCode().split(",");
				if(knowledgePoints.length>0)
					hql.append(" and (");
				int i=0;
				for(String knowledgePoint:knowledgePoints){
					if(i>0)
						hql.append(" or");
					hql.append(" '"+knowledgePoint+"'in elements(knowledgePoints)");
					i++;
				}
				if(knowledgePoints.length>0)
					hql.append(" )");
			}
			if(itemIds!=null&&!itemIds.equals("")){
				int modle=paperAssemblingPolicy.getPaperAssemblingMode();
//				if(modle>20)
//					modle=modle-10;
				if(modle==14)
					hql.append(" and id in ("+itemIds+")");
				else if(modle==11||modle==12||modle==13)
					hql.append(" and id not in ("+itemIds+")");
			}
			/*课程版本*/
			if(po.getCourseVersions()!=null&&!po.getCourseVersions().equals("")){
				hql.append(" and courseVersion in (" + po.getCourseVersions() + ")");
			}
			List<Item> itemList=this.find(hql.toString());	
			
			//剔除重复题
			Map<Integer,Item> map=new HashMap();
			for(Item item:itemList)
				map.put(item.getId(), item);
			List<Item> newList=new ArrayList<Item>(map.values());
			Collections.shuffle(newList);
			int i=1;
			for(Item item:newList){
				//剔除重复题
				if(items.contains(item))
					continue;
				items.add(item);
				
				//String itemTypeCodeFiistStr=item.getItemType().getCode().substring(0,1);
				//2,4为一对多类型
				//if(itemTypeCodeFiistStr.equals("2")||itemTypeCodeFiistStr.equals("4")){
				if(item.getSubItems()!=null&&item.getSubItems().size()>0){
					answeringTime+=item.getAnsweringTime()+item.getReadingTime();		
					items_num+=item.getSubItems().size();					
				}else{
					answeringTime+=item.getAnsweringTime();
					items_num++;
				}
				totalScore+=item.getScore();
				if(po.getAmount()!=null){
					if(i==po.getAmount().intValue())//大题数量
						break;
					i++;
				}
			}
		}
		
		dyPaper.setItems(items);
		dyPaper.setAnsweringTime(answeringTime);
		dyPaper.setAssemblingTime(new Date());
		dyPaper.setTotalScore((int)totalScore);
		dyPaper.setItemsNum(items_num);
		dyPaper.setStatus(1);
		dyPaper.setAsfNodeInstance(nodeInstance);

		this.save(dyPaper);
		
		
		//验证如果初始化时组卷策略数据，与实际情况剔除或选出做过的数据不符，证明业务逻辑出问题
		/*	StringBuffer out=new StringBuffer();
		if(paperAssemblingPolicy.getItems_num()!=items_num){
			out.append("itemNum Error,组卷策略题数:"+paperAssemblingPolicy.getItems_num());
			out.append("实际题数:"+items_num+"。");
		}
		if(paperAssemblingPolicy.getAnsweringTime()!=answeringTime){
			out.append("answeringTime Error,组卷策略答题时间:"+paperAssemblingPolicy.getAnsweringTime());
			out.append("实际答题时间:"+answeringTime+"。");
		}
		if(paperAssemblingPolicy.getTotalScore()!=totalScore){
			out.append("totalScore Error,组卷策略答题总分:"+paperAssemblingPolicy.getTotalScore());
			out.append("实际答题总分:"+totalScore+"。");
		}
		//如果出现问题则提示反馈信息
		if(out.length()>0){
			try{
			this.sendMail("用户Id："+nodeInstance.getProcessInstance().getActor()
					+",节点实例id为："+nodeInstance.getId()+",节点定义id"
					+nodeInstance.getNode().getId()+","+out.toString());
			}catch(Exception e){
				e.printStackTrace();
			} 
		}*/
			
		return dyPaper;
	}
	/**
	 * 根据组卷条件及策略生成试题for MPC
	 */
	@SuppressWarnings("unchecked")
	private DynamicAssembledPaper getItemListForMpc(
			String itemIds,
			NodeInstance nodeInstance,
			PaperAssemblingPolicy paperAssemblingPolicy,
			Integer itemNum,
			List<Item> tempItems){
		
		//获取组卷条件列表
		List<PaperAssemblingRequirements> paperAssemblingRequirementsList=this.find(
				"from PaperAssemblingRequirements a where a.asfNode.id=?", nodeInstance.getNode().getId());
		Assert.notEmpty(paperAssemblingRequirementsList);
		List<Item> items=new ArrayList<Item>();
		int answeringTime=0;		
		int items_num=0;
		float totalScore=0;
		DynamicAssembledPaper dyPaper=new DynamicAssembledPaper();
		int j=1;
		for(PaperAssemblingRequirements po:paperAssemblingRequirementsList){
			StringBuffer hql=new StringBuffer();
		//	List<Object> queryConditionList=new ArrayList<Object>();
	 		hql.append("from Item where status > 0");  
			if(po.getYear()!=null&&!po.getYear().equals("")){
				String[] year=po.getYear().split("-");
				if(year.length==2){
					if(!year[0].equals(""))
						hql.append(" and year>='"+year[0]+"'");
					if(!year[1].equals(""))
						hql.append(" and year<='"+year[1]+"'");
				}
			} 
			if(po.getValidityValue()!=null&&!po.getValidityValue().equals("")){
				String[] validityValue=po.getValidityValue().split("-");
				if(validityValue.length==2){
					if(!validityValue[0].equals(""))
						hql.append(" and validityValue>="+validityValue[0]);
					if(!validityValue[1].equals(""))
						hql.append(" and validityValue<="+validityValue[1]);
				}
			}
			if(po.getDifficultyValue()!=null&&!po.getDifficultyValue().equals("")){
				String[] difficultyValue=po.getDifficultyValue().split("-");
				if(difficultyValue.length==2){
					if(!difficultyValue[0].equals(""))
						hql.append(" and difficultyValue>="+difficultyValue[0]);
					if(!difficultyValue[1].equals(""))
						hql.append(" and difficultyValue<="+difficultyValue[1]);
				}
			}
			if(po.getItemTypeCode()!=null&&!po.getItemTypeCode().equals("")){			
					//hql.append(" and itemType.code in(?)");					
					//queryConditionList.add(po.getItemTypeCode());
					hql.append(" and itemType.code in('");
					hql.append(po.getItemTypeCode().replaceAll(",", "','"));
					hql.append("')");
			}
			
			if(po.getSource()!=null&&!po.getSource().equals(""))				
					hql.append(" and source in ("+po.getSource()+")");
			
			if(po.getOriginalPaperCode()!=null&&!po.getOriginalPaperCode().equals(""))			
				hql.append(" and originalPaperCode ='"+po.getOriginalPaperCode()+"'");
			
			if(po.getRegionCode()!=null&&!po.getRegionCode().equals(""))			
				hql.append(" and region.code ='"+po.getRegionCode()+"'");
			
			if(po.getSubjectCode()!=null&&!po.getSubjectCode().equals(""))			
				hql.append(" and subject.code ='"+po.getSubjectCode()+"'");
			if(po.getGradeCode()!=null&&!po.getGradeCode().equals(""))
				hql.append(" and '"+po.getGradeCode()+"' in elements(grades)");
			if(po.getKnowledgePointCode()!=null&&!po.getKnowledgePointCode().equals("")){
				//hql.append(" and '"+po.getKnowledgePointCode()+"'in elements(knowledgePoints)");
				String[] knowledgePoints=po.getKnowledgePointCode().split(",");
				if(knowledgePoints.length>0)
					hql.append(" and (");
				int i=0;
				for(String knowledgePoint:knowledgePoints){
					if(i>0)
						hql.append(" or");
					hql.append(" '"+knowledgePoint+"'in elements(knowledgePoints)");
					i++;
				}
				if(knowledgePoints.length>0)
					hql.append(" )");
			}
			/*直观评价*/
			if(po.getOpinion()!=null&&!po.getOpinion().equals("")){
				String[] opinionValue=po.getOpinion().split("-");
				if(opinionValue.length==2){
					if(!opinionValue[0].equals(""))
						hql.append(" and difficultyValue>="+opinionValue[0]);
					if(!opinionValue[1].equals(""))
						hql.append(" and difficultyValue<="+opinionValue[1]);
				}
			}
			/*适用对象：文、理科*/
			if(po.getApplicableObject()!=null&&!po.getApplicableObject().equals("")){
				hql.append(" and applicableObject in (" + po.getApplicableObject() + ")");
			}
			/*课程版本*/
			if(po.getCourseVersions()!=null&&!po.getCourseVersions().equals("")){
				hql.append(" and courseVersion in (" + po.getCourseVersions() + ")");
			}
			/*复习轮次*/
			if(po.getReviewRound()!=null&&!po.getReviewRound().equals("")){
				hql.append(" and reviewRound in (" + po.getReviewRound() + ")");
			}
			if(itemIds!=null&&!itemIds.equals("")){
				int modle=paperAssemblingPolicy.getPaperAssemblingMode();
				if(modle>20)
					modle=modle-10;
				if(modle==14)
					hql.append(" and id in ("+itemIds+")");
				else if(modle==11||modle==12||modle==13)
					hql.append(" and id not in ("+itemIds+")");
			}
			List<Item> itemList=this.find(hql.toString());	
			if(itemList==null||itemList.size()==0)
				continue;
			//剔除重复题
			Map<Integer,Item> map=new HashMap();
			for(Item item:itemList)
				map.put(item.getId(), item);
			List<Item> newList=new ArrayList<Item>(map.values());
			Collections.shuffle(newList);
			int i=1;
			boolean tag=false;
			for(Item item:newList){
				if(items.contains(item))
					continue;
				if(tempItems!=null&&tempItems.contains(item))
					continue;
				items.add(item);
				if(item.getAnsweringTime()==null)
					item.setAnsweringTime(0);
				if(item.getSubItems()!=null&&item.getSubItems().size()>0){
					if(item.getReadingTime()==null)
						item.setReadingTime(0);
					answeringTime+=item.getAnsweringTime()+item.getReadingTime();
				}else
					answeringTime+=item.getAnsweringTime();
				//mpc出题策略中试题个数为大题数量
				items_num++;
				totalScore+=item.getScore();
				if(po.getAmount()!=null){
					if(itemNum==null){
						if(i==po.getAmount().intValue())
							break;
					}
					else
						if(j>=itemNum.intValue()){
							tag=true;
							break;
						}
					
					i++;
					j++;
				}
			}
			if(tag)
				break;
		}
		int tempAnsweringTime=0,tempTotalScore=0;
		if(tempItems!=null){
			items.addAll(tempItems);
			//把往次生成的动态试卷记录状态置成0
			/*List<DynamicAssembledPaper> dyPaperList=this.find(
					"from DynamicAssembledPaper where asfNodeInstance.id=? and status=1", nodeInstance.getId());
			for(DynamicAssembledPaper po:dyPaperList){
				po.setStatus(0);
				this.save(po);
			}*/
			
			for(Item item:tempItems){
				if(item.getAnsweringTime()==null)
					item.setAnsweringTime(0);
				if(item.getSubItems()!=null&&item.getSubItems().size()>0){
					if(item.getReadingTime()==null)
						item.setReadingTime(0);
					tempAnsweringTime+=item.getAnsweringTime()+item.getReadingTime();
				}else
					tempAnsweringTime+=item.getAnsweringTime();
				tempTotalScore+=item.getScore();
			}
		}
		if(items==null||items.size()==0)
			return null;
		//生成新的动态试卷记录
		dyPaper.setItems(items);
		dyPaper.setAnsweringTime(answeringTime+tempAnsweringTime);
		dyPaper.setAssemblingTime(new Date());
		dyPaper.setTotalScore((int)totalScore+tempTotalScore);
		dyPaper.setItemsNum(items.size());
		dyPaper.setStatus(1);
		dyPaper.setAsfNodeInstance(nodeInstance);
		
		this.save(dyPaper);
		
		return dyPaper;
	}
	
	/*
	 * For Ky版本
	 * 根据星级等标签标记 来过滤题目
	 * 0 0.5 1 2 3 4 5 星级 11未答 12错题 14正确 15疑问 -1 全部
	 * 
	 */
	public List<Item> filterItemsForFy(List<Item> items,ViewControl viewControl, Float scope){
		if(scope==-1) return items;
		boolean isWeaknessEnhance=viewControl.getIsWeaknessEnhance();
		//Map<String,String> answerMap=viewControl.getAnswerMap();
		Map<String,Integer> markMap=viewControl.getMarkMap();
		Map<String,Boolean> rightMap=viewControl.getRightMap();
		Map<String,Float> starMap=viewControl.getStarMap();
		List<Item> selectedList=new ArrayList<Item>();
		Set<Item> selectedSet=new HashSet<Item>();
		Map<String,Boolean> doneMap =viewControl.getDoneMap();
		String mapKey=null;
		for(Item item:items){
			if(item.getSubItems().size()>0){
				for(SubItem subItem:item.getSubItems()){
					mapKey=ExamUtil.getMapKey(item, subItem);
					subItem.setEnable(false);
					if(scope<=5){
						Float star=starMap.get(mapKey);
						if(isWeaknessEnhance){
							if(star.equals(scope)){
								selectedSet.add(item);
								subItem.setEnable(true);
							}
						}else{
							if(star>=scope){
								selectedSet.add(item);
								subItem.setEnable(true);
							}
						}
						
					}
					if(scope==11){
						/*String answerStr=answerMap.get(mapKey);
						if(answerStr==null||answerStr.trim().length()==0){
							selectedSet.add(item);
							subItem.setEnable(true);
						}*/
						boolean tag=doneMap.get(mapKey);//false 是未答
						if(!tag){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
					}
					if(scope==12){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==false){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}if(scope==13){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==false){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
					if(scope==14){
						Boolean rightFlag=rightMap.get(mapKey);
						if(rightFlag!=null&&rightFlag==true){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
					if(scope==15){
						Integer markInt=markMap.get(mapKey);
						if(markInt!=null&&markInt==1){
							selectedSet.add(item);
							subItem.setEnable(true);
						}
						
					}
				}
			}else{
				mapKey=ExamUtil.getMapKey(item, null);
				if(scope<=5){
					Float star=starMap.get(mapKey);
					if(isWeaknessEnhance){
						if(star.equals(scope)){
							selectedSet.add(item);
							
						}
					}else{
						if(star>=scope){
							selectedSet.add(item);
							
						}
					}
					
				}
				if(scope==11){
					boolean tag=doneMap.get(mapKey);//false 是未答
					if(!tag){
						selectedSet.add(item);
					}
				}
				if(scope==12){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				if(scope==13){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				if(scope==14){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==true){
						selectedSet.add(item);
					}
					
				}
				if(scope==15){
					Integer markInt=markMap.get(mapKey);
					if(markInt!=null&&markInt==1){
						selectedSet.add(item);
					}
					
				}
				
			}
		}
		if(selectedSet.size()>0){
			selectedList.addAll(selectedSet);
		}
		else{
			if(isWeaknessEnhance)
				return null;
			selectedList=items;
			for(Item item:selectedList){
				for(SubItem subItem:item.getSubItems()){
					subItem.setEnable(true);
				}
				
			}
		}
		return selectedList;
	}
	/*
	 * For Mpc版本
	 * 根据星级等标签标记 来过滤题目
	 * 0 0.5 1 2 3 4 5 星级 11未答 12错题 13-未答题de错题; 14正确 15疑问 -1 全部
	 * 
	 */
	public List<Item> filterItemsForMpc(List<Item> items,ViewControl viewControl, Float scope){
		if(scope==-1) return items;
		boolean isWeaknessEnhance=viewControl.getIsWeaknessEnhance();
		//Map<String,String> answerMap=viewControl.getAnswerMap();
		Map<String,Integer> markMap=viewControl.getMarkMap();
		Map<String,Boolean> rightMap=viewControl.getRightMap();
		Map<String,Float> starMap=viewControl.getStarMap();
		List<Item> selectedList=new ArrayList<Item>();
		Set<Item> selectedSet=new HashSet<Item>();
		Map<String,Boolean> doneMap =viewControl.getDoneMap();
		String mapKey=null;
		for(Item item:items){
				mapKey=ExamUtil.getMapKey(item, null);
				Float star=starMap.get(mapKey);
				//System.out.println(mapKey+","+star);
				if(star==null){
					if(!isWeaknessEnhance)
						selectedSet.add(item);
					continue;
				}
				if(scope<=5){
					if(isWeaknessEnhance){
						if(star.equals(scope)){
							selectedSet.add(item);
							//System.out.println(item.getCode()+","+item.getId()+","+star);
						}
					}else{
						if(star>=scope){
							selectedSet.add(item);
						}
					}
				}
				else if(scope==11){
					boolean tag=doneMap.get(mapKey);//false 是未答
					if(!tag){
						selectedSet.add(item);
					}
				}
				else if(scope==12){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				else if(scope==13){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==false){
						selectedSet.add(item);
					}
					
				}
				else if(scope==14){
					Boolean rightFlag=rightMap.get(mapKey);
					if(rightFlag!=null&&rightFlag==true){
						selectedSet.add(item);
					}
					
				}
				else if(scope==15){
					Integer markInt=markMap.get(mapKey);
					if(markInt!=null&&markInt==1){
						selectedSet.add(item);				
					}					
				}
		}
		if(selectedSet.size()>0){
			selectedList.addAll(selectedSet);
			return selectedList;
		}
		else
			return null;

		
	}
	/**
	 * 验证动态试卷没有使用过
	 * true 使用过 false 未使用过
	 */
	/*public boolean checkDynamicAssembledPaperisUsed(NodeInstance nodeInstance,DynamicAssembledPaper dyPaper){
		String hql="select i from Item i ,CurrentAnswersStatus c where i.id=c.item.id and c.asfNodeInstance.id=?";
		List<Item> currentItemAnswersList=this.find(hql, new Object[]{nodeInstance.getId()});	
		for(Item dyPaperItem:dyPaper.getItems())
			if(!currentItemAnswersList.contains(dyPaperItem))
				return false;
		
		return true;
	}*/
	
	/**
	 * 判断当前节点后测是否通过，如未通过把本机节点所有动态组卷记录状态更新为0
	 */
	@SuppressWarnings("unchecked")
	public void updateDynamicAssembledPaper(NodeInstance nodeInstance){
		Assert.notNull(nodeInstance);
		nodeInstance.getProcessInstance().getId();
		nodeInstance.getNode().getNodeGroup().getId();
		String hql="from DynamicAssembledPaper d where d.status=1 and d.asfNodeInstance.node.id in("
			+"select pn.id from PracticeNode pn where pn.nodeGroup.id=?) and d.asfNodeInstance.processInstance.id=?";
		List<DynamicAssembledPaper> poList=this.find(hql, nodeInstance.getNode().getNodeGroup().getId(),nodeInstance.getProcessInstance().getId());
		for(DynamicAssembledPaper po:poList){
			po.setStatus(0);
			this.save(po);
		}
	}
	
	/**
	 * 取得上次考试的题
	 */
	@SuppressWarnings("unchecked")
	public DynamicAssembledPaper getCurrentLastDynamicPaper(NodeInstance nodeInstance){
		String hql="select dyPaper from DynamicAssembledPaper dyPaper inner join dyPaper.asfNodeInstance nodeIns where dyPaper.status=0 and nodeIns.id="
			+nodeInstance.getId()
		+" order by dyPaper.id desc";
		List<DynamicAssembledPaper> dyPapers=this.find(hql);
		DynamicAssembledPaper paper=null;
		if(dyPapers!=null&&dyPapers.size()>0){
			paper=dyPapers.get(0);			
		}
		return paper;
		
	}
	
	/**
	 * 取得当前节点的知识点列表
	 */
	public void genKnowledgePoints(ViewControl viewControl){
		Map<KnowledgePoint,Float> kpScore = new HashMap<KnowledgePoint,Float>();
		
		List<Item> paperItems  = viewControl.getItems();
		List<Item> listItems = new ArrayList<Item>();
		for (Item item: paperItems) {
			Item tmpItem = (Item)this.get(Item.class, item.getId());				
			listItems.add(tmpItem);
		}
		
		for (Item item: listItems) {
			if (ExamUtil.hasSubItem(item)) {
				for(SubItem subItem:item.getSubItems()){
					Float subScore  = subItem.getScore();
					Float subKvalue = subScore/(float)subItem.getKnowledgePoints().size();
					
					for(KnowledgePoint kPoint: subItem.getKnowledgePoints()){
						Float f1=kpScore.get(kPoint);
						if(f1==null) f1=subKvalue;
						else  		 f1=f1+subKvalue;
						kpScore.put(kPoint, f1);
					}
				}
			} else {
				Float subKvalue=item.getScore()/(float)item.getKnowledgePoints().size();				
			
				for(KnowledgePoint kPoint:item.getKnowledgePoints()){
					Float f1=kpScore.get(kPoint);
					if(f1==null) f1=subKvalue;
					else		 f1=f1+subKvalue;
					kpScore.put(kPoint, f1);
				}				
			}
		}
		
		Set<KnowledgePoint> kPoints=kpScore.keySet();
		Set<EvaluatingAnswerStatus> eAnswerStatusSet = new HashSet<EvaluatingAnswerStatus>();
		EvaluatingAnswerStatus eAnswerStatus=null;
		
		for(KnowledgePoint kPoint: kPoints ){
			Float f1= kpScore.get(kPoint);
			
			eAnswerStatus=new EvaluatingAnswerStatus();			
			eAnswerStatus.setKnowledgePoint(kPoint);
			eAnswerStatus.setTotalScore(f1);	
			eAnswerStatusSet.add(eAnswerStatus);
		}
		
		viewControl.setExamBeginProperty(new ExamBeginProperty());
		viewControl.getExamBeginProperty().setEvaluatingAnswerStatus(eAnswerStatusSet);		
	}
}
