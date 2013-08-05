package com.ambow.trainingengine.exam.web.data;

import java.util.List;


/*
 * Segment.java
 * 
 * Created on Jul 2, 2008 9:22:33 PM
 * 节的概念--
 * Copyright(C) 2008, by Ambow Research&Development Branch.
 * 
 * Original Author: Li Xin
 * Contributor(s): 参与者的名称，参与者名称2，
 * 
 * Changes 
 * -------
 * $log$
 */
public class Section {
	
	private List<Page> pages;
	
	private String instruction;

	private String title;

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
		if(pages.size()>0){
			this.instruction=pages.get(0).getInstruction();
			this.title=pages.get(0).getTitle();
		}
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
