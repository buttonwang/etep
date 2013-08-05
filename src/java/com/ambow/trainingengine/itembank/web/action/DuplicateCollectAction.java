/**
 * 
 */
package com.ambow.trainingengine.itembank.web.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.ItemType;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.service.AnswerOptionService;
import com.ambow.trainingengine.itembank.service.ItemDuplicateService;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.ItemTypeService;
import com.ambow.trainingengine.util.CloudCalculate;
import com.ambow.trainingengine.util.ExtraHtmlParser;
import com.ambow.trainingengine.util.FileUtil;
import com.ambow.trainingengine.util.SimpleWordSegment;
/**
 * @author yuanjunqi
 * 
 */
public class DuplicateCollectAction extends BaseAction {
	private int pageNo;
	private String ids;
	private String itemIds;
	private String dupIds;
	
	private String subject;
	private String grade;
	private String type;
	/**
	 * 
	 */
	private static final long serialVersionUID = -475414856254315784L;

	private ItemService itemService = null;
	
	private ItemTypeService itemTypeService = null;
	
	private ItemDuplicateService itemDuplicateService = null;
	
	private AnswerOptionService answerOptionService = null;
	
	/**
	 * 节点列表
	 */
	public String menu(){
		List<ItemType> list = this.getItemTypeService().list(subject, grade,null);
		this.setRequestAttribute("list", list);
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		return "menu";
	}

	/**
	 * 重复试题的查找
	 * 
	 * @return
	 * @throws Exception 
	 */
	public String seek() throws Exception {
		
		String root = getHttpServletRequest().getSession().getServletContext().getRealPath(File.separator);
		String tempFile = root + File.separator + "temp.dat";
		String dataDir = root + File.separator + "data" + File.separator;

		//初始化
		this.init(tempFile,dataDir);
		//相似度计算
		this.calculate(tempFile,dataDir);
		//写入到查重数据库
		this.writeDatatoDatabase(dataDir);
		return "seek";
	}
	
	/**
	 * 查询显示
	 * @return
	 */
	public String query(){
		int size = 10;
		Integer total = this.getItemDuplicateService().getRecordCount(subject,grade, type);
		setRequestAttribute("total", total);
		if (pageNo < 1) {
			pageNo = 1;
		}
		String subType = type.substring(type.length()-2, type.length());
		List<Map<String,Object>> recordList = this.getItemDuplicateService().queryForList(pageNo, size, subject,grade, type);
		List<Map<String,Object>> newRecordList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> recordMap :recordList){
			Integer item1 = (Integer)recordMap.get("item1");
			Integer item2 = (Integer)recordMap.get("item2");
			Map<String,Object> item1Map = this.getItemService().getItemMap(item1);
			Map<String,Object> item2Map = this.getItemService().getItemMap(item2);
			if(item1Map != null){
				recordMap.put("item1_id", item1Map.get("id"));
				if(subType.equals("11") || subType.equals("12")){
					String select = this.getSelect((Integer)item1Map.get("id"));
					recordMap.put("item1_content", item1Map.get("content")+select);
				}else{
					recordMap.put("item1_content", item1Map.get("content"));
				}
			}
			if(item2Map != null){
				recordMap.put("item2_id", item2Map.get("id"));
				if(subType.equals("11") || subType.equals("12")){
					String select = this.getSelect((Integer)item2Map.get("id"));
					recordMap.put("item2_content", item2Map.get("content")+select);
				}else{
					recordMap.put("item2_content", item2Map.get("content"));
				}
			}
			newRecordList.add(recordMap);
		}
		recordList = null;
		if (newRecordList != null) {
			setRequestAttribute("recordList", newRecordList);
		}
		
