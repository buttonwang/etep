package com.ambow.trainingengine.policy.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.core.dao.support.Page;
import com.ambow.studyflow.domain.Node;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.policy.domain.PaperAssemblingPolicy;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;

public class PaperAssemblingRequirementsService extends HibernateGenericDao{
	Logger logger = Logger.getLogger(PaperAssemblingRequirementsService.class);
	public Page listByPage(int pageNo) {
		return this.pagedQuery("from PaperAssemblingRequirements",  pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public List listByNodeId(Long nodeId) {
		return find("from PaperAssemblingRequirements as p where p.asfNode.id=?",nodeId);
	}
	/**
	 * 删除标识为 节点 id 为传入id 的对象 
	 * @param id
	 */
	public List deleteAllByNodeId(Integer id){
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PaperAssemblingRequirements where asfNode.id="+id);
		}catch(Exception e){
			logger.error("［PaperAssemblingRequirementsService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	/**
	 * 删除标识为 id的对象 
	 * @param id
	 */
	public List delete(Integer id) {
		List errorList = new ArrayList(0);
		try{
			//this.excuteHql("delete from PaperAssemblingRequirements where id="+id);
			PaperAssemblingRequirements paperAssemblingRequirements=this.get(PaperAssemblingRequirements.class, id);
			this.remove(paperAssemblingRequirements);
			updatePaperAssemblingPolicy(paperAssemblingRequirements.getAsfNode().getId());
		}catch(Exception e){
			logger.error("［PaperAssemblingRequirementsService:delete(Long id)］删除时出现异常"+id+"  "+e.toString() );
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	public List deleteBatch(String ids) {
		List errorList = new ArrayList(0);
		try{
			this.excuteHql("delete from PaperAssemblingRequirements where id in (" + ids + ")");
		}catch(Exception e){
			logger.error("［PaperAssemblingRequirementsService:deleteBatch(String ids)］批量删除时出现异常"+ids+"  "+e.toString());
			errorList.add("批量删除时出现异常");
		}
		return errorList;
	}
	
	/**
	 * 向系统中添加 新PaperAssemblingRequirements 对象
	 * @param paperAssemblingRequirements
	 * @return 添加过程中出现的错误。 如果错误列表大小为零表示在添加过程中没有出现错误，即添加成功 
	 */
	public List add(PaperAssemblingRequirements paperAssemblingRequirements){
		List errorList = new ArrayList(0);
		try{
			Node node = get(Node.class,paperAssemblingRequirements.getAsfNode().getId());
			paperAssemblingRequirements.setAsfNode(node);
			initRangeValue(paperAssemblingRequirements);
			save(paperAssemblingRequirements);
		}catch(Exception e){
			String errorMsg = "［PaperAssemblingRequirementsService:add(PaperAssemblingRequirements paperAssemblingRequirements)］"+paperAssemblingRequirements+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		//try{
			updatePaperAssemblingPolicy(paperAssemblingRequirements.getAsfNode().getId());
		//}catch(Exception e){
			
		//}
		return errorList;
	}
	
	/**
	 * 取除所有范围为空的值
	 * @param paperAssemblingRequirements
	 */
	public void initRangeValue(PaperAssemblingRequirements paperAssemblingRequirements){
		if(paperAssemblingRequirements!=null){
			String difficultyValue = paperAssemblingRequirements.getDifficultyValue();
			if(difficultyValue!=null&&difficultyValue.trim().equals("-")){
				paperAssemblingRequirements.setDifficultyValue("");
			}
			String validityValue = paperAssemblingRequirements.getValidityValue() ;
			if(validityValue!=null&&validityValue.trim().equals("-")){
				paperAssemblingRequirements.setValidityValue("");
			}
			String year = paperAssemblingRequirements.getYear();
			if(year!=null&&year.trim().equals("-")){
				paperAssemblingRequirements.setYear("");
			}
		}
	}
	/**
	 * 用于更新 PaperAssemblingRequirements 对象，默认与add行为相似
	 * @param paperAssemblingRequirements
	 * @return 更新 PaperAssemblingRequirements 对象过程中出现的错误。如果错误列表大小为零表示在更新过程中没有出现错误，即更新成功 
	 */
	public List update(PaperAssemblingRequirements paperAssemblingRequirements){
		List errorList = new ArrayList(0);
		try{
			//update PaperAssemblingRequirements前的(如果有子对象)对其子对象进行相关处理
			initRangeValue(paperAssemblingRequirements);
			save(paperAssemblingRequirements);
		}catch(Exception e){
			String errorMsg = "［PaperAssemblingRequirementsService:update(PaperAssemblingRequirements paperAssemblingRequirements)］"+paperAssemblingRequirements+" 保存对象时出现异常!"+e.toString();
			errorList.add("数据保存时出现异常");
			logger.error(errorMsg);
		}
		updatePaperAssemblingPolicy(paperAssemblingRequirements.getAsfNode().getId());
		return errorList;
	}
	
	public Map editViewMap(Integer id){
		Map map = new HashMap();
		map.put("PaperAssemblingRequirements", get(PaperAssemblingRequirements.class, id));
		
		 
		return map;
	}
	
	public Map showViewMap(Integer id){
		return editViewMap(id);
	}

	public List getAll( ){
		return super.getAll(PaperAssemblingRequirements.class) ;
	}
	
	public PaperAssemblingRequirements get(long id){
		return super.get(PaperAssemblingRequirements.class,id) ;
	}
	/**
	 * 修改组卷条件，更新组卷
	 */
	@SuppressWarnings("unchecked")
	public void updatePaperAssemblingPolicy(long nodeId){
		/*获取组卷策略*/
		PaperAssemblingPolicy paperAssemblingPolicy=this.get(PaperAssemblingPolicy.class, nodeId);
		if(paperAssemblingPolicy==null||paperAssemblingPolicy.getPaperAssemblingMode() ==null )
			return; 
		//获取paperAssemblingRequirements列表
		List<PaperAssemblingRequirements> list=this.find("from PaperAssemblingRequirements where asfNode.id=?", nodeId);
		if(list.isEmpty())
			return;
		//Assert.notEmpty(list);
		int answeringTime=0;		
		int items_num=0;
		int big_items_num=0;
		float totalScore=0;
		List<Item> items=new ArrayList<Item>();
		for(PaperAssemblingRequirements po:list){ 
			Map<Integer,Item> map=new HashMap();
			StringBuffer hql=new StringBuffer();
			hql.append("from Item where status > -1");			
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
		//	if(po.getItemTypeCode()!=null&&!po.getItemTypeCode().equals(""))			
		//			hql.append(" and itemType.code ='"+po.getItemTypeCode()+"'");
			
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
					hql.append(" '"+knowledgePoint+"' in elements(knowledgePoints)");
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
			List<Item> itemList=this.find(hql.toString());	
			//剔除重复题 
			for(Item item:itemList){
				map.put(item.getId(), item);
			//	System.out.println("itemId>>>>"+item.getId());
			}
			//System.out.println("hql>>>>"+hql.toString());
			List<Item> newList=new ArrayList<Item>(map.values());
			
			Collections.shuffle(newList);
			int i=1;
			for(Item item:newList){
				if(items.contains(item))
					continue;
				items.add(item);
				//System.out.println("itemId>>>>"+item.getId());
				//String itemTypeCodeFiistStr=item.getItemType().getCode().substring(0,1);
				//2,4为一对多类型
				big_items_num++;
				//System.out.println("i>>>>"+i);
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
				}
				i++;
			}
		}
		paperAssemblingPolicy.setAnsweringTime(answeringTime);
		paperAssemblingPolicy.setTotalScore((int)totalScore);
		paperAssemblingPolicy.setItems_num(items_num);//包含小题数量
		paperAssemblingPolicy.setBig_items_num(big_items_num);//大题数量
		this.save(paperAssemblingPolicy);
	}
}
