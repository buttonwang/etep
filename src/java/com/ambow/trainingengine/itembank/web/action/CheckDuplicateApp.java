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

import com.ambow.trainingengine.itembank.domain.ItemType;
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
public class CheckDuplicateApp extends LoadAppContext{

	private ItemService itemService = null;
	
	private ItemTypeService itemTypeService = null;
	
	private ItemDuplicateService itemDuplicateService = null;
	
	private AnswerOptionService answerOptionService = null;
	
	private String subject = null;
	private String grade = null;
	private String type = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		CheckDuplicateApp app = new CheckDuplicateApp();
		app.setUp();
		for(int i=0;i<args.length;++i){
			if("-subject".equals(args[i])){
				app.setSubject(args[++i]);
			}else if("-grade".equals(args[i])){
				app.setGrade(args[++i]);
			}else if("-type".equals(args[i])){
				app.setType(args[++i]);
			}
		}
		app.setItemService((ItemService)app.getBean("itemService"));
		app.setItemTypeService((ItemTypeService)app.getBean("itemTypeService"));
		app.setItemDuplicateService((ItemDuplicateService)app.getBean("itemDuplicateService"));
		app.setAnswerOptionService((AnswerOptionService)app.getBean("answerOptionService"));
		app.seek();

	}
	
	/**
	 * 重复试题的查找
	 * 
	 * @return
	 * @throws Exception 
	 */
	public void seek() throws Exception {
		long current = System.currentTimeMillis();
		String root = System.getProperty("user.dir");
		String tempFile = root + File.separator + "temp.dat";
		String dataDir = root + File.separator + "data" + File.separator;
		System.out.println("dataDir="+dataDir);

		//初始化
		this.init(tempFile,dataDir);
		//相似度计算
		this.calculate(tempFile,dataDir);
		//写入到查重数据库
		this.writeDatatoDatabase(dataDir);
		
		long time = System.currentTimeMillis() - current;
		System.out.println("total time :" + time/1000 + " 秒");
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
		System.out.print("size="+typeList.size());
		String[] args = new String[2];
		for(int i=0;i<typeList.size();i++){
			FileUtil.fullyDelete(new File(tempFile));
			ItemType itemType = typeList.get(i);
			if(itemType.getSubject() == null){
				continue;
			}
			List<Map<String, Object>> list = this.getItemService().getList(itemType.getSubject().getCode(),itemType.getCode());
			String content = "";
			for(int ii=0;ii<list.size();ii++){
				Map<String,Object> map = list.get(ii);
				Integer id = (Integer)map.get("id");
				String code = (String)map.get("code");
				String str = (String)map.get("content");
				
				String subType = itemType.getCode().substring(itemType.getCode().length()-2, itemType.getCode().length());
				if(subType.equals("11") || subType.equals("12")){
					String select = this.getSelect(id);
					str = str + select;
				}
				String con = ExtraHtmlParser.extractTextByVisitor("<div>"+str+"</div>", "utf-8");
				String result = wordSegment.segment(con, " ");
				content = content + id+"-"+code+":"+result + System.getProperty("line.separator");
			}
			FileUtil.writeFile(tempFile, content);
			Long time = System.currentTimeMillis();
			args[0] = tempFile;
			args[1] = dataDir+itemType.getSubject().getCode()+File.separator+itemType.getGrade().getCode()+File.separator+itemType.getCode();

			int res = ToolRunner.run(new Configuration(), new CloudCalculate(), args);

			System.out.println(System.currentTimeMillis() - time);

		}
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
			System.out.println("subject="+subjectCode+" grade="+gradeCode+" type="+typeCode);
			System.out.println(list.get(i));
			List<String> strList = FileUtil.readFile(list.get(i), "utf-8");
			if(strList != null && strList.size()>0){
				for(int j=0;j<strList.size();j=j+2){
					Object[] arr = new Object[8];
					String str = strList.get(j);
					String[] strArr = str.split("	");
					String str1 = strList.get(j+1);
					String[] strArr1 = str1.split("	");
					
					String[] item1Arr = strArr[0].split("-");
					String[] item2Arr = strArr1[0].split("-");
					arr[0] = Integer.parseInt(item1Arr[0]);
					arr[1] = item1Arr[1];
					arr[2] = Integer.parseInt(item2Arr[0]);
					arr[3] = item2Arr[1];
					arr[4] = Double.parseDouble(strArr[1]);
					arr[5] = subjectCode;
					arr[6] = gradeCode;
					arr[7] = typeCode;
					arrList.add(arr);
				}
			}
		}
		
		for(int ii=0;ii<arrList.size();ii++){
			Object[] arr = arrList.get(ii);
			this.getItemDuplicateService().save(arr);
		}
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

	@Override
	protected void setPath() {
		// TODO Auto-generated method stub
		
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
