package com.ambow.trainingengine.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class AccessUrl {
	public String getPageContent(String strUrl) {
	  try{	   
	   URL url=new URL(strUrl);
	   BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
	   String s="";
	   StringBuffer sb=new StringBuffer("");
	   while((s=br.readLine())!=null)
	   {     
	    sb.append(s);    
	   }
	   br.close();
	   return sb.toString();
	  }
	  catch(Exception e){
		 // e.printStackTrace();
		  return null; 
	  }
	}
	public static void main(String[] a){
		AccessUrl accessUrl=new AccessUrl();
		String content=accessUrl.getPageContent("http://10.10.11.223:8080/swie/user/getUserArticleInfo.jhtml?userName=ambow-test12&sysAbbr=kaoyan&sysCode=777&sysUserType=aaa");
		//String content=accessUrl.getPageContent("http://10.10.11.223:8080/swie/user/getUserArticleInfo.jhtml?userName=gao&sysAbbr=kaoyan&sysCode=777&sysUserType=aaa");
		//System.out.println(content);
		
	}
}