		Integer totalPage = total/size+1;
		setRequestAttribute("totalPage", totalPage);
		setRequestAttribute("pageNo", pageNo);
		setRequestAttribute("subject", subject);
		setRequestAttribute("grade", grade);
		setRequestAttribute("type", type);
		return "list";
	}
	
	public String getSelect(Integer itemId){
		StringBuffer optionBuff = new StringBuffer();
		List<Map<String,Object>> optionList = this.getAnswerOptionService().getContent(itemId);
		for(Map<String,Object> optionMap : optionList){
			optionBuff.append("<br>"+optionMap.get("code")+":"+optionMap.get("content"));
		}
		return optionBuff.toString();
	}
	
	/**
	 * 记录对比
	 * @return
	 */
	public String compare(){
		List<Map<String,Object>> list =this.getItemService().getList(ids);
		setRequestAttribute("list", list);
		return "compare";
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String deleteBatch(){
		this.getItemService().delete(itemIds);
		this.getItemDuplicateService().delete(dupIds);
		return "delete";
	}
	
	/**
	 * 初始化操作
	 * @throws IOException 
	 */
	private void init(String tempFile,String dataDir) throws IOException{
		FileUtil.fullyDelete(new File(tempFile));
		FileUtil.fullyDelete(new File(dataDir));
		this.getItemDuplicateService().clear(subject, grade,type);
	}
	
	/**
	 * 计算相似度保存到文件中
	 * @throws Exception
	 */
	private void calculate(String tempFile,String dataDir) throws Exception{
		SimpleWordSegment wordSegment = new SimpleWordSegment();
		List<ItemType> typeList = this.getItemTypeService().list(subject,grade,type);
		String[] args = new String[2];
		for(int i=0;i<typeList.size();i++){
			FileUtil.fullyDelete(new File(tempFile));
			ItemType itemType = typeList.get(i);
			List<Map<String, Object>> list = this.getItemService().getList(itemType.getSubject().getCode(),itemType.getCode());
			String content = "";
			for(int ii=0;ii<list.size();ii++){
				Map<String,Object> map = list.get(ii);
				Integer id = (Integer)map.get("id");
				String str = (String)map.get("content");
				String con = ExtraHtmlParser.extractTextByVisitor(str, "utf-8");

				String result = wordSegment.segment(con, " ");
				content = content + id+":"+result + System.getProperty("line.separator");
			}
			FileUtil.writeFile(tempFile, content);
			Long time = System.currentTimeMillis();
			args[0] = tempFile;
			args[1] = dataDir+itemType.getSubject().getCode()+File.separator+itemType.getGrade().getCode()+File.separator+itemType.getCode();

			int res = ToolRunner.run(new Configuration(), new CloudCalculate(), args);

			System.out.println(System.currentTimeMillis() - time);

		}
	}
	
	/**
	 * 重复记录信息写入到数据库
	 * @throws Exception
	 */
	private void writeDatatoDatabase(String dataDir) throws Exception{
		List<Object[]> arrList = new ArrayList<Object[]>();
		List<File> list = FileUtil.getFileList(dataDir, null);
		for(int i=0;i<list.size();i++){
			String subjectCode = list.get(i).getParentFile().getParentFile().getParentFile().getName();
			String gradeCode = list.get(i).getParentFile().getParentFile().getName();
			String typeCode = list.get(i).getParentFile().getName();
			List<String> strList = FileUtil.readFile(list.get(i), "utf-8");
			if(strList != null && strList.size()>0){
				for(int j=0;j<strList.size();j=j+2){
					Object[] arr = new Object[6];
					String str = strList.get(j);
					String[] strArr = str.split("	");
					String str1 = strList.get(j+1);
					String[] strArr1 = str1.split("	");

					arr[0] = Integer.parseInt(strArr[0]);
					arr[1] = Integer.parseInt(strArr1[0]);
					arr[2] = Double.parseDouble(strArr[1]);
					arr[3] = subjectCode;
					arr[4] = gradeCode;
					arr[5] = typeCode;
					arrList.add(arr);
				}
			}
		}
		
		for(int ii=0;ii<arrList.size();ii++){
			Object[] arr = arrList.get(ii);
			this.getItemDuplicateService().save(arr);
		}
	}
	
	@Override
	public String getAuthStr() {
		// TODO Auto-generated method stub
		return null;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}
	
	public ItemTypeService getItemTypeService() {
		return itemTypeService;
	}

	public void setItemTypeService(ItemTypeService itemTypeService) {
		this.itemTypeService = itemTypeService;
	}

	public ItemDuplicateService getItemDuplicateService() {
		return itemDuplicateService;
	}

	public void setItemDuplicateService(ItemDuplicateService itemDuplicateService) {
		this.itemDuplicateService = itemDuplicateService;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getItemIds() {
		return itemIds;
	}

	public void setItemIds(String itemIds) {
		this.itemIds = itemIds;
	}

	public String getDupIds() {
		return dupIds;
	}

	public void setDupIds(String dupIds) {
		this.dupIds = dupIds;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public AnswerOptionService getAnswerOptionService() {
		return answerOptionService;
	}

	public void setAnswerOptionService(AnswerOptionService answerOptionService) {
		this.answerOptionService = answerOptionService;
	}

}
