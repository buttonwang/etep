package com.ambow.trainingengine.itembank.web.action;

import com.ambow.core.dao.support.Page;
import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.Region;
import com.ambow.trainingengine.itembank.service.RegionService;

/*
 * RegionAction.java
 * 
 * Created on 2008-7-9 下午09:40:19
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
public class RegionAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private RegionService regionService;
	
	private Region region; 
	
	private String code = "";
	
	private String codes = "";
	
	private String queryType = "";
	
	private String queryValue = "";
	
	private int pageNo = 1;
	
	/** 查看列表 */
	public String list(){
		Page page = null;
		if (queryValue.equals(""))
			page = this.regionService.list(pageNo);
		else if (queryType.equals("1"))
			page  = this.regionService.findByCode(pageNo, queryValue);
		else if (queryType.equals("2"))
			page  = this.regionService.findByName(pageNo, "%"+queryValue+"%");
		
		this.setRequestAttribute("page", page);
		return "home";
	}
		
	/** 保存对象 */
	public String save(){
		Region actRegion = this.regionService.get(this.region.getCode());
		if (actRegion == null ) {
			this.regionService.save(this.region);
		} else {
			actRegion.setName(this.region.getName());
			this.regionService.save(actRegion);
		}
		
		return "redirect";
	}
	
	/** 显示页面，让用户可以看到已存在的数据*/
	public String show(){
		this.region = this.regionService.get(code);
		this.setRequestAttribute("region", this.region);
		return "view";
	}
	
	/**显示页面，让用户可以看到已存在的数据并对其进行修改*/
	public String edit(){
		this.region = this.regionService.get(code);
		this.setRequestAttribute("region", this.region);
		return INPUT;
	}
	
	/**删除对象*/
	public String delete(){
		this.regionService.removeById(code);
		return "redirect";
	}
	
	/**批量删除对象*/
	public String deleteBatch(){
		this.regionService.deleteBatch(codes);
		return "redirect";
	}
		
	@Override
	public String execute(){
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

	public RegionService getRegionService() {
		return regionService;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}


	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodes() {
		return codes;
	}

	public void setCodes(String codes) {
		this.codes = codes;
	}
}
