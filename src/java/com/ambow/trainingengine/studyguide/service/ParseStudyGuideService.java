package com.ambow.trainingengine.studyguide.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;

/*
 * ParseStudyGuideService.java
 * 
 * Created on 2009-7-30 下午03:47:25
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */

public class ParseStudyGuideService {
	
	private HibernateGenericDao genService;
	private String resourcePath;

	public void saveAll(List<StudyGuide> studyGuides) {
		genService.saveOrUpdateAll(studyGuides);
	}
	
	public StudyGuide parse(String fileName) throws IOException {
		StudyGuide studyGuide = new StudyGuide();
		
		String content =  parseContent(read(fileName));
		String importFile = fileName.substring(fileName.lastIndexOf("\\")+1);
		
		studyGuide.setContent(content);
		studyGuide.setCode(importFile.replace(".htm", ""));
		studyGuide.setImportFile(importFile);
		studyGuide.setCreatedTime(new Date());
		return studyGuide;
	}
	
	public String parseContent(String htmlString) {
		String r = htmlString.replaceAll("^.*<body[^>]*>", "");
		r = r.replace("</body>", "").replace("</html>", "");
		r = r.replaceAll("(src=\")(\\S*.files)", "$1" + resourcePath + "/" + "$2");
		return r;
	}
	
	private String read(String fileName) throws IOException {
		FileInputStream fileIn = new FileInputStream(fileName);;
		StringBuffer sb = new StringBuffer();	
		
		BufferedReader in = new BufferedReader(new InputStreamReader(fileIn, "GBK"));
		String s;
		while ((s = in.readLine()) != null) {
			sb.append(s + " ");
		}
		
		in.close();
		fileIn.close();
		return sb.toString();
	}
	
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

}
