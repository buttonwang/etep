package com.ambow.trainingengine.studyguide.web.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.service.GradeService;
import com.ambow.trainingengine.itembank.service.SubjectService;
import com.ambow.trainingengine.studyguide.domain.StudyGuide;
import com.ambow.trainingengine.studyguide.domain.StudyGuideItem;
import com.ambow.trainingengine.studyguide.domain.StudyGuideParagraph;
import com.ambow.trainingengine.studyguide.service.ParseStudyGuideService;
import com.ambow.trainingengine.studyguide.service.StudyGuideService;
import com.ambow.trainingengine.systemsecurity.domain.SysUser;
import com.ambow.trainingengine.util.SessionDict;

/*
 * ImportStudyGuideAction.java
 * 
 * Created on 2009-7-30 下午03:39:45
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

public class ImportStudyGuideAction extends BaseAction {

	private static final long serialVersionUID = -5850802386387161185L;
	private static final Log logger = LogFactory.getLog(ImportStudyGuideAction.class);
	
	File file;
	String fileContentType;
	String fileFileName;
	String fullUri;
	String dirName;
	String importInfo = "";
	String batchimportInfo = "";
	String batchImportError = "";
	
	/** 读取的文件内容 */
	String readStr = "";
	
	private ParseStudyGuideService parseStudyGuideService;
	private StudyGuideService studyGuideService;
	private GradeService gradeService;
	private SubjectService subjectService;
	private ArrayList<StudyGuide> studyGuides = new ArrayList<StudyGuide>(0); 
	private String dirstrs = "";
	
	@Override
	public String execute() throws Exception {
		this.readFile(file);
		importInfo = fileFileName+" 导入成功！";
		this.setRequestAttribute("importInfo", importInfo);
		return INPUT;
	}
	
	/**
	 * 读取一个文件
	 * @param file
	 * @throws Exception
	 */
	public void readFile(File file) throws Exception{
		String read = null;
		BufferedReader br = new BufferedReader(new FileReader(file));
		while((read = br.readLine()) != null){
			readStr = readStr + read + "  ";
		}
		//转换img路径
		readStr = parseStudyGuideService.parseContent(readStr);
		this.replaceKeyWord();
		//从字符串中读取studeyGuide对象内容
		StudyGuide sg = this.readStudyGuide();
		List<StudyGuideParagraph> list = this.readStudyGuideParagraphList(sg);
		sg.setParagraphs(list);
		studyGuideService.save(sg);
		//清空
		readStr = "";
	}
	
	/**
	 * 替换关键字
	 */
	public void replaceKeyWord(){
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>知识讲解：", "知识讲解：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>段落：", "段落：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>习题题干：", "习题题干：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>习题详解：", "习题详解：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>习题答案：", "习题答案：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>例题题干：", "例题题干：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>例题详解：", "例题详解：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>例题答案：", "例题答案：");
		readStr = readStr.replaceAll("<p[^>]*><span[^>]*>/hr/", "/hr/");
		readStr = readStr.replaceAll("<span[^>]*>##", "##");
	}
	
	/**
	 * 读取一个知识讲解
	 * @return
	 */
	public StudyGuide readStudyGuide(){
		String code = fileFileName.substring(0,fileFileName.lastIndexOf("."));
		String beginStr = "知识讲解：";
		String endStr = "/hr/";
		int begin = readStr.indexOf(beginStr);
		int end = readStr.indexOf(endStr);
		String zhishi = readStr.substring(begin+beginStr.length(), end);
		//去掉余下的属于 知识讲解： 的标签
		zhishi = zhishi.replaceFirst("</span></p>", "");
		//先查询studyGuide，数据库中有就更新，没有就生成一个新对象
		StudyGuide sg = studyGuideService.getStudyGuideByCode(code);
		if(sg == null){
			sg = new StudyGuide();
		}else{
			List<StudyGuideParagraph> pList = sg.getParagraphs();
			for (StudyGuideParagraph paragraph : pList) {
				studyGuideService.deleteParagraph(paragraph.getId());
			}
		}
		sg.setCode(code);
		sg.setContent(zhishi);
		if(code != null && !"".equals(code)){
			String gradeCode = code.substring(0,2);
			sg.setGrade(gradeService.findUniqueBy("code", gradeCode));
			String subjectCode = code.substring(2, 3);
			sg.setSubject(subjectService.findUniqueBy("code", subjectCode));
		}
		//去掉知识讲解后的剩余字符串
		readStr = readStr.substring(end+endStr.length());
		return sg;
	}
	
	public List<StudyGuideParagraph> readStudyGuideParagraphList(StudyGuide sg){
		List<StudyGuideParagraph> list = new ArrayList<StudyGuideParagraph>();
		if(readStr != null && !"".equals(readStr.trim())){
			String[] duanluo = readStr.split("段落：");
			int j=0;
			for (int i = 0; i < duanluo.length; i++) {
				if(duanluo[i].indexOf("/hr/") != -1){
					j++;
					if(duanluo[i] != null && !"".equals(duanluo[i].trim())){
						StudyGuideParagraph sgp = this.readStudyGuideParagraph(duanluo[i].replaceFirst("</span>",""),j);
						sgp.setStudyGuide(sg);
						list.add(sgp);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	 * 读取一个段落
	 * @param str
	 * @param i
	 * @return
	 */
	public StudyGuideParagraph readStudyGuideParagraph(String str,Integer i){
		String endStr = "/hr/";
		int end = str.indexOf(endStr);
		String duanluo = str.substring(0,end);
		String[] duanluoStr = duanluo.trim().split("##</span>");
		StudyGuideParagraph sgp = new StudyGuideParagraph();
		if(duanluoStr.length>1){
			sgp.setTitle(duanluoStr[0]);
			sgp.setContent(duanluoStr[1]);
		}else{
			sgp.setContent(duanluoStr[0]);
		}
		sgp.setOrderNum(i);
		
		//剩余习题字符串
		String xitiStr = str.substring(end+endStr.length());
		if(xitiStr != null && xitiStr.indexOf("/hr/") != -1){
//			读取习题
			List<StudyGuideItem> list = this.readStudyGuideItemList(xitiStr,sgp);
			sgp.setItems(list);
		}
		return sgp;
	}
	
	public List<StudyGuideItem> readStudyGuideItemList(String xitiStr,StudyGuideParagraph sgp){
		List<StudyGuideItem> list = new ArrayList<StudyGuideItem>();
		String[] xiti = xitiStr.split("/hr/</span></p>");
		int j = 0;
		for (int i = 0; i < xiti.length; i++) {
			if(xiti[i].indexOf("习题题干") != -1 || xiti[i].indexOf("例题题干") != -1){
				j++;
				if(xiti[i] != null && !"".equals(xiti[i].trim())){
					StudyGuideItem sgi = this.readStudyGuideItem(xiti[i],j);
					sgi.setStudyGuideParagraph(sgp);
					list.add(sgi);
				}
			}
		}
		return list;
	}
	
	/**
	 * 读取一道习题或例题
	 * @param xiti
	 * @param i
	 * @return
	 */
	public StudyGuideItem readStudyGuideItem(String xiti,Integer i){
		Integer type = 2;//习题
		String beginStr = "习题题干：";
		String midStr = "习题详解：";
		String endStr = "习题答案：";
		int begin = xiti.indexOf(beginStr);
		int mid = xiti.indexOf(midStr);
		int end = xiti.indexOf(endStr);
		if(begin == -1){//说明是例题题干
			beginStr = "例题题干：";
			midStr = "例题详解：";
			endStr = "例题答案：";
			begin = xiti.indexOf(beginStr);
			mid = xiti.indexOf(midStr);
			end = xiti.indexOf(endStr);
			type = 1;//例题
		}
		
		String tigan = xiti.substring(begin+beginStr.length(), mid);
		String xiangjie = xiti.substring(mid+midStr.length(), end);
		String daan = xiti.substring(end+endStr.length());
		tigan = tigan.replaceFirst("</span>", "");
		xiangjie = xiangjie.replaceFirst("</span>", "");
		daan = daan.replaceFirst("</span>", "");
		StudyGuideItem sgi = new StudyGuideItem();
		sgi.setContent(tigan);
		sgi.setAnalys(xiangjie);
		sgi.setAnswer(daan);
		sgi.setOrderNum(i);
		sgi.setType(type);
		return sgi;
	}
	
	/** 从本地目录下批量导入 
	 * @throws IOException */
	public String dirImport() throws IOException {
		String[] files = serachFiles(dirName);
		SysUser adminUser = (SysUser)this.getSessionObj(SessionDict.AdminUser);
		int importnum = 0;
		for(String savefile: files) {
			try {				 
				this.readFile(new File(savefile));
				importnum++;
				logger.info(savefile);
			} catch (Exception e) {
				batchImportError += savefile + "导入失败！<br>	";
			}
		}
		
		parseStudyGuideService.saveAll(studyGuides);
		batchimportInfo = "共" + String.valueOf(importnum) + "个学习指导HTML文件导入成功！<br>";
		
		return INPUT;
	}
	
	/**显示上传页面*/
	public String show() {
		return INPUT;
	}
	
	private String[] serachFiles(String dir) {
        File root = new File(dir);
        File[] filesOrDirs = root.listFiles();

        for (int i = 0; i < filesOrDirs.length; i++) {
            if (filesOrDirs[i].isDirectory()) {
                serachFiles(filesOrDirs[i].getAbsolutePath());
            } else {
            	if (filesOrDirs[i].getName().contains("htm"))
            		dirstrs += filesOrDirs[i].getAbsolutePath() + ",";
            }
        }
        return dirstrs.split(",");
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String getAuthStr() {
		return null;
	}
	
	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFullUri() {
		return fullUri;
	}

	public void setFullUri(String fullUri) {
		this.fullUri = fullUri;
	}

	public String getImportInfo() {
		return importInfo;
	}

	public void setImportInfo(String importInfo) {
		this.importInfo = importInfo;
	}

	public String getDirName() {
		return dirName;
	}

	public void setDirName(String dirName) {
		this.dirName = dirName;
	}

	public String getBatchImportError() {
		return batchImportError;
	}

	public void setBatchImportError(String importError) {
		this.batchImportError = importError;
	}

	public String getBatchimportInfo() {
		return batchimportInfo;
	}

	public void setBatchimportInfo(String batchimportInfo) {
		this.batchimportInfo = batchimportInfo;
	}
	
	public ParseStudyGuideService getParseStudyGuideService() {
		return parseStudyGuideService;
	}

	public void setParseStudyGuideService(ParseStudyGuideService parseStudyGuideService) {
		this.parseStudyGuideService = parseStudyGuideService;
	}

	public ArrayList<StudyGuide> getStudyGuides() {
		return studyGuides;
	}

	public void setStudyGuides(ArrayList<StudyGuide> studyGuides) {
		this.studyGuides = studyGuides;
	}

	public StudyGuideService getStudyGuideService() {
		return studyGuideService;
	}

	public void setStudyGuideService(StudyGuideService studyGuideService) {
		this.studyGuideService = studyGuideService;
	}

	public GradeService getGradeService() {
		return gradeService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}
	
}