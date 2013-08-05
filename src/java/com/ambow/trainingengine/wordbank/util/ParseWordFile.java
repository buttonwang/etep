package com.ambow.trainingengine.wordbank.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/*
 * ParseWordFile.java
 * 
 * Created on 2008-7-21 下午01:44:39
 * 
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Wang Wei
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 * 1.	解析单词文件，来源是EXCEL文件。
 */
public class ParseWordFile {
	
	protected static final Log logger = LogFactory.getLog(ParseWordFile.class);
	
	public static List<Object[]> getExcelList(String FileName, String ImpMan, int SheetNum) throws Exception {
		String InputFile = FileName;
		FileInputStream InputFS = null;
		
		List<Object[]> elist = new ArrayList<Object[]>();
								
		try {
			InputFS = new FileInputStream(InputFile);
			POIFSFileSystem fs = new POIFSFileSystem(InputFS);
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(SheetNum);
			
			int	rownum = sheet.getPhysicalNumberOfRows()+1;
			int cellnum = sheet.getRow(0).getPhysicalNumberOfCells();
			for (int r = 1; r < rownum; r++) {
				HSSFRow row   = sheet.getRow(r);   
                Object[] cells = new Object[cellnum];     
                logger.debug("row id:" + String.valueOf(r));
                
                if (row==null) continue;
                if (row.getCell((short)0)==null) continue;
                if (row.getCell((short)0).getCellType()==HSSFCell.CELL_TYPE_BLANK) continue;
                for (short c = 0; c < cellnum; c++){
                    HSSFCell cell  = row.getCell(c);
                    Object cellvalue = null;
                    if (cell != null){
	                    switch (cell.getCellType()){
	                        case HSSFCell.CELL_TYPE_FORMULA:
	                        	cellvalue = cell.getNumericCellValue();
	                            break;
	                        case HSSFCell.CELL_TYPE_NUMERIC:
	                        	if (HSSFDateUtil.isCellDateFormatted(cell)) {                				
	                        		cellvalue = cell.getDateCellValue();
	                			}else {
	                				cellvalue = cell.getNumericCellValue();
	                			}     
	                            break;
	                        case HSSFCell.CELL_TYPE_STRING:
	                        	cellvalue = cell.toString();
	                            break;
	                        case HSSFCell.CELL_TYPE_BOOLEAN:
	                        	cellvalue = cell.getBooleanCellValue();
	                            break;    
	                        default : cellvalue = cell.toString();
	                    }
                    }
                    cells[c] = cellvalue;
                    logger.debug(cellvalue);
                }
                //cells[cellnum-2] = FileName;
                //cells[cellnum-1] = ImpMan;
                
                
                elist.add(cells); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (InputFS != null)
				InputFS.close();
		}
		return elist;	
	}
}
