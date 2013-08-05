package com.ambow.trainingengine.itembank.service;

import java.util.List;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.web.data.SubItemVO;

public class SubItemService extends HibernateEntityDao<SubItem> {
	
	public SubItem subItemVOToSubItem(SubItemVO subItemVO,Item item){		
		SubItem subItem;
		if(subItemVO.getId()!=null){
			subItem=this.get(subItemVO.getId());
			subItem.getKnowledgePoints().clear();
		}else
			subItem=new SubItem();
		
		subItem.setItem(item);
		
		if(subItemVO.getAnswerGroup()!=null)
			subItem.setAnswerGroup(subItemVO.getAnswerGroup());
		if(subItemVO.getOrderNum()!=null)
			subItem.setOrderNum(subItemVO.getOrderNum());
		if(subItemVO.getOriginalSubItemNum()!=null)
			subItem.setOriginalSubItemNum(subItemVO.getOriginalSubItemNum());
		if(subItemVO.getContent()!=null)
			subItem.setContent(subItemVO.getContent());
		if(subItemVO.getContentTranslation()!=null)
			subItem.setContentTranslation(subItemVO.getContentTranslation());
		if(subItemVO.getCorrectAnswer()!=null)
			subItem.setCorrectAnswer(subItemVO.getCorrectAnswer());
		if(subItemVO.getAnswerAnalysis()!=null)
			subItem.setAnswerAnalysis(subItemVO.getAnswerAnalysis());
		if(subItemVO.getRelatedKeyPoints()!=null)
			subItem.setRelatedKeyPoints(subItemVO.getRelatedKeyPoints());
		if(subItemVO.getRelatedArticle()!=null)
			subItem.setRelatedArticle(subItemVO.getRelatedArticle());
		if(subItemVO.getKeyAnswerWords()!=null)
			subItem.setKeyAnswerWords(subItemVO.getKeyAnswerWords());
		if(subItemVO.getKeySentanceAnalysis()!=null)
			subItem.setKeySentanceAnalysis(subItemVO.getKeySentanceAnalysis());
		if(subItemVO.getKeySentances()!=null)
			subItem.setKeySentances(subItemVO.getKeySentances());
		if(subItemVO.getDifficultyValue()!=null)
			subItem.setDifficultyValue(subItemVO.getDifficultyValue());
		if(subItemVO.getKeyWords()!=null)
			subItem.setKeyWords(subItemVO.getKeyWords());
		
		//新增数理化
		if(subItemVO.getHint()!=null)
			subItem.setHint(subItemVO.getHint());
		
		if(subItemVO.getSkills()!=null)
			subItem.setSkills(subItemVO.getSkills());
		
		if(subItemVO.getAnswerPrototype()!=null)
			subItem.setAnswerPrototype(subItemVO.getAnswerPrototype());
		if(subItemVO.getAnalysisAtLarge1()!=null)
			subItem.setAnalysisAtLarge1(subItemVO.getAnalysisAtLarge1());
		if(subItemVO.getAnalysisAtLarge2()!=null)
			subItem.setAnalysisAtLarge2(subItemVO.getAnalysisAtLarge2());
		if(subItemVO.getAnalysisAtLarge3()!=null)
			subItem.setAnalysisAtLarge3(subItemVO.getAnalysisAtLarge3());
		if(subItemVO.getScore()!=null)
			subItem.setScore(subItemVO.getScore());
		if(subItemVO.getScore2()!=null)
			subItem.setScore2(subItemVO.getScore2());
		if(subItemVO.getAnsweringTime()!=null)
			subItem.setAnsweringTime(subItemVO.getAnsweringTime());
		if(subItemVO.getAnsweringTimeByMin() !=null)
			subItem.setAnsweringTimeByMin(subItemVO.getAnsweringTimeByMin());
		
		if(subItemVO.getKnowledgePointCodes()!=null&&!subItemVO.getKnowledgePointCodes().equals("")){
			String[] codes=subItemVO.getKnowledgePointCodes().split(",");
			for(int i=0;i<codes.length;i++){
				subItem.getKnowledgePoints().add(this.knowledgePointService.get(codes[i]));
			}
		}
		
		return subItem;
	}
	
	public List<ItemType> findItemType(String subjectCode ) {
		String hql = " select code,name,subject_code from item_type t  where t.subject_code =?";
		
		return this.findExecute(hql,subjectCode);
	}
	public KnowledgePointService knowledgePointService;
	
	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}
	
	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

}
