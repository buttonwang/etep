package com.ambow.trainingengine.itembank.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.service.ChangeItemImageService;
import com.ambow.trainingengine.itembank.service.ItemService;

/*
 * ImportImageAction.java
 * 
 * Created on 2008-8-26 下午08:38:17
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
@SuppressWarnings("serial")
public class ImportImageAction extends BaseAction {

	File file;
	String fileContentType;
	String fileFileName;
	String fullUri;
	
	int itemId;
	String src;
	String importInfo = "";	
	private ItemService itemService; 
	private ChangeItemImageService changeItemImageService;
	
	@Override
	public String execute() {
		try {
			saveFile();
			changeItemImageService.changeItemSrc(itemId, src, String.valueOf(itemId) + fileFileName);
			importInfo = "导入成功！";
		} catch (Exception e) {
			importInfo = e.getLocalizedMessage();
		}
		
		this.setRequestAttribute("importInfo", importInfo);
		return INPUT;
	}

	/**显示上传页面*/
	public String show() {
		return INPUT;
	}
	
	@SuppressWarnings("unused")
	private void saveFile() throws IOException  {
		FileInputStream fileIn = null;		
		FileOutputStream fileOut = null;
		String saveFileName = this.getRootDir() + String.valueOf(itemId) + fileFileName ;
		
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
		} finally {
			fileIn.close();
			fileOut.close();
			fileIn = null;
			fileOut = null;
		}
	}
	
	/* (non-Javadoc) 
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
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

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getImportInfo() {
		return importInfo;
	}

	public void setImportInfo(String importInfo) {
		this.importInfo = importInfo;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public void setChangeItemImageService(
			ChangeItemImageService changeItemImageService) {
		this.changeItemImageService = changeItemImageService;
	}

}
