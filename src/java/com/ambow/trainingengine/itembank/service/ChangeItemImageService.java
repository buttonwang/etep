package com.ambow.trainingengine.itembank.service;

import com.ambow.core.dao.HibernateGenericDao;
import com.ambow.trainingengine.itembank.domain.Item;

/*
 * ChangeItemImageService.java
 * 
 * Created on 2008-9-9 下午06:04:57
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
public class ChangeItemImageService {
	private String imgPath;	
	private HibernateGenericDao genService;
	
	public void changeItemSrc(int itemId, String src, String saveimg){
		String imgname = src.substring(src.lastIndexOf("/")+1); 
		String imgfname= "src=\"" + imgPath + "/" + saveimg + "\"";
		String repname ="src=([^<>])*" + imgname +"\"";
		Item item = genService.get(Item.class, itemId);		
		item.setContent(item.getContent().replaceFirst(repname, imgfname));
		genService.save(item);
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public void setGenService(HibernateGenericDao genService) {
		this.genService = genService;
	}

}
