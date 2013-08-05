package com.ambow.trainingengine.itembank.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.util.Assert;

import com.ambow.core.configuration.Constants;
import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.core.dao.support.Page;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.util.MathUtil;
import com.ambow.trainingengine.itembank.web.data.ItemVO;
import com.ambow.trainingengine.util.ParamObject;


public class ItemService extends HibernateEntityDao<Item> {
	private SimpleJdbcTemplate jdbcTemplate ;

	public GradeService gradeService;
	public KnowledgePointService knowledgePointService;
	public ItemThemeService itemThemeService;
	
	public RegionService regionService;
	
	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void saveall(List<Item> items) {
		Assert.notNull(items);
		this.saveOrUpdateAll(items);
	}

	public Page list(int pageNo) {
		return this.pagedQuery("from Item I", pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public void deleteBatch(String ids) {
		this.excuteHql("delete from Item where id in (" + ids + ")");		
	}
	
	/** 多条件的查询 */
	public Page findByConditions(int pageNo, String constr) {
		return this.pagedQuery("from Item I where 1=1 " + constr, pageNo, Constants.DEFAULT_PAGE_SIZE);
	}
	
	public Page findByConditions(int pageNo, String hql, Object[] queryConditions) {
		return this.pagedQuery(hql, pageNo, Constants.DEFAULT_PAGE_SIZE,queryConditions);
	}
	
	/**
	 * 获取全部对象
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getAll(Class<T> entityClass) {
		List<T> list= getHibernateTemplate().loadAll(entityClass);
		if(list==null)
			list =new ArrayList<T>();
		return list;
	}
	
	/**
	 * 查找试题
	 * @return
	 */
	public List<Map<String,Object>> getList(String subjectType,String itemType){
		List<Map<String,Object>> list = null;
		String sql = "select id,code,content from `item` where subject_code=? and item_type_code =?";
		list = this.getJdbcTemplate().queryForList(sql, subjectType,itemType);
		return list;
	}
	
	/**
	 * 节点统计
	 * @return
	 */
	public List<Map<String,Object>> getStatNodeList(String pointCodes){
		List<Map<String,Object>> list = null;
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select count(b.id) as num,b.item_type_code,c.name,b.difficulty_value ");
		sqlBuff.append("from `knowledge_point_item_ref` a  ");
		sqlBuff.append("inner join item b on a.item_id=b.id ");
		sqlBuff.append("inner join item_type c on b.item_type_code =c.code ");
		sqlBuff.append("where a.knowledge_point_code =? ");
		sqlBuff.append("group by b.item_type_code,b.difficulty_value ");
		list = this.getJdbcTemplate().queryForList(sqlBuff.toString(),pointCodes);
		return list;
	}
	
	/**
	 * 节点统计
	 * @return
	 */
	public List<Map<String,Object>> getStatList(String pointCodes){
		List<Map<String,Object>> list = null;
		StringBuffer sqlBuff = new StringBuffer();
		sqlBuff.append("select a.knowledge_point_code as knowledge,count(b.id) as num,b.item_type_code,c.name ");
		sqlBuff.append("from `knowledge_point_item_ref` a  ");
		sqlBuff.append("inner join item b on a.item_id=b.id ");
		sqlBuff.append("inner join item_type c on b.item_type_code =c.code ");
		sqlBuff.append("where a.knowledge_point_code in ("+pointCodes+") ");
		sqlBuff.append("group by b.item_type_code,a.knowledge_point_code ");
		//System.out.println(sqlBuff.toString());
		list = this.getJdbcTemplate().queryForList(sqlBuff.toString());
		return list;
	}
	
	/**
	 * 将试题状态设置为已废弃
	 * @param ids
	 */
	public void delete(String ids){
		String sql = "update item set status=-1 where id in(?)";
		//String sql = "delete from item where id in("+ids+")";
		this.getJdbcTemplate().update(sql, ids);
		//this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 查找试题
	 * @param ids
	 * @return
	 */
	public List<Map<String,Object>> getList(String ids){
		List<Map<String,Object>> list = null;
		String sql = "select id,code,content from item where id in("+ids+")";
		list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	
	/**
	 * 查找试题
	 * @param ids
	 * @return
	 */
	public Map<String,Object> getItemMap(Integer id){
		List<Map<String,Object>> list = null;
		String sql = "select id,code,content from item where id ="+id+" and status!=-1";
		list = this.getJdbcTemplate().queryForList(sql);
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取试题列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Item> getItemList(String typeCode){
		String hsql = "from Item I where I.itemType.code =? order by I.itemType.code, I.source, I.sourceFile";
		List<Item> items = this.find(hsql, typeCode);
		return items;
	}
	
	public Item itemVOToItem(ItemVO itemVO,ParamObject p){
		Item item=this.get(itemVO.getId());
		if(itemVO.getAnswerGroup()!=null){
			item.setAnswerGroup(itemVO.getAnswerGroup());
		}
		if(itemVO.getCourseVersion()!=null){
			item.setCourseVersion(itemVO.getCourseVersion());
		}
		if(itemVO.getImportFile()!=null)
			item.setImportFile(itemVO.getImportFile());
		if(itemVO.getCode()!=null)
			item.setCode(itemVO.getCode());
		if(itemVO.getYear()!=null)
			item.setYear(itemVO.getYear());
		if(itemVO.getSource()!=null)
			item.setSource(itemVO.getSource());
		if(itemVO.getSourceBook()!=null)
			item.setSourceBook(itemVO.getSourceBook());
		if(itemVO.getSourceFile()!=null)
			item.setSourceFile(itemVO.getSourceFile());
		if(itemVO.getOriginalItemNum()!=null)
			item.setOriginalItemNum(itemVO.getOriginalItemNum());
		if(itemVO.getScore()!=null)
			item.setScore(itemVO.getScore());
		if(itemVO.getScore2()!=null);
			item.setScore2(itemVO.getScore2());
		if(itemVO.getDifficultyValue()!=null)
			item.setDifficultyValue(itemVO.getDifficultyValue());
		if(itemVO.getValidityValue()!=null)
			item.setValidityValue(itemVO.getValidityValue());
		if(itemVO.getAnsweringTime()!=null)
			item.setAnsweringTime(itemVO.getAnsweringTime());
		if(itemVO.getAnsweringTimeByMin() !=null)
			item.setAnsweringTimeByMin(itemVO.getAnsweringTimeByMin());
		if(itemVO.getContent()!=null)
			item.setContent(itemVO.getContent());
		if(itemVO.getContentTranslation()!=null)
			item.setContentTranslation(itemVO.getContentTranslation());
		if(itemVO.getWordCount()!=null)
			item.setWordCount(itemVO.getWordCount());
		if(itemVO.getReadingTime()!=null)
			item.setReadingTime(itemVO.getReadingTime());
		if(itemVO.getReadingTimeByMin()!=null)
			item.setReadingTimeByMin(itemVO.getReadingTimeByMin());
		if(itemVO.getCorrectAnswer()!=null)
			item.setCorrectAnswer(itemVO.getCorrectAnswer());
		if(itemVO.getAnswerAnalysis()!=null)
			item.setAnswerAnalysis(itemVO.getAnswerAnalysis());
		if(itemVO.getAnalysisAtLarge1()!=null)
			item.setAnalysisAtLarge1(itemVO.getAnalysisAtLarge1());
		if(itemVO.getAnalysisAtLarge2()!=null)
			item.setAnalysisAtLarge2(itemVO.getAnalysisAtLarge2());
		if(itemVO.getAnalysisAtLarge3()!=null)
			item.setAnalysisAtLarge3(itemVO.getAnalysisAtLarge3());
		if(itemVO.getThinkingAnalyse()!=null)
			item.setThinkingAnalyse(itemVO.getThinkingAnalyse());
		if(itemVO.getSkills()!=null)
			item.setSkills(itemVO.getSkills());
		if(itemVO.getDifficultSentance()!=null)
			item.setDifficultSentance(itemVO.getDifficultSentance());
		if(itemVO.getScoringKeywords()!=null)
			item.setScoringKeywords(itemVO.getScoringKeywords());
		if(itemVO.getWritingTemplate()!=null)
			item.setWritingTemplate(itemVO.getWritingTemplate());
		if(itemVO.getScoringNorm()!=null)
			item.setScoringNorm(itemVO.getScoringNorm());
		if(itemVO.getApplicableObject()!=null)
			item.setApplicableObject(itemVO.getApplicableObject());
		if(itemVO.getKeywords()!=null)
			item.setKeywords(itemVO.getKeywords());
		if(itemVO.getKeySentances()!=null)
			item.setKeySentances(itemVO.getKeySentances());
		if(itemVO.getCreater()!=null)
			item.setCreater(itemVO.getCreater());
		if(itemVO.getCreatedTime()!=null)
			item.setCreatedTime(itemVO.getCreatedTime());
		if(itemVO.getUpdater()!=null)
			item.setUpdater(itemVO.getUpdater());
		if(itemVO.getUpdatedTime()!=null)
			item.setUpdatedTime(itemVO.getUpdatedTime());
		if(itemVO.getVerifier()!=null)
			item.setVerifier(itemVO.getVerifier());
		if(itemVO.getVerifiedTime()!=null)
			item.setVerifiedTime(itemVO.getVerifiedTime());
		if(itemVO.getStatus()!=null)
			item.setStatus(itemVO.getStatus());
		if(itemVO.getOpinion()!=null)
			item.setOpinion(itemVO.getOpinion());
		if(itemVO.getItemValue()!=null)
			item.setItemValue(itemVO.getItemValue());		
		if(itemVO.getAbilityValue()!=null)
			item.setAbilityValue(itemVO.getAbilityValue());
		if(itemVO.getOriginalPaperCode()!=null)
			item.setOriginalPaperCode(itemVO.getOriginalPaperCode());
		if(itemVO.getAnswerPrototype()!=null)
			item.setAnswerPrototype(itemVO.getAnswerPrototype());
		if(itemVO.getReviewRound()!=null)
			item.setReviewRound(itemVO.getReviewRound());
		
		if(itemVO.getHint()!=null)
			item.setHint(itemVO.getHint());
		
		if(itemVO.getRegionCode()!=null){
			item.setRegion(this.regionService.get(itemVO.getRegionCode()));
		}
		
		if(itemVO.getGradeCodes()!=null) {
			String[] codes=itemVO.getGradeCodes().split(",");
			item.getGrades().clear();
			if (!itemVO.getGradeCodes().equals(""))
				for(int i=0;i<codes.length;i++){
					item.getGrades().add(this.gradeService.get(codes[i]));
				}
		}
		
		if(itemVO.getItemTypeCode()!=null){
			ItemType  itemType= this.regionService.get(ItemType.class,itemVO.getItemTypeCode());
			if(itemType!=null){
				item.setItemType(itemType);
			}
		}			 
		
		if(itemVO.getKnowledgePointCodes()!=null) {
			String[] codes=itemVO.getKnowledgePointCodes().split(",");
			item.getKnowledgePoints().clear();
			if (!itemVO.getKnowledgePointCodes().equals(""))
				for(int i=0;i<codes.length;i++){
					item.getKnowledgePoints().add(this.knowledgePointService.get(codes[i]));
				}
		}
		
		if(itemVO.getKnowledgePointCodes2()!=null) {
			String[] codes=itemVO.getKnowledgePointCodes2().split(",");
			item.getKnowledgePoints2().clear();
			if (!itemVO.getKnowledgePointCodes2().equals(""))
				for(int i=0;i<codes.length;i++){
					item.getKnowledgePoints2().add(this.knowledgePointService.get(codes[i]));
				}
		}
		
		if(itemVO.getItemThemeCodes()!=null) {
			String[] codes=itemVO.getItemThemeCodes().split(",");
			item.getItemThemes().clear();
			if (!itemVO.getItemThemeCodes().equals("")) 
				for(int i=0;i<codes.length;i++){
					item.getItemThemes().add(this.itemThemeService.get(codes[i]));
				}
		}
		
		if (itemVO.getAnswerOptionIds()!= null) {
			Set<AnswerOption > dbAnswerOptionSet=item.getAnswerOptions();
			Set<AnswerOption> answerOptionSet = new HashSet<AnswerOption>();
			if(dbAnswerOptionSet!=null){
				for(AnswerOption answerOption : dbAnswerOptionSet) {
					removeById(AnswerOption.class, answerOption.getId());
				}	 
			}
			item.setAnswerOptions(answerOptionSet);	
		}
		item.genItemCode();	
		//有id的数据answerOption
		if(itemVO.getAnswerOptionIds()!= null&&!itemVO.getAnswerOptionIds().equals("")) {
			String[] answerOptionIds_itemVO=itemVO.getAnswerOptionIds();
			String[] answerOptionCodes_itemVO = itemVO.getAnswerOptionCodes();
			String[] answerOptionContents_itemVO = itemVO.getAnswerOptionContents();
			String[] answerOptionTranslations_itemVO = itemVO.getAnswerOptionTranslations();
			for(int i=0;i<answerOptionCodes_itemVO.length;i++){
				AnswerOption answerOption = new AnswerOption();
				answerOption.setItem(item);
			 	answerOption.setCode(answerOptionCodes_itemVO[i]);
				answerOption.setContent(answerOptionContents_itemVO[i]);
				if(answerOptionTranslations_itemVO != null &&answerOptionTranslations_itemVO.length>i){
					answerOption.setTranslation(answerOptionTranslations_itemVO[i]);
				}
				save(answerOption);
				//answerOptionSet.add(answerOption);
			}
		}
		//新增的 answerOption
		if(p!=null && p.getStrArr("answerOptionCodes")!=null&&p.getStrArr("answerOptionContents")!=null){
			String [] answerOptionCodes=p.getStrArr("answerOptionCodes");
			String [] answerOptionContents=p.getStrArr("answerOptionContents");
			for (int i = 0; i < answerOptionCodes.length; i++) {
				String code = answerOptionCodes[i];
				AnswerOption answerOption = new AnswerOption();
				answerOption.setItem(item);
				answerOption.setCode(answerOptionCodes[i] );
				answerOption.setContent(answerOptionContents[i]);
				save(answerOption);
				//answerOptionSet.add(answerOption);
			}
		}
		return item;
	}
	
	public boolean addAnswer(ParamObject p){
		Integer itemId = p.getInteger ("itemId");
		Integer subItemId = p.getInteger ("subItemId");
		Integer addAnswerIndex = p.getInteger ("addAnswerIndex");
		String addAnswerContent=  p.get("addAnswerContent");
		if(addAnswerIndex!=null&&addAnswerContent!=null&&!"".equals(addAnswerContent.trim())){
			if(subItemId!=null){
				SubItem subItem = this.get(SubItem.class,subItemId);
				if(subItem!=null){
					String correctAnswerStr = getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, subItem.getCorrectAnswer());
					subItem.setCorrectAnswer(correctAnswerStr);
					save(subItem);
					return true;
				}
			}else if (itemId!=null){
				Item item = this.get(Item.class,itemId);
				if(item!=null){
					String correctAnswerStr = getAddCorrectAnswerStr(addAnswerIndex, addAnswerContent, item.getCorrectAnswer());
					item.setCorrectAnswer(correctAnswerStr);
					save(item);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 给用；分隔的字符串的 的第addAnswerIndex位置加入  '内容'
	 * @param addAnswerIndex ‘指定的第几个空’
	 * @param addAnswerContent '内容'
	 * @param correctAnswerStrOld 原始字符（用"；"分隔的字符串）
	 * @return
	 */
	public String getAddCorrectAnswerStr(Integer addAnswerIndex, String addAnswerContent,String correctAnswerStrOld) {
		if(addAnswerIndex>=0&&addAnswerContent!=null&&!"".equals(addAnswerContent.trim())){
			if(correctAnswerStrOld!=null){
				String correctAnswers[]=correctAnswerStrOld.split("；");
				//如果没有越界则加入 内容 
				if(addAnswerIndex<correctAnswers.length){
					//给指定位置的答案加入一个答案
					correctAnswers[addAnswerIndex]+= "$"+MathUtil.removeSpaceAndChinaSpace(addAnswerContent);
					String correctAnswerStr  = "";
					for (int i = 0; i < correctAnswers.length; i++){
						if(i>0&&i<correctAnswers.length){
							correctAnswerStr += "；";
						}
						correctAnswerStr += correctAnswers[i];
					}
					return correctAnswerStr;
				}
			}
			//如果原来的答案为空 是否处理
			/*else{
				return addAnswerContent;
			}*/
		}
		return correctAnswerStrOld;
	}
	
	public GradeService getGradeService() {
		return gradeService;
	}
	
	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public ItemThemeService getItemThemeService() {
		return itemThemeService;
	}

	public void setItemThemeService(ItemThemeService itemThemeService) {
		this.itemThemeService = itemThemeService;
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
}