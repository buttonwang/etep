package com.ambow.trainingengine.itembank.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.service.ExportItemService;
import com.ambow.trainingengine.itembank.service.MPCExportItemService;

/*
 * ExportItemAction.java
 * 
 * Created on 2008-9-1 下午04:19:55
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
public class ExportItemAction extends BaseAction {

	private ExportItemService exportItemService;
	private MPCExportItemService mpcexportItemService;
	private String fileName;
	private InputStream inputStream;
	private String subject;
	private String grade;

	@Override
	public String execute() {
		try {
			exportItemService.setRootDir(this.getRootDir());
			exportItemService.exportExcel();
			
			String inFileName = getRootDir()+exportItemService.getExportFile();
			File file = new File(inFileName);
			inputStream = new FileInputStream(file);
			fileName = exportItemService.getExportFile();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return SUCCESS;
	}
	
	/**
	 * 试题excel导出(MPC)
	 * @return
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 */
	public String export() {
		try{
			mpcexportItemService.setRootDir(this.getRootDir());
			mpcexportItemService.exportExcel(this.subject, this.grade);
			//mpcexportItemService.exportExcel("E", "Z1");
			String inFileName = this.getRootDir() + mpcexportItemService.getExportFile();
			File file = new File(inFileName);
			inputStream = new FileInputStream(file);
			fileName = mpcexportItemService.getExportFile();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/* (non-Javadoc)
	 * @see com.ambow.core.web.action.BaseAction#getAuthStr()
	 */
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ExportItemService getExportItemService() {
		return exportItemService;
	}

	public void setExportItemService(ExportItemService exportItemService) {
		this.exportItemService = exportItemService;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public MPCExportItemService getMpcexportItemService() {
		return mpcexportItemService;
	}

	public void setMpcexportItemService(MPCExportItemService mpcexportItemService) {
		this.mpcexportItemService = mpcexportItemService;
	}
	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
}
