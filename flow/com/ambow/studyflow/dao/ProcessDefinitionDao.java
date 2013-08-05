package com.ambow.studyflow.dao;

import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.ProcessDefinition;

public class ProcessDefinitionDao extends HibernateGenericDao{
	public ProcessDefinition findById(long id)
	{
		return this.get(ProcessDefinition.class, id);
	}
	
	public List<ProcessDefinition> getProcessDefinitionByCategoryId(
			long categoryId) {
		String hql="from ProcessDefinition pd where pd.categoryId=?";
		return this.find(hql, categoryId);
	}

}


