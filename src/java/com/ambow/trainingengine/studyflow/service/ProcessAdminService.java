package com.ambow.trainingengine.studyflow.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.domain.ProcessDefinition;

public class ProcessAdminService extends HibernateGenericDao {
	
	public List<String> addProcess(ProcessDefinition process ,String creator){
		List errorList = new ArrayList(0);
		process.setCreateTime(new Date());
		process.setCreator(creator);
		if(true){
			errorList.add("不能保存");
		}else{
			
		}
		save(process);
		return errorList;
	}
	public ProcessDefinition get(long id){
		return get(ProcessDefinition.class,id);
	}
	public List delete(ProcessDefinition processDefinition){
		List errorList = new ArrayList(0);
		ProcessDefinition process = get(ProcessDefinition.class, processDefinition.getId());
		if(process==null){
			errorList.add("要删除的对象不存在");
		}else if(canDelete(process)){
			
		}
		
		return errorList;		
	}
	
	public boolean canDelete(ProcessDefinition process){
		 
		return false;
	} 
	
}
