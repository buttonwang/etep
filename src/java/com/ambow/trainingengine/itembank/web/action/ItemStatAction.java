/**
 * 
 */
package com.ambow.trainingengine.itembank.web.action;

import java.util.List;
import java.util.Map;

import com.ambow.core.web.action.BaseAction;
import com.ambow.trainingengine.itembank.domain.KnowledgePoint;
import com.ambow.trainingengine.itembank.domain.Model;
import com.ambow.trainingengine.itembank.service.ItemService;
import com.ambow.trainingengine.itembank.service.KnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelKnowledgePointService;
import com.ambow.trainingengine.itembank.service.ModelService;

/**
 * @author yuanjunqi
 *
 */
public class ItemStatAction extends BaseAction {

	private static final long serialVersionUID = 1415016254750793460L;
	private String subject ;
	private String grade;
	private String point;
	private Integer model;
	private KnowledgePointService knowledgePointService = null;
	private ItemService itemService = null;
	private ModelService modelService;
	private ModelKnowledgePointService modelKnowledgePointService;

	/**
	 * 节点列表
	 */
	public String menu(){

		List<KnowledgePoint> list = this.getKnowledgePointService().listOrderByCode(subject,grade);
		this.setRequestAttribute("list", list);
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		return "menu";
	}
	
	/**
	 * 模块菜单
	 * @return
	 */
	public String modelMenu(){
		List<Model> modelList = this.getModelService().queryForList(subject, grade);
		this.setRequestAttribute("modelList", modelList);
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		return "modelMenu";
	}
	
	/**
	 * 数理化统计
	 */
	public String execute() {
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		List<Map<String,Object>> list = null;
		if(model != null){
			list = this.getKnowledgePointService().getListWithModel(point,model);
		}else{
			list = this.getKnowledgePointService().getList(point);
		}
		
		if(list.size()==0){
			return this.node();
		}
		String[] names = new String[list.size()];
		String[] ids = new String[list.size()];
		long[][] arr = new long[list.size()][16];
		long[] statArr = new long[16];
		long[] stat = new long[3];
		String points = "";
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String code = (String)map.get("code");
			//String name = (String)map.get("name");
			names[i] = code;
			ids[i]= getIds("",code);
			if(points.equals("")){
				points = points + ids[i];
			}else{
				points = points +"," +ids[i];
			}
		}

		List<Map<String,Object>> statList = this.getItemService().getStatList(points);

		for(int i=0;i<statList.size();i++){
			Map<String,Object> map = statList.get(i);
			Long num = (Long)map.get("num");
			String name = (String)map.get("name");
			String code = (String)map.get("knowledge");
			for(int d=0;d<ids.length;d++){
				if(ids[d].contains(code)){
					this.judge(name, arr,null,null, num, d);
				}
			}
		}
		for(int i=0;i<names.length;i++){
			arr[i][2]= arr[i][0]+arr[i][1];
			arr[i][5]= arr[i][3]+arr[i][4];
			arr[i][8]= arr[i][6]+arr[i][7];
			arr[i][11]= arr[i][9]+arr[i][10];
			arr[i][14]= arr[i][12]+arr[i][13];
			arr[i][15]= arr[i][2] + arr[i][5] + arr[i][8] + arr[i][11] + arr[i][14];
			for(int j=0;j<16;j++){
				statArr[j] = statArr[j]+ arr[i][j];
			}
		}
		stat[0] = statArr[2];
		stat[1] = statArr[5] + statArr[8] + statArr[11] + statArr[14];
		stat[2] = statArr[15];

