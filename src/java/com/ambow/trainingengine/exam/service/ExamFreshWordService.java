/**
 * 
 */
package com.ambow.trainingengine.exam.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.studyflow.common.ProcessStatus;
import com.ambow.studyflow.domain.ProcessInstance;
import com.ambow.trainingengine.exam.util.ExamUtil;
import com.ambow.trainingengine.policy.domain.ProcessPolicy;
import com.ambow.trainingengine.wordbank.domain.ChineseMeaning;
import com.ambow.trainingengine.wordbank.domain.FreshWord;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.domain.WordExtension;
import com.ambow.trainingengine.wordbank.domain.WordType;
/*
 * ExamFreshWordService.java
 * 
 * Created on Sep 11, 2008 10:11:22 AM
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 单节点
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * wangwei 2008-10-23 findWord() 修改。按单词学级第次查询。
 */
public class ExamFreshWordService{
	
	private HibernateGenericDao genService;
	
	/*
	 * 
	 */
	public String addWord(String userId,String word){
		//System.out.println("Test ajax...");
		return null;
	}
	
	private Set<WordType> filterByGrade(WordBasic wBasic, String gradeCode) {
		Set<WordType> wordTypeList = new HashSet<WordType>(0);
		for(WordExtension wExtension: wBasic.getWordExtensions()){
			if (wExtension.getGrade().getCode().equalsIgnoreCase(gradeCode))
				wordTypeList.addAll(wExtension.getWordTypes());
		}
		return wordTypeList;
	}
		
	/*返回数组的第一位是状态位。
	 * 返回状态 0 未查到
	 * 返回状态1 查到了
	 */
	public String[] findWord(String word){
		word=ExamUtil.ToDBC(word).toLowerCase().trim();
		String hql="from WordBasic wBasic where wBasic.word like ?";
		List<WordBasic> wordList=genService.find(hql, word);
		WordBasic wordBasic=null;
		String wordStr=null;
		String express="";
		boolean flag=false;
		String wordExId="";
		
		if(wordList!=null&&wordList.size()>0){
			wordBasic=wordList.get(0);
			wordStr=wordBasic.getWord();
			String str="";
			
			// 按考研->六级->四级 将次查询，如果有就返回对应级别的单词。
			Set<WordType> 				wordTypeList = filterByGrade(wordBasic, "Z1");
			if (wordTypeList.size()==0) wordTypeList = filterByGrade(wordBasic, "Z6");  
			if (wordTypeList.size()==0) wordTypeList = filterByGrade(wordBasic, "Z5");
			
			if (wordTypeList.size()!=0) {
				flag = true;
				wordExId = wordTypeList.iterator().next().getWordExtension().getId().toString();
				for(WordType wType: wordTypeList){
					if(wType.getName()!=null)str=str+wType.getName();
					for(ChineseMeaning meaning:wType.getChineseMeanings()){
						if(str.trim().length()==0){
							str=str+meaning.getMeaning();
						}else{
							str=str+"<br>"+meaning.getMeaning();
						}
					}
				}
			}
			express=str;
		}
		System.out.println(express);
		if(flag){
			String[] results={"1",express,wordExId};
			return results;
		} else{
			String[] results={"0",word};				
			return results;
		}
		//return result;
	}
	
	/*
	 * TODO：嘿嘿~~2008-09-10
	 */
	public String[] insertToBank(String word,String wordExId,String processInstanceId){
		Integer id=Integer.parseInt(wordExId);
		Long insId=Long.parseLong(processInstanceId);
		//String[] result={};
		WordExtension wExtension=genService.get(WordExtension.class, id);
		//ProcessInstance processInstance=genService.get(ProcessInstance.class, insId);
		String hql="select count(*) from FreshWord fword where fword.wordExtension.id=? and processInstance=?";
		List<Long> list=genService.find(hql, id,insId);
		Long count=list.get(0);
		if(count==0){
			FreshWord freshWord=new FreshWord();
			freshWord.setProcessInstance(insId);
			freshWord.setWordExtension(wExtension);
			genService.save(freshWord);
			String[] result={"3",word};
			this.checkFreshWordPause(insId);//返回前检查流程状态。
			return result;
		}else{
			String[] result={"4",word,"单词已存在于你的个性化词库中！"};
			return result;
		}	
	}
	
	/*
	 * 每加入生词时，检查是否满足生词暂停的条件，如果满足则进行生词暂停
	 */
	private void checkFreshWordPause(Long insId){
		ProcessInstance processInstance=genService.get(ProcessInstance.class,insId);
		//如果已经生词训练或者流程暂停，返回
		if(processInstance.getProcessStatus()==ProcessStatus.SUSPEND_FRESHWORD||processInstance.getProcessStatus()==ProcessStatus.SUSPEND_PAUSE)return;
		String checkHql="select count(fword.id) from FreshWord fword where fword.processInstance=?";
		List<Long> list=genService.find(checkHql, insId);
		Long count=list.get(0);
		ProcessPolicy po=this.genService.get(ProcessPolicy.class, processInstance.getProcessDefinition().getId());
		if(count>=po.getFreshWordSum()){
		//if(count>=20){
			processInstance.setProcessStatus(ProcessStatus.SUSPEND_FRESHWORD);
			genService.save(processInstance);
		}
	}
	
	/*
	 * 将流程状态改回normal
	 */
	public void chStatusBack(Long insId){
		ProcessInstance processInstance=genService.get(ProcessInstance.class,insId);
		if(processInstance.getProcessStatus()==ProcessStatus.SUSPEND_FRESHWORD){
			processInstance.setProcessStatus(ProcessStatus.RUNNING);
			genService.save(processInstance);
		}
	}	
	
	/*
	 *检查是否符合进行生词训练的条件 
	 */
	public Boolean checkStatus(String userId){
		boolean flag=false;
		return flag;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}
	
}
