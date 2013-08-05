/**
 * 
 */
package com.ambow.trainingengine.itembank.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.ambow.trainingengine.itembank.domain.Item;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author yuanjunqi
 *
 */
public class ExtraExcel {
	WritableWorkbook wwb = null;
	
	/**
	 * 创建一个工作文本
	 * @param fileStr
	 * @throws IOException
	 */
	public void create(String fileStr) throws IOException{
		File file = new File(fileStr);
		wwb = Workbook.createWorkbook(file);
	}
	
	/**
	 * 创建一个片段
	 * @param list
	 * @param name
	 * @param num
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public void writeSheet(String[] strs,List<Item> list ,String name,int num) throws RowsExceededException, WriteException{
		WritableSheet ws = wwb.createSheet(name,num);
		
		for(int i=0;i<strs.length;i++){
			Label label = new Label(i,0,strs[i]);
			ws.addCell(label);
		}
		
		for(int i=1;i<=list.size();i++){
			Item item = list.get(i-1);
			Label label0 = new Label(0, i, item.getItemType().getCode());
			Label label1 = new Label(1, i, item.getItemType().getName());
			Label label2 = new Label(2, i, item.getCode());
			Label label3 = new Label(3, i, item.getYear());
			Label label4 = new Label(4, i, item.getSource());
			Label label5 = new Label(5, i, item.getSourceBook());
			Label label6 = new Label(6, i, item.getSourceFile());
			Label label7 = new Label(7, i, item.getOriginalPaperCode());
			Label label8 = new Label(8, i, item.getOriginalItemNum());
			Label label9 = new Label(9, i, item.getScore()+"");
			Label label10 = new Label(10, i, item.getKnowledgePointsName());
			Label label11 = new Label(11, i, item.getItemThemesName());
			Label label12 = new Label(12, i, item.getDifficultyValue()+"");
			Label label13 = new Label(13, i, item.getAnsweringTime()/60+"");
			Label label14 = new Label(14, i, getFirstSentence2(item.getContent()));
			Label label15 = new Label(15, i, getFirstSentence(item.getContentTranslation()));
			Label label16 = new Label(16, i, item.getWordCount()+"");
			Label label17 = new Label(17, i, item.getReadingTime()/60+"");
			Label label18 = new Label(18, i, getFirstSentence(item.getCorrectAnswer()));
			Label label19 = new Label(19, i, getFirstSentence(item.getAnswerAnalysis()));
			Label label20 = new Label(20, i, item.getKeywords());
			Label label21 = new Label(21, i, item.getKeySentances());
			Label label22 = new Label(22, i, getFirstSentence(item.getThinkingAnalyse()));
			Label label23 = new Label(23, i, getFirstSentence(item.getSkills()));
			Label label24 = new Label(24, i, getFirstSentence(item.getDifficultSentance()));
			Label label25 = new Label(25, i, getFirstSentence(item.getScoringKeywords()));
			Label label26 = new Label(26, i, getFirstSentence(item.getWritingTemplate()));
			Label label27 = new Label(27, i, getFirstSentence2(item.getScoringNorm()));
			
			ws.addCell(label0);
			ws.addCell(label1);
			ws.addCell(label2);
			ws.addCell(label3);
			ws.addCell(label4);
			ws.addCell(label5);
			ws.addCell(label6);
			ws.addCell(label7);
			ws.addCell(label8);
			ws.addCell(label9);
			ws.addCell(label10);
			ws.addCell(label11);
			ws.addCell(label12);
			ws.addCell(label13);
			ws.addCell(label14);
			ws.addCell(label15);
			ws.addCell(label16);
			ws.addCell(label17);
			ws.addCell(label18);
			ws.addCell(label19);
			ws.addCell(label20);
			ws.addCell(label21);
			ws.addCell(label22);
			ws.addCell(label23);
			ws.addCell(label24);
			ws.addCell(label25);
			ws.addCell(label26);
			ws.addCell(label27);
		}
	}
	
	/**
	 * 写出并关闭
	 * @throws IOException
	 * @throws WriteException
	 */
	public void close() throws IOException, WriteException{
		wwb.write();
		wwb.close();
	}
	
	/**
	 * 获取片段
	 * @param fileStr
	 * @return
	 * @throws Exception
	 */
	private Sheet getSheet(String fileStr) throws Exception {

		File file = new File(fileStr);

		Workbook wb = Workbook.getWorkbook(file);

		Sheet sheet = wb.getSheet(0);

		return sheet;
	}
	
	/**
	 * 获取标题数组
	 * @param fileStr
	 * @return
	 * @throws Exception
	 */
	public String[] getCells(String fileStr) throws Exception {
		Sheet sheet = getSheet(fileStr);
		String[] strs = new String[sheet.getColumns()];
		
		int colCount = sheet.getColumns();


		for (int i = 0; i < colCount; i++) {
			Cell c = sheet.getCell(i, 0);
			strs[i] = c.getContents();
		}
		return strs;
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
}
