package com.ambow.trainingengine.bug.service;

import junit.framework.TestCase;

import com.ambow.trainingengine.bug.service.BugService;
 

public class BugServiceTest extends TestCase{

	public void test_getAnalysisAtLarge(){
		String aOrder      ="ABCD";
		String randomOrder ="DBAC";
		
		String clientShowABCD =  "";//"CBDA";
		
		String analysisStr ="${A｝is a ,$｛B}is b ,｛C｝ is c ,{D｝ is d ";
		
		String clientShowAnalysisStr = BugService.getAnalysisAtLarge( randomOrder,  analysisStr);
		
		assertEquals("${C}is a ,${B}is b ,{D} is c ,{A} is d ".equals(clientShowAnalysisStr), true) ;
		 
		System.out.println(clientShowAnalysisStr);
	}		 

	public void test_getUserAnswerFormWangWeiAnswerOrder(){
		String def_______________   ="ABCD";
		String wangWeiAnswerOrder   ="CBDA";
		String showToUserRealAnswer ="DBAC"; 
		
		String showAnswerBD = BugService.getUserAnswerFormWangWeiAnswerOrder( wangWeiAnswerOrder,  "BD");
		String showAnswerAC = BugService.getUserAnswerFormWangWeiAnswerOrder( wangWeiAnswerOrder,  "AC");
		String showAnswerCD = BugService.getUserAnswerFormWangWeiAnswerOrder( wangWeiAnswerOrder,  "DC");
		String showAnswerAB = BugService.getUserAnswerFormWangWeiAnswerOrder( wangWeiAnswerOrder,  "AB");
		
		System.out.println(showAnswerBD);
		System.out.println(showAnswerAC);
		System.out.println(showAnswerCD);
		System.out.println(showAnswerAB);
		
		assertEquals("AB".equals(showAnswerBD)||"BA".equals(showAnswerBD),true);
		
		assertEquals("CD".equals(showAnswerAC)||"DC".equals(showAnswerAC),true);
		
		assertEquals("AD".equals(showAnswerCD)||"DA".equals(showAnswerCD),true);
		
		assertEquals("BC".equals(showAnswerAB)||"CB".equals(showAnswerAB),true);
		
		
	}
	
	 
	
	 
}
