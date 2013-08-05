package com.ambow.trainingengine.wordbank.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.wordbank.domain.WordBasic;
import com.ambow.trainingengine.wordbank.service.ParseWordService;
import com.ambow.trainingengine.wordbank.service.WordBasicService;
import com.ambow.trainingengine.wordbank.util.ParseWordFile;

@SuppressWarnings("serial")
public class ImportWordAction extends BaseAction {
	
	File file;
	
	String fileContentType;
	
	String fileFileName;
	
	String fullUri;
	
	String importInfo = "";
	
	private WordBasicService wordBasicService;
	
	private ParseWordService parseWordService; 
		
	public ArrayList<WordBasic> words = new ArrayList<WordBasic>(0); 
	
	/**导入单词到数据库*/
	@Override
	public String execute() {
		try {
			saveFile();
			wordBasicService.saveall(words);
			importInfo = "导入成功！";			
		} catch (Exception e) {
			parseWordService.clear();
			importInfo = e.getLocalizedMessage();
		}
		
		parseWordService.clear();
		this.setRequestAttribute("importInfo", importInfo);
		return INPUT;
	}

	/**显示上传页面*/
	public String show() {
		return INPUT;
	}

	@Override
	public String getAuthStr() {
		return null;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private void parseItems(String filename) throws Exception {
		String impman = getAuthStr();
		List<Object[]> list = ParseWordFile.getExcelList(filename, impman, 0);
				
		parseWordService.setWordList(list);
		parseWordService.parse();
		setWords(parseWordService.getWords());
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
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

	public WordBasicService getWordBasicService() {
		return wordBasicService;
	}

	public void setWordBasicService(WordBasicService wordBasicService) {
		this.wordBasicService = wordBasicService;
	}

	public ParseWordService getParseWordService() {
		return parseWordService;
	}

	public void setParseWordService(ParseWordService parseWordService) {
		this.parseWordService = parseWordService;
	}

	public ArrayList<WordBasic> getWords() {
		return words;
	}

	public void setWords(ArrayList<WordBasic> words) {
		this.words = words;
	}

}