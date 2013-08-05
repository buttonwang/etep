package com.ambow.trainingengine.itembank.service;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.ambow.core.dao.HibernateEntityDao;
import com.ambow.trainingengine.itembank.domain.AnswerOption;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.SubItem;
import com.ambow.trainingengine.itembank.web.data.AnswerOptionVO;

public class AnswerOptionService extends HibernateEntityDao<AnswerOption> {
	private SimpleJdbcTemplate jdbcTemplate ;

	public AnswerOption answerOptionVOToAnswerOption(AnswerOptionVO vo,SubItem subItem,Item item){		
		AnswerOption po;
		if(vo.getId()!=null){
			po=this.get(vo.getId());			
		}else{
			po=new AnswerOption();
			if(item!=null)
				po.setItem(item);
			if(subItem!=null)
				po.setSubItem(subItem);
		}
				
		if(vo.getCode()!=null){
			po.setCode(vo.getCode());
		}
		if(vo.getContent()!=null){
			po.setContent(vo.getContent());
		}
		if(vo.getTranslation()!=null){
			po.setTranslation(vo.getTranslation());
		}
		
		return po;
	}

	/**
	 * 查找选择题答案
	 * @param itemId
	 * @return
	 */
	public List<Map<String,Object>> getContent(Integer itemId){
		List<Map<String,Object>> list = null;
		String sql = "select code,content from `answer_option` where item_id=? order by code";
		list = this.getJdbcTemplate().queryForList(sql, itemId);
		return list;
	}
	
	public SimpleJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(SimpleJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
