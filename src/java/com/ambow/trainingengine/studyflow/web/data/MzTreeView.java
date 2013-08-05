package com.ambow.trainingengine.studyflow.web.data;

public class MzTreeView {
	
	private String pid_id;
	private String pid;
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

	public String getTreeN(String treeName) {
		String nodeTreeN = "{5}.N['{0}']='checked:{1};T:{2};url:{3};show:{4}";
		String isChecked = String.valueOf(this.checked);
		String isShow = String.valueOf(this.show);
		String url = "#";
		return MzTreeView.param(nodeTreeN, pid_id, isChecked, obj.toString(),url,isShow,treeName);
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

	public String getPid_id() {
		return pid_id;
	}

	public void setPid_id(String pid_id) {
		this.pid_id = pid_id;
	}

	public String getPid() {
		return pid;
	}

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