		this.setRequestAttribute("arr", arr);
		this.setRequestAttribute("statArr", statArr);
		this.setRequestAttribute("stat", stat);
		this.setRequestAttribute("names", names);
		return "stat_mpc";
	}
	
	private String getIds(String ids ,String newPoint){
		List<Map<String,Object>> list = this.getKnowledgePointService().getList(newPoint);

		if(list.size()==0){
			if(ids.equals("")){
				ids = ids + "'" + newPoint + "'";
			}else{
				ids = ids + ",'" + newPoint + "'";
			}
			
		}else{
			for(int i =0;i<list.size();i++){
				Map<String,Object> map = list.get(i);
				getIds(ids,(String)map.get("code"));
			}
		}
		return ids;
	}
	
	/**
	 * 模块统计
	 * @return
	 */
	public String modelStat(){
		//System.out.println("model");
		this.setRequestAttribute("subject", subject);
		this.setRequestAttribute("grade", grade);
		List<Map<String,Object>> modellist = this.getModelKnowledgePointService().getList(model);
		String kp = "";
		for(int a=0;a<modellist.size();a++){
			Map<String,Object> map = modellist.get(a);
			String code = (String)map.get("knowledge_point_code");
			if(kp.equals("")){
				kp = kp + "'"+code+"'";
			}else{
				kp = kp +",'" +code+"'";
			}
		}
		List<Map<String,Object>> list = this.getKnowledgePointService().getListWithIn(kp);
		if(list.size()==0){
			return this.node();
		}
		String[] names = new String[list.size()];
		String[] ids = new String[list.size()];
		long[][] arr = new long[list.size()][16];
		long[] statArr = new long[16];
		long[] stat = new long[3];
		String points = "";
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			String code = (String)map.get("code");
			names[i] = code;
			ids[i]= getIds("",code);
			if(points.equals("")){
				points = points + ids[i];
			}else{
				points = points +"," +ids[i];
			}
		}

		List<Map<String,Object>> statList = this.getItemService().getStatList(points);

		for(int i=0;i<statList.size();i++){
			Map<String,Object> map = statList.get(i);
			Long num = (Long)map.get("num");
			String name = (String)map.get("name");
			String code = (String)map.get("knowledge");
			for(int d=0;d<ids.length;d++){
				if(ids[d].contains(code)){
					this.judge(name, arr,null,null, num, d);
				}
			}
		}
		for(int i=0;i<names.length;i++){
			arr[i][2]= arr[i][0]+arr[i][1];
			arr[i][5]= arr[i][3]+arr[i][4];
			arr[i][8]= arr[i][6]+arr[i][7];
			arr[i][11]= arr[i][9]+arr[i][10];
			arr[i][14]= arr[i][12]+arr[i][13];
			arr[i][15]= arr[i][2] + arr[i][5] + arr[i][8] + arr[i][11] + arr[i][14];
			for(int j=0;j<16;j++){
				statArr[j] = statArr[j]+ arr[i][j];
			}
		}
		stat[0] = statArr[2];
		stat[1] = statArr[5] + statArr[8] + statArr[11] + statArr[14];
		stat[2] = statArr[15];

		this.setRequestAttribute("arr", arr);
		this.setRequestAttribute("statArr", statArr);
		this.setRequestAttribute("stat", stat);
		this.setRequestAttribute("names", names);
		//System.out.println(names);
		return "model_stat";
	}
	
	/**
	 * 数理化节点统计
	 * @return
	 */
	public String node(){
		//System.out.println("node");
		List<Map<String,Object>> list = this.getItemService().getStatNodeList(point);
		int[] diff = new int[]{39,49,59,69,79,89,99};
		long[][] arr = new long[diff.length][16];
		String[][] strArr = new String[diff.length][16];
		long[] statArr = new long[16];
		long[] stat = new long[3];
		
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = list.get(i);
			Long num = (Long)map.get("num");

			String code = (String)map.get("item_type_code");
			String name = (String)map.get("name");

			Float difficulty = (Float)map.get("difficulty_value");
			
			if(difficulty == null){
				continue;
			}

			for(int d=0;d<diff.length;d++){

				if(difficulty.intValue() <=diff[d]){
					this.judge(name, arr,strArr,code, num, d);
					break;
				}
			}
		}
		for(int i=0;i<diff.length;i++){
			arr[i][2]= arr[i][0]+arr[i][1];
			arr[i][5]= arr[i][3]+arr[i][4];
			arr[i][8]= arr[i][6]+arr[i][7];
			arr[i][11]= arr[i][9]+arr[i][10];
			arr[i][14]= arr[i][12]+arr[i][13];
			arr[i][15]= arr[i][2] + arr[i][5] + arr[i][8] + arr[i][11] + arr[i][14];
			for(int j=0;j<16;j++){
				statArr[j] = statArr[j]+ arr[i][j];
			}
		}
		stat[0] = statArr[2];
		stat[1] = statArr[5] + statArr[8] + statArr[11] + statArr[14];
		stat[2] = statArr[15];
		this.setRequestAttribute("arr", arr);
		this.setRequestAttribute("strArr", strArr);
		this.setRequestAttribute("statArr", statArr);
		this.setRequestAttribute("stat", stat);
		this.setRequestAttribute("diff", diff);
		this.setRequestAttribute("point", point);
		return "stat_node_mpc";
	}

	/**
	 * 判断
	 * @param name
	 * @param arr
	 * @param num
	 * @param i
	 */
	private void judge(String name,long[][] arr,String[][] codeArr,String code,Long num,int i){
		if(name.equals("单选")){
			arr[i][0] = arr[i][0] + num;
			if(codeArr != null){
				codeArr[i][0] = code;
			}
		}else if(name.equals("多选")){
			arr[i][1] = arr[i][1] + num;
			if(codeArr != null){
				codeArr[i][1] = code;
			}
		}else if(name.equals("一对一填空")){
		//}else if(name.equals("阅读A类")){	
			arr[i][3] = arr[i][3] + num;
			if(codeArr != null){
				codeArr[i][3] = code;
			}
		}else if(name.equals("一对多填空")){
		//}else if(name.equals("阅读B类")){	
			arr[i][4] = arr[i][4] + num;
			if(codeArr != null){
				codeArr[i][4] = code;
			}
		}else if(name.equals("一对一计算")){
			arr[i][6] = arr[i][6] + num;
			if(codeArr != null){
				codeArr[i][6] = code;
			}
		}else if(name.equals("一对多计算")){
			arr[i][7] = arr[i][7] + num;
			if(codeArr != null){
				codeArr[i][7] = code;
			}
		}else if(name.equals("一对一实验")){
			arr[i][9] = arr[i][9] + num;
			if(codeArr != null){
				codeArr[i][9] = code;
			}
		}else if(name.equals("一对多实验")){
			arr[i][10] = arr[i][10] + num;
			if(codeArr != null){
				codeArr[i][10] = code;
			}
		}else if(name.equals("一对一解答")){
			arr[i][12] = arr[i][12] + num;
			if(codeArr != null){
				codeArr[i][12] = code;
			}
		}else if(name.equals("一对多解答")){
			arr[i][13] = arr[i][13] + num;
			if(codeArr != null){
				codeArr[i][13] = code;
			}
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

	public KnowledgePointService getKnowledgePointService() {
		return knowledgePointService;
	}

	public void setKnowledgePointService(KnowledgePointService knowledgePointService) {
		this.knowledgePointService = knowledgePointService;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getPoint() {
		return point;
	}



	public void setPoint(String point) {
		this.point = point;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public ModelService getModelService() {
		return modelService;
	}

	public void setModelService(ModelService modelService) {
		this.modelService = modelService;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public ModelKnowledgePointService getModelKnowledgePointService() {
		return modelKnowledgePointService;
	}

	public void setModelKnowledgePointService(
			ModelKnowledgePointService modelKnowledgePointService) {
		this.modelKnowledgePointService = modelKnowledgePointService;
	}
}
