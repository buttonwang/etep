package com.ambow.trainingengine.itembank.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.itembank.domain.Item;
/*
 * ItemExportService.java
 * 
 * Created on 2008-9-1 下午03:20:37
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
public class ExportItemService {
	private String rootDir;
	private String templetFile;
	private String exportFile;
	private HibernateGenericDao genService;
	

	@SuppressWarnings("unchecked")
	public void exportExcel() throws IOException {
		List<Item> items = genService.find("from Item I where I.subject.code='E' order by I.itemType.code, I.source, I.sourceFile");
		List<Item> items_12A = new ArrayList<Item>(0); 
		List<Item> items_13A = new ArrayList<Item>(0); 
		List<Item> items_15A = new ArrayList<Item>(0); 
		List<Item> items_25A = new ArrayList<Item>(0); 
		List<Item> items_26A = new ArrayList<Item>(0); 
		List<Item> items_34A = new ArrayList<Item>(0); 
		List<Item> items_34C = new ArrayList<Item>(0);
		List<Item> items_39A = new ArrayList<Item>(0); 
		List<Item> items_39B = new ArrayList<Item>(0); 
		List<Item> items_44A = new ArrayList<Item>(0); 
		List<Item> items_47A = new ArrayList<Item>(0); 
		List<Item> items_47B = new ArrayList<Item>(0); 
		List<Item> items_47C = new ArrayList<Item>(0); 
		List<Item> items_47D = new ArrayList<Item>(0); 
		
		for(Item item: items) {
			if(item.getItemType() == null){
				continue;
			}
			if ("Z1E12A".equals(item.getItemType().getCode())) items_12A.add(item);
			if ("Z1E13A".equals(item.getItemType().getCode())) items_13A.add(item);
			if ("Z1E15A".equals(item.getItemType().getCode())) items_15A.add(item);
			if ("Z1E25A".equals(item.getItemType().getCode())) items_25A.add(item);
			if ("Z1E26A".equals(item.getItemType().getCode())) items_26A.add(item);
			if ("Z1E34A".equals(item.getItemType().getCode())) items_34A.add(item);
			if ("Z1E34C".equals(item.getItemType().getCode())) items_34C.add(item);
			if ("Z1E39A".equals(item.getItemType().getCode())) items_39A.add(item);
			if ("Z1E39B".equals(item.getItemType().getCode())) items_39B.add(item);
			if ("Z1E44A".equals(item.getItemType().getCode())) items_44A.add(item);
			if ("Z1E47A".equals(item.getItemType().getCode())) items_47A.add(item);
			if ("Z1E47B".equals(item.getItemType().getCode())) items_47B.add(item);
			if ("Z1E47C".equals(item.getItemType().getCode())) items_47C.add(item);
			if ("Z1E47D".equals(item.getItemType().getCode())) items_47D.add(item);			
		}
		
		FileInputStream InputFS = new FileInputStream(rootDir+templetFile);
		FileOutputStream OutFS  = new FileOutputStream(rootDir+exportFile);
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(InputFS);
						
			writeAllSheet(wb, items);
			write12ASheet(wb, items_12A);
			write13ASheet(wb, items_13A);
			write15ASheet(wb, items_15A);
			write25ASheet(wb, items_25A);
			write26ASheet(wb, items_26A);
			write34ASheet(wb, items_34A);
			write34CSheet(wb, items_34C);
			write39ASheet(wb, items_39A);
			write39BSheet(wb, items_39B);
			write44ASheet(wb, items_44A);
			write47ASheet(wb, items_47A);
			write47BSheet(wb, items_47B);
			write47CSheet(wb, items_47C);
			write47DSheet(wb, items_47D);
			
			/** 写入Excel文件 */
			wb.write(OutFS);
						
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			InputFS.close();
			OutFS.close();
			InputFS = null;						
			OutFS = null;
		}
	}
	
	private void writeAllSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			if(item.getItemType() == null){
				continue;
			}
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getItemThemesName());
			row.getCell((short)12).setCellValue(item.getDifficultyValue());
			row.getCell((short)13).setCellValue(item.getAnsweringTime()/60);			
			row.getCell((short)14).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)15).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)16).setCellValue(item.getWordCount());
			row.getCell((short)17).setCellValue(item.getReadingTime()/60);
			row.getCell((short)18).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)19).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)20).setCellValue(item.getKeywords());
			row.getCell((short)21).setCellValue(item.getKeySentances());
			row.getCell((short)22).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			row.getCell((short)23).setCellValue(getFirstSentence(item.getSkills()));
			row.getCell((short)24).setCellValue(getFirstSentence(item.getDifficultSentance()));
			row.getCell((short)25).setCellValue(getFirstSentence(item.getScoringKeywords()));
			row.getCell((short)26).setCellValue(getFirstSentence(item.getWritingTemplate()));
			row.getCell((short)27).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write12ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("12A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
			row.getCell((short)12).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)13).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)14).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)15).setCellValue(item.getWordCount());
			row.getCell((short)16).setCellValue(item.getReadingTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)19).setCellValue(item.getKeywords());
			row.getCell((short)20).setCellValue(item.getKeySentances());
			//row.getCell((short)21).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)22).setCellValue(getFirstSentence(item.getSkills()));
			//row.getCell((short)23).setCellValue(getFirstSentence(item.getDifficultSentance()));
			//row.getCell((short)24).setCellValue(getFirstSentence(item.getScoringKeywords()));
			//row.getCell((short)25).setCellValue(getFirstSentence(item.getWritingTemplate()));
			//row.getCell((short)26).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write13ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("13A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
			row.getCell((short)12).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)13).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)14).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)15).setCellValue(item.getWordCount());
			row.getCell((short)16).setCellValue(item.getReadingTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)19).setCellValue(item.getKeywords());
			row.getCell((short)20).setCellValue(item.getKeySentances());
			//row.getCell((short)21).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)22).setCellValue(getFirstSentence(item.getSkills()));
			//row.getCell((short)23).setCellValue(getFirstSentence(item.getDifficultSentance()));
			//row.getCell((short)24).setCellValue(getFirstSentence(item.getScoringKeywords()));
			//row.getCell((short)25).setCellValue(getFirstSentence(item.getWritingTemplate()));
			//row.getCell((short)26).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write15ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("15A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
			row.getCell((short)12).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)13).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)14).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)15).setCellValue(item.getWordCount());
			row.getCell((short)16).setCellValue(item.getReadingTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)19).setCellValue(item.getKeywords());
			row.getCell((short)20).setCellValue(item.getKeySentances());
			//row.getCell((short)21).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)22).setCellValue(getFirstSentence(item.getSkills()));
			//row.getCell((short)23).setCellValue(getFirstSentence(item.getDifficultSentance()));
			//row.getCell((short)24).setCellValue(getFirstSentence(item.getScoringKeywords()));
			//row.getCell((short)25).setCellValue(getFirstSentence(item.getWritingTemplate()));
			//row.getCell((short)26).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write25ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("25A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getDifficultSentance()));
							
			i++;
		}
	}
	
	private void write26ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("26A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getSkills()));
							
			i++;
		}
	}
	
	private void write34ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("34A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
			row.getCell((short)12).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)13).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)14).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)15).setCellValue(item.getWordCount());
			row.getCell((short)16).setCellValue(item.getReadingTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)19).setCellValue(item.getKeywords());
			row.getCell((short)20).setCellValue(item.getKeySentances());
			//row.getCell((short)21).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)22).setCellValue(getFirstSentence(item.getSkills()));
			//row.getCell((short)23).setCellValue(getFirstSentence(item.getDifficultSentance()));
			//row.getCell((short)24).setCellValue(getFirstSentence(item.getScoringKeywords()));
			//row.getCell((short)25).setCellValue(getFirstSentence(item.getWritingTemplate()));
			//row.getCell((short)26).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write34CSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("34C");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
			row.getCell((short)12).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)13).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)14).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)15).setCellValue(item.getWordCount());
			row.getCell((short)16).setCellValue(item.getReadingTime()/60);
			row.getCell((short)17).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)18).setCellValue(getFirstSentence(item.getAnswerAnalysis()));
			row.getCell((short)19).setCellValue(item.getKeywords());
			row.getCell((short)20).setCellValue(item.getKeySentances());
			//row.getCell((short)21).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)22).setCellValue(getFirstSentence(item.getSkills()));
			//row.getCell((short)23).setCellValue(getFirstSentence(item.getDifficultSentance()));
			//row.getCell((short)24).setCellValue(getFirstSentence(item.getScoringKeywords()));
			//row.getCell((short)25).setCellValue(getFirstSentence(item.getWritingTemplate()));
			//row.getCell((short)26).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write39ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("39A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getItemThemesName());
			row.getCell((short)12).setCellValue(item.getDifficultyValue());
			row.getCell((short)13).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)14).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)15).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)16).setCellValue(item.getWordCount());
			row.getCell((short)17).setCellValue(item.getReadingTime()/60);
			row.getCell((short)18).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)19).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			row.getCell((short)20).setCellValue(item.getScoringKeywords());					
			row.getCell((short)21).setCellValue(getFirstSentence(item.getWritingTemplate()));
			row.getCell((short)22).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write39BSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("39B");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getKnowledgePointsName());
			row.getCell((short)11).setCellValue(item.getItemThemesName());
			row.getCell((short)12).setCellValue(item.getDifficultyValue());
			row.getCell((short)13).setCellValue(item.getAnsweringTime()/60);
			
			row.getCell((short)14).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)15).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)16).setCellValue(item.getWordCount());
			row.getCell((short)17).setCellValue(item.getReadingTime()/60);
			row.getCell((short)18).setCellValue(getFirstSentence(item.getCorrectAnswer()));
			row.getCell((short)19).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			row.getCell((short)20).setCellValue(item.getScoringKeywords());					
			row.getCell((short)21).setCellValue(getFirstSentence(item.getWritingTemplate()));
			row.getCell((short)22).setCellValue(getFirstSentence2(item.getScoringNorm()));
							
			i++;
		}
	}
	
	private void write44ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("44A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			//row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)18).setCellValue(getFirstSentence(item.getDifficultSentance()));
							
			i++;
		}
	}
	
	private void write47ASheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("47A");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			//row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)18).setCellValue(getFirstSentence(item.getSkills()));
							
			i++;
		}
	}
	
	private void write47BSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("47B");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			//row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)18).setCellValue(getFirstSentence(item.getSkills()));
							
			i++;
		}
	}
	
	private void write47CSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("47C");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			//row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)18).setCellValue(getFirstSentence(item.getSkills()));
							
			i++;
		}
	}
	
	private void write47DSheet(HSSFWorkbook wb, List<Item> items) {		
		HSSFSheet sheet = wb.getSheet("47D");
		HSSFRow row = null;		
		int i = 1;
		
		for (Item item: items) {
			row = sheet.getRow(i);
			if (row==null) row = sheet.createRow((short) i);
			for (int j = 1; j <= 28; j++) {					
				row.createCell((short) (j - 1));
			}
			
			row.getCell((short)0).setCellValue(item.getItemType().getCode());
			row.getCell((short)1).setCellValue(item.getItemType().getName());
			row.getCell((short)2).setCellValue(item.getCode());
			row.getCell((short)3).setCellValue(item.getYear());
			row.getCell((short)4).setCellValue(item.getSourceName());
			row.getCell((short)5).setCellValue(item.getSourceBook());
			row.getCell((short)6).setCellValue(item.getSourceFile());
			row.getCell((short)7).setCellValue(item.getOriginalPaperCode());
			row.getCell((short)8).setCellValue(item.getOriginalItemNum());
			row.getCell((short)9).setCellValue(item.getScore());
			row.getCell((short)10).setCellValue(item.getItemThemesName());
			row.getCell((short)11).setCellValue(item.getDifficultyValue());
						
			row.getCell((short)12).setCellValue(getFirstSentence2(item.getContent()));
			row.getCell((short)13).setCellValue(getFirstSentence(item.getContentTranslation()));
			row.getCell((short)14).setCellValue(item.getWordCount());
			row.getCell((short)15).setCellValue(item.getReadingTime()/60);
			row.getCell((short)16).setCellValue(item.getAnsweringTime()/60);
			//row.getCell((short)17).setCellValue(getFirstSentence(item.getThinkingAnalyse()));
			//row.getCell((short)18).setCellValue(getFirstSentence(item.getSkills()));
							
			i++;
		}
	}
	
	private String getFirstSentence(String field) {
		if (field==null) return "";
				
		int firstflag = field.indexOf("。");
		if (firstflag==-1) firstflag = field.indexOf(".");
		if (firstflag==-1) firstflag = field.length();
		else firstflag = firstflag + 1;
				
		return field.substring(0, firstflag);
	}
	
	private String getFirstSentence2(String field) {
		if (field==null) return "";
		
		field = field.replaceAll("(<(/||[^>])*>)", "").replace("&nbsp;", "").trim();
		
		return getFirstSentence(field);
	}
	
	public String getTempletFile() {
		return templetFile;
	}

	public void setTempletFile(String templetFile) {
		this.templetFile = templetFile;
	}

	public String getExportFile() {
		return exportFile;
	}

	public void setExportFile(String exportFile) {
		this.exportFile = exportFile;
	}

	public HibernateGenericDao getGenService() {
		return genService;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

}
