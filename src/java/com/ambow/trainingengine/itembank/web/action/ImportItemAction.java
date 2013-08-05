package com.ambow.trainingengine.itembank.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.ParseItemService;
import com.ambow.trainingengine.itembank.util.ParseItemFile;

@SuppressWarnings("serial")
public class ImportItemAction extends BaseAction {
	
	protected static final Log logger = LogFactory.getLog(ImportItemAction.class);
	
	File file;
	String fileContentType;
	String fileFileName;
	String fullUri;
	String dirName;
	String importInfo = "";
	String batchimportInfo = "";	
	String batchImportError = "";	
	
	private ItemService itemService;
	private ParseItemService parseItemService; 
	public ArrayList<Item> items = new ArrayList<Item>(0); 
	private String dirstrs = "";
	
	@Override
	public String execute() {
		try {
			saveFile();
			itemService.saveall(items);
			importInfo = fileFileName + " 导入成功！";
		} catch (Exception e) {	
			importInfo = e.getLocalizedMessage();
			if (importInfo==null) {
				for(StackTraceElement element: e.getStackTrace()){
					importInfo=importInfo+element.toString()+"  <br>  ";
				}
			}
		}
		
		parseItemService.clear();
		this.setRequestAttribute("importInfo", importInfo);
		return INPUT;
	}

	public boolean importPaper() {
		boolean r = false;
		try {
			saveFile();
			itemService.saveall(items);
			//parseItemService.clear();
			return true;
		} catch (Exception e) {
			importInfo = e.getLocalizedMessage();
		}
		return r;
	}
	
	/** 从本地目录下批量导入 
	 * @throws IOException */
	public String dirImport() throws IOException {
		String[] files = serachFiles(dirName);
		int importnum = 0, itemnum = 0;
		for(String savefile: files) {
			try {				 
				parseItems(savefile);
				if (items.size()==0) {
					batchImportError += savefile + "没有试题，请查阅文件！<br>	"; 
					continue;
				}
				itemService.saveall(items);
								
				importnum++;
				itemnum += items.size();
				logger.info(savefile);
			} catch (Exception e) {
				batchImportError += savefile + "导入失败！<br>	";
				// TODO: handle exception
			} finally {
				parseItemService.clear();
				items.clear();
			}
		}
		
		batchimportInfo = "共" + String.valueOf(importnum) + "个HTML文件导入成功！<br>";
		batchimportInfo+= "共" + String.valueOf(itemnum) + "道试题导入成功！";
		
		return INPUT;
	}
	
	/**显示上传页面*/
	public String show() {
		return INPUT;
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

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	@SuppressWarnings("unused")
	private void saveFile()  {
		FileInputStream fileIn = null;		
		FileOutputStream fileOut = null;				
		String saveFileName = this.getRootDir() + fileFileName;
		
		try {
			fileIn = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		try {			
			fileOut = new FileOutputStream(saveFileName);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
				
		byte[] buffer = new byte[1024];
		int len;
		try {
			fileIn.mark(0);
			while (( len = fileIn.read(buffer)) > 0) {
				fileOut.write(buffer, 0, len);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			parseItems(saveFileName);
			
			fileIn.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void parseItems(FileInputStream fileIn) throws IOException {
		ParseItemFile p = new ParseItemFile();
		String[] testitems = p.parse(fileIn);
				
		for(String s: testitems) {
			System.out.println(s);
		}
		
		parseItemService.setParseStr(testitems);
		parseItemService.setImportFileName(fileFileName);
		parseItemService.parse();
		setItems(parseItemService.getItems());
	}
	
	@SuppressWarnings("unused")
	private void parseItems(String filename) throws IOException {
		ParseItemFile p = new ParseItemFile();
		String[] testitems = p.parse(filename);
				
		for(String s: testitems) {
			//System.out.println(s);
		}
		
		parseItemService.setParseStr(testitems);
		if (fileFileName != null)
			parseItemService.setImportFileName(fileFileName);
		else {
			filename = filename.substring(filename.lastIndexOf("\\")+1);
			parseItemService.setImportFileName(filename);
		}
		parseItemService.parse();
		setItems(parseItemService.getItems());
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

	public ParseItemService getParseItemService() {
		return parseItemService;
	}

	public void setParseItemService(ParseItemService parseItemService) {
		this.parseItemService = parseItemService;
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
}