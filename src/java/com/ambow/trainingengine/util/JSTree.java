package com.ambow.trainingengine.util;

import java.util.List;

public abstract class JSTree {
	public String getToString( ){
		return  String.format("{'pid_id':'%s','id':'%s','pid':'%s','obj':",pid+"_"+id,id,pid)+"%s}";
	};
	public String objToString(){
		return "{"+obj.toString()+"}";
	};
	public String getTreeN_Name(){
		return "'"+obj.toString()+"'";
	};
	public static String getJsonStr (List<JSTree> jsTreeLst){
		StringBuilder sb = new StringBuilder();
		int i = 0;	
		sb.append("[");
		for (JSTree jsTree : jsTreeLst) {
			if(++i>1){
				sb.append(",");
			}
			String objToStr = jsTree.objToString();
			if(objToStr==null||"".equals(objToStr.trim())){
				objToStr = "{}";
			}
			sb.append(String.format("{'id':'%s','pid':'%s','obj':",jsTree.id,jsTree.pid)+objToStr+"}");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static String  getTreeNData(List<JSTree> jsTreeLst ,String treeName){
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (JSTree jsTree : jsTreeLst) {
			String nodeTreeN = "{5}.N['{0}']='checked:{1};T:{2};url:{3};show:{4}';";
			String isChecked = String.valueOf(jsTree.checked);
			String isShow = String.valueOf(jsTree.show);
			String pid_id = jsTree.getPid() +"_"+jsTree.getId();
			String url = "#";
			sb.append(param(nodeTreeN, pid_id, isChecked, jsTree.getTreeN_Name(),url,isShow,treeName));
			sb.append("\n");
		}
		return sb.toString();
	}
	private String pid="-1";
	private String id;
	private Object obj;
	/** 是否显示 0显示，1不显示 */
	private int show = 0;
	/** 是否被选中，0选中，1不选中 */
	private int checked = 0;
 
	public Object getObj() {
		return obj;
	}

	public void setObj(Object o) {
		this.obj = o;
	}
	
	public String getTreeN(String treeName) {
		String nodeTreeN = "{5}.N['{0}']='checked:{1};T:{2};url:{3};show:{4}";
		String isChecked = String.valueOf(this.checked);
		String isShow = String.valueOf(this.show);
		String url = "#";
		return param(nodeTreeN, computePid_id(), isChecked, obj.toString(),url,isShow,treeName);
	}
	
	
	public static String param(String... arguments) {
		int num = arguments.length;
		String oStr = (String) arguments[0];

		for (int i = 1; i < num; i++) {
			String r = "";
			if (arguments[i] != null) {
				r = arguments[i];
			}
			oStr = oStr.replaceAll("\\{" + (i - 1) + "\\}", r);
		}
		return oStr;
	}

	public int getShow() {
		return show;
	}

	public void setShow(int show) {
		this.show = show;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}

	public String computePid_id() {
		return  pid+"_"+id;
	}

	public String getPid() {
		return pid;
	}

	//设置并计算pid_id
	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
