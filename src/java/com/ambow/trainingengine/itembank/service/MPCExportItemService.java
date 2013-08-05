/**
 * 
 */
package com.ambow.trainingengine.itembank.service;

import java.util.List;

import com.ambow.trainingengine.itembank.domain.Item;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.util.ExtraExcel;

/**
 * @author yuanjunqi
 *
 */
@SuppressWarnings("serial")
public class MPCExportItemService {
	
	private String rootDir;
	private String templetFile;
	private String exportFile;
	private ItemTypeService itemTypeService;
	private ItemService itemService;

	@SuppressWarnings("unchecked")
	public void exportExcel(String subject,String grade) throws Exception {
		List<ItemType> itemTypeList = this.itemTypeService.list(subject, grade, null);
		ExtraExcel extraExcel = new ExtraExcel();
		extraExcel.create(rootDir+exportFile);
		String[] strs = extraExcel.getCells(rootDir+templetFile);
		for(int i=0;i<itemTypeList.size();i++){
			ItemType itemType = itemTypeList.get(i);
			List<Item> itemList = this.itemService.getItemList(itemType.getCode());
			String code = itemType.getCode();
			code = code.substring(3);
			extraExcel.writeSheet(strs,itemList, code, i);
		}
		extraExcel.close();
	}

	/**
	 * 获取试题类型字符串
	 * @return
	 */
	public String getItemTypeStr(List<ItemType> itemTypeList){
		String itemTypeStr = null;
		
		return itemTypeStr;
	}
	
	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
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

	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

}
