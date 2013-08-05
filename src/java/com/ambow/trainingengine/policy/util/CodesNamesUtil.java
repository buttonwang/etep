package com.ambow.trainingengine.policy.util;

import java.util.ArrayList;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.policy.domain.AssemblingPaperReqTemplate;
import com.ambow.trainingengine.policy.domain.PaperAssemblingRequirements;

public class CodesNamesUtil {
	
	public static PaperAssemblingRequirements initPaperAssemblingRequirements(PaperAssemblingRequirements paperAssemblingRequirements,HibernateGenericDao dao){
		if(paperAssemblingRequirements!=null&&paperAssemblingRequirements.getKnowledgePointCode()!=null&&!"".equals(paperAssemblingRequirements.getKnowledgePointCode().trim())){
			paperAssemblingRequirements.setKnowledgePointName(getPAR_KnowledgePointNames(dao,paperAssemblingRequirements.getKnowledgePointCode()));
		};
		if(paperAssemblingRequirements!=null&&paperAssemblingRequirements.getItemTypeCode()!=null&&!"".equals(paperAssemblingRequirements.getItemTypeCode().trim())){
			paperAssemblingRequirements.setItemTypeName( getPAR_ItemTypeNames(dao,paperAssemblingRequirements.getItemTypeCode()));
		};
		return paperAssemblingRequirements;
	}
	
	public static AssemblingPaperReqTemplate initAssemblingPaperReqTemplate(AssemblingPaperReqTemplate assemblingPaperReqTemplate,HibernateGenericDao dao){
		if(assemblingPaperReqTemplate!=null&&assemblingPaperReqTemplate.getKnowledgePointCode()!=null&&!"".equals(assemblingPaperReqTemplate.getKnowledgePointCode().trim())){
			assemblingPaperReqTemplate.setKnowledgePointName(getPAR_KnowledgePointNames(dao,assemblingPaperReqTemplate.getKnowledgePointCode()));
		};
		if(assemblingPaperReqTemplate!=null&&assemblingPaperReqTemplate.getItemTypeCode()!=null&&!"".equals(assemblingPaperReqTemplate.getItemTypeCode().trim())){
			assemblingPaperReqTemplate.setItemTypeName( getPAR_ItemTypeNames(dao,assemblingPaperReqTemplate.getItemTypeCode()));
		};
		return assemblingPaperReqTemplate;
	}
	
	/*通过 codes 获取 KnowledgePointNames*/
	public static String getPAR_KnowledgePointNames(HibernateGenericDao dao,String knowledgePointCodes ){
		String str = "";
		if(knowledgePointCodes!=null){
			String hqlParam = getHqlParam(knowledgePointCodes);
			if(!"".equals(hqlParam.trim())){
				List<KnowledgePoint> knowledgePointLst = dao.find("from KnowledgePoint where code in ("+hqlParam+")");
				int tempI = 0;
				if(knowledgePointLst!=null&&knowledgePointLst.size()>0){
					for (KnowledgePoint knowledgePoint : knowledgePointLst) {
						if(++tempI>1){
							str+=",";
						}
						str+=knowledgePoint.getName();
					}
				} 
			}
		}
		return str ;
	}
	
	public static String getPAR_ItemTypeNames(HibernateGenericDao dao,String itemTypeCodes ){
		String str = "";
		if(itemTypeCodes!=null){
			String hqlParam = getHqlParam(itemTypeCodes);
			if(!"".equals(hqlParam.trim())){
				List<ItemType> itemTypeLst = dao.find("from ItemType where code in ("+hqlParam+")");
				int tempI = 0;
				if(itemTypeLst!=null&&itemTypeLst.size()>0){
					for (ItemType itemType : itemTypeLst) {
						if(++tempI>1){
							str+=",";
						}
						str+=itemType.getName();
					}
				} 
			}
		}
		return str ;
	}
	
	private static String getHqlParam(String codesStr) {
		String itemTypeCodeArr[] = codesStr.trim().split(",");
		List<String> itemTypeCodeLst = new ArrayList<String>();
		for (int i = 0; i < itemTypeCodeArr.length; i++) {
			if(!"".equals( itemTypeCodeArr[i].trim())){
				itemTypeCodeLst.add(itemTypeCodeArr[i]);
			}
		}
		String hqlParam = "";
		int tempH = 0;
		for (String string : itemTypeCodeLst) {
			if(++tempH>1){
				hqlParam+=",";
			}
			hqlParam+="'"+string+"'";
		}
		return hqlParam;
	}
}
